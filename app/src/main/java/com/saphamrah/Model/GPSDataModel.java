package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class GPSDataModel
{

    private static final String TABLE_NAME = "GpsData_PPC";
    private static final String COLUMN_ccGpsData_PPC = "ccGpsData_PPC";
    private static final String COLUMN_ccAfrad = "ccAfrad";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_Latitude = "Latitude";
    private static final String COLUMN_Longitude = "Longitude";
    private static final String COLUMN_Tarikh = "Tarikh";
    private static final String COLUMN_ExtraProp_IsSend = "ExtraProp_IsSend";
    private static final String COLUMN_Distance = "Distance";
    private static final String COLUMN_ccMamorPakhsh = "ccMamorPakhsh";
    private static final String COLUMN_Altitude = "Altitude";
    private static final String COLUMN_Accurancy = "Accurancy";
    private static final String COLUMN_Bearing = "Bearing";
    private static final String COLUMN_Speed = "Speed";
    private static final String COLUMN_Status = "Status";
    private static final String COLUMN_ElapsedRealtimeNanos = "ElapsedRealtimeNanos";
    private static final String COLUMN_Provider = "Provider";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccGpsData_PPC() {
        return COLUMN_ccGpsData_PPC;
    }
    public static String COLUMN_ccAfrad() {
        return COLUMN_ccAfrad;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMasir() {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_Latitude() {
        return COLUMN_Latitude;
    }
    public static String COLUMN_Longitude() {
        return COLUMN_Longitude;
    }
    public static String COLUMN_Tarikh() {
        return COLUMN_Tarikh;
    }
    public static String COLUMN_ExtraProp_IsSend() {
        return COLUMN_ExtraProp_IsSend;
    }
    public static String COLUMN_Distance() {
        return COLUMN_Distance;
    }
    public static String COLUMN_ccMamorPakhsh() {
        return COLUMN_ccMamorPakhsh;
    }
    public static String COLUMN_Altitude() {
        return COLUMN_Altitude;
    }
    public static String COLUMN_Accurancy() {
        return COLUMN_Accurancy;
    }
    public static String COLUMN_Bearing() {
        return COLUMN_Bearing;
    }
    public static String COLUMN_Speed() {
        return COLUMN_Speed;
    }
    public static String COLUMN_Status() {
        return COLUMN_Status;
    }
    public static String COLUMN_ElapsedRealtimeNanos() {
        return COLUMN_ElapsedRealtimeNanos;
    }
    public static String COLUMN_Provider() {
        return COLUMN_Provider;
    }



    @SerializedName("ccGpsData_PPC")
    @Expose
    private Integer ccGpsData_PPC;
    @SerializedName("ccForoshandeh")
    @Expose
    private Integer ccForoshandeh;
    @SerializedName("ccMasir")
    @Expose
    private Integer ccMasir;
    @SerializedName("Latitude")
    @Expose
    private double latitude;
    @SerializedName("Longitude")
    @Expose
    private double longitude;
    @SerializedName("Altitude")
    @Expose
    private double altitude;
    @SerializedName("Accurancy")
    @Expose
    private float accurancy;
    @SerializedName("Bearing")
    @Expose
    private float bearing;
    @SerializedName("Speed")
    @Expose
    private float speed;
    private long elapsedRealTimeNanos;
    private String provider;
    @SerializedName("Tarikh")
    @Expose
    private String tarikh;
    private Integer extraProp_IsSend;
    private Integer status;
    @SerializedName("Distance")
    @Expose
    private double distance;
    @SerializedName("ccMamorPakhsh")
    @Expose
    private Integer ccMamorPakhsh;

    private int ccAfrad;


    public Integer getCcGpsData_PPC() {
        return ccGpsData_PPC;
    }

    public void setCcGpsData_PPC(Integer ccGpsData_PPC) {
        this.ccGpsData_PPC = ccGpsData_PPC;
    }

    public Integer getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(Integer ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public Integer getCcMasir() {
        return ccMasir;
    }

    public void setCcMasir(Integer ccMasir) {
        this.ccMasir = ccMasir;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getAccurancy() {
        return accurancy;
    }

    public void setAccurancy(float accurancy) {
        this.accurancy = accurancy;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public long getElapsedRealTimeNanos() {
        return elapsedRealTimeNanos;
    }

    public void setElapsedRealTimeNanos(long elapsedRealTimeNanos) {
        this.elapsedRealTimeNanos = elapsedRealTimeNanos;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public Integer getExtraProp_IsSend() {
        return extraProp_IsSend;
    }

    public void setExtraProp_IsSend(Integer extraProp_IsSend) {
        this.extraProp_IsSend = extraProp_IsSend;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Integer getCcMamorPakhsh() {
        return ccMamorPakhsh;
    }

    public void setCcMamorPakhsh(Integer ccMamorPakhsh) {
        this.ccMamorPakhsh = ccMamorPakhsh;
    }

    public int getCcAfrad()
    {
        return ccAfrad;
    }
    public void setCcAfrad(int ccAfrad)
    {
        this.ccAfrad = ccAfrad;
    }


    public JSONObject toJsonObject()
    {
        try
        {
            String tarikh = this.tarikh != null ? this.tarikh : "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(COLUMN_ccGpsData_PPC() , ccGpsData_PPC);
            jsonObject.put(COLUMN_ccAfrad() , ccAfrad);
            jsonObject.put(COLUMN_ccForoshandeh() , ccForoshandeh);
            jsonObject.put(COLUMN_ccMasir() , ccMasir);
            jsonObject.put(COLUMN_Tarikh() , tarikh);
            jsonObject.put(COLUMN_Latitude() , latitude);
            jsonObject.put(COLUMN_Longitude() , longitude);
            jsonObject.put(COLUMN_Distance() , distance);
            jsonObject.put(COLUMN_ccMamorPakhsh() , ccMamorPakhsh);
            jsonObject.put(COLUMN_Altitude() , altitude);
            jsonObject.put(COLUMN_Accurancy() , accurancy);
            jsonObject.put(COLUMN_Bearing() , bearing);
            jsonObject.put(COLUMN_Speed() , speed);
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
        String tarikh = this.tarikh != null ? this.tarikh : "";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccGpsData_PPC() , ccGpsData_PPC);
        jsonObject.addProperty(COLUMN_ccAfrad() , ccAfrad);
        jsonObject.addProperty(COLUMN_ccForoshandeh() , ccForoshandeh);
        jsonObject.addProperty(COLUMN_ccMasir() , ccMasir);
        jsonObject.addProperty(COLUMN_Tarikh() , tarikh);
        jsonObject.addProperty(COLUMN_Latitude() , latitude);
        jsonObject.addProperty(COLUMN_Longitude() , longitude);
        jsonObject.addProperty(COLUMN_Distance() , distance);
        jsonObject.addProperty(COLUMN_ccMamorPakhsh() , ccMamorPakhsh);
        jsonObject.addProperty(COLUMN_Altitude() , altitude);
        jsonObject.addProperty(COLUMN_Accurancy() , accurancy);
        jsonObject.addProperty(COLUMN_Bearing() , bearing);
        jsonObject.addProperty(COLUMN_Speed() , speed);
        return jsonObject.toString();
    }


    @NonNull
    @Override
    public String toString()
    {
        return "GPSDataModel{" +
                "ccGpsData_PPC=" + ccGpsData_PPC +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMasir=" + ccMasir +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", accurancy=" + accurancy +
                ", bearing=" + bearing +
                ", speed=" + speed +
                ", elapsedRealTimeNanos=" + elapsedRealTimeNanos +
                ", provider='" + provider + '\'' +
                ", tarikh='" + tarikh + '\'' +
                ", extraProp_IsSend=" + extraProp_IsSend +
                ", status=" + status +
                ", distance=" + distance +
                ", ccMamorPakhsh=" + ccMamorPakhsh +
                ", ccAfrad=" + ccAfrad +
                '}';
    }
}
