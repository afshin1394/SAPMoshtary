package com.saphamrah.BaseMVP;

import com.saphamrah.Model.TizerModel;

import java.util.ArrayList;

public interface TizerMVP
{

    interface RequiredViewOps
    {
        void setListFolder(ArrayList<String> tizerModels);
        void setListFile(ArrayList<TizerModel> tizerModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType, int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getListFolder();
        void updateData(String activityNameForLog);
        void getListFile(String folderName);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
    }


    interface RequiredPresenterOps
    {
        void onGetListFolder(ArrayList<String> tizerModels);
        void onGetListFile(ArrayList<TizerModel> tizerModels);
        void onUpdateData();
        void failedUpdate();
    }


    interface ModelOps
    {
        void getListFolder();
        void updateData(String activityNameForLog);
        void getListFile(String folderName);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);

    }

}
