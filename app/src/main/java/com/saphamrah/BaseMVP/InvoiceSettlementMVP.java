package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.ConfigNoeVosolMojazeFaktorModel;
import com.saphamrah.Model.ConfigNoeVosolMojazeMoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.PosShomarehHesabModel;
import java.util.ArrayList;

public interface InvoiceSettlementMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
		void showSayyadCheckScanner(int visibility);											
        void openBarkhordAvalieActivity();
        void openEmzaMoshtaryActivity();
        void onGetInfo(String shomareDarkhast , int ccNoeVosol , String noeVosol , String cost , String remainCost /*, ArrayList<> paymentsItem */);
        void onGetVosols(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels);
        void onGetNoeVosols(ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels);
        void showVosolNaghdInfo(int codeNoevosol);
        void showVosolPOSInfo(ArrayList<PosShomarehHesabModel> posShomarehHesabModels , int codeNoevosol);
        void showVosolIranCheckInfo(ArrayList<ParameterChildModel> childParameterModels , int codeNoevosol);
        void showVosolFishBankiInfo(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels , int codeNoevosol);
        void showVosolCheckInfo(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels, ArrayList<BankModel> bankModels , int codeNoevosol , String tarikhSarResidForCheckShamsi , String tarikhSarResidForCheckGregorian);
        void showVosolResidInfo(int codeNoevosol);
        void showZeroRemainCostAlert();
        void onSuccessCheckRemainCost();
		void showDataOfCheckFromScanner(String checkHesabNumber, String checkCodeShobe, String sayyadId);
        void onGetBank(BankModel bankModel);								
        void showToast(int resId, int messageType , int duration);
        void showAlert(int resId , int messageType);
        void showAlert(String text , int messageType);
        void onSuccessInsert(long mablaghMandeFaktor);
        void onSuccessRemove(int position , long mablaghMandeh);

        void onGetDetailsCheckBargashti(ArrayList<BargashtyModel> bargashtyModels);
        void onGetNoeVosolsMojazMoshtary(ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels);
        void onGetVosolsPishDaryaft(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels);
        void oncallTajil(long mablaghTajil_Naghd , long mandehFaktorPasAzTajil_Naghd ,long mablaghTajil_Check , long mandehFaktorPasAzTajil_Check,boolean canGetTajil);
        void canNotAdd();
        void onVisibilityLayoutTajil(boolean visible);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(InvoiceSettlementMVP.RequiredViewOps view);
		void getConfig();				 
        void checkBottomBarClick(int position);
        void getInfo(long ccDarkhastFaktor, int from);
        void getVosols(long ccDarkhastFaktor);
        void getNoeVosols(long ccDarkhastFaktor , boolean getNoeVosolFromShared , int from , int ccMoshtary);
        void checkSelectedNoeVosol(String codeNoeVosol , int ccMoshtary , long ccDarkhastFaktor , double mablaghMandeh);
        void checkRemainCost(int codeNoeVosolMoshtary , String mablaghMandeh ,int from);
        void checkInsert(int ccMoshtary , long ccDarkhastFaktor , int codeNoeVosolMoshtary , int flagInputHesab , String mablaghMandeh , DariaftPardakhtPPCModel dariaftPardakhtPPCModel , ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels);
        void checkInsertCheckBargashty(int ccMoshtary , long ccDarkhastFaktor , int codeNoeVosolMoshtary , int flagInputHesab , String mablaghMandeh , DariaftPardakhtPPCModel dariaftPardakhtPPCModel , ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModelArrayList);
        void removeItem(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel , int position , int from,long ccDarkhastFaktor);
        void checkRegisteredVosol(int position);
        void checkDataFromScanner(String data);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getDetailsCheckBargashti(String shomarehSanad , int ccDarajeh ,int ccNoeMoshtary);

        void checkInsertCheckPishDrayaft(int ccMoshtary , int codeNoeVosolMoshtary  , int flagInputHesab , String mablaghMandeh , DariaftPardakhtPPCModel dariaftPardakhtPPCModel , ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModelArrayList);
        void getVosolsPishDariaft(long ccMoshtary);
        void removeItemPishDaryaft(DariaftPardakhtPPCModel dariaftPardakhtPPCModel , int position , int from);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(InvoiceSettlementMVP.RequiredViewOps view);
		void onGetConfig(String showSaayadCheckInfoScanner);													
        void onGetInfo(String shomareDarkhast , int ccNoeVosol , String noeVosol , String cost , String remainCost /*, ArrayList<> paymentsItem */ , int from);
        void onGetVosols(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels);
        void onGetNoeVosols(ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels);
        void onGetPosShomareHesab(ArrayList<PosShomarehHesabModel> posShomarehHesabModels , int codeNoevosol);
        void onGetFishBanki(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels , int codeNoevosol);
        void onGetMoshtaryHesab(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels , ArrayList<BankModel> bankModels , int codeNoevosol , String tarikhSarResidForCheckShamsi , String tarikhSarResidForCheckGregorian);
        void onGetNoeIranCheck(ArrayList<ParameterChildModel> childParameterModels , int codeNoevosol);
        void onErrorCheckInsert(int errorResId);
        void onErrorCheckInsert(String errorsText);
        void onSuccessInsert(long mablaghMandeFaktor);
        void onSuccessRemoveItem(int position , long mablaghMandeh);
        void onFailedRemoveItem();
        void onSuccessCheckRegisteredVosol(int position);
		void onGetBank(BankModel bankModel);									
        void onError(int resId);

        void onGetDetailsCheckBargashti(ArrayList<BargashtyModel> bargashtyModels);
        void onGetNoeVosolsMojazMoshtary(ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels);
        void onGetVosolsPishDaryaft(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels);
        void oncallTajil(long mablaghTajil_Naghd , long mandehFaktorPasAzTajil_Naghd ,long mablaghTajil_Check , long mandehFaktorPasAzTajil_Check , boolean canGetTajil);
        void onVisibilityLayoutTajil(boolean visible);
    }


    interface ModelOps
    {
		void getConfig();				 
        void getInfo(long ccDarkhastFaktor , int from);
        void getVosols(long ccDarkhastFaktor);
        void getNoeVosols(long ccDarkhastFaktor , boolean getNoeVosolFromShared , int from , int ccMoshtary);
        void getPosShomareHesab(int codeNoevosol);
        void getFishBanki(int codeNoevosol);
        void getMoshtaryHesab(int ccMoshtary , long ccDarkhastFaktor, int codeNoevosol , double mablaghMande);
        void getNoeIranCheck(int codeNoevosol);
        void checkInsert(int ccMoshtary ,long ccDarkhastFaktor , int codeNoeVosolMoshtary , int flagInputHesab , String mablaghMandeh , String nameNoeVosol , DariaftPardakhtPPCModel dariaftPardakhtPPCModel);
        void checkInsertCheckBargashty(int ccMoshtary , long ccDarkhastFaktor , int codeNoeVosolMoshtary , int flagInputHesab , String mablaghMandeh , String nameNoeVosol , DariaftPardakhtPPCModel dariaftPardakhtPPCModel);
        void removeItem(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel , int position , int from,long ccDarkhastFaktor);
        void checkRegisteredVosol(int position);
		void getBank(String codeBank);							  
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getDetailsCheckBargashti(String shomarehSanad , int ccDarajeh ,int ccNoeMoshtary);
        void checkInsertCheckPishDrayaft(int ccMoshtary  , int codeNoeVosolMoshtary , int flagInputHesab , String mablaghMandeh , String nameNoeVosol , DariaftPardakhtPPCModel dariaftPardakhtPPCModel);
       void getVosolsPishDariaft(long ccMoshtary);
        void removeItemPishDaryaft(DariaftPardakhtPPCModel dariaftPardakhtPPCModel , int position , int from);

    }

}
