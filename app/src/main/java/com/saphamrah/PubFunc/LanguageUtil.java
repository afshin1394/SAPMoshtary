package com.saphamrah.PubFunc;

public class LanguageUtil
{

    public String convertFaNumberToEN(String strNumber)
    {
        String[] persianNumber = new String[]{"۰","۱","۲","۳","۴","۵","۶","۷","۸","۹"};
        strNumber = strNumber.replace(persianNumber[0] , "0");
        strNumber = strNumber.replace(persianNumber[1] , "1");
        strNumber = strNumber.replace(persianNumber[2] , "2");
        strNumber = strNumber.replace(persianNumber[3] , "3");
        strNumber = strNumber.replace(persianNumber[4] , "4");
        strNumber = strNumber.replace(persianNumber[5] , "5");
        strNumber = strNumber.replace(persianNumber[6] , "6");
        strNumber = strNumber.replace(persianNumber[7] , "7");
        strNumber = strNumber.replace(persianNumber[8] , "8");
        strNumber = strNumber.replace(persianNumber[9] , "9");
        return strNumber;
    }



}
