package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.MoshtaryBrandModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryBrandResult;
import com.saphamrah.protos.CustomerBrandGrpc;
import com.saphamrah.protos.CustomerBrandReply;
import com.saphamrah.protos.CustomerBrandReplyList;
import com.saphamrah.protos.CustomerBrandRequest;

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

public class MoshtaryBrandDAO
{
    private DBHelper dbHelper;
    private Context context;
    private final String CLASS_NAME = "MoshtaryBrandDAO";


    public MoshtaryBrandDAO(Context context)
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
            MoshtaryBrandModel.COLUMN_ccMoshtaryBrand(),
            MoshtaryBrandModel.COLUMN_ccMoshtary(),
            MoshtaryBrandModel.COLUMN_ccBrand()
        };
    }

    public void fetchMoshtaryBrandGrpc(final Context context, final String activityNameForLog, String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryBrandDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryBrandGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerBrandGrpc.CustomerBrandBlockingStub customerBrandBlockingStub = CustomerBrandGrpc.newBlockingStub(managedChannel);
                CustomerBrandRequest customerBrandRequest = CustomerBrandRequest.newBuilder().setCustomersID(ccMoshtarys).build();
                Callable<CustomerBrandReplyList> customerBrandReplyListCallable = () -> customerBrandBlockingStub.getCustomerBrand(customerBrandRequest);
                RxAsync.makeObservable(customerBrandReplyListCallable)
                        .map(customerBrandReplyList ->  {
                            ArrayList<MoshtaryBrandModel> models = new ArrayList<>();
                            for (CustomerBrandReply reply : customerBrandReplyList.getCustomerBrandsList()) {
                                MoshtaryBrandModel model = new MoshtaryBrandModel();
                                model.setCcBrand(reply.getBrandID());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setCcMoshtaryBrand(reply.getCustomerBrandID());

                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MoshtaryBrandModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MoshtaryBrandModel> moshtaryBrandModels) {
                                retrofitResponse.onSuccess(moshtaryBrandModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MoshtaryBrandDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryBrandGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }

    public void fetchMoshtaryBrand(final Context context, final String activityNameForLog, String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMoshtaryBrand", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtaryBrandResult> call = apiServiceGet.getAllMoshtaryBrand(ccMoshtarys);
            call.enqueue(new Callback<GetAllMoshtaryBrandResult>() {
                @Override
                public void onResponse(Call<GetAllMoshtaryBrandResult> call, Response<GetAllMoshtaryBrandResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchMoshtaryBrand", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMoshtaryBrandResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchMoshtaryBrand", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchMoshtaryBrand", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMoshtaryBrand", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchMoshtaryBrand", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtaryBrandResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchMoshtaryBrand", "onFailure");
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

    public boolean insertGroup(ArrayList<MoshtaryBrandModel> moshtaryBrandModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryBrandModel moshtaryBrandModel : moshtaryBrandModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryBrandModel);
                db.insertOrThrow(MoshtaryBrandModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryBrandModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<MoshtaryBrandModel> getAll()
    {
        ArrayList<MoshtaryBrandModel> moshtaryBrandModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryBrandModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryBrandModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryBrandModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return moshtaryBrandModels;
    }


    public int getCountByccMoshtary(int ccMoshtary)
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select count(" + MoshtaryBrandModel.COLUMN_ccMoshtaryBrand() + ") from " +
                    MoshtaryBrandModel.TableName() + " where " + MoshtaryBrandModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
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
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryBrandModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return count;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryBrandModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryBrandModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryBrandModel moshtaryBrandModel)
    {
        ContentValues contentValues = new ContentValues();

        if (moshtaryBrandModel.getCcMoshtaryBrand() > 0)
        {
            contentValues.put(MoshtaryBrandModel.COLUMN_ccMoshtaryBrand() , moshtaryBrandModel.getCcMoshtaryBrand());
        }
        contentValues.put(MoshtaryBrandModel.COLUMN_ccMoshtary() , moshtaryBrandModel.getCcMoshtary());
        contentValues.put(MoshtaryBrandModel.COLUMN_ccBrand() , moshtaryBrandModel.getCcBrand());

        return contentValues;
    }


    private ArrayList<MoshtaryBrandModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryBrandModel> moshtaryBrandModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryBrandModel moshtaryBrandModel = new MoshtaryBrandModel();

            moshtaryBrandModel.setCcMoshtaryBrand(cursor.getInt(cursor.getColumnIndex(MoshtaryBrandModel.COLUMN_ccMoshtaryBrand())));
            moshtaryBrandModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryBrandModel.COLUMN_ccMoshtary())));
            moshtaryBrandModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(MoshtaryBrandModel.COLUMN_ccBrand())));

            moshtaryBrandModels.add(moshtaryBrandModel);
            cursor.moveToNext();
        }
        return moshtaryBrandModels;
    }

}
