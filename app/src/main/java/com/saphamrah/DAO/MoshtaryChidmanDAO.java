package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryChidmanModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryChidmanResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryChidmanDAO 
{


    private DBHelper dbHelper;
    private Context context;


    public MoshtaryChidmanDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryChidmanDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryChidmanModel.COLUMN_ccMoshtaryChidman(),
            MoshtaryChidmanModel.COLUMN_ccMoshtary(),
            MoshtaryChidmanModel.COLUMN_ccMasir(),
            MoshtaryChidmanModel.COLUMN_TarikhMasir()
        };
    }

    public void fetchMoshtaryChidman(final Context context, final String activityNameForLog,final String ccMasirs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryChidmanDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryChidman", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllMoshtaryChidmanResult> call = apiService.getAllMoshtaryChidman(ccMasirs);
            call.enqueue(new Callback<GetAllMoshtaryChidmanResult>()
            {
                @Override
                public void onResponse(Call<GetAllMoshtaryChidmanResult> call, Response<GetAllMoshtaryChidmanResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryChidmanDAO.class.getSimpleName(), "", "fetchMoshtaryChidman", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMoshtaryChidmanResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryChidmanDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryChidman", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryChidmanDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryChidman", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryChidmanDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryChidman", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryChidmanDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryChidman", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtaryChidmanResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryChidmanDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryChidman", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryChidmanModel moshtaryChidmanModel : moshtaryChidmanModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryChidmanModel);
                db.insertOrThrow(MoshtaryChidmanModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryChidmanModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryChidmanDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MoshtaryChidmanModel> getAll()
    {
        ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryChidmanModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryChidmanModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryChidmanModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryChidmanDAO" , "" , "getAll" , "");
        }
        return moshtaryChidmanModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryChidmanModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryChidmanModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryChidmanDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryChidmanModel moshtaryChidmanModel)
    {
        ContentValues contentValues = new ContentValues();

        if (moshtaryChidmanModel.getCcMoshtaryChidman() > 0)
        {
            contentValues.put(MoshtaryChidmanModel.COLUMN_ccMoshtaryChidman() , moshtaryChidmanModel.getCcMoshtaryChidman());
        }
        contentValues.put(MoshtaryChidmanModel.COLUMN_ccMoshtary() , moshtaryChidmanModel.getCcMoshtary());
        contentValues.put(MoshtaryChidmanModel.COLUMN_ccMasir() , moshtaryChidmanModel.getCcMasir());
        contentValues.put(MoshtaryChidmanModel.COLUMN_TarikhMasir() , moshtaryChidmanModel.getTarikhMasir());

        return contentValues;
    }


    private ArrayList<MoshtaryChidmanModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryChidmanModel moshtaryChidmanModel = new MoshtaryChidmanModel();

            moshtaryChidmanModel.setCcMoshtaryChidman(cursor.getInt(cursor.getColumnIndex(MoshtaryChidmanModel.COLUMN_ccMoshtaryChidman())));
            moshtaryChidmanModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryChidmanModel.COLUMN_ccMoshtary())));
            moshtaryChidmanModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(MoshtaryChidmanModel.COLUMN_ccMasir())));
            moshtaryChidmanModel.setTarikhMasir(cursor.getString(cursor.getColumnIndex(MoshtaryChidmanModel.COLUMN_TarikhMasir())));

            moshtaryChidmanModels.add(moshtaryChidmanModel);
            cursor.moveToNext();
        }
        return moshtaryChidmanModels;
    }
    
}
