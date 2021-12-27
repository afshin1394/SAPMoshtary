package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.MahalCodePostiModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetMahalCodePostiResult;
import com.saphamrah.protos.AllDistrictPostalCodeGrpc;
import com.saphamrah.protos.AllDistrictPostalCodeReply;
import com.saphamrah.protos.AllDistrictPostalCodeReplyList;
import com.saphamrah.protos.AllDistrictPostalCodeRequest;

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

public class MahalCodePostiDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MahalCodePostiDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MahalCodePostiDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
                {
                        MahalCodePostiModel.COLUMN_ccMahal(),
                        MahalCodePostiModel.COLUMN_ccMahalCodePosti(),
                        MahalCodePostiModel.COLUMN_ccMantagheh(),
                        MahalCodePostiModel.COLUMN_CodePosti_from(),
                        MahalCodePostiModel.COLUMN_CodePosti_to(),
                };
    }

    public void fetchMahalCodePostiGrpc(final Context context, final String activityNameForLog,final int ccMarkazForosh, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MahalCodePostiDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                AllDistrictPostalCodeGrpc.AllDistrictPostalCodeBlockingStub blockingStub = AllDistrictPostalCodeGrpc.newBlockingStub(managedChannel);
                AllDistrictPostalCodeRequest request = AllDistrictPostalCodeRequest.newBuilder().setSellCenterID(String.valueOf(ccMarkazForosh)).build();

                Callable<AllDistrictPostalCodeReplyList> replyListCallable = () -> blockingStub.getAllDistrictPostalCode(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<MahalCodePostiModel> models = new ArrayList<>();
                            for (AllDistrictPostalCodeReply reply : replyList.getAllDistrictPostalCodesList()) {
                                MahalCodePostiModel model = new MahalCodePostiModel();


                                model.setCcMahalCodePosti(reply.getPostalCodeDistrictID());
                                model.setCcMahal(reply.getDistrictID());
                                model.setCodePostiFrom(reply.getPostalCodeFrom());
                                model.setCodePostiTo(reply.getPostalCodeTo());
                                model.setCcMantagheh(reply.getAreaID());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MahalCodePostiModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MahalCodePostiModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MahalCodePostiDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }

    public void fetchMahalCodePosti(final Context context, final String activityNameForLog,final int ccMarkazForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMahalCodePostiResult> call = apiServiceGet.getMahalCodePosti(String.valueOf(ccMarkazForosh));
            call.enqueue(new Callback<GetMahalCodePostiResult>()
            {
                @Override
                public void onResponse(Call<GetMahalCodePostiResult> call, Response<GetMahalCodePostiResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, BargashtyDAO.class.getSimpleName(), "", "fetchBargashty", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMahalCodePostiResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMahalCodePostiResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    public boolean insertGroup(ArrayList<MahalCodePostiModel> mahalCodePostiModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MahalCodePostiModel mahalCodePostiModel : mahalCodePostiModels)
            {
                ContentValues contentValues = modelToContentvalue(mahalCodePostiModel);
                db.insertOrThrow(MahalCodePostiModel.TableName() , null , contentValues);
                Log.i("MahalCodePostiDao" , contentValues.toString());
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalCodePostiDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MahalCodePostiModel> getAll()
    {
        ArrayList<MahalCodePostiModel> mahalCodePostiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MahalCodePostiModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mahalCodePostiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalCodePostiDAO" , "" , "getAll" , "");
        }
        return mahalCodePostiModels;
    }

    public ArrayList<MahalCodePostiModel> getCodePostiCcmahal(int ccMahal)
    {
        ArrayList<MahalCodePostiModel> mahalCodePostiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MahalCodePostiModel.TableName(), allColumns(), "ccMahal= " + ccMahal, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mahalCodePostiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalCodePostiDAO" , "" , "getAll" , "");
        }
        return mahalCodePostiModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MahalCodePostiModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MahalCodePostiDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MahalCodePostiModel mahalCodePostiModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MahalCodePostiModel.COLUMN_ccMahal() , mahalCodePostiModel.getCcMahal());
        contentValues.put(MahalCodePostiModel.COLUMN_ccMahalCodePosti() , mahalCodePostiModel.getCcMahalCodePosti());
        contentValues.put(MahalCodePostiModel.COLUMN_ccMantagheh() , mahalCodePostiModel.getCcMantagheh());
        contentValues.put(MahalCodePostiModel.COLUMN_CodePosti_from() , mahalCodePostiModel.getCodePostiFrom());
        contentValues.put(MahalCodePostiModel.COLUMN_CodePosti_to() , mahalCodePostiModel.getCodePostiTo());

        return contentValues;
    }


    private ArrayList<MahalCodePostiModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MahalCodePostiModel> mahalCodePostiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MahalCodePostiModel mahalCodePostiModel = new MahalCodePostiModel();

            mahalCodePostiModel.setCcMahal(cursor.getInt(cursor.getColumnIndex(MahalCodePostiModel.COLUMN_ccMahal())));
            mahalCodePostiModel.setCcMahalCodePosti(cursor.getInt(cursor.getColumnIndex(MahalCodePostiModel.COLUMN_ccMahalCodePosti())));
            mahalCodePostiModel.setCcMantagheh(cursor.getInt(cursor.getColumnIndex(MahalCodePostiModel.COLUMN_ccMantagheh())));
            mahalCodePostiModel.setCodePostiFrom(cursor.getString(cursor.getColumnIndex(MahalCodePostiModel.COLUMN_CodePosti_from())));
            mahalCodePostiModel.setCodePostiTo(cursor.getString(cursor.getColumnIndex(MahalCodePostiModel.COLUMN_CodePosti_to())));

            mahalCodePostiModels.add(mahalCodePostiModel);
            cursor.moveToNext();
        }
        return mahalCodePostiModels;
    }


}
