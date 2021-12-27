package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.RptKharidKalaModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetKharidKalaByccForoshandehResult;
import com.saphamrah.protos.BuyGoodsBySalesManIDGrpc;
import com.saphamrah.protos.BuyGoodsBySalesManIDReply;
import com.saphamrah.protos.BuyGoodsBySalesManIDReplyList;
import com.saphamrah.protos.BuyGoodsBySalesManIDRequest;

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

public class RptKharidKalaDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public RptKharidKalaDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptKharidKalaDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptKharidKalaModel.COLUMN_Radif(),
            RptKharidKalaModel.COLUMN_ccKalaGoroh(),
            RptKharidKalaModel.COLUMN_NameKalaGoroh(),
            RptKharidKalaModel.COLUMN_CodeGoroh(),
            RptKharidKalaModel.COLUMN_ccKalaCode(),
            RptKharidKalaModel.COLUMN_CodeKala(),
            RptKharidKalaModel.COLUMN_NameKala(),
            RptKharidKalaModel.COLUMN_ccMoshtary(),
            RptKharidKalaModel.COLUMN_MablaghKharid(),
            RptKharidKalaModel.COLUMN_Karton(),
            RptKharidKalaModel.COLUMN_Basteh(),
            RptKharidKalaModel.COLUMN_Adad()
        };
    }

    public void fetchKharidKalaByccForoshandehGrpc(final Context context, final String activityNameForLog, final String level, final String ccForoshandeh, final String ccKalaGoroh, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TizerDAO.class.getSimpleName(), activityNameForLog, "fetchKharidKalaByccForoshandehGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                BuyGoodsBySalesManIDGrpc.BuyGoodsBySalesManIDBlockingStub blockingStub = BuyGoodsBySalesManIDGrpc.newBlockingStub(managedChannel);
                BuyGoodsBySalesManIDRequest request = BuyGoodsBySalesManIDRequest.newBuilder().setLevel(level).setSalesManID(ccForoshandeh).setGroupGoodsID(ccKalaGoroh).build();

                Callable<BuyGoodsBySalesManIDReplyList> buyGoodsBySalesManIDReplyListCallable = () -> blockingStub.getBuyGoodsBySalesManID(request);
                RxAsync.makeObservable(buyGoodsBySalesManIDReplyListCallable)

                        .map(tizerReplyList -> {
                            ArrayList<RptKharidKalaModel> models = new ArrayList<>();
                            for (BuyGoodsBySalesManIDReply reply : tizerReplyList.getBuyGoodsBySalesManIDsList()) {
                                RptKharidKalaModel model = new RptKharidKalaModel();

                                model.setRadif(reply.getRow());
                                model.setCcKalaGoroh(reply.getGroupGoodsID());
                                model.setNameKalaGoroh(reply.getGroupGoodsName());
                                model.setCodeGoroh(reply.getGroupCode());
                                model.setCcKalaCode(reply.getGoodsCodeID());
                                model.setCodeKala(reply.getGoodsCode());
                                model.setNameKala(reply.getGoodsName());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setMablaghKharid(reply.getBuyPrice());
                                model.setKarton(reply.getBox());
                                model.setBasteh(reply.getPackage());
                                model.setAdad(reply.getNumber());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptKharidKalaModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptKharidKalaModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchKharidKalaByccForoshandehGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    public void fetchKharidKalaByccForoshandeh(final Context context, final String activityNameForLog, final String level, final String ccForoshandeh, final String ccKalaGoroh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptKharidKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKharidKalaByccForoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetKharidKalaByccForoshandehResult> call = apiServiceGet.getKharidKalaByccForoshandeh(level , ccForoshandeh , ccKalaGoroh);
            call.enqueue(new Callback<GetKharidKalaByccForoshandehResult>() {
                @Override
                public void onResponse(Call<GetKharidKalaByccForoshandehResult> call, Response<GetKharidKalaByccForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptKharidKalaDAO.class.getSimpleName(), "", "fetchKharidKalaByccForoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetKharidKalaByccForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptKharidKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKharidKalaByccForoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptKharidKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKharidKalaByccForoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptKharidKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKharidKalaByccForoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptKharidKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKharidKalaByccForoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetKharidKalaByccForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptKharidKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKharidKalaByccForoshandeh", "onFailure");
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

    public boolean insertGroup(ArrayList<RptKharidKalaModel> rptKharidKalaModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptKharidKalaModel rptKharidKalaModel : rptKharidKalaModels)
            {
                ContentValues contentValues = modelToContentvalue(rptKharidKalaModel);
                db.insertOrThrow(RptKharidKalaModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptKharidKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKharidKalaDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptKharidKalaModel> getAll()
    {
        ArrayList<RptKharidKalaModel> rptKharidKalaModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptKharidKalaModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptKharidKalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptKharidKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKharidKalaDAO" , "" , "getAll" , "");
        }
        return rptKharidKalaModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptKharidKalaModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptKharidKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKharidKalaDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptKharidKalaModel rptKharidKalaModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(RptKharidKalaModel.COLUMN_Radif() , rptKharidKalaModel.getRadif());
        contentValues.put(RptKharidKalaModel.COLUMN_ccKalaGoroh() , rptKharidKalaModel.getCcKalaGoroh());
        contentValues.put(RptKharidKalaModel.COLUMN_NameKalaGoroh() , rptKharidKalaModel.getNameKalaGoroh());
        contentValues.put(RptKharidKalaModel.COLUMN_CodeGoroh() , rptKharidKalaModel.getCodeGoroh());
        contentValues.put(RptKharidKalaModel.COLUMN_ccKalaCode() , rptKharidKalaModel.getCcKalaCode());
        contentValues.put(RptKharidKalaModel.COLUMN_CodeKala() , rptKharidKalaModel.getCodeKala());
        contentValues.put(RptKharidKalaModel.COLUMN_NameKala() , rptKharidKalaModel.getNameKala());
        contentValues.put(RptKharidKalaModel.COLUMN_ccMoshtary() , rptKharidKalaModel.getCcMoshtary());
        contentValues.put(RptKharidKalaModel.COLUMN_MablaghKharid() , rptKharidKalaModel.getMablaghKharid());
        contentValues.put(RptKharidKalaModel.COLUMN_Karton() , rptKharidKalaModel.getKarton());
        contentValues.put(RptKharidKalaModel.COLUMN_Basteh() , rptKharidKalaModel.getBasteh());
        contentValues.put(RptKharidKalaModel.COLUMN_Adad() , rptKharidKalaModel.getAdad());

        return contentValues;
    }


    private ArrayList<RptKharidKalaModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptKharidKalaModel> rptKharidKalaModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptKharidKalaModel rptKharidKalaModel = new RptKharidKalaModel();

            rptKharidKalaModel.setRadif(cursor.getInt(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_Radif())));
            rptKharidKalaModel.setCcKalaGoroh(cursor.getInt(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_ccKalaGoroh())));
            rptKharidKalaModel.setNameKalaGoroh(cursor.getString(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_NameKalaGoroh())));
            rptKharidKalaModel.setCodeGoroh(cursor.getString(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_CodeGoroh())));
            rptKharidKalaModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_ccKalaCode())));
            rptKharidKalaModel.setCodeKala(cursor.getString(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_CodeKala())));
            rptKharidKalaModel.setNameKala(cursor.getString(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_NameKala())));
            rptKharidKalaModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_ccMoshtary())));
            rptKharidKalaModel.setMablaghKharid(cursor.getInt(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_MablaghKharid())));
            rptKharidKalaModel.setKarton(cursor.getInt(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_Karton())));
            rptKharidKalaModel.setBasteh(cursor.getInt(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_Basteh())));
            rptKharidKalaModel.setAdad(cursor.getInt(cursor.getColumnIndex(RptKharidKalaModel.COLUMN_Adad())));

            rptKharidKalaModels.add(rptKharidKalaModel);
            cursor.moveToNext();
        }
        return rptKharidKalaModels;
    }



}
