package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.AmargarGorohModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllAmargarGorohResult;
import com.saphamrah.protos.GroupStatisticianGrpc;
import com.saphamrah.protos.GroupStatisticianReply;
import com.saphamrah.protos.GroupStatisticianReplyList;
import com.saphamrah.protos.GroupStatisticianRequest;

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

public class AmargarGorohDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "AmargarGorohDAO";


    public AmargarGorohDAO(Context context)
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
            AmargarGorohModel.COLUMN_ccAmargarGoroh(),
            AmargarGorohModel.COLUMN_CodeAmargarGoroh(),
            AmargarGorohModel.COLUMN_SharhAmargarGoroh(),
            AmargarGorohModel.COLUMN_CodeVazeiat()
        };
    }

    public void fetchAmrgarGorohGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        try {


        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
 //       serverIpModel.setServerIp("192.168.80.181");
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
            GroupStatisticianGrpc.GroupStatisticianBlockingStub statisticGoodsBlockingStub = GroupStatisticianGrpc.newBlockingStub(managedChannel);
            GroupStatisticianRequest groupStatisticianRequest = GroupStatisticianRequest.newBuilder().build();
            Callable<GroupStatisticianReplyList> getGroupStatisticianCallable  = () -> statisticGoodsBlockingStub.getGroupStatistician(groupStatisticianRequest);
            RxAsync.makeObservable(getGroupStatisticianCallable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(statisticGoodsReplyList -> {
                        ArrayList<AmargarGorohModel> amargarGorohModels = new ArrayList<>();
                        for (GroupStatisticianReply groupStatisticianReply : statisticGoodsReplyList.getGroupStatisticianReplysList()) {
                            AmargarGorohModel amargarGorohModel = new AmargarGorohModel();
                            amargarGorohModel.setCcAmargarGoroh(groupStatisticianReply.getGroupStatisticianID());
                            amargarGorohModel.setCodeAmargarGoroh(groupStatisticianReply.getGroupStatisticianCode());
                            amargarGorohModel.setSharhAmargarGoroh(groupStatisticianReply.getGroupStatisticianDescription());
                            amargarGorohModel.setCodeVazeiat(groupStatisticianReply.getStatusCode());

                            amargarGorohModels.add(amargarGorohModel);
                        }

                        return amargarGorohModels;

                    }).subscribe(new Observer<ArrayList<AmargarGorohModel>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(@NonNull ArrayList<AmargarGorohModel> kalaModels) {
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


    public void fetchamrgarGoroh(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchamrgarGoroh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllAmargarGorohResult> call = apiServiceGet.getAllAmargarGoroh();
            call.enqueue(new Callback<GetAllAmargarGorohResult>() {
                @Override
                public void onResponse(Call<GetAllAmargarGorohResult> call, Response<GetAllAmargarGorohResult> response)
                {
                    try
                    {
                        GetProgramModel.responseSize += response.raw().toString().getBytes(StandardCharsets.UTF_8).length;

                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchamrgarGoroh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllAmargarGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchamrgarGoroh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchamrgarGoroh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchamrgarGoroh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchamrgarGoroh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllAmargarGorohResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchamrgarGoroh", "onFailure");
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

    public boolean insertGroup(ArrayList<AmargarGorohModel> amargarGorohModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (AmargarGorohModel amargarGorohModel : amargarGorohModels)
            {
                ContentValues contentValues = modelToContentvalue(amargarGorohModel);
                db.insertOrThrow(AmargarGorohModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , AmargarGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<AmargarGorohModel> getAll()
    {
        ArrayList<AmargarGorohModel> amargarGorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AmargarGorohModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    amargarGorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AmargarGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return amargarGorohModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AmargarGorohModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AmargarGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(AmargarGorohModel amargarGorohModel)
    {
        ContentValues contentValues = new ContentValues();

        if (amargarGorohModel.getCcAmargarGoroh() > 0)
        {
            contentValues.put(AmargarGorohModel.COLUMN_ccAmargarGoroh() , amargarGorohModel.getCcAmargarGoroh());
        }
        contentValues.put(AmargarGorohModel.COLUMN_CodeAmargarGoroh() , amargarGorohModel.getCodeAmargarGoroh());
        contentValues.put(AmargarGorohModel.COLUMN_SharhAmargarGoroh() , amargarGorohModel.getSharhAmargarGoroh());
        contentValues.put(AmargarGorohModel.COLUMN_CodeVazeiat() , amargarGorohModel.getCodeVazeiat());

        return contentValues;
    }


    private ArrayList<AmargarGorohModel> cursorToModel(Cursor cursor)
    {
        ArrayList<AmargarGorohModel> amargarGorohModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            AmargarGorohModel amargarGorohModel = new AmargarGorohModel();

            amargarGorohModel.setCcAmargarGoroh(cursor.getInt(cursor.getColumnIndex(AmargarGorohModel.COLUMN_ccAmargarGoroh())));
            amargarGorohModel.setCodeAmargarGoroh(cursor.getString(cursor.getColumnIndex(AmargarGorohModel.COLUMN_CodeAmargarGoroh())));
            amargarGorohModel.setSharhAmargarGoroh(cursor.getString(cursor.getColumnIndex(AmargarGorohModel.COLUMN_SharhAmargarGoroh())));
            amargarGorohModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(AmargarGorohModel.COLUMN_CodeVazeiat())));

            amargarGorohModels.add(amargarGorohModel);
            cursor.moveToNext();
        }

        return amargarGorohModels;
    }

}
