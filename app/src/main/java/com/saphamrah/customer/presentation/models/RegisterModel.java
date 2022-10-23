package com.saphamrah.customer.presentation.models;

import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.network.model.RegisterNetworkModel;
import com.saphamrah.customer.presentation.interactors.RegisterInteracts;

import java.util.ArrayList;

public class RegisterModel extends RegisterInteracts.ModelOps {

    private RegisterInteracts.RequiredPresenterOps registerRequiredPresenterOps;

    public RegisterModel(RegisterInteracts.RequiredPresenterOps registerRequiredPresenterOps) {
        this.registerRequiredPresenterOps = registerRequiredPresenterOps;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getIdentities() {


        BaseBottomSheetRecyclerModel baseBottomSheetRecyclerModel = new BaseBottomSheetRecyclerModel();
        BaseBottomSheetRecyclerModel baseBottomSheetRecyclerModel1 = new BaseBottomSheetRecyclerModel();
        ArrayList<BaseBottomSheetRecyclerModel> itemTitles = new ArrayList<>();

        baseBottomSheetRecyclerModel.setName("حقیقی");
        itemTitles.add(baseBottomSheetRecyclerModel);

        baseBottomSheetRecyclerModel1.setName("حقوقی");
        itemTitles.add(baseBottomSheetRecyclerModel1);

        registerRequiredPresenterOps.onGetIdentities(itemTitles);
    }

    @Override
    public void getProvinces() {


    }

    @Override
    public void getCities() {

    }

    @Override
    public void sendRegisterData(RegisterNetworkModel registerNetworkModel) {

    }
}
