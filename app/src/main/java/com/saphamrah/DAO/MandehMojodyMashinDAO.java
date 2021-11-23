package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.AmargarGorohModel;
import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxCallback;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetMandehMojodyMashinResponse;
import com.saphamrah.WebService.ServiceResponse.GetMandehMojodyMashinResult;
import com.saphamrah.protos.GroupStatisticianGrpc;
import com.saphamrah.protos.GroupStatisticianReply;
import com.saphamrah.protos.GroupStatisticianReplyList;
import com.saphamrah.protos.GroupStatisticianRequest;
import com.saphamrah.protos.RemainingInventoryGrpc;
import com.saphamrah.protos.RemainingInventoryReply;
import com.saphamrah.protos.RemainingInventoryReplyList;
import com.saphamrah.protos.RemainingInventoryRequest;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MandehMojodyMashinDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = MandehMojodyMashinDAO.class.getSimpleName();


    public MandehMojodyMashinDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MandehMojodyMashinDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MandehMojodyMashinModel.COLUMN_ccKalaCode(),
            MandehMojodyMashinModel.COLUMN_CodeNoeKala(),
            MandehMojodyMashinModel.COLUMN_Mojody(),
            MandehMojodyMashinModel.COLUMN_ShomarehBach(),
            MandehMojodyMashinModel.COLUMN_TarikhTolid(),
            MandehMojodyMashinModel.COLUMN_GheymatMasrafKonandeh(),
            MandehMojodyMashinModel.COLUMN_GheymatForosh(),
            MandehMojodyMashinModel.COLUMN_ccTaminKonandeh(),
            MandehMojodyMashinModel.COLUMN_Max_Mojody(),
            MandehMojodyMashinModel.COLUMN_Max_MojodyByShomarehBach(),
            MandehMojodyMashinModel.COLUMN_IsAdamForosh()
        };
    }

    public void fetchMandehMojodyMashinGrpc(final Context context, final String activityNameForLog, final String ccAnbarak, String ccForoshandeh, String ccMamorPakhsh, final RetrofitResponse retrofitResponse)
    {
        try {


            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            serverIpModel.setPort("5000");


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                RemainingInventoryGrpc.RemainingInventoryBlockingStub remainingInventoryBlockingStub = RemainingInventoryGrpc.newBlockingStub(managedChannel);
                RemainingInventoryRequest remainingInventoryRequest = RemainingInventoryRequest.newBuilder().build();

                Callable<RemainingInventoryReplyList> getRemainingInventoryCallable  = () -> remainingInventoryBlockingStub.getRemainingInventory(remainingInventoryRequest);
                RxAsync.makeObservable(getRemainingInventoryCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(remainingInventoryReplyList -> {
                            ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = new ArrayList<>();
                            for (RemainingInventoryReply remainingInventoryReply : remainingInventoryReplyList.getRemainingInventoriesList()) {
                                MandehMojodyMashinModel mandehMojodyMashinModel = new MandehMojodyMashinModel();

                                mandehMojodyMashinModel.setCcKalaCode(remainingInventoryReply.getGoodCodeID());
                                mandehMojodyMashinModel.setCodeNoeKala(remainingInventoryReply.getGoodTypeCode());
                                mandehMojodyMashinModel.setMojody(remainingInventoryReply.getInventory());
                                mandehMojodyMashinModel.setShomarehBach(remainingInventoryReply.getBachNumber());
                                mandehMojodyMashinModel.setTarikhTolid(remainingInventoryReply.getProductionDate());
                                mandehMojodyMashinModel.setGheymatMasrafKonandeh(remainingInventoryReply.getConsumerPrice());
                                mandehMojodyMashinModel.setGheymatForosh(remainingInventoryReply.getSellPrice());
                                mandehMojodyMashinModel.setCcTaminKonandeh(remainingInventoryReply.getProviderID());
                                mandehMojodyMashinModel.setMaxMojody(remainingInventoryReply.getMaxInventory());
                                mandehMojodyMashinModel.setMax_MojodyByShomarehBach(0);
                                mandehMojodyMashinModel.setIsAdamForosh(remainingInventoryReply.getIsNonSale());


                                mandehMojodyMashinModels.add(mandehMojodyMashinModel);
                            }

                            return mandehMojodyMashinModels;

                        }).subscribe(new Observer<ArrayList<MandehMojodyMashinModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<MandehMojodyMashinModel> kalaModels) {
                        retrofitResponse.onSuccess(kalaModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }




    }
    public void fetchMandehMojodyMashin(final Context context, final String activityNameForLog, final String ccAnbarak, String ccForoshandeh, String ccMamorPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MandehMojodyMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMandehMojodyMashin", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIServiceGet.class);
            Call<GetMandehMojodyMashinResult> call = apiService.getMandehMojodyMashin(ccAnbarak , ccForoshandeh , ccMamorPakhsh);
            call.enqueue(new Callback<GetMandehMojodyMashinResult>() {
                @Override
                public void onResponse(Call<GetMandehMojodyMashinResult> call, Response<GetMandehMojodyMashinResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MandehMojodyMashinDAO.class.getSimpleName(), "", "fetchMandehMojodyMashin", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMandehMojodyMashinResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MandehMojodyMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMandehMojodyMashin", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MandehMojodyMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMandehMojodyMashin", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MandehMojodyMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMandehMojodyMashin", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MandehMojodyMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMandehMojodyMashin", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMandehMojodyMashinResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MandehMojodyMashinDAO.class.getSimpleName(), activityNameForLog, "fetchMandehMojodyMashin", "onFailure");
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



    public boolean insertGroup(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MandehMojodyMashinModel mandehMojodyMashinModel : mandehMojodyMashinModels)
            {
                ContentValues contentValues = modelToContentvalue(mandehMojodyMashinModel);
                db.insertOrThrow(MandehMojodyMashinModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MandehMojodyMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MandehMojodyMashinDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<MandehMojodyMashinModel> getAll()
    {
        ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MandehMojodyMashinModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mandehMojodyMashinModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MandehMojodyMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MandehMojodyMashinDAO" , "" , "getAll" , "");
        }
        return mandehMojodyMashinModels;
    }


    public void updateIsAdamForosh(String ccKalaCodes)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "update MandehMojodyMashin set IsAdamForosh = 1 where ccKalaCode in (" + ccKalaCodes + ")";
            db.execSQL(query);
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MandehMojodyMashinModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MandehMojodyMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MandehMojodyMashinDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MandehMojodyMashinModel mandehMojodyMashinModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MandehMojodyMashinModel.COLUMN_ccKalaCode() , mandehMojodyMashinModel.getCcKalaCode());
        contentValues.put(MandehMojodyMashinModel.COLUMN_CodeNoeKala() , mandehMojodyMashinModel.getCodeNoeKala());
        contentValues.put(MandehMojodyMashinModel.COLUMN_Mojody() , mandehMojodyMashinModel.getMojody());
        contentValues.put(MandehMojodyMashinModel.COLUMN_ShomarehBach() , mandehMojodyMashinModel.getShomarehBach());
        contentValues.put(MandehMojodyMashinModel.COLUMN_TarikhTolid() , mandehMojodyMashinModel.getTarikhTolid());
        contentValues.put(MandehMojodyMashinModel.COLUMN_GheymatMasrafKonandeh() , mandehMojodyMashinModel.getGheymatMasrafKonandeh());
        contentValues.put(MandehMojodyMashinModel.COLUMN_GheymatForosh() , mandehMojodyMashinModel.getGheymatForosh());
        contentValues.put(MandehMojodyMashinModel.COLUMN_ccTaminKonandeh() , mandehMojodyMashinModel.getCcTaminKonandeh());
        contentValues.put(MandehMojodyMashinModel.COLUMN_Max_Mojody() , mandehMojodyMashinModel.getMaxMojody());
        contentValues.put(MandehMojodyMashinModel.COLUMN_Max_MojodyByShomarehBach() , mandehMojodyMashinModel.getMax_MojodyByShomarehBach());
        contentValues.put(MandehMojodyMashinModel.COLUMN_IsAdamForosh() , mandehMojodyMashinModel.getIsAdamForosh());

        return contentValues;
    }


    private ArrayList<MandehMojodyMashinModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MandehMojodyMashinModel mandehMojodyMashinModel = new MandehMojodyMashinModel();

            mandehMojodyMashinModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_ccKalaCode())));
            mandehMojodyMashinModel.setCodeNoeKala(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_CodeNoeKala())));
            mandehMojodyMashinModel.setMojody(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_Mojody())));
            mandehMojodyMashinModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_ShomarehBach())));
            mandehMojodyMashinModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_TarikhTolid())));
            mandehMojodyMashinModel.setGheymatMasrafKonandeh(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_GheymatMasrafKonandeh())));
            mandehMojodyMashinModel.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_GheymatForosh())));
            mandehMojodyMashinModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_ccTaminKonandeh())));
            mandehMojodyMashinModel.setMaxMojody(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_Max_Mojody())));
            mandehMojodyMashinModel.setMax_MojodyByShomarehBach(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_Max_MojodyByShomarehBach())));
            mandehMojodyMashinModel.setIsAdamForosh(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_IsAdamForosh())));

            mandehMojodyMashinModels.add(mandehMojodyMashinModel);
            cursor.moveToNext();
        }
        return mandehMojodyMashinModels;
    }





    private Callable<Boolean>  insertGroupRX(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        return new Callable<Boolean>() {
            public Boolean call() {
                return insertGroup(mandehMojodyMashinModels);
            }
        };
    }

    private Callable<Boolean> deleteAllRx() {
        return new Callable<Boolean>() {
            public Boolean call() {
                return deleteAll();
            }
        };
    }

}
