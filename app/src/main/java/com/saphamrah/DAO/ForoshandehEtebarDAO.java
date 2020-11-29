package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ForoshandehEtebarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetEtebarForoshandehResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForoshandehEtebarDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "ForoshandehEtebarDAO";


    public ForoshandehEtebarDAO(Context context)
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
            ForoshandehEtebarModel.COLUMN_ccForoshandeh(),
            ForoshandehEtebarModel.COLUMN_SaghfEtebarRiali(),
            ForoshandehEtebarModel.COLUMN_SaghfEtebarAsnad(),
            ForoshandehEtebarModel.COLUMN_SaghfEtebarTedadi(),
            ForoshandehEtebarModel.COLUMN_SaghfEtebarModat(),
            ForoshandehEtebarModel.COLUMN_EtebarRialAsnadShakhsi(),
            ForoshandehEtebarModel.COLUMN_EtebarTedadAsnadShakhsi(),
            ForoshandehEtebarModel.COLUMN_EtebarModatAsnadShakhsi(),
            ForoshandehEtebarModel.COLUMN_EtebarRialAsnadMoshtary(),
            ForoshandehEtebarModel.COLUMN_EtebarTedadAsnadMoshtary(),
            ForoshandehEtebarModel.COLUMN_EtebarModatAsnadMoshtary(),
            ForoshandehEtebarModel.COLUMN_EtebarRialMoavagh(),
            ForoshandehEtebarModel.COLUMN_EtebarTedadMoavagh(),
            ForoshandehEtebarModel.COLUMN_EtebarModatMoavagh(),
            ForoshandehEtebarModel.COLUMN_EtebarRialBargashty(),
            ForoshandehEtebarModel.COLUMN_EtebarTedadBargashty(),
            ForoshandehEtebarModel.COLUMN_EtebarModatBargashty(),
            ForoshandehEtebarModel.COLUMN_ModatVosol(),
            ForoshandehEtebarModel.COLUMN_RialAsnad(),
            ForoshandehEtebarModel.COLUMN_TedadAsnad(),
            ForoshandehEtebarModel.COLUMN_ModatAsnad(),
            ForoshandehEtebarModel.COLUMN_RialMoavagh(),
            ForoshandehEtebarModel.COLUMN_TedadMoavagh(),
            ForoshandehEtebarModel.COLUMN_ModatMoavagh(),
            ForoshandehEtebarModel.COLUMN_RialBargashty(),
            ForoshandehEtebarModel.COLUMN_TedadBargashty(),
            ForoshandehEtebarModel.COLUMN_ModatBargashty()
        };
    }

    public void fetchEtebarForoshandeh(final Context context, final String activityNameForLog, final String ccForoshandehs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchEtebarForoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetEtebarForoshandehResult> call = apiService.getEtebarForoshandeh(ccForoshandehs);
            call.enqueue(new Callback<GetEtebarForoshandehResult>() {
                @Override
                public void onResponse(Call<GetEtebarForoshandehResult> call, Response<GetEtebarForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchEtebarForoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetEtebarForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchEtebarForoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchEtebarForoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchEtebarForoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchEtebarForoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetEtebarForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchEtebarForoshandeh", "onFailure");
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

    public boolean insertGroup(ArrayList<ForoshandehEtebarModel> foroshandehEtebarModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ForoshandehEtebarModel foroshandehEtebarModel : foroshandehEtebarModels)
            {
                ContentValues contentValues = modelToContentvalue(foroshandehEtebarModel);
                db.insertOrThrow(ForoshandehEtebarModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ForoshandehEtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ForoshandehEtebarModel> getAll()
    {
        ArrayList<ForoshandehEtebarModel> etebarModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehEtebarModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    etebarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehEtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return etebarModels;
    }


    public ForoshandehEtebarModel getByccForoshandeh(int ccForoshandeh)
    {
        ForoshandehEtebarModel foroshandehEtebarModel = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + ForoshandehEtebarModel.TableName() + " where " + ForoshandehEtebarModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehEtebarModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehEtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getByccForoshandeh" , "");
        }
        return foroshandehEtebarModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ForoshandehEtebarModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ForoshandehEtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccForoshanhde(int ccForoshandeh)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ForoshandehEtebarModel.TableName(), ForoshandehEtebarModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ForoshandehEtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarDAO" , "" , "deleteByccForoshanhde" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ForoshandehEtebarModel foroshandehEtebarModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ForoshandehEtebarModel.COLUMN_ccForoshandeh() , foroshandehEtebarModel.getCcForoshandeh());
        contentValues.put(ForoshandehEtebarModel.COLUMN_SaghfEtebarRiali() , foroshandehEtebarModel.getSaghfEtebarRiali());
        contentValues.put(ForoshandehEtebarModel.COLUMN_SaghfEtebarAsnad() , foroshandehEtebarModel.getSaghfEtebarAsnad());
        contentValues.put(ForoshandehEtebarModel.COLUMN_SaghfEtebarTedadi() , foroshandehEtebarModel.getSaghfEtebarTedadi());
        contentValues.put(ForoshandehEtebarModel.COLUMN_SaghfEtebarModat() , foroshandehEtebarModel.getSaghfEtebarModat());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarRialAsnadShakhsi() , foroshandehEtebarModel.getEtebarRialAsnadShakhsi());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarTedadAsnadShakhsi() , foroshandehEtebarModel.getEtebarTedadAsnadShakhsi());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarModatAsnadShakhsi() , foroshandehEtebarModel.getEtebarModatAsnadShakhsi());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarRialAsnadMoshtary() , foroshandehEtebarModel.getEtebarRialAsnadMoshtary());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarTedadAsnadMoshtary() , foroshandehEtebarModel.getEtebarTedadAsnadMoshtary());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarModatAsnadMoshtary() , foroshandehEtebarModel.getEtebarModatAsnadMoshtary());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarRialMoavagh() , foroshandehEtebarModel.getEtebarRialMoavagh());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarTedadMoavagh() , foroshandehEtebarModel.getEtebarTedadMoavagh());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarModatMoavagh() , foroshandehEtebarModel.getEtebarModatMoavagh());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarRialBargashty() , foroshandehEtebarModel.getEtebarRialBargashty());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarTedadBargashty() , foroshandehEtebarModel.getEtebarTedadBargashty());
        contentValues.put(ForoshandehEtebarModel.COLUMN_EtebarModatBargashty() , foroshandehEtebarModel.getEtebarModatBargashty());
        contentValues.put(ForoshandehEtebarModel.COLUMN_ModatVosol() , foroshandehEtebarModel.getModatVosol());
        contentValues.put(ForoshandehEtebarModel.COLUMN_RialAsnad() , foroshandehEtebarModel.getRialAsnad());
        contentValues.put(ForoshandehEtebarModel.COLUMN_TedadAsnad() , foroshandehEtebarModel.getTedadAsnad());
        contentValues.put(ForoshandehEtebarModel.COLUMN_ModatAsnad() , foroshandehEtebarModel.getModatAsnad());
        contentValues.put(ForoshandehEtebarModel.COLUMN_RialMoavagh() , foroshandehEtebarModel.getRialMoavagh());
        contentValues.put(ForoshandehEtebarModel.COLUMN_TedadMoavagh() , foroshandehEtebarModel.getTedadMoavagh());
        contentValues.put(ForoshandehEtebarModel.COLUMN_ModatMoavagh() , foroshandehEtebarModel.getModatMoavagh());
        contentValues.put(ForoshandehEtebarModel.COLUMN_RialBargashty() , foroshandehEtebarModel.getRialBargashty());
        contentValues.put(ForoshandehEtebarModel.COLUMN_TedadBargashty() , foroshandehEtebarModel.getTedadBargashty());
        contentValues.put(ForoshandehEtebarModel.COLUMN_ModatBargashty() , foroshandehEtebarModel.getModatBargashty());

        return contentValues;
    }


    private ArrayList<ForoshandehEtebarModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ForoshandehEtebarModel> foroshandehEtebarModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ForoshandehEtebarModel foroshandehEtebarModel = new ForoshandehEtebarModel();

            foroshandehEtebarModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_ccForoshandeh())));
            foroshandehEtebarModel.setSaghfEtebarRiali(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_SaghfEtebarRiali())));
            foroshandehEtebarModel.setSaghfEtebarAsnad(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_SaghfEtebarAsnad())));
            foroshandehEtebarModel.setSaghfEtebarTedadi(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_SaghfEtebarTedadi())));
            foroshandehEtebarModel.setSaghfEtebarModat(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_SaghfEtebarModat())));
            foroshandehEtebarModel.setEtebarRialAsnadShakhsi(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarRialAsnadShakhsi())));
            foroshandehEtebarModel.setEtebarTedadAsnadShakhsi(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarTedadAsnadShakhsi())));
            foroshandehEtebarModel.setEtebarModatAsnadShakhsi(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarModatAsnadShakhsi())));
            foroshandehEtebarModel.setEtebarRialAsnadMoshtary(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarRialAsnadMoshtary())));
            foroshandehEtebarModel.setEtebarTedadAsnadMoshtary(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarTedadAsnadMoshtary())));
            foroshandehEtebarModel.setEtebarModatAsnadMoshtary(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarModatAsnadMoshtary())));
            foroshandehEtebarModel.setEtebarRialMoavagh(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarRialMoavagh())));
            foroshandehEtebarModel.setEtebarTedadMoavagh(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarTedadMoavagh())));
            foroshandehEtebarModel.setEtebarModatMoavagh(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarModatMoavagh())));
            foroshandehEtebarModel.setEtebarRialBargashty(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarRialBargashty())));
            foroshandehEtebarModel.setEtebarTedadBargashty(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarTedadBargashty())));
            foroshandehEtebarModel.setEtebarModatBargashty(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_EtebarModatBargashty())));
            foroshandehEtebarModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_ModatVosol())));
            foroshandehEtebarModel.setRialAsnad(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_RialAsnad())));
            foroshandehEtebarModel.setTedadAsnad(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_TedadAsnad())));
            foroshandehEtebarModel.setModatAsnad(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_ModatAsnad())));
            foroshandehEtebarModel.setRialMoavagh(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_RialMoavagh())));
            foroshandehEtebarModel.setTedadMoavagh(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_TedadMoavagh())));
            foroshandehEtebarModel.setModatMoavagh(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_ModatMoavagh())));
            foroshandehEtebarModel.setRialBargashty(cursor.getLong(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_RialBargashty())));
            foroshandehEtebarModel.setTedadBargashty(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_TedadBargashty())));
            foroshandehEtebarModel.setModatBargashty(cursor.getInt(cursor.getColumnIndex(ForoshandehEtebarModel.COLUMN_ModatBargashty())));

            foroshandehEtebarModels.add(foroshandehEtebarModel);
            cursor.moveToNext();
        }
        return foroshandehEtebarModels;
    }

}
