package com.saphamrah.BaseMVP.RptThreeMonth;

import android.content.Context;

import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;

import java.util.ArrayList;

public interface RptThreeMonthMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setListAdapter(ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels , int sumTedad , long sumMablaghFaktor);
        void hideFooter();
        void filterListAdapter(ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels,int sumTedad,long sumMablaghFaktor);
        void showToast(int resId, int messageType , int duration);
        void closeLoadingDialog();
        void failedUpdate(String type, String error);


    }


    interface PresenterOps
    {
        void getList();
        void getListFilteredByName(ArrayList<Rpt3MonthGetSumModel> rpt3MonthGetSumModels,String query);
        void getListFilteredByCode(ArrayList<Rpt3MonthGetSumModel> rpt3MonthGetSumModels,String query);
        void onConfigurationChanged(RptThreeMonthMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void updateData(String activityNameForLog);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptThreeMonthMVP.RequiredViewOps view);
        void onGetList(ArrayList<Rpt3MonthGetSumModel> moshtaryFaktorModels);
        void onGetFilteredList(ArrayList<Rpt3MonthGetSumModel> moshtaryFaktorModels,String filter);
        void onUpdateData();
        void failedUpdate();
    }


    interface ModelOps
    {
        void getListFilteredByName(ArrayList<Rpt3MonthGetSumModel> rpt3MonthGetSumModels,String filter);
        void getListFilteredByCode(ArrayList<Rpt3MonthGetSumModel> rpt3MonthGetSumModels,String filter);
        void getList();

        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void updateData(String activityNameForLog);
        void onDestroy();
    }

}
