package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TakhfifHajmiSatrModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllvTakhfifHajmiSatrResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakhfifHajmiSatrDAO 
{


    private DBHelper dbHelper;
    private Context context;


    public TakhfifHajmiSatrDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TakhfifHajmiSatrDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            TakhfifHajmiSatrModel.COLUMN_ccTakhfifHajmiSatr(),
            TakhfifHajmiSatrModel.COLUMN_ccTakhfifHajmi(),
            TakhfifHajmiSatrModel.COLUMN_NameNoeField(),
            TakhfifHajmiSatrModel.COLUMN_ccNoeField(),
            TakhfifHajmiSatrModel.COLUMN_Az(),
            TakhfifHajmiSatrModel.COLUMN_Ta(),
            TakhfifHajmiSatrModel.COLUMN_CodeNoeBastehBandy(),
            TakhfifHajmiSatrModel.COLUMN_BeEza(),
            TakhfifHajmiSatrModel.COLUMN_CodeNoeBastehBandyBeEza(),
            TakhfifHajmiSatrModel.COLUMN_DarsadTakhfif(),
            TakhfifHajmiSatrModel.COLUMN_GheymatForosh(),
            TakhfifHajmiSatrModel.COLUMN_MinTedadAghlam(),
            TakhfifHajmiSatrModel.COLUMN_MinRial(),
            TakhfifHajmiSatrModel.COLUMN_ccGorohMohasebeh()
        };
    }


    /**
     * @deprecated
     * @param context
     * @param activityNameForLog
     * @param retrofitResponse
     */
    public void fetchTakhfifHajmiSatr(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvTakhfifHajmiSatrResult> call = apiServiceGet.getAllvTakhfifHajmiSatr();
            call.enqueue(new Callback<GetAllvTakhfifHajmiSatrResult>() {
                @Override
                public void onResponse(Call<GetAllvTakhfifHajmiSatrResult> call, Response<GetAllvTakhfifHajmiSatrResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifHajmiSatrDAO.class.getSimpleName(), "", "fetchTakhfifHajmiSatr", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifHajmiSatrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifHajmiSatrResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public void fetchTakhfifHajmiSatr(final Context context, final String activityNameForLog, String ccMarkazSazmanForosh, String ccTakhfifHajmis, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvTakhfifHajmiSatrResult> call = apiServiceGet.getTakhfifHajmiSatr("2", ccMarkazSazmanForosh, ccTakhfifHajmis);
            call.enqueue(new Callback<GetAllvTakhfifHajmiSatrResult>() {
                @Override
                public void onResponse(Call<GetAllvTakhfifHajmiSatrResult> call, Response<GetAllvTakhfifHajmiSatrResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifHajmiSatrDAO.class.getSimpleName(), "", "fetchTakhfifHajmiSatr", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifHajmiSatrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifHajmiSatrResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifHajmiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiSatr", "onFailure");
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


    public boolean insertGroup(ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TakhfifHajmiSatrModel takhfifHajmiSatrModel : takhfifHajmiSatrModels)
            {
                ContentValues contentValues = modelToContentvalue(takhfifHajmiSatrModel);
                db.insertOrThrow(TakhfifHajmiSatrModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TakhfifHajmiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiSatrDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<TakhfifHajmiSatrModel> getAll()
    {
        ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifHajmiSatrModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifHajmiSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiSatrDAO" , "" , "getAll" , "");
        }
        return takhfifHajmiSatrModels;
    }

    /**
     *
     * @param ccTakhfifHajmi
     * @param noeTedadRialArray -> noeTedadRialArray[0] = NoeTedadRial.Tedad.getValue() , noeTedadRialArray[1] = NoeTedadRial.Rial.getValue()
     * @param codeNoeBasteBandiArray -> codeNoeBasteBandiArray[0] = CodeNoeBastehBandy.Karton.getValue() , codeNoeBasteBandiArray[1] = CodeNoeBastehBandy.Basteh.getValue() ,codeNoeBasteBandiArray[2] = CodeNoeBastehBandy.Adad.getValue()
     * @param nameNoeField
     * @param ccNoeField
     * @param tedad
     * @param tedadBasteh
     * @param tedadKarton
     * @param mablaghKol
     * @param noeTedadRial
     * @return
     */
    public ArrayList<TakhfifHajmiSatrModel> getForFaktor(int ccTakhfifHajmi, int[] noeTedadRialArray, int[] codeNoeBasteBandiArray, int nameNoeField, int ccNoeField, double tedad, double tedadBasteh, double tedadKarton, double mablaghKol, int noeTedadRial, double vazn, long tedadAghlam)
    {
        ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrs = new ArrayList<>();
        try
        {
            Log.d("takhfifHajmi" , "ccTakhfifHajmi:" + ccTakhfifHajmi +"noeTedadRial : " + noeTedadRial + " , noeTedadRialArray[0] : " + noeTedadRialArray[0] + " , noeTedadRialArray[1]" + noeTedadRialArray[1] + " , noeTedadRialArray[2]" + noeTedadRialArray[2] + " , noeTedadRialArray[2]" + noeTedadRialArray[2]);
            String StrSQL= "SELECT * FROM TakhfifHajmiSatr WHERE ccTakhfifHajmi= " + ccTakhfifHajmi + " AND NameNoeField= " + nameNoeField + " AND ccNoeField= " + ccNoeField;
            if (noeTedadRial == noeTedadRialArray[0])//(noeTedadRial== NoeTedadRial.Tedad.getValue())
            {
                StrSQL += "  AND (  (CodeNoeBastehBandy= " + codeNoeBasteBandiArray[0] + " AND Az<= " + tedadKarton + " AND " + tedadKarton + "<= Ta)"
                        + " OR(CodeNoeBastehBandy= " + codeNoeBasteBandiArray[1] + " AND Az<= " + tedadBasteh + " AND " + tedadBasteh + "<= Ta)"
                        + " OR(CodeNoeBastehBandy= " + codeNoeBasteBandiArray[2] + " AND Az <= " + tedad + " AND " + tedad + " <= Ta)" + " )";
            }//if
            if (noeTedadRial == noeTedadRialArray[1])//(noeTedadRial== NoeTedadRial.Rial.getValue())
            {
                StrSQL +=" AND Az<= " + mablaghKol + " AND " + mablaghKol + "<= Ta";
            }
            if (noeTedadRial == noeTedadRialArray[2])//(noeTedadRial== NoeTedadRial.Rial.getValue())
            {
                StrSQL +=" AND Az<= " + vazn + " AND " + vazn + "<= Ta";
            }
            if(noeTedadRial == noeTedadRialArray[3])
            {
                StrSQL +=" AND Az<= " + tedadAghlam + " AND " + tedadAghlam + "<= Ta";
            }
            Log.d("takhfifHajmi" , "query : " + StrSQL);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(StrSQL, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifHajmiSatrs = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiSatrDAO" , "" , "getForFaktor" , "");
        }
        return takhfifHajmiSatrs;
    }

    public ArrayList<TakhfifHajmiSatrModel> getByccTakhfifHajmi(int ccTakhfifHajmi)
    {
        ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifHajmiSatrModel.TableName(), allColumns(), "ccTakhfifHajmi=" + ccTakhfifHajmi, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifHajmiSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiSatrDAO" , "" , "getByccTakhfifHajmi" , "");
        }
        return takhfifHajmiSatrModels;
    }

    public ArrayList<TakhfifHajmiSatrModel> getByccTakhfifHajmi(int ccTakhfifHajmi , String limit)
    {
        ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifHajmiSatrModel.TableName(), allColumns(), "ccTakhfifHajmi=" + ccTakhfifHajmi, null, null, null, null, limit);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifHajmiSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiSatrDAO" , "" , "getByccTakhfifHajmi" , "");
        }
        return takhfifHajmiSatrModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TakhfifHajmiSatrModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , TakhfifHajmiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiSatrDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TakhfifHajmiSatrModel takhfifHajmiSatrModel)
    {
        ContentValues contentValues = new ContentValues();

        if (takhfifHajmiSatrModel.getCcTakhfifHajmiSatr() > 0)
        {
            contentValues.put(TakhfifHajmiSatrModel.COLUMN_ccTakhfifHajmiSatr() , takhfifHajmiSatrModel.getCcTakhfifHajmiSatr());
        }
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_ccTakhfifHajmi() , takhfifHajmiSatrModel.getCcTakhfifHajmi());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_NameNoeField() , takhfifHajmiSatrModel.getNameNoeField());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_ccNoeField() , takhfifHajmiSatrModel.getCcNoeField());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_Az() , takhfifHajmiSatrModel.getAz());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_Ta() , takhfifHajmiSatrModel.getTa());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_CodeNoeBastehBandy() , takhfifHajmiSatrModel.getCodeNoeBastehBandy());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_BeEza() , takhfifHajmiSatrModel.getBeEza());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_CodeNoeBastehBandyBeEza() , takhfifHajmiSatrModel.getCodeNoeBastehBandyBeEza());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_DarsadTakhfif() , takhfifHajmiSatrModel.getDarsadTakhfif());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_GheymatForosh() , takhfifHajmiSatrModel.getGheymatForosh());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_MinTedadAghlam() , takhfifHajmiSatrModel.getMinTedadAghlam());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_MinRial() , takhfifHajmiSatrModel.getMinRial());
        contentValues.put(TakhfifHajmiSatrModel.COLUMN_ccGorohMohasebeh() , takhfifHajmiSatrModel.getCcGorohMohasebeh());

        return contentValues;
    }


    private ArrayList<TakhfifHajmiSatrModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TakhfifHajmiSatrModel takhfifHajmiSatrModel = new TakhfifHajmiSatrModel();

            takhfifHajmiSatrModel.setCcTakhfifHajmiSatr(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_ccTakhfifHajmiSatr())));
            takhfifHajmiSatrModel.setCcTakhfifHajmi(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_ccTakhfifHajmi())));
            takhfifHajmiSatrModel.setNameNoeField(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_NameNoeField())));
            takhfifHajmiSatrModel.setCcNoeField(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_ccNoeField())));
            takhfifHajmiSatrModel.setAz(cursor.getDouble(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_Az())));
            takhfifHajmiSatrModel.setTa(cursor.getDouble(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_Ta())));
            takhfifHajmiSatrModel.setCodeNoeBastehBandy(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_CodeNoeBastehBandy())));
            takhfifHajmiSatrModel.setBeEza(cursor.getDouble(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_BeEza())));
            takhfifHajmiSatrModel.setCodeNoeBastehBandyBeEza(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_CodeNoeBastehBandyBeEza())));
            takhfifHajmiSatrModel.setDarsadTakhfif(cursor.getDouble(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_DarsadTakhfif())));
            takhfifHajmiSatrModel.setGheymatForosh(cursor.getDouble(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_GheymatForosh())));
            takhfifHajmiSatrModel.setMinTedadAghlam(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_MinTedadAghlam())));
            takhfifHajmiSatrModel.setMinRial(cursor.getDouble(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_MinRial())));
            takhfifHajmiSatrModel.setCcGorohMohasebeh(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_ccGorohMohasebeh())));

            takhfifHajmiSatrModels.add(takhfifHajmiSatrModel);
            cursor.moveToNext();
        }
        return takhfifHajmiSatrModels;
    }


}
