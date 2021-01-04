package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryPolygonModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetMoshtaryPolygonResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryPolygonDAO 
{
    private DBHelper dbHelper;
    private Context context;


    public MoshtaryPolygonDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryPolygonDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryPolygonModel.COLUMN_ccPolygonForosh(),
            MoshtaryPolygonModel.COLUMN_ccMoshtary(),
            MoshtaryPolygonModel.COLUMN_CanVisitKharejAzMahal(),
            MoshtaryPolygonModel.COLUMN_ccMasir(),
            MoshtaryPolygonModel.COLUMN_ToorVisit()
        };
    }

    public void fetchMoshtaryPolygon(final Context context, final String activityNameForLog, final String ccMasirs, final String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryPolygonDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPolygon", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMoshtaryPolygonResult> call = apiServiceGet.getMoshtaryPolygon(ccMasirs , ccMoshtarys);
            call.enqueue(new Callback<GetMoshtaryPolygonResult>() {
                @Override
                public void onResponse(Call<GetMoshtaryPolygonResult> call, Response<GetMoshtaryPolygonResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryPolygonDAO.class.getSimpleName(), "", "fetchMoshtaryPolygon", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMoshtaryPolygonResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryPolygonDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPolygon", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryPolygonDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPolygon", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryPolygonDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPolygon", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryPolygonDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPolygon", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMoshtaryPolygonResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryPolygonDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPolygon", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<MoshtaryPolygonModel> moshtaryPolygonModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryPolygonModel moshtaryPolygonModel : moshtaryPolygonModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryPolygonModel);
                db.insertOrThrow(MoshtaryPolygonModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryPolygonModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPolygonDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MoshtaryPolygonModel> getAll()
    {
        ArrayList<MoshtaryPolygonModel> moshtaryPolygonModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryPolygonModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryPolygonModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryPolygonModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPolygonDAO" , "" , "getAll" , "");
        }
        return moshtaryPolygonModels;
    }

    public int getCanVisitKharejAzMahal(int ccMoshtary)
    {
        int CanVisitKharejAzMahal = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //Cursor cursor = db.query(MoshtaryPolygonModel.TableName(), allColumns(), MoshtaryPolygonModel.COLUMN_CanVisitKharejAzMahal()+ " = " + ccMoshtary, null, null, null, null);
            Cursor cursor = db.query(MoshtaryPolygonModel.TableName(), allColumns(), MoshtaryPolygonModel.COLUMN_ccMoshtary()+ " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    CanVisitKharejAzMahal = cursorToModel(cursor).get(0).getCanVisitKharejAzMahal();
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryPolygonModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPolygonDAO" , "" , "getAll" , "");
        }
        return CanVisitKharejAzMahal;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryPolygonModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryPolygonModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPolygonDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryPolygonModel moshtaryPolygonModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoshtaryPolygonModel.COLUMN_ccPolygonForosh() , moshtaryPolygonModel.getCcPolygonForosh());
        contentValues.put(MoshtaryPolygonModel.COLUMN_ccMoshtary() , moshtaryPolygonModel.getCcMoshtary());
        contentValues.put(MoshtaryPolygonModel.COLUMN_CanVisitKharejAzMahal() , moshtaryPolygonModel.getCanVisitKharejAzMahal());
        contentValues.put(MoshtaryPolygonModel.COLUMN_ccMasir() , moshtaryPolygonModel.getCcMasir());
        contentValues.put(MoshtaryPolygonModel.COLUMN_ToorVisit() , moshtaryPolygonModel.getToorVisit());

        return contentValues;
    }


    private ArrayList<MoshtaryPolygonModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryPolygonModel> moshtaryPolygonModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryPolygonModel moshtaryPolygonModel = new MoshtaryPolygonModel();

            moshtaryPolygonModel.setCcPolygonForosh(cursor.getInt(cursor.getColumnIndex(MoshtaryPolygonModel.COLUMN_ccPolygonForosh())));
            moshtaryPolygonModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryPolygonModel.COLUMN_ccMoshtary())));
            moshtaryPolygonModel.setCanVisitKharejAzMahal(cursor.getInt(cursor.getColumnIndex(MoshtaryPolygonModel.COLUMN_CanVisitKharejAzMahal())));
            moshtaryPolygonModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(MoshtaryPolygonModel.COLUMN_ccMasir())));
            moshtaryPolygonModel.setToorVisit(cursor.getInt(cursor.getColumnIndex(MoshtaryPolygonModel.COLUMN_ToorVisit())));

            moshtaryPolygonModels.add(moshtaryPolygonModel);
            cursor.moveToNext();
        }
        return moshtaryPolygonModels;
    }
    
    
}
