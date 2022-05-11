package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ElamMarjoeeSatrPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetMarjoeeForoshandehByDarkhastFaktorSatrResult;
import com.saphamrah.protos.ReturnSalesManByInvoiceRequestRowGrpc;
import com.saphamrah.protos.ReturnSalesManByInvoiceRequestRowReply;
import com.saphamrah.protos.ReturnSalesManByInvoiceRequestRowReplyList;
import com.saphamrah.protos.ReturnSalesManByInvoiceRequestRowRequest;

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

public class ElamMarjoeeSatrPPCDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "ElamMarjoeeSatrPPCDAO";

    public ElamMarjoeeSatrPPCDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ElamMarjoeeSatrPPCDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeeSatrPPC(),
            ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor(),
            ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeePPC(),
            ElamMarjoeeSatrPPCModel.COLUMN_ccElatMarjoeeKala(),
            ElamMarjoeeSatrPPCModel.COLUMN_CodeNoeMarjoee(),
            ElamMarjoeeSatrPPCModel.COLUMN_ccKala(),
            ElamMarjoeeSatrPPCModel.COLUMN_ccKalaCode(),
            ElamMarjoeeSatrPPCModel.COLUMN_ShomarehBach(),
            ElamMarjoeeSatrPPCModel.COLUMN_TarikhTolid(),
            ElamMarjoeeSatrPPCModel.COLUMN_TarikhEngheza(),
            ElamMarjoeeSatrPPCModel.COLUMN_Tedad3(),
            ElamMarjoeeSatrPPCModel.COLUMN_Fee(),
            ElamMarjoeeSatrPPCModel.COLUMN_ccTaminkonandeh(),
            ElamMarjoeeSatrPPCModel.COLUMN_GheymatMasrafkonandeh(),
            ElamMarjoeeSatrPPCModel.COLUMN_GheymatForoshAsli(),
            ElamMarjoeeSatrPPCModel.COLUMN_GheymatKharid(),
            ElamMarjoeeSatrPPCModel.COLUMN_IsMabna(),
            ElamMarjoeeSatrPPCModel.COLUMN_ExtraProp_ccMoshtary()
        };
    }

    public void fetchMarjoeeSatrGrpc(final Context context, final String activityNameForLog , String ccDarkhastHavaleh , final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElamMarjoeeSatrPPCDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                ReturnSalesManByInvoiceRequestRowGrpc.ReturnSalesManByInvoiceRequestRowBlockingStub blockingStub = ReturnSalesManByInvoiceRequestRowGrpc.newBlockingStub(managedChannel);
                ReturnSalesManByInvoiceRequestRowRequest request = ReturnSalesManByInvoiceRequestRowRequest.newBuilder().setRowTitleType("2").setDraftRequestID(ccDarkhastHavaleh).build();

                Callable<ReturnSalesManByInvoiceRequestRowReplyList> replyListCallable  = () -> blockingStub.getReturnSalesManByInvoiceRequestRow(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<ElamMarjoeeSatrPPCModel> models = new ArrayList<>();
                            for (ReturnSalesManByInvoiceRequestRowReply reply : replyList.getReturnSalesManByInvoiceRequestRowsList()) {
                                ElamMarjoeeSatrPPCModel model = new ElamMarjoeeSatrPPCModel();


                                model.setCcElamMarjoeeSatrPPC(reply.getReturnAnnouncementRowPPCID());
                                model.setCcDarkhastFaktor(reply.getInvoiceRequestID());
                                model.setCcElamMarjoeePPC(reply.getReturnAnnouncementPPCID());
                                model.setCcElatMarjoeeKala(reply.getGoodsReturnReasonID());
                                model.setCodeNoeMarjoee(reply.getReturnTypeCode());
                                model.setCcKala(reply.getGoodsID());
                                model.setCcKalaCode(reply.getGoodsCodeID());
                                model.setShomarehBach(reply.getBatchNumber());
                                model.setTarikhTolid(reply.getProductionDate());
                                model.setTarikhEngheza(reply.getExpirationDate());
                                model.setTedad3(reply.getQuantity3());
                                model.setFee(reply.getPrice());
                                model.setCcTaminkonandeh(reply.getPrividerID());
                                model.setGheymatMasrafkonandeh(reply.getConsumerPrice());
                                model.setGheymatForoshAsli(reply.getOriginalSellPrice());
                                model.setGheymatKharid(reply.getOriginalSellPrice());
                                model.setIsMabna(reply.getIsBase());


                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ElamMarjoeeSatrPPCModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ElamMarjoeeSatrPPCModel> models) {
                                retrofitResponse.onSuccess(models);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ElamMarjoeeSatrPPCDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }
    }

    public void fetchMarjoeeSatr(final Context context, final String activityNameForLog, final String ccDarkhastHavaleh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMarjoeeSatr", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMarjoeeForoshandehByDarkhastFaktorSatrResult> call = apiServiceGet.getMarjoeeForoshandehByDarkhastFaktorSatr("2" , ccDarkhastHavaleh);
            call.enqueue(new Callback<GetMarjoeeForoshandehByDarkhastFaktorSatrResult>() {
                @Override
                public void onResponse(Call<GetMarjoeeForoshandehByDarkhastFaktorSatrResult> call, Response<GetMarjoeeForoshandehByDarkhastFaktorSatrResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchMarjoeeSatr", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMarjoeeForoshandehByDarkhastFaktorSatrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchMarjoeeSatr", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchMarjoeeSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMarjoeeSatr", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchMarjoeeSatr", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMarjoeeForoshandehByDarkhastFaktorSatrResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchMarjoeeSatr", "onFailure");
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


    public boolean insertGroup(ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel : elamMarjoeeSatrPPCModels)
            {
                ContentValues contentValues = modelToContentvalue(elamMarjoeeSatrPPCModel);
                db.insertOrThrow(ElamMarjoeeSatrPPCModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(elamMarjoeeSatrPPCModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(ElamMarjoeeSatrPPCModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<ElamMarjoeeSatrPPCModel> getAll()
    {
        ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElamMarjoeeSatrPPCModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeeSatrPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "getAll" , "");
        }
        return elamMarjoeeSatrPPCModels;
    }

    public ArrayList<ElamMarjoeeSatrPPCModel> getByccElamMarjoeePPC(long ccElamMarjoee)
    {
        ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElamMarjoeeSatrPPCModel.TableName(), allColumns(), ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeePPC() + " = " + ccElamMarjoee, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeeSatrPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "getAll" , "");
        }
        return elamMarjoeeSatrPPCModels;
    }

    public ArrayList<ElamMarjoeeSatrPPCModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElamMarjoeeSatrPPCModel.TableName(), allColumns(), ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeeSatrPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "getAll" , "");
        }
        return elamMarjoeeSatrPPCModels;
    }

    public int getCountByccDarkhastFaktor(int ccDarkhastFaktor)
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //select count(ccElamMarjoeeSatrPPC) from ElamMarjoeeSatrPPC where ElamMarjoeeSatrPPC.ccDarkhastFaktor = ccDarkhastFaktor
            String query = "select count(" + ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeeSatrPPC() + ") from " + ElamMarjoeeSatrPPCModel.TableName() + " where " + ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "getCountByccDarkhastFaktor" , "");
        }
        return count;
    }

    public long getSumMablaghMarjoeeByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        long sumMablagh = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //select sum(Tedad3*Fee) from ElamMarjoeeSatrPPC where ccDarkhastFaktor = ccDarkhastFaktor
            String query = "select sum(Tedad3*Fee) from ElamMarjoeeSatrPPC where ccDarkhastFaktor = " + ccDarkhastFaktor;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablagh = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "getSumMablaghMarjoeeByccDarkhastFaktor" , "");
        }
        return sumMablagh;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElamMarjoeeSatrPPCModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccElamMarjoeeSatr(int ccElamMarjoeeSatr)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElamMarjoeeSatrPPCModel.TableName(), ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeeSatrPPC() + " = " + ccElamMarjoeeSatr, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "deleteByccElamMarjoeeSatr" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElamMarjoeeSatrPPCModel.TableName(), ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "deleteByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean updateccDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew)
    {
        String query = "update " + ElamMarjoeeSatrPPCModel.TableName() + " set " + ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew + " where " + ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "updateccDarkhastFaktor" , "");
            return false;
        }
    }


    public int updateCount(int ccElamMarjoeeSatr , int newCount)
    {
        //String query = "update " + ElamMarjoeeSatrPPCModel.TableName() + " set " + ElamMarjoeeSatrPPCModel.COLUMN_Tedad3() + " = " + newCount + " where " + ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeeSatrPPC() + " = " + ccElamMarjoeeSatr;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_Tedad3() , newCount);
            int affectedRows = db.update(ElamMarjoeeSatrPPCModel.TableName(), contentValues, ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeeSatrPPC() + " = " + ccElamMarjoeeSatr, null);
            db.close();
            return affectedRows;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeeSatrPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCDAO" , "" , "updateCount" , "");
            return 0;
        }
    }

    private static ContentValues modelToContentvalue(ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel)
    {
        ContentValues contentValues = new ContentValues();

        if (elamMarjoeeSatrPPCModel.getCcElamMarjoeeSatrPPC() > 0)
        {
            contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeeSatrPPC() , elamMarjoeeSatrPPCModel.getCcElamMarjoeeSatrPPC());
        }
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor() , elamMarjoeeSatrPPCModel.getCcDarkhastFaktor());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeePPC() , elamMarjoeeSatrPPCModel.getCcElamMarjoeePPC());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_ccElatMarjoeeKala() , elamMarjoeeSatrPPCModel.getCcElatMarjoeeKala());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_CodeNoeMarjoee() , elamMarjoeeSatrPPCModel.getCodeNoeMarjoee());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_ccKala() , elamMarjoeeSatrPPCModel.getCcKala());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_ccKalaCode() , elamMarjoeeSatrPPCModel.getCcKalaCode());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_ShomarehBach() , elamMarjoeeSatrPPCModel.getShomarehBach());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_TarikhTolid() , elamMarjoeeSatrPPCModel.getTarikhTolid());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_TarikhEngheza() , elamMarjoeeSatrPPCModel.getTarikhEngheza());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_Tedad3() , elamMarjoeeSatrPPCModel.getTedad3());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_Fee() , elamMarjoeeSatrPPCModel.getFee());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_ccTaminkonandeh() , elamMarjoeeSatrPPCModel.getCcTaminkonandeh());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_GheymatMasrafkonandeh() , elamMarjoeeSatrPPCModel.getGheymatMasrafkonandeh());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_GheymatForoshAsli() , elamMarjoeeSatrPPCModel.getGheymatForoshAsli());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_GheymatKharid() , elamMarjoeeSatrPPCModel.getGheymatKharid());
        contentValues.put(ElamMarjoeeSatrPPCModel.COLUMN_IsMabna() , elamMarjoeeSatrPPCModel.getIsMabna());

        return contentValues;
    }


    private ArrayList<ElamMarjoeeSatrPPCModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel = new ElamMarjoeeSatrPPCModel();

            elamMarjoeeSatrPPCModel.setCcElamMarjoeeSatrPPC(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeeSatrPPC())));
            elamMarjoeeSatrPPCModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor())));
            elamMarjoeeSatrPPCModel.setCcElamMarjoeePPC(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeePPC())));
            elamMarjoeeSatrPPCModel.setCcElatMarjoeeKala(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccElatMarjoeeKala())));
            elamMarjoeeSatrPPCModel.setCodeNoeMarjoee(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_CodeNoeMarjoee())));
            elamMarjoeeSatrPPCModel.setCcKala(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccKala())));
            elamMarjoeeSatrPPCModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccKalaCode())));
            elamMarjoeeSatrPPCModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ShomarehBach())));
            elamMarjoeeSatrPPCModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_TarikhTolid())));
            elamMarjoeeSatrPPCModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_TarikhEngheza())));
            elamMarjoeeSatrPPCModel.setTedad3(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_Tedad3())));
            elamMarjoeeSatrPPCModel.setFee(cursor.getDouble(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_Fee())));
            elamMarjoeeSatrPPCModel.setCcTaminkonandeh(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccTaminkonandeh())));
            elamMarjoeeSatrPPCModel.setGheymatMasrafkonandeh(cursor.getDouble(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_GheymatMasrafkonandeh())));
            elamMarjoeeSatrPPCModel.setGheymatForoshAsli(cursor.getDouble(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_GheymatForoshAsli())));
            elamMarjoeeSatrPPCModel.setGheymatKharid(cursor.getDouble(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_GheymatKharid())));
            elamMarjoeeSatrPPCModel.setIsMabna(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_IsMabna())));

            elamMarjoeeSatrPPCModels.add(elamMarjoeeSatrPPCModel);
            cursor.moveToNext();
        }
        return elamMarjoeeSatrPPCModels;
    }



}
