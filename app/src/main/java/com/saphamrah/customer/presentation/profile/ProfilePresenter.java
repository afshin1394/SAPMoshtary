package com.saphamrah.customer.presentation.profile;

import android.content.Context;

import com.saphamrah.customer.presentation.profile.ProfileInteracts;
import com.saphamrah.customer.presentation.profile.ProfileModel;

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
