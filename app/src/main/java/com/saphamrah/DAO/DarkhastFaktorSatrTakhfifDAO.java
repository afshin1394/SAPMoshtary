package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.DarkhastFaktorSatrTakhfifModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarkhastFaktorSatrTakhfifDAO 
{
    private DBHelper dbHelper;
    private Context context;


    public DarkhastFaktorSatrTakhfifDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorSatrTakhfifDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatrTakhfif(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatr(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_ccTakhfif(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_SharhTakhfif(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_CodeNoeTakhfif(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_DarsadTakhfif(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_MablaghTakhfif(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_NotSendToSql(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_ForJayezeh(),
            DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_Olaviat()
        };
    }

    public void fetchAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate(final Context context, final String activityNameForLog, final String ccforoshandeh, final String fromDate, final String endDate, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorSatrTakhfifDAO.class.getSimpleName(), activityNameForLog, "fetchAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult> call = apiServiceGet.getAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate(ccforoshandeh , fromDate , endDate);
            call.enqueue(new Callback<GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult>() {
                @Override
                public void onResponse(Call<GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult> call, Response<GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, DarkhastFaktorSatrDAO.class.getSimpleName(), "", "fetchAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), DarkhastFaktorSatrTakhfifDAO.class.getSimpleName(), activityNameForLog, "fetchAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), DarkhastFaktorSatrTakhfifDAO.class.getSimpleName(), activityNameForLog, "fetchAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorSatrTakhfifDAO.class.getSimpleName(), activityNameForLog, "fetchAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), DarkhastFaktorSatrTakhfifDAO.class.getSimpleName(), activityNameForLog, "fetchAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), DarkhastFaktorSatrTakhfifDAO.class.getSimpleName(), activityNameForLog, "fetchAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate", "onFailure");
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

    public boolean insertGroup(ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DarkhastFaktorSatrTakhfifModel darkhastFaktorSatrTakhfifModel : darkhastFaktorSatrTakhfifModels)
            {
                ContentValues contentValues = modelToContentvalue(darkhastFaktorSatrTakhfifModel);
                db.insertOrThrow(DarkhastFaktorSatrTakhfifModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(DarkhastFaktorSatrTakhfifModel darkhastFaktorSatrTakhfifModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(darkhastFaktorSatrTakhfifModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(DarkhastFaktorSatrTakhfifModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<DarkhastFaktorSatrTakhfifModel> getAll()
    {
        ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorSatrTakhfifModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrTakhfifModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorSatrTakhfifModels;
    }

    public ArrayList<DarkhastFaktorSatrTakhfifModel> getByccDarkhastFaktorSatr(int ccDarkhastFaktorSatr)
    {
        ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorSatrTakhfifModel.TableName(), allColumns(), DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatr() + " = " + ccDarkhastFaktorSatr + " and " + DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_ForJayezeh() + " = 0", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrTakhfifModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getByccDarkhastFaktorSatr" , "");
        }
        return darkhastFaktorSatrTakhfifModels;
    }

    public ArrayList<DarkhastFaktorSatrTakhfifModel> getByccDarkhastFaktorSatrs(String ccDarkhastFaktorSatrs)
    {
        ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorSatrTakhfifModel.TableName(), allColumns(), DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatr() + " in (" + ccDarkhastFaktorSatrs + ") and " + DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_ForJayezeh() + " = 0", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrTakhfifModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getByccDarkhastFaktorSatr" , "");
        }
        return darkhastFaktorSatrTakhfifModels;
    }

    public double getSumMablaghTakhfifByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        double sumMablaghTakhfif = 0;
        try
        {
            String query = "select sum(MablaghTakhfif) from DarkhastFaktorSatrTakhfif \n" +
                    " where ccTakhfif in \n" +
                    " (select ccTakhfif from DarkhastFaktorTakhfif where ccDarkhastFaktor = " + ccDarkhastFaktor + ")";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablaghTakhfif = cursor.getDouble(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getSumMablaghTakhfifByccDarkhastFaktor" , "");
        }
        return sumMablaghTakhfif;
    }


    public long getSumMablaghTakhfifByccDarkhastFaktorAndccTakhfif(long ccDarkhastFaktor , int ccTakhfif , String allMablaghTakhfif)
    {
        long sumMablaghTakhfif = 0;
        try
        {
            String query = "select sum(MablaghTakhfif) from DarkhastFaktorSatrTakhfif where \n" +
                    " ccDarkhastFaktorSatr in (Select ccDarkhastFaktorSatr from DarkhastFaktorSatr where ccDarkhastFaktor = " + ccDarkhastFaktor + ") \n" +
                    " and ccTakhfif = " + ccTakhfif + " and MablaghTakhfif in (" + allMablaghTakhfif + ")";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablaghTakhfif = cursor.getLong(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getSumMablaghTakhfifByccDarkhastFaktorAndccTakhfif" , "");
        }
        return sumMablaghTakhfif;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorSatrTakhfifModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktorSatr(String ccDarkhastFaktorSatrs)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorSatrTakhfifModel.TableName(), DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatr() + " in (" + ccDarkhastFaktorSatrs + ")", null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean updateWithLoop(ArrayList<Integer> arrayList)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (Integer id : arrayList)
            {
                ContentValues values = new ContentValues();
                values.put(DarkhastFaktorSatrTakhfifModel.COLUMN_DarsadTakhfif() , "10");
                db.update(DarkhastFaktorSatrTakhfifModel.TableName(), values, DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatrTakhfif() + " = " + id, null);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }


    public boolean updateWithIn(String ids)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            String query = "update " + DarkhastFaktorSatrTakhfifModel.TableName() + " set " + DarkhastFaktorSatrTakhfifModel.COLUMN_DarsadTakhfif() +
                    " = 10 where " + DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatrTakhfif() + " in ( " + ids + ")";
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }

    private static ContentValues modelToContentvalue(DarkhastFaktorSatrTakhfifModel darkhastFaktorSatrTakhfifModel)
    {
        ContentValues contentValues = new ContentValues();

        if (darkhastFaktorSatrTakhfifModel.getCcDarkhastFaktorSatrTakhfif() > 0)
        {
            contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatrTakhfif() , darkhastFaktorSatrTakhfifModel.getCcDarkhastFaktorSatrTakhfif());
        }
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatr() , darkhastFaktorSatrTakhfifModel.getCcDarkhastFaktorSatr());
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_ccTakhfif() , darkhastFaktorSatrTakhfifModel.getCcTakhfif());
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_SharhTakhfif() , darkhastFaktorSatrTakhfifModel.getSharhTakhfif());
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_CodeNoeTakhfif() , darkhastFaktorSatrTakhfifModel.getCodeNoeTakhfif());
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_DarsadTakhfif() , darkhastFaktorSatrTakhfifModel.getDarsadTakhfif());
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_MablaghTakhfif() , darkhastFaktorSatrTakhfifModel.getMablaghTakhfif());
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_NotSendToSql() , darkhastFaktorSatrTakhfifModel.getExtraProp_NotSendToSql());
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_ForJayezeh() , darkhastFaktorSatrTakhfifModel.getExtraProp_ForJayezeh());
        contentValues.put(DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_Olaviat() , darkhastFaktorSatrTakhfifModel.getExtraProp_Olaviat());

        return contentValues;
    }


    private ArrayList<DarkhastFaktorSatrTakhfifModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorSatrTakhfifModel darkhastFaktorSatrTakhfifModel = new DarkhastFaktorSatrTakhfifModel();

            darkhastFaktorSatrTakhfifModel.setCcDarkhastFaktorSatrTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatrTakhfif())));
            darkhastFaktorSatrTakhfifModel.setCcDarkhastFaktorSatr(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_ccDarkhastFaktorSatr())));
            darkhastFaktorSatrTakhfifModel.setCcTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_ccTakhfif())));
            darkhastFaktorSatrTakhfifModel.setSharhTakhfif(cursor.getString(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_SharhTakhfif())));
            darkhastFaktorSatrTakhfifModel.setCodeNoeTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_CodeNoeTakhfif())));
            darkhastFaktorSatrTakhfifModel.setDarsadTakhfif(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_DarsadTakhfif())));
            darkhastFaktorSatrTakhfifModel.setMablaghTakhfif(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_MablaghTakhfif())));
            darkhastFaktorSatrTakhfifModel.setExtraProp_NotSendToSql(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_NotSendToSql())));
            darkhastFaktorSatrTakhfifModel.setExtraProp_ForJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_ForJayezeh())));
            darkhastFaktorSatrTakhfifModel.setExtraProp_Olaviat(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrTakhfifModel.COLUMN_ExtraProp_Olaviat())));

            darkhastFaktorSatrTakhfifModels.add(darkhastFaktorSatrTakhfifModel);
            cursor.moveToNext();
        }
        return darkhastFaktorSatrTakhfifModels;
    }
    
}
