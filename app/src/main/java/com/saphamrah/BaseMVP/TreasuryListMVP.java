package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ElatAdamTahvilDarkhastModel;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;

import java.util.ArrayList;

public interface TreasuryListMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int noeMasouliat , int sort);
        void onGetFaktorMandeDar(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int noeMasouliat);
        void onGetTreasuryListWithRouting(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int noeMasouliat);
        void onGetCustomerAddress(double latitude , double longitude);
        void onGetFaktorImage(byte[] faktorImage);
        void openDarkhastKalaActivity(long ccDarkhastFaktor , int ccMoshtary);
        void showHideFabButtons(boolean faktorRooz);
        void onError(boolean closeActivity , int errorResId);
		void onError(boolean closeActivity , String message);													 
        void showAlertMessage(int resId, int messageType);
        void onErrorSendRasGiri(String error);
        void showAlertMessage(int resId , String parameter, int messageType);
        void showToast(int resId, int messageType , int duration);
        void showLoading();
        void closeLoading();
        void onSuccessSend(int position);
        void onSuccessLocation(int position);

        void openInvoiceSettlementActivity(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void onGetSearchResult(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels);
        void onGetElatAdamTahvilDarkhast(ArrayList<ElatAdamTahvilDarkhastModel> models, ArrayList<String> elatAdamTahvilDarkhastTitles, DarkhastFaktorModel darkhastFaktorModel,int position);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(TreasuryListMVP.RequiredViewOps view);
        void checkDateAndFakeLocation(int state);
        void getTreasuryList(int faktorRooz, int sortType);
        void getTreasuryListWithRouting(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels);
        void getDariaftPardakhtForSend(long ccDarkhastFaktor, int ccMoshtary , int codeVazeiat , int position);
        void getCustomerLocation(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void getFaktorImage(long ccDarkhastFaktor);
        void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);

        void checkMoshtaryKharejAzMahal(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel, int position);

        void updateGpsData();

        void checkIsLocationSendToServer(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void searchCustomer(String searchWord , ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels);
        void getElatAdamTahvilDarkhast(long ccDarkhastFaktor, int ccMoshtary,int position);
        void sendElatAdamTahvilDarkhast(ElatAdamTahvilDarkhastModel elatAdamTahvilDarkhastModel , DarkhastFaktorModel darkhastFaktorModel,int position);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(TreasuryListMVP.RequiredViewOps view);
        void onErrorUseFakeLocation();
        void onCheckServerTime(int state,boolean valid, String message , int sort);
        void onGetCustomerAddress(double latitude , double longitude);
        void onGetFaktorImage(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel);
        void onFailedSetDarkhastFaktorShared(int resId);
        void onSuccessSetDarkhastFaktorShared(long ccDarkhastFaktor , int ccMoshtary);
        void onWarningSetDarkhastFaktorShared(int resId);
        void onSuccessUpdateMandeMojodiMashin();
        void onFailedUpdateMandeMojodiMashin();
        void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int faktorRooz , int noeMasouliat , int sort);
        void onGetTreasuryListWithRouting(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int noeMasouliat);
        void onErrorSend(int resId);
        void onSuccessSend(int position);
        void onErrorAccessToLocation();
        void onErrorGetCustomerLocation(int resId , String customerName);
        void onError(int resId);
        void onErrorSendRasGiri(String error);
        void onSuccess(int resId);
        void onSuccessLocation(int resId,int position);
        void openInvoiceSettlement(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel,boolean openInvoiceSettlement);
        void closeLoading();
        void onGetElatAdamTahvilDarkhast(ArrayList<ElatAdamTahvilDarkhastModel> models, DarkhastFaktorModel darkhastFaktorModel,int position);
    }


    interface ModelOps
    {
        void checkDateAndFakeLocation(int state);
        void getTreasuryList(int faktorRooz, int sortType);
        void getTreasuryListWithRouting(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels);
        void getDariaftPardakhtForSend(long ccDarkhastFaktor , int ccMoshtary, int position);
        void getCustomerLocation(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void getFaktorImage(long ccDarkhastFaktor);
        void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void checkMohtaryKharejAzMahal(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel,int position);
        void updateGpsData();
        void checkIsLocationSendToServer(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void getElatAdamTahvilDarkhast(long ccDarkhastFaktor, int ccMoshtary,int position);
        void sendElatAdamTahvilDarkhast(ElatAdamTahvilDarkhastModel elatAdamTahvilDarkhastModel , DarkhastFaktorModel darkhastFaktorModel,int position);

    }


}
