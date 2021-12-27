package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.PorseshnamehTozihatModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetPorseshnamehTozihatResult;
import com.saphamrah.protos.DescriptionQuestionnaireGrpc;
import com.saphamrah.protos.DescriptionQuestionnaireReply;
import com.saphamrah.protos.DescriptionQuestionnaireReplyList;
import com.saphamrah.protos.DescriptionQuestionnaireRequest;

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

public class PorseshnamehTozihatDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "PorseshnamehTozihatDAO";


    public PorseshnamehTozihatDAO(Context context)
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
            PorseshnamehTozihatModel.COLUMN_ccPorseshnamehTozihat(),
            PorseshnamehTozihatModel.COLUMN_Sharh()
        };
    }


    public void fetchPorseshnamehTozihatGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
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
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaDAO.class.getSimpleName(), activityNameForLog, "fetchPorseshnamehTozihatGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else {
                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                DescriptionQuestionnaireGrpc.DescriptionQuestionnaireBlockingStub descriptionQuestionnaireBlockingStub = DescriptionQuestionnaireGrpc.newBlockingStub(managedChannel);
                DescriptionQuestionnaireRequest descriptionQuestionnaireRequest = DescriptionQuestionnaireRequest.newBuilder().build();

                Callable<DescriptionQuestionnaireReplyList> descriptionQuestionnaireReplyListCallable  = () -> descriptionQuestionnaireBlockingStub.getDiscriptionQuestionnaire(descriptionQuestionnaireRequest);
                RxAsync.makeObservable(descriptionQuestionnaireReplyListCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(descriptionQuestionnaireReplyList -> {
                            ArrayList<PorseshnamehTozihatModel> porseshnamehTozihatModels = new ArrayList<>();
                            for (DescriptionQuestionnaireReply descriptionQuestionnaireReply : descriptionQuestionnaireReplyList.getDescriptionQuestionnairesList()) {
                                PorseshnamehTozihatModel porseshnamehTozihatModel = new PorseshnamehTozihatModel();
                                porseshnamehTozihatModel.setCcPorseshnamehTozihat(descriptionQuestionnaireReply.getDescriptionQuestionnaireID());
                                porseshnamehTozihatModel.setSharh(descriptionQuestionnaireReply.getDescription());
                                porseshnamehTozihatModels.add(porseshnamehTozihatModel);
                            }

                            return porseshnamehTozihatModels;

                        }).subscribe(new Observer<ArrayList<PorseshnamehTozihatModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<PorseshnamehTozihatModel> rotbehModels) {
                        retrofitResponse.onSuccess(rotbehModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(),e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (!managedChannel.isShutdown()){
                            managedChannel.shutdown();
                        }
                        if (!compositeDisposable.isDisposed()) {
                            compositeDisposable.dispose();
                        }
                        compositeDisposable.clear();
                    }
                });

            }
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchPorseshnamehTozihatGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , exception.getMessage());
        }
    }

    public void fetchPorseshnamehTozihat(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchPorseshnamehTozihat", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetPorseshnamehTozihatResult> call = apiServiceGet.getPorseshnamehTozihat();
            call.enqueue(new Callback<GetPorseshnamehTozihatResult>() {
                @Override
                public void onResponse(Call<GetPorseshnamehTozihatResult> call, Response<GetPorseshnamehTozihatResult> response)
                {
                    try
                    {
                        GetProgramModel.responseSize += response.raw().toString().getBytes(StandardCharsets.UTF_8).length;

                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchPorseshnamehTozihat", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetPorseshnamehTozihatResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchPorseshnamehTozihat", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchPorseshnamehTozihat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchPorseshnamehTozihat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchPorseshnamehTozihat", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetPorseshnamehTozihatResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchPorseshnamehTozihat", "onFailure");
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

    public boolean insertGroup(ArrayList<PorseshnamehTozihatModel> porseshnamehTozihatModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (PorseshnamehTozihatModel model : porseshnamehTozihatModels)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(PorseshnamehTozihatModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , PorseshnamehTozihatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<PorseshnamehTozihatModel> getAll()
    {
        ArrayList<PorseshnamehTozihatModel> porseshnamehTozihatModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PorseshnamehTozihatModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    porseshnamehTozihatModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehTozihatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return porseshnamehTozihatModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PorseshnamehTozihatModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehTozihatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(PorseshnamehTozihatModel porseshnamehTozihatModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PorseshnamehTozihatModel.COLUMN_ccPorseshnamehTozihat() , porseshnamehTozihatModel.getCcPorseshnamehTozihat());
        contentValues.put(PorseshnamehTozihatModel.COLUMN_Sharh() , porseshnamehTozihatModel.getSharh());

        return contentValues;
    }


    private ArrayList<PorseshnamehTozihatModel> cursorToModel(Cursor cursor)
    {
        ArrayList<PorseshnamehTozihatModel> porseshnamehTozihatModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            PorseshnamehTozihatModel porseshnamehTozihatModel = new PorseshnamehTozihatModel();

            porseshnamehTozihatModel.setCcPorseshnamehTozihat(cursor.getInt(cursor.getColumnIndex(PorseshnamehTozihatModel.COLUMN_ccPorseshnamehTozihat())));
            porseshnamehTozihatModel.setSharh(cursor.getString(cursor.getColumnIndex(PorseshnamehTozihatModel.COLUMN_Sharh())));

            porseshnamehTozihatModels.add(porseshnamehTozihatModel);
            cursor.moveToNext();
        }
        return porseshnamehTozihatModels;
    }


}
