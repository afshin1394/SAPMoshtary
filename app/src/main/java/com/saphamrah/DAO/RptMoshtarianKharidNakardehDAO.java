package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import androidx.annotation.NonNull;

import com.saphamrah.Model.RptFaktorTozieNashodehModel;
import com.saphamrah.Model.RptMoshtaryKharidNakardeModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtarianKharidNakardeResult;
import com.saphamrah.protos.RptUnprocureCustomerGrpc;
import com.saphamrah.protos.RptUnprocureCustomerReply;
import com.saphamrah.protos.RptUnprocureCustomerReplyList;
import com.saphamrah.protos.RptUnprocureCustomerRequest;

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

public class RptMoshtarianKharidNakardehDAO {
    private DBHelper dbHelper;
    private Context context;



    public RptMoshtarianKharidNakardehDAO (Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptMoshtarianKharidNakardehDAO " , "" , "constructor" , "");
        }
    }
    private String[] allColumns()
    {
        return new String[]
                {
                        RptMoshtaryKharidNakardeModel.getCOLUMN_Radif(),
                        RptMoshtaryKharidNakardeModel.getCOLUMN_ccMoshtary(),
                        RptMoshtaryKharidNakardeModel.getCOLUMN_NameMoshtary(),
                        RptMoshtaryKharidNakardeModel.getCOLUMN_TarikhFaktor()
                };
    }


    public void fetchAllMoshtarianKharidNakardeGrpc(final Context context, final String activityNameForLog, final String ccForoshandeh,final String ccMasirs, final RetrofitResponse retrofitResponse)
    {
        try {


            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, PrintFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtarianKharidNakardeGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                RptUnprocureCustomerGrpc.RptUnprocureCustomerBlockingStub blockingStub = RptUnprocureCustomerGrpc.newBlockingStub(managedChannel);
                RptUnprocureCustomerRequest request = RptUnprocureCustomerRequest.newBuilder().setSalesManID(ccForoshandeh).setRoutesID(ccMasirs).build();
                Callable<RptUnprocureCustomerReplyList> rptUnprocureCustomerReplyListCallable  = () -> blockingStub.getRptUnprocureCustomer(request);
                RxAsync.makeObservable(rptUnprocureCustomerReplyListCallable)
                        .map(invoicePrintReplyList -> {
                            ArrayList<RptMoshtaryKharidNakardeModel> models = new ArrayList<>();
                            for (RptUnprocureCustomerReply reply : invoicePrintReplyList.getRptUnprocureCustomersList()) {
                                RptMoshtaryKharidNakardeModel model = new RptMoshtaryKharidNakardeModel();
                                model.setRadif(reply.getRow());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setTarikhFaktor(reply.getInvoiceDate());
                                model.setCodeMoshtary(reply.getCustomerCode());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.single())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptMoshtaryKharidNakardeModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptMoshtaryKharidNakardeModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), PrintFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtarianKharidNakardeGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }
    }

    public void fetchAllMoshtarianKharidNakarde(final Context context, final String activityNameForLog, final String ccForoshandeh,final String ccMasirs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtarianKharidNakarde", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtarianKharidNakardeResult> call = apiServiceGet.getAllMoshtarianeKharidNakarde(ccForoshandeh,ccMasirs);
            call.enqueue(new Callback<GetAllMoshtarianKharidNakardeResult>() {
                @Override
                public void onResponse(Call<GetAllMoshtarianKharidNakardeResult> call, Response<GetAllMoshtarianKharidNakardeResult> response)
                {
                    try
                    {
                        Log.i("sdadadad", "onResponse: ");
                        if (response.raw().body() != null)
                        {


                            Log.i("nameMoshtary", "onResponse: "+response.body().getData().get(0).getNameMoshtary());
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptMandehdarDAO.class.getSimpleName(), "", "fetchAllMandehdar", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMoshtarianKharidNakardeResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    if (result.getData()!=null) {
                                        if (result.getData().size() > 0)
                                            retrofitResponse.onSuccess(result.getData());
                                        else
                                            retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_EMPTY(), context.getResources().getString(R.string.resultIsNull));
                                    }else{
                                        retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_EMPTY(), context.getResources().getString(R.string.resultIsNull));

                                    }
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtarianKharidNakardeResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtarianKharidNakarde", "onFailure");
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

    public boolean insertGroup(ArrayList<RptMoshtaryKharidNakardeModel> rptMoshtaryKharidNakardeModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            Log.i("sizeRptMoshtary", "insertGroup: "+rptMoshtaryKharidNakardeModels.size());
            for (RptMoshtaryKharidNakardeModel rptMoshtaryKharidNakardeModel : rptMoshtaryKharidNakardeModels)
            {

                ContentValues contentValues = modelToContentvalue(rptMoshtaryKharidNakardeModel);
                Log.i("rptMoshtarian", "insertGroup: "+RptMoshtaryKharidNakardeModel.getTABLE_NAME());
                db.insertOrThrow(RptMoshtaryKharidNakardeModel.getTABLE_NAME() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptMandehdarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMandehdarDAO" , "" , "insertGroup" , "");
            return false;
        }
    }
    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptMoshtaryKharidNakardeModel.getTABLE_NAME(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptFaktorTozieNashodehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptFaktorTozieNashodehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }
    public ArrayList<RptMoshtaryKharidNakardeModel> getAllMoshtarianKharidakarde()
    {
        ArrayList<RptMoshtaryKharidNakardeModel> rptMoshtaryKharidNakardeModels = new ArrayList<>();
        try
        {
            Log.i("cursorCount", "getAllMoshtarianKharidakarde: ");

            Log.i("cursorCount", "getAllMoshtarianKharidakarde: ");


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Log.i("cursorCount", "getAllMoshtarianKharidakarde: ");
            Cursor cursor = db.query(RptMoshtaryKharidNakardeModel.getTABLE_NAME(), allColumns(), null, null, null, null, null);
            Log.i("cursorCount", "getAllMoshtarianKharidakarde: "+cursor.getCount());
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {

                    rptMoshtaryKharidNakardeModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptFaktorTozieNashodehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptFaktorTozieNashodehDAO" , "" , "getAllWithSum" , "");
        }
        return rptMoshtaryKharidNakardeModels;
    }
    private static ContentValues modelToContentvalue(RptMoshtaryKharidNakardeModel rptMoshtaryKharidNakardeModel)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(rptMoshtaryKharidNakardeModel.getCOLUMN_Radif() , rptMoshtaryKharidNakardeModel.getRadif());


        contentValues.put(rptMoshtaryKharidNakardeModel.getCOLUMN_ccMoshtary() , rptMoshtaryKharidNakardeModel.getCodeMoshtary());

        contentValues.put(rptMoshtaryKharidNakardeModel.getCOLUMN_NameMoshtary() , rptMoshtaryKharidNakardeModel.getNameMoshtary());
        contentValues.put(rptMoshtaryKharidNakardeModel.getCOLUMN_TarikhFaktor() , rptMoshtaryKharidNakardeModel.getTarikhFaktor());


        return contentValues;
    }
    private ArrayList<RptMoshtaryKharidNakardeModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptMoshtaryKharidNakardeModel> rptMoshtaryKharidNakardeModels = new ArrayList<>();
        Log.i("cursorsdds", "cursorToModel: "+cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptMoshtaryKharidNakardeModel rptMoshtaryKharidNakardeModel = new RptMoshtaryKharidNakardeModel();

            rptMoshtaryKharidNakardeModel.setRadif(cursor.getInt(cursor.getColumnIndex(rptMoshtaryKharidNakardeModel.getCOLUMN_Radif())));
            rptMoshtaryKharidNakardeModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(rptMoshtaryKharidNakardeModel.getCOLUMN_ccMoshtary())));
            rptMoshtaryKharidNakardeModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(rptMoshtaryKharidNakardeModel.getCOLUMN_NameMoshtary())));
            rptMoshtaryKharidNakardeModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(rptMoshtaryKharidNakardeModel.getCOLUMN_TarikhFaktor())));
            rptMoshtaryKharidNakardeModels.add(rptMoshtaryKharidNakardeModel);
            cursor.moveToNext();
        }
        return rptMoshtaryKharidNakardeModels;
    }

}

