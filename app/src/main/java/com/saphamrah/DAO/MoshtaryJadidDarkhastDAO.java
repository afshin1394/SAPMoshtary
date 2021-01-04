package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MoshtaryJadidDarkhastModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryJadidDarkhastPPCResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryJadidDarkhastDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "MoshtaryJadidDarkhastDAO";


    public MoshtaryJadidDarkhastDAO(Context context)
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
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, exception.toString(), CLASS_NAME , "" , "constructor" , "");
        }
    }
    
    
    public void fetchMoshtaryJadidDarkhast(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, CLASS_NAME, "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtaryJadidDarkhastPPCResult> call = apiServiceGet.getAllMoshtaryJadidDarkhastPPC();
            call.enqueue(new Callback<GetAllMoshtaryJadidDarkhastPPCResult>() {
                @Override
                public void onResponse(Call<GetAllMoshtaryJadidDarkhastPPCResult> call, Response<GetAllMoshtaryJadidDarkhastPPCResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", CLASS_NAME, "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMoshtaryJadidDarkhastPPCResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, CLASS_NAME, "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, CLASS_NAME, "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, CLASS_NAME, "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, CLASS_NAME, "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtaryJadidDarkhastPPCResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, CLASS_NAME, "onFailure");
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
    
    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryJadidDarkhastModel.COLUMN_ccMoshtaryJadidDarkhast(),
            MoshtaryJadidDarkhastModel.COLUMN_ccNoeMoshtary(),
            MoshtaryJadidDarkhastModel.COLUMN_ccKalaCode(),
            MoshtaryJadidDarkhastModel.COLUMN_flag_ForMoshtaryJadid(),
            MoshtaryJadidDarkhastModel.COLUMN_Sath()
        };
    }
    
    public boolean insertGroup(ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryJadidDarkhastModel moshtaryJadidDarkhastModel : moshtaryJadidDarkhastModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryJadidDarkhastModel);
                db.insertOrThrow(MoshtaryJadidDarkhastModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryJadidDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<MoshtaryJadidDarkhastModel> getAll()
    {
        ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryJadidDarkhastModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryJadidDarkhastModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryJadidDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, CLASS_NAME , "" , "getAll" , "");
        }
        return moshtaryJadidDarkhastModels;
    }


    public boolean getHasKalaAsasiKharidNakardeh(int ccKalaCode, int ccNoeSenf, int ccMoshtary,int countMoshtaryBrand)
    {
        boolean flag = false;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String StrSql = " SELECT count(ccMoshtaryJadidDarkhast) "
                    + " FROM MoshtaryJadidDarkhast  "
                    + " WHERE ccKalaCode= " + ccKalaCode
                    + "             AND ccKalaCode NOT IN (SELECT DISTINCT ccKalaCode "
                    + "                                              FROM    DarkhastFaktor "
                    + "                                                 LEFT OUTER JOIN DarkhastFaktorSatr ON DarkhastFaktor.ccDarkhastFaktor = DarkhastFaktorSatr.ccDarkhastFaktor "
                    + "                                              WHERE   DarkhastFaktor.ccMoshtary = "+ccMoshtary+" AND DarkhastFaktor.CodeVazeiat>3) "
                    + "             AND ccKalaCode IN (SELECT ccKalaCode FROM KalaGoroh LEFT JOIN GorohKalaNoeSenf ON GorohKalaNoeSenf.ccGorohKala = KalaGoroh.ccGoroh WHERE ccNoeSenf = " + ccNoeSenf + " AND ccGorohLink=560)";
            if(countMoshtaryBrand > 0)
            {
                StrSql += " AND ccKalaCode IN (SELECT ccKalaCode From Kala Where ccBrand IN (SELECT ccBrand FROM MoshtaryBrand WHERE ccMoshtary="+ccMoshtary+"))";
            }
            Cursor cursor = db.rawQuery(StrSql,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    int count = cursor.getInt(0);
                    if (count > 0)
                    {
                        flag = true;
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryJadidDarkhastModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, CLASS_NAME , "" , "getHasKalaAsasiKharidNakardeh" , "");
        }
        return flag;
    }

    public int getSathKala(int ccKalaCode)
    {
        int sath = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select " + MoshtaryJadidDarkhastModel.COLUMN_Sath() + " from " + MoshtaryJadidDarkhastModel.TableName() +
                    " where " + MoshtaryJadidDarkhastModel.COLUMN_ccKalaCode() + " = " + ccKalaCode;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sath = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryJadidDarkhastModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, CLASS_NAME , "" , "getSathKala" , "");
        }
        return sath;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryJadidDarkhastModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryJadidDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryJadidDarkhastModel moshtaryJadidDarkhastModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoshtaryJadidDarkhastModel.COLUMN_ccMoshtaryJadidDarkhast() , moshtaryJadidDarkhastModel.getCcMoshtaryJadidDarkhast());
        contentValues.put(MoshtaryJadidDarkhastModel.COLUMN_ccNoeMoshtary() , moshtaryJadidDarkhastModel.getCcNoeMoshtary());
        contentValues.put(MoshtaryJadidDarkhastModel.COLUMN_ccKalaCode() , moshtaryJadidDarkhastModel.getCcKalaCode());
        contentValues.put(MoshtaryJadidDarkhastModel.COLUMN_flag_ForMoshtaryJadid() , moshtaryJadidDarkhastModel.getFlag_ForMoshtaryJadid());
        contentValues.put(MoshtaryJadidDarkhastModel.COLUMN_Sath() , moshtaryJadidDarkhastModel.getSath());

        return contentValues;
    }


    private ArrayList<MoshtaryJadidDarkhastModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryJadidDarkhastModel> moshtaryJadidDarkhastModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryJadidDarkhastModel moshtaryJadidDarkhastModel = new MoshtaryJadidDarkhastModel();

            moshtaryJadidDarkhastModel.setCcMoshtaryJadidDarkhast(cursor.getInt(cursor.getColumnIndex(MoshtaryJadidDarkhastModel.COLUMN_ccMoshtaryJadidDarkhast())));
            moshtaryJadidDarkhastModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryJadidDarkhastModel.COLUMN_ccNoeMoshtary())));
            moshtaryJadidDarkhastModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(MoshtaryJadidDarkhastModel.COLUMN_ccKalaCode())));
            moshtaryJadidDarkhastModel.setFlag_ForMoshtaryJadid(cursor.getInt(cursor.getColumnIndex(MoshtaryJadidDarkhastModel.COLUMN_flag_ForMoshtaryJadid())));
            moshtaryJadidDarkhastModel.setSath(cursor.getInt(cursor.getColumnIndex(MoshtaryJadidDarkhastModel.COLUMN_Sath())));

            moshtaryJadidDarkhastModels.add(moshtaryJadidDarkhastModel);
            cursor.moveToNext();
        }
        return moshtaryJadidDarkhastModels;
    }

}
