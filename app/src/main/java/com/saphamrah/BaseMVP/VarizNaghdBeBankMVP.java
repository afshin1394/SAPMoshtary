package com.saphamrah.BaseMVP;

import com.saphamrah.MVP.Model.VarizNaghdBeBankModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.VarizBeBankModel;

import java.util.ArrayList;

public interface VarizNaghdBeBankMVP
{


    interface RequiredViewOps
    {
        void onGetAllBank(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels);
        void onUpdateDao(boolean update);
       void onGetAllMoshtary(ArrayList<VarizBeBankModel> varizBeBankModels);
        void onGetSumMablagh(ArrayList<VarizBeBankModel> varizBeBankModels);
        void showToast(int resId, int messageType, int duration);
        void onGetAllVarizBeBank(ArrayList<VarizBeBankModel> varizBeBankModels);
        void onCheckHaveShomarehSanadForUpdate(boolean haveShomarehSanad);
        void showAlertMessage(int resId, int messageType);
        void closeLoading();
    }


    interface PresenterOps
    {
        void onGetAllBank();
        void updateDaoAll(int ccBankSanad , String nameShobehSanad , String codeShobehSand , String shomareHesabSanad , int ccShomareHesab , String shomarehSanad ,String nameBankSanad , String TarikhSanad, ArrayList<VarizBeBankModel> modelsArrayList,String mablaghEntekhabi);
        void getAllMoshtary();
        void getRefresh();
        void getSumMablagh();
        void getAllVarizBeBank();
        void sendVariz(VarizBeBankModel varizBeBankModel);
        void checkHaveShomarehSanadForUpdate(String ExtraProp_ShomarehSanad);
    }


    interface RequiredPresenterOps
    {
        void onGetAllBank(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels);
        void onUpdateDao(boolean update);
        void onGetAllMoshtary(ArrayList<VarizBeBankModel> varizBeBankModels);
        void onGetSumMablagh(ArrayList<VarizBeBankModel> varizBeBankModels);
        void onGetRefresh(int resId, int messageType, int duration);
        void onGetAllVarizBeBank(ArrayList<VarizBeBankModel> varizBeBankModels);
        void onCheckHaveShomarehSanadForUpdate(boolean haveShomarehSanad);
        void onErrorSend(int resId);
        void onSuccessSend();
    }


    interface ModelOps
    {
        void getAllBank();
        void updateDaoAll(int ccBankSanad , String nameShobehSanad , String codeShobehSand , String shomareHesabSanad , int ccShomareHesab , String shomarehSanad ,String nameBankSanad , String TarikhSanad, ArrayList<VarizBeBankModel> modelsArrayList,String mablaghEntekhabi);
        void getAllMoshtary();
        void getRefresh();
        void getSumMablagh();
        void getAllVarizBeBank();
        void sendVariz(VarizBeBankModel varizBeBankModel);
        void checkHaveShomarehSanadForUpdate(String ExtraProp_ShomarehSanad);
    }

}
