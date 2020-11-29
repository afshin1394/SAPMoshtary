package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllvForoshandehByccForoshandehResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForoshandehDAO 
{


    private DBHelper dbHelper;
    private Context context;


    public ForoshandehDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            ForoshandehModel.COLUMN_ccForoshandeh(),
            ForoshandehModel.COLUMN_FullNameForoshandeh(),
            ForoshandehModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            ForoshandehModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh(),
            ForoshandehModel.COLUMN_CodeForoshandeh(),
            ForoshandehModel.COLUMN_ccAfradForoshandeh(),
            ForoshandehModel.COLUMN_ccMarkazForosh(),
            ForoshandehModel.COLUMN_NameMarkazForosh(),
            ForoshandehModel.COLUMN_ccSazmanForosh(),
            ForoshandehModel.COLUMN_NameSazmanForosh()
        };
    }

    public void fetchForoshandeh(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvForoshandehByccForoshandehResult> call = apiService.getAllvForoshandehByccForoshandeh(ccForoshandeh);
            call.enqueue(new Callback<GetAllvForoshandehByccForoshandehResult>() {
                @Override
                public void onResponse(Call<GetAllvForoshandehByccForoshandehResult> call, Response<GetAllvForoshandehByccForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ForoshandehDAO.class.getSimpleName(), "", "fetchForoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvForoshandehByccForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvForoshandehByccForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public void fetchAllForoshandeh(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvForoshandehByccForoshandehResult> call = apiService.getAllForoshandeh();
            call.enqueue(new Callback<GetAllvForoshandehByccForoshandehResult>() {
                @Override
                public void onResponse(Call<GetAllvForoshandehByccForoshandehResult> call, Response<GetAllvForoshandehByccForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ForoshandehDAO.class.getSimpleName(), "", "fetchForoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvForoshandehByccForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvForoshandehByccForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandeh", "onFailure");
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

    public boolean insertGroup(ArrayList<ForoshandehModel> foroshandehModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ForoshandehModel foroshandehModel : foroshandehModels)
            {
                ContentValues contentValues = modelToContentvalue(foroshandehModel);
                db.insertOrThrow(ForoshandehModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<ForoshandehModel> getAll()
    {
        ArrayList<ForoshandehModel> foroshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehDAO" , "" , "getAll" , "");
        }
        return foroshandehModels;
    }

    public ArrayList<String> getDistinctccForoshandeh()
    {
        ArrayList<String> foroshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select distinct(" + ForoshandehModel.COLUMN_ccForoshandeh() + ") from " + ForoshandehModel.TableName();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        foroshandehModels.add(cursor.getString(0));
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
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehDAO" , "" , "getAll" , "");
        }
        return foroshandehModels;
    }

    public ForoshandehModel getByccForoshande(int ccForoshandeh)
    {
        ForoshandehModel foroshandehModel = new ForoshandehModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehModel.TableName(), allColumns(), ForoshandehModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehDAO" , "" , "getByccForoshande" , "");
        }
        return foroshandehModel;
    }

    public List<ForoshandehModel> getByccSazman(int ccSazmanForosh)
    {
        List<ForoshandehModel> foroshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + ForoshandehModel.TableName() + " where " + ForoshandehModel.COLUMN_ccSazmanForosh() + " = " + ccSazmanForosh;
            Log.d("ForoshandeDao" , "query : " + query);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehDAO" , "" , "getByccMarkaz" , "");
        }
        return foroshandehModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ForoshandehModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ForoshandehModel foroshandehModel)
    {
        ContentValues contentValues = new ContentValues();

        if (foroshandehModel.getCcForoshandeh() > 0)
        {
            contentValues.put(ForoshandehModel.COLUMN_ccForoshandeh() , foroshandehModel.getCcForoshandeh());
        }
        contentValues.put(ForoshandehModel.COLUMN_FullNameForoshandeh() , foroshandehModel.getFullNameForoshandeh());
        contentValues.put(ForoshandehModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , foroshandehModel.getCcMarkazSazmanForoshSakhtarForosh());
        contentValues.put(ForoshandehModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh() , foroshandehModel.getNameMarkazSazmanForoshSakhtarForosh());
        contentValues.put(ForoshandehModel.COLUMN_CodeForoshandeh() , foroshandehModel.getCodeForoshandeh());
        contentValues.put(ForoshandehModel.COLUMN_ccAfradForoshandeh() , foroshandehModel.getCcAfradForoshandeh());
        contentValues.put(ForoshandehModel.COLUMN_ccMarkazForosh() , foroshandehModel.getCcMarkazForosh());
        contentValues.put(ForoshandehModel.COLUMN_NameMarkazForosh() , foroshandehModel.getNameMarkazForosh());
        contentValues.put(ForoshandehModel.COLUMN_ccSazmanForosh() , foroshandehModel.getCcSazmanForosh());
        contentValues.put(ForoshandehModel.COLUMN_NameSazmanForosh() , foroshandehModel.getNameSazmanForosh());

        return contentValues;
    }


    private ArrayList<ForoshandehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ForoshandehModel> foroshandehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ForoshandehModel foroshandehModel = new ForoshandehModel();

            foroshandehModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(ForoshandehModel.COLUMN_ccForoshandeh())));
            foroshandehModel.setFullNameForoshandeh(cursor.getString(cursor.getColumnIndex(ForoshandehModel.COLUMN_FullNameForoshandeh())));
            foroshandehModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(ForoshandehModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));
            foroshandehModel.setNameMarkazSazmanForoshSakhtarForosh(cursor.getString(cursor.getColumnIndex(ForoshandehModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh())));
            foroshandehModel.setCodeForoshandeh(cursor.getString(cursor.getColumnIndex(ForoshandehModel.COLUMN_CodeForoshandeh())));
            foroshandehModel.setCcAfradForoshandeh(cursor.getInt(cursor.getColumnIndex(ForoshandehModel.COLUMN_ccAfradForoshandeh())));
            foroshandehModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(ForoshandehModel.COLUMN_ccMarkazForosh())));
            foroshandehModel.setNameMarkazForosh(cursor.getString(cursor.getColumnIndex(ForoshandehModel.COLUMN_NameMarkazForosh())));
            foroshandehModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(ForoshandehModel.COLUMN_ccSazmanForosh())));
            foroshandehModel.setNameSazmanForosh(cursor.getString(cursor.getColumnIndex(ForoshandehModel.COLUMN_NameSazmanForosh())));

            foroshandehModels.add(foroshandehModel);
            cursor.moveToNext();
        }
        return foroshandehModels;
    }
    
    
}
