package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;

import java.util.ArrayList;

public interface RptForoshandehVisitMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void setAdapter(ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels, RptVisitForoshandehMoshtaryModel rptVisitForoshandehMoshtarySum);
        void emptyList();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void getVisitList();
        void updateReport();
        void onConfigurationChanged(RptForoshandehVisitMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptForoshandehVisitMVP.RequiredViewOps view);
        void onGetVisitList(ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels);
        void onSuccessUpdate();
        void onFailedUpdate();
    }


    interface ModelOps
    {
        void getVisitList();
        void updateReport();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
