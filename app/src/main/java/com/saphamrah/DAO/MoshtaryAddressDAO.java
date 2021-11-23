package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvMoshtaryAddressResult;
import com.saphamrah.protos.BankGrpc;
import com.saphamrah.protos.BankReply;
import com.saphamrah.protos.BankReplyList;
import com.saphamrah.protos.BankRequest;
import com.saphamrah.protos.CustomerAddressGrpc;
import com.saphamrah.protos.CustomerAddressReply;
import com.saphamrah.protos.CustomerAddressReplyList;
import com.saphamrah.protos.CustomerAddressRequest;

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

public class MoshtaryAddressDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryAddressDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryAddressDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryAddressModel.COLUMN_ccMoshtaryAddress(),
            MoshtaryAddressModel.COLUMN_ccMoshtary(),
            MoshtaryAddressModel.COLUMN_ccAddress(),
            MoshtaryAddressModel.COLUMN_ccNoeAddress(),
            MoshtaryAddressModel.COLUMN_ccMahaleh(),
            MoshtaryAddressModel.COLUMN_NameNoeAddress(),
            MoshtaryAddressModel.COLUMN_Address(),
            MoshtaryAddressModel.COLUMN_ccShahr(),
            MoshtaryAddressModel.COLUMN_KhiabanAsli(),
            MoshtaryAddressModel.COLUMN_KhiabanFarei1(),
            MoshtaryAddressModel.COLUMN_KhiabanFarei2(),
            MoshtaryAddressModel.COLUMN_KoocheAsli(),
            MoshtaryAddressModel.COLUMN_KoocheFarei1(),
            MoshtaryAddressModel.COLUMN_KoocheFarei2(),
            MoshtaryAddressModel.COLUMN_Pelak(),
            MoshtaryAddressModel.COLUMN_Telephone(),
            MoshtaryAddressModel.COLUMN_CodePosty(),
            MoshtaryAddressModel.COLUMN_ExtraProp_InsertInPPC(),
            MoshtaryAddressModel.COLUMN_ExtraProp_IsSendToSql(),
            MoshtaryAddressModel.COLUMN_Longitude_x(),
            MoshtaryAddressModel.COLUMN_Latitude_y(),
        };
    }


    /**
     * @deprecated this is deprecated and seperate for foroshandeh and mamorpakhsh
     * @param context
     * @param activityNameForLog
     * @param ccMoshtarys
     * @param ccMarkazSazmanForosh
     * @param retrofitResponse
     */

    public void fetchAllvMoshtaryAddressGrpc(final Context context, final String activityNameForLog,final String ccMoshtarys, final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        try {


            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            serverIpModel.setPort("5000");


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerAddressGrpc.CustomerAddressBlockingStub customerAddressBlockingStub = CustomerAddressGrpc.newBlockingStub(managedChannel);
                CustomerAddressRequest customerAddressRequest = CustomerAddressRequest.newBuilder().build();
                Callable<CustomerAddressReplyList> getCustomerAddressCallable  = () -> customerAddressBlockingStub.getCustomerAddress(customerAddressRequest);
                RxAsync.makeObservable(getCustomerAddressCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(customerAddressReplyList -> {
                            ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
                            for (CustomerAddressReply customerAddressReply : customerAddressReplyList.getCustomerAddresssList()) {
                                MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();

                                moshtaryAddressModel.setCcMoshtaryAddress(customerAddressReply.getAddressCustomerID());
                                moshtaryAddressModel.setCcMoshtary(customerAddressReply.getCustomerID());
                                moshtaryAddressModel.setCcAddress(customerAddressReply.getAddressID());
                                moshtaryAddressModel.setCcNoeAddress(customerAddressReply.getAddressTypeID());
                                moshtaryAddressModel.setCcMahaleh(customerAddressReply.getDistrictID());
                                moshtaryAddressModel.setNameNoeAddress(customerAddressReply.getAddressTypeName());
                                moshtaryAddressModel.setAddress(customerAddressReply.getAddress());
                                moshtaryAddressModel.setCcShahr(customerAddressReply.getCityID());
                                moshtaryAddressModel.setKhiabanAsli(customerAddressReply.getMainStreet());
                                moshtaryAddressModel.setKhiabanFarei1(customerAddressReply.getByStreet1());
                                moshtaryAddressModel.setKhiabanFarei2(customerAddressReply.getByStreet2());
                                moshtaryAddressModel.setKoocheAsli(customerAddressReply.getMainAlley());
                                moshtaryAddressModel.setKoocheFarei1(customerAddressReply.getByAlley1());
                                moshtaryAddressModel.setKoocheFarei2(customerAddressReply.getByAlley2());
                                moshtaryAddressModel.setPelak(customerAddressReply.getPlague());
                                moshtaryAddressModel.setTelephone(customerAddressReply.getPhone());
                                moshtaryAddressModel.setCodePosty(customerAddressReply.getPostalCode());
                                moshtaryAddressModel.setLatitude_y(customerAddressReply.getLatitude());
                                moshtaryAddressModel.setLongitude_x(customerAddressReply.getLatitude());


                                moshtaryAddressModels.add(moshtaryAddressModel);
                            }

                            return moshtaryAddressModels;

                        }).subscribe(new Observer<ArrayList<MoshtaryAddressModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<MoshtaryAddressModel> moshtaryAddressModels) {
                        retrofitResponse.onSuccess(moshtaryAddressModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(),e.getMessage());
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
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }




    }

    public void fetchAllvMoshtaryAddress(final Context context, final String activityNameForLog,final String ccMoshtarys, final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddress", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvMoshtaryAddressResult> call = apiServiceGet.getAllvMoshtaryAddress(ccMoshtarys , ccMarkazSazmanForosh);
            call.enqueue(new Callback<GetAllvMoshtaryAddressResult>() {
                @Override
                public void onResponse(Call<GetAllvMoshtaryAddressResult> call, Response<GetAllvMoshtaryAddressResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryAddressDAO.class.getSimpleName(), "", "fetchAllvMoshtaryAddress", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvMoshtaryAddressResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddress", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddress", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddress", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddress", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvMoshtaryAddressResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddress", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    /**
     * @deprecated this is deprecated and seperate for foroshandeh and mamorpakhsh
     * @param context
     * @param activityNameForLog
     * @param ccMoshtarys
     * @param ccMarkazSazmanForosh
     * @param retrofitResponse
     */
    public void fetchAllvMoshtaryAddressForPakhsh(final Context context, final String activityNameForLog,final String ccMoshtarys, final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressForPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvMoshtaryAddressResult> call = apiServiceGet.getAllvMoshtaryAddressForPakhsh(ccMoshtarys , ccMarkazSazmanForosh);
            call.enqueue(new Callback<GetAllvMoshtaryAddressResult>() {
                @Override
                public void onResponse(Call<GetAllvMoshtaryAddressResult> call, Response<GetAllvMoshtaryAddressResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryAddressDAO.class.getSimpleName(), "", "fetchAllvMoshtaryAddressForPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvMoshtaryAddressResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressForPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressForPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressForPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressForPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvMoshtaryAddressResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressForPakhsh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }




    public void fetchAllvMoshtaryAddressByNoeMasouliat(final Context context, final String activityNameForLog,final String ccForoshandeh, final String ccMasirs, final String ccMoshtarys, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressByNoeMasouliat", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvMoshtaryAddressResult> call = apiServiceGet.getMoshtaryAddressByNoeMasouliat(ccForoshandeh, ccMasirs, ccMoshtarys);
            call.enqueue(new Callback<GetAllvMoshtaryAddressResult>() {
                @Override
                public void onResponse(Call<GetAllvMoshtaryAddressResult> call, Response<GetAllvMoshtaryAddressResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryAddressDAO.class.getSimpleName(), "", "fetchAllvMoshtaryAddressByNoeMasouliat", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvMoshtaryAddressResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressByNoeMasouliat", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressByNoeMasouliat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressByNoeMasouliat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressByNoeMasouliat", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvMoshtaryAddressResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryAddressDAO.class.getSimpleName(), activityNameForLog, "fetchAllvMoshtaryAddressByNoeMasouliat", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public boolean insertGroup(ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryAddressModel moshtaryAddressModel : moshtaryAddressModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryAddressModel);
                db.insertOrThrow(MoshtaryAddressModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MoshtaryAddressModel> getAll()
    {
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryAddressModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAddressModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "getAll" , "");
        }
        return moshtaryAddressModels;
    }


    public MoshtaryAddressModel getAddressByccMoshtaryAndType(int ccMoshtary)
    {
        MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();
        String query = "select * from " + MoshtaryAddressModel.TableName() +
                " where " + MoshtaryAddressModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and " +
                MoshtaryAddressModel.COLUMN_ccNoeAddress() + " in (1,2)" + " order by " + MoshtaryAddressModel.COLUMN_ccNoeAddress() + " desc";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAddressModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "getAddressByccMoshtaryAndType" , "");
        }
        return  moshtaryAddressModel;
    }



    public MoshtaryAddressModel getByccMoshtaryAndccAddress(int ccMoshtary , int ccAddress)
    {
        MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();
        String query = "select * from " + MoshtaryAddressModel.TableName() +
                " where " + MoshtaryAddressModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and " +
                MoshtaryAddressModel.COLUMN_ccAddress() + " = " + ccAddress;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAddressModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "getByccMoshtaryAndccAddress" , "");
        }
        return  moshtaryAddressModel;
    }

    public int getMoshtaryAddressByccMoshtaryAndccAddress(int ccMoshtary , int ccAddress)
    {
        int ccMoshtaryAddress = -1;
        String query = "select " + MoshtaryAddressModel.COLUMN_ccMoshtaryAddress() + " from " + MoshtaryAddressModel.TableName() +
                " where " + MoshtaryAddressModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and " +
                MoshtaryAddressModel.COLUMN_ccAddress() + " = " + ccAddress;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccMoshtaryAddress = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryAddressDAO" , "" , "getMoshtaryAddressByccMoshtaryAndccAddress" , "");
        }
        return  ccMoshtaryAddress;
    }
	
    public ArrayList<MoshtaryAddressModel> getByccMoshtaryAndccNoeAddress(int ccMoshtary , String ccNoeAddress)
    {
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryAddressModel.TableName(), allColumns(), MoshtaryAddressModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and " + MoshtaryAddressModel.COLUMN_ccNoeAddress() + " in (" + ccNoeAddress + ")", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAddressModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "getByccMoshtary" , "");
        }
        return moshtaryAddressModels;
    }

    public ArrayList<MoshtaryAddressModel> getByccMoshtary(int ccMoshtary)
    {
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryAddressModel.TableName(), allColumns(), MoshtaryAddressModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAddressModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "getByccMoshtary" , "");
        }
        return moshtaryAddressModels;
    }

    public MoshtaryAddressModel getTopOneAddress(int ccMoshtary)
    {
        MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();
        try
        {
            String query = "SELECT * FROM MoshtaryAddress "
                    + " WHERE ccMoshtary = " + ccMoshtary
                    + " ORDER BY ccNoeAddress "
                    + " Limit 1 ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAddressModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "getTopOneAddress" , "");
        }
        return moshtaryAddressModel;
    }

    public boolean updateTelephonePostalCode(int ccAddress , int ccMoshtary , String telephone , String postalCode)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoshtaryAddressModel.COLUMN_Telephone() , telephone);
            contentValues.put(MoshtaryAddressModel.COLUMN_CodePosty() , postalCode);
            db.update(MoshtaryAddressModel.TableName() , contentValues , MoshtaryAddressModel.COLUMN_ccAddress() + " = " + ccAddress + " and " + MoshtaryAddressModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "updateTelephonePostalCode" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAddressModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAddressModel.TableName(), MoshtaryAddressModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtaries(String ccMoshtaries)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAddressModel.TableName(), MoshtaryAddressModel.COLUMN_ccMoshtary() + " in (" + ccMoshtaries+ ")" , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "deleteByccMoshtaries" , "");
            return false;
        }
    }

    public boolean updateccAddressAndSendToSQL(int ccMoshtaryAddress , int newccAddress)
    {
        try
        {
            String query = "update " + MoshtaryAddressModel.TableName() + " set " + MoshtaryAddressModel.COLUMN_ccAddress() + " = " + newccAddress + " , " + MoshtaryAddressModel.COLUMN_ExtraProp_IsSendToSql() + " = 1 " +
                    " where " + MoshtaryAddressModel.COLUMN_ccMoshtaryAddress() + " = " + ccMoshtaryAddress;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "updateccAddress" , "");
            return false;
        }
    }


    public boolean updateMoshtaryJadid(int ccMoshtaryOld, int ccMoshtaryNew, int ccAddressNew, int ccNoeAddress)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MoshtaryAddressModel.COLUMN_ccMoshtary(), ccMoshtaryNew);
            values.put(MoshtaryAddressModel.COLUMN_ccAddress(), ccAddressNew);
            values.put(MoshtaryAddressModel.COLUMN_ExtraProp_IsSendToSql(), 1);
            db.update(MoshtaryAddressModel.TableName(), values, "ccMoshtary= ? and ccNoeAddress=?", new String[]{String.valueOf(ccMoshtaryOld), String.valueOf(ccNoeAddress)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryAddressModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAddressDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryAddressModel moshtaryAddressModel)
    {
        ContentValues contentValues = new ContentValues();

        if (moshtaryAddressModel.getCcMoshtaryAddress() > 0)
        {
            contentValues.put(MoshtaryAddressModel.COLUMN_ccMoshtaryAddress() , moshtaryAddressModel.getCcMoshtaryAddress());
        }
        contentValues.put(MoshtaryAddressModel.COLUMN_ccMoshtary() , moshtaryAddressModel.getCcMoshtary());
        contentValues.put(MoshtaryAddressModel.COLUMN_ccAddress() , moshtaryAddressModel.getCcAddress());
        contentValues.put(MoshtaryAddressModel.COLUMN_ccNoeAddress() , moshtaryAddressModel.getCcNoeAddress());
        contentValues.put(MoshtaryAddressModel.COLUMN_ccMahaleh() , moshtaryAddressModel.getCcMahaleh());
        contentValues.put(MoshtaryAddressModel.COLUMN_NameNoeAddress() , moshtaryAddressModel.getNameNoeAddress());
        contentValues.put(MoshtaryAddressModel.COLUMN_Address() , moshtaryAddressModel.getAddress());
        contentValues.put(MoshtaryAddressModel.COLUMN_ccShahr() , moshtaryAddressModel.getCcShahr());
        contentValues.put(MoshtaryAddressModel.COLUMN_KhiabanAsli() , moshtaryAddressModel.getKhiabanAsli());
        contentValues.put(MoshtaryAddressModel.COLUMN_KhiabanFarei1() , moshtaryAddressModel.getKhiabanFarei1());
        contentValues.put(MoshtaryAddressModel.COLUMN_KhiabanFarei2() , moshtaryAddressModel.getKhiabanFarei2());
        contentValues.put(MoshtaryAddressModel.COLUMN_KoocheAsli() , moshtaryAddressModel.getKoocheAsli());
        contentValues.put(MoshtaryAddressModel.COLUMN_KoocheFarei1() , moshtaryAddressModel.getKoocheFarei1());
        contentValues.put(MoshtaryAddressModel.COLUMN_KoocheFarei2() , moshtaryAddressModel.getKoocheFarei2());
        contentValues.put(MoshtaryAddressModel.COLUMN_Pelak() , moshtaryAddressModel.getPelak());
        contentValues.put(MoshtaryAddressModel.COLUMN_Telephone() , moshtaryAddressModel.getTelephone());
        contentValues.put(MoshtaryAddressModel.COLUMN_CodePosty() , moshtaryAddressModel.getCodePosty());
        contentValues.put(MoshtaryAddressModel.COLUMN_ExtraProp_InsertInPPC() , moshtaryAddressModel.getExtraProp_InsertInPPC());
        contentValues.put(MoshtaryAddressModel.COLUMN_ExtraProp_IsSendToSql() , moshtaryAddressModel.getExtraProp_IsSendToSql());
        contentValues.put(MoshtaryAddressModel.COLUMN_Longitude_x() , moshtaryAddressModel.getLongitude_x());
        contentValues.put(MoshtaryAddressModel.COLUMN_Latitude_y() , moshtaryAddressModel.getLatitude_y());

        return contentValues;
    }


    private ArrayList<MoshtaryAddressModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();

            moshtaryAddressModel.setCcMoshtaryAddress(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccMoshtaryAddress())));
            moshtaryAddressModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccMoshtary())));
            moshtaryAddressModel.setCcAddress(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccAddress())));
            moshtaryAddressModel.setCcNoeAddress(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccNoeAddress())));
            moshtaryAddressModel.setCcMahaleh(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccMahaleh())));
            moshtaryAddressModel.setNameNoeAddress(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_NameNoeAddress())));
            moshtaryAddressModel.setAddress(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Address())));
            moshtaryAddressModel.setCcShahr(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccShahr())));
            moshtaryAddressModel.setKhiabanAsli(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KhiabanAsli())));
            moshtaryAddressModel.setKhiabanFarei1(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KhiabanFarei1())));
            moshtaryAddressModel.setKhiabanFarei2(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KhiabanFarei2())));
            moshtaryAddressModel.setKoocheAsli(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KoocheAsli())));
            moshtaryAddressModel.setKoocheFarei1(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KoocheFarei1())));
            moshtaryAddressModel.setKoocheFarei2(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KoocheFarei2())));
            moshtaryAddressModel.setPelak(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Pelak())));
            moshtaryAddressModel.setTelephone(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Telephone())));
            moshtaryAddressModel.setCodePosty(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_CodePosty())));
            moshtaryAddressModel.setExtraProp_InsertInPPC(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ExtraProp_InsertInPPC())));
            moshtaryAddressModel.setExtraProp_IsSendToSql(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ExtraProp_IsSendToSql())));
            moshtaryAddressModel.setLongitude_x(cursor.getDouble(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Longitude_x())));
            moshtaryAddressModel.setLatitude_y(cursor.getDouble(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Latitude_y())));

            moshtaryAddressModels.add(moshtaryAddressModel);
            cursor.moveToNext();
        }
        return moshtaryAddressModels;
    }
    
    
}
