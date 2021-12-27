package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.BarkhordForoshandehBaMoshtaryResult;
import com.saphamrah.protos.SalesManBehaviorWithCustomerGrpc;
import com.saphamrah.protos.SalesManBehaviorWithCustomerReply;
import com.saphamrah.protos.SalesManBehaviorWithCustomerReplyList;
import com.saphamrah.protos.SalesManBehaviorWithCustomerRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class BarkhordForoshandehBaMoshtaryDAO
{


    private DBHelper dbHelper;
    private Context context;


    public BarkhordForoshandehBaMoshtaryDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "BarkhordForoshandehBaMoshtaryDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            BarkhordForoshandehBaMoshtaryModel.COLUMN_ccBarkhordForoshandeh(),
            BarkhordForoshandehBaMoshtaryModel.COLUMN_ccForoshandeh(),
            BarkhordForoshandehBaMoshtaryModel.COLUMN_ccMoshtary(),
            BarkhordForoshandehBaMoshtaryModel.COLUMN_Tarikh(),
            BarkhordForoshandehBaMoshtaryModel.COLUMN_Tozihat(),
            BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite(),
            BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld(),
            BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_Recent(),

        };
    }

    public void fetchBarkhordForoshandehBaMoshtaryGrpc(final Context context, final String activityNameForLog,final String ccForoshandeh, final String ccMoshtarys, final String tedadMah, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchRotbehGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                SalesManBehaviorWithCustomerGrpc.SalesManBehaviorWithCustomerBlockingStub blockingStub = SalesManBehaviorWithCustomerGrpc.newBlockingStub(managedChannel);
                SalesManBehaviorWithCustomerRequest request = SalesManBehaviorWithCustomerRequest.newBuilder().setSalesManID(ccForoshandeh).setCustomersID(ccMoshtarys).setNumberMonths(tedadMah).build();

                Callable<SalesManBehaviorWithCustomerReplyList> replyListCallable  = () -> blockingStub.getSalesManBehaviorWithCustomer(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<BarkhordForoshandehBaMoshtaryModel> models = new ArrayList<>();
                            for (SalesManBehaviorWithCustomerReply reply : replyList.getSalesManBehaviorWithCustomersList()) {
                                BarkhordForoshandehBaMoshtaryModel model = new BarkhordForoshandehBaMoshtaryModel();

                                model.setCcBarkhordForoshandeh(reply.getSalesManBehaviorID());
                                model.setCcForoshandeh(reply.getSalesManID());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setTarikh(reply.getDate());
                                model.setTozihat(reply.getDescription());
                                model.setIsFavorite(reply.getIsFavorite());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<BarkhordForoshandehBaMoshtaryModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<BarkhordForoshandehBaMoshtaryModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchBarkhordForoshandehBaMoshtaryGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }
    }


    public void fetchBarkhordForoshandehBaMoshtary(final Context context, final String activityNameForLog,final String ccForoshandeh, final String ccMoshtarys, final String tedadMah, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchBarkhordForoshandehBaMoshtary", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<BarkhordForoshandehBaMoshtaryResult> call = apiServiceGet.getBarkhordForoshandehBaMoshtary(ccForoshandeh, ccMoshtarys, tedadMah);
            call.enqueue(new Callback<BarkhordForoshandehBaMoshtaryResult>() {
                @Override
                public void onResponse(Call<BarkhordForoshandehBaMoshtaryResult> call, Response<BarkhordForoshandehBaMoshtaryResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), "", "fetchBarkhordForoshandehBaMoshtary", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            BarkhordForoshandehBaMoshtaryResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchBarkhordForoshandehBaMoshtary", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE() , result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull), endpoint), BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchBarkhordForoshandehBaMoshtary", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL() , context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchBarkhordForoshandehBaMoshtary", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchBarkhordForoshandehBaMoshtary", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<BarkhordForoshandehBaMoshtaryResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchBarkhordForoshandehBaMoshtary", "onFailure");
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

    public boolean insertGroup(ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (BarkhordForoshandehBaMoshtaryModel barkhordForoshandehBaMoshtaryModel : barkhordForoshandehBaMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(barkhordForoshandehBaMoshtaryModel);
                db.insertOrThrow(BarkhordForoshandehBaMoshtaryModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName() , "" , "insertGroup" , "");
            return false;
        }
    }

    public long insert(BarkhordForoshandehBaMoshtaryModel model)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(model);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long id = db.insertOrThrow(BarkhordForoshandehBaMoshtaryModel.TableName(), null , contentValues);
            db.close();
            return id;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BarkhordForoshandehBaMoshtaryDAO" , "" , "insert" , "");
            return -1;
        }
    }

    public ArrayList<BarkhordForoshandehBaMoshtaryModel> getAll()
    {
        ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(BarkhordForoshandehBaMoshtaryModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    barkhordForoshandehBaMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName() , "" , "getAll" , "");
        }
        return barkhordForoshandehBaMoshtaryModels;
    }

    /**
     * get Barkhords by ccMoshtary and ordered by date desc
     * @return arraylist of BarkhordForoshandehBaMoshtaryModel
     */
    public ArrayList<BarkhordForoshandehBaMoshtaryModel> getAllByccMoshtary(int ccMoshtary)
    {
        ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " SELECT *\n" +
                    "FROM "+BarkhordForoshandehBaMoshtaryModel.TableName()+" \n" +
                    "Where "+BarkhordForoshandehBaMoshtaryModel.COLUMN_ccMoshtary()+ " = "+ccMoshtary+" \n" +
                    "ORDER BY \n" +
                    "  CASE WHEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_Recent()+" = 1  THEN "+ BarkhordForoshandehBaMoshtaryModel.COLUMN_Tarikh()+" END DESC,\n" +
                    "  CASE WHEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite()+" = 1 AND "+BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld()+" = 0 THEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_Tarikh()+" END DESC,\n" +
                    "  CASE WHEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite()+" = 1 AND "+BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld()+" = 1 THEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite()+" END DESC,\n" +
                    "  CASE WHEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite()+" = 0 AND "+BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld()+" = 0 THEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_Tarikh()+" END DESC,\n" +
                    "  CASE WHEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite()+" = 0 AND "+BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld()+" = 1 THEN "+BarkhordForoshandehBaMoshtaryModel.COLUMN_Tarikh()+" END DESC\n" +
                    ";";
            Cursor cursor = db.rawQuery(query,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    barkhordForoshandehBaMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName() , "" , "getAllByccMoshtary" , "");
        }
        return barkhordForoshandehBaMoshtaryModels;
    }

    /**
     *
     * @param ccBarkhords
     * @param operator add =0 remove=1
     * @return
     */
    public boolean updateIsFavoriteByccBarkhord(int ccBarkhords,boolean operator)
    {
    try
        {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //makeFavorite
        if (!operator)
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite() , 0);
        //makeNotFavorite
        else
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite() , 1);

        db.update(BarkhordForoshandehBaMoshtaryModel.TableName(),contentValues, BarkhordForoshandehBaMoshtaryModel.COLUMN_ccBarkhordForoshandeh() + " = " + ccBarkhords,null);
        db.close();
        return true;
    }
        catch (Exception exception)
    {
        exception.printStackTrace();
        PubFunc.Logger logger = new PubFunc().new Logger();
        String message = context.getResources().getString(R.string.errorUpdate , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryModel.TableName() , "" , "updateIsFavoriteByccBarkhord" , "");
        return false;
    }

    }

    public int getCountTodayByccMoshtary(int ccMoshtary)
    {
        int count = 0;
        try
        {
            String query = "select count (" + BarkhordForoshandehBaMoshtaryModel.COLUMN_ccBarkhordForoshandeh() + " ) from " + BarkhordForoshandehBaMoshtaryModel.TableName() +
                    " where " + BarkhordForoshandehBaMoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and strftime('%Y-%m-%d' , date('now')) = strftime('%Y-%m-%d' , Tarikh)";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
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
            String message = context.getResources().getString(R.string.errorSelectAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName() , "" , "getCountTodayByccMoshtary" , "");
        }
        return count;
    }

    public ArrayList<BarkhordForoshandehBaMoshtaryModel> getAllByccMoshtaryAndNotSend(int ccMoshtary)
    {
        ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(BarkhordForoshandehBaMoshtaryModel.TableName(), allColumns(), BarkhordForoshandehBaMoshtaryModel.COLUMN_ccMoshtary() + "=" + ccMoshtary + " and " + BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld() + " = 0", null, null, null, BarkhordForoshandehBaMoshtaryModel.COLUMN_Tarikh() + " desc");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    barkhordForoshandehBaMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName() , "" , "getAllByccMoshtary" , "");
        }
        return barkhordForoshandehBaMoshtaryModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(BarkhordForoshandehBaMoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BarkhordForoshandehBaMoshtaryDAO.class.getSimpleName() , "" , "deleteAll" , "");
            return false;
        }
    }


    public boolean deleteByccBarkhord(int ccBarkhord , int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(BarkhordForoshandehBaMoshtaryModel.TableName(), BarkhordForoshandehBaMoshtaryModel.COLUMN_ccBarkhordForoshandeh() + " = " + ccBarkhord + " and " + BarkhordForoshandehBaMoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BarkhordForoshandehBaMoshtaryDAO" , "" , "deleteByccBarkhord" , "");
            return false;
        }
    }

    public boolean updateGroupSendToServer(String ccBarkhords)
    {
        String query = "update " + BarkhordForoshandehBaMoshtaryModel.TableName() + " set " + BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld() + " = 1 where " + BarkhordForoshandehBaMoshtaryModel.COLUMN_ccBarkhordForoshandeh() + " in (" + ccBarkhords + ")";
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
            String message = context.getResources().getString(R.string.errorDeleteAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BarkhordForoshandehBaMoshtaryDAO" , "" , "updateSendToServer" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(BarkhordForoshandehBaMoshtaryModel barkhordForoshandehBaMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();

        if (barkhordForoshandehBaMoshtaryModel.getCcBarkhordForoshandeh() != null && barkhordForoshandehBaMoshtaryModel.getCcBarkhordForoshandeh() > 0)
        {
            contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_ccBarkhordForoshandeh() , barkhordForoshandehBaMoshtaryModel.getCcBarkhordForoshandeh());
        }
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_ccForoshandeh() , barkhordForoshandehBaMoshtaryModel.getCcForoshandeh());
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_ccMoshtary() , barkhordForoshandehBaMoshtaryModel.getCcMoshtary());
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_Tarikh() , barkhordForoshandehBaMoshtaryModel.getTarikh());
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_Tozihat() , barkhordForoshandehBaMoshtaryModel.getTozihat());
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite() , barkhordForoshandehBaMoshtaryModel.getIsFavorite());
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld() , barkhordForoshandehBaMoshtaryModel.getExtraProp_IsOld());
        contentValues.put(BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_Recent() , barkhordForoshandehBaMoshtaryModel.ExtraProp_IsRecent());


        return contentValues;
    }


    private ArrayList<BarkhordForoshandehBaMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            BarkhordForoshandehBaMoshtaryModel barkhordForoshandehBaMoshtaryModel = new BarkhordForoshandehBaMoshtaryModel();

            barkhordForoshandehBaMoshtaryModel.setCcBarkhordForoshandeh(cursor.getInt(cursor.getColumnIndex(BarkhordForoshandehBaMoshtaryModel.COLUMN_ccBarkhordForoshandeh())));
            barkhordForoshandehBaMoshtaryModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(BarkhordForoshandehBaMoshtaryModel.COLUMN_ccForoshandeh())));
            barkhordForoshandehBaMoshtaryModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(BarkhordForoshandehBaMoshtaryModel.COLUMN_ccMoshtary())));
            barkhordForoshandehBaMoshtaryModel.setTarikh(cursor.getString(cursor.getColumnIndex(BarkhordForoshandehBaMoshtaryModel.COLUMN_Tarikh())));
            barkhordForoshandehBaMoshtaryModel.setTozihat(cursor.getString(cursor.getColumnIndex(BarkhordForoshandehBaMoshtaryModel.COLUMN_Tozihat())));
            barkhordForoshandehBaMoshtaryModel.setIsFavorite(cursor.getInt(cursor.getColumnIndex(BarkhordForoshandehBaMoshtaryModel.COLUMN_IsFavorite())));
            barkhordForoshandehBaMoshtaryModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld())));
            barkhordForoshandehBaMoshtaryModel.setExtraProp_IsRecent(cursor.getInt(cursor.getColumnIndex(BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_IsOld())));

            barkhordForoshandehBaMoshtaryModels.add(barkhordForoshandehBaMoshtaryModel);
            cursor.moveToNext();
        }
        return barkhordForoshandehBaMoshtaryModels;
    }


    private Date convertStringToDate(String strDate)
    {
        Date date = new Date();
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            date = sdf.parse(strDate);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return date;
    }


    public boolean updateAllRecentBarkhords() {

        String query = "update " + BarkhordForoshandehBaMoshtaryModel.TableName() + " set " + BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_Recent() + " = 0 where " + BarkhordForoshandehBaMoshtaryModel.COLUMN_ExtraProp_Recent() + " = 1 ";
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
            String message = context.getResources().getString(R.string.errorDeleteAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BarkhordForoshandehBaMoshtaryDAO" , "" , "updateAllRecentBarkhords" , "");
            return false;
        }
    }
}
