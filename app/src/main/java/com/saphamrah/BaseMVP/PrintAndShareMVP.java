package com.saphamrah.BaseMVP;

import android.widget.RelativeLayout;

import com.saphamrah.Model.PrintFaktorModel;

import java.util.ArrayList;


public interface PrintAndShareMVP
{

    interface RequiredViewOps
    {

        void showToast(int resId, int messageType , int duration);
        void onGetAllPrintFaktor(ArrayList<PrintFaktorModel> modelArrayList);
        void onGetImagePrintFaktor(PrintFaktorModel model);
        void closeLoadingDialog();
        void onGetPrintfaktor(String uniqueID,int action,byte[] image);
        void showLoading();

    }


    interface PresenterOps
    {

        void onConfigurationChanged(PrintAndShareMVP.RequiredViewOps view);
        void update();
        void getAllPrintFaktor();
        void getImagePrintFaktor(String UniqeID);

    }


    interface RequiredPresenterOps
    {
        void onGetAllPrintFaktor(ArrayList<PrintFaktorModel> modelArrayList);
        void onGetImagePrintFaktor(PrintFaktorModel model);
        void onUpdateData();
        void failedUpdate();
        void onGetPrintFaktor(String uniqueId,int action,byte[] decode);
        void onError(int errorGetImageWithUniqID);
        void onWarning(int faktorImageNotAvailable);
    }


    interface ModelOps
    {
        void update();
        void getAllPrintFaktor();
        void getImagePrintFaktor(String UniqeID);
        void getFaktorImage(int action, PrintFaktorModel printFaktorModel);
        void Destroy();
    }

}
