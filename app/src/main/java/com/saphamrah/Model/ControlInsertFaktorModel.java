package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ControlInsertFaktorModel
{

    @SerializedName("Flag")
    @Expose
    private Integer flag;
    @SerializedName("Id")
    @Expose
    private Integer id;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
