package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TedadFaktorMoshtaryModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetTedadFaktorMoshtaryResult;
import com.saphamrah.protos.CustomerInvoiceCountGrpc;
import com.saphamrah.protos.CustomerInvoiceCountReply;
import com.saphamrah.protos.CustomerInvoiceCountReplyList;
import com.saphamrah.protos.CustomerInvoiceCountRequest;

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

public class TedadFaktorMoshtaryDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public TedadFaktorMoshtaryDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TedadFaktorMoshtaryDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            TedadFaktorMoshtaryModel.COLUMN_ccMoshtary(),
            TedadFaktorMoshtaryModel.COLUMN_TedadFaktorMoshtary()
        };
    }

    public void fetchTedadFaktorMoshtaryGrpc(final Context context, final String activityNameForLog, final String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TedadFaktorMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchTedadFaktorMoshtaryGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerInvoiceCountGrpc.CustomerInvoiceCountBlockingStub customerInvoiceCountBlockingStub = CustomerInvoiceCountGrpc.newBlockingStub(managedChannel);
                CustomerInvoiceCountRequest customerInvoiceCountRequest = CustomerInvoiceCountRequest.newBuilder().setCustomersID(ccMoshtarys).build();
                Callable<CustomerInvoiceCountReplyList> customerInvoiceCountReplyListCallable = () -> customerInvoiceCountBlockingStub.getCustomerInvoiceCount(customerInvoiceCountRequest);
                RxAsync.makeObservable(customerInvoiceCountReplyListCallable)
                        .map(customerInvoiceCountReplyList  ->  {
                            ArrayList<TedadFaktorMoshtaryModel> models = new ArrayList<>();
                            for (CustomerInvoiceCountReply reply : customerInvoiceCountReplyList.getCustomerInvoiceCountsList()) {

                                TedadFaktorMoshtaryModel model = new TedadFaktorMoshtaryModel();

                                model.setCcMoshtary(reply.getCustomerID());
                                model.setTedadFaktorMoshtary(reply.getCustomerInvoiceCount());





                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<TedadFaktorMoshtaryModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<TedadFaktorMoshtaryModel> tedadFaktorMoshtaryModels) {
                                retrofitResponse.onSuccess(tedadFaktorMoshtaryModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), TedadFaktorMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchTedadFaktorMoshtaryGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }

    public void fetchTedadFaktorMoshtary(final Context context, final String activityNameForLog, final String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TedadFaktorMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchTedadFaktorMoshtary", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetTedadFaktorMoshtaryResult> call = apiServiceGet.getTedadFaktorMoshtary(ccMoshtarys);
            call.enqueue(new Callback<GetTedadFaktorMoshtaryResult>() {
                @Override
                public void onResponse(Call<GetTedadFaktorMoshtaryResult> call, Response<GetTedadFaktorMoshtaryResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TedadFaktorMoshtaryDAO.class.getSimpleName(), "", "fetchTedadFaktorMoshtary", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetTedadFaktorMoshtaryResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TedadFaktorMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchTedadFaktorMoshtary", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TedadFaktorMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchTedadFaktorMoshtary", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TedadFaktorMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchTedadFaktorMoshtary", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TedadFaktorMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchTedadFaktorMoshtary", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetTedadFaktorMoshtaryResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), TedadFaktorMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchTedadFaktorMoshtary", "onFailure");
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

    public boolean insertGroup(ArrayList<TedadFaktorMoshtaryModel> tedadFaktorMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TedadFaktorMoshtaryModel tedadFaktorMoshtaryModel : tedadFaktorMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(tedadFaktorMoshtaryModel);
                db.insertOrThrow(TedadFaktorMoshtaryModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TedadFaktorMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TedadFaktorMoshtaryDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<TedadFaktorMoshtaryModel> getAll()
    {
        ArrayList<TedadFaktorMoshtaryModel> tedadFaktorMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TedadFaktorMoshtaryModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    tedadFaktorMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TedadFaktorMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TedadFaktorMoshtaryDAO" , "" , "getAll" , "");
        }
        return tedadFaktorMoshtaryModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TedadFaktorMoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , TedadFaktorMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TedadFaktorMoshtaryDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TedadFaktorMoshtaryModel tedadFaktorMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();

        if (tedadFaktorMoshtaryModel.getCcMoshtary() > 0)
        {
            contentValues.put(TedadFaktorMoshtaryModel.COLUMN_ccMoshtary() , tedadFaktorMoshtaryModel.getCcMoshtary());
        }
        contentValues.put(TedadFaktorMoshtaryModel.COLUMN_TedadFaktorMoshtary() , tedadFaktorMoshtaryModel.getTedadFaktorMoshtary());

        return contentValues;
    }


    private ArrayList<TedadFaktorMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TedadFaktorMoshtaryModel> tedadFaktorMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TedadFaktorMoshtaryModel tedadFaktorMoshtaryModel = new TedadFaktorMoshtaryModel();

            tedadFaktorMoshtaryModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(TedadFaktorMoshtaryModel.COLUMN_ccMoshtary())));
            tedadFaktorMoshtaryModel.setTedadFaktorMoshtary(cursor.getInt(cursor.getColumnIndex(TedadFaktorMoshtaryModel.COLUMN_TedadFaktorMoshtary())));

            tedadFaktorMoshtaryModels.add(tedadFaktorMoshtaryModel);
            cursor.moveToNext();
        }
        return tedadFaktorMoshtaryModels;
    }


}
