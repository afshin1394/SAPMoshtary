package com.saphamrah.customer.base;

public interface BasePreferences {

    void putString(String key, String value);
    String getString(String key, String value);
    void clear();
}
