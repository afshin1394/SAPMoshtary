package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MahalCodePostiModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.PolygonForoshSatrModel;

import java.util.ArrayList;


public interface AddCustomerAddressMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
		void onGetConfig(boolean requireCodePosti, boolean requireTelephone);																			 
        void onGetOstanItems(ArrayList<MahalModel> ostans);
        void onGetShahrestanItems(ArrayList<MahalModel> shahrestans);
        void onGetBakhshItems(ArrayList<MahalModel> bakhshes);
        void onGetShahrItems(ArrayList<MahalModel> shahres);
        void onGetMantagheItems(ArrayList<MahalModel> mantaghes);
        void onGetNoeAddressItems(ArrayList<Integer> itemId , ArrayList<String> itemTitles);
        void onErrorExistDarkhastTahvil();
        void onErrorCantAddNewAddress();
        void onErrorExistDarkhast();
        void onErrorExistTahvil();
        void onErrorOstan();
        void onErrorShahrestan();
        void onErrorBakhsh();
        void onErrorShahr();
        void onErrorMantaghe();
        void onErrorNoeAddress();
        void onErrorTelephone();
        void onErrorCodePosti(boolean wrong);
        void onErrorKhiabanAsli();
        void onErrorKoocheAsli();
        void onErrorEmptyPelak();
        void onErrorLengthPelak();
        void onSuccessfullySaveNewAddress(MoshtaryAddressModel moshtaryAddressModel);
        void onSuccessDeleteAddress(int deletedPosition);
        void drawSellPolygon(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels , String polygonColor);
        void onSuccessGetAddCustomerInfo(AddCustomerInfoModel addCustomerInfoModel);
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);
        void onGetMahalCodePosti(ArrayList<MahalCodePostiModel> mahalCodePostiModels);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddCustomerAddressMVP.RequiredViewOps view);
		void getConfig();
        void getOstanItems();
        void getShahrestanItems(int ostanId);
        void getBakhshItems(int shahrestanId);
        void getShahrItems(int bakhshId);
        void getMantagheItems(int shahrId);
        void getNoeAddressItems();
        void getSellPolygon();
        void checkNewAddress(MoshtaryAddressModel moshtaryAddressModel , ArrayList<MoshtaryAddressModel> moshtaryAddressModels, boolean requireTelephone, boolean requireCodePosti, ArrayList<MahalCodePostiModel> mahalCodePostiModelArrayList);
        void checkDeleteAddress(int position);
        void getAddCustomerInfo();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getMahalCodePosti(int ccMahal);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
		void onGetConfig(boolean requireCodePosti, boolean requireTelephone);																	 
        void onGetOstanItems(ArrayList<MahalModel> ostans);
        void onGetShahrestanItems(ArrayList<MahalModel> shahrestans);
        void onGetBakhshItems(ArrayList<MahalModel> bakhshes);
        void onGetShahrItems(ArrayList<MahalModel> shahres);
        void onGetMantagheItems(ArrayList<MahalModel> mantaghes);
        void onGetNoeAddressItems(ArrayList<Integer> itemId , ArrayList<String> itemTitles);
        void onSuccessfullySavedMoshtaryAddress(MoshtaryAddressModel moshtaryAddressModel);
        void onFailedSaveMoshtaryAddress(int errorMessageResId);
        void onSuccessDeleteAddress(int deletedPosition);
        void onFailedDeleteAddress();
        void onGetSellPolygon(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels , String polygonColor);
        void onGetAddCustomerInfo(AddCustomerInfoModel addCustomerInfoModel);
        void onConfigurationChanged(AddCustomerAddressMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
        void onMahalCodePosti(ArrayList<MahalCodePostiModel> mahalCodePostiModels);
    }

    interface ModelOps
    {
		void getConfig();
        void getOstanItems();
        void getShahrestanItems(int ostanId);
        void getBakhshItems(int shahrestanId);
        void getShahrItems(int bakhshId);
        void getMantagheItems(int shahrId);
        void getNoeAddressItems();
        void getSellPolygon();
        void saveNewAddress(MoshtaryAddressModel moshtaryAddressModel);
        void deleteAddress(int position);
        void getAddCustomerInfo();
        void getMahalCodePosti(int ccMahal);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
