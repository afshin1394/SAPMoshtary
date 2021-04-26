package com.saphamrah.MVP.Presenter;

import com.saphamrah.BaseMVP.VarizNaghdBeBankMVP;
import com.saphamrah.MVP.Model.VarizNaghdBeBankModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.VarizBeBankModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class VarizNaghdBeBankPresenter implements VarizNaghdBeBankMVP.PresenterOps , VarizNaghdBeBankMVP.RequiredPresenterOps
{
    private WeakReference<VarizNaghdBeBankMVP.RequiredViewOps> mView;
    private VarizNaghdBeBankMVP.ModelOps mModel;

    public VarizNaghdBeBankPresenter(VarizNaghdBeBankMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new VarizNaghdBeBankModel(this);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onGetAllBank() {
        mModel.getAllBank();
    }

    @Override
    public void updateDaoAll(int ccBankSanad , String nameShobehSanad , String codeShobehSand , String shomareHesabSanad , int ccShomareHesab , String shomarehSanad ,String nameBankSanad , String TarikhSanad, ArrayList<VarizBeBankModel> modelsArrayList)
    {
        mModel.updateDaoAll(ccBankSanad ,nameShobehSanad , codeShobehSand , shomareHesabSanad , ccShomareHesab , shomarehSanad , nameBankSanad , TarikhSanad , modelsArrayList);
    }


    @Override
    public void getAllMoshtary() {
        mModel.getAllMoshtary();
    }

    @Override
    public void getRefresh() {
        mModel.getRefresh();
    }

    @Override
    public void getSumMablagh() {
        mModel.getSumMablagh();
    }

    @Override
    public void getAllVarizBeBank() {
        mModel.getAllVarizBeBank();
    }

    @Override
    public void sendVariz(VarizBeBankModel varizBeBankModel) {
        mModel.sendVariz(varizBeBankModel);
    }

    @Override
    public void checkHaveShomarehSanadForUpdate(String ExtraProp_ShomarehSanad) {
        mModel.checkHaveShomarehSanadForUpdate(ExtraProp_ShomarehSanad);
    }

    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public void onGetAllBank(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels) {
        mView.get().onGetAllBank(markazShomarehHesabModels);
    }

    @Override
    public void onUpdateDao(boolean update) {
        mView.get().onUpdateDao(update);
    }

    @Override
    public void onGetAllMoshtary(ArrayList<VarizBeBankModel> varizBeBankModels) {
        mView.get().onGetAllMoshtary(varizBeBankModels);
    }

    @Override
    public void onGetSumMablagh(ArrayList<VarizBeBankModel> varizBeBankModels) {
        mView.get().onGetSumMablagh(varizBeBankModels);
    }

    @Override
    public void onGetRefresh(int resId, int messageType, int duration) {
            mView.get().showToast(resId, messageType , duration);
            /*
            for refresh recycler data
             */
            mModel.getAllVarizBeBank();
    }

    @Override
    public void onGetAllVarizBeBank(ArrayList<VarizBeBankModel> varizBeBankModels) {
        mView.get().onGetAllVarizBeBank(varizBeBankModels);
    }

    @Override
    public void onCheckHaveShomarehSanadForUpdate(boolean haveShomarehSanad) {
        mView.get().onCheckHaveShomarehSanadForUpdate(haveShomarehSanad);
    }

    @Override
    public void onErrorSend(int resId) {
        mView.get().showToast(resId , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessSend() {
        mView.get().showAlertMessage(R.string.successSendData , Constants.SUCCESS_MESSAGE());
    }


}
