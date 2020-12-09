package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.MVP.Model.RptMoshtarianKharidNakardehModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.RptMoshtaryKharidNakardeModel;

import java.util.ArrayList;

public interface RptMoshtarianKharidNakardeMVP {
    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetListMoshtarianKharidNakarde(ArrayList<RptMoshtaryKharidNakardeModel> rptMoshtaryKharidNakardeModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType, int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getListMoshtarianKharidNakarde();
        void updateListMoshtarianKharidNakarde();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RequiredViewOps view);
        void onGetListMoshtarianKharidNakarde(ArrayList<RptMoshtaryKharidNakardeModel> rptMoshtaryKharidNakardeModels);
        void onSuccessUpdateListMandehDar();
        void onErrorUpdateListMandehDar(String Type);
    }


    interface ModelOps
    {
        void getListMoshtarianKharidNakarde();
        void updateListMoshtarianKharidNakarde();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }
}
