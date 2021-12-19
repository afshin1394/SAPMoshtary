package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvMoshtaryShomarehHesabResult;

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

public class MoshtaryShomarehHesabDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryShomarehHesabDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryShomarehHesabDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryShomarehHesabModel.COLUMN_ccMoshtaryShomarehHesab(),
            MoshtaryShomarehHesabModel.COLUMN_ccMoshtary(),
            MoshtaryShomarehHesabModel.COLUMN_CodeMoshtary(),
            MoshtaryShomarehHesabModel.COLUMN_NameMoshtary(),
            MoshtaryShomarehHesabModel.COLUMN_ccBank(),
            MoshtaryShomarehHesabModel.COLUMN_NameBank(),
            MoshtaryShomarehHesabModel.COLUMN_ccNoeHesab(),
            MoshtaryShomarehHesabModel.COLUMN_NameNoeHesab(),
            MoshtaryShomarehHesabModel.COLUMN_ccShomarehHesab(),
            MoshtaryShomarehHesabModel.COLUMN_ShomarehHesab(),
            MoshtaryShomarehHesabModel.COLUMN_NameShobeh(),
            MoshtaryShomarehHesabModel.COLUMN_CodeShobeh(),
            MoshtaryShomarehHesabModel.COLUMN_ShartBardashtAzHesab(),
            MoshtaryShomarehHesabModel.COLUMN_SahebanHesab()
        };
    }

    public void fetchAllvMoshtaryShomarehHesab(final Context context, final String activityNameForLog, final String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryShomarehHesab", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvMoshtaryShomarehHesabResult> call = apiServiceGet.getAllvMoshtaryShomarehHesab(ccMoshtarys);
            call.enqueue(new Callback<GetAllvMoshtaryShomarehHesabResult>() {
                @Override
                public void onResponse(Call<GetAllvMoshtaryShomarehHesabResult> call, Response<GetAllvMoshtaryShomarehHesabResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryShomarehHesabDAO.class.getSimpleName(), "", "fetchAllvMoshtaryShomarehHesab", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvMoshtaryShomarehHesabResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryShomarehHesab", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryShomarehHesab", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryShomarehHesab", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryShomarehHesab", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvMoshtaryShomarehHesabResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryShomarehHesabDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryShomarehHesab", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryShomarehHesabModel moshtaryShomarehHesabModel : moshtaryShomarehHesabModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryShomarehHesabModel);
                db.insertOrThrow(MoshtaryShomarehHesabModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryShomarehHesabDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MoshtaryShomarehHesabModel> getAll()
    {
        ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryShomarehHesabModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryShomarehHesabModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryShomarehHesabDAO" , "" , "getAll" , "");
        }
        return moshtaryShomarehHesabModels;
    }


    public ArrayList<MoshtaryShomarehHesabModel> getAllByccMoshtary(int ccMoshtary)
    {
        ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryShomarehHesabModel.TableName(), allColumns(), MoshtaryShomarehHesabModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryShomarehHesabModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryShomarehHesabDAO" , "" , "getAllByccMoshtary" , "");
        }
        return moshtaryShomarehHesabModels;
    }

    public String getNameMoshtaryByccMoshtaryShomareHesab(int ccMoshtaryShomareHesab)
    {
        String nameMoshtary = "";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryShomarehHesabModel.TableName(), new String[]{MoshtaryShomarehHesabModel.COLUMN_NameMoshtary()}, MoshtaryShomarehHesabModel.COLUMN_ccMoshtaryShomarehHesab() + " = " + ccMoshtaryShomareHesab, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    nameMoshtary = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryShomarehHesabDAO" , "" , "getNameMoshtaryByccShomareHesab" , "");
        }
        return nameMoshtary;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryShomarehHesabModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryShomarehHesabDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryShomarehHesabModel.TableName() , MoshtaryShomarehHesabModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryShomarehHesabDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    public boolean updateccShomareHesab(int ccMoshtaryShomareHesab , int newccShomareHesab)
    {
        try
        {
            String query = "update " + MoshtaryShomarehHesabModel.TableName() + " set " + MoshtaryShomarehHesabModel.COLUMN_ccShomarehHesab() + " = " + newccShomareHesab + " where " + MoshtaryShomarehHesabModel.COLUMN_ccMoshtaryShomarehHesab() + " = " + ccMoshtaryShomareHesab;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryShomarehHesabDAO" , "" , "updateccMoshtaryShomareHesab" , "");
            return false;
        }
    }

    public boolean updateMoshtaryJadid(int newccMoshtary , int oldccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MoshtaryShomarehHesabModel.COLUMN_ccMoshtary(), newccMoshtary);
            db.update(MoshtaryShomarehHesabModel.TableName(), values, "ccMoshtary= ?", new String[]{String.valueOf(oldccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryShomarehHesabModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryShomarehHesabDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel)
    {
        ContentValues contentValues = new ContentValues();

        if (moshtaryShomarehHesabModel.getCcMoshtaryShomarehHesab() > 0)
        {
            contentValues.put(MoshtaryShomarehHesabModel.COLUMN_ccMoshtaryShomarehHesab() , moshtaryShomarehHesabModel.getCcMoshtaryShomarehHesab());
        }
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_ccMoshtary() , moshtaryShomarehHesabModel.getCcMoshtary());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_CodeMoshtary() , moshtaryShomarehHesabModel.getCodeMoshtary());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_NameMoshtary() , moshtaryShomarehHesabModel.getNameMoshtary());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_ccBank() , moshtaryShomarehHesabModel.getCcBank());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_NameBank() , moshtaryShomarehHesabModel.getNameBank());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_ccNoeHesab() , moshtaryShomarehHesabModel.getCcNoeHesab());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_NameNoeHesab() , moshtaryShomarehHesabModel.getNameNoeHesab());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_ccShomarehHesab() , moshtaryShomarehHesabModel.getCcShomarehHesab());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_ShomarehHesab() , moshtaryShomarehHesabModel.getShomarehHesab());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_NameShobeh() , moshtaryShomarehHesabModel.getNameShobeh());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_CodeShobeh() , moshtaryShomarehHesabModel.getCodeShobeh());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_ShartBardashtAzHesab() , moshtaryShomarehHesabModel.getShartBardashtAzHesab());
        contentValues.put(MoshtaryShomarehHesabModel.COLUMN_SahebanHesab() , moshtaryShomarehHesabModel.getSahebanHesab());

        return contentValues;
    }


    private ArrayList<MoshtaryShomarehHesabModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryShomarehHesabModel moshtaryShomarehHesabModel = new MoshtaryShomarehHesabModel();

            moshtaryShomarehHesabModel.setCcMoshtaryShomarehHesab(cursor.getInt(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_ccMoshtaryShomarehHesab())));
            moshtaryShomarehHesabModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_ccMoshtary())));
            moshtaryShomarehHesabModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_CodeMoshtary())));
            moshtaryShomarehHesabModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_NameMoshtary())));
            moshtaryShomarehHesabModel.setCcBank(cursor.getInt(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_ccBank())));
            moshtaryShomarehHesabModel.setNameBank(cursor.getString(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_NameBank())));
            moshtaryShomarehHesabModel.setCcNoeHesab(cursor.getInt(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_ccNoeHesab())));
            moshtaryShomarehHesabModel.setNameNoeHesab(cursor.getString(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_NameNoeHesab())));
            moshtaryShomarehHesabModel.setCcShomarehHesab(cursor.getInt(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_ccShomarehHesab())));
            moshtaryShomarehHesabModel.setShomarehHesab(cursor.getString(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_ShomarehHesab())));
            moshtaryShomarehHesabModel.setNameShobeh(cursor.getString(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_NameShobeh())));
            moshtaryShomarehHesabModel.setCodeShobeh(cursor.getString(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_CodeShobeh())));
            moshtaryShomarehHesabModel.setShartBardashtAzHesab(cursor.getInt(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_ShartBardashtAzHesab())));
            moshtaryShomarehHesabModel.setSahebanHesab(cursor.getString(cursor.getColumnIndex(MoshtaryShomarehHesabModel.COLUMN_SahebanHesab())));

            moshtaryShomarehHesabModels.add(moshtaryShomarehHesabModel);
            cursor.moveToNext();
        }
        return moshtaryShomarehHesabModels;
    }


    
}
