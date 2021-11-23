package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.NoeTablighatModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllNoeTablighatResult;
import com.saphamrah.protos.AdsTypeGrpc;
import com.saphamrah.protos.AdsTypeReply;
import com.saphamrah.protos.AdsTypeReplyList;
import com.saphamrah.protos.AdsTypeRequest;

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

public class NoeTablighatDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "NoeTablighatDAO";

    public NoeTablighatDAO(Context context)
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
            NoeTablighatModel.COLUMN_ccNoeTablighat(),
            NoeTablighatModel.COLUMN_NameNoeTablighat()
        };
    }
    public void fetchNoeTablighatGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeTablighatDAO.class.getSimpleName(), activityNameForLog, "fetchNoeTablighatGrpc", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
            AdsTypeGrpc.AdsTypeBlockingStub adsTypeBlockingStub = AdsTypeGrpc.newBlockingStub(managedChannel);
            AdsTypeRequest adsTypeRequest = AdsTypeRequest.newBuilder().build();

            Callable<AdsTypeReplyList> adsTypeReplyListCallable  = () -> adsTypeBlockingStub.getAdsType(adsTypeRequest);
            RxAsync.makeObservable(adsTypeReplyListCallable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(adsTypeReplyList -> {
                        ArrayList<NoeTablighatModel> noeTablighatModels = new ArrayList<>();
                        for (AdsTypeReply adsTypeReply : adsTypeReplyList.getAdsTypesList()) {
                            NoeTablighatModel noeTablighatModel = new NoeTablighatModel();
                            noeTablighatModel.setCcNoeTablighat(adsTypeReply.getAdsTypeID());
                            noeTablighatModel.setNameNoeTablighat(adsTypeReply.getAdsTypeName());

                            noeTablighatModels.add(noeTablighatModel);
                        }

                        return noeTablighatModels;

                    }).subscribe(new Observer<ArrayList<NoeTablighatModel>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(@NonNull ArrayList<NoeTablighatModel> noeTablighatModels) {
                    retrofitResponse.onSuccess(noeTablighatModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), NoeTablighatDAO.class.getSimpleName(), activityNameForLog, "fetchNoeTablighatGrpc", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , exception.getMessage());
        }

    }


    public void fetchNoeTablighat(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchNoeTablighat", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllNoeTablighatResult> call = apiServiceGet.getAllNoeTablighat();
            call.enqueue(new Callback<GetAllNoeTablighatResult>() {
                @Override
                public void onResponse(Call<GetAllNoeTablighatResult> call, Response<GetAllNoeTablighatResult> response)
                {
                    try
                    {
                        GetProgramModel.responseSize += response.raw().toString().getBytes(StandardCharsets.UTF_8).length;

                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchNoeTablighat", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllNoeTablighatResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchNoeTablighat", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchNoeTablighat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchNoeTablighat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchNoeTablighat", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllNoeTablighatResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchNoeTablighat", "onFailure");
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

    public boolean insertGroup(ArrayList<NoeTablighatModel> noeTablighatModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (NoeTablighatModel noeTablighatModel : noeTablighatModels)
            {
                ContentValues contentValues = modelToContentvalue(noeTablighatModel);
                db.insertOrThrow(NoeTablighatModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , NoeTablighatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<NoeTablighatModel> getAll()
    {
        ArrayList<NoeTablighatModel> noeTablighatModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(NoeTablighatModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    noeTablighatModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeTablighatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return noeTablighatModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(NoeTablighatModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , NoeTablighatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(NoeTablighatModel noeTablighatModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(NoeTablighatModel.COLUMN_ccNoeTablighat() , noeTablighatModel.getCcNoeTablighat());
        contentValues.put(NoeTablighatModel.COLUMN_NameNoeTablighat() , noeTablighatModel.getNameNoeTablighat());

        return contentValues;
    }


    private ArrayList<NoeTablighatModel> cursorToModel(Cursor cursor)
    {
        ArrayList<NoeTablighatModel> noeTablighatModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            NoeTablighatModel noeTablighatModel = new NoeTablighatModel();

            noeTablighatModel.setCcNoeTablighat(cursor.getInt(cursor.getColumnIndex(NoeTablighatModel.COLUMN_ccNoeTablighat())));
            noeTablighatModel.setNameNoeTablighat(cursor.getString(cursor.getColumnIndex(NoeTablighatModel.COLUMN_NameNoeTablighat())));

            noeTablighatModels.add(noeTablighatModel);
            cursor.moveToNext();
        }
        return noeTablighatModels;
    }

}
