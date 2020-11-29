package com.saphamrah.BaseMVP;

import android.content.Context;

public interface AddDepositCashToBankMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddDepositCashToBankMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(AddDepositCashToBankMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
