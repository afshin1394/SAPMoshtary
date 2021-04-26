package com.saphamrah.BaseMVP;

import com.saphamrah.Model.BargashtyModel;

import java.util.ArrayList;

public interface CheckBargashtiMVP
{


    interface RequiredViewOps
    {
        void onGetAllCheckBargashti(ArrayList<BargashtyModel> bargashtyModels);
        void showToast(int resId, int messageType, int duration);
        void showAlertMessage(int resId, int messageType);
        void closeLoadingDialog();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(CheckBargashtiMVP.RequiredViewOps view);
        void onGetAllCheckBargashti();
        void getDariaftPardakhtForSend(long ccDarkhastFaktor  , int position);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void updateListBargashty();

    }


    interface RequiredPresenterOps
    {
        void onGetAllCheckBargashti(ArrayList<BargashtyModel> checkBargashtiModels);
        void onErrorSend(int resId);
        void onSuccessSend(int position);
        void onErrorUpdateListBargashty();
    }


    interface ModelOps
    {
        void updateListBargashty();
        void getAllCheckBargashti();
        void getDariaftPardakhtForSend(long ccDarkhastFaktor , int position);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);

    }

}
