package com.saphamrah.customer.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public  class  CollectionUtils {

    public static <T> List<T> convertArrayToList(T array[])
    {
        List<T> list = new ArrayList<>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }


    public static <T> T[] convertListToArray(List<T> t) {

        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(t.getClass(), t.size());
        for (int i = 0; i < t.size(); i++) arr[i] = t.get(i);
        return arr;
    }
}
