package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.KalaZaribForoshModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.kalaZaribForosh.KalaZaribForoshConstKt;
import com.saphamrah.UIModel.kalaZaribForosh.KalaZaribForoshUiModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvKalaZaribForoshResult;
import com.saphamrah.protos.SalesCoefficientGoodsGrpc;
import com.saphamrah.protos.SalesCoefficientGoodsReply;
import com.saphamrah.protos.SalesCoefficientGoodsReplyList;
import com.saphamrah.protos.SalesCoefficientGoodsRequest;

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

public class KalaZaribForoshDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public KalaZaribForoshDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaZaribForoshDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            KalaZaribForoshModel.COLUMN_ccKalaZaribForosh(),
            KalaZaribForoshModel.COLUMN_ccKalaCode(),
            KalaZaribForoshModel.COLUMN_ccGorohMoshtary(),
            KalaZaribForoshModel.COLUMN_ZaribForosh(),
            KalaZaribForoshModel.COLUMN_Darajeh()
        };
    }

    public void fetchKalaZaribForoshGrpc(final Context context, final String activityNameForLog, int ccAnbarak ,  int ccForoshandeh,  int ccMamorPakhsh,  String ccGorohs, final RetrofitResponse retrofitResponse) {
        try {


            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            serverIpModel.setPort("5000");


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                SalesCoefficientGoodsGrpc.SalesCoefficientGoodsBlockingStub salesCoefficientGoodsBlockingStub = SalesCoefficientGoodsGrpc.newBlockingStub(managedChannel);
                SalesCoefficientGoodsRequest salesCoefficientGoodsRequest = SalesCoefficientGoodsRequest.newBuilder().setBinID(String.valueOf(ccAnbarak)).setSellerID(String.valueOf(ccForoshandeh)).setDistributerID(String.valueOf(ccMamorPakhsh)).setGroupsID(ccGorohs).build();
                Callable<SalesCoefficientGoodsReplyList> getSalesCoefficientGoodsCallable = () -> salesCoefficientGoodsBlockingStub.getSalesCoefficientGoods(salesCoefficientGoodsRequest);
                RxAsync.makeObservable(getSalesCoefficientGoodsCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(salesCoefficientGoodsReplyList -> {
                            ArrayList<KalaZaribForoshModel> kalaZaribForoshModels = new ArrayList<>();
                            for (SalesCoefficientGoodsReply salesCoefficientGoodsReply : salesCoefficientGoodsReplyList.getSalesCoefficientGoodssList()) {
                                KalaZaribForoshModel kalaZaribForoshModel = new KalaZaribForoshModel();

                                kalaZaribForoshModel.setCcKalaZaribForosh(salesCoefficientGoodsReply.getSalesCoefficientGoodsID());
                                kalaZaribForoshModel.setCcKalaCode(salesCoefficientGoodsReply.getCodeGoodsID());
                                kalaZaribForoshModel.setCcGorohMoshtary(salesCoefficientGoodsReply.getCustomerGroupID());
                                kalaZaribForoshModel.setZaribForosh(salesCoefficientGoodsReply.getSalesCoefficient());
                                kalaZaribForoshModel.setDarajeh(salesCoefficientGoodsReply.getDegree());


                                kalaZaribForoshModels.add(kalaZaribForoshModel);
                            }

                            return kalaZaribForoshModels;

                        }).subscribe(new Observer<ArrayList<KalaZaribForoshModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<KalaZaribForoshModel> kalaZaribForoshModels) {
                        retrofitResponse.onSuccess(kalaZaribForoshModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }


    public void fetchAllKalaZaribForosh(final Context context, final String activityNameForLog, final String ccGorohs, String ccMarkazForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvKalaZaribForoshResult> call = apiServiceGet.getAllvKalaZaribForosh(ccGorohs , ccMarkazForosh);
            call.enqueue(new Callback<GetAllvKalaZaribForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvKalaZaribForoshResult> call, Response<GetAllvKalaZaribForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaZaribForoshDAO.class.getSimpleName(), "", "fetchAllKalaZaribForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvKalaZaribForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvKalaZaribForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",  t.getMessage(), endpoint), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllKalaZaribForosh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public void fetchKalaZaribForosh(final Context context, final String activityNameForLog, int ccAnbarak ,  int ccForoshandeh,  int ccMamorPakhsh,  String ccGorohs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchKalaZaribForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvKalaZaribForoshResult> call = apiServiceGet.getKalaZaribForosh(ccAnbarak , ccForoshandeh ,ccMamorPakhsh , ccGorohs);
            call.enqueue(new Callback<GetAllvKalaZaribForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvKalaZaribForoshResult> call, Response<GetAllvKalaZaribForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaZaribForoshDAO.class.getSimpleName(), "", "fetchKalaZaribForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvKalaZaribForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchKalaZaribForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchKalaZaribForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchKalaZaribForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchKalaZaribForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvKalaZaribForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",  t.getMessage(), endpoint), KalaZaribForoshDAO.class.getSimpleName(), activityNameForLog, "fetchKalaZaribForosh", "onFailure");
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

    public boolean insertGroup(ArrayList<KalaZaribForoshModel> kalaZaribForoshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaZaribForoshModel kalaZaribForoshModel : kalaZaribForoshModels)
            {
                ContentValues contentValues = modelToContentvalue(kalaZaribForoshModel);
                db.insertOrThrow(KalaZaribForoshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaZaribForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaZaribForoshDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<KalaZaribForoshModel> getAll()
    {
        ArrayList<KalaZaribForoshModel> kalaZaribForoshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaZaribForoshModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaZaribForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaZaribForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaZaribForoshDAO" , "" , "getAll" , "");
        }
        return kalaZaribForoshModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaZaribForoshModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaZaribForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaZaribForoshDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    public boolean deleteByccKalaZaribForosh(int ccKalaZaribForosh)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaZaribForoshModel.TableName(), KalaZaribForoshModel.COLUMN_ccKalaZaribForosh() + " = " + ccKalaZaribForosh, null) ;
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaZaribForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaZaribForoshDAO" , "" , "deleteByccKalaZaribForosh" , "");
            return false;
        }
    }


    public boolean deleteByccGoroh(String ccGoroh) {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaZaribForoshModel.TableName(), KalaZaribForoshModel.COLUMN_ccKalaZaribForosh() + " in ( " + ccGoroh + " ) ", null) ;
            db.execSQL("delete from "+KalaZaribForoshModel.TableName()+" where "+KalaZaribForoshModel.COLUMN_ccGorohMoshtary() + " in ("+ccGoroh+")");
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaZaribForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaZaribForoshDAO" , "" , "deleteByccGoroh" , "");
            return false;
        }

    }

    public ArrayList<KalaZaribForoshUiModel> getKalaZaribForoshKala(int darajeh , int ccNoeMoshtary){

        ArrayList<KalaZaribForoshUiModel> models = new ArrayList<>();
            try
            {
                String query = "SELECT CodeKala,NameKala,ZaribForosh  \n" +
                        "FROM kalazaribforosh\n" +
                        "join (select distinct ccKalaCode,CodeKala,NameKala from Kala) Kala on Kala.ccKalaCode =  kalazaribforosh.ccKalaCode\n" +
                        "where ccGorohMoshtary= "+ ccNoeMoshtary +" and Darajeh in (0, " + darajeh + ") ";
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null)
                {
                    if (cursor.getCount() > 0)
                    {
                        models = KalaZaribForoshConstKt.cursorToModel(cursor);
                    }
                    cursor.close();
                }
                db.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                String message = context.getResources().getString(R.string.errorSelectAll , BankModel.TableName()) + "\n" + e.toString();
                logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "BankDAO" , "" , "getByCodeBank" , "");
            }
            return models;
        }


    private static ContentValues modelToContentvalue(KalaZaribForoshModel kalaZaribForoshModel)
    {
        ContentValues contentValues = new ContentValues();

        if (kalaZaribForoshModel.getCcKalaZaribForosh() > 0)
        {
            contentValues.put(KalaZaribForoshModel.COLUMN_ccKalaZaribForosh() , kalaZaribForoshModel.getCcKalaZaribForosh());
        }
        contentValues.put(KalaZaribForoshModel.COLUMN_ccKalaCode() , kalaZaribForoshModel.getCcKalaCode());
        contentValues.put(KalaZaribForoshModel.COLUMN_ccGorohMoshtary() , kalaZaribForoshModel.getCcGorohMoshtary());
        contentValues.put(KalaZaribForoshModel.COLUMN_ZaribForosh() , kalaZaribForoshModel.getZaribForosh());
        contentValues.put(KalaZaribForoshModel.COLUMN_Darajeh() , kalaZaribForoshModel.getDarajeh());

        return contentValues;
    }


    private ArrayList<KalaZaribForoshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaZaribForoshModel> kalaZaribForoshModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaZaribForoshModel kalaZaribForoshModel = new KalaZaribForoshModel();

            kalaZaribForoshModel.setCcKalaZaribForosh(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ccKalaZaribForosh())));
            kalaZaribForoshModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ccKalaCode())));
            kalaZaribForoshModel.setCcGorohMoshtary(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ccGorohMoshtary())));
            kalaZaribForoshModel.setZaribForosh(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ZaribForosh())));
            kalaZaribForoshModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_Darajeh())));


            kalaZaribForoshModels.add(kalaZaribForoshModel);
            cursor.moveToNext();
        }
        return kalaZaribForoshModels;
    }
    
    
}
