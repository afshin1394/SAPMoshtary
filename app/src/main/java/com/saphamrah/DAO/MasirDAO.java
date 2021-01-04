package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllMasirResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasirDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MasirDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MasirDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MasirModel.COLUMN_ccMasir(),
            MasirModel.COLUMN_ccForoshandeh(),
            MasirModel.COLUMN_NameMasir(),
            MasirModel.COLUMN_ToorVisit(),
            MasirModel.COLUMN_ExtraProp_TarikhMasir(),
            MasirModel.COLUMN_ccMasirRoozVisit()
        };
    }

    public void fetchAllMasir(final Context context, final String activityNameForLog,final String ccForoshandeh,final String ccMarkazForosh,final String azTarikh,final String taTarikh,final String codeNoe, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMasirResult> call = apiServiceGet.getAllMasir(ccForoshandeh , ccMarkazForosh , azTarikh , taTarikh , codeNoe);
            call.enqueue(new Callback<GetAllMasirResult>() {
                @Override
                public void onResponse(Call<GetAllMasirResult> call, Response<GetAllMasirResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MasirDAO.class.getSimpleName(), "", "fetchAllMasir", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMasirResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMasirResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public void fetchAllMasirFaalForoshandeh(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllMasirResult> call = apiServiceGet.getAllMasirFaalForoshandeh();
            call.enqueue(new Callback<GetAllMasirResult>() {
                @Override
                public void onResponse(Call<GetAllMasirResult> call, Response<GetAllMasirResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MasirDAO.class.getSimpleName(), "", "fetchAllMasir", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMasirResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMasirResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MasirDAO.class.getSimpleName(), activityNameForLog, "fetchAllMasir", "onFailure");
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

    public boolean insertGroup(ArrayList<MasirModel> masirModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MasirModel masirModel : masirModels)
            {
                ContentValues contentValues = modelToContentvalue(masirModel);
                db.insertOrThrow(MasirModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<MasirModel> getAll()
    {
        ArrayList<MasirModel> masirModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MasirModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    masirModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirDAO" , "" , "getAll" , "");
        }
        return masirModels;
    }

    public ArrayList<MasirModel> getByccForoshandeh(int ccForoshandeh)
    {
        ArrayList<MasirModel> masirModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + MasirModel.TableName() + " where " + MasirModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    masirModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirDAO" , "" , "getByccForoshandeh" , "");
        }
        return masirModels;
    }

    public ArrayList<MasirModel> getByccMasir(int ccMasir)
    {
        ArrayList<MasirModel> masirModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MasirModel.TableName(), allColumns(), MasirModel.COLUMN_ccMasir() + " = " + ccMasir, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    masirModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirDAO" , "" , "getAll" , "");
        }
        return masirModels;
    }

    public int getccMasirByNoeForoshandeh(int NoeForoshandeh)
    {
        String query = null;
        int ccMasir = 0;
        if(NoeForoshandeh == 1)
        {
            query = "select * from " + MasirModel.TableName() + " WHERE "  + MasirModel.COLUMN_ccMasir() + " IN (SELECT " + MasirModel.COLUMN_ccMasir() + " From " + PolygonForoshSatrModel.TableName() +") order by " + MasirModel.COLUMN_ToorVisit() + " desc Limit 1";
        }
        else if(NoeForoshandeh >= 2)
        {
            query = "select * from " + MasirModel.TableName() + " WHERE " + MasirModel.COLUMN_ccMasir() + " > 0 order by " + MasirModel.COLUMN_ToorVisit() + " desc Limit 1";
        }
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if(cursor.getCount() == 0)
                {
                    query = "SELECT * FROM " + MasirModel.TableName() + " WHERE " + MasirModel.COLUMN_ccMasir() +" > 0 order by " + MasirModel.COLUMN_ToorVisit() + " desc Limit 1";
                    cursor = db.rawQuery(query , null);
                }

                if (cursor != null)
                {
                    if (cursor.getCount() > 0)
                    {
                        ccMasir = cursorToModel(cursor).get(0).getCcMasir();
                    }
                    cursor.close();
                }
            }
            db.close();
        }
        catch (Exception exception)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirDAO" , "" , "getccMasirByNoeForoshandeh" , "");
        }
        return ccMasir;
    }

    public String getstrCcMasir(){
        String strCcMasirs = "-1";
        String strQry="select ccMasir from Masir ";
        ArrayList<Integer> Masirs=new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(strQry , null);
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    do {
                        Masirs.add(cursor.getInt(0));
                        cursor.moveToNext();
                    }while (!cursor.isAfterLast());
                    cursor.close();
                }
                db.close();
            }
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirDAO" , "" , "getccMasirha" , "");
        }
        Log.i("MasirhaaaByForoshande", "getccMasirByForoshandeh: "+Masirs);
        strCcMasirs +=","+ new PubFunc().new DAOUtil().convertIntegerArrayToString(Masirs);

        return strCcMasirs;

    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MasirModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    public boolean updateTarikhMasir(String tarikhMasir)
    {
        ContentValues values = new ContentValues();
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //change format from yyyy/mm/dd to yyyy-mm-ddT00:00:00, because only this format of string can convert to Date class
            tarikhMasir = tarikhMasir + "T00:00:00"; //convert from date to dateTime
            Date date = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(tarikhMasir.replace("/" , "-"));

            values.put(MasirModel.COLUMN_ExtraProp_TarikhMasir(),  new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(date));
            db.update(MasirModel.TableName(), values, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MasirDAO" , "" , "updateTarikhMasir" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MasirModel masirModel)
    {
        ContentValues contentValues = new ContentValues();

        if (masirModel.getCcMasir() > 0)
        {
            contentValues.put(MasirModel.COLUMN_ccMasir() , masirModel.getCcMasir());
        }
        contentValues.put(MasirModel.COLUMN_ccForoshandeh() , masirModel.getCcForoshandeh());
        contentValues.put(MasirModel.COLUMN_NameMasir() , masirModel.getNameMasir());
        contentValues.put(MasirModel.COLUMN_ToorVisit() , masirModel.getToorVisit());
        contentValues.put(MasirModel.COLUMN_ExtraProp_TarikhMasir() , "null");
        contentValues.put(MasirModel.COLUMN_ccMasirRoozVisit() , masirModel.getCcMasirRoozVisit());

        return contentValues;
    }


    private ArrayList<MasirModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MasirModel> masirModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MasirModel masirModel = new MasirModel();

            masirModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(MasirModel.COLUMN_ccMasir())));
            masirModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(MasirModel.COLUMN_ccForoshandeh())));
            masirModel.setNameMasir(cursor.getString(cursor.getColumnIndex(MasirModel.COLUMN_NameMasir())));
            masirModel.setToorVisit(cursor.getInt(cursor.getColumnIndex(MasirModel.COLUMN_ToorVisit())));
            masirModel.setTarikhMasir(cursor.getString(cursor.getColumnIndex(MasirModel.COLUMN_ExtraProp_TarikhMasir())));
            masirModel.setCcMasirRoozVisit(cursor.getInt(cursor.getColumnIndex(MasirModel.COLUMN_ccMasirRoozVisit())));

            masirModels.add(masirModel);
            cursor.moveToNext();
        }
        return masirModels;
    }





}
