package com.saphamrah.WebService.RxService.Response.DataResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.WebService.RxService.Response.BaseResponse;
import com.saphamrah.WebService.ServiceResponse.KalaPhotoResult;

import java.util.ArrayList;

public class KalaPhotoResponse extends BaseResponse {



    @SerializedName("Data")
    @Expose
    private ArrayList<KalaPhotoResult> kalaPhotoResults=null;

    public ArrayList<KalaPhotoResult> getKalaPhotoResults() {
        return kalaPhotoResults;
    }

    public void setKalaPhotoResults(ArrayList<KalaPhotoResult> kalaPhotoResults) {
        this.kalaPhotoResults = kalaPhotoResults;
    }


}
