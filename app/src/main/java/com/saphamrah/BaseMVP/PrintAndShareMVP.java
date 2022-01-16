package com.saphamrah.BaseMVP;

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
    }


    interface ModelOps
    {
        void update();
        void getAllPrintFaktor();
        void getImagePrintFaktor(String UniqeID);

    }

}
