package com.saphamrah.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.NoeVosolMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.NoeVosolMoshtaryResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoeVosolMoshtaryDAO
{

    NoeVosolMoshtaryModel modelGetTABLE_NAME = new NoeVosolMoshtaryModel();
    private DBHelper dbHelper;


    /*
    create constructor
     */
    public NoeVosolMoshtaryDAO(Context context)
    {

        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "NoeVosolMoshtaryDAO" , "" , "constructor" , "");
        }
    }

    /*
    fetch = request server and get result
     */
    public void fetchNoeVosolMoshtary(final Context context , final String activityNameForLog, int ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeVosolMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeVosolMoshtary", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<NoeVosolMoshtaryResult> call = apiServiceGet.getNoeVosolMoshtary(ccMarkazSazmanForosh);
            call.enqueue(new Callback<NoeVosolMoshtaryResult>()
            {
                @Override
                public void onResponse(Call<NoeVosolMoshtaryResult> call, Response<NoeVosolMoshtaryResult> response)
                {
                    Log.i("NoeVosol" , "onResponse");
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, NoeVosolMoshtaryDAO.class.getSimpleName(), "", "fetchNoeVosolMoshtary", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            NoeVosolMoshtaryResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    Log.i("NoeVosol" , result.getData().toString());
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), NoeVosolMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeVosolMoshtary", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = "";
                                try
                                {
                                    endpoint = call.request().url().toString();
                                    endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                                }catch (Exception e){e.printStackTrace();}
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), NoeVosolMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeVosolMoshtary", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = "";
                            try
                            {
                                endpoint = call.request().url().toString();
                                endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                            }catch (Exception e){e.printStackTrace();}
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeVosolMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeVosolMoshtary", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), NoeVosolMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeVosolMoshtary", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<NoeVosolMoshtaryResult> call, Throwable t)
                {
                    Log.i("NoeVosol" , "failure");
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), NoeVosolMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchNoeVosolMoshtary", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    /*
    get name all columns in model
     */
    private String[] allColumns()
    {
        return new String[]
                {
                        modelGetTABLE_NAME.getCOLUMN_ccDarajeh(),
                        modelGetTABLE_NAME.getCOLUMN_ccMarkazSazmanForosh(),
                        modelGetTABLE_NAME.getCOLUMN_ccNoeMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_CodeNoeVosol(),
                        modelGetTABLE_NAME.getCOLUMN_CodeNoeVosolAzMoshtary()
                };
    }

    /*
    set result model to DB
     */
    public boolean insertGroup(ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (NoeVosolMoshtaryModel noeVosolMoshtaryModel : noeVosolMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(noeVosolMoshtaryModel);
                db.insertOrThrow(modelGetTABLE_NAME.getCOLUM_TABLE_NAME() , null , contentValues);

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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorGroupInsert , modelGetTABLE_NAME.getCOLUM_TABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "NoeVosolMoshtaryDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    // get all data as db
    public ArrayList<NoeVosolMoshtaryModel> getAll()
    {
        ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getCOLUM_TABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    noeVosolMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getCOLUM_TABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "NoeVosolMoshtaryDAO" , "" , "getAll" , "");
        }
        return noeVosolMoshtaryModels;
    }

    public ArrayList<NoeVosolMoshtaryModel> getByccNoeVosolMoshtary(int ccNoeVosol , int ccMarkazSazmanforosh, int ccNoeMoshtary , int ccDarajeh)
    {
        ArrayList<NoeVosolMoshtaryModel> arrayList = new ArrayList<>();
        String query = "select * from (SELECT DISTINCT ccMarkazSazmanForosh, ccNoeMoshtary, ccDarajeh,CodeNoeVosolAzMoshtary, CodeNoeVosol, NameNoeVosol, NameNoeVosolAzMoshtary "
                + " FROM NoeVosolMoshtary "
                + " WHERE CodeNoeVosolAzMoshtary= " + ccNoeVosol +" and ccMarkazSazmanforosh = "+ ccMarkazSazmanforosh + " and ccNoeMoshtary = "+ ccNoeMoshtary +" and ccDarajeh = "+ ccDarajeh
//                + " UNION ALL "
//                + " SELECT DISTINCT ccMarkazSazmanForosh, ccNoeMoshtary, ccDarajeh,CodeNoeVosolAzMoshtary, CodeNoeVosolAzMoshtary CodeNoeVosol, NameNoeVosolAzMoshtary NameNoeVosol, NameNoeVosolAzMoshtary "
//                + " FROM NoeVosolMoshtary "
//                + " WHERE CodeNoeVosolAzMoshtary= " + ccNoeVosol + " and ccMarkazSazmanforosh = "+ ccMarkazSazmanforosh + " and ccNoeMoshtary = "+ ccNoeMoshtary +" and ccDarajeh = "+ ccDarajeh +"
                + ")  order by codenoevosol desc";


        Log.d("Vosol","query:"+query);
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                arrayList = cursorToModel(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(BaseApplication.getContext(),Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getAllByccChildParameter" , "");
        }
        return arrayList;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(modelGetTABLE_NAME.getCOLUM_TABLE_NAME(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll , modelGetTABLE_NAME.getCOLUM_TABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "NoeVosolMoshtaryDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(NoeVosolMoshtaryModel noeVosolMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();
        NoeVosolMoshtaryModel model = new NoeVosolMoshtaryModel();
        contentValues.put(model.getCOLUMN_ccDarajeh(),noeVosolMoshtaryModel.getCcDarajeh());
        contentValues.put(model.getCOLUMN_ccMarkazSazmanForosh(),noeVosolMoshtaryModel.getCcMarkazSazmanForosh());
        contentValues.put(model.getCOLUMN_ccNoeMoshtary(),noeVosolMoshtaryModel.getCcNoeMoshtary());
        contentValues.put(model.getCOLUMN_CodeNoeVosol(),noeVosolMoshtaryModel.getCodeNoeVosol());
        contentValues.put(model.getCOLUMN_CodeNoeVosolAzMoshtary(),noeVosolMoshtaryModel.getCodeNoeVosolAzMoshtary());
        contentValues.put(model.getCOLUMN_NameNoeVosol(),noeVosolMoshtaryModel.getNameNoeVosol());
        contentValues.put(model.getCOLUMN_NameNoeVosolAzMoshtary(),noeVosolMoshtaryModel.getNameNoeVosolAzMoshtary());

        return contentValues;
    }


    /*
    set  cursor to model in get All
     */
    private ArrayList<NoeVosolMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            NoeVosolMoshtaryModel model = new NoeVosolMoshtaryModel();
            model.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccMarkazSazmanForosh())));
            model.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccNoeMoshtary())));
            model.setCcDarajeh(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccDarajeh())));
            model.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_CodeNoeVosolAzMoshtary())));
            model.setCodeNoeVosol(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_CodeNoeVosol())));
            model.setNameNoeVosol(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_NameNoeVosol())));
            model.setNameNoeVosolAzMoshtary(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_NameNoeVosolAzMoshtary())));
            noeVosolMoshtaryModels.add(model);
            cursor.moveToNext();
        }
        return noeVosolMoshtaryModels;
    }



}
