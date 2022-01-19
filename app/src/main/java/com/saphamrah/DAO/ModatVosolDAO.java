package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ModatVosolModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvModatVosolByccMarkazForoshGorohResult;
import com.saphamrah.protos.ReceiptDurationByGroupSellCenterIDGrpc;
import com.saphamrah.protos.ReceiptDurationByGroupSellCenterIDReply;
import com.saphamrah.protos.ReceiptDurationByGroupSellCenterIDReplyList;
import com.saphamrah.protos.ReceiptDurationByGroupSellCenterIDRequest;

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

public class ModatVosolDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ModatVosolDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ModatVosolDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ModatVosolModel.COLUMN_ccModatVosol(),
            ModatVosolModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            ModatVosolModel.COLUMN_CodeNoe(),
            ModatVosolModel.COLUMN_SharhModatVosol(),
            ModatVosolModel.COLUMN_Az(),
            ModatVosolModel.COLUMN_Ta(),
            ModatVosolModel.COLUMN_ModatVosol(),
            ModatVosolModel.COLUMN_Darajeh(),
            ModatVosolModel.COLUMN_ccBrand(),
            ModatVosolModel.COLUMN_ccGoroh(),
            ModatVosolModel.COLUMN_ccGorohKala()
        };
    }


    public void fetchAllvModatVosolByccMarkazForoshGorohGrpc(final Context context, final String activityNameForLog, final String ccMarkazForosh, final String ccGorohs, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGorohGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                ReceiptDurationByGroupSellCenterIDGrpc.ReceiptDurationByGroupSellCenterIDBlockingStub receiptDurationByGroupSellCenterIDBlockingStub = ReceiptDurationByGroupSellCenterIDGrpc.newBlockingStub(managedChannel);
                ReceiptDurationByGroupSellCenterIDRequest receiptDurationByGroupSellCenterIDRequest = ReceiptDurationByGroupSellCenterIDRequest.newBuilder().setSellCenterID(ccMarkazForosh).setGroupsID(ccGorohs).build();
                Callable<ReceiptDurationByGroupSellCenterIDReplyList> receiptDurationByGroupSellCenterIDReplyListCallable = () -> receiptDurationByGroupSellCenterIDBlockingStub.getReceiptDurationByGroupSellCenterID(receiptDurationByGroupSellCenterIDRequest);
                RxAsync.makeObservable(receiptDurationByGroupSellCenterIDReplyListCallable)
                        .map(receiptDurationByGroupSellCenterIDReplyList -> {
                            ArrayList<ModatVosolModel> modatVosolModels = new ArrayList<>();
                            for (ReceiptDurationByGroupSellCenterIDReply receiptDurationByGroupSellCenterIDReply : receiptDurationByGroupSellCenterIDReplyList.getReceiptDurationByGroupSellCenterIDsList()) {

                                ModatVosolModel modatVosolModel = new ModatVosolModel();

                                modatVosolModel.setCcModatVosol(receiptDurationByGroupSellCenterIDReply.getReceiptDurationID());
                                modatVosolModel.setCcMarkazSazmanForoshSakhtarForosh(receiptDurationByGroupSellCenterIDReply.getSellStructureSellOrganizationCenterID());
                                modatVosolModel.setCodeNoe(receiptDurationByGroupSellCenterIDReply.getTypeCode());
                                modatVosolModel.setSharhModatVosol(receiptDurationByGroupSellCenterIDReply.getReceiptDurationDescription());
                                modatVosolModel.setCcBrand(receiptDurationByGroupSellCenterIDReply.getBrandID());
                                modatVosolModel.setCcGorohKala(receiptDurationByGroupSellCenterIDReply.getGoodsGroupID());
                                modatVosolModel.setCcGoroh(receiptDurationByGroupSellCenterIDReply.getGroupID());
                                modatVosolModel.setAz(receiptDurationByGroupSellCenterIDReply.getFrom());
                                modatVosolModel.setTa(receiptDurationByGroupSellCenterIDReply.getTo());
                                modatVosolModel.setModatVosol(receiptDurationByGroupSellCenterIDReply.getReceiptDuration());

                                modatVosolModels.add(modatVosolModel);
                            }

                            return modatVosolModels;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ModatVosolModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ModatVosolModel> darkhastFaktorModels) {
                                retrofitResponse.onSuccess(darkhastFaktorModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGorohGrpc", "CatchException");
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
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGorohGrpc", "CatchException");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }


    public void fetchAllvModatVosolByccMarkazForoshGoroh(final Context context, final String activityNameForLog, final String ccMarkazForosh, final String ccGorohs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvModatVosolByccMarkazForoshGorohResult> call = apiServiceGet.getAllvModatVosolByccMarkazForoshGoroh(ccMarkazForosh , ccGorohs);
            call.enqueue(new Callback<GetAllvModatVosolByccMarkazForoshGorohResult>() {
                @Override
                public void onResponse(Call<GetAllvModatVosolByccMarkazForoshGorohResult> call, Response<GetAllvModatVosolByccMarkazForoshGorohResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ModatVosolDAO.class.getSimpleName(), "", "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvModatVosolByccMarkazForoshGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = "message : " + response.message() + "\n" + "code : " + response.code();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvModatVosolByccMarkazForoshGorohResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<ModatVosolModel> modatVosolModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ModatVosolModel modatVosolModel : modatVosolModels)
            {
                Log.d("modatVosol" , "Modat Vosol DAO : " + modatVosolModel.toString());
                ContentValues contentValues = modelToContentvalue(modatVosolModel);
                db.insertOrThrow(ModatVosolModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ModatVosolModel> getAll()
    {
        ArrayList<ModatVosolModel> modatVosolModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ModatVosolModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    modatVosolModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "getAll" , "");
        }
        return modatVosolModels;
    }

    public ArrayList<ModatVosolModel> getForMohasebehModatvosol(int ccGoroh, int daraje)
    {
        ArrayList<ModatVosolModel> modatVosolModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ModatVosolModel.TableName(), allColumns(), "ccGoroh = " + ccGoroh + " AND Darajeh in (0, " + daraje + ")", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    modatVosolModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "getForMohasebehModatvosol" , "");
        }
        Log.d("ModatVosolDAO","modatVosolModels : " + modatVosolModels);
        return modatVosolModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ModatVosolModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMarkazAndccGoroh(String ccMarkaz , String ccGorohs)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ModatVosolModel.TableName(), ModatVosolModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() + " = " + ccMarkaz + " and " + ModatVosolModel.COLUMN_ccGoroh() + " in (" + ccGorohs + ")", null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "deleteByccMarkazAndccGoroh" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ModatVosolModel.TableName(), MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ModatVosolModel modatVosolModel)
    {
        ContentValues contentValues = new ContentValues();

        if (modatVosolModel.getCcModatVosol() > 0)
        {
            contentValues.put(ModatVosolModel.COLUMN_ccModatVosol() , modatVosolModel.getCcModatVosol());
        }
        contentValues.put(ModatVosolModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , modatVosolModel.getCcMarkazSazmanForoshSakhtarForosh());
        contentValues.put(ModatVosolModel.COLUMN_CodeNoe() , modatVosolModel.getCodeNoe());
        contentValues.put(ModatVosolModel.COLUMN_SharhModatVosol() , modatVosolModel.getSharhModatVosol());
        contentValues.put(ModatVosolModel.COLUMN_Az() , modatVosolModel.getAz());
        contentValues.put(ModatVosolModel.COLUMN_Ta() , modatVosolModel.getTa());
        contentValues.put(ModatVosolModel.COLUMN_ModatVosol() , modatVosolModel.getModatVosol());
        contentValues.put(ModatVosolModel.COLUMN_Darajeh() , modatVosolModel.getDarajeh());
        contentValues.put(ModatVosolModel.COLUMN_ccBrand() , modatVosolModel.getCcBrand());
        contentValues.put(ModatVosolModel.COLUMN_ccGoroh() , modatVosolModel.getCcGoroh());
        contentValues.put(ModatVosolModel.COLUMN_ccGorohKala() , modatVosolModel.getCcGorohKala());

        return contentValues;
    }

    private ArrayList<ModatVosolModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ModatVosolModel> modatVosolModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ModatVosolModel modatVosolModel = new ModatVosolModel();

            modatVosolModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccModatVosol())));
            modatVosolModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));
            modatVosolModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_CodeNoe())));
            modatVosolModel.setSharhModatVosol(cursor.getString(cursor.getColumnIndex(ModatVosolModel.COLUMN_SharhModatVosol())));
            modatVosolModel.setAz(cursor.getFloat(cursor.getColumnIndex(ModatVosolModel.COLUMN_Az())));
            modatVosolModel.setTa(cursor.getFloat(cursor.getColumnIndex(ModatVosolModel.COLUMN_Ta())));
            modatVosolModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ModatVosol())));
            modatVosolModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_Darajeh())));
            modatVosolModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccBrand())));
            modatVosolModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccGoroh())));
            modatVosolModel.setCcGorohKala(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccGorohKala())));

            modatVosolModels.add(modatVosolModel);
            cursor.moveToNext();
        }
        return modatVosolModels;
    }
    
    
}
