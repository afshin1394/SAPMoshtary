package com.saphamrah.BaseMVP;


import android.content.Context;

import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;

import java.util.ArrayList;

public interface MandehdarZangireiMVP {

    interface RequiredViewOps
    {
        Context getAppContext();
        void closeLoadingDialog();
        void showToast(int resId, int messageType, int duration);
        void onGetListMandehDar(ArrayList<RptMandehdarModel> mandehdarModels);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(MandehdarZangireiMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getListMandehDar();
        void updateListMandehDar();
        void getDetails(RptMandehdarModel model);

    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetListMandehDar(ArrayList<RptMandehdarModel> mandehdarModels);
        void onUpdateData();
        void failedUpdate();
    }


    interface ModelOps
    {
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getListMandehDar();
        void updateListMandehDar();
        void getDetails(RptMandehdarModel model);
    }

}
