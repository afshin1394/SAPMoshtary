package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;

import com.saphamrah.Model.MahalCodePostiModel;

import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetRtpThreeMonthPurchaseResult;
import com.saphamrah.protos.RptCustomer3MonthPurchaseGrpc;
import com.saphamrah.protos.RptCustomer3MonthPurchaseReply;
import com.saphamrah.protos.RptCustomer3MonthPurchaseReplyList;
import com.saphamrah.protos.RptCustomer3MonthPurchaseRequest;

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

public class Rpt3MonthPurchaseDAO {
    private static final String TAG = Rpt3MonthPurchaseDAO.class.getSimpleName() ;
    Rpt3MonthPurchaseModel modelGetTABLE_NAME = new Rpt3MonthPurchaseModel();
    private DBHelper dbHelper;


    public Rpt3MonthPurchaseDAO(Context context) {
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptThreeMonthPurchaseDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        modelGetTABLE_NAME.getCOLUMN_ccMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_ccDarkhastFaktor(),
                        modelGetTABLE_NAME.getCOLUMN_CodeMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_NameMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_ShomarehFaktor(),
                        modelGetTABLE_NAME.getCOLUMN_TarikhFaktor(),
                        modelGetTABLE_NAME.getCOLUMN_MablaghKhalesFaktor(),
                        modelGetTABLE_NAME.getCOLUMN_MarjoeeKamel(),
                };
    }

    public void fetchRptThreeMonthPurchaseGrpc(final Context context, final String activityNameForLog, int ccForoshandeh, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchaseGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                RptCustomer3MonthPurchaseGrpc.RptCustomer3MonthPurchaseBlockingStub rptCustomer3MonthPurchaseBlockingStub = RptCustomer3MonthPurchaseGrpc.newBlockingStub(managedChannel);
                RptCustomer3MonthPurchaseRequest rptCustomer3MonthPurchaseRequest = RptCustomer3MonthPurchaseRequest.newBuilder().setSalesManID(String.valueOf(ccForoshandeh)).build();
                Callable<RptCustomer3MonthPurchaseReplyList> RptCustomer3MonthPurchaseReplyListCallable = () -> rptCustomer3MonthPurchaseBlockingStub.getRptCustomer3MonthPurchase(rptCustomer3MonthPurchaseRequest);
                RxAsync.makeObservable(RptCustomer3MonthPurchaseReplyListCallable)
                        .map(rptCustomer3MonthPurchaseReplyList ->  {
                            ArrayList<Rpt3MonthPurchaseModel> models = new ArrayList<>();
                            for (RptCustomer3MonthPurchaseReply reply : rptCustomer3MonthPurchaseReplyList.getRptCustomer3MonthPurchasesList()) {
                                Rpt3MonthPurchaseModel model = new Rpt3MonthPurchaseModel();

                                model.setCcMoshtary(reply.getCustomerID());
                                model.setCcDarkhastFaktor(reply.getInvoiceRequestID());
                                model.setCodeMoshtary(reply.getCustomerCode());
                                model.setMarjoeeKamel(reply.getCompleteReturn());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setShomarehFaktor(reply.getInvoiceNumber());
                                model.setTarikhFaktor(reply.getInvoiceDate());
                                model.setMablaghKhalesFaktor(reply.getPureInvoicePrice());


                                models.add(model);

                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Rpt3MonthPurchaseModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels) {
                                retrofitResponse.onSuccess(rpt3MonthPurchaseModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchaseGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }

    public void fetchRptThreeMonthPurchas(final Context context, final String activityNameForLog, int ccForoshandeh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchas", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetRtpThreeMonthPurchaseResult> call = apiServiceGet.getRtpThreeMonthPurchaseResult(String.valueOf(ccForoshandeh));
            call.enqueue(new Callback<GetRtpThreeMonthPurchaseResult>() {
                @Override
                public void onResponse(Call<GetRtpThreeMonthPurchaseResult> call, Response<GetRtpThreeMonthPurchaseResult> response) {
                    try {
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, Rpt3MonthPurchaseDAO.class.getSimpleName(), "", "fetchBargashty", "onResponse");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetRtpThreeMonthPurchaseResult result = response.body();
                            if (result != null) {
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());
                                } else {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchas", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchas", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchas", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchas", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetRtpThreeMonthPurchaseResult> call, Throwable t) {
                    String endpoint = "";
                    try {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/") + 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchas", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), t.getMessage());
                }
            });
        }
    }



    @SuppressLint("LongLogTag")
    public boolean insertGroup(ArrayList<Rpt3MonthPurchaseModel> rptThreeMonthPurchaseModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (Rpt3MonthPurchaseModel rptThreeMonthPurchaseModel1 : rptThreeMonthPurchaseModel) {
                ContentValues contentValues = modelToContentvalue(rptThreeMonthPurchaseModel1);
                db.insertOrThrow(rptThreeMonthPurchaseModel1.getTABLE_NAME(), null, contentValues);
                Log.i("RptThreeMonthPurchaseDAO", contentValues.toString());
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "insertGroup", "");
            return false;
        }
    }


    public ArrayList<Rpt3MonthPurchaseModel> getAll() {
        ArrayList<Rpt3MonthPurchaseModel> rptThreeMonthPurchaseModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptThreeMonthPurchaseModels = cursorToModelGetAll(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "getAll", "");
        }
        return rptThreeMonthPurchaseModels;
    }

    public ArrayList<Rpt3MonthPurchaseModel> getAllByCcMoshtary(int ccMoshtary) {
        ArrayList<Rpt3MonthPurchaseModel> rptThreeMonthPurchaseModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), modelGetTABLE_NAME.getCOLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptThreeMonthPurchaseModels = cursorToModelGetAll(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "getAll", "");
        }
        return rptThreeMonthPurchaseModels;
    }


    public ArrayList<Rpt3MonthPurchaseModel> getFilteredListByFactorNu(int ccMoshtary,String filter) {
        Log.i(TAG, "getAllByCcMoshtaryFilterByNameMoshtary: "+ccMoshtary +"filter:"+filter);

        ArrayList<Rpt3MonthPurchaseModel> rptThreeMonthPurchaseModels = new ArrayList<>();
        try {
            String strSql="select * from RptThreeMonthPurchase  " +"\n"+
                    " where ccMoshtary = " + ccMoshtary +"\n"+
                    " AND ShomarehFaktor like '%"+ filter+"%'";

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor=db.rawQuery(strSql,null);

            if (cursor != null) {
                Log.i(TAG, "getAllByCcMoshtaryFilterByNameMoshtary: "+cursor);
                if (cursor.getCount() > 0) {
                    Log.i(TAG, "getAllByCcMoshtaryFilterByNameMoshtary: "+cursor.getCount());

                    rptThreeMonthPurchaseModels = cursorToModelGetAll(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "getAll", "");
        }
        return rptThreeMonthPurchaseModels;
    }



//    public ArrayList<Rpt3MonthPurchaseModel> getAllByCcMoshtaryFilterByNameMoshtary(int ccMoshtary,String filter) {
//        Log.i(TAG, "getAllByCcMoshtaryFilterByNameMoshtary: "+ccMoshtary +"filter:"+filter);
//
//        ArrayList<Rpt3MonthPurchaseModel> rptThreeMonthPurchaseModels = new ArrayList<>();
//        try {
//            String strSql="select * from RptThreeMonthPurchase  where ccMoshtary = "+ccMoshtary+"AND NameMoshtary like '%"+filter+"%'";
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursor=db.rawQuery(strSql,null);
//
//            if (cursor != null) {
//                Log.i(TAG, "getAllByCcMoshtaryFilterByNameMoshtary: "+cursor);
//                if (cursor.getCount() > 0) {
//                    Log.i(TAG, "getAllByCcMoshtaryFilterByNameMoshtary: "+cursor.getCount());
//
//                    rptThreeMonthPurchaseModels = cursorToModelGetAll(cursor);
//                }
//                cursor.close();
//            }
//            db.close();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            PubFunc.Logger logger = new PubFunc().new Logger();
//            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
//            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "getAll", "");
//        }
//        return rptThreeMonthPurchaseModels;
//    }
//
//    public ArrayList<Rpt3MonthPurchaseModel> getAllByCcMoshtaryFilterByCodeMoshtary(int ccMoshtary,String filter) {
//        Log.i(TAG, "getAllByCcMoshtaryFilterByCodeMoshtary: "+ccMoshtary +"filter:"+filter);
//        ArrayList<Rpt3MonthPurchaseModel> rptThreeMonthPurchaseModels = new ArrayList<>();
//        try {
//            String strSql="select * from RptThreeMonthPurchase  where ccMoshtary = "+ccMoshtary+"AND CodeMoshtary like '%"+filter+"%'";
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursor=db.rawQuery(strSql,null);
//
//            if (cursor != null) {
//                Log.i(TAG, "getAllByCcMoshtaryFilterByCodeMoshtary: "+cursor);
//                if (cursor.getCount() > 0) {
//                    Log.i(TAG, "getAllByCcMoshtaryFilterByCodeMoshtary: "+cursor.getCount());
//                    rptThreeMonthPurchaseModels = cursorToModelGetAll(cursor);
//                }
//                cursor.close();
//            }
//            db.close();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            PubFunc.Logger logger = new PubFunc().new Logger();
//            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
//            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "getAll", "");
//        }
//        return rptThreeMonthPurchaseModels;
//    }


    public ArrayList<Rpt3MonthGetSumModel> getSumByMoshtary() {
        ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels = new ArrayList<Rpt3MonthGetSumModel>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " select distinct ccmoshtary , codemoshtary , namemoshtary , count(ccdarkhastfaktor) as tedad , sum(mablaghkhalesfaktor) as summablagh \n" +
                    " from RptThreeMonthPurchase \n" +
                    " group by ccmoshtary , codemoshtary , namemoshtary";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rtp3MonthGetSumModels = cursorToModelSum(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "getSumByMoshtary", "");
        }
        return rtp3MonthGetSumModels;
    }


    public ArrayList<Rpt3MonthGetSumModel> getSumByMoshtaryFilteredByCodeMoshtary(String filter) {

        ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels = new ArrayList<Rpt3MonthGetSumModel>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String query =  " select ccmoshtary , codemoshtary , namemoshtary , count(ccdarkhastfaktor) as tedad , sum(mablaghkhalesfaktor) as summablagh  " +
                    "                     from RptThreeMonthPurchase  where CodeMoshtary like '%"+ filter +"%' " +
                    "                     group by ccmoshtary , codemoshtary , namemoshtary ";
//            String query =  "select ccmoshtary , codemoshtary , namemoshtary , count(ccdarkhastfaktor) as tedad , sum(mablaghkhalesfaktor) as summablagh"+
//            "from RptThreeMonthPurchase  where codemoshtary like '%"+filter+"%'"+
//            "group by ccmoshtary , codemoshtary , namemoshtary " ;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rtp3MonthGetSumModels = cursorToModelSum(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "getSumByMoshtary", "");
        }
        return rtp3MonthGetSumModels;
    }


    public ArrayList<Rpt3MonthGetSumModel> getSumByMoshtaryFilteredByNameMoshtary(String filter) {
        ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels = new ArrayList<Rpt3MonthGetSumModel>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query =  " select ccmoshtary , codemoshtary , namemoshtary , count(ccdarkhastfaktor) as tedad , sum(mablaghkhalesfaktor) as summablagh  " +
                    "                     from RptThreeMonthPurchase  where namemoshtary like '%"+ filter +"%' " +
                    "                     group by ccmoshtary , codemoshtary , namemoshtary ";

//            String query =  " select ccmoshtary , codemoshtary , namemoshtary , count(ccdarkhastfaktor) as tedad , sum(mablaghkhalesfaktor) as summablagh"+
//                    "from RptThreeMonthPurchase  where namemoshtary like '%"+filter+"%'"+
//                    "group by ccmoshtary , codemoshtary , namemoshtary " ;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                Log.i("cursurSize", "getSumByMoshtaryFilteredByCodeMoshtary: "+cursor.getCount());
                if (cursor.getCount() > 0) {
                    rtp3MonthGetSumModels = cursorToModelSum(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "Rpt3MonthPurchaseDAO", "", "getSumByMoshtary", "");
        }
        return rtp3MonthGetSumModels;
    }


    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.delete(modelGetTABLE_NAME.getTABLE_NAME(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll, MahalCodePostiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "MahalCodePostiDAO", "", "deleteAll", "");
            return false;
        }
    }

    private ContentValues modelToContentvalue(Rpt3MonthPurchaseModel rptThreeMonthPurchaseModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(modelGetTABLE_NAME.getCOLUMN_ccMoshtary(), rptThreeMonthPurchaseModel.getCcMoshtary());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_ccDarkhastFaktor(), rptThreeMonthPurchaseModel.getCcDarkhastFaktor());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_CodeMoshtary(), rptThreeMonthPurchaseModel.getCodeMoshtary());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_NameMoshtary(), rptThreeMonthPurchaseModel.getNameMoshtary());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_ShomarehFaktor(), rptThreeMonthPurchaseModel.getShomarehFaktor());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_TarikhFaktor(), rptThreeMonthPurchaseModel.getTarikhFaktor());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_MablaghKhalesFaktor(), rptThreeMonthPurchaseModel.getMablaghKhalesFaktor());
        contentValues.put(modelGetTABLE_NAME.getCOLUMN_MarjoeeKamel(), rptThreeMonthPurchaseModel.getMarjoeeKamel());


        return contentValues;
    }


    private ArrayList<Rpt3MonthPurchaseModel> cursorToModelGetAll(Cursor cursor) {
        ArrayList<Rpt3MonthPurchaseModel> rptThreeMonthPurchaseModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Rpt3MonthPurchaseModel rptThreeMonthPurchaseModel = new Rpt3MonthPurchaseModel();

            rptThreeMonthPurchaseModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(rptThreeMonthPurchaseModel.getCOLUMN_ccMoshtary())));
            rptThreeMonthPurchaseModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(rptThreeMonthPurchaseModel.getCOLUMN_CodeMoshtary())));
            rptThreeMonthPurchaseModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(rptThreeMonthPurchaseModel.getCOLUMN_NameMoshtary())));
            rptThreeMonthPurchaseModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(rptThreeMonthPurchaseModel.getCOLUMN_ccDarkhastFaktor())));
            rptThreeMonthPurchaseModel.setShomarehFaktor(cursor.getString(cursor.getColumnIndex(rptThreeMonthPurchaseModel.getCOLUMN_ShomarehFaktor())));
            rptThreeMonthPurchaseModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(rptThreeMonthPurchaseModel.getCOLUMN_TarikhFaktor())));
            rptThreeMonthPurchaseModel.setMablaghKhalesFaktor(cursor.getLong(cursor.getColumnIndex(rptThreeMonthPurchaseModel.getCOLUMN_MablaghKhalesFaktor())));
            rptThreeMonthPurchaseModel.setMarjoeeKamel(cursor.getInt(cursor.getColumnIndex(rptThreeMonthPurchaseModel.getCOLUMN_MarjoeeKamel())));


            rptThreeMonthPurchaseModels.add(rptThreeMonthPurchaseModel);
            cursor.moveToNext();
        }
        return rptThreeMonthPurchaseModels;
    }

    private ArrayList<Rpt3MonthGetSumModel> cursorToModelSum(Cursor cursor) {
        ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Rpt3MonthGetSumModel rtp3MonthGetSumModel = new Rpt3MonthGetSumModel();
            rtp3MonthGetSumModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(rtp3MonthGetSumModel.getCOLUMN_ccMoshtary())));
            rtp3MonthGetSumModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(rtp3MonthGetSumModel.getCOLUMN_CodeMoshtary())));
            rtp3MonthGetSumModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(rtp3MonthGetSumModel.getCOLUMN_NameMoshtary())));
            rtp3MonthGetSumModel.setSumMablagh(cursor.getLong(cursor.getColumnIndex(rtp3MonthGetSumModel.getCOLUMN_summablagh())));
            rtp3MonthGetSumModel.setTedad(cursor.getInt(cursor.getColumnIndex(rtp3MonthGetSumModel.getCOLUMN_tedad())));


            rtp3MonthGetSumModels.add(rtp3MonthGetSumModel);
            cursor.moveToNext();
        }
        return rtp3MonthGetSumModels;
    }


}
