package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.MarjoeeKalaMVP;
import com.saphamrah.MVP.Model.MarjoeeKalaModel;
import com.saphamrah.Model.ElamMarjoeeSatrPPCModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MarjoeeKalaPresenter implements MarjoeeKalaMVP.PresenterOps , MarjoeeKalaMVP.RequiredPresenterOps
{

    private WeakReference<MarjoeeKalaMVP.RequiredViewOps> mView;
    private MarjoeeKalaMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public MarjoeeKalaPresenter(MarjoeeKalaMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MarjoeeKalaModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(MarjoeeKalaMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getForoshandehMamorPakhshInfo()
    {
        mModel.getForoshandehMamorPakhshInfo();
    }

    @Override
    public void checkFaktorDetails(long ccDarkhastFaktor, int ccMoshtary)
    {
        mView.get().showAlertLoading();
        if (ccDarkhastFaktor == -1 || ccMoshtary == -1)
        {
            mView.get().showAlert(R.string.errorInvalidFaktorDetail, Constants.FAILED_MESSAGE(), true);
        }
    }

    @Override
    public void updateListKalaForMarjoee(long ccDarkhastFaktor)
    {
        mModel.updateListKalaForMarjoee(ccDarkhastFaktor);
    }

    @Override
    public void getListElatMarjoee()
    {
        mModel.getListElatMarjoee();
    }

    @Override
    public void getKalaMarjoee(long ccDarkhastFaktor)
    {
        if (ccDarkhastFaktor > 0)
        {
            mModel.getKalaMarjoee(ccDarkhastFaktor);
        }
    }

    @Override
    public void checkKalaForAddToMarjoee(ListKalaForMarjoeeModel selectedKala,ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels, ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel, int ccMoshtary, String count)
    {
        int intCount = 0;
        int intCountSelectedKala=0;
        if (ccMoshtary <= 0)
        {
            mView.get().onErrorAddToMarjoee(R.string.errorSelectCustomer);
            return;
        }
        try
        {//TODO:Marjoee
            intCount = Integer.parseInt(count);
            intCountSelectedKala = selectedKala.getTedad();
            elamMarjoeeSatrPPCModel.setTedad3(intCount);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mView.get().onErrorAddToMarjoee(R.string.errorInputCountMarjoee);
            return;
        }
        Log.d("MarjoeeKala","intCount:"+intCount+" , intCountSelectedKala:"+intCountSelectedKala);
        if (intCountSelectedKala < intCount)
        {
            mView.get().onErrorAddToMarjoee(R.string.errorMoreCountMarjoee);
            return;
        }
        if (intCount <= 0)
        {
            mView.get().onErrorAddToMarjoee(R.string.errorInputCountMarjoee);
            return;
        }


        if (elamMarjoeeSatrPPCModel == null)
        {
            mView.get().onErrorAddToMarjoee(R.string.errorInputData);
            return;
        }
        else
        {
            if (elamMarjoeeSatrPPCModel.getCcDarkhastFaktor() <= 0)
            {
                mView.get().onErrorAddToMarjoee(R.string.errorFindccDarkhastFaktor);
                return;
            }
            if (elamMarjoeeSatrPPCModel.getCcElatMarjoeeKala() <= 0 || elamMarjoeeSatrPPCModel.getCodeNoeMarjoee() <= 0)
            {
                mView.get().onErrorAddToMarjoee(R.string.errorSelectElatMarjooe);
                return;
            }
            if (elamMarjoeeSatrPPCModel.getCcKala()<0 || elamMarjoeeSatrPPCModel.getCcKalaCode()<=0 || elamMarjoeeSatrPPCModel.getShomarehBach().trim().length()==0 ||
                    elamMarjoeeSatrPPCModel.getTarikhTolid().trim().replace("/" , "").length()==0 || elamMarjoeeSatrPPCModel.getGheymatMasrafkonandeh()<=0 ||
                    elamMarjoeeSatrPPCModel.getTarikhEngheza().trim().replace("/" , "").length()==0 ||
                    elamMarjoeeSatrPPCModel.getFee()<=0 || elamMarjoeeSatrPPCModel.getCcTaminkonandeh()<=0)
            {
                mView.get().onErrorAddToMarjoee(R.string.errorSelectccKala);
                return;
            }
            boolean isDuplicate = false;
            for (KalaElamMarjoeeModel model : kalaElamMarjoeeModels)
            {
                if (model.getCcKalaCode() == elamMarjoeeSatrPPCModel.getCcKalaCode() && model.getShomarehBach().equals(elamMarjoeeSatrPPCModel.getShomarehBach()))
                {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate)
            {
                mView.get().onErrorAddToMarjoee(R.string.errorDuplicateKala);
            }
            else
            {
                mModel.insertKalaToMarjoee(elamMarjoeeSatrPPCModel , ccMoshtary);
            }
        }

    }

    @Override
    public void checkRemoveKalaFromMarjoee(KalaElamMarjoeeModel kalaElamMarjoeeModel, int position)
    {
        if (position >= 0 && kalaElamMarjoeeModel != null && kalaElamMarjoeeModel.getCcElamMarjoeePPC() >= 0)
        {
            mModel.removeKalaFromMarjoee(kalaElamMarjoeeModel , position);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkUpdateCountOfMarjoee(long ccDarkhastFaktor, int ccElamMarjoeeSatr, int oldCount, int newCount)
    {
        if (ccElamMarjoeeSatr <= 0)
        {
            mView.get().showAlert(R.string.notFoundccMarjoee, Constants.FAILED_MESSAGE(), false);
        }
        else if (newCount <= 0)
        {
            mView.get().showAlert(R.string.errorZeroCount, Constants.FAILED_MESSAGE(), false);
        }
        else
        {
            mModel.updateCountOfMarjoee(ccDarkhastFaktor, ccElamMarjoeeSatr, newCount);
        }
    }

    @Override
    public void insertDariaftPardakht(long ccDarkhastFaktor , int ccMoshtary)
    {
        mModel.insertDariaftPardakht(ccDarkhastFaktor, ccMoshtary);
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
    public void onGetForoshandehMamorPakhshInfo(int noeMasouliat , int noeSabtMarjoee)
    {
        if (noeSabtMarjoee <= 0)
        {
            mView.get().showAlert(R.string.errorNoeSabtMarjoee, Constants.FAILED_MESSAGE(), true);
        }
        else
        {
            mView.get().onGetForoshandehMamorPakhshInfo(noeMasouliat , noeSabtMarjoee);
        }
    }

    @Override
    public void onErrorUpdateListKalaForMarjoee(String message)
    {
        mView.get().closeAlertLoading();
        mView.get().showToast(message, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessUpdateListKalaForMarjoee(ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels)
    {
        mView.get().closeAlertLoading();
        mView.get().onUpdateListKalaForMarjoee(listKalaForMarjoeeModels);
    }

    @Override
    public void onGetListElatMarjoeeKala(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels)
    {
        if (elatMarjoeeKalaModels != null && elatMarjoeeKalaModels.size() > 0)
        {
            mView.get().onGetListElatMarjoeeKala(elatMarjoeeKalaModels);
        }
        else
        {
            mView.get().showToast(R.string.errorGetElatMarjoee, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetKalaMarjoee(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels)
    {
        if (kalaElamMarjoeeModels != null && kalaElamMarjoeeModels.size() > 0)
        {
            mView.get().onGetKalaMarjoee(kalaElamMarjoeeModels);
        }
    }

    @Override
    public void onFailedInsertMarjoee()
    {
        mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onSuccessInsertMarjoee()
    {
        mView.get().onSuccessInsertMarjoee();
    }


    @Override
    public void onSuccessRemoveItem(int position)
    {
        mView.get().onSuccessRemoveItem(position);
    }

    @Override
    public void onSuccessInsertDariaftPardakht()
    {
        mView.get().onSuccessInsertDariaftPardakht();
    }

    @Override
    public void onFailedInsertDariaftPardakht()
    {
        mView.get().showToast(R.string.errorInsertDariaftPardakhtMarjoee, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedRemoveItem()
    {
        mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onUpdateCount(boolean status)
    {
        if (status)
        {
            mView.get().showToast(R.string.updateSuccessed, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mView.get().showToast(R.string.updateFailed, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }
}
