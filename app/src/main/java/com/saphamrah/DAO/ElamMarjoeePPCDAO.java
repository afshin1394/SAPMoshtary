package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ElamMarjoeePPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetMarjoeeForoshandehByDarkhastFaktorTitrResult;
import com.saphamrah.protos.ReturnSalesManByInvoiceRequestTitleGrpc;
import com.saphamrah.protos.ReturnSalesManByInvoiceRequestTitleReply;
import com.saphamrah.protos.ReturnSalesManByInvoiceRequestTitleReplyList;
import com.saphamrah.protos.ReturnSalesManByInvoiceRequestTitleRequest;

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

public class ElamMarjoeePPCDAO
{

    private static final String CLASS_NAME = "ElamMarjoeePPCDAO";
    private DBHelper dbHelper;
    private Context context;


    public ElamMarjoeePPCDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ElamMarjoeePPCDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ElamMarjoeePPCModel.COLUMN_ccElamMarjoeePPC(),
            ElamMarjoeePPCModel.COLUMN_ccDarkhastFaktor(),
            ElamMarjoeePPCModel.COLUMN_ccForoshandeh(),
            ElamMarjoeePPCModel.COLUMN_ccMoshtary(),
            ElamMarjoeePPCModel.COLUMN_TarikhElamMarjoee(),
            ElamMarjoeePPCModel.COLUMN_Elat(),
            ElamMarjoeePPCModel.COLUMN_TedadAghlamMarjoee()
        };
    }

    public void fetchMarjoeeGrpc(final Context context, final String activityNameForLog , String ccDarkhastHavaleh , final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElamMarjoeePPCDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                ReturnSalesManByInvoiceRequestTitleGrpc.ReturnSalesManByInvoiceRequestTitleBlockingStub blockingStub = ReturnSalesManByInvoiceRequestTitleGrpc.newBlockingStub(managedChannel);
                ReturnSalesManByInvoiceRequestTitleRequest request = ReturnSalesManByInvoiceRequestTitleRequest.newBuilder().setRowTitleType("1").setDraftRequestID(ccDarkhastHavaleh).build();

                Callable<ReturnSalesManByInvoiceRequestTitleReplyList> replyListCallable  = () -> blockingStub.getReturnSalesManByInvoiceRequestTitle(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<ElamMarjoeePPCModel> models = new ArrayList<>();
                            for (ReturnSalesManByInvoiceRequestTitleReply reply : replyList.getReturnSalesManByInvoiceRequestTitlesList()) {
                                ElamMarjoeePPCModel model = new ElamMarjoeePPCModel();

                                model.setCcElamMarjoeePPC(reply.getReturnAnnouncementPPC());
                                model.setCcDarkhastFaktor(reply.getInvoiceRequestID());
                                model.setCcForoshandeh(reply.getSalesManID());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setTarikhElamMarjoee(reply.getReturnAnnouncementDate());
                                model.setElat(reply.getReason());
                                model.setTedadAghlamMarjoee(reply.getReturnItemsQuantity());


                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ElamMarjoeePPCModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ElamMarjoeePPCModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ElamMarjoeePPCDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }
    }

    public void fetchMarjoee(final Context context, final String activityNameForLog, final String ccDarkhastHavaleh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMarjoee", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetMarjoeeForoshandehByDarkhastFaktorTitrResult> call = apiServiceGet.getMarjoeeForoshandehByDarkhastFaktorTitr("1" , ccDarkhastHavaleh);
            call.enqueue(new Callback<GetMarjoeeForoshandehByDarkhastFaktorTitrResult>() {
                @Override
                public void onResponse(Call<GetMarjoeeForoshandehByDarkhastFaktorTitrResult> call, Response<GetMarjoeeForoshandehByDarkhastFaktorTitrResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchMarjoee", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMarjoeeForoshandehByDarkhastFaktorTitrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchMarjoee", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchMarjoee", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMarjoee", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchMarjoee", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMarjoeeForoshandehByDarkhastFaktorTitrResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchMarjoee", "onFailure");
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

    public boolean insertGroup(ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ElamMarjoeePPCModel elamMarjoeePPCModel : elamMarjoeePPCModels)
            {
                ContentValues contentValues = modelToContentvalue(elamMarjoeePPCModel);
                db.insertOrThrow(ElamMarjoeePPCModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public int insert(ElamMarjoeePPCModel elamMarjoeePPCModel)
    {
        int ccElamMarjoeePPC = -1;
        try
        {
            ContentValues contentValues = modelToContentvalue(elamMarjoeePPCModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long lngccElamMarjoeePPC = db.insertOrThrow(ElamMarjoeePPCModel.TableName() , null , contentValues);
            db.close();
            return (int)lngccElamMarjoeePPC;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "insert" , "");
            return ccElamMarjoeePPC;
        }
    }

    public ArrayList<ElamMarjoeePPCModel> getAll()
    {
        ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElamMarjoeePPCModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeePPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "getAll" , "");
        }
        return elamMarjoeePPCModels;
    }

    public ArrayList<ElamMarjoeePPCModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElamMarjoeePPCModel.TableName(), allColumns(), ElamMarjoeePPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeePPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return elamMarjoeePPCModels;
    }

    public int getccElamMarjoeeByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        int ccElamMarjoeePPC = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select " + ElamMarjoeePPCModel.COLUMN_ccElamMarjoeePPC() + " from " + ElamMarjoeePPCModel.TableName() + " where " + ElamMarjoeePPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccElamMarjoeePPC = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "getCountByccDarkhastFaktor" , "");
        }
        return ccElamMarjoeePPC;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElamMarjoeePPCModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    public boolean deleteAllByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElamMarjoeePPCModel.TableName(), ElamMarjoeePPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "deleteAllByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean deleteByccElamMarjoee(int ccElamMarjoee)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElamMarjoeePPCModel.TableName(), ElamMarjoeePPCModel.COLUMN_ccElamMarjoeePPC() + " = " + ccElamMarjoee, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "deleteByccElamMarjoee" , "");
            return false;
        }
    }

    public boolean updateccDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew)
    {
        String query = "update " + ElamMarjoeePPCModel.TableName() + " set " + ElamMarjoeePPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew + " where " + ElamMarjoeePPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
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
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeePPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeePPCDAO" , "" , "updateccDarkhastFaktor" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ElamMarjoeePPCModel elamMarjoeePPCModel)
    {
        ContentValues contentValues = new ContentValues();

        if (elamMarjoeePPCModel.getCcElamMarjoeePPC() > 0)
        {
            contentValues.put(ElamMarjoeePPCModel.COLUMN_ccElamMarjoeePPC() , elamMarjoeePPCModel.getCcElamMarjoeePPC());
        }
        contentValues.put(ElamMarjoeePPCModel.COLUMN_ccDarkhastFaktor() , elamMarjoeePPCModel.getCcDarkhastFaktor());
        contentValues.put(ElamMarjoeePPCModel.COLUMN_ccForoshandeh() , elamMarjoeePPCModel.getCcForoshandeh());
        contentValues.put(ElamMarjoeePPCModel.COLUMN_ccMoshtary() , elamMarjoeePPCModel.getCcMoshtary());
        contentValues.put(ElamMarjoeePPCModel.COLUMN_TarikhElamMarjoee() , elamMarjoeePPCModel.getTarikhElamMarjoee());
        contentValues.put(ElamMarjoeePPCModel.COLUMN_Elat() , elamMarjoeePPCModel.getElat());
        contentValues.put(ElamMarjoeePPCModel.COLUMN_TedadAghlamMarjoee() , elamMarjoeePPCModel.getTedadAghlamMarjoee());

        return contentValues;
    }

    private ArrayList<ElamMarjoeePPCModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ElamMarjoeePPCModel elamMarjoeePPCModel = new ElamMarjoeePPCModel();

            elamMarjoeePPCModel.setCcElamMarjoeePPC(cursor.getInt(cursor.getColumnIndex(ElamMarjoeePPCModel.COLUMN_ccElamMarjoeePPC())));
            elamMarjoeePPCModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(ElamMarjoeePPCModel.COLUMN_ccDarkhastFaktor())));
            elamMarjoeePPCModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(ElamMarjoeePPCModel.COLUMN_ccForoshandeh())));
            elamMarjoeePPCModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(ElamMarjoeePPCModel.COLUMN_ccMoshtary())));
            elamMarjoeePPCModel.setTarikhElamMarjoee(cursor.getString(cursor.getColumnIndex(ElamMarjoeePPCModel.COLUMN_TarikhElamMarjoee())));
            elamMarjoeePPCModel.setElat(cursor.getString(cursor.getColumnIndex(ElamMarjoeePPCModel.COLUMN_Elat())));
            elamMarjoeePPCModel.setTedadAghlamMarjoee(cursor.getInt(cursor.getColumnIndex(ElamMarjoeePPCModel.COLUMN_TedadAghlamMarjoee())));

            elamMarjoeePPCModels.add(elamMarjoeePPCModel);
            cursor.moveToNext();
        }
        return elamMarjoeePPCModels;
    }


}
