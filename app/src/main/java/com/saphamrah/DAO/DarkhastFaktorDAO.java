package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.KalaDarkhastFaktorModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetDarkhastFaktorResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarkhastFaktorDAO
{

    private DBHelper dbHelper;
    private Context context;


    public DarkhastFaktorDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            DarkhastFaktorModel.COLUMN_ccDarkhastFaktor(),
            DarkhastFaktorModel.COLUMN_TarikhFaktor(),
            DarkhastFaktorModel.COLUMN_ShomarehDarkhast(),
            DarkhastFaktorModel.COLUMN_ShomarehFaktor(),
            DarkhastFaktorModel.COLUMN_ccForoshandeh(),
            DarkhastFaktorModel.COLUMN_ccMoshtary(),
            DarkhastFaktorModel.COLUMN_TarikhErsal(),
            DarkhastFaktorModel.COLUMN_TarikhPishbinyTahvil(),
            DarkhastFaktorModel.COLUMN_ModatRoozRaasGiri(),
            DarkhastFaktorModel.COLUMN_ModateVosol(),
            DarkhastFaktorModel.COLUMN_CodeNoeVosolAzMoshtary(),
            DarkhastFaktorModel.COLUMN_MablaghKhalesFaktor(),
            DarkhastFaktorModel.COLUMN_BeMasoliat(),
            DarkhastFaktorModel.COLUMN_CodeNoeHaml(),
            DarkhastFaktorModel.COLUMN_SumMaliat(),
            DarkhastFaktorModel.COLUMN_SumAvarez(),
            DarkhastFaktorModel.COLUMN_SaatVorodBeMaghazeh(),
            DarkhastFaktorModel.COLUMN_SaatKhorojAzMaghazeh(),
            DarkhastFaktorModel.COLUMN_CodeVazeiat(),
            DarkhastFaktorModel.COLUMN_Latitude(),
            DarkhastFaktorModel.COLUMN_Longitude(),
            DarkhastFaktorModel.COLUMN_PPC_VersionNumber(),
            DarkhastFaktorModel.COLUMN_TakhfifNaghdy(),
            DarkhastFaktorModel.COLUMN_ccAddressMoshtary(),
            DarkhastFaktorModel.COLUMN_ExtraProp_IsOld(),
            DarkhastFaktorModel.COLUMN_ExtraProp_MablaghTakhfifSenfiHajmi(),
            DarkhastFaktorModel.COLUMN_MablaghKolFaktor(),
            DarkhastFaktorModel.COLUMN_ExtraProp_InsertInPPC(),
            DarkhastFaktorModel.COLUMN_ExtraProp_MablaghArzeshAfzoodeh(),
            DarkhastFaktorModel.COLUMN_ExtraProp_SumTakhfifat(),
            DarkhastFaktorModel.COLUMN_ExtraProp_MablaghNahaeeFaktor(),
            DarkhastFaktorModel.COLUMN_MarjoeeKamel(),
            DarkhastFaktorModel.COLUMN_NameNoeVosolAzMoshtary(),
            DarkhastFaktorModel.COLUMN_NameNoeHaml(),
            DarkhastFaktorModel.COLUMN_NameVazeiat(),
            DarkhastFaktorModel.COLUMN_NameNoeTahvil(),
            DarkhastFaktorModel.COLUMN_ccMarkazForosh(),
            DarkhastFaktorModel.COLUMN_ccSazmanForosh(),
            DarkhastFaktorModel.COLUMN_ccMarkazSazmanForosh(),
            DarkhastFaktorModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            DarkhastFaktorModel.COLUMN_ccMarkazAnbar(),
            DarkhastFaktorModel.COLUMN_ModateTakhfif(),
            DarkhastFaktorModel.COLUMN_FaktorRooz(),
            DarkhastFaktorModel.COLUMN_MablaghVosol(),
            DarkhastFaktorModel.COLUMN_MablaghMandeh(),
            DarkhastFaktorModel.COLUMN_ccTafkikJoze(),
            DarkhastFaktorModel.COLUMN_Darajeh(),
            DarkhastFaktorModel.COLUMN_ExtraProp_IsMarjoeeKamel(),
            DarkhastFaktorModel.COLUMN_ExtraProp_MablaghMandeh_NaghlAzGhabl(),
            DarkhastFaktorModel.COLUMN_ExtraProp_Resid(),
            DarkhastFaktorModel.COLUMN_ExtraProp_MablaghDariaftPardakht(),
            DarkhastFaktorModel.COLUMN_CodeMarkaz(),
            DarkhastFaktorModel.COLUMN_ForForosh(),
            DarkhastFaktorModel.COLUMN_ForTasviehVosol(),
            DarkhastFaktorModel.COLUMN_ExtraProp_DarkhastFaktorImage(),
            DarkhastFaktorModel.COLUMN_ExtraProp_IsSend(),
            DarkhastFaktorModel.COLUMN_ExtraProp_ShomarehDarkhast(),
            DarkhastFaktorModel.COLUMN_UniqID_Tablet(),
            DarkhastFaktorModel.COLUMN_CodeMoshtary(),
            DarkhastFaktorModel.COLUMN_Noe_Faktor_Havaleh(),
            DarkhastFaktorModel.COLUMN_ExtraProp_ShowFaktorMamorPakhsh(),
            DarkhastFaktorModel.COLUMN_ccUser(),
			DarkhastFaktorModel.COLUMN_ccDarkhastFaktorNoeForosh(),
			DarkhastFaktorModel.COLUMN_IsTajil(),
			DarkhastFaktorModel.COLUMN_IsTakhir(),
        };
    }

    public void fetchDarkhastFaktor(final Context context, final String activityNameForLog, final String ccAfrad, final String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktor", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetDarkhastFaktorResult> call = apiServiceGet.getDarkhastFaktor(ccAfrad , ccMoshtarys);
            call.enqueue(new Callback<GetDarkhastFaktorResult>() {
                @Override
                public void onResponse(Call<GetDarkhastFaktorResult> call, Response<GetDarkhastFaktorResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, DarkhastFaktorDAO.class.getSimpleName(), "", "fetchDarkhastFaktor", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetDarkhastFaktorResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), DarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktor", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), DarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktor", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktor", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), DarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktor", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetDarkhastFaktorResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), DarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktor", "onFailure");
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

    public boolean insertGroupFromGetProgram(ArrayList<DarkhastFaktorModel> darkhastFaktorModels , int noeMasouliat)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DarkhastFaktorModel darkhastFaktorModel : darkhastFaktorModels)
            {
                darkhastFaktorModel.setExtraProp_IsOld(1);
                /*if (noeMasouliat == 4 || noeMasouliat == 5)
                {
                    darkhastFaktorModel.setExtraProp_IsOld(0);
                }
                else
                {
                    darkhastFaktorModel.setExtraProp_IsOld(1);
                }*/
                ContentValues contentValues = modelToContentvalue(darkhastFaktorModel);
                db.insertOrThrow(DarkhastFaktorModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "insertGroupFromGetProgram" , "");
            return false;
        }
    }

    public boolean insertGroup(ArrayList<DarkhastFaktorModel> darkhastFaktorModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DarkhastFaktorModel darkhastFaktorModel : darkhastFaktorModels)
            {
                ContentValues contentValues = modelToContentvalue(darkhastFaktorModel);
                db.insertOrThrow(DarkhastFaktorModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public long insert(DarkhastFaktorModel darkhastFaktorModel , int noeMasouliat)
    {
        long insertId = 0;
        ContentValues values = new ContentValues();

        if (darkhastFaktorModel.getCcDarkhastFaktor() > 0)
        {
            values.put(DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() , darkhastFaktorModel.getCcDarkhastFaktor());
        }
        if(darkhastFaktorModel.getTarikhFaktor() != null && !darkhastFaktorModel.getTarikhFaktor().trim().equals(""))
        {
            values.put(DarkhastFaktorModel.COLUMN_TarikhFaktor() , darkhastFaktorModel.getTarikhFaktor());
        }
        values.put(DarkhastFaktorModel.COLUMN_ShomarehDarkhast() , darkhastFaktorModel.getShomarehDarkhast());
        values.put(DarkhastFaktorModel.COLUMN_ShomarehFaktor() , darkhastFaktorModel.getShomarehFaktor());
        values.put(DarkhastFaktorModel.COLUMN_ccForoshandeh() , darkhastFaktorModel.getCcForoshandeh());
        values.put(DarkhastFaktorModel.COLUMN_ccMoshtary() , darkhastFaktorModel.getCcMoshtary());
        if (darkhastFaktorModel.getTarikhErsal()!= null && !darkhastFaktorModel.getTarikhFaktor().trim().equals(""))
        {
            values.put(DarkhastFaktorModel.COLUMN_TarikhErsal() , darkhastFaktorModel.getTarikhErsal());
        }
        values.put(DarkhastFaktorModel.COLUMN_ModatRoozRaasGiri(), darkhastFaktorModel.getModatRoozRaasGiri());
        values.put(DarkhastFaktorModel.COLUMN_ModateVosol(), darkhastFaktorModel.getModateVosol());
        values.put(DarkhastFaktorModel.COLUMN_CodeNoeVosolAzMoshtary(), darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
        values.put(DarkhastFaktorModel.COLUMN_MablaghKolFaktor(), darkhastFaktorModel.getMablaghKolFaktor());
        values.put(DarkhastFaktorModel.COLUMN_TakhfifNaghdy(), darkhastFaktorModel.getTakhfifNaghdy());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghTakhfifSenfiHajmi(), darkhastFaktorModel.getExtraProp_MablaghTakhfifSenfiHajmi());
        values.put(DarkhastFaktorModel.COLUMN_MablaghKhalesFaktor(), darkhastFaktorModel.getMablaghKhalesFaktor());
        values.put(DarkhastFaktorModel.COLUMN_BeMasoliat(), darkhastFaktorModel.getBeMasoliat());
        values.put(DarkhastFaktorModel.COLUMN_CodeNoeHaml(), darkhastFaktorModel.getCodeNoeHaml());
        values.put(DarkhastFaktorModel.COLUMN_SumMaliat(), darkhastFaktorModel.getSumMaliat());
        values.put(DarkhastFaktorModel.COLUMN_SumAvarez(), darkhastFaktorModel.getSumAvarez());
        if (darkhastFaktorModel.getSaatVorodBeMaghazeh()!= null && !darkhastFaktorModel.getTarikhFaktor().trim().equals(""))
        {
            values.put(DarkhastFaktorModel.COLUMN_SaatVorodBeMaghazeh(), darkhastFaktorModel.getSaatVorodBeMaghazeh());
        }
        if (darkhastFaktorModel.getSaatKhorojAzMaghazeh()!= null && !darkhastFaktorModel.getTarikhFaktor().trim().equals(""))
        {
            values.put(DarkhastFaktorModel.COLUMN_SaatKhorojAzMaghazeh(), darkhastFaktorModel.getSaatKhorojAzMaghazeh());
        }
        values.put(DarkhastFaktorModel.COLUMN_ccAddressMoshtary(), darkhastFaktorModel.getCcAddressMoshtary());
        values.put(DarkhastFaktorModel.COLUMN_CodeVazeiat(), darkhastFaktorModel.getCodeVazeiat());
        values.put(DarkhastFaktorModel.COLUMN_Latitude(), darkhastFaktorModel.getLatitude());
        values.put(DarkhastFaktorModel.COLUMN_Longitude(), darkhastFaktorModel.getLongitude());
        values.put(DarkhastFaktorModel.COLUMN_PPC_VersionNumber(), darkhastFaktorModel.getPPC_VersionNumber());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_IsOld(), darkhastFaktorModel.getExtraProp_IsOld());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_InsertInPPC(), darkhastFaktorModel.getExtraProp_InsertInPPC());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghArzeshAfzoodeh(), darkhastFaktorModel.getExtraProp_MablaghArzeshAfzoodeh());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_SumTakhfifat(), darkhastFaktorModel.getExtraProp_SumTakhfifat());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghNahaeeFaktor(), darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor());
        values.put(DarkhastFaktorModel.COLUMN_MarjoeeKamel(), darkhastFaktorModel.getMarjoeeKamel());
        values.put(DarkhastFaktorModel.COLUMN_NameNoeVosolAzMoshtary(), darkhastFaktorModel.getNameNoeVosolAzMoshtary());
        values.put(DarkhastFaktorModel.COLUMN_NameNoeHaml(), darkhastFaktorModel.getNameNoeHaml());
        values.put(DarkhastFaktorModel.COLUMN_NameVazeiat(), darkhastFaktorModel.getNameVazeiat());
        values.put(DarkhastFaktorModel.COLUMN_NameNoeTahvil(), darkhastFaktorModel.getNameNoeTahvil());
        values.put(DarkhastFaktorModel.COLUMN_ccMarkazForosh(), darkhastFaktorModel.getCcMarkazForosh());
        values.put(DarkhastFaktorModel.COLUMN_ccSazmanForosh(), darkhastFaktorModel.getCcSazmanForosh());
        values.put(DarkhastFaktorModel.COLUMN_ccMarkazSazmanForosh(), darkhastFaktorModel.getCcMarkazSazmanForosh());
        values.put(DarkhastFaktorModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(), darkhastFaktorModel.getCcMarkazSazmanForoshSakhtarForosh());
        values.put(DarkhastFaktorModel.COLUMN_ccMarkazAnbar(), darkhastFaktorModel.getCcMarkazAnbar());
        values.put(DarkhastFaktorModel.COLUMN_ModateTakhfif(), darkhastFaktorModel.getModateTakhfif());
        if(noeMasouliat == 2)
        {
            values.put(DarkhastFaktorModel.COLUMN_FaktorRooz(), 0);//darkhastFaktorModel.getFaktorRooz());
        }
        else
        {
            values.put(DarkhastFaktorModel.COLUMN_FaktorRooz(), darkhastFaktorModel.getFaktorRooz());
        }
        values.put(DarkhastFaktorModel.COLUMN_MablaghVosol(), darkhastFaktorModel.getMablaghVosol());
        values.put(DarkhastFaktorModel.COLUMN_MablaghMandeh(), darkhastFaktorModel.getMablaghMandeh());
        values.put(DarkhastFaktorModel.COLUMN_ccTafkikJoze(), darkhastFaktorModel.getCcTafkikJoze());
        values.put(DarkhastFaktorModel.COLUMN_Darajeh(), darkhastFaktorModel.getDarajeh());
        values.put(DarkhastFaktorModel.COLUMN_CodeMarkaz(), darkhastFaktorModel.getCodeMarkaz());
        values.put(DarkhastFaktorModel.COLUMN_ForForosh(), darkhastFaktorModel.getForForosh());
        if(noeMasouliat == 2)
        {
            values.put(DarkhastFaktorModel.COLUMN_ForTasviehVosol(), 1);//darkhastFaktorModel.getForTasviehVosol());
        }
        else
        {
            values.put(DarkhastFaktorModel.COLUMN_ForTasviehVosol(), darkhastFaktorModel.getForTasviehVosol());
        }
        values.put(DarkhastFaktorModel.COLUMN_Noe_Faktor_Havaleh() , darkhastFaktorModel.getNoeFaktorHavaleh());
        values.put(DarkhastFaktorModel.COLUMN_ccDarkhastFaktorNoeForosh() , darkhastFaktorModel.getCcDarkhastFaktorNoeForosh());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_IsMarjoeeKamel(), darkhastFaktorModel.getExtraProp_IsMarjoeeKamel());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghMandeh_NaghlAzGhabl(), darkhastFaktorModel.getExtraProp_MablaghMandeh_NaghlAzGhabl());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_Resid(), darkhastFaktorModel.getExtraProp_Resid());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghDariaftPardakht(), darkhastFaktorModel.getExtraProp_MablaghDariaftPardakht());
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_DarkhastFaktorImage(), "");
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_IsSend(), 0);
        values.put(DarkhastFaktorModel.COLUMN_ExtraProp_ShomarehDarkhast(), darkhastFaktorModel.getExtraProp_ShomarehDarkhast());
        values.put(DarkhastFaktorModel.COLUMN_UniqID_Tablet(), darkhastFaktorModel.getUniqID_Tablet());
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            insertId = db.insertOrThrow(DarkhastFaktorModel.TableName() , null , values);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "insert" , "");
        }
        return insertId;
    }

    public ArrayList<DarkhastFaktorModel> getAll()
    {
        ArrayList<DarkhastFaktorModel> darkhastFaktorModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorModels;
    }


    public ArrayList<DarkhastFaktorModel> getAllByNotCodeVazeiat(int codeVazeiat)
    {
        ArrayList<DarkhastFaktorModel> darkhastFaktorModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "Select * from " + DarkhastFaktorModel.TableName() + " where " + DarkhastFaktorModel.COLUMN_CodeVazeiat() + " != " + codeVazeiat;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getAllByCodeVazeiat" , "");
        }
        return darkhastFaktorModels;
    }


    public Map<Integer, String> getccMoshtaryPakhshForForoshandeh(int codeVazeiat)
    {
        Map<Integer, String> map = new HashMap<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            /*String query = "select group_concat(distinct(ccMoshtary)), ccForoshandeh from " + DarkhastFaktorModel.TableName() +
                    " where " + DarkhastFaktorModel.COLUMN_CodeVazeiat() + " != " + codeVazeiat + " and CodeMoshtary = 0 group by ccForoshandeh";*/
            String query = "select group_concat(distinct(\"'\" || CodeMoshtary || \"'\")), ccForoshandeh from " + DarkhastFaktorModel.TableName() +
                    " where " + DarkhastFaktorModel.COLUMN_CodeVazeiat() + " != " + codeVazeiat + " group by ccForoshandeh";
            Log.d("DarkhastFaktor" , "map query : " + query);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        int ccForoshandeh = cursor.getInt(1);
                        String ccMoshtarys = cursor.getString(0);
                        map.put(ccForoshandeh, ccMoshtarys);
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getccMoshtaryPakhshForForoshandeh" , "");
        }
        Log.d("DarkhastFaktor" , "map key in dao : " + map.keySet());
        Log.d("DarkhastFaktor" , "map value in dao : " + map.values());
        return map;
    }



    public String getAllccDarkhastFaktorsWithComma()
    {
        String ccDarkhastFaktors = "-1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorModel.TableName(), new String[]{DarkhastFaktorModel.COLUMN_ccDarkhastFaktor()}, null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        ccDarkhastFaktors += "," + cursor.getInt(0);
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getAllccDarkhastFaktorsWithComma" , "");
        }
        return ccDarkhastFaktors;
    }

    public String getccDarkhastFaktorsByNoeFaktorHavale(int ccNoeFaktorHavale)
    {
        String ccDarkhastFaktors = "";
        try
        {
            //select group_concat(ccDarkhastFaktor) from DarkhastFaktor where ccDarkhastFaktorNoeForosh =
            String query = "select group_concat(" + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + ") from " + DarkhastFaktorModel.TableName() +
                    " where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktorNoeForosh() + " = " + ccNoeFaktorHavale;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccDarkhastFaktors = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getccDarkhastFaktorsByNoeFaktorHavale" , "");
        }
        return ccDarkhastFaktors == null ? "" : ccDarkhastFaktors;
    }

    public String getAllccForoshandeh()
    {
        String ccForoshandehs = "-1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select distinct " + DarkhastFaktorModel.COLUMN_ccForoshandeh() + " from " + DarkhastFaktorModel.TableName();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        ccForoshandehs += "," + cursor.getInt(0);
                        Log.d("GetProgram", "ccForoshandehs:" + ccForoshandehs);
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getccForoshandehByForTasviehVosol" , "");
        }
        return ccForoshandehs;
    }

    public String getccForoshandehByForTasviehVosol(int forTasviehVosol)
    {
        String ccForoshandehs = "-1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select distinct(" + DarkhastFaktorModel.COLUMN_ccForoshandeh() + ") from " + DarkhastFaktorModel.TableName() + " where " + DarkhastFaktorModel.COLUMN_ForTasviehVosol() + " = " + forTasviehVosol;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    ccForoshandehs += "," + cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getccForoshandehByForTasviehVosol" , "");
        }
        return ccForoshandehs;
    }

    public ArrayList<String> getccForoshandehForGetCheckBargashty()
    {
        ArrayList<String> arrayListccForoshandeh = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorModel.TableName(), new String[]{DarkhastFaktorModel.COLUMN_ccForoshandeh()}, DarkhastFaktorModel.COLUMN_ForTasviehVosol() + " = 1", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        arrayListccForoshandeh.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccForoshandeh()))));
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getccForoshandehForGetCheckBargashty" , "");
        }
        return arrayListccForoshandeh;
    }

    public ArrayList<DarkhastFaktorModel> getAllByNoeFaktorHavaleAndNotCodeVazeiat(int noeFaktorHavale, int codeVazeiat)
    {
        ArrayList<DarkhastFaktorModel> darkhastFaktorModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "Select * from " + DarkhastFaktorModel.TableName() + " where " +
                    DarkhastFaktorModel.COLUMN_Noe_Faktor_Havaleh() + " = " + noeFaktorHavale +
                    " and " + DarkhastFaktorModel.COLUMN_CodeVazeiat() + " != " + codeVazeiat;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorModels;
    }

    public DarkhastFaktorModel getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        DarkhastFaktorModel darkhastFaktorModel = new DarkhastFaktorModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorModel.TableName(), allColumns(), DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorModel;
    }


    public String getTarikhByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        String tarikhDarkhast = "";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorModel.TableName(), new String[]{DarkhastFaktorModel.COLUMN_TarikhFaktor()}, DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    tarikhDarkhast = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getTarikhByccDarkhastFaktor" , "");
        }
        return tarikhDarkhast;
    }

    public ArrayList<DarkhastFaktorModel> getByccMoshtary(long ccMoshtary)
    {
        ArrayList<DarkhastFaktorModel> darkhastFaktorModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorModel.TableName(), allColumns(), DarkhastFaktorModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorModels;
    }


    public double getMablaghFaktorBaz(int ccMoshtary)
    {
        double mablaghFaktor = 0;
        try
        {
            String query = " SELECT sum(ExtraProp_MablaghNahaeeFaktor) FROM DarkhastFaktor "
                    + " WHERE ccMoshtary= ? AND "
                    + "       (   (ExtraProp_IsOld = 0 AND CodeVazeiat >= 2 ) OR  "
                    + "          ( ExtraProp_IsOld = 1 AND ExtraProp_InsertInPPC = 1 and ccDarkhastFaktor NOT IN(SELECT ccDarkhastFaktorPPC FROM Rpt_DarkhastFaktorVazeiatPPC  WHERE ccMoshtary = " + ccMoshtary + "  ))  OR "
                    + "            ccDarkhastFaktor     IN(SELECT ccDarkhastFaktorPPC FROM Rpt_DarkhastFaktorVazeiatPPC WHERE CodeVazeiat >= 4 and ccMoshtary = " + ccMoshtary + "  )"
                    + "       )";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccMoshtary)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    mablaghFaktor = cursor.getDouble(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getMablaghFaktorBaz" , "");
        }
        return mablaghFaktor;
    }

    public double getMablaghFaktorBazRoozTaeedNashodeh(int ccMoshtary, int NoeMasouliat, long ccDarkhastFaktor)
    {
        double mablaghFaktor = 0;
        try
        {
            String query = " SELECT sum(ExtraProp_MablaghNahaeeFaktor) FROM DarkhastFaktor "
                    + " WHERE    CodeNoeVosolAzMoshtary = 1 AND ccMoshtary= ? AND "
                    + "          (ExtraProp_IsOld = 1 AND CodeVazeiat <= 2 )  " ;
            if (NoeMasouliat==5)
                query += " AND ccDarkhastFaktor <> " + ccDarkhastFaktor;

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccMoshtary)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    mablaghFaktor = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getTedadFaktorBazRoozTaeedNashodeh" , "");
        }
        return mablaghFaktor;
    }

    public long getMablaghMandeh(long ccDarkhastFaktor)
    {
        long mablaghMandeh = -1;
        try
        {
            String query = "select " + DarkhastFaktorModel.COLUMN_MablaghMandeh() + " from " + DarkhastFaktorModel.TableName();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    mablaghMandeh = cursor.getLong(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getMablaghMandeh" , "");
        }
        return mablaghMandeh;
    }

    public long getMablaghKolFaktor(long ccDarkhastFaktor)
    {
        long mablaghKolFaktor = -1;
        try
        {
            String query = "select " + DarkhastFaktorModel.COLUMN_MablaghKolFaktor() + " from " + DarkhastFaktorModel.TableName() + " where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    mablaghKolFaktor = cursor.getLong(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getMablaghKolFaktor" , "");
        }
        return mablaghKolFaktor;
    }

    public int getccMarkazSazmanForoshSakhtarForosh()
    {
        int ccMarkazSazmanForoshSakhtarForosh = 0;
        try
        {
            String query = "select " + DarkhastFaktorModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() + " from " + DarkhastFaktorModel.TableName() + " limit 1";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccMarkazSazmanForoshSakhtarForosh = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getccMarkazSazmanForoshSakhtarForosh" , "");
        }
        return ccMarkazSazmanForoshSakhtarForosh;
    }

    public int getCountByccMoshtaryRooz(int ccMoshtary)
    {
        int count = -1;
        String query = "select count(*) from " + DarkhastFaktorModel.TableName() + " where " + DarkhastFaktorModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and " + DarkhastFaktorModel.COLUMN_TarikhFaktor() + " >= date('now')";
        try
        {
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getCount" , "");
        }
        return count;
    }

    public ArrayList<DarkhastFaktorModel> getLastFaktorForoshandeh()
    {
        ArrayList<DarkhastFaktorModel> darkhastFaktorModels = new ArrayList<>();
        String query =	"SELECT * FROM " + DarkhastFaktorModel.TableName() + " ORDER BY " + DarkhastFaktorModel.COLUMN_TarikhFaktor() + " DESC LIMIT 1 ";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getLastFaktorForoshandeh" , "");
        }
        return darkhastFaktorModels;
    }

    public int getTedadFaktorTarikhPishbinyTahvil()
    {
        int count = 0;
        try
        {
            String query = "select count(ccDarkhastFaktor) from DarkhastFaktor where TarikhFaktor >= date('now') AND TarikhPishbinyTahvil >= date('now')";
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getTedadFaktorTarikhPishbinyTahvil" , "");
        }
        return count;
    }

    public int getCountErsalNashode()
    {
        int count = 0;
        String query = "select count(" + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + ") from " + DarkhastFaktorModel.TableName() +
                " where " + DarkhastFaktorModel.COLUMN_ExtraProp_IsOld() + " = 0 and " + DarkhastFaktorModel.COLUMN_ExtraProp_IsSend() + " = 0";
        try
        {
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getCountErsalNashode" , "");
        }
        return count;
    }

    public int getTedadFaktorBaz(int ccMoshtary)
    {
        int tedadFaktor = 0;
        try
        {
            String query = " SELECT count(" + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + ") FROM DarkhastFaktor "
                    + " WHERE ccMoshtary= ? AND "
                    + "       (  (ExtraProp_IsOld = 0 AND CodeVazeiat >= 2 ) OR  "
                    + "          ( ExtraProp_IsOld = 1 AND ExtraProp_InsertInPPC = 1 and ccDarkhastFaktor NOT IN(SELECT ccDarkhastFaktorPPC FROM Rpt_DarkhastFaktorVazeiatPPC  WHERE ccMoshtary = " + ccMoshtary + "  ))  OR "
                    + "            ccDarkhastFaktor IN(SELECT ccDarkhastFaktorPPC FROM Rpt_DarkhastFaktorVazeiatPPC WHERE CodeVazeiat >= 4 and ccMoshtary = " + ccMoshtary + "  )"
                    + "       )";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ccMoshtary)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    tedadFaktor = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getTedadFaktorBaz" , "");
        }
        return tedadFaktor;
    }

    public int getTedadFaktorBazRoozTaeedNashodeh(int ccMoshtary, int NoeMasouliat, long ccDarkhastFaktor)
    {
        int tedadFaktor = 0;
        try
        {
            String query = " SELECT count(" + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + ") FROM DarkhastFaktor "
                    + " WHERE    CodeNoeVosolAzMoshtary = 1 AND ccMoshtary= ? AND "
                    + "          (ExtraProp_IsOld = 1 AND CodeVazeiat <= 2 )  " ;
            if (NoeMasouliat==5)
                query += " AND ccDarkhastFaktor <> " + ccDarkhastFaktor;

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccMoshtary)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    tedadFaktor = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getTedadFaktorBazRoozTaeedNashodeh" , "");
        }
        return tedadFaktor;
    }


    public String getAllccMarkazSazmanSakhtarForosh()
    {
        String ccMarkazSazmanForoshSakhtarForosh = "";
        String query = "select GROUP_CONCAT(distinct(" + DarkhastFaktorModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() + ")) from " + DarkhastFaktorModel.TableName();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccMarkazSazmanForoshSakhtarForosh = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getAllccMarkazSazmanSakhtarForosh" , "");
        }
        return ccMarkazSazmanForoshSakhtarForosh;
    }

    public SparseArray<KalaDarkhastFaktorModel> getGoodsOfMinFaktor(int ccMoshtary)
    {
        SparseArray<KalaDarkhastFaktorModel> kalaDarkhastFaktors = new SparseArray<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT B.[ccKalaCode], MIN(A.[TarikhFaktor]) AS TarikhFaktor, SUM(B.[Tedad3]) AS TedadFaktor \n" +
                    " FROM DarkhastFaktor A LEFT OUTER JOIN DarkhastFaktorSatr B ON A.[ccDarkhastFaktor]= B.[ccDarkhastFaktor] \n" +
                    " WHERE A.[ccMoshtary]= " + ccMoshtary + " AND A.[MarjoeeKamel]= 0 \n" +
                    " AND A.CodeVazeiat > " + Constants.CODE_VAZEIAT_FAKTOR_TAEED() + " GROUP BY B.[ccKalaCode]";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    KalaDarkhastFaktorModel kalaDarkhastFaktor;
                    while (!cursor.isAfterLast())
                    {
                        kalaDarkhastFaktor = new KalaDarkhastFaktorModel();
                        kalaDarkhastFaktor.setCcKalaCode(cursor.getInt(cursor.getColumnIndex("ccKalaCode")));
                        kalaDarkhastFaktor.setTarikhFaktor(cursor.getString(cursor.getColumnIndex("TarikhFaktor")));
                        kalaDarkhastFaktor.setTedadFaktor(cursor.getInt(cursor.getColumnIndex("TedadFaktor")));
                        kalaDarkhastFaktors.put(kalaDarkhastFaktor.getCcKalaCode(), kalaDarkhastFaktor);
                        cursor.moveToNext();
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "DarkhastFaktorDAO" , "" , "getTedadPishnahadiDarkhast" , "");
        }
        return kalaDarkhastFaktors;
    }


    public boolean canInsFaktorKharejAzMahal(int ccMoshtary)
    {
        boolean flag = false;
        try
        {
            String query = "SELECT * FROM DarkhastFaktor "
                    + " WHERE ccMoshtary= ? AND ccMoshtary IN(Select ccMoshtary From Rpt_DarkhastFaktorVazeiatPPC) AND "
                    + " ccMoshtary IN( SELECT ccMoshtary FROM Masir_ErsalFaktor M Left Outer Join ForoshandehMoshtary F On M.ccMasir = F.ccMasir) ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccMoshtary)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    flag = true;
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "canInsFaktorKharejAzMahal" , "");
        }
        return flag;
    }

    /**
     * 350 == ZANJIRE
     * @return ccMoshtary by janjire
     */
    public String getCcMoshtaryForZanjire(){

        String ccMoshtary = "-1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT DarkhastFaktor.ccMoshtary FROM DarkhastFaktor " +
                    "LEFT JOIN Moshtary ON Moshtary.ccMoshtary = DarkhastFaktor.ccMoshtary " +
                    "WHERE ccNoeMoshtary = 350 "  ;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        ccMoshtary += " , " + cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccMoshtary()));

                        cursor.moveToNext();
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "DarkhastFaktorDAO" , "" , "getCcMoshtaryForZanjire" , "");
        }
        return ccMoshtary;

    }

    public String getCcdarkhastFaktorsForZanjirei(){

        String ccDarkhastFaktor = "-1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT DarkhastFaktor.ccDarkhastFaktor FROM DarkhastFaktor " +
                    "LEFT JOIN Moshtary ON Moshtary.ccMoshtary = DarkhastFaktor.ccMoshtary " +
                    "WHERE ccNoeMoshtary = 350 "  ;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        ccDarkhastFaktor += " , " + cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccDarkhastFaktor()));

                        cursor.moveToNext();
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "DarkhastFaktorDAO" , "" , "getCcdarkhastFaktorsForZanjirei" , "");
        }
        return ccDarkhastFaktor;

    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorModel.TableName(), DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "deleteByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean deleteAllFaktorTaeedNashode()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorModel.TableName(), DarkhastFaktorModel.COLUMN_CodeVazeiat() + " < 2", null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "deleteAllFaktorTaeedNashode" , "");
            return false;
        }
    }

    public boolean updateMandehDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            String query =" UPDATE DarkhastFaktor "
                    +" SET MablaghMandeh = MablaghKhalesFaktor -  ifnull((SELECT sum(Mablagh) FROM DariaftPardakhtDarkhastFaktorPPC WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + "),0),  "
                    +" ExtraProp_MablaghDariaftPardakht = MablaghKhalesFaktor - ( ifnull((SELECT sum(Mablagh) FROM DariaftPardakhtPPC WHERE CodeNoeVosol <> " + Constants.VALUE_MARJOEE() + " AND ccDarkhastFaktor = " + ccDarkhastFaktor + "),0)   "
                    +" +  ifnull((SELECT sum(Mablagh) FROM DariaftPardakhtDarkhastFaktorPPC WHERE CodeNoeVosol = " + Constants.VALUE_MARJOEE() + " AND ccDarkhastFaktor = " + ccDarkhastFaktor + "),0) )  "
                    +" Where ccDarkhastFaktor = " + ccDarkhastFaktor ;
            Log.d("mablaghMandeh" , "query : " + query);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateMandehDarkhastFaktor" , "");
            return false;
        }
    }

    public void updateMarjoee(String ccDarkhastFaktor, int Marjoee)
    {
        try
        {
            String strSql =" UPDATE DarkhastFaktor "
                    +" SET   ExtraProp_IsMarjoeeKamel = " + Marjoee
                    +" Where ccDarkhastFaktor = " + ccDarkhastFaktor ;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(strSql, null);
            cursor.moveToFirst();
            cursor.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    public boolean updateModatRoozRaasGiri(long ccDarkhastFaktor , int modatRoozRaasGiri)
    {
        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DarkhastFaktorModel.COLUMN_ModatRoozRaasGiri() , modatRoozRaasGiri);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(DarkhastFaktorModel.TableName(), contentValues, DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateModatRoozRaasGiri" , "");
            return false;
        }
    }

    public void updateResid(long ccDarkhastFaktor , int resid)
    {
        try
        {
            String query = "UPDATE " + DarkhastFaktorModel.TableName() + " SET " + DarkhastFaktorModel.COLUMN_ExtraProp_Resid() + " =  " + resid + " Where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor ;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateResid" , "");
        }
    }

    public void updateCodeVazeiat()
    {
        try
        {
            String query = "UPDATE " + DarkhastFaktorModel.TableName() + " SET " + DarkhastFaktorModel.COLUMN_CodeVazeiat() + " = 4";
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "DarkhastFaktorDAO" , "" , "updateResid" , "");
        }
    }

    public DarkhastFaktorModel update(DarkhastFaktorModel darkhastFaktorModel)
    {
        ContentValues values = new ContentValues();
        try
        {
            if (darkhastFaktorModel.getTarikhFaktor()!= null)
            {
                values.put(DarkhastFaktorModel.COLUMN_TarikhFaktor(), darkhastFaktorModel.getTarikhFaktor());
            }
            values.put(DarkhastFaktorModel.COLUMN_ShomarehDarkhast(), darkhastFaktorModel.getShomarehDarkhast());
            values.put(DarkhastFaktorModel.COLUMN_ShomarehFaktor(), darkhastFaktorModel.getShomarehFaktor());
            values.put(DarkhastFaktorModel.COLUMN_ccForoshandeh(), darkhastFaktorModel.getCcForoshandeh());
            values.put(DarkhastFaktorModel.COLUMN_ccMoshtary(), darkhastFaktorModel.getCcMoshtary());
            if (darkhastFaktorModel.getTarikhErsal()!= null)
            {
                values.put(DarkhastFaktorModel.COLUMN_TarikhErsal(), darkhastFaktorModel.getTarikhErsal());
            }
            values.put(DarkhastFaktorModel.COLUMN_ModatRoozRaasGiri(), darkhastFaktorModel.getModatRoozRaasGiri());
            values.put(DarkhastFaktorModel.COLUMN_ModateVosol(), darkhastFaktorModel.getModateVosol());
            values.put(DarkhastFaktorModel.COLUMN_CodeNoeVosolAzMoshtary(), darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
            values.put(DarkhastFaktorModel.COLUMN_NameNoeVosolAzMoshtary(), darkhastFaktorModel.getNameNoeVosolAzMoshtary());
            values.put(DarkhastFaktorModel.COLUMN_MablaghKolFaktor(), darkhastFaktorModel.getMablaghKolFaktor());
            values.put(DarkhastFaktorModel.COLUMN_TakhfifNaghdy(), darkhastFaktorModel.getTakhfifNaghdy());
            values.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghTakhfifSenfiHajmi(), darkhastFaktorModel.getExtraProp_MablaghTakhfifSenfiHajmi());
            values.put(DarkhastFaktorModel.COLUMN_MablaghKhalesFaktor(), darkhastFaktorModel.getMablaghKhalesFaktor());
            values.put(DarkhastFaktorModel.COLUMN_BeMasoliat(), darkhastFaktorModel.getBeMasoliat());
            values.put(DarkhastFaktorModel.COLUMN_CodeNoeHaml(), darkhastFaktorModel.getCodeNoeHaml());
            values.put(DarkhastFaktorModel.COLUMN_SumMaliat(), darkhastFaktorModel.getSumMaliat());
            values.put(DarkhastFaktorModel.COLUMN_SumAvarez(), darkhastFaktorModel.getSumAvarez());
            if (darkhastFaktorModel.getSaatVorodBeMaghazeh()!= null)
            {
                values.put(DarkhastFaktorModel.COLUMN_SaatVorodBeMaghazeh(), darkhastFaktorModel.getSaatVorodBeMaghazeh());
            }
            if (darkhastFaktorModel.getSaatKhorojAzMaghazeh()!= null)
            {
                values.put(DarkhastFaktorModel.COLUMN_SaatKhorojAzMaghazeh(), darkhastFaktorModel.getSaatKhorojAzMaghazeh());
            }
            values.put(DarkhastFaktorModel.COLUMN_ccAddressMoshtary(), darkhastFaktorModel.getCcAddressMoshtary());
            values.put(DarkhastFaktorModel.COLUMN_CodeVazeiat(), darkhastFaktorModel.getCodeVazeiat());
            values.put(DarkhastFaktorModel.COLUMN_Latitude(), darkhastFaktorModel.getLatitude());
            values.put(DarkhastFaktorModel.COLUMN_Longitude(), darkhastFaktorModel.getLongitude());
            values.put(DarkhastFaktorModel.COLUMN_ForForosh(), darkhastFaktorModel.getForForosh());
            values.put(DarkhastFaktorModel.COLUMN_ForTasviehVosol(), darkhastFaktorModel.getForTasviehVosol());
            values.put(DarkhastFaktorModel.COLUMN_PPC_VersionNumber(), darkhastFaktorModel.getPPC_VersionNumber());
            values.put(DarkhastFaktorModel.COLUMN_TarikhPishbinyTahvil(), darkhastFaktorModel.getTarikhPishbinyTahvil());
            values.put(DarkhastFaktorModel.COLUMN_TakhfifNaghdy(), darkhastFaktorModel.getPPC_VersionNumber());
            values.put(DarkhastFaktorModel.COLUMN_ExtraProp_IsOld(), darkhastFaktorModel.getExtraProp_IsOld());
            values.put(DarkhastFaktorModel.COLUMN_ExtraProp_InsertInPPC(), darkhastFaktorModel.getExtraProp_InsertInPPC());
            values.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghArzeshAfzoodeh(), darkhastFaktorModel.getExtraProp_MablaghArzeshAfzoodeh());
            values.put(DarkhastFaktorModel.COLUMN_ExtraProp_SumTakhfifat(), darkhastFaktorModel.getExtraProp_SumTakhfifat());
            values.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghNahaeeFaktor(), darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor());
            values.put(DarkhastFaktorModel.COLUMN_ExtraProp_ShomarehDarkhast(), darkhastFaktorModel.getExtraProp_ShomarehDarkhast());
            values.put(DarkhastFaktorModel.COLUMN_UniqID_Tablet(), darkhastFaktorModel.getUniqID_Tablet());

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(DarkhastFaktorModel.TableName(), values, "ccDarkhastFaktor = ?", new String[]{String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor())});
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "update" , "");
        }
        return darkhastFaktorModel;
    }

    public boolean updateExtraPropShowFaktorMamorPakhsh(long ccDarkhastFaktor , int value)
    {
        String query = "update " + DarkhastFaktorModel.TableName() + " set " + DarkhastFaktorModel.COLUMN_ExtraProp_ShowFaktorMamorPakhsh() + " = " + value + " where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
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
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateSaateKhorojAzMaghazeh" , "");
            return false;
        }
    }

    public void updateSaateKhorojAzMaghazehAndInsertInPPC(long ccDarkhastFaktor , String currentDate)
    {
        String query = "update " + DarkhastFaktorModel.TableName() + " set " + DarkhastFaktorModel.COLUMN_SaatKhorojAzMaghazeh() + " = '" + currentDate +
                "' , " + DarkhastFaktorModel.COLUMN_ExtraProp_InsertInPPC() + " = 1 " + " where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateSaateKhorojAzMaghazeh" , "");
        }
    }

    public void updateMablaghKol(long ccDarkhastFaktor , double mablaghKolFaktor)
    {
        try
        {
            String query = "UPDATE " + DarkhastFaktorModel.TableName() + " SET " + DarkhastFaktorModel.COLUMN_MablaghKolFaktor() + " =  " + mablaghKolFaktor + " Where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor ;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateMablaghKol" , "");
        }
    }

    public void updateExtraPropMablaghNahaeeFaktor(long ccDarkhastFaktor , float mablaghNahaeeFaktor)
    {
        try
        {
            String query = "UPDATE " + DarkhastFaktorModel.TableName() + " SET " + DarkhastFaktorModel.COLUMN_ExtraProp_MablaghNahaeeFaktor() + " =  " + mablaghNahaeeFaktor + " Where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor ;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateExtraPropMablaghNahaeeFaktor" , "");
        }
    }

    public void updateSaateVorodAndTarikhFaktor(long ccDarkhastFaktor , String saateVorodBeMaghazeh , String tarikhFaktor)
    {
        String query = "update " + DarkhastFaktorModel.TableName() + " set " + DarkhastFaktorModel.COLUMN_SaatVorodBeMaghazeh() + " = '" + saateVorodBeMaghazeh +
                "' , " + DarkhastFaktorModel.COLUMN_TarikhFaktor() + " = '" + tarikhFaktor + "' where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateSaateVorodAndTarikhFaktor" , "");
        }
    }

    public boolean updateSendedDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew , int isOld)
    {
        try
        {
            String query = "update " + DarkhastFaktorModel.TableName() + " set " + DarkhastFaktorModel.COLUMN_ExtraProp_IsOld() + " = " + isOld + " , " + DarkhastFaktorModel.COLUMN_ExtraProp_IsSend() + " = 1 , " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew +
                    " where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateIsOld" , "");
            return true;
        }
    }

    public boolean updateExtraPropIsMarjoeeKamelDarkhastFaktor(long ccDarkhastFaktor , int extraPropIsMarjoeeKamel)
    {
        try
        {
            String query = "update " + DarkhastFaktorModel.TableName() + " set " + DarkhastFaktorModel.COLUMN_ExtraProp_IsMarjoeeKamel() + " = " + extraPropIsMarjoeeKamel +
                    " where " + DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "updateIsOld" , "");
            return true;
        }
    }

    private static ContentValues modelToContentvalue(DarkhastFaktorModel darkhastFaktorModel)
    {
        ContentValues contentValues = new ContentValues();

        if (darkhastFaktorModel.getCcDarkhastFaktor() > 0)
        {
            contentValues.put(DarkhastFaktorModel.COLUMN_ccDarkhastFaktor() , darkhastFaktorModel.getCcDarkhastFaktor());
        }
        contentValues.put(DarkhastFaktorModel.COLUMN_TarikhFaktor() , darkhastFaktorModel.getTarikhFaktor());
        contentValues.put(DarkhastFaktorModel.COLUMN_ShomarehDarkhast() , darkhastFaktorModel.getShomarehDarkhast());
        contentValues.put(DarkhastFaktorModel.COLUMN_ShomarehFaktor() , darkhastFaktorModel.getShomarehFaktor());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccForoshandeh() , darkhastFaktorModel.getCcForoshandeh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccMoshtary() , darkhastFaktorModel.getCcMoshtary());
        contentValues.put(DarkhastFaktorModel.COLUMN_TarikhErsal() , darkhastFaktorModel.getTarikhErsal());
        contentValues.put(DarkhastFaktorModel.COLUMN_TarikhPishbinyTahvil() , darkhastFaktorModel.getTarikhPishbinyTahvil());
        contentValues.put(DarkhastFaktorModel.COLUMN_ModatRoozRaasGiri() , darkhastFaktorModel.getModatRoozRaasGiri());
        contentValues.put(DarkhastFaktorModel.COLUMN_ModateVosol() , darkhastFaktorModel.getModateVosol());
        contentValues.put(DarkhastFaktorModel.COLUMN_CodeNoeVosolAzMoshtary() , darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
        //contentValues.put(DarkhastFaktorModel.COLUMN_MablaghFaktorPishAzTakhfif() , darkhastFaktorModel.getMablaghFaktorPishAzTakhfif());
        contentValues.put(DarkhastFaktorModel.COLUMN_MablaghKhalesFaktor() , darkhastFaktorModel.getMablaghKhalesFaktor());
        contentValues.put(DarkhastFaktorModel.COLUMN_BeMasoliat() , darkhastFaktorModel.getBeMasoliat());
        contentValues.put(DarkhastFaktorModel.COLUMN_CodeNoeHaml() , darkhastFaktorModel.getCodeNoeHaml());
        contentValues.put(DarkhastFaktorModel.COLUMN_SumMaliat() , darkhastFaktorModel.getSumMaliat());
        contentValues.put(DarkhastFaktorModel.COLUMN_SumAvarez() , darkhastFaktorModel.getSumAvarez());
        contentValues.put(DarkhastFaktorModel.COLUMN_SaatVorodBeMaghazeh() , darkhastFaktorModel.getSaatVorodBeMaghazeh());
        contentValues.put(DarkhastFaktorModel.COLUMN_SaatKhorojAzMaghazeh() , darkhastFaktorModel.getSaatKhorojAzMaghazeh());
        contentValues.put(DarkhastFaktorModel.COLUMN_CodeVazeiat() , darkhastFaktorModel.getCodeVazeiat());
        contentValues.put(DarkhastFaktorModel.COLUMN_Latitude() , darkhastFaktorModel.getLatitude());
        contentValues.put(DarkhastFaktorModel.COLUMN_Longitude() , darkhastFaktorModel.getLongitude());
        contentValues.put(DarkhastFaktorModel.COLUMN_PPC_VersionNumber() , darkhastFaktorModel.getPPC_VersionNumber());
        contentValues.put(DarkhastFaktorModel.COLUMN_TakhfifNaghdy() , darkhastFaktorModel.getTakhfifNaghdy());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccAddressMoshtary() , darkhastFaktorModel.getCcAddressMoshtary());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_IsOld() , darkhastFaktorModel.getExtraProp_IsOld());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghTakhfifSenfiHajmi() , darkhastFaktorModel.getExtraProp_MablaghTakhfifSenfiHajmi());
        contentValues.put(DarkhastFaktorModel.COLUMN_MablaghKolFaktor() , darkhastFaktorModel.getMablaghKolFaktor());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_InsertInPPC() , darkhastFaktorModel.getExtraProp_InsertInPPC());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghArzeshAfzoodeh() , darkhastFaktorModel.getExtraProp_MablaghArzeshAfzoodeh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_SumTakhfifat() , darkhastFaktorModel.getExtraProp_SumTakhfifat());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghNahaeeFaktor() , darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor());
        contentValues.put(DarkhastFaktorModel.COLUMN_MarjoeeKamel() , darkhastFaktorModel.getMarjoeeKamel());
        contentValues.put(DarkhastFaktorModel.COLUMN_NameNoeVosolAzMoshtary() , darkhastFaktorModel.getNameNoeVosolAzMoshtary());
        contentValues.put(DarkhastFaktorModel.COLUMN_NameNoeHaml() , darkhastFaktorModel.getNameNoeHaml());
        contentValues.put(DarkhastFaktorModel.COLUMN_NameVazeiat() , darkhastFaktorModel.getNameVazeiat());
        contentValues.put(DarkhastFaktorModel.COLUMN_NameNoeTahvil() , darkhastFaktorModel.getNameNoeTahvil());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccMarkazForosh() , darkhastFaktorModel.getCcMarkazForosh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccSazmanForosh() , darkhastFaktorModel.getCcSazmanForosh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccMarkazSazmanForosh() , darkhastFaktorModel.getCcMarkazSazmanForosh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , darkhastFaktorModel.getCcMarkazSazmanForoshSakhtarForosh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccMarkazAnbar() , darkhastFaktorModel.getCcMarkazAnbar());
        contentValues.put(DarkhastFaktorModel.COLUMN_ModateTakhfif() , darkhastFaktorModel.getModateTakhfif());
        contentValues.put(DarkhastFaktorModel.COLUMN_FaktorRooz() , darkhastFaktorModel.getFaktorRooz());
        contentValues.put(DarkhastFaktorModel.COLUMN_MablaghVosol() , darkhastFaktorModel.getMablaghVosol());
        contentValues.put(DarkhastFaktorModel.COLUMN_MablaghMandeh() , darkhastFaktorModel.getMablaghMandeh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccTafkikJoze() , darkhastFaktorModel.getCcTafkikJoze());
        contentValues.put(DarkhastFaktorModel.COLUMN_Darajeh() , darkhastFaktorModel.getDarajeh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_IsMarjoeeKamel() , darkhastFaktorModel.getExtraProp_IsMarjoeeKamel());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghMandeh_NaghlAzGhabl() , darkhastFaktorModel.getExtraProp_MablaghMandeh_NaghlAzGhabl());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_Resid() , darkhastFaktorModel.getExtraProp_Resid());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghDariaftPardakht() , darkhastFaktorModel.getExtraProp_MablaghDariaftPardakht());
        contentValues.put(DarkhastFaktorModel.COLUMN_CodeMarkaz() , darkhastFaktorModel.getCodeMarkaz());
        contentValues.put(DarkhastFaktorModel.COLUMN_ForForosh() , darkhastFaktorModel.getForForosh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ForTasviehVosol() , darkhastFaktorModel.getForTasviehVosol());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_DarkhastFaktorImage() , darkhastFaktorModel.getExtraProp_DarkhastFaktorImage());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_IsSend() , darkhastFaktorModel.getExtraProp_IsSend());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_ShomarehDarkhast() , darkhastFaktorModel.getExtraProp_ShomarehDarkhast());
        contentValues.put(DarkhastFaktorModel.COLUMN_UniqID_Tablet() , darkhastFaktorModel.getUniqID_Tablet());
        contentValues.put(DarkhastFaktorModel.COLUMN_CodeMoshtary() , darkhastFaktorModel.getCodeMoshtary());
        contentValues.put(DarkhastFaktorModel.COLUMN_Noe_Faktor_Havaleh() , darkhastFaktorModel.getNoeFaktorHavaleh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ExtraProp_ShowFaktorMamorPakhsh() , darkhastFaktorModel.getShowFaktorMamorPakhsh());
        contentValues.put(DarkhastFaktorModel.COLUMN_ccUser() , darkhastFaktorModel.getCcUser());
		contentValues.put(DarkhastFaktorModel.COLUMN_ccDarkhastFaktorNoeForosh() , darkhastFaktorModel.getCcDarkhastFaktorNoeForosh());
		contentValues.put(DarkhastFaktorModel.COLUMN_IsTajil() , darkhastFaktorModel.getIsTajil());
		contentValues.put(DarkhastFaktorModel.COLUMN_IsTakhir() , darkhastFaktorModel.getIsTakhir());

        return contentValues;
    }


    private ArrayList<DarkhastFaktorModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorModel> darkhastFaktorModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorModel darkhastFaktorModel = new DarkhastFaktorModel();

            darkhastFaktorModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_TarikhFaktor())));
            darkhastFaktorModel.setShomarehDarkhast(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ShomarehDarkhast())));
            darkhastFaktorModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ShomarehFaktor())));
            darkhastFaktorModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccForoshandeh())));
            darkhastFaktorModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccMoshtary())));
            darkhastFaktorModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_TarikhErsal())));
            darkhastFaktorModel.setTarikhPishbinyTahvil(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_TarikhPishbinyTahvil())));
            darkhastFaktorModel.setModatRoozRaasGiri(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ModatRoozRaasGiri())));
            darkhastFaktorModel.setModateVosol(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ModateVosol())));
            darkhastFaktorModel.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_CodeNoeVosolAzMoshtary())));
            //darkhastFaktorModel.setMablaghFaktorPishAzTakhfif(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_MablaghFaktorPishAzTakhfif())));
            darkhastFaktorModel.setMablaghKhalesFaktor(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_MablaghKhalesFaktor())));
            darkhastFaktorModel.setBeMasoliat(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_BeMasoliat())));
            darkhastFaktorModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_CodeNoeHaml())));
            darkhastFaktorModel.setSumMaliat(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_SumMaliat())));
            darkhastFaktorModel.setSumAvarez(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_SumAvarez())));
            darkhastFaktorModel.setSaatVorodBeMaghazeh(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_SaatVorodBeMaghazeh())));
            darkhastFaktorModel.setSaatKhorojAzMaghazeh(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_SaatKhorojAzMaghazeh())));
            darkhastFaktorModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_CodeVazeiat())));
            darkhastFaktorModel.setLatitude(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_Latitude())));
            darkhastFaktorModel.setLongitude(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_Longitude())));
            darkhastFaktorModel.setPPC_VersionNumber(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_PPC_VersionNumber())));
            //darkhastFaktorModel.setPPC_Version_Date(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_PPC_Version_Date())));
            darkhastFaktorModel.setTakhfifNaghdy(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_TakhfifNaghdy())));
            darkhastFaktorModel.setCcAddressMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccAddressMoshtary())));
            darkhastFaktorModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_IsOld())));
            darkhastFaktorModel.setExtraProp_MablaghTakhfifSenfiHajmi(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghTakhfifSenfiHajmi())));
            darkhastFaktorModel.setMablaghKolFaktor(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_MablaghKolFaktor())));
            darkhastFaktorModel.setExtraProp_InsertInPPC(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_InsertInPPC())));
            darkhastFaktorModel.setExtraProp_MablaghArzeshAfzoodeh(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghArzeshAfzoodeh())));
            darkhastFaktorModel.setExtraProp_SumTakhfifat(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_SumTakhfifat())));
            darkhastFaktorModel.setExtraProp_MablaghNahaeeFaktor(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghNahaeeFaktor())));
            darkhastFaktorModel.setMarjoeeKamel(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_MarjoeeKamel())));
            darkhastFaktorModel.setNameNoeVosolAzMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_NameNoeVosolAzMoshtary())));
            darkhastFaktorModel.setNameNoeHaml(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_NameNoeHaml())));
            darkhastFaktorModel.setNameVazeiat(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_NameVazeiat())));
            darkhastFaktorModel.setNameVazeiat(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_NameNoeTahvil())));
            darkhastFaktorModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccMarkazForosh())));
            darkhastFaktorModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccSazmanForosh())));
            darkhastFaktorModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccMarkazSazmanForosh())));
            darkhastFaktorModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));
            darkhastFaktorModel.setCcMarkazAnbar(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccMarkazAnbar())));
            darkhastFaktorModel.setModateTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ModateTakhfif())));
            darkhastFaktorModel.setFaktorRooz(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_FaktorRooz())));
            darkhastFaktorModel.setMablaghVosol(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_MablaghVosol())));
            darkhastFaktorModel.setMablaghMandeh(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_MablaghMandeh())));
            darkhastFaktorModel.setCcTafkikJoze(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccTafkikJoze())));
            darkhastFaktorModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_Darajeh())));
            darkhastFaktorModel.setExtraProp_IsMarjoeeKamel(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_IsMarjoeeKamel())));
            darkhastFaktorModel.setExtraProp_MablaghMandeh_NaghlAzGhabl(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghMandeh_NaghlAzGhabl())));
            darkhastFaktorModel.setExtraProp_Resid(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_Resid())));
            darkhastFaktorModel.setExtraProp_MablaghDariaftPardakht(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_MablaghDariaftPardakht())));
            darkhastFaktorModel.setCodeMarkaz(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_CodeMarkaz())));
            darkhastFaktorModel.setForForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ForForosh())));
            darkhastFaktorModel.setForTasviehVosol(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ForTasviehVosol())));
            darkhastFaktorModel.setExtraProp_DarkhastFaktorImage(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_DarkhastFaktorImage())));
            darkhastFaktorModel.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_IsSend())));
            darkhastFaktorModel.setExtraProp_ShomarehDarkhast(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_ShomarehDarkhast())));
            darkhastFaktorModel.setUniqID_Tablet(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_UniqID_Tablet())));
            darkhastFaktorModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_CodeMoshtary())));
            darkhastFaktorModel.setNoeFaktorHavaleh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_Noe_Faktor_Havaleh())));
            darkhastFaktorModel.setShowFaktorMamorPakhsh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_ShowFaktorMamorPakhsh())));
            darkhastFaktorModel.setCcUser(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccUser())));
			darkhastFaktorModel.setCcDarkhastFaktorNoeForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccDarkhastFaktorNoeForosh())));
			darkhastFaktorModel.setIsTajil(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_IsTajil())));
			darkhastFaktorModel.setIsTakhir(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_IsTakhir())));

            darkhastFaktorModels.add(darkhastFaktorModel);
            cursor.moveToNext();
        }
        return darkhastFaktorModels;
    }


}
