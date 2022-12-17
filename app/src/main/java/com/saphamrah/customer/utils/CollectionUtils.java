package com.saphamrah.customer.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static <T> List<T> convertArrayToList(T array[]) {
        List<T> list = new ArrayList<>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }


    public static <T extends Object> T[] convertListToArray(List<T> t) {
        return null;
    }


    public static <T> Set<T> findDuplicates(List<T> listContainingDuplicates, int reputation) {
        int checkCount = 0;
        final Set<T> setToReturn = new HashSet<>();
        final Set<T> set = new HashSet<>();
        if (reputation <= 0){
            setToReturn.addAll(listContainingDuplicates);
        }else {
            for (T yourType : listContainingDuplicates) {
                if (!set.add(yourType) ) {
                    if (checkCount == reputation) {
                        setToReturn.add(yourType);
                        checkCount = 0;
                    }
                    else
                        checkCount++;
                }else{
                    checkCount++;
                }
            }
        }

        return setToReturn;
    }



}
