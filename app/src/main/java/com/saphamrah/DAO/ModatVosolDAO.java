package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.ModatVosolModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllvModatVosolByccMarkazForoshGorohResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModatVosolDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ModatVosolDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ModatVosolDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ModatVosolModel.COLUMN_ccModatVosol(),
            ModatVosolModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            ModatVosolModel.COLUMN_CodeNoe(),
            ModatVosolModel.COLUMN_SharhModatVosol(),
            ModatVosolModel.COLUMN_Az(),
            ModatVosolModel.COLUMN_Ta(),
            ModatVosolModel.COLUMN_ModatVosol(),
            ModatVosolModel.COLUMN_Darajeh(),
            ModatVosolModel.COLUMN_ccBrand(),
            ModatVosolModel.COLUMN_ccGoroh(),
            ModatVosolModel.COLUMN_ccGorohKala()
        };
    }

    public void fetchAllvModatVosolByccMarkazForoshGoroh(final Context context, final String activityNameForLog, final String ccMarkazForosh, final String ccGorohs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvModatVosolByccMarkazForoshGorohResult> call = apiService.getAllvModatVosolByccMarkazForoshGoroh(ccMarkazForosh , ccGorohs);
            call.enqueue(new Callback<GetAllvModatVosolByccMarkazForoshGorohResult>() {
                @Override
                public void onResponse(Call<GetAllvModatVosolByccMarkazForoshGorohResult> call, Response<GetAllvModatVosolByccMarkazForoshGorohResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ModatVosolDAO.class.getSimpleName(), "", "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvModatVosolByccMarkazForoshGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = "message : " + response.message() + "\n" + "code : " + response.code();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvModatVosolByccMarkazForoshGorohResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), ModatVosolDAO.class.getSimpleName(), activityNameForLog, "fetchAllvModatVosolByccMarkazForoshGoroh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<ModatVosolModel> modatVosolModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ModatVosolModel modatVosolModel : modatVosolModels)
            {
                Log.d("modatVosol" , "Modat Vosol DAO : " + modatVosolModel.toString());
                ContentValues contentValues = modelToContentvalue(modatVosolModel);
                db.insertOrThrow(ModatVosolModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ModatVosolModel> getAll()
    {
        ArrayList<ModatVosolModel> modatVosolModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ModatVosolModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    modatVosolModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "getAll" , "");
        }
        return modatVosolModels;
    }

    public ArrayList<ModatVosolModel> getForMohasebehModatvosol(int ccGoroh, int daraje)
    {
        ArrayList<ModatVosolModel> modatVosolModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ModatVosolModel.TableName(), allColumns(), "ccGoroh = " + ccGoroh + " AND Darajeh in (0, " + daraje + ")", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    modatVosolModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "getForMohasebehModatvosol" , "");
        }
        Log.d("ModatVosolDAO","modatVosolModels : " + modatVosolModels);
        return modatVosolModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ModatVosolModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMarkazAndccGoroh(String ccMarkaz , String ccGorohs)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ModatVosolModel.TableName(), ModatVosolModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() + " = " + ccMarkaz + " and " + ModatVosolModel.COLUMN_ccGoroh() + " in (" + ccGorohs + ")", null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "deleteByccMarkazAndccGoroh" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ModatVosolModel.TableName(), MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ModatVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ModatVosolModel modatVosolModel)
    {
        ContentValues contentValues = new ContentValues();

        if (modatVosolModel.getCcModatVosol() > 0)
        {
            contentValues.put(ModatVosolModel.COLUMN_ccModatVosol() , modatVosolModel.getCcModatVosol());
        }
        contentValues.put(ModatVosolModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , modatVosolModel.getCcMarkazSazmanForoshSakhtarForosh());
        contentValues.put(ModatVosolModel.COLUMN_CodeNoe() , modatVosolModel.getCodeNoe());
        contentValues.put(ModatVosolModel.COLUMN_SharhModatVosol() , modatVosolModel.getSharhModatVosol());
        contentValues.put(ModatVosolModel.COLUMN_Az() , modatVosolModel.getAz());
        contentValues.put(ModatVosolModel.COLUMN_Ta() , modatVosolModel.getTa());
        contentValues.put(ModatVosolModel.COLUMN_ModatVosol() , modatVosolModel.getModatVosol());
        contentValues.put(ModatVosolModel.COLUMN_Darajeh() , modatVosolModel.getDarajeh());
        contentValues.put(ModatVosolModel.COLUMN_ccBrand() , modatVosolModel.getCcBrand());
        contentValues.put(ModatVosolModel.COLUMN_ccGoroh() , modatVosolModel.getCcGoroh());
        contentValues.put(ModatVosolModel.COLUMN_ccGorohKala() , modatVosolModel.getCcGorohKala());

        return contentValues;
    }

    private ArrayList<ModatVosolModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ModatVosolModel> modatVosolModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ModatVosolModel modatVosolModel = new ModatVosolModel();

            modatVosolModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccModatVosol())));
            modatVosolModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));
            modatVosolModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_CodeNoe())));
            modatVosolModel.setSharhModatVosol(cursor.getString(cursor.getColumnIndex(ModatVosolModel.COLUMN_SharhModatVosol())));
            modatVosolModel.setAz(cursor.getFloat(cursor.getColumnIndex(ModatVosolModel.COLUMN_Az())));
            modatVosolModel.setTa(cursor.getFloat(cursor.getColumnIndex(ModatVosolModel.COLUMN_Ta())));
            modatVosolModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ModatVosol())));
            modatVosolModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_Darajeh())));
            modatVosolModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccBrand())));
            modatVosolModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccGoroh())));
            modatVosolModel.setCcGorohKala(cursor.getInt(cursor.getColumnIndex(ModatVosolModel.COLUMN_ccGorohKala())));

            modatVosolModels.add(modatVosolModel);
            cursor.moveToNext();
        }
        return modatVosolModels;
    }
    
    
}
