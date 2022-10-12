package com.saphamrah.customer.base;

import android.content.Context;

public interface BaseView {

    void onError(String error);

    void showLoading(String message);

    void dismissLoading();

    void showNoConnection();

    Context getAppContext();

}
