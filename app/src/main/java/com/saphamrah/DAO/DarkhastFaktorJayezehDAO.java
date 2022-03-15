package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.DarkhastFaktorJayezehResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarkhastFaktorJayezehDAO
{

    private DBHelper dbHelper;
    private Context context;



    public DarkhastFaktorJayezehDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorJayezehDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktorJayezeh(),
            DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor(),
            DarkhastFaktorJayezehModel.COLUMN_ccJayezeh(),
            DarkhastFaktorJayezehModel.COLUMN_Sharh(),
            DarkhastFaktorJayezehModel.COLUMN_ccKala(),
            DarkhastFaktorJayezehModel.COLUMN_ccKalaCode(),
            DarkhastFaktorJayezehModel.COLUMN_Tedad(),
            DarkhastFaktorJayezehModel.COLUMN_ExtraProp_IsJayezehEntekhabi(),
            DarkhastFaktorJayezehModel.COLUMN_ExtraProp_ccJayezehTakhfif(),
            DarkhastFaktorJayezehModel.COLUMN_ExtraProp_CodeNoeJayezeh(),
            DarkhastFaktorJayezehModel.COLUMN_ExtraProp_ccJayezehSatr(),
            DarkhastFaktorJayezehModel.COLUMN_ExtraProp_NameKala()
        };
    }
    public boolean insertGroup(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorTakhfifModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DarkhastFaktorJayezehModel darkhastFaktorJayezehModel : darkhastFaktorTakhfifModels)
            {
                ContentValues contentValues = modelToContentvalue(darkhastFaktorJayezehModel);
                db.insertOrThrow(DarkhastFaktorJayezehModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorjayezehDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(DarkhastFaktorJayezehModel darkhastFaktorJayezehModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(darkhastFaktorJayezehModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(DarkhastFaktorJayezehModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "insert" , "");
            return false;
        }
    }

    public void fetchDarkhastFaktorJayezeh( Context context,  String activityNameForLog, String ccDarkhastHavalehs,RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorJayezehDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorJayezeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<DarkhastFaktorJayezehResult> call = apiServiceGet.getDarkhastFaktorjayezeh(ccDarkhastHavalehs);
            call.enqueue(new Callback<DarkhastFaktorJayezehResult>() {
                @Override
                public void onResponse(Call<DarkhastFaktorJayezehResult> call, Response<DarkhastFaktorJayezehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, DarkhastFaktorJayezehDAO.class.getSimpleName(), "", "fetchDarkhastFaktorJayezeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            DarkhastFaktorJayezehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), DarkhastFaktorJayezehDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorJayezeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull), endpoint), DarkhastFaktorJayezehDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorJayezeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorJayezehDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorJayezeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), DarkhastFaktorJayezehDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorJayezeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<DarkhastFaktorJayezehResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    String endpoint = getEndpoint(call);
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), DarkhastFaktorJayezehDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorJayezeh", "onFailure");
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

    public ArrayList<DarkhastFaktorJayezehModel> getAll()
    {
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorJayezehModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorJayezehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorJayezehModels;
    }

    public ArrayList<DarkhastFaktorJayezehModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorJayezehModel.TableName(), allColumns(), DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorJayezehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorJayezehModels;
    }


    /**
     * دریافت تخفیف برای درخواست فاکتور بر اساس کد نوع جایزه که ممکن است انتخابی و یا اتوماتیک باشد.
     * @param ccDarkhastFaktor
     * @param codeNoe اگر 1 باشد به این معنی است که جایزه به صورت اتوماتیک داده شده ولی اگر 2 باشد به این معنی است که تیتر جایزه ثبت شده و فروشنده باید جایزه را انتخاب نماید(یعنی جایزه انتخابی است)
     * @return
     */
    public ArrayList<DarkhastFaktorJayezehModel> getByccDarkhastFaktorAndCodeNoe(long ccDarkhastFaktor , int codeNoe)
    {
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //String query = "select dfj.* , k.MablaghMasrafKonandeh from DarkhastFaktorJayezeh dfj left join Kala k on k.ccKalaCode = dfj.ccKalaCode where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ExtraProp_CodeNoeJayezeh = " + codeNoe + " AND Tedad>0 group by ccDarkhastFaktorJayezeh";
            String query = "select dfj.* , k.MablaghMasrafKonandeh from DarkhastFaktorJayezeh dfj left join Kala k on k.ccKalaCode = dfj.ccKalaCode where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ExtraProp_CodeNoeJayezeh = " + codeNoe + " AND Tedad>0 group by ccDarkhastFaktorJayezeh";
            Log.d("DarkhastFaktorJayezeh" , "Jayezeh haveBonus getByccDarkhastFaktorAndCodeNoe:" + query);

            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorJayezehModels = cursorToModelPrint(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "getByccDarkhastFaktorAndCodeNoe" , "");
        }
        return darkhastFaktorJayezehModels;
    }

    public ArrayList<DarkhastFaktorJayezehModel> getByccDarkhastFaktorAndCodeNoeForHaveBonus(long ccDarkhastFaktor , int codeNoe)
    {
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //String query = "select dfj.* , k.MablaghMasrafKonandeh from DarkhastFaktorJayezeh dfj left join Kala k on k.ccKalaCode = dfj.ccKalaCode where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ExtraProp_CodeNoeJayezeh = " + codeNoe + " AND Tedad>0 group by ccDarkhastFaktorJayezeh";
            String query = "select dfj.* , k.MablaghMasrafKonandeh from DarkhastFaktorJayezeh dfj left join Kala k on k.ccKalaCode = dfj.ccKalaCode where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ExtraProp_CodeNoeJayezeh = " + codeNoe + " AND Tedad>=0 group by ccDarkhastFaktorJayezeh";
            Log.d("DarkhastFaktorJayezeh" , "Jayezeh haveBonus getByccDarkhastFaktorAndCodeNoe:" + query);

            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorJayezehModels = cursorToModelPrint(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "getByccDarkhastFaktorAndCodeNoe" , "");
        }
        return darkhastFaktorJayezehModels;
    }

    public int getCountByccDarkhastFaktorAndCodeNoe(long ccDarkhastFaktor , int codeNoe)
    {
        int count = 0;
        try
        {
            //select count(ccDarkhastFaktorJayezeh) from DarkhastFaktorJayezeh where ccDarkhastFaktor =  and ExtraProp_CodeNoeJayezeh = 1
            String query = "select count(" + DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktorJayezeh() + ") from " + DarkhastFaktorJayezehModel.TableName() + " where " + DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorJayezehModel.COLUMN_ExtraProp_CodeNoeJayezeh() + " = " + codeNoe;
            Log.d("DarkhastFaktorJayezeh" , "Jayezeh getCountByccDarkhastFaktorAndCodeNoe  query:" + query);

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "getCountByccDarkhastFaktorAndCodeNoe" , "");
        }
        return count;
    }

    public int getCountByccDarkhastFaktorForJayezehArzesh(long ccDarkhastFaktor)
    {
        int count = 0;
        try
        {
            //select count(ccDarkhastFaktorJayezeh) from DarkhastFaktorJayezeh where ccDarkhastFaktor =  and ExtraProp_CodeNoeJayezeh = 1
            String query = " SELECT count(ccDarkhastFaktor) from DarkhastFaktorJayezeh where ccDarkhastFaktor = " + ccDarkhastFaktor + "  and ExtraProp_ccJayezehTakhfif in ( " +
                    " SELECT DISTINCT ccJayezeh FROM Jayezeh WHERE CodeNoe=4)";

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "getCountByccDarkhastFaktorAndCodeNoe" , "");
        }
        return count;
    }

    public ArrayList<DarkhastFaktorJayezehModel> getByccKalaCodesAndccDarkhastFaktor(String ccKalaCodes , long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorJayezehModel.TableName(), allColumns(), DarkhastFaktorJayezehModel.COLUMN_ccKalaCode() + " in (" + ccKalaCodes + ") and " + DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorJayezehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "getByccKalaCodes" , "");
        }
        return darkhastFaktorJayezehModels;
    }

    public int getCountJayezeByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select count(" + DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktorJayezeh() + ") from " + DarkhastFaktorJayezehModel.TableName() + " where " + DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() +" = " + ccDarkhastFaktor;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "getByccKalaCodes" , "");
        }
        return count;
    }

    public boolean deleteByccDarkhastFaktorAndccJayeze(long ccDarkhastFaktor , int ccJayeze)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorJayezehModel.TableName(), DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorJayezehModel.COLUMN_ccJayezeh() + " = " + ccJayeze, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "deleteByccDarkhastFaktorAndccJayeze" , "");
            return false;
        }
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorJayezehModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorJayezehModel.TableName(), DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean updateSendedDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew)
    {
        try
        {
            String query = "update " + DarkhastFaktorJayezehModel.TableName() + " set " + DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew +
                    " where " + DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorJayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehDAO" , "" , "updateSendedDarkhastFaktor" , "");
            return true;
        }
    }

    private static ContentValues modelToContentvalue(DarkhastFaktorJayezehModel darkhastFaktorJayezehModel)
    {
        ContentValues contentValues = new ContentValues();

        if (darkhastFaktorJayezehModel.getCcDarkhastFaktorJayezeh() > 0)
        {
            contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktorJayezeh() , darkhastFaktorJayezehModel.getCcDarkhastFaktorJayezeh());
        }
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor() , darkhastFaktorJayezehModel.getCcDarkhastFaktor());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ccJayezeh() , darkhastFaktorJayezehModel.getCcJayezeh());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_Sharh() , darkhastFaktorJayezehModel.getSharh());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ccKala() , darkhastFaktorJayezehModel.getCcKala());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ccKalaCode() , darkhastFaktorJayezehModel.getCcKalaCode());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_Tedad() , darkhastFaktorJayezehModel.getTedad());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_IsJayezehEntekhabi() , darkhastFaktorJayezehModel.getExtraProp_IsJayezehEntekhabi());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_ccJayezehTakhfif() , darkhastFaktorJayezehModel.getExtraProp_ccJayezehTakhfif());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_ccJayezehSatr() , darkhastFaktorJayezehModel.getExtraProp_ccJayezehSatr());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_CodeNoeJayezeh() , darkhastFaktorJayezehModel.getExtraProp_CodeNoeJayezeh());
        contentValues.put(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_NameKala() , darkhastFaktorJayezehModel.getExtraProp_NameKala());

        return contentValues;
    }


    private ArrayList<DarkhastFaktorJayezehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorJayezehModel darkhastFaktorJayezehModel = new DarkhastFaktorJayezehModel();

            darkhastFaktorJayezehModel.setCcDarkhastFaktorJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktorJayezeh())));
            darkhastFaktorJayezehModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorJayezehModel.setCcJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccJayezeh())));
            darkhastFaktorJayezehModel.setSharh(cursor.getString(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_Sharh())));
            darkhastFaktorJayezehModel.setCcKala(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccKala())));
            darkhastFaktorJayezehModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccKalaCode())));
            darkhastFaktorJayezehModel.setTedad(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_Tedad())));
            darkhastFaktorJayezehModel.setExtraProp_IsJayezehEntekhabi(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_IsJayezehEntekhabi())));
            darkhastFaktorJayezehModel.setExtraProp_ccJayezehTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_ccJayezehTakhfif())));
            darkhastFaktorJayezehModel.setExtraProp_ccJayezehSatr(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_ccJayezehSatr())));
            darkhastFaktorJayezehModel.setExtraProp_CodeNoeJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_CodeNoeJayezeh())));
            darkhastFaktorJayezehModel.setExtraProp_NameKala(cursor.getString(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_NameKala())));

            darkhastFaktorJayezehModels.add(darkhastFaktorJayezehModel);
            cursor.moveToNext();
        }
        return darkhastFaktorJayezehModels;
    }


    private ArrayList<DarkhastFaktorJayezehModel> cursorToModelPrint(Cursor cursor)
    {
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorJayezehModel darkhastFaktorJayezehModel = new DarkhastFaktorJayezehModel();

            darkhastFaktorJayezehModel.setCcDarkhastFaktorJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktorJayezeh())));
            darkhastFaktorJayezehModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorJayezehModel.setCcJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccJayezeh())));
            darkhastFaktorJayezehModel.setSharh(cursor.getString(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_Sharh())));
            darkhastFaktorJayezehModel.setCcKala(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccKala())));
            darkhastFaktorJayezehModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ccKalaCode())));
            darkhastFaktorJayezehModel.setTedad(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_Tedad())));
            darkhastFaktorJayezehModel.setExtraProp_IsJayezehEntekhabi(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_IsJayezehEntekhabi())));
            darkhastFaktorJayezehModel.setExtraProp_ccJayezehTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_ccJayezehTakhfif())));
            darkhastFaktorJayezehModel.setExtraProp_ccJayezehSatr(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_ccJayezehSatr())));
            darkhastFaktorJayezehModel.setExtraProp_CodeNoeJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_CodeNoeJayezeh())));
            darkhastFaktorJayezehModel.setExtraProp_NameKala(cursor.getString(cursor.getColumnIndex(DarkhastFaktorJayezehModel.COLUMN_ExtraProp_NameKala())));
            darkhastFaktorJayezehModel.setMablaghMasrafKonandeh(cursor.getFloat(cursor.getColumnIndex("MablaghMasrafKonandeh")));

            darkhastFaktorJayezehModels.add(darkhastFaktorJayezehModel);
            cursor.moveToNext();
        }
        return darkhastFaktorJayezehModels;
    }
    

}
