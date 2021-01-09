package com.saphamrah.Network.RxNetwork;

import com.saphamrah.WebService.RxService.Response.BaseResponse;

import io.reactivex.disposables.Disposable;

public interface RxCallback<I extends BaseResponse> {
    void onStart(Disposable disposable);

    void onSuccess(I response);

    void onError(String message,String type);


}
