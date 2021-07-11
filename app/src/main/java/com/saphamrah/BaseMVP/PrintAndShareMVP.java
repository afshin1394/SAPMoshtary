package com.saphamrah.BaseMVP;

import com.saphamrah.Model.PrintFaktorModel;

import java.util.ArrayList;


public interface PrintAndShareMVP
{

    interface RequiredViewOps
    {

        void showToast(int resId, int messageType , int duration);
        void onGetAllPrintFaktor(ArrayList<PrintFaktorModel> modelArrayList);
        void closeLoadingDialog();
    }


    interface PresenterOps
    {

        void onConfigurationChanged(PrintAndShareMVP.RequiredViewOps view);
        void update();
        void getAllPrintFaktor();

    }


    interface RequiredPresenterOps
    {
        void onGetAllPrintFaktor(ArrayList<PrintFaktorModel> modelArrayList);
        void onUpdateData();
        void failedUpdate();
    }


    interface ModelOps
    {
        void update();
        void getAllPrintFaktor();

    }

}
