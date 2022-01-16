package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public interface TreasuryListMapMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
		void onGetNoeMasouliat(int noeMasouliat);										 
        void showCurrentLocation(double lat , double lng);
        void showRoutingInfo();
        void showCustomerCodeInfo();
        void showFirstPriority(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel ,  MoshtaryAddressModel moshtaryAddressModel);
        void showSecondPriority(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel ,  MoshtaryAddressModel moshtaryAddressModel);
        void showThirdPriority(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel, MoshtaryAddressModel moshtaryAddressModel);
        void onGetTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModelsNew);
        void onGetAllEditedTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModelArrayList);
        void onGetEditedTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModelArrayList);
        void showCustomerFaktors(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, MoshtaryAddressModel moshtaryAddressModel , String customerPriority , DarkhastFaktorMoshtaryForoshandeModel customerInfo);
        void onGetFaktorImage(byte[] faktorImage);
        void openDarkhastKalaActivity(long ccDarkhastFaktor , int ccMoshtary);
        void onSuccessRouting(String customerName, ArrayList<GeoPoint> pointsOfPolyline, String routingResponse);
		void openInvoiceSettlement(int ccMoshtary, long ccDarkhastFaktor);																  
        void showAlertDialog(int resId, String parameter, int messageType);
        void showAlertDialog(int resId, boolean closeActivity, int messageType);
		void showAlertDialog(String message, boolean closeActivity, int messageType);																			 
        void showToast(int resId, int messageType , int duration);
        void closeLoadingDialog();
        void onGetSortList(int sortList);
        void onErrorSendRasGiri(String error);
        void showLoadingDialog();

        void onOpenInvoiceSettlement(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(TreasuryListMapMVP.RequiredViewOps view);
		void getNoeMasouliat();					   
        void checkFakeLocationAndDateTime();
        void getCustomers(int sortType);
        void getCustomerFaktors(DarkhastFaktorMoshtaryForoshandeModel customerInfo , MoshtaryAddressModel moshtaryAddressModel, String customerPriority);
        void getTodayTreasuryList(int sortType);
        void getFaktorImage(long ccDarkhastFaktor);
        void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void sendDariaftPardakht(long noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void routingFromCurrentLocation(int ccMoshtary, String customerName, double desLatitude , double desLongitude);
		void checkEditTreasury(int noeMasouliat , DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);																													   
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getSortList();

        void checkMoshtaryKharejAzMahal(int noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);

        void checkClearingTreasury(int noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(TreasuryListMapMVP.RequiredViewOps view);
		void onGetNoeMasouliat(int noeMasouliat);										 
        void onErrorUseFakeLocation();
        void onCheckServerTime(boolean isValidDateTime, String message);
        void onGetCustomersList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListCanEditCustomerDarkhast, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListAllDarkhastEdited, int sortType, double currentLatitude, double currentLongitude , ArrayList<MoshtaryAddressModel> moshtaryAddressModels);
        void onGetCustomerFaktors(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, MoshtaryAddressModel moshtaryAddressModel , String customerPriority , DarkhastFaktorMoshtaryForoshandeModel customerInfo);
        void onGetTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> canEditDarkhastFaktorMoshtaryForoshandeModels, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> cantEditDarkhastFaktorMoshtaryForoshandeModels, double currentLocationLat, double currentLocationLog, int sortType, ArrayList<MoshtaryAddressModel> moshtaryAddressModels);
        void onErrorGetCustomerLocation(int resId , String parameter);
        void onGetFaktorImage(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel);
        void onSuccessSetDarkhastFaktorShared(long ccDarkhastFaktor , int ccMoshtary);
        void onFailedSetDarkhastFaktorShared(int resId);
        void onWarningSetDarkhastFaktorShared(int resId);
        void onSuccessUpdateMandeMojodiMashin();
        void onFailedUpdateMandeMojodiMashin();
        void onSuccessSendDariaftPardakht();
        void onSuccessRouting(String customerName, ArrayList<GeoPoint> pointsOfPolyline, String routingResponse);
        void onError(int resId);
        void onGetSortList(int sortList);
        void onErrorSendRasGiri(String error);
        void onWarning(int emptyList);

        void onSuccess(int successSendData);

        void onOpenInvoiceSettlement(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel, boolean b);
    }


    interface ModelOps
    {
		void getNoeMasouliat();					   
        void checkFakeLocationAndDateTime();
        void getCustomersOrderByRouting();
        void getCustomersOrderByCode();
        void getCustomerFaktors(DarkhastFaktorMoshtaryForoshandeModel customerInfo, MoshtaryAddressModel moshtaryAddressModel, String customerPriority);
        void getTodayTreasuryListByRouting();
        void getTodayTreasuryListByCustomerCode();
        void getFaktorImage(long ccDarkhastFaktor);
        void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void sendDariaftPardakht(long ccDarkhastFaktor);
        void routingFromCurrentLocation(int ccMoshtary, String customerName, double desLatitude , double desLongitude);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getSortList();

        void checkMoshtaryKharejAzMahal(int noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);

        void checkIsLocationSendToServer(int noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
    }

}
