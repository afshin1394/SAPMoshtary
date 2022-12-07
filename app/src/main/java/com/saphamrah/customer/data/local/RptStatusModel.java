package com.saphamrah.customer.data.local;

public class RptStatusModel {
    private String code;
    private String tarikh;
    private String mablaq;
    private String noePardakht;
    private String elateAdamDarkhast;
    private int statusCode;
    private boolean isSuccessful;

//    private int imageRes;


    public RptStatusModel(String code, String tarikh, String mablaq, String noePardakht, int statusCode, boolean isSuccessful) {
        this.code = code;
        this.tarikh = tarikh;
        this.mablaq = mablaq;
        this.noePardakht = noePardakht;
        this.statusCode = statusCode;
        this.isSuccessful = isSuccessful;
    }

    public RptStatusModel(String code, String tarikh, String mablaq, String noePardakht, String elateAdamDarkhast, int statusCode, boolean isSuccessful) {
        this.code = code;
        this.tarikh = tarikh;
        this.mablaq = mablaq;
        this.noePardakht = noePardakht;
        this.elateAdamDarkhast = elateAdamDarkhast;
        this.statusCode = statusCode;
        this.isSuccessful = isSuccessful;
    }

    public String getElateAdamDarkhast() {
        return elateAdamDarkhast;
    }

    public void setElateAdamDarkhast(String elateAdamDarkhast) {
        this.elateAdamDarkhast = elateAdamDarkhast;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public String getMablaq() {
        return mablaq;
    }

    public void setMablaq(String mablaq) {
        this.mablaq = mablaq;
    }

    public String getNoePardakht() {
        return noePardakht;
    }

    public void setNoePardakht(String noePardakht) {
        this.noePardakht = noePardakht;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
