package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.InvoiceSettlementMVP;
import com.saphamrah.MVP.Model.InvoiceSettlementModel;
import com.saphamrah.Model.BankModel;
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
    public void getInfo(long ccDarkhastFaktor)
    {
        mModel.getInfo(ccDarkhastFaktor);
    }

    @Override
    public void getVosols(long ccDarkhastFaktor)
    {
        mModel.getVosols(ccDarkhastFaktor);
    }

    @Override
    public void getNoeVosols(long ccDarkhastFaktor , boolean getNoeVosolFromShared , boolean isFromTreasuryList)
    {
        mModel.getNoeVosols(ccDarkhastFaktor , getNoeVosolFromShared , isFromTreasuryList);
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
    public void checkRemainCost(int codeNoeVosolMoshtary , String mablaghMandeh)
    {
        if (codeNoeVosolMoshtary > 0)
        {
            if (mablaghMandeh.trim().equals("0"))
            {
                mView.get().showZeroRemainCostAlert();
            }
            else
            {
                mView.get().onSuccessCheckRemainCost();
            }
        }
        else
        {
            mView.get().showToast(R.string.errorInvalidSelectedNoeVosol, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkInsert(int ccMoshtary , long ccDarkhastFaktor , int codeNoeVosolMoshtary , int flagInputHesab, String mablaghMandeh , DariaftPardakhtPPCModel dariaftPardakhtPPCModel , ArrayList<ParameterChildModel> childParameterModelsNoeVosol)
    {
        if (codeNoeVosolMoshtary == 0)
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            String nameNoeVosol = "";
            String strCodeNoeVosol = String.valueOf(codeNoeVosolMoshtary);
            for (ParameterChildModel model : childParameterModelsNoeVosol)
            {
                if (strCodeNoeVosol.equals(model.getValue()))
                {
                    nameNoeVosol = model.getTxt();
                    break;
                }
            }
            mModel.checkInsert(ccMoshtary, ccDarkhastFaktor, codeNoeVosolMoshtary, flagInputHesab , mablaghMandeh , nameNoeVosol , dariaftPardakhtPPCModel);
        }
    }

    @Override
    public void removeItem(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel , int position)
    {
        if (dariaftPardakhtDarkhastFaktorPPCModel != null && dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakhtDarkhastFaktor() > 0)
        {
            mModel.removeItem(dariaftPardakhtDarkhastFaktorPPCModel , position);
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
            try
            {
                hesabNumber = BankUtils.getBankAccountNumber(dataOfCheck[1]);
                codeBank = BankUtils.getBankCode(dataOfCheck[1]);
                codeShobe = BankUtils.getBranchCode(dataOfCheck[2]);
                sayyadId = dataOfCheck[3];
                mView.get().showDataOfCheckFromScanner(hesabNumber, codeShobe, sayyadId);
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


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetConfig(String showSaayadCheckInfoScanner)
    {
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
    public void onGetInfo(String shomareDarkhast , int ccNoeVosol, String noeVosol, String cost, String remainCost)
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

        if (shomareDarkhast == null || ccNoeVosol <= 0 || noeVosol.trim().equals("") || cost.trim().equals("") || remainCost.trim().equals(""))
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetVosols(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels)
    {
        if (dariaftPardakhtDarkhastFaktorPPCModels != null && dariaftPardakhtDarkhastFaktorPPCModels.size() > 0)
        {
            mView.get().onGetVosols(dariaftPardakhtDarkhastFaktorPPCModels);
        }
    }

    @Override
    public void onGetNoeVosols(ArrayList<ParameterChildModel> childParameterModels)
    {
        if (childParameterModels.size() > 0)
        {
            mView.get().onGetNoeVosols(childParameterModels);
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


}
