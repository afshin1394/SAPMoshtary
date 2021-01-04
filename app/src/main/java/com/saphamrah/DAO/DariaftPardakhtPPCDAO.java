package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetDariaftPardakhtHavalePPCResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DariaftPardakhtPPCDAO
{


    private DBHelper dbHelper;
    private Context context;


    public DariaftPardakhtPPCDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DariaftPardakhtPPCDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakht(),
            DariaftPardakhtPPCModel.COLUMN_ccMarkazAnbar(),
            DariaftPardakhtPPCModel.COLUMN_CodeNoeVosol(),
            DariaftPardakhtPPCModel.COLUMN_NameNoeVosol(),
            DariaftPardakhtPPCModel.COLUMN_ccShomarehHesab(),
            DariaftPardakhtPPCModel.COLUMN_SharhShomarehHesab(),
            DariaftPardakhtPPCModel.COLUMN_ZamaneSabt(),
            DariaftPardakhtPPCModel.COLUMN_NameSahebHesab(),
            DariaftPardakhtPPCModel.COLUMN_ccBankSanad(),
            DariaftPardakhtPPCModel.COLUMN_NameBankSanad(),
            DariaftPardakhtPPCModel.COLUMN_NameShobehSanad(),
            DariaftPardakhtPPCModel.COLUMN_CodeShobehSanad(),
            DariaftPardakhtPPCModel.COLUMN_ShomarehHesabSanad(),
            DariaftPardakhtPPCModel.COLUMN_ccNoeHesabSanad(),
            DariaftPardakhtPPCModel.COLUMN_NameNoeHesabSanad(),
            DariaftPardakhtPPCModel.COLUMN_CodeNoeCheck(),
            DariaftPardakhtPPCModel.COLUMN_NameNoeCheck(),
            DariaftPardakhtPPCModel.COLUMN_ShomarehSanad(),
            DariaftPardakhtPPCModel.COLUMN_TarikhSanad(),
            DariaftPardakhtPPCModel.COLUMN_TarikhSanadShamsi(),
            DariaftPardakhtPPCModel.COLUMN_Mablagh(),
            DariaftPardakhtPPCModel.COLUMN_MablaghMandeh(),
            DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakhtLink(),
            DariaftPardakhtPPCModel.COLUMN_ccKardex(),
            DariaftPardakhtPPCModel.COLUMN_CodeVazeiat(),
            DariaftPardakhtPPCModel.COLUMN_ccMoshtary(),
            DariaftPardakhtPPCModel.COLUMN_IsPishDariaft(),
            DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor(),
            DariaftPardakhtPPCModel.COLUMN_Tabdil_NaghdBeFish(),
            DariaftPardakhtPPCModel.COLUMN_NaghlAzGhabl(),
            DariaftPardakhtPPCModel.COLUMN_ccBrand(),
            DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsDirkard(),
            DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsSend(),
            DariaftPardakhtPPCModel.COLUMN_ccAfradErsalKonandeh(),
            DariaftPardakhtPPCModel.COLUMN_IsCheckMoshtary(),
            DariaftPardakhtPPCModel.COLUMN_ccMarkazForosh(),
            DariaftPardakhtPPCModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh()
        };
    }

    public void fetchDariaftPardakhtPPC(final Context context, final String activityNameForLog,final String noeFaktorHavale, final String ccDarkhastFaktors, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DariaftPardakhtPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtPPC", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetDariaftPardakhtHavalePPCResult> call = apiServiceGet.getDariaftPardakhtHavalePPC(noeFaktorHavale , ccDarkhastFaktors);
            call.enqueue(new Callback<GetDariaftPardakhtHavalePPCResult>() {
                @Override
                public void onResponse(Call<GetDariaftPardakhtHavalePPCResult> call, Response<GetDariaftPardakhtHavalePPCResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, DariaftPardakhtPPCDAO.class.getSimpleName(), "", "fetchDariaftPardakhtPPC", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetDariaftPardakhtHavalePPCResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), DariaftPardakhtPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtPPC", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), DariaftPardakhtPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtPPC", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DariaftPardakhtPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtPPC", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), DariaftPardakhtPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtPPC", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }
                @Override
                public void onFailure(Call<GetDariaftPardakhtHavalePPCResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), DariaftPardakhtPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtPPC", "onFailure");
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

    public boolean insertGroup(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DariaftPardakhtPPCModel dariaftPardakhtPPCModel : dariaftPardakhtPPCModels)
            {
                ContentValues contentValues = modelToContentvalue(dariaftPardakhtPPCModel);
                db.insertOrThrow(DariaftPardakhtPPCModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public long insert(DariaftPardakhtPPCModel dariaftPardakhtPPCModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(dariaftPardakhtPPCModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long ccDariaftPardakhtPPCModel = db.insertOrThrow(DariaftPardakhtPPCModel.TableName() , null , contentValues);
            db.close();
            return ccDariaftPardakhtPPCModel;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "insert" , "");
            return -1;
        }
    }

    public ArrayList<DariaftPardakhtPPCModel> getAll()
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtPPCModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "getAll" , "");
        }
        return dariaftPardakhtPPCModels;
    }

    public ArrayList<DariaftPardakhtPPCModel> getByccMoshtary(int ccMoshtary)
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtPPCModel.TableName(), allColumns(), DariaftPardakhtPPCModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "getByccMoshtary" , "");
        }
        return dariaftPardakhtPPCModels;
    }

    public DariaftPardakhtPPCModel getByccDariaftPardakht(long ccDariaftPardakht)
    {
        DariaftPardakhtPPCModel dariaftPardakhtPPCModel = new DariaftPardakhtPPCModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtPPCModel.TableName(), allColumns(), DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakht() + " = " + ccDariaftPardakht, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtPPCModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "getByccMoshtary" , "");
        }
        return dariaftPardakhtPPCModel;
    }

    public long getMarjoeeByccMoshtary(int ccMoshtary , String codeNoeVosolMarjoee)
    {
        long ccDariaftPardakht = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtPPCModel.TableName(), allColumns(), "ccMoshtary=" + ccMoshtary + " AND CodeNoeVosol = " + codeNoeVosolMarjoee, null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    ccDariaftPardakht = cursorToModel(cursor).get(0).getCcDariaftPardakht();
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "getMarjoeeByccMoshtary" , "");
        }
        return ccDariaftPardakht;
    }

    public ArrayList<DariaftPardakhtPPCModel> getForSendToSqlByccDarkhastFaktor(long ccDarkhatFaktor)
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(" SELECT * FROM DariaftPardakhtPPC dp "
                    +" WHERE NaghlAzGhabl = 0 AND ExtraProp_IsSend = 0 AND ccDariaftPardakht> 0 AND ccDarkhastFaktor= ? " +
                    "			AND (SELECT Count(*) FROM DariaftPardakhtDarkhastFaktorPPC WHERE ccDariaftPardakht = dp.ccDariaftPardakht) >= 1 "
                    + " ", new String[]{String.valueOf(ccDarkhatFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtPPCs = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "getForSendToSqlByccDarkhastFaktor" , "");
        }
        return dariaftPardakhtPPCs;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtPPCModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDariaftPardakht(long ccDariaftPardakht)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtPPCModel.TableName(), DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakht() + " = " + ccDariaftPardakht, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "deleteByccDariaftPardakht" , "");
            return false;
        }
    }

    public boolean deleteByccDariaftPardakhts(String ccDariaftPardakhts)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtPPCModel.TableName(), DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakht() + " in (" + ccDariaftPardakhts + ")", null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "deleteByccDariaftPardakhts" , "");
            return false;
        }
    }


    public boolean deleteByccDarkhastFaktorAndCodeNoeVosol(long ccDarkhastFaktor , String codeNoeVosol)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtPPCModel.TableName(), DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DariaftPardakhtPPCModel.COLUMN_CodeNoeVosol() + " = " + codeNoeVosol, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "deleteByccDarkhastFaktorAndCodeNoeVosol" , "");
            return false;
        }
    }

    public boolean updateSendedDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew , int isSend)
    {
        try
        {
            String query = "update " + DariaftPardakhtPPCModel.TableName() + " set " + DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsSend() + " = " + isSend + " , " + DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew +
                    " where " + DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "updateSendedDarkhastFaktor" , "");
            return true;
        }
    }

    private static ContentValues modelToContentvalue(DariaftPardakhtPPCModel dariaftPardakhtPPCModel)
    {
        ContentValues contentValues = new ContentValues();

        if (dariaftPardakhtPPCModel.getCcDariaftPardakht() > 0)
        {
            contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakht() , dariaftPardakhtPPCModel.getCcDariaftPardakht());
        }
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccMarkazAnbar() , dariaftPardakhtPPCModel.getCcMarkazAnbar());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_CodeNoeVosol() , dariaftPardakhtPPCModel.getCodeNoeVosol());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_NameNoeVosol() , dariaftPardakhtPPCModel.getNameNoeVosol());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccShomarehHesab() , dariaftPardakhtPPCModel.getCcShomarehHesab());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_SharhShomarehHesab() , dariaftPardakhtPPCModel.getSharhShomarehHesab());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ZamaneSabt() , dariaftPardakhtPPCModel.getZamaneSabt());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_NameSahebHesab() , dariaftPardakhtPPCModel.getNameSahebHesab());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccBankSanad() , dariaftPardakhtPPCModel.getCcBankSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_NameBankSanad() , dariaftPardakhtPPCModel.getNameBankSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_NameShobehSanad() , dariaftPardakhtPPCModel.getNameShobehSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_CodeShobehSanad() , dariaftPardakhtPPCModel.getCodeShobehSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ShomarehHesabSanad() , dariaftPardakhtPPCModel.getShomarehHesabSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccNoeHesabSanad() , dariaftPardakhtPPCModel.getCcNoeHesabSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_NameNoeHesabSanad() , dariaftPardakhtPPCModel.getNameNoeHesabSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_CodeNoeCheck() , dariaftPardakhtPPCModel.getCodeNoeCheck());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_NameNoeCheck() , dariaftPardakhtPPCModel.getNameNoeCheck());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ShomarehSanad() , dariaftPardakhtPPCModel.getShomarehSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_TarikhSanad() , dariaftPardakhtPPCModel.getTarikhSanad());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_TarikhSanadShamsi() , dariaftPardakhtPPCModel.getTarikhSanadShamsi());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_Mablagh() , dariaftPardakhtPPCModel.getMablagh());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_MablaghMandeh() , dariaftPardakhtPPCModel.getMablaghMandeh());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakhtLink() , dariaftPardakhtPPCModel.getCcDariaftPardakhtLink());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccKardex() , dariaftPardakhtPPCModel.getCcKardex());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_CodeVazeiat() , dariaftPardakhtPPCModel.getCodeVazeiat());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccMoshtary() , dariaftPardakhtPPCModel.getCcMoshtary());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_IsPishDariaft() , dariaftPardakhtPPCModel.getIsPishDariaft());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor() , dariaftPardakhtPPCModel.getCcDarkhastFaktor());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_Tabdil_NaghdBeFish() , dariaftPardakhtPPCModel.getTabdil_NaghdBeFish());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_NaghlAzGhabl() , dariaftPardakhtPPCModel.getNaghlAzGhabl());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccBrand() , dariaftPardakhtPPCModel.getCcBrand());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsDirkard() , dariaftPardakhtPPCModel.getExtraProp_IsDirkard());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsSend() , dariaftPardakhtPPCModel.getExtraProp_IsSend());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccBrand() , dariaftPardakhtPPCModel.getCcBrand());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccAfradErsalKonandeh() , dariaftPardakhtPPCModel.getCcAfradErsalKonandeh());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_IsCheckMoshtary() , dariaftPardakhtPPCModel.getIsCheckMoshtary());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccMarkazForosh() , dariaftPardakhtPPCModel.getCcMarkazForosh());
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , dariaftPardakhtPPCModel.getCcMarkazSazmanForoshSakhtarForosh());

        return contentValues;
    }


    private ArrayList<DariaftPardakhtPPCModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DariaftPardakhtPPCModel dariaftPardakhtPPCModel = new DariaftPardakhtPPCModel();

            dariaftPardakhtPPCModel.setCcDariaftPardakht(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakht())));
            dariaftPardakhtPPCModel.setCcMarkazAnbar(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccMarkazAnbar())));
            dariaftPardakhtPPCModel.setCodeNoeVosol(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_CodeNoeVosol())));
            dariaftPardakhtPPCModel.setNameNoeVosol(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_NameNoeVosol())));
            dariaftPardakhtPPCModel.setCcShomarehHesab(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccShomarehHesab())));
            dariaftPardakhtPPCModel.setSharhShomarehHesab(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_SharhShomarehHesab())));
            dariaftPardakhtPPCModel.setZamaneSabt(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ZamaneSabt())));
            dariaftPardakhtPPCModel.setNameSahebHesab(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_NameSahebHesab())));
            dariaftPardakhtPPCModel.setCcBankSanad(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccBankSanad())));
            dariaftPardakhtPPCModel.setNameBankSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_NameBankSanad())));
            dariaftPardakhtPPCModel.setNameShobehSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_NameShobehSanad())));
            dariaftPardakhtPPCModel.setCodeShobehSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_CodeShobehSanad())));
            dariaftPardakhtPPCModel.setShomarehHesabSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ShomarehHesabSanad())));
            dariaftPardakhtPPCModel.setCcNoeHesabSanad(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccNoeHesabSanad())));
            dariaftPardakhtPPCModel.setNameNoeHesabSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_NameNoeHesabSanad())));
            dariaftPardakhtPPCModel.setCodeNoeCheck(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_CodeNoeCheck())));
            dariaftPardakhtPPCModel.setNameNoeCheck(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_NameNoeCheck())));
            dariaftPardakhtPPCModel.setShomarehSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ShomarehSanad())));
            dariaftPardakhtPPCModel.setTarikhSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_TarikhSanad())));
            dariaftPardakhtPPCModel.setTarikhSanadShamsi(cursor.getString(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_TarikhSanadShamsi())));
            dariaftPardakhtPPCModel.setMablagh(cursor.getDouble(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_Mablagh())));
            dariaftPardakhtPPCModel.setMablaghMandeh(cursor.getFloat(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_MablaghMandeh())));
            dariaftPardakhtPPCModel.setCcDariaftPardakhtLink(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakhtLink())));
            dariaftPardakhtPPCModel.setCcKardex(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccKardex())));
            dariaftPardakhtPPCModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_CodeVazeiat())));
            dariaftPardakhtPPCModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccMoshtary())));
            dariaftPardakhtPPCModel.setIsPishDariaft(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_IsPishDariaft())));
            dariaftPardakhtPPCModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor())));
            dariaftPardakhtPPCModel.setTabdil_NaghdBeFish(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_Tabdil_NaghdBeFish())));
            dariaftPardakhtPPCModel.setNaghlAzGhabl(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_NaghlAzGhabl())));
            dariaftPardakhtPPCModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccBrand())));
            dariaftPardakhtPPCModel.setExtraProp_IsDirkard(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsDirkard())));
            dariaftPardakhtPPCModel.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsSend())));
            dariaftPardakhtPPCModel.setCcAfradErsalKonandeh(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccAfradErsalKonandeh())));
            dariaftPardakhtPPCModel.setIsCheckMoshtary(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_IsCheckMoshtary())));
            dariaftPardakhtPPCModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccMarkazForosh())));
            dariaftPardakhtPPCModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));

            dariaftPardakhtPPCModels.add(dariaftPardakhtPPCModel);
            cursor.moveToNext();
        }
        return dariaftPardakhtPPCModels;
    }
    
    
}
