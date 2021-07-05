package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TakhfifSenfiSatrModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllvTakhfifSenfiSatrResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakhfifSenfiSatrDAO
{

    private DBHelper dbHelper;
    private Context context;


    public TakhfifSenfiSatrDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TakhfifSenfiSatrDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
                {
                        TakhfifSenfiSatrModel.COLUMN_ccTakhfifSenfiSatr(),
                        TakhfifSenfiSatrModel.COLUMN_ccTakhfifSenfi(),
                        TakhfifSenfiSatrModel.COLUMN_NameNoeField(),
                        TakhfifSenfiSatrModel.COLUMN_ccNoeField(),
                        TakhfifSenfiSatrModel.COLUMN_Az(),
                        TakhfifSenfiSatrModel.COLUMN_Ta(),
                        TakhfifSenfiSatrModel.COLUMN_CodeNoeBastehBandy(),
                        TakhfifSenfiSatrModel.COLUMN_BeEza(),
                        TakhfifSenfiSatrModel.COLUMN_CodeNoeBastehBandyBeEza(),
                        TakhfifSenfiSatrModel.COLUMN_DarsadTakhfif(),
                        TakhfifSenfiSatrModel.COLUMN_GheymatForosh(),
                        TakhfifSenfiSatrModel.COLUMN_MinTedadAghlam(),
                        TakhfifSenfiSatrModel.COLUMN_MinRial(),
                        TakhfifSenfiSatrModel.COLUMN_ccGorohMohasebeh()

                };
    }


    public void fetchTakhfifSenfiSatr(final Context context, final String activityNameForLog, String ccMarkazForosh, String ccTakhfifSenfi, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvTakhfifSenfiSatrResult> call = apiServiceGet.getTakhfifSenfiSatr("2", ccMarkazForosh, ccTakhfifSenfi);
            call.enqueue(new Callback<GetAllvTakhfifSenfiSatrResult>()
            {
                @Override
                public void onResponse(Call<GetAllvTakhfifSenfiSatrResult> call, Response<GetAllvTakhfifSenfiSatrResult> response)
                {
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifSenfiSatrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifSenfiSatrResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    /**
     * @deprecated
     * @param context
     * @param activityNameForLog
     * @param retrofitResponse
     */
    public void fetchTakhfifSenfiSatr(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvTakhfifSenfiSatrResult> call = apiServiceGet.getAllvTakhfifSenfiSatr();
            call.enqueue(new Callback<GetAllvTakhfifSenfiSatrResult>()
            {
                @Override
                public void onResponse(Call<GetAllvTakhfifSenfiSatrResult> call, Response<GetAllvTakhfifSenfiSatrResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifSenfiSatrDAO.class.getSimpleName(), "", "fetchTakhfifSenfiSatr", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifSenfiSatrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifSenfiSatrResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), TakhfifSenfiSatrDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiSatr", "onFailure");
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

    public boolean insertGroup(ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TakhfifSenfiSatrModel takhfifSenfiSatrModel : takhfifSenfiSatrModels)
            {
                ContentValues contentValues = modelToContentvalue(takhfifSenfiSatrModel);
                db.insertOrThrow(TakhfifSenfiSatrModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TakhfifSenfiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiSatrDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<TakhfifSenfiSatrModel> getAll()
    {
        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifSenfiSatrModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifSenfiSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifSenfiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiSatrDAO" , "" , "getAll" , "");
        }
        return takhfifSenfiSatrModels;
    }


    /**
     *
     * @param ccTakhfifSenfi
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
    public ArrayList<TakhfifSenfiSatrModel> getForFaktor(int ccTakhfifSenfi, int[] noeTedadRialArray, int[] codeNoeBasteBandiArray, int nameNoeField, int ccNoeField, double tedad, double tedadBasteh, double tedadKarton, double mablaghKol, int noeTedadRial, double vazn) {
        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = new ArrayList<>();
        try {

            String query = "SELECT * FROM TakhfifSenfiSatr " + " WHERE ccTakhfifSenfi= " + ccTakhfifSenfi + "   AND NameNoeField= " + nameNoeField + "   AND ccNoeField= " + ccNoeField;
            if (noeTedadRial == noeTedadRialArray[0]) {
                query += "   AND (  (CodeNoeBastehBandy= " + codeNoeBasteBandiArray[0] + " AND Az<= " + tedadKarton + " AND " + tedadKarton + "<= Ta)"
                        + " OR(CodeNoeBastehBandy= " + codeNoeBasteBandiArray[1] + " AND Az<= " + tedadBasteh + " AND " + tedadBasteh + "<= Ta)"
                        + " OR(CodeNoeBastehBandy= " + codeNoeBasteBandiArray[2] + " AND Az <= " + tedad + " AND " + tedad + " <= Ta)"
                        + " )";
            }//if
            if (noeTedadRial == noeTedadRialArray[1])//(noeTedadRial== NoeTedadRial.Rial.getValue())
            {
                query += " AND Az<= " + mablaghKol + " AND " + mablaghKol + "<= Ta";
            }
            if (noeTedadRial == noeTedadRialArray[2])//(noeTedadRial== NoeTedadRial.Rial.getValue())
            {
                query += " AND Az<= " + vazn + " AND " + vazn + "<= Ta";
            }
            if (ccNoeField == 850) {
                Log.i("thisfaktorrr", "getForFaktor: ccNoeField:" + ccNoeField);
                Log.i("thisfaktorrr", "getForFaktor: query:" + query);
            }


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    takhfifSenfiSatrs = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifSenfiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiSatrDAO" , "" , "getForFaktor" , "");
        }
        return takhfifSenfiSatrs;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TakhfifSenfiSatrModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , TakhfifSenfiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiSatrDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TakhfifSenfiSatrModel takhfifSenfiSatrModel)
    {
        ContentValues contentValues = new ContentValues();

        if (takhfifSenfiSatrModel.getCcTakhfifSenfiSatr() > 0)
        {
            contentValues.put(TakhfifSenfiSatrModel.COLUMN_ccTakhfifSenfiSatr() , takhfifSenfiSatrModel.getCcTakhfifSenfiSatr());
        }
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_ccTakhfifSenfi(), takhfifSenfiSatrModel.getCcTakhfifSenfi());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_NameNoeField(), takhfifSenfiSatrModel.getNameNoeField());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_ccNoeField(), takhfifSenfiSatrModel.getCcNoeField());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_Az(), takhfifSenfiSatrModel.getAz());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_Ta(), takhfifSenfiSatrModel.getTa());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_CodeNoeBastehBandy(), takhfifSenfiSatrModel.getCodeNoeBastehBandy());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_BeEza(), takhfifSenfiSatrModel.getBeEza());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_CodeNoeBastehBandyBeEza(), takhfifSenfiSatrModel.getCodeNoeBastehBandyBeEza());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_DarsadTakhfif(), takhfifSenfiSatrModel.getDarsadTakhfif());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_GheymatForosh(), takhfifSenfiSatrModel.getGheymatForosh());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_MinTedadAghlam(), takhfifSenfiSatrModel.getMinTedadAghlam());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_MinRial(), takhfifSenfiSatrModel.getMinRial());
        contentValues.put(TakhfifSenfiSatrModel.COLUMN_ccGorohMohasebeh(), takhfifSenfiSatrModel.getCcGorohMohasebeh());

        return contentValues;
    }


    private ArrayList<TakhfifSenfiSatrModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TakhfifSenfiSatrModel takhfifSenfiSatrModel = new TakhfifSenfiSatrModel();

            takhfifSenfiSatrModel.setCcTakhfifSenfiSatr(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_ccTakhfifSenfiSatr())));
            takhfifSenfiSatrModel.setCcTakhfifSenfi(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_ccTakhfifSenfi())));
            takhfifSenfiSatrModel.setNameNoeField(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_NameNoeField())));
            takhfifSenfiSatrModel.setCcNoeField(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_ccNoeField())));
            takhfifSenfiSatrModel.setAz(cursor.getDouble(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_Az())));
            takhfifSenfiSatrModel.setTa(cursor.getDouble(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_Ta())));
            takhfifSenfiSatrModel.setCodeNoeBastehBandy(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_CodeNoeBastehBandy())));
            takhfifSenfiSatrModel.setBeEza(cursor.getDouble(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_BeEza())));
            takhfifSenfiSatrModel.setCodeNoeBastehBandyBeEza(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_CodeNoeBastehBandyBeEza())));
            takhfifSenfiSatrModel.setDarsadTakhfif(cursor.getDouble(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_DarsadTakhfif())));
            takhfifSenfiSatrModel.setGheymatForosh(cursor.getDouble(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_GheymatForosh())));
            takhfifSenfiSatrModel.setMinTedadAghlam(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_MinTedadAghlam())));
            takhfifSenfiSatrModel.setMinRial(cursor.getDouble(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_MinRial())));
            takhfifSenfiSatrModel.setCcGorohMohasebeh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_ccGorohMohasebeh())));

            takhfifSenfiSatrModels.add(takhfifSenfiSatrModel);
            cursor.moveToNext();
        }
        return takhfifSenfiSatrModels;
    }
    public ArrayList<TakhfifSenfiSatrModel> getByccTakhfifSenfi(int ccTakhfifSenfi , String limit)
    {
        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifSenfiSatrModel.TableName(), allColumns(), "ccTakhfifSenfi=" + ccTakhfifSenfi, null, null, null, null, limit);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifSenfiSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifSenfiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiSatrDAO" , "" , "getByccTakhfifSenfi" , "");
        }
        return takhfifSenfiSatrModels;
    }

    public ArrayList<TakhfifSenfiSatrModel> getByccTakhfifSenfi(int ccTakhfifSenfi) {


        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifSenfiSatrModel.TableName(), allColumns(), "ccTakhfifSenfi=" + ccTakhfifSenfi, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    takhfifSenfiSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, TakhfifSenfiSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiSatrDAO", "", "getByccTakhfifSenfi", "");
        }
        return takhfifSenfiSatrModels;
    }
}
