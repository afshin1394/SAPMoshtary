package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;

import com.saphamrah.Model.ForoshandehMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllForoshandehMoshtaryByccMasirResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForoshandehMoshtaryDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ForoshandehMoshtaryDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMoshtaryDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            ForoshandehMoshtaryModel.COLUMN_ccForoshandehMoshtary(),
            ForoshandehMoshtaryModel.COLUMN_ccMoshtary(),
            ForoshandehMoshtaryModel.COLUMN_ccMasir(),
            ForoshandehMoshtaryModel.COLUMN_Olaviat(),
            ForoshandehMoshtaryModel.COLUMN_SaatVisitAz(),
            ForoshandehMoshtaryModel.COLUMN_SaatVisitTa()
        };
    }

    public void fetchAllForoshandehMoshtaryByccMasir(final Context context, final String activityNameForLog,final String ccMasir, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllForoshandehMoshtaryByccMasir", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllForoshandehMoshtaryByccMasirResult> call = apiService.getAllForoshandehMoshtaryByccMasir(ccMasir);
            call.enqueue(new Callback<GetAllForoshandehMoshtaryByccMasirResult>() {
                @Override
                public void onResponse(Call<GetAllForoshandehMoshtaryByccMasirResult> call, Response<GetAllForoshandehMoshtaryByccMasirResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ForoshandehMoshtaryDAO.class.getSimpleName(), "", "fetchAllForoshandehMoshtaryByccMasir", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllForoshandehMoshtaryByccMasirResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllForoshandehMoshtaryByccMasir", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), ForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllForoshandehMoshtaryByccMasir", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllForoshandehMoshtaryByccMasir", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllForoshandehMoshtaryByccMasir", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllForoshandehMoshtaryByccMasirResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllForoshandehMoshtaryByccMasir", "onFailure");
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

    public boolean insertGroup(ArrayList<ForoshandehMoshtaryModel> foroshandehMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ForoshandehMoshtaryModel foroshandehMoshtaryModel : foroshandehMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(foroshandehMoshtaryModel);
                db.insertOrThrow(ForoshandehMoshtaryModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehMoshtaryDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(ForoshandehMoshtaryModel foroshandehMoshtaryModel)
    {
        ContentValues contentValues = modelToContentvalue(foroshandehMoshtaryModel);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(ForoshandehMoshtaryModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , ForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehMoshtaryDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<ForoshandehMoshtaryModel> getAll()
    {
        ArrayList<ForoshandehMoshtaryModel> foroshandehMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehMoshtaryModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehMoshtaryDAO" , "" , "getAll" , "");
        }
        return foroshandehMoshtaryModels;
    }

    public ArrayList<ForoshandehMoshtaryModel> getOne()
    {
        ArrayList<ForoshandehMoshtaryModel> foroshandehMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehMoshtaryModel.TableName(), allColumns(), null, null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehMoshtaryDAO" , "" , "getOne" , "");
        }
        return foroshandehMoshtaryModels;
    }

    public ForoshandehMoshtaryModel getByccMoshtary(int ccMoshtary)
    {
        ForoshandehMoshtaryModel foroshandehMoshtaryModel = new ForoshandehMoshtaryModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehMoshtaryModel.TableName(), allColumns(), "ccMoshtary= ?", new String[]{String.valueOf(ccMoshtary)}, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    Log.d("customerInfo" , "count : " + cursor.getCount() + " and ccMoshtary : " + ccMoshtary);
                    foroshandehMoshtaryModel = cursorToModel(cursor).get(0);
                    Log.d("customerInfo" , "foroshandeh Moshtary " + foroshandehMoshtaryModel.toString());
                }

                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMoshtaryDAO" , "" , "getByccMoshtary.ccMoshtary = " + ccMoshtary , "");
        }
        return foroshandehMoshtaryModel;
    }

    public boolean updateSaateVisit(int ccMoshtary , String saateVisit)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ForoshandehMoshtaryModel.COLUMN_SaatVisitAz() , saateVisit);
            contentValues.put(ForoshandehMoshtaryModel.COLUMN_SaatVisitTa() , saateVisit);
            db.update(ForoshandehMoshtaryModel.TableName() , contentValues , ForoshandehMoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMoshtaryJadid(int newccMoshtary , int oldccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ForoshandehMoshtaryModel.COLUMN_ccMoshtary(), newccMoshtary);
            db.update(ForoshandehMoshtaryModel.TableName(), values, "ccMoshtary= ?", new String[]{String.valueOf(oldccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , ForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehMoshtaryDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ForoshandehMoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehMoshtaryDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ForoshandehMoshtaryModel.TableName() , ForoshandehMoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , ForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehMoshtaryDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ForoshandehMoshtaryModel foroshandehMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();

        if (foroshandehMoshtaryModel.getCcForoshandehMoshtary() > 0)
        {
            contentValues.put(ForoshandehMoshtaryModel.COLUMN_ccForoshandehMoshtary() , foroshandehMoshtaryModel.getCcForoshandehMoshtary());
        }
        contentValues.put(ForoshandehMoshtaryModel.COLUMN_ccMoshtary() , foroshandehMoshtaryModel.getCcMoshtary());
        contentValues.put(ForoshandehMoshtaryModel.COLUMN_ccMasir() , foroshandehMoshtaryModel.getCcMasir());
        contentValues.put(ForoshandehMoshtaryModel.COLUMN_Olaviat() , foroshandehMoshtaryModel.getOlaviat());
        contentValues.put(ForoshandehMoshtaryModel.COLUMN_SaatVisitAz() , foroshandehMoshtaryModel.getSaatVisitAz());
        contentValues.put(ForoshandehMoshtaryModel.COLUMN_SaatVisitTa() , foroshandehMoshtaryModel.getSaatVisitTa());

        return contentValues;
    }


    private ArrayList<ForoshandehMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ForoshandehMoshtaryModel> foroshandehMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ForoshandehMoshtaryModel foroshandehMoshtaryModel = new ForoshandehMoshtaryModel();

            foroshandehMoshtaryModel.setCcForoshandehMoshtary(cursor.getInt(cursor.getColumnIndex(ForoshandehMoshtaryModel.COLUMN_ccForoshandehMoshtary())));
            foroshandehMoshtaryModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(ForoshandehMoshtaryModel.COLUMN_ccMoshtary())));
            foroshandehMoshtaryModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(ForoshandehMoshtaryModel.COLUMN_ccMasir())));
            foroshandehMoshtaryModel.setOlaviat(cursor.getDouble(cursor.getColumnIndex(ForoshandehMoshtaryModel.COLUMN_Olaviat())));

            String saateVisit = cursor.getString(cursor.getColumnIndex(ForoshandehMoshtaryModel.COLUMN_SaatVisitAz()));
            if (saateVisit != null)
            {
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                    Date date = sdf.parse(saateVisit);
                    saateVisit = (String) DateFormat.format(Constants.DATE_TIME_FORMAT() , date);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            foroshandehMoshtaryModel.setSaatVisitAz(saateVisit);
            foroshandehMoshtaryModel.setSaatVisitTa(saateVisit);

            foroshandehMoshtaryModels.add(foroshandehMoshtaryModel);
            cursor.moveToNext();
        }
        return foroshandehMoshtaryModels;
    }
    
}
