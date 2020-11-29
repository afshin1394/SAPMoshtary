package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetGPSDataResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GPSDataPpcDAO
{

    private DBHelper dbHelper;
    private Context context;



    public GPSDataPpcDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "GPSDataPPC" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            GPSDataModel.COLUMN_ccGpsData_PPC(),
            GPSDataModel.COLUMN_ccAfrad(),
            GPSDataModel.COLUMN_ccForoshandeh(),
            GPSDataModel.COLUMN_ccMasir(),
            GPSDataModel.COLUMN_Latitude(),
            GPSDataModel.COLUMN_Longitude(),
            GPSDataModel.COLUMN_Tarikh(),
            GPSDataModel.COLUMN_ExtraProp_IsSend(),
            GPSDataModel.COLUMN_Distance(),
            GPSDataModel.COLUMN_ccMamorPakhsh(),
            GPSDataModel.COLUMN_Altitude(),
            GPSDataModel.COLUMN_Accurancy(),
            GPSDataModel.COLUMN_Bearing(),
            GPSDataModel.COLUMN_Speed(),
            GPSDataModel.COLUMN_Status(),
            GPSDataModel.COLUMN_ElapsedRealtimeNanos(),
            GPSDataModel.COLUMN_Provider()
        };
    }


    public void fetchGPSDataByccForoshandeh(final Context context, final String activityNameForLog,final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccForoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetGPSDataResult> call = apiService.getGPSDataByccForoshandeh(ccForoshandeh);
            call.enqueue(new Callback<GetGPSDataResult>() {
                @Override
                public void onResponse(Call<GetGPSDataResult> call, Response<GetGPSDataResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, GPSDataPpcDAO.class.getSimpleName(), "", "fetchGPSDataByccForoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetGPSDataResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccForoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccForoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccForoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccForoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetGPSDataResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccForoshandeh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public void fetchGPSDataByccMamorPakhs(final Context context, final String activityNameForLog,final String ccMamorPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccMamorPakhs", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetGPSDataResult> call = apiService.getGPSDataByccMamorPakhsh(ccMamorPakhsh);
            call.enqueue(new Callback<GetGPSDataResult>() {
                @Override
                public void onResponse(Call<GetGPSDataResult> call, Response<GetGPSDataResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, GPSDataPpcDAO.class.getSimpleName(), "", "fetchGPSDataByccMamorPakhs", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetGPSDataResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccMamorPakhs", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccMamorPakhs", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccMamorPakhs", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccMamorPakhs", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetGPSDataResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), GPSDataPpcDAO.class.getSimpleName(), activityNameForLog, "fetchGPSDataByccMamorPakhs", "onFailure");
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

    public boolean insert(GPSDataModel gpsDataModel)
    {
        Log.d("gps" , "before insert : " + gpsDataModel.toString());
        ContentValues contentValues = modelToContentValue(gpsDataModel);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(GPSDataModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "GPSDataPPC" , "" , "insert" , "");
            return false;
        }
    }

    public boolean insertGroup(ArrayList<GPSDataModel> gpsDataModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (GPSDataModel gpsDataModel : gpsDataModels)
            {
                ContentValues contentValues = modelToContentValue(gpsDataModel);
                db.insertOrThrow(GPSDataModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public boolean updateById(int id , int status)
    {
        String query = "update " + GPSDataModel.TableName() + " set " + GPSDataModel.COLUMN_Status() + " = " + status + " where " + GPSDataModel.COLUMN_ccGpsData_PPC() + " = " + id;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO", "",  "updateById", "");
            return false;
        }
    }

    public boolean updateIsSend(String ccGPSDatas)
    {
        String query = "update " + GPSDataModel.TableName() + " set " + GPSDataModel.COLUMN_ExtraProp_IsSend() + " = 1 where " + GPSDataModel.COLUMN_ccGpsData_PPC() + " in ( " + ccGPSDatas + ")";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO", "",  "updateIsSend", "");
            return false;
        }
    }

    public ArrayList<GPSDataModel> getAll()
    {
        ArrayList<GPSDataModel> arrayListGpsDataModel = new ArrayList<>();
        String query = "select * from " + GPSDataModel.TableName();

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                arrayListGpsDataModel = cursorToArraylistModel(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO" , "" , "getAll" , "");
        }
        return arrayListGpsDataModel;
    }


    public ArrayList<GPSDataModel> getAllByccForoshandeh(int ccForoshande)
    {
        ArrayList<GPSDataModel> arrayListGpsDataModel = new ArrayList<>();
        String query = "select * from " + GPSDataModel.TableName() + " where " + GPSDataModel.COLUMN_ccForoshandeh() + " = " + ccForoshande + " order by " + GPSDataModel.COLUMN_ccGpsData_PPC();

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                arrayListGpsDataModel = cursorToArraylistModel(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO" , "" , "getAllByccForoshandeh" , "");
        }
        return arrayListGpsDataModel;
    }

    public ArrayList<GPSDataModel> getAllByccMamorPakhsh(int ccMamorPakhsh)
    {
        ArrayList<GPSDataModel> arrayListGpsDataModel = new ArrayList<>();
        String query = "select * from " + GPSDataModel.TableName() + " where " + GPSDataModel.COLUMN_ccMamorPakhsh() + " = " + ccMamorPakhsh + " order by " + GPSDataModel.COLUMN_ccGpsData_PPC();

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                arrayListGpsDataModel = cursorToArraylistModel(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO" , "" , "getAllByccMamorPakhsh" , "");
        }
        return arrayListGpsDataModel;
    }

    public ArrayList<GPSDataModel> getAllNotSend()
    {
        ArrayList<GPSDataModel> arrayListGpsDataModel = new ArrayList<>();
        String query = "select * from " + GPSDataModel.TableName() + " where " + GPSDataModel.COLUMN_ExtraProp_IsSend() + " = 0";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                arrayListGpsDataModel = cursorToArraylistModel(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO" , "" , "getAllNotSend" , "");
        }
        return arrayListGpsDataModel;
    }

    public GPSDataModel getLastPoint()
    {
        //String query = "select * from " + GPSDataModel.TableName() + " order by " + COLUMN_CC_GPS_DATA_PPC + " desc";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //Cursor cursor = db.rawQuery(query , null);
            Cursor cursor = db.query(GPSDataModel.TableName(),allColumns(),null,null,null,null,GPSDataModel.COLUMN_ccGpsData_PPC() + " desc");
            if (cursor != null)
            {
                ArrayList<GPSDataModel> arrayList = cursorToArraylistModel(cursor);
                if (arrayList.size() > 0)
                {
                    db.close();
                    return arrayList.get(0);
                }
                else
                {
                    db.close();
                    return null;
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        return null;
    }

    public boolean deleteAll()
    {
        String query = "delete from " + GPSDataModel.TableName();
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteSendedRecords()
    {
        //String query = "delete from " + GPSDataModel.TableName() + " where " + GPSDataModel.COLUMN_ExtraProp_IsSend() + " = 1 ";
        String query = "delete from " + GPSDataModel.TableName() ;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , GPSDataModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GPSDataPpcDAO" , "" , "deleteSendedRecords" , "");
            return false;
        }
    }

    private ContentValues modelToContentValue(GPSDataModel model)
    {
        ContentValues contentValues = new ContentValues();

        if (model.getCcGpsData_PPC() != null && model.getCcGpsData_PPC() > 0)
        {
            contentValues.put(GPSDataModel.COLUMN_ccGpsData_PPC() , model.getCcGpsData_PPC());
        }
        contentValues.put(GPSDataModel.COLUMN_ccAfrad() , model.getCcAfrad());
        contentValues.put(GPSDataModel.COLUMN_ccForoshandeh() , model.getCcForoshandeh());
        contentValues.put(GPSDataModel.COLUMN_ccMasir() , model.getCcMasir());
        contentValues.put(GPSDataModel.COLUMN_Latitude() , model.getLatitude());
        contentValues.put(GPSDataModel.COLUMN_Longitude() , model.getLongitude());
        contentValues.put(GPSDataModel.COLUMN_Tarikh() , model.getTarikh());
        contentValues.put(GPSDataModel.COLUMN_ExtraProp_IsSend() , model.getExtraProp_IsSend());
        contentValues.put(GPSDataModel.COLUMN_Distance() , model.getDistance());
        contentValues.put(GPSDataModel.COLUMN_ccMamorPakhsh() , model.getCcMamorPakhsh());
        contentValues.put(GPSDataModel.COLUMN_Altitude() , model.getAltitude());
        contentValues.put(GPSDataModel.COLUMN_Accurancy() , model.getAccurancy());
        contentValues.put(GPSDataModel.COLUMN_Bearing() , model.getBearing());
        contentValues.put(GPSDataModel.COLUMN_Speed() , model.getSpeed());
        contentValues.put(GPSDataModel.COLUMN_Status() , model.getStatus());
        contentValues.put(GPSDataModel.COLUMN_ElapsedRealtimeNanos() , model.getElapsedRealTimeNanos());
        contentValues.put(GPSDataModel.COLUMN_Provider() , model.getProvider());

        return contentValues;
    }


    private ArrayList<GPSDataModel> cursorToArraylistModel(Cursor cursor)
    {
        ArrayList<GPSDataModel> arrayListGpsDataModel = new ArrayList<>();
        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                GPSDataModel gpsDataModel = new GPSDataModel();
                gpsDataModel.setCcGpsData_PPC(cursor.getInt(cursor.getColumnIndex(GPSDataModel.COLUMN_ccGpsData_PPC())));
                gpsDataModel.setCcAfrad(cursor.getInt(cursor.getColumnIndex(GPSDataModel.COLUMN_ccAfrad())));
                gpsDataModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(GPSDataModel.COLUMN_ccForoshandeh())));
                gpsDataModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(GPSDataModel.COLUMN_ccMasir())));
                gpsDataModel.setLatitude(cursor.getFloat(cursor.getColumnIndex(GPSDataModel.COLUMN_Latitude())));
                gpsDataModel.setLongitude(cursor.getFloat(cursor.getColumnIndex(GPSDataModel.COLUMN_Longitude())));
                gpsDataModel.setTarikh(cursor.getString(cursor.getColumnIndex(GPSDataModel.COLUMN_Tarikh())));
                gpsDataModel.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(GPSDataModel.COLUMN_ExtraProp_IsSend())));
                gpsDataModel.setDistance(cursor.getFloat(cursor.getColumnIndex(GPSDataModel.COLUMN_Distance())));
                gpsDataModel.setCcMamorPakhsh(cursor.getInt(cursor.getColumnIndex(GPSDataModel.COLUMN_ccMamorPakhsh())));
                gpsDataModel.setAltitude(cursor.getFloat(cursor.getColumnIndex(GPSDataModel.COLUMN_Altitude())));
                gpsDataModel.setAccurancy(cursor.getFloat(cursor.getColumnIndex(GPSDataModel.COLUMN_Accurancy())));
                gpsDataModel.setBearing(cursor.getFloat(cursor.getColumnIndex(GPSDataModel.COLUMN_Bearing())));
                gpsDataModel.setSpeed(cursor.getFloat(cursor.getColumnIndex(GPSDataModel.COLUMN_Speed())));
                gpsDataModel.setStatus(cursor.getInt(cursor.getColumnIndex(GPSDataModel.COLUMN_Status())));
                gpsDataModel.setElapsedRealTimeNanos(cursor.getLong(cursor.getColumnIndex(GPSDataModel.COLUMN_ElapsedRealtimeNanos())));
                gpsDataModel.setProvider(cursor.getString(cursor.getColumnIndex(GPSDataModel.COLUMN_Provider())));

                arrayListGpsDataModel.add(gpsDataModel);
                cursor.moveToNext();
            }
        }
        return arrayListGpsDataModel;
    }




}
