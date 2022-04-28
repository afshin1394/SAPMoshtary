package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.DarkhastFaktorSatrTakhfifModel;
import com.saphamrah.Model.ForoshandehMoshtaryModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryParentModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryByccMasirResult;
import com.saphamrah.WebService.ServiceResponse.GetMoshtaryPakhshResult;
import com.saphamrah.WebService.ServiceResponse.GetMoshtarysFirstParentPPCResult;
import com.saphamrah.protos.CustomerByRouteIDGrpc;
import com.saphamrah.protos.CustomerByRouteIDReply;
import com.saphamrah.protos.CustomerByRouteIDReplyList;
import com.saphamrah.protos.CustomerByRouteIDRequest;

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

public class MoshtaryDAO
{

    private DBHelper dbHelper;
    private Context context;

    private final int INSERT_OPERATION = 1;


    public MoshtaryDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryModel.COLUMN_ccMoshtary(),
			MoshtaryModel.COLUMN_ccAfrad(),										   
            MoshtaryModel.COLUMN_NameMoshtary(),
            MoshtaryModel.COLUMN_NameTablo(),
            MoshtaryModel.COLUMN_Olaviat(),
            MoshtaryModel.COLUMN_ModateVosol(),
            MoshtaryModel.COLUMN_CodeMoshtary(),
            MoshtaryModel.COLUMN_Address(),
			MoshtaryModel.COLUMN_Mobile(),										  
            MoshtaryModel.COLUMN_CodeNoeVosolAzMoshtary(),
            MoshtaryModel.COLUMN_ccForoshandeh(),
			MoshtaryModel.COLUMN_ccMasir(),
            MoshtaryModel.COLUMN_ToorVisit(),
            MoshtaryModel.COLUMN_CodeNoeHaml(),
            MoshtaryModel.COLUMN_FNameMoshtary(),
            MoshtaryModel.COLUMN_LNameMoshtary(),
            MoshtaryModel.COLUMN_Darajeh(),
            MoshtaryModel.COLUMN_NameDarajeh(),
            MoshtaryModel.COLUMN_CodeMely(),
            MoshtaryModel.COLUMN_CodeNoeShakhsiat(),
			MoshtaryModel.COLUMN_ccNoeSenf(),
            MoshtaryModel.COLUMN_ccNoeMoshtary(),
            MoshtaryModel.COLUMN_ShenasehMely(),
            MoshtaryModel.COLUMN_CodeVazeiat(),
            MoshtaryModel.COLUMN_MasahatMaghazeh(),
            MoshtaryModel.COLUMN_HasAnbar(),
            MoshtaryModel.COLUMN_ExtraProp_IsOld(),
            MoshtaryModel.COLUMN_ccMoshtaryParent(),
            MoshtaryModel.COLUMN_ExtraProp_IsMoshtaryAmargar(),
            MoshtaryModel.COLUMN_ExtraProp_ccPorseshnameh(),
            MoshtaryModel.COLUMN_ExtraProp_NoeForoshandeh_First(),
            MoshtaryModel.COLUMN_ExtraProp_MoshtaryMojazKharejAzMasir(),
            MoshtaryModel.COLUMN_ExtraProp_Olaviat(),
            MoshtaryModel.COLUMN_DateOfMasir(),
            MoshtaryModel.COLUMN_ControlEtebarForoshandeh(),
            MoshtaryModel.COLUMN_ModateNaghd(),
            MoshtaryModel.COLUMN_TarikhMoarefiMoshtary(),
            MoshtaryModel.COLUMN_KharejAzMahal(),
            MoshtaryModel.COLUMN_CodeEghtesady()

        };
    }

    public void fetchAllMoshtaryByccMasirGrpc(final Context context, final String activityNameForLog, final String ccForoshandeh, final String ccMasirs, final String codeMoshtary, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryByccMasirGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerByRouteIDGrpc.CustomerByRouteIDBlockingStub customerByRouteIDBlockingStub = CustomerByRouteIDGrpc.newBlockingStub(managedChannel);
                CustomerByRouteIDRequest customerByRouteIDRequest = CustomerByRouteIDRequest.newBuilder().setCustomerCode(codeMoshtary).setRouteID(ccMasirs).setSellerID(ccForoshandeh).build();
                Callable<CustomerByRouteIDReplyList> customerByRouteIDReplyListCallable = () -> customerByRouteIDBlockingStub.getCustomerByRouteID(customerByRouteIDRequest);
                RxAsync.makeObservable(customerByRouteIDReplyListCallable)
                        .map(customerByRouteIDReplyList ->  {
                            ArrayList<MoshtaryModel> models = new ArrayList<>();
                            for (CustomerByRouteIDReply reply : customerByRouteIDReplyList.getCustomerByRouteIDsList()) {
                                MoshtaryModel model = new MoshtaryModel();

                                model.setCcMoshtary(reply.getCustomerID());
                                model.setCcAfrad(reply.getPersonalID());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setNameTablo(reply.getSignsName());
                                model.setOlaviat(reply.getPriority());
                                model.setModateVosol(reply.getRecieptionDuration());
                                model.setCodeMoshtary(reply.getCustomerCode());

                                model.setMobile(reply.getMobile());
                                model.setCodeNoeVosolAzMoshtary(reply.getCustomerAllocationCodeType());
                                model.setCcForoshandeh(reply.getSaleManID());
                                model.setCcMasir(reply.getRouteID());
                                model.setToorVisit(reply.getVisitTour());
                                model.setCodeNoeHaml(reply.getCarryingCodeType());
                                model.setFNameMoshtary(reply.getFullCustomerName());
                                model.setLNameMoshtary(reply.getLastCustomerName());
                                model.setDarajeh(reply.getDegree());
                                model.setNameDarajeh(reply.getDegreeName());
                                model.setCodeMely(reply.getNationalCode());
                                model.setCodeNoeShakhsiat(reply.getPersonalityTypeCode());
                                model.setCcNoeMoshtary(reply.getCustomerTypeID());
                                model.setCcNoeSenf(reply.getGuildTypeID());
                                model.setShenasehMely(reply.getNationalID());
                                model.setCodeVazeiat(reply.getSituationCode());
                                model.setMasahatMaghazeh(reply.getShopArea());
                                model.setHasAnbar(reply.getHasStore());
                                model.setccMoshtaryParent(reply.getParentCustomerID());

                                model.setControlEtebarForoshandeh(reply.getSaleManCrediteConterol());
                                model.setModateNaghd(reply.getCashDuration());
                                model.setTarikhMoarefiMoshtary(reply.getCustomerIntroductionDate());
                                model.setKharejAzMahal(reply.getOutOfRange());


                                models.add(model);

                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MoshtaryModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MoshtaryModel> moshtaryModels) {
                                retrofitResponse.onSuccess(moshtaryModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
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
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryByccMasirGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }


    public void fetchAllMoshtaryByccMasir(final Context context, final String activityNameForLog, final String ccForoshandeh, final String ccMasirs, final String codeMoshtary, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryByccMasir", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtaryByccMasirResult> call = apiServiceGet.getAllMoshtaryByccMasir(ccForoshandeh , ccMasirs , codeMoshtary); //codeMoshtary = -1
            call.enqueue(new Callback<GetAllMoshtaryByccMasirResult>() {
                @Override
                public void onResponse(Call<GetAllMoshtaryByccMasirResult> call, Response<GetAllMoshtaryByccMasirResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryDAO.class.getSimpleName(), "", "fetchAllMoshtaryByccMasir", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllMoshtaryByccMasirResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryByccMasir", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryByccMasir", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryByccMasir", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryByccMasir", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtaryByccMasirResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryByccMasir", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    public void fetchMoshtaryPakhsh(final Context context, final String activityNameForLog, final String ccMoshtaryPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetMoshtaryPakhshResult> call = apiServiceGet.getMoshtaryPakhsh(ccMoshtaryPakhsh);
            call.enqueue(new Callback<GetMoshtaryPakhshResult>() {
                @Override
                public void onResponse(Call<GetMoshtaryPakhshResult> call, Response<GetMoshtaryPakhshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryDAO.class.getSimpleName(), "", "fetchMoshtaryPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMoshtaryPakhshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMoshtaryPakhshResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryPakhsh", "onFailure");
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


    public boolean insertGroup(ArrayList<MoshtaryModel> moshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MoshtaryModel moshtaryModel = null;
        try
        {
            if (moshtaryModels.size() > 0)
            {
                db.beginTransaction();
                for (int i=0 ; i<moshtaryModels.size() ; i++)
                {
                    moshtaryModel = moshtaryModels.get(i);
                    moshtaryModel.setExtraProp_IsOld(1);
                    ContentValues contentValues = modelToContentvalue(moshtaryModel , INSERT_OPERATION);
                    db.insertOrThrow(MoshtaryModel.TableName() , null , contentValues);
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
            }
            return true;
        }
        catch (Exception exception)
        {
            String codeMoshtary = "";
            if (moshtaryModel != null)
            {
                codeMoshtary = moshtaryModel.getCodeMoshtary();
            }
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryModel.TableName()) + "\n" + exception.toString() + " * CodeMoshtary : " + codeMoshtary + "*";
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insertGroup(MoshtaryModel moshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try
        {
                db.beginTransaction();

                    moshtaryModels.setExtraProp_IsOld(1);
                    ContentValues contentValues = modelToContentvalue(moshtaryModels , INSERT_OPERATION);
                    db.insertOrThrow(MoshtaryModel.TableName() , null , contentValues);

                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            return true;
        }
        catch (Exception exception)
        {
            String codeMoshtary = "";
            if (moshtaryModels != null)
            {
                codeMoshtary = moshtaryModels.getCodeMoshtary();
            }
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryModel.TableName()) + "\n" + exception.toString() + " * CodeMoshtary : " + codeMoshtary + "*";
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public long insert(MoshtaryModel moshtaryModel)
    {
        long insertId = 0;
        ContentValues contentValues = modelToContentvalue(moshtaryModel , 1);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            insertId = db.insertOrThrow(MoshtaryModel.TableName() , null , contentValues);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "insert" , "");
        }
        return insertId;
    }


    public boolean updateOlaviatFromForoshandehMoshtary()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "update Moshtary set Olaviat = (select Olaviat from ForoshandehMoshtary where ccMoshtary = Moshtary.ccMoshtary)";
            db.execSQL(query);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "updateOlaviatFromForoshandehMoshtary" , "");
            return false;
        }
    }

    public boolean updateExtraOlaviatFromOlaviat()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "select Olaviat , ccMoshtary, NameMoshtary from Moshtary order by Olaviat,ccMoshtary";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        String updateQuery = "update Moshtary set ExtraProp_Olaviat = " + (cursor.getPosition()+1) + " where ccMoshtary = " + cursor.getInt(1);
                        Log.d("moshtary", "updateQuery" + updateQuery +" , nameMoshtary="+ cursor.getString(2));
                        db.execSQL(updateQuery);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
            Log.d("moshtary" , "end : " + System.currentTimeMillis());
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "updateExtraOlaviatFromOlaviat" , "");
            return false;
        }
    }
	
    public boolean updateToorVisitMoshtary()
    {
        String query =
                "UPDATE " + MoshtaryModel.TableName() +
                        " SET "  +   MoshtaryModel.COLUMN_ToorVisit() + " = (Select " + MasirModel.COLUMN_ToorVisit() +
                        " From " + MasirModel.TableName() +
                        " Where " +  MasirModel.COLUMN_ccMasir() + " = (Select " + ForoshandehMoshtaryModel.COLUMN_ccMasir() +
                        " From " + ForoshandehMoshtaryModel.TableName() +
                        " Where " + ForoshandehMoshtaryModel.COLUMN_ccMoshtary() + " = " + MoshtaryModel.TableName() + "." + MoshtaryModel.COLUMN_ccMoshtary() + "))";
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
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "UpdateToorVisitMoshtary.query = " + query , "");
            return false;
        }
    }

    public boolean updateMobile(int ccMoshtary , String mobile)
    {
        String query = "update " + MoshtaryModel.TableName() + " set " + MoshtaryModel.COLUMN_Mobile() + " = '" + mobile + "' where " + MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
        try
        {
            //Log.d("update" , "query : " + query);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "updateMobile" , "");
            return false;
        }
    }
	
	    public boolean updateNoeFaaliat(int ccMoshtary , int ccNoeFaaliat)
    {
        String query = "update " + MoshtaryModel.TableName() + " set " + MoshtaryModel.COLUMN_ccNoeMoshtary() + " = '" + ccNoeFaaliat + "' where " + MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
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
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "updateNoeFaaliat" , "");
            return false;
        }
    }

    public boolean updateNoeSenf(int ccMoshtary , int ccNoeSenf)
    {
        String query = "update " + MoshtaryModel.TableName() + " set " + MoshtaryModel.COLUMN_ccNoeSenf() + " = '" + ccNoeSenf + "' where " + MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
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
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "updateNoeSenf" , "");
            return false;
        }
    }

    public boolean updateHasAnbarAndMasahateMaghaze(int ccMoshtary , int hasAnbar , int masahateMaghaze)
    {
        String query = "update " + MoshtaryModel.TableName() + " set " + MoshtaryModel.COLUMN_HasAnbar() + " = " + hasAnbar + " , " + MoshtaryModel.COLUMN_MasahatMaghazeh() + " = " + masahateMaghaze + " where " + MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
        try
        {
            //Log.d("update" , "query : " + query);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "updateMobile" , "");
            return false;
        }
    }

    @SuppressLint("LongLogTag")
    public boolean updateccMoshtaryParentInMoshtary(ArrayList<MoshtaryParentModel> moshtaryParentModels) {


        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (MoshtaryParentModel moshtaryParentModel : moshtaryParentModels) {
                int moshtaryParent = ((int) moshtaryParentModel.getCcMoshtaryParent());
                int moshtary = ((int) moshtaryParentModel.getCcMoshtary());
                int noeForoshandehFirst = ((int) moshtaryParentModel.getNoeForoshandeh_First());
                String query = "UPDATE Moshtary SET ccMoshtaryParent = '" + moshtaryParent + "', ExtraProp_NoeForoshandeh_First = '" + noeForoshandehFirst + "' WHERE ccMoshtary = '" + moshtary + "'";
                db.execSQL(query);
                //db.endTransaction();
            }

            //db.setTransactionSuccessful();
            //db.endTransaction();
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate, MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO", "", "updateccmoshtaryParent", "");
            return false;
        }
    }


    public boolean updateNatCodeMasahatAnbar(int ccMoshtary , String nationalCode , String shenaseMely , int masahateMaghaze , int codeAnbar)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MoshtaryModel.COLUMN_CodeMely(), nationalCode);
            values.put(MoshtaryModel.COLUMN_ShenasehMely(), shenaseMely);
            values.put(MoshtaryModel.COLUMN_MasahatMaghazeh(), masahateMaghaze);
            values.put(MoshtaryModel.COLUMN_HasAnbar(), codeAnbar);
            db.update(MoshtaryModel.TableName(), values, "ccMoshtary= ?", new String[]{String.valueOf(ccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "updateNatCodeMasahatAnbarSaateVisit" , "");
            return false;
        }
    }

    public boolean updateMoshtaryJadid(int newccMoshtary , int oldccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MoshtaryModel.COLUMN_ccMoshtary(), newccMoshtary);
            values.put(MoshtaryModel.COLUMN_ExtraProp_IsOld(), 1);
            db.update(MoshtaryModel.TableName(), values, "ccMoshtary= ?", new String[]{String.valueOf(oldccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    public ArrayList<MoshtaryModel> getAll()
    {
        ArrayList<MoshtaryModel> moshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryModel.TableName(), allColumns(), null, null, null, null, "DateOfMasir, Olaviat");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "getAll" , "");
        }
        return moshtaryModels;
    }

    public ArrayList<MoshtaryModel> getNewCustomers()
    {
        ArrayList<MoshtaryModel> moshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryModel.TableName() , allColumns(), MoshtaryModel.COLUMN_CodeMoshtary() + " = '00000'", null, null, null, MoshtaryModel.COLUMN_ExtraProp_IsOld());
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "getNewCustomers" , "");
        }
        return moshtaryModels;
    }

    public MoshtaryModel getByccMoshtary(int ccMoshtary)
    {
        MoshtaryModel moshtaryModel = new MoshtaryModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM Moshtary WHERE ccMoshtary=" + ccMoshtary;
            //Cursor cursor = db.query(MoshtaryModel.TableName(), allColumns(),  "ccMoshtary= ?", new String[]{String.valueOf(ccMoshtary)}, null, null,  null);
            Log.d("MoshtaryDAO","getByccMoshtary ccMoshtary= " + ccMoshtary);
            Log.d("MoshtaryDAO","getByccMoshtary query= " + query);
            Cursor cursor = db.rawQuery(query, null);
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
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "getByccMoshtary" , "");
        }
        return moshtaryModel;
    }

    public boolean isMoshtaryZanjiree(int ccMoshtary) {
        boolean isZanjiree = true;
        String query = "SELECT * FROm Moshtary WHERE ccMoshtary = " + ccMoshtary + " AND ccNoeMoshtary = 350";
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    isZanjiree = true;
                } else {
                    isZanjiree = false;
                }
                cursor.close();
            } else {
                isZanjiree = false;

            }

            assert cursor != null;
            cursor.close();
        } catch (Exception exception) {
            isZanjiree = false;
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryGharardadDAO", "", "isMoshtaryZanjiree", "");

        }

        return isZanjiree;
    }


    public int countCodeMoshtary(String codeMoshtary)
    {
        int count = 0;
        try
        {
            String query = "select count(" + MoshtaryModel.COLUMN_CodeMoshtary() + ") from " + MoshtaryModel.TableName() + " where CodeMoshtary = '"+ codeMoshtary + "'";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "countCodeMoshtary" , "");
        }
        return count;
    }
    public String getAllccNoeMoshtaryByccMoshtary(int ccMoshtary)
    {
        String ccNoeMoshtary = "";
        try
        {
            String query = "select distinct ccNoeMoshtary as ccGorohs from Moshtary where " + MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccNoeMoshtary = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "getByccMoshtary" , "");
        }
        if (ccNoeMoshtary == null)
        {
            ccNoeMoshtary = "-1";
        }
        return ccNoeMoshtary;
    }


    public String getAllccNoeSenf()
    {
        String ccGorohs = "";
        try
        {
            String query = "select group_concat(distinct ccNoeSenf) || ',' || group_concat(distinct ccNoeMoshtary) as ccGorohs from Moshtary";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccGorohs = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "getByccMoshtary" , "");
        }
        if (ccGorohs == null)
        {
            ccGorohs = "-1,347,348";
        }
        return ccGorohs;
    }
	
    public ArrayList<MoshtaryModel> getMoshtaryMasir()
    {
        ArrayList<MoshtaryModel> moshtaryModels = new ArrayList<>();
        String query = " SELECT * FROM( SELECT * FROM Moshtary "
                + " WHERE (ccMoshtary IN(SELECT ccMoshtary FROM ForoshandehMoshtary)  AND ExtraProp_IsOld = 1) "
                + " OR ExtraProp_IsMoshtaryAmargar = 1 "
                + " UNION ALL "
                + " SELECT * FROM Moshtary WHERE ExtraProp_MoshtaryMojazKharejAzMasir = 1 " + " )A" + " ORDER BY Olaviat";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "getMoshtaryMasir" , "");
        }
        return moshtaryModels;
    }


    public int getDarajehByccMoshtary(int ccMoshtary)
    {
        int darajeh = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryModel.TableName(), new String[]{MoshtaryModel.COLUMN_Darajeh()},  "ccMoshtary= ?", new String[]{String.valueOf(ccMoshtary)}, null, null,  null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    darajeh = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "getDarajehByccMoshtary" , "");
        }
        return darajeh;
    }

    public int getExtraOlaviatByccMoshtary(int ccMoshtary)
    {
        int olaviat = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryModel.TableName(), new String[]{MoshtaryModel.COLUMN_Olaviat()},  "ccMoshtary= ?", new String[]{String.valueOf(ccMoshtary)}, null, null,  null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    olaviat = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "getExtraOlaviatByccMoshtary" , "");
        }
        return olaviat;
    }

    public int getccAfradByccMoshtary(int ccMoshtary)
    {
        int olaviat = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryModel.TableName(), new String[]{MoshtaryModel.COLUMN_ccAfrad()},  "ccMoshtary= ?", new String[]{String.valueOf(ccMoshtary)}, null, null,  null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    olaviat = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "getccAfradByccMoshtary" , "");
        }
        return olaviat;
    }

    public int getToorVisit(int ccMoshtary)
    {
        int toorVisit = -1;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryModel.TableName(), new String[]{MoshtaryModel.COLUMN_ToorVisit()}, MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    toorVisit = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "getToorVisit" , "");
        }
        return toorVisit;
    }

    public int getCcMasirByCcMoshtary(int ccMoshtary){
        int ccMasir = -1;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select "+MoshtaryModel.COLUMN_ccMasir() + " from "+MoshtaryModel.TableName()+"where"+MoshtaryModel.COLUMN_ccMoshtary()+" = "+ccMoshtary,null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    ccMasir = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO", "", "getToorVisit", "");
        }
        return ccMasir;
    }

    public int getCcMasirByCcForoshandeh(int ccForoshandeh){
        int ccMasir = -1;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select "+MoshtaryModel.COLUMN_ccMasir() + " from "+MoshtaryModel.TableName()+"where"+MoshtaryModel.COLUMN_ccForoshandeh()+" = "+ccForoshandeh,null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    ccMasir = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO", "", "getCcMasirByCcForoshandeh", "");
        }
        return ccMasir;
    }

    public int getCcMasirByCodeMoshtary(int codeMoshtary){
        int ccMasir = -1;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select "+MoshtaryModel.COLUMN_ccMasir() + " from "+MoshtaryModel.TableName()+"where"+MoshtaryModel.COLUMN_CodeMoshtary()+" = "+codeMoshtary,null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    ccMasir = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO", "", "getCcMasirByCodeMoshtary", "");
        }
        return ccMasir;
    }

    public ArrayList<MoshtaryModel> getMoshtaryZanjiree(int zangireiParam){
        ArrayList<MoshtaryModel> models = new ArrayList<>();
        String query = "SELECT * FROM Moshtary WHERE ccNoeMoshtary ="+zangireiParam;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception){
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "MoshtaryDAO" , "" , "getMoshtaryZanjiree" , "");

        }
       return models;

    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryModel.TableName() , MoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtarys(String ccMoshtarys)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryModel.TableName() , MoshtaryModel.COLUMN_ccMoshtary() + " in ( " + ccMoshtarys + " )" , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    public boolean deleteByCodeMoshtarys(String codeMoshtarys)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryModel.TableName() , MoshtaryModel.COLUMN_CodeMoshtary() + " in ( " + codeMoshtarys + " )" , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "deleteByCodeMoshtarys" , "");
            return false;
        }
    }

    public boolean deleteByCodeMoshtarysAndccForoshandeh(String codeMoshtarys , int ccforoshandeh)
    {
        String query = "DELETE FROM MOSHTARY WHERE CodeMoshtary = " + codeMoshtarys + " AND ccForoshandeh = " + ccforoshandeh;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.rawQuery(query , null);
//            db.delete(MoshtaryModel.TableName() , MoshtaryModel.COLUMN_CodeMoshtary() + " in ( " + codeMoshtarys + " )" + "AND" , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryDAO" , "" , "deleteByCodeMoshtarys" , "");
            return false;
        }
    }

    private ContentValues modelToContentvalue(MoshtaryModel moshtaryModel , int operationType) //operationType -> insert = 1
    {
        ContentValues contentValues = new ContentValues();

        if (operationType == INSERT_OPERATION)
        {
            if (moshtaryModel.getCcMoshtary() > 0)
            {
                contentValues.put(MoshtaryModel.COLUMN_ccMoshtary() , moshtaryModel.getCcMoshtary());
            }
        }
        else
        {
            contentValues.put(MoshtaryModel.COLUMN_ccMoshtary() , moshtaryModel.getCcMoshtary());
        }
		contentValues.put(MoshtaryModel.COLUMN_ccAfrad() , moshtaryModel.getCcAfrad());																			   
        contentValues.put(MoshtaryModel.COLUMN_NameMoshtary() , moshtaryModel.getNameMoshtary());
        contentValues.put(MoshtaryModel.COLUMN_NameTablo() , moshtaryModel.getNameTablo());
        contentValues.put(MoshtaryModel.COLUMN_Olaviat() , moshtaryModel.getOlaviat());
        contentValues.put(MoshtaryModel.COLUMN_ModateVosol() , moshtaryModel.getModateVosol());
        contentValues.put(MoshtaryModel.COLUMN_CodeMoshtary() , moshtaryModel.getCodeMoshtary());
        contentValues.put(MoshtaryModel.COLUMN_Address() , moshtaryModel.getAddress());
		contentValues.put(MoshtaryModel.COLUMN_Mobile() , moshtaryModel.getMobile());																					 
        contentValues.put(MoshtaryModel.COLUMN_CodeNoeVosolAzMoshtary() , moshtaryModel.getCodeNoeVosolAzMoshtary());
        contentValues.put(MoshtaryModel.COLUMN_ccForoshandeh() , moshtaryModel.getccForoshandeh());
		contentValues.put(MoshtaryModel.COLUMN_ccMasir() , moshtaryModel.getCcMasir());																						
        contentValues.put(MoshtaryModel.COLUMN_ToorVisit() , moshtaryModel.getToorVisit());
        contentValues.put(MoshtaryModel.COLUMN_CodeNoeHaml() , moshtaryModel.getCodeNoeHaml());
        contentValues.put(MoshtaryModel.COLUMN_FNameMoshtary() , moshtaryModel.getFNameMoshtary());
        contentValues.put(MoshtaryModel.COLUMN_LNameMoshtary() , moshtaryModel.getLNameMoshtary());
        contentValues.put(MoshtaryModel.COLUMN_Darajeh() , moshtaryModel.getDarajeh());
        contentValues.put(MoshtaryModel.COLUMN_NameDarajeh() , moshtaryModel.getNameDarajeh());
        contentValues.put(MoshtaryModel.COLUMN_CodeMely() , moshtaryModel.getCodeMely());
        contentValues.put(MoshtaryModel.COLUMN_CodeNoeShakhsiat() , moshtaryModel.getCodeNoeShakhsiat());
		contentValues.put(MoshtaryModel.COLUMN_ccNoeSenf() , moshtaryModel.getCcNoeSenf());
        contentValues.put(MoshtaryModel.COLUMN_ccNoeMoshtary() , moshtaryModel.getCcNoeMoshtary());	   
        contentValues.put(MoshtaryModel.COLUMN_ShenasehMely() , moshtaryModel.getShenasehMely());
        contentValues.put(MoshtaryModel.COLUMN_CodeVazeiat() , moshtaryModel.getCodeVazeiat());
        contentValues.put(MoshtaryModel.COLUMN_MasahatMaghazeh() , moshtaryModel.getMasahatMaghazeh());
        contentValues.put(MoshtaryModel.COLUMN_HasAnbar() , moshtaryModel.getHasAnbar());
        contentValues.put(MoshtaryModel.COLUMN_ExtraProp_IsOld() , moshtaryModel.getExtraProp_IsOld());
        contentValues.put(MoshtaryModel.COLUMN_ccMoshtaryParent() , moshtaryModel.getccMoshtaryParent());
        contentValues.put(MoshtaryModel.COLUMN_ExtraProp_IsMoshtaryAmargar() , moshtaryModel.getExtraProp_IsMoshtaryAmargar());
        contentValues.put(MoshtaryModel.COLUMN_ExtraProp_ccPorseshnameh() , moshtaryModel.getExtraProp_ccPorseshnameh());
        contentValues.put(MoshtaryModel.COLUMN_ExtraProp_NoeForoshandeh_First() , moshtaryModel.getExtraProp_NoeForoshandeh_First());
        contentValues.put(MoshtaryModel.COLUMN_ExtraProp_MoshtaryMojazKharejAzMasir() , moshtaryModel.getExtraProp_MoshtaryMojazKharejAzMasir());
        contentValues.put(MoshtaryModel.COLUMN_ExtraProp_Olaviat() , moshtaryModel.getExtraProp_Olaviat());
        contentValues.put(MoshtaryModel.COLUMN_DateOfMasir() , moshtaryModel.getDateOfMasir());
        contentValues.put(MoshtaryModel.COLUMN_ControlEtebarForoshandeh() , moshtaryModel.getControlEtebarForoshandeh());
        contentValues.put(MoshtaryModel.COLUMN_ModateNaghd() , moshtaryModel.getModateNaghd());
        contentValues.put(MoshtaryModel.COLUMN_TarikhMoarefiMoshtary(), moshtaryModel.getTarikhMoarefiMoshtary());
        contentValues.put(MoshtaryModel.COLUMN_KharejAzMahal(), moshtaryModel.getKharejAzMahal());
        contentValues.put(MoshtaryModel.COLUMN_CodeEghtesady(), moshtaryModel.getCodeEghtesady());


        return contentValues;
    }

    private ArrayList<MoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryModel> moshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryModel moshtaryModel = new MoshtaryModel();

            moshtaryModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccMoshtary())));
			moshtaryModel.setCcAfrad(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccAfrad())));																										   
            moshtaryModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameMoshtary())));
            moshtaryModel.setNameTablo(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameTablo())));
            moshtaryModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_Olaviat())));
            moshtaryModel.setModateVosol(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ModateVosol())));
            moshtaryModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeMoshtary())));
            moshtaryModel.setAddress(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_Address())));
			moshtaryModel.setMobile(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_Mobile())));																											
            moshtaryModel.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeNoeVosolAzMoshtary())));
            moshtaryModel.setccForoshandeh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccForoshandeh())));																				   
			moshtaryModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccMasir())));																											
            moshtaryModel.setToorVisit(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ToorVisit())));
            moshtaryModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeNoeHaml())));
            moshtaryModel.setFNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_FNameMoshtary())));
            moshtaryModel.setLNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_LNameMoshtary())));
            moshtaryModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_Darajeh())));
            moshtaryModel.setNameDarajeh(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameDarajeh())));
            moshtaryModel.setCodeMely(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeMely())));
            moshtaryModel.setCodeNoeShakhsiat(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeNoeShakhsiat())));
			moshtaryModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccNoeSenf())));
            moshtaryModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccNoeMoshtary())));
            moshtaryModel.setShenasehMely(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_ShenasehMely())));
            moshtaryModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeVazeiat())));
            moshtaryModel.setMasahatMaghazeh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_MasahatMaghazeh())));
            moshtaryModel.setHasAnbar(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_HasAnbar())));
            moshtaryModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_IsOld())));
            moshtaryModel.setccMoshtaryParent(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccMoshtaryParent())));
            moshtaryModel.setExtraProp_IsMoshtaryAmargar(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_IsMoshtaryAmargar())));
            moshtaryModel.setExtraProp_ccPorseshnameh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_ccPorseshnameh())));
            moshtaryModel.setExtraProp_NoeForoshandeh_First(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_NoeForoshandeh_First())));
            moshtaryModel.setExtraProp_MoshtaryMojazKharejAzMasir(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_MoshtaryMojazKharejAzMasir())));
            moshtaryModel.setExtraProp_Olaviat(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_Olaviat())));
            moshtaryModel.setDateOfMasir(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_DateOfMasir())));
            moshtaryModel.setControlEtebarForoshandeh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ControlEtebarForoshandeh())));
            moshtaryModel.setModateNaghd(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ModateNaghd())));
            moshtaryModel.setTarikhMoarefiMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_TarikhMoarefiMoshtary())));
            moshtaryModel.setKharejAzMahal(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_KharejAzMahal())));
            moshtaryModel.setCodeEghtesady(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeEghtesady())));


            moshtaryModels.add(moshtaryModel);
            cursor.moveToNext();
        }
        return moshtaryModels;
    }
    
    
}
