package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;

import com.saphamrah.Model.DarkhastFaktorAfradForoshModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.MahalCodePostiModel;

import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetRtpThreeMonthPurchaseResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rpt3MonthPurchaseDAO {
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

    public void fetchRptThreeMonthPurchas(final Context context, final String activityNameForLog, int ccForoshandeh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, Rpt3MonthPurchaseDAO.class.getSimpleName(), activityNameForLog, "fetchRptThreeMonthPurchas", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp(), serverIpModel.getPort()).create(APIService.class);
            Call<GetRtpThreeMonthPurchaseResult> call = apiService.getRtpThreeMonthPurchaseResult(String.valueOf(ccForoshandeh));
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


    public ArrayList<Rpt3MonthGetSumModel> getSumByMoshtary() {
        ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels = new ArrayList<Rpt3MonthGetSumModel>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " select ccmoshtary , codemoshtary , namemoshtary , count(ccdarkhastfaktor) as tedad , sum(mablaghkhalesfaktor) as summablagh \n" +
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
