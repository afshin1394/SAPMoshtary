package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TakhfifNaghdyModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllvTakhfifNaghdyByccMarkazForoshResult;
import com.saphamrah.protos.CashDiscountGrpc;
import com.saphamrah.protos.CashDiscountReply;
import com.saphamrah.protos.CashDiscountReplyList;
import com.saphamrah.protos.CashDiscountRequest;

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

public class TakhfifNaghdyDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public TakhfifNaghdyDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TakhfifNaghdyDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            TakhfifNaghdyModel.COLUMN_ccTakhfifNaghdy(),
            TakhfifNaghdyModel.COLUMN_SharhTakhfif(),
            TakhfifNaghdyModel.COLUMN_ccNoeFieldMoshtary(),
            TakhfifNaghdyModel.COLUMN_CodeNoeMohasebeh(),
            TakhfifNaghdyModel.COLUMN_DarsadTakhfif(),
            TakhfifNaghdyModel.COLUMN_CodeNoe(),
            TakhfifNaghdyModel.COLUMN_NameNoeFieldMoshtary(),
            TakhfifNaghdyModel.COLUMN_Darajeh(),
            TakhfifNaghdyModel.COLUMN_ccMarkazSazmanForosh(),
            TakhfifNaghdyModel.COLUMN_ccNoeSenf(),
            TakhfifNaghdyModel.COLUMN_NameNoeSenf()
        };
    }

    public void fetchTakhfifNaghdyGrpc(final Context context, final String activityNameForLog,final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdyGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CashDiscountGrpc.CashDiscountBlockingStub cashDiscountBlockingStub = CashDiscountGrpc.newBlockingStub(managedChannel);
                CashDiscountRequest cashDiscountRequest = CashDiscountRequest.newBuilder().setSellOrganizationCenterID(ccMarkazSazmanForosh).build();
                Callable<CashDiscountReplyList> cashDiscountReplyListCallable = () -> cashDiscountBlockingStub.getCashDiscounts(cashDiscountRequest);
                RxAsync.makeObservable(cashDiscountReplyListCallable)
                        .map(cashDiscountReplyList ->  {
                            ArrayList<TakhfifNaghdyModel> models = new ArrayList<>();
                            for (CashDiscountReply reply : cashDiscountReplyList.getCashDiscountsList()) {
                                TakhfifNaghdyModel model = new TakhfifNaghdyModel();
                                model.setDarsadTakhfif(reply.getDiscountPercentage());
                                model.setCcTakhfifNaghdy(reply.getCashDiscountID());
                                model.setSharhTakhfif(reply.getDiscountDescription());
                                model.setDarajeh(reply.getDegree());
                                model.setCcNoeFieldMoshtary(reply.getCustomerFieldTypeID());
                                model.setCodeNoe(reply.getTypeCode());
                                model.setCcNoeSenf(reply.getGuildTypeID());
                                model.setCodeNoeMohasebeh(reply.getCalculationTypeCode());
                                model.setNameNoeFieldMoshtary(reply.getCustomerFieldTypeName());
                                model.setNameNoeSenf(reply.getGuildTypeName());
                                model.setCcMarkazSazmanForosh(reply.getSellOrganizationCenterID());
                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<TakhfifNaghdyModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<TakhfifNaghdyModel> rptSanadModels) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdyGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    public void fetchTakhfifNaghdy(final Context context, final String activityNameForLog,final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdy", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> call = apiServiceGet.getTakhfifNaghdy(ccMarkazSazmanForosh);
            call.enqueue(new Callback<GetAllvTakhfifNaghdyByccMarkazForoshResult>()
            {
                @Override
                public void onResponse(Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> call, Response<GetAllvTakhfifNaghdyByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifNaghdyByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdy", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdy", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdy", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdy", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage() , endpoint), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdy", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public void fetchTakhfifNaghdyForPakhsh(final Context context, final String activityNameForLog,final String ccMarkazForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdyForPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> call = apiServiceGet.getAllvTakhfifNaghdyByccMarkazForoshForPakhsh(ccMarkazForosh);
            call.enqueue(new Callback<GetAllvTakhfifNaghdyByccMarkazForoshResult>()
            {
                @Override
                public void onResponse(Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> call, Response<GetAllvTakhfifNaghdyByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifNaghdyDAO.class.getSimpleName(), "", "fetchTakhfifNaghdyForPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvTakhfifNaghdyByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdyForPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdyForPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdyForPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdyForPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifNaghdyDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifNaghdyForPakhsh", "onFailure");
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

    public boolean insertGroup(ArrayList<TakhfifNaghdyModel> takhfifNaghdyModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TakhfifNaghdyModel takhfifNaghdyModel : takhfifNaghdyModels)
            {
                ContentValues contentValues = modelToContentvalue(takhfifNaghdyModel);
                db.insertOrThrow(TakhfifNaghdyModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TakhfifNaghdyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifNaghdyDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<TakhfifNaghdyModel> getAll()
    {
        ArrayList<TakhfifNaghdyModel> takhfifNaghdyModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifNaghdyModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifNaghdyModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifNaghdyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifNaghdyDAO" , "" , "getAll" , "");
        }
        return takhfifNaghdyModels;
    }

    public ArrayList<TakhfifNaghdyModel> getByMoshtary(MoshtaryModel moshtaryModel  , int ccGoroh, int Darajeh)
    {
        final int NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY = 1;
        final int NAME_NOE_FIELD_MOSHTARY_CC_GOROH = 2;
        final int GOROH_LINK_NOE_MOSHTARY = 304 ;
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
        int ccMarkazSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getCcMarkazSazmanForosh(),0);
        ArrayList<TakhfifNaghdyModel> takhfifNaghdyModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifNaghdyModel.TableName(), allColumns(),
                            " (ccMarkazSazmanForosh = ? AND Darajeh in (? , 0) And ((NameNoeFieldMoshtary = ? AND ccNoeFieldMoshtary = ?)"
                                    //+ " OR (NameNoeFieldMoshtary = ? AND ccNoeFieldMoshtary = ?)"
                                    + " OR (NameNoeFieldMoshtary = ? AND ccNoeFieldMoshtary IN(?, ?) AND ccNoeSenf in (" + moshtaryModel.getCcMoshtary() + " , 0))))",
                         //   new String[]{String.valueOf(ccMarkazSazmanForosh) ,String.valueOf(moshtaryModel.getDarajeh()), String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY), String.valueOf(moshtaryModel.getCcMoshtary()),
                            new String[]{String.valueOf(ccMarkazSazmanForosh) ,String.valueOf(Darajeh), String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY), String.valueOf(moshtaryModel.getCcMoshtary()),
                                    String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_GOROH), String.valueOf(ccGoroh), String.valueOf(GOROH_LINK_NOE_MOSHTARY)},
                            null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifNaghdyModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifNaghdyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifNaghdyDAO" , "" , "getByMoshtary" , "");
        }
        return takhfifNaghdyModels;
    }

    public int getccTakhfifNaghdiByccGorohMoshtary(int ccGorohMoshtary)
    {
        int ccTakhfifNaghdi = 0;
        ArrayList<TakhfifNaghdyModel> takhfifNaghdyModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select " + TakhfifNaghdyModel.COLUMN_ccTakhfifNaghdy() + " from " + TakhfifNaghdyModel.TableName() + " where " + TakhfifNaghdyModel.COLUMN_ccNoeFieldMoshtary() + " = " + ccGorohMoshtary;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccTakhfifNaghdi = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifNaghdyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifNaghdyDAO" , "" , "getccTakhfifNaghdiByccGorohMoshtary" , "");
        }
        return ccTakhfifNaghdi;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TakhfifNaghdyModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , TakhfifNaghdyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifNaghdyDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TakhfifNaghdyModel takhfifNaghdyModel)
    {
        ContentValues contentValues = new ContentValues();

        if (takhfifNaghdyModel.getCcTakhfifNaghdy() > 0)
        {
            contentValues.put(TakhfifNaghdyModel.COLUMN_ccTakhfifNaghdy() , takhfifNaghdyModel.getCcTakhfifNaghdy());
        }
        contentValues.put(TakhfifNaghdyModel.COLUMN_SharhTakhfif() , takhfifNaghdyModel.getSharhTakhfif());
        contentValues.put(TakhfifNaghdyModel.COLUMN_ccNoeFieldMoshtary() , takhfifNaghdyModel.getCcNoeFieldMoshtary());
        contentValues.put(TakhfifNaghdyModel.COLUMN_CodeNoeMohasebeh() , takhfifNaghdyModel.getCodeNoeMohasebeh());
        contentValues.put(TakhfifNaghdyModel.COLUMN_DarsadTakhfif() , takhfifNaghdyModel.getDarsadTakhfif());
        contentValues.put(TakhfifNaghdyModel.COLUMN_CodeNoe() , takhfifNaghdyModel.getCodeNoe());
        contentValues.put(TakhfifNaghdyModel.COLUMN_NameNoeFieldMoshtary() , takhfifNaghdyModel.getNameNoeFieldMoshtary());
        contentValues.put(TakhfifNaghdyModel.COLUMN_Darajeh() , takhfifNaghdyModel.getDarajeh());
        contentValues.put(TakhfifNaghdyModel.COLUMN_ccMarkazSazmanForosh() , takhfifNaghdyModel.getCcMarkazSazmanForosh());
        contentValues.put(TakhfifNaghdyModel.COLUMN_ccNoeSenf() , takhfifNaghdyModel.getCcNoeSenf());
        contentValues.put(TakhfifNaghdyModel.COLUMN_NameNoeSenf() , takhfifNaghdyModel.getNameNoeSenf());

        return contentValues;
    }


    private ArrayList<TakhfifNaghdyModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TakhfifNaghdyModel> takhfifNaghdyModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TakhfifNaghdyModel takhfifNaghdyModel = new TakhfifNaghdyModel();

            takhfifNaghdyModel.setCcTakhfifNaghdy(cursor.getInt(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_ccTakhfifNaghdy())));
            takhfifNaghdyModel.setSharhTakhfif(cursor.getString(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_SharhTakhfif())));
            takhfifNaghdyModel.setCcNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_ccNoeFieldMoshtary())));
            takhfifNaghdyModel.setCodeNoeMohasebeh(cursor.getInt(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_CodeNoeMohasebeh())));
            takhfifNaghdyModel.setDarsadTakhfif(cursor.getDouble(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_DarsadTakhfif())));
            takhfifNaghdyModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_CodeNoe())));
            takhfifNaghdyModel.setNameNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_NameNoeFieldMoshtary())));
            takhfifNaghdyModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_Darajeh())));
            takhfifNaghdyModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_ccMarkazSazmanForosh())));
            takhfifNaghdyModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_ccNoeSenf())));
            takhfifNaghdyModel.setNameNoeSenf(cursor.getString(cursor.getColumnIndex(TakhfifNaghdyModel.COLUMN_NameNoeSenf())));

            takhfifNaghdyModels.add(takhfifNaghdyModel);
            cursor.moveToNext();
        }
        return takhfifNaghdyModels;
    }
    
    
}
