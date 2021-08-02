package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.AllMoshtaryPishdaryaftModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryForoshandehResult;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryPishdaryaftResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMoshtaryPishdaryaftDAO
{

    private DBHelper dbHelper;
    private Context context;


    public AllMoshtaryPishdaryaftDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "AllMoshtaryPishdaryaftDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            AllMoshtaryPishdaryaftModel.COLUMN_ccMoshtary(),
            AllMoshtaryPishdaryaftModel.COLUMN_CodeMoshtary(),
            AllMoshtaryPishdaryaftModel.COLUMN_NameMoshtary(),
            AllMoshtaryPishdaryaftModel.COLUMN_Address(),
            AllMoshtaryPishdaryaftModel.COLUMN_Telephone(),
            AllMoshtaryPishdaryaftModel.COLUMN_ccMasir(),
            AllMoshtaryPishdaryaftModel.COLUMN_NameMasir(),
            AllMoshtaryPishdaryaftModel.COLUMN_ccForoshandeh(),
            AllMoshtaryPishdaryaftModel.COLUMN_Latitude(),
            AllMoshtaryPishdaryaftModel.COLUMN_Longitude(),
            AllMoshtaryPishdaryaftModel.COLUMN_Darajeh(),
            AllMoshtaryPishdaryaftModel.COLUMN_ccNoeMoshtary()

        };
    }

    public void fetchAllMoshtaryforoshandeh(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AllMoshtaryPishdaryaftDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtaryPishdaryaftResult> call = apiServiceGet.getAllMoshtaryPishDaryaft(ccForoshandeh);
            call.enqueue(new Callback<GetAllMoshtaryPishdaryaftResult>() {
                @Override
                public void onResponse(Call<GetAllMoshtaryPishdaryaftResult> call, Response<GetAllMoshtaryPishdaryaftResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, AllMoshtaryPishdaryaftDAO.class.getSimpleName(), "", "fetchAllMoshtaryforoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMoshtaryPishdaryaftResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), AllMoshtaryPishdaryaftDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull), endpoint), AllMoshtaryPishdaryaftDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AllMoshtaryPishdaryaftDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), AllMoshtaryPishdaryaftDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtaryPishdaryaftResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint) , AllMoshtaryPishdaryaftDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryforoshandeh", "onFailure");
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

    public boolean insertGroup(ArrayList<AllMoshtaryPishdaryaftModel> AllMoshtaryPishdaryaftModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (AllMoshtaryPishdaryaftModel AllMoshtaryPishdaryaftModel : AllMoshtaryPishdaryaftModels)
            {
                ContentValues contentValues = modelToContentvalue(AllMoshtaryPishdaryaftModel);
                db.insertOrThrow(AllMoshtaryPishdaryaftModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , AllMoshtaryPishdaryaftModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryPishdaryaftDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<AllMoshtaryPishdaryaftModel> getAll()
    {
        ArrayList<AllMoshtaryPishdaryaftModel> AllMoshtaryPishdaryaftModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AllMoshtaryPishdaryaftModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    AllMoshtaryPishdaryaftModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AllMoshtaryPishdaryaftModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryPishdaryaftDAO" , "" , "getAll" , "");
        }
        return AllMoshtaryPishdaryaftModels;
    }

    public AllMoshtaryPishdaryaftModel getByccMoshtary(int ccMoshtary)
    {
        AllMoshtaryPishdaryaftModel moshtaryModel = new AllMoshtaryPishdaryaftModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AllMoshtaryPishdaryaftModel.TableName(), allColumns(),  "ccMoshtary= ?", new String[]{String.valueOf(ccMoshtary)}, null, null,  null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AllMoshtaryPishdaryaftModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryPishdaryaftDAO" , "" , "getByccMoshtary" , "");
        }
        return moshtaryModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AllMoshtaryPishdaryaftModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AllMoshtaryPishdaryaftModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AllMoshtaryPishdaryaftDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(AllMoshtaryPishdaryaftModel AllMoshtaryPishdaryaftModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_ccMoshtary() , AllMoshtaryPishdaryaftModel.getCcMoshtary());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_CodeMoshtary() , AllMoshtaryPishdaryaftModel.getCodeMoshtary());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_NameMoshtary() , AllMoshtaryPishdaryaftModel.getNameMoshtary());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_Address() , AllMoshtaryPishdaryaftModel.getAddress());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_Telephone() , AllMoshtaryPishdaryaftModel.getTelephone());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_ccMasir() , AllMoshtaryPishdaryaftModel.getCcMasir());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_NameMasir() , AllMoshtaryPishdaryaftModel.getNameMasir());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_ccForoshandeh() , AllMoshtaryPishdaryaftModel.getCcForoshandeh());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_Latitude() , AllMoshtaryPishdaryaftModel.getLatitude());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_Longitude() , AllMoshtaryPishdaryaftModel.getLongitude());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_ccNoeMoshtary() , AllMoshtaryPishdaryaftModel.getCcNoeMoshtary());
        contentValues.put(AllMoshtaryPishdaryaftModel.COLUMN_Darajeh() , AllMoshtaryPishdaryaftModel.getDarajeh());

        return contentValues;
    }

    private ArrayList<AllMoshtaryPishdaryaftModel> cursorToModel(Cursor cursor)
    {
        ArrayList<AllMoshtaryPishdaryaftModel> AllMoshtaryPishdaryaftModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            AllMoshtaryPishdaryaftModel AllMoshtaryPishdaryaftModel = new AllMoshtaryPishdaryaftModel();

            AllMoshtaryPishdaryaftModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_ccMoshtary())));
            AllMoshtaryPishdaryaftModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_CodeMoshtary())));
            AllMoshtaryPishdaryaftModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_NameMoshtary())));
            AllMoshtaryPishdaryaftModel.setAddress(cursor.getString(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_Address())));
            AllMoshtaryPishdaryaftModel.setTelephone(cursor.getString(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_Telephone())));
            AllMoshtaryPishdaryaftModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_ccMasir())));
            AllMoshtaryPishdaryaftModel.setNameMasir(cursor.getString(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_NameMasir())));
            AllMoshtaryPishdaryaftModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_ccForoshandeh())));
            AllMoshtaryPishdaryaftModel.setLatitude(cursor.getInt(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_Latitude())));
            AllMoshtaryPishdaryaftModel.setLongitude(cursor.getInt(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_Longitude())));
            AllMoshtaryPishdaryaftModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_Darajeh())));
            AllMoshtaryPishdaryaftModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(AllMoshtaryPishdaryaftModel.COLUMN_ccNoeMoshtary())));

            AllMoshtaryPishdaryaftModels.add(AllMoshtaryPishdaryaftModel);
            cursor.moveToNext();
        }
        return AllMoshtaryPishdaryaftModels;
    }
    
}
