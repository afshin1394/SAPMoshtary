package com.saphamrah.PubFunc;

public class UnitConverter
{


    /**
     * تبدیل تعداد وارد شده به کارتن بسته عدد
     * @param tedad تعداد کالای انتخاب شده
     * @param cartonUnitCount تعداد کالا در کارتن
     * @param basteUnitCount تعداد کالا در بسته
     * @return int[] => 0 : count in carton , 1 : count in basteh , 2 : adad
     */
    public static int[] tedadToCartonBasteAdad(int tedad , int cartonUnitCount , int basteUnitCount)
    {
        int cartonCount = tedad / cartonUnitCount;
        int remain = tedad % cartonUnitCount;
        int basteCount = remain / basteUnitCount;
        int adad = remain % basteUnitCount;
        return new int[]{cartonCount , basteCount , adad};
    }



}
