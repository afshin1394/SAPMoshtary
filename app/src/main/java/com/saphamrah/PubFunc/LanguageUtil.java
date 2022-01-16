package com.saphamrah.PubFunc;

public class LanguageUtil
{

    public String convertFaNumberToEN(String strNumber)
    {
        String[] persianNumber = new String[]{"۰","۱","۲","۳","۴","۵","۶","۷","۸","۹","ي","ئ","یٰ","ة","ك","ؤ","ء","أ","ٱ","إ","اً","هٔ"};
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
        strNumber = strNumber.replace(persianNumber[10] , "ی");
        strNumber = strNumber.replace(persianNumber[11] , "ی");
        strNumber = strNumber.replace(persianNumber[12] , "ی");
        strNumber = strNumber.replace(persianNumber[13] , "ه");
        strNumber = strNumber.replace(persianNumber[14] , "ک");
        strNumber = strNumber.replace(persianNumber[15] , "و");
        strNumber = strNumber.replace(persianNumber[16] , "ی");
        strNumber = strNumber.replace(persianNumber[17] , "ا");
        strNumber = strNumber.replace(persianNumber[18] , "ا");
        strNumber = strNumber.replace(persianNumber[19] , "ا");
        strNumber = strNumber.replace(persianNumber[20] , "ا");
        strNumber = strNumber.replace(persianNumber[21] , "ه");
        return strNumber;
    }



}
