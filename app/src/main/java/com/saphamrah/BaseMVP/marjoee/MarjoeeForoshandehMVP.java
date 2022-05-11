package com.saphamrah.BaseMVP.marjoee;

import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;

import java.util.ArrayList;

public interface MarjoeeForoshandehMVP
{

    interface RequiredViewOps
    {
        void showToast(int resId, int messageType, int duration);
        void onGetMarjoee(ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels);
        void onGetForoshandehMamorPakhshInfo(int noeMasouliat , int noeSabtMarjoee);
        void onTaeidSabtMarjoee(int ccKardexSatr,int selectedCount, int position);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getMarjoee(long ccDarkhastFaktor);
        void getForoshandehMamorPakhshInfo();
        void checkTaeidSabtMarjoee(ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModelCheckTaeidSabt ,long ccDarkhastFaktor, int itemCount, int selectedCount , int position,boolean addMarjoee, int ccMoshtary);
//        void deleteMarjoee(long ccDarkhastFaktor,int ccKalaCode);
    }


    interface RequiredPresenterOps
    {
        void onGetMarjoee(ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels);
        void onGetForoshandehMamorPakhshInfo(int noeMasouliat , int noeSabtMarjoee);
        void onTaeidSabtMarjoee(int ccKardexSatr,int selectedCount, int position);
    }


    interface ModelOps
    {
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getMarjoee(long ccDarkhastFaktor);
        void getForoshandehMamorPakhshInfo();
        void checkTaeidSabtMarjoee( ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModelCheckTaeidSabt ,long ccDarkhastFaktor, int itemCount, int selectedCount, int position,boolean insert, int ccMoshtary);
//        void deleteMarjoee(long ccDarkhastFaktor,int ccKalaCode,int selectedCount,int position);
    }

}
