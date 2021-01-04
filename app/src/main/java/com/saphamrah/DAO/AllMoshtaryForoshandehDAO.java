package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryForoshandehResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMoshtaryForoshandehDAO 
{
    
    private DBHelper dbHelper;
    private Context context;


    public AllMoshtaryForoshandehDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "AllMoshtaryForoshandehDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            AllMoshtaryForoshandehModel.COLUMN_ccMoshtary(),
            AllMoshtaryForoshandehModel.COLUMN_CodeMoshtary(),
            AllMoshtaryForoshandehModel.COLUMN_NameMoshtary(),
            AllMoshtaryForoshandehModel.COLUMN_Address(),
            AllMoshtaryForoshandehModel.COLUMN_Telephone(),
            AllMoshtaryForoshandehModel.COLUMN_ccMasir(),
            AllMoshtaryForoshandehModel.COLUMN_NameMasir(),
            AllMoshtaryForoshandehModel.COLUMN_ccForoshandeh(),
            AllMoshtaryForoshandehModel.COLUMN_Latitude(),
            AllMoshtaryForoshandehModel.COLUMN_Longitude()
        };
    }

    public void fetchAllMoshtaryforoshandeh(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AllMoshtaryForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtaryForoshandehResult> call = apiServiceGet.getAllMoshtaryForoshandeh(ccForoshandeh);
            call.enqueue(new Callback<GetAllMoshtaryForoshandehResult>() {
                @Override
                public void onResponse(Call<GetAllMoshtaryForoshandehResult> call, Response<GetAllMoshtaryForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, AllMoshtaryForoshandehDAO.class.getSimpleName(), "", "fetchAllMoshtaryforoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMoshtaryForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), AllMoshtaryForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull), endpoint), AllMoshtaryForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AllMoshtaryForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), AllMoshtaryForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtaryForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint) , AllMoshtaryForoshandehDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onFailure");
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

    public boolean insertGroup(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (AllMoshtaryForoshandehModel allMoshtaryForoshandehModel : allMoshtaryForoshandehModels)
            {
                ContentValues contentValues = modelToContentvalue(allMoshtaryForoshandehModel);
                db.insertOrThrow(AllMoshtaryForoshandehModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , AllMoshtaryForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryForoshandehDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<AllMoshtaryForoshandehModel> getAll()
    {
        ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AllMoshtaryForoshandehModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    allMoshtaryForoshandehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AllMoshtaryForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryForoshandehDAO" , "" , "getAll" , "");
        }
        return allMoshtaryForoshandehModels;
    }

    public ArrayList<MasirModel> getAllMasirsByccForoshande(int ccForoshande)
    {
        ArrayList<MasirModel> masirModels = new ArrayList<>();
        String query = "SELECT " + MasirModel.COLUMN_ccMasir() + "," + MasirModel.COLUMN_NameMasir() +
                " FROM " + AllMoshtaryForoshandehModel.TableName() +
                " WHERE " + AllMoshtaryForoshandehModel.COLUMN_ccForoshandeh() + " IN (" + ccForoshande + ") " +
                " GROUP BY " + MasirModel.COLUMN_ccMasir() + "," + MasirModel.COLUMN_NameMasir() + " ORDER BY NameMasir ";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        MasirModel masirModel = new MasirModel();
                        masirModel.setCcMasir(cursor.getInt(0));
                        masirModel.setNameMasir(cursor.getString(1));
                        masirModel.setCcForoshandeh(ccForoshande);

                        masirModels.add(masirModel);

                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AllMoshtaryForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryForoshandehDAO" , "" , "getMasirsByccForoshande" , "");
        }
        return masirModels;
    }

    public String getAllccMasirsWithComma(int ccForoshandeh)
    {
        String ccMasirs = "";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select group_concat(distinct(" + AllMoshtaryForoshandehModel.COLUMN_ccMasir() + ")) from " + AllMoshtaryForoshandehModel.TableName() + " where " +
                    AllMoshtaryForoshandehModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccMasirs = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AllMoshtaryForoshandehModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryForoshandehDAO" , "" , "getAllccMasirsWithComma" , "");
        }
        return ccMasirs;
    }

    public ArrayList<MasirModel> getMasirsByccForoshandeAndNotMasirRooz(int ccForoshande)
    {
        ArrayList<MasirModel> masirModels = new ArrayList<>();
        String query = "SELECT " + MasirModel.COLUMN_ccMasir() + "," + MasirModel.COLUMN_NameMasir() +
                " FROM " + AllMoshtaryForoshandehModel.TableName() +
                " WHERE " + AllMoshtaryForoshandehModel.COLUMN_ccForoshandeh() + " IN (" + ccForoshande + ") and ccMasir not in (select ccMasir from Masir)" +
                " GROUP BY " + MasirModel.COLUMN_ccMasir() + "," + MasirModel.COLUMN_NameMasir() + " ORDER BY NameMasir ";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        MasirModel masirModel = new MasirModel();
                        masirModel.setCcMasir(cursor.getInt(0));
                        masirModel.setNameMasir(cursor.getString(1));
                        masirModel.setCcForoshandeh(ccForoshande);

                        masirModels.add(masirModel);

                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AllMoshtaryForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryForoshandehDAO" , "" , "getMasirsByccForoshande" , "");
        }
        return masirModels;
    }

    public ArrayList<AllMoshtaryForoshandehModel> getAllByccForoshandeh(int ccForoshandeh)
    {
        ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //String query = "select * from AllMoshtaryForoshandeh where ccMasir not in (select distinct(ccMasir) from Masir) and ccForoshandeh = " + ccForoshandeh;
            String query = "select * from AllMoshtaryForoshandeh where ccForoshandeh = " + ccForoshandeh;
            //Cursor cursor = db.query(AllMoshtaryForoshandehModel.TableName(), allColumns(), AllMoshtaryForoshandehModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh, null, null, null, null);
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    allMoshtaryForoshandehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AllMoshtaryForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryForoshandehDAO" , "" , "getByccForoshandeh" , "");
        }
        return allMoshtaryForoshandehModels;
    }

    public ArrayList<AllMoshtaryForoshandehModel> getAllByccMasir(int ccForoshandeh , int ccMasir)
    {
        ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //String query = "select * from AllMoshtaryForoshandeh where ccMasir = " + ccMasir + " and ccMasir not in (select distinct(ccMasir) from Masir) and ccForoshandeh = " + ccForoshandeh;
            String query = "select * from AllMoshtaryForoshandeh where ccMasir = " + ccMasir + " and ccForoshandeh = " + ccForoshandeh;
            //Cursor cursor = db.query(AllMoshtaryForoshandehModel.TableName(), allColumns(), AllMoshtaryForoshandehModel.COLUMN_ccMasir() + " = " + ccMasir + " and " + AllMoshtaryForoshandehModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh, null, null, null, null);
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    allMoshtaryForoshandehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AllMoshtaryForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryForoshandehDAO" , "" , "getAllByccMasir" , "");
        }
        return allMoshtaryForoshandehModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AllMoshtaryForoshandehModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AllMoshtaryForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryForoshandehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_ccMoshtary() , allMoshtaryForoshandehModel.getCcMoshtary());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_CodeMoshtary() , allMoshtaryForoshandehModel.getCodeMoshtary());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_NameMoshtary() , allMoshtaryForoshandehModel.getNameMoshtary());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_Address() , allMoshtaryForoshandehModel.getAddress());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_Telephone() , allMoshtaryForoshandehModel.getTelephone());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_ccMasir() , allMoshtaryForoshandehModel.getCcMasir());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_NameMasir() , allMoshtaryForoshandehModel.getNameMasir());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_ccForoshandeh() , allMoshtaryForoshandehModel.getCcForoshandeh());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_Latitude() , allMoshtaryForoshandehModel.getLatitude());
        contentValues.put(AllMoshtaryForoshandehModel.COLUMN_Longitude() , allMoshtaryForoshandehModel.getLongitude());

        return contentValues;
    }

    private ArrayList<AllMoshtaryForoshandehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            AllMoshtaryForoshandehModel allMoshtaryForoshandehModel = new AllMoshtaryForoshandehModel();

            allMoshtaryForoshandehModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_ccMoshtary())));
            allMoshtaryForoshandehModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_CodeMoshtary())));
            allMoshtaryForoshandehModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_NameMoshtary())));
            allMoshtaryForoshandehModel.setAddress(cursor.getString(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_Address())));
            allMoshtaryForoshandehModel.setTelephone(cursor.getString(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_Telephone())));
            allMoshtaryForoshandehModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_ccMasir())));
            allMoshtaryForoshandehModel.setNameMasir(cursor.getString(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_NameMasir())));
            allMoshtaryForoshandehModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_ccForoshandeh())));
            allMoshtaryForoshandehModel.setLatitude(cursor.getInt(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_Latitude())));
            allMoshtaryForoshandehModel.setLongitude(cursor.getInt(cursor.getColumnIndex(AllMoshtaryForoshandehModel.COLUMN_Longitude())));

            allMoshtaryForoshandehModels.add(allMoshtaryForoshandehModel);
            cursor.moveToNext();
        }
        return allMoshtaryForoshandehModels;
    }
    
}
