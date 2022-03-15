package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.TreasuryListMVP;
import com.saphamrah.MVP.Model.TreasuryListModel;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ElatAdamTahvilDarkhastModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TreasuryListPresenter implements TreasuryListMVP.PresenterOps , TreasuryListMVP.RequiredPresenterOps
{

    private WeakReference<TreasuryListMVP.RequiredViewOps> mView;
    private TreasuryListMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public TreasuryListPresenter(TreasuryListMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new TreasuryListModel(this);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(TreasuryListMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void checkDateAndFakeLocation(int state)
    {
        mModel.checkDateAndFakeLocation(state);
    }

    @Override
    public void getTreasuryList(int faktorRooz , int sortType)
    {
        mModel.getTreasuryList(faktorRooz, sortType);
    }

    @Override
    public void getTreasuryListWithRouting(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels)
    {
        mModel.getTreasuryListWithRouting(darkhastFaktorMoshtaryForoshandeModels);
    }

    @Override
    public void getDariaftPardakhtForSend(long ccDarkhastFaktor , int codeVazeiat , int position)
    {
        if (codeVazeiat < 6)
        {
            mView.get().showToast(R.string.errorFirstSendFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mView.get().showLoading();
            mModel.getDariaftPardakhtForSend(ccDarkhastFaktor , position);
        }
    }

    @Override
    public void getCustomerLocation(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        mModel.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModel);
    }

    @Override
    public void getFaktorImage(long ccDarkhastFaktor)
    {
        mModel.getFaktorImage(ccDarkhastFaktor);
    }

    @Override
    public void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        mModel.setDarkhastFaktorShared(darkhastFaktorMoshtaryForoshandeModel);
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
    public void onDestroy(boolean isChangingConfig) {
        mModel.onDestroy();
    }

    @Override
    public void checkMoshtaryKharejAzMahal(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel,int position) {
        mModel.checkMohtaryKharejAzMahal(darkhastFaktorMoshtaryForoshandeModel , position);

    }

    @Override
    public void updateGpsData() {
        mModel.updateGpsData();
    }

    @Override
    public void checkIsLocationSendToServer(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel) {


        mModel.checkIsLocationSendToServer(darkhastFaktorMoshtaryForoshandeModel);

    }

    @Override
    public void searchCustomer(String searchWord, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels) {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> searchResultModel = new ArrayList<>();
        for (DarkhastFaktorMoshtaryForoshandeModel model : darkhastFaktorMoshtaryForoshandeModels)
        {
            if (model.getFullNameMoshtary().contains(searchWord))
            {
                searchResultModel.add(model);
            }
        }
        mView.get().onGetSearchResult(searchResultModel);
    }

    @Override
    public void getElatAdamTahvilDarkhast(long ccDarkhastFaktor,int position) {
        mModel.getElatAdamTahvilDarkhast(ccDarkhastFaktor,position);
    }



    @Override
    public void sendElatAdamTahvilDarkhast(ElatAdamTahvilDarkhastModel elatAdamTahvilDarkhastModel, DarkhastFaktorModel darkhastFaktorModel,int position) {
        mModel.sendElatAdamTahvilDarkhast(elatAdamTahvilDarkhastModel,darkhastFaktorModel,position);
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onErrorUseFakeLocation()
    {
        mView.get().closeLoading();
        mView.get().onError(true , R.string.errorFakeLocation);
    }

    @Override
    public void onCheckServerTime(int state,boolean valid, String message,int sortList)
    {
        if (valid)
        {
            mModel.getTreasuryList(state , sortList);
        }
        else
        {
            mView.get().onError(true, message);
        }

    }

    @Override
    public void onGetCustomerAddress(double latitude , double longitude)
    {
        if (latitude > 0 && longitude > 0)
        {
            mView.get().onGetCustomerAddress(latitude , longitude);
        }
        else
        {
            mView.get().showToast(R.string.errorFindCustomerAddress, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetFaktorImage(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel)
    {
        if (darkhastFaktorEmzaMoshtaryModel != null && darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage() != null && darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage().length > 0)
        {
            mView.get().onGetFaktorImage(darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage());
        }
        else
        {
            mView.get().showToast(R.string.notFoundAnyFaktorImage, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onFailedSetDarkhastFaktorShared(int resId)
    {
        mView.get().closeLoading();
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }

    @Override
    public void onSuccessSetDarkhastFaktorShared(long ccDarkhastFaktor , int ccMoshtary)
    {
        mView.get().openDarkhastKalaActivity(ccDarkhastFaktor , ccMoshtary);
    }

    @Override
    public void onWarningSetDarkhastFaktorShared(int resId)
    {
        mView.get().showToast(resId, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessUpdateMandeMojodiMashin()
    {
        mView.get().showToast(R.string.updateMandeMojodi, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedUpdateMandeMojodiMashin()
    {
        mView.get().showToast(R.string.errorUpdateMandeMojodi, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int faktorRooz , int noeMasouliat,int sort)
    {
        if (faktorRooz == 0)
        {

            if (darkhastFaktorMoshtaryForoshandeModels.size() > 0)
            {
                mView.get().onGetFaktorRooz(darkhastFaktorMoshtaryForoshandeModels , noeMasouliat , sort);
            }
            else
            {
                mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        else
        {

            if (darkhastFaktorMoshtaryForoshandeModels.size() > 0)
            {
                mView.get().onGetFaktorMandeDar(darkhastFaktorMoshtaryForoshandeModels , noeMasouliat);
            }
            else
            {
                mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
            }
        }
    }


    @Override
    public void onGetTreasuryListWithRouting(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int noeMasouliat)
    {
        mView.get().onGetTreasuryListWithRouting(darkhastFaktorMoshtaryForoshandeModels , noeMasouliat);
    }

    @Override
    public void onErrorSend(int resId)
    {
        mView.get().closeLoading();
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onSuccessSend(int position)
    {
        mView.get().closeLoading();
        mView.get().showAlertMessage(R.string.successSendData , Constants.SUCCESS_MESSAGE());
        mView.get().onSuccessSend(position);
    }


    @Override
    public void onErrorAccessToLocation()
    {
        mView.get().closeLoading();
        mView.get().showToast(R.string.errorAccessToLocation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onErrorGetCustomerLocation(int resId, String customerName)
    {
        mView.get().closeLoading();
        mView.get().showAlertMessage(resId , customerName , Constants.FAILED_MESSAGE());
    }

    @Override
    public void onError(int resId)
    {
        mView.get().closeLoading();
        mView.get().showAlertMessage(resId , Constants.FAILED_MESSAGE());
    }

    @Override
    public void onErrorSendRasGiri(String error) {
        mView.get().closeLoading();
        mView.get().onErrorSendRasGiri(error);
    }

    @Override
    public void openInvoiceSettlement(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel,boolean openInvoiceSettlement) {
        if (openInvoiceSettlement)
            mView.get().openInvoiceSettlementActivity(darkhastFaktorMoshtaryForoshandeModel);
        else
            mView.get().showAlertMessage(R.string.errorMamorPakhshLocationsNotSent, Constants.FAILED_MESSAGE());
    }

    @Override
    public void closeLoading() {
        mView.get().closeLoading();
    }

    @Override
    public void onGetElatAdamTahvilDarkhast(ArrayList<ElatAdamTahvilDarkhastModel> models,  DarkhastFaktorModel darkhastFaktorModel,int position) {
        ArrayList<String> elatAdamTahvilDarkhastTitles = new ArrayList<>();
        for (ElatAdamTahvilDarkhastModel model : models)
        {
            elatAdamTahvilDarkhastTitles.add(model.getNameNoeVorod());
        }
        mView.get().onGetElatAdamTahvilDarkhast(models,elatAdamTahvilDarkhastTitles,darkhastFaktorModel,position);
    }


    @Override
    public void onSuccess(int resId) {
        closeLoading();
        mView.get().showAlertMessage(resId,Constants.SUCCESS_MESSAGE());
    }

    @Override
    public void onSuccessLocation(int resId, int position) {
        closeLoading();
        mView.get().showAlertMessage(resId,Constants.SUCCESS_MESSAGE());
        mView.get().onSuccessLocation(position);
    }
}
