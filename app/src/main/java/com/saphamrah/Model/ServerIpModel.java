package com.saphamrah.Model;

public class ServerIpModel
{

    private static final String TABLE_NAME = "ServerIP";
    private static final String COLUMN_ccServerIP = "ccServerIP";
    private static final String COLUMN_ServerIP = "ServerIP";
    private static final String COLUMN_PortServerIP = "PortServerIP";
    private static final String COLUMN_NameServerIP = "NameServerIP";
    private static final String COLUMN_IsTest = "IsTest";


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



    private int id;
    private String serverIp;
    private String port;
    private String local;
    private int test;

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

}
