package com.saphamrah.customer.utils;

public class Constants {





    /*filter&sort*/
    public static final int SORT = 1;
    public static final int FILTER = 2;

    public static final int FILTER_LIST = 3;
    public static final int FILTER_SLIDER = 4;
    public static final int FILTER_DATE_PICKER = 5;

    /*productType*/
    public static final int ADVERTISEMENT = 1;
    public static final int SELL = 2;

    public static final int SUCCESSFUL = 1;
    public static final int FAILED = 2;


    // Good Group
    public static final int PASTE = 1;
    public static final int SAUCE = 2;
    public static final int  CANNED = 3;
    public static final int  ICE_CREAM = 4;
    public static final int  JUICE = 5;
    public static final int MATCH = 6;



    public static final int BRAND = 100;
    public static final int GOROH_KALA = 200;



    public static final int DELPAZIR = 101;
    public static final int MIHAN = 102;
    public static final int LINA = 103;
    public static final int MAHRAM = 104;
    public static final int KALLEH = 105;
    public static final int TAVAKOLLI = 106;



    // Sort
    public static final int MAX_CONSUMER_PRICE = 1001;
    public static final int MIN_CONSUMER_PRICE = 1002;
    public static final int MIN_SELL_PRICE = 1003;
    public static final int MAX_SELL_PRICE = 1004;
    public static final int CONSUMER_PRICE_TRACK = 1005;
    public static final int SELL_PRICE_TRACK = 1006;

    ////Status Codes
    public static final int FAILED_FROM_SERVER = 1;
    public static final int FAILED_BY_USER = 2;


    public enum PaymentStates {
        SHOW_PRODUCTS, CALCULATE_BONUS_DISCOUNT, ADD_RETURNED, SELECTABLE_BONUS ,CONFIRM_REQUEST, PISH_FAKTOR, SAVE_REQUEST
    }



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
