package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.EtebarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.CollectionUtils;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetEtebarResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EtebarDAO
{

    private DBHelper dbHelper;
    private Context context;


    public EtebarDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "EtebarDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            EtebarModel.COLUMN_ccForoshandeh(),
            EtebarModel.COLUMN_RialBargashty(),
            EtebarModel.COLUMN_TedadBargashty(),
            EtebarModel.COLUMN_ModatBargashty(),
            EtebarModel.COLUMN_flag_SabtNaghd()
        };
    }


    public void fetchEtebar(final Context context, final String activityNameForLog, final String ccForoshandeh, final String ccAnbarak, String flagUpdateEtebar, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarDAO", activityNameForLog, "fetchEtebar", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetEtebarResult> call = apiServiceGet.getEtebar(ccForoshandeh , ccAnbarak, flagUpdateEtebar);
            call.enqueue(new Callback<GetEtebarResult>() {
                @Override
                public void onResponse(Call<GetEtebarResult> call, Response<GetEtebarResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "EtebarDAO", "", "fetchEtebar", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetEtebarResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "EtebarDAO", activityNameForLog, "fetchEtebar", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), "EtebarDAO", activityNameForLog, "fetchEtebar", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarDAO", activityNameForLog, "fetchEtebar", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "EtebarDAO", activityNameForLog, "fetchEtebar", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetEtebarResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), "EtebarDAO", activityNameForLog, "fetchEtebar", "onFailure");
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

    public boolean insertGroup(ArrayList<EtebarModel> etebarModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (EtebarModel etebarModel : etebarModels)
            {
                ContentValues contentValues = modelToContentvalue(etebarModel);
                db.insertOrThrow(EtebarModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , EtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarModel" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<EtebarModel> getAll()
    {
        ArrayList<EtebarModel> etebarModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(EtebarModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    etebarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , EtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarModel" , "" , "getAll" , "");
        }
        return etebarModels;
    }

    public EtebarModel getByccForoshandeh(int ccForoshandeh)
    {
        EtebarModel etebarModels = new EtebarModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(EtebarModel.TableName(), allColumns(), EtebarModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    etebarModels = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , EtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarDAO" , "" , "getByccForoshandeh" , "");
        }
        return etebarModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(EtebarModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , EtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccForoshanhde(int ccForoshandeh)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(EtebarModel.TableName(), EtebarModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , EtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarDAO" , "" , "deleteByccForoshanhde" , "");
            return false;
        }
    }


    public ArrayList<String> deleteByccForoshanhdeString(String ccForoshandeh)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(EtebarModel.TableName(), EtebarModel.COLUMN_ccForoshandeh() + " in (" + ccForoshandeh + ")", null);
            db.close();

            return new CollectionUtils().convertStringToStringArray(ccForoshandeh);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , EtebarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "EtebarDAO" , "" , "deleteByccForoshanhde" , "");
            return null;
        }
    }

    private static ContentValues modelToContentvalue(EtebarModel etebarModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(EtebarModel.COLUMN_ccForoshandeh() , etebarModel.getCcForoshandeh());
        contentValues.put(EtebarModel.COLUMN_RialBargashty() , etebarModel.getRialBargashty());
        contentValues.put(EtebarModel.COLUMN_TedadBargashty() , etebarModel.getTedadBargashty());
        contentValues.put(EtebarModel.COLUMN_ModatBargashty() , etebarModel.getModatBargashty());
        contentValues.put(EtebarModel.COLUMN_flag_SabtNaghd() , etebarModel.getFlagSabtNaghd());

        return contentValues;
    }


    private ArrayList<EtebarModel> cursorToModel(Cursor cursor)
    {
        ArrayList<EtebarModel> etebarModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            EtebarModel etebarModel = new EtebarModel();

            etebarModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(EtebarModel.COLUMN_ccForoshandeh())));
            etebarModel.setRialBargashty(cursor.getLong(cursor.getColumnIndex(EtebarModel.COLUMN_RialBargashty())));
            etebarModel.setTedadBargashty(cursor.getInt(cursor.getColumnIndex(EtebarModel.COLUMN_TedadBargashty())));
            etebarModel.setModatBargashty(cursor.getInt(cursor.getColumnIndex(EtebarModel.COLUMN_ModatBargashty())));
            etebarModel.setFlagSabtNaghd(cursor.getInt(cursor.getColumnIndex(EtebarModel.COLUMN_flag_SabtNaghd())));

            etebarModels.add(etebarModel);
            cursor.moveToNext();
        }
        return etebarModels;
    }


}
