package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptSanadModel;

import java.util.List;

public interface RptSanadMVP {


        interface RequiredViewOps {
            Context getAppContext();
            void setAdapter(List<RptSanadModel> rptSanadModels);
            void closeLoadingDialog();
            void showToast(int resId, int messageType , int duration);
        }

        interface PresenterOps {
            void getRptSanad();
            void updateRptSanad();

            void onConfigurationChanged(com.saphamrah.BaseMVP.RptSanadMVP.RequiredViewOps view);
            void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
            void onDestroy(boolean isChangingConfig);
        }

        interface RequiredPresenterOps {
            Context getAppContext();
            void onConfigurationChanged(com.saphamrah.BaseMVP.RptSanadMVP.RequiredViewOps view);
            void onGetRptSanad(List<RptSanadModel> rptSanadModels);
            void onFailedGetRptSanad(int resId);
            void onSuccessUpdateRptSanad();
            void onErrorUpdateRptSanad();
        }

        interface ModelOps {
            void getRptSanad();
            void updateRptSanad();

            void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
            void onDestroy();
        }


}
