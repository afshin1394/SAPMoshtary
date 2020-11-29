package com.saphamrah.BaseMVP.MainViewPagerMVP;

import android.content.Context;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

public interface HadafForoshDarsadFragmentMVP {
    interface RequiredViewOps {
        Context getAppContext();

        void onGetHadafForoshTedady(BaseHadafForoshModel rptBrandHadafForoshModel);
    }


    interface PresenterOps {


        void getHadafForoshTedady();
        void onConfigurationChanged(RequiredViewOps view);


        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);

        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps {
        Context getAppContext();


        void onGetHadafForoshTedadyKole(BaseHadafForoshModel baseHadafForoshModel);


        void onConfigurationChanged(RequiredViewOps view);
    }


    interface ModelOps {


        void getHadafForoshTedadyKole();

        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);

        void onDestroy();
    }
}
