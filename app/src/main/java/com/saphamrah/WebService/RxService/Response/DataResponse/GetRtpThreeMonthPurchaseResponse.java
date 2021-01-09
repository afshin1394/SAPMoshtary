package com.saphamrah.WebService.RxService.Response.DataResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.WebService.RxService.Response.BaseResponse;

import java.util.ArrayList;

public class GetRtpThreeMonthPurchaseResponse extends BaseResponse {

    @SerializedName("Data")
    @Expose
    private ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels = null;


    public ArrayList<Rpt3MonthPurchaseModel> getRpt3MonthPurchaseModels() {
        return rpt3MonthPurchaseModels;
    }

    public void setRpt3MonthPurchaseModels(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels) {
        this.rpt3MonthPurchaseModels = rpt3MonthPurchaseModels;
    }

}
