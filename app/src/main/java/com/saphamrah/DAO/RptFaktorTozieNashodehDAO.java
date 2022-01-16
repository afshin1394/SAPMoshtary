package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.RptFaktorTozieNashodehModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllrptFaktorTozieNashodehResult;
import com.saphamrah.protos.RptNotDistributedInvoiceGrpc;
import com.saphamrah.protos.RptNotDistributedInvoiceReply;
import com.saphamrah.protos.RptNotDistributedInvoiceReplyList;
import com.saphamrah.protos.RptNotDistributedInvoiceRequest;

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

public class RptFaktorTozieNashodehDAO
{

    private DBHelper dbHelper;
    private Context context;


    public RptFaktorTozieNashodehDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptFaktorTozieNashodehDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptFaktorTozieNashodehModel.COLUMN_ccRpt_FaktorTozieNashodeh(),
            RptFaktorTozieNashodehModel.COLUMN_ccDarkhastFaktor(),
            RptFaktorTozieNashodehModel.COLUMN_ccMarkazForosh(),
            RptFaktorTozieNashodehModel.COLUMN_ccGorohForosh(),
            RptFaktorTozieNashodehModel.COLUMN_ccForoshandeh(),
            RptFaktorTozieNashodehModel.COLUMN_ccMoshtary(),
            RptFaktorTozieNashodehModel.COLUMN_NameMarkazForosh(),
            RptFaktorTozieNashodehModel.COLUMN_SharhGorohForosh(),
            RptFaktorTozieNashodehModel.COLUMN_SharhForoshandeh(),
            RptFaktorTozieNashodehModel.COLUMN_FullNameForoshandeh(),
            RptFaktorTozieNashodehModel.COLUMN_CodeMoshtary(),
            RptFaktorTozieNashodehModel.COLUMN_NameMoshtary(),
            RptFaktorTozieNashodehModel.COLUMN_ShomarehDarkhast(),
            RptFaktorTozieNashodehModel.COLUMN_TarikhDarkhast(),
            RptFaktorTozieNashodehModel.COLUMN_TarikhDarkhastWithSlash(),
            RptFaktorTozieNashodehModel.COLUMN_SaatDarkhast(),
            RptFaktorTozieNashodehModel.COLUMN_ShomarehFaktor(),
            RptFaktorTozieNashodehModel.COLUMN_TarikhFaktorWithSlash(),
            RptFaktorTozieNashodehModel.COLUMN_RoundMablaghKhalesFaktor(),
            RptFaktorTozieNashodehModel.COLUMN_CodeNoeVorod(),
            RptFaktorTozieNashodehModel.COLUMN_txtCodeNoeVorod(),
            RptFaktorTozieNashodehModel.COLUMN_CodeVazeiat(),
            RptFaktorTozieNashodehModel.COLUMN_txtCodeVazeiat(),
            RptFaktorTozieNashodehModel.COLUMN_TarikhErsal(),
            RptFaktorTozieNashodehModel.COLUMN_NameMamorPakhsh()
        };
    }
    public void fetchAllrptFaktorTozieNashodehGrpc(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {

        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdarGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                RptNotDistributedInvoiceGrpc.RptNotDistributedInvoiceBlockingStub remainingInventoryBlockingStub = RptNotDistributedInvoiceGrpc.newBlockingStub(managedChannel);
                RptNotDistributedInvoiceRequest RptNotDistributedInvoiceRequest = com.saphamrah.protos.RptNotDistributedInvoiceRequest.newBuilder().setSalesManID(ccForoshandeh).build();
                Callable<RptNotDistributedInvoiceReplyList> RptNotDistributedInvoiceReplyListCallable = () -> remainingInventoryBlockingStub.getRptNotDistributedInvoice(RptNotDistributedInvoiceRequest);
                RxAsync.makeObservable(RptNotDistributedInvoiceReplyListCallable)
                        .map(RptNotDistributedInvoiceReplyList ->  {
                            ArrayList<RptFaktorTozieNashodehModel> models = new ArrayList<>();
                            for (RptNotDistributedInvoiceReply reply : RptNotDistributedInvoiceReplyList.getRptNotDistributedInvoicesList()) {
                                RptFaktorTozieNashodehModel model = new RptFaktorTozieNashodehModel();

                                model.setSharhGorohForosh(reply.getSellGroupDescription());
                                model.setCcForoshandeh(reply.getSalesManID());
                                model.setSharhForoshandeh(reply.getSalesManDescription());
                                model.setFullNameForoshandeh(reply.getSalesManFullName());
                                model.setCcMoshtary(reply.getCustomerId());
                                model.setCodeMoshtary(reply.getCustomerCode());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setShomarehDarkhast(reply.getRequestNumber());
                                model.setTarikhDarkhast(reply.getRequestDate());
                                model.setTarikhDarkhastWithSlash(reply.getRequestDateWithSlash());
                                model.setSaatDarkhast(reply.getRequestTime());
                                model.setShomarehFaktor(reply.getInvoiceNumber());
                                model.setTarikhFaktor(reply.getInvoiceDate());
                                model.setTarikhFaktorWithSlash(reply.getInvoiceDateWithSlash());
                                model.setCodeVazeiat(reply.getStatusCode());
                                model.setTxtCodeVazeiat(reply.getStatusCodeTxt());
                                model.setRoundMablaghKhalesFaktor(reply.getPureInvoicePriceRound());
                                model.setCodeNoeVorod(reply.getEnteryTypeCode());
                                model.setTarikhErsal(reply.getSendDate());
                                model.setNameMamorPakhsh(reply.getDistributerName());
                                model.setCcDarkhastFaktor(reply.getInvoiceRequestID());
                                model.setCcRpt_FaktorTozieNashodeh(reply.getRptNotDistributedInvoiceID());
                                model.setCcMarkazForosh(reply.getSellCenterID());
                                model.setCcGorohForosh(reply.getSellGroupID());
                                model.setNameMarkazForosh(reply.getSellCenterName());


                                models.add(model);

                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptFaktorTozieNashodehModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptFaktorTozieNashodehModel> models) {
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
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdarGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }
    public void fetchAllrptFaktorTozieNashodeh(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        //serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptFaktorTozieNashodehDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptFaktorTozieNashodeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllrptFaktorTozieNashodehResult> call = apiServiceGet.getAllrptFaktorTozieNashodeh(ccForoshandeh);
            call.enqueue(new Callback<GetAllrptFaktorTozieNashodehResult>()
            {
                @Override
                public void onResponse(Call<GetAllrptFaktorTozieNashodehResult> call, Response<GetAllrptFaktorTozieNashodehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptFaktorTozieNashodehDAO.class.getSimpleName(), "", "fetchAllrptFaktorTozieNashodeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllrptFaktorTozieNashodehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptFaktorTozieNashodehDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptFaktorTozieNashodeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptFaktorTozieNashodehDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptFaktorTozieNashodeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptFaktorTozieNashodehDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptFaktorTozieNashodeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptFaktorTozieNashodehDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptFaktorTozieNashodeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptFaktorTozieNashodehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptFaktorTozieNashodehDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptFaktorTozieNashodeh", "onFailure");
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

    public boolean insertGroup(ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptFaktorTozieNashodehModel rptFaktorTozieNashodehModel : rptFaktorTozieNashodehModels)
            {
                ContentValues contentValues = modelToContentvalue(rptFaktorTozieNashodehModel);
                db.insertOrThrow(RptFaktorTozieNashodehModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptFaktorTozieNashodehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptFaktorTozieNashodehDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<RptFaktorTozieNashodehModel> getAll()
    {
        ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptFaktorTozieNashodehModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptFaktorTozieNashodehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptFaktorTozieNashodehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptFaktorTozieNashodehDAO" , "" , "getAll" , "");
        }
        return rptFaktorTozieNashodehModels;
    }

    public ArrayList<RptFaktorTozieNashodehModel> getAllWithSum()
    {
        ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels = new ArrayList<>();
        try
        {
            String query = " SELECT 0 AS id, NameMoshtary, CodeMoshtary, RoundMablaghKhalesFaktor, CodeVazeiat, txtCodeVazeiat, \n" +
                    " TarikhFaktorWithSlash, ShomarehFaktor, TarikhErsal, NameMamorPakhsh \n" +
                    " FROM Rpt_FaktorTozieNashodeh  \n" +
                    " UNION ALL \n" +
                    " SELECT 1 AS id, 'جمع', '', Sum(RoundMablaghKhalesFaktor) AS RoundMablaghKhalesFaktor, 0, '', '', 0, '', '' \n" +
                    " FROM Rpt_FaktorTozieNashodeh \n" +
                    " ORDER BY id,TarikhFaktorWithSlash, ShomarehFaktor";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptFaktorTozieNashodehModels = cursorToModelWithSum(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptFaktorTozieNashodehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptFaktorTozieNashodehDAO" , "" , "getAllWithSum" , "");
        }
        return rptFaktorTozieNashodehModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptFaktorTozieNashodehModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptFaktorTozieNashodehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptFaktorTozieNashodehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptFaktorTozieNashodehModel rptFaktorTozieNashodehModel)
    {
        ContentValues contentValues = new ContentValues();

        if (rptFaktorTozieNashodehModel.getCcRpt_FaktorTozieNashodeh() > 0)
        {
            contentValues.put(RptFaktorTozieNashodehModel.COLUMN_ccRpt_FaktorTozieNashodeh() , rptFaktorTozieNashodehModel.getCcRpt_FaktorTozieNashodeh());
        }
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_ccDarkhastFaktor() , rptFaktorTozieNashodehModel.getCcDarkhastFaktor());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_ccMarkazForosh() , rptFaktorTozieNashodehModel.getCcMarkazForosh());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_ccGorohForosh() , rptFaktorTozieNashodehModel.getCcGorohForosh());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_ccForoshandeh() , rptFaktorTozieNashodehModel.getCcForoshandeh());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_ccMoshtary() , rptFaktorTozieNashodehModel.getCcMoshtary());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_NameMarkazForosh() , rptFaktorTozieNashodehModel.getNameMarkazForosh());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_SharhGorohForosh() , rptFaktorTozieNashodehModel.getSharhGorohForosh());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_SharhForoshandeh() , rptFaktorTozieNashodehModel.getSharhForoshandeh());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_FullNameForoshandeh() , rptFaktorTozieNashodehModel.getFullNameForoshandeh());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_CodeMoshtary() , rptFaktorTozieNashodehModel.getCodeMoshtary());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_NameMoshtary() , rptFaktorTozieNashodehModel.getNameMoshtary());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_ShomarehDarkhast() , rptFaktorTozieNashodehModel.getShomarehDarkhast());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_TarikhDarkhast() , rptFaktorTozieNashodehModel.getTarikhDarkhast());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_TarikhDarkhastWithSlash() , rptFaktorTozieNashodehModel.getTarikhDarkhastWithSlash());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_SaatDarkhast() , rptFaktorTozieNashodehModel.getSaatDarkhast());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_ShomarehFaktor() , rptFaktorTozieNashodehModel.getShomarehFaktor());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_TarikhFaktorWithSlash() , rptFaktorTozieNashodehModel.getTarikhFaktorWithSlash());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_RoundMablaghKhalesFaktor() , rptFaktorTozieNashodehModel.getRoundMablaghKhalesFaktor());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_CodeNoeVorod() , rptFaktorTozieNashodehModel.getCodeNoeVorod());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_txtCodeNoeVorod() , rptFaktorTozieNashodehModel.getTxtCodeNoeVorod());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_CodeVazeiat() , rptFaktorTozieNashodehModel.getCodeVazeiat());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_txtCodeVazeiat() , rptFaktorTozieNashodehModel.getTxtCodeVazeiat());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_TarikhErsal() , rptFaktorTozieNashodehModel.getTarikhErsal());
        contentValues.put(RptFaktorTozieNashodehModel.COLUMN_NameMamorPakhsh() , rptFaktorTozieNashodehModel.getNameMamorPakhsh());

        return contentValues;
    }


    private ArrayList<RptFaktorTozieNashodehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptFaktorTozieNashodehModel rptFaktorTozieNashodehModel = new RptFaktorTozieNashodehModel();

            rptFaktorTozieNashodehModel.setCcRpt_FaktorTozieNashodeh(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ccRpt_FaktorTozieNashodeh())));
            rptFaktorTozieNashodehModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ccDarkhastFaktor())));
            rptFaktorTozieNashodehModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ccMarkazForosh())));
            rptFaktorTozieNashodehModel.setCcGorohForosh(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ccGorohForosh())));
            rptFaktorTozieNashodehModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ccForoshandeh())));
            rptFaktorTozieNashodehModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ccMoshtary())));
            rptFaktorTozieNashodehModel.setNameMarkazForosh(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_NameMarkazForosh())));
            rptFaktorTozieNashodehModel.setSharhGorohForosh(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_SharhGorohForosh())));
            rptFaktorTozieNashodehModel.setSharhForoshandeh(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_SharhForoshandeh())));
            rptFaktorTozieNashodehModel.setFullNameForoshandeh(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_FullNameForoshandeh())));
            rptFaktorTozieNashodehModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_CodeMoshtary())));
            rptFaktorTozieNashodehModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_NameMoshtary())));
            rptFaktorTozieNashodehModel.setShomarehDarkhast(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ShomarehDarkhast())));
            rptFaktorTozieNashodehModel.setTarikhDarkhast(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_TarikhDarkhast())));
            rptFaktorTozieNashodehModel.setTarikhDarkhastWithSlash(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_TarikhDarkhastWithSlash())));
            rptFaktorTozieNashodehModel.setSaatDarkhast(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_SaatDarkhast())));
            rptFaktorTozieNashodehModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ShomarehFaktor())));
            rptFaktorTozieNashodehModel.setTarikhFaktorWithSlash(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_TarikhFaktorWithSlash())));
            rptFaktorTozieNashodehModel.setRoundMablaghKhalesFaktor(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_RoundMablaghKhalesFaktor())));
            rptFaktorTozieNashodehModel.setCodeNoeVorod(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_CodeNoeVorod())));
            rptFaktorTozieNashodehModel.setTxtCodeNoeVorod(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_txtCodeNoeVorod())));
            rptFaktorTozieNashodehModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_CodeVazeiat())));
            rptFaktorTozieNashodehModel.setTxtCodeVazeiat(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_txtCodeVazeiat())));
            rptFaktorTozieNashodehModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_TarikhErsal())));
            rptFaktorTozieNashodehModel.setNameMamorPakhsh(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_NameMamorPakhsh())));

            rptFaktorTozieNashodehModels.add(rptFaktorTozieNashodehModel);
            cursor.moveToNext();
        }
        return rptFaktorTozieNashodehModels;
    }

    private ArrayList<RptFaktorTozieNashodehModel> cursorToModelWithSum(Cursor cursor)
    {
        ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptFaktorTozieNashodehModel rptFaktorTozieNashodehModel = new RptFaktorTozieNashodehModel();

            rptFaktorTozieNashodehModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_CodeMoshtary())));
            rptFaktorTozieNashodehModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_NameMoshtary())));
            rptFaktorTozieNashodehModel.setRoundMablaghKhalesFaktor(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_RoundMablaghKhalesFaktor())));
            rptFaktorTozieNashodehModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_CodeVazeiat())));
            rptFaktorTozieNashodehModel.setTxtCodeVazeiat(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_txtCodeVazeiat())));
            rptFaktorTozieNashodehModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_ShomarehFaktor())));
            rptFaktorTozieNashodehModel.setTarikhFaktorWithSlash(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_TarikhFaktorWithSlash())));
            rptFaktorTozieNashodehModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_TarikhErsal())));
            rptFaktorTozieNashodehModel.setNameMamorPakhsh(cursor.getString(cursor.getColumnIndex(RptFaktorTozieNashodehModel.COLUMN_NameMamorPakhsh())));

            rptFaktorTozieNashodehModels.add(rptFaktorTozieNashodehModel);
            cursor.moveToNext();
        }
        return rptFaktorTozieNashodehModels;
    }

}
