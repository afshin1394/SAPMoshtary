package com.saphamrah.customer.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel {

    protected CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public abstract void setLogToDB(
            Integer logType,
            String message,
            String logClass,
            String logActivity,
            String functionParent,
            String functionChild
    );

    public abstract void onDestroy();
}
