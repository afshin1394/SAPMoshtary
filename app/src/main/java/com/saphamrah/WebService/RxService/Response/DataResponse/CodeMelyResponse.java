package com.saphamrah.WebService.RxService.Response.DataResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.WebService.RxService.Response.BaseResponse;

public class CodeMelyResponse extends BaseResponse {

    @SerializedName("Data")
    @Expose
    private String hashCode = null;

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    @Override
    public String toString() {
        return "CodeMelyResponse{" +
                "message=" + getMessage() +
                "codeMelyModels=" + hashCode +
                "success"+ getSuccess()+
                '}';
    }
}
