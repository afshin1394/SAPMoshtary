package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.ConfigNoeVosolMojazeFaktorModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetConfigNoeVosolMojazefaktorResult;
import com.saphamrah.protos.InvoiceAllowCollectionTypeConfigGrpc;
import com.saphamrah.protos.InvoiceAllowCollectionTypeConfigReply;
import com.saphamrah.protos.InvoiceAllowCollectionTypeConfigReplyList;
import com.saphamrah.protos.InvoiceAllowCollectionTypeConfigRequest;

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

public class ConfigNoeVosolMojazeFaktorDAO
{

    ConfigNoeVosolMojazeFaktorModel modelGetTABLE_NAME = new ConfigNoeVosolMojazeFaktorModel();
    private DBHelper dbHelper;




    /**
    create constructor
     */
    public ConfigNoeVosolMojazeFaktorDAO(Context context)
    {

        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ConfigNoeVosolMojazeFaktorDAO" , "" , "constructor" , "");
        }
    }

    /*
get name all columns in model
 */
    private String[] allColumns()
    {
        return new String[]
                {
                        modelGetTABLE_NAME.getCOLUMN_ccConfig_NoeVosolMojaze_Faktor(),
                        modelGetTABLE_NAME.getCOLUMN_txtNoeVosol(),
                        modelGetTABLE_NAME.getCOLUMN_CodeNoeVosol_Tablet(),
                        modelGetTABLE_NAME.getCOLUMN_CodeNoeVosolAzMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_CodeVazeiat(),
                        modelGetTABLE_NAME.getCOLUMN_MashmoolTakhfifNaghdi(),
                        modelGetTABLE_NAME.getCOLUMN_MashmoolDirkardVosol(),
                        modelGetTABLE_NAME.getCOLUMN_MaxModatTajil(),
                        modelGetTABLE_NAME.getCOLUMN_MinMablaghForIsShow()
                };
    }

    public void fetchConfigNoeVosolMojazeFaktorGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                InvoiceAllowCollectionTypeConfigGrpc.InvoiceAllowCollectionTypeConfigBlockingStub blockingStub = InvoiceAllowCollectionTypeConfigGrpc.newBlockingStub(managedChannel);
                InvoiceAllowCollectionTypeConfigRequest request = InvoiceAllowCollectionTypeConfigRequest.newBuilder().build();

                Callable<InvoiceAllowCollectionTypeConfigReplyList> replyListCallable  = () -> blockingStub.getInvoiceAllowCollectionTypeConfig(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<ConfigNoeVosolMojazeFaktorModel> models = new ArrayList<>();
                            for (InvoiceAllowCollectionTypeConfigReply reply : replyList.getInvoiceAllowCollectionTypeConfigsList()) {
                                ConfigNoeVosolMojazeFaktorModel model = new ConfigNoeVosolMojazeFaktorModel();

                                model.setCcConfigNoeVosolMojazeFaktor(reply.getInvoiceAllowCollectionTypeConfigID());
                                model.setCodeNoeVosol_Tablet(reply.getCollectionTypeCodeTablet());
                                model.setCodeNoeVosolAzMoshtary(reply.getCollectionTypeCodeFromCustomer());
                                model.setCodeVazeiat(reply.getSituationCode());
                                model.setTxtNoeVosol(reply.getCollectionTypeTxt());
                                model.setMashmoolTakhfifNaghdi(reply.getCashDiscountSubject());
                                model.setMashmoolDirkardVosol(reply.getCollectionDelaySubject());
                                model.setMaxModatTajil(reply.getAcceleratedDurationMax());
                                model.setMinMablaghForIsShow(reply.getPriceMinForIsShow());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ConfigNoeVosolMojazeFaktorModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ConfigNoeVosolMojazeFaktorModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }




    }

    /**
    fetch = request server and get result
     */
    public void fetchConfigNoeVosolMojazeFaktor(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeFaktorDAO", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetConfigNoeVosolMojazefaktorResult> call = apiServiceGet.getConfigNoeVosolMojazefaktor();
            call.enqueue(new Callback<GetConfigNoeVosolMojazefaktorResult>()
            {
                @Override
                public void onResponse(Call<GetConfigNoeVosolMojazefaktorResult> call, Response<GetConfigNoeVosolMojazefaktorResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), "", "ConfigNoeVosolMojazeFaktorDAO", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetConfigNoeVosolMojazefaktorResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeFaktorDAO", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeFaktorDAO", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeFaktorDAO", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeFaktorDAO", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetConfigNoeVosolMojazefaktorResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ConfigNoeVosolMojazeFaktorDAO.class.getSimpleName(), activityNameForLog, "ConfigNoeVosolMojazeFaktorDAO", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    /*
    set result model to DB
     */
    @SuppressLint("LongLogTag")
    public boolean insertGroup(ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ConfigNoeVosolMojazeFaktorModel configNoeVosolMojazeFaktorModel : configNoeVosolMojazeFaktorModels)
            {
                ContentValues contentValues = modelToContentvalue(configNoeVosolMojazeFaktorModel);
                db.insertOrThrow(modelGetTABLE_NAME.getTABLE_NAME() , null , contentValues);

                Log.i("ConfigNoeVosolMojazeFaktorDAO" , contentValues.toString());
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ConfigNoeVosolMojazeFaktorDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    // get all data as db
    public ArrayList<ConfigNoeVosolMojazeFaktorModel> getAll()
    {
        ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    configNoeVosolMojazeFaktorModels = cursorToModel(cursor);
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ConfigNoeVosolMojazeFaktorDAO" , "" , "getAll" , "");
        }
        return configNoeVosolMojazeFaktorModels;
    }

    public ArrayList<ConfigNoeVosolMojazeFaktorModel> getByNoeVosol(int NoeVosol , double mablaghKhalesFaktor)
    {
        ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels = new ArrayList<>();
        try
        {
            String query = "select * from ConfigNoeVosolMojazeFaktor where CodeNoeVosolAzMoshtary = " + NoeVosol + " AND (MinMablaghForIsShow = -1 or MinMablaghForIsShow <= " + mablaghKhalesFaktor + ") order by CodeNoeVosol_Tablet";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    configNoeVosolMojazeFaktorModels = cursorToModel(cursor);
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ConfigNoeVosolMojazeFaktorDAO" , "" , "getAll" , "");
        }
        return configNoeVosolMojazeFaktorModels;
    }
    public String getValueByNoeVosol(int NoeVosol)
    {
        String value = "-1";
        try
        {
            String query = "select " + modelGetTABLE_NAME.getCOLUMN_CodeNoeVosol_Tablet() + " from " + modelGetTABLE_NAME.getTABLE_NAME() + " where " + modelGetTABLE_NAME.getCOLUMN_CodeNoeVosolAzMoshtary() + " = " + NoeVosol + " order by " + modelGetTABLE_NAME.getCOLUMN_CodeNoeVosol_Tablet();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast())
                    {
                        value +=  "," + cursor.getString(0);
                        cursor.moveToNext();
                    }

                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), exception.toString(), "ConfigNoeVosolMojazeFaktorDAO" , "" , "getValueByNoeVosol" , "");
        }
        value = value == null ? "" : value;
        return value;
    }

    public int isTajilFromCodeSabtShode(int NoeVosolMoshtary, int NoeVosolSabtShodeh)
    {
        int value = 0;
        try
        {
            String query = "select * from " + modelGetTABLE_NAME.getTABLE_NAME() + " where " + modelGetTABLE_NAME.getCOLUMN_CodeNoeVosol_Tablet() + " = " + NoeVosolMoshtary ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast())
                    {
                        value =  cursor.getInt(cursor.getColumnIndex(modelGetTABLE_NAME.getCOLUMN_MashmoolTakhfifNaghdi()));
                        cursor.moveToNext();
                    }

                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), exception.toString(), "ConfigNoeVosolMojazeFaktorDAO" , "" , "getValueByNoeVosol" , "");
        }

        return value;
    }

    public int isDirkardFromCodeSabtShode(int NoeVosol)
    {
        int value = 0;
        try
        {
            String query = "select * from " + modelGetTABLE_NAME.getTABLE_NAME() + " where " + modelGetTABLE_NAME.getCOLUMN_CodeNoeVosol_Tablet() + " = " + NoeVosol ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast())
                    {
                        value =  cursor.getInt(cursor.getColumnIndex(modelGetTABLE_NAME.getCOLUMN_MashmoolDirkardVosol()));
                        cursor.moveToNext();
                    }

                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), exception.toString(), "ConfigNoeVosolMojazeFaktorDAO" , "" , "getValueByNoeVosol" , "");
        }

        return value;
    }

    public int getMaxModatTajil(int codeNoeVosol , int CodeNoeVosolAzMoshtary)
    {
        String query = "select MaxModatTajil from ConfigNoeVosolMojazeFaktor where CodeNoeVosol_Tablet =  " + codeNoeVosol +" and CodeNoeVosolAzMoshtary = " + CodeNoeVosolAzMoshtary ;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    int maxTajil = cursor.getInt(cursor.getColumnIndex(modelGetTABLE_NAME.getCOLUMN_MaxModatTajil()));
                    cursor.close();
                    db.close();
                    return maxTajil;
                }
                else
                {
                    db.close();
                    cursor.close();
                    return 0;
                }
            }
            else
            {
                db.close();
                return 0;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(BaseApplication.getContext(),Constants.LOG_EXCEPTION(), exception.toString(), "ConfigNoeVosolMojazFaktor" , "" , "getMaxTedadTajil" , "");
            return 0;
        }
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "configNoeVosolMojazeFaktorDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ConfigNoeVosolMojazeFaktorModel model)
    {
        ContentValues contentValues = new ContentValues();
        ConfigNoeVosolMojazeFaktorModel faktor = new ConfigNoeVosolMojazeFaktorModel();
        contentValues.put(faktor.getCOLUMN_ccConfig_NoeVosolMojaze_Faktor() , model.getCcConfigNoeVosolMojazeFaktor());
        contentValues.put(faktor.getCOLUMN_txtNoeVosol(), model.getTxtNoeVosol());
        contentValues.put(faktor.getCOLUMN_CodeNoeVosol_Tablet(),model.getCodeNoeVosol_Tablet());
        contentValues.put(faktor.getCOLUMN_CodeNoeVosolAzMoshtary() , model.getCodeNoeVosolAzMoshtary());
        contentValues.put(faktor.getCOLUMN_CodeVazeiat() , model.getCodeVazeiat());
//        contentValues.put(faktor.getCOLUMN_IsPishDariaft() , model.getIsPishDariaft());
        contentValues.put(faktor.getCOLUMN_MashmoolTakhfifNaghdi() , model.getMashmoolTakhfifNaghdi());
        contentValues.put(faktor.getCOLUMN_MashmoolDirkardVosol() , model.getMashmoolDirkardVosol());
        contentValues.put(faktor.getCOLUMN_MaxModatTajil() , model.getMaxModatTajil());
        contentValues.put(faktor.getCOLUMN_MinMablaghForIsShow() , model.getMinMablaghForIsShow());

        return contentValues;
    }


    /*
    set  cursor to model in get All
     */
    private ArrayList<ConfigNoeVosolMojazeFaktorModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ConfigNoeVosolMojazeFaktorModel configNoeVosolMojazeFaktorModel = new ConfigNoeVosolMojazeFaktorModel();
            configNoeVosolMojazeFaktorModel.setCcConfigNoeVosolMojazeFaktor(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_ccConfig_NoeVosolMojaze_Faktor())));
            configNoeVosolMojazeFaktorModel.setCodeNoeVosol_Tablet(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_CodeNoeVosol_Tablet())));
            configNoeVosolMojazeFaktorModel.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_CodeNoeVosolAzMoshtary())));
            configNoeVosolMojazeFaktorModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_CodeVazeiat())));
            configNoeVosolMojazeFaktorModel.setTxtNoeVosol(cursor.getString(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_txtNoeVosol())));
//            configNoeVosolMojazeFaktorModel.setIsPishDariaft(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_IsPishDariaft())));
            configNoeVosolMojazeFaktorModel.setMashmoolTakhfifNaghdi(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_MashmoolTakhfifNaghdi())));
            configNoeVosolMojazeFaktorModel.setMashmoolDirkardVosol(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_MashmoolDirkardVosol())));
            configNoeVosolMojazeFaktorModel.setMaxModatTajil(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_MaxModatTajil())));
            configNoeVosolMojazeFaktorModel.setMinMablaghForIsShow(cursor.getInt(cursor.getColumnIndex(configNoeVosolMojazeFaktorModel.getCOLUMN_MinMablaghForIsShow())));


            configNoeVosolMojazeFaktorModels.add(configNoeVosolMojazeFaktorModel);
            cursor.moveToNext();
        }
        return configNoeVosolMojazeFaktorModels;
    }



}
