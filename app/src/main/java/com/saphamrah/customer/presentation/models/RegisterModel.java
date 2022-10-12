package com.saphamrah.customer.presentation.models;

import com.saphamrah.customer.data.network.model.RegisterNetworkModel;
import com.saphamrah.customer.presentation.interactors.RegisterInteracts;

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
