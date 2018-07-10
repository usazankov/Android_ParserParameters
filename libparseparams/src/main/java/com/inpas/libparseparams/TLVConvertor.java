package com.inpas.libparseparams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TLVConvertor {

    public static List<TLVDataObj> getTLVDataObjList(final byte[] data){
        List<TLVDataObj> list = new ArrayList<TLVDataObj>();
        int i = 0;
        int length = data.length;
        while(i < length) {
            TLVDataObj obj = new TLVDataObj(Arrays.copyOfRange(data, i, length));
            if(obj.getLength() > 0)list.add(obj);
            i += TLVDataObj.TagHeaderLength + obj.getLength();
        }
        return list;
    }

    public static List<TLVDataObj> getChildTLVDataObjList(final byte[] data){
        List<TLVDataObj> list = new ArrayList<TLVDataObj>();
        TLVDataObj temp = new TLVDataObj(data);
        byte[] array;
        if(temp.isConstructed()) {
            array = temp.getValue();
            int i = 0;
            int length = array.length;
            while(i < length) {
                TLVDataObj obj = new TLVDataObj(Arrays.copyOfRange(array, i, length));
                if(obj.getLength() > 0)list.add(obj);
                i += TLVDataObj.TagHeaderLength + obj.getLength();
            }

        }
        return list;

    }
}

