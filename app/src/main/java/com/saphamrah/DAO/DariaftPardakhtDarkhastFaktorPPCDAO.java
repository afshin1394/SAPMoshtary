package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KardexSatrModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.VarizBeBankModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetDariaftPardakhtDarkhastFaktorHavalehPPCResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DariaftPardakhtDarkhastFaktorPPCDAO
{

    private DBHelper dbHelper;
    private Context context;


    public DariaftPardakhtDarkhastFaktorPPCDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CcDariaftPardakhtDarkhastFaktor(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDariaftPardakht(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeNoeVosol(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_NameNoeVosol(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ShomarehSanad(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_TarikhSanad(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_TarikhSanadShamsi(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_MablaghDariaftPardakht(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_Mablagh(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeVazeiat(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ZamaneTakhsiseFaktor(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccAfradErsalKonandeh(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazAnbar(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_Tabdil_NaghdBeFish(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccTafkikJoze(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_NaghlAzGhabl(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_IsForTasviehTakhir(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ZamaneTakhsiseFaktorShamsi(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsDirkard(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccKardexSatr(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsBestankari_ForTasviehTakhir(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsSend(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_CanDelete(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsTajil(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccDarkhastFaktorServer(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazForosh(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_IsTaeedShodeh(),
        };
    }

    public void fetchDariaftPardakhtDarkhastFaktorPPC(final Context context, final String activityNameForLog,final String noeFaktorHavale, final String ccDarkhastFaktor, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DariaftPardakhtDarkhastFaktorPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtDarkhastFaktorPPC", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult> call = apiServiceGet.getDariaftPardakhtDarkhastFaktorHavalehPPC(noeFaktorHavale , ccDarkhastFaktor);
            call.enqueue(new Callback<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult>() {
                @Override
                public void onResponse(Call<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult> call, Response<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, DariaftPardakhtDarkhastFaktorPPCDAO.class.getSimpleName(), "", "fetchDariaftPardakhtDarkhastFaktorPPC", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetDariaftPardakhtDarkhastFaktorHavalehPPCResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), DariaftPardakhtDarkhastFaktorPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtDarkhastFaktorPPC", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), DariaftPardakhtDarkhastFaktorPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtDarkhastFaktorPPC", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DariaftPardakhtDarkhastFaktorPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtDarkhastFaktorPPC", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), DariaftPardakhtDarkhastFaktorPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtDarkhastFaktorPPC", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), DariaftPardakhtDarkhastFaktorPPCDAO.class.getSimpleName(), activityNameForLog, "fetchDariaftPardakhtDarkhastFaktorPPC", "onFailure");
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

    @SuppressLint("LongLogTag")
    public boolean insertGroup(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels , boolean fromGetProgram)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel : dariaftPardakhtDarkhastFaktorPPCModels)
            {
                if (fromGetProgram)
                {
                    dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_IsSend(1);
                }
                ContentValues contentValues = modelToContentvalue(dariaftPardakhtDarkhastFaktorPPCModel);
                db.insertOrThrow(DariaftPardakhtDarkhastFaktorPPCModel.TableName() , null , contentValues);
                Log.d("DariaftPardakhtDarkh", "CcDariaftPardakhtDarkhastFaktor:"+dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakhtDarkhastFaktor());
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            Log.e("dariaftPardakhtDarkhastFaktor" ," insert  :" +  String.valueOf(db.isOpen()) );
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
            Log.e("dariaftPardakhtDarkhastFaktor" , "insertGroup" );
            String message = context.getResources().getString(R.string.errorGroupInsert , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString() + "\n" + dariaftPardakhtDarkhastFaktorPPCModels.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    @SuppressLint("LongLogTag")
    public long insert(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(dariaftPardakhtDarkhastFaktorPPCModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long ccDariaftPardakhtDarkhastFaktorPPC = db.insertOrThrow(DariaftPardakhtDarkhastFaktorPPCModel.TableName() , null , contentValues);
            db.close();
            Log.e("dariaftPardakhtDarkhastFaktor" , " insert  :" +String.valueOf(db.isOpen()) );
            return ccDariaftPardakhtDarkhastFaktorPPC;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            Log.e("dariaftPardakhtDarkhastFaktor" , "insert" );
            String message = context.getResources().getString(R.string.errorGroupInsert , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "insert" , "");
            return -1;
        }
    }

    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getAll()
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getAll" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCModels;
    }

    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " SELECT *  FROM ( " +
                    " 	SELECT * FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeVosol <> " + Constants.VALUE_MARJOEE() +
                    " 	UNION ALL " +
                    " 	SELECT 	0 ccDariaftPardakhtDarkhastFaktor,ccDarkhastFaktor, 0 ccDariaftPardakht,CodeNoeVosol, NameNoeVosol, 0 ShomarehSanad, TarikhSanad, TarikhSanadShamsi,SUM(MablaghDariaftPardakht) MablaghDariaftPardakht, " +
                    "			sum(Mablagh) Mablagh,0 CodeVazeiat, ZamaneTakhsiseFaktor, 0 ccAfradMamorVosol,0 ccMarkazAnbar, " +
                    "			0 AS Tabdil_NaghdBeFish, 0 AS ccTafkikJoze, 0 AS NaghlAzGhabl,0 AS IsForTasviehTakhir, ZamaneTakhsiseFaktorShamsi," +
                    "			0 AS ExtraProp_IsDirkard, 0 AS ExtraProp_ccKardexSatr," +
                    "			0 ExtraProp_IsBestankari_ForTasviehTakhir, ExtraProp_IsSend, 0 AS ExtraProp_CanDelete, 0 AS ExtraProp_IsTajil, 0 as ExtraProp_ccDarkhastFaktorServer, 0 as ccMarkazForosh, 0 as ccMarkazSazmanForoshSakhtarForosh , 0 as  ExtraProp_ccDaryaftPardakhtCheckBargashty , 0 IsTaeedShodeh " +
                    " 	FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeVosol = " + Constants.VALUE_MARJOEE() + " AND "+DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_Mablagh() + " <> 0  AND "+ DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_MablaghDariaftPardakht() + " <> 0 "+
                    " 	GROUP BY CodeNoeVosol, NameNoeVosol, TarikhSanadShamsi, ccDarkhastFaktor" + " ) A" +
                    " ORDER BY ccDariaftPardakhtDarkhastFaktor ";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCModels;
    }

    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getByccDarkhastFaktorSortMablagh(long ccDarkhastFaktor)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " SELECT *  FROM ( " +
                    " 	SELECT * FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeVosol <> " + Constants.VALUE_MARJOEE() +
                    " 	UNION ALL " +
                    " 	SELECT 	0 ccDariaftPardakhtDarkhastFaktor,ccDarkhastFaktor, 0 ccDariaftPardakht,CodeNoeVosol, NameNoeVosol, 0 ShomarehSanad, TarikhSanad, TarikhSanadShamsi,SUM(MablaghDariaftPardakht) MablaghDariaftPardakht, " +
                    "			sum(Mablagh) Mablagh,0 CodeVazeiat, ZamaneTakhsiseFaktor, 0 ccAfradMamorVosol,0 ccMarkazAnbar, " +
                    "			0 AS Tabdil_NaghdBeFish, 0 AS ccTafkikJoze, 0 AS NaghlAzGhabl,0 AS IsForTasviehTakhir, ZamaneTakhsiseFaktorShamsi," +
                    "			0 AS ExtraProp_IsDirkard, 0 AS ExtraProp_ccKardexSatr," +
                    "			0 ExtraProp_IsBestankari_ForTasviehTakhir, ExtraProp_IsSend, 0 AS ExtraProp_CanDelete, 0 AS ExtraProp_IsTajil, 0 as ExtraProp_ccDarkhastFaktorServer, 0 as ccMarkazForosh, 0 as ccMarkazSazmanForoshSakhtarForosh , 0 as  ExtraProp_ccDaryaftPardakhtCheckBargashty , 0 IsTaeedShodeh " +
                    " 	FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeVosol = " + Constants.VALUE_MARJOEE() + " AND "+DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_Mablagh() + " <> 0  AND "+ DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_MablaghDariaftPardakht() + " <> 0 "+
                    " 	GROUP BY CodeNoeVosol, NameNoeVosol, TarikhSanadShamsi, ccDarkhastFaktor" + " ) A" +
                    " ORDER BY MablaghDariaftPardakht desc  ";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCModels;
    }



    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getByccDarkhastFaktorWithoutMarjoee(long ccDarkhastFaktor)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query =
                    " 	SELECT * FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeVosol <> " + Constants.VALUE_MARJOEE() +
                    " ORDER BY ccDariaftPardakhtDarkhastFaktor ";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCModels;
    }

    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getByccDarkhastFaktorForCheckPosition(long ccDarkhastFaktor)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " SELECT *  FROM ( " +
                    " 	SELECT * FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeVosol <> " + Constants.VALUE_MARJOEE() + " AND IsTaeedShodeh=0 " +
                    " 	UNION ALL " +
                    " 	SELECT 	* " +
                    " 	FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeVosol = " + Constants.VALUE_MARJOEE() + " AND IsTaeedShodeh=0 " +
                    " 	GROUP BY CodeNoeVosol, NameNoeVosol, TarikhSanadShamsi, ccDarkhastFaktor" + " ) A" +
                    " ORDER BY ccDariaftPardakhtDarkhastFaktor ";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCModels;
    }

    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getByccDariaftPardakht(long ccDariaftPardakht)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " SELECT *  FROM " +
                    " 	SELECT * FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ccDarkhastFaktor = " + ccDariaftPardakht + " ";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCModels;
    }
    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getByccDariaftPardakht_CheckBargashty(long ccDariaftPardakht)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query =
                    " 	SELECT * FROM DariaftPardakhtDarkhastFaktorPPC " +
                    " 	WHERE ExtraProp_ccDaryaftPardakhtCheckBargashty = " + ccDariaftPardakht + " ";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCModels;
    }

    public DariaftPardakhtDarkhastFaktorPPCModel SetForInsert_DariaftPardakhtDarkhastFaktorPPC(long ccDarkhastFaktor, long ccDariaftPardakht,
                                                                                               String CodeNoeSanad, String NameNoeVosol, String ShomarehSanad, Date TarikhSanad, String TarikhSanadShamsi,
                                                                                               double MablaghDariaftPardakht, double MablaghTakhsis, int ccMarkazAnbar, int IsDirkard, long ccTafkikJoze,
                                                                                               long ccKardexSatr, int ExtraProp_IsBestankari_ForTasviehTakhir, int IsForTasviehTakhir, int ExtraProp_CanDelete, int IsTajil)

    {

        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();

        DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPC = new DariaftPardakhtDarkhastFaktorPPCModel();

        dariaftPardakhtDarkhastFaktorPPC.setCcDarkhastFaktor(ccDarkhastFaktor);
        dariaftPardakhtDarkhastFaktorPPC.setCcDariaftPardakht(ccDariaftPardakht);
        dariaftPardakhtDarkhastFaktorPPC.setCodeNoeVosol(Integer.parseInt(CodeNoeSanad));
        dariaftPardakhtDarkhastFaktorPPC.setNameNoeVosol(NameNoeVosol);
        dariaftPardakhtDarkhastFaktorPPC.setShomarehSanad(ShomarehSanad);
        dariaftPardakhtDarkhastFaktorPPC.setTarikhSanad(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(TarikhSanad));
        dariaftPardakhtDarkhastFaktorPPC.setTarikhSanadShamsi(TarikhSanadShamsi);
        dariaftPardakhtDarkhastFaktorPPC.setMablaghDariaftPardakht((long)MablaghDariaftPardakht);
        dariaftPardakhtDarkhastFaktorPPC.setMablagh((long)MablaghTakhsis);
        dariaftPardakhtDarkhastFaktorPPC.setCodeVazeiat(0);
        dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktor(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
        dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktorShamsi(new PubFunc().new DateUtils().gregorianToPersianDateTime(new Date()));
//        dariaftPardakhtDarkhastFaktorPPC.setCcAfradMamorVosol(mamorPakhsh.get(0).getCcAfrad());
        dariaftPardakhtDarkhastFaktorPPC.setCcMarkazAnbar(ccMarkazAnbar);
        dariaftPardakhtDarkhastFaktorPPC.setCcUser(foroshandehMamorPakhshModel.getCcAfrad());
        dariaftPardakhtDarkhastFaktorPPC.setCcLinkTakhirTajilFaktor(0);
//        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsOld(0);
        dariaftPardakhtDarkhastFaktorPPC.setTabdil_NaghdBeFish(0);
        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccKardexSatr(ccKardexSatr);
//        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_CodeNoeSanad(CodeNoeSanad);
        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsDirkard(IsDirkard);
        dariaftPardakhtDarkhastFaktorPPC.setCcTafkikJoze(ccTafkikJoze);
        dariaftPardakhtDarkhastFaktorPPC.setNaghlAzGhabl(0);
        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsBestankari_ForTasviehTakhir(ExtraProp_IsBestankari_ForTasviehTakhir);
        dariaftPardakhtDarkhastFaktorPPC.setIsForTasviehTakhir(IsForTasviehTakhir);
        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_CanDelete(ExtraProp_CanDelete);
        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsTajil(IsTajil);
//        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsPrint(0);
//        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_Vazeiat(0);
//        dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ShomarehElamieh(0);

        return dariaftPardakhtDarkhastFaktorPPC;
    }

    public int getCountByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select count(" + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CcDariaftPardakhtDarkhastFaktor() + ") from " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() + " where " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
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
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getCountByccDarkhastFaktor" , "");
        }
        return count;
    }

    public Date getTarikhSarResidShamsiCheck(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCs, int modatVosol, long mablaghFaktor, Date tarikhErsal, double mablaghMandeh)
    {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        SimpleDateFormat sdfDateWithoutTime = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
        String codeNoeVosolCheck = "0";
        final int ConvertToDay = (24 * 60 * 60 * 1000);
        long diffDays = 0;

        long sumMablaghTakhsisDiffdays = 0;
        int tedadRoozRaasGiri;
        Date tarikhSarResid;
        Date today = new Date();
        try
        {
            tarikhErsal = sdfDateTime.parse(sdfDateTime.format(tarikhErsal));
            today = sdfDateWithoutTime.parse(sdfDateWithoutTime.format(today));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        for (DariaftPardakhtDarkhastFaktorPPCModel model : dariaftPardakhtDarkhastFaktorPPCs)
        {
            try
            {
                Date tarikhSanad = sdfDateTime.parse(model.getTarikhSanad());
                Date zamaneTakhsiseFaktor = sdfDateWithoutTime.parse(model.getTarikhSanad());
                diffDays = 0;
                codeNoeVosolCheck = String.valueOf(model.getCodeNoeVosol());
                if(codeNoeVosolCheck.equals(Constants.VALUE_VAJH_NAGHD()) || codeNoeVosolCheck.equals(Constants.VALUE_IRANCHECK()))
                {
                    diffDays = (zamaneTakhsiseFaktor.getTime() - tarikhErsal.getTime()) / ConvertToDay;
                }
                if(codeNoeVosolCheck.equals(Constants.VALUE_POS()) || codeNoeVosolCheck.equals(Constants.VALUE_FISH_BANKI()))
                {
                    diffDays = (tarikhSanad.getTime() - tarikhErsal.getTime()) / ConvertToDay;
                }
                if(codeNoeVosolCheck.equals(Constants.VALUE_CHECK()))
                {
                    Date maxRoozTarikhSanad;
                    if(today.getTime() > tarikhSanad.getTime() )
                    {
                        maxRoozTarikhSanad = new Date();
                    }
                    else
                    {
                        maxRoozTarikhSanad = tarikhSanad;
                    }
                    diffDays = (maxRoozTarikhSanad.getTime() - tarikhErsal.getTime()) / ConvertToDay;
                }
                sumMablaghTakhsisDiffdays += model.getMablagh() * diffDays;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        int TedadRoozMazad= 10;//todo mazad

        modatVosol +=1;
        modatVosol += TedadRoozMazad;
        tedadRoozRaasGiri = (int) Math.round(((modatVosol * mablaghFaktor) - sumMablaghTakhsisDiffdays ) / mablaghMandeh);
        tarikhSarResid = new PubFunc().new DateUtils().addDay(tarikhErsal , tedadRoozRaasGiri);
        return tarikhSarResid;
    }

    public int getCountShomarehSanad(String shomarehSanad , double mablagh)
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            String query = "select count(" + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CcDariaftPardakhtDarkhastFaktor() + ") from " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() + " where NaghlAzGhabl = 0  AND ShomarehSanad = " + shomarehSanad;
            String query = "select count(" + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CcDariaftPardakhtDarkhastFaktor() + ") from " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() + " where  ShomarehSanad = " + shomarehSanad;
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
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getExistsShomarehSanad" , "");
        }
        return count;
    }

    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getForSendToSql(long ccDariaftPardakhtPPC)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() +  " WHERE  NaghlAzGhabl = 0 AND ExtraProp_IsSend = 0 AND ccDariaftPardakht = " + ccDariaftPardakhtPPC, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCs = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getForSendToSql" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCs;
    }

    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getForSendToSqlByccDariaftPardakhts(String ccDariaftPardakhtPPCs)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() +  " WHERE  NaghlAzGhabl = 0 AND ExtraProp_IsSend = 0 AND ccDariaftPardakht in ( " + ccDariaftPardakhtPPCs + " )", null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCs = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getForSendToSqlByccDariaftPardakhts" , "");
        }
        return dariaftPardakhtDarkhastFaktorPPCs;
    }

    public double getSumMablaghVajhNaghd(long ccTafkikJoze, String codeNoeVosolVajhNaghd)
    {
        double sum = 0;
        try
        {
            String query = "select sum(MablaghDariaftPardakht) from DariaftPardakhtDarkhastFaktorPPC where ExtraProp_IsSend=0 And CodeNoeVosol= " + codeNoeVosolVajhNaghd + " And ccTafkikJoze = " + ccTafkikJoze;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sum = cursor.getDouble(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getSumMablaghVajhNaghd" , "");
        }
        /*String query = "select sum("
        ExtraProp_IsSend=0 AND CodeNoeVosol="+CodeNoeVosol.VajhNaghd.getValue()*/
        return sum;
    }

    /**
     show vosol vajh naghd
    */
    public ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> getAllVajhNaghd(){
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT(), Locale.getDefault());
        String formattedDate ="'"+ df.format(c) +"'";


        try
        {
            String query = " select * from DariaftPardakhtDarkhastFaktorPPC where ZamaneTakhsiseFaktor > " + formattedDate + " and CodeNoeVosol = 1 and codevazeiat = 0 --and extraprop_issend = 0";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dariaftPardakhtDarkhastFaktorPPCModels = cursorToModel(cursor);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getAll" , "");
        }


        return dariaftPardakhtDarkhastFaktorPPCModels;
    }


     /**
    show list moshtary with mablagh
     */

    public ArrayList<VarizBeBankModel> getAllMoshtary(){
        ArrayList<VarizBeBankModel> varizBeBankModels = new ArrayList<>();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT(), Locale.getDefault());
        String formattedDate ="'"+ df.format(c) +"'";


        try
        {
            String query = "select  dpdf. ccDariaftPardakhtDarkhastFaktor,dpdf. ccDarkhastFaktor, dpdf.ccDariaftPardakht , m.namemoshtary , m.ccmoshtary, SUM(Mablagh) Mablagh\n" +
                    "from DariaftPardakhtDarkhastFaktorPPC dpdf \n" +
                    "left join darkhastfaktor d on d.ccDarkhastFaktor = dpdf.ccDarkhastFaktor\n" +
                    "left join moshtary m on m.ccMoshtary = d.ccMoshtary\n" +
                    "where dpdf.ZamaneTakhsiseFaktor > " + formattedDate + "  and dpdf.CodeNoeVosol = 1 and dpdf.codevazeiat = 0\n" +
                    "GROUP BY dpdf. ccDariaftPardakhtDarkhastFaktor,dpdf. ccDarkhastFaktor, dpdf.ccDariaftPardakht , m.namemoshtary , m.ccmoshtary";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    varizBeBankModels = cursorToModelMoshtary(cursor);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "getAll" , "");
        }


        return varizBeBankModels;
    }

    /**
     * have dirkard
     */
    public boolean HaveDirkardDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), allColumns(), " CodeNoeVosol = " + Constants.VALUE_SANAD_DIRKARD +  " AND ccDarkhastFaktor=? ",
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
    public boolean HaveDirkardBargahsty(long ccDariaftPardakht_CheckBargashty)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), allColumns(), " CodeNoeVosol = " + Constants.VALUE_SANAD_DIRKARD +  " AND ExtraProp_ccDaryaftPardakhtCheckBargashty=? ",
                    new String[]{String.valueOf(ccDariaftPardakht_CheckBargashty)}, null, null,  null);
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

    public boolean HaveTajil(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), allColumns(), " CodeNoeVosol = " + Constants.VALUE_SANAD_TAJIL + " AND ccDarkhastFaktor=? ",
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

    /**
     * update db vajh naghd to fish banki
     * @param entity
     * @return
     */
    public boolean UpdateNaghdBeFish(DariaftPardakhtDarkhastFaktorPPCModel entity)
    {
        ContentValues values = new ContentValues();
        boolean update = false;


        try
        {
            values.put("NameNoeVosol", entity.getNameNoeVosol());
            values.put("CodeNoeVosol", entity.getCodeNoeVosol());
            values.put("ShomarehSanad", entity.getShomarehSanad());
            values.put("TarikhSanadShamsi", entity.getTarikhSanadShamsi());
            values.put("TarikhSanad", entity.getTarikhSanad());


            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(DariaftPardakhtDarkhastFaktorPPCModel.getTableName(), values, "ccDariaftPardakhtDarkhastFaktor=?" , new String[]{String.valueOf(entity.getCcDariaftPardakhtDarkhastFaktor())});
            update = true;
            db.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return update;
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
            String query = "update " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() + " set " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsSend() + " = " + isSend + "  where " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty() + " = " + ccDaryaftPardakhtNew;
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

    public void Update_MablaghDariaftPardakht(long ccDariaftPardakhtDarkhastFaktor, double Mablagh)
    {
        try
        {
            String query =" UPDATE DariaftPardakhtDarkhastFaktorPPC "
                    +" SET   Mablagh= Mablagh - " +  Mablagh
                    +" Where ccDariaftPardakhtDarkhastFaktor = " + ccDariaftPardakhtDarkhastFaktor ;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void UpdateMablaghDariaftPardakht(long ccDariaftPardakhtDarkhastFaktor, double Mablagh)
    {
        try
        {
            String query =" UPDATE DariaftPardakhtDarkhastFaktorPPC "
                    +" SET   Mablagh = " +  Mablagh
                    +" Where ccDariaftPardakhtDarkhastFaktor = " + ccDariaftPardakhtDarkhastFaktor ;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void UpdateMablaghDariaftPardakhtAfterRemoveTajil()
    {
        try
        {
            String query =" UPDATE DariaftPardakhtDarkhastFaktorPPC "
                    +" SET   Mablagh =  MablaghDariaftPardakht "
                    +" Where ExtraProp_IsSend = 0 "  ;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDariaftPardakht(long ccDariaftPardakht)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDariaftPardakht() + " = " + ccDariaftPardakht, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "deleteByccDariaftPardakht" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "deleteByccDariaftPardakht" , "");
            return false;
        }
    }



    public boolean deleteMarjoeeForoshandehByccDarkhastFaktor(int ccKardexSatr,long ccDarkhastFaktor,String codeNoeVosol)
    {
        String query = "delete from " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() + " where " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " AND " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeNoeVosol() + " = " + codeNoeVosol + " AND "+ DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccKardexSatr() + " = "+ ccKardexSatr;
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "deleteMarjoeeForoshandehByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean deleteMarjoeeForoshandehByccDariaftPardakht(int ccDariaftPardakht)
    {
        String query = "delete from " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() + " where " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDariaftPardakht() + " = " + ccDariaftPardakht + " AND " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeNoeVosol() + " = " + Constants.VALUE_MARJOEE();
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "deleteMarjoeeForoshandehByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean deleteByccKardexSatr(int ccKardexSatr)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccKardexSatr() + " = " + ccKardexSatr, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "deleteByccKardexSatr" , "");
            return false;
        }
    }

    public boolean deleteDirKardAndTajil(long ccDarkhastFaktor , long ccDariaftPardakht )
    {
        String query = "";
        SQLiteDatabase db = dbHelper.getWritableDatabase();

            query = "delete from " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() + " where  ((ExtraProp_IsDirkard = 1 AND CodeNoeVosol = " + Constants.VALUE_SANAD_DIRKARD +")"
                 + "  OR (ExtraProp_IsTajil = 1 AND CodeNoeVosol =" + Constants.VALUE_SANAD_TAJIL +")) ";
        if (ccDarkhastFaktor > 0 ) {
            query+="  AND ccDarkhastFaktor= " + ccDarkhastFaktor;
        }
        else
        {
            query+= "  AND ExtraProp_ccDaryaftPardakhtCheckBargashty = " + ccDariaftPardakht;
        }
        try
        {
                db.execSQL(query);
                db.close();
                return true;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "deleteByccDariaftPardakht" , "");
                return false;
            }


        }




    public boolean deleteByccDarkhastFaktorAndCodeNoeVosol(long ccDarkhastFaktor , String codeNoeVosol)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeNoeVosol() + " = " + codeNoeVosol, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "deleteByccDarkhastFaktorAndCodeNoeVosol" , "");
            return false;
        }
    }

    public void updateExtraPropccDarkhastFaktor(long ccDarkhastFaktor, long ccDarkhatFaktor_Server)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccDarkhastFaktorServer(), ccDarkhatFaktor_Server);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(DariaftPardakhtDarkhastFaktorPPCModel.TableName(), values, DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "updateExtraPropccDarkhastFaktor" , "");
        }
    }

    public boolean updateSendedDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew , int isSend)
    {
        try
        {
            String query = "update " + DariaftPardakhtDarkhastFaktorPPCModel.TableName() + " set " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsSend() + " = " + isSend + " , " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew +
                    " where " + DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DariaftPardakhtDarkhastFaktorPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DariaftPardakhtDarkhastFaktorPPCDAO" , "" , "updateSendedDarkhastFaktor" , "");
            return true;
        }
    }

    private static ContentValues modelToContentvalue(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel)
    {
        ContentValues contentValues = new ContentValues();

        if (dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakhtDarkhastFaktor() > 0)
        {
            contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CcDariaftPardakhtDarkhastFaktor() , dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakhtDarkhastFaktor());
        }
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor() , dariaftPardakhtDarkhastFaktorPPCModel.getCcDarkhastFaktor());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDariaftPardakht() , dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakht());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeNoeVosol() , dariaftPardakhtDarkhastFaktorPPCModel.getCodeNoeVosol());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_NameNoeVosol() , dariaftPardakhtDarkhastFaktorPPCModel.getNameNoeVosol());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ShomarehSanad() , dariaftPardakhtDarkhastFaktorPPCModel.getShomarehSanad());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_TarikhSanad() , dariaftPardakhtDarkhastFaktorPPCModel.getTarikhSanad());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_TarikhSanadShamsi() , dariaftPardakhtDarkhastFaktorPPCModel.getTarikhSanadShamsi());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_MablaghDariaftPardakht() , dariaftPardakhtDarkhastFaktorPPCModel.getMablaghDariaftPardakht());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_Mablagh() , dariaftPardakhtDarkhastFaktorPPCModel.getMablagh());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeVazeiat() , dariaftPardakhtDarkhastFaktorPPCModel.getCodeVazeiat());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ZamaneTakhsiseFaktor() , dariaftPardakhtDarkhastFaktorPPCModel.getZamaneTakhsiseFaktor());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccAfradErsalKonandeh() , dariaftPardakhtDarkhastFaktorPPCModel.getCcAfradErsalKonandeh());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazAnbar() , dariaftPardakhtDarkhastFaktorPPCModel.getCcMarkazAnbar());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_Tabdil_NaghdBeFish() , dariaftPardakhtDarkhastFaktorPPCModel.getTabdil_NaghdBeFish());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccTafkikJoze() , dariaftPardakhtDarkhastFaktorPPCModel.getCcTafkikJoze());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_NaghlAzGhabl() , dariaftPardakhtDarkhastFaktorPPCModel.getNaghlAzGhabl());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_IsForTasviehTakhir() , dariaftPardakhtDarkhastFaktorPPCModel.getIsForTasviehTakhir());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ZamaneTakhsiseFaktorShamsi() , dariaftPardakhtDarkhastFaktorPPCModel.getZamaneTakhsiseFaktorShamsi());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsDirkard() , dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_IsDirkard());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccKardexSatr() , dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_ccKardexSatr());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsBestankari_ForTasviehTakhir() , dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_IsBestankari_ForTasviehTakhir());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsSend() , dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_IsSend());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_CanDelete() , dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_CanDelete());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsTajil() , dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_IsTajil());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccDarkhastFaktorServer() , dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_ccDarkhastFaktorServer());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazForosh() , dariaftPardakhtDarkhastFaktorPPCModel.getCcMarkazForosh());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , dariaftPardakhtDarkhastFaktorPPCModel.getCcMarkazSazmanForoshSakhtarForosh());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty() , dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_ccDaryaftPardakhtCheckBargashty());
        contentValues.put(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_IsTaeedShodeh() , dariaftPardakhtDarkhastFaktorPPCModel.getIsTaeedShodeh());

        return contentValues;
    }


    private ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel = new DariaftPardakhtDarkhastFaktorPPCModel();

            dariaftPardakhtDarkhastFaktorPPCModel.setCcDariaftPardakhtDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CcDariaftPardakhtDarkhastFaktor())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDarkhastFaktor())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCcDariaftPardakht(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccDariaftPardakht())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCodeNoeVosol(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeNoeVosol())));
            dariaftPardakhtDarkhastFaktorPPCModel.setNameNoeVosol(cursor.getString(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_NameNoeVosol())));
            dariaftPardakhtDarkhastFaktorPPCModel.setShomarehSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ShomarehSanad())));
            dariaftPardakhtDarkhastFaktorPPCModel.setTarikhSanad(cursor.getString(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_TarikhSanad())));
            dariaftPardakhtDarkhastFaktorPPCModel.setTarikhSanadShamsi(cursor.getString(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_TarikhSanadShamsi())));
            dariaftPardakhtDarkhastFaktorPPCModel.setMablaghDariaftPardakht(cursor.getDouble(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_MablaghDariaftPardakht())));
            dariaftPardakhtDarkhastFaktorPPCModel.setMablagh(cursor.getDouble(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_Mablagh())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_CodeVazeiat())));
            dariaftPardakhtDarkhastFaktorPPCModel.setZamaneTakhsiseFaktor(cursor.getString(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ZamaneTakhsiseFaktor())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCcAfradErsalKonandeh(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccAfradErsalKonandeh())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCcMarkazAnbar(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazAnbar())));
            dariaftPardakhtDarkhastFaktorPPCModel.setTabdil_NaghdBeFish(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_Tabdil_NaghdBeFish())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCcTafkikJoze(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccTafkikJoze())));
            dariaftPardakhtDarkhastFaktorPPCModel.setNaghlAzGhabl(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_NaghlAzGhabl())));
            dariaftPardakhtDarkhastFaktorPPCModel.setIsForTasviehTakhir(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_IsForTasviehTakhir())));
            dariaftPardakhtDarkhastFaktorPPCModel.setZamaneTakhsiseFaktorShamsi(cursor.getString(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ZamaneTakhsiseFaktorShamsi())));
            dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_IsDirkard(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsDirkard())));
            dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_ccKardexSatr(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccKardexSatr())));
            dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_IsBestankari_ForTasviehTakhir(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsBestankari_ForTasviehTakhir())));
            dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsSend())));
            dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_CanDelete(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_CanDelete())));
            dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_IsTajil(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_IsTajil())));
            dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_ccDarkhastFaktorServer(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccDarkhastFaktorServer())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazForosh())));
            dariaftPardakhtDarkhastFaktorPPCModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));
            dariaftPardakhtDarkhastFaktorPPCModel.setExtraProp_ccDaryaftPardakhtCheckBargashty(cursor.getLong(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty())));
            dariaftPardakhtDarkhastFaktorPPCModel.setIsTaeedShodeh(cursor.getInt(cursor.getColumnIndex(DariaftPardakhtDarkhastFaktorPPCModel.COLUMN_IsTaeedShodeh())));
            dariaftPardakhtDarkhastFaktorPPCModels.add(dariaftPardakhtDarkhastFaktorPPCModel);
            cursor.moveToNext();
        }
        return dariaftPardakhtDarkhastFaktorPPCModels;
    }


    /**
     * use it from getAllMoshtary
     * @param cursor
     * @return
     */
    private ArrayList<VarizBeBankModel> cursorToModelMoshtary(Cursor cursor)
    {
        ArrayList<VarizBeBankModel> varizBeBankModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            VarizBeBankModel model = new VarizBeBankModel();

            model.setCcDariaftPardakht(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccDariaftPardakht())));
            model.setNamemoshtary(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_namemoshtary())));
            model.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccmoshtary())));
            model.setMablagh(cursor.getLong(cursor.getColumnIndex(model.getCOLUMN_Mablagh())));
            model.setCodeNoeVosol(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_CodeNoeVosol())));
            model.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ExtraProp_IsSend())));
            model.setExtraProp_IsSelected(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ExtraProp_IsSelected())));
            model.setTaeed(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_Taeed())));
            varizBeBankModels.add(model);
            cursor.moveToNext();
        }
        return varizBeBankModels;
    }


}
