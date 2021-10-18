package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.MVP.View.MoshtaryChidmanActivity;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MoshtaryChidmanModel;

import java.util.ArrayList;
import java.util.Date;

public interface MoshtaryChidmanMVP {
    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetMoshtaryChidman(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels);
        void onInsertMoshtaryChidman(MoshtaryChidmanModel moshtaryChidmanModel);
        void showToast(int resId, int messageType , int duration);
        void closeLoading();
        void showAlertDialog(int resId, int messageType);
        void showLoadingDialog();
        void onDeleteMoshtaryChidman();
        void onUpdateMoshtaryChidman();
        void onSendMoshtaryChidman();
        void closeActivity();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddCustomerListMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void insertMoshtaryChidman(MoshtaryChidmanModel moshtaryChidmanModel);
        void getMoshtaryChidman();
        void onDestroy(boolean isChangingConfig);
        void deleteMoshtaryChidman(int ccMoshtaryChidman);
        void updateMoshtaryChidman(MoshtaryChidmanModel moshtaryChidmanModel);
        void sendMoshtaryChidmans(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels);
        void checkNotSendMoshtaryChidman(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetMoshtaryChidman(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels);
        void onInsertMoshtaryChidman(MoshtaryChidmanModel moshtaryChidmanModel);
        void onSuccess(int resId);
        void onError(int resId);
        void onGetConfig(boolean canInsertNewCustomer);
        void onDeleteMoshtaryChidman();
        void onUpdateMoshtaryChidman();
        void onSendMoshtaryChidmans();
    }


    interface ModelOps
    {
        void getConfig();
        void getMoshtaryChidman();
        void insertMoshtaryChidman(MoshtaryChidmanModel moshtaryChidmanModel);
        void updateMoshtaryChidman(MoshtaryChidmanModel moshtaryChidmanModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void sendMoshtaryChidmans(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels);

    }

}
