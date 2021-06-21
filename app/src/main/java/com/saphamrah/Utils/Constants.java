package com.saphamrah.Utils;


import android.widget.Toast;

public class Constants
{

    ///////////////////////////// VERSION TYPE /////////////////////////////
    private static final int CURRENT_VERSION_TYPE = 2 ; //  0- main  1- Learn  2- Test 4-pegah 5-pegahLearn 6-Demo 7-PegahTest

    public static int CURRENT_VERSION_TYPE(){return CURRENT_VERSION_TYPE;}


    ///////////////////////////// LOG TYPE /////////////////////////////
    private static final int LOG_EXCEPTION = 1;
    private static final int LOG_RESPONSE_CONTENT_LENGTH = 2;
    private static final int LOG_SPEED_TEST = 3;

    public static int LOG_EXCEPTION(){return LOG_EXCEPTION;}
    public static int LOG_RESPONSE_CONTENT_LENGTH(){return LOG_RESPONSE_CONTENT_LENGTH;}
    public static int LOG_SPEED_TEST(){return LOG_SPEED_TEST;}


    ///////////////////////////// ERROR TYPE /////////////////////////////
    private static final String RETROFIT_HTTP_ERROR = "HTTP_ERROR";
    private static final String RETROFIT_RESULT_IS_NULL = "RESULT_IS_NULL";
    private static final String RETROFIT_RESULT_IS_EMPTY = "RESULT_IS_EMPTY";
    private static final String RETROFIT_NOT_SUCCESS_MESSAGE = "RESPONSE_NOT_SUCCESS";
    private static final String RETROFIT_EXCEPTION = "EXCEPTION";
    private static final String RETROFIT_THROWABLE = "THROWABLE";

    public static String RETROFIT_HTTP_ERROR(){return RETROFIT_HTTP_ERROR;}
    public static String RETROFIT_RESULT_IS_NULL(){return RETROFIT_RESULT_IS_NULL;}
    public static String RETROFIT_RESULT_IS_EMPTY(){return RETROFIT_RESULT_IS_EMPTY;}
    public static String RETROFIT_NOT_SUCCESS_MESSAGE(){return RETROFIT_NOT_SUCCESS_MESSAGE;}
    public static String RETROFIT_EXCEPTION(){return RETROFIT_EXCEPTION;}
    public static String RETROFIT_THROWABLE(){return RETROFIT_THROWABLE;}


    private static final String HTTP_EXCEPTION = "EXCEPTION";
    private static final String HTTP_NULL_RESPONSE = "HTTP_NULL_RESPONSE";
    private static final String HTTP_RESOURCE_NOT_FOUND = "HTTP_RESOURCE_NOT_FOUND_ERROR";
    private static final String HTTP_INTERNAL_SERVER_ERROR = "HTTP_SERVER_ERROR";
    private static final String HTTP_ERROR = "HTTP_ERROR";
    private static final String HTTP_BAD_REQUEST="HTTP_BAD_REQUEST";
    private static final String HTTP_WRONG_ENDPOINT = "HTTP_WRONG_ENDPOINT";
    private static final String HTTP_TIME_OUT_EXCEPTION="HTTP_TIME_OUT_EXCEPTION";



    public static final  String HTTP_WRONG_ENDPOINT() {  return  HTTP_WRONG_ENDPOINT;   }
    public static final  String HTTP_NULL_RESPONSE() {  return  HTTP_NULL_RESPONSE;   }
    public static final  String HTTP_BAD_REQUEST(){return HTTP_BAD_REQUEST;}
    public static final  String HTTP_RESOURCE_NOT_FOUND(){return HTTP_RESOURCE_NOT_FOUND;}
    public static final  String HTTP_INTERNAL_SERVER_ERROR(){return HTTP_INTERNAL_SERVER_ERROR;}
    public static final  String HTTP_ERROR(){return HTTP_ERROR;}
    public static final String HTTP_TIME_OUT_EXCEPTION(){return HTTP_TIME_OUT_EXCEPTION;}



    ///////////////////////////// PERMISSIONS CODE /////////////////////////////
    private static final int READ_PHONE_STATE = 101;
    private static final int ACCESS_FINE_LOCATION = 102;
    private static final int CHANGE_WIFI_STATE = 103;
    private static final int ACCESS_NETWORK_STATE = 104;
    private static final int WRITE_EXTERNAL_STORAGE = 105;
    private static final int READ_PRIVILEGED_PHONE_STATE = 106;
    private static final int ALL_PERMISSIONS = 917;


    public static int READ_PHONE_STATE() {
        return READ_PHONE_STATE;
    }
    public static int ACCESS_FINE_LOCATION() { return ACCESS_FINE_LOCATION; }
    public static int CHANGE_WIFI_STATE() { return CHANGE_WIFI_STATE; }
    public static int ACCESS_NETWORK_STATE() { return ACCESS_NETWORK_STATE; }
    public static int WRITE_EXTERNAL_STORAGE() { return WRITE_EXTERNAL_STORAGE; }
    public static int ALL_PERMISSIONS() {
        return ALL_PERMISSIONS;
    }

    ///////////////////////////// OPEN ACTIVITY FOR RESULT CODE /////////////////////////////
    private static final int OPEN_LOCALE_SETTING = 101;
    private static final int OPEN_LOCATION_SETTING = 102;
    private static final int OPEN_UPDATE_GOOGLE_PLAY_SERVICES = 103;


    public static int OPEN_LOCALE_SETTING() {
        return OPEN_LOCALE_SETTING;
    }
    public static int OPEN_LOCATION_SETTING() { return OPEN_LOCATION_SETTING; }
    public static int OPEN_UPDATE_GOOGLE_PLAY_SERVICES() { return OPEN_UPDATE_GOOGLE_PLAY_SERVICES; }


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


    ///////////////////////////// MESSAGE TYPE /////////////////////////////
    private static final int FAILED_MESSAGE = -1;
    private static final int NONE_MESSAGE = 0;
    private static final int SUCCESS_MESSAGE = 1;
    private static final int RESPONSE_NOT_COMPLETE = -2;
    private static final int INFO_MESSAGE = 2;
    private static final int DURATION_SHORT = Toast.LENGTH_SHORT; //using this durations(DURATION_SHORT , DURATION_LONG) in presenter,
    private static final int DURATION_LONG = Toast.LENGTH_LONG;   //because we can't use android class in presenter


