package com.saphamrah.customer.presentation.presenters.profile;

import android.content.Context;

import com.saphamrah.customer.presentation.interactors.login.LoginInteracts;
import com.saphamrah.customer.presentation.interactors.login.SendOtpLoginInteracts;
import com.saphamrah.customer.presentation.interactors.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.models.login.SendOtpLoginModel;
import com.saphamrah.customer.presentation.models.profile.ProfileModel;

public class ProfilePresenter implements ProfileInteracts.PresenterOps, ProfileInteracts.RequiredPresenterOps {
    private final ProfileModel model;

    public ProfilePresenter(ProfileInteracts.RequiredViewOps view) {
        model = new ProfileModel();
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
