package com.saphamrah.WebService.ServiceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.NoeMalekiatMoshtaryModel;

import java.util.ArrayList;

public class GetAllNoeMalekiatMoshtaryResult
{

    @SerializedName("Data")
    @Expose
    private ArrayList<NoeMalekiatMoshtaryModel> data = null;
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

    public ArrayList<NoeMalekiatMoshtaryModel> getData() {
        return data;
    }

    public void setData(ArrayList<NoeMalekiatMoshtaryModel> data) {
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
