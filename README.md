# ParserParameters

Тестовое Android-приложение (2018 г.): парсер конфигурационных параметров платёжного терминала UNIPOS (INPAS) из бинарных TLV-файлов (`.pst`) в Java-объекты. Сам парсер вынесен в отдельную Android-библиотеку, а приложение-пример читает файлы параметров с внешнего хранилища устройства и выводит результат разбора в Logcat.

## Формат данных

Параметры терминала представляют собой бинарный TLV-поток (разбор — `libparseparams/src/main/java/com/inpas/libparseparams/`):

- запись: тег (2 байта, big-endian) + длина (4 байта, big-endian) + значение; заголовок — 6 байт (`TLVDataObj.TagHeaderLength`);
- старший бит (0x8000) тега — признак вложенности: значение такого тега само является набором TLV-записей (`TLVDataObj.isConstructed()`);
- первый байт значения примитивного тега — служебный код типа (в коде закомментированы константы `HEXType`, `StringType`, `ByteType`, `WordType`, `DwordType`, `DoubleType`), парсер его пропускает;
- строки по умолчанию читаются в кодировке windows-1251, кодировка меняется через `TLVParserParameters.setEncoding()`.

Состав параметров (12 пресетов) и номера тегов заданы в `app/src/main/java/com/inpas/parserparameters/TagsTLV.java`: Currency, PaymentSystem, CardProduct, SecurityKey, AccountType, Template, TerminalProfile, UsersGroup, Possessor, Acquiring, ConnectionsServer, Terminal.

## Возможности

- разбор массива байт на список TLV-объектов, включая вложенные теги (`TLVConvertor`);
- заполнение полей Java-классов через рефлексию: соответствие «тег → имя поля» задаётся таблицами (`TagsTLV`), поля моделей помечены аннотацией Gson `@SerializedName`;
- поддержка типов: `String`, `Integer`, `BigInteger`, `Double`, `Byte`, `HexString`, перечисления (enum с методом `fromValue`), списки объектов, а также списки `Byte` и списки enum; `List<Integer>` трактуется как список ссылок (Anchor) на другие справочники;
- генерация Java-моделей из JSON-схем плагином jsonschema2pojo в пакет `com.inpas.model` (настройки — `app/build.gradle`);
- неизвестные теги и неизвестные значения перечислений пропускаются с записью в лог.

## Технологии

Версии — только из файлов сборки:

- Gradle 4.4 (wrapper), Android Gradle Plugin 3.1.3;
- compileSdkVersion 28, minSdkVersion 16, targetSdkVersion 28 (app и libparseparams);
- Android Support Library: appcompat-v7 28.0.0-alpha3, constraint-layout 1.1.2;
- Gson 2.8.5; jsonschema2pojo (gradle-плагин); javax.annotation 10.0-b28; validation-api 1.1.0.CR2;
- тестовые зависимости: JUnit 4.12, AndroidJUnitRunner 1.0.2, Espresso 3.0.2.

## Модули

| Модуль / каталог | Назначение |
| --- | --- |
| `libparseparams` | Android-библиотека парсера: `TLVConvertor`, `TLVDataObj`, `TLVParserParameters`, интерфейс `IParserParameters`, вспомогательный тип `HexString` |
| `app` | Приложение-пример: `MainActivity` (читает 12 файлов `.pst` из каталога `tlv/` внешнего хранилища), синглтон `Parameters` с результатами разбора, таблица тегов `TagsTLV` |
| `parameters` | Каталог схем, в `settings.gradle` не входит: 24 JSON-схемы для генерации моделей и справочные схемы конфигуратора терминала |

`parameters/jsonschemes/` — JSON-схемы (jsonschema2pojo): пресеты и справочники (`CurrencyPreset`, `TerminalPreset`, `DefBinRange`, `DefSwitch` и др.).
`parameters/TMS_Scheme/profile.xml` — схема TMS (`profile version="1.0.14.58" type="UNIPOS"`).
`parameters/CM_Scheme/` — XSD-схема «Конфигуратора SA PSP для Android-терминалов» (Config Manager 2.1.1.97) и база данных Config Manager `PosDroid.mdb` (MS Access, 46 МБ). Файлы TMS_Scheme и CM_Scheme добавлены как справочные и в сборке не участвуют (gradle использует только `parameters/jsonschemes/*.json`).

## Сборка и тесты

Сборка стандартная для Android/Gradle: `gradlew assembleDebug`. Для работы приложения на устройстве нужны файлы `.pst` в каталоге `tlv/` внешнего хранилища — в репозитории их нет. Тесты — только шаблонные `ExampleUnitTest` / `ExampleInstrumentedTest`, созданные мастером проекта; тестов самого парсера нет.

## Статус

Учебный/экспериментальный проект 2018 года: 5 коммитов за 10–11 июля 2018 г., после этого разработки не было. UI приложения — шаблонный «Hello World», все результаты работы выводятся в Logcat (тег `params`).
