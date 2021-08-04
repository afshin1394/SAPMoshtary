package com.saphamrah.Model;


import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class LogPPCModel
{

    ///////////////////////////// LOG TYPE /////////////////////////////
    public static final int LOG_EXCEPTION = 1;
    public static final int LOG_RESPONSE_CONTENT_LENGTH = 2;
    public static final int LOG_SPEED_TEST = 3;			   
    private static final String TABLE_NAME = "LogPPC";
    private static final String COLUMN_ccLogPPC = "ccLogPPC";
    private static final String COLUMN_ccAfrad = "ccAfrad";
    private static final String COLUMN_IMEI = "IMEI";
    private static final String COLUMN_Type = "Type";   // 1-exception and error , 2-response content length
    private static final String COLUMN_LogMessage = "LogMessage";
    private static final String COLUMN_LogClass = "LogClass";
    private static final String COLUMN_LogActivity = "LogActivity";
    private static final String COLUMN_LogFunctionParent = "LogFunctionParent";
    private static final String COLUMN_LogFunctionChild = "LogFunctionChild";
    private static final String COLUMN_LogDate = "LogDate";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";
    private static final String COLUMN_AndroidAPI = "AndroidAPI";
    private static final String COLUMN_LineOfCode = "LineOfCode";
    private static final String COLUMN_StackTrace = "StackTrace";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccLogPPC() {
        return COLUMN_ccLogPPC;
    }
    public static String COLUMN_ccAfrad() {
        return COLUMN_ccAfrad;
    }
    public static String COLUMN_IMEI() {
        return COLUMN_IMEI;
    }
    public static String COLUMN_Type() {
        return COLUMN_Type;
    }
    public static String COLUMN_LogMessage() {
        return COLUMN_LogMessage;
    }
    public static String COLUMN_LogClass() {
        return COLUMN_LogClass;
    }
    public static String COLUMN_LogActivity() {
        return COLUMN_LogActivity;
    }
    public static String COLUMN_LogFunctionParent() {
        return COLUMN_LogFunctionParent;
    }
    public static String COLUMN_LogFunctionChild() {
        return COLUMN_LogFunctionChild;
    }
    public static String COLUMN_LogDate() {
        return COLUMN_LogDate;
    }
    public static String COLUMN_ExtraProp_IsOld() {
        return COLUMN_ExtraProp_IsOld;
    }
    public static String COLUMN_AndroidAPI() {
        return COLUMN_AndroidAPI;
    }
    public static String COLUMN_LineOfCode() {
        return COLUMN_LineOfCode;
    }
    public static String COLUMN_StackTrace() {
        return COLUMN_StackTrace;
    }

    @SerializedName("ccLogPPC")
    @Expose
    private int ccLogPPC;

    @SerializedName("ccAfrad")
    @Expose
    private int ccAfrad;

    @SerializedName("IMEI")
    @Expose
    private String IMEI;

    @SerializedName("Type")
    @Expose
    private int logType;

    @SerializedName("LogMessage")
    @Expose
    private String logMessage;

    @SerializedName("LogClass")
    @Expose
    private String logClass;

    @SerializedName("LogActivity")
    @Expose
    private String logActivity;

    @SerializedName("LogFunctionParent")
    @Expose
    private String logFunctionParent;

    @SerializedName("LogFunctionChild")
    @Expose
    private String logFunctionChild;

    @SerializedName("LogDate")
    @Expose
    private String logDate;

    private int extraProp_IsOld;

    @SerializedName("AndroidAPI")
    @Expose
    private int androidAPI;

    @SerializedName("LineOfCode")
    @Expose
    private String lineOfCode;

    @SerializedName("StackTrace")
    @Expose
    private String StackTrace;


    public int getCcLogPPC() {
        return ccLogPPC;
    }

    public void setCcLogPPC(int ccLogPPC) {
        this.ccLogPPC = ccLogPPC;
    }

    public int getCcAfrad() {
        return ccAfrad;
    }

    public void setCcAfrad(int ccAfrad) {
        this.ccAfrad = ccAfrad;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }

    public String getLogActivity() {
        return logActivity;
    }

    public void setLogActivity(String logActivity) {
        this.logActivity = logActivity;
    }

    public String getLogFunctionParent() {
        return logFunctionParent;
    }

    public void setLogFunctionParent(String logFunctionParent) {
        this.logFunctionParent = logFunctionParent;
    }

    public String getLogFunctionChild() {
        return logFunctionChild;
    }

    public void setLogFunctionChild(String logFunctionChild) {
        this.logFunctionChild = logFunctionChild;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public int getExtraProp_IsOld() {
        return extraProp_IsOld;
    }

    public void setExtraProp_IsOld(int extraProp_IsOld) {
        this.extraProp_IsOld = extraProp_IsOld;
    }

    public int getAndroidAPI() {
        return androidAPI;
    }

    public void setAndroidAPI(int androidAPI) {
        this.androidAPI = androidAPI;
    }


    public String getLineOfCode() {
        return lineOfCode;
    }

    public void setLineOfCode(String lineOfCode) {
        this.lineOfCode = lineOfCode;
    }


    public String getStackTrace() {
        return StackTrace;
    }

    public void setStackTrace(String stackTrace) {
        StackTrace = stackTrace;
    }

    public JSONObject toJsonObject(String deviceIP)
    {
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ccAfrad" , ccAfrad);
            jsonObject.put("IMEI" , IMEI);
            jsonObject.put("Type" , logType);
            jsonObject.put("LogMessage" , deviceIP + "*" + logMessage);
            jsonObject.put("LogClass" , logClass);
            jsonObject.put("LogActivity" , logActivity);
            jsonObject.put("LogFunctionParent" , logFunctionParent);
            jsonObject.put("LogFunctionChild" , logFunctionChild);
            jsonObject.put("LogDate" , logDate);
            jsonObject.put("AndroidAPI" , androidAPI);
//            jsonObject.put("LineOfCode" , lineOfCode);
//            jsonObject.put("StackTrace" , StackTrace);

            return jsonObject;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String toJsonString()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ccAfrad" , ccAfrad);
        jsonObject.addProperty("IMEI" , IMEI);
        jsonObject.addProperty("Type" , logType);
        jsonObject.addProperty("LogMessage" , logMessage);
        jsonObject.addProperty("LogClass" , logClass);
        jsonObject.addProperty("LogActivity" , logActivity);
        jsonObject.addProperty("LogFunctionParent" , logFunctionParent);
        jsonObject.addProperty("LogFunctionChild" , logFunctionChild);
        jsonObject.addProperty("LogDate" , logDate);
        jsonObject.addProperty("AndroidAPI" , androidAPI);
//        jsonObject.addProperty("LineOfCode" , lineOfCode);
//        jsonObject.addProperty("StackTrace" , StackTrace);
        return jsonObject.toString();
    }


    @NonNull
    @Override
    public String toString() {
        return "LogPPCModel{" +
                "ccLogPPC=" + ccLogPPC +
                ", ccAfrad=" + ccAfrad +
                ", IMEI='" + IMEI + '\'' +
                ", logType=" + logType +
                ", logMessage='" + logMessage + '\'' +
                ", logClass='" + logClass + '\'' +
                ", logActivity='" + logActivity + '\'' +
                ", logFunctionParent='" + logFunctionParent + '\'' +
                ", logFunctionChild='" + logFunctionChild + '\'' +
                ", logDate='" + logDate + '\'' +
                ", extraProp_IsOld=" + extraProp_IsOld +
                ", androidAPI=" + androidAPI +
                ", lineOfCode='" + lineOfCode + '\'' +
                ", StackTrace='" + StackTrace + '\'' +
                '}';
    }



}
