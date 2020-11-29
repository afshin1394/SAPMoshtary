package com.saphamrah.PubFunc;

import android.content.Context;

import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.Shared.UserTypeShared;

import java.util.List;

public class UserType
{

    /**
     * این متد بررسی می کند که اگر شناسه دستگاه فعلی با یکی از شناسه های موجود در لیست فروشندگان آموزشی برابر بود، این کاربر به عنوان کاربر تست شناخته می شود.
     * @param context
     * @param foroshandehAmoozeshiModelList لیست فروشندگان آموزشی که شناسه های دستگاه های آموزشی به شناسه دستگاه های اصلی کاربران متناظر شده
     * @param deviceIMEI شناسه دستگاه کاربر فعلی
     * @return اگر کاربر اصلی سیستم باشد مقدار بازگشتی برابر "" خواهد بود، اگر کاربر تستی باشد مقدار بازگشتی شناسه دستگاه اصلی متناظر خواهد بود.
     */
    public String checkUserType(Context context , List<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModelList , String deviceIMEI)
    {
        String foroshandehAsliIMEI = ""; //if this parameter == "" then UserType = main and use deviceIMEI else UserType = test and use this IMEI
        for (ForoshandehAmoozeshiModel foroshandehAmoozeshi : foroshandehAmoozeshiModelList)
        {
            if (foroshandehAmoozeshi.getDeviceNumberForoshandehAmoozeshi().trim().equals(deviceIMEI))
            {
                foroshandehAsliIMEI = foroshandehAmoozeshi.getDeviceNumberForoshandehAsli();
                break;
            }
        }

        String usingIMEI = "";
        int isTest = 0;
        if (foroshandehAsliIMEI.trim().length() != 0)
        {
            usingIMEI = foroshandehAsliIMEI;
            isTest = 1;
        }
        else
        {
            usingIMEI = deviceIMEI;
        }

        //usingIMEI = deviceIMEI;
        //isTest = 0;

        UserTypeShared userTypeShared = new UserTypeShared(context);
        userTypeShared.putString(userTypeShared.USING_IMEI() , usingIMEI);
        userTypeShared.putInt(userTypeShared.USER_TYPE() , isTest);

        return foroshandehAsliIMEI;
    }

}
