package com.saphamrah.BaseMVP;

import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.NoePishnahadModel;
import com.saphamrah.Model.SuggestModel;
import com.saphamrah.Model.VarizBeBankModel;

import java.util.ArrayList;

public interface NazaratAndPishnahadMVP
{


    interface RequiredViewOps
    {

        void showToast(int resId, int messageType, int duration);
        void showAlertMessage(int resId, int messageType);
        void onGetNoePishnahadat(ArrayList<NoePishnahadModel> noePishnahadModels,ArrayList<String> noePishnahadTitles );
        void onGetSuggest(ArrayList<SuggestModel> suggestModels);

    }


    interface PresenterOps
    {
        void getNoePishnahad(int noePishnahad);
        void getSuggest(int ccMoshtary);
        void insertPishnahad(int ccNoePishnahad , String description, String descriptionPishanhad,int ccMoshtary);
        void deleteSuggest(int ccSuggest,int ccMoshtary);
    }


    interface RequiredPresenterOps
    {
       void onSuccessInsert();
       void onErrorInsert();
        void onGetNoePishnahadat(ArrayList<NoePishnahadModel> noePishnahadModels,ArrayList<String> noePishnahadTitles );
        void onGetSuggest(ArrayList<SuggestModel> suggestModels);
        void onSuccessDeleteSuggest();
        void onErrorDeleteSuggest();
    }


    interface ModelOps
    {
        void getNoePishnahad(int noePishnahad);
        void getSuggest(int ccMoshtary);
        void insertPishnahad(int ccNoePishnahad , String description, String descriptionPishanhad,int ccMoshtary);
        void deleteSuggest(int ccSuggest,int ccMoshtary);

    }

}
