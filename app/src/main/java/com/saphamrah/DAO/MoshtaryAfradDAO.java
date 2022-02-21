package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import androidx.annotation.NonNull;

import com.saphamrah.Model.MoshtaryAfradModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvMoshtaryAfradResult;
import com.saphamrah.protos.PersonCustomerGrpc;
import com.saphamrah.protos.PersonCustomerReply;
import com.saphamrah.protos.PersonCustomerReplyList;
import com.saphamrah.protos.PersonCustomerRequest;

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

public class MoshtaryAfradDAO
{
    
    private DBHelper dbHelper;
    private Context context;


    public MoshtaryAfradDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryAfradDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryAfradModel.COLUMN_ccMoshtaryAfrad(),
            MoshtaryAfradModel.COLUMN_ccMoshtary(),
            MoshtaryAfradModel.COLUMN_ccAfrad(),
            MoshtaryAfradModel.COLUMN_FullNameMoshtaryAfrad(),
            MoshtaryAfradModel.COLUMN_MojazEmza(),
            MoshtaryAfradModel.COLUMN_TarafHesab(),
            MoshtaryAfradModel.COLUMN_FName(),
            MoshtaryAfradModel.COLUMN_LName(),
            MoshtaryAfradModel.COLUMN_ExtraProp_BirthDate()
        };
    }

    public void fetchAllvMoshtaryAfradGrpc(final Context context, final String activityNameForLog,final String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                PersonCustomerGrpc.PersonCustomerBlockingStub personCustomerBlockingStub = PersonCustomerGrpc.newBlockingStub(managedChannel);
                PersonCustomerRequest personCustomerRequest = PersonCustomerRequest.newBuilder().setCustomersID(ccMoshtarys).build();
                Callable<PersonCustomerReplyList> personCustomerReplyListCallable = () -> personCustomerBlockingStub.getPersonCustomer(personCustomerRequest);
                RxAsync.makeObservable(personCustomerReplyListCallable)
                        .map(personCustomerReplyList -> {
                            ArrayList<MoshtaryAfradModel> moshtaryAfradModels = new ArrayList<>();
                            for (PersonCustomerReply personCustomerReply : personCustomerReplyList.getPersonCustomersList()) {

                                MoshtaryAfradModel moshtaryAfradModel = new MoshtaryAfradModel();

                                moshtaryAfradModel.setCcAfrad(personCustomerReply.getPersonID());
                                moshtaryAfradModel.setCcMoshtaryAfrad(personCustomerReply.getPersonCustomerID());
                                moshtaryAfradModel.setCcMoshtary(personCustomerReply.getCustomerID());
                                moshtaryAfradModel.setFullNameMoshtaryAfrad(personCustomerReply.getFullNamePersonCustomer());
                                moshtaryAfradModel.setMojazEmza(personCustomerReply.getAllowSignature());
                                moshtaryAfradModel.setTarafHesab(personCustomerReply.getAccountParty());
                                moshtaryAfradModel.setFName(personCustomerReply.getFullName());
                                moshtaryAfradModel.setLName(personCustomerReply.getLastName());


                                moshtaryAfradModels.add(moshtaryAfradModel);
                            }

                            return moshtaryAfradModels;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MoshtaryAfradModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MoshtaryAfradModel> darkhastFaktorModels) {
                                retrofitResponse.onSuccess(darkhastFaktorModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAfradGrpc", "CatchException");
                                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                                if (!compositeDisposable.isDisposed()) {
                                    compositeDisposable.dispose();
                                }
                                compositeDisposable.clear();
                            }
                        });

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAfradGrpc", "CatchException");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    public void fetchAllvMoshtaryAfrad(final Context context, final String activityNameForLog,final String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAfrad", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Log.d("getprogram afrad",ccMoshtarys);
            Call<GetAllvMoshtaryAfradResult> call = apiServiceGet.getAllvMoshtaryAfrad(ccMoshtarys);
            call.enqueue(new Callback<GetAllvMoshtaryAfradResult>() {
                @Override
                public void onResponse(Call<GetAllvMoshtaryAfradResult> call, Response<GetAllvMoshtaryAfradResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryAfradDAO.class.getSimpleName(), "", "fetchAllvMoshtaryAfrad", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvMoshtaryAfradResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAfrad", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAfrad", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAfrad", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAfrad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvMoshtaryAfradResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryAfradDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAfrad", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<MoshtaryAfradModel> moshtaryAfradModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryAfradModel moshtaryAfradModel : moshtaryAfradModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryAfradModel);
                db.insertOrThrow(MoshtaryAfradModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(MoshtaryAfradModel moshtaryAfradModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(moshtaryAfradModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(MoshtaryAfradModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<MoshtaryAfradModel> getAll()
    {
        ArrayList<MoshtaryAfradModel> moshtaryAfradModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryAfradModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAfradModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "getAll" , "");
        }
        return moshtaryAfradModels;
    }

    public ArrayList<MoshtaryAfradModel> getByccMoshtary(int ccMoshtary)
    {
        ArrayList<MoshtaryAfradModel> moshtaryAfradModel = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryAfradModel.TableName(), allColumns(), MoshtaryAfradModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAfradModel = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "getByccMoshtary" , "");
        }
        return moshtaryAfradModel;
    }

    public boolean deleteGroup(String ccAfrads)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAfradModel.TableName(), MoshtaryAfradModel.COLUMN_ccAfrad() + " in ( " + ccAfrads + " )", null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "deleteGroup" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAfradModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAfradModel.TableName() , MoshtaryAfradModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    public boolean updateccAfrad(int newccAfrad , int oldccAfrad)
    {
        try
        {
            String query = "update " + MoshtaryAfradModel.TableName() + " set " + MoshtaryAfradModel.COLUMN_ccAfrad() + " = " + newccAfrad + " where " + MoshtaryAfradModel.COLUMN_ccAfrad() + " = " + oldccAfrad;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "updateccAfrad" , "");
            return false;
        }
    }

    public boolean updateMoshtaryJadid(int newccMoshtary , int oldccMoshtary , int newccAfrad)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MoshtaryAfradModel.COLUMN_ccMoshtary(), newccMoshtary);
            values.put(MoshtaryAfradModel.COLUMN_ccAfrad(), newccAfrad);
            db.update(MoshtaryAfradModel.TableName(), values, "ccMoshtary= ?", new String[]{String.valueOf(oldccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryAfradModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAfradDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryAfradModel moshtaryAfradModel)
    {
        ContentValues contentValues = new ContentValues();

        if (moshtaryAfradModel.getCcMoshtaryAfrad() > 0)
        {
            contentValues.put(MoshtaryAfradModel.COLUMN_ccMoshtaryAfrad() , moshtaryAfradModel.getCcMoshtaryAfrad());
        }
        contentValues.put(MoshtaryAfradModel.COLUMN_ccMoshtary() , moshtaryAfradModel.getCcMoshtary());
        contentValues.put(MoshtaryAfradModel.COLUMN_ccAfrad() , moshtaryAfradModel.getCcAfrad());
        contentValues.put(MoshtaryAfradModel.COLUMN_FullNameMoshtaryAfrad() , moshtaryAfradModel.getFullNameMoshtaryAfrad());
        contentValues.put(MoshtaryAfradModel.COLUMN_MojazEmza() , moshtaryAfradModel.getMojazEmza());
        contentValues.put(MoshtaryAfradModel.COLUMN_TarafHesab() , moshtaryAfradModel.getTarafHesab());
        contentValues.put(MoshtaryAfradModel.COLUMN_FName() , moshtaryAfradModel.getFName());
        contentValues.put(MoshtaryAfradModel.COLUMN_LName() , moshtaryAfradModel.getLName());
        contentValues.put(MoshtaryAfradModel.COLUMN_ExtraProp_BirthDate() , moshtaryAfradModel.getExtraProp_BirthDate());

        return contentValues;
    }


    private ArrayList<MoshtaryAfradModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryAfradModel> moshtaryAfradModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryAfradModel moshtaryAfradModel = new MoshtaryAfradModel();

            moshtaryAfradModel.setCcMoshtaryAfrad(cursor.getInt(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_ccMoshtaryAfrad())));
            moshtaryAfradModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_ccMoshtary())));
            moshtaryAfradModel.setCcAfrad(cursor.getInt(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_ccAfrad())));
            moshtaryAfradModel.setFullNameMoshtaryAfrad(cursor.getString(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_FullNameMoshtaryAfrad())));
            moshtaryAfradModel.setMojazEmza(cursor.getInt(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_MojazEmza())) > 0);
            moshtaryAfradModel.setTarafHesab(cursor.getInt(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_TarafHesab())) > 0);
            moshtaryAfradModel.setFName(cursor.getString(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_FName())));
            moshtaryAfradModel.setLName(cursor.getString(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_LName())));
            moshtaryAfradModel.setExtraProp_BirthDate(cursor.getString(cursor.getColumnIndex(MoshtaryAfradModel.COLUMN_ExtraProp_BirthDate())));

            moshtaryAfradModels.add(moshtaryAfradModel);
            cursor.moveToNext();
        }
        return moshtaryAfradModels;
    }



}
