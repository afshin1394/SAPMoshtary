package com.saphamrah.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtils {
    public String convertIntegerArrayToString(ArrayList<Integer> IntegerArray) {
        String strInteger = "";
        for (int i = 0; i < IntegerArray.size(); i++) {
            if (i == 0) {
                strInteger += String.valueOf(IntegerArray.get(i));
            } else {
                strInteger += "," + String.valueOf(IntegerArray.get(i));


            }
        }

        Log.i("PubFunc", "convertIntegerArrayToString: " + strInteger);
        return strInteger;

    }

    public String convertStringArrayToString(ArrayList<String> StringArray) {
        String strString = "";
        for (int i = 0; i < StringArray.size(); i++) {
            if (i == 0) {
                strString += StringArray.get(i);
            } else {
                strString += "," + StringArray.get(i);
            }
        }

        Log.i("PubFunc", "convertStringArrayToString: " + strString);
        return strString;

    }


    public ArrayList<String> convertStringToStringArray(String strString) {
       ArrayList<String> stringArray = new ArrayList<>();
       String[] strings= strString.split(",");
        for (int i = 0; i < strings.length; i++) {
          stringArray.add(strings[i]);
        }

        Log.i("PubFunc", "convertStringToStringArray: " + strString);
        return stringArray;
    }


    public  <T> ArrayList<T> convertArrayToList(T array[])
    {

        // Create an empty List
        ArrayList<T> list = new ArrayList<>();

        // Iterate through the array
        for (T t : array) {
            // Add each element into the list
            list.add(t);
        }

        // Return the converted List
        return list;
    }

    /**find duplicates in a list controling their properties T is the model and it should implement equal method*/
    public <T> boolean hasDuplicates(Collection<T> collection) {
        int equality = 0;
        if (collection.size() > 0) {
            for (int i = 0; i < collection.size(); i++) {
                for (int j = 0; j < collection.size(); j++) {
                    if (((ArrayList<T>) collection).get(i).equals(((ArrayList<T>) collection).get(j))
                    ) {
                        equality++;
                    }
                }
            }
        }
        if (equality > collection.size()){
            return true;
        }
        return false;
    }
    /**find duplicates in a list with their key value*/
    public <T> Set<T> collectionWithRemovedDuplicates(Collection<T> collection) {

        Set<T> duplicates = new LinkedHashSet<>();

        for (T t : collection) {
            duplicates.add(t);
        }
        return duplicates;
    }
    public <T> T  findMinimumValue(HashMap<T,Double> dictionary) {
        T ob =null;
        Double min = Double.MAX_VALUE;
        List<Double> values = new ArrayList<>();
        for (T t : dictionary.keySet()) {
            values.add(((Double) dictionary.get(t)));
        }

        for (Double value : values)
        {
            if (value<min)
                min = value;
        }
        for (T t : dictionary.keySet()) {
            if (dictionary.get(t).equals(min)){
                ob = t;
            }
        }


        return ob;
    }
}
