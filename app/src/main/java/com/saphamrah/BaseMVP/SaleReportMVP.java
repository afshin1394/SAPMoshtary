package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptForoshModel;

import java.util.ArrayList;


public interface SaleReportMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void drawCountFaktorChart(int countFaktorRooz , int countFaktorMah);
        void drawMablaghFaktorChart(float mablaghFaktorRooz , float mablaghFaktorMah);
        void drawCountMarjoeeChart(int countMarjoeeRooz , int countMarjoeeMah);
        void drawMablaghMarjoeeChart(float mablaghMarjoeeRooz , float mablaghMarjoeeMah);
        void drawMablaghForoshChart(float mablaghFaktorRooz , float mablaghFaktorMah , float mablaghMarjoeeRooz , float mablaghMarjoeeMah);
        void drawCountForoshChart(int countFaktorRooz , int countFaktorMah , int countMarjoeeRooz , int countMarjoeeMah);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(SaleReportMVP.RequiredViewOps view);
        void getSaleReport();
        void updateSaleReport();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetSaleReport(ArrayList<RptForoshModel> rptForoshModels);
        void onConfigurationChanged(SaleReportMVP.RequiredViewOps view);
        void onNetworkError();
    }


    interface ModelOps
    {
        void getSaleReport();
        void updateSaleReport();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