    public static int FAILED_MESSAGE() {
        return FAILED_MESSAGE;
    }
    public static int NONE_MESSAGE() {
        return NONE_MESSAGE;
    }
    public static int SUCCESS_MESSAGE() {
        return SUCCESS_MESSAGE;
    }
    public static int RESPONSE_NOT_COMPLETE(){return RESPONSE_NOT_COMPLETE;}
    public static int INFO_MESSAGE() {
        return INFO_MESSAGE;
    }
    public static int DURATION_SHORT() {
        return DURATION_SHORT;
    }
    public static int DURATION_LONG() {
        return DURATION_LONG;
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



    ///////////////////////////// SYSTEM MENU /////////////////////////////
    private static final String DIRECTORY_OF_ACTIVITY = ".MVP.View.";

    public static String DIRECTORY_OF_ACTIVITY(){return DIRECTORY_OF_ACTIVITY;}


    ///////////////////////////// ENCRYPTION /////////////////////////////
    private static final String ENC_KEY = "yKVkeds7fUlDpmQ0N3o";

    public static String ENC_KEY(){return ENC_KEY;}



    ///////////////////////////// RETURNED MESSAGE FROM BULK INSERT THREAD /////////////////////////////
    private static final int BULK_INSERT_SUCCESSFUL = 1;
    private static final int BULK_INSERT_FAILED = -1;

    public static int BULK_INSERT_SUCCESSFUL(){return BULK_INSERT_SUCCESSFUL;}
    public static int BULK_INSERT_FAILED(){return BULK_INSERT_FAILED;}


    ///////////////////////////// GET PROGRAM AND UPDATES /////////////////////////////
    private static final int GET_PROGRAM_FULL = 1;
    private static final int GET_PROGRAM_UPDATE_KALA = 2;
    private static final int GET_PROGRAM_UPDATE_JAYEZE = 3;
    private static final int GET_PROGRAM_UPDATE_MOSHTARY = 4;
    private static final int GET_PROGRAM_UPDATE_PARAMETERS = 5;
    private static final int GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH = 6;

    public static int GET_PROGRAM_FULL(){return GET_PROGRAM_FULL;}
    public static int GET_PROGRAM_UPDATE_KALA(){return GET_PROGRAM_UPDATE_KALA;}
    public static int GET_PROGRAM_UPDATE_JAYEZE(){return GET_PROGRAM_UPDATE_JAYEZE;}
    public static int GET_PROGRAM_UPDATE_MOSHTARY(){return GET_PROGRAM_UPDATE_MOSHTARY;}
    public static int GET_PROGRAM_UPDATE_PARAMETERS(){return GET_PROGRAM_UPDATE_PARAMETERS;}
    public static int GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH(){return GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH;}


    ///////////////////////////// SPEED TEST RATE /////////////////////////////
    private static final int SPEED_TEST_GOOD = 1;
    private static final int SPEED_TEST_MEDIUM = 2;
    private static final int SPEED_TEST_BAD = 3;

    public static int SPEED_TEST_GOOD(){return SPEED_TEST_GOOD;}
    public static int SPEED_TEST_MEDIUM(){return SPEED_TEST_MEDIUM;}
    public static int SPEED_TEST_BAD(){return SPEED_TEST_BAD;}



    ///////////////////////////// MAHAL TYPE /////////////////////////////
    /*private static final int MAHAL_TYPE_OSTAN = 2;
    private static final int MAHAL_TYPE_SHAHRESTAN = 3;
    private static final int MAHAL_TYPE_BAKHSH = 4;
    private static final int MAHAL_TYPE_SHAHR = 5;
    private static final int MAHAL_TYPE_MANTAGHE = 6;

    public static int MAHAL_TYPE_OSTAN() {
        return MAHAL_TYPE_OSTAN;
    }
    public static int MAHAL_TYPE_SHAHRESTAN() {
        return MAHAL_TYPE_SHAHRESTAN;
    }
    public static int MAHAL_TYPE_BAKHSH() {
        return MAHAL_TYPE_BAKHSH;
    }
    public static int MAHAL_TYPE_SHAHR() {
        return MAHAL_TYPE_SHAHR;
    }
    public static int MAHAL_TYPE_MANTAGHE() {
        return MAHAL_TYPE_MANTAGHE;
    }*/




    ///////////////////////////// BITMAP QUALITY /////////////////////////////
    private static final int BITMAP_TO_BYTE_QUALITY = 100;

    public static int BITMAP_TO_BYTE_QUALITY() {
        return BITMAP_TO_BYTE_QUALITY;
    }



    ///////////////////////////// ADD CUSTOMER PARAMETER /////////////////////////////
    private static final int SHOW_SELL_POLYGON = 1;
    private static final int SHOW_ADD_DOCS = 2;
    private static final int SHOW_CUSTOMER_LOCATION = 3;
    private static final int DELETE_CUSTOMER = 4;
    private static final int SEND_CUSTOMER = 5;
    private static final int ADDRESS_TYPE_DARKHAST = 1;
    private static final int ADDRESS_TYPE_DARKHAST_TAHVIL = 2;
    private static final int ADDRESS_TYPE_TAHVIL = 3;

    public static int SHOW_SELL_POLYGON() {
        return SHOW_SELL_POLYGON;
    }
    public static int SHOW_ADD_DOCS() {
        return SHOW_ADD_DOCS;
    }
    public static int SHOW_CUSTOMER_LOCATION() {
        return SHOW_CUSTOMER_LOCATION;
    }
    public static int DELETE_CUSTOMER() {
        return DELETE_CUSTOMER;
    }
    public static int SEND_CUSTOMER() {
        return SEND_CUSTOMER;
    }
    public static int ADDRESS_TYPE_DARKHAST() {
        return ADDRESS_TYPE_DARKHAST;
    }
    public static int ADDRESS_TYPE_DARKHAST_TAHVIL() {return ADDRESS_TYPE_DARKHAST_TAHVIL;}
    public static int ADDRESS_TYPE_TAHVIL() {
        return ADDRESS_TYPE_TAHVIL;
    }



    ///////////////////////////// INVOICE REQUESTS LIST PARAMETER /////////////////////////////
    private static final int DELETE = 1;
    private static final int PRINT = 2;
    private static final int SEND = 3;
    private static final int SAVE_IMAGE = 4;
    private static final int SHOW_IMAGE = 5;
    private static final int EDIT = 6;
    private static final int SHOW_LOCATION = 7;
    private static final int EDIT_DARKHAST = 8;
    private static final int SHOW_FAKTOR_DETAIL = 9;
    public static final int MARJOEE = 10;
    public static final int SAVE_SEND_LOCATION = 11;


    public static int DELETE() {
        return DELETE;
    }
    public static int PRINT() {
        return PRINT;
    }
    public static int SEND() {
        return SEND;
    }
    public static int SAVE_IMAGE() {
        return SAVE_IMAGE;
    }
    public static int SHOW_IMAGE() {
        return SHOW_IMAGE;
    }
    public static int SHOW_FAKTOR_DETAIL() {
        return SHOW_FAKTOR_DETAIL;
    }
    public static int CLEARING() {
        return EDIT;
    }
    public static int SHOW_LOCATION() {
        return SHOW_LOCATION;
    }
    public static int EDIT_DARKHAST() {
        return EDIT_DARKHAST;
    }

    ///////////////////////////// OPERATION OF REQUEST CUSTOMER LIST PARAMETER /////////////////////////////
    private static final int REQUEST_CUSTOMER_SHOW_LOCATION = 1;
    private static final int REQUEST_CUSTOMER_CHANGE_LOCATION = 2;
    private static final int REQUEST_CUSTOMER_SHOW_CUSTOMER_INFO = 3;
    private static final int REQUEST_CUSTOMER_CHANGE_CUSTOMER_INFO = 4;
    private static final int REQUEST_CUSTOMER_SELECT_CUSTOMER = 5;
    private static final int REQUEST_CUSTOMER_UPDATE_CREDIT = 6;


    private static final int REQUEST_CUSTOMER_CCPARAMETER_OF_CHECKS = 17;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_MOJAZ_FOR_DARKHAST = 48;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_ETEBAR_CHECK_BARGASHTY = 49;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_MOJAZ_FOR_RESID = 50;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_FAKTOR_ERSAL_NASHODEH = 51;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_CHECK_BARGASHTY = 52;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_TIME_DARKHAST = 53;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_MASAHAT_MAGHAZEH = 54;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_ANBARAK = 55;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_CC_START_REST_TIME = 56;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_MOSHTARY_FOROSHANDEH = 57;
    private static final int CHECK_MOSHTARY_MASIR_ROOZ_DISTANCE = 58;
    private static final int UPDATE_MANDE_MOJODI = 18;
    private static final int UPDATE_MANDE_MOJODI_MASIR_ROOZ = 59;


    public static int REQUEST_CUSTOMER_SHOW_LOCATION() {
        return REQUEST_CUSTOMER_SHOW_LOCATION;
    }
    public static int REQUEST_CUSTOMER_CHANGE_LOCATION() {
        return REQUEST_CUSTOMER_CHANGE_LOCATION;
    }
    public static int REQUEST_CUSTOMER_SHOW_CUSTOMER_INFO() {
        return REQUEST_CUSTOMER_SHOW_CUSTOMER_INFO;
    }
    public static int REQUEST_CUSTOMER_CHANGE_CUSTOMER_INFO() {
        return REQUEST_CUSTOMER_CHANGE_CUSTOMER_INFO;
    }
    public static int REQUEST_CUSTOMER_UPDATE_CREDIT() {
        return REQUEST_CUSTOMER_UPDATE_CREDIT;
    }
    public static int REQUEST_CUSTOMER_SELECT_CUSTOMER() {
        return REQUEST_CUSTOMER_SELECT_CUSTOMER;
    }
    public static int REQUEST_CUSTOMER_CCPARAMETER_OF_CHECKS() {
        return REQUEST_CUSTOMER_CCPARAMETER_OF_CHECKS;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_MOJAZ_FOR_DARKHAST() {
        return CHECK_MOSHTARY_MASIR_ROOZ_MOJAZ_FOR_DARKHAST;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_ETEBAR_CHECK_BARGASHTY() {
        return CHECK_MOSHTARY_MASIR_ROOZ_ETEBAR_CHECK_BARGASHTY;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_MOJAZ_FOR_RESID() {
        return CHECK_MOSHTARY_MASIR_ROOZ_MOJAZ_FOR_RESID;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_FAKTOR_ERSAL_NASHODEH() {
        return CHECK_MOSHTARY_MASIR_ROOZ_FAKTOR_ERSAL_NASHODEH;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_CHECK_BARGASHTY() {
        return CHECK_MOSHTARY_MASIR_ROOZ_CHECK_BARGASHTY;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_TIME_DARKHAST() {
        return CHECK_MOSHTARY_MASIR_ROOZ_TIME_DARKHAST;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_MASAHAT_MAGHAZEH() {
        return CHECK_MOSHTARY_MASIR_ROOZ_MASAHAT_MAGHAZEH;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_ANBARAK() {
        return CHECK_MOSHTARY_MASIR_ROOZ_ANBARAK;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_CC_START_REST_TIME() {
        return CHECK_MOSHTARY_MASIR_ROOZ_CC_START_REST_TIME;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_MOSHTARY_FOROSHANDEH() {
        return CHECK_MOSHTARY_MASIR_ROOZ_MOSHTARY_FOROSHANDEH;
    }
    public static int CHECK_MOSHTARY_MASIR_ROOZ_DISTANCE() {
        return CHECK_MOSHTARY_MASIR_ROOZ_DISTANCE;
    }
    public static int UPDATE_MANDE_MOJODI() {
        return UPDATE_MANDE_MOJODI;
    }
    public static int UPDATE_MANDE_MOJODI_MASIR_ROOZ() {
        return UPDATE_MANDE_MOJODI_MASIR_ROOZ;
    }

    ///////////////////////////// PHOTO TYPE /////////////////////////////
    private static final int PHOTO_TYPE_NATIONAL_CARD = 4;
    private static final int PHOTO_TYPE_JAVAZE_KASB = 6;
    private static final int PHOTO_TYPE_DASTE_CHECK = 28;


    public static int PHOTO_TYPE_NATIONAL_CARD() {
        return PHOTO_TYPE_NATIONAL_CARD;
    }
    public static int PHOTO_TYPE_JAVAZE_KASB() {
        return PHOTO_TYPE_JAVAZE_KASB;
    }
    public static int PHOTO_TYPE_DASTE_CHECK() {
        return PHOTO_TYPE_DASTE_CHECK;
    }


    ///////////////////////////// API KEY /////////////////////////////
    private static final String API_KEY_OPEN_WEATHER_MAP_PART_ONE = "c8c6f0c3821ec";
    private static final int API_KEY_OPEN_WEATHER_MAP_PART_TWO = 55066;
    private static final String API_KEY_OPEN_WEATHER_MAP_PART_THREE = "bfaac7bdcdf47c";

    public static String API_KEY_OPEN_WEATHER_MAP() {
        return API_KEY_OPEN_WEATHER_MAP_PART_ONE + API_KEY_OPEN_WEATHER_MAP_PART_TWO + API_KEY_OPEN_WEATHER_MAP_PART_THREE;
    }



    ///////////////////////////// Parameter Table Name /////////////////////////////
    private static final String NOE_SHAKHSIAT = "NoeShakhsiat";
    private static final int CODE_NOE_SHAKHSIAT_HAGHIGHI = 1;
    private static final int CODE_NOE_SHAKHSIAT_HOGHOGHI = 2;
    private static final String ROTBE = "Rotbe";
    private static final String NOE_VOSOL_MOSHTARY = "NoeVosolMoshtary";
    private static final String NOE_VOSOL_MOSHTARY_RESID = "resid";
    private static final int CODE_NOE_VOSOL_MOSHTARY_RESID = 1;
    private static final int CODE_NOE_VOSOL_MOSHTARY_CHECK = 2;
    private static final int CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD = 3;
    private static final int CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh = 5;
    private static final int CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh = 4;
    private static final int CODE_NOE_VOSOL_MOSHTARY_Resid_Naghd = 6;
    private static final int CC_CHILD_VOSOL_MOSHTARY_RESID = 4;
    private static final int CC_CHILD_VOSOL_MOSHTARY_CHECK = 5;
    private static final int CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD = 6;
    private static final int CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh = 7;
    private static final int CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh = 8;
    private static final int CC_CHILD_VOSOL_MOSHTARY_Resid_Naghd = 9;
    private static final int CODE_NOE_VOSOL_RESID = 9;
    private static final int CODE_NOE_VOSOL_CHECK = 2;
    private static final int CODE_NOE_VOSOL_VAJH_NAGHD = 1;
    private static final int CC_CHILD_CC_NOE_ADDRESS_DARKHAST_TAHVIL = 17;
    private static final int CC_CHILD_CC_NOE_ADDRESS_TAHVIL = 18;
    private static final int CC_CHILD_VOSOL_RESID = 65;
    private static final int CC_CHILD_VOSOL_CHECK = 62;
    private static final int CC_CHILD_VOSOL_VAJH_NAGHD = 61;
    private static final int CC_VOSOL_IS_MOJAZ_FOR_RESID = 97;
    private static final int CC_VOSOL_IS_ETEBAR_CHECK_BARGASHTY = 98;
    private static final int CC_VOSOL_IS_MOSHTARY_FOROSHANDE = 102;
    private static final int CC_VOSOL_IS_RESID = 99;
    private static final int CC_VOSOL_IS_CHECK = 100;
    private static final int CC_VOSOL_IS_VAJH_NAGHD = 101;
    private static final String NOE_VOSOL_MOSHTARY_RESID_NAGHD = "residNaghd";
    private static final String NOE_VOSOL_MOSHTARY_JADID = "NoeVosolMoshtaryJadid";
    private static final String NOE_VOSOL_MOSHTARY_JADID_KHORDE_OMDE = "NoeVosolMoshtaryJadidKhordeOmde";
    private static final int CC_CHILD_PARAMETER_NOE_FAALIAT_MOSHTARY_JADID = 132;
    private static final String NOE_HAML = "NoeHaml";
    private static final String NOE_HAML_MOSHTARY_JADID = "NoeHamlMoshtaryJadid";
    private static final String SHART_BARDASHT = "ShartBardasht";
    //private static final String EDIT_CUSTOMER_ITEMS = "EditCustomerItems";
    private static final int CC_EDIT_CUSTOMER_ITEMS = 12;
    private static final int CC_CHILD_CAN_UPDATE_CUSTOMER = 139;
    private static final String CAN_UPDATE_NATIONAL_CODE = "updateNationalCode";
    private static final String CAN_UPDATE_NOE_FAALIAT = "updateNoeFaaliat";
    private static final String CAN_UPDATE_NOE_SENF = "updateNoeSenf";
    private static final String CAN_UPDATE_ANBAR = "updateAnbar";
    private static final String CAN_UPDATE_MASAHAT_MAGHAZE = "updateMasahatMaghaze";
    private static final String CAN_UPDATE_SAATE_VISIT = "updateSaateVisit";
    private static final String CAN_UPDATE_MOBILE = "updateMobile";
    private static final String CAN_UPDATE_ADDRESS = "updateAddress";
    private static final int CC_BARKHORD_AVALIE_CONFIG = 13;
    private static final int CC_MOJOODI_GIRI_CONFIG = 14;
    private static final String SHOW = "show";
    private static final String FORCE = "force";
    private static final String MULTIPLE = "multiple";
    private static final int CC_CODE_NOE_VOSOL_MPSHTARY_RESID = 4;
    private static final int CC_PARAMETER_CODE_NOE_VOSOL_MOSHTARY = 4; // noe vosol az moshtary
    private static final int CC_BE_MASOLIAT_FOROSH = 36;
    private static final int CC_NOE_HAML_ADDI = 12;
    private static final int CC_VAZEIAT_FAKTOR_TEMP = 37;
    private static final int CC_VOSOL_NAGHD = 76;
    private static final int CC_VOSOL_CHECK = 77;
    private static final int CC_VOSOL_RESID = 78;
    private static final String CC_IRAN_CHECK = "23";
    private static final String VALUE_VAJH_NAGHD = "1";
    private static final String VALUE_VAJH_NAGHD_YEK_SETARE = "5";
    private static final String VALUE_VAJH_NAGHD_DO_SETARE = "4";
    private static final String VALUE_POS = "20";
    private static final String VALUE_IRANCHECK = "22";
    private static final String VALUE_FISH_BANKI = "3";
    private static final String VALUE_CHECK = "2";
    private static final String VALUE_RESID = "9";
    private static final String VALUE_MARJOEE = "6";
    private static final String CC_CODE_NOE_SANAD = "24";
    private static final String VALUE_SANAD_IRAN_CHECK = "101";
    private static final String VALUE_SANAD_CHECK_BANKI = "3";
    private static final String VALUE_SANAD_POS = "102";
    private static final String VALUE_SANAD_RESID = "9";
    public static final String VALUE_SANAD_TAJIL = "18";
    public static final String VALUE_SANAD_DIRKARD = "19";
    public static final String VALUE_CHECK_BANKI = "100";
    private static final String CC_PARAMETER_TAEED_DARKHAST_CONFIG = "25";
    private static final int CC_TAEED_DARKHAST_SHOW_BTN_MARJOEE = 96;
    private static final String CC_PARAMETER_GOROH_MOSHTARY = "26";
    private static final int CC_CHILD_GOROH_MOSHTARY_KHORDE = 103;
    private static final int CC_CHILD_GOROH_MOSHTARY_OMDE = 104;
    private static final int CC_CHILD_GOROH_MOSHTARY_TAAVONI_VIJE = 105;
    private static final int CC_CHILD_GOROH_MOSHTARY_ZANJIRE = 106;
    private static final int CC_CHILD_GOROH_MOSHTARY_NAMAYANDE1 = 107;
    private static final int CC_CHILD_GOROH_MOSHTARY_NAMAYANDE2 = 108;
    private static final int CC_CHILD_GOROH_MOSHTARY_TAAVONI_KARKONAN = 109;
    private static final int CC_CHILD_CODE_TAKHFIF_SENFI = 110;
    private static final int CC_CHILD_CODE_TAKHFIF_NAGHDI = 111;
    private static final int CC_CHILD_CODE_TAKHFIF_JAYEZE = 113;
    private static final int CC_CHILD_CODE_TAKHFIF_HAJMI = 114;
    private static final int CC_CHILD_TAKHFIF_FOR_VOSOL_NAGHD = 116;
    private static final int CC_CHILD_TAKHFIF_FOR_VOSOL_CHECK = 117;
    private static final int CC_CHILD_TAKHFIF_FOR_VOSOL_RESID = 118;
    private static final int CC_NOE_BASTE_BANDI = 29;
    private static final int CC_CHILD_NOE_BASTE_BANDI_CARTON = 119;
    private static final int CC_CHILD_NOE_BASTE_BANDI_BASTE = 120;
    private static final int CC_CHILD_NOE_BASTE_BANDI_ADAD = 121;
    private static final int CC_NOE_TEDAD_RIAL = 30;
    private static final int CC_CHILD_NOE_TEDAD_RIAL_TEDAD = 122;
    private static final int CC_CHILD_NOE_TEDAD_RIAL_RIAL = 123;
    public static final int CC_CHILD_NOE_TEDAD_RIAL_AGHLAM = 124;
    public static final int CC_CHILD_NOE_TEDAD_RIAL_Vazn = 184;
    private static final int CC_CHILD_TELEPHONE_BAZRESI = 125;
    private static final int CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD = 61;
    private static final int CC_CHILD_CODE_NOE_VOSOL = 62;
    private static final int CC_CHILD_CODE_NOE_VOSOL_MARJOEE = 64;
    private static final int CC_PARAMETER_PRINT_CONFIG = 32;
    private static final int CC_CHILD_PRINT_SHOW_ARZESH_AFZOODE = 129;
    private static final int CC_CHILD_NOT_CHECK_KHAREJ_AZ_MAHAL = 130;
    private static final int CC_DOWNLOAD_URL = 31;
    private static final int CC_CHILD_DOWNLOAD_URL_ASLI = 126;
    private static final int CC_CHILD_DOWNLOAD_URL_AMOOZESH = 127;
    private static final int CC_CHILD_DOWNLOAD_URL_TEST = 128;
    private static final int CC_CHILD_DOWNLOAD_URL_AZMAYESHI = 141;
    private static final int CC_CHILD_FORCE_WIFI_ON_FOR_GPS = 131;
    private static final int CC_CHILD_CAN_INSERT_FAKTOR_MOSHTARY_JADID = 133;
    private static final int CC_CHILD_MAX_DIFF_TAKHFIF_TITR_BY_SATR = 134;
    private static final int CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST = 135;
    private static final int CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST = 137;
    private static final int CC_CHILD_UNINSTALL_INVALID_PACKAGE = 136;
    private static final int CC_CHILD_CHECK_LOCATION_IN_POLYGON_FOR_NEW_CUSTOMER = 138;
    private static final int CC_CHILD_CAN_INSERT_MOSHTARY_JADID = 142;
    private static final int CC_CHILD_ENABLE_GET_MESSAGE = 143;
    public static final int CC_NOE_RIAL_JAYEZEH = 35;
    public static final int CC_CHILD_NOE_RIAL_JAYEZEH_ADADY = 144;
    public static final int CC_CHILD_NOE_RIAL_JAYEZEH_DARSADI = 145;
	public static final int CC_CHILD_CODE_JAYEZEH = 146;													
    private static final int CC_CHILD_IS_SEND_KALA_MOJODI = 147;
	public static final int CC_CHILD_SHOW_SAYYAD_CHECK_SCANNER = 148;																  
	public static final int CC_CHILD_MALIAT = 152;
    public static final int CC_CHILD_AVAREZ = 153;
    public static final int CC_CHILD_HADAGHAL_MABLAGH_KHARID_MOSHTARY_JADID = 155;
    public static final int CC_CHILD_UPDATE_JAYEZEH_TAKHFIF = 156;
    public static final int CC_CHILD_ZARIB_KHAREJ_AZ_MAHAL_METR = 160;
    public static final int CC_CHILD_UPDATE_GALLERY = 161;
    public static final int CC_CHILD_MABLAGH_MAX_FAKTOR_OMDEH_MOSHTARY_JADID = 163;
    public static final int CC_CHILD_MABLAGH_MAX_FAKTOR_KHORDEH_MOSHTARY_JADID = 164;
    public static final int CC_CHILD_Min_Zaman_Hozor_Dar_Maghazeh   = 165;
    public static final int CC_CHILD_GPS_ENABLE = 166;
    public static final int CC_CHILD_GPS_MIN_DISTANCE = 168;
    public static final int CC_CHILD_GPS_TIME_INTERVAL = 169;
    public static final int CC_CHILD_GPS_TIME_FASTEST_INTERVAL = 170;
    public static final int CC_CHILD_GPS_MAX_ACCURANCY = 171;
    public static final int CC_CHILD_PISHNAHAD_KALA = 172;
    public static final int CC_CHILD_REQUIER_CODE_MELI = 173;
    public static final int CC_CHILD_REQUIER_CODE_POSTI = 174;
    public static final int CC_CHILD_REQUIER_MOBILE = 175;
    public static final int CC_CHILD_REQUIER_TELEPHONE = 176;
    public static final int CC_CHILD_REQUIER_MASAHAT = 177;
    public static final int CC_CHILD_GET_ETEBAR_FOROSHANDEH_ONLINE = 178;
    public static final int CC_CHILD_MOHASEBE_KALA_ASASI = 179;
    public static final int CC_CHILD_ZARIB_DARKHAST_TABLET = 180;
    public static final int CC_CHILD_MAX_ROOZ_PISHBINI_TAHVIL = 181;
    public static final int CC_CHILD_MIN_TAKHFIF_NAGHDI_KHORDE = 182;
    public static final int CC_CHILD_MIN_TAKHFIF_NAGHDI_GHEIRE_KHORDE = 183;



    public static String NOE_SHAKHSIAT()
    {
        return NOE_SHAKHSIAT;
    }
    public static int CODE_NOE_SHAKHSIAT_HAGHIGHI()
    {
        return CODE_NOE_SHAKHSIAT_HAGHIGHI;
    }
    public static int CODE_NOE_SHAKHSIAT_HOGHOGHI()
    {
        return CODE_NOE_SHAKHSIAT_HOGHOGHI;
    }
    public static String ROTBE()
    {
        return ROTBE;
    }
    public static String NOE_VOSOL_MOSHTARY()
    {
        return NOE_VOSOL_MOSHTARY;
    }
    public static String NOE_VOSOL_MOSHTARY_RESID()
    {
        return NOE_VOSOL_MOSHTARY_RESID;
    }
    public static int CODE_NOE_VOSOL_MOSHTARY_RESID()
    {
        return CODE_NOE_VOSOL_MOSHTARY_RESID;
    }
    public static int CODE_NOE_VOSOL_MOSHTARY_CHECK()
    {
        return CODE_NOE_VOSOL_MOSHTARY_CHECK;
    }
    public static int CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh()
    {
        return CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh;
    }
    public static int CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh()
    {
        return CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh;
    }
    public static int CODE_NOE_VOSOL_MOSHTARY_Resid_Naghd()
    {
        return CODE_NOE_VOSOL_MOSHTARY_Resid_Naghd;
    }
    public static int CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD()
    {
        return CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD;
    }
    public static int CC_CHILD_CC_NOE_ADDRESS_DARKHAST_TAHVIL()
    {
        return CC_CHILD_CC_NOE_ADDRESS_DARKHAST_TAHVIL;
    }
    public static int CODE_NOE_VOSOL_VAJH_NAGHD()
    {
        return CODE_NOE_VOSOL_VAJH_NAGHD;
    }
    public static int CC_CHILD_CC_NOE_ADDRESS_TAHVIL()
    {
        return CC_CHILD_CC_NOE_ADDRESS_TAHVIL;
    }
    public static int CODE_NOE_VOSOL_RESID()
    {
        return CODE_NOE_VOSOL_RESID;
    }
    public static int CODE_NOE_VOSOL_CHECK()
    {
        return CODE_NOE_VOSOL_CHECK;
    }
    public static int CC_CHILD_VOSOL_MOSHTARY_RESID()
    {
        return CC_CHILD_VOSOL_MOSHTARY_RESID;
    }
    public static int CC_CHILD_VOSOL_MOSHTARY_CHECK()
    {
        return CC_CHILD_VOSOL_MOSHTARY_CHECK;
    }
    public static int CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD()
    {
        return CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD;
    }

    public static int CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh()
    {
        return CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh;
    }
    public static int CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh()
    {
        return CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh;
    }
    public static int CC_CHILD_VOSOL_MOSHTARY_Resid_Naghd()
    {
        return CC_CHILD_VOSOL_MOSHTARY_Resid_Naghd;
    }
    public static int CC_CHILD_VOSOL_VAJH_NAGHD()
    {
        return CC_CHILD_VOSOL_VAJH_NAGHD;
    }
    public static int CC_CHILD_VOSOL_RESID()
    {
        return CC_CHILD_VOSOL_RESID;
    }
    public static int CC_CHILD_VOSOL_CHECK()
    {
        return CC_CHILD_VOSOL_CHECK;
    }
    public static int CC_VOSOL_IS_MOJAZ_FOR_RESID()
    {
        return CC_VOSOL_IS_MOJAZ_FOR_RESID;
    }
    public static int CC_VOSOL_IS_ETEBAR_CHECK_BARGASHTY()
    {
        return CC_VOSOL_IS_ETEBAR_CHECK_BARGASHTY;
    }
    public static int CC_VOSOL_IS_MOSHTARY_FOROSHANDE()
    {
        return CC_VOSOL_IS_MOSHTARY_FOROSHANDE;
    }
    public static int CC_VOSOL_IS_RESID()
    {
        return CC_VOSOL_IS_RESID;
    }
    public static int CC_VOSOL_IS_CHECK()
    {
        return CC_VOSOL_IS_CHECK;
    }
    public static int CC_VOSOL_IS_VAJH_NAGHD()
    {
        return CC_VOSOL_IS_VAJH_NAGHD;
    }
    public static String NOE_VOSOL_MOSHTARY_RESID_NAGHD()
    {
        return NOE_VOSOL_MOSHTARY_RESID_NAGHD;
    }
    public static String NOE_VOSOL_MOSHTARY_JADID()
    {
        return NOE_VOSOL_MOSHTARY_JADID;
    }
    public static String NOE_VOSOL_MOSHTARY_JADID_KHORDE_OMDE() {
        return NOE_VOSOL_MOSHTARY_JADID_KHORDE_OMDE;
    }
    public static String NOE_HAML() {
        return NOE_HAML;
    }
    public static String NOE_HAML_MOSHTARY_JADID() {
        return NOE_HAML_MOSHTARY_JADID;
    }
    public static String SHART_BARDASHT() {
        return SHART_BARDASHT;
    }
    public static int CC_EDIT_CUSTOMER_ITEMS() {
        return CC_EDIT_CUSTOMER_ITEMS;
    }
    public static int CC_CHILD_CAN_UPDATE_CUSTOMER() {
        return CC_CHILD_CAN_UPDATE_CUSTOMER;
    }
    public static String CAN_UPDATE_NATIONAL_CODE() {
        return CAN_UPDATE_NATIONAL_CODE;
    }
    public static String CAN_UPDATE_NOE_FAALIAT() {
        return CAN_UPDATE_NOE_FAALIAT;
    }
    public static String CAN_UPDATE_NOE_SENF() {
        return CAN_UPDATE_NOE_SENF;
    }
    public static String CAN_UPDATE_ANBAR() {
        return CAN_UPDATE_ANBAR;
    }
    public static String CAN_UPDATE_MASAHAT_MAGHAZE() {
        return CAN_UPDATE_MASAHAT_MAGHAZE;
    }
    public static String CAN_UPDATE_SAATE_VISIT() {
        return CAN_UPDATE_SAATE_VISIT;
    }
    public static String CAN_UPDATE_MOBILE() {
        return CAN_UPDATE_MOBILE;
    }
    public static String CAN_UPDATE_ADDRESS() {
        return CAN_UPDATE_ADDRESS;
    }
    public static int CC_MOJOODI_GIRI_CONFIG() {
        return CC_MOJOODI_GIRI_CONFIG;
    }
    public static int CC_BARKHORD_AVALIE_CONFIG() {
        return CC_BARKHORD_AVALIE_CONFIG;
    }
    public static String SHOW() {
        return SHOW;
    }
    public static String FORCE() {
        return FORCE;
    }
    public static String MULTIPLE() {
        return MULTIPLE;
    }
    public static int CC_CODE_NOE_VOSOL_MPSHTARY_RESID() {
        return CC_CODE_NOE_VOSOL_MPSHTARY_RESID;
    }
    public static int CC_PARAMETER_CODE_NOE_VOSOL_MOSHTARY() {
        return CC_PARAMETER_CODE_NOE_VOSOL_MOSHTARY;
    }
    public static int CC_BE_MASOLIAT_FOROSH() {
        return CC_BE_MASOLIAT_FOROSH;
    }
    public static int CC_NOE_HAML_ADDI() {
        return CC_NOE_HAML_ADDI;
    }
    public static int CC_VAZEIAT_FAKTOR_TEMP() {
        return CC_VAZEIAT_FAKTOR_TEMP;
    }
    public static int CC_VOSOL_NAGHD() {
        return CC_VOSOL_NAGHD;
    }
    public static int CC_VOSOL_CHECK() {
        return CC_VOSOL_CHECK;
    }
    public static int CC_VOSOL_RESID() {
        return CC_VOSOL_RESID;
    }
    public static String CC_IRAN_CHECK() {
        return CC_IRAN_CHECK;
    }
    public static String VALUE_VAJH_NAGHD() {
        return VALUE_VAJH_NAGHD;
    }
    public static String VALUE_VAJH_NAGHD_YEK_SETARE() {
        return VALUE_VAJH_NAGHD_YEK_SETARE;
    }
    public static String VALUE_VAJH_NAGHD_DO_SETARE() {
        return VALUE_VAJH_NAGHD_DO_SETARE;
    }
    public static String VALUE_POS() {
        return VALUE_POS;
    }
    public static String VALUE_IRANCHECK() {
        return VALUE_IRANCHECK;
    }
    public static String VALUE_FISH_BANKI() {
        return VALUE_FISH_BANKI;
    }
    public static String VALUE_CHECK() {
        return VALUE_CHECK;
    }
    public static String VALUE_RESID() {
        return VALUE_RESID;
    }
    public static String VALUE_MARJOEE() {
        return VALUE_MARJOEE;
    }
    public static String CC_CODE_NOE_SANAD()
    {
        return CC_CODE_NOE_SANAD;
    }
    public static String VALUE_SANAD_IRAN_CHECK()
    {
        return VALUE_SANAD_IRAN_CHECK;
    }
    public static String VALUE_SANAD_CHECK_BANKI()
    {
        return VALUE_SANAD_CHECK_BANKI;
    }
    public static String VALUE_SANAD_POS()
    {
        return VALUE_SANAD_POS;
    }
    public static String VALUE_SANAD_RESID()
    {
        return VALUE_SANAD_RESID;
    }
    public static String CC_PARAMETER_TAEED_DARKHAST_CONFIG()
    {
        return CC_PARAMETER_TAEED_DARKHAST_CONFIG;
    }
    public static int CC_TAEED_DARKHAST_SHOW_BTN_MARJOEE()
    {
        return CC_TAEED_DARKHAST_SHOW_BTN_MARJOEE;
    }
    public static String CC_PARAMETER_GOROH_MOSHTARY()
    {
        return CC_PARAMETER_GOROH_MOSHTARY;
    }
    public static int CC_CHILD_GOROH_MOSHTARY_KHORDE()
    {
        return CC_CHILD_GOROH_MOSHTARY_KHORDE;
    }
    public static int CC_CHILD_GOROH_MOSHTARY_OMDE()
    {
        return CC_CHILD_GOROH_MOSHTARY_OMDE;
    }
    public static int CC_CHILD_GOROH_MOSHTARY_TAAVONI_VIJE()
    {
        return CC_CHILD_GOROH_MOSHTARY_TAAVONI_VIJE;
    }
    public static int CC_CHILD_GOROH_MOSHTARY_ZANJIRE()
    {
        return CC_CHILD_GOROH_MOSHTARY_ZANJIRE;
    }
    public static int CC_CHILD_GOROH_MOSHTARY_NAMAYANDE1()
    {
        return CC_CHILD_GOROH_MOSHTARY_NAMAYANDE1;
    }
    public static int CC_CHILD_GOROH_MOSHTARY_NAMAYANDE2()
    {
        return CC_CHILD_GOROH_MOSHTARY_NAMAYANDE2;
    }
    public static int CC_CHILD_GOROH_MOSHTARY_TAAVONI_KARKONAN()
    {
        return CC_CHILD_GOROH_MOSHTARY_TAAVONI_KARKONAN;
    }
    public static int CC_CHILD_CODE_TAKHFIF_SENFI()
    {
        return CC_CHILD_CODE_TAKHFIF_SENFI;
    }
    public static int CC_CHILD_CODE_TAKHFIF_NAGHDI()
    {
        return CC_CHILD_CODE_TAKHFIF_NAGHDI;
    }
    public static int CC_CHILD_CODE_TAKHFIF_JAYEZE()
    {
        return CC_CHILD_CODE_TAKHFIF_JAYEZE;
    }
    public static int CC_CHILD_CODE_TAKHFIF_HAJMI()
    {
        return CC_CHILD_CODE_TAKHFIF_HAJMI;
    }
    public static int CC_CHILD_TAKHFIF_FOR_VOSOL_NAGHD()
    {
        return CC_CHILD_TAKHFIF_FOR_VOSOL_NAGHD;
    }
    public static int CC_CHILD_TAKHFIF_FOR_VOSOL_CHECK()
    {
        return CC_CHILD_TAKHFIF_FOR_VOSOL_CHECK;
    }
    public static int CC_CHILD_TAKHFIF_FOR_VOSOL_RESID()
    {
        return CC_CHILD_TAKHFIF_FOR_VOSOL_RESID;
    }
    public static int CC_NOE_BASTE_BANDI()
    {
        return CC_NOE_BASTE_BANDI;
    }
    public static int CC_CHILD_NOE_BASTE_BANDI_CARTON()
    {
        return CC_CHILD_NOE_BASTE_BANDI_CARTON;
    }
    public static int CC_CHILD_NOE_BASTE_BANDI_BASTE()
    {
        return CC_CHILD_NOE_BASTE_BANDI_BASTE;
    }
    public static int CC_CHILD_NOE_BASTE_BANDI_ADAD()
    {
        return CC_CHILD_NOE_BASTE_BANDI_ADAD;
    }
    public static int CC_NOE_TEDAD_RIAL()
    {
        return CC_NOE_TEDAD_RIAL;
    }
    public static int CC_CHILD_NOE_TEDAD_RIAL_TEDAD()
    {
        return CC_CHILD_NOE_TEDAD_RIAL_TEDAD;
    }
    public static int CC_CHILD_NOE_TEDAD_RIAL_RIAL()
    {
        return CC_CHILD_NOE_TEDAD_RIAL_RIAL;
    }
    public static int CC_CHILD_TELEPHONE_BAZRESI()
    {
        return CC_CHILD_TELEPHONE_BAZRESI;
    }
    public static int CC_CHILD_CODE_NOE_VOSOL()
    {
        return CC_CHILD_CODE_NOE_VOSOL;
    }
    public static int CC_CHILD_CODE_NOE_VOSOL_MARJOEE()
    {
        return CC_CHILD_CODE_NOE_VOSOL_MARJOEE;
    }
    public static int CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD()
    {
        return CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD;
    }
    public static int CC_PARAMETER_PRINT_CONFIG()
    {
        return CC_PARAMETER_PRINT_CONFIG;
    }
    public static int CC_CHILD_PRINT_SHOW_ARZESH_AFZOODE()
    {
        return CC_CHILD_PRINT_SHOW_ARZESH_AFZOODE;
    }
    public static int CC_CHILD_NOT_CHECK_KHAREJ_AZ_MAHAL()
    {
        return CC_CHILD_NOT_CHECK_KHAREJ_AZ_MAHAL;
    }
    public static int CC_DOWNLOAD_URL()
    {
        return CC_DOWNLOAD_URL;
    }
    public static int CC_CHILD_DOWNLOAD_URL_ASLI()
    {
        return CC_CHILD_DOWNLOAD_URL_ASLI;
    }
    public static int CC_CHILD_DOWNLOAD_URL_AMOOZESH()
    {
        return CC_CHILD_DOWNLOAD_URL_AMOOZESH;
    }
    public static int CC_CHILD_DOWNLOAD_URL_TEST()
    {
        return CC_CHILD_DOWNLOAD_URL_TEST;
    }
    public static int CC_CHILD_DOWNLOAD_URL_AZMAYESHI()
    {
        return CC_CHILD_DOWNLOAD_URL_AZMAYESHI;
    }
    public static int CC_CHILD_FORCE_WIFI_ON_FOR_GPS()
    {
        return CC_CHILD_FORCE_WIFI_ON_FOR_GPS;
    }
    public static int CC_CHILD_PARAMETER_NOE_FAALIAT_MOSHTARY_JADID()
    {
        return CC_CHILD_PARAMETER_NOE_FAALIAT_MOSHTARY_JADID;
    }
    public static int CC_CHILD_CAN_INSERT_FAKTOR_MOSHTARY_JADID()
    {
        return CC_CHILD_CAN_INSERT_FAKTOR_MOSHTARY_JADID;
    }
    public static int CC_CHILD_MAX_DIFF_TAKHFIF_TITR_BY_SATR()
    {
        return CC_CHILD_MAX_DIFF_TAKHFIF_TITR_BY_SATR;
    }
    public static int CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST()
    {
        return CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST;
    }
    public static int CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST()
    {
        return CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST;
    }
    public static int CC_CHILD_UNINSTALL_INVALID_PACKAGE()
    {
        return CC_CHILD_UNINSTALL_INVALID_PACKAGE;
    }
    public static int CC_CHILD_CHECK_LOCATION_IN_POLYGON_FOR_NEW_CUSTOMER()
    {
        return CC_CHILD_CHECK_LOCATION_IN_POLYGON_FOR_NEW_CUSTOMER;
    }
    public static int CC_CHILD_CAN_INSERT_MOSHTARY_JADID()
    {
        return CC_CHILD_CAN_INSERT_MOSHTARY_JADID;
    }
    public static int CC_CHILD_ENABLE_GET_MESSAGE()
    {
        return CC_CHILD_ENABLE_GET_MESSAGE;
    }
    public static int CC_CHILD_IS_SEND_KALA_MOJODI()
    {
        return CC_CHILD_IS_SEND_KALA_MOJODI;
    }


    ///////////////////////////// Code Vazeiat Faktor /////////////////////////////
    private static final int CODE_VAZEIAT_FAKTOR_TAEED = 2;
    private static final int CODE_VAZEIAT_FAKTOR_TAEED_MAMOR_PAKHSH = 3;
    public static final int CODE_VAZEIAT_FAKTOR_TASVIEH = 7;


    public static int CODE_VAZEIAT_FAKTOR_TAEED()
    {
        return CODE_VAZEIAT_FAKTOR_TAEED;
    }
    public static int CODE_VAZEIAT_FAKTOR_TAEED_MAMOR_PAKHSH()
    {
        return CODE_VAZEIAT_FAKTOR_TAEED_MAMOR_PAKHSH;
    }

    ///////////////////////////// Adam Darkhast Parameter /////////////////////////////
    private static final int NEED_CUSTOMER_DUPLICATED_CODE = 10;

    public static int NEED_CUSTOMER_DUPLICATED_CODE() {
        return NEED_CUSTOMER_DUPLICATED_CODE;
    }


    ///////////////////////////// Treasury list sort /////////////////////////////
    public final static int SORT_TREASURY_BY_ROUTING = 1;
    public final static int SORT_TREASURY_BY_CUSTOMER_CODE = 2;


    ///////////////////////////// Goroh Table Parameter /////////////////////////////
    private static final int CC_GOROH_NOE_FAALIAT = 304;
    private static final int CC_GOROH_NOE_SENF = 305;

    public static int CC_GOROH_NOE_FAALIAT() {
        return CC_GOROH_NOE_FAALIAT;
    }
    public static int CC_GOROH_NOE_SENF() {
        return CC_GOROH_NOE_SENF;
    }



    ///////////////////////////// NOE SABT MARJOEE /////////////////////////////
    public static final int NOE_SABT_MARJOEE_1 = 1; // مامورپخش امکان کم و زیاد کردن اقلام مرجوعی را دارد.
    public static final int NOE_SABT_MARJOEE_2 = 2; // مامورپخش فقط امکان کم کردن اقلام مرجوعی را دارد.


    ///////////////////////////// Menu Headers ID /////////////////////////////
    private static final int SALE = 12;
    private static final int VOSOL = 19;
    private static final int SALES_REPORT = 29;
    private static final int VOSOL_REPORT = 46;
    private static final int MARKETING = 52;
    private static final int SYSTEM = 56;


    /**
     * id of systemMenu table for sale item in menu
     * @return
     */
    public static int SALE()
    {
        return SALE;
    }

    /**
     * id of systemMenu table for vosol item in menu
     * @return
     */
    public static int VOSOL()
    {
        return VOSOL;
    }

    /**
     * id of systemMenu table for marketing item in menu
     * @return
     */
    public static int MARKETING()
    {
        return MARKETING;
    }

    /**
     * id of systemMenu table for sale_report item in menu
     * @return
     */
    public static int SALES_REPORT()
    {
        return SALES_REPORT;
    }

    /**
     * id of systemMenu table for vosol_report item in menu
     * @return
     */
    public static int VOSOL_REPORT()
    {
        return VOSOL_REPORT;
    }

    /**
     * id of systemMenu table for system item in menu
     * @return
     */
    public static int SYSTEM()
    {
        return SYSTEM;
    }


    ///////////////////////////// PishDaryaft /////////////////////////////
    public static int FROM_TREASURYLIST = 1;
    public static int FROM_PISH_DARYAFT = 2;
    public static int FROM_CHECK_BARGASHTI = 3;

    //////////////////////////// marjoee foroshandeh /////////////////////////
    // set Selected fragment in from fragment
    // 1 = MarjoeeForoshandeh  2 = marjoeeMoredi   3 = marjoeeKoli
    public static int SELECTED_FRAGMENT = 0;



    ///////////////////////////// map.ir key  /////////////////////////////
    public static final String MAP_IR_KEY="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImUxYTZmMzQxZWNmNWE1MGQ4Njk4MzFhM2Q2NTYzNWY4Y2ExMmFhZDgyNDUzYzdmNmQ2NDhiZTFlNmIzYjEwMGRlZmFhYzBhYjI3Yjg5ZjI3In0.eyJhdWQiOiIxMTIzNiIsImp0aSI6ImUxYTZmMzQxZWNmNWE1MGQ4Njk4MzFhM2Q2NTYzNWY4Y2ExMmFhZDgyNDUzYzdmNmQ2NDhiZTFlNmIzYjEwMGRlZmFhYzBhYjI3Yjg5ZjI3IiwiaWF0IjoxNjAzMDIxNDY2LCJuYmYiOjE2MDMwMjE0NjYsImV4cCI6MTYwNTUyNzA2Niwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.iiqMVRcc9anPpNnuV6p3TfYhImdrtq0OTyyOoGVdloScTV9ZQsl2XZl6i9khrvAOiD4UayHuOK5BjXSF6UHVyuqO4EsPwqX4UBjsgB_frQMPVjJdlrw8NtYXUv1Ylzd_PBRuVPozmQRTv9223TG7lzqgd2pYhzR1IRO0zAycGLV5CfOCOCt5EDzTcCv91vau32qIIh4y6sn8K_JMiM5gjkDeVnKtJf5W1JfsoF4RSGWjwlbdUjPw-5BwWyPQSvKtSgpliqqf0Z8BZYPQy6MoPGf1ykn5lHGeAlf2XprHhXx66JubdAzJmUasef2yNEncVdhrVN4hyDiGaGDQAO68UQ";


    public static final int MAP_IR= 2;
    public static final int MAP_VALHALA =1;


    public static final String MAP_TYPE="MapType";


    ///////////////////////////// NUMBER OF ITEMS TO BE DISPLAYED IN KALA LIST  /////////////////////////////
    public static final int SINGLE_ITEM=1;
    public static final int DOUBLE_ITEM=2;
    public static final int FOUR_ITEM=3;


    //////////////////////////// For Type WebService ///////////////////////
    public static final int GET_REQUESTS = 1;
    public static final int POST_REQUEST = 2;
    public static final int MULTI_REQUEST = 3;

    //////////////////////////// For GPS Service Notification ///////////////////////
    private static final String GPS_CHANNEL_ID ="GPS_CHANNEL_ID";
    private static final String MAIN_CHANNEL_ID ="MAIN_CHANNEL_ID";
    public static final String  GPS_CHANNEL_ID() {
        return GPS_CHANNEL_ID;
    }
    public static final String MAIN_CHANNEL_ID(){
        return MAIN_CHANNEL_ID;
    }

    /////////////////////////// Authentication /////////////////////

    public static final int MAX_LENGTH_STRING_FILE = 15;
    public static final String SYSTEM_INFO = "systemInfo";
    public static final String File_Path ="Auth/Data";

    ///////////////////////////// Code Noe Vorod Vosol /////////////////////////////////
    public static final int CODE_NOE_VOROD_VOSOL_FAKTOR = 1;
    public static final int CODE_NOE_VOROD_VOSOL_BARGASHTY = 3;
    public static final int CODE_NOE_VOROD_PISHDARYAFT = 5;


    //Noe Vosol TreasuryList
    public static final int VOSOL_FAKTOR_ROOZ = 0;
    public static final int VOSOL_MANDE_DAR = 1;

}

