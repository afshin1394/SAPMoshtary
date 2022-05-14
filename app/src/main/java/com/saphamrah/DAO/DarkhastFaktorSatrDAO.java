package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.DataTableModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TakhfifHajmiTitrSatrModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetDarkhastFaktorSatrResult;
import com.saphamrah.protos.InvoiceRequestDraftRowGrpc;
import com.saphamrah.protos.InvoiceRequestDraftRowReply;
import com.saphamrah.protos.InvoiceRequestDraftRowReplyList;
import com.saphamrah.protos.InvoiceRequestDraftRowRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class DarkhastFaktorSatrDAO
{


    private DBHelper dbHelper;
    private Context context;


    public DarkhastFaktorSatrDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorSatrDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
                {
                        DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr(),
                        DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor(),
                        DarkhastFaktorSatrModel.COLUMN_ccTaminKonandeh(),
                        DarkhastFaktorSatrModel.COLUMN_ccKala(),
                        DarkhastFaktorSatrModel.COLUMN_ccKalaCode(),
                        DarkhastFaktorSatrModel.COLUMN_Tedad3(),
                        DarkhastFaktorSatrModel.COLUMN_CodeNoeKala(),
                        DarkhastFaktorSatrModel.COLUMN_ShomarehBach(),
                        DarkhastFaktorSatrModel.COLUMN_TarikhTolid(),
                        DarkhastFaktorSatrModel.COLUMN_MablaghForosh(),
                        DarkhastFaktorSatrModel.COLUMN_MablaghForoshKhalesKala(),
                        DarkhastFaktorSatrModel.COLUMN_MablaghTakhfifNaghdiVahed(),
                        DarkhastFaktorSatrModel.COLUMN_Maliat(),
                        DarkhastFaktorSatrModel.COLUMN_Avarez(),
                        DarkhastFaktorSatrModel.COLUMN_ccAfrad(),
                        DarkhastFaktorSatrModel.COLUMN_ExtraProp_IsOld(),
                        DarkhastFaktorSatrModel.COLUMN_TarikhEngheza(),
                        DarkhastFaktorSatrModel.COLUMN_ccAnbarMarjoee(),
                        DarkhastFaktorSatrModel.COLUMN_ccAnbarGhesmat(),
                        DarkhastFaktorSatrModel.COLUMN_GheymatMasrafKonandeh(),
                        DarkhastFaktorSatrModel.COLUMN_GheymatForoshAsli(),
                        DarkhastFaktorSatrModel.COLUMN_GheymatMasrafKonandehAsli(),
                        DarkhastFaktorSatrModel.COLUMN_GheymatKharid(),
                        DarkhastFaktorSatrModel.COLUMN_ExtraProp_Maliat(),
                        DarkhastFaktorSatrModel.COLUMN_ExtraProp_Avarez()

                };
    }


    public void fetchDarkhastFaktorSatrGrpc(final Context context, final String activityNameForLog, String noeHavaleFaktor, final String ccDarkhastFaktors, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorSatrDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorSatrGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                InvoiceRequestDraftRowGrpc.InvoiceRequestDraftRowBlockingStub blockingStub = InvoiceRequestDraftRowGrpc.newBlockingStub(managedChannel);
                InvoiceRequestDraftRowRequest request = InvoiceRequestDraftRowRequest.newBuilder().setInvoiceRequestsID(ccDarkhastFaktors).setDraftInvoiceType(noeHavaleFaktor).build();

                Callable<InvoiceRequestDraftRowReplyList> replyListCallable = () -> blockingStub.getInvoiceRequestDraftRow(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<DarkhastFaktorSatrModel> models = new ArrayList<>();
                            for (InvoiceRequestDraftRowReply reply : replyList.getInvoiceRequestDraftRowsList()) {
                                DarkhastFaktorSatrModel model = new DarkhastFaktorSatrModel();

                                model.setCcDarkhastFaktorSatr(reply.getRowInvoiceRequestID());
                                model.setCcDarkhastFaktor(reply.getInvoiceRequestID());
                                model.setCcTaminKonandeh(reply.getProviderID());
                                model.setCcKala(reply.getGoodID());
                                model.setCcKalaCode(reply.getGoodCodeID());
                                model.setTedad3(reply.getQueantity3());
                                model.setCodeNoeKala(reply.getGoodTypeCode());
                                model.setShomarehBach(reply.getBatchNumber());
                                model.setTarikhTolid(reply.getProductionDate());
                                model.setMablaghForosh(reply.getSellPrice());
                                model.setMablaghForoshKhalesKala(reply.getPureGoodSellPrice());
                                model.setMablaghTakhfifNaghdiVahed(reply.getUnitedCashDiscountPrice());
                                model.setMaliat(reply.getTax());
                                model.setAvarez(reply.getComplications());
                                model.setCcAfrad(reply.getPersonsID());
                                model.setTarikhEngheza(reply.getExpirationDate());
                                model.setCcAnbarMarjoee(reply.getReturnStoreID());
                                model.setCcAnbarGhesmat(reply.getStorePortionID());
                                model.setGheymatKharid(reply.getBuyPrice());
                                model.setGheymatMasrafKonandeh(reply.getConsumerPrice());
                                model.setGheymatForoshAsli(reply.getOriginalSellPrice());
                                model.setGheymatMasrafKonandehAsli(reply.getOrginalConsumerPrice());
                                model.setVazn(reply.getWeight());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<DarkhastFaktorSatrModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<DarkhastFaktorSatrModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ParameterDAO.class.getSimpleName(), activityNameForLog, "fetchMahalCodePostiGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }

    public void fetchDarkhastFaktorSatr(final Context context, final String activityNameForLog, String noeHavaleFaktor, final String ccDarkhastFaktors, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorSatrDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorSatr", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetDarkhastFaktorSatrResult> call = apiServiceGet.getDarkhastFaktorSatr(noeHavaleFaktor , ccDarkhastFaktors);
            call.enqueue(new Callback<GetDarkhastFaktorSatrResult>() {
                @Override
                public void onResponse(Call<GetDarkhastFaktorSatrResult> call, Response<GetDarkhastFaktorSatrResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, DarkhastFaktorSatrDAO.class.getSimpleName(), "", "fetchDarkhastFaktorSatr", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetDarkhastFaktorSatrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), DarkhastFaktorSatrDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorSatr", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), DarkhastFaktorSatrDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorSatrDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorSatr", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), DarkhastFaktorSatrDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorSatr", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetDarkhastFaktorSatrResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), DarkhastFaktorSatrDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorSatr", "onFailure");
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

    public boolean insertGroup(ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrModels)
            {
                Log.d("getProgram","darkhastFaktorSatrModel:"+darkhastFaktorSatrModel.toString());
                ContentValues contentValues = modelToContentvalue(darkhastFaktorSatrModel);
                db.insertOrThrow(DarkhastFaktorSatrModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(DarkhastFaktorSatrModel darkhastFaktorSatrModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(darkhastFaktorSatrModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(DarkhastFaktorSatrModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<DarkhastFaktorSatrModel> getAll()
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorSatrModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorSatrModels;
    }

    public ArrayList<DarkhastFaktorSatrModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorSatrModel.TableName(), allColumns(), DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " AND Tedad3 > 0", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorSatrModels;
    }

    public ArrayList<DarkhastFaktorSatrModel> getByccDarkhastFaktorAndccForoshandeh(long ccDarkhastFaktor, int ccForoshandeh, boolean MashmolMaliat)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " SELECT DFS.* \n" +
                    "FROM DarkhastFaktorSatr DFS\n" +
                    "     LEFT JOIN Kala K ON K.ccKalaCode = DFS.ccKalaCode AND K.ShomarehBach = DFS.ShomarehBach AND K.MablaghForosh= DFS.GheymatForoshAsli AND \n" +
                    "                                          K.MablaghMasrafKonandeh = DFS.GheymatMasrafKonandehAsli AND\n" +
                    "                                          K.TarikhTolid = DFS.TarikhTolid AND K.TarikhEngheza = DFS.TarikhEngheza AND K.ccTaminKonandeh = DFS.ccTaminKonandeh" +
                    "WHERE ccDarkhastFaktor = "+ ccDarkhastFaktor + " AND ccAfrad = " + ccForoshandeh;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrs = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByDarkhastFaktorForMohasebehJayezehTedadi" , "");
        }
        return darkhastFaktorSatrs;
    }

    public ArrayList<DarkhastFaktorSatrModel> getByccKalaCodeAndCcDarkhastFaktor(int ccKalaCode , long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorSatrModel.TableName(), allColumns(), DarkhastFaktorSatrModel.COLUMN_ccKalaCode() + " = " + ccKalaCode + " AND ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeKala = 1 ", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByccKalaCode" , "");
        }
        return darkhastFaktorSatrModels;
    }


    public ArrayList<DarkhastFaktorSatrModel> getByDarkhastFaktorForMohasebehJayezehTedadi(long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " SELECT ccKalaCode, Max(mablaghforosh) AS MablaghForosh, sum(tedad3) AS Tedad3  FROM DarkhastFaktorSatr WHERE ccDarkhastFaktor = "+ ccDarkhastFaktor + " AND " + " ccKalaCode IN(SELECT DISTINCT ccNoeField FROM JayezehSatr where NameNoeField = 1 AND ccJayezeh IN(SELECT ccJayezeh FROM Jayezeh)) group by ccKalaCode ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    DarkhastFaktorSatrModel darkhastFaktorSatrModel;
                    while (!cursor.isAfterLast())
                    {
                        darkhastFaktorSatrModel = new DarkhastFaktorSatrModel();

                        darkhastFaktorSatrModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKalaCode())));
                        darkhastFaktorSatrModel.setMablaghForosh(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghForosh())));
                        darkhastFaktorSatrModel.setTedad3(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Tedad3())));

                        darkhastFaktorSatrs.add(darkhastFaktorSatrModel);
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByDarkhastFaktorForMohasebehJayezehTedadi" , "");
        }
        return darkhastFaktorSatrs;
    }

    public String getListOfKalaAdamForoshInFaktor(long ccDarkhastFaktor)
    {
        String goodsName = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try
        {
            String query = "select k.NameKala from Kala k inner join DarkhastFaktorSatr df on k.ccKalaCode = df.ccKalaCode \n" +
                    " inner join KalaMojodi km on k.ccKalaCode = km.ccKalaCode where df.ccDarkhastFaktor = " + ccDarkhastFaktor + " and km.IsAdamForosh = 1";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        goodsName += "\n" + cursor.getString(0);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getListOfKalaAdamForoshInFaktor" , "");
        }
        return goodsName;
    }

    public ArrayList<DataTableModel> getByccDarkhastFaktorAndccKalaCode(long ccDarkhastFaktor , int ccKalaCode, int currentOlaviat, int ccTakhfifHajmi , int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> dataTableModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT A.ccDarkhastFaktorSatr, K.ccKalaCode, SUM(A.Tedad3) AS Tedad, SUM(A.Tedad3/ TedadDarKarton) AS TedadBox, SUM(A.Tedad3/ TedadDarBasteh) AS TedadPackage, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * " + gheymat + ") AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 * " + gheymat + ")-(select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + " AND ccKalaCode = " + ccKalaCode + " ) ) AS MablaghKol ";
            }
            query += " , " + gheymat;
            query += " , (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                     + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghTakhfif ";
            //query += " ,SUM(Tedad3 * K.VaznKhales) AS Vazn, COUNT(ccDarkhastFaktorSatr) AS TedadAghlam ";
            query += "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN (SELECT DISTINCT ccKalaCode, ccBrand, TedadDarKarton, TedadDarBasteh, VaznKhales FROM Kala) K ON A.ccKalaCode= K.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " and A.ccKalaCode = " + ccKalaCode + " GROUP BY A.ccKalaCode";
            //  + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " and A.ccKalaCode = " + ccKalaCode + " GROUP BY A.ccDarkhastFaktorSatr";
            Log.d("takhfif" , "takhfifKala query : " + query);


            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dataTableModels = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return dataTableModels;
    }
    public ArrayList<DataTableModel> getByccDarkhastFaktorAndccKalaCodeAndNoeGheymat(long ccDarkhastFaktor, int ccKalaCode, int noeGheymat, int currentOlaviat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> dataTableModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT A.ccDarkhastFaktorSatr, A.ccKalaCode, A.Tedad3 AS Tedad ";
            query += " , " + gheymat;
            query += " , ((SELECT SUM(MablaghTakhfif) from DarkhastFaktorSatrTakhfif WHERE ccDarkhastFaktorSatr=A.ccDarkhastFaktorSatr AND ExtraProp_Olaviat < " + currentOlaviat +")) ";
            query += "  FROM DarkhastFaktorSatr A "
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " and A.ccKalaCode = " + ccKalaCode
                    + " ORDER BY A.Tedad3 DESC ";
             Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    dataTableModels = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return dataTableModels;
    }

    public int getMablaghForoshByccKalaCode(int ccKalaCode, long ccDarkhastFaktor)
    {
        int mablaghForosh = 0;
        try
        {

            String query = "SELECT IFNULL(Sum(MablaghForosh)/Count(ccKalaCode),0) AS MablaghMiangin FROM " + DarkhastFaktorSatrModel.TableName() + " WHERE ccKalaCode  = " + ccKalaCode + " AND ccDarkhastFaktor= " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            Log.d("KalaDAO","query:" + query);
            if (cursor != null && cursor.moveToFirst())
            {
                if (cursor.getCount() > 0)
                {
                    mablaghForosh = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getByccKalaCode" , "");
        }
        return mablaghForosh;
    }

    public ArrayList<DarkhastFaktorSatrModel> getByccDarkhastFaktorForTakhfifHajmiKala(long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = new ArrayList<>();
        try
        {
//            String query = "select ccDarkhastFaktor, ccDarkhastFaktorSatr, sum(MablaghForosh) As MablaghForosh , sum(Tedad3) AS Tedad3 , ccKala , ccKalaCode from DarkhastFaktorSatr \n" +
//                    " where ccDarkhastFaktor = " + ccDarkhastFaktor + " group by ccKalaCode";

            String query = "SELECT ccDarkhastFaktor, ccDarkhastFaktorSatr, SUM(DFS.MablaghForosh) As MablaghForosh , SUM(Tedad3) AS Tedad3 , DFS.ccKala , DFS.ccKalaCode, Kala.VaznKhales AS Vazn\n" +
                    " From DarkhastFaktorSatr DFS \n" +
                    " LEFT JOIN (select Distinct cckalacode,VaznKhales from kala) Kala ON Kala.ccKalaCode = DFS.ccKalaCode \n" +
                    " WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " GROUP BY DFS.ccKalaCode";

            Log.d("DarkhastFaktorSatr","query:"+query );
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        DarkhastFaktorSatrModel darkhastFaktorSatrModel = new DarkhastFaktorSatrModel();
                        darkhastFaktorSatrModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor())));
                        darkhastFaktorSatrModel.setCcDarkhastFaktorSatr(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr())));
                        darkhastFaktorSatrModel.setMablaghForosh(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghForosh())));
                        darkhastFaktorSatrModel.setTedad3(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Tedad3())));
                        darkhastFaktorSatrModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKalaCode())));
                        darkhastFaktorSatrModel.setCcKala(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKala())));
                        darkhastFaktorSatrModel.setVazn(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Vazn())));

                        darkhastFaktorSatrModels.add(darkhastFaktorSatrModel);
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByccDarkhastFaktorForTakhfifHajmiKala" , "");
        }
        return darkhastFaktorSatrModels;
    }

    public ArrayList<DarkhastFaktorSatrModel> getByccDarkhastFaktorForTakhfifSenfiKala(long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = new ArrayList<>();
        try
        {
//            String query = "select ccDarkhastFaktor, ccDarkhastFaktorSatr, sum(MablaghForosh) As MablaghForosh , sum(Tedad3) AS Tedad3 , ccKala , ccKalaCode from DarkhastFaktorSatr \n" +
//                    " where ccDarkhastFaktor = " + ccDarkhastFaktor + " group by ccKalaCode";

            String query = "SELECT ccDarkhastFaktor, ccDarkhastFaktorSatr, SUM(DFS.MablaghForosh) As MablaghForosh , SUM(Tedad3) AS Tedad3 , DFS.ccKala , DFS.ccKalaCode, Kala.VaznKhales AS Vazn\n" +
                    " From DarkhastFaktorSatr DFS \n" +
                    " LEFT JOIN (select Distinct cckalacode,VaznKhales from kala) Kala ON Kala.ccKalaCode = DFS.ccKalaCode \n" +
                    " WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + " GROUP BY DFS.ccKalaCode";

            Log.d("DarkhastFaktorSatr","query:"+query );
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        DarkhastFaktorSatrModel darkhastFaktorSatrModel = new DarkhastFaktorSatrModel();
                        darkhastFaktorSatrModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor())));
                        darkhastFaktorSatrModel.setCcDarkhastFaktorSatr(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr())));
                        darkhastFaktorSatrModel.setMablaghForosh(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghForosh())));
                        darkhastFaktorSatrModel.setTedad3(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Tedad3())));
                        darkhastFaktorSatrModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKalaCode())));
                        darkhastFaktorSatrModel.setCcKala(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKala())));
                        darkhastFaktorSatrModel.setVazn(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Vazn())));

                        darkhastFaktorSatrModels.add(darkhastFaktorSatrModel);
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByccDarkhastFaktorForTakhfifHajmiKala" , "");
        }
        return darkhastFaktorSatrModels;
    }

    public ArrayList<DataTableModel> getCcNoeFiledForJayezeh(int ccJayezeh)
    {
        ArrayList<DataTableModel> ccNoeFiled = new ArrayList<>();
        String query = " SELECT  DISTINCT ccNoeField, '''' || ccnoefield || '''' strCcNoeFiled  FROM JayezehSatr WHERE ccJayezeh="  + ccJayezeh;
        Log.d("DarkhastFaktorSatrDAO", "jayezeh getCcNoeFiledForJayezeh query : " + query);

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccNoeFiled = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getCountByccDarkhastFaktor" , "");
        }
        return ccNoeFiled;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikGorohKalaAndJayezeh(long ccDarkhastFaktor, int ccJayezeh)
    {
        ArrayList<DataTableModel> ccNoeFileds = getCcNoeFiledForJayezeh(ccJayezeh);
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        for(DataTableModel ccNoeFiled : ccNoeFileds) {
            try {


                SQLiteDatabase db = dbHelper.getReadableDatabase();

                //String query = "SELECT B.ccGoroh, " +
//                    " SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
//                    + "       SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, SUM(Tedad3 * MablaghForosh) AS MablaghKol ,"
//                    + "        COUNT(A.ccKalaCode) AS TedadAghlam "
//                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
//                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
//                    + "          FROM Kala A LEFT OUTER JOIN "
//                    + "               (SELECT ccKalaCode, ccGoroh FROM KalaGoroh WHERE ccGoroh IN "
//                    + "                       (" + ccNoeFild + ")"
//                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
//                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
//                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor
//                    + " GROUP BY B.ccGoroh";


                String query = " SELECT B.ccNoeField ccNoe, " +
                        " SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, " +
                        " SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, SUM(Tedad3 * MablaghForosh) AS MablaghKol ,        COUNT(A.ccKalaCode) AS TedadAghlam " +
                        " FROM DarkhastFaktorSatr A " +
                        " JOIN   (SELECT DISTINCT J.ccNoeField, K.ccKalaCode, G.ccGoroh, K.TedadDarKarton, K.TedadDarBasteh " +
                        " FROM Kala K " +
                        " JOIN (SELECT DISTINCT ccNoeField FROM JayezehSatr WHERE ccNoeField in ( " + ccNoeFiled.getFiled2() + " )) J " +
                        " JOIN KalaGoroh G ON K.ccKalaCode= G.ccKalaCode AND ccGoroh IN (" + ccNoeFiled.getFiled1() + ") " +
                        " ) B ON A.ccKalaCode= B.ccKalaCode " +
                        " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor +
                        " And A.CodeNoeKala=1 " +
                        " GROUP BY B.ccNoeField ";


                Log.d("DarkhastFaktorSatrDAO", "jayezeh getTedadBeTafkikGorohKalaAndJayezeh query : " + query);
                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        gorohs.addAll( new PubFunc().new DAOUtil().cursorToDataTable(context, cursor));
                    }
                    cursor.close();
                }
                db.close();
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                String message = context.getResources().getString(R.string.errorSelectAll, DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO", "", "getTedadBeTafkikGorohKalaAndJayezeh", "");
            }
        }
        Log.d("DarkhastFaktorSatrDAO", "jayezeh getTedadBeTafkikGorohKalaAndJayezeh gorohs" + gorohs.toString() + "ccJayezeh" + ccJayezeh);
        return gorohs;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikBrandAndJayezeh(long ccDarkhastFaktor, int ccJayezeh)
    {
        ArrayList<DataTableModel> brands = new ArrayList<>();

        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String query = "SELECT B.ccBrand, " +
                " SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                + "       SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, SUM(Tedad3 * MablaghForosh) AS MablaghKol ,"
                + "        COUNT(B.ccBrand) AS TedadAghlam "
                + "  FROM DarkhastFaktorSatr A  JOIN (SELECT DISTINCT ccKalaCode, ccBrand, TedadDarKarton, TedadDarBasteh, VaznKhales FROM Kala)B ON A.ccKalaCode= B.ccKalaCode "
                + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor
                + " And A.CodeNoeKala=1 "
                + " GROUP BY B.ccBrand";





            Log.d("DarkhastFaktorSatrDAO", "jayezeh getTedadBeTafkikBrandAndJayezeh query : " + query);
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    brands = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO", "", "getTedadBeTafkikGorohKalaAndJayezeh", "");
        }
        Log.d("DarkhastFaktorSatrDAO", "jayezeh getTedadBeTafkikBrandAndJayezeh gorohs" + brands.toString() + "ccJayezeh" + ccJayezeh);
        return brands;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikTaminKonandehAndJayezeh(long ccDarkhastFaktor, int ccJayezeh)
    {
        ArrayList<DataTableModel> brands = new ArrayList<>();

        try {


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String query = "SELECT B.ccTaminKonandeh, " +
                    " SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + "       SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, SUM(Tedad3 * MablaghForosh) AS MablaghKol ,"
                    + "        COUNT(B.ccTaminKonandeh) AS TedadAghlam "
                    + "  FROM DarkhastFaktorSatr A  JOIN (SELECT DISTINCT ccKalaCode, ccTaminkonandeh, TedadDarKarton, TedadDarBasteh, VaznKhales FROM Kala)B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor
                    + " And A.CodeNoeKala=1 "
                    + " GROUP BY B.ccTaminKonandeh";





            Log.d("DarkhastFaktorSatrDAO", "jayezeh getTedadBeTafkikBrandAndJayezeh query : " + query);
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    brands = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO", "", "getTedadBeTafkikGorohKalaAndJayezeh", "");
        }
        Log.d("DarkhastFaktorSatrDAO", "jayezeh getTedadBeTafkikBrandAndJayezeh gorohs" + brands.toString() + "ccJayezeh" + ccJayezeh);
        return brands;
    }

    public List<DataTableModel> getTedadAghlamBeTafkikGorohKalaAndTakhfifHajmiAndNoeMoshtary(long ccDarkhastFaktor, int ccTakhfifHajmi, int HadeaghalTedadKarton)
    {
        List<DataTableModel> gorohs= new ArrayList<>();
        try
        {

            String StrSQL= "SELECT Main.ccGoroh,sum(Main.TedadAghlam),Sum(Main.MablaghKol) "
                    + " FROM (SELECT B.ccGoroh,CASE WHEN Tedad3 >= (B.Tedad1*?) THEN Count(A.ccKalaCode) ELSE 0 END AS TedadAghlam, SUM(Tedad3 * MablaghForosh) AS MablaghKol "
                    + "              FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "                     (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.Tedad1 "
                    + "                        FROM Kala A LEFT OUTER JOIN "
                    + "                             (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh  "
                    + "                                FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B "
                    + "                                     ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                                WHERE B.ccTakhfifHajmi= ? "
                    + "                            )G ON A.ccKalaCode= G.ccKalaCode "
                    + "                     )B ON A.ccKalaCode= B.ccKalaCode "
                    + "               WHERE ccDarkhastFaktor= ? AND ccGoroh Is Not Null "
                    + "               GROUP BY B.ccGoroh,B.Tedad1,Tedad3) Main "
                    + "                 GROUP BY Main.ccGoroh";

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(StrSQL, new String[]{String.valueOf(HadeaghalTedadKarton), String.valueOf(ccTakhfifHajmi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndJayezeh" , "");
        }
        return gorohs;
    }

    public List<DataTableModel> getTedadAghlamBeTafkikGorohKalaAndTakhfifSenfiAndNoeMoshtary(long ccDarkhastFaktor, int ccTakhfifSenfi, int HadeaghalTedadKarton)
    {
        List<DataTableModel> gorohs= new ArrayList<>();
        try
        {

            String StrSQL= "SELECT Main.ccGoroh,sum(Main.TedadAghlam),Sum(Main.MablaghKol) "
                    + " FROM (SELECT B.ccGoroh,CASE WHEN Tedad3 >= (B.Tedad1*?) THEN Count(A.ccKalaCode) ELSE 0 END AS TedadAghlam, SUM(Tedad3 * MablaghForosh) AS MablaghKol "
                    + "              FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "                     (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.Tedad1 "
                    + "                        FROM Kala A LEFT OUTER JOIN "
                    + "                             (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh  "
                    + "                                FROM KalaGoroh A LEFT OUTER JOIN TakhfifSenfiSatr B "
                    + "                                     ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                                WHERE B.ccTakhfifSenfi= ? "
                    + "                            )G ON A.ccKalaCode= G.ccKalaCode "
                    + "                     )B ON A.ccKalaCode= B.ccKalaCode "
                    + "               WHERE ccDarkhastFaktor= ? AND ccGoroh Is Not Null "
                    + "               GROUP BY B.ccGoroh,B.Tedad1,Tedad3) Main "
                    + "                 GROUP BY Main.ccGoroh";

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(StrSQL, new String[]{String.valueOf(HadeaghalTedadKarton), String.valueOf(ccTakhfifSenfi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndJayezeh" , "");
        }
        return gorohs;
    }

    public List<DataTableModel> getTedadAghlamBeTafkikGorohKalaAndTakhfifHajmi(long ccDarkhastFaktor, int ccTakhfifHajmi)
    {
        List<DataTableModel> gorohs= new ArrayList<>();
        try
        {
            String StrSQL= "SELECT B.ccGoroh, COUNT(A.ccKalaCode) AS TedadAghlam, SUM(Tedad3 * MablaghForosh) AS MablaghKol "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B"
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                  WHERE B.ccTakhfifHajmi= ? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= ? AND ccGoroh Is Not Null "
                    + " GROUP BY B.ccGoroh";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(StrSQL, new String[]{String.valueOf(ccTakhfifHajmi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndJayezeh" , "");
        }
        return gorohs;
    }

    public List<DataTableModel> getTedadAghlamBeTafkikGorohKalaAndTakhfifSenfi(long ccDarkhastFaktor, int ccTakhfifSenfi)
    {
        List<DataTableModel> gorohs= new ArrayList<>();
        try
        {
            String StrSQL= "SELECT B.ccGoroh, COUNT(A.ccKalaCode) AS TedadAghlam, SUM(Tedad3 * MablaghForosh) AS MablaghKol "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifSenfiSatr B"
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                  WHERE B.ccTakhfifSenfi= ? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= ? AND ccGoroh Is Not Null "
                    + " GROUP BY B.ccGoroh";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(StrSQL, new String[]{String.valueOf(ccTakhfifSenfi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndJayezeh" , "");
        }
        return gorohs;
    }

    public int getCountByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        int count = 0;
        String query = "select ccKalaCode from DarkhastFaktorSatr where ccDarkhastFaktor = " + ccDarkhastFaktor;
        try
        {
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
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getCountByccDarkhastFaktor" , "");
        }
        return count;
    }

    public int getTedadAghlamByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        int count = 0;
        String query = "select count(ccDarkhastFaktorSatr) from DarkhastFaktorSatr where CodeNoeKala = 1 And ccDarkhastFaktor = " + ccDarkhastFaktor;
        try
        {
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
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getCountByccDarkhastFaktor" , "");
        }
        return count;
    }

    public HashMap<String,Integer> getCountOfKalaCodeByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        HashMap<String,Integer> hashMap = new HashMap<>();
        String query = "select IFNULL(sum(Tedad3),0) tedad , ccKalaCode from DarkhastFaktorSatr where ccDarkhastFaktor = " + ccDarkhastFaktor + " And CodeNoeKala != 2 group by ccKalaCode";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    String ccKalaCode = "";
                    int tedad = 0;
                    while (!cursor.isAfterLast())
                    {
                        ccKalaCode = String.valueOf(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKalaCode())));
                        tedad = cursor.getInt(cursor.getColumnIndex("tedad"));
                        hashMap.put(ccKalaCode , tedad);
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getCountOfKalaCodeByccDarkhastFaktor" , "");
        }
        return hashMap;
    }

    public ArrayList<DarkhastFaktorSatrModel> getByccDarkhastFaktorAndNotNoeKala(long ccDarkhastFaktor , int codeNoeKala, int ccForoshandeh)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorSatrModel.TableName(), allColumns(), DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorSatrModel.COLUMN_CodeNoeKala() + " != " + codeNoeKala + " and " + DarkhastFaktorSatrModel.COLUMN_ccAfrad() + " = " + ccForoshandeh, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorSatrModels;
    }

    public ArrayList<DarkhastFaktorSatrModel> getByccDarkhastFaktorAndNoeKala(long ccDarkhastFaktor , int codeNoeKala)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorSatrModel.TableName(), allColumns(), DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorSatrModel.COLUMN_CodeNoeKala() + " = " + codeNoeKala, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorSatrModels;
    }

    public int getTedadKharid3Mah(int ccMoshtary)
    {
        int TedadKharid3Mah=0;
        String StrSQL = " SELECT  COUNT(DISTINCT DarkhastFaktorSatr.ccKalaCode) "
                + " FROM    DarkhastFaktor "
                + " LEFT OUTER JOIN DarkhastFaktorSatr ON DarkhastFaktor.ccDarkhastFaktor = DarkhastFaktorSatr.ccDarkhastFaktor "
                + " LEFT OUTER JOIN Kala ON DarkhastFaktorSatr.ccKalaCode = Kala.ccKalaCode "
                + " WHERE   (DarkhastFaktor.ccMoshtary = ?)";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(StrSQL , new String[]{String.valueOf(ccMoshtary)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        TedadKharid3Mah = cursor.getInt(0);
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
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "deleteAll" , "");
        }
        return TedadKharid3Mah;
    }


    /**
     * تعداد کالاهای اساسی در درخواست فاکتور را بر می گرداند.
     * @param ccKalaCodesOfKalaAsasi لیست کد کالاهای اساسی
     * @param ccDarkhastFaktor شناسه درخواست فاکتور
     * @return تعداد کالاهای اساسی در درخواست فاکتور
     */
    public int getCountccKalaCodesAsasiInNewFaktor(String ccKalaCodesOfKalaAsasi, long ccDarkhastFaktor)
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select count(*) from DarkhastFaktorSatr " +
                    "where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ccKalaCode in (" + ccKalaCodesOfKalaAsasi + ")";
            Log.d("darkhastKala", "query : " + query);
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
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "countccKalaCodesAsasiInNewFaktor" , "");
        }
        return count;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikBrand(long ccDarkhastFaktor , int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> brands = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccBrand, SUM(A.Tedad3) AS Tedad, SUM(A.Tedad3/ TedadDarKarton) AS TedadBox, SUM(A.Tedad3/ TedadDarBasteh) AS TedadPackage, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 *  " + gheymat + " ) - (select sum(MablaghTakhfif) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
            }
            query += " ,SUM(Tedad3 * B.VaznKhales) AS Vazn, COUNT(ccDarkhastFaktorSatr) AS TedadAghlam "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN (SELECT DISTINCT ccKalaCode, ccBrand, TedadDarKarton, TedadDarBasteh, VaznKhales FROM Kala)B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= ? " + " GROUP BY B.ccBrand";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    brands = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikBrand" , "");
        }
        return brands;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikBrandMohasebeh(long ccDarkhastFaktor , int currentOlaviat, int ccBrand, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> brands = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccBrand, SUM(A.Tedad3) AS Tedad, SUM(A.Tedad3/ TedadDarKarton) AS TedadBox, SUM(A.Tedad3/ TedadDarBasteh) AS TedadPackage, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 *  " + gheymat + " ) - (select sum(MablaghTakhfif) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
            }
            query += "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN (SELECT DISTINCT ccKalaCode, ccBrand, TedadDarKarton, TedadDarBasteh FROM Kala)B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= ?  AND  ccBrand= ?" + " GROUP BY B.ccBrand";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccDarkhastFaktor), String.valueOf(ccBrand)});
            Log.d("Takhfif","query: " + query);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    brands = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikBrand" , "");
        }
        return brands;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikTaminKonandeh(long ccDarkhastFaktor, int currentOlaviat, int noeGheymat)
    {
        ArrayList<DataTableModel> taminKonandehs = new ArrayList<>();
        try
        {
            String gheymat="";
            if (noeGheymat == 1) {
                gheymat = "MablaghForosh";
            } else {
                gheymat = "GheymatMasrafKonandeh";
            }
            String query = "SELECT B.ccTaminKonandeh, SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + " SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage,";
                    if(currentOlaviat == 0 || currentOlaviat == 1)
                    {
                        query += " SUM(Tedad3 * " + gheymat + ") AS MablaghKol ";
                    }
                    else
                    {
                        query += " SUM(Tedad3 * " + gheymat + ") - (select sum(MablaghTakhfif) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                                + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
                    }
                    query += " ,SUM(Tedad3 * B.VaznKhales) AS Vazn, COUNT(ccDarkhastFaktorSatr) AS TedadAghlam "
                            + " FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + " (SELECT DISTINCT ccKalaCode, ccTaminKonandeh, TedadDarKarton, TedadDarBasteh, VaznKhales FROM Kala)B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= ? "
                    + " GROUP BY B.ccTaminKonandeh";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    taminKonandehs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikTaminKonandeh" , "");
        }
        return taminKonandehs;
    }

    public ArrayList<DataTableModel> getBrandByccTaminKonandeh(long ccDarkhastFaktor, int ccTaminKonandeh, int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> brands = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccBrand, SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + " SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 *  " + gheymat + " ) - (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat <= " + (currentOlaviat)
                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
//                query += " (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat <= " + (currentOlaviat)
//                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
            }
                query += " FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                        + " (SELECT DISTINCT ccKalaCode, ccBrand, TedadDarKarton, TedadDarBasteh FROM Kala)B ON A.ccKalaCode= B.ccKalaCode "
                        + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " AND ccTaminKonandeh = " +  ccTaminKonandeh + " GROUP BY B.ccBrand";

            Log.d("takhfifTaminKonandeh", "query : " + query.toString());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    brands = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getBrandByccTaminKonandeh" , "");
        }
        return brands;
    }

    public ArrayList<DataTableModel> getBrandByccTaminKonandehMohasebeh(long ccDarkhastFaktor, int ccTaminKonandeh, int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> brands = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccBrand, SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + " SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage,";
                    if(currentOlaviat == 0 || currentOlaviat == 1)
                    {
                        query += " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol ";
                    }
                    else
                    {
                        query += " SUM(Tedad3 * " + gheymat + " ) - (select sum(MablaghTakhfif) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                                + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
                    }

                    query += " FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + " (SELECT DISTINCT ccKalaCode, ccBrand, TedadDarKarton, TedadDarBasteh FROM Kala)B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " AND ccTaminKonandeh = " +  ccTaminKonandeh + " GROUP BY B.ccBrand";

            Log.d("takhfifTaminKonandeh", "getBrandByccTaminKonandehMohasebeh query : " + query.toString());

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    brands = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getBrandByccTaminKonandeh" , "");
        }
        return brands;
    }

    public ArrayList<DataTableModel> getTaminKonandehOfKalaInDarkhast(long ccDarkhastFaktor)
    {
        ArrayList<DataTableModel> kalaBrandAndMablagh = new ArrayList<>();
        try
        {
            String query = "select dfs.ccTaminKonandeh, dfs.ccDarkhastFaktorSatr, dfs.Tedad3,CAST(dfs.MablaghForosh AS int) MablaghForosh from  DarkhastFaktorSatr dfs \n" +
                    "  where dfs.ccDarkhastFaktor = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        DataTableModel dataTableModel = new DataTableModel();
                        dataTableModel.setFiled1(cursor.getString(cursor.getColumnIndex("ccTaminKonandeh")));
                        dataTableModel.setFiled2(cursor.getString(cursor.getColumnIndex("ccDarkhastFaktorSatr")));
                        dataTableModel.setFiled3(cursor.getString(cursor.getColumnIndex("Tedad3")));
                        dataTableModel.setFiled4(cursor.getString(cursor.getColumnIndex("MablaghForosh")));
                        kalaBrandAndMablagh.add(dataTableModel);
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
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getBrandOfKalaInDarkhast" , "");
        }
        return kalaBrandAndMablagh;
    }

    public ArrayList<DataTableModel> getBrandOfKalaInDarkhast(long ccDarkhastFaktor)
    {
        ArrayList<DataTableModel> kalaBrandAndMablagh = new ArrayList<>();
        try
        {
            String query = "select k.ccBrand, dfs.ccDarkhastFaktorSatr, dfs.Tedad3,dfs.MablaghForosh from  DarkhastFaktorSatr dfs \n" +
                    " LEFT JOIN (SELECT DISTINCT ccKalaCode,ccBrand FROM Kala) k \n" +
                    " ON k.ccKalaCode = dfs.ccKalaCode where dfs.ccDarkhastFaktor = " + ccDarkhastFaktor;
            Log.d("darkhastfaktorsatr", "Brand query : " + query);

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        DataTableModel dataTableModel = new DataTableModel();
                        dataTableModel.setFiled1(cursor.getString(cursor.getColumnIndex("ccBrand")));
                        dataTableModel.setFiled2(cursor.getString(cursor.getColumnIndex("ccDarkhastFaktorSatr")));
                        dataTableModel.setFiled3(cursor.getString(cursor.getColumnIndex("Tedad3")));
                        dataTableModel.setFiled4(cursor.getString(cursor.getColumnIndex("MablaghForosh")));
                        kalaBrandAndMablagh.add(dataTableModel);
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
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getBrandOfKalaInDarkhast" , "");
        }
        return kalaBrandAndMablagh;
    }


    public ArrayList<DataTableModel> getTedadBeTafkikGorohKalaAndTakhfifHajmi(long ccDarkhastFaktor, int ccTakhfifHajmi, int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccGoroh, SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + "       SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 * " + gheymat + " ) - (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
            }
            query += " , MAX(TedadDarKarton) AS TedadDarKarton, MAX(TedadDarBasteh) AS TedadDarBasteh, SUM(Tedad3 * VaznKhales) AS Vazn"
                    + " , COUNT(ccDarkhastFaktorSatr) TedadAghlam "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh, A.VaznKhales "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B"
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                  WHERE B.ccTakhfifHajmi= " + ccTakhfifHajmi
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " AND ccGoroh Is Not Null "
                    + " GROUP BY B.ccGoroh";
            Log.d("faktorSatr" , "query : " + query);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccTakhfifHajmi), String.valueOf(ccDarkhastFaktor)});
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndTakhfifHajmi" , "");
        }
        return gorohs;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikGorohKalaAndTakhfifSenfi(long ccDarkhastFaktor, int ccTakhfifSenfi, int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccGoroh, SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + "       SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 * " + gheymat + " ) - (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
            }
            query += " , MAX(TedadDarKarton) AS TedadDarKarton, MAX(TedadDarBasteh) AS TedadDarBasteh, SUM(Tedad3 * VaznKhales) AS Vazn"
                    + " , COUNT(ccDarkhastFaktorSatr) TedadAghlam "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh, A.VaznKhales "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifSenfiSatr B"
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                  WHERE B.ccTakhfifSenfi= " + ccTakhfifSenfi
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " AND ccGoroh Is Not Null "
                    + " GROUP BY B.ccGoroh";
            Log.d("faktorSatr" , "query : " + query);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndTakhfifSenfi" , "");
        }
        return gorohs;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikGorohKalaMohasebehAndTakhfifHajmi(long ccDarkhastFaktor, int ccTakhfifHajmiSatr, int ccGorohMohasebeh, int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccGoroh, SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + "       SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 * " + gheymat + " ) - (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                     //   + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
                        + " and ccDarkhastFaktorSatr in (SELECT ccDarkhastFaktorSatr FROM DarkhastFaktorSatr LEFT JOIN KalaGoroh on KalaGoroh.ccKalaCode = darkhastfaktorsatr.ccKalaCode  where ccDarkhastFaktor=  " + ccDarkhastFaktor + " and KalaGoroh.ccGoroh = " + ccGorohMohasebeh + " ) ) AS MablaghKol ";
            }
            query += " , MAX(TedadDarKarton) AS TedadDarKarton, MAX(TedadDarBasteh) AS TedadDarBasteh"
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccGorohMohasebeh AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B"
                    + "                       ON A.ccGoroh= B.ccGorohMohasebeh OR A.ccGorohLink= B.ccGorohMohasebeh OR A.ccRoot= B.ccGorohMohasebeh "
                    + "                  WHERE B.ccTakhfifHajmiSatr= ? AND B.ccGorohMohasebeh=? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= ? AND ccGoroh Is Not Null "
                    + " GROUP BY B.ccGoroh";
            Log.d("faktorGorohMoh" , "query : " + query + "ccTakhfifHajmiSatr:" + ccTakhfifHajmiSatr + "ccGorohMohasebeh:" + ccGorohMohasebeh + "ccDarkhastFaktor:" + ccDarkhastFaktor);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccTakhfifHajmiSatr), String.valueOf(ccGorohMohasebeh), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndTakhfifHajmi" , "");
        }
        return gorohs;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikGorohKalaMohasebehAndTakhfifSenfi(long ccDarkhastFaktor, int ccTakhfifSenfiSatr, int ccGorohMohasebeh, int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccGoroh, SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + "       SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 *  " + gheymat + " ) - (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                       // + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
                        + " and ccDarkhastFaktorSatr in (SELECT ccDarkhastFaktorSatr FROM DarkhastFaktorSatr LEFT JOIN KalaGoroh on KalaGoroh.ccKalaCode = darkhastfaktorsatr.ccKalaCode  where ccDarkhastFaktor=  " + ccDarkhastFaktor + " and KalaGoroh.ccGoroh = " + ccGorohMohasebeh + " ) ) AS MablaghKol ";
            }
            query += " , MAX(TedadDarKarton) AS TedadDarKarton, MAX(TedadDarBasteh) AS TedadDarBasteh"
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccGorohMohasebeh AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifSenfiSatr B"
                    + "                       ON A.ccGoroh= B.ccGorohMohasebeh OR A.ccGorohLink= B.ccGorohMohasebeh OR A.ccRoot= B.ccGorohMohasebeh "
                    + "                  WHERE B.ccTakhfifSenfiSatr= ? AND B.ccGorohMohasebeh=? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= ? AND ccGoroh Is Not Null "
                    + " GROUP BY B.ccGoroh";
            Log.d("faktorGorohMoh" , "query : " + query + "ccTakhfifSenfiSatr:" + ccTakhfifSenfiSatr + "ccGorohMohasebeh:" + ccGorohMohasebeh + "ccDarkhastFaktor:" + ccDarkhastFaktor);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccTakhfifSenfiSatr), String.valueOf(ccGorohMohasebeh), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndTakhfifHajmi" , "");
        }
        return gorohs;
    }

    public ArrayList<DataTableModel> getRowsBeTafkikGorohKalaAndTakhfifHajmi(long ccDarkhastFaktor, int ccTakhfifHajmi, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "A.MablaghForosh";
        } else {
            gheymat = "A.GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();

        try
        {
            String query = "SELECT A.ccDarkhastFaktorSatr, B.ccGoroh, " + gheymat + " MablaghForosh, A.Tedad3 "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B "
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                 WHERE B.ccTakhfifHajmi= ? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= ? AND  B.ccGoroh IS NOT NULL ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccTakhfifHajmi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getRowsBeTafkikGorohKalaAndTakhfifHajmi" , "");
        }
        return gorohs;
    }
    public ArrayList<DataTableModel> getRowsBeTafkikGorohKalaMohasebehAndTakhfifHajmi(long ccDarkhastFaktor, int ccTakhfifHajmi, int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "A.MablaghForosh";
        } else {
            gheymat = "A.GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();

        try
        {
            String query = "SELECT A.ccDarkhastFaktorSatr, B.ccGoroh, " + gheymat + " MablaghForosh, A.Tedad3 ,((SELECT SUM(MablaghTakhfif) from DarkhastFaktorSatrTakhfif WHERE ccDarkhastFaktorSatr=A.ccDarkhastFaktorSatr AND ExtraProp_Olaviat < "+currentOlaviat+"))"
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccGorohMohasebeh AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B "
                    + "                       ON A.ccGoroh= B.ccGorohMohasebeh OR A.ccGorohLink= B.ccGorohMohasebeh OR A.ccRoot= B.ccGorohMohasebeh "
                   // + "                 WHERE B.ccTakhfifHajmiSatr= ? "
                    + "                 WHERE B.ccTakhfifHajmi= ? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= ? AND  B.ccGoroh IS NOT NULL "
                    + " ORDER BY B.ccGoroh ASC, A.Tedad3 ASC, A.MablaghForosh DESC";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Log.d("faktorsatrGorohMoh" , "query : " + query);
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccTakhfifHajmi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getRowsBeTafkikGorohKalaAndTakhfifHajmi" , "");
        }
        return gorohs;
    }

    public ArrayList<DataTableModel> getRowsBeTafkikGorohKalaMohasebehAndTakhfifSenfi(long ccDarkhastFaktor, int ccTakhfifSenfi, int currentOlaviat, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "A.MablaghForosh";
        } else {
            gheymat = "A.GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();

        try
        {
            String query = "SELECT A.ccDarkhastFaktorSatr, B.ccGoroh, " + gheymat + " MablaghForosh, A.Tedad3 ,((SELECT SUM(MablaghTakhfif) from DarkhastFaktorSatrTakhfif WHERE ccDarkhastFaktorSatr=A.ccDarkhastFaktorSatr AND ExtraProp_Olaviat < "+currentOlaviat+"))"
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccGorohMohasebeh AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifSenfiSatr B "
                    + "                       ON A.ccGoroh= B.ccGorohMohasebeh OR A.ccGorohLink= B.ccGorohMohasebeh OR A.ccRoot= B.ccGorohMohasebeh "
                    + "                 WHERE B.ccTakhfifSenfi= ? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= ? AND  B.ccGoroh IS NOT NULL "
                    + " ORDER BY B.ccGoroh ASC,A.MablaghForosh DESC, A.Tedad3 DESC";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Log.d("faktorsatrGorohMoh" , "query : " + query);
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccTakhfifSenfi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getRowsBeTafkikGorohKalaAndTakhfifSenfi" , "");
        }
        return gorohs;
    }

    public ArrayList<DataTableModel> getTedadBeTafkikGorohKalaAndTakhfifSenfi(long ccDarkhastFaktor, int ccTakhfifSenfi, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "A.MablaghForosh";
        } else {
            gheymat = "A.GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();

        try
        {
            String query = "SELECT B.ccGoroh, SUM(A.Tedad3)AS Tedad, SUM(A.Tedad3/ TedadDarKarton)AS TedadBox, "
                    + "       SUM(A.Tedad3/ TedadDarBasteh)AS TedadPackage, "
                    + " SUM(Tedad3 * " + gheymat + " ) AS MablaghKol "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifSenfiSatr B"
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                  WHERE B.ccTakhfifSenfi= ? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= ?"
                    + " GROUP BY B.ccGoroh";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccTakhfifSenfi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadBeTafkikGorohKalaAndTakhfifSenfi" , "");
        }
        return gorohs;
    }


    public long getSumMablaghJayezehByccDarkhast(long ccDarkhastFaktor)
    {
        long sumMablaghJayezeh = 0;
        try
        {
            //select sum(MablaghForosh * Tedad3) from DarkhastFaktorSatr where CodeNoeKala = 2 and ccDarkhastFaktor
            String query = "select sum(" + DarkhastFaktorSatrModel.COLUMN_MablaghForosh() + " * " + DarkhastFaktorSatrModel.COLUMN_Tedad3() + ") " + "from " + DarkhastFaktorSatrModel.TableName() + " where " + DarkhastFaktorSatrModel.COLUMN_CodeNoeKala() + " = 2 and " + DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablaghJayezeh = cursor.getLong(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getSumMablaghJayezehByccDarkhast" , "");
        }
        return sumMablaghJayezeh;
    }

    public long getSumMablaghFaktorByccDarkhast(long ccDarkhastFaktor)
    {
        long sumMablagh = 0;
        try
        {
            //select sum(MablaghForosh * Tedad3) from DarkhastFaktorSatr where ccDarkhastFaktor = ?
            String query = "select sum(" + DarkhastFaktorSatrModel.COLUMN_MablaghForosh() + " * " + DarkhastFaktorSatrModel.COLUMN_Tedad3() + ") " + "from " + DarkhastFaktorSatrModel.TableName() + " where " + DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablagh = cursor.getLong(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getSumMablaghFaktorByccDarkhast" , "");
        }
        return sumMablagh;
    }

    public long getSumMablaghFaktorWithMaliatByccDarkhast(long ccDarkhastFaktor)
    {
        long sumMablagh = 0;
        try
        {
            //select sum(MablaghForosh * Tedad3) from DarkhastFaktorSatr where ccDarkhastFaktor = ?
            String query = " SELECT SUM(MablaghForoshKhalesKala * Tedad3) FROM DarkhastFaktorSatr  WHERE ccDarkhastFaktor = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablagh = cursor.getLong(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getSumMablaghFaktorByccDarkhast" , "");
        }
        return sumMablagh;
    }

    public ArrayList<DataTableModel> getRowsBeTafkikGorohKalaAndTakhfifSenfi(long ccDarkhastFaktor, int ccTakhfifSenfi, int noeGheymat)
    {
        String gheymat="";
        if (noeGheymat == 1) {
            gheymat = "MablaghForosh";
        } else {
            gheymat = "GheymatMasrafKonandeh";
        }
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        try
        {
            String query = "SELECT A.ccDarkhastFaktorSatr, B.ccGoroh, " + gheymat + " MablaghForosh, A.Tedad3 "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifSenfiSatr B "
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                 WHERE B.ccTakhfifSenfi= ? "
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode "
                    + " WHERE ccDarkhastFaktor= ? AND B.ccGoroh IS NOT NULL ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , new String[]{String.valueOf(ccTakhfifSenfi), String.valueOf(ccDarkhastFaktor)});
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getRowsBeTafkikGorohKalaAndTakhfifSenfi" , "");
        }
        return gorohs;
    }

    public int getTedadByKalaInfo(long ccDarkhastFaktor , int ccTaminKonandeh , int ccKala , int ccKalaCode , String shomareBach, String TarikhTolid, String TarikhEngheza)
    {
        int tedad = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select Tedad3 from darkhastFaktorSatr where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ccTaminKonandeh = " + ccTaminKonandeh +
                    " and ccKala = " + ccKala + " and ccKalaCode = " + ccKalaCode + " and ShomarehBach = '" + shomareBach + "' and TarikhTolid = '" + TarikhTolid + "'" +
                    " and TarikhEngheza = '" + TarikhEngheza + "'";
            Log.d("faktor" , "query : " + query);
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    tedad = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
            return tedad;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadByKalaInfo" , "");
            return -1;
        }
    }


    public ArrayList<DataTableModel> getTedadKartonByccGorohKala(long ccDarkhastFaktor, int ccTakhfifHajmi, int ccGorohKala)
    {
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccGoroh, SUM(A.Tedad3 * 1.0/ TedadDarKarton ) AS TedadBox, SUM(A.Tedad3 * 1.0/ TedadDarBasteh ) AS TedadPackage, \n"
                    + "  SUM(A.Tedad3 * 1.0) AS Tedad, SUM(A.Tedad3 * 1.0 * MablaghForosh) AS MablaghKol "
                    + "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B"
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                  WHERE B.ccTakhfifHajmi= " + ccTakhfifHajmi +  "  and B.ccNoeField=  " + ccGorohKala
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " AND ccGoroh = " + ccGorohKala
                    + " GROUP BY B.ccGoroh";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                    //tedadKarton = Float.valueOf(gorohs.get(0).getFiled1()) ;
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadKartonByccGorohKala" , "");
        }
        return gorohs;
    }

    public ArrayList<DataTableModel> getTedadKartonByccGorohKala(long ccDarkhastFaktor, int ccTakhfifHajmi, int ccGorohKala, int currentOlaviat)
    {
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        try
        {
            String query = "SELECT B.ccGoroh, SUM(A.Tedad3 * 1.0/ TedadDarKarton ) AS TedadBox, SUM(A.Tedad3 * 1.0/ TedadDarBasteh ) AS TedadPackage, \n"
                    + "  SUM(A.Tedad3 * 1.0) AS Tedad, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 * MablaghForosh) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 * MablaghForosh) - (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
            }
            query += "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B"
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                  WHERE B.ccTakhfifHajmi= " + ccTakhfifHajmi +  "  and B.ccNoeField=  " + ccGorohKala
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " AND ccGoroh = " + ccGorohKala
                    + " GROUP BY B.ccGoroh";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Log.d("takhfif","getTedadKartonByccGorohKala query:" + query);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                    //tedadKarton = Float.valueOf(gorohs.get(0).getFiled1()) ;
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadKartonByccGorohKala" , "");
        }
        return gorohs;
    }

    public double getSumMablaghHashiehSood(long ccDarkhastFaktor) {
        double mablaghMasrafKonandeh = 0;
        double mablaghForosh = 0;
        double mablaghMaliatAvarez = 0;
        double mablaghPasAzKasrMaliatAvarez = 0;
        KalaDAO kaladao = new KalaDAO(context);
        KalaModel kalaModel = new KalaModel();
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(context);
        List<ParameterChildModel> parameterChildModels = parameterChildDAO.getAll();

        try {

            String StrSQL = " SELECT *  "
                    + " FROM DarkhastFaktorSatr "
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor;

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(StrSQL, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ArrayList<DarkhastFaktorSatrModel> entity = cursorToModel(cursor);

                kalaModel = kaladao.getByccKalaCode(entity.get(0).getCcKalaCode());
                mablaghMasrafKonandeh += kalaModel.getMablaghMasrafKonandeh() * entity.get(0).getTedad3();

                mablaghMaliatAvarez = 0;
                mablaghForosh = (entity.get(0).getMablaghForosh() * entity.get(0).getTedad3());

                int maliat = 0;
                int avarez = 0;

                for (int i = 0; i < parameterChildModels.size(); i++) {
                    if (parameterChildModels.get(i).getCcParameterChild() == Constants.CC_CHILD_AVAREZ) {
                        avarez = Integer.parseInt(parameterChildModels.get(i).getValue());
                    }
                    if (parameterChildModels.get(i).getCcParameterChild() == Constants.CC_CHILD_MALIAT) {
                        maliat = Integer.parseInt(parameterChildModels.get(i).getValue());
                    }
                }

                if (kalaModel.getMashmolMaliatAvarez() == 1)
                    mablaghMaliatAvarez = (entity.get(0).getMablaghForosh() * entity.get(0).getTedad3()) * ((maliat + avarez) / 100);

                mablaghPasAzKasrMaliatAvarez += mablaghForosh + mablaghMaliatAvarez;

                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return mablaghMasrafKonandeh - mablaghPasAzKasrMaliatAvarez;
    }

    public ArrayList<DataTableModel> getTedadBastehByccGorohKala(long ccDarkhastFaktor, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel, int ccGorohKala)
    {
        ArrayList<DataTableModel> gorohs = new ArrayList<>();
        int ccTakhfifHajmi=takhfifHajmiTitrSatrModel.getCcTakhfifHajmi();
        int noeGheymat=takhfifHajmiTitrSatrModel.getNoeGheymat();
        int currentOlaviat=takhfifHajmiTitrSatrModel.getOlaviat();

        try
        {
            String query = "SELECT B.ccGoroh, SUM(A.Tedad3 * 1.0/ TedadDarKarton ) AS TedadBox, SUM(A.Tedad3 * 1.0/ TedadDarBasteh ) AS TedadPackage, \n"
                    + "  SUM(A.Tedad3 * 1.0) AS Tedad, ";
            if(currentOlaviat == 0 || currentOlaviat == 1)
            {
                query += " SUM(Tedad3 *  MablaghForosh ) AS MablaghKol ";
            }
            else
            {
                query += " SUM(Tedad3 * MablaghForosh) - (select ifnull(sum(MablaghTakhfif),0) from DarkhastFaktorSatrTakhfif where ExtraProp_Olaviat < " + (currentOlaviat)
                        + " and ccDarkhastFaktorSatr in (select ccdarkhastfaktorsatr from darkhastfaktorsatr where ccdarkhastfaktor = " + ccDarkhastFaktor + ") ) AS MablaghKol ";
            }
            query += "  FROM DarkhastFaktorSatr A LEFT OUTER JOIN "
                    + "       (SELECT DISTINCT A.ccKalaCode, G.ccGoroh, A.TedadDarKarton, A.TedadDarBasteh "
                    + "          FROM Kala A LEFT OUTER JOIN "
                    + "               (SELECT A.ccKalaCode, B.ccNoeField AS ccGoroh "
                    + "                  FROM KalaGoroh A LEFT OUTER JOIN TakhfifHajmiSatr B"
                    + "                       ON A.ccGoroh= B.ccNoeField OR A.ccGorohLink= B.ccNoeField OR A.ccRoot= B.ccNoeField "
                    + "                  WHERE B.ccTakhfifHajmi= " + ccTakhfifHajmi +  "  and B.ccNoeField=  " + ccGorohKala
                    + "               )G ON A.ccKalaCode= G.ccKalaCode "
                    + "       )B ON A.ccKalaCode= B.ccKalaCode"
                    + " WHERE ccDarkhastFaktor= " + ccDarkhastFaktor + " AND ccGoroh = " + ccGorohKala
                    + " GROUP BY B.ccGoroh";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gorohs = new PubFunc().new DAOUtil().cursorToDataTable(context , cursor);
                    //tedadKarton = Float.valueOf(gorohs.get(0).getFiled1()) ;
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadKartonByccGorohKala" , "");
        }
        return gorohs;
    }

    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorSatrModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadKharid3Mah" , "");
            return false;
        }
    }

    public boolean updateMablaghForoshKhalesKalaMaliatAvarez(int ccDarkhastFaktorSatr , double mablaghKhalesForosh , double maliat , double avarez)
    {
        String query = "UPDATE " + DarkhastFaktorSatrModel.TableName() + " SET " + DarkhastFaktorSatrModel.COLUMN_MablaghForoshKhalesKala() + " = " + mablaghKhalesForosh + " , " +
                        DarkhastFaktorSatrModel.COLUMN_Maliat() + " = " + maliat + " , " + DarkhastFaktorSatrModel.COLUMN_Avarez() + " = " + avarez +
                        " where " + DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr() + " = " + ccDarkhastFaktorSatr;
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
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "updateMablaghForoshKhalesKalaMaliatAvarez" , "");
            return false;
        }
    }
    public boolean updateMaliatAvarezTakhfif(int ccDarkhastFaktorSatr , double maliatTakhfif , double avarezTakhfif)
    {
        String query = "UPDATE " + DarkhastFaktorSatrModel.TableName()
                + " SET " + DarkhastFaktorSatrModel.COLUMN_ExtraProp_Maliat() + " = " + maliatTakhfif + " , " + DarkhastFaktorSatrModel.COLUMN_ExtraProp_Avarez() + " = " + avarezTakhfif +
                " where " + DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr() + " = " + ccDarkhastFaktorSatr;
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
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "updateMablaghForoshKhalesKalaMaliatAvarez" , "");
            return false;
        }
    }

    public boolean updateMablaghTakhfifNaghdyVahed(int ccDarkhastFaktorSatr , double mablaghTakhfifNaghdyVahed) {
        String query = "UPDATE " + DarkhastFaktorSatrModel.TableName()
                + " SET " + DarkhastFaktorSatrModel.COLUMN_MablaghTakhfifNaghdiVahed() + " = " + mablaghTakhfifNaghdyVahed +
                " where " + DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr() + " = " + ccDarkhastFaktorSatr;
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
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "updateMablaghTakhfifNaghdyVahed" , "");
            return false;
        }
    }

    public boolean updateSendedDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew , int isOld)
    {
        try
        {
            String query = "update " + DarkhastFaktorSatrModel.TableName() + " set " + DarkhastFaktorSatrModel.COLUMN_ExtraProp_IsOld() + " = " + isOld + " , " + DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew +
                    " where " + DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "updateSendedDarkhastFaktor" , "");
            return true;
        }
    }

    public boolean delete(long ccDarkhastFaktor , int ccTaminKonandeh , int ccKala , int ccKalaCode , String shomareBach, String TarikhTolid, String TarikhEngheza, double gheymatForoshAsli, double gheymatMasrafKonandehAsli, double gheymatKharid)
    {

        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorSatrModel.TableName(), DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor +
                    " and " + DarkhastFaktorSatrModel.COLUMN_ccTaminKonandeh() + " = " + ccTaminKonandeh +
                    " and " + DarkhastFaktorSatrModel.COLUMN_ccKala() + " = " + ccKala +
                    " and " + DarkhastFaktorSatrModel.COLUMN_ccKalaCode() + " = " + ccKalaCode +
                    " and " + DarkhastFaktorSatrModel.COLUMN_ShomarehBach() + " = '" + shomareBach + "'" +
                    " and " + DarkhastFaktorSatrModel.COLUMN_TarikhTolid() + " = '" + TarikhTolid + "'" +
                    " and " + DarkhastFaktorSatrModel.COLUMN_TarikhEngheza() + " = '" + TarikhEngheza + "'" +
                    " and " + DarkhastFaktorSatrModel.COLUMN_GheymatMasrafKonandehAsli() + " = '" + gheymatMasrafKonandehAsli + "'" +
                    " and " + DarkhastFaktorSatrModel.COLUMN_GheymatForoshAsli() + " = '" + gheymatForoshAsli + "'" +
                    " and " + DarkhastFaktorSatrModel.COLUMN_GheymatKharid() + " = '" + gheymatKharid + "'"
                    , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTedadKharid3Mah" , "");
            return false;
        }
    }

    public boolean deleteByccKalaCodeAndccDarkhastFaktor(long ccDarkhastFaktor , String ccKalaCode)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorSatrModel.TableName(), DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorSatrModel.COLUMN_ccKalaCode() + " = " + ccKalaCode , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "deleteByccKalaCodeAndccDarkhastFaktor" , "");
            return false;
        }
    }


    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorSatrModel.TableName(), DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "deleteByccKalaCodeAndccDarkhastFaktor" , "");
            return false;
        }
    }


