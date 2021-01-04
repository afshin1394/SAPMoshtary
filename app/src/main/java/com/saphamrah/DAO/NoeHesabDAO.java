package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.NoeHesabModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllNoeHesabResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoeHesabDAO
{

    private DBHelper dbHelper;
    private Context context;


    public NoeHesabDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "NoeHesabDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            NoeHesabModel.COLUMN_ccNoeHesab(),
            NoeHesabModel.COLUMN_NameNoeHesab()
        };
    }

    public void fetchNoeHesab(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeHesabDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllNoeHesabResult> call = apiServiceGet.getAllNoeHesab();
            call.enqueue(new Callback<GetAllNoeHesabResult>() {
                @Override
                public void onResponse(Call<GetAllNoeHesabResult> call, Response<GetAllNoeHesabResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, NoeHesabDAO.class.getSimpleName(), "", "fetchNoeHesab", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllNoeHesabResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), NoeHesabDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), NoeHesabDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeHesabDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), NoeHesabDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllNoeHesabResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), NoeHesabDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<NoeHesabModel> noeHesabModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (NoeHesabModel noeHesabModel : noeHesabModels)
            {
                ContentValues contentValues = modelToContentvalue(noeHesabModel);
                db.insertOrThrow(NoeHesabModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , NoeHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeHesabDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<NoeHesabModel> getAll()
    {
        ArrayList<NoeHesabModel> noeHesabModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(NoeHesabModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    noeHesabModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeHesabDAO" , "" , "getAll" , "");
        }
        return noeHesabModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(NoeHesabModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , NoeHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeHesabDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(NoeHesabModel noeHesabModel)
    {
        ContentValues contentValues = new ContentValues();

        if (noeHesabModel.getCcNoeHesab() > 0)
        {
            contentValues.put(NoeHesabModel.COLUMN_ccNoeHesab() , noeHesabModel.getCcNoeHesab());
        }
        contentValues.put(NoeHesabModel.COLUMN_NameNoeHesab() , noeHesabModel.getNameNoeHesab());

        return contentValues;
    }


    private ArrayList<NoeHesabModel> cursorToModel(Cursor cursor)
    {
        ArrayList<NoeHesabModel> noeHesabModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            NoeHesabModel noeHesabModel = new NoeHesabModel();

            noeHesabModel.setCcNoeHesab(cursor.getInt(cursor.getColumnIndex(NoeHesabModel.COLUMN_ccNoeHesab())));
            noeHesabModel.setNameNoeHesab(cursor.getString(cursor.getColumnIndex(NoeHesabModel.COLUMN_NameNoeHesab())));

            noeHesabModels.add(noeHesabModel);
            cursor.moveToNext();
        }
        return noeHesabModels;
    }


}
