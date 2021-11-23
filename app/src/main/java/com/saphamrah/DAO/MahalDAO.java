package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllMahalByccMarkazForoshResult;
import com.saphamrah.protos.PlaceByCenterIDGrpc;
import com.saphamrah.protos.PlaceByCenterIDReply;
import com.saphamrah.protos.PlaceByCenterIDReplyList;
import com.saphamrah.protos.PlaceByCenterIDRequest;

import java.nio.charset.StandardCharsets;
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

public class MahalDAO
{


    private DBHelper dbHelper;
    private Context context;



    public MahalDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MahalDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MahalModel.COLUMN_ccMahal(),
            MahalModel.COLUMN_NameMahal(),
            MahalModel.COLUMN_CodeNoeMahal(),
            MahalModel.COLUMN_ccMahalLink(),
            MahalModel.COLUMN_PishShomareh()
        };
    }

    public void fetchAllMahalByccMarkazForosh(final Context context, final String activityNameForLog,final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMahalByccMarkazForoshResult> call = apiServiceGet.getAllMahalByccMarkazForosh(ccMarkazSazmanForosh);
            call.enqueue(new Callback<GetAllMahalByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllMahalByccMarkazForoshResult> call, Response<GetAllMahalByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MahalDAO.class.getSimpleName(), "", "fetchAllMahalByccMarkazForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMahalByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMahalByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForosh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public void fetchAllMahalByccMarkazForoshAmargarGrpc(final Context context, final String activityNameForLog,final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargarGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                PlaceByCenterIDGrpc.PlaceByCenterIDBlockingStub placeByCenterIDBlockingStub = PlaceByCenterIDGrpc.newBlockingStub(managedChannel);
                PlaceByCenterIDRequest placeByCenterIDRequest = PlaceByCenterIDRequest.newBuilder().setCenterID(Integer.parseInt(ccMarkazSazmanForosh)).build();

                Callable<PlaceByCenterIDReplyList> getStatisticGoodsCallable = new Callable<PlaceByCenterIDReplyList>() {
                    @Override
                    public PlaceByCenterIDReplyList call() {
                        return placeByCenterIDBlockingStub.getPlacesByCenterID(placeByCenterIDRequest);
                    }
                };
                RxAsync.makeObservable(getStatisticGoodsCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(placeByCenterIDReplyList -> {
                            ArrayList<MahalModel> mahalModels = new ArrayList<>();
                            for (PlaceByCenterIDReply placeByCenterIDReply : placeByCenterIDReplyList.getPlacesByCenterIdList()) {
                                MahalModel mahalModel = new MahalModel();
                                mahalModel.setCcMahal(placeByCenterIDReply.getPlaceID());
                                mahalModel.setCcMahalLink(placeByCenterIDReply.getLinkPlaceID());
                                mahalModel.setNameMahal(placeByCenterIDReply.getPlaceName());
                                mahalModel.setCodeNoeMahal(placeByCenterIDReply.getPlaceTypeCode());
                                mahalModel.setPishShomareh(placeByCenterIDReply.getAreaCode());
                                mahalModels.add(mahalModel);
                            }

                            return mahalModels;

                        }).subscribe(new Observer<ArrayList<MahalModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<MahalModel> mahalModels) {
                        retrofitResponse.onSuccess(mahalModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargarGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }

    public void fetchAllMahalByccMarkazForoshAmargar(final Context context, final String activityNameForLog,final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargar", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllMahalByccMarkazForoshResult> call = apiServiceGet.getAllMahalByccMarkazForoshAmargar(ccMarkazSazmanForosh);
            call.enqueue(new Callback<GetAllMahalByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllMahalByccMarkazForoshResult> call, Response<GetAllMahalByccMarkazForoshResult> response)
                {
                    try
                    {
                        GetProgramModel.responseSize += response.raw().toString().getBytes(StandardCharsets.UTF_8).length;

                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MahalDAO.class.getSimpleName(), "", "fetchAllMahalByccMarkazForoshAmargar", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMahalByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargar", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargar", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargar", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargar", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMahalByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MahalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMahalByccMarkazForoshAmargar", "onFailure");
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

    public boolean insertGroup(ArrayList<MahalModel> mahalModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MahalModel mahalModel : mahalModels)
            {
                ContentValues contentValues = modelToContentvalue(mahalModel);
                db.insertOrThrow(MahalModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MahalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<MahalModel> getAll()
    {
        ArrayList<MahalModel> mahalModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MahalModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mahalModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MahalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalDAO" , "" , "getAll" , "");
        }
        return mahalModels;
    }

    public ArrayList<MahalModel> getByCodeNoeAndccMahalLink(int codeNoeMahal , int ccMahalLink)
    {
        ArrayList<MahalModel> mahalModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MahalModel.TableName(), allColumns(),
                    MahalModel.COLUMN_CodeNoeMahal() + "=? AND " + MahalModel.COLUMN_ccMahalLink() + "=?",
                    new String[]{String.valueOf(codeNoeMahal), String.valueOf(ccMahalLink)}, null, null, null);

            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mahalModels = cursorToModel(cursor);
                    cursor.close();
                }
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MahalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalDAO" , "" , "getByCodeNoeAndccMahalLink" , "");
        }
        return mahalModels;
    }

    public MahalModel getByCodeNoeAndccMahal(int codeNoeMahal , int ccMahal)
    {
        MahalModel mahalModels = new MahalModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MahalModel.TableName(), allColumns(),
                    MahalModel.COLUMN_CodeNoeMahal() + "=? AND " + MahalModel.COLUMN_ccMahal() + "=?",
                    new String[]{String.valueOf(codeNoeMahal), String.valueOf(ccMahal)}, null, null, null);

            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mahalModels = cursorToModel(cursor).get(0);
                    cursor.close();
                }
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MahalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalDAO" , "" , "getByCodeNoeAndccMahal" , "");
        }
        return mahalModels;
    }

    public MahalModel getByParent(int ccMahal, int codeNoeMahal)
    {
        MahalModel mahalModel = new MahalModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from Mahal where ccMahal = (select ccMahalLink from Mahal where ccMahal = " + ccMahal + " and CodeNoeMahal = " + codeNoeMahal + ")";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mahalModel = cursorToModel(cursor).get(0);
                    cursor.close();
                }
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MahalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalDAO" , "" , "getByParent" , "");
        }
        return mahalModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MahalModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MahalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    private ArrayList<MahalModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MahalModel> mahalModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MahalModel mahalModel = new MahalModel();

            mahalModel.setCcMahal(cursor.getInt(cursor.getColumnIndex(MahalModel.COLUMN_ccMahal())));
            mahalModel.setNameMahal(cursor.getString(cursor.getColumnIndex(MahalModel.COLUMN_NameMahal())));
            mahalModel.setCodeNoeMahal(cursor.getInt(cursor.getColumnIndex(MahalModel.COLUMN_CodeNoeMahal())));
            mahalModel.setCcMahalLink(cursor.getInt(cursor.getColumnIndex(MahalModel.COLUMN_ccMahalLink())));
            mahalModel.setPishShomareh(cursor.getString(cursor.getColumnIndex(MahalModel.COLUMN_PishShomareh())));

            mahalModels.add(mahalModel);
            cursor.moveToNext();
        }
        return mahalModels;
    }

    private static ContentValues modelToContentvalue(MahalModel mahalModel)
    {
        ContentValues contentValues = new ContentValues();

        if (mahalModel.getCcMahal() != null && mahalModel.getCcMahal() > 0)
        {
            contentValues.put(MahalModel.COLUMN_ccMahal() , mahalModel.getCcMahal());
        }
        contentValues.put(MahalModel.COLUMN_NameMahal() , mahalModel.getNameMahal());
        contentValues.put(MahalModel.COLUMN_CodeNoeMahal() , mahalModel.getCodeNoeMahal());
        contentValues.put(MahalModel.COLUMN_ccMahalLink() , mahalModel.getCcMahalLink());
        contentValues.put(MahalModel.COLUMN_PishShomareh() , mahalModel.getPishShomareh());

        return contentValues;
    }


}
