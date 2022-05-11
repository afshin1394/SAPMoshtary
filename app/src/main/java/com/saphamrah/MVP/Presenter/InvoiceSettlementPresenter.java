package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.InvoiceSettlementMVP;
import com.saphamrah.MVP.Model.InvoiceSettlementModel;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.ConfigNoeVosolMojazeFaktorModel;
import com.saphamrah.Model.ConfigNoeVosolMojazeMoshtaryModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.PosShomarehHesabModel;
import com.saphamrah.PubFunc.BankUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class InvoiceSettlementPresenter implements InvoiceSettlementMVP.PresenterOps , InvoiceSettlementMVP.RequiredPresenterOps
{

    private WeakReference<InvoiceSettlementMVP.RequiredViewOps> mView;
    private InvoiceSettlementMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public InvoiceSettlementPresenter(InvoiceSettlementMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new InvoiceSettlementModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(InvoiceSettlementMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }
	
	@Override
    public void getConfig()
    {
        mModel.getConfig();
    }

    @Override
    public void checkBottomBarClick(int position)
    {
        switch (position)
        {
            case 5:
                mView.get().openEmzaMoshtaryActivity();
                break;
        }
    }

    @Override
    public void getInfo(long ccDarkhastFaktor , int from)
    {
        mModel.getInfo(ccDarkhastFaktor , from);
    }

    @Override
    public void getVosols(long ccDarkhastFaktor, int ccMoshtary)
    {
        mModel.getVosols(ccDarkhastFaktor, ccMoshtary);
    }

    @Override
    public void getVosolsPishDariaft(long ccMoshtary) {
        mModel.getVosolsPishDariaft(ccMoshtary);
    }

    @Override
    public void getNoeVosols(long ccDarkhastFaktor , boolean getNoeVosolFromShared, int from , int ccMoshtary)
    {
        mModel.getNoeVosols(ccDarkhastFaktor , getNoeVosolFromShared , from , ccMoshtary);
    }

    @Override
    public void checkSelectedNoeVosol(String codeNoeVosol , int ccMoshtary , long ccDarkhastFaktor, double mablaghMandeh)
    {
        int intCodeNoeVosol = -1;
        try
        {
            intCodeNoeVosol = Integer.parseInt(codeNoeVosol);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "checkSelectedNoeVosol", "");
        }

        if (intCodeNoeVosol != -1)
        {
            if (codeNoeVosol.equals(Constants.VALUE_VAJH_NAGHD()))
            {
                mView.get().showVosolNaghdInfo(intCodeNoeVosol);
            }
            else if (codeNoeVosol.equals(Constants.VALUE_POS()))
            {
                mModel.getPosShomareHesab(intCodeNoeVosol);
            }
            else if (codeNoeVosol.equals(Constants.VALUE_IRANCHECK()))
            {
                mModel.getNoeIranCheck(intCodeNoeVosol);
            }
            else if (codeNoeVosol.equals(Constants.VALUE_FISH_BANKI()))
            {
                mModel.getFishBanki(intCodeNoeVosol);
            }
            else if (codeNoeVosol.equals(Constants.VALUE_CHECK()))
            {
                mModel.getMoshtaryHesab(ccMoshtary , ccDarkhastFaktor, intCodeNoeVosol , mablaghMandeh);
            }
            else if (codeNoeVosol.equals(Constants.VALUE_RESID()))
            {
                mView.get().showVosolResidInfo(intCodeNoeVosol);
            }
        }
    }

    @Override
    public void checkRemainCost(int codeNoeVosolMoshtary , String mablaghMandeh ,int from)
    {
        if (codeNoeVosolMoshtary > 0)
        {
            if (from == Constants.FROM_TREASURYLIST  || from == -1){
                if (mablaghMandeh.trim().equals("0"))
                {
                    mView.get().showZeroRemainCostAlert();
                }
                else
                {
                    mView.get().onSuccessCheckRemainCost();
                }
            } else  if (from == Constants.FROM_PISH_DARYAFT){
                mView.get().onSuccessCheckRemainCost();
            } else if (from == Constants.FROM_CHECK_BARGASHTI){
                if (mablaghMandeh.trim().equals("0"))
                {
                    mView.get().canNotAdd();
                }
                else
                {
                    mView.get().onSuccessCheckRemainCost();
                }
            }

        }
        else
        {
            mView.get().showToast(R.string.errorInvalidSelectedNoeVosol, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkInsert(int ccMoshtary , long ccDarkhastFaktor , int codeNoeVosolMoshtary , int flagInputHesab, String mablaghMandeh , DariaftPardakhtPPCModel dariaftPardakhtPPCModel , ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels)
    {
        if (codeNoeVosolMoshtary == 0)
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            String nameNoeVosol = "";
            String strCodeNoeVosol = String.valueOf(codeNoeVosolMoshtary);
            for (ConfigNoeVosolMojazeFaktorModel model : configNoeVosolMojazeFaktorModels)
            {

                if (strCodeNoeVosol.equals(String.valueOf(model.getCodeNoeVosol_Tablet())))
                {
                    nameNoeVosol = model.getTxtNoeVosol();
                    break;
                }
            }
            mModel.checkInsert(ccMoshtary, ccDarkhastFaktor, codeNoeVosolMoshtary, flagInputHesab , mablaghMandeh , nameNoeVosol , dariaftPardakhtPPCModel);
        }
    }

    @Override
    public void checkInsertCheckBargashty(int ccMoshtary, long ccDarkhastFaktor, int codeNoeVosolMoshtary, int flagInputHesab, String mablaghMandeh, DariaftPardakhtPPCModel dariaftPardakhtPPCModel, ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModelArrayList) {
        if (codeNoeVosolMoshtary == 0)
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            String nameNoeVosol = "";
            int strCodeNoeVosol = codeNoeVosolMoshtary;
            for (ConfigNoeVosolMojazeMoshtaryModel model : configNoeVosolMojazeMoshtaryModelArrayList)
            {
                if (strCodeNoeVosol== model.getCodeNoeVosol_Tablet())
                {
                    nameNoeVosol = model.getTxtNoeVosol();
                    break;
                }
            }
            mModel.checkInsertCheckBargashty(ccMoshtary, ccDarkhastFaktor, codeNoeVosolMoshtary, flagInputHesab , mablaghMandeh , nameNoeVosol , dariaftPardakhtPPCModel);
        }
    }

    @Override
    public void checkInsertCheckPishDrayaft(int ccMoshtary , int codeNoeVosolMoshtary, int flagInputHesab, String mablaghMandeh, DariaftPardakhtPPCModel dariaftPardakhtPPCModel, ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModelArrayList) {

        String nameNoeVosol = "";
        int strCodeNoeVosol = codeNoeVosolMoshtary;
        for (ConfigNoeVosolMojazeMoshtaryModel model : configNoeVosolMojazeMoshtaryModelArrayList)
        {
            if (strCodeNoeVosol== model.getCodeNoeVosol_Tablet())
            {
                nameNoeVosol = model.getTxtNoeVosol();
                break;
            }
        }
        mModel.checkInsertCheckPishDrayaft(ccMoshtary, codeNoeVosolMoshtary, flagInputHesab , mablaghMandeh , nameNoeVosol , dariaftPardakhtPPCModel);


    }





    @Override
    public void removeItem(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel , int position , int from,long ccDarkhastFaktor, int ccMoshtary)
    {
        if (dariaftPardakhtDarkhastFaktorPPCModel != null && dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakhtDarkhastFaktor() > 0)
        {
            mModel.removeItem(dariaftPardakhtDarkhastFaktorPPCModel , position , from, ccDarkhastFaktor, ccMoshtary);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void removeItemPishDaryaft(DariaftPardakhtPPCModel dariaftPardakhtPPCModel, int position, int from) {
        if (dariaftPardakhtPPCModel != null )
        {
            mModel.removeItemPishDaryaft(dariaftPardakhtPPCModel , position , from);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }



    @Override
    public void checkRegisteredVosol(int position)
    {
        if (position == 0)
        {
            PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
            bottomBarConfig.getConfig(getAppContext());
            if (bottomBarConfig.getShowBarkhordAvalie())
            {
                mView.get().openBarkhordAvalieActivity();
            }
            else
            {
                mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        else if (position == 1 || position == 2 || position == 3)
        {
            mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else if (position == 5)
        {
            mModel.checkRegisteredVosol(position);
        }
    }

	@Override
    public void checkDataFromScanner(String data)
    {
        if (data != null && !data.trim().equals(""))
        {
            String[] dataOfCheck = data.split("\n");
            String hesabNumber = "";
            String codeBank = "";
            String codeShobe = "";
            String sayyadId = "";
            String checkNumber = "";
            try
            {

                hesabNumber = BankUtils.getBankAccountNumber(dataOfCheck[3]);
                codeBank = BankUtils.getBankCode(dataOfCheck[3]);
                codeShobe = BankUtils.getBranchCode(dataOfCheck[4]);
                checkNumber = BankUtils.getNumber(dataOfCheck[5]);
                sayyadId = dataOfCheck[6];
                mView.get().showDataOfCheckFromScanner(hesabNumber, codeShobe, sayyadId , checkNumber);
                mModel.getBank(codeBank);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                mModel.setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "InvoiceSettlementPresenter", "", "checkDataFromScanner", "");
                mView.get().showToast(R.string.notFoundData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }
	
    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }

    @Override
    public void getDetailsCheckBargashti(String shomarehSanad , int ccDarajeh ,int ccNoeMoshtary) {
        mModel.getDetailsCheckBargashti(shomarehSanad,ccDarajeh , ccNoeMoshtary);
    }




    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetConfig(String showSaayadCheckInfoScanner)
    {
        Log.i("showSaayadCheckI" ,"showSaayadCheckInfoScanner : " + showSaayadCheckInfoScanner);
        // 8 = View.GONE , 0 = View.VISIBLE
        if (showSaayadCheckInfoScanner == null || showSaayadCheckInfoScanner.trim().equals("") || showSaayadCheckInfoScanner.trim().equals("0"))
        {
            mView.get().showSayyadCheckScanner(8);
            Log.d("showSaayadCheckI","View.GONE");
        }
        else
        {
            mView.get().showSayyadCheckScanner(0);
            Log.d("showSaayadCheckI","View.VISIBLE");
        }
    }

    @Override
    public void onGetInfo(String shomareDarkhast , int ccNoeVosol, String noeVosol, String cost, String remainCost , int from)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (!remainCost.trim().equals(""))
        {
            try
            {
                remainCost = formatter.format(Double.parseDouble(remainCost));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        if (!cost.trim().equals(""))
        {
            try
            {
                cost = formatter.format(Double.parseDouble(cost));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        if (shomareDarkhast != null)
        {
            mView.get().onGetInfo(shomareDarkhast, ccNoeVosol, noeVosol, cost, remainCost);
        }

        Log.d("invoce" , "shomareDarkhast : " + shomareDarkhast + " , ccNoeVosol : " + ccNoeVosol + " , cost : " + cost + " , remainCost : " + remainCost);

        if (shomareDarkhast == null || ccNoeVosol <= 0 || noeVosol.trim().equals("") || cost.trim().equals("") || remainCost.trim().equals("") )
        {
            if (from !=Constants.FROM_PISH_DARYAFT && from !=Constants.FROM_CHECK_BARGASHTI)
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetVosols(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels)
    {
        if (dariaftPardakhtDarkhastFaktorPPCModels != null) //&& dariaftPardakhtDarkhastFaktorPPCModels.size() > 0)
        {
            mView.get().onGetVosols(dariaftPardakhtDarkhastFaktorPPCModels);
        }
    }

    @Override
    public void onGetVosolsPishDaryaft(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels) {
        if (dariaftPardakhtPPCModels !=null ){
            mView.get().onGetVosolsPishDaryaft(dariaftPardakhtPPCModels);
        }

    }

    @Override
    public void oncallTajil(long mablaghTajil_Naghd, long mandehFaktorPasAzTajil_Naghd, long mablaghTajil_Check, long mandehFaktorPasAzTajil_Check,boolean canGetTajil) {
        mView.get().oncallTajil(mablaghTajil_Naghd,mandehFaktorPasAzTajil_Naghd , mablaghTajil_Check,mandehFaktorPasAzTajil_Check,canGetTajil);
    }

    @Override
    public void onVisibilityLayoutTajil(boolean visible) {
        mView.get().onVisibilityLayoutTajil(visible);
    }

//    @Override
//    public void onGetVosolsPishDariaft(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels)
//    {
//        if (dariaftPardakhtDarkhastFaktorPPCModels != null && dariaftPardakhtDarkhastFaktorPPCModels.size() > 0)
//        {
//            mView.get().onGetVosols(dariaftPardakhtDarkhastFaktorPPCModels);
//        }
//    }

    @Override
    public void onGetNoeVosols(ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels)
    {
        if (configNoeVosolMojazeFaktorModels.size() > 0)
        {
            mView.get().onGetNoeVosols(configNoeVosolMojazeFaktorModels);
        }
        else
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }



    @Override
    public void onGetPosShomareHesab(ArrayList<PosShomarehHesabModel> posShomarehHesabModels , int codeNoevosol)
    {
        mView.get().showVosolPOSInfo(posShomarehHesabModels , codeNoevosol);
    }

    @Override
    public void onGetFishBanki(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels , int codeNoevosol)
    {
        mView.get().showVosolFishBankiInfo(markazShomarehHesabModels , codeNoevosol);
    }

    @Override
    public void onGetMoshtaryHesab(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels, ArrayList<BankModel> bankModels , int codeNoevosol , String tarikhSarResidForCheckShamsi , String tarikhSarResidForCheckGregorian)
    {
        mView.get().showVosolCheckInfo(moshtaryShomarehHesabModels, bankModels, codeNoevosol, tarikhSarResidForCheckShamsi, tarikhSarResidForCheckGregorian);
    }

    @Override
    public void onGetNoeIranCheck(ArrayList<ParameterChildModel> childParameterModels , int codeNoevosol)
    {
        mView.get().showVosolIranCheckInfo(childParameterModels , codeNoevosol);
    }

    @Override
    public void onErrorCheckInsert(int errorResId)
    {
        mView.get().showAlert(errorResId , Constants.FAILED_MESSAGE());
    }

    @Override
    public void onErrorCheckInsert(String errorsText)
    {
        mView.get().showAlert(errorsText , Constants.FAILED_MESSAGE());
    }

    @Override
    public void onSuccessInsert(long mablaghMandeFaktor)
    {
        mView.get().onSuccessInsert(mablaghMandeFaktor);
    }

    @Override
    public void onSuccessRemoveItem(int position , long mablaghMandeh)
    {
        mView.get().onSuccessRemove(position , mablaghMandeh);
    }

    @Override
    public void onFailedRemoveItem()
    {
        mView.get().showToast(R.string.failedOps, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessCheckRegisteredVosol(int position)
    {
        checkBottomBarClick(position);
    }

	@Override
    public void onGetBank(BankModel bankModel)
    {
        if (bankModel != null)
        {
            mView.get().onGetBank(bankModel);
        }
    }
	
    @Override
    public void onError(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetDetailsCheckBargashti(ArrayList<BargashtyModel> bargashtyModels) {
        mView.get().onGetDetailsCheckBargashti(bargashtyModels);
    }

    @Override
    public void onGetNoeVosolsMojazMoshtary(ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels) {
        mView.get().onGetNoeVosolsMojazMoshtary(configNoeVosolMojazeMoshtaryModels);
    }




}
