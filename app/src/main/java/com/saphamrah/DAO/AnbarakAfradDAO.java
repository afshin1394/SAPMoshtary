package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAnbarakAfradResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnbarakAfradDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public AnbarakAfradDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "AnbarakAfradDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            AnbarakAfradModel.COLUMN_ccAfradForoshandeh(),
            AnbarakAfradModel.COLUMN_ccAfradMamorPakhsh(),
            AnbarakAfradModel.COLUMN_ccAfradRanandeh(),
            AnbarakAfradModel.COLUMN_ccAfradForoshandehJaygozin(),
            AnbarakAfradModel.COLUMN_ccAfradMamorPakhshJaygozin(),
            AnbarakAfradModel.COLUMN_ccAfradRanandehJaygozin(),
            AnbarakAfradModel.COLUMN_ccNoeForoshandeh(),
            AnbarakAfradModel.COLUMN_ccAnbarak(),
            AnbarakAfradModel.COLUMN_NameAnbarak()
        };
    }

    public void fetchAnbarakAfrad(final Context context, final String activityNameForLog,final String ccAfradForoshandehMamorPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AnbarakAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAnbarakAfrad", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAnbarakAfradResult> call = apiServiceGet.getAnbarakAfrad(ccAfradForoshandehMamorPakhsh);
            call.enqueue(new Callback<GetAnbarakAfradResult>() {
                @Override
                public void onResponse(Call<GetAnbarakAfradResult> call, Response<GetAnbarakAfradResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, AnbarakAfradDAO.class.getSimpleName(), "", "fetchAnbarakAfrad", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAnbarakAfradResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), AnbarakAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAnbarakAfrad", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull), endpoint), AnbarakAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAnbarakAfrad", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AnbarakAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAnbarakAfrad", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), AnbarakAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAnbarakAfrad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAnbarakAfradResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    String endpoint = getEndpoint(call);
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), AnbarakAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAnbarakAfrad", "onFailure");
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

    public boolean insertGroup(ArrayList<AnbarakAfradModel> anbarakAfradModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (AnbarakAfradModel anbarakAfradModel : anbarakAfradModels)
            {
                ContentValues contentValues = modelToContentvalue(anbarakAfradModel);
                db.insertOrThrow(AnbarakAfradModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , AnbarakAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AnbarakAfradDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<AnbarakAfradModel> getAll()
    {
        ArrayList<AnbarakAfradModel> anbarakAfradModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AnbarakAfradModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    anbarakAfradModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AnbarakAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AnbarakAfradDAO" , "" , "getAll" , "");
        }
        return anbarakAfradModels;
    }

    public ArrayList<AnbarakAfradModel> getByccAfradForoshandeh(int ccAfrad)
    {
        ArrayList<AnbarakAfradModel> anbarakAfradModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AnbarakAfradModel.TableName(), allColumns(), AnbarakAfradModel.COLUMN_ccAfradForoshandeh() + " = " + ccAfrad, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    anbarakAfradModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AnbarakAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AnbarakAfradDAO" , "" , "getByccAfradForoshandeh" , "");
        }
        return anbarakAfradModels;
    }

    public ArrayList<AnbarakAfradModel> getByccAfradMamorPakhsh(int ccAfradMamorPakhsh)
    {
        ArrayList<AnbarakAfradModel> anbarakAfradModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AnbarakAfradModel.TableName(), allColumns(), AnbarakAfradModel.COLUMN_ccAfradMamorPakhsh() + " = " + ccAfradMamorPakhsh, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    anbarakAfradModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AnbarakAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AnbarakAfradDAO" , "" , "getByccAfradMamorPakhsh" , "");
        }
        return anbarakAfradModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AnbarakAfradModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AnbarakAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AnbarakAfradDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(AnbarakAfradModel anbarakAfradModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(AnbarakAfradModel.COLUMN_ccAfradForoshandeh() , anbarakAfradModel.getCcAfradForoshandeh());
        contentValues.put(AnbarakAfradModel.COLUMN_ccAfradMamorPakhsh() , anbarakAfradModel.getCcAfradMamorPakhsh());
        contentValues.put(AnbarakAfradModel.COLUMN_ccAfradRanandeh() , anbarakAfradModel.getCcAfradRanandeh());
        contentValues.put(AnbarakAfradModel.COLUMN_ccAfradForoshandehJaygozin() , anbarakAfradModel.getCcAfradForoshandehJaygozin());
        contentValues.put(AnbarakAfradModel.COLUMN_ccAfradMamorPakhshJaygozin() , anbarakAfradModel.getCcAfradMamorPakhshJaygozin());
        contentValues.put(AnbarakAfradModel.COLUMN_ccAfradRanandehJaygozin() , anbarakAfradModel.getCcAfradRanandehJaygozin());
        contentValues.put(AnbarakAfradModel.COLUMN_ccNoeForoshandeh() , anbarakAfradModel.getCcNoeForoshandeh());
        contentValues.put(AnbarakAfradModel.COLUMN_ccAnbarak() , anbarakAfradModel.getCcAnbarak());
        contentValues.put(AnbarakAfradModel.COLUMN_NameAnbarak() , anbarakAfradModel.getNameAnbarak());

        return contentValues;
    }


    private ArrayList<AnbarakAfradModel> cursorToModel(Cursor cursor)
    {
        ArrayList<AnbarakAfradModel> anbarakAfradModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            AnbarakAfradModel anbarakAfradModel = new AnbarakAfradModel();

            anbarakAfradModel.setCcAfradForoshandeh(cursor.getInt(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_ccAfradForoshandeh())));
            anbarakAfradModel.setCcAfradMamorPakhsh(cursor.getInt(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_ccAfradMamorPakhsh())));
            anbarakAfradModel.setCcAfradRanandeh(cursor.getInt(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_ccAfradRanandeh())));
            anbarakAfradModel.setCcAfradForoshandehJaygozin(cursor.getInt(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_ccAfradForoshandehJaygozin())));
            anbarakAfradModel.setCcAfradMamorPakhshJaygozin(cursor.getInt(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_ccAfradMamorPakhshJaygozin())));
            anbarakAfradModel.setCcAfradRanandehJaygozin(cursor.getInt(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_ccAfradRanandehJaygozin())));
            anbarakAfradModel.setCcNoeForoshandeh(cursor.getInt(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_ccNoeForoshandeh())));
            anbarakAfradModel.setCcAnbarak(cursor.getInt(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_ccAnbarak())));
            anbarakAfradModel.setNameAnbarak(cursor.getString(cursor.getColumnIndex(AnbarakAfradModel.COLUMN_NameAnbarak())));

            anbarakAfradModels.add(anbarakAfradModel);
            cursor.moveToNext();
        }
        return anbarakAfradModels;
    }



}
