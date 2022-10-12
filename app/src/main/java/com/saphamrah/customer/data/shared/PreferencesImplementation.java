/*
package com.saphamrah.customer.data.shared;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saphamrah.customer.base.BasePreferences;
import com.saphamrah.customer.utils.FileManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;


public class PreferencesImplementation implements BasePreferences {

    private File file;
    private Type mapType;

    private String name;

    public PreferencesImplementation(String name) {
        this.name = name;
        try {
            this.file = new File(name).getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mapType = (new TypeToken() {
        }).getType();
    }


    @Override
    public void putString(String key, String value) {
        HashMap<String, String> data = new HashMap<String, String>();
        String readed = FileManager.readFileContent(file);
        if (Objects.equals(readed, "")) {
            data.put(key, value);

        } else {
            data = (HashMap<String, String>) fromJson(readed, mapType);
            data.put(key, value);
        }

        FileManager.writeToFile(file, toJson(data));
    }

    @Override
    public String getString(String key, String value) {

        String readed = FileManager.readFileContent(this.file);
        HashMap data;

        String var5;
        try {

            data = (HashMap)this.fromJson(readed, mapType);
            String var10000 = (String)data.get(key);
            if (var10000 == null) {
                var10000 = value;
            }

            var5 = var10000;
        } catch (Exception var7) {
            var5 = value;
        }

        return var5;
    }

    @Override

    public void clear() {
        FileManager.writeToFile(file, "");

    }

    private Object fromJson(String fromJson, Type type) {
        return (new Gson()).fromJson(fromJson, type);
    }

    private String toJson(Object toJson) {
        return (new Gson()).toJson(toJson);
    }
}
*/
