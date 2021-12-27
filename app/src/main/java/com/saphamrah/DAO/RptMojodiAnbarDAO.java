package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.RotbehModel;
import com.saphamrah.Model.RptMojodiAnbarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetRptMojodiAnbarakResult;
import com.saphamrah.protos.RptBinInventoryGrpc;
import com.saphamrah.protos.RptBinInventoryReply;
import com.saphamrah.protos.RptBinInventoryReplyList;
import com.saphamrah.protos.RptBinInventoryRequest;

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

public class RptMojodiAnbarDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public RptMojodiAnbarDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptMojodiAnbarDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptMojodiAnbarModel.COLUMN_Radif(),
            RptMojodiAnbarModel.COLUMN_ccKalaCode(),
            RptMojodiAnbarModel.COLUMN_CodeKala(),
            RptMojodiAnbarModel.COLUMN_NameKala(),
            RptMojodiAnbarModel.COLUMN_MandehMojodi_Karton(),
            RptMojodiAnbarModel.COLUMN_MandehMojodi_Basteh(),
            RptMojodiAnbarModel.COLUMN_MandehMojodi_Adad(),
            RptMojodiAnbarModel.COLUMN_IsAdamForosh(),
            RptMojodiAnbarModel.COLUMN_ccSazmanForosh(),
            RptMojodiAnbarModel.COLUMN_NameSazmanForosh(),
            RptMojodiAnbarModel.COLUMN_ccDarkhastFaktorNoeForosh()


        };
    }


    public void fetchRptMojodyAnbarakGrpc(final Context context, final String activityNameForLog, final String ccAnbarak , int ccForoshandeh , int ccMamorPakhsh ,int noeMasouliat , final RetrofitResponse retrofitResponse) {
         /*
        check noe masoliat for request
         */
        String ccAnbarakReq = ccAnbarak;
        int ccForoshandehReq = ccForoshandeh;
        int ccMamorPakhshReq = ccMamorPakhsh;

        if (noeMasouliat ==  2 || noeMasouliat == 3){
            ccMamorPakhshReq = 0;
        }

        if (noeMasouliat ==  4 || noeMasouliat == 5){
            ccForoshandehReq = 0;
        }
        if (noeMasouliat ==  1 ){
            ccAnbarakReq = "0";
            ccMamorPakhshReq = 0;
        }
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
                RptBinInventoryGrpc.RptBinInventoryBlockingStub blockingStub = RptBinInventoryGrpc.newBlockingStub(managedChannel);
                RptBinInventoryRequest request = RptBinInventoryRequest.newBuilder().setBinID(ccAnbarakReq).setSalesManID(String.valueOf(ccForoshandehReq)).setDistributerID(String.valueOf(ccMamorPakhshReq)).build();

                Callable<RptBinInventoryReplyList> replyListCallable = () -> blockingStub.getRptBinInventory(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<RptMojodiAnbarModel> models = new ArrayList<>();
                            for (RptBinInventoryReply reply : replyList.getRptBinInventorysList()) {
                                RptMojodiAnbarModel model = new RptMojodiAnbarModel();


                                model.setRadif(reply.getRow());
                                model.setCcKalaCode(reply.getGoodsCodeID());
                                model.setCodeKala(reply.getGoodsCode());
                                model.setNameKala(reply.getGoodsName());
                                model.setMandehMojodi_Karton(reply.getBoxRemainingInventory());
                                model.setMandehMojodi_Basteh(reply.getPakageRemainingInventory());
                                model.setMandehMojodi_Adad(reply.getNumberRemainingInventory());
                                model.setIsAdamForosh(reply.getIsNonSell());
                                model.setCcSazmanForosh(reply.getSellOrganizationID());
                                model.setNameSazmanForosh(reply.getSellOrganizationName());
                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptMojodiAnbarModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptMojodiAnbarModel> models) {
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

    public void fetchRptMojodyAnbarak(final Context context, final String activityNameForLog, final String ccAnbarak , int ccForoshandeh , int ccMamorPakhsh ,int noeMasouliat , final RetrofitResponse retrofitResponse)
    {

        /*
        check noe masoliat for request
         */
        String ccAnbarakReq = ccAnbarak;
        int ccForoshandehReq = ccForoshandeh;
        int ccMamorPakhshReq = ccMamorPakhsh;

        if (noeMasouliat ==  2 || noeMasouliat == 3){
            ccMamorPakhshReq = 0;
        }

        if (noeMasouliat ==  4 || noeMasouliat == 5){
            ccForoshandehReq = 0;
        }
        if (noeMasouliat ==  1 ){
            ccAnbarakReq = "0";
            ccMamorPakhshReq = 0;
        }
        Log.d("RptMojodyAnbarak","ccMamorPakhshReq"+ccMamorPakhshReq +" ,ccForoshandehReq"+ccForoshandehReq+", ccAnbarakReq:"+ccAnbarakReq);

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMojodiAnbarDAO.class.getSimpleName(), activityNameForLog, "fetchRptMojodyAnbarak", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetRptMojodiAnbarakResult> call = apiServiceGet.getRptMojodiAnbarak(ccAnbarakReq , String.valueOf(ccForoshandehReq) , String.valueOf(ccMamorPakhshReq));
            call.enqueue(new Callback<GetRptMojodiAnbarakResult>() {
                @Override
                public void onResponse(Call<GetRptMojodiAnbarakResult> call, Response<GetRptMojodiAnbarakResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptMojodiAnbarDAO.class.getSimpleName(), "", "fetchRptMojodyAnbarak", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetRptMojodiAnbarakResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptMojodiAnbarDAO.class.getSimpleName(), activityNameForLog, "fetchRptMojodyAnbarak", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptMojodiAnbarDAO.class.getSimpleName(), activityNameForLog, "fetchRptMojodyAnbarak", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMojodiAnbarDAO.class.getSimpleName(), activityNameForLog, "fetchRptMojodyAnbarak", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptMojodiAnbarDAO.class.getSimpleName(), activityNameForLog, "fetchRptMojodyAnbarak", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetRptMojodiAnbarakResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptMojodiAnbarDAO.class.getSimpleName(), activityNameForLog, "fetchRptMojodyAnbarak", "onFailure");
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

    public boolean insertGroup(ArrayList<RptMojodiAnbarModel> rptMojodiAnbarModels)
    {
        Log.d("mojodiModel" , "size on insertgroup : " + rptMojodiAnbarModels.size());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptMojodiAnbarModel rptMojodiAnbarModel : rptMojodiAnbarModels)
            {
                ContentValues contentValues = modelToContentvalue(rptMojodiAnbarModel);
                db.insertOrThrow(RptMojodiAnbarModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptMojodiAnbarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMojodiAnbarDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptMojodiAnbarModel> getAllOrderByCodeKala()
    {
        ArrayList<RptMojodiAnbarModel> rptMojodiAnbarModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptMojodiAnbarModel.TableName(), allColumns(), null, null, null, null, RptMojodiAnbarModel.COLUMN_CodeKala());
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMojodiAnbarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMojodiAnbarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMojodiAnbarDAO" , "" , "getAllOrderByCodeKala" , "");
        }
        return rptMojodiAnbarModels;
    }

    public ArrayList<RptMojodiAnbarModel> getAllOrderByNameKala(int sortByHavalehFaktor)
    {
        ArrayList<RptMojodiAnbarModel> rptMojodiAnbarModels = new ArrayList<>();
        try
        {
            String query = "select * from " + RptMojodiAnbarModel.TableName() + " WHERE ccDarkhastFaktorNoeForosh = "+ sortByHavalehFaktor +" order by " + RptMojodiAnbarModel.COLUMN_NameKala() + " COLLATE UNICODE";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            //Cursor cursor = db.query(RptMojodiAnbarModel.TableName(), allColumns(), null, null, null, null, RptMojodiAnbarModel.COLUMN_NameKala());
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMojodiAnbarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMojodiAnbarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMojodiAnbarDAO" , "" , "getAllOrderByNameKala" , "");
        }
        return rptMojodiAnbarModels;
    }

    public ArrayList<RptMojodiAnbarModel> getAllOrderBySortHavalehFaktor(int sortHavalehFaktor)
    {
        ArrayList<RptMojodiAnbarModel> rptMojodiAnbarModels = new ArrayList<>();
        try
        {
            String query = "select * from " + RptMojodiAnbarModel.TableName() + " Where ccDarkhastFaktorNoeForosh = "+ sortHavalehFaktor + " order by " + RptMojodiAnbarModel.COLUMN_NameKala() + " COLLATE UNICODE";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            //Cursor cursor = db.query(RptMojodiAnbarModel.TableName(), allColumns(), null, null, null, null, RptMojodiAnbarModel.COLUMN_NameKala());
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMojodiAnbarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMojodiAnbarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMojodiAnbarDAO" , "" , "getAllOrderByNameKala" , "");
        }
        return rptMojodiAnbarModels;
    }

    public ArrayList<RptMojodiAnbarModel> getAllOrderByCount(int sortByHavalehFaktor)
    {
        ArrayList<RptMojodiAnbarModel> rptMojodiAnbarModels = new ArrayList<>();
        try
        {
            String query = "select * from " + RptMojodiAnbarModel.TableName() + " WHERE ccDarkhastFaktorNoeForosh = "+ sortByHavalehFaktor +" order by " +
                    RptMojodiAnbarModel.COLUMN_MandehMojodi_Karton() + " desc , " +
                    RptMojodiAnbarModel.COLUMN_MandehMojodi_Basteh() + " desc , " +
                    RptMojodiAnbarModel.COLUMN_MandehMojodi_Adad() + " desc";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMojodiAnbarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMojodiAnbarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMojodiAnbarDAO" , "" , "getAllOrderByCount" , "");
        }
        return rptMojodiAnbarModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptMojodiAnbarModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptMojodiAnbarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMojodiAnbarDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptMojodiAnbarModel rptMojodiAnbarModel)
    {
        ContentValues contentValues = new ContentValues();

        if (rptMojodiAnbarModel.getRadif() > 0)
        {
            contentValues.put(RptMojodiAnbarModel.COLUMN_Radif() , rptMojodiAnbarModel.getRadif());
        }
        contentValues.put(RptMojodiAnbarModel.COLUMN_ccKalaCode() , rptMojodiAnbarModel.getCcKalaCode());
        contentValues.put(RptMojodiAnbarModel.COLUMN_CodeKala() , rptMojodiAnbarModel.getCodeKala());
        contentValues.put(RptMojodiAnbarModel.COLUMN_NameKala() , rptMojodiAnbarModel.getNameKala());
        contentValues.put(RptMojodiAnbarModel.COLUMN_MandehMojodi_Karton() , rptMojodiAnbarModel.getMandehMojodi_Karton());
        contentValues.put(RptMojodiAnbarModel.COLUMN_MandehMojodi_Basteh() , rptMojodiAnbarModel.getMandehMojodi_Basteh());
        contentValues.put(RptMojodiAnbarModel.COLUMN_MandehMojodi_Adad() , rptMojodiAnbarModel.getMandehMojodi_Adad());
        contentValues.put(RptMojodiAnbarModel.COLUMN_IsAdamForosh() , rptMojodiAnbarModel.getIsAdamForosh());
        contentValues.put(RptMojodiAnbarModel.COLUMN_ccSazmanForosh() , rptMojodiAnbarModel.getCcSazmanForosh());
        contentValues.put(RptMojodiAnbarModel.COLUMN_NameSazmanForosh() , rptMojodiAnbarModel.getNameSazmanForosh());
        contentValues.put(RptMojodiAnbarModel.COLUMN_ccDarkhastFaktorNoeForosh() , rptMojodiAnbarModel.getCcDarkhastFaktorNoeForosh());


        return contentValues;
    }


    private ArrayList<RptMojodiAnbarModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptMojodiAnbarModel> rptMojodiAnbarModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptMojodiAnbarModel rptMojodiAnbarModel = new RptMojodiAnbarModel();

            rptMojodiAnbarModel.setRadif(cursor.getInt(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_Radif())));
            rptMojodiAnbarModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_ccKalaCode())));
            rptMojodiAnbarModel.setCodeKala(cursor.getString(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_CodeKala())));
            rptMojodiAnbarModel.setNameKala(cursor.getString(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_NameKala())));
            rptMojodiAnbarModel.setMandehMojodi_Karton(cursor.getInt(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_MandehMojodi_Karton())));
            rptMojodiAnbarModel.setMandehMojodi_Basteh(cursor.getInt(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_MandehMojodi_Basteh())));
            rptMojodiAnbarModel.setMandehMojodi_Adad(cursor.getInt(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_MandehMojodi_Adad())));
            rptMojodiAnbarModel.setIsAdamForosh(cursor.getInt(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_IsAdamForosh())));
            rptMojodiAnbarModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_ccSazmanForosh())));
            rptMojodiAnbarModel.setNameSazmanForosh(cursor.getString(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_NameSazmanForosh())));
            rptMojodiAnbarModel.setCcDarkhastFaktorNoeForosh(cursor.getInt(cursor.getColumnIndex(RptMojodiAnbarModel.COLUMN_ccDarkhastFaktorNoeForosh())));

            rptMojodiAnbarModels.add(rptMojodiAnbarModel);
            cursor.moveToNext();
        }
        return rptMojodiAnbarModels;
    }
    
}
