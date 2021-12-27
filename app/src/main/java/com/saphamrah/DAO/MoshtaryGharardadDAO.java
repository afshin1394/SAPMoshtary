package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MoshtaryGharardadKalaModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryGharardadResult;
import com.saphamrah.protos.CustomerContractGrpc;
import com.saphamrah.protos.CustomerContractReply;
import com.saphamrah.protos.CustomerContractReplyList;
import com.saphamrah.protos.CustomerContractRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class MoshtaryGharardadDAO {
    private DBHelper dbHelper;
    private Context context;


    public MoshtaryGharardadDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryGharardadDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad(),
                        MoshtaryGharardadModel.COLUMN_ccMoshtary(),
                        MoshtaryGharardadModel.COLUMN_ccMoshtaryNoeGharardad(),
                        MoshtaryGharardadModel.COLUMN_NameMoshtaryNoeGharardad(),
                        MoshtaryGharardadModel.COLUMN_ShomarehGharardad(),
                        MoshtaryGharardadModel.COLUMN_TarikhGharardad(),
                        MoshtaryGharardadModel.COLUMN_FromDate(),
                        MoshtaryGharardadModel.COLUMN_EndDate(),
                        MoshtaryGharardadModel.COLUMN_TarikhEtebar(),
                        MoshtaryGharardadModel.COLUMN_ccNoeVisit(),
                        MoshtaryGharardadModel.COLUMN_NameNoeVisit(),
                        MoshtaryGharardadModel.COLUMN_CodeNoeHaml(),
                        MoshtaryGharardadModel.COLUMN_ModatVosol(),
                        MoshtaryGharardadModel.COLUMN_ModatTakhirMojaz(),
                        MoshtaryGharardadModel.COLUMN_ccDarkhastFaktorNoeForosh(),
                        MoshtaryGharardadModel.COLUMN_CodeNoeVosolAzMoshtary(),
                        MoshtaryGharardadModel.COLUMN_NameNoeVosolAzMoshtary()

                };
    }

    public void fetchMoshtaryGharardadGrpc(final Context context, final String activityNameForLog, String ccForoshandeh, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryEtebarPishFarzDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryEtebarPishFarzGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerContractGrpc.CustomerContractBlockingStub customerContractBlockingStub = CustomerContractGrpc.newBlockingStub(managedChannel);
                CustomerContractRequest customerContractRequest = CustomerContractRequest.newBuilder().setSalesManID(String.valueOf(ccForoshandeh)).build();
                Callable<CustomerContractReplyList> customerContractReplyListCallable = () -> customerContractBlockingStub.getCustomerContract(customerContractRequest);
                RxAsync.makeObservable(customerContractReplyListCallable)
                        .map(customerContractReplyList -> {
                            ArrayList<MoshtaryGharardadModel> models = new ArrayList<>();
                            for (CustomerContractReply reply : customerContractReplyList.getCustomerContractsList()) {
                                MoshtaryGharardadModel model = new MoshtaryGharardadModel();

                                model.setRadif(reply.getIndex());
                                model.setCcMoshtaryGharardad(reply.getCustomerContractID());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setNameMoshtaryNoeGharardad(reply.getCustomerContractName());
                                model.setShomarehGharardad(reply.getContractNumber());
                                model.setTarikhGharardad(reply.getContractDate());
                                model.setFromDate(reply.getFromDate());
                                model.setEndDate(reply.getEndDate());
                                model.setTarikhEtebar(reply.getCreditDate());
                                model.setCcNoeVisit(reply.getVisitTypeID());
                                model.setNameNoeVisit(reply.getVisitTypeName());
                                model.setCodeNoeHaml(reply.getCarryingCodeType());
                                model.setModatVosol(reply.getReceiptDuration());
                                model.setModatTakhirMojaz(reply.getValidDelayDuration());
                                model.setCcDarkhastFaktorNoeForosh(reply.getSellTypeInvoiceRequestID());
                                model.setCodeNoeVosolAzMoshtary(reply.getReceiptTypeFromCustomerCode());
                                model.setNameNoeVosolAzMoshtary(reply.getReceiptTypeFromCustomerName());
                                model.setCcSazmanForosh(reply.getSellOrganizationID());
                                model.setNameSazmanForosh(reply.getSellOrganizationName());
                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MoshtaryGharardadModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MoshtaryGharardadModel> moshtaryGharardadModel) {
                                retrofitResponse.onSuccess(moshtaryGharardadModel);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
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

            }
        } catch (Exception exception) {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    public void fetchMoshtaryGharardadPakhshGrpc(final Context context, final String activityNameForLog, int ccForoshandeh, int ccMoshtaryGharardad, int ccSazmanForosh, RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryEtebarPishFarzDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryEtebarPishFarzGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerContractGrpc.CustomerContractBlockingStub customerContractBlockingStub = CustomerContractGrpc.newBlockingStub(managedChannel);
                CustomerContractRequest customerContractRequest = CustomerContractRequest.newBuilder().setSalesManID(String.valueOf(ccForoshandeh)).setCustomerContractID(ccMoshtaryGharardad).setSellOrganizationID(ccSazmanForosh).build();
                Callable<CustomerContractReplyList> customerContractReplyListCallable = () -> customerContractBlockingStub.getCustomerContract(customerContractRequest);
                RxAsync.makeObservable(customerContractReplyListCallable)
                        .map(customerContractReplyList -> {
                            ArrayList<MoshtaryGharardadModel> models = new ArrayList<>();
                            for (CustomerContractReply reply : customerContractReplyList.getCustomerContractsList()) {
                                MoshtaryGharardadModel model = new MoshtaryGharardadModel();

                                model.setRadif(reply.getIndex());
                                model.setCcMoshtaryGharardad(reply.getCustomerContractID());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setNameMoshtaryNoeGharardad(reply.getCustomerContractName());
                                model.setShomarehGharardad(reply.getContractNumber());
                                model.setTarikhGharardad(reply.getContractDate());
                                model.setFromDate(reply.getFromDate());
                                model.setEndDate(reply.getEndDate());
                                model.setTarikhEtebar(reply.getCreditDate());
                                model.setCcNoeVisit(reply.getVisitTypeID());
                                model.setNameNoeVisit(reply.getVisitTypeName());
                                model.setCodeNoeHaml(reply.getCarryingCodeType());
                                model.setModatVosol(reply.getReceiptDuration());
                                model.setModatTakhirMojaz(reply.getValidDelayDuration());
                                model.setCcDarkhastFaktorNoeForosh(reply.getSellTypeInvoiceRequestID());
                                model.setCodeNoeVosolAzMoshtary(reply.getReceiptTypeFromCustomerCode());
                                model.setNameNoeVosolAzMoshtary(reply.getReceiptTypeFromCustomerName());
                                model.setCcSazmanForosh(reply.getSellOrganizationID());
                                model.setNameSazmanForosh(reply.getSellOrganizationName());
                                models.add(model);
                            }
                            return models;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MoshtaryGharardadModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MoshtaryGharardadModel> moshtaryGharardadModel) {
                                retrofitResponse.onSuccess(moshtaryGharardadModel);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
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

            }
        } catch (Exception exception) {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }

    @SuppressLint("LongLogTag")
    public void fetchMoshtaryGharardadASync(final Context context, final String activityNameForLog, String ccForoshandeh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);

        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "fetchMoshtaryGhararDad: " + serverIpModel.getServerIp().trim() + " " + serverIpModel.getPort().trim() + " ");
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "");
            retrofitResponse.onFailed(Constants.HTTP_ERROR(), message);
            Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "fetchMoshtaryGhararDad: " + serverIpModel.getServerIp().trim() + " " + serverIpModel.getPort().trim() + " ");

        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Log.i("HDAPDJOPWJ", "fetchMoshtaryGhararDad: " + serverIpModel.getPort() + " " + serverIpModel.getServerIp());
            Call<GetAllMoshtaryGharardadResult> call = apiServiceGet.getMoshtaryGharardad(String.valueOf(ccForoshandeh));

            Log.i("urlOfCall", "fetchMoshtaryGhararDad: " + call.request().url());

            call.enqueue(new Callback<GetAllMoshtaryGharardadResult>() {
                @Override
                public void onResponse(Call<GetAllMoshtaryGharardadResult> call, Response<GetAllMoshtaryGharardadResult> response) {
                    Log.i("messsages", "onResponse: " + response.message());
                    try {
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryGharardadDAO.class.getSimpleName(), "", "fetchMoshtaryGhararDadKala", "onResponse");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                    if (response.isSuccessful()) {
                        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP2: " + response.isSuccessful());
                        GetAllMoshtaryGharardadResult result = response.body();
                        if (result != null) {
                            if (result.getSuccess()) {
                                Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP4: " + result.getSuccess());
                                        retrofitResponse.onSuccess(result.getData());

                            } else {
                                Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6:" + result.getSuccess());
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                            }
                        } else {
                            Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6: result null");
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                        }
                    } else {
                        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6: result null");
                        String message = String.format("error body : %1$s , code : %2$s", response.message(), response.code());
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                    }
                    }
                    catch (Exception exception) {
                        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP7: Exception in Request");
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtaryGharardadResult> call, Throwable t) {
                    Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP8: fail 404 or 403" + t.getMessage());
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), t.getMessage());
                }
            });
        }
    }



    @SuppressLint("LongLogTag")
    public void fetchMoshtaryGharardadSync(final Context context, final String activityNameForLog, int ccForoshandeh, int ccMoshtaryGharardad, int ccSazmanForosh, RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);

        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "fetchMoshtaryGhararDad: " + serverIpModel.getServerIp().trim() + " " + serverIpModel.getPort().trim() + " ");
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "");
            retrofitResponse.onFailed(Constants.HTTP_ERROR(), message);


        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Log.i("HDAPDJOPWJ", "fetchMoshtaryGhararDad: " + serverIpModel.getPort() + " " + serverIpModel.getServerIp());
            Call<GetAllMoshtaryGharardadResult> call = apiServiceGet.getMoshtaryGharardad(String.valueOf(ccForoshandeh), ccMoshtaryGharardad, ccSazmanForosh);

            Log.i("urlOfCall", "fetchMoshtaryGhararDad: " + call.request().url());
            try {
                Response<GetAllMoshtaryGharardadResult> response = call.execute();


                try {
                    if (response.raw().body() != null) {
                        long contentLength = response.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryGharardadDAO.class.getSimpleName(), "", "fetchMoshtaryGhararDadKala", "onResponse");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (response.isSuccessful()) {
                        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP2: " + response.isSuccessful());
                        GetAllMoshtaryGharardadResult result = response.body();
                        if (result != null) {
                            if (result.getSuccess()) {
                                Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP4: " + result.getSuccess());
                                retrofitResponse.onSuccess(result.getData());

                            } else {
                                Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6:" + result.getSuccess());
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                            }
                        } else {
                            Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6: result null");
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));

                        }
                    } else {
                        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6: result null");
                        String message = String.format("error body : %1$s , code : %2$s", response.message(), response.code());
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);

                    }
                } catch (Exception exception) {
                    Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP7: Exception in Request");
                    exception.printStackTrace();
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                    retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());

                }


            } catch (Exception exception) {
                Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP7: Exception in Request");
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), exception.getMessage());

            }
        }
    }



    @SuppressLint("LongLogTag")
    public ArrayList<Integer> getAllCcSazmanForosh() {
        ArrayList<Integer> AllCcSazmanForosh = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select ccSazmanForosh From MoshtaryGhararDad", null);
            if (cursor != null) {
                Log.d("_GetAll_CcSazman_Forosh_", "getAllCcSazmanForosh: " + cursor.getCount());
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        AllCcSazmanForosh.add(cursor.getInt(0));
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryGharardadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), "", "MoshtaryGharardadModel", "");
        }
        return AllCcSazmanForosh;
    }

