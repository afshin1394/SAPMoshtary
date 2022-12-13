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


    // brand

    public static final int DELPAZIR = 101;
    public static final int MIHAN = 102;
    public static final int LINA = 103;
    public static final int MAHRAM = 104;
    public static final int KALLEH = 105;


    // Sort
    public static final int MAX_CONSUMER_PRICE = 1001;
    public static final int MIN_CONSUMER_PRICE = 1002;
    public static final int MIN_SELL_PRICE = 1003;
    public static final int MAX_SELL_PRICE = 1004;




    public enum PaymentStates {
        SHOW_PRODUCTS, CALCULATE_BONUS_DISCOUNT, ADD_RETURNED, SELECTABLE_BONUS ,CONFIRM_REQUEST, PISH_FAKTOR, SAVE_REQUEST
    }
}
