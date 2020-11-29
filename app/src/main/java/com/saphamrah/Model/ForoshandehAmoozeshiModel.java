package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForoshandehAmoozeshiModel
{

    private static final String TABLE_NAME = "ForoshandehAmoozeshi_DeviceNumber";
    private static final String COLUMN_ccDeviceNumber = "ccDeviceNumber";
    private static final String COLUMN_DeviceNumber_ForoshandehAmoozeshi = "DeviceNumber_ForoshandehAmoozeshi";
    private static final String COLUMN_DeviceNumber_ForoshandehAsli = "DeviceNumber_ForoshandehAsli";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDeviceNumber() {
        return COLUMN_ccDeviceNumber;
    }
    public static String COLUMN_DeviceNumber_ForoshandehAmoozeshi() {
        return COLUMN_DeviceNumber_ForoshandehAmoozeshi;
    }
    public static String COLUMN_DeviceNumber_ForoshandehAsli() {
        return COLUMN_DeviceNumber_ForoshandehAsli;
    }




    @SerializedName("ccDeviceNumber")
    @Expose
    private Integer ccDeviceNumber;
    @SerializedName("DeviceNumber_ForoshandehAmoozeshi")
    @Expose
    private String deviceNumberForoshandehAmoozeshi;
    @SerializedName("DeviceNumber_ForoshandehAsli")
    @Expose
    private String deviceNumberForoshandehAsli;

    public Integer getCcDeviceNumber() {
        return ccDeviceNumber;
    }

    public void setCcDeviceNumber(Integer ccDeviceNumber) {
        this.ccDeviceNumber = ccDeviceNumber;
    }

    public String getDeviceNumberForoshandehAmoozeshi() {
        return deviceNumberForoshandehAmoozeshi;
    }

    public void setDeviceNumberForoshandehAmoozeshi(String deviceNumberForoshandehAmoozeshi) {
        this.deviceNumberForoshandehAmoozeshi = deviceNumberForoshandehAmoozeshi;
    }

    public String getDeviceNumberForoshandehAsli() {
        return deviceNumberForoshandehAsli;
    }

    public void setDeviceNumberForoshandehAsli(String deviceNumberForoshandehAsli) {
        this.deviceNumberForoshandehAsli = deviceNumberForoshandehAsli;
    }

}
