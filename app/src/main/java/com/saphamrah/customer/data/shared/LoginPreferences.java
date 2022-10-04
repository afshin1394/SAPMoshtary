package com.saphamrah.customer.data.shared;

import com.saphamrah.customer.utils.PreferencesImpl;

public class LoginPreferences {
    public static final String TOKEN = "TOKEN";
    public static final String MOBILE = "MOBILE";
    public static final String FIRST = "ONAPPLICATIONRUN";
    public static final String NOT_SAVED = "NOT_SAVED";


    // TODO should define base path
    private final PreferencesImpl prefs = new PreferencesImpl("BASE_PATH" + "/prefs");

    private String token;
    private String mobile;
    private Boolean first;
    private Boolean isLogin;
    private Boolean isFirst;


    public String getToken() {
        return prefs.getString(TOKEN, NOT_SAVED);
    }

    public void setToken(String token) {
        prefs.putString(TOKEN, token);
    }

    public String getMobile() {
        return prefs.getString(MOBILE, NOT_SAVED);
    }

    public void setMobile(String mobile) {
        prefs.putString(MOBILE, mobile);
    }

    public Boolean isFirst() {
        return Boolean.valueOf(prefs.getString(FIRST, "true"));
    }

    public void setFirst(Boolean first) {
        prefs.putString(FIRST, first.toString());
    }

    public Boolean isLogin() {
        return !prefs.getString(TOKEN, NOT_SAVED).equals(NOT_SAVED);
    }


    void clearToken() {
        token = NOT_SAVED;
        mobile = NOT_SAVED;
        first = true;
    }

}
