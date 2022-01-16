package com.saphamrah.BaseMVP.marjoee;

import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.MarjoeeMamorPakhshModel;

import java.util.ArrayList;

public interface MarjoeeMorediMVP
{

    interface RequiredViewOps
    {
        void showToast(int resId, int messageType, int duration);
        void onGetMarjoeeMoredi(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels);
        void onGetElatMarjoeeMoredi  (ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, ArrayList<String> elatMarjoeeKalaTitles);
        void onTaeidSabtMarjoee(int selectedCount,String nameElatMarjoee, int position);
        void onGetSearch(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsSearch);
    }

    interface PresenterOps
    {

        void onConfigurationChanged(RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getMarjoeeMoredi(int ccMoshtary);
        void checkTaeidSabtMarjoee(MarjoeeMamorPakhshModel model , long ccRefrence, int itemCount, int selectedCount , int position,ArrayList<ElatMarjoeeKalaModel> elatMarjoee);
        void deleteMarjoee(int ccMarjoeeMamorPakhsh, int ccMoshtary,int ccKalaCode);
        void getElatMarjoeeMoredi();
        void searchNameKala(String searchWord ,ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsAdpater);

    }

    interface RequiredPresenterOps
    {

        void onGetMarjoeeMoredi(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels);
        void onGetElatMarjoeeMoredi  (ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, ArrayList<String> elatMarjoeeKalaTitles);
        void onTaeidSabtMarjoee(int selectedCount,String nameElatMarjoee, int position);
        void onGetSearch(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsSearch);
    }


    interface ModelOps
    {
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getMarjoeeMoredi(int ccMoshtary);
        void checkTaeidSabtMarjoee(MarjoeeMamorPakhshModel model , long ccRefrence, int itemCount, int selectedCount , int position,ArrayList<ElatMarjoeeKalaModel> elatMarjoee);
        void deleteMarjoee(int ccMarjoeeMamorPakhsh, int ccMoshtary,int ccKalaCode);
        void getElatMarjoeeMoredi();
        void searchNameKala(String searchWord ,ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsAdpater);
    }

}
