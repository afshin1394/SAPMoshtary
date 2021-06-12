package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerIpModel
{

    private static final String TABLE_NAME = "ServerIP";
    private static final String COLUMN_ccServerIP = "ccServerIP";
    private static final String COLUMN_ServerIP = "ServerIP";
    private static final String COLUMN_PortServerIP = "PortServerIP";
    private static final String COLUMN_NameServerIP = "NameServerIP";
    private static final String COLUMN_IsTest = "IsTest";
    private static final String COLUMN_Server_Type = "ServerType";
    private static final String COLUMN_NameServerPersian = "NameServerPersian";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccServerIP() {
        return COLUMN_ccServerIP;
    }
    public static String COLUMN_ServerIP() {
        return COLUMN_ServerIP;
    }
    public static String COLUMN_PortServerIP() {
        return COLUMN_PortServerIP;
    }
    public static String COLUMN_NameServerIP() {
        return COLUMN_NameServerIP;
    }
    public static String COLUMN_IsTest() {
        return COLUMN_IsTest;
    }
    public static String COLUMN_Server_Type(){return COLUMN_Server_Type;}
    public static String COLUMN_NameServerPersian(){return COLUMN_NameServerPersian;}


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("serverIp")
    @Expose
    private String serverIp;
    @SerializedName("port")
    @Expose
    private String port;
    @SerializedName("local")
    @Expose
    private String local;
    @SerializedName("test")
    @Expose
    private int test;
    @SerializedName("serverType")
    @Expose
    private int serverType;
    @SerializedName("NameServerPersian")
    @Expose
    private String NameServerPersian;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public String getNameServerPersian() {
        return NameServerPersian;
    }

    public void setNameServerPersian(String nameServerPersian) {
        NameServerPersian = nameServerPersian;
    }
}
