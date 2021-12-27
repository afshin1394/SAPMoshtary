package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.RptSanadModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllrptListAsnadForoshandehResult;
import com.saphamrah.protos.RptDocumentGrpc;
import com.saphamrah.protos.RptDocumentReply;
import com.saphamrah.protos.RptDocumentReplyList;
import com.saphamrah.protos.RptDocumentRequest;

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

public class RptSanadDAO
{
    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "RptSanadDAO";


    public RptSanadDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptSanadDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptSanadModel.COLUMN_ccRpt_Sanad(),
            RptSanadModel.COLUMN_ccDarkhastFaktor(),
            RptSanadModel.COLUMN_ccMoshtary(),
            RptSanadModel.COLUMN_NameMoshtary(),
            RptSanadModel.COLUMN_CodeMoshtaryOld(),
            RptSanadModel.COLUMN_ShomarehFaktor(),
            RptSanadModel.COLUMN_TarikhFaktor(),
            RptSanadModel.COLUMN_TarikhErsal(),
            RptSanadModel.COLUMN_TarikhSanad(),
            RptSanadModel.COLUMN_MablaghKhalesFaktor(),
            RptSanadModel.COLUMN_ShomarehSanad(),
            RptSanadModel.COLUMN_MablaghCheck(),
            RptSanadModel.COLUMN_MablaghTakhsis(),
            RptSanadModel.COLUMN_CodeVazeiatDariaftPardakht(),
            RptSanadModel.COLUMN_ccMarkazForosh(),
            RptSanadModel.COLUMN_ccGorohForosh(),
            RptSanadModel.COLUMN_ccForoshandeh()
        };
    }

    public void fetchAllRptSanadGrpc(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdarGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                RptDocumentGrpc.RptDocumentBlockingStub rptDocumentBlockingStub = RptDocumentGrpc.newBlockingStub(managedChannel);
                RptDocumentRequest rptDocumentRequest = RptDocumentRequest.newBuilder().setSalesManID(ccForoshandeh).build();
                Callable<RptDocumentReplyList> rptDocumentReplyListCallable = () -> rptDocumentBlockingStub.getRptDocument(rptDocumentRequest);
                RxAsync.makeObservable(rptDocumentReplyListCallable)
                        .map(rptDocumentReplyList ->  {
                            ArrayList<RptSanadModel> models = new ArrayList<>();
                            for (RptDocumentReply reply : rptDocumentReplyList.getRptDocumentsList()) {
                                RptSanadModel model = new RptSanadModel();

                                model.setCcForoshandeh(reply.getSalesManID());
                                model.setCcDarkhastFaktor(reply.getInvoiceRequestID());
                                model.setCcGorohForosh(reply.getSellGroupID());
                                model.setCcMarkazForosh(reply.getSellCenterID());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setCodeMoshtaryOld(reply.getOldCustomerCode());
                                model.setCodeVazeiatDariaftPardakht(reply.getReceivingPaymentStatusCode());
                                model.setMablaghCheck(reply.getCheckPrice());
                                model.setMablaghKhalesFaktor(reply.getPureInvoicePrice());
                                model.setMablaghTakhsis(reply.getSpecifiedPrice());
                                model.setShomarehSanad(reply.getDocumentNumber());
                                model.setTarikhErsal(reply.getSendDate());
                                model.setTarikhFaktor(reply.getInvoiceDate());
                                model.setTarikhSanad(reply.getDocumentDate());
                                model.setShomarehFaktor(reply.getInvoiceNumber());
                                models.add(model);

                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptSanadModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptSanadModel> rptSanadModels) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), RptMandehdarDAO.class.getSimpleName(), activityNameForLog, "fetchAllMandehdarGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }

    public void fetchAllRptSanad(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchAllRptSanad", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllrptListAsnadForoshandehResult> call = apiServiceGet.getAllRptSanad(ccForoshandeh);
            call.enqueue(new Callback<GetAllrptListAsnadForoshandehResult>() {
                @Override
                public void onResponse(Call<GetAllrptListAsnadForoshandehResult> call, Response<GetAllrptListAsnadForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchAllRptSanad", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllrptListAsnadForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchAllRptSanad", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchAllRptSanad", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchAllRptSanad", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchAllRptSanad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptListAsnadForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchAllRptSanad", "onFailure");
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

    public boolean insertGroup(ArrayList<RptSanadModel> rptSanadModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptSanadModel rptSanadModel : rptSanadModels)
            {
                ContentValues contentValues = modelToContentvalue(rptSanadModel);
                db.insertOrThrow(RptSanadModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptSanadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptSanadDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptSanadModel> getAll()
    {
        ArrayList<RptSanadModel> rptSanadModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptSanadModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptSanadModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptSanadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptSanadDAO" , "" , "getAll" , "");
        }
        return rptSanadModels;
    }

    public float getSumAsnadByccMoshtary(int ccMoshtary)
    {
        float sumAsnad = 0;
        String query = " Select Sum(MablaghCheck) AS MablaghCheck From (SELECT Distinct TarikhSanad, ShomarehSanad, MablaghCheck "
                + "	FROM Rpt_Sanad Where ccMoshtary=" + ccMoshtary +")" ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            if (cursor.getCount() != 0)
            {
                sumAsnad = cursor.getFloat(0);
            }
            cursor.close();
        }
        db.close();
        return sumAsnad;
    }

    public int getCountAsnadByccMoshtary(int ccMoshtary)
    {
        int count = 0;
        String query = " Select count(ShomarehSanad) From (SELECT Distinct TarikhSanad, ShomarehSanad, MablaghCheck "
                + "	FROM Rpt_Sanad Where ccMoshtary=" + ccMoshtary +")" ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            if (cursor.getCount() != 0)
            {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getModatAsnadByccMoshtary(int ccMoshtary)
    {
        int count = 0;
        String query = "select julianday(Min(TarikhSanad)) - julianday('now') from Rpt_Sanad where ccMoshtary = " + ccMoshtary;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            if (cursor.getCount() != 0)
            {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        db.close();
        return count;
    }

    public float getSumAsnadByccForoshandeh(int ccForoshandeh)
    {
        float sumAsnad = 0;
        String query = " Select Sum(MablaghCheck) AS MablaghCheck From (SELECT Distinct TarikhSanad, ShomarehSanad, MablaghCheck "
                + "	FROM Rpt_Sanad Where ccForoshandeh=" + ccForoshandeh + " and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1))";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            if (cursor.getCount() != 0)
            {
                sumAsnad = cursor.getFloat(0);
            }
            cursor.close();
        }
        db.close();
        return sumAsnad;
    }

    public int getCountAsnadByccForoshandeh(int ccForoshandeh)
    {
        int count = 0;
        String query = " Select count(ShomarehSanad) From (SELECT Distinct TarikhSanad, ShomarehSanad, MablaghCheck "
                + "	FROM Rpt_Sanad Where ccForoshandeh=" + ccForoshandeh +" and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1)) " ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            if (cursor.getCount() != 0)
            {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getModatAsnadByccForoshandeh(int ccForoshandeh)
    {
        int count = 0;
        String query = "select julianday('now') - julianday(Min(TarikhSanad)) from Rpt_Sanad where ccForoshandeh = " + ccForoshandeh + " and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1)";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            if (cursor.getCount() != 0)
            {
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
            db.delete(RptSanadModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptSanadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptSanadDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptSanadModel rptSanadModel)
    {
        ContentValues contentValues = new ContentValues();

        if (rptSanadModel.getCcRpt_Sanad() > 0)
        {
            contentValues.put(RptSanadModel.COLUMN_ccRpt_Sanad() , rptSanadModel.getCcRpt_Sanad());
        }
        contentValues.put(RptSanadModel.COLUMN_ccDarkhastFaktor() , rptSanadModel.getCcDarkhastFaktor());
        contentValues.put(RptSanadModel.COLUMN_ccMoshtary() , rptSanadModel.getCcMoshtary());
        contentValues.put(RptSanadModel.COLUMN_NameMoshtary() , rptSanadModel.getNameMoshtary());
        contentValues.put(RptSanadModel.COLUMN_CodeMoshtaryOld() , rptSanadModel.getCodeMoshtaryOld());
        contentValues.put(RptSanadModel.COLUMN_ShomarehFaktor() , rptSanadModel.getShomarehFaktor());
        contentValues.put(RptSanadModel.COLUMN_TarikhFaktor() , rptSanadModel.getTarikhFaktor());
        contentValues.put(RptSanadModel.COLUMN_TarikhErsal() , rptSanadModel.getTarikhErsal());
        contentValues.put(RptSanadModel.COLUMN_TarikhSanad() , rptSanadModel.getTarikhSanad());
        contentValues.put(RptSanadModel.COLUMN_MablaghKhalesFaktor() , rptSanadModel.getMablaghKhalesFaktor());
        contentValues.put(RptSanadModel.COLUMN_ShomarehSanad() , rptSanadModel.getShomarehSanad());
        contentValues.put(RptSanadModel.COLUMN_MablaghCheck() , rptSanadModel.getMablaghCheck());
        contentValues.put(RptSanadModel.COLUMN_MablaghTakhsis() , rptSanadModel.getMablaghTakhsis());
        contentValues.put(RptSanadModel.COLUMN_CodeVazeiatDariaftPardakht() , rptSanadModel.getCodeVazeiatDariaftPardakht());
        contentValues.put(RptSanadModel.COLUMN_ccMarkazForosh() , rptSanadModel.getCcMarkazForosh());
        contentValues.put(RptSanadModel.COLUMN_ccGorohForosh() , rptSanadModel.getCcGorohForosh());
        contentValues.put(RptSanadModel.COLUMN_ccForoshandeh() , rptSanadModel.getCcForoshandeh());

        return contentValues;
    }


    private ArrayList<RptSanadModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptSanadModel> rptSanadModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptSanadModel rptSanadModel = new RptSanadModel();

            rptSanadModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(RptSanadModel.COLUMN_ccDarkhastFaktor())));
            rptSanadModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(RptSanadModel.COLUMN_ccMoshtary())));
            rptSanadModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptSanadModel.COLUMN_NameMoshtary())));
            rptSanadModel.setCodeMoshtaryOld(cursor.getString(cursor.getColumnIndex(RptSanadModel.COLUMN_CodeMoshtaryOld())));
            rptSanadModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(RptSanadModel.COLUMN_ShomarehFaktor())));
            rptSanadModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(RptSanadModel.COLUMN_TarikhFaktor())));
            rptSanadModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(RptSanadModel.COLUMN_TarikhErsal())));
            rptSanadModel.setTarikhSanad(cursor.getString(cursor.getColumnIndex(RptSanadModel.COLUMN_TarikhSanad())));
            rptSanadModel.setMablaghKhalesFaktor(cursor.getFloat(cursor.getColumnIndex(RptSanadModel.COLUMN_MablaghKhalesFaktor())));
            rptSanadModel.setShomarehSanad(cursor.getString(cursor.getColumnIndex(RptSanadModel.COLUMN_ShomarehSanad())));
            rptSanadModel.setMablaghCheck(cursor.getFloat(cursor.getColumnIndex(RptSanadModel.COLUMN_MablaghCheck())));
            rptSanadModel.setMablaghTakhsis(cursor.getFloat(cursor.getColumnIndex(RptSanadModel.COLUMN_MablaghTakhsis())));
            rptSanadModel.setCodeVazeiatDariaftPardakht(cursor.getInt(cursor.getColumnIndex(RptSanadModel.COLUMN_CodeVazeiatDariaftPardakht())));
            rptSanadModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(RptSanadModel.COLUMN_ccMarkazForosh())));
            rptSanadModel.setCcGorohForosh(cursor.getInt(cursor.getColumnIndex(RptSanadModel.COLUMN_ccGorohForosh())));
            rptSanadModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(RptSanadModel.COLUMN_ccForoshandeh())));

            rptSanadModels.add(rptSanadModel);
            cursor.moveToNext();
        }
        return rptSanadModels;
    }


    


}
