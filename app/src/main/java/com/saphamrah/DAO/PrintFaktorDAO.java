package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.PrintFaktorModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetPrintFaktorResult;
import com.saphamrah.WebService.ServiceResponse.GetTizeriResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintFaktorDAO
{

    PrintFaktorModel modelGetTABLE_NAME = new PrintFaktorModel();
    private DBHelper dbHelper;




    /*
    create constructor
     */
    public PrintFaktorDAO(Context context)
    {
        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "PrintFaktorDAO" , "" , "constructor" , "");
        }
    }

    /*
    fetch = request server and get result
     */
    public void fetchPrintFaktor(final Context context, final String activityNameForLog, int ccAfrad ,  final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, PrintFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchTizer", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetPrintFaktorResult> call = apiServiceGet.getPrintFaktor(ccAfrad);
            call.enqueue(new Callback<GetPrintFaktorResult>()
            {
                @Override
                public void onResponse(Call<GetPrintFaktorResult> call, Response<GetPrintFaktorResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, PrintFaktorDAO.class.getSimpleName(), "", "fetchPrintFaktor", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetPrintFaktorResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), PrintFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchPrintFaktor", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), PrintFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchPrintFaktor", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, PrintFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchPrintFaktor", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), PrintFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchPrintFaktor", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetPrintFaktorResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), PrintFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchPrintFaktor", "onFailure");
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
                modelGetTABLE_NAME.getCOLUMN_CodeMoshtary(),
                modelGetTABLE_NAME.getCOLUMN_MablaghFaktor(),
                modelGetTABLE_NAME.getCOLUMN_NameMoshtary(),
                modelGetTABLE_NAME.getCOLUMN_Radif(),
                modelGetTABLE_NAME.getCOLUMN_ShomarehFaktor(),
                modelGetTABLE_NAME.getCOLUMN_UniqID_Tablet(),
        };
    }

    /*
    set result model to DB
     */
    public boolean insertGroup(ArrayList<PrintFaktorModel> models)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (PrintFaktorModel model : models)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(modelGetTABLE_NAME.getTABLE_NAME() , null , contentValues);

                Log.i("PrintFaktorDao" , contentValues.toString());
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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorGroupInsert , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "PrintFaktorDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    // get all data as db
    public ArrayList<PrintFaktorModel> getAll()
    {
        ArrayList<PrintFaktorModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "PrintFaktorDAO" , "" , "getAll" , "");
        }
        return models;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(modelGetTABLE_NAME.getTABLE_NAME(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "PrintFaktorDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(PrintFaktorModel model)
    {
        ContentValues contentValues = new ContentValues();
        PrintFaktorModel printFaktorModel = new PrintFaktorModel();
        contentValues.put(printFaktorModel.getCOLUMN_CodeMoshtary() , model.getCodeMoshtary());
        contentValues.put(printFaktorModel.getCOLUMN_MablaghFaktor() , model.getMablaghFaktor());
        contentValues.put(printFaktorModel.getCOLUMN_NameMoshtary() , model.getNameMoshtary());
        contentValues.put(printFaktorModel.getCOLUMN_ShomarehFaktor() , model.getShomarehFaktor());
        contentValues.put(printFaktorModel.getCOLUMN_Radif() , model.getRadif());
        contentValues.put(printFaktorModel.getCOLUMN_UniqID_Tablet() , model.getUniqID_Tablet());


        return contentValues;
    }


    /*
    set  cursor to model in get All
     */
    private ArrayList<PrintFaktorModel> cursorToModel(Cursor cursor)
    {
        ArrayList<PrintFaktorModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            PrintFaktorModel model = new PrintFaktorModel();
            model.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_CodeMoshtary())));
            model.setMablaghFaktor(cursor.getDouble(cursor.getColumnIndex(model.getCOLUMN_MablaghFaktor())));
            model.setNameMoshtary(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_NameMoshtary())));
            model.setShomarehFaktor(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_ShomarehFaktor())));
            model.setUniqID_Tablet(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_UniqID_Tablet())));
            model.setRadif(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_Radif())));

            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }



}
