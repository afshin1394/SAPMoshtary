package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInfoModel
{

    @SerializedName("ServerDateTime")
    @Expose
    private String serverDateTime;

    public String getServerDateTime() {
        return serverDateTime;
    }

    public void setServerDateTime(String serverDateTime) {
        this.serverDateTime = serverDateTime;
    }

}
