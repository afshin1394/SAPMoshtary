package com.saphamrah.customer;

public class Constants {

    ///////////////////////////// GPS TRACKER /////////////////////////////
    private static final String ZOOM = "17";
    private static final String RESPONSE_TYPE = "json";
    private static final String DISTANCE = "distance";
    private static final String INTERVAL = "interval";
    private static final String FASTEST_INTERVAL = "fastestInterval";
    private static final int MIN_DISTANCE_CHANGE_FOR_UPDATE = 20; // in meter
    private static final int INTERVAL_VALUE = 3000; // in milisecond
    private static final int FASTEST_INTERVAL_VALUE = 2000; // in milisecond
    private static final int MAX_ACCURACY_VALUE = 20;
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String SPEED = "speed";
    private static final String TIME = "time";
    private static final String ALTITUDE = "altitude";
    private static final String ACCURACY = "accuracy";
    private static final String BEARING = "bearing";
    private static final String ELAPSED_REAL_TIME_NANOS = "elapsedRealtimeNanos";
    private static final String PROVIDER = "provider";

    public static String ZOOM(){return ZOOM;}
    public static String RESPONSE_TYPE(){return RESPONSE_TYPE;}
    public static String DISTANCE(){return DISTANCE;}
    public static String INTERVAL(){return INTERVAL;}
    public static String FASTEST_INTERVAL(){return FASTEST_INTERVAL;}
    public static int MIN_DISTANCE_CHANGE_FOR_UPDATE(){return MIN_DISTANCE_CHANGE_FOR_UPDATE;}
    public static int INTERVAL_VALUE(){return INTERVAL_VALUE;}
    public static int FASTEST_INTERVAL_VALUE(){return FASTEST_INTERVAL_VALUE;}
    public static int MAX_ACCURACY_VALUE(){return MAX_ACCURACY_VALUE;}
    public static String LATITUDE(){return LATITUDE;}
    public static String LONGITUDE(){return LONGITUDE;}
    public static String SPEED(){return SPEED;}
    public static String TIME(){return TIME;}
    public static String ALTITUDE(){return ALTITUDE;}
    public static String ACCURACY(){return ACCURACY;}
    public static String BEARING(){return BEARING;}
    public static String ELAPSED_REAL_TIME_NANOS(){return ELAPSED_REAL_TIME_NANOS;}
    public static String PROVIDER(){return PROVIDER;}



    ///////////////////////////// DEFAULT VALUE /////////////////////////////
    private static final int ALLOWABLE_SERVER_LOCAL_TIME_DIFF = 300; // time unit = second
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATE_TIME_WITH_SPACE = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_SHORT_FORMAT = "yyyy-MM-dd";
    private static final String DATE_SHORT_FORMAT_WITH_SLASH = "yyyy/MM/dd";
    private static final String NORMAL_DATE_TIME_FORMAT = "yyyy/MM/dd kk:mm:ss";
    private static final String DATE_TIME_MILISECONDS = "yyyy-MM-dd'T'HH:mm:ss.sss";
    private static final String DATE_TIME_MILISCONDS_2 = "yyyy-MM-dd HH:mm:ss.sss";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String SHORT_TIME_FORMAT = "HH:mm";
    private static final String UNIQ_TIME = "yyyyMMddHHmmss";

    public static int ALLOWABLE_SERVER_LOCAL_TIME_DIFF() { return ALLOWABLE_SERVER_LOCAL_TIME_DIFF; }

    /**
     *
     * @return yyyy-MM-dd'T'HH:mm:ss
     */
    public static String DATE_TIME_FORMAT() { return DATE_TIME_FORMAT; }

    /**
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String DATE_TIME_WITH_SPACE() { return DATE_TIME_WITH_SPACE; }

    /**
     *
     * @return yyyy-MM-dd
     */
    public static String DATE_SHORT_FORMAT() { return DATE_SHORT_FORMAT; }

    /**
     *
     * @return yyyy/MM/dd
     */
    public static String DATE_SHORT_FORMAT_WITH_SLASH() { return DATE_SHORT_FORMAT_WITH_SLASH; }

    /**
     *
     * @return yyyy/MM/dd kk:mm:ss
     */
    public static String NORMAL_DATE_TIME_FORMAT() { return NORMAL_DATE_TIME_FORMAT; }

    /**
     *
     * @return yyyy-MM-dd'T'HH:mm:ss.sss
     */
    public static String DATE_TIME_MILISECONDS() { return DATE_TIME_MILISECONDS; }

    /**
     *
     * @return yyyy-MM-dd HH:mm:ss.sss
     */
    public static String DATE_TIME_MILISCONDS_2() { return DATE_TIME_MILISCONDS_2; }

    /**
     *
     * @return HH:mm:ss
     */
    public static String TIME_FORMAT() { return TIME_FORMAT; }


    /**
     *
     * @return HH:mm
     */
    public static String SHORT_TIME_FORMAT() { return SHORT_TIME_FORMAT; }

    /**
     *
     * @return yyyyMMddHHmmss
     */
    public static String UNIQ_TIME() { return UNIQ_TIME; }

}
