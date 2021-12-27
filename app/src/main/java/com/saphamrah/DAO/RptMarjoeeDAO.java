package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.RptMarjoeeKalaModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetrptMarjoeeForoshandehResult;
import com.saphamrah.protos.RptSalesManReturnGrpc;
import com.saphamrah.protos.RptSalesManReturnReply;
import com.saphamrah.protos.RptSalesManReturnReplyList;
import com.saphamrah.protos.RptSalesManReturnRequest;

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

public class RptMarjoeeDAO
{


    private DBHelper dbHelper;
    private Context context;
    private String className = "RptListVosolDAO";


    public RptMarjoeeDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), className , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            RptMarjoeeKalaModel.COLUMN_Radif(),
            RptMarjoeeKalaModel.COLUMN_ccElamMarjoee(),
            RptMarjoeeKalaModel.COLUMN_ccElamMarjoeeSatr(),
            RptMarjoeeKalaModel.COLUMN_ccDarkhastFaktor(),
            RptMarjoeeKalaModel.COLUMN_ShomarehFaktor(),
            RptMarjoeeKalaModel.COLUMN_ccMoshtary(),
            RptMarjoeeKalaModel.COLUMN_CodeMoshtary(),
            RptMarjoeeKalaModel.COLUMN_NameMoshtary(),
            RptMarjoeeKalaModel.COLUMN_CodeKala(),
            RptMarjoeeKalaModel.COLUMN_NameKala(),
            RptMarjoeeKalaModel.COLUMN_Tedad3(),
            RptMarjoeeKalaModel.COLUMN_ShomarehBach(),
            RptMarjoeeKalaModel.COLUMN_TarikhTolid(),
            RptMarjoeeKalaModel.COLUMN_TarikhEngheza(),
            RptMarjoeeKalaModel.COLUMN_Fee(),
            RptMarjoeeKalaModel.COLUMN_ccTaminkonandeh(),
            RptMarjoeeKalaModel.COLUMN_GheymatMasrafkonandeh()
        };
    }


    public void fetchRPTMarjoeeGrpc(final Context context, final String activityNameForLog , int ccAfrad, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMarjoeeDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                RptSalesManReturnGrpc.RptSalesManReturnBlockingStub blockingStub = RptSalesManReturnGrpc.newBlockingStub(managedChannel);
                RptSalesManReturnRequest request = RptSalesManReturnRequest.newBuilder().setPersonID(String.valueOf(ccAfrad)).build();

                Callable<RptSalesManReturnReplyList> replyListCallable = () -> blockingStub.getRptSalesManReturn(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<RptMarjoeeKalaModel> models = new ArrayList<>();
                            for (RptSalesManReturnReply reply : replyList.getRptSalesManReturnsList()) {
                                RptMarjoeeKalaModel model = new RptMarjoeeKalaModel();

                                model.setRadif(reply.getRow());
                                model.setCcElamMarjoee(reply.getReturnAnnouncementID());
                                model.setCcElamMarjoeeSatr(reply.getReturnAnnouncementRowID());
                                model.setCcDarkhastFaktor(reply.getInvoiceRequestID());
                                model.setShomarehFaktor(reply.getInvoiceNumber());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setCodeMoshtary(reply.getCustomerCode());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setCodeKala(reply.getGoodsCode());
                                model.setNameKala(reply.getGoodsName());
                                model.setTedad3(reply.getQuantity3());
                                model.setShomarehBach(reply.getBatchNumber());
                                model.setTarikhTolid(reply.getProductionDate());
                                model.setTarikhEngheza(reply.getExpirationDate());
                                model.setFee(reply.getPrice());
                                model.setCcTaminkonandeh(reply.getPrividerID());
                                model.setGheymatMasrafkonandeh(reply.getConsumerPrice());
                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptMarjoeeKalaModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptMarjoeeKalaModel> models) {
                                retrofitResponse.onSuccess(models);
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
        } catch (Exception exception) {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), RptMarjoeeDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }


    public void fetchRPTMarjoee(final Context context, final String activityNameForLog, final String ccAfrad, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMarjoee", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetrptMarjoeeForoshandehResult> call = apiServiceGet.getrptMarjoeeForoshandeh(ccAfrad);
            call.enqueue(new Callback<GetrptMarjoeeForoshandehResult>() {
                @Override
                public void onResponse(Call<GetrptMarjoeeForoshandehResult> call, Response<GetrptMarjoeeForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptListVosolDAO.class.getSimpleName(), "", "fetchRPTMarjoee", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetrptMarjoeeForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMarjoee", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMarjoee", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMarjoee", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMarjoee", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetrptMarjoeeForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptListVosolDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMarjoee", "onFailure");
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

    public boolean insertGroup(ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptMarjoeeKalaModel rptMarjoeeKalaModel : rptMarjoeeKalaModels)
            {
                ContentValues contentValues = modelToContentvalue(rptMarjoeeKalaModel);
                db.insertOrThrow(RptMarjoeeKalaModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, className , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptMarjoeeKalaModel> getAll()
    {
        ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels = new ArrayList<>();
        String query = "SELECT * FROM Rpt_Marjoee\n" +
                " UNION \n" +
                " SELECT -1, 0, 0, ccDarkhastFaktor, ShomarehFaktor, 0, CodeMoshtary, NameMoshtary, \n" +
                " 0, 0, IFNull(sum(Tedad3),0) AS Tedad3, 0, 0, 0, IFNULL(sum(Fee*Tedad3),0) AS Fee, \n" +
                " 0,IFNull(sum(GheymatMasrafkonandeh*Tedad3),0) AS GheymatMasrafkonandeh FROM Rpt_Marjoee \n" +
                " GROUP BY ccDarkhastFaktor \n" +
                " ORDER BY ccDarkhastFaktor , Radif desc";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMarjoeeKalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, className , "" , "getAll" , "");
        }
        return rptMarjoeeKalaModels;
    }


    public ArrayList<RptMarjoeeKalaModel> getAllOrderByCustomer()
    {
        ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels = new ArrayList<>();
        String query = "SELECT * FROM Rpt_Marjoee\n" +
                " UNION \n" +
                " SELECT -1, 0, 0, ccDarkhastFaktor, ShomarehFaktor, 0, CodeMoshtary, NameMoshtary, \n" +
                " 0, 0, IFNull(sum(Tedad3),0) AS Tedad3, 0, 0, 0, IFNULL(sum(Fee*Tedad3),0) AS Fee, \n" +
                " 0,IFNull(sum(GheymatMasrafkonandeh*Tedad3),0) AS GheymatMasrafkonandeh FROM Rpt_Marjoee \n" +
                " GROUP BY ccDarkhastFaktor \n" +
                " ORDER BY ccDarkhastFaktor";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMarjoeeKalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, className , "" , "getAll" , "");
        }
        return rptMarjoeeKalaModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptMarjoeeKalaModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptMarjoeeKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, className , "" , "deleteAll" , "");
            return false;
        }
    }

    private ContentValues modelToContentvalue(RptMarjoeeKalaModel rptMarjoeeKalaModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(RptMarjoeeKalaModel.COLUMN_Radif() , rptMarjoeeKalaModel.getRadif());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_ccElamMarjoee() , rptMarjoeeKalaModel.getCcElamMarjoee());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_ccElamMarjoeeSatr() , rptMarjoeeKalaModel.getCcElamMarjoeeSatr());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_ccDarkhastFaktor() , rptMarjoeeKalaModel.getCcDarkhastFaktor());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_ShomarehFaktor() , rptMarjoeeKalaModel.getShomarehFaktor());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_ccMoshtary() , rptMarjoeeKalaModel.getCcMoshtary());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_CodeMoshtary() , rptMarjoeeKalaModel.getCodeMoshtary());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_NameMoshtary() , rptMarjoeeKalaModel.getNameMoshtary());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_CodeKala() , rptMarjoeeKalaModel.getCodeKala());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_NameKala() , rptMarjoeeKalaModel.getNameKala());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_Tedad3() , rptMarjoeeKalaModel.getTedad3());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_ShomarehBach() , rptMarjoeeKalaModel.getShomarehBach());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_TarikhTolid() , rptMarjoeeKalaModel.getTarikhTolid());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_TarikhEngheza() , rptMarjoeeKalaModel.getTarikhEngheza());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_Fee() , rptMarjoeeKalaModel.getFee());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_ccTaminkonandeh() , rptMarjoeeKalaModel.getCcTaminkonandeh());
        contentValues.put(RptMarjoeeKalaModel.COLUMN_GheymatMasrafkonandeh() , rptMarjoeeKalaModel.getGheymatMasrafkonandeh());

        return contentValues;
    }


    private ArrayList<RptMarjoeeKalaModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptMarjoeeKalaModel> rptListVosolModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptMarjoeeKalaModel rptMarjoeeKalaModel = new RptMarjoeeKalaModel();

            rptMarjoeeKalaModel.setRadif(cursor.getInt(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_Radif())));
            rptMarjoeeKalaModel.setCcElamMarjoee(cursor.getLong(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_ccElamMarjoee())));
            rptMarjoeeKalaModel.setCcElamMarjoeeSatr(cursor.getLong(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_ccElamMarjoeeSatr())));
            rptMarjoeeKalaModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_ccDarkhastFaktor())));
            rptMarjoeeKalaModel.setShomarehFaktor(cursor.getString(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_ShomarehFaktor())));
            rptMarjoeeKalaModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_ccMoshtary())));
            rptMarjoeeKalaModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_CodeMoshtary())));
            rptMarjoeeKalaModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_NameMoshtary())));
            rptMarjoeeKalaModel.setCodeKala(cursor.getString(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_CodeKala())));
            rptMarjoeeKalaModel.setNameKala(cursor.getString(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_NameKala())));
            rptMarjoeeKalaModel.setTedad3(cursor.getInt(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_Tedad3())));
            rptMarjoeeKalaModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_ShomarehBach())));
            rptMarjoeeKalaModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_TarikhTolid())));
            rptMarjoeeKalaModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_TarikhEngheza())));
            rptMarjoeeKalaModel.setFee(cursor.getLong(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_Fee())));
            rptMarjoeeKalaModel.setCcTaminkonandeh(cursor.getInt(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_ccTaminkonandeh())));
            rptMarjoeeKalaModel.setGheymatMasrafkonandeh(cursor.getLong(cursor.getColumnIndex(RptMarjoeeKalaModel.COLUMN_GheymatMasrafkonandeh())));

            rptListVosolModels.add(rptMarjoeeKalaModel);
            cursor.moveToNext();
        }
        return rptListVosolModels;
    }


}
