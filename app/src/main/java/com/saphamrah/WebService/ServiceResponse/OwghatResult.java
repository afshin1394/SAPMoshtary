package com.saphamrah.WebService.ServiceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Model.OwghatModel;

public class OwghatResult
{

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("result")
    @Expose
    private OwghatModel result;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public OwghatModel getResult() {
        return result;
    }

    public void setResult(OwghatModel result) {
        this.result = result;
    }

}
