package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllrptDarkhastFaktorHavalehVazeiatResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RptDarkhastFaktorVazeiatPPCDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public RptDarkhastFaktorVazeiatPPCDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptDarkhastFaktorVazeiatPPCDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccRpt_DarkhastFaktorVazeiatPPC(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccDarkhastFaktor(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccMarkazForosh(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccGorohForosh(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccForoshandeh(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccMoshtary(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_NameMarkazForosh(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_SharhGorohForosh(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_SharhForoshandeh(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_FullNameForoshandeh(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeMoshtary(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_NameMoshtary(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ShomarehDarkhast(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhDarkhast(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhDarkhastWithSlash(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_SaatDarkhast(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ShomarehFaktor(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhFaktor(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhFaktorWithSlash(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_RoundMablaghKhalesFaktor(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeNoeVorod(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_txtCodeNoeVorod(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeVazeiat(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_txtCodeVazeiat(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccDarkhastFaktorPPC(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_MablaghNahaeeFaktor(),
            RptDarkhastFaktorVazeiatPPCModel.COLUMN_IsFaktorBaz()
        };
    }

    public void fetchRptDarkhastFaktorVazeiat(final Context context, final String activityNameForLog,final String ccForoshandeh, final String ccMamorPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), activityNameForLog, "fetchRptDarkhastFaktorVazeiat", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllrptDarkhastFaktorHavalehVazeiatResult> call = apiServiceGet.getAllrptDarkhastFaktorHavalehVazeiat(ccForoshandeh , ccMamorPakhsh);
            call.enqueue(new Callback<GetAllrptDarkhastFaktorHavalehVazeiatResult>()
            {
                @Override
                public void onResponse(Call<GetAllrptDarkhastFaktorHavalehVazeiatResult> call, Response<GetAllrptDarkhastFaktorHavalehVazeiatResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), "", "fetchRptDarkhastFaktorVazeiat", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllrptDarkhastFaktorHavalehVazeiatResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), activityNameForLog, "fetchRptDarkhastFaktorVazeiat", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), activityNameForLog, "fetchRptDarkhastFaktorVazeiat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), activityNameForLog, "fetchRptDarkhastFaktorVazeiat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), activityNameForLog, "fetchRptDarkhastFaktorVazeiat", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptDarkhastFaktorHavalehVazeiatResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), activityNameForLog, "fetchRptDarkhastFaktorVazeiat", "onFailure");
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

    public boolean insertGroup(ArrayList<RptDarkhastFaktorVazeiatPPCModel> darkhastFaktorVazeiatPPCModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptDarkhastFaktorVazeiatPPCModel darkhastFaktorVazeiatPPCModel : darkhastFaktorVazeiatPPCModels)
            {
                ContentValues contentValues = modelToContentvalue(darkhastFaktorVazeiatPPCModel);
                db.insertOrThrow(RptDarkhastFaktorVazeiatPPCModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptDarkhastFaktorVazeiatPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptDarkhastFaktorVazeiatPPCDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptDarkhastFaktorVazeiatPPCModel> getAll()
    {
        ArrayList<RptDarkhastFaktorVazeiatPPCModel> darkhastFaktorVazeiatPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptDarkhastFaktorVazeiatPPCModel.TableName(), allColumns(), null, null, null, null, RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhFaktor() + " desc ");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorVazeiatPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptDarkhastFaktorVazeiatPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptDarkhastFaktorVazeiatPPCDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorVazeiatPPCModels;
    }

    public int getCountFaktorForMoshtary(int ccMoshtary)
    {
        int count = 0;
        try
        {
            String query = "select \n" +
                    " (select count(ccMoshtary) from DarkhastFaktor \n" +
                    " where ccMoshtary = " + ccMoshtary + " and strftime('%Y-%m-%d' , TarikhFaktor) = strftime('%Y-%m-%d', date('now')) ) + \n" +
                    " (select count(ccMoshtary) from Rpt_DarkhastFaktorVazeiatPPC \n" +
                    " where ccMoshtary = " + ccMoshtary + " and strftime('%Y-%m-%d' , TarikhFaktor) = strftime('%Y-%m-%d', date('now')) ) \n" +
                    " as SumCount";
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
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptDarkhastFaktorVazeiatPPCModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptDarkhastFaktorVazeiatPPCDAO" , "" , "getCountFaktorForMoshtary" , "");
        }
        return count;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptDarkhastFaktorVazeiatPPCModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptDarkhastFaktorVazeiatPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptDarkhastFaktorVazeiatPPCDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptDarkhastFaktorVazeiatPPCModel darkhastFaktorVazeiatPPCModel)
    {
        ContentValues contentValues = new ContentValues();

        if (darkhastFaktorVazeiatPPCModel.getCcRpt_DarkhastFaktorVazeiatPPC() > 0)
        {
            contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccRpt_DarkhastFaktorVazeiatPPC() , darkhastFaktorVazeiatPPCModel.getCcRpt_DarkhastFaktorVazeiatPPC());
        }
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccDarkhastFaktor() , darkhastFaktorVazeiatPPCModel.getCcDarkhastFaktor());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccMarkazForosh() , darkhastFaktorVazeiatPPCModel.getCcMarkazForosh());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccGorohForosh() , darkhastFaktorVazeiatPPCModel.getCcGorohForosh());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccForoshandeh() , darkhastFaktorVazeiatPPCModel.getCcForoshandeh());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccMoshtary() , darkhastFaktorVazeiatPPCModel.getCcMoshtary());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_NameMarkazForosh() , darkhastFaktorVazeiatPPCModel.getNameMarkazForosh());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_SharhGorohForosh() , darkhastFaktorVazeiatPPCModel.getSharhGorohForosh());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_SharhForoshandeh() , darkhastFaktorVazeiatPPCModel.getSharhForoshandeh());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_FullNameForoshandeh() , darkhastFaktorVazeiatPPCModel.getFullNameForoshandeh());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeMoshtary() , darkhastFaktorVazeiatPPCModel.getCodeMoshtary());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_NameMoshtary() , darkhastFaktorVazeiatPPCModel.getNameMoshtary());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ShomarehDarkhast() , darkhastFaktorVazeiatPPCModel.getShomarehDarkhast());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhDarkhast() , darkhastFaktorVazeiatPPCModel.getTarikhDarkhast());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhDarkhastWithSlash() , darkhastFaktorVazeiatPPCModel.getTarikhDarkhastWithSlash());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_SaatDarkhast() , darkhastFaktorVazeiatPPCModel.getSaatDarkhast());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ShomarehFaktor() , darkhastFaktorVazeiatPPCModel.getShomarehFaktor());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhFaktor() , darkhastFaktorVazeiatPPCModel.getTarikhFaktor());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhFaktorWithSlash() , darkhastFaktorVazeiatPPCModel.getTarikhFaktorWithSlash());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_RoundMablaghKhalesFaktor() , darkhastFaktorVazeiatPPCModel.getRoundMablaghKhalesFaktor());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeNoeVorod() , darkhastFaktorVazeiatPPCModel.getCodeNoeVorod());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_txtCodeNoeVorod() , darkhastFaktorVazeiatPPCModel.getTxtCodeNoeVorod());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeVazeiat() , darkhastFaktorVazeiatPPCModel.getCodeVazeiat());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_txtCodeVazeiat() , darkhastFaktorVazeiatPPCModel.getTxtCodeVazeiat());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccDarkhastFaktorPPC() , darkhastFaktorVazeiatPPCModel.getCcDarkhastFaktorPPC());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_MablaghNahaeeFaktor() , darkhastFaktorVazeiatPPCModel.getMablaghNahaeeFaktor());
        contentValues.put(RptDarkhastFaktorVazeiatPPCModel.COLUMN_IsFaktorBaz() , darkhastFaktorVazeiatPPCModel.getIsFaktorBaz());

        return contentValues;
    }


    private ArrayList<RptDarkhastFaktorVazeiatPPCModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptDarkhastFaktorVazeiatPPCModel> darkhastFaktorVazeiatPPCModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptDarkhastFaktorVazeiatPPCModel darkhastFaktorVazeiatPPCModel = new RptDarkhastFaktorVazeiatPPCModel();

            darkhastFaktorVazeiatPPCModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorVazeiatPPCModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccMarkazForosh())));
            darkhastFaktorVazeiatPPCModel.setCcGorohForosh(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccGorohForosh())));
            darkhastFaktorVazeiatPPCModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccForoshandeh())));
            darkhastFaktorVazeiatPPCModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccMoshtary())));
            darkhastFaktorVazeiatPPCModel.setNameMarkazForosh(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_NameMarkazForosh())));
            darkhastFaktorVazeiatPPCModel.setSharhGorohForosh(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_SharhGorohForosh())));
            darkhastFaktorVazeiatPPCModel.setSharhForoshandeh(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_SharhForoshandeh())));
            darkhastFaktorVazeiatPPCModel.setFullNameForoshandeh(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_FullNameForoshandeh())));
            darkhastFaktorVazeiatPPCModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeMoshtary())));
            darkhastFaktorVazeiatPPCModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_NameMoshtary())));
            darkhastFaktorVazeiatPPCModel.setShomarehDarkhast(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ShomarehDarkhast())));
            darkhastFaktorVazeiatPPCModel.setTarikhDarkhast(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhDarkhast())));
            darkhastFaktorVazeiatPPCModel.setTarikhDarkhastWithSlash(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhDarkhastWithSlash())));
            darkhastFaktorVazeiatPPCModel.setSaatDarkhast(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_SaatDarkhast())));
            darkhastFaktorVazeiatPPCModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ShomarehFaktor())));
            darkhastFaktorVazeiatPPCModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhFaktor())));
            darkhastFaktorVazeiatPPCModel.setTarikhFaktorWithSlash(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_TarikhFaktorWithSlash())));
            darkhastFaktorVazeiatPPCModel.setRoundMablaghKhalesFaktor(cursor.getFloat(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_RoundMablaghKhalesFaktor())));
            darkhastFaktorVazeiatPPCModel.setCodeNoeVorod(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeNoeVorod())));
            darkhastFaktorVazeiatPPCModel.setTxtCodeNoeVorod(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_txtCodeNoeVorod())));
            darkhastFaktorVazeiatPPCModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_CodeVazeiat())));
            darkhastFaktorVazeiatPPCModel.setTxtCodeVazeiat(cursor.getString(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_txtCodeVazeiat())));
            darkhastFaktorVazeiatPPCModel.setCcDarkhastFaktorPPC(cursor.getLong(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_ccDarkhastFaktorPPC())));
            darkhastFaktorVazeiatPPCModel.setMablaghNahaeeFaktor(cursor.getFloat(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_MablaghNahaeeFaktor())));
            darkhastFaktorVazeiatPPCModel.setIsFaktorBaz(cursor.getInt(cursor.getColumnIndex(RptDarkhastFaktorVazeiatPPCModel.COLUMN_IsFaktorBaz())));

            darkhastFaktorVazeiatPPCModels.add(darkhastFaktorVazeiatPPCModel);
            cursor.moveToNext();
        }
        return darkhastFaktorVazeiatPPCModels;
    }
    
}
