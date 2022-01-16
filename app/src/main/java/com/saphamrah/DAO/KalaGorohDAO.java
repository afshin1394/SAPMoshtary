package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaFilterUiModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvKalaGorohResult;
import com.saphamrah.protos.GroupGoodsGrpc;
import com.saphamrah.protos.GroupGoodsReply;
import com.saphamrah.protos.GroupGoodsReplyList;
import com.saphamrah.protos.GroupGoodsRequest;
import com.saphamrah.protos.GroupGoodsStatisticianGrpc;
import com.saphamrah.protos.GroupGoodsStatisticianReply;
import com.saphamrah.protos.GroupGoodsStatisticianReplyList;
import com.saphamrah.protos.GroupGoodsStatisticianRequest;

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

public class KalaGorohDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public KalaGorohDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaGorohDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            KalaGorohModel.COLUMN_ccKalaGoroh(),
            KalaGorohModel.COLUMN_ccKalaCode(),
            KalaGorohModel.COLUMN_ccGoroh(),
            KalaGorohModel.COLUMN_NameGoroh(),
            KalaGorohModel.COLUMN_ccGorohLink(),
            KalaGorohModel.COLUMN_ccRoot()
        };
    }

    public void fetchAllvKalaGorohAmargarGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
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
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargarGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else
            {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                GroupGoodsStatisticianGrpc.GroupGoodsStatisticianBlockingStub groupGoodsBlockingStub = GroupGoodsStatisticianGrpc.newBlockingStub(managedChannel);
                GroupGoodsStatisticianRequest groupGoodsStatisticianRequest = GroupGoodsStatisticianRequest.newBuilder().build();

                Callable<GroupGoodsStatisticianReplyList> groupGoodsStatisticianReplyListCallable  = () -> groupGoodsBlockingStub.getGroupGoodsStatistician(groupGoodsStatisticianRequest);
                RxAsync.makeObservable(groupGoodsStatisticianReplyListCallable)

                        .map(groupGoodsStatisticianReplyList -> {
                            ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
                            for (GroupGoodsStatisticianReply groupGoodsStatisticianReply : groupGoodsStatisticianReplyList.getGroupGoodsStatisticianReplysList()) {
                                KalaGorohModel kalaGorohModel = new KalaGorohModel();
                                kalaGorohModel.setCcGoroh(groupGoodsStatisticianReply.getGroupID());
//                                kalaGorohModel.setCodeGoroh(groupGoodsStatisticianReply.getCodeGoodsID());
                                kalaGorohModel.setCcKalaGoroh(groupGoodsStatisticianReply.getGroupGoodsID());
                                kalaGorohModel.setNameGoroh(groupGoodsStatisticianReply.getGroupName());
                                kalaGorohModel.setCcGorohLink(groupGoodsStatisticianReply.getLinkGroupID());
                                kalaGorohModel.setCcRoot(groupGoodsStatisticianReply.getRootID());

                                kalaGorohModels.add(kalaGorohModel);
                            }

                            return kalaGorohModels;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<KalaGorohModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<KalaGorohModel> noeTablighatModels) {
                                retrofitResponse.onSuccess(noeTablighatModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargarGrpc", "CatchException");
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
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargarGrpc", "CatchException");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(),exception.getMessage());
        }

    }
    public void fetchAllvKalaGorohGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
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
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargarGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else
            {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                GroupGoodsGrpc.GroupGoodsBlockingStub groupGoodsBlockingStub = GroupGoodsGrpc.newBlockingStub(managedChannel);
                GroupGoodsRequest groupGoodsRequest = GroupGoodsRequest.newBuilder().build();

                Callable<GroupGoodsReplyList> groupGoodsReplyListCallable  = () -> groupGoodsBlockingStub.getGroupGoods(groupGoodsRequest);
                RxAsync.makeObservable(groupGoodsReplyListCallable)

                        .map(groupGoodsReplyList -> {
                            ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
                            for (GroupGoodsReply groupGoodsReply : groupGoodsReplyList.getGroupGoodssList()) {
                                KalaGorohModel kalaGorohModel = new KalaGorohModel();

                                kalaGorohModel.setCcKalaGoroh(groupGoodsReply.getGroupGoodsID());
                                kalaGorohModel.setCcKalaCode(groupGoodsReply.getGoodsCodeID());
                                kalaGorohModel.setCcGoroh(groupGoodsReply.getGroupId());
                                kalaGorohModel.setNameGoroh(groupGoodsReply.getGroupName());
                                kalaGorohModel.setCcGorohLink(groupGoodsReply.getGroupLinkID());
                                kalaGorohModel.setCcRoot(groupGoodsReply.getRootId());

                                kalaGorohModels.add(kalaGorohModel);
                            }

                            return kalaGorohModels;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<KalaGorohModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<KalaGorohModel> noeTablighatModels) {
                                retrofitResponse.onSuccess(noeTablighatModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargarGrpc", "CatchException");
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
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargarGrpc", "CatchException");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(),exception.getMessage());
        }

    }


    public void fetchAllvKalaGoroh(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvKalaGorohResult> call = apiServiceGet.getAllvKalaGoroh();
            call.enqueue(new Callback<GetAllvKalaGorohResult>() {
                @Override
                public void onResponse(Call<GetAllvKalaGorohResult> call, Response<GetAllvKalaGorohResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaGorohDAO.class.getSimpleName(), "", "fetchAllvKalaGoroh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvKalaGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvKalaGorohResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGoroh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    public void fetchAllvKalaGorohAmargar(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvKalaGorohResult> call = apiServiceGet.getAllKalaGorohAmargar();
            call.enqueue(new Callback<GetAllvKalaGorohResult>() {
                @Override
                public void onResponse(Call<GetAllvKalaGorohResult> call, Response<GetAllvKalaGorohResult> response)
                {
                    try
                    {
                        GetProgramModel.responseSize += response.raw().toString().getBytes(StandardCharsets.UTF_8).length;

                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaGorohDAO.class.getSimpleName(), "", "fetchAllvKalaGorohAmargar", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvKalaGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvKalaGorohResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllvKalaGorohAmargar", "onFailure");
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

    public boolean insertGroup(ArrayList<KalaGorohModel> kalaGorohModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaGorohModel kalaGorohModel : kalaGorohModels)
            {
                ContentValues contentValues = modelToContentvalue(kalaGorohModel);
                db.insertOrThrow(KalaGorohModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<KalaGorohModel> getAll()
    {
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaGorohModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaGorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "getAll" , "");
        }
        return kalaGorohModels;
    }

    public ArrayList<KalaGorohModel> getByccGoroh(int ccGoroh)
    {
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaGorohModel.TableName(), allColumns(), KalaGorohModel.COLUMN_ccGoroh() + " = " + ccGoroh, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaGorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "getAll" , "");
        }
        return kalaGorohModels;
    }

    public ArrayList<KalaFilterUiModel> getKalaFilter()
    {
        ArrayList<KalaFilterUiModel> kalaFilterUiModels = new ArrayList<>();
        String query = "select 0 as ccGoroh ,'همه' as NameGoroh\n" +
                "UNION ALL\n" +
                "SELECT DISTINCT ccGoroh, NameGoroh\n" +
                "FROM KalaGoroh \n" +
                "WHERE ccGorohLink=560 AND ccKalaCode IN (SELECT DISTINCT ccKalaCode FROM Kala)";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(query,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {


                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        KalaFilterUiModel model = new KalaFilterUiModel();

                        model.setCcGoroh(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccGoroh())));
                        model.setNameGoroh(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_NameGoroh())));

                        kalaFilterUiModels.add(model);
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
            String message = context.getResources().getString(R.string.errorSelectAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "getAll" , "");
        }
        return kalaFilterUiModels;
    }


    public ArrayList<KalaGorohModel> getGorohByBrand(int ccBrand)
    {
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from KalaGoroh where ccGoroh in (select ccKalaGoroh from Brand where ccBrand = " + ccBrand + ")";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaGorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "getGorohByBrand" , "");
        }
        return kalaGorohModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaGorohModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGorohDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KalaGorohModel kalaGorohModel)
    {
        ContentValues contentValues = new ContentValues();

        if (kalaGorohModel.getCcKalaGoroh() > 0)
        {
            contentValues.put(KalaGorohModel.COLUMN_ccKalaGoroh() , kalaGorohModel.getCcKalaGoroh());
        }
        contentValues.put(KalaGorohModel.COLUMN_ccKalaCode() , kalaGorohModel.getCcKalaCode());
        contentValues.put(KalaGorohModel.COLUMN_ccGoroh() , kalaGorohModel.getCcGoroh());
        contentValues.put(KalaGorohModel.COLUMN_NameGoroh() , kalaGorohModel.getNameGoroh());
        contentValues.put(KalaGorohModel.COLUMN_ccGorohLink() , kalaGorohModel.getCcGorohLink());
        contentValues.put(KalaGorohModel.COLUMN_ccRoot() , kalaGorohModel.getCcRoot());

        return contentValues;
    }


    private ArrayList<KalaGorohModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaGorohModel> kalaGorohModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaGorohModel kalaGorohModel = new KalaGorohModel();

            kalaGorohModel.setCcKalaGoroh(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccKalaGoroh())));
            kalaGorohModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccKalaCode())));
            kalaGorohModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccGoroh())));
            kalaGorohModel.setNameGoroh(cursor.getString(cursor.getColumnIndex(KalaGorohModel.COLUMN_NameGoroh())));
            kalaGorohModel.setCcGorohLink(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccGorohLink())));
            kalaGorohModel.setCcRoot(cursor.getInt(cursor.getColumnIndex(KalaGorohModel.COLUMN_ccRoot())));


            kalaGorohModels.add(kalaGorohModel);
            cursor.moveToNext();
        }
        return kalaGorohModels;
    }
    
    
}
