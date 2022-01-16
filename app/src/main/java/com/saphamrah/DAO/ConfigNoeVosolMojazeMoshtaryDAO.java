package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.ConfigNoeVosolMojazeMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerAdamDarkhastModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetConfigNoeVosolMojazeMoshtaryResult;
import com.saphamrah.protos.CustomerAllowedReceiptionTypeConfigGrpc;
import com.saphamrah.protos.CustomerAllowedReceiptionTypeConfigReply;
import com.saphamrah.protos.CustomerAllowedReceiptionTypeConfigReplyList;
import com.saphamrah.protos.CustomerAllowedReceiptionTypeConfigRequest;

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

public class ConfigNoeVosolMojazeMoshtaryDAO
{

    ConfigNoeVosolMojazeMoshtaryModel modelGetTABLE_NAME = new ConfigNoeVosolMojazeMoshtaryModel();
    private DBHelper dbHelper;




    /**
    create constructor
     */
    public ConfigNoeVosolMojazeMoshtaryDAO(Context context)
    {

        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ConfigNoeVosolMojazeMoshtaryDAO" , "" , "constructor" , "");
        }
    }

    /*
 get name all columns in model
  */
    private String[] allColumns()
    {
        return new String[]
                {
                        modelGetTABLE_NAME.getCOLUMNccConfigNoeVosolMojazeMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_ccDarajeh(),
                        modelGetTABLE_NAME.getCOLUMN_ccNoeMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_CodeVazeiat(),
                        modelGetTABLE_NAME.getCOLUMNCodeNoeVosol_Tablet(),
                        modelGetTABLE_NAME.getCOLUMN_txtNoeVosol(),
                        modelGetTABLE_NAME.getCOLUMN_IsPishDariaft(),
                        modelGetTABLE_NAME.getCOLUMN_modatVosolMazad(),
                        modelGetTABLE_NAME.getCOLUMN_MashmoolTakhfifNaghdi(),
                        modelGetTABLE_NAME.getCOLUMN_MashmoolDirkardVosol(),


                };
    }

    public void fetchConfigNoeVosolMojazeMoshtaryGrpc(final Context context, final String activityNameForLog, String ccNoeMoshtarys , final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchRotbehGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerAllowedReceiptionTypeConfigGrpc.CustomerAllowedReceiptionTypeConfigBlockingStub blockingStub = CustomerAllowedReceiptionTypeConfigGrpc.newBlockingStub(managedChannel);
                CustomerAllowedReceiptionTypeConfigRequest request = CustomerAllowedReceiptionTypeConfigRequest.newBuilder().setCustomersID(ccNoeMoshtarys).build();

                Callable<CustomerAllowedReceiptionTypeConfigReplyList> replyListCallable  = () -> blockingStub.getCustomerAllowedReceiptionTypeConfig(request);
                RxAsync.makeObservable(replyListCallable)
                        .map(replyList -> {
                            ArrayList<ConfigNoeVosolMojazeMoshtaryModel> models = new ArrayList<>();
                            for (CustomerAllowedReceiptionTypeConfigReply reply : replyList.getCustomerAllowedReceiptionTypeConfigsList()) {
                                ConfigNoeVosolMojazeMoshtaryModel model = new ConfigNoeVosolMojazeMoshtaryModel();

                                model.setCcConfigNoeVosolMojazeMoshtary(reply.getCustomerAllowedReceiptionTypeConfigID());
                                model.setCodeNoeVosol_Tablet(reply.getReceiptionTypeCodeTablet());
                                model.setCcDarajeh(reply.getDegreeID());
                                model.setCcNoeMoshtary(reply.getCustomerTypeID());
                                model.setCodeVazeiat(reply.getSituationCode());
                                model.setTxtNoeVosol(reply.getReceiptionTypeTxt());
                                model.setIsPishDariaft(reply.getIsUpFrontReciept());
                                model.setModatVosolMazad(reply.getExtraReceiptionDuration());
                                model.setMashmoolTakhfifNaghdi(reply.getCashDiscountSubject());
                                model.setMashmoolDirkardVosol(reply.getReciptionDelaySubject());


                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ConfigNoeVosolMojazeMoshtaryModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ConfigNoeVosolMojazeMoshtaryModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeMoshtaryGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }
    }

    /**
    fetch = request server and get result
     */
    public void fetchConfigNoeVosolMojazeMoshtary(final Context context, final String activityNameForLog, String ccNoeMoshtarys , final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeMoshtaryDAO", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetConfigNoeVosolMojazeMoshtaryResult> call = apiServiceGet.getConfigNoeVosolMojazeMoshtary(ccNoeMoshtarys);
            call.enqueue(new Callback<GetConfigNoeVosolMojazeMoshtaryResult>()
            {
                @Override
                public void onResponse(Call<GetConfigNoeVosolMojazeMoshtaryResult> call, Response<GetConfigNoeVosolMojazeMoshtaryResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), "", "ConfigNoeVosolMojazeMoshtaryDAO", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetConfigNoeVosolMojazeMoshtaryResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeMoshtaryDAO", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeMoshtaryDAO", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeMoshtaryDAO", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeMoshtaryDAO", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetConfigNoeVosolMojazeMoshtaryResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ConfigNoeVosolMojazeMoshtaryDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeMoshtaryDAO", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    /*
    set result model to DB
     */
    @SuppressLint("LongLogTag")
    public boolean insertGroup(ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ConfigNoeVosolMojazeMoshtaryModel configNoeVosolMojazeMoshtaryModel : configNoeVosolMojazeMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(configNoeVosolMojazeMoshtaryModel);
                db.insertOrThrow(modelGetTABLE_NAME.getTABLE_NAME() , null , contentValues);

                Log.i("ConfigNoeVosolMojazeMoshtaryDAO" , contentValues.toString());
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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorGroupInsert , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ConfigNoeVosolMojazeMoshtaryDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    // get all data as db
    public ArrayList<ConfigNoeVosolMojazeMoshtaryModel> getAll()
    {
        ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    configNoeVosolMojazeMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ConfigNoeVosolMojazeMoshtaryDAO" , "" , "getAll" , "");
        }
        return configNoeVosolMojazeMoshtaryModels;
    }


    public ArrayList<ConfigNoeVosolMojazeMoshtaryModel> getCodeVosolCheckBargashti(int ccnoeMoshtary , int ccDarajeh){
        ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels = new ArrayList<>();
        try
        {


            String query = "select * from ConfigNoeVosolMojazeMoshtary\n" +
                    "where IsPishDariaft = 0 AND ccNoeMoshtary = " + ccnoeMoshtary + " AND ccDarajeh = "+ ccDarajeh +"";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    configNoeVosolMojazeMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , "AdamDarkhast,Moshtary,ElatAdamDarkhast") + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ConfigNoeVosolMojazeMoshtaryDAO" , "" , "getAllForSendToServer" , "");
        }
        return configNoeVosolMojazeMoshtaryModels;
    }


    public ArrayList<ConfigNoeVosolMojazeMoshtaryModel> getCodeVosolPishDaryaft(int ccnoeMoshtary , int ccDarajeh){
        ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels = new ArrayList<>();
        try
        {


            String query = "select * from ConfigNoeVosolMojazeMoshtary\n" +
                    "where IsPishDariaft = 2 AND ccNoeMoshtary = " + ccnoeMoshtary + " AND ccDarajeh = "+ ccDarajeh +"";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    configNoeVosolMojazeMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , "AdamDarkhast,Moshtary,ElatAdamDarkhast") + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ConfigNoeVosolMojazeMoshtaryDAO" , "" , "getAllForSendToServer" , "");
        }
        return configNoeVosolMojazeMoshtaryModels;
    }

    public int getTedadRoozMazadForRotbeh(int darajeh , int codeNoeVosolAzMoshtary , int ccGorohMoshtary){
        int modatVosolMazad = 0;
        try
        {


            String query = "select ModatVosolMazad from ConfigNoeVosolMojazeMoshtary\n" +
                    "where ccDarajeh = "+ darajeh +" AND CodeNoeVosol_Tablet = " + codeNoeVosolAzMoshtary + " AND ccNoeMoshtary = "+ ccGorohMoshtary +"";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    modatVosolMazad = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , "InvoiceSettlement") + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ConfigNoeVosolMojazeMoshtaryDAO" , "" , "getTedadRoozForRotbeh" , "");
        }
        return modatVosolMazad;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(modelGetTABLE_NAME.getTABLE_NAME(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "configNoeVosolMojazeMoshtaryDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ConfigNoeVosolMojazeMoshtaryModel configNoeVosolMojazeMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();
        ConfigNoeVosolMojazeMoshtaryModel configNoeVosolMojazeMoshtary = new ConfigNoeVosolMojazeMoshtaryModel();
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMNccConfigNoeVosolMojazeMoshtary() , configNoeVosolMojazeMoshtaryModel.getCcConfigNoeVosolMojazeMoshtary());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMN_txtNoeVosol() , configNoeVosolMojazeMoshtaryModel.getTxtNoeVosol());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMNCodeNoeVosol_Tablet() , configNoeVosolMojazeMoshtaryModel.getCodeNoeVosol_Tablet());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMN_ccDarajeh() , configNoeVosolMojazeMoshtaryModel.getCcDarajeh());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMN_ccNoeMoshtary() , configNoeVosolMojazeMoshtaryModel.getCcNoeMoshtary());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMN_CodeVazeiat() , configNoeVosolMojazeMoshtaryModel.getCodeVazeiat());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMN_IsPishDariaft() , configNoeVosolMojazeMoshtaryModel.getIsPishDariaft());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMN_MashmoolTakhfifNaghdi() , configNoeVosolMojazeMoshtaryModel.getMashmoolTakhfifNaghdi());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMN_MashmoolDirkardVosol() , configNoeVosolMojazeMoshtaryModel.getMashmoolDirkardVosol());
        contentValues.put(configNoeVosolMojazeMoshtary.getCOLUMN_modatVosolMazad(), configNoeVosolMojazeMoshtaryModel.getModatVosolMazad());
        return contentValues;
    }


    /*
    set  cursor to model in get All
     */
    private ArrayList<ConfigNoeVosolMojazeMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ConfigNoeVosolMojazeMoshtaryModel model = new ConfigNoeVosolMojazeMoshtaryModel();
           model.setCcConfigNoeVosolMojazeMoshtary(cursor.getInt(cursor.getColumnIndex(model.getCOLUMNccConfigNoeVosolMojazeMoshtary())));
           model.setTxtNoeVosol(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_txtNoeVosol())));
           model.setCodeNoeVosol_Tablet(cursor.getInt(cursor.getColumnIndex(model.getCOLUMNCodeNoeVosol_Tablet())));
           model.setCcDarajeh(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccDarajeh())));
           model.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccNoeMoshtary())));
           model.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_CodeVazeiat())));
           model.setIsPishDariaft(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_IsPishDariaft())));
           model.setMashmoolTakhfifNaghdi(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_MashmoolTakhfifNaghdi())));
           model.setMashmoolDirkardVosol(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_MashmoolDirkardVosol())));
           model.setModatVosolMazad(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_modatVosolMazad())));
           configNoeVosolMojazeMoshtaryModels.add(model);
            cursor.moveToNext();
        }
        return configNoeVosolMojazeMoshtaryModels;
    }





}
