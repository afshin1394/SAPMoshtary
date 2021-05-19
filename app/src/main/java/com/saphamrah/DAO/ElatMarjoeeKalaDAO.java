package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllElatMarjoeeKalaResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElatMarjoeeKalaDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ElatMarjoeeKalaDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ElatMarjoeeKalaDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            ElatMarjoeeKalaModel.COLUMN_ccElatMarjoeeKala(),
            ElatMarjoeeKalaModel.COLUMN_Sharh(),
            ElatMarjoeeKalaModel.COLUMN_CodeNoeElat(),
            ElatMarjoeeKalaModel.COLUMN_IsZayeat(),
                ElatMarjoeeKalaModel.COLUMN_GetImage()
        };
    }

    public void fetchElatMarjoeeKala(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElatMarjoeeKalaDAO.class.getSimpleName(), activityNameForLog, "fetchElatMarjoeeKala", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllElatMarjoeeKalaResult> call = apiServiceGet.getElatMarjoeeKala();
            call.enqueue(new Callback<GetAllElatMarjoeeKalaResult>() {
                @Override
                public void onResponse(Call<GetAllElatMarjoeeKalaResult> call, Response<GetAllElatMarjoeeKalaResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ElatMarjoeeKalaDAO.class.getSimpleName(), "", "fetchElatMarjoeeKala", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllElatMarjoeeKalaResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ElatMarjoeeKalaDAO.class.getSimpleName(), activityNameForLog, "fetchElatMarjoeeKala", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s , %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), ElatMarjoeeKalaDAO.class.getSimpleName(), activityNameForLog, "fetchElatMarjoeeKala", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ElatMarjoeeKalaDAO.class.getSimpleName(), activityNameForLog, "fetchElatMarjoeeKala", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ElatMarjoeeKalaDAO.class.getSimpleName(), activityNameForLog, "fetchElatMarjoeeKala", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllElatMarjoeeKalaResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ElatMarjoeeKalaDAO.class.getSimpleName(), activityNameForLog, "fetchElatMarjoeeKala", "onFailure");
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

    public boolean insertGroup(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ElatMarjoeeKalaModel elatMarjoeeKalaModel : elatMarjoeeKalaModels)
            {
                ContentValues contentValues = modelToContentvalue(elatMarjoeeKalaModel);
                db.insertOrThrow(ElatMarjoeeKalaModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ElatMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatMarjoeeKalaDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<ElatMarjoeeKalaModel> getAll()
    {
        ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElatMarjoeeKalaModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elatMarjoeeKalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElatMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatMarjoeeKalaDAO" , "" , "getAll" , "");
        }
        return elatMarjoeeKalaModels;
    }

    public ArrayList<ElatMarjoeeKalaModel> getElatMarjoeePakhsh() {
        ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels = new ArrayList<>();

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElatMarjoeeKalaModel.TableName(), allColumns(), ElatMarjoeeKalaModel.COLUMN_CodeNoeElat() + "=" + 1, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    elatMarjoeeKalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, ElatMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatMarjoeeKalaDAO", "", "getElatMarjoee", "");
        }
        return elatMarjoeeKalaModels;
    }

    public ArrayList<ElatMarjoeeKalaModel> getElatMarjoeeForosh() {
        ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels = new ArrayList<>();

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElatMarjoeeKalaModel.TableName(), allColumns(), ElatMarjoeeKalaModel.COLUMN_CodeNoeElat() + "=" + 2, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    elatMarjoeeKalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, ElatMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatMarjoeeKalaDAO", "", "getElatMarjoee", "");
        }
        return elatMarjoeeKalaModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElatMarjoeeKalaModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElatMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElatMarjoeeKalaDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private ArrayList<ElatMarjoeeKalaModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ElatMarjoeeKalaModel elatMarjoeeKalaModel = new ElatMarjoeeKalaModel();

            elatMarjoeeKalaModel.setCcElatMarjoeeKala(cursor.getInt(cursor.getColumnIndex(ElatMarjoeeKalaModel.COLUMN_ccElatMarjoeeKala())));
            elatMarjoeeKalaModel.setSharh(cursor.getString(cursor.getColumnIndex(ElatMarjoeeKalaModel.COLUMN_Sharh())));
            elatMarjoeeKalaModel.setCodeNoeElat(cursor.getInt(cursor.getColumnIndex(ElatMarjoeeKalaModel.COLUMN_CodeNoeElat())));
            elatMarjoeeKalaModel.setIsZayeat(cursor.getInt(cursor.getColumnIndex(ElatMarjoeeKalaModel.COLUMN_IsZayeat())));
            elatMarjoeeKalaModel.setGetImage(cursor.getInt(cursor.getColumnIndex(ElatMarjoeeKalaModel.COLUMN_GetImage())));

            elatMarjoeeKalaModels.add(elatMarjoeeKalaModel);
            cursor.moveToNext();
        }
        return elatMarjoeeKalaModels;
    }

    private static ContentValues modelToContentvalue(ElatMarjoeeKalaModel elatMarjoeeKalaModel)
    {
        ContentValues contentValues = new ContentValues();

        if (elatMarjoeeKalaModel.getCcElatMarjoeeKala() != null && elatMarjoeeKalaModel.getCcElatMarjoeeKala() > 0)
        {
            contentValues.put(ElatMarjoeeKalaModel.COLUMN_ccElatMarjoeeKala() , elatMarjoeeKalaModel.getCcElatMarjoeeKala());
        }
        contentValues.put(ElatMarjoeeKalaModel.COLUMN_Sharh() , elatMarjoeeKalaModel.getSharh());
        contentValues.put(ElatMarjoeeKalaModel.COLUMN_CodeNoeElat() , elatMarjoeeKalaModel.getCodeNoeElat());
        contentValues.put(ElatMarjoeeKalaModel.COLUMN_IsZayeat() , elatMarjoeeKalaModel.getIsZayeat());
        contentValues.put(ElatMarjoeeKalaModel.COLUMN_GetImage() , elatMarjoeeKalaModel.getGetImage());

        return contentValues;
    }

    
}
