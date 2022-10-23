package com.saphamrah.customer.presentation.presenters;

import android.content.Context;

import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.network.model.RegisterNetworkModel;
import com.saphamrah.customer.presentation.interactors.RegisterInteracts;
import com.saphamrah.customer.presentation.models.RegisterModel;

import java.util.ArrayList;

public class RegisterPresenter implements RegisterInteracts.PresenterOps, RegisterInteracts.RequiredPresenterOps {

    private final RegisterInteracts.RequiredViewOps view;
    private final RegisterModel model;

    public RegisterPresenter(RegisterInteracts.RequiredViewOps view) {
        this.view = view;
        model = new RegisterModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        model.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public Context getContext() {
        return view.getAppContext();
    }

    @Override
    public void getIdentities() {
        model.getIdentities();
    }

    @Override
    public void getProvinces() {
        model.getProvinces();
    }

    @Override
    public void getCities() {
        model.getCities();
    }

    @Override
    public void checkValidityOfRegisterData(RegisterNetworkModel registerNetworkModel) {
        boolean validData = true;
        if (registerNetworkModel != null) {
            if (registerNetworkModel.getFirstName() == null || registerNetworkModel.getFirstName().isEmpty()) {
                validData = false;
                view.onErrorFirstName();
            }
            if (registerNetworkModel.getLastName() == null || registerNetworkModel.getLastName().isEmpty()) {
                validData = false;
                view.onErrorLastName();
            }
            if (registerNetworkModel.getMobile() == null || registerNetworkModel.getMobile().isEmpty()) {
                validData = false;
                view.onErrorMobile();
            }

            if (validData) {
                model.sendRegisterData(registerNetworkModel);
            }
        }

    }

    @Override
    public void onGetIdentities(ArrayList<BaseBottomSheetRecyclerModel> itemTitles) {
        view.onGetIdentities(itemTitles);
    }

    @Override
    public void onGetProvinces() {
        view.onGetProvinces();
    }

    @Override
    public void onGetCities() {
        view.onGetCities();
    }

    @Override
    public void onSendRegisterData() {
        view.onSendRegisterData();
    }
}
