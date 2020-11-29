package com.saphamrah.PubFunc;

import com.saphamrah.Model.CheckInfo;

import java.util.HashMap;

public class BankUtils
{

    private static HashMap<String , String> hashMapCountryCode = new HashMap<>();

    static
    {
        hashMapCountryCode.put("A" , "10");
        hashMapCountryCode.put("B" , "11");
        hashMapCountryCode.put("C" , "12");
        hashMapCountryCode.put("D" , "13");
        hashMapCountryCode.put("E" , "14");
        hashMapCountryCode.put("F" , "15");
        hashMapCountryCode.put("G" , "16");
        hashMapCountryCode.put("H" , "17");
        hashMapCountryCode.put("I" , "18");
        hashMapCountryCode.put("J" , "19");
        hashMapCountryCode.put("K" , "20");
        hashMapCountryCode.put("L" , "21");
        hashMapCountryCode.put("M" , "22");
        hashMapCountryCode.put("N" , "23");
        hashMapCountryCode.put("O" , "24");
        hashMapCountryCode.put("P" , "25");
        hashMapCountryCode.put("Q" , "26");
        hashMapCountryCode.put("R" , "27");
        hashMapCountryCode.put("S" , "28");
        hashMapCountryCode.put("T" , "29");
        hashMapCountryCode.put("U" , "30");
        hashMapCountryCode.put("V" , "31");
        hashMapCountryCode.put("W" , "32");
        hashMapCountryCode.put("X" , "33");
        hashMapCountryCode.put("Y" , "34");
        hashMapCountryCode.put("Z" , "35");
    }


