package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KardexSatrModel;
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
            DariaftPardakhtPPCModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            DariaftPardakhtPPCModel.COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty()

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

    public boolean insertGroup(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels , boolean fromGetProgram)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DariaftPardakhtPPCModel dariaftPardakhtPPCModel : dariaftPardakhtPPCModels)
            {
                if (fromGetProgram)
                {
                    dariaftPardakhtPPCModel.setExtraProp_IsSend(1);
                }
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

    public DariaftPardakhtPPCModel SetForInsert_DariaftPardakhtPPC(
            int ccMarkazAnbar, String CodeNoeSanad, String NameNoeSanad, int ccShomarehHesab,
            String SharhShomarehHesab,String NameSahebHesab, int ccBankSanad, String NameBankSanad,
            String NameShobehSanad, String CodeShobehSanad, String ShomarehHesabSanad, int CodeNoeCheck,
            String ShomarehSanad, String TarikhSanadShamsi, double Mablagh, double MablaghMandeh,
            int ccMoshtary, int IsPishDariaft, long ccDarkhastFaktor, int IsDirkard, String TarikhSarResidMilady, int IsTajil, int CodeNoeVosol )
    {

        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(context);
        ForoshandehMamorPakhshModel ForoshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();

        DariaftPardakhtPPCModel dariaftPardakhtPPC = new DariaftPardakhtPPCModel();

        dariaftPardakhtPPC.setCcMarkazAnbar(ccMarkazAnbar);
//        dariaftPardakhtPPC.setCodeNoeSanad(CodeNoeSanad);
//        dariaftPardakhtPPC.setNameNoeSanad(NameNoeSanad);
        dariaftPardakhtPPC.setCcShomarehHesab(ccShomarehHesab);
        dariaftPardakhtPPC.setSharhShomarehHesab(SharhShomarehHesab);
        dariaftPardakhtPPC.setCodeNoeVosol(Integer.parseInt(CodeNoeSanad));
        // check shavad
        dariaftPardakhtPPC.setZamaneSabt(String.valueOf(0));
        dariaftPardakhtPPC.setNameSahebHesab(NameSahebHesab);
        dariaftPardakhtPPC.setCcBankSanad(ccBankSanad);
        dariaftPardakhtPPC.setNameBankSanad(NameBankSanad);
        dariaftPardakhtPPC.setNameShobehSanad(NameShobehSanad);
        dariaftPardakhtPPC.setCodeShobehSanad(CodeShobehSanad);
        dariaftPardakhtPPC.setShomarehHesabSanad(ShomarehHesabSanad);
        dariaftPardakhtPPC.setCcNoeHesabSanad(0);
        dariaftPardakhtPPC.setNameNoeHesabSanad("");
        dariaftPardakhtPPC.setCcShahrCheck(String.valueOf(0));
        dariaftPardakhtPPC.setCodeNoeCheck(CodeNoeCheck);
        dariaftPardakhtPPC.setNameNoeCheck("");
        dariaftPardakhtPPC.setShomarehSanad(ShomarehSanad);
        dariaftPardakhtPPC.setTarikhSanadShamsi(TarikhSanadShamsi);
        dariaftPardakhtPPC.setTarikhSanad(TarikhSarResidMilady);
        dariaftPardakhtPPC.setMablagh(Mablagh);
        dariaftPardakhtPPC.setMablaghMandeh(MablaghMandeh);
        dariaftPardakhtPPC.setCcDariaftPardakhtLink(0);
//        dariaftPardakhtPPC.setccUser(mamorPakhsh.get(0).getCcAfrad());
        dariaftPardakhtPPC.setCcKardex(0);
        dariaftPardakhtPPC.setCodeVazeiat(0);
//        dariaftPardakhtPPC.setPos(0);
//        dariaftPardakhtPPC.setccvTafsily0(ccvTafsily0);
        dariaftPardakhtPPC.setIsPishDariaft(IsPishDariaft);
//        dariaftPardakhtPPC.setccLinkTakhirTajil(0);
//        dariaftPardakhtPPC.setExtraProp_IsOld(0);
//        dariaftPardakhtPPC.setccDarkhastFaktor(ccDarkhastFaktor);
//        dariaftPardakhtPPC.setExtraProp_CodeNoeSanad(CodeNoeSanad);
        dariaftPardakhtPPC.setExtraProp_IsDirkard(IsDirkard);
        dariaftPardakhtPPC.setTabdil_NaghdBeFish(0);
//        dariaftPardakhtPPC.setExtraProp_IsTajil(IsTajil);
        dariaftPardakhtPPC.setCodeNoeVosol(CodeNoeVosol);
        dariaftPardakhtPPC.setCcMoshtary(ccMoshtary);

        return dariaftPardakhtPPC;
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
    public ArrayList<DariaftPardakhtPPCModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtPPCModel.TableName(), allColumns(), DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
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

    public ArrayList<DariaftPardakhtPPCModel> getByccMoshtaryPishdariaft(long ccMoshtary)
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = new ArrayList<>();
        String query = "SELECT * FROM "+ DariaftPardakhtPPCModel.TableName() + " WHERE "+ DariaftPardakhtPPCModel.COLUMN_ccMoshtary() +" = " + ccMoshtary +" AND IsPishDariaft = " + Constants.FROM_PISH_DARYAFT + " ";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

//            Cursor cursor = db.query(DariaftPardakhtPPCModel.TableName(), allColumns(),  + " = " + ccMoshtary + " AND IsPishDariaft = " + 1 , null, null, null, null);
            Cursor cursor = db.rawQuery(query , null);
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

    public ArrayList<DariaftPardakhtPPCModel> getForSendToSqlByccDariaftPardakht(long ccDariaftPardakht)
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(" SELECT * FROM DariaftPardakhtPPC dp "
                    +" WHERE NaghlAzGhabl = 0 AND ExtraProp_IsSend = 0 AND ccDariaftPardakht> 0 AND ccDariaftPardakht= ? " +
                    "			AND (SELECT Count(*) FROM DariaftPardakhtDarkhastFaktorPPC WHERE ccDariaftPardakht = dp.ccDariaftPardakht) >= 1 "
                    + " ", new String[]{String.valueOf(ccDariaftPardakht)});
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

    public ArrayList<DariaftPardakhtPPCModel> getForSendToSqlByccMoshtary(int ccMoshtary)
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtPPCModel.TableName(), allColumns(), " ccMoshtary= " + ccMoshtary + " AND ExtraProp_IsSend = " + 0, null, null, null, null);

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

    public boolean haveTajilDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtPPCModel.TableName(), allColumns(), " CodeNoeVosol = " + Constants.VALUE_SANAD_TAJIL + " AND ccDarkhastFaktor=? ",
                    new String[]{String.valueOf(ccDarkhastFaktor)}, null, null,  null);
            cursor.moveToFirst();
            if(cursor.getCount() !=0)
                return true;
            cursor.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return false;
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

    public boolean deleteByIsPishDaryaft(long ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtPPCModel.TableName(), DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakht() + " = " + ccMoshtary + " AND IsPishDariaft = " + Constants.FROM_PISH_DARYAFT + " ", null);
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

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtPPCModel.TableName(), DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "deleteByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean deleteByccDariaftPardakht_checkBargashty(long ccDariaftPardakht)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtPPCModel.TableName(), DariaftPardakhtPPCModel.COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty() + " = " + ccDariaftPardakht, null);
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

    public boolean deleteMarjoeeForoshandehByccDarkhastFaktor(String ccDarkhastFaktor)
    {
        String query = "delete from " + DariaftPardakhtPPCModel.TableName() + " where " + DariaftPardakhtPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCModelDAO" , "" , "deleteMarjoeeForoshandehByccDarkhastFaktor" , "");
            return false;
        }
    }


    public boolean deleteMarjoeeForoshandehByccMoshtary(int ccMoshtary)
    {
        String query = "delete from " + DariaftPardakhtPPCModel.TableName() + " where " + DariaftPardakhtPPCModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " AND " + DariaftPardakhtPPCModel.COLUMN_CodeNoeVosol() + " = " + Constants.VALUE_MARJOEE();
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCModelDAO" , "" , "deleteMarjoeeForoshandehByccDarkhastFaktor" , "");
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

    /**
     * update DB when send details check bargashty to server
     * @param ccDaryaftPardakhtNew
     * @param ccDaryaftPardakhtOld
     * @param isSend
     * @return
     */
    public boolean updateSendedCheckBargashty(long ccDaryaftPardakhtNew , long ccDaryaftPardakhtOld , int isSend)
    {
        try
        {
            String query = "update " + DariaftPardakhtPPCModel.TableName() + " set " + DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsSend() + " = " + isSend + "  where " + DariaftPardakhtPPCModel.COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty() + " = " + ccDaryaftPardakhtNew;
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


    /**
     * update DB when send details pish daryaft  to server
     * @param ccMoshtaryGetServer
     * @param isSend
     * @return
     */

    public boolean updateSendedPishDaryaft(long ccMoshtaryGetServer  , int isSend)
    {
        try
        {
            String query = "update " + DariaftPardakhtPPCModel.TableName() + " set " + DariaftPardakhtPPCModel.COLUMN_ExtraProp_IsSend() + " = " + isSend +
                    " where " + DariaftPardakhtPPCModel.COLUMN_ccMoshtary() + " = " + ccMoshtaryGetServer;
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

    /**
     * update db vajh naghd to fish banki
     * @param entity
     * @return
     */
    public boolean UpdateNaghdBeFish(DariaftPardakhtPPCModel entity)
    {
        ContentValues values = new ContentValues();
        boolean update = false;
        try
        {
            values.put("CodeNoeVosol" , entity.getCodeNoeVosol());
            values.put("ShomarehHesabSanad" , entity.getShomarehHesabSanad());
            values.put("TarikhSanadShamsi", entity.getTarikhSanadShamsi());
            values.put("TarikhSanad", entity.getTarikhSanad());
            values.put("NameBankSanad" , entity.getNameBankSanad());
            values.put("CodeShobehSanad" , entity.getCodeShobehSanad());
            values.put("NameShobehSanad" , entity.getNameShobehSanad());
            values.put("ShomarehHesabSanad" , entity.getShomarehHesabSanad());
            values.put("Tabdil_NaghdBeFish" , entity.getTabdil_NaghdBeFish());
            values.put("Mablagh" , entity.getMablagh());
            values.put("NameNoeVosol",entity.getNameNoeVosol());
            values.put("NameSahebHesab", "");

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.update(DariaftPardakhtPPCModel.TableName(), values, "ccDariaftPardakht=" +entity.getCcDariaftPardakht(),null);

            update= true;
            db.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }


        return update;
    }

    public boolean updateMablaghMarjoee(long ccDariaftPardakht , double Mablagh)
    {
        try
        {
            String query = "update " + DariaftPardakhtPPCModel.TableName() + " set " + DariaftPardakhtPPCModel.COLUMN_ccDariaftPardakht() + " = " + ccDariaftPardakht +
                    " where " + DariaftPardakhtPPCModel.COLUMN_Mablagh() + " = " + Mablagh;
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtPPCDAO" , "" , "updateMablaghMarjoee" , "");
            return false;
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
        contentValues.put(DariaftPardakhtPPCModel.COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty(), dariaftPardakhtPPCModel.getExtraProp_ccDaryaftPardakhtCheckBargashty());
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
            dariaftPardakhtPPCModel.setMablagh(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_Mablagh())));
            dariaftPardakhtPPCModel.setMablaghMandeh(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_MablaghMandeh())));
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
            dariaftPardakhtPPCModel.setExtraProp_ccDaryaftPardakhtCheckBargashty(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtPPCModel.COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty())));
            dariaftPardakhtPPCModels.add(dariaftPardakhtPPCModel);
            cursor.moveToNext();
        }
        return dariaftPardakhtPPCModels;
    }
    
    
}
