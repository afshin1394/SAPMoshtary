package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvMarkazShomarehHesabResult;
import com.saphamrah.protos.AcountNumberCenterGrpc;
import com.saphamrah.protos.AcountNumberCenterReply;
import com.saphamrah.protos.AcountNumberCenterReplyList;
import com.saphamrah.protos.AcountNumberCenterRequest;
import com.saphamrah.protos.BankGrpc;
import com.saphamrah.protos.BankReply;
import com.saphamrah.protos.BankReplyList;
import com.saphamrah.protos.BankRequest;

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

public class MarkazShomarehHesabDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MarkazShomarehHesabDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MarkazShomarehHesabDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MarkazShomarehHesabModel.COLUMN_ccMarkaz(),
            MarkazShomarehHesabModel.COLUMN_ccShomarehHesab(),
            MarkazShomarehHesabModel.COLUMN_ShomarehHesab(),
            MarkazShomarehHesabModel.COLUMN_ccBank(),
            MarkazShomarehHesabModel.COLUMN_NameBank(),
            MarkazShomarehHesabModel.COLUMN_CodeShobeh(),
            MarkazShomarehHesabModel.COLUMN_NameShobeh(),
            MarkazShomarehHesabModel.COLUMN_ccNoeHesab(),
            MarkazShomarehHesabModel.COLUMN_NameNoeHesab(),
            MarkazShomarehHesabModel.COLUMN_ShomarehSheba()
        };
    }

    public void fetchMarkazShomareHesabGrpc(final Context context, final String activityNameForLog,final String ccMarkaz, final RetrofitResponse retrofitResponse)
    {
        try {


            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            serverIpModel.setPort("5000");


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                AcountNumberCenterGrpc.AcountNumberCenterBlockingStub acountNumberCenterBlockingStub = AcountNumberCenterGrpc.newBlockingStub(managedChannel);
                AcountNumberCenterRequest acountNumberCenterRequest = AcountNumberCenterRequest.newBuilder().setCenterID(ccMarkaz).build();
                Callable<AcountNumberCenterReplyList> getAcountNumberCenterCallable  = () -> acountNumberCenterBlockingStub.getAcountNumberCenter(acountNumberCenterRequest);
                RxAsync.makeObservable(getAcountNumberCenterCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(acountNumberCenterReplyList -> {
                            ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels = new ArrayList<>();
                            for (AcountNumberCenterReply acountNumberCenterReply : acountNumberCenterReplyList.getAcountNumberCentersList()) {
                                MarkazShomarehHesabModel markazShomarehHesabModel = new MarkazShomarehHesabModel();

                                markazShomarehHesabModel.setCcMarkaz(acountNumberCenterReply.getCenterID());
                                markazShomarehHesabModel.setCcShomarehHesab(acountNumberCenterReply.getAcountNumberID());
                                markazShomarehHesabModel.setShomarehHesab(acountNumberCenterReply.getAcountNumber());
                                markazShomarehHesabModel.setCcBank(acountNumberCenterReply.getBankID());
                                markazShomarehHesabModel.setNameBank(acountNumberCenterReply.getBankName());
                                markazShomarehHesabModel.setCodeShobeh(acountNumberCenterReply.getBranchCode());
                                markazShomarehHesabModel.setNameShobeh(acountNumberCenterReply.getBranchName());
                                markazShomarehHesabModel.setCcNoeHesab(acountNumberCenterReply.getAcountTypeID());
                                markazShomarehHesabModel.setNameNoeHesab(acountNumberCenterReply.getAcountTypeName());
                                markazShomarehHesabModel.setShomarehSheba(acountNumberCenterReply.getIBAN());



                                markazShomarehHesabModels.add(markazShomarehHesabModel);
                            }

                            return markazShomarehHesabModels;

                        }).subscribe(new Observer<ArrayList<MarkazShomarehHesabModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels) {
                        retrofitResponse.onSuccess(markazShomarehHesabModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(),e.getMessage());
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }




    }
    public void fetchMarkazShomareHesab(final Context context, final String activityNameForLog,final String ccMarkaz, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MarkazShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchMarkazShomareHesab", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvMarkazShomarehHesabResult> call = apiServiceGet.getAllvMarkazShomarehHesab(ccMarkaz);
            call.enqueue(new Callback<GetAllvMarkazShomarehHesabResult>() {
                @Override
                public void onResponse(Call<GetAllvMarkazShomarehHesabResult> call, Response<GetAllvMarkazShomarehHesabResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MarkazShomarehHesabDAO.class.getSimpleName(), "", "fetchMarkazShomareHesab", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvMarkazShomarehHesabResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MarkazShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchMarkazShomareHesab", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MarkazShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchMarkazShomareHesab", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MarkazShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchMarkazShomareHesab", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MarkazShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchMarkazShomareHesab", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvMarkazShomarehHesabResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MarkazShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchMarkazShomareHesab", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    private String getEndpoint(Call call)
    {
        String endpoint = "";
        try
        {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
        }
        catch (Exception e)
        {e.printStackTrace();}
        return endpoint;
    }

    public boolean insertGroup(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MarkazShomarehHesabModel markazShomarehHesabModel : markazShomarehHesabModels)
            {
                ContentValues contentValues = modelToContentvalue(markazShomarehHesabModel);
                db.insertOrThrow(MarkazShomarehHesabModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , MarkazShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarkazShomarehHesabDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MarkazShomarehHesabModel> getAll()
    {
        ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MarkazShomarehHesabModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    markazShomarehHesabModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MarkazShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarkazShomarehHesabDAO" , "" , "getAll" , "");
        }
        return markazShomarehHesabModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MarkazShomarehHesabModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MarkazShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarkazShomarehHesabDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MarkazShomarehHesabModel markazShomarehHesabModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MarkazShomarehHesabModel.COLUMN_ccMarkaz() , markazShomarehHesabModel.getCcMarkaz());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_ccShomarehHesab() , markazShomarehHesabModel.getCcShomarehHesab());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_ShomarehHesab() , markazShomarehHesabModel.getShomarehHesab());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_ccBank() , markazShomarehHesabModel.getCcBank());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_NameBank() , markazShomarehHesabModel.getNameBank());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_CodeShobeh() , markazShomarehHesabModel.getCodeShobeh());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_NameShobeh() , markazShomarehHesabModel.getNameShobeh());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_ccNoeHesab() , markazShomarehHesabModel.getCcNoeHesab());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_NameNoeHesab() , markazShomarehHesabModel.getNameNoeHesab());
        contentValues.put(MarkazShomarehHesabModel.COLUMN_ShomarehSheba() , markazShomarehHesabModel.getShomarehSheba());

        return contentValues;
    }


    private ArrayList<MarkazShomarehHesabModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MarkazShomarehHesabModel markazShomarehHesabModel = new MarkazShomarehHesabModel();

            markazShomarehHesabModel.setCcMarkaz(cursor.getInt(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_ccMarkaz())));
            markazShomarehHesabModel.setCcShomarehHesab(cursor.getInt(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_ccShomarehHesab())));
            markazShomarehHesabModel.setShomarehHesab(cursor.getString(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_ShomarehHesab())));
            markazShomarehHesabModel.setCcBank(cursor.getInt(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_ccBank())));
            markazShomarehHesabModel.setNameBank(cursor.getString(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_NameBank())));
            markazShomarehHesabModel.setCodeShobeh(cursor.getString(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_CodeShobeh())));
            markazShomarehHesabModel.setNameShobeh(cursor.getString(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_NameShobeh())));
            markazShomarehHesabModel.setCcNoeHesab(cursor.getInt(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_ccNoeHesab())));
            markazShomarehHesabModel.setNameNoeHesab(cursor.getString(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_NameNoeHesab())));
            markazShomarehHesabModel.setShomarehSheba(cursor.getString(cursor.getColumnIndex(MarkazShomarehHesabModel.COLUMN_ShomarehSheba())));

            markazShomarehHesabModels.add(markazShomarehHesabModel);
            cursor.moveToNext();
        }
        return markazShomarehHesabModels;
    }


}
