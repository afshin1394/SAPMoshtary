package com.saphamrah.WebService.RxService.Response.DataResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.HadafForosh.RptHadafForoshModel;
import com.saphamrah.WebService.RxService.Response.BaseResponse;

import java.util.ArrayList;

public class GetAllrptHadafeForoshResponse extends BaseResponse {

    @SerializedName("Data")
    @Expose
    private ArrayList<RptHadafForoshModel> rptHadafForoshModels = null;


    public ArrayList<RptHadafForoshModel> getData() {
        return rptHadafForoshModels;
    }
    public void setData(ArrayList<RptHadafForoshModel> rptHadafForoshModels) {
        this.rptHadafForoshModels = rptHadafForoshModels;
    }


}
