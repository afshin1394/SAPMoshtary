package com.saphamrah.WebService.RxService.Response.DataResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.WebService.RxService.Response.BaseResponse;

import java.util.ArrayList;

public class GetAllrptKalaInfoResponse extends BaseResponse
{


    @SerializedName("Data")
    @Expose
    private ArrayList<RptKalaInfoModel> rptKalaInfoModels = null;


    public ArrayList<RptKalaInfoModel> getRptKalaInfoModels() {
        return rptKalaInfoModels;
    }

    public void setData(ArrayList<RptKalaInfoModel> data) {
        this.rptKalaInfoModels = data;
    }




}
