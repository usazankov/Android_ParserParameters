package com.inpas.libparseparams;


import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.inpas.types.*;
import com.google.gson.annotations.SerializedName;

public class TLVParserParameters implements IParserParameters {
    Map<Integer, String> tlvFields;
    Map<Integer, String> tlvFieldsRoot;
    private String tagLog = "TLVParserParameters";
    /*final static byte UnknownType = 0x0;
    final static byte HEXType = 0x1;
    final static byte StringType = 0x2;
    final static byte ByteType = 0x3;
    final static byte WordType = 0x4;
    final static byte DwordType = 0x5;
    final static byte DoubleType = 0x6;*/
    private String encoding = new String("windows-1251");

    public void setMapTagTlvToClassName(Map<Integer, String> map){
        tlvFields = map;
    }

    public void setMapRootTagTlvToClassName(Map<Integer, String> map){
        tlvFieldsRoot = map;
    }

    public void setEncoding(String encoding){
        this.encoding = encoding;
    }

    public int byteArrayToInt(byte[] b)
    {
        int value = 0;
        int length = b.length > 4 ? 4 : b.length;
        for (int i = 0; i < length; i++) {
            int shift = (length - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }
    public double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
    public <T> T parse(final byte[] file, final Class<T> tlvClass) throws Exception{
        T t = parseThrowing(file, tlvClass, true);
        return t;
    }

    private <T> T parseThrowing(final byte[] file,
                                final Class<T> tlvClass, boolean isRoot) throws Exception{
        T rootObject = tlvClass.newInstance();
        Map<String, Field> hashMap = new HashMap<String, Field>();
        final Field[] fields = tlvClass.getDeclaredFields();

        //Смотрим какие поля определены в данном классе
        for (Field field : fields) {
            final SerializedName tlvFieldAnnotation = field.getAnnotation(SerializedName.class);
            //Если есть поля с аннотацией SerializedName, то добавляем в таблицу соответствия имени к полю класса
            if(tlvFieldAnnotation != null) {
                hashMap.put(tlvFieldAnnotation.value(), field);
            }
        }
        List<TLVDataObj> list = TLVConvertor.getTLVDataObjList(file);
        for(TLVDataObj obj: list) {
            //По идентификатору тэга получаем соответствующее ему имя класса
            String nameClass;
            if(isRoot) nameClass = tlvFieldsRoot.get(obj.getTagId());
            else nameClass = tlvFields.get(obj.getTagId());
            if(nameClass == null) {
                Log.d(tagLog,"В хэш-таблице TagId-ClassName нет поля, соответствующего TagId: " + obj.getTagId()
                + ". Текущий обрабатываемый класс: " + tlvClass.getName());
            }
            //Проверяем, есть ли такое имя класса в раннее определенной таблице: имя аннотации - поле
            if(hashMap.containsKey(nameClass)) {
                Field field = hashMap.get(nameClass);
                //Получаем доступ к полю для возможности получения и установки значения
                field.setAccessible(true);
                if(obj.isConstructed()) {//Если вложенный тэг
                    final Class<?> fieldType = field.getType();
                    //Массив объектов
                    if(fieldType == List.class) {
                        Type type = field.getGenericType();
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pType = (ParameterizedType)type;
                            //Работаем только с объектами, у которых один параметр дженерика:
                            if(pType.getActualTypeArguments().length != 1) {
                                throw new RuntimeException("Количество параметров дженерика не равно 1, ошибка парсинга TagId: "
                                        + String.valueOf(obj.getTagId()) + " Имя класса:" + tlvClass.getName());
                            }
                            Class<?> c = (Class<?>)pType.getActualTypeArguments()[0];
                            //Получаем метод добавления элемента
                            Method method = fieldType.getMethod("add", Object.class);
                            //Получаем сам список
                            Object listObjects = field.get(rootObject);
                            List<TLVDataObj> listTLV = TLVConvertor.getTLVDataObjList(obj.getValue());
                            for(TLVDataObj temp: listTLV) {
                                try {
                                    Object item;
                                    if(c == Integer.class) {//Для обработки ссылок на другие справочники
                                        byte[] array = temp.getValue();
                                        item = byteArrayToInt(Arrays.copyOfRange(array, 1, array.length));
                                    }else {
                                        //Рекурсивно создаем объект элемента списка
                                        item = parseThrowing(temp.getValue(), c, false);
                                    }

                                    //Вызываем метод добавления элемента
                                    method.invoke(listObjects, item );
                                }catch(Exception ex) {
                                    throw new RuntimeException("-> Ошибка при создании объекта с тэгом TagId: " + String.valueOf(obj.getTagId())+ " Имя класса: " + c.getName() + " " + ex);
                                }
                            }

                        }
                    }else{ //Иначе - это какой-то другой объект
                        Type type = field.getGenericType();
                        if(type instanceof Class<?>) {
                            //Рекурсивно создаем объект
                            Object item = parseThrowing(obj.getValue(), (Class<?>)type, false);
                            field.set(rootObject, item);
                        }else{
                            throw new RuntimeException("Неподдерживаемый тип объекта, ошибка парсинга TagId: " + String.valueOf(obj.getTagId())+ " Имя класса: " + tlvClass.getName());
                        }
                    }
                }else {//Если не вложенный тэг - значит тип параметра 'примитивный'
                    try {
                        parsePrimitiveType(rootObject, field, obj);
                    }catch(Exception ex) {
                        throw new RuntimeException("Ошибка парсинга TagId: " + String.valueOf(obj.getTagId())
                                + "\nИмя класса: " + tlvClass.getName() + "\nChild exception: " + ex, ex);
                    }
                }
            }else {
                if(nameClass != null) {
                    Log.d(tagLog,"В классе " + tlvClass.getName() + " нет поля " + nameClass +
                            " соответствующего TagId: " + obj.getTagId() );
                }
            }
        }
        initPrimitiveTypes(rootObject, fields);
        return rootObject;
    }

    private void parsePrimitiveType(Object rootObject, Field field, TLVDataObj obj) throws IllegalArgumentException,
            IllegalAccessException, UnsupportedEncodingException, NoSuchMethodException, SecurityException {
        final Class<?> fieldType = field.getType();

        byte[] array = obj.getValue();
        if(fieldType == String.class) {
            String value = new String(Arrays.copyOfRange(array, 1, array.length), encoding);
            field.set(rootObject, value);
        }else if(fieldType == Integer.class) {
            Integer value = byteArrayToInt(Arrays.copyOfRange(array, 1, array.length));
            field.set(rootObject, value);
        }else if(fieldType == BigInteger.class) {
            field.set(rootObject, new BigInteger(Arrays.copyOfRange(array, 1, array.length)));
        }else if(fieldType == Double.class) {
            Double value = toDouble(Arrays.copyOfRange(array, 1, array.length));
            field.set(rootObject, value);
        }else if(fieldType == Byte.class) {
            Byte b = Arrays.copyOfRange(array, 1, array.length)[0];
            field.set(rootObject, b);
        }else if(fieldType == HexString.class) {
            HexString str = new HexString(Arrays.copyOfRange(array, 1, array.length));
            field.set(rootObject, str);
        }else if(fieldType.isEnum()){
            Integer value = byteArrayToInt(Arrays.copyOfRange(array, 1, array.length));
            Method method = fieldType.getMethod("fromValue", Integer.class);
            Object objEnum = field.get(rootObject);
            try {
                objEnum = method.invoke(objEnum, value);
            }catch(InvocationTargetException ex) {
                Log.d(tagLog, "Перечислению " + fieldType.getName() + " присваивается неизвестное значение: "+ ex.getTargetException().getMessage()
                                + ". Полю с тэгом "+ obj.getTagId() + " будет присвоено значение по умолчанию");
            }
            field.set(rootObject, objEnum);
        }else if(fieldType == List.class){
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType)type;
                //Работаем только с объектами, у которых один параметр дженерика:
                Class<?> c = (Class<?>)pType.getActualTypeArguments()[0];
                //Получаем сам список
                Object listByte = field.get(rootObject);
                //Обработка массива байт
                if(listByte instanceof List){
                    if(c == Byte.class) {
                        @SuppressWarnings("unchecked")
                        List<Byte> listObjects = (List<Byte>)listByte;
                        byte[] val = Arrays.copyOfRange(array, 1, array.length);
                        for(byte b: val){
                            listObjects.add(b);
                        }
                        field.set(rootObject, listObjects);
                        //Обработка массива перечислений
                    }else if(c.isEnum()) {
                        byte[] val = Arrays.copyOfRange(array, 1, array.length);
                        Method fromValue = c.getMethod("fromValue", Integer.class);
                        //Получаем сам список
                        Object listObjects = field.get(rootObject);
                        //Получаем метод добавления элемента
                        Method method = fieldType.getMethod("add", Object.class);
                        for(byte b: val){
                            try {
                                Object objEnum = fromValue.invoke(null, (int)b);
                                method.invoke(listObjects, objEnum);
                            }catch(InvocationTargetException ex) {
                                Log.d(tagLog,"Перечислению " + c.getName() + " присваивается неизвестное значение: " + ex.getTargetException().getMessage()
                                        + ". Полю с тэгом "+ obj.getTagId() + " будет присвоено значение по умолчанию. Обрабатываемый объект: " + field.getName() + " класса " + rootObject.getClass().getName() );
                            }
                        }
                    }else {
                        throw new RuntimeException("Неподдерживаемый список объектов: " + " TagId:" + String.valueOf(obj.getTagId()));
                    }
                }
            }
        }else{
            Log.d(tagLog,"Тип " + fieldType.toString() + " не поддерживается, "
                            + "значение с TagID: " + obj.getTagId() + " не будет обработано");
        }
    }

    private void initPrimitiveTypes(Object rootObject, Field[] fields) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : fields) {
            Type type = field.getGenericType();
            if(type instanceof Class<?>) {
                field.setAccessible(true);
                Object obj = field.get(rootObject);
                if(obj == null) {
                    final Class<?> fieldType = field.getType();
                    if(fieldType == BigInteger.class) {
                        obj = new BigInteger("0");
                    }else if(fieldType == HexString.class) {
                        obj = new HexString();
                    }
                    if(obj != null) {
                        field.set(rootObject, obj);
                    }
                }
            }
        }
    }

    /*private void initNullObjects(Object rootObject, Field[] fields) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : fields) {
            Type type = field.getGenericType();
            if(type instanceof Class<?>) {
                field.setAccessible(true);
                Object obj = field.get(rootObject);
                if(obj == null) {
                    final Class<?> fieldType = field.getType();
                    try {
                        obj = fieldType.newInstance();
                        field.set(rootObject, obj);
                    }catch(InstantiationException ex) {
                        System.out.println("Объект "+ fieldType.getName()+" не будет инициализирован по умолчанию");
                    }
                }
            }
        }
    }*/
}

