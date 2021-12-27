package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TakhfifHajmiModel;
import com.saphamrah.Model.TakhfifHajmiSatrModel;
import com.saphamrah.Model.TakhfifHajmiTitrSatrModel;
import com.saphamrah.Model.TakhfifSenfiModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvTakhfifHajmiByccMarkazForoshResult;
import com.saphamrah.protos.VolumetricDiscountGrpc;
import com.saphamrah.protos.VolumetricDiscountReply;
import com.saphamrah.protos.VolumetricDiscountReplyList;
import com.saphamrah.protos.VolumetricDiscountRequest;

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

public class TakhfifHajmiDAO 
{


    private DBHelper dbHelper;
    private Context context;


    public TakhfifHajmiDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TakhfifHajmiDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            TakhfifHajmiModel.COLUMN_Radif(),
            TakhfifHajmiModel.COLUMN_ccTakhfifHajmi(),
            TakhfifHajmiModel.COLUMN_CodeNoe(),
            TakhfifHajmiModel.COLUMN_SharhTakhfif(),
            TakhfifHajmiModel.COLUMN_NoeTedadRial(),
            TakhfifHajmiModel.COLUMN_NameNoeFieldMoshtary(),
            TakhfifHajmiModel.COLUMN_ccNoeFieldMoshtary(),
            TakhfifHajmiModel.COLUMN_CodeNoeHaml(),
            TakhfifHajmiModel.COLUMN_Darajeh(),
            TakhfifHajmiModel.COLUMN_ForJayezeh(),
            TakhfifHajmiModel.COLUMN_NoeVosol(),
            TakhfifHajmiModel.COLUMN_txtNoeVosol(),
            TakhfifHajmiModel.COLUMN_ccGorohTakidi(),
            TakhfifHajmiModel.COLUMN_ccMarkazSazmanForosh(),
            TakhfifHajmiModel.COLUMN_Olaviat(),
			TakhfifHajmiModel.COLUMN_IsPelekani(),
			TakhfifHajmiModel.COLUMN_ccMantagheh(),
            TakhfifHajmiModel.COLUMN_ccNoeSenf(),
            TakhfifHajmiModel.COLUMN_NameNoeSenf(),
            TakhfifHajmiModel.COLUMN_NoeGheymat()
        };
    }

    public void fetchTakhfifHajmiTitrGrpc(final Context context, final String activityNameForLog, final String ccMarkazSazmanForosh, final String ccTakhfifHajmi, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                VolumetricDiscountGrpc.VolumetricDiscountBlockingStub volumetricDiscountBlockingStub = VolumetricDiscountGrpc.newBlockingStub(managedChannel);
                VolumetricDiscountRequest volumetricDiscountRequest = VolumetricDiscountRequest.newBuilder().setVolumetricDiscountID(ccTakhfifHajmi).setRowTitleType("1").setSellOrganizationCenterID(ccMarkazSazmanForosh).build();
                Callable<VolumetricDiscountReplyList> volumetricDiscountReplyListCallable = () -> volumetricDiscountBlockingStub.getVolumetricDiscount(volumetricDiscountRequest);
                RxAsync.makeObservable(volumetricDiscountReplyListCallable)
                        .map(volumetricDiscountReplyList ->  {
                            ArrayList<TakhfifHajmiModel> models = new ArrayList<>();
                            for (VolumetricDiscountReply reply : volumetricDiscountReplyList.getVolumetricDiscountsList()) {
                                TakhfifHajmiModel model = new TakhfifHajmiModel();
                                model.setCcTakhfifHajmi(reply.getVolumetricDiscountID());
                                model.setCcMantagheh(reply.getAreaID());
                                model.setCcGorohTakidi(reply.getInjuctiveGroupID());
                                model.setCcMarkazSazmanForosh(reply.getSellOrganizationCenterID());
                                model.setCcNoeFieldMoshtary(reply.getCustomerFieldTypeID());
                                model.setCcNoeSenf(reply.getGuildTypeID());
                                model.setNameNoeSenf(reply.getGuildTypeName());
                                model.setCodeNoe(reply.getTypeCode());
                                model.setCodeNoeHaml(reply.getCarryingTypeCode());
                                model.setDarajeh(reply.getDegree());
                                model.setForJayezeh(reply.getForBonus());
                                model.setIsPelekani(reply.getIsStepByStep());
                                model.setNameNoeFieldMoshtary(reply.getCustomerFieldTypeName());
                                model.setNameNoeSenf(reply.getGuildTypeName());
                                model.setOlaviat(reply.getPriority());
                                model.setRadif(reply.getIndex());
                                model.setNoeVosol(reply.getReceiptType());
                                model.setTxtNoeVosol(reply.getReceiptTypeTxt());
                                model.setSharhTakhfif(reply.getDiscountDescription());
                                model.setNoeTedadRial(reply.getRialCountType());
                                model.setNoeGheymat(reply.getPriceType());

                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<TakhfifHajmiModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<TakhfifHajmiModel> rptSanadModels) {
                                retrofitResponse.onSuccess(rptSanadModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    public void fetchTakhfifHajmi(final Context context, final String activityNameForLog, final String ccMarkazForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call = apiServiceGet.getAllvTakhfifHajmiByccMarkazForosh(ccMarkazForosh);
            call.enqueue(new Callback<GetAllvTakhfifHajmiByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call, Response<GetAllvTakhfifHajmiByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifHajmiDAO.class.getSimpleName(), "", "fetchTakhfifHajmi", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifHajmiByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public void fetchTakhfifHajmiForPakhsh(final Context context, final String activityNameForLog, final String ccMarkazForoshPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiForPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call = apiServiceGet.getAllvTakhfifHajmiByccMarkazForoshForPakhsh(ccMarkazForoshPakhsh);
            call.enqueue(new Callback<GetAllvTakhfifHajmiByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call, Response<GetAllvTakhfifHajmiByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifHajmiDAO.class.getSimpleName(), "", "fetchTakhfifHajmiForPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifHajmiByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiForPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiForPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiForPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiForPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmiForPakhsh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public void fetchTakhfifHajmiTitr(final Context context, final String activityNameForLog, final String ccMarkazSazmanForosh, final String ccTakhfifHajmi, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call = apiServiceGet.getTakhfifHajmiTitr("1", ccMarkazSazmanForosh, ccTakhfifHajmi);
            call.enqueue(new Callback<GetAllvTakhfifHajmiByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call, Response<GetAllvTakhfifHajmiByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifHajmiDAO.class.getSimpleName(), "", "fetchTakhfifHajmi", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifHajmiByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifHajmiByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifHajmiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifHajmi", "onFailure");
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


    public boolean insertGroupConditional(ArrayList<TakhfifHajmiModel> takhfifHajmiModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TakhfifHajmiModel takhfifHajmiModel : takhfifHajmiModels)
            {
                ContentValues contentValues = modelToContentvalue(takhfifHajmiModel);
                db.insertOrThrow(TakhfifHajmiModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TakhfifHajmiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public String insertGroup(ArrayList<TakhfifHajmiModel> takhfifHajmiModels)
    {
        String ccTakhfifHajmis = "-1";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TakhfifHajmiModel takhfifHajmiModel : takhfifHajmiModels)
            {
                ccTakhfifHajmis += "," + takhfifHajmiModel.getCcTakhfifHajmi();
                ContentValues contentValues = modelToContentvalue(takhfifHajmiModel);
                db.insertOrThrow(TakhfifHajmiModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return ccTakhfifHajmis;
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TakhfifHajmiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiDAO" , "" , "insertGroup" , "");
            return "";
        }
    }

    public ArrayList<TakhfifHajmiModel> getAll()
    {
        ArrayList<TakhfifHajmiModel> takhfifHajmiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifHajmiModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifHajmiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiDAO" , "" , "getAll" , "");
        }
        return takhfifHajmiModels;
    }

    public String getCcTakhfifHajmiForGetProgram()
    {
        String ccTakhfifajmis="-1";
        String query = "SELECT DISTINCT ccTakhfifHajmi FROM TakhfifHajmi";

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

                        ccTakhfifajmis += "," + cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccTakhfifHajmi()));
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
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiDAO" , "" , "getAll" , "");
        }
        return ccTakhfifajmis;
    }


    public ArrayList<TakhfifHajmiTitrSatrModel> getByMoshtaryWithSatr(MoshtaryModel moshtary, int codeNoeHaml, boolean ShebhOmdeh, int CodeNoeVosol, int Darajeh)
    {
        final int NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY = 1;
        final int NAME_NOE_FIELD_MOSHTARY_CC_GOROH = 2;
        final int GOROH_LINK_NOE_MOSHTARY = 304 ;
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
        int ccMarkazSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getCcMarkazSazmanForosh(),0);
        ArrayList<TakhfifHajmiTitrSatrModel> takhfifHajmiTitrSatrModels = new ArrayList<>();
        try
        {
            

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String ccNoeFieldMoshtarys =  moshtary.getCcNoeMoshtary() + "," + GOROH_LINK_NOE_MOSHTARY;
            int ccNoeSenfMoshtary = moshtary.getCcNoeSenf();
            int ccMoshtaryParent = moshtary.getccMoshtaryParent();
            String noeVosols =  "0 ," + CodeNoeVosol;
            String query = "select t.* , ts.NameNoeField from TakhfifHajmi t inner join (select distinct(NameNoeField) , ccTakhfifHajmi from TakhfifHajmiSatr ) ts on t.ccTakhfifHajmi = ts.ccTakhfifHajmi where ";
            if(!ShebhOmdeh)
            {
                query += " ((NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= " + moshtary.getCcMoshtary() + ")"
                        + " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= "+ccMoshtaryParent+")"
                        + " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_GOROH + " AND ccNoeFieldMoshtary IN(" + ccNoeFieldMoshtarys + ") AND ccNoeSenf in (" + ccNoeSenfMoshtary + " , 0)))"
                       // + " AND CodeNoeHaml= " + codeNoeHaml + " AND NoeVosol IN(" + noeVosols + ") AND (Darajeh in ( " + moshtary.getDarajeh() + " , 0 ) AND ccMarkazSazmanForosh = "+ ccMarkazSazmanForosh +")";
                        + " AND CodeNoeHaml= " + codeNoeHaml + " AND NoeVosol IN(" + noeVosols + ") AND (Darajeh in ( " + Darajeh + " , 0 ) AND ccMarkazSazmanForosh = "+ ccMarkazSazmanForosh +")";
            }
            else
            {
                query += " ((NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= " + moshtary.getCcMoshtary() + ")"
                        + " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= "+ccMoshtaryParent+")"
                        + " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_GOROH + " AND ccNoeFieldMoshtary IN(" + ccNoeFieldMoshtarys + ") AND ccNoeSenf in (" + ccNoeSenfMoshtary + " , 0)))"
                        //+ " AND CodeNoeHaml= " + codeNoeHaml + " AND ccTakhfifHajmi<>1620  AND NoeVosol IN(" + noeVosols + ") AND (Darajeh in ( " + moshtary.getDarajeh() + " , 0) AND ccMarkazSazmanForosh = "+ ccMarkazSazmanForosh +")";
                        + " AND CodeNoeHaml= " + codeNoeHaml + " AND ccTakhfifHajmi<>1620  AND NoeVosol IN(" + noeVosols + ") AND (Darajeh in ( " + Darajeh + " , 0) AND ccMarkazSazmanForosh = "+ ccMarkazSazmanForosh +")";
            }
            Log.d("TakhfifHajmi" , "query : " + query);
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifHajmiTitrSatrModels = cursorToModelTitrSatr(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiDAO" , "" , "getByMoshtaryWithSatr" , "");
        }
        return takhfifHajmiTitrSatrModels;
    }

    public int getMaxOlaviat()
    {
        int maxOlaviat = -1;
        try
        {
            String query = "select MAX(" + TakhfifHajmiModel.COLUMN_Olaviat() + ") from " + TakhfifHajmiModel.TableName();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    maxOlaviat = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiDAO" , "" , "getDistinctOlaviat" , "");
        }
        return maxOlaviat;
    }

    public ArrayList<TakhfifHajmiModel> getByMoshtary(MoshtaryModel moshtary, int ccMarkazSazmanForosh, int codeNoeHaml, boolean ShebhOmdeh, int CodeNoeVosol)
    {
        final int NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY = 1;
        final int NAME_NOE_FIELD_MOSHTARY_CC_GOROH = 2;
        final int GOROH_LINK_NOE_MOSHTARY = 304 ;
        ArrayList<TakhfifHajmiModel> takhfifHajmiModels = new ArrayList<>();
        try
        {
            

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = null;
            if(!ShebhOmdeh)
            {
                cursor = db.query(TakhfifHajmiModel.TableName(), allColumns(),
                                "(NameNoeFieldMoshtary= ? AND ccNoeFieldMoshtary= ?)"
                                        + " OR (NameNoeFieldMoshtary= ? AND ccNoeFieldMoshtary IN(?, ?))"
                                        + " AND CodeNoeHaml= ?  AND NoeVosol IN(0, ?) AND (Darajeh = ?) And ccMarkazSazmanForosh = ? ",
                                new String[]{String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY), String.valueOf(moshtary.getCcMoshtary()),
                                        //String.valueOf(NameNoeFieldMoshtary.ccMoshtary.getValue()), //String.valueOf(moshtary.getccMoshtary_Link()),
                                        String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_GOROH), String.valueOf(moshtary.getCcNoeMoshtary()),
                                        String.valueOf(GOROH_LINK_NOE_MOSHTARY),
                                        String.valueOf(codeNoeHaml), String.valueOf(CodeNoeVosol), String.valueOf(moshtary.getDarajeh()), String.valueOf(ccMarkazSazmanForosh)},
                                null, null, null);

            }
            else
            {//takhfifHajmi ShebhOmdeh
                cursor = db.query(TakhfifHajmiModel.TableName(), allColumns(),
                                "(NameNoeFieldMoshtary= ? AND ccNoeFieldMoshtary= ?)"
                                        + " OR (NameNoeFieldMoshtary= ? AND ccNoeFieldMoshtary IN(?, ?))"
                                        + " AND CodeNoeHaml= ? AND ccTakhfifHajmi<>1620  AND NoeVosol IN(0, ?) AND (Darajeh = ?)  And ccMarkazSazmanForosh = ? ",
                                new String[]{String.valueOf(ccMarkazSazmanForosh), String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY), String.valueOf(moshtary.getCcMoshtary()),
                                        //String.valueOf(NameNoeFieldMoshtary.ccMoshtary.getValue()), //String.valueOf(moshtary.getccMoshtary_Link()),
                                        String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_GOROH), String.valueOf(moshtary.getCcNoeMoshtary()),
                                        String.valueOf(GOROH_LINK_NOE_MOSHTARY),
                                        String.valueOf(codeNoeHaml),  String.valueOf(CodeNoeVosol), String.valueOf(moshtary.getDarajeh()), String.valueOf(ccMarkazSazmanForosh)},
                                null, null, null);
            }

            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifHajmiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifHajmiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiDAO" , "" , "getByMoshtary" , "");
        }
        return takhfifHajmiModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TakhfifHajmiModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , TakhfifHajmiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifHajmiDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TakhfifHajmiModel takhfifHajmiModel)
    {
        ContentValues contentValues = new ContentValues();


        if (takhfifHajmiModel.getCcTakhfifHajmi() > 0)
        {
            contentValues.put(TakhfifHajmiModel.COLUMN_ccTakhfifHajmi() , takhfifHajmiModel.getCcTakhfifHajmi());
        }
        if (takhfifHajmiModel.getRadif() > 0)
        {
            contentValues.put(TakhfifHajmiModel.COLUMN_Radif() , takhfifHajmiModel.getRadif());
        }
        contentValues.put(TakhfifHajmiModel.COLUMN_CodeNoe() , takhfifHajmiModel.getCodeNoe());
        contentValues.put(TakhfifHajmiModel.COLUMN_SharhTakhfif() , takhfifHajmiModel.getSharhTakhfif());
        contentValues.put(TakhfifHajmiModel.COLUMN_NoeTedadRial() , takhfifHajmiModel.getNoeTedadRial());
        contentValues.put(TakhfifHajmiModel.COLUMN_NameNoeFieldMoshtary() , takhfifHajmiModel.getNameNoeFieldMoshtary());
        contentValues.put(TakhfifHajmiModel.COLUMN_ccNoeFieldMoshtary() , takhfifHajmiModel.getCcNoeFieldMoshtary());
        contentValues.put(TakhfifHajmiModel.COLUMN_CodeNoeHaml() , takhfifHajmiModel.getCodeNoeHaml());
        contentValues.put(TakhfifHajmiModel.COLUMN_Darajeh() , takhfifHajmiModel.getDarajeh());
        contentValues.put(TakhfifHajmiModel.COLUMN_ForJayezeh() , takhfifHajmiModel.getForJayezeh());
        contentValues.put(TakhfifHajmiModel.COLUMN_NoeVosol() , takhfifHajmiModel.getNoeVosol());
        contentValues.put(TakhfifHajmiModel.COLUMN_txtNoeVosol() , takhfifHajmiModel.getTxtNoeVosol());
        contentValues.put(TakhfifHajmiModel.COLUMN_ccGorohTakidi() , takhfifHajmiModel.getCcGorohTakidi());
        contentValues.put(TakhfifHajmiModel.COLUMN_ccMarkazSazmanForosh() , takhfifHajmiModel.getCcMarkazSazmanForosh());
        contentValues.put(TakhfifHajmiModel.COLUMN_Olaviat() , takhfifHajmiModel.getOlaviat());
		contentValues.put(TakhfifHajmiModel.COLUMN_IsPelekani() , takhfifHajmiModel.getIsPelekani());	   
		contentValues.put(TakhfifHajmiModel.COLUMN_ccMantagheh() , takhfifHajmiModel.getCcMantagheh());
        contentValues.put(TakhfifHajmiModel.COLUMN_ccNoeSenf() , takhfifHajmiModel.getCcNoeSenf());
        contentValues.put(TakhfifHajmiModel.COLUMN_NameNoeSenf() , takhfifHajmiModel.getNameNoeSenf());
        contentValues.put(TakhfifHajmiModel.COLUMN_NoeGheymat() , takhfifHajmiModel.getNoeGheymat());

        return contentValues;
    }

    private ArrayList<TakhfifHajmiModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TakhfifHajmiModel> takhfifHajmiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TakhfifHajmiModel takhfifHajmiModel = new TakhfifHajmiModel();

            takhfifHajmiModel.setRadif(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_Radif())));
            takhfifHajmiModel.setCcTakhfifHajmi(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccTakhfifHajmi())));
            takhfifHajmiModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_CodeNoe())));
            takhfifHajmiModel.setSharhTakhfif(cursor.getString(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_SharhTakhfif())));
            takhfifHajmiModel.setNoeTedadRial(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NoeTedadRial())));
            takhfifHajmiModel.setNameNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NameNoeFieldMoshtary())));
            takhfifHajmiModel.setCcNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccNoeFieldMoshtary())));
            takhfifHajmiModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_CodeNoeHaml())));
            takhfifHajmiModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_Darajeh())));
            takhfifHajmiModel.setForJayezeh(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ForJayezeh())));
            takhfifHajmiModel.setNoeVosol(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NoeVosol())));
            takhfifHajmiModel.setTxtNoeVosol(cursor.getString(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_txtNoeVosol())));
            takhfifHajmiModel.setCcGorohTakidi(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccGorohTakidi())));
            takhfifHajmiModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccMarkazSazmanForosh())));
            takhfifHajmiModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_Olaviat())));
			takhfifHajmiModel.setIsPelekani(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_IsPelekani())));																												   
			takhfifHajmiModel.setCcMantagheh(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccMantagheh())));
            takhfifHajmiModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccNoeSenf())));
            takhfifHajmiModel.setNameNoeSenf(cursor.getString(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NameNoeSenf())));
            takhfifHajmiModel.setNoeGheymat(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NoeGheymat())));

            takhfifHajmiModels.add(takhfifHajmiModel);
            cursor.moveToNext();
        }
        return takhfifHajmiModels;
    }


    private ArrayList<TakhfifHajmiTitrSatrModel> cursorToModelTitrSatr(Cursor cursor)
    {
        ArrayList<TakhfifHajmiTitrSatrModel> takhfifHajmiTitrSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel = new TakhfifHajmiTitrSatrModel();

            //////////// TITR ////////////
            takhfifHajmiTitrSatrModel.setRadif(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_Radif())));
            takhfifHajmiTitrSatrModel.setCcTakhfifHajmi(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccTakhfifHajmi())));
            takhfifHajmiTitrSatrModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_CodeNoe())));
            takhfifHajmiTitrSatrModel.setSharhTakhfif(cursor.getString(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_SharhTakhfif())));
            takhfifHajmiTitrSatrModel.setNoeTedadRial(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NoeTedadRial())));
            takhfifHajmiTitrSatrModel.setNameNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NameNoeFieldMoshtary())));
            takhfifHajmiTitrSatrModel.setCcNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccNoeFieldMoshtary())));
            takhfifHajmiTitrSatrModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_CodeNoeHaml())));
            takhfifHajmiTitrSatrModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_Darajeh())));
            takhfifHajmiTitrSatrModel.setForJayezeh(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ForJayezeh())));
            takhfifHajmiTitrSatrModel.setNoeVosol(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NoeVosol())));
            takhfifHajmiTitrSatrModel.setTxtNoeVosol(cursor.getString(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_txtNoeVosol())));
            takhfifHajmiTitrSatrModel.setCcGorohTakidi(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccGorohTakidi())));
            takhfifHajmiTitrSatrModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_Olaviat())));
			takhfifHajmiTitrSatrModel.setIsPelekani(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_IsPelekani())));																															
			takhfifHajmiTitrSatrModel.setCcMantagheh(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccMantagheh())));
            takhfifHajmiTitrSatrModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_ccNoeSenf())));
            takhfifHajmiTitrSatrModel.setNameNoeSenf(cursor.getString(cursor.getColumnIndex(TakhfifHajmiModel.COLUMN_NameNoeSenf())));
            takhfifHajmiTitrSatrModel.setNoeGheymat(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeGheymat())));


            //////////// SATR ////////////
            takhfifHajmiTitrSatrModel.setNameNoeField(cursor.getInt(cursor.getColumnIndex(TakhfifHajmiSatrModel.COLUMN_NameNoeField())));


            takhfifHajmiTitrSatrModels.add(takhfifHajmiTitrSatrModel);
            cursor.moveToNext();
        }
        return takhfifHajmiTitrSatrModels;
    }


}
