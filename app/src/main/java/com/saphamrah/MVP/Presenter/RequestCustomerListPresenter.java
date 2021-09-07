package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.RequestCustomerListMVP;
import com.saphamrah.MVP.Model.RequestCustomerListModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class RequestCustomerListPresenter implements RequestCustomerListMVP.PresenterOps , RequestCustomerListMVP.RequiredPresenterOps
{

    private WeakReference<RequestCustomerListMVP.RequiredViewOps> mView;
    private RequestCustomerListMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public RequestCustomerListPresenter(RequestCustomerListMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RequestCustomerListModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(RequestCustomerListMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void onUpdateMoshtaryMorajehShodehRooz() {
        mView.get().closeLoading();
    }

    @Override
    public void onFailUpdateMoshtaryMorajehShodehRooz() {

        mView.get().closeLoading();
        mView.get().showErrorAlert(R.string.errorOperationUpdateMoshtaryMorajehShodehRooz , Constants.FAILED_MESSAGE(), false);
    }

    @Override
    public void checkFakeLocation()
    {
        mModel.checkFakeLocation();
    }

    @Override
    public void checkDateOfGetProgram()
    {
        mModel.getDateOfGetProgram();
    }

    @Override
    public void getCustomers()
    {
        mModel.getCustomers();
    }

    @Override
    public void searchCustomerName(String searchWord , ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels, ArrayList<Integer> arrayListNoeMorajeh)
    {
        ArrayList<MoshtaryModel> moshtaryModelsSearch = new ArrayList<>();
        ArrayList<MoshtaryAddressModel> moshtaryAddressModelsSearch = new ArrayList<>();
        ArrayList<MoshtaryGharardadModel> moshtaryGharardadModelsSearch = new ArrayList<>();

        ArrayList<Integer> moshtaryNoeMorajehSearch = new ArrayList<>();
        for (int i=0 ; i<moshtaryModels.size() ; i++)
        {
            MoshtaryModel moshtary = moshtaryModels.get(i);
            if (moshtary.getNameMoshtary().contains(searchWord))
            {

                moshtaryModelsSearch.add(moshtary);
                moshtaryAddressModelsSearch.add(moshtaryAddressModels.get(i));
                moshtaryGharardadModelsSearch.add(moshtaryGharardadModels.get(i));
                moshtaryNoeMorajehSearch.add(arrayListNoeMorajeh.get(i));
            }
        }

//        mModel.searchCustomerName(searchWord,moshtaryModels,moshtaryAddressModels,moshtaryGharardadModels,arrayListNoeMorajeh);

        mView.get().onGetSearch(moshtaryModelsSearch , moshtaryAddressModelsSearch ,moshtaryGharardadModelsSearch, moshtaryNoeMorajehSearch);
    }

    //TODO search
    @Override
    public void searchCustomerCode(String searchWord , ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels ,ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , ArrayList<Integer> arrayListNoeMorajeh)
    {
        searchWord = new PubFunc().new LanguageUtil().convertFaNumberToEN(searchWord);
        ArrayList<MoshtaryModel> moshtaryModelsSearch = new ArrayList<>();
        ArrayList<MoshtaryAddressModel> moshtaryAddressModelsSearch = new ArrayList<>();
        ArrayList<MoshtaryGharardadModel> moshtaryGharardadModelsSearch = new ArrayList<>();
        ArrayList<Integer> moshtaryNoeMorajehSearch = new ArrayList<>();
        for (int i=0 ; i<moshtaryModels.size() ; i++)
        {
            MoshtaryModel moshtary = moshtaryModels.get(i);
            if (moshtary.getCodeMoshtary().contains(searchWord))
            {
                moshtaryModelsSearch.add(moshtary);
                moshtaryAddressModelsSearch.add(moshtaryAddressModels.get(i));
                moshtaryGharardadModelsSearch.add(moshtaryGharardadModels.get(i));
                moshtaryNoeMorajehSearch.add(arrayListNoeMorajeh.get(i));
            }
        }
        //TODO search
        mView.get().onGetSearch(moshtaryModelsSearch , moshtaryAddressModelsSearch ,moshtaryGharardadModelsSearch, moshtaryNoeMorajehSearch);
    }

    @Override
    public void checkDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel)
    {
        mModel.checkDuplicateRequestForCustomer(moshtaryModel,moshtaryGharardadModel);
    }

    @Override
    public void checkSelectedCustomer(int ccMoshtary,int ccSazmanForoshGharardad,int ccMoshtaryGharardad)
    {
        mModel.checkSelectedCustomer(ccMoshtary,ccMoshtaryGharardad,ccSazmanForoshGharardad);
    }


    @Override
    public void checkUpdateEtebarMoshtary(MoshtaryModel moshtaryModel)
    {
        if (moshtaryModel.getCcMoshtary() > 0)
        {
            mModel.updateEtebarMoshtary(moshtaryModel);
        }
        else
        {
            mView.get().showErrorAlert(R.string.errorSelectCustomer, Constants.FAILED_MESSAGE(), false);
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
      mModel.onDestroy();
    }

    @Override
    public void updateMoshtaryMorajehShodehRooz() {
        mModel.updateMoshtaryMorajehShodehRooz();
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
        mView.get().showErrorAlert(R.string.errorFakeLocation, Constants.FAILED_MESSAGE(), true);
    }

    @Override
    public void onGetDateOfGetProgram(String date)
    {
        mView.get().onGetDateOfGetProgram(date);
    }

    @Override
    public void onErrorNeedGetProgram()
    {
        mView.get().showErrorAlert(R.string.needGetProgram, Constants.FAILED_MESSAGE(), true);
    }

    /*@Override
    public void onSetRequestInfoShared(int ccMoshtary)
    {
        mView.get().onSuccessSelectedCustomer(ccMoshtary);
    }*/

    @Override
    public void onSetRequestInfoShared(int ccMoshtary,int ccSazmanForosh, boolean showBarkhordAvalie, boolean showMojodiGiri)
    {
        mView.get().closeLoading();
        if (showBarkhordAvalie)
        {
            mView.get().showBarkhordAvalieActivity(ccMoshtary);
        }
        else if (showMojodiGiri)
        {
            mView.get().showMojoodiGiriActivity(ccMoshtary,ccSazmanForosh);
        }
        else
        {
            mView.get().showDarkhastKalaActivity(ccMoshtary,ccSazmanForosh);
        }
    }

    @Override
    public void onFailedSetRequestInfoShared()
    {
        mView.get().closeLoading();
        mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void showAlertDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel)
    {
        mView.get().closeLoading();
        mView.get().showAlertDuplicateRequestForCustomer(moshtaryModel,moshtaryGharardadModel);
    }

    @Override
    public void onErrorSelectCustomer(int resId)
    {
        mView.get().closeLoading();
        mView.get().showErrorAlert(resId , Constants.FAILED_MESSAGE(), false);
    }

    @Override
    public void onWarningSelectCustomer(int resId)
    {
        //mView.get().closeLoading();
        mView.get().showToast(resId, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessUpdateMandeMojodi()
    {
        mView.get().showToast(R.string.updateMandeMojodi, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedUpdateMandeMojodi()
    {
        mView.get().showToast(R.string.errorUpdateMandeMojodi, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetCustomers(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , boolean canUpdateCustomer)
    {
        mView.get().onGetCustomers(moshtaryModels , moshtaryAddressModels , arrayListNoeMorajeh , moshtaryGharardadModels, canUpdateCustomer);
    }


    @Override
    public void onErrorAccessToLocation()
    {
        mView.get().showToast(R.string.errorAccessToLocation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onSuccessUpdateMoshtaryEtebar()
    {
        mView.get().closeLoading();
        mView.get().showErrorAlert(R.string.successUpdateMoshtaryEtebar, Constants.SUCCESS_MESSAGE(), false);
    }

    @Override
    public void onFailedUpdateMoshtaryEtebar()
    {
        mView.get().closeLoading();
        mView.get().showErrorAlert(R.string.errorUpdateMoshtaryEtebar, Constants.FAILED_MESSAGE(), false);
    }

    @Override
    public void onFailedUpdateForoshandehEtebar()
    {
        mView.get().closeLoading();
        mView.get().showErrorAlert(R.string.errorUpdateForoshandehEtebar, Constants.FAILED_MESSAGE(), false);
    }

}