//    public int getCountOfNoe2KalaByccDarkhast(long ccDarkhastFaktor){
//    SQLiteDatabase db=dbHelper.getReadableDatabase();
//        Cursor cursor=null;
//    try
//    {
//        cursor =db.rawQuery("Select * from DarkhastFaktorSatr where ccDarkhastFaktor = " + ccDarkhastFaktor + " AND CodeNoeKala = 2 ",null);
//
//
//    }
//    catch (Exception e)
//    {
//        e.printStackTrace();
//        PubFunc.Logger logger = new PubFunc().new Logger();
//        String message = context.getResources().getString(R.string.errorDelete , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString() + ", ccDarkhastfaktor: " + ccDarkhastFaktor;
//        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getCountOfNoe2KalaByccDarkhast" , "");
//
//    }
//        return cursor.getCount();
//
//}
//
//    public int getTableCountAfterDelete(long darkhastFaktor){
//        Cursor cursor=null;
////        BigInteger bigInteger=BigInteger.valueOf(darkhastFaktor);
//        try
//        {
//            Log.i("darkhastFaktor", "getTableCountAfterDelete: "+darkhastFaktor);
//            String queryAll= "select * from DarkhastFaktorSatr where ccDarkhastFaktor =" + darkhastFaktor +" and CodeNoeKala = 2" ;
//            SQLiteDatabase db=dbHelper.getReadableDatabase();
//            cursor=db.rawQuery(queryAll,null);
//        }
//        catch (Exception e){
//
//            e.printStackTrace();
//            PubFunc.Logger logger = new PubFunc().new Logger();
//            String message = context.getResources().getString(R.string.errorDelete , DarkhastFaktorSatrModel.TableName()) + "\n" + e.toString() + ", ccDarkhastfaktor: " + darkhastFaktor;
//            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "getTableCountAfterDelete" , "");
//
//
//        }
//        return cursor.getCount();
//    }



    public boolean deleteJayezehForccDarkhastFaktor(long ccDarkhastFaktor)
    {
        //String query = "delete from " + DarkhastFaktorSatrModel.TableName() + " where " + DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorSatrModel.COLUMN_MablaghForosh() + " = 1";
        Log.i("DarkhastFaktorSatrDAO", "deleteJayezehForccDarkhastFaktor:" +ccDarkhastFaktor );
        String query = " DELETE From DarkhastFaktorSatr " +
                       " WHERE ccDarkhastFaktor = "+ ccDarkhastFaktor +
                       " AND CodeNoeKala = 2 " +
                       " AND MablaghForosh = 1 ";

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
            String message = context.getResources().getString(R.string.errorDelete , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString() + " ,ccDarkhastFaktor: " + ccDarkhastFaktor;
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "deleteJayezehForccDarkhastFaktor" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(DarkhastFaktorSatrModel darkhastFaktorSatrModel)
    {
        ContentValues contentValues = new ContentValues();

        if (darkhastFaktorSatrModel.getCcDarkhastFaktorSatr() > 0)
        {
            contentValues.put(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr() , darkhastFaktorSatrModel.getCcDarkhastFaktorSatr());
        }
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor(), darkhastFaktorSatrModel.getCcDarkhastFaktor());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ccTaminKonandeh(), darkhastFaktorSatrModel.getCcTaminKonandeh());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ccKala(), darkhastFaktorSatrModel.getCcKala());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ccKalaCode(), darkhastFaktorSatrModel.getCcKalaCode());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_Tedad3(), darkhastFaktorSatrModel.getTedad3());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_CodeNoeKala(), darkhastFaktorSatrModel.getCodeNoeKala());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ShomarehBach(), darkhastFaktorSatrModel.getShomarehBach());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_TarikhTolid(), darkhastFaktorSatrModel.getTarikhTolid());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_MablaghForosh(), darkhastFaktorSatrModel.getMablaghForosh());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_MablaghForoshKhalesKala(), darkhastFaktorSatrModel.getMablaghForoshKhalesKala());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_MablaghTakhfifNaghdiVahed(), darkhastFaktorSatrModel.getMablaghTakhfifNaghdiVahed());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_Maliat(), darkhastFaktorSatrModel.getMaliat());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_Avarez(), darkhastFaktorSatrModel.getAvarez());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ccAfrad(), darkhastFaktorSatrModel.getCcAfrad());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ExtraProp_IsOld(), darkhastFaktorSatrModel.getExtraProp_IsOld());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_TarikhEngheza(), darkhastFaktorSatrModel.getTarikhEngheza());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ccAnbarMarjoee(), darkhastFaktorSatrModel.getCcAnbarMarjoee());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ccAnbarGhesmat(), darkhastFaktorSatrModel.getCcAnbarGhesmat());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_GheymatMasrafKonandeh(), darkhastFaktorSatrModel.getGheymatMasrafKonandeh());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_GheymatForoshAsli(), darkhastFaktorSatrModel.getGheymatForoshAsli());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_GheymatMasrafKonandehAsli(), darkhastFaktorSatrModel.getGheymatMasrafKonandehAsli());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_GheymatKharid(), darkhastFaktorSatrModel.getGheymatKharid());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ExtraProp_Maliat(), darkhastFaktorSatrModel.getExtraProp_Maliat());
        contentValues.put(DarkhastFaktorSatrModel.COLUMN_ExtraProp_Avarez(), darkhastFaktorSatrModel.getExtraProp_Avarez());
        return contentValues;
    }


    private ArrayList<DarkhastFaktorSatrModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorSatrModel darkhastFaktorSatrModel = new DarkhastFaktorSatrModel();

            darkhastFaktorSatrModel.setCcDarkhastFaktorSatr(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr())));
            darkhastFaktorSatrModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorSatrModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccTaminKonandeh())));
            darkhastFaktorSatrModel.setCcKala(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKala())));
            darkhastFaktorSatrModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKalaCode())));
            darkhastFaktorSatrModel.setTedad3(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Tedad3())));
            darkhastFaktorSatrModel.setCodeNoeKala(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_CodeNoeKala())));
            darkhastFaktorSatrModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ShomarehBach())));
            darkhastFaktorSatrModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_TarikhTolid())));
            darkhastFaktorSatrModel.setMablaghForosh(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghForosh())));
            darkhastFaktorSatrModel.setMablaghForoshKhalesKala(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghForoshKhalesKala())));
            darkhastFaktorSatrModel.setMablaghTakhfifNaghdiVahed(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghTakhfifNaghdiVahed())));
            darkhastFaktorSatrModel.setMaliat(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Maliat())));
            darkhastFaktorSatrModel.setAvarez(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Avarez())));
            darkhastFaktorSatrModel.setCcAfrad(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccAfrad())));
            darkhastFaktorSatrModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ExtraProp_IsOld())) > 0);
            darkhastFaktorSatrModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_TarikhEngheza())));
            darkhastFaktorSatrModel.setCcAnbarMarjoee(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccAnbarMarjoee())));
            darkhastFaktorSatrModel.setCcAnbarGhesmat(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccAnbarGhesmat())));
            darkhastFaktorSatrModel.setGheymatMasrafKonandeh(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_GheymatMasrafKonandeh())));
            darkhastFaktorSatrModel.setGheymatForoshAsli(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_GheymatForoshAsli())));
            darkhastFaktorSatrModel.setGheymatMasrafKonandehAsli(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_GheymatMasrafKonandehAsli())));
            darkhastFaktorSatrModel.setGheymatKharid(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_GheymatKharid())));
            darkhastFaktorSatrModel.setExtraProp_Maliat(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ExtraProp_Maliat())));
            darkhastFaktorSatrModel.setExtraProp_Avarez(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ExtraProp_Avarez())));
            darkhastFaktorSatrModels.add(darkhastFaktorSatrModel);
            cursor.moveToNext();
        }
        return darkhastFaktorSatrModels;
    }



}
