package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.AmargarMarkazSazmanForoshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAmargarMarkazForoshResult;
import com.saphamrah.protos.SellOrganizationCenterStatisticianGrpc;
import com.saphamrah.protos.SellOrganizationCenterStatisticianReply;
import com.saphamrah.protos.SellOrganizationCenterStatisticianReplyList;
import com.saphamrah.protos.SellOrganizationCenterStatisticianRequest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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

public class AmargarMarkazSazmanForoshDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "AmargarMarkazSazmanForoshDAO";

    public AmargarMarkazSazmanForoshDAO(Context context)
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
            AmargarMarkazSazmanForoshModel.COLUMN_ccAmargarMarkazSazmanForosh(),
            AmargarMarkazSazmanForoshModel.COLUMN_ccAmargar(),
            AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazForosh(),
            AmargarMarkazSazmanForoshModel.COLUMN_nameMarkazForosh(),
            AmargarMarkazSazmanForoshModel.COLUMN_ccSazmanForosh(),
            AmargarMarkazSazmanForoshModel.COLUMN_nameSazmanForosh(),
            AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazSazmanForosh(),
            AmargarMarkazSazmanForoshModel.COLUMN_nameMarkazSazmanForosh()
        };
    }


    public void fetchAmargarMarkazForoshGrpc(final Context context, final String activityNameForLog, final String ccAmargar, final RetrofitResponse retrofitResponse)
    {
        try {


            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargarGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                SellOrganizationCenterStatisticianGrpc.SellOrganizationCenterStatisticianBlockingStub sellOrganizationCenterStatisticianBlockingStub = SellOrganizationCenterStatisticianGrpc.newBlockingStub(managedChannel);
                SellOrganizationCenterStatisticianRequest sellOrganizationCenterStatisticianRequest = SellOrganizationCenterStatisticianRequest.newBuilder().setStatisticianID(Integer.parseInt(ccAmargar)).build();

                Callable<SellOrganizationCenterStatisticianReplyList> getSellOrganizationCenterStatisticianCallable  = () -> sellOrganizationCenterStatisticianBlockingStub.getSellOrganizationCenterStatistician(sellOrganizationCenterStatisticianRequest);
                RxAsync.makeObservable(getSellOrganizationCenterStatisticianCallable)
                        .map(sellOrganizationCenterStatisticianReplyList -> {
                            ArrayList<AmargarMarkazSazmanForoshModel> mahalModels = new ArrayList<>();
                            for (SellOrganizationCenterStatisticianReply sellOrganizationCenterStatisticianReply : sellOrganizationCenterStatisticianReplyList.getSellOrganizationCenterStatisticiansList()) {
                                AmargarMarkazSazmanForoshModel amargarMarkazSazmanForoshModel = new AmargarMarkazSazmanForoshModel();
                                amargarMarkazSazmanForoshModel.setCcAmargarMarkazSazmanForosh(sellOrganizationCenterStatisticianReply.getSellOrganizationCenterStatisticianID());
                                amargarMarkazSazmanForoshModel.setCcMarkazForosh(sellOrganizationCenterStatisticianReply.getSellCenterID());
                                amargarMarkazSazmanForoshModel.setCcSazmanForosh(sellOrganizationCenterStatisticianReply.getSellOrganizationID());
                                amargarMarkazSazmanForoshModel.setCcMarkazSazmanForosh(sellOrganizationCenterStatisticianReply.getSellOrganizationCenterID());
                                amargarMarkazSazmanForoshModel.setCcAmargar (sellOrganizationCenterStatisticianReply.getStatisticianID());
                                amargarMarkazSazmanForoshModel.setNameMarkazForosh(sellOrganizationCenterStatisticianReply.getSellCenterName());
                                amargarMarkazSazmanForoshModel.setNameSazmanForosh(sellOrganizationCenterStatisticianReply.getSellOrganizationName());
                                amargarMarkazSazmanForoshModel.setNameMarkazSazmanForosh(sellOrganizationCenterStatisticianReply.getSellOrganizationCenterName());


                                mahalModels.add(amargarMarkazSazmanForoshModel);
                            }

                            return mahalModels;

                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<AmargarMarkazSazmanForoshModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<AmargarMarkazSazmanForoshModel> mahalModels) {
                                retrofitResponse.onSuccess(mahalModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargarGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }

    }

    public void fetchAmargarMarkazForosh(final Context context, final String activityNameForLog, final String ccAmargar, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchAmargarMarkazForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAmargarMarkazForoshResult> call = apiServiceGet.getAmargarMarkazForosh(ccAmargar);
            call.enqueue(new Callback<GetAmargarMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAmargarMarkazForoshResult> call, Response<GetAmargarMarkazForoshResult> response)
                {
                    try
                    {
                        GetProgramModel.responseSize += response.raw().toString().getBytes(StandardCharsets.UTF_8).length;

                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchAmargarMarkazForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAmargarMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchAmargarMarkazForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchAmargarMarkazForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchAmargarMarkazForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchAmargarMarkazForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAmargarMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchAmargarMarkazForosh", "onFailure");
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

    public boolean insertGroup(ArrayList<AmargarMarkazSazmanForoshModel> AmargarMarkazSazmanForoshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (AmargarMarkazSazmanForoshModel AmargarMarkazSazmanForoshModel : AmargarMarkazSazmanForoshModels)
            {
                ContentValues contentValues = modelToContentvalue(AmargarMarkazSazmanForoshModel);
                db.insertOrThrow(AmargarMarkazSazmanForoshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , AmargarMarkazSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<AmargarMarkazSazmanForoshModel> getAll()
    {
        ArrayList<AmargarMarkazSazmanForoshModel> AmargarMarkazSazmanForoshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AmargarMarkazSazmanForoshModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    AmargarMarkazSazmanForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AmargarMarkazSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return AmargarMarkazSazmanForoshModels;
    }

    public List<AmargarMarkazSazmanForoshModel> getAllMarkaz()
    {
        ArrayList<AmargarMarkazSazmanForoshModel> AmargarMarkazSazmanForoshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AmargarMarkazSazmanForoshModel.TableName(), allColumns(), null, null, AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazForosh(), null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    AmargarMarkazSazmanForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AmargarMarkazSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return AmargarMarkazSazmanForoshModels;
    }

    public List<AmargarMarkazSazmanForoshModel> getAllSazmanByMarkaz(Integer selectedMarkazForosh)
    {
        ArrayList<AmargarMarkazSazmanForoshModel> AmargarMarkazSazmanForoshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + AmargarMarkazSazmanForoshModel.TableName() + " where " + AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazForosh() + " = " + selectedMarkazForosh;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    AmargarMarkazSazmanForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AmargarMarkazSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAllSazmanByMarkaz" , "");
        }
        return AmargarMarkazSazmanForoshModels;
    }

    public String getAllccMarkazForosh()
    {
        String ccMarkazForosh = "";
        try
        {
            String query = "select group_concat(" + AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazForosh() + ") from " + AmargarMarkazSazmanForoshModel.TableName();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccMarkazForosh = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AmargarMarkazSazmanForoshModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAllccMarkazForosh" , "");
        }
        return ccMarkazForosh == null ? "" : ccMarkazForosh;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AmargarMarkazSazmanForoshModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AmargarMarkazSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(AmargarMarkazSazmanForoshModel amargarMarkazSazmanForoshModel)
    {
        ContentValues contentValues = new ContentValues();

        if (amargarMarkazSazmanForoshModel.getCcAmargarMarkazSazmanForosh() > 0)
        {
            contentValues.put(AmargarMarkazSazmanForoshModel.COLUMN_ccAmargarMarkazSazmanForosh() , amargarMarkazSazmanForoshModel.getCcAmargarMarkazSazmanForosh());
        }
        contentValues.put(AmargarMarkazSazmanForoshModel.COLUMN_ccAmargar() , amargarMarkazSazmanForoshModel.getCcAmargar());
        contentValues.put(AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazForosh() , amargarMarkazSazmanForoshModel.getCcMarkazForosh());
        contentValues.put(AmargarMarkazSazmanForoshModel.COLUMN_nameMarkazForosh() , amargarMarkazSazmanForoshModel.getNameMarkazForosh());
        contentValues.put(AmargarMarkazSazmanForoshModel.COLUMN_ccSazmanForosh() , amargarMarkazSazmanForoshModel.getCcSazmanForosh());
        contentValues.put(AmargarMarkazSazmanForoshModel.COLUMN_nameSazmanForosh() , amargarMarkazSazmanForoshModel.getNameSazmanForosh());
        contentValues.put(AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazSazmanForosh() , amargarMarkazSazmanForoshModel.getCcMarkazSazmanForosh());
        contentValues.put(AmargarMarkazSazmanForoshModel.COLUMN_nameMarkazSazmanForosh() , amargarMarkazSazmanForoshModel.getNameMarkazSazmanForosh());

        return contentValues;
    }


    private ArrayList<AmargarMarkazSazmanForoshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<AmargarMarkazSazmanForoshModel> AmargarMarkazSazmanForoshModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            AmargarMarkazSazmanForoshModel amargarMarkazSazmanForoshModel = new AmargarMarkazSazmanForoshModel();

            amargarMarkazSazmanForoshModel.setCcAmargarMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(AmargarMarkazSazmanForoshModel.COLUMN_ccAmargarMarkazSazmanForosh())));
            amargarMarkazSazmanForoshModel.setCcAmargar(cursor.getInt(cursor.getColumnIndex(AmargarMarkazSazmanForoshModel.COLUMN_ccAmargar())));
            amargarMarkazSazmanForoshModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazForosh())));
            amargarMarkazSazmanForoshModel.setNameMarkazForosh(cursor.getString(cursor.getColumnIndex(AmargarMarkazSazmanForoshModel.COLUMN_nameMarkazForosh())));
            amargarMarkazSazmanForoshModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(AmargarMarkazSazmanForoshModel.COLUMN_ccSazmanForosh())));
            amargarMarkazSazmanForoshModel.setNameSazmanForosh(cursor.getString(cursor.getColumnIndex(AmargarMarkazSazmanForoshModel.COLUMN_nameSazmanForosh())));
            amargarMarkazSazmanForoshModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(AmargarMarkazSazmanForoshModel.COLUMN_ccMarkazSazmanForosh())));
            amargarMarkazSazmanForoshModel.setNameMarkazSazmanForosh(cursor.getString(cursor.getColumnIndex(AmargarMarkazSazmanForoshModel.COLUMN_nameMarkazSazmanForosh())));

            AmargarMarkazSazmanForoshModels.add(amargarMarkazSazmanForoshModel);
            cursor.moveToNext();
        }
        return AmargarMarkazSazmanForoshModels;
    }


}
