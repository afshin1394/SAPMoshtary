package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllrptAmarForoshResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RptForoshDAO
{

    private DBHelper dbHelper;
    private Context context;


    public RptForoshDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptForoshDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptForoshModel.COLUMN_ExtraProp_ccRpt_Forosh(),
            RptForoshModel.COLUMN_CountFaktorRooz(),
            RptForoshModel.COLUMN_SumMablaghFaktorRooz(),
            RptForoshModel.COLUMN_CountFaktorMah(),
            RptForoshModel.COLUMN_SumMablaghFaktorMah(),
            RptForoshModel.COLUMN_CountMarjoeeRooz(),
            RptForoshModel.COLUMN_SumMablaghMarjoeeRooz(),
            RptForoshModel.COLUMN_CountMarjoeeMah(),
            RptForoshModel.COLUMN_SumMablaghMarjoeeMah(),
            RptForoshModel.COLUMN_MianginForoshRoozMojaz(),
            RptForoshModel.COLUMN_ccGorohForosh(),
            RptForoshModel.COLUMN_SharhGorohForosh(),
            RptForoshModel.COLUMN_ccForoshandeh(),
            RptForoshModel.COLUMN_SharhForoshandeh()
        };
    }

    public void fetchAllrptAmarForosh(final Context context, final String activityNameForLog, final String ccForoshandeh, final String taTarikh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptAmarForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllrptAmarForoshResult> call = apiService.getAllrptAmarForosh(ccForoshandeh , taTarikh);
            call.enqueue(new Callback<GetAllrptAmarForoshResult>() {
                @Override
                public void onResponse(Call<GetAllrptAmarForoshResult> call, Response<GetAllrptAmarForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptForoshDAO.class.getSimpleName(), "", "fetchAllrptAmarForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllrptAmarForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptAmarForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptAmarForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptAmarForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptAmarForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptAmarForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptAmarForosh", "onFailure");
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

    public boolean insertGroup(ArrayList<RptForoshModel> rptForoshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptForoshModel rptForoshModel : rptForoshModels)
            {
                ContentValues contentValues = modelToContentvalue(rptForoshModel);
                db.insertOrThrow(RptForoshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptForoshDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptForoshModel> getAll()
    {
        ArrayList<RptForoshModel> rptForoshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptForoshModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptForoshDAO" , "" , "getAll" , "");
        }
        return rptForoshModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptForoshModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptForoshDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptForoshModel rptForoshModel)
    {
        ContentValues contentValues = new ContentValues();

        if (rptForoshModel.getExtraProp_ccRpt_Forosh() > 0)
        {
            contentValues.put(RptForoshModel.COLUMN_ExtraProp_ccRpt_Forosh() , rptForoshModel.getExtraProp_ccRpt_Forosh());
        }
        contentValues.put(RptForoshModel.COLUMN_CountFaktorRooz() , rptForoshModel.getCountFaktorRooz());
        contentValues.put(RptForoshModel.COLUMN_SumMablaghFaktorRooz() , rptForoshModel.getSumMablaghFaktorRooz());
        contentValues.put(RptForoshModel.COLUMN_CountFaktorMah() , rptForoshModel.getCountFaktorMah());
        contentValues.put(RptForoshModel.COLUMN_SumMablaghFaktorMah() , rptForoshModel.getSumMablaghFaktorMah());
        contentValues.put(RptForoshModel.COLUMN_CountMarjoeeRooz() , rptForoshModel.getCountMarjoeeRooz());
        contentValues.put(RptForoshModel.COLUMN_SumMablaghMarjoeeRooz() , rptForoshModel.getSumMablaghMarjoeeRooz());
        contentValues.put(RptForoshModel.COLUMN_CountMarjoeeMah() , rptForoshModel.getCountMarjoeeMah());
        contentValues.put(RptForoshModel.COLUMN_SumMablaghMarjoeeMah() , rptForoshModel.getSumMablaghMarjoeeMah());
        contentValues.put(RptForoshModel.COLUMN_MianginForoshRoozMojaz() , rptForoshModel.getMianginForoshRoozMojaz());
        contentValues.put(RptForoshModel.COLUMN_ccGorohForosh() , rptForoshModel.getCcGorohForosh());
        contentValues.put(RptForoshModel.COLUMN_SharhGorohForosh() , rptForoshModel.getSharhGorohForosh());
        contentValues.put(RptForoshModel.COLUMN_ccForoshandeh() , rptForoshModel.getCcForoshandeh());
        contentValues.put(RptForoshModel.COLUMN_SharhForoshandeh() , rptForoshModel.getSharhForoshandeh());

        return contentValues;
    }


    private ArrayList<RptForoshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptForoshModel> rptForoshModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptForoshModel rptForoshModel = new RptForoshModel();

            rptForoshModel.setCountFaktorRooz(cursor.getInt(cursor.getColumnIndex(RptForoshModel.COLUMN_CountFaktorRooz())));
            rptForoshModel.setSumMablaghFaktorRooz(cursor.getFloat(cursor.getColumnIndex(RptForoshModel.COLUMN_SumMablaghFaktorRooz())));
            rptForoshModel.setCountFaktorMah(cursor.getInt(cursor.getColumnIndex(RptForoshModel.COLUMN_CountFaktorMah())));
            rptForoshModel.setSumMablaghFaktorMah(cursor.getDouble(cursor.getColumnIndex(RptForoshModel.COLUMN_SumMablaghFaktorMah())));
            rptForoshModel.setCountMarjoeeRooz(cursor.getFloat(cursor.getColumnIndex(RptForoshModel.COLUMN_CountMarjoeeRooz())));
            rptForoshModel.setSumMablaghMarjoeeRooz(cursor.getInt(cursor.getColumnIndex(RptForoshModel.COLUMN_SumMablaghMarjoeeRooz())));
            rptForoshModel.setCountMarjoeeMah(cursor.getFloat(cursor.getColumnIndex(RptForoshModel.COLUMN_CountMarjoeeMah())));
            rptForoshModel.setSumMablaghMarjoeeMah(cursor.getFloat(cursor.getColumnIndex(RptForoshModel.COLUMN_SumMablaghMarjoeeMah())));
            rptForoshModel.setMianginForoshRoozMojaz(cursor.getInt(cursor.getColumnIndex(RptForoshModel.COLUMN_MianginForoshRoozMojaz())));
            rptForoshModel.setCcGorohForosh(cursor.getInt(cursor.getColumnIndex(RptForoshModel.COLUMN_ccGorohForosh())));
            rptForoshModel.setSharhGorohForosh(cursor.getString(cursor.getColumnIndex(RptForoshModel.COLUMN_SharhGorohForosh())));
            rptForoshModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(RptForoshModel.COLUMN_ccForoshandeh())));
            rptForoshModel.setSharhForoshandeh(cursor.getString(cursor.getColumnIndex(RptForoshModel.COLUMN_SharhForoshandeh())));

            rptForoshModels.add(rptForoshModel);
            cursor.moveToNext();
        }
        return rptForoshModels;
    }

}
