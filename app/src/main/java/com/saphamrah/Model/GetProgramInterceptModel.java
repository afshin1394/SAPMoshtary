package com.saphamrah.Model;

public class GetProgramInterceptModel {
    public static final String TABLE_NAME = "GetProgramIntercept";

    public static final String COLUMN_MethodName = "MethodName";
    public static final String COLUMN_ResponseTime = "ResponseTime";
    public static final String COLUMN_ResponseSize = "ResponseSize";


    private String methodName;
    private String responseSize;
    private String responseTime;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(String responseSize) {
        this.responseSize = responseSize;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
}
