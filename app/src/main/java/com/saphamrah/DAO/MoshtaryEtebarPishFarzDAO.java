package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryEtebarPishFarzModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetMoshtaryEtebarPishfarzResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryEtebarPishFarzDAO
{

    private DBHelper dbHelper;
    private Context context;
    private final String CLASS_NAME = "MoshtaryEtebarPishFarzDAO";


    public MoshtaryEtebarPishFarzDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryEtebarPishFarzModel.COLUMN_ccMoshtaryEtebarPishFarz(),
            MoshtaryEtebarPishFarzModel.COLUMN_ccNoeMoshtary(),
            MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarRiali(),
            MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarAsnad(),
            MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarTedadi(),
            MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarModat(),
            MoshtaryEtebarPishFarzModel.COLUMN_RialTazamin(),
            MoshtaryEtebarPishFarzModel.COLUMN_RialAsnadShakhsi(),
            MoshtaryEtebarPishFarzModel.COLUMN_TedadAsnadShakhsi(),
            MoshtaryEtebarPishFarzModel.COLUMN_ModatAsnadShakhsi(),
            MoshtaryEtebarPishFarzModel.COLUMN_RialAsnadMoshtary(),
            MoshtaryEtebarPishFarzModel.COLUMN_TedadAsnadMoshtary(),
            MoshtaryEtebarPishFarzModel.COLUMN_ModatAsnadMoshtary(),
            MoshtaryEtebarPishFarzModel.COLUMN_RialMoavagh(),
            MoshtaryEtebarPishFarzModel.COLUMN_TedadMoavagh(),
            MoshtaryEtebarPishFarzModel.COLUMN_ModatMoavagh(),
            MoshtaryEtebarPishFarzModel.COLUMN_RialBargashty(),
            MoshtaryEtebarPishFarzModel.COLUMN_TedadBargashty(),
            MoshtaryEtebarPishFarzModel.COLUMN_ModatBargashty(),
            MoshtaryEtebarPishFarzModel.COLUMN_ModatVosol()
        };
    }


    public void fetchMoshtaryEtebarPishFarz(final Context context, final String activityNameForLog, String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMoshtaryEtebarPishFarz", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetMoshtaryEtebarPishfarzResult> call = apiService.getMoshtaryEtebarPishfarz(ccForoshandeh);
            call.enqueue(new Callback<GetMoshtaryEtebarPishfarzResult>() {
                @Override
                public void onResponse(Call<GetMoshtaryEtebarPishfarzResult> call, Response<GetMoshtaryEtebarPishfarzResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchMoshtaryEtebarPishFarz", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMoshtaryEtebarPishfarzResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchMoshtaryEtebarPishFarz", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), CLASS_NAME, activityNameForLog, "fetchMoshtaryEtebarPishFarz", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = "message : " + response.message() + "\n" + "code : " + response.code();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMoshtaryEtebarPishFarz", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchMoshtaryEtebarPishFarz", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMoshtaryEtebarPishfarzResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), CLASS_NAME, activityNameForLog, "fetchMoshtaryEtebarPishFarz", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<MoshtaryEtebarPishFarzModel> moshtaryEtebarPishFarzModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryEtebarPishFarzModel moshtaryEtebarModel : moshtaryEtebarPishFarzModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryEtebarModel);
                db.insertOrThrow(MoshtaryEtebarPishFarzModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryEtebarPishFarzModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MoshtaryEtebarPishFarzModel> getAll()
    {
        ArrayList<MoshtaryEtebarPishFarzModel> moshtaryEtebarPishFarzModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryEtebarPishFarzModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryEtebarPishFarzModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryEtebarPishFarzModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return moshtaryEtebarPishFarzModels;
    }


    public MoshtaryEtebarPishFarzModel getByccNoeMoshtary(String ccNoeMoshtary)
    {
        MoshtaryEtebarPishFarzModel moshtaryEtebarPishFarzModel = new MoshtaryEtebarPishFarzModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryEtebarPishFarzModel.TableName(), allColumns(), MoshtaryEtebarPishFarzModel.COLUMN_ccNoeMoshtary() + " = " + ccNoeMoshtary, null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryEtebarPishFarzModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryEtebarPishFarzModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getByccNoeMoshtary" , "");
        }
        return moshtaryEtebarPishFarzModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryEtebarPishFarzModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryEtebarPishFarzModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }


    private static ContentValues modelToContentvalue(MoshtaryEtebarPishFarzModel moshtaryEtebarPishFarzModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_ccMoshtaryEtebarPishFarz() , moshtaryEtebarPishFarzModel.getCcMoshtaryEtebarPishFarz());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_ccNoeMoshtary() , moshtaryEtebarPishFarzModel.getCcNoeMoshtary());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarRiali() , moshtaryEtebarPishFarzModel.getSaghfEtebarRiali());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarAsnad() , moshtaryEtebarPishFarzModel.getSaghfEtebarAsnad());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarTedadi() , moshtaryEtebarPishFarzModel.getSaghfEtebarTedadi());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarModat() , moshtaryEtebarPishFarzModel.getSaghfEtebarModat());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_RialTazamin() , moshtaryEtebarPishFarzModel.getRialTazamin());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_RialAsnadShakhsi() , moshtaryEtebarPishFarzModel.getRialAsnadShakhsi());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_TedadAsnadShakhsi() , moshtaryEtebarPishFarzModel.getTedadAsnadShakhsi());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_ModatAsnadShakhsi() , moshtaryEtebarPishFarzModel.getModatAsnadShakhsi());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_RialAsnadMoshtary() , moshtaryEtebarPishFarzModel.getRialAsnadMoshtary());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_TedadAsnadMoshtary() , moshtaryEtebarPishFarzModel.getTedadAsnadMoshtary());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_ModatAsnadMoshtary() , moshtaryEtebarPishFarzModel.getModatAsnadMoshtary());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_RialMoavagh() , moshtaryEtebarPishFarzModel.getRialMoavagh());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_TedadMoavagh() , moshtaryEtebarPishFarzModel.getTedadMoavagh());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_ModatMoavagh() , moshtaryEtebarPishFarzModel.getModatMoavagh());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_RialBargashty() , moshtaryEtebarPishFarzModel.getRialBargashty());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_TedadBargashty() , moshtaryEtebarPishFarzModel.getTedadBargashty());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_ModatBargashty() , moshtaryEtebarPishFarzModel.getModatBargashty());
        contentValues.put(MoshtaryEtebarPishFarzModel.COLUMN_ModatVosol() , moshtaryEtebarPishFarzModel.getModatVosol());

        return contentValues;
    }


    private ArrayList<MoshtaryEtebarPishFarzModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryEtebarPishFarzModel> moshtaryEtebarPishFarzModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryEtebarPishFarzModel moshtaryEtebarPishFarzModel = new MoshtaryEtebarPishFarzModel();

            moshtaryEtebarPishFarzModel.setCcMoshtaryEtebarPishFarz(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_ccMoshtaryEtebarPishFarz())));
            moshtaryEtebarPishFarzModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_ccNoeMoshtary())));
            moshtaryEtebarPishFarzModel.setSaghfEtebarRiali(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarRiali())));
            moshtaryEtebarPishFarzModel.setSaghfEtebarAsnad(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarAsnad())));
            moshtaryEtebarPishFarzModel.setSaghfEtebarTedadi(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarTedadi())));
            moshtaryEtebarPishFarzModel.setSaghfEtebarModat(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_SaghfEtebarModat())));
            moshtaryEtebarPishFarzModel.setRialTazamin(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_RialTazamin())));
            moshtaryEtebarPishFarzModel.setRialAsnadShakhsi(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_RialAsnadShakhsi())));
            moshtaryEtebarPishFarzModel.setTedadAsnadShakhsi(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_TedadAsnadShakhsi())));
            moshtaryEtebarPishFarzModel.setModatAsnadShakhsi(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_ModatAsnadShakhsi())));
            moshtaryEtebarPishFarzModel.setRialAsnadMoshtary(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_RialAsnadMoshtary())));
            moshtaryEtebarPishFarzModel.setTedadAsnadMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_TedadAsnadMoshtary())));
            moshtaryEtebarPishFarzModel.setModatAsnadMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_ModatAsnadMoshtary())));
            moshtaryEtebarPishFarzModel.setRialMoavagh(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_RialMoavagh())));
            moshtaryEtebarPishFarzModel.setTedadMoavagh(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_TedadMoavagh())));
            moshtaryEtebarPishFarzModel.setModatMoavagh(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_ModatMoavagh())));
            moshtaryEtebarPishFarzModel.setRialBargashty(cursor.getLong(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_RialBargashty())));
            moshtaryEtebarPishFarzModel.setTedadBargashty(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_TedadBargashty())));
            moshtaryEtebarPishFarzModel.setModatBargashty(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_ModatBargashty())));
            moshtaryEtebarPishFarzModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(MoshtaryEtebarPishFarzModel.COLUMN_ModatVosol())));

            moshtaryEtebarPishFarzModels.add(moshtaryEtebarPishFarzModel);
            cursor.moveToNext();
        }
        return moshtaryEtebarPishFarzModels;
    }

}
