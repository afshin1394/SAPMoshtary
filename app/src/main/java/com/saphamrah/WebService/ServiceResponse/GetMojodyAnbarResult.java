package com.saphamrah.WebService.ServiceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.KalaModel;

import java.util.ArrayList;

public class GetMojodyAnbarResult
{

    @SerializedName("Data")
    @Expose
    private ArrayList<KalaModel> data = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("HasPermission")
    @Expose
    private Boolean hasPermission;
    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount;

    public ArrayList<KalaModel> getData() {
        return data;
    }

    public void setData(ArrayList<KalaModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
