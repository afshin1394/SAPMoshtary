package com.saphamrah.BaseMVP.MainViewPagerMVP;

import android.content.Context;

import com.saphamrah.Model.RptForoshModel;

import java.util.ArrayList;

public interface MablaghTedadForoshFragmentsBaseMVP  {
    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetAmarForosh(ArrayList<RptForoshModel> rptForoshModels);

    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getAmarForosh();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RequiredViewOps view);
        void onGetAmarForosh(ArrayList<RptForoshModel> rptForoshModels);

    }


    interface ModelOps
    {
        void getAmarForosh();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
