package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.GorohModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.NoeTablighatModel;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.Model.PorseshnamehTablighatModel;
import com.saphamrah.Model.PorseshnamehTozihatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AddPorseshnameInfoMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void showMoshtaryInfo(ListMoshtarianModel listMoshtarianModel);
        void onGetPorseshnamehInfo(PorseshnamehModel porseshnamehModel);
        void showAdsOfPorseshnameh(List<PorseshnamehTablighatModel> porseshnamehTablighatModels);
        void showStatesOfMantaghe(MahalModel mantagheModel, MahalModel shahrModel, MahalModel bakhshModel, MahalModel shahrestanModel, MahalModel ostanModel);
        void showNameSenfMoshtary(String nameGoroh);
        void setProductItems(Map<String,Integer> mapProductItems);
        void showErrorGetProductItems();
        void setAnbarItems(Map<String, Integer> mapAnbarItems);
        void showErrorGetAnbarItems();
        void setAds(List<NoeTablighatModel> noeTablighatModels, List<String> adsTitle);
        void showErrorNotFoundAds();
        void setDescrption(List<PorseshnamehTozihatModel> porseshnamehTozihatModels, List<String> descTitles);
        void showErrorGetDescrption();
        void setNoeFaaliat(ArrayList<GorohModel> noeFaaliatItems, List<String> titles);
        void setNoeSenf(ArrayList<GorohModel> noeSenfItems, List<String> titles);
        void setOstanItems(ArrayList<MahalModel> mahalModels, List<String> ostanTitles);
        void setShahrestanTitlesItems(ArrayList<MahalModel> mahalModels, List<String> shahrestanTitles);
        void setBakhshTitlesItems(ArrayList<MahalModel> mahalModels, List<String> bakhshTitles);
        void setShahrTitlesItems(ArrayList<MahalModel> mahalModels, List<String> shahrTitles);
        void setMantagheTitlesItems(ArrayList<MahalModel> mahalModels, List<String> mantagheTitles);
        void openKalaAmargarActivity(int ccPorseshname);
        void showAlertChangedPhone(int ccPorseshname);
        void showCustomerAddress(MahalModel ostanModel, MahalModel shahrestanModel, MahalModel bakhshModel, MahalModel shahrModel, MahalModel mantagheModel);
        void showErrorNotSelectedOstan();
        void showErrorNotSelectedShahrestan();
        void showErrorNotSelectedBakhsh();
        void showErrorNotSelectedShahr();
        void showErrorFname();
        void showErrorLname();
        void showErrorNameMaghazeh();
        void showErrorMasahateMaghazeh();
        void showErrorOstan();
        void showErrorShahrestan();
        void showErrorBakhsh();
        void showErrorShahr();
        void showErrorMantaghe();
        void showErrorNameMahale();
        void showErrorKhiabanAsli();
        void showErrorKoocheAsli();
        void showErrorPelak();
        void showErrorAddress();
        void showErrorWrongMobile();
        void showErrorInsertPorseshname();
        void showErrorGetNoeFaaliat();
        void showErrorNotSelectedNoeFaaliat();
        void showErrorGetNoeSenf();
        void showErrorNoeFaaliat();
        void showErrorNoeSenf();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddPorseshnameInfoMVP.RequiredViewOps view);
        void saveInfo();
        void getMoshtary(int ccMoshtary, String codeMoshtary);
        void getPorseshnamehInfo(Integer ccPorseshname);
        void getAdsOfPorseshnameh(int ccPorseshnameh);
        void getNameSenfMoshtary(int ccSenfMoshtary);
        void getStatesOfMantaghe(int ccMahal);
        void getProductsItem();
        void getAnbarItems();
        void getAds();
        void getDescription();
        void getOstanItems();
        void getAllNoeFaaliat();
        void getNoeSenf(Integer selectedNoeFaaliatId);
        void getShahrestanItems(Integer selectedOstanId);
        void getBakhshItems(Integer selectedShahrestanId);
        void getShahrItems(Integer selectedBakhshId);
        void getMantagheItems(Integer selectedShahrId);
        void insertPorseshname(int ccPorseshname, int ccMoshtary, Integer selectedProductId, List<Integer> selectedAdsId, String fname, String lname, String nationalCode, String nameMaghazeh, String masahateMaghazeh, Integer selectedccMasir, Integer selectedAnbarId, Integer selectedNoeFaaliat, Integer selectedNoeSenf, String ostan, String shahrestan, String bakhsh, String shahr, String mantaghe, Integer selectedMantagheId, String nameMahale, String telephone, String oldTelephone, String mobile, String oldMobile, String postalCode, String khiabanAsli, String khiabanFaree1, String khiabanFaree2, String koocheAsli, String koocheFaree, String pelak, Integer selectedDescId);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(AddPorseshnameInfoMVP.RequiredViewOps view);
        void onGetMoshtary(ListMoshtarianModel listMoshtarianModel);
        void onGetPorseshnamehInfo(PorseshnamehModel porseshnamehModel);
        void onGetStatesOfMantaghe(MahalModel mantagheModel, MahalModel shahrModel, MahalModel bakhshModel, MahalModel shahrestanModel, MahalModel ostanModel);
        void onGetAdsOfPorseshnameh(List<PorseshnamehTablighatModel> porseshnamehTablighatModels);
        void onGetNameSenfMoshtary(String nameGoroh);
        void onGetProductsItem(Map<String,Integer> mapProducts);
        void onGetAnbarItems(Map<String, Integer> mapAnbarItems);
        void onGetAds(List<NoeTablighatModel> noeTablighatModels);
        void onGetDescription(List<PorseshnamehTozihatModel> porseshnamehTozihatModels);
        void onGetAllNoeFaaliat(ArrayList<GorohModel> noeFaaliatItems);
        void onGetNoeSenfItems(ArrayList<GorohModel> noeSenfItems);
        void onGetOstanItems(ArrayList<MahalModel> mahalModels);
        void onGetShahrestanItems(ArrayList<MahalModel> mahalModels);
        void onGetBakhshItems(ArrayList<MahalModel> mahalModels);
        void onGetShahrItems(ArrayList<MahalModel> mahalModels);
        void onGetMantagheItems(ArrayList<MahalModel> mahalModels);
        void onInsertPorseshname(int ccPorseshname, boolean changedPhone);
        void onUpdatePorseshnameh(int ccPorseshname);
        void onGetAddress(MahalModel ostanModel, MahalModel shahrestanModel, MahalModel bakhshModel, MahalModel shahrModel, MahalModel mantagheModel);
    }


    interface ModelOps
    {
        void saveInfo();
        void getMoshtary(int ccMoshtary, String codeMoshtary);
        void getPorseshnamehInfo(Integer ccPorseshname);
        void getAdsOfPorseshnameh(int ccPorseshnameh);
        void getNameSenfMoshtary(int ccSenfMoshtary);
        void getStatesOfMantaghe(int ccMahal);
        void getProductsItem();
        void getAnbarItems();
        void getAds();
        void getDescription();
        void getAllNoeFaaliat();
        void getNoeSenf(Integer selectedNoeFaaliatId);
        void getOstanItems();
        void getShahrestanItems(int ccOstan);
        void getBakhshItems(int shahrestanId);
        void getShahrItems(int bakhshId);
        void getMantagheItems(int shahrId);
        void insertPorseshname(PorseshnamehModel porseshnamehModel, List<Integer> selectedAdsId, boolean changedPhone);
        void updatePorseshname(int ccPorseshname, PorseshnamehModel porseshnamehModel, List<Integer> selectedAdsId);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
