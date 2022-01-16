package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

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
import com.saphamrah.WebService.ServiceResponse.GetAllrptListMoavaghForoshandehResult;
import com.saphamrah.protos.RptRemainingInvoiceGrpc;
import com.saphamrah.protos.RptRemainingInvoiceReply;
import com.saphamrah.protos.RptRemainingInvoiceReplyList;
import com.saphamrah.protos.RptRemainingInvoiceRequest;

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

public class RptMandehdarDAO 
{
    
    private DBHelper dbHelper;
    private Context context;


    public RptMandehdarDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptMandehdarDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptMandehdarModel.COLUMN_ccRpt_Mandehdar(),
            RptMandehdarModel.COLUMN_ccDarkhastFaktor(),
            RptMandehdarModel.COLUMN_ccMoshtary(),
            RptMandehdarModel.COLUMN_NameMoshtary(),
            RptMandehdarModel.COLUMN_CodeMoshtary(),
            RptMandehdarModel.COLUMN_MablaghKhalesFaktor(),
            RptMandehdarModel.COLUMN_MablaghMandehMoshtary(),
            RptMandehdarModel.COLUMN_TarikhFaktor(),
            RptMandehdarModel.COLUMN_TarikhErsal(),
            RptMandehdarModel.COLUMN_ShomarehFaktor(),
            RptMandehdarModel.COLUMN_CodeVazeiat(),
            RptMandehdarModel.COLUMN_ccMarkazForosh(),
            RptMandehdarModel.COLUMN_ccGorohForosh(),
            RptMandehdarModel.COLUMN_ccForoshandeh(),
            RptMandehdarModel.COLUMN_NameMarkazForosh(),
            RptMandehdarModel.COLUMN_NameGorohForosh(),
            RptMandehdarModel.COLUMN_NameForoshandeh(),
            RptMandehdarModel.COLUMN_VaznFaktor(),
            RptMandehdarModel.COLUMN_CodeNoeVosolAsliMoshtary(),
            RptMandehdarModel.COLUMN_CodeNoeVosolFaktor()
        };
    }

    public void fetchAllMandehdarGrpc(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
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
                RptRemainingInvoiceGrpc.RptRemainingInvoiceBlockingStub remainingInventoryBlockingStub = RptRemainingInvoiceGrpc.newBlockingStub(managedChannel);
                RptRemainingInvoiceRequest rptRemainingInvoiceRequest = RptRemainingInvoiceRequest.newBuilder().setSalesManID(ccForoshandeh).build();
                Callable<RptRemainingInvoiceReplyList> rptRemainingInvoiceReplyListCallable = () -> remainingInventoryBlockingStub.getRptRemainingInvoice(rptRemainingInvoiceRequest);
                RxAsync.makeObservable(rptRemainingInvoiceReplyListCallable)
                        .map(rptRemainingInvoiceReplyList ->  {
                            ArrayList<RptMandehdarModel> models = new ArrayList<>();
                            for (RptRemainingInvoiceReply reply : rptRemainingInvoiceReplyList.getRptRemainingInvoicesList()) {
                                RptMandehdarModel model = new RptMandehdarModel();

                                model.setCcRpt_Mandehdar(reply.getRptRemainingID());
                                model.setCcDarkhastFaktor(reply.getInvoiceRequestID());
                                model.setCodeMoshtary(reply.getCustomerCode());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setCcGorohForosh(reply.getSellGroupID());
                                model.setCcMarkazForosh(reply.getSellCenterID());
                                model.setCodeNoeVosolAsliMoshtary(reply.getCustomerOriginialReceiptTypeCode());
                                model.setCodeNoeVosolFaktor(reply.getInvoiceReceiptTypeCode());
                                model.setCodeVazeiat(reply.getStatusCode());
                                model.setMablaghMandehMoshtary(reply.getCustomerRemainingAmount());
                                model.setMablaghKhalesFaktor(reply.getPureInvoicPrice());
                                model.setTarikhFaktor(reply.getInvoiceDate());
                                model.setNameForoshandeh(reply.getSalesManName());
                                model.setShomarehFaktor(reply.getInvoiceNumber());
                                model.setNameGorohForosh(reply.getSellGroupName());
                                model.setNameMarkazForosh(reply.getSellCenterName());
                                model.setTarikhErsal(reply.getSendDate());
                                model.setVaznFaktor(reply.getInvoiceWeight());
                                model.setCcForoshandeh(reply.getSalesManID());


                                models.add(model);

                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptMandehdarModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptMandehdarModel> rptMandehdarModels) {
                                retrofitResponse.onSuccess(rptMandehdarModels);
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

    public void fetchAllMandehdar(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        //serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllrptListMoavaghForoshandehResult> call = apiServiceGet.getAllrptListMoavaghForoshandeh(ccForoshandeh);
            call.enqueue(new Callback<GetAllrptListMoavaghForoshandehResult>() {
                @Override
                public void onResponse(Call<GetAllrptListMoavaghForoshandehResult> call, Response<GetAllrptListMoavaghForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptMandehdarDAO.class.getSimpleName(), "", "fetchAllMandehdar", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllrptListMoavaghForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptListMoavaghForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdar", "onFailure");
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

    public boolean insertGroup(ArrayList<RptMandehdarModel> rptMandehdarModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptMandehdarModel rptMandehdarModel : rptMandehdarModels)
            {
                ContentValues contentValues = modelToContentvalue(rptMandehdarModel);
                db.insertOrThrow(RptMandehdarModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptMandehdarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMandehdarDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptMandehdarModel> getAll()
    {
        ArrayList<RptMandehdarModel> rptMandehdarModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptMandehdarModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMandehdarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMandehdarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMandehdarDAO" , "" , "getAll" , "");
        }
        return rptMandehdarModels;
    }


    public ArrayList<RptMandehdarModel> getAllWithSum()
    {
        //if name moshtary == -1 -> this row is sum
        ArrayList<RptMandehdarModel> rptMandehdarModels = new ArrayList<>();
        String query = " SELECT * FROM ("
                +" SELECT 0 AS _id, CodeMoshtary, NameMoshtary, TarikhFaktor, TarikhErsal, ShomarehFaktor, "
                +"            MablaghKhalesFaktor AS MablaghFaktor , MablaghMandehMoshtary AS MablaghMandeh "
                +" FROM Rpt_MandehDar "
                +" UNION ALL "
                +" SELECT 1 AS _id, '' , '', '', '', '', SUM(MablaghKhalesFaktor) AS MablaghFaktor , SUM(MablaghMandehMoshtary) AS MablaghMandeh "
                +" FROM Rpt_MandehDar ) "
                +" ORDER BY _id ASC,TarikhFaktor DESC";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMandehdarModels = cursorToModelWithSum(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMandehdarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMandehdarDAO" , "" , "getAllWithSum" , "");
        }
        return rptMandehdarModels;
    }

    public int getTedadResid(int codeNoeVosolFaktor , int codeNoeVosolMoshtary)
    {
        int Tedad = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptMandehdarModel.TableName(), allColumns(), " CodeNoeVosolAsliMoshtary<>" + codeNoeVosolMoshtary + " AND CodeNoeVosolFaktor="+ codeNoeVosolFaktor,null, null, null,  null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                if(cursor.getCount() != 0)
                {
                    Tedad += 1;
                }
                cursor.close();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMandehdarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMandehdarDAO" , "" , "getTedadResid" , "");
        }
        return Tedad;
    }

    public float getSumMoavaghByccMoshtary(int ccMoshtary)
    {
        float sumMoavagh = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select sum(" + RptMandehdarModel.COLUMN_MablaghMandehMoshtary() + ") from " + RptMandehdarModel.TableName() + " where " + RptMandehdarModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMoavagh = cursor.getFloat(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMandehdarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMandehdarDAO" , "" , "getSumMoavaghByccMoshtary" , "");
        }
        return sumMoavagh;
    }

    public int getTedadFaktorMandehDar(int ccMoshtary)
    {
        int count = 0;
        String query = "SELECT count(" + RptMandehdarModel.COLUMN_ccRpt_Mandehdar() + ") FROM " + RptMandehdarModel.TableName() + " WHERE " + RptMandehdarModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " AND TarikhFaktor < date('now')";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
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
        return count;
    }

    public double getMablaghFaktorMandehDar(int ccMoshtary)
    {
        double mablagh = 0;
        String query = "SELECT SUM(" + RptMandehdarModel.COLUMN_MablaghMandehMoshtary() + ") FROM " + RptMandehdarModel.TableName() + " WHERE " + RptMandehdarModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " AND TarikhFaktor < date('now')";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if (cursor != null)
        {
            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                mablagh = cursor.getInt(0);
            }
            cursor.close();
        }
        db.close();
        return mablagh;
    }

    public int getMoavaghFaktorMandehDar(int ccMoshtary)
    {
        int count = 0;
        String query = "select julianday('now') - julianday(Min(TarikhFaktor)) AS Diff from Rpt_Mandehdar where ccMoshtary = " + ccMoshtary;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
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
        return count;
    }

    public float getSumMoavaghByccForoshandeh(int ccForoshandeh)
    {
        float sumMoavagh = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select sum(" + RptMandehdarModel.COLUMN_MablaghMandehMoshtary() + ") from " + RptMandehdarModel.TableName() + " where " + RptMandehdarModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh + " and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1)";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMoavagh = cursor.getFloat(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMandehdarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMandehdarDAO" , "" , "getSumMoavaghByccMoshtary" , "");
        }
        return sumMoavagh;
    }

    public int getTedadFaktorMandehDarByccForoshandeh(int ccForoshandeh)
    {
        int count = 0;
        String query = "SELECT count(" + RptMandehdarModel.COLUMN_ccRpt_Mandehdar() + ") FROM " + RptMandehdarModel.TableName() + " WHERE " + RptMandehdarModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh + " AND TarikhFaktor < date('now') and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1)";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
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
        return count;
    }

    public int getMoavaghFaktorMandehDarByccForoshandeh(int ccForoshandeh)
    {
        int count = 0;
        String query = "select julianday('now') - julianday(Min(TarikhFaktor)) AS Diff from Rpt_Mandehdar where ccForoshandeh = " + ccForoshandeh + " and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1)";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
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
        return count;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptMandehdarModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptMandehdarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMandehdarDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptMandehdarModel rptMandehdarModel)
    {
        ContentValues contentValues = new ContentValues();

        if (rptMandehdarModel.getCcRpt_Mandehdar() > 0)
        {
            contentValues.put(RptMandehdarModel.COLUMN_ccRpt_Mandehdar() , rptMandehdarModel.getCcRpt_Mandehdar());
        }
        contentValues.put(RptMandehdarModel.COLUMN_ccDarkhastFaktor() , rptMandehdarModel.getCcDarkhastFaktor());
        contentValues.put(RptMandehdarModel.COLUMN_ccMoshtary() , rptMandehdarModel.getCcMoshtary());
        contentValues.put(RptMandehdarModel.COLUMN_NameMoshtary() , rptMandehdarModel.getNameMoshtary());
        contentValues.put(RptMandehdarModel.COLUMN_CodeMoshtary() , rptMandehdarModel.getCodeMoshtary());
        contentValues.put(RptMandehdarModel.COLUMN_MablaghKhalesFaktor() , rptMandehdarModel.getMablaghKhalesFaktor());
        contentValues.put(RptMandehdarModel.COLUMN_MablaghMandehMoshtary() , rptMandehdarModel.getMablaghMandehMoshtary());
        contentValues.put(RptMandehdarModel.COLUMN_TarikhFaktor() , rptMandehdarModel.getTarikhFaktor());
        contentValues.put(RptMandehdarModel.COLUMN_TarikhErsal() , rptMandehdarModel.getTarikhErsal());
        contentValues.put(RptMandehdarModel.COLUMN_ShomarehFaktor() , rptMandehdarModel.getShomarehFaktor());
        contentValues.put(RptMandehdarModel.COLUMN_CodeVazeiat() , rptMandehdarModel.getCodeVazeiat());
        contentValues.put(RptMandehdarModel.COLUMN_ccMarkazForosh() , rptMandehdarModel.getCcMarkazForosh());
        contentValues.put(RptMandehdarModel.COLUMN_ccGorohForosh() , rptMandehdarModel.getCcGorohForosh());
        contentValues.put(RptMandehdarModel.COLUMN_ccForoshandeh() , rptMandehdarModel.getCcForoshandeh());
        contentValues.put(RptMandehdarModel.COLUMN_NameMarkazForosh() , rptMandehdarModel.getNameMarkazForosh());
        contentValues.put(RptMandehdarModel.COLUMN_NameGorohForosh() , rptMandehdarModel.getNameGorohForosh());
        contentValues.put(RptMandehdarModel.COLUMN_NameForoshandeh() , rptMandehdarModel.getNameForoshandeh());
        contentValues.put(RptMandehdarModel.COLUMN_VaznFaktor() , rptMandehdarModel.getVaznFaktor());
        contentValues.put(RptMandehdarModel.COLUMN_CodeNoeVosolAsliMoshtary() , rptMandehdarModel.getCodeNoeVosolAsliMoshtary());
        contentValues.put(RptMandehdarModel.COLUMN_CodeNoeVosolFaktor() , rptMandehdarModel.getCodeNoeVosolFaktor());

        return contentValues;
    }


    private ArrayList<RptMandehdarModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptMandehdarModel> rptMandehdarModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptMandehdarModel rptMandehdarModel = new RptMandehdarModel();

            rptMandehdarModel.setCcRpt_Mandehdar(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_ccRpt_Mandehdar())));
            rptMandehdarModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_ccDarkhastFaktor())));
            rptMandehdarModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_ccMoshtary())));
            rptMandehdarModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_NameMoshtary())));
            rptMandehdarModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_CodeMoshtary())));
            rptMandehdarModel.setMablaghKhalesFaktor(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_MablaghKhalesFaktor())));
            rptMandehdarModel.setMablaghMandehMoshtary(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_MablaghMandehMoshtary())));
            rptMandehdarModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_TarikhFaktor())));
            rptMandehdarModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_TarikhErsal())));
            rptMandehdarModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_ShomarehFaktor())));
            rptMandehdarModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_CodeVazeiat())));
            rptMandehdarModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_ccMarkazForosh())));
            rptMandehdarModel.setCcGorohForosh(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_ccGorohForosh())));
            rptMandehdarModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_ccForoshandeh())));
            rptMandehdarModel.setNameMarkazForosh(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_NameMarkazForosh())));
            rptMandehdarModel.setNameGorohForosh(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_NameGorohForosh())));
            rptMandehdarModel.setNameForoshandeh(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_NameForoshandeh())));
            rptMandehdarModel.setVaznFaktor(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_VaznFaktor())));
            rptMandehdarModel.setCodeNoeVosolAsliMoshtary(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_CodeNoeVosolAsliMoshtary())));
            rptMandehdarModel.setCodeNoeVosolFaktor(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_CodeNoeVosolFaktor())));

            rptMandehdarModels.add(rptMandehdarModel);
            cursor.moveToNext();
        }
        return rptMandehdarModels;
    }


    private ArrayList<RptMandehdarModel> cursorToModelWithSum(Cursor cursor)
    {
        ArrayList<RptMandehdarModel> rptMandehdarModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptMandehdarModel rptMandehdarModel = new RptMandehdarModel();

            rptMandehdarModel.setCcRpt_Mandehdar(cursor.getInt(cursor.getColumnIndex("_id")));
            rptMandehdarModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_NameMoshtary())));
            rptMandehdarModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_CodeMoshtary())));
            rptMandehdarModel.setMablaghKhalesFaktor(cursor.getInt(cursor.getColumnIndex("MablaghFaktor")));
            rptMandehdarModel.setMablaghMandehMoshtary(cursor.getInt(cursor.getColumnIndex("MablaghMandeh")));
            rptMandehdarModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_TarikhFaktor())));
            rptMandehdarModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(RptMandehdarModel.COLUMN_TarikhErsal())));
            rptMandehdarModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(RptMandehdarModel.COLUMN_ShomarehFaktor())));

            rptMandehdarModels.add(rptMandehdarModel);
            cursor.moveToNext();
        }
        return rptMandehdarModels;
    }










}
