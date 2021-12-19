package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvPolygonForoshSatrByForoshandehResult;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolygonForoshSatrDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public PolygonForoshSatrDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "PolygonForoshSatrDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            PolygonForoshSatrModel.COLUMN_ccPolygonForosh(),
            PolygonForoshSatrModel.COLUMN_ccPolygonForoshSatr(),
            PolygonForoshSatrModel.COLUMN_Lat_y(),
            PolygonForoshSatrModel.COLUMN_Lng_x(),
            PolygonForoshSatrModel.COLUMN_ccMasir(),
            PolygonForoshSatrModel.COLUMN_ccForoshandeh()
        };
    }


    public void fetchAllvPolygonForoshSatrByForoshandeh(final Context context, final String activityNameForLog,final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, PolygonForoshSatrDAO.class.getSimpleName(), activityNameForLog, "fetchAllvPolygonForoshSatrByForoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvPolygonForoshSatrByForoshandehResult> call = apiServiceGet.getAllvPolygonForoshSatrByForoshandeh(ccForoshandeh);
            call.enqueue(new Callback<GetAllvPolygonForoshSatrByForoshandehResult>() {
                @Override
                public void onResponse(Call<GetAllvPolygonForoshSatrByForoshandehResult> call, Response<GetAllvPolygonForoshSatrByForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, PolygonForoshSatrDAO.class.getSimpleName(), "", "fetchAllvPolygonForoshSatrByForoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvPolygonForoshSatrByForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), PolygonForoshSatrDAO.class.getSimpleName(), activityNameForLog, "fetchAllvPolygonForoshSatrByForoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), PolygonForoshSatrDAO.class.getSimpleName(), activityNameForLog, "fetchAllvPolygonForoshSatrByForoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, PolygonForoshSatrDAO.class.getSimpleName(), activityNameForLog, "fetchAllvPolygonForoshSatrByForoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), PolygonForoshSatrDAO.class.getSimpleName(), activityNameForLog, "fetchAllvPolygonForoshSatrByForoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvPolygonForoshSatrByForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), PolygonForoshSatrDAO.class.getSimpleName(), activityNameForLog, "fetchAllvPolygonForoshSatrByForoshandeh", "onFailure");
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

    public boolean insertGroup(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (PolygonForoshSatrModel polygonForoshSatrModel : polygonForoshSatrModels)
            {
                ContentValues contentValues = modelToContentvalue(polygonForoshSatrModel);
                db.insertOrThrow(PolygonForoshSatrModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , PolygonForoshSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "PolygonForoshSatrDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<PolygonForoshSatrModel> getAll()
    {
        ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PolygonForoshSatrModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    polygonForoshSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PolygonForoshSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "PolygonForoshSatrDAO" , "" , "getAll" , "");
        }
        return polygonForoshSatrModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PolygonForoshSatrModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PolygonForoshSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "PolygonForoshSatrDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public ArrayList<String> getAllccPolygonForosh()
    {
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "select distinct(" + PolygonForoshSatrModel.COLUMN_ccPolygonForosh() + ") from " + PolygonForoshSatrModel.TableName();
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
                        arrayList.add(cursor.getString(cursor.getColumnIndex(PolygonForoshSatrModel.COLUMN_ccPolygonForosh())));
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
            String message = context.getResources().getString(R.string.errorSelectAll , PolygonForoshSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "PolygonForoshSatrDAO" , "" , "getAllccPolygonForosh" , "");
        }
        return arrayList;
    }


    public ArrayList<PolygonForoshSatrModel> getAllByCcPolygonForosh(String ccPolygonForosh)
    {
        ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PolygonForoshSatrModel.TableName(), allColumns(), PolygonForoshSatrModel.COLUMN_ccPolygonForosh() + " = " + ccPolygonForosh, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    polygonForoshSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PolygonForoshSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "PolygonForoshSatrDAO" , "" , "getAllByCcPolygonForosh" , "");
        }
        Log.d("SellPolygon","polygonForoshSatrModels:"+polygonForoshSatrModels.toString());
        return polygonForoshSatrModels;
    }

    private static ContentValues modelToContentvalue(PolygonForoshSatrModel polygonForoshSatrModel)
    {
        ContentValues contentValues = new ContentValues();

        if (polygonForoshSatrModel.getCcPolygonForoshSatr() > 0)
        {
            contentValues.put(PolygonForoshSatrModel.COLUMN_ccPolygonForoshSatr() , polygonForoshSatrModel.getCcPolygonForoshSatr());
        }
        contentValues.put(PolygonForoshSatrModel.COLUMN_ccPolygonForosh() , polygonForoshSatrModel.getCcPolygonForosh());
        contentValues.put(PolygonForoshSatrModel.COLUMN_Lat_y() , polygonForoshSatrModel.getLat_y());
        contentValues.put(PolygonForoshSatrModel.COLUMN_Lng_x() , polygonForoshSatrModel.getLng_x());
        contentValues.put(PolygonForoshSatrModel.COLUMN_ccMasir() , polygonForoshSatrModel.getCcMasir());
        contentValues.put(PolygonForoshSatrModel.COLUMN_ccForoshandeh() , polygonForoshSatrModel.getCcForoshandeh());

        return contentValues;
    }


    private ArrayList<PolygonForoshSatrModel> cursorToModel(Cursor cursor)
    {
        ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            PolygonForoshSatrModel polygonForoshSatrModel = new PolygonForoshSatrModel();

            polygonForoshSatrModel.setCcPolygonForosh(cursor.getInt(cursor.getColumnIndex(PolygonForoshSatrModel.COLUMN_ccPolygonForosh())));
            polygonForoshSatrModel.setCcPolygonForoshSatr(cursor.getInt(cursor.getColumnIndex(PolygonForoshSatrModel.COLUMN_ccPolygonForoshSatr())));
            polygonForoshSatrModel.setLat_y(cursor.getDouble(cursor.getColumnIndex(PolygonForoshSatrModel.COLUMN_Lat_y())));
            polygonForoshSatrModel.setLng_x(cursor.getDouble(cursor.getColumnIndex(PolygonForoshSatrModel.COLUMN_Lng_x())));
            polygonForoshSatrModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(PolygonForoshSatrModel.COLUMN_ccMasir())));
            polygonForoshSatrModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(PolygonForoshSatrModel.COLUMN_ccForoshandeh())));

            polygonForoshSatrModels.add(polygonForoshSatrModel);
            cursor.moveToNext();
        }
        return polygonForoshSatrModels;
    }
    
    
}
