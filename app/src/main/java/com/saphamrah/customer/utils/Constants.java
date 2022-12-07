package com.saphamrah.customer.utils;

public class Constants {

    /*filter&sort*/
    public static final int SORT = 1;
    public static final int FILTER_LIST = 2;
    public static final int FILTER_SLIDER = 3;
    public static final int FILTER_DATE_PICKER = 4;

    /*productType*/
    public static final int ADVERTISEMENT = 1;
    public static final int SELL = 2;

    public static final int SUCCESSFUL = 1;
    public static final int FAILED = 2;


    public enum PaymentStates {
        SHOW_PRODUCTS, CALCULATE_BONUS_DISCOUNT, ADD_RETURNED, SELECTABLE_BONUS ,CONFIRM_REQUEST, PISH_FAKTOR, SAVE_REQUEST
    }
}