public static final String GET_ALL_CCGHARARDAD_SAZMANFOROSH="getAllCCGharardadSazmanForosh";
    @SuppressLint("LongLogTag")
    public Map<Integer,List<Integer>> getAllCcGharardadBySazman() {

        List<Integer> allCcSazmanForosh = getAllCcSazmanForosh();

        Log.i(GET_ALL_CCGHARARDAD_SAZMANFOROSH, "getAllCcGharardadBySazman: "+allCcSazmanForosh.size());

       Map<Integer,List<Integer>> gharardadSazman = new HashMap<>();



        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select ccSazmanForosh,ccMoshtaryGharardad From MoshtaryGhararDad", null);

            if (cursor != null) {

                if (cursor.getCount() > 0) {

                    for (Integer integer : allCcSazmanForosh) {
                        List<Integer> allGharardadOfCcSazmanForosh = new ArrayList<>();
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            if (integer == cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_CcSazmanForosh()))) {
                                allGharardadOfCcSazmanForosh.add(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad())));
                            }
                            cursor.moveToNext();
                        }

                        gharardadSazman.put(integer, allGharardadOfCcSazmanForosh);
                        for (Integer integer1:allGharardadOfCcSazmanForosh)
                            Log.i(GET_ALL_CCGHARARDAD_SAZMANFOROSH, "getAllCcGharardadBySazman: "+String.valueOf(integer1));
                    }
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryGharardadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), "", "MoshtaryGharardadModel", "");
        }
        return gharardadSazman;
    }

    public ArrayList<MoshtaryGharardadModel> getByccMoshtary(int ccMoshtary) {
        ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQry = "select * from " + MoshtaryGharardadModel.TableName() + " where " + MoshtaryGharardadModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
            Cursor cursor = db.rawQuery(strQry, null);
            if (cursor != null) {
                Log.d("findByccMoshtary", "getAllmoshtaryGharardadModelsbyccMoshtary: " + cursor.getCount());
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        moshtaryGharardadModels = cursorToModel(cursor);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryGharardadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), "", "MoshtaryGharardadModel", "");
        }
        return moshtaryGharardadModels;
    }
    public MoshtaryGharardadModel getMoshtaryGharardadByCcMoshtaryAndCcSazmanForosh(int ccMoshtary , int ccSazmanForosh){
        MoshtaryGharardadModel models = new MoshtaryGharardadModel();
        String query = "SELECT * FROM MoshtaryGharardad WHERE ccMoshtary = " + ccMoshtary + " AND ccSazmanForosh = " + ccSazmanForosh ;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception){
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryGharardadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryGharardadDAO" , "" , "getMoshtaryGharardadByCcMoshtaryAndCcSazmanForosh" , "");

        }
        return models;

    }
    public boolean insertGroup(ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels) {
        Log.i("getProgram", "insertGroup moshtaryGharardadModels: " + moshtaryGharardadModels + " " );
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (MoshtaryGharardadModel moshtaryGharardadModel : moshtaryGharardadModels) {
                ContentValues contentValues = modelToContentvalue(moshtaryGharardadModel);
                db.insertOrThrow(MoshtaryGharardadModel.TableName(), null, contentValues);

            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            if (db.inTransaction()) {
                db.endTransaction();
            }
            if (db.isOpen()) {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert, MoshtaryGharardadKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryGharardadDAO", "", "insertGroup", "");
            return false;
        }
    }

    @SuppressLint("LongLogTag")
    public boolean deleteAll() {
        try {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int NumberOfAffectedItems = db.delete(MoshtaryGharardadModel.TableName(), null, null);
            Log.i("_DeleteAll_Moshtary_Zangirii_", "deleteAll: " + NumberOfAffectedItems);
            db.close();
            return true;
        } catch (Exception exception) {
            Log.i("_DeleteAll_Moshtary_Zangirii_", "Exception" + exception.getMessage());
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, MoshtaryGharardadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryGharardadDAO", "", "deleteAll", "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryGharardadModel moshtaryGharardadModel) {
        ContentValues contentValues = new ContentValues();

        try {


        contentValues.put(MoshtaryGharardadModel.COLUMN_Radif(), moshtaryGharardadModel.getRadif());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad(), moshtaryGharardadModel.getCcMoshtaryGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccMoshtary(), moshtaryGharardadModel.getCcMoshtary());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccMoshtaryNoeGharardad(), moshtaryGharardadModel.getCcMoshtaryNoeGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_NameMoshtaryNoeGharardad(), moshtaryGharardadModel.getNameMoshtaryNoeGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ShomarehGharardad(), moshtaryGharardadModel.getShomarehGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_TarikhGharardad(), moshtaryGharardadModel.getTarikhGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_FromDate(), moshtaryGharardadModel.getFromDate());
        contentValues.put(MoshtaryGharardadModel.COLUMN_EndDate(), moshtaryGharardadModel.getEndDate());
        contentValues.put(MoshtaryGharardadModel.COLUMN_TarikhEtebar(), moshtaryGharardadModel.getTarikhEtebar());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccNoeVisit(), moshtaryGharardadModel.getCcNoeVisit());
        contentValues.put(MoshtaryGharardadModel.COLUMN_NameNoeVisit(), moshtaryGharardadModel.getNameNoeVisit());
        contentValues.put(MoshtaryGharardadModel.COLUMN_CodeNoeHaml(), moshtaryGharardadModel.getCodeNoeHaml());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ModatVosol(), moshtaryGharardadModel.getModatVosol());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ModatTakhirMojaz(), moshtaryGharardadModel.getModatTakhirMojaz());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccDarkhastFaktorNoeForosh(), moshtaryGharardadModel.getCcDarkhastFaktorNoeForosh());
        contentValues.put(MoshtaryGharardadModel.COLUMN_CodeNoeVosolAzMoshtary(), moshtaryGharardadModel.getCodeNoeVosolAzMoshtary());
        contentValues.put(MoshtaryGharardadModel.COLUMN_NameNoeVosolAzMoshtary(), moshtaryGharardadModel.getNameNoeVosolAzMoshtary());
        contentValues.put(MoshtaryGharardadModel.COLUMN_CcSazmanForosh(), moshtaryGharardadModel.getCcSazmanForosh());
        contentValues.put(MoshtaryGharardadModel.COLUMN_nameSazmanForosh(), moshtaryGharardadModel.getNameSazmanForosh());

        }catch (Exception e){
            Log.i("exceptionnn", "modelToContentvalue: "+e.getMessage());
        }
        return contentValues;

    }

    private ArrayList<MoshtaryGharardadModel> cursorToModel(Cursor cursor) {
        ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MoshtaryGharardadModel moshtaryGharardadModel = new MoshtaryGharardadModel();


            moshtaryGharardadModel.setRadif(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_Radif())));
            moshtaryGharardadModel.setCcMoshtaryGharardad(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad())));
            moshtaryGharardadModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad())));
            moshtaryGharardadModel.setCcMoshtaryNoeGharardad(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad())));
            moshtaryGharardadModel.setNameMoshtaryNoeGharardad(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_NameMoshtaryNoeGharardad())));
            moshtaryGharardadModel.setShomarehGharardad(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ShomarehGharardad())));
            moshtaryGharardadModel.setTarikhGharardad(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_TarikhGharardad())));
            moshtaryGharardadModel.setFromDate(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_FromDate())));
            moshtaryGharardadModel.setEndDate(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_EndDate())));
            moshtaryGharardadModel.setTarikhEtebar(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_TarikhEtebar())));
            moshtaryGharardadModel.setCcNoeVisit(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_TarikhEtebar())));
            moshtaryGharardadModel.setNameNoeVisit(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_NameNoeVisit())));
            moshtaryGharardadModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_CodeNoeHaml())));
            moshtaryGharardadModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ModatVosol())));
            moshtaryGharardadModel.setModatTakhirMojaz(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ModatTakhirMojaz())));
            moshtaryGharardadModel.setCcDarkhastFaktorNoeForosh(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccDarkhastFaktorNoeForosh())));
            moshtaryGharardadModel.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_CodeNoeVosolAzMoshtary())));
            moshtaryGharardadModel.setNameNoeVosolAzMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_NameNoeVosolAzMoshtary())));
            moshtaryGharardadModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_CcSazmanForosh())));
            moshtaryGharardadModel.setNameSazmanForosh(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.getCOLUMN_NameSazmanForosh())));

            moshtaryGharardadModels.add(moshtaryGharardadModel);
            cursor.moveToNext();
        }
        return moshtaryGharardadModels;
    }





}
