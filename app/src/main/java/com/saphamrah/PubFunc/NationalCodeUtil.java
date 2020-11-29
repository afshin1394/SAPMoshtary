package com.saphamrah.PubFunc;

public class NationalCodeUtil
{


    private final String[] notNationalCode = new String[]{
            "0000000000",
            "1111111111",
            "2222222222",
            "3333333333",
            "4444444444",
            "5555555555",
            "6666666666",
            "7777777777",
            "8888888888",
            "9999999999",
    };

    public boolean checkNationalCode(String nationalCode)
    {
        if (nationalCode == null)
        {
            return false;
        }
        if (nationalCode.isEmpty())
        {
            return false;
        }
        if (nationalCode.length() != 10)
        {
            return false;
        }
        if (!nationalCode.matches("[0-9]+"))
        {
            return false;
        }

        for (String s : notNationalCode)
        {
            if (s.equalsIgnoreCase(nationalCode))
            {
                return false;
            }
        }

        String nationalCodeWithoutControlDigit = nationalCode.substring(0, nationalCode.length() - 1);
        String controlDigit = nationalCode.substring(nationalCode.length() - 1);
        int sum = 0;
        int i = 10;
        for (char c : nationalCodeWithoutControlDigit.toCharArray())
        {
            int temp = Integer.parseInt("" + c) * i;
            i--;
            sum += temp;
        }
        int modBy11 = sum % 11;
        if (modBy11 < 2)
        {
            if (modBy11 == Integer.parseInt(controlDigit))
            {
                return true;
            }
        }
        else if (11 - modBy11 == Integer.parseInt(controlDigit))
        {
            return true;
        }
        return false;
    }

    public boolean checkNationalEconomicalCode(String nationalCode)
    {
        if (nationalCode == null)
        {
            return false;
        }
        if (nationalCode.isEmpty())
        {
            return false;
        }
        if (nationalCode.length() != 11)
        {
            return false;
        }
        if (!nationalCode.matches("[0-9]+"))
        {
            return false;
        }
        String nationalCodeWithoutControlDigit = nationalCode.substring(0, nationalCode.length() - 1);
        String controlDigit = nationalCode.substring(nationalCode.length() - 1);
        String deci = nationalCode.substring(nationalCode.length() - 2, nationalCode.length() - 1);
        int decimal = Integer.parseInt(deci) + 2;
        int[] multiplier = new int[]{29, 27, 23, 19, 17, 29, 27, 23, 19, 17};
        int sum = 0;
        int i = 0;
        for (char c : nationalCodeWithoutControlDigit.toCharArray())
        {
            int temp = (Integer.parseInt("" + c) + decimal) * multiplier[i];
            i++;
            sum += temp;
        }
        int modBy11 = sum % 11;
        if (modBy11 == 10)
        {
            modBy11 = 0;
        }

        if (modBy11 == Integer.parseInt(controlDigit))
        {
            return true;
        }

        return false;
    }

}
