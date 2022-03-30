package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.TreasuryListMapMVP;
import com.saphamrah.MVP.Model.TreasuryListMapModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ElatAdamTahvilDarkhastModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;

import org.osmdroid.util.GeoPoint;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TreasuryListMapPresenter implements TreasuryListMapMVP.PresenterOps , TreasuryListMapMVP.RequiredPresenterOps
{

    private TreasuryListMapMVP.ModelOps mModel;
    private WeakReference<TreasuryListMapMVP.RequiredViewOps> mView;


    public TreasuryListMapPresenter(TreasuryListMapMVP.RequiredViewOps viewOps)
    {
        mModel = new TreasuryListMapModel(this);
        mView = new WeakReference<>(viewOps);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(TreasuryListMapMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

	@Override
    public void getNoeMasouliat()
    {
        mModel.getNoeMasouliat();
    }
	
    @Override
    public void checkFakeLocationAndDateTime()
    {
        mModel.checkFakeLocationAndDateTime();
    }

    @Override
    public void getCustomers(int sortType)
    {
        if (sortType == Constants.SORT_TREASURY_BY_CUSTOMER_CODE)
        {
            mModel.getCustomersOrderByCode();
        }
        else if (sortType == Constants.SORT_TREASURY_BY_ROUTING)
        {
            mModel.getCustomersOrderByRouting();
        }
    }

    @Override
    public void getCustomerFaktors(DarkhastFaktorMoshtaryForoshandeModel customerInfo, MoshtaryAddressModel moshtaryAddressModel, String customerPriority)
    {
        Log.i("getCustomerFaktorsLog", "getCustomerFaktors: "+"customerInfo"+customerInfo  +"moshtaryAddressModel"+ moshtaryAddressModel);
        if (customerInfo != null && customerInfo.getCcMoshtary() > 0)
        {
            mModel.getCustomerFaktors(customerInfo, moshtaryAddressModel, customerPriority);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void getTodayTreasuryList(int sortType)
    {
        if (sortType == Constants.SORT_TREASURY_BY_ROUTING)
        {
            mModel.getTodayTreasuryListByRouting();
        }
        else if (sortType == Constants.SORT_TREASURY_BY_CUSTOMER_CODE)
        {
            mModel.getTodayTreasuryListByCustomerCode();
        }
    }

    @Override
    public void getFaktorImage(long ccDarkhastFaktor)
    {
        if (ccDarkhastFaktor > 0)
        {
            mModel.getFaktorImage(ccDarkhastFaktor);
        }
        else
        {
            mView.get().showToast(R.string.errorFindccDarkhastFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        if (darkhastFaktorMoshtaryForoshandeModel.isCanEditDarkhast())
        {
            mModel.setDarkhastFaktorShared(darkhastFaktorMoshtaryForoshandeModel);
        }
        else
        {
            mView.get().showToast(R.string.errorCantEditDarkhastAgain, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void sendDariaftPardakht(long noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        if ((noeMasouliat == 4 && darkhastFaktorMoshtaryForoshandeModel.getCodeVazeiat() == 99) || (noeMasouliat == 5 && darkhastFaktorMoshtaryForoshandeModel.getExtraProp_IsSend() == 0 && darkhastFaktorMoshtaryForoshandeModel.getCodeVazeiat() < 6))
        {
            mView.get().showToast(R.string.errorFirstSendFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mModel.sendDariaftPardakht(darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor());
        }
    }

    @Override
    public void routingFromCurrentLocation(int ccMoshtary, String customerName, double desLatitude , double desLongitude)
    {
        if (desLatitude > 0 && desLongitude > 0)
        {
            mModel.routingFromCurrentLocation(ccMoshtary, customerName, desLatitude, desLongitude);
        }
        else
        {
            mView.get().showAlertDialog(R.string.errorGetCustomerLocation, customerName, Constants.FAILED_MESSAGE());
        }
    }

    @Override
    public void checkEditTreasury(int noeMasouliat , DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        if ((noeMasouliat == 4 && darkhastFaktorMoshtaryForoshandeModel.getCodeVazeiat() == 99) || (noeMasouliat == 5 && darkhastFaktorMoshtaryForoshandeModel.getExtraProp_IsSend() == 0 && darkhastFaktorMoshtaryForoshandeModel.getCodeVazeiat() < 6))
        {
            mView.get().showToast(R.string.errorFirstSendFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mView.get().openInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary(), darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor());
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {
      mModel.onDestroy();
    }

    @Override
    public void getSortList() {
        mModel.getSortList();
    }

    @Override
    public void checkMoshtaryKharejAzMahal(int noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel) {
        mView.get().showLoadingDialog();
        mModel.checkMoshtaryKharejAzMahal(noeMasouliat,darkhastFaktorMoshtaryForoshandeModel);
    }

    @Override
    public void checkClearingTreasury(int noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel) {
        if ((noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD && darkhastFaktorMoshtaryForoshandeModel.getCodeVazeiat() == 99) || (noeMasouliat == 5 && darkhastFaktorMoshtaryForoshandeModel.getExtraProp_IsSend() == 0 && darkhastFaktorMoshtaryForoshandeModel.getCodeVazeiat() < 6)) {
            mView.get().showToast(R.string.errorFirstSendFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        } else {
            mModel.checkIsLocationSendToServer(noeMasouliat,darkhastFaktorMoshtaryForoshandeModel);

        }
    }

    @Override
    public void getElatAdamTahvilDarkhast(long ccDarkhastFaktor, int position) {
        mModel.getElatAdamTahvilDarkhast(ccDarkhastFaktor,position);
    }

    @Override
    public void sendElatAdamTahvilDarkhast(ElatAdamTahvilDarkhastModel elatAdamTahvilDarkhastModel, DarkhastFaktorModel darkhastFaktorModel, int position) {
        mModel.sendElatAdamTahvilDarkhast(elatAdamTahvilDarkhastModel,darkhastFaktorModel,position);

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetNoeMasouliat(int noeMasouliat)
    {
        mView.get().onGetNoeMasouliat(noeMasouliat);
    }

    @Override
    public void onErrorUseFakeLocation()
    {
        mView.get().showAlertDialog(R.string.errorFakeLocation, true, Constants.FAILED_MESSAGE());
    }

    @Override
    public void onCheckServerTime(boolean isValidDateTime, String message)
    {
        if (!isValidDateTime)
        {
            mView.get().showAlertDialog(message, true, Constants.FAILED_MESSAGE());
        }
    }

    @Override
    public void onGetCustomersList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListCanEditCustomerDarkhast, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListAllDarkhastEdited, int sortType, double currentLatitude, double currentLongitude , ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        mView.get().showCurrentLocation(currentLatitude , currentLongitude);
        if (sortType == Constants.SORT_TREASURY_BY_ROUTING)
        {
            mView.get().showRoutingInfo();
        }
        else if (sortType == Constants.SORT_TREASURY_BY_CUSTOMER_CODE)
        {
            mView.get().showCustomerCodeInfo();
        }
        if (arrayListAllDarkhastEdited.size() > 0)
        {
            mView.get().onGetAllEditedTodayTreasuryList(arrayListAllDarkhastEdited , moshtaryAddressModels);
        }
        if (arrayListCanEditCustomerDarkhast.size() > 0)
        {
            if (arrayListCanEditCustomerDarkhast.size() >= 3)
            {
                ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModelsNew = new ArrayList<>();
                ArrayList<MoshtaryAddressModel> moshtaryAddressModelsNew = new ArrayList<>();
                moshtaryAddressModelsNew.addAll(moshtaryAddressModels.subList(3 , moshtaryAddressModels.size()));
                darkhastFaktorMoshtaryForoshandeModelsNew.addAll(arrayListCanEditCustomerDarkhast.subList(3 , arrayListCanEditCustomerDarkhast.size()));
                mView.get().onGetTodayTreasuryList(darkhastFaktorMoshtaryForoshandeModelsNew , moshtaryAddressModelsNew);
                mView.get().showThirdPriority(arrayListCanEditCustomerDarkhast.get(2) , moshtaryAddressModels.get(2));
                mView.get().showSecondPriority(arrayListCanEditCustomerDarkhast.get(1), moshtaryAddressModels.get(1));
                mView.get().showFirstPriority(arrayListCanEditCustomerDarkhast.get(0), moshtaryAddressModels.get(0));
            }
            else if (arrayListCanEditCustomerDarkhast.size() == 2)
            {
                mView.get().showSecondPriority(arrayListCanEditCustomerDarkhast.get(1) ,moshtaryAddressModels.get(1));
                mView.get().showFirstPriority(arrayListCanEditCustomerDarkhast.get(0) , moshtaryAddressModels.get(0));
            }
            else //if (darkhastFaktorMoshtaryForoshandeModels.size() == 1)
            {
                mView.get().showFirstPriority(arrayListCanEditCustomerDarkhast.get(0), moshtaryAddressModels.get(0));
            }
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }

        mView.get().closeLoadingDialog();
    }

    @Override
    public void onGetCustomerFaktors(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, MoshtaryAddressModel moshtaryAddressModel , String customerPriority , DarkhastFaktorMoshtaryForoshandeModel customerInfo,boolean isSend,boolean isHavaleh,ArrayList<Integer> codeNoeVorods)
    {
        if (darkhastFaktorMoshtaryForoshandeModels.size() > 0)
        {
            mView.get().showCustomerFaktors(darkhastFaktorMoshtaryForoshandeModels, moshtaryAddressModel , customerPriority , customerInfo,isSend,isHavaleh,codeNoeVorods);
        }
        else
        {
            mView.get().showAlertDialog(R.string.notExistFaktorForTreasury, false, Constants.FAILED_MESSAGE());
        }
    }

    @Override
    public void onGetTodayTreasuryList(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> canEditDarkhastFaktorMoshtaryForoshandeModels, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> cantEditDarkhastFaktorMoshtaryForoshandeModels, double currentLocationLat, double currentLocationLog, int sortType, ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        mView.get().showCurrentLocation(currentLocationLat , currentLocationLog);
        if (sortType == Constants.SORT_TREASURY_BY_ROUTING)
        {
            mView.get().showRoutingInfo();
        }
        else if (sortType == Constants.SORT_TREASURY_BY_CUSTOMER_CODE)
        {
            mView.get().showCustomerCodeInfo();
        }
        if (cantEditDarkhastFaktorMoshtaryForoshandeModels.size() > 0)
        {
            mView.get().onGetEditedTodayTreasuryList(cantEditDarkhastFaktorMoshtaryForoshandeModels, moshtaryAddressModels);
        }
        if (canEditDarkhastFaktorMoshtaryForoshandeModels.size() > 0)
        {
            if (canEditDarkhastFaktorMoshtaryForoshandeModels.size() >= 3)
            {
                ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModelsNew = new ArrayList<>();
                darkhastFaktorMoshtaryForoshandeModelsNew.addAll(canEditDarkhastFaktorMoshtaryForoshandeModels.subList(3 , canEditDarkhastFaktorMoshtaryForoshandeModels.size()));
                ArrayList<MoshtaryAddressModel> moshtaryAddressModelsNew = new ArrayList<>();
                moshtaryAddressModelsNew.addAll(moshtaryAddressModels.subList(3 , moshtaryAddressModels.size()));
                mView.get().onGetTodayTreasuryList(darkhastFaktorMoshtaryForoshandeModelsNew , moshtaryAddressModelsNew);
                mView.get().showThirdPriority(canEditDarkhastFaktorMoshtaryForoshandeModels.get(2) , moshtaryAddressModels.get(2));
                mView.get().showSecondPriority(canEditDarkhastFaktorMoshtaryForoshandeModels.get(1), moshtaryAddressModels.get(1));
                mView.get().showFirstPriority(canEditDarkhastFaktorMoshtaryForoshandeModels.get(0), moshtaryAddressModels.get(0));
            }
            else if (canEditDarkhastFaktorMoshtaryForoshandeModels.size() == 2)
            {
                mView.get().showSecondPriority(canEditDarkhastFaktorMoshtaryForoshandeModels.get(1), moshtaryAddressModels.get(1));
                mView.get().showFirstPriority(canEditDarkhastFaktorMoshtaryForoshandeModels.get(0), moshtaryAddressModels.get(0));
            }
            else //if (darkhastFaktorMoshtaryForoshandeModels.size() == 1)
            {
                mView.get().showFirstPriority(canEditDarkhastFaktorMoshtaryForoshandeModels.get(0), moshtaryAddressModels.get(0));
            }
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }

        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorGetCustomerLocation(int resId, String parameter)
    {
        mView.get().showAlertDialog(resId, parameter, Constants.FAILED_MESSAGE());
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
    public void onSuccessSetDarkhastFaktorShared(long ccDarkhastFaktor, int ccMoshtary)
    {
        mView.get().openDarkhastKalaActivity(ccDarkhastFaktor, ccMoshtary);
    }

    @Override
    public void onFailedSetDarkhastFaktorShared(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
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
    public void onSuccessSendDariaftPardakht()
    {
        mView.get().showAlertDialog(R.string.successSendData, false, Constants.SUCCESS_MESSAGE());
        mView.get().closeLoadingDialog();

    }

    @Override
    public void onSuccessRouting(String customerName, ArrayList<GeoPoint> pointsOfPolyline, String routingResponse)
    {
        if (pointsOfPolyline.size() > 0)
        {
            mView.get().onSuccessRouting(customerName, pointsOfPolyline, routingResponse);
            mView.get().closeLoadingDialog();

        }
        else
        {
            mView.get().showAlertDialog(R.string.errorRouting, false, Constants.FAILED_MESSAGE());
            mView.get().closeLoadingDialog();

        }
    }

    @Override
    public void onError(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onGetSortList(int sortList) {
        mView.get().onGetSortList(sortList);
    }

    @Override
    public void onErrorSendRasGiri(String error) {
        mView.get().onErrorSendRasGiri(error);
    }

    @Override
    public void onWarning(int resId) {
        mView.get().showToast(resId, Constants.INFO_MESSAGE(), Constants.DURATION_SHORT());
        mView.get().closeLoadingDialog();

    }

    @Override
    public void onSuccess(int successSendData) {
        mView.get().closeLoadingDialog();
        mView.get().showAlertDialog(successSendData, false,Constants.SUCCESS_MESSAGE());
    }

    @Override
    public void onOpenInvoiceSettlement(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel, boolean canOpenSettlement) {
        if (canOpenSettlement)
            mView.get().onOpenInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModel);
        else
            mView.get().showAlertDialog(R.string.errorMamorPakhshLocationsNotSent, false,Constants.FAILED_MESSAGE());
    }

    @Override
    public void onGetElatAdamTahvilDarkhast(ArrayList<ElatAdamTahvilDarkhastModel> models, DarkhastFaktorModel darkhastFaktorModel, int position) {
        ArrayList<String> elatAdamTahvilDarkhastTitles = new ArrayList<>();
        for (ElatAdamTahvilDarkhastModel model : models)
        {
            elatAdamTahvilDarkhastTitles.add(model.getNameNoeVorod());
        }
        mView.get().onGetElatAdamTahvilDarkhast(models,elatAdamTahvilDarkhastTitles,darkhastFaktorModel,position);
    }

    @Override
    public void onSuccessSend(int position) {
        mView.get().closeLoading();
        mView.get().showAlertMessage(R.string.successSendData , Constants.SUCCESS_MESSAGE());
        mView.get().onSuccessSend(position);
    }

    @Override
    public void closeLoading() {
        mView.get().closeLoading();
    }


}
