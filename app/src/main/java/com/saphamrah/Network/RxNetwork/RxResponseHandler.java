package com.saphamrah.Network.RxNetwork;

import com.saphamrah.WebService.RxService.Response.BaseResponse;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public  abstract class RxResponseHandler<T extends ArrayList>

{
    CompositeDisposable compositeDisposable;
    public  void onStart(Disposable disposable){
        compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(disposable);
    }
    public abstract void onSuccess(T response);
    public abstract void onFailed(String message, String type);
    public void onProgress(int progress){

    }
    public void onComplete(){
        if (compositeDisposable!=null){
            if (!compositeDisposable.isDisposed()) {
                compositeDisposable.dispose();
                compositeDisposable.clear();
                compositeDisposable=null;
            }
        }
    }
}
