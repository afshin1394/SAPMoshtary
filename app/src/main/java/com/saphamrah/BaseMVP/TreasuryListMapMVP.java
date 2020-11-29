package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
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
        void showFirstPriority(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void showSecondPriority(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void showThirdPriority(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void onGetTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels);
        void onGetAllEditedTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels);
        void onGetEditedTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels);
        void showCustomerFaktors(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , String customerPriority , DarkhastFaktorMoshtaryForoshandeModel customerInfo);
        void onGetFaktorImage(byte[] faktorImage);
        void openDarkhastKalaActivity(long ccDarkhastFaktor , int ccMoshtary);
        void onSuccessRouting(String customerName, ArrayList<GeoPoint> pointsOfPolyline, String routingResponse);
		void openInvoiceSettlement(int ccMoshtary, long ccDarkhastFaktor);																  
        void showAlertDialog(int resId, String parameter, int messageType);
        void showAlertDialog(int resId, boolean closeActivity, int messageType);
		void showAlertDialog(String message, boolean closeActivity, int messageType);																			 
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(TreasuryListMapMVP.RequiredViewOps view);
		void getNoeMasouliat();					   
        void checkFakeLocationAndDateTime();
        void getCustomers(int sortType);
        void getCustomerFaktors(DarkhastFaktorMoshtaryForoshandeModel customerInfo , String customerPriority);
        void getTodayTreasuryList(int sortType);
        void getFaktorImage(long ccDarkhastFaktor);
        void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void sendDariaftPardakht(long noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void routingFromCurrentLocation(int ccMoshtary, String customerName, double desLatitude , double desLongitude);
		void checkEditTreasury(int noeMasouliat , DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);																													   
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(TreasuryListMapMVP.RequiredViewOps view);
		void onGetNoeMasouliat(int noeMasouliat);										 
        void onErrorUseFakeLocation();
        void onCheckServerTime(boolean isValidDateTime, String message);
        void onGetCustomersList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListCanEditCustomerDarkhast, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListAllDarkhastEdited, int sortType, double currentLatitude, double currentLongitude);
        void onGetCustomerFaktors(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , String customerPriority , DarkhastFaktorMoshtaryForoshandeModel customerInfo);
        void onGetTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> canEditDarkhastFaktorMoshtaryForoshandeModels, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> cantEditDarkhastFaktorMoshtaryForoshandeModels, double currentLocationLat, double currentLocationLog, int sortType);
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
    }


    interface ModelOps
    {
		void getNoeMasouliat();					   
        void checkFakeLocationAndDateTime();
        void getCustomersOrderByRouting();
        void getCustomersOrderByCode();
        void getCustomerFaktors(DarkhastFaktorMoshtaryForoshandeModel customerInfo , String customerPriority);
        void getTodayTreasuryListByRouting();
        void getTodayTreasuryListByCustomerCode();
        void getFaktorImage(long ccDarkhastFaktor);
        void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void sendDariaftPardakht(long ccDarkhastFaktor);
        void routingFromCurrentLocation(int ccMoshtary, String customerName, double desLatitude , double desLongitude);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
