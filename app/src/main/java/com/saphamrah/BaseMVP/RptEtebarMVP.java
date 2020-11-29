package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.UIModel.rptEtebarModel.RptEtebarParentModel;

import java.util.ArrayList;
import java.util.List;

public interface RptEtebarMVP {

    interface RequiredViewOps {
        Context getAppContext();
        void setAdapter(List<RptEtebarParentModel> rptEtebarModels);
        void showToast(int resId, int messageType , int duration);
    }

    interface PresenterOps {
        void onConfigurationChanged(RptEtebarMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);

        void getEtebar(int ccForoshande, int ccMoshtary, int ccSazmanForosh);
    }

    interface RequiredPresenterOps {
        Context getAppContext();
        void onConfigurationChanged(RptEtebarMVP.RequiredViewOps view);

        void onGetEtebar(List<RptEtebarParentModel> etebarModels);
        void onFailedGetEtebar(int resId);
    }

    interface ModelOps {
       void getEtebar(int foroshande, int moshtary, int SazmanForosh);
        int getCcForoshandeForRptEtebar();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }
}
