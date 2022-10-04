package com.saphamrah.customer.data.shared;

import com.google.gson.reflect.TypeToken;
import com.saphamrah.customer.base.BasePreferences;
import com.saphamrah.customer.utils.FileManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;


public class PreferencesImpl implements BasePreferences {

    private File file;
    private Type mapType;

    private String name;

    public PreferencesImpl(String name) {
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
       /* HashMap<String, String> data = new HashMap<String, String>();
        String readed = FileManager.readFileContent(file);
        if (Objects.equals(readed, "")) {
            data.put(key, value);

        } else {
            data = readed.fromJson(mapType);
            data.get(key) = value;
        }

        FileManager.writeToFile(file, data.toJson());*/
    }

    @Override
    public String getString(String key, String value) {
        return null;
    }

    @Override
    public void clear() {
        FileManager.writeToFile(file, "");

    }
}
