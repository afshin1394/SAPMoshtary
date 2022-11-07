package com.saphamrah.customer.presentation.login;

import android.content.Context;

import com.saphamrah.customer.data.local.db.entity.Bank;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoginPresenter implements LoginInteracts.PresenterOps, LoginInteracts.RequiredPresenterOps {

    private final WeakReference<LoginInteracts.RequiredViewOps> view;
    private final LoginModel model;

    public LoginPresenter(LoginInteracts.RequiredViewOps view) {
        this.view = new WeakReference<>(view) ;
        model = new LoginModel(this);
    }

    @Override

    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        model.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }


    @Override
    public Context getContext() {
        return view.get().getAppContext();
    }

  /*  @Override
    public void getAllBanks() {
        model.getAllBanks();
    }

    @Override
    public void onGetAllBanks(List<Bank> banks) {
        view.onGetAllBanks(banks);
    }*/

   /* @Override
    public void insertBanks(List<Bank> banks) {
        model.insertBanks(banks);
    }*/

}
