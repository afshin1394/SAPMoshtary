package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.NoePishnahadModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.VarizBeBankModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.NoePishnahadResult;
import com.saphamrah.protos.ProposalTypeGrpc;
import com.saphamrah.protos.ProposalTypeReply;
import com.saphamrah.protos.ProposalTypeReplyList;
import com.saphamrah.protos.ProposalTypeRequest;

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

public class NoePishnahadDAO
{

    private DBHelper dbHelper;
    private Context context;
    private NoePishnahadModel modelTABLE_NAME = new NoePishnahadModel();

    public NoePishnahadDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "NoePishnahadDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
                modelTABLE_NAME.getCOLUMN_NameNoePishnahad(),
                modelTABLE_NAME.getCOLUMN_ccNoePishnahad(),
                modelTABLE_NAME.getCOLUMN_NoePishnahad()

        };
    }

    public void fetchNoePishnahadGRPC(final Context context, final String activityNameForLog , final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoePishnahadModel.class.getSimpleName(), activityNameForLog, "fetchRotbehGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                ProposalTypeGrpc.ProposalTypeBlockingStub proposalTypeBlockingStub = ProposalTypeGrpc.newBlockingStub(managedChannel);
                ProposalTypeRequest proposalTypeRequest = ProposalTypeRequest.newBuilder().build();

                Callable<ProposalTypeReplyList> proposalTypeCallable  = () -> proposalTypeBlockingStub.getProposalType(proposalTypeRequest);
                RxAsync.makeObservable(proposalTypeCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(rankReplyList -> {
                            ArrayList<NoePishnahadModel> noePishnahadModels = new ArrayList<>();
                            for (ProposalTypeReply proposalTypeReply : proposalTypeCallable.call().getProposalTypesList()) {
                                NoePishnahadModel noePishnahadModel = new NoePishnahadModel();
                                noePishnahadModel.setCcNoePishnahad(proposalTypeReply.getProposalTypeID());
                                noePishnahadModel.setNameNoePishnahad(proposalTypeReply.getProposalTypeName());
                                noePishnahadModel.setNoePishnahad(proposalTypeReply.getProposalType());

                                noePishnahadModels.add(noePishnahadModel);
                            }

                            return noePishnahadModels;

                        }).subscribe(new Observer<ArrayList<NoePishnahadModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<NoePishnahadModel> noePishnahadModels) {
                        retrofitResponse.onSuccess(noePishnahadModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), NoePishnahadModel.class.getSimpleName(), activityNameForLog, "fetchRotbehGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }




    }


    public void fetchNoePishnahad(final Context context, final String activityNameForLog , final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        //serverIpModel.setPort("8040");
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoePishnahadDAO.class.getSimpleName(), activityNameForLog, "fetchNoePishnahad", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<NoePishnahadResult> call = apiServiceGet.getNoePishnahad();
            call.enqueue(new Callback<NoePishnahadResult>()
            {
                @Override
                public void onResponse(Call<NoePishnahadResult> call, Response<NoePishnahadResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, NoePishnahadDAO.class.getSimpleName(), "", "fetchNoePishnahad", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            NoePishnahadResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), NoePishnahadDAO.class.getSimpleName(), activityNameForLog, "fetchNoePishnahad", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), NoePishnahadDAO.class.getSimpleName(), activityNameForLog, "fetchNoePishnahad", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoePishnahadDAO.class.getSimpleName(), activityNameForLog, "fetchNoePishnahad", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), NoePishnahadDAO.class.getSimpleName(), activityNameForLog, "fetchNoePishnahad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<NoePishnahadResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), NoePishnahadDAO.class.getSimpleName(), activityNameForLog, "fetchNoePishnahad", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<NoePishnahadModel> models)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (NoePishnahadModel model : models)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(NoePishnahadModel.TABLE_NAME , null , contentValues);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NazaratAndPishnahadatDAO" , "" , "insertGroup" , "");
            return false;
        }
    }



    public ArrayList<NoePishnahadModel> getAll()
    {
        ArrayList<NoePishnahadModel> models = new ArrayList<>();
        String query = "SELECT * FROM Suggest";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoePishnahadDAO" , "" , "getAll" , "");
        }
        return models;
    }

    public ArrayList<NoePishnahadModel> getNoePishnahad(int noePishnahad)
    {
        ArrayList<NoePishnahadModel> models = new ArrayList<>();
        String query = "SELECT * FROM NoePishnahad WHERE NoePishnehad = " + noePishnahad;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoePishnahadDAO" , "" , "getNoePishnahadat" , "");
        }
        return models;
    }



    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(NoePishnahadModel.TABLE_NAME, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoePishnahadatDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(NoePishnahadModel model)
    {
        ContentValues contentValues = new ContentValues();


        contentValues.put(model.getCOLUMN_ccNoePishnahad() , model.getCcNoePishnahad());
        contentValues.put(model.getCOLUMN_NameNoePishnahad() , model.getNameNoePishnahad());
        contentValues.put(model.getCOLUMN_NoePishnahad() , model.getNoePishnahad());




        return contentValues;
    }

    private ArrayList<NoePishnahadModel> cursorToModel(Cursor cursor)
    {
        ArrayList<NoePishnahadModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            NoePishnahadModel model = new NoePishnahadModel();

            model.setNameNoePishnahad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_NameNoePishnahad())));
            model.setCcNoePishnahad(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccNoePishnahad())));
            model.setNoePishnahad(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_NoePishnahad())));

            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }


}
