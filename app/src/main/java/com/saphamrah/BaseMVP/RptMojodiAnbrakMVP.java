package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.RptMojodiAnbarModel;
import java.util.ArrayList;


public interface RptMojodiAnbrakMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void setAdapter(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels);
        void setAdapterAsTable(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels);
        void showSearchResult(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable);
        void onSortByCodeKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable);
        void onSortByNameKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable);
        void onSortByCount(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable);
        //Todo
        void onGetGallery(ArrayList<KalaPhotoModel> kalaPhotoModels);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptMojodiAnbrakMVP.RequiredViewOps view);
        //Todo
        void getGallery();
        void getMojodiAnbar();
        void updateMojodiAnbar();
        void checkSearchNameKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , String searchWord , boolean viewAsTable);
        void sortByCodeKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable);
        void sortByNameKala(boolean viewAsTable);
        void sortByCount(boolean viewAsTable);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetGallery(ArrayList<KalaPhotoModel> kalaPhotoModels);
        void onGetMojodiAnbar(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels);
        void onGetMojodiAnbarOrderByNameKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels  , boolean viewAsTable);
        void onGetMojodiAnbarOrderByCount(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels  , boolean viewAsTable);
        void onFailedGetMojodiAnbar(int resId);
        void onConfigurationChanged(RptMojodiAnbrakMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getGallery();
        void getMojodiAnbar();
        void updateMojodiAnbar();
        void getMojodiAnbarOrderByNameKala(boolean viewAsTable);
        void getMojodiAnbarOrderByCount(boolean viewAsTable);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
