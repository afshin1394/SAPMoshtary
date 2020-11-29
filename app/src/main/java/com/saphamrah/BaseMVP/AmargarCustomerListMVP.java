package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AmargarMarkazSazmanForoshModel;
import com.saphamrah.Model.ElatAdamMoarefiMoshtaryModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Valhalla.Location;

import java.util.ArrayList;
import java.util.List;

public interface AmargarCustomerListMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetMarkazForosh(List<AmargarMarkazSazmanForoshModel> markazModels, List<String> titles);
        void onGetSazmanForosh(List<AmargarMarkazSazmanForoshModel> markazModels, List<String> titles);
        void onGetForoshandeh(List<ForoshandehModel> foroshandehModels, List<String> titles);
        void onGetMasir(List<MasirModel> masirModels, List<String> titles);
        void showListMoshtarian(List<ListMoshtarianModel> arrayListData);
        void showListMoshtarianByLocation(ArrayList<ListMoshtarianModel> arrayListData);
        void onGetRadiusConfig(ArrayList<String> arrayListRadiusItems);
        void showCustomerLocation(double latitude, double longitude);
        void showElatAdamMoarefiMoshtary(ArrayList<ElatAdamMoarefiMoshtaryModel> elatAdamMoarefiMoshtaryModels, List<String> titles, int ccMoshtary);
        void showAlertGetCodeMoshtaryTekrari(int selectedccElat, int ccMoshtary);
        void openAddPorseshnameActivity(int ccMoshtary, String codeMoshtary);
        void showAlertLoading();
        void closeAlertLoading();
        void showErrorGetMarkaz();
        void showErrorSelectSazman();
        void showErrorSelectMarkaz();
        void showErrorNotFoundForoshandeh();
        void showErrorSelectForoshandeh();
        void showErrorNotFoundMasir();
        void showErrorSelectMasir();
        void showErrorGetListMoshtarian();
        void showNotFoundMoshtary();
        void showErrorGetRadiusConfig();
        void showErrorSelectRadius();
        void showErrorWrongLocation();
        void showErrorWrongCustomerLocation(String customerName);
        void showErrorSelectCustomer();
        void showErrorSendPorseshname();
        void showErrorGetPorseshname();
        void showErrorGetElatAdamMoarefiMoshtary();
        void showSuccessInsertAdamFaal();
        void showErrorInsertAdamFaal();
        void showErrorDistanceForAddPorseshname();
        void showErrorSendAllData();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AmargarCustomerListMVP.RequiredViewOps view);
        void getAllCustomers();
        void getAmargarMarkazForosh();
        void getAmargarSazmanForosh(Integer selectedMarkazForosh);
        void getForoshandeh(Integer selectedSazmanId);
        void getMasir(Integer selectedForoshandehId);
        void getListMoshtarian(Integer selectedMasirId);
        void getCustomerListByLocation(String selectedItem, String valueOf, String valueOf1);
        void getRadiusConfig();
        void checkCustomerLocation(String customerName, double latitude, double longitude);
        void checkForSendPorseshname(int ccMoshtary);
        void getElatAdamMoarefiMoshtary(int ccMoshtary);
        void checkElatAdamMoarefi(int selectedccElat, int ccMoshtary);
        void checkForAddPorseshname(Location currentLocation, ListMoshtarianModel model);
        void insertAdamFaal(int ccMoshtary, int selectedccElat, String codeMoshtaryTekrari);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetAllCustomers(List<ListMoshtarianModel> listMoshtarianModels);
        void onGetAmargarMarkazForosh(List<AmargarMarkazSazmanForoshModel> markazModels);
        void onGetAmargarSazmanForosh(List<AmargarMarkazSazmanForoshModel> markazModels);
        void onGetForoshandeh(List<ForoshandehModel> foroshandehModels);
        void onGetMasir(List<MasirModel> masirModels);
        void onGetListMoshtarian(ArrayList<ListMoshtarianModel> arrayListData);
        void onGetListMoshtarianByLocation(ArrayList<ListMoshtarianModel> arrayListData);
        void onGetRadiusConfig(ArrayList<ParameterChildModel> childParameterModelsConfig);
        void onErrorSendPorseshname(int resultCode);
        void onGetElatAdamMoarefiMoshtary(ArrayList<ElatAdamMoarefiMoshtaryModel> elatAdamMoarefiMoshtaryModels, int ccMoshtary);
        void onSaveAdamFaal(boolean result);
        void onCheckAddPorsehsname(int resultCode, int ccMoshtary, String codeMoshtary);
        void onConfigurationChanged(AmargarCustomerListMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getAllCustomers();
        void getAmargarMarkazForosh();
        void getAmargarSazmanForosh(Integer selectedMarkazForosh);
        void getForoshandeh(Integer ccSazman);
        void getMasir(Integer selectedForoshandehId);
        void getRadiusConfig();
        void getListMoshtarian(Integer selectedMasirId);
        void getCustomerListByLocation(String selectedItem, String latitude, String longitude);
        void sendPorseshname(int ccMoshtary);
        void getElatAdamMoarefiMoshtary(int ccMoshtary);
        void saveAdamFaal(int selectedccElat, int ccMoshtary, String codeMoshtaryTekrari);
        void checkForAddPorseshname(Location currentLocation, ListMoshtarianModel model);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
