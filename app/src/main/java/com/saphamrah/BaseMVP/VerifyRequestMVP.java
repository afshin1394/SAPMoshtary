package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.TaghvimTatilModel;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VerifyRequestMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetConfig(boolean showBtnMarjoee);
        void showTarikhPishbiniTahvil();
        void hideTarikhPishbiniTahvil();
        void onGetRequestDetail(int sumTedadAghlam, int tedadAghlam, double sumVazn, double sumHajm, double vaznFaktor, double hajmFaktor, double sumMablaghKol, double sumMablaghBaArzeshAfzoodeh, double sumMablaghKhales, int sumTedadAghlamPishnehadiBiSetareh);
        void showModatRoozRaasgiri(int modatRoozRaasgiri, boolean isSelectedVosolVajhNagh, boolean isSelectedVosolResidNaghd);
        void onGetCustomerAddress(ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<String> moshtaryAddressTitles);
        void openBarkhordAvalieActivity();
        void openDarkhastActivity();
        void openInvoiceSettlementActivity();
        void openCustomerSignActivity();
        void onGetInfo(long ccDarkhastFaktor , int modatVosol , ArrayList<ParameterChildModel> childParameterModelsVosols , ArrayList<String> vosolTitles);
        void openMarjoeeKalaActivity(long ccDarkhastFaktor , int ccMoshtary);
        void onGetRequestsList(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels);
        void onGetMarjoeeList(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels);
        void onGetDiscounts(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels, double sumMablaghTakhfif);
        void onGetBonus(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels , boolean showAddBonusBtn, boolean haveBonus);
        void onSuccessCalculateDiscount(boolean haveBonus, boolean canSelectBonus);
        void onFailedCalculateDiscount(int resId);
        void showCalculatedDateOfTarikhPishbiniTahvil(Map<String, Date> mapDates);
        void onDeleteBonus(boolean openSelectBonusActivity);
        void showToast(int resId, int messageType , int duration);
        void showToast(int resId, String param, int messageType, int duration);
        void showToast(int resId, String paramOne , String paramTow, int messageType, int duration);
        void showAlertDialog(int resId, boolean closeActivity, int messageType);
        void showLoading();
        void closeLoading();
        void onHashiehSoud(double mablaghTakhfifNaghdi ,double mablaghTakhfifHajmi ,double mablaghJayezeh ,double mablaghHashiehSood ,double jamSoodMaghazeh ,double darsadSoodMaghazeh);

    }


    interface PresenterOps
    {
        void onConfigurationChanged(VerifyRequestMVP.RequiredViewOps view);
        void getConfig();
        void getRequestDetail(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels);
        void getModatRoozRaasgiri(int ccChildSelectNoeVosol);
        void checkBottomBarClick(int position);
        void getCustomerInfo(int ccMoshtary, int ccSazmanForosh);
        void checkForOpenMarjoeeKalaActivity(long ccDarkhastFaktor , int ccMoshtary , String sumMablaghKhales);
        void getRequestsList();
        void getMarjoeeList();
        void getBonusList();
        void getDiscounts(long ccDarkhastFaktor);
        void calculateDiscounts(int ccChildParameterNoeVosol , int valueNoeVosol);
        void calculateTarikhPishbiniTahvil();
        void checkUpdateDarkhastFaktor(String modatVosol, int modatRoozRaasGiri, String sumMablaghKol, String sumMablaghKhales, String mablaghArzeshAfzoode, String sumTakhfifat, int codeNoeVosol, String nameNoeVosol, int ccAddress);
        void deleteBonus(long ccDarkhastFaktor , boolean openSelectBonusActivity);
        void checkData(int clickedBottomBarposition ,String mablaghKol, String sumTakhfifat, String mablaghFaktor , String sumMablaghBaArzeshAfzoode , int ccAddress , int modatVosol , int codeNoeVosol, String nameNoeVosol , int modatRoozRaasGiri , String vaznFaktor , String hajmFaktor, Date tarikhPishbiniTahvil, String TedadAghlam);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getHashiehSoud(long ccDarkhastFaktor,String mablaghBaArzeshAfzoodeh);
        void updateMoshtaryEtebar(int ccMoshtary);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(VerifyRequestMVP.RequiredViewOps view);
        void onGetConfig(boolean showBtnMarjoee);
        void onGetRequestDetail(int sumTedadAghlam, int tedadAghlam, double sumVazn, double sumHajm, double vaznFaktor, double hajmFaktor, double sumMablaghKol, double sumMablaghBaArzeshAfzoodeh, double sumMablaghKhales, int sumTedadAghlamPishnehadiBiSetareh);
        void onGetModatRoozRaasgiri(int modatRoozRaasgiri, boolean isSelectedVosolVajhNagh, boolean isSelectedVosolResidNaghd);
        void onGetCustomerAddress(ArrayList<MoshtaryAddressModel> moshtaryAddressModels, boolean showTarikhPishbiniTahvil);
        void onGetInfo(long ccDarkhastFaktor , int modatVosol , ArrayList<ParameterChildModel> childParameterModelsVosols);
        void onGetRequestsList(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels);
        void onGetMarjoeeList(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels);
        void onGetBonus(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels , boolean showAddBonusBtn, boolean haveBonus);
        void onErrorCalculateDiscount(int resId);
        void onSuccessCalculateDiscount(boolean haveBonus, boolean canSelectBonus);
        void onGetDiscounts(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels);
        void onGetTarikhPishbiniTahvilInfo(int maxTedadRooz, List<TaghvimTatilModel> taghvimTatilModels);
        void onDeleteBonus(boolean openSelectBonusActivity);
        void onErrorOperations(int resId);
        void onErrorCheck(int errorResId , String paramOne);
        void onErrorVazn(int errorResId , String paramOne , String paramTwo);

        void onSuccessCheck(int clickedBottomBarposition);
        void onUpdateMablaghNahaeeFaktor(long ccDarkhastFaktor , int ccMoshtary);
        void deleteTakhfifJayezeForDarkhastFaktor(long ccDarkhastFaktor);
        void showLoading();
        void closeLoading();
        void onHashiehSoud(double mablaghTakhfifNaghdi ,double mablaghTakhfifHajmi ,double mablaghJayezeh ,double mablaghHashiehSood ,double jamSoodMaghazeh ,double darsadSoodMaghazeh);
        void onFailedUpdateMoshtaryEtebar();
        void onSuccessUpdateMoshtaryEtebar();
    }


    interface ModelOps
    {
        void getConfig();
        void getRequestDetail(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels);
        void getModatRoozRassgiri(int ccChildSelectNoeVosol);
        void getCustomerInfo(int ccMoshtary, int ccSazmanForosh);
        void getRequestsList();
        void getMarjoeeList();
        void getBonusList();
        void calculateDiscounts(int ccChildParameterNoeVosol , int valueNoeVosol);
        void getDiscounts(long ccDarkhastFaktor);
        void getTarikhPishbiniTahvilInfo();
        void updateDarkhastFaktor(String modatVosol, int modatRoozRaasGiri , double sumMablaghKol , double sumMablaghKhales , float mablaghArzeshAfzoode , float sumTakhfifat , int codeNoeVosol , String nameNoeVosol , int ccAddress);
        void deleteBonus(long ccDarkhastFaktor , boolean openSelectBonusActivity);
        void deleteTakhfifJayezeForDarkhastFaktor(long ccDarkhastFaktor);
        void updateExtraPropMablaghNahaeeFaktor(long ccDarkhastFaktor, int ccMoshtary, float sumMablaghKhales);
        void checkData(int clickedBottomBarposition ,double mablaghKol, float sumTakhfifat, double mablaghFaktor , long sumMablaghBaArzeshAfzoode , int ccAddress , int modatVosol , int codeNoeVosol, String nameNoeVosol , int modatRoozRaasGiri , double vaznFaktor , double hajmFaktor, Date tarikhPishbiniTahvil, int tedadAghlam);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getHashiehSoud(long ccDarkhastFaktor, String mablaghBaArzeshAfzoodeh);
        void updateMoshtaryEtebar(int ccMoshtary);
    }

}
