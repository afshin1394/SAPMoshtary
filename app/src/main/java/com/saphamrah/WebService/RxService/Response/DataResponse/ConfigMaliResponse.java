package com.saphamrah.WebService.RxService.Response.DataResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.ConfigMaliModel;
import com.saphamrah.Model.HadafForosh.RptHadafForoshModel;
import com.saphamrah.WebService.RxService.Response.BaseResponse;

import java.util.ArrayList;

public class ConfigMaliResponse extends BaseResponse {

    @SerializedName("Data")
    @Expose
    private ArrayList<ConfigMaliModel> configMaliModels = null;


    public ArrayList<ConfigMaliModel> getData() {
        return configMaliModels;
    }
    public void setData(ArrayList<ConfigMaliModel> configMaliModels) {
        this.configMaliModels = configMaliModels;
    }

}
