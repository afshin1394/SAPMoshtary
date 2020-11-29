package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TizerTablighatPegahModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllTizerTablighatResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TizerTablighatPegahDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public TizerTablighatPegahDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TizerTablighatPegahDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            TizerTablighatPegahModel.COLUMN_ccTizer(),
            TizerTablighatPegahModel.COLUMN_NameTizer(),
            TizerTablighatPegahModel.COLUMN_NameTizer_Farsi()
        };
    }

    public void fetchTizerTablighat(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TizerTablighatPegahDAO.class.getSimpleName(), activityNameForLog, "fetchTizerTablighat", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllTizerTablighatResult> call = apiService.getAllTizerTablighat();
            call.enqueue(new Callback<GetAllTizerTablighatResult>()
            {
                @Override
                public void onResponse(Call<GetAllTizerTablighatResult> call, Response<GetAllTizerTablighatResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TizerTablighatPegahDAO.class.getSimpleName(), "", "fetchTizerTablighat", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllTizerTablighatResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TizerTablighatPegahDAO.class.getSimpleName(), activityNameForLog, "fetchTizerTablighat", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TizerTablighatPegahDAO.class.getSimpleName(), activityNameForLog, "fetchTizerTablighat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TizerTablighatPegahDAO.class.getSimpleName(), activityNameForLog, "fetchTizerTablighat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TizerTablighatPegahDAO.class.getSimpleName(), activityNameForLog, "fetchTizerTablighat", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllTizerTablighatResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TizerTablighatPegahDAO.class.getSimpleName(), activityNameForLog, "fetchTizerTablighat", "onFailure");
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

    public boolean insertGroup(ArrayList<TizerTablighatPegahModel> tizerTablighatPegahModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TizerTablighatPegahModel tizerTablighatPegahModel : tizerTablighatPegahModels)
            {
                ContentValues contentValues = modelToContentvalue(tizerTablighatPegahModel);
                db.insertOrThrow(TizerTablighatPegahModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TizerTablighatPegahModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TizerTablighatPegahDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<TizerTablighatPegahModel> getAll()
    {
        ArrayList<TizerTablighatPegahModel> tizerTablighatPegahModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TizerTablighatPegahModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    tizerTablighatPegahModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TizerTablighatPegahModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TizerTablighatPegahDAO" , "" , "getAll" , "");
        }
        return tizerTablighatPegahModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TizerTablighatPegahModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , TizerTablighatPegahModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TizerTablighatPegahDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TizerTablighatPegahModel tizerTablighatPegahModel)
    {
        ContentValues contentValues = new ContentValues();

        if (tizerTablighatPegahModel.getCcTizer() > 0)
        {
            contentValues.put(TizerTablighatPegahModel.COLUMN_ccTizer() , tizerTablighatPegahModel.getCcTizer());
        }
        contentValues.put(TizerTablighatPegahModel.COLUMN_NameTizer() , tizerTablighatPegahModel.getNameTizer());
        contentValues.put(TizerTablighatPegahModel.COLUMN_NameTizer_Farsi() , tizerTablighatPegahModel.getNameTizer_Farsi());

        return contentValues;
    }


    private ArrayList<TizerTablighatPegahModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TizerTablighatPegahModel> tizerTablighatPegahModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TizerTablighatPegahModel tizerTablighatPegahModel = new TizerTablighatPegahModel();

            tizerTablighatPegahModel.setCcTizer(cursor.getInt(cursor.getColumnIndex(TizerTablighatPegahModel.COLUMN_ccTizer())));
            tizerTablighatPegahModel.setNameTizer(cursor.getString(cursor.getColumnIndex(TizerTablighatPegahModel.COLUMN_NameTizer())));
            tizerTablighatPegahModel.setNameTizer_Farsi(cursor.getString(cursor.getColumnIndex(TizerTablighatPegahModel.COLUMN_NameTizer_Farsi())));

            tizerTablighatPegahModels.add(tizerTablighatPegahModel);
            cursor.moveToNext();
        }
        return tizerTablighatPegahModels;
    }
    
}
