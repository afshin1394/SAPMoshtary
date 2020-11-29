package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.saphamrah.Model.RptListVosolModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetrptListVosolResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RptListVosolDAO
{


    private DBHelper dbHelper;
    private Context context;


    public RptListVosolDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptListVosolDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptListVosolModel.COLUMN_ccDarkhastFaktor(),
            RptListVosolModel.COLUMN_CodeMoshtary(),
            RptListVosolModel.COLUMN_NameMoshtary(),
            RptListVosolModel.COLUMN_ShomarehFaktor(),
            RptListVosolModel.COLUMN_MablaghKhalesFaktor(),
            RptListVosolModel.COLUMN_MablaghNaghd(),
            RptListVosolModel.COLUMN_MablaghFish(),
            RptListVosolModel.COLUMN_MablaghPos(),
            RptListVosolModel.COLUMN_MablaghCheck(),
            RptListVosolModel.COLUMN_MablaghMarjoee(),
            RptListVosolModel.COLUMN_MablaghTajil(),
            RptListVosolModel.COLUMN_MablaghDirKard(),
            RptListVosolModel.COLUMN_MablaghResid(),
            RptListVosolModel.COLUMN_MablaghBestankari(),
            RptListVosolModel.COLUMN_MablaghMandehFaktor()
        };
    }

    public void fetchRPTListVosol(final Context context, final String activityNameForLog, final String ccAfradForoshandehMamorPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTListVosol", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetrptListVosolResult> call = apiService.getrptListVosol(ccAfradForoshandehMamorPakhsh);
            call.enqueue(new Callback<GetrptListVosolResult>() {
                @Override
                public void onResponse(Call<GetrptListVosolResult> call, Response<GetrptListVosolResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptListVosolDAO.class.getSimpleName(), "", "fetchRPTListVosol", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetrptListVosolResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTListVosol", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTListVosol", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTListVosol", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTListVosol", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetrptListVosolResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTListVosol", "onFailure");
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

    public boolean insertGroup(ArrayList<RptListVosolModel> rptListVosolModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptListVosolModel rptListVosolModel : rptListVosolModels)
            {
                ContentValues contentValues = modelToContentvalue(rptListVosolModel);
                db.insertOrThrow(RptListVosolModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptListVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptListVosolDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptListVosolModel> getAll()
    {
        ArrayList<RptListVosolModel> rptListVosolModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptListVosolModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptListVosolModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptListVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptListVosolDAO" , "" , "getAll" , "");
        }
        return rptListVosolModels;
    }

    public ArrayList<RptListVosolModel> getFaktorMandehDar()
    {
        ArrayList<RptListVosolModel> rptListVosolModels = new ArrayList<>();
        try
        {
            String query = "SELECT   0 as _id, 0 AS ccDarkhastFaktor, CodeMoshtary, NameMoshtary, ShomarehFaktor,  \n" +
                    " MablaghKhalesFaktor AS MablaghFaktor , MablaghMandehFaktor AS MablaghMandeh, \n" +
                    " MablaghNaghd, MablaghFish, MablaghPos, MablaghCheck, MablaghMarjoee, MablaghTajil, \n" +
                    " MablaghDirKard, MablaghResid, MablaghBestankari, \n" +
                    " MablaghMandehFaktor - (MablaghNaghd + MablaghFish+ MablaghPos+MablaghCheck+ \n" +
                    " MablaghMarjoee+ MablaghTajil+ MablaghDirKard) AS MandehNahaee, \n" +
                    " (MablaghNaghd + MablaghFish+ MablaghPos+MablaghCheck+ \n" +
                    " MablaghMarjoee+ MablaghTajil+ MablaghDirKard) AS JamDariafti , 0 AS Jam \n" +
                    " FROM     Rpt_ListVosol \n" +
                    " UNION ALL \n" +
                    " SELECT   1 as _id, 0 AS ccDarkhastFaktor, '' , '', '', SUM(MablaghKhalesFaktor) AS MablaghFaktor, \n" +
                    " SUM(MablaghMandehFaktor) AS MablaghMandeh, SUM(MablaghNaghd) AS MablaghNaghd, \n" +
                    " SUM(MablaghFish) AS MablaghFish, SUM(MablaghPos) AS MablaghPos, SUM(MablaghCheck) AS MablaghCheck, \n" +
                    " SUM(MablaghMarjoee) AS MablaghMarjoee, SUM(MablaghTajil) AS MablaghTajil, SUM(MablaghDirKard) AS MablaghDirKard, \n" +
                    " SUM(MablaghResid) AS MablaghResid, SUM(MablaghBestankari) AS MablaghBestankari, 0 AS MandehNahaee, 0 AS JamDariafti, 1 AS Jam \n" +
                    " FROM     Rpt_ListVosol \n" +
                    " order by _id \n";

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptListVosolModels = cursorToModelForSumListVosol(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptListVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptListVosolDAO" , "" , "getFaktorMandehDar" , "");
        }
        return rptListVosolModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptListVosolModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptListVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptListVosolDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptListVosolModel rptListVosolModel)
    {
        ContentValues contentValues = new ContentValues();

        if (rptListVosolModel.getCcDarkhastFaktor() > 0)
        {
            contentValues.put(RptListVosolModel.COLUMN_ccDarkhastFaktor() , rptListVosolModel.getCcDarkhastFaktor());
        }
        contentValues.put(RptListVosolModel.COLUMN_CodeMoshtary() , rptListVosolModel.getCodeMoshtary());
        contentValues.put(RptListVosolModel.COLUMN_NameMoshtary() , rptListVosolModel.getNameMoshtary());
        contentValues.put(RptListVosolModel.COLUMN_ShomarehFaktor() , rptListVosolModel.getShomarehFaktor());
        contentValues.put(RptListVosolModel.COLUMN_MablaghKhalesFaktor() , rptListVosolModel.getMablaghKhalesFaktor());
        contentValues.put(RptListVosolModel.COLUMN_MablaghNaghd() , rptListVosolModel.getMablaghNaghd());
        contentValues.put(RptListVosolModel.COLUMN_MablaghFish() , rptListVosolModel.getMablaghFish());
        contentValues.put(RptListVosolModel.COLUMN_MablaghPos() , rptListVosolModel.getMablaghPos());
        contentValues.put(RptListVosolModel.COLUMN_MablaghCheck() , rptListVosolModel.getMablaghCheck());
        contentValues.put(RptListVosolModel.COLUMN_MablaghMarjoee() , rptListVosolModel.getMablaghMarjoee());
        contentValues.put(RptListVosolModel.COLUMN_MablaghTajil() , rptListVosolModel.getMablaghTajil());
        contentValues.put(RptListVosolModel.COLUMN_MablaghDirKard() , rptListVosolModel.getMablaghDirKard());
        contentValues.put(RptListVosolModel.COLUMN_MablaghResid() , rptListVosolModel.getMablaghResid());
        contentValues.put(RptListVosolModel.COLUMN_MablaghBestankari() , rptListVosolModel.getMablaghBestankari());
        contentValues.put(RptListVosolModel.COLUMN_MablaghMandehFaktor() , rptListVosolModel.getMablaghMandehFaktor());

        return contentValues;
    }


    private ArrayList<RptListVosolModel> cursorToModelForSumListVosol(Cursor cursor)
    {
        ArrayList<RptListVosolModel> rptListVosolModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptListVosolModel rptListVosolModel = new RptListVosolModel();

            rptListVosolModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(RptListVosolModel.COLUMN_ccDarkhastFaktor())));
            rptListVosolModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptListVosolModel.COLUMN_CodeMoshtary())));
            rptListVosolModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptListVosolModel.COLUMN_NameMoshtary())));
            rptListVosolModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(RptListVosolModel.COLUMN_ShomarehFaktor())));
            rptListVosolModel.setMablaghKhalesFaktor(cursor.getFloat(cursor.getColumnIndex("MablaghFaktor")));
            rptListVosolModel.setMablaghNaghd(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghNaghd())));
            rptListVosolModel.setMablaghFish(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghFish())));
            rptListVosolModel.setMablaghPos(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghPos())));
            rptListVosolModel.setMablaghCheck(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghCheck())));
            rptListVosolModel.setMablaghMarjoee(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghMarjoee())));
            rptListVosolModel.setMablaghTajil(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghTajil())));
            rptListVosolModel.setMablaghDirKard(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghDirKard())));
            rptListVosolModel.setMablaghResid(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghResid())));
            rptListVosolModel.setMablaghBestankari(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghBestankari())));
            rptListVosolModel.setMablaghMandehFaktor(cursor.getFloat(cursor.getColumnIndex("MablaghMandeh")));
            rptListVosolModel.setMandehNahaee(cursor.getLong(cursor.getColumnIndex("MandehNahaee")));
            rptListVosolModel.setJamDariafti(cursor.getLong(cursor.getColumnIndex("JamDariafti")));
            rptListVosolModel.setJam(cursor.getLong(cursor.getColumnIndex("Jam")));

            rptListVosolModels.add(rptListVosolModel);
            cursor.moveToNext();
        }
        return rptListVosolModels;
    }

    private ArrayList<RptListVosolModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptListVosolModel> rptListVosolModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptListVosolModel rptListVosolModel = new RptListVosolModel();

            rptListVosolModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(RptListVosolModel.COLUMN_ccDarkhastFaktor())));
            rptListVosolModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptListVosolModel.COLUMN_CodeMoshtary())));
            rptListVosolModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptListVosolModel.COLUMN_NameMoshtary())));
            rptListVosolModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(RptListVosolModel.COLUMN_ShomarehFaktor())));
            rptListVosolModel.setMablaghKhalesFaktor(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghKhalesFaktor())));
            rptListVosolModel.setMablaghNaghd(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghNaghd())));
            rptListVosolModel.setMablaghFish(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghFish())));
            rptListVosolModel.setMablaghPos(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghPos())));
            rptListVosolModel.setMablaghCheck(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghCheck())));
            rptListVosolModel.setMablaghMarjoee(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghMarjoee())));
            rptListVosolModel.setMablaghTajil(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghTajil())));
            rptListVosolModel.setMablaghDirKard(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghDirKard())));
            rptListVosolModel.setMablaghResid(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghResid())));
            rptListVosolModel.setMablaghBestankari(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghBestankari())));
            rptListVosolModel.setMablaghMandehFaktor(cursor.getFloat(cursor.getColumnIndex(RptListVosolModel.COLUMN_MablaghMandehFaktor())));

            rptListVosolModels.add(rptListVosolModel);
            cursor.moveToNext();
        }
        return rptListVosolModels;
    }
    
    

}
