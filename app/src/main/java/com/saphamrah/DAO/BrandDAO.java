package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.NonNull;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.BrandModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllBrandResult;
import com.saphamrah.protos.AllBrandGrpc;
import com.saphamrah.protos.AllBrandReply;
import com.saphamrah.protos.AllBrandReplyList;
import com.saphamrah.protos.AllBrandRequest;
import com.saphamrah.protos.BankGrpc;
import com.saphamrah.protos.BankReply;
import com.saphamrah.protos.BankReplyList;
import com.saphamrah.protos.BankRequest;
import com.saphamrah.protos.BrandGrpc;
import com.saphamrah.protos.BrandReply;
import com.saphamrah.protos.BrandReplyList;
import com.saphamrah.protos.BrandRequest;

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

public class BrandDAO
{

    private DBHelper dbHelper;
    private Context context;


    public BrandDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "BrandDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            BrandModel.COLUMN_ccBrand(),
            BrandModel.COLUMN_NameBrand(),
            BrandModel.COLUMN_ccKalaGoroh()
        };
    }

    public void fetchBrandGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
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
                AllBrandGrpc.AllBrandBlockingStub allBrandBlockingStub = AllBrandGrpc.newBlockingStub(managedChannel);
                AllBrandRequest allBrandRequest = AllBrandRequest.newBuilder().build();
                Callable<AllBrandReplyList> getAllBrandCallable  = () -> allBrandBlockingStub.getAllBrand(allBrandRequest);
                RxAsync.makeObservable(getAllBrandCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(allBrandReplyList -> {
                            ArrayList<BrandModel> brandModels = new ArrayList<>();
                            for (AllBrandReply allBrandReply : allBrandReplyList.getAllbrandsList()) {
                                BrandModel brandModel = new BrandModel();

                                brandModel.setCcBrand(allBrandReply.getBrandID());
                                brandModel.setNameBrand(allBrandReply.getBrandName());

                                brandModels.add(brandModel);
                            }

                            return brandModels;

                        }).subscribe(new Observer<ArrayList<BrandModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<BrandModel> brandModels) {
                        retrofitResponse.onSuccess(brandModels);
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
    public void fetchBrand(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BrandDAO.class.getSimpleName(), activityNameForLog, "fetchBrand", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllBrandResult> call = apiServiceGet.getAllBrand();
            call.enqueue(new Callback<GetAllBrandResult>() {
                @Override
                public void onResponse(Call<GetAllBrandResult> call, Response<GetAllBrandResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, BrandDAO.class.getSimpleName(), "", "fetchBrand", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllBrandResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchBrand", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchBrand", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BrandDAO.class.getSimpleName(), activityNameForLog, "fetchBrand", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchBrand", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllBrandResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchBrand", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public void fetchAmargarBrandGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        try{

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
  //      serverIpModel.setServerIp("192.168.80.181");
        serverIpModel.setPort("5000");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BrandDAO.class.getSimpleName(), activityNameForLog, "fetchAmargarBrandGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
        }
        else {

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
            BrandGrpc.BrandBlockingStub brandBlockingStub = BrandGrpc.newBlockingStub(managedChannel);
            BrandRequest brandRequest = BrandRequest.newBuilder().build();
            Callable<BrandReplyList> brandReplyListCallable  = () -> brandBlockingStub.getBrand(brandRequest);
            RxAsync.makeObservable(brandReplyListCallable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(brandReplyList -> {
                        ArrayList<BrandModel> brandModels = new ArrayList<>();
                        for (BrandReply brandReply : brandReplyList.getBrandReplysList()) {
                            BrandModel brandModel = new BrandModel();
                            brandModel.setCcBrand(brandReply.getBrandID());
                            brandModel.setNameBrand(brandReply.getBrandName());
                            brandModel.setCcKalaGoroh(brandReply.getGoodsGroupID());
                            brandModels.add(brandModel);
                        }

                        return brandModels;

                    }).subscribe(new Observer<ArrayList<BrandModel>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(@NonNull ArrayList<BrandModel> kalaModels) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchAmargarBrandGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }




    }


    public void fetchAmargarBrand(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BrandDAO.class.getSimpleName(), activityNameForLog, "fetchAmargarBrand", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllBrandResult> call = apiServiceGet.getAllAmargarBrand();
            call.enqueue(new Callback<GetAllBrandResult>() {
                @Override
                public void onResponse(Call<GetAllBrandResult> call, Response<GetAllBrandResult> response)
                {
                    try
                    {
                        GetProgramModel.responseSize += response.raw().toString().getBytes(StandardCharsets.UTF_8).length;
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, BrandDAO.class.getSimpleName(), "", "fetchAmargarBrand", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllBrandResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchAmargarBrand", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchAmargarBrand", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BrandDAO.class.getSimpleName(), activityNameForLog, "fetchAmargarBrand", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchAmargarBrand", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllBrandResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), BrandDAO.class.getSimpleName(), activityNameForLog, "fetchAmargarBrand", "onFailure");
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

    public boolean insertGroup(ArrayList<BrandModel> brandModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (BrandModel brandModel : brandModels)
            {
                ContentValues contentValues = modelToContentvalue(brandModel);
                db.insertOrThrow(BrandModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            String message = context.getResources().getString(R.string.errorGroupInsert , BrandModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BrandDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<BrandModel> getAll()
    {
        ArrayList<BrandModel> brandModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(BrandModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    brandModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BrandModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BrandDAO" , "" , "getAll" , "");
        }
        return brandModels;
    }

    public ArrayList<BrandModel> getAllDistinct()
    {
        ArrayList<BrandModel> brandModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from Brand group by ccBrand";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    brandModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BrandModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BrandDAO" , "" , "getAllDistinct" , "");
        }
        return brandModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(BrandModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BrandModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BrandDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(BrandModel brandModel)
    {
        ContentValues contentValues = new ContentValues();

        if (brandModel.getCcBrand() != null && brandModel.getCcBrand() > 0)
        {
            contentValues.put(BrandModel.COLUMN_ccBrand() , brandModel.getCcBrand());
        }
        contentValues.put(BrandModel.COLUMN_NameBrand() , brandModel.getNameBrand());
        contentValues.put(BrandModel.COLUMN_ccKalaGoroh() , brandModel.getCcKalaGoroh());

        return contentValues;
    }


    private ArrayList<BrandModel> cursorToModel(Cursor cursor)
    {
        ArrayList<BrandModel> brandModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            BrandModel brandModel = new BrandModel();

            brandModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(BrandModel.COLUMN_ccBrand())));
            brandModel.setNameBrand(cursor.getString(cursor.getColumnIndex(BrandModel.COLUMN_NameBrand())));
            brandModel.setCcKalaGoroh(cursor.getInt(cursor.getColumnIndex(BrandModel.COLUMN_ccKalaGoroh())));

            brandModels.add(brandModel);
            cursor.moveToNext();
        }
        return brandModels;
    }

}
