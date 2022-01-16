package com.saphamrah.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;
import com.saphamrah.protos.VersionGrpc;
import com.saphamrah.protos.VersionReply;
import com.saphamrah.protos.VersionRequest;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionDAO {
    
    private Context context;

    public VersionDAO(Context context) {
        this.context = context;
    }

    public void fetchVersionInfo(String activityNameForLog, ServerIpModel serverIpModel , RetrofitResponse retrofitResponse) {

        APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);//ApiClient.getClient(serverIP, port).create(APIServiceGet.class);
        Call<GetVersionResult> call = apiServiceGet.getVersionInfo();
        call.enqueue(new Callback<GetVersionResult>() {
            @Override
            public void onResponse(Call<GetVersionResult> call, Response<GetVersionResult> response) {
                if (response.raw().body() != null)
                {
                    Log.d("intercept", "in on response SplashModel.getServerVersion and response : " + response.raw().body().contentLength());
                    long contentLength = response.raw().body().contentLength();
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context , Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, VersionDAO.class.getSimpleName(), "", "", "onResponse");
                }
                try {
                    if (response.isSuccessful()) {
                        GetVersionResult result = response.body();
                        ArrayList<GetVersionResult> versionResults = new ArrayList<>();
                        versionResults.add(result);
                        retrofitResponse.onSuccess(versionResults);
                    }
                  
                } catch (Exception exception) {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), VersionDAO.class.getSimpleName(), activityNameForLog, "fetchVersionInfo", "");
                    retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetVersionResult> call, Throwable t) {
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), VersionDAO.class.getSimpleName(), activityNameForLog, "fetchVersionInfo", "");
                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), t.getMessage());
            }
        });
    }

    public void fetchVersionInfoGrpc(String activityNameForLog, ServerIpModel serverIpModel , RetrofitResponse retrofitResponse) {
        try {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
        VersionGrpc.VersionBlockingStub blockingStub = VersionGrpc.newBlockingStub(managedChannel);
        VersionRequest request = VersionRequest.newBuilder().build();

        Callable<VersionReply> replyListCallable = () -> blockingStub.getVersion(request);
        RxAsync.makeObservable(replyListCallable)

                .map(reply -> {

                   return reply.getContent();

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String content) {
                        ArrayList<String> contents = new ArrayList<>();
                        contents.add(content);
                        retrofitResponse.onSuccess(contents);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), e.getMessage());
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.getMessage(), VersionDAO.class.getSimpleName(), activityNameForLog, "fetchVersionInfo", "");
                        retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (!compositeDisposable.isDisposed()) {
                            compositeDisposable.dispose();
                        }
                        compositeDisposable.clear();
                    }
                });
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), VersionDAO.class.getSimpleName(), activityNameForLog, "fetchVersionInfo", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
          }
       }
    }
