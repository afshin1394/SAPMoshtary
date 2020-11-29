package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.saphamrah.Model.NoeMalekiatMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllNoeMalekiatMoshtaryResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoeMalekiatMoshtaryDAO
{

    private DBHelper dbHelper;
    private Context context;


    public NoeMalekiatMoshtaryDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "NoeMalekiatMoshtaryDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            NoeMalekiatMoshtaryModel.COLUMN_ccNoeMalekiatMoshtary(),
            NoeMalekiatMoshtaryModel.COLUMN_NameNoeMalekiatMoshtary()
        };
    }

    public void fetchNoeMalekiatMoshtary(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeMalekiatMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeMalekiatMoshtary", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllNoeMalekiatMoshtaryResult> call = apiService.getAllNoeMalekiatMoshtary();
            call.enqueue(new Callback<GetAllNoeMalekiatMoshtaryResult>() {
                @Override
                public void onResponse(Call<GetAllNoeMalekiatMoshtaryResult> call, Response<GetAllNoeMalekiatMoshtaryResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, NoeMalekiatMoshtaryDAO.class.getSimpleName(), "", "fetchNoeMalekiatMoshtary", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllNoeMalekiatMoshtaryResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), NoeMalekiatMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeMalekiatMoshtary", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), NoeMalekiatMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeMalekiatMoshtary", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeMalekiatMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeMalekiatMoshtary", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), NoeMalekiatMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeMalekiatMoshtary", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllNoeMalekiatMoshtaryResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), NoeMalekiatMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeMalekiatMoshtary", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<NoeMalekiatMoshtaryModel> noeMalekiatMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (NoeMalekiatMoshtaryModel noeMalekiatMoshtaryModel : noeMalekiatMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(noeMalekiatMoshtaryModel);
                db.insertOrThrow(NoeMalekiatMoshtaryModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , NoeMalekiatMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeMalekiatMoshtaryDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<NoeMalekiatMoshtaryModel> getAll()
    {
        ArrayList<NoeMalekiatMoshtaryModel> noeMalekiatMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(NoeMalekiatMoshtaryModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    noeMalekiatMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeMalekiatMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeMalekiatMoshtaryDAO" , "" , "getAll" , "");
        }
        return noeMalekiatMoshtaryModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(NoeMalekiatMoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , NoeMalekiatMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeMalekiatMoshtaryDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(NoeMalekiatMoshtaryModel noeMalekiatMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();

        if (noeMalekiatMoshtaryModel.getCcNoeMalekiatMoshtary() > 0)
        {
            contentValues.put(NoeMalekiatMoshtaryModel.COLUMN_ccNoeMalekiatMoshtary() , noeMalekiatMoshtaryModel.getCcNoeMalekiatMoshtary());
        }
        contentValues.put(NoeMalekiatMoshtaryModel.COLUMN_NameNoeMalekiatMoshtary() , noeMalekiatMoshtaryModel.getNameNoeMalekiatMoshtary());

        return contentValues;
    }


    private ArrayList<NoeMalekiatMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<NoeMalekiatMoshtaryModel> noeMalekiatMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            NoeMalekiatMoshtaryModel noeMalekiatMoshtaryModel = new NoeMalekiatMoshtaryModel();

            noeMalekiatMoshtaryModel.setCcNoeMalekiatMoshtary(cursor.getInt(cursor.getColumnIndex(NoeMalekiatMoshtaryModel.COLUMN_ccNoeMalekiatMoshtary())));
            noeMalekiatMoshtaryModel.setNameNoeMalekiatMoshtary(cursor.getString(cursor.getColumnIndex(NoeMalekiatMoshtaryModel.COLUMN_NameNoeMalekiatMoshtary())));

            noeMalekiatMoshtaryModels.add(noeMalekiatMoshtaryModel);
            cursor.moveToNext();
        }
        return noeMalekiatMoshtaryModels;
    }


    
}