    public static boolean checkIBAN(String IBAN)
    {
        try
        {
            if (IBAN == null || IBAN.length() != 26 || !IBAN.toUpperCase().startsWith("IR"))
            {
                return false;
            }
            else
            {
                // get two char from first that represent countryCode like "IR"
                String firstItemOfCountryCode = String.valueOf(IBAN.charAt(0));
                String secondItemOfCountryCode = String.valueOf(IBAN.charAt(1));
                // convert countryCode to number like "IR" -> 1827
                String numberOfCountryCode = hashMapCountryCode.get(firstItemOfCountryCode.toUpperCase()) + hashMapCountryCode.get(secondItemOfCountryCode.toUpperCase());
                // get checkSum and bankCode and bankAccountNumber
                String checkSum = IBAN.substring(2, 4);
                String bankAccountInfo = IBAN.substring(4);
                // add number of CountryCode and checksum to end of bankAccountInfo(bankCode + bankAccountNumber)
                String newIBAN = bankAccountInfo + numberOfCountryCode + checkSum;

                // بخاطر بزرگ بودن عدد، ابتدا 9 کاراکتر اول را به 97 تقسیم میکنیم، سپس باقیمانده را به 7 رقم بعدی می چسبانیم و دوباره بر 97 تقسیم میکنیم و همین روند تا تمام شدن رشته ادامه میدهیم
                String newIbanSection = newIBAN.substring(0, 10);
                long remain = (Long.valueOf(newIbanSection)%97);
                int startPosition = 10;
                while (startPosition < newIBAN.length())
                {
                    int endPosition = startPosition + 8;

                    if (endPosition < newIBAN.length())
                    {
                        newIbanSection = remain + newIBAN.substring(startPosition, endPosition);
                        remain = (Long.valueOf(newIbanSection)%97);
                    }
                    else
                    {
                        newIbanSection = remain + newIBAN.substring(startPosition);
                        remain = (Long.valueOf(newIbanSection)%97);
                    }
                    startPosition = endPosition;
                }
                return remain == 1;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * دریافت شماره حساب بر اساس شماره شبا که از کاراکتر 7 ام تا انتها برابر با شماره حساب است.
     * @param IBAN شماره شبا 26 کاراکتری
     * @return شماره حساب
     */
    public static String getBankAccountNumber(String IBAN)
    {
        if (IBAN != null && IBAN.trim().length() == 26)
        {
            return IBAN.substring(7);
        }
        else
        {
            return "";
        }
    }


    /**
     * کد بانک که در شماره شبا وجود دارد برگردانده می شود.
     * @param IBAN شماره شبا 26 کارکتری
     * @return کد بانک
     */
    public static String getBankCode(String IBAN)
    {
        String bankCode = "";
        if (IBAN != null && IBAN.trim().length() == 26)
        {
            bankCode = IBAN.substring(4, 7);
        }
        return bankCode.trim();
    }


    /**
     * دریافت کد شعبه از رشته شامل کدبانک و کد شعبه که بر اساس یک کاراکتر خاص جدا شده.
     * کاراکتر خاص ممکن است _ یا - یا موارد دیگر باشد.
     * @param branchBankCode رشته شامل کدبانک و کدشعبه
     * @return کدشعبه
     */
    public static String getBranchCode(String branchBankCode)
    {
        if (branchBankCode != null && branchBankCode.trim().length() > 0)
        {
            String[] splittedBranchBankCode;
            if (branchBankCode.contains("_"))
            {
                splittedBranchBankCode = branchBankCode.split("_");
            }
            else if (branchBankCode.contains("-"))
            {
                splittedBranchBankCode = branchBankCode.split("-");
            }
            else
            {
                splittedBranchBankCode = null;
            }


            if (splittedBranchBankCode != null && splittedBranchBankCode.length >= 2)
            {
                return splittedBranchBankCode[1];
            }
            else
            {
                return "";
            }
        }
        else
        {
            return "";
        }
    }


    private CheckInfo parseCheckInfo(String rawCheckInfo , HashMap<String,String> hashMapBanks)
    {
        CheckInfo checkInfo = new CheckInfo();
        String[] splittedRawCheckInfo = rawCheckInfo.split("\n");
        for (String str : splittedRawCheckInfo)
        {
            if (str.toUpperCase().startsWith("IR"))
            {
                String bankCode = getBankCode(str).trim();
                if (bankCode.equals("011")) //صنعت و معدن
                {

                }
                else if (bankCode.equals("012")) // ملت
                {
                    checkInfo = parseMellatBank(rawCheckInfo);
                }
                else if (bankCode.equals("013")) // رفاه کارگران
                {
                    checkInfo = parseRefahKargaranBank(rawCheckInfo);
                }
                else if (bankCode.equals("014")) // مسکن
                {

                }
                else if (bankCode.equals("015")) // سپه
                {

                }
                else if (bankCode.equals("016")) // کشاورزی
                {

                }
                else if (bankCode.equals("017")) // ملی
                {
                    checkInfo = parseMelliBank(rawCheckInfo);
                }
                else if (bankCode.equals("018")) // تجارت
                {

                }
                else if (bankCode.equals("019")) // صادرات
                {

                }
                else if (bankCode.equals("020")) // توسعه صادرات
                {

                }
                else if (bankCode.equals("021")) // پست بانک
                {

                }
                else if (bankCode.equals("022")) // توسعه تعاون
                {

                }
                else if (bankCode.equals("052")) // قوامین
                {

                }
                else if (bankCode.equals("053")) // کارآفرین
                {

                }
                else if (bankCode.equals("054")) // پارسیان
                {

                }
                else if (bankCode.equals("055")) // اقتصاد نوین
                {

                }
                else if (bankCode.equals("056")) // سامان
                {

                }
                else if (bankCode.equals("057")) // پاسارگاد
                {

                }
                else if (bankCode.equals("058")) // سرمایه
                {

                }
                else if (bankCode.equals("059")) // مالی بنیاد (سینا)
                {

                }
                else if (bankCode.equals("060")) // قرض الحسنه مهر ایران
                {

                }
                else if (bankCode.equals("061")) // شهر
                {

                }
                else if (bankCode.equals("062")) // تات (آینده)
                {

                }
                else if (bankCode.equals("063")) // انصار
                {

                }
                else if (bankCode.equals("064")) // گردشگری
                {

                }
                else if (bankCode.equals("065")) // حکمت ایرانیان
                {

                }
                else if (bankCode.equals("066")) // دی
                {

                }
                else if (bankCode.equals("069")) // ایران زمین
                {

                }
                else if (bankCode.equals("070")) // قرض الحسنه رسالت
                {

                }
                else if (bankCode.equals("078")) // خاورمیانه
                {

                }
                break;
            }
        }
        return checkInfo;
    }


    /*private CheckInfo parseSanaatMadan(String rawData)
    {
        CheckInfo checkInfo = new CheckInfo();

    }*/

    private CheckInfo parseMellatBank(String rawData)
    {
        CheckInfo checkInfo = new CheckInfo();
        try
        {
            String[] splittedRawData = rawData.split("\n");
            if (splittedRawData.length == 5)
            {
                checkInfo.setNoeShakhsiat(Integer.parseInt(splittedRawData[0]));
                checkInfo.setIban(splittedRawData[2]);
                checkInfo.setSayyadId(splittedRawData[4]);
                String[] splittedBankInfo = splittedRawData[3].split("_");
                if (splittedBankInfo.length > 1)
                {
                    checkInfo.setBranchCode(splittedBankInfo[0]);
                }
            }
        }
        catch (Exception e){e.printStackTrace();}
        return checkInfo;
    }

    private CheckInfo parseRefahKargaranBank(String rawData)
    {
        CheckInfo checkInfo = new CheckInfo();
        try
        {
            String[] splittedRawData = rawData.split("\n");
            if (splittedRawData.length == 4)
            {
                checkInfo.setNameNoeShakhsiat(splittedRawData[0]);
                checkInfo.setIban(splittedRawData[2]);
                checkInfo.setSayyadId(splittedRawData[3]);
                String[] splittedSahebHesabInfo = splittedRawData[1].split(" ");
                String nameSahebHesab = "";
                if (splittedSahebHesabInfo.length > 1)
                {
                    for(int i = 1 ; i < splittedSahebHesabInfo.length ; i++)
                    {
                        nameSahebHesab += splittedSahebHesabInfo[i];
                    }
                }
                checkInfo.setNameSahebHesab(nameSahebHesab);
            }
        }
        catch (Exception e){e.printStackTrace();}
        return checkInfo;
    }


    private CheckInfo parseMelliBank(String rawData)
    {
        CheckInfo checkInfo = new CheckInfo();
        try
        {
            String[] splittedRawData = rawData.split("\n");
            if (splittedRawData.length == 6)
            {
                checkInfo.setNoeShakhsiat(Integer.parseInt(splittedRawData[0]));
                checkInfo.setIban(splittedRawData[2]);
                checkInfo.setSayyadId(splittedRawData[4]);
                checkInfo.setCheckNumber(splittedRawData[5]);
                String[] splittedSahebHesabInfo = splittedRawData[1].split("_");
                if (splittedSahebHesabInfo.length > 1)
                {
                    checkInfo.setNameSahebHesab(splittedSahebHesabInfo[1]);
                }
                String[] splittedBankInfo = splittedRawData[3].split("_");
                if (splittedBankInfo.length > 1)
                {
                    checkInfo.setBranchCode(splittedBankInfo[0]);
                }
            }
        }
        catch (Exception e){e.printStackTrace();}
        return checkInfo;
    }




}
