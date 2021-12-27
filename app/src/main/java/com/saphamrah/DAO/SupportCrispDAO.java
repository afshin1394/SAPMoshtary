package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.SupportCrispModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.SupportCrispResult;
import com.saphamrah.protos.SupportCrispGrpc;
import com.saphamrah.protos.SupportCrispReply;
import com.saphamrah.protos.SupportCrispReplyList;
import com.saphamrah.protos.SupportCrispRequest;

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

public class SupportCrispDAO
{

    DBHelper dbHelper;
    private Context context;

    static SupportCrispModel crispModel = new SupportCrispModel();

    public SupportCrispDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "SupportCrispDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
                {
                        crispModel.getOLUMN_ccSupportCrisp,
                        crispModel.getCOLUMN_ccSazmanForosh,
                        crispModel.getCOLUMN_CrispID
                };
    }

    public void fetchSupportCrispGrpc(final Context context, final String activityNameForLog, int ccsazmanforosh, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, SupportCrispDAO.class.getSimpleName(), activityNameForLog, "fetchSupportCrispGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                SupportCrispGrpc.SupportCrispBlockingStub supportCrispBlockingStub = SupportCrispGrpc.newBlockingStub(managedChannel);
                SupportCrispRequest supportCrispRequest = SupportCrispRequest.newBuilder().setSellOrganizationID(ccsazmanforosh).build();
                Callable<SupportCrispReplyList> supportCrispReplyListCallable = () -> supportCrispBlockingStub.getSupportCrisp(supportCrispRequest);
                RxAsync.makeObservable(supportCrispReplyListCallable)
                        .map(supportCrispReplyList ->  {
                            ArrayList<SupportCrispModel> models = new ArrayList<>();
                            for (SupportCrispReply reply : supportCrispReplyList.getSupportCrispsList()) {

                                SupportCrispModel model = new SupportCrispModel();
                                model.setCcSupportCrisp(reply.getSupportCrispID());
                                model.setCrispID(reply.getCrispID());
                                model.setCcSazmanForosh(reply.getSellOrganizationID());



                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<SupportCrispModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<SupportCrispModel> supportCrispModels) {
                                retrofitResponse.onSuccess(supportCrispModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), SupportCrispDAO.class.getSimpleName(), activityNameForLog, "fetchSupportCrispGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }

    public void fetchSupportCrisp(final Context context, final String activityNameForLog, int ccsazmanforosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, SupportCrispDAO.class.getSimpleName(), activityNameForLog, "fetchSupportCrisp", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<SupportCrispResult> call = apiServiceGet.getSupportCrisp(ccsazmanforosh);
            call.enqueue(new Callback<SupportCrispResult>()
            {
                @Override
                public void onResponse(Call<SupportCrispResult> call, Response<SupportCrispResult> response)
                {
                    Log.i("fetchSupportCrisp" , "onResponse");
                    try
                    {
                        if (response.isSuccessful())
                        {
                            SupportCrispResult result = response.body();
                            Log.i("fetchSupportCrisp" , response.body().toString());
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    Log.i("fetchSupportCrisp" , result.getData().toString());
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), SupportCrispDAO.class.getSimpleName(), activityNameForLog, "fetchSupportCrisp", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, SupportCrispDAO.class.getSimpleName(), activityNameForLog, "fetchSupportCrisp", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), SupportCrispDAO.class.getSimpleName(), activityNameForLog, "fetchSupportCrisp", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<SupportCrispResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), SupportCrispDAO.class.getSimpleName(), activityNameForLog, "fetchSupportCrisp", "onFailure");
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





    public boolean insertGroup(ArrayList<SupportCrispModel> supportCrispModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (SupportCrispModel supportCrispModel : supportCrispModels)
            {
                ContentValues contentValues = modelToContentvalue(supportCrispModel);
                db.insertOrThrow(crispModel.TABLE_NAME , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , crispModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SupportCrispDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(crispModel.TABLE_NAME, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , crispModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SupportCrispDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    public SupportCrispModel getBySazmanForosh(int ccSazmanForosh)
    {
        SupportCrispModel supportCrispModel = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //Cursor cursor = db.rawQuery(query , null);
            Cursor cursor = db.query(crispModel.TABLE_NAME, allColumns(), crispModel.getCOLUMN_ccSazmanForosh + " = " + ccSazmanForosh, null,null,null,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    supportCrispModel = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , crispModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "SupportCrispDAO" , "" , "getBySazmanForosh" , "");
        }
        return supportCrispModel;
    }


    public SupportCrispModel cursorToModel(Cursor cursor)
    {
        SupportCrispModel supportCrispModel = new SupportCrispModel();

        cursor.moveToFirst();
        supportCrispModel.setCcSupportCrisp(cursor.getInt(cursor.getColumnIndex(crispModel.getCOLUMN_ccSazmanForosh)));
        supportCrispModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(crispModel.getOLUMN_ccSupportCrisp)));
        supportCrispModel.setCrispID(cursor.getString(cursor.getColumnIndex(crispModel.getCOLUMN_CrispID)));

        return supportCrispModel;
    }

    private static ContentValues modelToContentvalue(SupportCrispModel supportCrispModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(crispModel.getOLUMN_ccSupportCrisp , supportCrispModel.getCcSupportCrisp());
        contentValues.put(crispModel.getCOLUMN_ccSazmanForosh , supportCrispModel.getCcSazmanForosh());
        contentValues.put(crispModel.getCOLUMN_CrispID , supportCrispModel.getCrispID());

        return contentValues;
    }
}
