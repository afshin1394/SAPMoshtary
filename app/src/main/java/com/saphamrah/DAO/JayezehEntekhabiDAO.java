package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.JayezehEntekhabiModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllvJayezehEntekhabiResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JayezehEntekhabiDAO 
{


    private DBHelper dbHelper;
    private Context context;


    public JayezehEntekhabiDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "JayezehEntekhabiDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            JayezehEntekhabiModel.COLUMN_ccJayezeh(),
            JayezehEntekhabiModel.COLUMN_ccJayezehSatr(),
            JayezehEntekhabiModel.COLUMN_ccJayezehSatrKala(),
            JayezehEntekhabiModel.COLUMN_ccKala(),
            JayezehEntekhabiModel.COLUMN_ccKalaCode(),
            JayezehEntekhabiModel.COLUMN_CodeKalaOld(),
            JayezehEntekhabiModel.COLUMN_NameKala(),
            JayezehEntekhabiModel.COLUMN_ccMarkazForosh(),
            JayezehEntekhabiModel.COLUMN_MablaghForosh(),
            JayezehEntekhabiModel.COLUMN_ExtraProp_Tedad(),
            JayezehEntekhabiModel.COLUMN_ccNoeMoshtary(),
            JayezehEntekhabiModel.COLUMN_CodeNoe(),
            JayezehEntekhabiModel.COLUMN_ccTakhfifHajmi(),
            JayezehEntekhabiModel.COLUMN_ccKalaCodeAsli()
        };
    }


    public void fetchJayezehEntekhabi(final Context context, final String activityNameForLog, final String ccMarkazForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabi", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvJayezehEntekhabiResult> call = apiServiceGet.getAllvJayezehEntekhabi(ccMarkazForosh);
            call.enqueue(new Callback<GetAllvJayezehEntekhabiResult>() {
                @Override
                public void onResponse(Call<GetAllvJayezehEntekhabiResult> call, Response<GetAllvJayezehEntekhabiResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, JayezehEntekhabiDAO.class.getSimpleName(), "", "fetchJayezehEntekhabi", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvJayezehEntekhabiResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabi", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabi", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabi", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabi", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvJayezehEntekhabiResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabi", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public void fetchJayezehEntekhabiForPakhsh(final Context context, final String activityNameForLog, final String ccMarkazForoshPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabiForPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvJayezehEntekhabiResult> call = apiServiceGet.getAllvJayezehEntekhabiForPakhsh(ccMarkazForoshPakhsh);
            call.enqueue(new Callback<GetAllvJayezehEntekhabiResult>() {
                @Override
                public void onResponse(Call<GetAllvJayezehEntekhabiResult> call, Response<GetAllvJayezehEntekhabiResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, JayezehEntekhabiDAO.class.getSimpleName(), "", "fetchJayezehEntekhabiForPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvJayezehEntekhabiResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabiForPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabiForPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabiForPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabiForPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvJayezehEntekhabiResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), JayezehEntekhabiDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehEntekhabiForPakhsh", "onFailure");
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

    public boolean insertGroup(ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (JayezehEntekhabiModel jayezehEntekhabiModel : jayezehEntekhabiModels)
            {
                ContentValues contentValues = modelToContentvalue(jayezehEntekhabiModel);
                db.insertOrThrow(JayezehEntekhabiModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , JayezehEntekhabiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehEntekhabiDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<JayezehEntekhabiModel> getAll()
    {
        ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(JayezehEntekhabiModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehEntekhabiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , JayezehEntekhabiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehEntekhabiDAO" , "" , "getAll" , "");
        }
        return jayezehEntekhabiModels;
    }


    public JayezehEntekhabiModel getKalaPromotionByccTakhfifHajmi(int ccNoeMoshtary, int ccTakhfifHajmi)
    {
        JayezehEntekhabiModel jayezehEntekhabiModel = new JayezehEntekhabiModel();
        try
        {
            String query = "SELECT * FROM JayezehEntekhabi WHERE CodeNoe = 4 AND ccNoeMoshtary=" + ccNoeMoshtary + " AND ccTakhfifHajmi=" + ccTakhfifHajmi;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehEntekhabiModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , JayezehEntekhabiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehEntekhabiDAO" , "" , "getKalaPromotionByccTakhfifHajmi" , "");
        }
        return jayezehEntekhabiModel;
    }

    public ArrayList<JayezehEntekhabiModel> getByccKalaCode(int ccKalaCode)
    {
        ArrayList<JayezehEntekhabiModel> jayezeh = new ArrayList<>();
        try
        {
            String StrSql =" SELECT * FROM JayezehEntekhabi "
                    +" WHERE ccKalaCode =" + ccKalaCode ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(StrSql, null);

            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                jayezeh = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return jayezeh;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(JayezehEntekhabiModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , JayezehEntekhabiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehEntekhabiDAO" , "" , "deleteAll" , "");
            return false;
        }
    }
    public Boolean updateMablaghForoshAll(ArrayList<KalaModel> kalaModels) {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (int i = 0; i < kalaModels.size(); i++) {
             int ccKalaCode = kalaModels.get(i).getCcKalaCode();
             double mablaghForosh = kalaModels.get(i).getMablaghForosh();


                String query = "UPDATE " + JayezehEntekhabiModel.TableName() +
                        " SET " + JayezehEntekhabiModel.COLUMN_MablaghForosh() + " = " + mablaghForosh +
                        " WHERE " + JayezehEntekhabiModel.COLUMN_ccKalaCode() + " = " + ccKalaCode;
                db.execSQL(query);

            }
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , JayezehEntekhabiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehEntekhabiDAO" , "" , "updateMablaghForosh" , "");
            return false;
        }

    }

    public boolean updateMablaghForosh(double mablaghForosh, int ccKalaCode)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query =	"UPDATE " + JayezehEntekhabiModel.TableName() +
                    " SET " + JayezehEntekhabiModel.COLUMN_MablaghForosh() + " = " + mablaghForosh +
                    " WHERE " + JayezehEntekhabiModel.COLUMN_ccKalaCode() + " = " + ccKalaCode ;
            db.execSQL(query);
//            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , JayezehEntekhabiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehEntekhabiDAO" , "" , "updateMablaghForosh" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(JayezehEntekhabiModel jayezehEntekhabiModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(JayezehEntekhabiModel.COLUMN_ccJayezeh() , jayezehEntekhabiModel.getCcJayezeh());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ccJayezehSatr() , jayezehEntekhabiModel.getCcJayezehSatr());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ccJayezehSatrKala() , jayezehEntekhabiModel.getCcJayezehSatrKala());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ccKala() , jayezehEntekhabiModel.getCcKala());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ccKalaCode() , jayezehEntekhabiModel.getCcKalaCode());
        contentValues.put(JayezehEntekhabiModel.COLUMN_CodeKalaOld() , jayezehEntekhabiModel.getCodeKalaOld());
        contentValues.put(JayezehEntekhabiModel.COLUMN_NameKala() , jayezehEntekhabiModel.getNameKala());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ccMarkazForosh() , jayezehEntekhabiModel.getCcMarkazForosh());
        contentValues.put(JayezehEntekhabiModel.COLUMN_MablaghForosh() , jayezehEntekhabiModel.getMablaghForosh());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ExtraProp_Tedad() , jayezehEntekhabiModel.getExtraProp_Tedad());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ccNoeMoshtary() , jayezehEntekhabiModel.getCcNoeMoshtary());
        contentValues.put(JayezehEntekhabiModel.COLUMN_CodeNoe() , jayezehEntekhabiModel.getCodeNoe());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ccTakhfifHajmi() , jayezehEntekhabiModel.getCcTakhfifHajmi());
        contentValues.put(JayezehEntekhabiModel.COLUMN_ccKalaCodeAsli() , jayezehEntekhabiModel.getCcKalaCodeAsli());

        return contentValues;
    }


    private ArrayList<JayezehEntekhabiModel> cursorToModel(Cursor cursor)
    {
        ArrayList<JayezehEntekhabiModel> jayezehEntekhabiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            JayezehEntekhabiModel jayezehEntekhabiModel = new JayezehEntekhabiModel();

            jayezehEntekhabiModel.setCcJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccJayezeh())));
            jayezehEntekhabiModel.setCcJayezehSatr(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccJayezehSatr())));
            jayezehEntekhabiModel.setCcJayezehSatrKala(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccJayezehSatrKala())));
            jayezehEntekhabiModel.setCcKala(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccKala())));
            jayezehEntekhabiModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccKalaCode())));
            jayezehEntekhabiModel.setCodeKalaOld(cursor.getString(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_CodeKalaOld())));
            jayezehEntekhabiModel.setNameKala(cursor.getString(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_NameKala())));
            jayezehEntekhabiModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccMarkazForosh())));
            jayezehEntekhabiModel.setMablaghForosh(cursor.getFloat(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_MablaghForosh())));
            jayezehEntekhabiModel.setExtraProp_Tedad(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ExtraProp_Tedad())));
            jayezehEntekhabiModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccNoeMoshtary())));
            jayezehEntekhabiModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_CodeNoe())));
            jayezehEntekhabiModel.setCcTakhfifHajmi(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccTakhfifHajmi())));
            jayezehEntekhabiModel.setCcKalaCodeAsli(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccKalaCodeAsli())));

            jayezehEntekhabiModels.add(jayezehEntekhabiModel);
            cursor.moveToNext();
        }
        return jayezehEntekhabiModels;
    }
    
    
}
