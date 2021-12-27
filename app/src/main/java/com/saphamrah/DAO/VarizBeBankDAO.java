package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.RotbehModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Model.VarizBeBankModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetVarizBeBankResult;
import com.saphamrah.protos.DepositToBankGrpc;
import com.saphamrah.protos.DepositToBankReply;
import com.saphamrah.protos.DepositToBankReplyList;
import com.saphamrah.protos.DepositToBankRequest;

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

public class VarizBeBankDAO
{

    private DBHelper dbHelper;
    private Context context;
    private VarizBeBankModel modelTABLE_NAME = new VarizBeBankModel();

    public VarizBeBankDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "VarizBeBankDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
                modelTABLE_NAME.getCOLUMN_ccDariaftPardakht(),
                modelTABLE_NAME.getCOLUMN_namemoshtary(),
                modelTABLE_NAME.getCOLUMN_ccmoshtary(),
                modelTABLE_NAME.getCOLUMN_Mablagh(),
                modelTABLE_NAME.getCOLUMN_CodeNoeVosol(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_IsSend(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_IsSelected(),
                modelTABLE_NAME.getCOLUMN_Taeed(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_ccBankSanad(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_NameShobehSanad(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_CodeShobehSand(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_ShomareHesabSanad(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_ccShomareHesab(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_ShomarehSanad(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_TarikhSanad(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_NameBankSanad(),
                modelTABLE_NAME.getCOLUMN_txtCodeNoeVosol(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_MablaghSabtShode()

        };
    }


    public void fetchVarizBeBankGrpc(final Context context, final String activityNameForLog , int ccAfrad, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RotbehModel.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                DepositToBankGrpc.DepositToBankBlockingStub blockingStub = DepositToBankGrpc.newBlockingStub(managedChannel);
                DepositToBankRequest request = DepositToBankRequest.newBuilder().setPersonID(String.valueOf(ccAfrad)).build();

                Callable<DepositToBankReplyList> replyListCallable = () -> blockingStub.getDepositToBank(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<VarizBeBankModel> models = new ArrayList<>();
                            for (DepositToBankReply reply : replyList.getDepositToBanksList()) {
                                VarizBeBankModel model = new VarizBeBankModel();


                                model.setCcDariaftPardakht(reply.getReceivePaymentID());
                                model.setNamemoshtary(reply.getCustomerName());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setMablagh(reply.getPrice());
                                model.setCodeNoeVosol(reply.getCollectionTypeCode());
                                model.setTaeed(reply.getConfirm());
                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<VarizBeBankModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<VarizBeBankModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), RotbehModel.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }

    public void fetchVarizBeBanck(final Context context, final String activityNameForLog ,int ccAfrad, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, VarizBeBankDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetVarizBeBankResult> call = apiServiceGet.getVosolNaghdRooz(ccAfrad);
            call.enqueue(new Callback<GetVarizBeBankResult>()
            {
                @Override
                public void onResponse(Call<GetVarizBeBankResult> call, Response<GetVarizBeBankResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, VarizBeBankDAO.class.getSimpleName(), "", "fetchVarizBeBank", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetVarizBeBankResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), VarizBeBankDAO.class.getSimpleName(), activityNameForLog, "fetchVarizBeBank", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), VarizBeBankDAO.class.getSimpleName(), activityNameForLog, "fetchVarizBeBank", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, VarizBeBankDAO.class.getSimpleName(), activityNameForLog, "fetchVarizBeBank", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), VarizBeBankDAO.class.getSimpleName(), activityNameForLog, "fetchVarizBeBank", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetVarizBeBankResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), VarizBeBankDAO.class.getSimpleName(), activityNameForLog, "fetchVarizBeBank", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<VarizBeBankModel> models)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (VarizBeBankModel model : models)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(VarizBeBankModel.TABLE_NAME , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , VarizBeBankModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "VarizBeBankDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<VarizBeBankModel> getAll()
    {
        ArrayList<VarizBeBankModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(VarizBeBankModel.TABLE_NAME, allColumns(), null, null, null, null, modelTABLE_NAME.getCOLUMN_ExtraProp_IsSelected());
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
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "VarizBeBankDAO" , "" , "getAll" , "");
        }
        return models;
    }

    public ArrayList<VarizBeBankModel> getIsSelected()
    {
        String query = "select * from varizBeBank where " + modelTABLE_NAME.getCOLUMN_ExtraProp_IsSelected() + " = " + 0 + " ";
        ArrayList<VarizBeBankModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
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
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "VarizBeBankDAO" , "" , "getIsSelected" , "");
        }
        return models;
    }

    /**
     * when we have complete variz
     * @param shomarehSanad
     * @return
     */
    public ArrayList<VarizBeBankModel> getByCcShomarehSanadMablagh(String shomarehSanad)
    {
        String query = "select * from varizBeBank where " + modelTABLE_NAME.getCOLUMN_ExtraProp_ShomarehSanad() + " = '" + shomarehSanad + "' and Mablagh = ExtraProp_MablaghSabtShode  and ExtraProp_IsSend = 0";
        ArrayList<VarizBeBankModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
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
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "VarizBeBankDAO" , "" , "getByCcShomarehSanadMablagh" , "");
        }
        return models;
    }

    /**
     * when we have not complete variz
     * @param shomarehSanad
     * @return
     */
    public ArrayList<VarizBeBankModel> getByCcShomarehSanadExtraProp_Mablagh(String shomarehSanad)
    {
        String query = "select * from varizBeBank where " + modelTABLE_NAME.getCOLUMN_ExtraProp_ShomarehSanad() + " = '" + shomarehSanad + "' and Mablagh != ExtraProp_MablaghSabtShode and ExtraProp_IsSend = 0 ";
        ArrayList<VarizBeBankModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
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
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "VarizBeBankDAO" , "" , "getByCcShomarehSanadExtraProp_Mablagh" , "");
        }
        return models;
    }

    public ArrayList<VarizBeBankModel> getVarizUpdate(){
        ArrayList<VarizBeBankModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT ExtraProp_NameShobehSanad , ExtraProp_ShomarehSanad , ExtraProp_TarikhSanad , ExtraProp_NameBankSanad, ExtraProp_IsSend ,sum(ExtraProp_MablaghSabtShode) ExtraProp_MablaghSabtShode " +
                    " FROM varizbebank " +
                    " WHERE ExtraProp_IsSelected = 1 " +
                    " GROUP BY ExtraProp_NameShobehSanad , ExtraProp_ShomarehSanad , ExtraProp_TarikhSanad , ExtraProp_NameBankSanad ,ExtraProp_IsSend";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModelVarizUpdater(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , VarizBeBankModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "VarizBeBankDAO" , "" , "getVarizUpdate" , "");
        }
        return models;
    }

    public boolean checkHaveShomarehSanadForUpdate(String ExtraProp_ShomarehSanad){
        boolean haveShomarehsanad = false;

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM VarizBeBank WHERE  ExtraProp_ShomarehSanad  =   " + ExtraProp_ShomarehSanad + "" ;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    haveShomarehsanad = true;
                } else {
                    haveShomarehsanad = false;
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , VarizBeBankModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "VarizBeBankDAO" , "" , "checkHaveShomarehSanadForUpdate" , "");
        }


