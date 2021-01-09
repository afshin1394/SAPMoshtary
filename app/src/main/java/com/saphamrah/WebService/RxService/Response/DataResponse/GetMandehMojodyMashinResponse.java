package com.saphamrah.WebService.RxService.Response.DataResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.WebService.RxService.Response.BaseResponse;

import java.util.ArrayList;

public class GetMandehMojodyMashinResponse extends BaseResponse {
    @SerializedName("Data")
    @Expose
    private ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = null;


    public ArrayList<MandehMojodyMashinModel> getMandehMojodyMashinModels() {
        return mandehMojodyMashinModels;
    }

    public void setMandehMojodyMashinModels(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        this.mandehMojodyMashinModels = mandehMojodyMashinModels;
    }
}
