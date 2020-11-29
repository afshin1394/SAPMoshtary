package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllvMoshtaryEtebarSazmanForoshResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryEtebarSazmanForoshDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryEtebarSazmanForoshDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryEtebarSazmanForoshDAO.class.getSimpleName() , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryEtebarSazmanForoshModel.COLUMN_ccMoshtary(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarRiali(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarAsnad(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarTedadi(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarModat(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialAsnadShakhsi(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadAsnadShakhsi(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatAsnadShakhsi(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialAsnadMoshtary(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadAsnadMoshtary(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatAsnadMoshtary(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialMoavagh(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadMoavagh(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatMoavagh(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialBargashty(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadBargashty(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatBargashty(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_RialAsnad(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_TedadAsnad(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_ModatAsnad(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_RialMoavagh(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_TedadMoavagh(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_ModatMoavagh(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_RialBargashty(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_TedadBargashty(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_ModatBargashty(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_ModatVosol(),
            MoshtaryEtebarSazmanForoshModel.COLUMN_ccSazmanForosh()
        };
    }


    public void fetchAllvMoshtaryEtebarSazmanForosh(final Context context, final String activityNameForLog,final String ccMoshtarys, final String ccSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvMoshtaryEtebarSazmanForoshResult> call = apiService.getAllvMoshtaryEtebarSazmanForosh(ccMoshtarys , ccSazmanForosh);
            call.enqueue(new Callback<GetAllvMoshtaryEtebarSazmanForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvMoshtaryEtebarSazmanForoshResult> call, Response<GetAllvMoshtaryEtebarSazmanForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName(), "", "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvMoshtaryEtebarSazmanForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvMoshtaryEtebarSazmanForoshResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public void fetchAllvMoshtaryEtebarSazmanForoshForPakhsh(final Context context, final String activityNameForLog,final String ccMoshtarys, final String ccSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvMoshtaryEtebarSazmanForoshResult> call = apiService.getAllvMoshtaryEtebarSazmanForoshForPakhsh(ccMoshtarys , ccSazmanForosh);
            call.enqueue(new Callback<GetAllvMoshtaryEtebarSazmanForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvMoshtaryEtebarSazmanForoshResult> call, Response<GetAllvMoshtaryEtebarSazmanForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName(), "", "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvMoshtaryEtebarSazmanForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvMoshtaryEtebarSazmanForoshResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), "MoshtaryEtebarSazmanForoshDAO", activityNameForLog, "fetchAllvMoshtaryEtebarSazmanForosh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public boolean insertGroup(ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel : moshtaryEtebarSazmanForoshModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryEtebarSazmanForoshModel);
                db.insertOrThrow(MoshtaryEtebarSazmanForoshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryEtebarSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName() , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel)
    {
        ContentValues contentValues = modelToContentvalue(moshtaryEtebarSazmanForoshModel);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long id = db.insertOrThrow(MoshtaryEtebarSazmanForoshModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , MoshtaryEtebarSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName() , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<MoshtaryEtebarSazmanForoshModel> getAll()
    {
        ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryEtebarSazmanForoshModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryEtebarSazmanForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryEtebarSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName() , "" , "getAll" , "");
        }
        return moshtaryEtebarSazmanForoshModels;
    }

    public MoshtaryEtebarSazmanForoshModel getByccMoshtary(int ccMoshtary)
    {
        MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModels = new MoshtaryEtebarSazmanForoshModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryEtebarSazmanForoshModel.TableName(), allColumns(), MoshtaryEtebarSazmanForoshModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryEtebarSazmanForoshModels = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryEtebarSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName() , "" , "getAll" , "");
        }
        Log.d("etebar" , "ccMoshtary : " + ccMoshtary + "moshtaryEtebarSazmanForoshModels:" + moshtaryEtebarSazmanForoshModels.toString());
        return moshtaryEtebarSazmanForoshModels;
    }
    public MoshtaryEtebarSazmanForoshModel getByccMoshtaryccSazmanForosh(int ccMoshtary, int ccSazmanForosh)
    {
        MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModels = new MoshtaryEtebarSazmanForoshModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor;
            if(ccSazmanForosh!=-1)
                cursor = db.query(MoshtaryEtebarSazmanForoshModel.TableName(), allColumns(), MoshtaryEtebarSazmanForoshModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " AND " + MoshtaryEtebarSazmanForoshModel.COLUMN_ccSazmanForosh() + " = " + ccSazmanForosh, null, null, null, null);
            else
                cursor = db.query(MoshtaryEtebarSazmanForoshModel.TableName(), allColumns(), MoshtaryEtebarSazmanForoshModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryEtebarSazmanForoshModels = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryEtebarSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName() , "" , "getAll" , "");
        }
        Log.d("etebar" , "ccMoshtary : " + ccMoshtary + "moshtaryEtebarSazmanForoshModels:" + moshtaryEtebarSazmanForoshModels.toString());
        return moshtaryEtebarSazmanForoshModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryEtebarSazmanForoshModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryEtebarSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName() , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryEtebarSazmanForoshModel.TableName() , MoshtaryEtebarSazmanForoshModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryEtebarSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryEtebarSazmanForoshDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }


    public boolean updateMoshtaryJadid(int newccMoshtary , int oldccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MoshtaryEtebarSazmanForoshModel.COLUMN_ccMoshtary(), newccMoshtary);
            db.update(MoshtaryEtebarSazmanForoshModel.TableName(), values, "ccMoshtary= ?", new String[]{String.valueOf(oldccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryEtebarSazmanForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryEtebarSazmanForoshDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_ccMoshtary() , moshtaryEtebarSazmanForoshModel.getCcMoshtary());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarRiali() , moshtaryEtebarSazmanForoshModel.getSaghfEtebarRiali());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarAsnad() , moshtaryEtebarSazmanForoshModel.getSaghfEtebarAsnad());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarTedadi() , moshtaryEtebarSazmanForoshModel.getSaghfEtebarTedadi());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarModat() , moshtaryEtebarSazmanForoshModel.getSaghfEtebarModat());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialAsnadShakhsi() , moshtaryEtebarSazmanForoshModel.getEtebarRialAsnadShakhsi());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadAsnadShakhsi() , moshtaryEtebarSazmanForoshModel.getEtebarTedadAsnadShakhsi());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatAsnadShakhsi() , moshtaryEtebarSazmanForoshModel.getEtebarModatAsnadShakhsi());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialAsnadMoshtary() , moshtaryEtebarSazmanForoshModel.getEtebarRialAsnadMoshtary());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadAsnadMoshtary() , moshtaryEtebarSazmanForoshModel.getEtebarTedadAsnadMoshtary());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatAsnadMoshtary() , moshtaryEtebarSazmanForoshModel.getEtebarModatAsnadMoshtary());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialMoavagh() , moshtaryEtebarSazmanForoshModel.getEtebarRialMoavagh());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadMoavagh() , moshtaryEtebarSazmanForoshModel.getEtebarTedadMoavagh());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatMoavagh() , moshtaryEtebarSazmanForoshModel.getEtebarModatMoavagh());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialBargashty() , moshtaryEtebarSazmanForoshModel.getEtebarRialBargashty());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadBargashty() , moshtaryEtebarSazmanForoshModel.getEtebarTedadBargashty());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatBargashty() , moshtaryEtebarSazmanForoshModel.getEtebarModatBargashty());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_RialAsnad() , moshtaryEtebarSazmanForoshModel.getRialAsnad());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_TedadAsnad() , moshtaryEtebarSazmanForoshModel.getTedadAsnad());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_ModatAsnad() , moshtaryEtebarSazmanForoshModel.getModatAsnad());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_RialMoavagh() , moshtaryEtebarSazmanForoshModel.getRialMoavagh());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_TedadMoavagh() , moshtaryEtebarSazmanForoshModel.getTedadMoavagh());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_ModatMoavagh() , moshtaryEtebarSazmanForoshModel.getModatMoavagh());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_RialBargashty() , moshtaryEtebarSazmanForoshModel.getRialBargashty());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_TedadBargashty() , moshtaryEtebarSazmanForoshModel.getTedadBargashty());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_ModatBargashty() , moshtaryEtebarSazmanForoshModel.getModatBargashty());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_ModatVosol() , moshtaryEtebarSazmanForoshModel.getModatVosol());
        contentValues.put(MoshtaryEtebarSazmanForoshModel.COLUMN_ccSazmanForosh() , moshtaryEtebarSazmanForoshModel.getCcSazmanForosh());

        return contentValues;
    }


    private ArrayList<MoshtaryEtebarSazmanForoshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryEtebarSazmanForoshModel> moshtaryEtebarSazmanForoshModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = new MoshtaryEtebarSazmanForoshModel();

            moshtaryEtebarSazmanForoshModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_ccMoshtary())));
            moshtaryEtebarSazmanForoshModel.setSaghfEtebarRiali(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarRiali())));
            moshtaryEtebarSazmanForoshModel.setSaghfEtebarAsnad(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarAsnad())));
            moshtaryEtebarSazmanForoshModel.setSaghfEtebarTedadi(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarTedadi())));
            moshtaryEtebarSazmanForoshModel.setSaghfEtebarModat(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_SaghfEtebarModat())));
            moshtaryEtebarSazmanForoshModel.setEtebarRialAsnadShakhsi(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialAsnadShakhsi())));
            moshtaryEtebarSazmanForoshModel.setEtebarTedadAsnadShakhsi(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadAsnadShakhsi())));
            moshtaryEtebarSazmanForoshModel.setEtebarModatAsnadShakhsi(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatAsnadShakhsi())));
            moshtaryEtebarSazmanForoshModel.setEtebarRialAsnadMoshtary(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialAsnadMoshtary())));
            moshtaryEtebarSazmanForoshModel.setEtebarTedadAsnadMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadAsnadMoshtary())));
            moshtaryEtebarSazmanForoshModel.setEtebarModatAsnadMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatAsnadMoshtary())));
            moshtaryEtebarSazmanForoshModel.setEtebarRialMoavagh(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialMoavagh())));
            moshtaryEtebarSazmanForoshModel.setEtebarTedadMoavagh(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadMoavagh())));
            moshtaryEtebarSazmanForoshModel.setEtebarModatMoavagh(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatMoavagh())));
            moshtaryEtebarSazmanForoshModel.setEtebarRialBargashty(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarRialBargashty())));
            moshtaryEtebarSazmanForoshModel.setEtebarTedadBargashty(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarTedadBargashty())));
            moshtaryEtebarSazmanForoshModel.setEtebarModatBargashty(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_EtebarModatBargashty())));
            moshtaryEtebarSazmanForoshModel.setRialAsnad(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_RialAsnad())));
            moshtaryEtebarSazmanForoshModel.setTedadAsnad(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_TedadAsnad())));
            moshtaryEtebarSazmanForoshModel.setModatAsnad(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_ModatAsnad())));
            moshtaryEtebarSazmanForoshModel.setRialMoavagh(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_RialMoavagh())));
            moshtaryEtebarSazmanForoshModel.setTedadMoavagh(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_TedadMoavagh())));
            moshtaryEtebarSazmanForoshModel.setModatMoavagh(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_ModatMoavagh())));
            moshtaryEtebarSazmanForoshModel.setRialBargashty(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_RialBargashty())));
            moshtaryEtebarSazmanForoshModel.setTedadBargashty(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_TedadBargashty())));
            moshtaryEtebarSazmanForoshModel.setModatBargashty(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_ModatBargashty())));
            moshtaryEtebarSazmanForoshModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_ModatVosol())));
            moshtaryEtebarSazmanForoshModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarSazmanForoshModel.COLUMN_ccSazmanForosh())));

            moshtaryEtebarSazmanForoshModels.add(moshtaryEtebarSazmanForoshModel);
            cursor.moveToNext();
        }
        return moshtaryEtebarSazmanForoshModels;
    }


}
