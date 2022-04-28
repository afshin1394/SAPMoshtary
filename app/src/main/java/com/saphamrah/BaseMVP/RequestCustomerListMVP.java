package com.saphamrah.BaseMVP;

import android.content.Context;

import com.google.gson.JsonObject;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryMorajehShodehRoozModel;

import org.json.JSONObject;

import java.util.ArrayList;


public interface RequestCustomerListMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetDateOfGetProgram(String date);
        void onGetCustomers(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh,ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , boolean canUpdateCustomer);

        void onGetSearch(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels,ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , ArrayList<Integer> arrayListNoeMorajeh);
        void showBarkhordAvalieActivity(int ccMoshtary);
        void showMojoodiGiriActivity(int ccMoshtary,int ccSazmanForosh);
        void showDarkhastKalaActivity(int ccMoshtary,int ccSazmanForosh);
        void showAlertDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel);
        void showAlertOneRequestForCustomer();
        void closeLoading();
        void showToast(int resId, int messageType , int duration);
        void showErrorAlert(int resId, int messageType, boolean closeActivity);

        void onSuccessUpdateCustomerAddress(int position);
        void onGetElatAdamDarkhast(int ccMoshtary,ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels, ArrayList<String> elatAdamDarkhastTitles);

        void showTakeImageAlert(ElatAdamDarkhastModel elatAdamDarkhastModel);

        void showDuplicatedCustomerCodeAlert(int ccMoshtary,ElatAdamDarkhastModel elatAdamDarkhastModel);

        void onSuccessInsertAdamDarkhast();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequestCustomerListMVP.RequiredViewOps view);
        void checkFakeLocation();
        void checkDateOfGetProgram();
        void getCustomers();
        void searchCustomerName(String searchWord , ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels ,ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels, ArrayList<Integer> arrayListNoeMorajeh);
        void searchCustomerCode(String searchWord , ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels ,ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels, ArrayList<Integer> arrayListNoeMorajeh);
        void checkDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel);
        void checkSelectedCustomer(int ccMoshtary,int ccSazmanForoshGharardad,int ccMoshtaryGharardad);
        void checkUpdateEtebarMoshtary(MoshtaryModel moshtaryModel);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void updateMoshtaryMorajehShodehRooz();
        void sendCustomerLocation(int position,MoshtaryAddressModel ccMoshtary);
        void searchNameTablo(String searchWord, ArrayList<MoshtaryModel> moshtaryModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModels, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels, ArrayList<Integer> moshtaryNoeMorajeh);
        void searchTelephone(String newText, ArrayList<MoshtaryModel> moshtaryModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModels, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels, ArrayList<Integer> moshtaryNoeMorajeh);
        void getElatAdamDarkhast(int ccMoshtary);
        void checkSeletedAdamDarkhastItem(int ccMoshtary, ElatAdamDarkhastModel elatAdamDarkhastModel);

        void checkAdamDarkhastForInsert(int ccMoshtary, ElatAdamDarkhastModel elatAdamDarkhastModel, byte[] imageAdamDarkhast, String codeMoshtaryTekrari);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onErrorUseFakeLocation();
        void onGetDateOfGetProgram(String date);
        void onErrorNeedGetProgram();
        void onSetRequestInfoShared(int ccMoshtary,int ccSazmanForosh, boolean showBarkhordAvalie, boolean showMojodiGiri);
        void onFailedSetRequestInfoShared();
        void showAlertDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel);
        void showAlertOneRequestForCustomer();
        void onErrorSelectCustomer(int resId);
        void onWarningSelectCustomer(int resId);
        void onSuccessUpdateMandeMojodi();
        void onFailedUpdateMandeMojodi();
        void onGetCustomers(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , boolean canUpdateCustomer);
        void onErrorAccessToLocation();
        void onSuccessUpdateMoshtaryEtebar();
        void onFailedUpdateMoshtaryEtebar();
        void onFailedUpdateForoshandehEtebar();
        void onConfigurationChanged(RequestCustomerListMVP.RequiredViewOps view);
        void onUpdateMoshtaryMorajehShodehRooz();
        void onFailUpdateMoshtaryMorajehShodehRooz();
        void onSuccessUpdateCustomerAddress(int position);
        void onFailedUpdateCustomerAddress();

        void onGetElatAdamDarkhast(int ccMoshtary,ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels);

        void onSuccessInsertAdamDarkhast();

        void onFailedInsertAdamDarkhast();
    }


    interface ModelOps
    {
        void checkFakeLocation();
        void getDateOfGetProgram();
        void getCustomers();
        void checkDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel);
        void checkSelectedCustomer(int ccMoshtary,int moshtaryGharardad,int moshtaryGharardadccSazmanForosh);
        void updateEtebarMoshtary(MoshtaryModel moshtaryModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void updateMoshtaryMorajehShodehRooz();
        void sendCustomerLocation(int position, JSONObject jsonObjectFinal);
        void getElatAdamDarkhast(int ccMoshtary);

        void insertAdamDarkhast(int ccMoshtary, Integer ccElatAdamDarkhast, byte[] imageAdamDarkhast, String codeMoshtaryTekrari);
    }

}
