package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.MoshtaryMorajehShodehRoozModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.MoshtaryMorajehShodehRoozResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryMorajehShodehRoozDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryMorajehShodehRoozDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryMorajehShodehRoozDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryMorajehShodehRoozModel.COLUMN_ccForoshandeh(),
            MoshtaryMorajehShodehRoozModel.COLUMN_ccMoshtary(),
            MoshtaryMorajehShodehRoozModel.COLUMN_NoeMorajeh(),
            MoshtaryMorajehShodehRoozModel.COLUMN_EtebarEzafehShodeh(),
            MoshtaryMorajehShodehRoozModel.COLUMN_MablaghMandehFaktor()
        };
    }


    public void fetchMoshtaryMorajehShodehRooz(final Context context, final String activityNameForLog, final String ccForoshandeh , String ccMasir, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryMorajehShodehRoozDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryMorajehShodehRooz", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<MoshtaryMorajehShodehRoozResult> call = apiService.getMoshtaryMorajehShodehRooz(ccForoshandeh , ccMasir);
            call.enqueue(new Callback<MoshtaryMorajehShodehRoozResult>() {
                @Override
                public void onResponse(Call<MoshtaryMorajehShodehRoozResult> call, Response<MoshtaryMorajehShodehRoozResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryMorajehShodehRoozDAO.class.getSimpleName(), "", "fetchMoshtaryMorajehShodehRooz", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            MoshtaryMorajehShodehRoozResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryMorajehShodehRoozDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryMorajehShodehRooz", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryMorajehShodehRoozDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryMorajehShodehRooz", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryMorajehShodehRoozDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryMorajehShodehRooz", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryMorajehShodehRoozDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryMorajehShodehRooz", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<MoshtaryMorajehShodehRoozResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryMorajehShodehRoozDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorSatr", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRoozModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryMorajehShodehRoozModel moshtaryMorajehShodehRoozModel : moshtaryMorajehShodehRoozModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryMorajehShodehRoozModel);
                db.insertOrThrow(MoshtaryMorajehShodehRoozModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryMorajehShodehRoozModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryMorajehShodehRoozDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(MoshtaryMorajehShodehRoozModel moshtaryMorajehShodehRoozModel)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();

            ContentValues contentValues = modelToContentvalue(moshtaryMorajehShodehRoozModel);
            db.insertOrThrow(MoshtaryMorajehShodehRoozModel.TableName() , null , contentValues);

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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryMorajehShodehRoozModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryMorajehShodehRoozDAO" , "" , "insert" , "");
            return false;
        }
    }


    public ArrayList<MoshtaryMorajehShodehRoozModel> getAll()
    {
        ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRoozModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryMorajehShodehRoozModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryMorajehShodehRoozModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryMorajehShodehRoozModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryMorajehShodehRoozDAO" , "" , "getAll" , "");
        }
        return moshtaryMorajehShodehRoozModels;
    }

    public ArrayList<MoshtaryMorajehShodehRoozModel> getByccMoshtary(int ccMoshtary)
    {
        ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRoozModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryMorajehShodehRoozModel.TableName(), allColumns(), MoshtaryMorajehShodehRoozModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryMorajehShodehRoozModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryMorajehShodehRoozModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryMorajehShodehRoozDAO" , "" , "getByccMoshtary" , "");
        }
        return moshtaryMorajehShodehRoozModels;
    }

    public ArrayList<MoshtaryMorajehShodehRoozModel> getEtebarByccMoshtary(int ccMoshtary)
    {
        ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRooz = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryMorajehShodehRoozModel.TableName(), allColumns(), "NoeMorajeh=4 AND ccMoshtary="+ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryMorajehShodehRooz = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryMorajehShodehRoozModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryMorajehShodehRoozDAO" , "" , "getEtebarByccMoshtary" , "");

        }
        return moshtaryMorajehShodehRooz;
    }

    public int getCountByccMoshtary(int ccMoshtary)
    {
        int count = -1;
        String query = "select count(*) from " + MoshtaryMorajehShodehRoozModel.TableName() + " where " + MoshtaryMorajehShodehRoozModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
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
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryMorajehShodehRoozModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryMorajehShodehRoozDAO" , "" , "getCountByccMoshtary" , "");
        }
        return count;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryMorajehShodehRoozModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryMorajehShodehRoozModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryMorajehShodehRoozDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryMorajehShodehRoozModel.TableName(), MoshtaryMorajehShodehRoozModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryMorajehShodehRoozModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryMorajehShodehRoozDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryMorajehShodehRoozModel moshtaryMorajehShodehRoozModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoshtaryMorajehShodehRoozModel.COLUMN_ccForoshandeh() , moshtaryMorajehShodehRoozModel.getCcForoshandeh());
        contentValues.put(MoshtaryMorajehShodehRoozModel.COLUMN_ccMoshtary() , moshtaryMorajehShodehRoozModel.getCcMoshtary());
        contentValues.put(MoshtaryMorajehShodehRoozModel.COLUMN_NoeMorajeh() , moshtaryMorajehShodehRoozModel.getNoeMorajeh());
        contentValues.put(MoshtaryMorajehShodehRoozModel.COLUMN_EtebarEzafehShodeh() , moshtaryMorajehShodehRoozModel.getEtebarEzafehShodeh());
        contentValues.put(MoshtaryMorajehShodehRoozModel.COLUMN_MablaghMandehFaktor() , moshtaryMorajehShodehRoozModel.getMablaghMandehFaktor());

        return contentValues;
    }


    private ArrayList<MoshtaryMorajehShodehRoozModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryMorajehShodehRoozModel> moshtaryMorajehShodehRoozModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryMorajehShodehRoozModel moshtaryMorajehShodehRoozModel = new MoshtaryMorajehShodehRoozModel();

            moshtaryMorajehShodehRoozModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(MoshtaryMorajehShodehRoozModel.COLUMN_ccForoshandeh())));
            moshtaryMorajehShodehRoozModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryMorajehShodehRoozModel.COLUMN_ccMoshtary())));
            moshtaryMorajehShodehRoozModel.setNoeMorajeh(cursor.getInt(cursor.getColumnIndex(MoshtaryMorajehShodehRoozModel.COLUMN_NoeMorajeh())));
            moshtaryMorajehShodehRoozModel.setEtebarEzafehShodeh(cursor.getInt(cursor.getColumnIndex(MoshtaryMorajehShodehRoozModel.COLUMN_EtebarEzafehShodeh())));
            moshtaryMorajehShodehRoozModel.setMablaghMandehFaktor(cursor.getInt(cursor.getColumnIndex(MoshtaryMorajehShodehRoozModel.COLUMN_MablaghMandehFaktor())));

            moshtaryMorajehShodehRoozModels.add(moshtaryMorajehShodehRoozModel);
            cursor.moveToNext();
        }
        return moshtaryMorajehShodehRoozModels;
    }

}
