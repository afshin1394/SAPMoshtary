package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllrptVisitForoshandehResult;
import com.saphamrah.protos.RptCustomerVisitGrpc;
import com.saphamrah.protos.RptCustomerVisitReply;
import com.saphamrah.protos.RptCustomerVisitReplyList;
import com.saphamrah.protos.RptCustomerVisitRequest;

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

public class RptVisitForoshandehMoshtaryDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public RptVisitForoshandehMoshtaryDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptVisitForoshandehMoshtaryDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptVisitForoshandehMoshtaryModel.COLUMN_Radif(),
            RptVisitForoshandehMoshtaryModel.COLUMN_Olaviat(),
            RptVisitForoshandehMoshtaryModel.COLUMN_CodeMoshtary(),
            RptVisitForoshandehMoshtaryModel.COLUMN_NameMoshtary(),
            RptVisitForoshandehMoshtaryModel.COLUMN_Telephone(),
            RptVisitForoshandehMoshtaryModel.COLUMN_RialKharid(),
            RptVisitForoshandehMoshtaryModel.COLUMN_SaatVorodBeMaghazeh(),
            RptVisitForoshandehMoshtaryModel.COLUMN_SaatKhorojAzMaghazeh(),
            RptVisitForoshandehMoshtaryModel.COLUMN_ZamanDarMaghazeh(),
            RptVisitForoshandehMoshtaryModel.COLUMN_VazeiatMorajeh(),
            RptVisitForoshandehMoshtaryModel.COLUMN_VazeiatDarkhast(),
            RptVisitForoshandehMoshtaryModel.COLUMN_DalilDarkhastManfi(),
            RptVisitForoshandehMoshtaryModel.COLUMN_Tedad_AghlamFaktor(),
            RptVisitForoshandehMoshtaryModel.COLUMN_IsMorajeh()
        };
    }


    public void fetchAllrptVisitForoshandehMoshtaryGrpc(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptVisitForoshandehMoshtaryGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                RptCustomerVisitGrpc.RptCustomerVisitBlockingStub rptCustomerVisitBlockingStub = RptCustomerVisitGrpc.newBlockingStub(managedChannel);
                RptCustomerVisitRequest rptCustomerVisitRequest = RptCustomerVisitRequest.newBuilder().setSalesManID(ccForoshandeh).build();
                Callable<RptCustomerVisitReplyList> rptCustomerVisitReplyListCallable = () -> rptCustomerVisitBlockingStub.getRptCustomerVisit(rptCustomerVisitRequest);
                RxAsync.makeObservable(rptCustomerVisitReplyListCallable)
                        .map(rptCustomerVisitReplyList ->  {
                            ArrayList<RptVisitForoshandehMoshtaryModel> models = new ArrayList<>();
                            for (RptCustomerVisitReply reply : rptCustomerVisitReplyList.getRptCustomerVisitsList()) {
                                RptVisitForoshandehMoshtaryModel model = new RptVisitForoshandehMoshtaryModel();

                                model.setCodeMoshtary(reply.getCustomerCode());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setIsMorajeh(reply.getIsReferral());
                                model.setOlaviat(reply.getPriority());
                                model.setRadif(reply.getIndex());
                                model.setDalilDarkhastManfi(reply.getNonRequestReason());
                                model.setSaatKhorojAzMaghazeh(reply.getShopExitTime());
                                model.setSaatVorodBeMaghazeh(reply.getShopEntranceTime());
                                model.setTedad_AghlamFaktor(reply.getInvoiceItemsCount());
                                model.setTelephone(reply.getTelephone());
                                model.setVazeiatDarkhast(reply.getRequestStatus());
                                model.setVazeiatMorajeh(reply.getReferralStatus());
                                model.setZamanDarMaghazeh(reply.getTimePeriodInShop());
                                model.setRialKharid(reply.getPurchaseRial());


                                models.add(model);

                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptVisitForoshandehMoshtaryModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels) {
                                retrofitResponse.onSuccess(rptVisitForoshandehMoshtaryModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptVisitForoshandehMoshtaryGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }

    public void fetchAllrptVisitForoshandehMoshtary(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        //serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptVisitForoshandehMoshtary", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllrptVisitForoshandehResult> call = apiServiceGet.getAllrptVisitForoshandeh(ccForoshandeh);
            call.enqueue(new Callback<GetAllrptVisitForoshandehResult>() {
                @Override
                public void onResponse(Call<GetAllrptVisitForoshandehResult> call, Response<GetAllrptVisitForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), "", "fetchAllrptVisitForoshandehMoshtary", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllrptVisitForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptVisitForoshandehMoshtary", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptVisitForoshandehMoshtary", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptVisitForoshandehMoshtary", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptVisitForoshandehMoshtary", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptVisitForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptVisitForoshandehMoshtaryDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptVisitForoshandehMoshtary", "onFailure");
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

    public boolean insertGroup(ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptVisitForoshandehMoshtaryModel rptVisitForoshandehMoshtaryModel : rptVisitForoshandehMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(rptVisitForoshandehMoshtaryModel);
                db.insertOrThrow(RptVisitForoshandehMoshtaryModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptVisitForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptVisitForoshandehMoshtaryDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<RptVisitForoshandehMoshtaryModel> getAll()
    {
        ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptVisitForoshandehMoshtaryModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptVisitForoshandehMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptVisitForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptVisitForoshandehMoshtaryDAO" , "" , "getAll" , "");
        }
        return rptVisitForoshandehMoshtaryModels;
    }


    public ArrayList<RptVisitForoshandehMoshtaryModel> getForReportWithSum()
    {
        ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM( \n" +
                    " Select 0 AS id, Radif, Olaviat, CodeMoshtary, NameMoshtary, Telephone, RialKharid, \n" +
                    " SaatVorodBeMaghazeh, SaatKhorojAzMaghazeh, ZamanDarMaghazeh, VazeiatMorajeh, \n" +
                    " VazeiatDarkhast, DalilDarkhastManfi, Tedad_AghlamFaktor, IsMorajeh \n" +
                    " FROM   Rpt_VisitForoshandeh_Moshtary \n" +
                    " UNION ALL \n" +
                    " Select 1 AS id, 0, 0, '', '', '', SUM(RialKharid) AS RialKharid, '', '', '', '', '', '', \n" +
                    " Sum(Tedad_AghlamFaktor) AS Tedad_AghlamFaktor, 0 \n" +
                    " FROM   Rpt_VisitForoshandeh_Moshtary )A \n" +
                    " ORDER BY id , SaatVorodBeMaghazeh";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptVisitForoshandehMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptVisitForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptVisitForoshandehMoshtaryDAO" , "" , "getAll" , "");
        }
        return rptVisitForoshandehMoshtaryModels;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptVisitForoshandehMoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptVisitForoshandehMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptVisitForoshandehMoshtaryDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptVisitForoshandehMoshtaryModel rptVisitForoshandehMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_Radif() , rptVisitForoshandehMoshtaryModel.getRadif());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_Olaviat() , rptVisitForoshandehMoshtaryModel.getOlaviat());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_CodeMoshtary() , rptVisitForoshandehMoshtaryModel.getCodeMoshtary());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_NameMoshtary() , rptVisitForoshandehMoshtaryModel.getNameMoshtary());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_Telephone() , rptVisitForoshandehMoshtaryModel.getTelephone());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_RialKharid() , rptVisitForoshandehMoshtaryModel.getRialKharid());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_SaatVorodBeMaghazeh() , rptVisitForoshandehMoshtaryModel.getSaatVorodBeMaghazeh());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_SaatKhorojAzMaghazeh() , rptVisitForoshandehMoshtaryModel.getSaatKhorojAzMaghazeh());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_ZamanDarMaghazeh() , rptVisitForoshandehMoshtaryModel.getZamanDarMaghazeh());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_VazeiatMorajeh() , rptVisitForoshandehMoshtaryModel.getVazeiatMorajeh());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_VazeiatDarkhast() , rptVisitForoshandehMoshtaryModel.getVazeiatDarkhast());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_DalilDarkhastManfi() , rptVisitForoshandehMoshtaryModel.getDalilDarkhastManfi());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_Tedad_AghlamFaktor() , rptVisitForoshandehMoshtaryModel.getTedad_AghlamFaktor());
        contentValues.put(RptVisitForoshandehMoshtaryModel.COLUMN_IsMorajeh() , rptVisitForoshandehMoshtaryModel.getIsMorajeh());

        return contentValues;
    }


    private ArrayList<RptVisitForoshandehMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptVisitForoshandehMoshtaryModel rptVisitForoshandehMoshtaryModel = new RptVisitForoshandehMoshtaryModel();

            rptVisitForoshandehMoshtaryModel.setRadif(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_Radif())));
            rptVisitForoshandehMoshtaryModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_Olaviat())));
            rptVisitForoshandehMoshtaryModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_CodeMoshtary())));
            rptVisitForoshandehMoshtaryModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_NameMoshtary())));
            rptVisitForoshandehMoshtaryModel.setTelephone(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_Telephone())));
            rptVisitForoshandehMoshtaryModel.setRialKharid(cursor.getDouble(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_RialKharid())));
            rptVisitForoshandehMoshtaryModel.setSaatVorodBeMaghazeh(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_SaatVorodBeMaghazeh())));
            rptVisitForoshandehMoshtaryModel.setSaatKhorojAzMaghazeh(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_SaatKhorojAzMaghazeh())));
            rptVisitForoshandehMoshtaryModel.setZamanDarMaghazeh(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_ZamanDarMaghazeh())));
            rptVisitForoshandehMoshtaryModel.setVazeiatMorajeh(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_VazeiatMorajeh())));
            rptVisitForoshandehMoshtaryModel.setVazeiatDarkhast(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_VazeiatDarkhast())));
            rptVisitForoshandehMoshtaryModel.setDalilDarkhastManfi(cursor.getString(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_DalilDarkhastManfi())));
            rptVisitForoshandehMoshtaryModel.setTedad_AghlamFaktor(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_Tedad_AghlamFaktor())));
            rptVisitForoshandehMoshtaryModel.setIsMorajeh(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehMoshtaryModel.COLUMN_IsMorajeh())));

            rptVisitForoshandehMoshtaryModels.add(rptVisitForoshandehMoshtaryModel);
            cursor.moveToNext();
        }
        return rptVisitForoshandehMoshtaryModels;
    }


    
}