        return haveShomarehsanad;
    }




    /**
     * update db vajh naghd to fish banki
     * @param entity
     * @return
     */
    public boolean updateNaghdBeFish(VarizBeBankModel entity)
    {
        ContentValues values = new ContentValues();
        boolean update = false;


        try
        {
            values.put("ccDariaftPardakht", entity.getCcDariaftPardakht());
            values.put("ExtraProp_MablaghSabtShode" , entity.getExtraProp_MablaghSabtShode());
            values.put("ExtraProp_ccBankSanad" , entity.getExtraProp_ccBankSanad());
            values.put("ExtraProp_NameShobehSanad" , entity.getExtraProp_NameShobehSanad());
            values.put("ExtraProp_CodeShobehSand" , entity.getExtraProp_CodeShobehSand());
            values.put("ExtraProp_ShomareHesabSanad" , entity.getExtraProp_ShomareHesabSanad());
            values.put("ExtraProp_ccShomareHesab" , entity.getExtraProp_ccShomareHesab());
            values.put("ExtraProp_ShomarehSanad" , entity.getExtraProp_ShomarehSanad());
            values.put("ExtraProp_TarikhSanad" , entity.getExtraProp_TarikhSanad());
            values.put("ExtraProp_NameBankSanad" , entity.getExtraProp_NameBankSanad());
            values.put("CodeNoeVosol" , entity.getCodeNoeVosol());
            values.put("ExtraProp_IsSelected" , entity.getExtraProp_IsSelected());



            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(VarizBeBankModel.TABLE_NAME, values, "ccDariaftPardakht = " +entity.getCcDariaftPardakht(),null);
            update = true;
            db.close();
        }
        catch (Exception e)
        {
            update = false;
            throw new RuntimeException(e);
        }

        return update;
    }

    /**
     * update is send when send is success
     * @param shomarehSanad
     * @return
     */
    public boolean updateIsSend(int shomarehSanad)
    {
        ContentValues values = new ContentValues();
        boolean update = false;


        try
        {
            values.put("ExtraProp_IsSend", 1);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(VarizBeBankModel.TABLE_NAME, values, "ExtraProp_ShomarehSanad = " + shomarehSanad,null);
            update = true;
            db.close();
        }
        catch (Exception e)
        {
            update = false;
            throw new RuntimeException(e);
        }

        return update;
    }

    public boolean updateCcDaryaftPardakht(int ccDaryaftPardakhat , int ccDaryaftPardakhatNew)
    {
        ContentValues values = new ContentValues();
        boolean update = false;


        try
        {
            values.put("ccDariaftPardakht", ccDaryaftPardakhatNew);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(VarizBeBankModel.TABLE_NAME, values, "ccDariaftPardakht = " + ccDaryaftPardakhat,null);
            update = true;
            db.close();
        }
        catch (Exception e)
        {
            update = false;
            throw new RuntimeException(e);
        }

        return update;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(VarizBeBankModel.TABLE_NAME, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "VarizBeBankDOA" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(VarizBeBankModel model)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(model.getCOLUMN_ccDariaftPardakht() , model.getCcDariaftPardakht());
        contentValues.put(model.getCOLUMN_namemoshtary() , model.getNamemoshtary());
        contentValues.put(model.getCOLUMN_ccmoshtary() , model.getCcMoshtary());
        contentValues.put(model.getCOLUMN_Mablagh() , model.getMablagh());
        contentValues.put(model.getCOLUMN_CodeNoeVosol() , model.getCodeNoeVosol());
        contentValues.put(model.getCOLUMN_ExtraProp_IsSend(),model.getExtraProp_IsSend());
        contentValues.put(model.getCOLUMN_ExtraProp_IsSelected() , model.getExtraProp_IsSelected());
        contentValues.put(model.getCOLUMN_Taeed() , model.getTaeed());
        contentValues.put(model.getCOLUMN_ExtraProp_ccBankSanad() , model.getExtraProp_ccBankSanad());
        contentValues.put(model.getCOLUMN_ExtraProp_NameShobehSanad() , model.getExtraProp_NameShobehSanad());
        contentValues.put(model.getCOLUMN_ExtraProp_CodeShobehSand() , model.getExtraProp_CodeShobehSand());
        contentValues.put(model.getCOLUMN_ExtraProp_ShomareHesabSanad() , model.getExtraProp_ShomareHesabSanad());
        contentValues.put(model.getCOLUMN_ExtraProp_ccShomareHesab() , model.getExtraProp_ccShomareHesab());
        contentValues.put(model.getCOLUMN_ExtraProp_ShomarehSanad() , model.getExtraProp_ShomarehSanad());
        contentValues.put(model.getCOLUMN_ExtraProp_TarikhSanad() , model.getExtraProp_TarikhSanad());
        contentValues.put(model.getCOLUMN_ExtraProp_NameBankSanad() , model.getExtraProp_NameBankSanad());
        contentValues.put(model.getCOLUMN_txtCodeNoeVosol() , model.getTxtCodeNoeVosol());
        contentValues.put(model.getCOLUMN_ExtraProp_MablaghSabtShode() , model.getMablagh());


        return contentValues;
    }

    private ArrayList<VarizBeBankModel> cursorToModel(Cursor cursor)
    {
        ArrayList<VarizBeBankModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            VarizBeBankModel model = new VarizBeBankModel();

            model.setCcDariaftPardakht(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccDariaftPardakht())));
            model.setNamemoshtary(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_namemoshtary())));
            model.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccmoshtary())));
            model.setMablagh(cursor.getDouble(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_Mablagh())));
            model.setCodeNoeVosol(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_CodeNoeVosol())));
            model.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_IsSend())));
            model.setExtraProp_IsSelected(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_IsSelected())));
            model.setTaeed(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_Taeed())));
            model.setExtraProp_ccBankSanad(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_ccBankSanad())));
            model.setExtraProp_NameShobehSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_NameShobehSanad())));
            model.setExtraProp_CodeShobehSand(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_CodeShobehSand())));
            model.setExtraProp_ShomareHesabSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_ShomareHesabSanad())));
            model.setExtraProp_ccShomareHesab(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_ccShomareHesab())));
            model.setExtraProp_ShomarehSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_ShomarehSanad())));
            model.setExtraProp_TarikhSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_TarikhSanad())));
            model.setExtraProp_NameBankSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_NameBankSanad())));
            model.setTxtCodeNoeVosol(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_txtCodeNoeVosol())));
            model.setExtraProp_MablaghSabtShode(cursor.getDouble(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_MablaghSabtShode())));



            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }

    private ArrayList<VarizBeBankModel> cursorToModelVarizUpdater(Cursor cursor)
    {
        ArrayList<VarizBeBankModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            VarizBeBankModel model = new VarizBeBankModel();

            model.setExtraProp_MablaghSabtShode(cursor.getDouble(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_MablaghSabtShode())));
            model.setExtraProp_NameShobehSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_NameShobehSanad())));
            model.setExtraProp_ShomarehSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_ShomarehSanad())));
            model.setExtraProp_TarikhSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_TarikhSanad())));
            model.setExtraProp_NameBankSanad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_NameBankSanad())));




            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }


}
