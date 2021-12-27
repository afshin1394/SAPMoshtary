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
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.RptMoshtaryGharardadUiModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;

import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryGharardadKalaResult;
import com.saphamrah.protos.CustomerContractGoodsGrpc;
import com.saphamrah.protos.CustomerContractGoodsReply;
import com.saphamrah.protos.CustomerContractGoodsReplyList;
import com.saphamrah.protos.CustomerContractGoodsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryGharardadKalaDAO {
    private DBHelper dbHelper;
    private Context context;


    public MoshtaryGharardadKalaDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryGhararDadKalaDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        MoshtaryGharardadKalaModel.COLUMN_radif(),
                        MoshtaryGharardadKalaModel.COLUMN_ccMoshtaryGharardad(),
                        MoshtaryGharardadKalaModel.COLUMN_ccKalaCode(),
                        MoshtaryGharardadKalaModel.COLUMN_MablaghForosh(),
                        MoshtaryGharardadKalaModel.COLUMN_MablaghMasrafKonandeh(),
                        MoshtaryGharardadKalaModel.COLUMN_ControlMablagh(),
                        MoshtaryGharardadKalaModel.COLUMN_ExtraPropCcSazmanForosh()

                };
    }

    public void fetchMoshtaryGharadadKalaGrpc(final Context context, final String activityNameForLog, int ccSazmanForosh, int ccMoshtaryGharardad, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharadadKalaGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerContractGoodsGrpc.CustomerContractGoodsBlockingStub customerContractGoodsBlockingStub = CustomerContractGoodsGrpc.newBlockingStub(managedChannel);
                CustomerContractGoodsRequest customerContractGoodsRequest = CustomerContractGoodsRequest.newBuilder().setCustomerContractID(String.valueOf(ccMoshtaryGharardad)).setSellOrganizationID(String.valueOf(ccSazmanForosh)).build();
                Callable<CustomerContractGoodsReplyList> customerContractGoodsReplyListCallable = () -> customerContractGoodsBlockingStub.getCustomerContractGoods(customerContractGoodsRequest);
                RxAsync.makeObservable(customerContractGoodsReplyListCallable)
                        .map(customerContractGoodsReplyList  ->  {
                            ArrayList<MoshtaryGharardadKalaModel> models = new ArrayList<>();
                            for (CustomerContractGoodsReply reply : customerContractGoodsReplyList.getCustomerContractGoodsList()) {

                                MoshtaryGharardadKalaModel model = new MoshtaryGharardadKalaModel();

                                model.setCcMoshtaryGharardad(reply.getCustomerContractID());
                                model.setCcKalaCode(reply.getGoodCodeID());
                                model.setControlMablagh(reply.getPriceControl());
                                model.setRadif(reply.getIndex());
                                model.setMablaghForosh(reply.getSellPrice());
                                model.setMablaghMasrafKonandeh(reply.getConsumerPrice());

                                models.add(model);
                            }
                            return models;
                        })
                        .subscribe(new Observer<ArrayList<MoshtaryGharardadKalaModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModels) {
                                retrofitResponse.onSuccess(moshtaryGharardadKalaModels);
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
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharadadKalaGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }


    @SuppressLint("LongLogTag")
    public void fetchMoshtaryGharadadKala(final Context context, final String activityNameForLog, int ccSazmanForosh, int ccMoshtaryGharardad, final RetrofitResponse retrofitResponse) {

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharadadKala", "");
            retrofitResponse.onFailed(Constants.HTTP_ERROR(), message);
        } else {
            Log.i("fetchMoshtaryGharardadkala", "fetchMoshtaryGharardadkala" + serverIpModel.getServerIp() + " " + serverIpModel.getPort());
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtaryGharardadKalaResult> call = apiServiceGet.getMoshtaryGharardadKala((String.valueOf(ccSazmanForosh)), (String.valueOf(ccMoshtaryGharardad)));

            try {
                Response<GetAllMoshtaryGharardadKalaResult> response = call.execute();
                try {
                    if (response.raw().body() != null) {
                        long contentLength = response.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: Response is not Null");
                        logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryGharardadKalaDAO.class.getSimpleName(), "", "fetchMoshtaryGharadadKala", "onResponse");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "exception in response.raw().body()");
                }
                try {

                    if (response.isSuccessful()) {
                        GetAllMoshtaryGharardadKalaResult result = response.body();
                        if (result != null) {
                            if (result.getSuccess()) {
                                result.setData(result.getData());
                                if (result.getData() != null) {
                                    if (result.getData().size() > 0) {
                                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: getTheResponse" + result.getData().get(0).getCcKalaCode() + " " + response.body());
                                        retrofitResponse.onSuccess(result.getData());

                                    }
                                }
                            } else {
                                Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: resultNotSuccessfull");
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                            }

                        } else {
                            Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: retrofit result is null");
                            String endpoint = getEndpoint(call);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                        }

                    } else {
                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: failed on Api Call");
                        String endpoint = getEndpoint(call);
                        String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                    }
                } catch (Exception exception) {
                    Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: checking weather response is successfull" + exception.getMessage());
                    exception.printStackTrace();
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onResponse");
                    retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());

                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onFailure: 8");
                String endpoint = getEndpoint(call);
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", e.getMessage(), endpoint), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onFailure");
                retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), e.getMessage());
            }


        }

    }

    //TODO get gharardad faktor zangirei
    @SuppressLint("LongLogTag")
    public void fetchMoshtaryGharadadKalaSync(final Context context, final String activityNameForLog, int ccSazmanForosh, int ccMoshtaryGharardad,RetrofitResponse retrofitResponse) {

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharadadKala", "");
            retrofitResponse.onFailed(Constants.HTTP_ERROR(), message);

        } else {
            Log.i("fetchMoshtaryGharardadkala", "fetchMoshtaryGharardadkala" + serverIpModel.getServerIp() + " " + serverIpModel.getPort());
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtaryGharardadKalaResult> call = apiServiceGet.getMoshtaryGharardadKala((String.valueOf(ccSazmanForosh)), (String.valueOf(ccMoshtaryGharardad)));

            try {
                Response<GetAllMoshtaryGharardadKalaResult> response = call.execute();
                try {
                    if (response.raw().body() != null) {
                        long contentLength = response.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: Response is not Null");
                        logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryGharardadKalaDAO.class.getSimpleName(), "", "fetchMoshtaryGharadadKala", "onResponse");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "exception in response.raw().body()");
                }
                try {

                    if (response.isSuccessful()) {
                        GetAllMoshtaryGharardadKalaResult result = response.body();
                        if (result != null) {
                            if (result.getSuccess()) {
                                result.setData(result.getData());
                                if (result.getData() != null) {
                                    if (result.getData().size() > 0) {
                                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: getTheResponse" + result.getData().get(0).getCcKalaCode() + " " + response.body());
                                        retrofitResponse.onSuccess(result.getData());

                                    }
                                }
                            } else {
                                Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: resultNotSuccessfull");
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                            }

                        } else {
                            Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: retrofit result is null");
                            String endpoint = getEndpoint(call);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                        }

                    } else {
                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: failed on Api Call");
                        String endpoint = getEndpoint(call);
                        String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                    }
                } catch (Exception exception) {
                    Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: checking weather response is successfull" + exception.getMessage());
                    exception.printStackTrace();
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onResponse");
                    retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onFailure: 8");
                String endpoint = getEndpoint(call);
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", e.getMessage(), endpoint), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onFailure");
                retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), e.getMessage());

            }


        }


    }


    private String getEndpoint(Call call) {
        String endpoint = "";
        try {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endpoint;
    }



    public boolean insert(MoshtaryGharardadKalaModel moshtaryGharardadKalaModel) {
        try {
            ContentValues contentValues = modelToContentvalue(moshtaryGharardadKalaModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(MoshtaryGharardadKalaModel.TableName(), null, contentValues);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert, MoshtaryGharardadKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryGharardadKalaDao", "", "insert", "");
            return false;
        }


    }
    public static final String GET_TABLE_COUNT="getTableCount";
    public int getTableItemsCount() {
        Cursor cursor=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strQry = "select * from " + MoshtaryGharardadKalaModel.TableName();
        try {
            cursor=db.rawQuery(strQry, null);
            Log.i(GET_TABLE_COUNT, "getTableItemsCount: "+cursor.getCount());
        } catch (Exception exception) {

        }
        return cursor.getCount();
    }

    public boolean insertGroupAll(ArrayList<ArrayList<MoshtaryGharardadKalaModel> > moshtaryGharardadKalaModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModelArrayList : moshtaryGharardadKalaModels) {
                for (MoshtaryGharardadKalaModel moshtaryGharardadKalaModel:moshtaryGharardadKalaModelArrayList) {
                    ContentValues contentValues = modelToContentvalue(moshtaryGharardadKalaModel);
                    db.insertOrThrow(MoshtaryGharardadKalaModel.TableName(), null, contentValues);
                }
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

    public ArrayList<RptMoshtaryGharardadUiModel> getKalaByCcMoshtaryGharardadAndCcSazmanForosh(String ccMoshtaryGharardad, String ccSazmanForosh){
        ArrayList<RptMoshtaryGharardadUiModel> models = new ArrayList<>();
        String query = "SELECT k.CodeKala, k.NameKala  , m.MablaghMasrafKonandeh , m.MablaghForosh , m.ControlMablagh ,m.ccKalaCode \n" +
                "FROM MoshtaryGharardadKala m \n" +
                "LEFT JOIN (SELECT DISTINCT ccKalaCode,CodeKala, NameKala FROM Kala) k ON k.ccKalaCode = m.ccKalaCode \n" +
                "WHERE IFNULL(CodeKala,'0')<>'0' AND m.ccMoshtaryGharardad = " + ccMoshtaryGharardad + " AND m.ExtraPropCcSazmanForosh =  " + ccSazmanForosh +"  \n" +
                "ORDER BY CodeKala";
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModelUi(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception){
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryGharardadKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryGharardadKalaDAO" , "" , "getKalaByCcMoshtaryGharardadAndCcSazmanForosh" , "");

        }
        return models;

    }


    public boolean insertGroup(ArrayList<MoshtaryGharardadKalaModel>  moshtaryGharardadKalaModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
                for (MoshtaryGharardadKalaModel moshtaryGharardadKalaModel : moshtaryGharardadKalaModels) {
                    ContentValues contentValues = modelToContentvalue(moshtaryGharardadKalaModel);
                    db.insertOrThrow(MoshtaryGharardadKalaModel.TableName(), null, contentValues);

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

    public boolean deleteAll() {

        try {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryGharardadKalaModel.TableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, MoshtaryGharardadKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryGharardadDAO", "", "deleteAll", "");
            return false;
        }
    }

    //TODO
    private static ContentValues modelToContentvalue(MoshtaryGharardadKalaModel moshtaryGharardadKalaModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_radif(), moshtaryGharardadKalaModel.getRadif());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_ccMoshtaryGharardad(), moshtaryGharardadKalaModel.getCcMoshtaryGharardad());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_ccKalaCode(), moshtaryGharardadKalaModel.getCcKalaCode());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_MablaghForosh(), moshtaryGharardadKalaModel.getMablaghForosh());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_MablaghMasrafKonandeh(), moshtaryGharardadKalaModel.getMablaghMasrafKonandeh());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_ControlMablagh(), moshtaryGharardadKalaModel.getControlMablagh());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_ExtraPropCcSazmanForosh(),moshtaryGharardadKalaModel.getExtraprop_ccSazmanForosh());


        return contentValues;
    }


    private ArrayList<MoshtaryGharardadKalaModel> cursorToModel(Cursor cursor) {
        ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MoshtaryGharardadKalaModel moshtaryGharardadKalaModel = new MoshtaryGharardadKalaModel();

            moshtaryGharardadKalaModel.setRadif(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_radif())));
            moshtaryGharardadKalaModel.setCcMoshtaryGharardad(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_ccMoshtaryGharardad())));
            moshtaryGharardadKalaModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_ccKalaCode())));
            moshtaryGharardadKalaModel.setMablaghForosh(cursor.getLong(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_MablaghForosh())));
            moshtaryGharardadKalaModel.setMablaghMasrafKonandeh(cursor.getLong(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_MablaghMasrafKonandeh())));
            moshtaryGharardadKalaModel.setControlMablagh(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_ControlMablagh())));
            moshtaryGharardadKalaModel.setExtraprop_ccSazmanForosh(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_ExtraPropCcSazmanForosh())));
            moshtaryGharardadKalaModels.add(moshtaryGharardadKalaModel);
            cursor.moveToNext();
        }
        return moshtaryGharardadKalaModels;
    }
    private ArrayList<RptMoshtaryGharardadUiModel> cursorToModelUi(Cursor cursor) {
        ArrayList<RptMoshtaryGharardadUiModel> moshtaryGharardadKalaModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RptMoshtaryGharardadUiModel moshtaryGharardadKalaModel = new RptMoshtaryGharardadUiModel();

            moshtaryGharardadKalaModel.setCodeKala(cursor.getInt(cursor.getColumnIndex(RptMoshtaryGharardadUiModel.COLUMN_CodeKala())));
            moshtaryGharardadKalaModel.setNameKala(cursor.getString(cursor.getColumnIndex(RptMoshtaryGharardadUiModel.COLUMN_NameKala())));
            moshtaryGharardadKalaModel.setMablaghForosh(cursor.getLong(cursor.getColumnIndex(RptMoshtaryGharardadUiModel.COLUMN_MablaghForosh())));
            moshtaryGharardadKalaModel.setMablaghMasrafKonandeh(cursor.getLong(cursor.getColumnIndex(RptMoshtaryGharardadUiModel.COLUMN_MablaghMasrafKonandeh())));
            moshtaryGharardadKalaModel.setControlMablagh(cursor.getInt(cursor.getColumnIndex(RptMoshtaryGharardadUiModel.COLUMN_ControlMablagh())));
            moshtaryGharardadKalaModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(RptMoshtaryGharardadUiModel.COLUMN_ccKalaCode())));
            moshtaryGharardadKalaModels.add(moshtaryGharardadKalaModel);
            cursor.moveToNext();
        }
        return moshtaryGharardadKalaModels;
    }
}
