package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.MarkazShahrMarkaziModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllMarkazShahrMarkaziResult;
import com.saphamrah.protos.CentralDownTownGrpc;
import com.saphamrah.protos.CentralDownTownReply;
import com.saphamrah.protos.CentralDownTownReplyList;
import com.saphamrah.protos.CentralDownTownRequest;

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

public class MarkazShahrMarkaziDAO
{

    private final static String CLASS_NAME = "MarkazShahrMarkaziDAO";
    private DBHelper dbHelper;
    private Context context;


    public MarkazShahrMarkaziDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MarkazShahrMarkaziModel.COLUMN_ccMarkaz_ShahrMarkazi(),
            MarkazShahrMarkaziModel.COLUMN_ccMarkaz(),
            MarkazShahrMarkaziModel.COLUMN_ccMahaleh()
        };
    }

    public void fetchMarkazShahrMarkaziGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MarkazShahrMarkaziDAO.class.getSimpleName(), activityNameForLog, "fetchMarkazShahrMarkaziGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CentralDownTownGrpc.CentralDownTownBlockingStub centralDownTownBlockingStub = CentralDownTownGrpc.newBlockingStub(managedChannel);
                CentralDownTownRequest customerBrandRequest = CentralDownTownRequest.newBuilder().build();
                Callable<CentralDownTownReplyList> centralDownTownReplyListCallable = () -> centralDownTownBlockingStub.getCentralDownTown(customerBrandRequest);
                RxAsync.makeObservable(centralDownTownReplyListCallable)
                        .map(centralDownTownReplyList ->  {
                            ArrayList<MarkazShahrMarkaziModel> models = new ArrayList<>();
                            for (CentralDownTownReply reply : centralDownTownReplyList.getCentralDownTownRepliesList()) {
                                MarkazShahrMarkaziModel model = new MarkazShahrMarkaziModel();
                                model.setCcMarkazShahrMarkazi(reply.getCityCenterCenterID());
                                model.setCcMarkaz(reply.getCenterID());
                                model.setCcMahaleh(reply.getDistrictID());

                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MarkazShahrMarkaziModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels) {
                                retrofitResponse.onSuccess(markazShahrMarkaziModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MarkazShahrMarkaziDAO.class.getSimpleName(), activityNameForLog, "fetchMarkazShahrMarkaziGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    public void fetchMarkazShahrMarkazi(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMarkazShahrMarkazi", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMarkazShahrMarkaziResult> call = apiServiceGet.getAllMarkazShahrMarkazi();
            call.enqueue(new Callback<GetAllMarkazShahrMarkaziResult>() {
                @Override
                public void onResponse(Call<GetAllMarkazShahrMarkaziResult> call, Response<GetAllMarkazShahrMarkaziResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchMarkazShahrMarkazi", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMarkazShahrMarkaziResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchMarkazShahrMarkazi", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchMarkazShahrMarkazi", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMarkazShahrMarkazi", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchMarkazShahrMarkazi", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMarkazShahrMarkaziResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchMarkazShahrMarkazi", "onFailure");
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

    public boolean insertGroup(ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MarkazShahrMarkaziModel markazShahrMarkaziModel : markazShahrMarkaziModels)
            {
                ContentValues contentValues = modelToContentvalue(markazShahrMarkaziModel);
                db.insertOrThrow(MarkazShahrMarkaziModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MarkazShahrMarkaziModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<MarkazShahrMarkaziModel> getAll()
    {
        ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MarkazShahrMarkaziModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    markazShahrMarkaziModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MarkazShahrMarkaziModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return markazShahrMarkaziModels;
    }

    public int getCountByccMahale(int ccMahaleh)
    {
        int count = 0;
        try
        {
            String query = "select count(ccMahaleh) from Markaz_ShahrMarkazi where ccMahaleh = " + ccMahaleh;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
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
            String message = context.getResources().getString(R.string.errorSelectAll , MarkazShahrMarkaziModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getCountByccMahale" , "");
        }
        return count;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MarkazShahrMarkaziModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MarkazShahrMarkaziModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MarkazShahrMarkaziModel markazShahrMarkaziModel)
    {
        ContentValues contentValues = new ContentValues();

        if (markazShahrMarkaziModel.getCcMarkazShahrMarkazi() > 0)
        {
            contentValues.put(MarkazShahrMarkaziModel.COLUMN_ccMarkaz_ShahrMarkazi() , markazShahrMarkaziModel.getCcMarkazShahrMarkazi());
        }
        contentValues.put(MarkazShahrMarkaziModel.COLUMN_ccMarkaz() , markazShahrMarkaziModel.getCcMarkaz());
        contentValues.put(MarkazShahrMarkaziModel.COLUMN_ccMahaleh() , markazShahrMarkaziModel.getCcMahaleh());

        return contentValues;
    }


    private ArrayList<MarkazShahrMarkaziModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MarkazShahrMarkaziModel> markazShahrMarkaziModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MarkazShahrMarkaziModel markazShahrMarkaziModel = new MarkazShahrMarkaziModel();

            markazShahrMarkaziModel.setCcMarkazShahrMarkazi(cursor.getInt(cursor.getColumnIndex(MarkazShahrMarkaziModel.COLUMN_ccMarkaz_ShahrMarkazi())));
            markazShahrMarkaziModel.setCcMarkaz(cursor.getInt(cursor.getColumnIndex(MarkazShahrMarkaziModel.COLUMN_ccMarkaz())));
            markazShahrMarkaziModel.setCcMahaleh(cursor.getInt(cursor.getColumnIndex(MarkazShahrMarkaziModel.COLUMN_ccMahaleh())));

            markazShahrMarkaziModels.add(markazShahrMarkaziModel);
            cursor.moveToNext();
        }
        return markazShahrMarkaziModels;
    }

}
