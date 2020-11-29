package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ModatVosolGorohModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllModatVosolGorohResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModatVosolGorohDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ModatVosolGorohDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ModatVosolGorohDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ModatVosolGorohModel.COLUMN_ccModatVosolGoroh(),
            ModatVosolGorohModel.COLUMN_ccModatVosol(),
            ModatVosolGorohModel.COLUMN_ccGoroh()
        };
    }

    public void fetchAllModatVosolGoroh(final Context context, final String activityNameForLog, final String ccGorohs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolGoroh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllModatVosolGorohResult> call = apiService.getAllModatVosolGoroh(ccGorohs);
            call.enqueue(new Callback<GetAllModatVosolGorohResult>() {
                @Override
                public void onResponse(Call<GetAllModatVosolGorohResult> call, Response<GetAllModatVosolGorohResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ModatVosolGorohDAO.class.getSimpleName(), "", "fetchAllModatVosolGoroh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllModatVosolGorohResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ModatVosolGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolGoroh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), ModatVosolGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolGoroh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = "message : " + response.message() + "\n" + "code : " + response.code();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolGoroh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ModatVosolGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolGoroh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }
                @Override
                public void onFailure(Call<GetAllModatVosolGorohResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), ModatVosolGorohDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolGoroh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<ModatVosolGorohModel> modatVosolGorohModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ModatVosolGorohModel modatVosolGorohModel : modatVosolGorohModels)
            {
                ContentValues contentValues = modelToContentvalue(modatVosolGorohModel);
                db.insertOrThrow(ModatVosolGorohModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ModatVosolGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolGorohDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<ModatVosolGorohModel> getAll()
    {
        ArrayList<ModatVosolGorohModel> modatVosolGorohModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ModatVosolGorohModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    modatVosolGorohModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ModatVosolGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolGorohDAO" , "" , "getAll" , "");
        }
        return modatVosolGorohModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ModatVosolGorohModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ModatVosolGorohModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolGorohDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ModatVosolGorohModel modatVosolGorohModel)
    {
        ContentValues contentValues = new ContentValues();

        if (modatVosolGorohModel.getCcModatVosolGoroh() > 0)
        {
            contentValues.put(ModatVosolGorohModel.COLUMN_ccModatVosolGoroh() , modatVosolGorohModel.getCcModatVosolGoroh());
        }
        contentValues.put(ModatVosolGorohModel.COLUMN_ccModatVosol() , modatVosolGorohModel.getCcModatVosol());
        contentValues.put(ModatVosolGorohModel.COLUMN_ccGoroh() , modatVosolGorohModel.getCcGoroh());

        return contentValues;
    }


    private ArrayList<ModatVosolGorohModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ModatVosolGorohModel> modatVosolGorohModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ModatVosolGorohModel modatVosolGorohModel = new ModatVosolGorohModel();

            modatVosolGorohModel.setCcModatVosolGoroh(cursor.getInt(cursor.getColumnIndex(ModatVosolGorohModel.COLUMN_ccModatVosolGoroh())));
            modatVosolGorohModel.setCcModatVosol(cursor.getInt(cursor.getColumnIndex(ModatVosolGorohModel.COLUMN_ccModatVosol())));
            modatVosolGorohModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(ModatVosolGorohModel.COLUMN_ccGoroh())));

            modatVosolGorohModels.add(modatVosolGorohModel);
            cursor.moveToNext();
        }
        return modatVosolGorohModels;
    }

}
