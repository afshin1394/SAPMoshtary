package com.saphamrah.customer.mvp.view.login;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseActivity;
import com.saphamrah.customer.base.BaseApplication;
import com.saphamrah.customer.base.BasePermissionModel;
import com.saphamrah.customer.listeners.SmsListener;
import com.saphamrah.customer.mvp.view.login.interactors.LoginInteracts;
import com.saphamrah.customer.mvp.view.login.presenters.LoginPresenter;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity<LoginInteracts.PresenterOps> implements LoginInteracts.RequiredViewOps, SmsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setActivityPresenter(new LoginPresenter(this));

        checkPermissions(new String[]{Manifest.permission.RECEIVE_SMS});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }


    }



    @Override
    public void onError(String error) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public Context getAppContext() {
        return LoginActivity.this;
    }

    @Override
    public void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {
        ((BaseApplication)getApplication()).setSmsListener(this);

    }

    @Override
    public void messageReceived(String messageText) {

    }
}