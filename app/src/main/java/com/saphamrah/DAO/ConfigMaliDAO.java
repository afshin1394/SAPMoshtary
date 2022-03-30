package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.BrandModel;
import com.saphamrah.Model.CompanyModel;
import com.saphamrah.Model.ConfigMaliModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.ConfigMaliResult;
import com.saphamrah.WebService.ServiceResponse.GetAllBrandResult;
import com.saphamrah.WebService.ServiceResponse.GetListBargashtyForoshandehResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigMaliDAO {

    private DBHelper dbHelper;
    private Context context;



    public ConfigMaliDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ConfigMaliDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
                {
                        ConfigMaliModel.COLUMN_ccConfigMali(),
                        ConfigMaliModel.COLUMN_ccMarkazSazmanForosh(),
                        ConfigMaliModel.COLUMN_DarsadMojozeTakhirCheckMoshtary(),
                        ConfigMaliModel.COLUMN_DarsadMojozeTajilCheckMoshtary(),
                        ConfigMaliModel.COLUMN_TedadRoozMojozeTarikhCheckMoshtary()
                };
    }

    public void fetchConfigMali(final Context context, final String activityNameForLog,final String ccMarkazforosh,final String ccSazmanforosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ConfigMaliDAO.class.getSimpleName(), activityNameForLog, "fetchConfigMali", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<ConfigMaliResult> call = apiServiceGet.getConfigMali(ccMarkazforosh,ccSazmanforosh);
            call.enqueue(new Callback<ConfigMaliResult>()
            {
                @Override
                public void onResponse(Call<ConfigMaliResult> call, Response<ConfigMaliResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ConfigMaliDAO.class.getSimpleName(), "", "fetchConfigMali", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            ConfigMaliResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ConfigMaliDAO.class.getSimpleName(), activityNameForLog, "fetchConfigMali", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), ConfigMaliDAO.class.getSimpleName(), activityNameForLog, "fetchConfigMali", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ConfigMaliDAO.class.getSimpleName(), activityNameForLog, "fetchConfigMali", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ConfigMaliDAO.class.getSimpleName(), activityNameForLog, "fetchConfigMali", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<ConfigMaliResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ConfigMaliDAO.class.getSimpleName(), activityNameForLog, "fetchConfigMali", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public boolean insertGroup(ArrayList<ConfigMaliModel> configMaliModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ConfigMaliModel configMaliModel : configMaliModels)
            {
                ContentValues contentValues = modelToContentvalue(configMaliModel);
                db.insertOrThrow(ConfigMaliModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            String message = context.getResources().getString(R.string.errorGroupInsert , ConfigMaliModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ConfigMaliDAO" , "" , "insertGroup" , "");
            return false;
        }
    }



    public ArrayList<ConfigMaliModel> getAll()
    {
        ArrayList<ConfigMaliModel> configMaliModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(CompanyModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    configMaliModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ConfigMaliModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ConfigMaliDAO" , "" , "getAll" , "");
        }
        return configMaliModels;
    }



    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ConfigMaliModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ConfigMaliModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ConfigMaliDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ConfigMaliModel configMaliModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ConfigMaliModel.COLUMN_ccConfigMali() , configMaliModel.getCcConfigMali());
        contentValues.put(ConfigMaliModel.COLUMN_ccMarkazSazmanForosh() , configMaliModel.getCcMarkazSazmanForosh());
        contentValues.put(ConfigMaliModel.COLUMN_DarsadMojozeTajilCheckMoshtary() , configMaliModel.getDarsadMojozeTajilCheckMoshtary());
        contentValues.put(ConfigMaliModel.COLUMN_TedadRoozMojozeTarikhCheckMoshtary() , configMaliModel.getTedadRoozMojozeTarikhCheckMoshtary());
        contentValues.put(ConfigMaliModel.COLUMN_DarsadMojozeTakhirCheckMoshtary() , configMaliModel.getDarsadMojozeTakhirCheckMoshtary());

        return contentValues;
    }


    private ArrayList<ConfigMaliModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ConfigMaliModel> configMaliModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ConfigMaliModel configMaliModel = new ConfigMaliModel();

            configMaliModel.setCcConfigMali(cursor.getInt(cursor.getColumnIndex(ConfigMaliModel.COLUMN_ccConfigMali())));
            configMaliModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(ConfigMaliModel.COLUMN_ccMarkazSazmanForosh())));
            configMaliModel.setDarsadMojozeTakhirCheckMoshtary(cursor.getInt(cursor.getColumnIndex(ConfigMaliModel.COLUMN_DarsadMojozeTakhirCheckMoshtary())));
            configMaliModel.setTedadRoozMojozeTarikhCheckMoshtary(cursor.getInt(cursor.getColumnIndex(ConfigMaliModel.COLUMN_TedadRoozMojozeTarikhCheckMoshtary())));
            configMaliModel.setDarsadMojozeTajilCheckMoshtary(cursor.getInt(cursor.getColumnIndex(ConfigMaliModel.COLUMN_DarsadMojozeTajilCheckMoshtary())));

            configMaliModels.add(configMaliModel);
            cursor.moveToNext();
        }
        return configMaliModels;
    }

}
