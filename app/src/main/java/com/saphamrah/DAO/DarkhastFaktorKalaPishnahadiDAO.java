package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.DarkhastFaktorKalaPishnahadiModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.KalaDarkhastFaktorModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MahalCodePostiModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.DarkhastFaktorKalaPishnahadiResult;
import com.saphamrah.protos.BankGrpc;
import com.saphamrah.protos.BankReply;
import com.saphamrah.protos.BankReplyList;
import com.saphamrah.protos.BankRequest;
import com.saphamrah.protos.SuggestedGoodInvoiceRequestGrpc;
import com.saphamrah.protos.SuggestedGoodInvoiceRequestReply;
import com.saphamrah.protos.SuggestedGoodInvoiceRequestReplyList;
import com.saphamrah.protos.SuggestedGoodInvoiceRequestRequest;

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

public class DarkhastFaktorKalaPishnahadiDAO {
    DarkhastFaktorKalaPishnahadiModel modelGetTABLE_NAME = new DarkhastFaktorKalaPishnahadiModel();
    private DBHelper dbHelper;



    public DarkhastFaktorKalaPishnahadiDAO(Context context) {
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorKalaPishnahadiDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        modelGetTABLE_NAME.getCOLUMN_ccDarkhastFaktorKalaPishnahadi(),
                        modelGetTABLE_NAME.getCOLUMN_ccKalaCode(),
                        modelGetTABLE_NAME.getCOLUMN_MinTarikhFaktor(),
                        modelGetTABLE_NAME.getCOLUMN_tedad(),
                        modelGetTABLE_NAME.getCOLUMN_ccMoshtary(),

                };
    }

    public void fetchDarkhastFaktorKalaPishnahadiGrpc(final Context context, final String activityNameForLog, String ccForoshandeh , String ccmoshtarys, final RetrofitResponse retrofitResponse)
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
                SuggestedGoodInvoiceRequestGrpc.SuggestedGoodInvoiceRequestBlockingStub suggestedGoodInvoiceRequestBlockingStub = SuggestedGoodInvoiceRequestGrpc.newBlockingStub(managedChannel);
                SuggestedGoodInvoiceRequestRequest suggestedGoodInvoiceRequestRequest = SuggestedGoodInvoiceRequestRequest.newBuilder().build();
                Callable<SuggestedGoodInvoiceRequestReplyList> getSuggestedGoodInvoiceRequestCallable  = () -> suggestedGoodInvoiceRequestBlockingStub.getSuggestedGoodInvoiceRequest(suggestedGoodInvoiceRequestRequest);
                RxAsync.makeObservable(getSuggestedGoodInvoiceRequestCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(suggestedGoodInvoiceRequestReplyList -> {
                            ArrayList<DarkhastFaktorKalaPishnahadiModel> darkhastFaktorKalaPishnahadiModels = new ArrayList<>();
                            for (SuggestedGoodInvoiceRequestReply suggestedGoodInvoiceRequestReply : suggestedGoodInvoiceRequestReplyList.getSuggestedGoodInvoiceRequestsList()) {
                                DarkhastFaktorKalaPishnahadiModel darkhastFaktorKalaPishnahadiModel = new DarkhastFaktorKalaPishnahadiModel();

                                darkhastFaktorKalaPishnahadiModel.setCcDarkhastFaktorKalaPishnahadi(suggestedGoodInvoiceRequestReply.getSuggestedGoodInvoiceReqCustomerID());
                                darkhastFaktorKalaPishnahadiModel.setCcMoshtary(suggestedGoodInvoiceRequestReply.getCustomerID());
                                darkhastFaktorKalaPishnahadiModel.setCcKalaCode(suggestedGoodInvoiceRequestReply.getGoodCodeID());
                                darkhastFaktorKalaPishnahadiModel.setMinTarikhFaktor(suggestedGoodInvoiceRequestReply.getMinInvoiceDate());
                                darkhastFaktorKalaPishnahadiModel.setTedad(suggestedGoodInvoiceRequestReply.getQuantity());


                                darkhastFaktorKalaPishnahadiModels.add(darkhastFaktorKalaPishnahadiModel);
                            }

                            return darkhastFaktorKalaPishnahadiModels;

                        }).subscribe(new Observer<ArrayList<DarkhastFaktorKalaPishnahadiModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<DarkhastFaktorKalaPishnahadiModel> darkhastFaktorKalaPishnahadiModels) {
                        retrofitResponse.onSuccess(darkhastFaktorKalaPishnahadiModels);
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

    public void fetchDarkhastFaktorKalaPishnahadi(final Context context, final String activityNameForLog, String ccForoshandeh , String ccmoshtarys, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorKalaPishnahadiDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorKalaPishnahadi", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<DarkhastFaktorKalaPishnahadiResult> call = apiServiceGet.getDarkhastFaktorKalaPishnahadiResult(ccForoshandeh , ccmoshtarys);
            call.enqueue(new Callback<DarkhastFaktorKalaPishnahadiResult>() {
                @Override
                public void onResponse(Call<DarkhastFaktorKalaPishnahadiResult> call, Response<DarkhastFaktorKalaPishnahadiResult> response) {
                    try {
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, DarkhastFaktorKalaPishnahadiDAO.class.getSimpleName(), "", "fetchDarkhastFaktorKalaPishnahadi", "onResponse");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (response.isSuccessful()) {
                            DarkhastFaktorKalaPishnahadiResult result = response.body();
                            if (result != null) {
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());
                                } else {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), DarkhastFaktorKalaPishnahadiDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorKalaPishnahadi", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            } else {
                                String endpoint = "";
                                try {
                                    endpoint = call.request().url().toString();
                                    endpoint = endpoint.substring(endpoint.lastIndexOf("/") + 1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), DarkhastFaktorKalaPishnahadiDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorKalaPishnahadi", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        } else {
                            String endpoint = "";
                            try {
                                endpoint = call.request().url().toString();
                                endpoint = endpoint.substring(endpoint.lastIndexOf("/") + 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, DarkhastFaktorKalaPishnahadiDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorKalaPishnahadi", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), DarkhastFaktorKalaPishnahadiDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorKalaPishnahadi", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<DarkhastFaktorKalaPishnahadiResult> call, Throwable t) {
                    String endpoint = "";
                    try {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/") + 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), DarkhastFaktorKalaPishnahadiDAO.class.getSimpleName(), activityNameForLog, "fetchDarkhastFaktorKalaPishnahadi", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), t.getMessage());
                }
            });
        }
    }



    @SuppressLint("LongLogTag")
    public boolean insertGroup(ArrayList<DarkhastFaktorKalaPishnahadiModel> darkhastFaktorKalaPishnahadiModelArrayList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (DarkhastFaktorKalaPishnahadiModel darkhastFaktorKalaPishnahadiModel : darkhastFaktorKalaPishnahadiModelArrayList) {
                ContentValues contentValues = modelToContentvalue(darkhastFaktorKalaPishnahadiModel);
                db.insertOrThrow(darkhastFaktorKalaPishnahadiModel.getTableName(), null, contentValues);
                Log.i("DarkhastFaktorKalaPishnahadiDAO", contentValues.toString());
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            if (db.inTransaction()) {
                db.endTransaction();
            }
            if (db.isOpen()) {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorGroupInsert, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "DarkhastFaktorKalaPishnahadiDAO", "", "insertGroup", "");
            return false;
        }
    }


    public ArrayList<DarkhastFaktorKalaPishnahadiModel> getAll() {
        ArrayList<DarkhastFaktorKalaPishnahadiModel> darkhastFaktorKalaPishnahadiModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTableName(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    darkhastFaktorKalaPishnahadiModels = cursorToModelGetAll(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "DarkhastFaktorKalaPishnahadiDAO", "", "getAll", "");
        }
        return darkhastFaktorKalaPishnahadiModels;
    }


    public SparseArray<KalaDarkhastFaktorModel> getByccMoshtaryForMinQTY(int ccMoshtary)
    {
        SparseArray<KalaDarkhastFaktorModel> kalaDarkhastFaktors = new SparseArray<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT ccKalaCode, MinTarikhFaktor AS TarikhFaktor, Tedad AS TedadFaktor \n" +
                    " FROM DarkhastFaktorKalaPishnahadi \n" +
                    " WHERE ccMoshtary = " + ccMoshtary ;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    KalaDarkhastFaktorModel kalaDarkhastFaktor;
                    while (!cursor.isAfterLast())
                    {
                        kalaDarkhastFaktor = new KalaDarkhastFaktorModel();
                        kalaDarkhastFaktor.setCcKalaCode(cursor.getInt(cursor.getColumnIndex("ccKalaCode")));
                        kalaDarkhastFaktor.setTarikhFaktor(cursor.getString(cursor.getColumnIndex("TarikhFaktor")));
                        kalaDarkhastFaktor.setTedadFaktor(cursor.getInt(cursor.getColumnIndex("TedadFaktor")));
                        kalaDarkhastFaktors.put(kalaDarkhastFaktor.getCcKalaCode(), kalaDarkhastFaktor);
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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(BaseApplication.getContext(), LogPPCModel.LOG_EXCEPTION, message, "DarkhastFaktorDAO" , "" , "getTedadPishnahadiDarkhast" , "");
        }
        return kalaDarkhastFaktors;
    }

    public int getTedadKharid3Mah(int ccMoshtary)
    {
        int TedadKharid3Mah=0;
        String StrSQL = " SELECT  COUNT(DISTINCT ccKalaCode) "
                + " FROM    DarkhastFaktorKalaPishnahadi "
                + " WHERE   ccMoshtary = ? ";
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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll , DarkhastFaktorSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrDAO" , "" , "deleteAll" , "");
        }
        return TedadKharid3Mah;
    }

    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.delete(modelGetTABLE_NAME.getTableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "DarkhastFaktorKalaPishnahadiDAO", "", "deleteAll", "");
            return false;
        }
    }

    private ContentValues modelToContentvalue(DarkhastFaktorKalaPishnahadiModel darkhastFaktorKalaPishnahadiModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(modelGetTABLE_NAME.getCOLUMN_ccDarkhastFaktorKalaPishnahadi(), darkhastFaktorKalaPishnahadiModel.getCcDarkhastFaktorKalaPishnahadi());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_ccKalaCode(), darkhastFaktorKalaPishnahadiModel.getCcKalaCode());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_MinTarikhFaktor(), darkhastFaktorKalaPishnahadiModel.getMinTarikhFaktor());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_tedad(), darkhastFaktorKalaPishnahadiModel.getTedad());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_ccMoshtary(), darkhastFaktorKalaPishnahadiModel.getCcMoshtary());
        return contentValues;
    }


    private ArrayList<DarkhastFaktorKalaPishnahadiModel> cursorToModelGetAll(Cursor cursor) {
        ArrayList<DarkhastFaktorKalaPishnahadiModel> darkhastFaktorKalaPishnahadiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DarkhastFaktorKalaPishnahadiModel darkhastFaktorKalaPishnahadiModel = new DarkhastFaktorKalaPishnahadiModel();

            darkhastFaktorKalaPishnahadiModel.setCcDarkhastFaktorKalaPishnahadi(cursor.getInt(cursor.getColumnIndex(darkhastFaktorKalaPishnahadiModel.getCOLUMN_ccDarkhastFaktorKalaPishnahadi())));
            darkhastFaktorKalaPishnahadiModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(darkhastFaktorKalaPishnahadiModel.getCOLUMN_ccKalaCode())));
            darkhastFaktorKalaPishnahadiModel.setMinTarikhFaktor(cursor.getString(cursor.getColumnIndex(darkhastFaktorKalaPishnahadiModel.getCOLUMN_MinTarikhFaktor())));
            darkhastFaktorKalaPishnahadiModel.setTedad(cursor.getInt(cursor.getColumnIndex(darkhastFaktorKalaPishnahadiModel.getCOLUMN_tedad())));
            darkhastFaktorKalaPishnahadiModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(darkhastFaktorKalaPishnahadiModel.getCOLUMN_ccMoshtary())));

            darkhastFaktorKalaPishnahadiModels.add(darkhastFaktorKalaPishnahadiModel);
            cursor.moveToNext();
        }
        return darkhastFaktorKalaPishnahadiModels;
    }

}
