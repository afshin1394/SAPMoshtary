package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.MasirVaznHajmMashinModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetMairVaznHajmMashinResult;
import com.saphamrah.protos.DirectionCarWeightVolumeGrpc;
import com.saphamrah.protos.DirectionCarWeightVolumeReply;
import com.saphamrah.protos.DirectionCarWeightVolumeReplyList;
import com.saphamrah.protos.DirectionCarWeightVolumeRequest;

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

public class MasirVaznHajmMashinDAO
{


    private DBHelper dbHelper;
    private Context context;


    public MasirVaznHajmMashinDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MasirVaznHajmMashinDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MasirVaznHajmMashinModel.COLUMN_ccMasir(),
            MasirVaznHajmMashinModel.COLUMN_ccMoshtary(),
            MasirVaznHajmMashinModel.COLUMN_VaznMashin(),
            MasirVaznHajmMashinModel.COLUMN_HajmMashin()
        };
    }

    public void fetchMasirVaznHajmMashinGrpc(final Context context, final String activityNameForLog,final String ccMasir, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MasirVaznHajmMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMasirVaznHajmMashinGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                DirectionCarWeightVolumeGrpc.DirectionCarWeightVolumeBlockingStub blockingStub = DirectionCarWeightVolumeGrpc.newBlockingStub(managedChannel);
                DirectionCarWeightVolumeRequest request =DirectionCarWeightVolumeRequest.newBuilder().setRouteID(ccMasir).build();

                Callable<DirectionCarWeightVolumeReplyList> replyListCallable = () -> blockingStub.getDirectionCarWeightVolume(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<MasirVaznHajmMashinModel> models = new ArrayList<>();
                            for (DirectionCarWeightVolumeReply reply : replyList.getDirectionCarWeightVolumesList()) {
                                MasirVaznHajmMashinModel model = new MasirVaznHajmMashinModel();

                                model.setCcMasir(reply.getRouteID());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setVaznMashin(reply.getCarWeight());
                                model.setHajmMashin(reply.getCarCapacity());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MasirVaznHajmMashinModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MasirVaznHajmMashinModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ParameterDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }

    public void fetchMasirVaznHajmMashin(final Context context, final String activityNameForLog,final String ccMasir, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MasirVaznHajmMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMasirVaznHajmMashin", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMairVaznHajmMashinResult> call = apiServiceGet.getMairVaznHajmMashin(ccMasir);
            call.enqueue(new Callback<GetMairVaznHajmMashinResult>() {
                @Override
                public void onResponse(Call<GetMairVaznHajmMashinResult> call, Response<GetMairVaznHajmMashinResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MasirVaznHajmMashinDAO.class.getSimpleName(), "", "fetchMasirVaznHajmMashin", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMairVaznHajmMashinResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MasirVaznHajmMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMasirVaznHajmMashin", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MasirVaznHajmMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMasirVaznHajmMashin", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MasirVaznHajmMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMasirVaznHajmMashin", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MasirVaznHajmMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMasirVaznHajmMashin", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMairVaznHajmMashinResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MasirVaznHajmMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMasirVaznHajmMashin", "onFailure");
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

    public boolean insertGroup(ArrayList<MasirVaznHajmMashinModel> masirVaznHajmMashinModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MasirVaznHajmMashinModel masirVaznHajmMashinModel : masirVaznHajmMashinModels)
            {
                ContentValues contentValues = modelToContentvalue(masirVaznHajmMashinModel);
                db.insertOrThrow(MasirVaznHajmMashinModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MasirVaznHajmMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirVaznHajmMashinDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(MasirVaznHajmMashinModel masirVaznHajmMashinModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(masirVaznHajmMashinModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(MasirVaznHajmMashinModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , MasirVaznHajmMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirVaznHajmMashinDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<MasirVaznHajmMashinModel> getAll()
    {
        ArrayList<MasirVaznHajmMashinModel> masirVaznHajmMashinModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MasirVaznHajmMashinModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    masirVaznHajmMashinModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MasirVaznHajmMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirVaznHajmMashinDAO" , "" , "getAll" , "");
        }
        return masirVaznHajmMashinModels;
    }

    public MasirVaznHajmMashinModel getByccMoshtary(int ccMoshtary)
    {
        MasirVaznHajmMashinModel masirVaznHajmMashinModel = new MasirVaznHajmMashinModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MasirVaznHajmMashinModel.TableName(), allColumns(), "ccMoshtary= " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    masirVaznHajmMashinModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MasirVaznHajmMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirVaznHajmMashinDAO" , "" , "getByccMoshtary" , "");
        }
        return masirVaznHajmMashinModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MasirVaznHajmMashinModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MasirVaznHajmMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirVaznHajmMashinDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MasirVaznHajmMashinModel.TableName(), MasirVaznHajmMashinModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MasirVaznHajmMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirVaznHajmMashinDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    public boolean updateMoshtaryJadid(int newccMoshtary , int oldccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MasirVaznHajmMashinModel.COLUMN_ccMoshtary(), newccMoshtary);
            db.update(MasirVaznHajmMashinModel.TableName(), values, "ccMoshtary= ?", new String[]{String.valueOf(oldccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MasirVaznHajmMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirVaznHajmMashinDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MasirVaznHajmMashinModel masirVaznHajmMashinModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MasirVaznHajmMashinModel.COLUMN_ccMasir() , masirVaznHajmMashinModel.getCcMasir());
        contentValues.put(MasirVaznHajmMashinModel.COLUMN_ccMoshtary() , masirVaznHajmMashinModel.getCcMoshtary());
        contentValues.put(MasirVaznHajmMashinModel.COLUMN_VaznMashin() , masirVaznHajmMashinModel.getVaznMashin());
        contentValues.put(MasirVaznHajmMashinModel.COLUMN_HajmMashin() , masirVaznHajmMashinModel.getHajmMashin());

        return contentValues;
    }


    private ArrayList<MasirVaznHajmMashinModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MasirVaznHajmMashinModel> masirVaznHajmMashinModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MasirVaznHajmMashinModel masirVaznHajmMashinModel = new MasirVaznHajmMashinModel();

            masirVaznHajmMashinModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(MasirVaznHajmMashinModel.COLUMN_ccMasir())));
            masirVaznHajmMashinModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MasirVaznHajmMashinModel.COLUMN_ccMoshtary())));
            masirVaznHajmMashinModel.setVaznMashin(cursor.getInt(cursor.getColumnIndex(MasirVaznHajmMashinModel.COLUMN_VaznMashin())));
            masirVaznHajmMashinModel.setHajmMashin(cursor.getFloat(cursor.getColumnIndex(MasirVaznHajmMashinModel.COLUMN_HajmMashin())));

            masirVaznHajmMashinModels.add(masirVaznHajmMashinModel);
            cursor.moveToNext();
        }
        return masirVaznHajmMashinModels;
    }


}
