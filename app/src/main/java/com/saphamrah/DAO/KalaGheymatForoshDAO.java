package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.KalaGheymatForoshModel;
import com.saphamrah.Model.NoePishnahadModel;
import com.saphamrah.Model.ServerIpModel;

import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.KalaGheymatForoshResult;
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

public class KalaGheymatForoshDAO
{

    private DBHelper dbHelper;
    private Context context;
    private KalaGheymatForoshModel modelTABLE_NAME = new KalaGheymatForoshModel();

    public KalaGheymatForoshDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaGheymatForoshDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
                modelTABLE_NAME.COLUMN_ccKalaGheymatForosh,
                modelTABLE_NAME.COLUMN_ccKalaCode,
                modelTABLE_NAME.COLUMN_ccMarkazForosh,
                modelTABLE_NAME.COLUMN_ccSazmanForosh,
                modelTABLE_NAME.COLUMN_ccNoeMoshtary,
                modelTABLE_NAME.COLUMN_ccNoeSenf,
                modelTABLE_NAME.COLUMN_ccDarajeh,
                modelTABLE_NAME.COLUMN_ZaribAfzayeshGheymat
        };
    }




    public void fetchNoeKalaGheymatForosh( Context context,String ccMarkazForosh , String ccSazmanForosh ,  String activityNameForLog ,  RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGheymatForoshDAO.class.getSimpleName(), activityNameForLog, "fetchNoeKalaGheymatForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<KalaGheymatForoshResult> call = apiServiceGet.getKalaGheymatForosh(String.valueOf(ccMarkazForosh) , String.valueOf(ccSazmanForosh));
            call.enqueue(new Callback<KalaGheymatForoshResult>()
            {
                @Override
                public void onResponse(Call<KalaGheymatForoshResult> call, Response<KalaGheymatForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaGheymatForoshDAO.class.getSimpleName(), "", "KalaGheymatForoshDAO", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            KalaGheymatForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaGheymatForoshDAO.class.getSimpleName(), activityNameForLog, "KalaGheymatForoshDAO", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), KalaGheymatForoshDAO.class.getSimpleName(), activityNameForLog, "KalaGheymatForoshDAO", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaGheymatForoshDAO.class.getSimpleName(), activityNameForLog, "KalaGheymatForoshDAO", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaGheymatForoshDAO.class.getSimpleName(), activityNameForLog, "KalaGheymatForoshDAO", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<KalaGheymatForoshResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaGheymatForoshDAO.class.getSimpleName(), activityNameForLog, "KalaGheymatForoshDAO", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<KalaGheymatForoshModel> models)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaGheymatForoshModel model : models)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(modelTABLE_NAME.TABLE_NAME , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , modelTABLE_NAME.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGheymatForoshDAO" , "" , "insertGroup" , "");
            return false;
        }
    }



    public ArrayList<KalaGheymatForoshModel> getAll()
    {
        ArrayList<KalaGheymatForoshModel> models = new ArrayList<>();
        String query = "SELECT * FROM KalaGheymatForosh";
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
            String message = context.getResources().getString(R.string.errorSelectAll , modelTABLE_NAME.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGheymatForoshDAO" , "" , "getAll" , "");
        }
        return models;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(modelTABLE_NAME.TABLE_NAME, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , modelTABLE_NAME.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaGheymatForoshDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KalaGheymatForoshModel model)
    {
        ContentValues contentValues = new ContentValues();


        contentValues.put(model.COLUMN_ccKalaGheymatForosh , model.getCcKalaGheymatForosh());
        contentValues.put(model.COLUMN_ccKalaCode , model.getCcKalaCode());
        contentValues.put(model.COLUMN_ccMarkazForosh , model.getCcMarkazForosh());
        contentValues.put(model.COLUMN_ccSazmanForosh , model.getCcSazmanForosh());
        contentValues.put(model.COLUMN_ccNoeMoshtary , model.getCcNoeMoshtary());
        contentValues.put(model.COLUMN_ccNoeSenf , model.getCcNoeSenf());
        contentValues.put(model.COLUMN_ccDarajeh , model.getCcDarajeh());
        contentValues.put(model.COLUMN_ZaribAfzayeshGheymat , model.getZaribAfzayeshGheymat());


        return contentValues;
    }

    private ArrayList<KalaGheymatForoshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaGheymatForoshModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaGheymatForoshModel model = new KalaGheymatForoshModel();

            model.setCcKalaGheymatForosh(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.COLUMN_ccKalaGheymatForosh)));
            model.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.COLUMN_ccKalaCode)));
            model.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.COLUMN_ccMarkazForosh)));
            model.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.COLUMN_ccSazmanForosh)));
            model.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.COLUMN_ccNoeMoshtary)));
            model.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.COLUMN_ccNoeSenf)));
            model.setCcDarajeh(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.COLUMN_ccDarajeh)));
            model.setZaribAfzayeshGheymat(cursor.getDouble(cursor.getColumnIndex(modelTABLE_NAME.COLUMN_ZaribAfzayeshGheymat)));


            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }


}
