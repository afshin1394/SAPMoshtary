package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.MarjoeeMamorPakhshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ConcurrencyUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.MarjoeeMamorPakhshResult;
import com.saphamrah.protos.DistributerReturnGrpc;
import com.saphamrah.protos.DistributerReturnReply;
import com.saphamrah.protos.DistributerReturnReplyList;
import com.saphamrah.protos.DistributerReturnRequest;

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

public class MarjoeeMamorPakhshDAO
{

    MarjoeeMamorPakhshModel modelGetTABLE_NAME = new MarjoeeMamorPakhshModel();
    private DBHelper dbHelper;


    /*
    create constructor
     */
    public MarjoeeMamorPakhshDAO(Context context)
    {

        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MarjoeeMamorPakhsh" , "" , "constructor" , "");
        }
    }

    /*
get name all columns in model
 */
    private String[] allColumns()
    {
        return new String[]
                {
                        modelGetTABLE_NAME.getCOLUM_ccMarjoeeMamorPakhsh(),
                        modelGetTABLE_NAME.getCOLUM_ccKala(),
                        modelGetTABLE_NAME.getCOLUM_ccKalaCode(),
                        modelGetTABLE_NAME.getCOLUM_CodeKalaOld(),
                        modelGetTABLE_NAME.getCOLUM_NameKala(),
                        modelGetTABLE_NAME.getCOLUM_ccTaminKonandeh(),
                        modelGetTABLE_NAME.getCOLUM_ShomarehBach(),
                        modelGetTABLE_NAME.getCOLUM_TarikhTolid(),
                        modelGetTABLE_NAME.getCOLUM_TarikhTolidShamsi(),
                        modelGetTABLE_NAME.getCOLUM_TarikhEngheza(),
                        modelGetTABLE_NAME.getCOLUM_ccMoshtary(),
                        modelGetTABLE_NAME.getCOLUM_NameMoshtary(),
                        modelGetTABLE_NAME.getCOLUM_ccAnbarMarjoee(),
                        modelGetTABLE_NAME.getCOLUM_mablaghKharid(),
                        modelGetTABLE_NAME.getCOLUM_mablaghForosh(),
                        modelGetTABLE_NAME.getCOLUM_mablaghForoshKhales(),
                        modelGetTABLE_NAME.getCOLUM_mablaghMasrafKonandeh(),
                        modelGetTABLE_NAME.getCOLUM_Tedad3(),
                        modelGetTABLE_NAME.getCOLUM_ExtraProp_ccElatMarjoee(),
                        modelGetTABLE_NAME.getCOLUM_ExtraProp_NameElatMarjoee(),
                        modelGetTABLE_NAME.getCOLUM_ExtraProp_TedadNahaeeMarjoee(),
                        modelGetTABLE_NAME.getCOLUM_ccAnbarGhesmat()
                };
    }

    public void fetchMarjoeeMamorPakhshGrpc(Context context  ,  String activityNameForLog, String ccMoshtarys,  RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MarjoeeMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                DistributerReturnGrpc.DistributerReturnBlockingStub blockingStub = DistributerReturnGrpc.newBlockingStub(managedChannel);
                DistributerReturnRequest request = DistributerReturnRequest.newBuilder().setCustomersID(ccMoshtarys).build();

                Callable<DistributerReturnReplyList> replyListCallable = () -> blockingStub.getDistributerReturn(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<MarjoeeMamorPakhshModel> models = new ArrayList<>();
                            for (DistributerReturnReply reply : replyList.getDistributerReturnsList()) {
                                MarjoeeMamorPakhshModel model = new MarjoeeMamorPakhshModel();

                                model.setCcMarjoeeMamorPakhsh(reply.getDistributerReturnID());
                                model.setCcKala(reply.getGoodsID());
                                model.setCcKalaCode(reply.getGoodsCodeID());
                                model.setCodeKalaOld(reply.getOldGoodsCode());
                                model.setNameKala(reply.getGoodsName());
                                model.setCcTaminKonandeh(reply.getPrividerID());
                                model.setShomarehBach(reply.getBatchNumber());
                                model.setTarikhTolid(reply.getProductionDate());
                                model.setTarikhTolidShamsi(reply.getShamsiProductionDate());
                                model.setTarikhEngheza(reply.getExpirationDate());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setCcAnbarMarjoee(reply.getReturnStoreName());
                                model.setMablaghKharid(reply.getBuyPrice());
                                model.setMablaghForosh(reply.getSellPrice());
                                model.setMablaghForoshKhales(reply.getPureSellPrice());
                                model.setMablaghMasrafKonandeh(reply.getConsumerPrice());
                                model.setTedad3(reply.getQuantity3());
                                model.setCcAnbarGhesmat(reply.getStorePortionID());
                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MarjoeeMamorPakhshModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MarjoeeMamorPakhshModel> models) {
                                retrofitResponse.onSuccess(models);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MarjoeeMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }

    public void fetchMarjoeeMamorPakhsh( Context context  ,  String activityNameForLog, String ccMoshtarys,  RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MarjoeeMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchMarjoeeMamorPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<MarjoeeMamorPakhshResult> call = apiServiceGet.getMarjoeeMamorPakhsh(ccMoshtarys);
            call.enqueue(new Callback<MarjoeeMamorPakhshResult>()
            {
                @Override
                public void onResponse(Call<MarjoeeMamorPakhshResult> call, Response<MarjoeeMamorPakhshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MarjoeeMamorPakhshDAO.class.getSimpleName(), "", "fetchMarjoeeMamorPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            MarjoeeMamorPakhshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MarjoeeMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchMarjoeeMamorPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = "";
                                try
                                {
                                    endpoint = call.request().url().toString();
                                    endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                                }catch (Exception e){e.printStackTrace();}
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), MarjoeeMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchMarjoeeMamorPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = "";
                            try
                            {
                                endpoint = call.request().url().toString();
                                endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                            }catch (Exception e){e.printStackTrace();}
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MarjoeeMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchMarjoeeMamorPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MarjoeeMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchMarjoeeMamorPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<MarjoeeMamorPakhshResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MarjoeeMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchMarjoeeMamorPakhsh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    /*
    set result model to DB
     */
    public boolean insertGroup(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MarjoeeMamorPakhshModel marjoeeMamorPakhshModel : marjoeeMamorPakhshModels)
            {
                ContentValues contentValues = modelToContentvalue(marjoeeMamorPakhshModel);
                db.insertOrThrow(modelGetTABLE_NAME.getCOLUM_TABLE_NAME() , null , contentValues);

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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorGroupInsert , modelGetTABLE_NAME.getCOLUM_TABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "MarjoeeMamorPakhshDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    // get all data as db
    public ArrayList<MarjoeeMamorPakhshModel> getAll()
    {
        ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getCOLUM_TABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    marjoeeMamorPakhshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getCOLUM_TABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "MarjoeeMamorPakhshDAO" , "" , "getAll" , "");
        }
        return marjoeeMamorPakhshModels;
    }

    /**
     * for use runOnUiThread
     */
    public interface CallBack{
        void call(ArrayList<MarjoeeMamorPakhshModel> models);
    }

    // get by ccmoshtary
    public void getByccmoshtary(int ccmoshtary , CallBack callBack)
    {

         new Thread(new Runnable() {
             @Override
             public void run() {
                 ArrayList<MarjoeeMamorPakhshModel> models = new ArrayList<>();
                 try
                 {

                     SQLiteDatabase db = dbHelper.getReadableDatabase();
                     String query = "select * from " + modelGetTABLE_NAME.getCOLUM_TABLE_NAME() + " where " + modelGetTABLE_NAME.getCOLUM_ccMoshtary() + " = " + ccmoshtary ;


                     Cursor cursor = db.rawQuery(query , null);
                     if (cursor != null)
                     {
                         if (cursor.getCount() > 0)
                         {
                             models = cursorToModel(cursor);
                         }
                         cursor.close();
                     }
                     db.close();
                 }
                 catch (Exception exception)
                 {
                     exception.printStackTrace();
                     PubFunc.Logger logger = new PubFunc().new Logger();
                     String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getCOLUM_TABLE_NAME())  + "\n" + exception.toString();
                     logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "MarjoeeMamorPakhshDAO" , "" , "getByccmoshtary" , "");
                 }


                 ArrayList<MarjoeeMamorPakhshModel> finalModels = models;
                 ConcurrencyUtils.getInstance().runOnUiThread(() -> {
                     callBack.call(finalModels);
                 });
             }
         }).start();


    }

    public boolean updateTedadMarjoee(int ccMarjoeeMamorPakhsh , String  ShomarehBach ,int ccTaminKonandeh ,float mablaghForosh  ,float mablaghMasrafKonandeh  , int ExtraProp_TedadMarjoee)
    { String query = "update " + modelGetTABLE_NAME.getCOLUM_TABLE_NAME() + " set " + modelGetTABLE_NAME.getCOLUM_ExtraProp_TedadNahaeeMarjoee() + " = " + ExtraProp_TedadMarjoee +
            " where " + modelGetTABLE_NAME.getCOLUM_ccMarjoeeMamorPakhsh() + " = " + ccMarjoeeMamorPakhsh + " AND " + modelGetTABLE_NAME.getCOLUM_ShomarehBach() + " = '" + ShomarehBach + "' AND "
            + modelGetTABLE_NAME.getCOLUM_ccTaminKonandeh() + " = " + ccTaminKonandeh + " AND "
            + modelGetTABLE_NAME.getCOLUM_mablaghForosh() + " = " + mablaghForosh + " AND " + modelGetTABLE_NAME.getCOLUM_mablaghMasrafKonandeh() + " = " + mablaghMasrafKonandeh ;
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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorUpdate , modelGetTABLE_NAME.getCOLUM_TABLE_NAME() ) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "MarjoeeMamorPakhshDAO" , "" , "updateMablaghMarjoee" , "");
            return false;
        }
    }



    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(modelGetTABLE_NAME.getCOLUM_TABLE_NAME(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll , modelGetTABLE_NAME.getCOLUM_TABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "MarjoeeMamorPakhshDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private ContentValues modelToContentvalue(MarjoeeMamorPakhshModel pakhshModel)
    {
        ContentValues contentValues = new ContentValues();
        MarjoeeMamorPakhshModel model = new MarjoeeMamorPakhshModel();
        contentValues.put(model.getCOLUM_ccMarjoeeMamorPakhsh(),pakhshModel.getCcMarjoeeMamorPakhsh());
        contentValues.put(model.getCOLUM_ccKala(),pakhshModel.getCcKala());
        contentValues.put(model.getCOLUM_ccKalaCode(),pakhshModel.getCcKalaCode());
        contentValues.put(model.getCOLUM_CodeKalaOld(),pakhshModel.getCodeKalaOld());
        contentValues.put(model.getCOLUM_NameKala(),pakhshModel.getNameKala());
        contentValues.put(model.getCOLUM_ccTaminKonandeh(),pakhshModel.getCcTaminKonandeh());
        contentValues.put(model.getCOLUM_ShomarehBach(),pakhshModel.getShomarehBach());
        contentValues.put(model.getCOLUM_TarikhTolid(),pakhshModel.getTarikhTolid());
        contentValues.put(model.getCOLUM_TarikhTolidShamsi(),pakhshModel.getTarikhTolidShamsi());
        contentValues.put(model.getCOLUM_TarikhEngheza(),pakhshModel.getTarikhEngheza());
        contentValues.put(model.getCOLUM_ccMoshtary(),pakhshModel.getCcMoshtary());
        contentValues.put(model.getCOLUM_NameMoshtary(),pakhshModel.getNameMoshtary());
        contentValues.put(model.getCOLUM_ccAnbarMarjoee(),pakhshModel.getCcAnbarMarjoee());
        contentValues.put(model.getCOLUM_mablaghKharid(),pakhshModel.getMablaghKharid());
        contentValues.put(model.getCOLUM_mablaghForosh(),pakhshModel.getMablaghForosh());
        contentValues.put(model.getCOLUM_mablaghForoshKhales(),pakhshModel.getMablaghForoshKhales());
        contentValues.put(model.getCOLUM_mablaghMasrafKonandeh(),pakhshModel.getMablaghMasrafKonandeh());
        contentValues.put(model.getCOLUM_Tedad3(),pakhshModel.getTedad3());
        contentValues.put(model.getCOLUM_ExtraProp_ccElatMarjoee(),pakhshModel.getExtraProp_ccElatMarjoee());
        contentValues.put(model.getCOLUM_ExtraProp_NameElatMarjoee(),pakhshModel.getExtraProp_NameElatMarjoee());
        contentValues.put(model.getCOLUM_ExtraProp_TedadNahaeeMarjoee(),pakhshModel.getExtraProp_TedadNahaeeMarjoee());
        contentValues.put(model.getCOLUM_ccAnbarGhesmat(),pakhshModel.getCcAnbarMarjoee());


        return contentValues;
    }


    /*
    set  cursor to model in get All
     */
    private ArrayList<MarjoeeMamorPakhshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels = new ArrayList<>();
        int count = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MarjoeeMamorPakhshModel model = new MarjoeeMamorPakhshModel();
            model.setCcMarjoeeMamorPakhsh(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ccMarjoeeMamorPakhsh())));
            model.setCcKala(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ccKala())));
            model.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ccKalaCode())));
            model.setCodeKalaOld(cursor.getString(cursor.getColumnIndex(model.getCOLUM_CodeKalaOld())));
            model.setNameKala(cursor.getString(cursor.getColumnIndex(model.getCOLUM_NameKala())));
            model.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ccTaminKonandeh())));
            model.setShomarehBach(cursor.getString(cursor.getColumnIndex(model.getCOLUM_ShomarehBach())));
            model.setTarikhTolid(cursor.getString(cursor.getColumnIndex(model.getCOLUM_TarikhTolid())));
            model.setTarikhTolidShamsi(cursor.getString(cursor.getColumnIndex(model.getCOLUM_TarikhTolidShamsi())));
            model.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(model.getCOLUM_TarikhEngheza())));
            model.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ccMoshtary())));
            model.setNameMoshtary(cursor.getString(cursor.getColumnIndex(model.getCOLUM_NameMoshtary())));
            model.setCcAnbarMarjoee(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ccAnbarMarjoee())));
            model.setMablaghKharid(cursor.getLong(cursor.getColumnIndex(model.getCOLUM_mablaghKharid())));
            model.setMablaghForosh(cursor.getLong((cursor.getColumnIndex(model.getCOLUM_mablaghForosh()))));
            model.setMablaghForoshKhales(cursor.getDouble((cursor.getColumnIndex(model.getCOLUM_mablaghForoshKhales()))));
            model.setMablaghMasrafKonandeh(cursor.getLong((cursor.getColumnIndex(model.getCOLUM_mablaghMasrafKonandeh()))));
            model.setTedad3(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_Tedad3())));
            model.setExtraProp_ccElatMarjoee(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ExtraProp_ccElatMarjoee())));
            model.setExtraProp_NameElatMarjoee(cursor.getString(cursor.getColumnIndex(model.getCOLUM_ExtraProp_NameElatMarjoee())));
            model.setExtraProp_TedadNahaeeMarjoee(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ExtraProp_TedadNahaeeMarjoee())));
            model.setCcAnbarGhesmat(cursor.getInt(cursor.getColumnIndex(model.getCOLUM_ccAnbarGhesmat())));
            model.setPositionAll(count);
            count++;

            marjoeeMamorPakhshModels.add(model);
            cursor.moveToNext();
        }
        return marjoeeMamorPakhshModels;
    }



}
