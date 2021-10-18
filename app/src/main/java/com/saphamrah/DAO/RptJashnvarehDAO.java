package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllRptJashnvarehResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RptJashnvarehDAO {
    private DBHelper dbHelper;
    private Context context;

    public RptJashnvarehDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptJashnvarehDAO", "", "constructor", "");
        }
    }


    private String[] allColumns() {
        return new String[]
                {
                        RptJashnvarehForoshModel.Column_Radif(),
                        RptJashnvarehForoshModel.Column_ccJashnvarehForosh(),
                        RptJashnvarehForoshModel.Column_ccJashnvarehForoshSatr(),
                        RptJashnvarehForoshModel.Column_SharhJashnvareh(),
                        RptJashnvarehForoshModel.Column_ccDarkhastFaktor(),
                        RptJashnvarehForoshModel.Column_ccMoshtary(),
                        RptJashnvarehForoshModel.Column_CodeMoshtary(),
                        RptJashnvarehForoshModel.Column_NameMoshtary(),
                        RptJashnvarehForoshModel.Column_ccMarkazSazmanForosh(),
                        RptJashnvarehForoshModel.Column_NameMarkaz(),
                        RptJashnvarehForoshModel.Column_NameSazmanForosh(),
                        RptJashnvarehForoshModel.Column_NoeMohasebeh(),
                        RptJashnvarehForoshModel.Column_MabnaMohasebeh(),
                        RptJashnvarehForoshModel.Column_SharhMabnaMohasebeh(),
                        RptJashnvarehForoshModel.Column_txtNoeMohasebeh(),
                        RptJashnvarehForoshModel.Column_MohasebehBarAsase(),
                        RptJashnvarehForoshModel.Column_AzTarikhJashnvareh(),
                        RptJashnvarehForoshModel.Column_TaTarikhJashnvareh(),
                        RptJashnvarehForoshModel.Column_AzTarikhJashnvarehSatr(),
                        RptJashnvarehForoshModel.Column_TaTarikhJashnvarehSatr(),
                        RptJashnvarehForoshModel.Column_txtCodeNoeBastehBandy(),
                        RptJashnvarehForoshModel.Column_txtCodeNoeBastehBandyBeEza(),
                        RptJashnvarehForoshModel.Column_Az(),
                        RptJashnvarehForoshModel.Column_Ta(),
                        RptJashnvarehForoshModel.Column_BeEza(),
                        RptJashnvarehForoshModel.Column_EmtiazJashnvareh(),
                        RptJashnvarehForoshModel.Column_RialYekEmtiazJashnvareh(),
                        RptJashnvarehForoshModel.Column_EmtiazMoshtary(),
                        RptJashnvarehForoshModel.Column_RialEmtiazMoshtary(),
                        RptJashnvarehForoshModel.Column_Doreh(),
                        RptJashnvarehForoshModel.Column_TarikhMohasebehEmtiaz(),

                };
    }


    public void fetchRptJashnvareh(final Context context, final String activityNameForLog, int ccForoshandeh, String ccMoshtaries, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, this.getClass().getSimpleName(), activityNameForLog, "fetchAllrptAmarForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllRptJashnvarehResult> call = apiServiceGet.getRptJashnvarehForosh(ccForoshandeh, ccMoshtaries);
            call.enqueue(new Callback<GetAllRptJashnvarehResult>() {
                @Override
                public void onResponse(Call<GetAllRptJashnvarehResult> call, Response<GetAllRptJashnvarehResult> response) {
                    try {
                        Log.i("fetchAllReport", "onResponse");
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, this.getClass().getSimpleName(), "", "fetchRptJashnvareh", "onResponse");
                        }


                    } catch (Exception e) {
                        Log.i("fetchAllReport", "onCatchException " + e.getMessage());
                        e.printStackTrace();

                    }
                    try {
                        if (response.isSuccessful()) {
                            GetAllRptJashnvarehResult result = response.body();
                            if (result != null) {
                                Log.i("fetchAllReport", "result not null ");
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());

                                    Log.i("fetchAllReport", "result not null and successful ");
                                } else {
                                    Log.i("fetchAllReport", "result not null but not successful " + result.getMessage());

                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), this.getClass().getSimpleName(), activityNameForLog, "fetchAllRptJashnavareForoshModel", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            } else {
                                Log.i("fetchAllReport", "result not null " + result.getMessage());

                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), this.getClass().getSimpleName(), activityNameForLog, "fetchAllRptJashnavareForoshModel", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        } else {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, this.getClass().getSimpleName(), activityNameForLog, "fetchAllRptJashnavareForoshModel", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), this.getClass().getSimpleName(), activityNameForLog, "fetchAllRptJashnavareForoshModel", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllRptJashnvarehResult> call, Throwable t) {
                    Log.i(this.getClass().getSimpleName(), "onFailure: ");
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), this.getClass().getSimpleName(), activityNameForLog, "fetchAllRptJashnvarehDAO", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), t.getMessage());
                }
            });
        }
    }


    private String getEndpoint(Call call) {
        String endpoint = "";
        try {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endpoint;
    }


    private static ContentValues modelToContentvalue(RptJashnvarehForoshModel rptJashnvarehForoshModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(RptJashnvarehForoshModel.Column_Radif(), rptJashnvarehForoshModel.getRadif());
        contentValues.put(RptJashnvarehForoshModel.Column_ccJashnvarehForosh(), rptJashnvarehForoshModel.getCcJashnvarehForosh());
        contentValues.put(RptJashnvarehForoshModel.Column_ccJashnvarehForoshSatr(), rptJashnvarehForoshModel.getCcJashnvarehForoshSatr());
        contentValues.put(RptJashnvarehForoshModel.Column_SharhJashnvareh(), rptJashnvarehForoshModel.getSharhJashnvareh());
        contentValues.put(RptJashnvarehForoshModel.Column_ccDarkhastFaktor(), rptJashnvarehForoshModel.getCcDarkhastFaktor());
        contentValues.put(RptJashnvarehForoshModel.Column_ccMoshtary(), rptJashnvarehForoshModel.getCcMoshtary());
        contentValues.put(RptJashnvarehForoshModel.Column_CodeMoshtary(), rptJashnvarehForoshModel.getCodeMoshtary());
        contentValues.put(RptJashnvarehForoshModel.Column_NameMoshtary(), rptJashnvarehForoshModel.getNameMoshtary());
        contentValues.put(RptJashnvarehForoshModel.Column_ccMarkazSazmanForosh(), rptJashnvarehForoshModel.getCcMarkazSazmanForosh());
        contentValues.put(RptJashnvarehForoshModel.Column_NameMarkaz(), rptJashnvarehForoshModel.getNameMarkaz());
        contentValues.put(RptJashnvarehForoshModel.Column_NameSazmanForosh(), rptJashnvarehForoshModel.getNameSazmanForosh());
        contentValues.put(RptJashnvarehForoshModel.Column_NoeMohasebeh(), rptJashnvarehForoshModel.getNoeMohasebeh());
        contentValues.put(RptJashnvarehForoshModel.Column_MabnaMohasebeh(), rptJashnvarehForoshModel.getMabnaMohasebeh());
        contentValues.put(RptJashnvarehForoshModel.Column_SharhMabnaMohasebeh(), rptJashnvarehForoshModel.getSharhMabnaMohasebeh());
        contentValues.put(RptJashnvarehForoshModel.Column_txtNoeMohasebeh(), rptJashnvarehForoshModel.getTxtNoeMohasebeh());
        contentValues.put(RptJashnvarehForoshModel.Column_MohasebehBarAsase(), rptJashnvarehForoshModel.getMohasebehBarAsase());
        contentValues.put(RptJashnvarehForoshModel.Column_AzTarikhJashnvareh(), rptJashnvarehForoshModel.getAzTarikhJashnvareh());
        contentValues.put(RptJashnvarehForoshModel.Column_TaTarikhJashnvareh(), rptJashnvarehForoshModel.getTaTarikhJashnvareh());
        contentValues.put(RptJashnvarehForoshModel.Column_AzTarikhJashnvarehSatr(), rptJashnvarehForoshModel.getAzTarikhJashnvarehSatr());
        contentValues.put(RptJashnvarehForoshModel.Column_TaTarikhJashnvarehSatr(), rptJashnvarehForoshModel.getTaTarikhJashnvarehSatr());
        contentValues.put(RptJashnvarehForoshModel.Column_txtCodeNoeBastehBandy(), rptJashnvarehForoshModel.getTxtCodeNoeBastehBandy());
        contentValues.put(RptJashnvarehForoshModel.Column_txtCodeNoeBastehBandyBeEza(), rptJashnvarehForoshModel.getTxtCodeNoeBastehBandyBeEza());
        contentValues.put(RptJashnvarehForoshModel.Column_Az(), rptJashnvarehForoshModel.getAz());
        contentValues.put(RptJashnvarehForoshModel.Column_Ta(), rptJashnvarehForoshModel.getTa());
        contentValues.put(RptJashnvarehForoshModel.Column_BeEza(), rptJashnvarehForoshModel.getBeEza());
        contentValues.put(RptJashnvarehForoshModel.Column_EmtiazJashnvareh(), rptJashnvarehForoshModel.getEmtiazJashnvareh());
        contentValues.put(RptJashnvarehForoshModel.Column_RialYekEmtiazJashnvareh(), rptJashnvarehForoshModel.getRialYekEmtiazJashnvareh());
        contentValues.put(RptJashnvarehForoshModel.Column_EmtiazMoshtary(), rptJashnvarehForoshModel.getEmtiazMoshtary());
        contentValues.put(RptJashnvarehForoshModel.Column_RialEmtiazMoshtary(), rptJashnvarehForoshModel.getRialEmtiazMoshtary());
        contentValues.put(RptJashnvarehForoshModel.Column_Doreh(), rptJashnvarehForoshModel.getDoreh());
        contentValues.put(RptJashnvarehForoshModel.Column_TarikhMohasebehEmtiaz(), rptJashnvarehForoshModel.getTarikhMohasebehEmtiaz());

        return contentValues;
    }


    private ArrayList<RptJashnvarehForoshModel> cursorToModel(Cursor cursor) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
        PubFunc.DateUtils dateUtils = new PubFunc().new DateUtils();
        PubFunc.FontUtils fontUtils = new PubFunc().new FontUtils();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RptJashnvarehForoshModel rptJashnvarehForoshModel = new RptJashnvarehForoshModel();
            Log.i("cursorToModel", "cursorToModel: ");
            rptJashnvarehForoshModel.setRadif(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_Radif())));
            rptJashnvarehForoshModel.setCcJashnvarehForosh(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_ccJashnvarehForosh())));
            rptJashnvarehForoshModel.setAzTarikhJashnvarehSatr(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_ccJashnvarehForoshSatr())));
            rptJashnvarehForoshModel.setSharhJashnvareh(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_SharhJashnvareh())));
            rptJashnvarehForoshModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_ccDarkhastFaktor())));
            rptJashnvarehForoshModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_ccMoshtary())));
            rptJashnvarehForoshModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_CodeMoshtary())));
            rptJashnvarehForoshModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_NameMoshtary())));
            rptJashnvarehForoshModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_ccMarkazSazmanForosh())));
            rptJashnvarehForoshModel.setNameMarkaz(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_NameMarkaz())));
            rptJashnvarehForoshModel.setNameSazmanForosh(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_NameSazmanForosh())));
            rptJashnvarehForoshModel.setNoeMohasebeh(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_NoeMohasebeh())));
            rptJashnvarehForoshModel.setMabnaMohasebeh(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_MabnaMohasebeh())));
            rptJashnvarehForoshModel.setSharhMabnaMohasebeh(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_SharhMabnaMohasebeh())));
            rptJashnvarehForoshModel.setTxtNoeMohasebeh(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_NoeMohasebeh())));
            rptJashnvarehForoshModel.setMohasebehBarAsase(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_MohasebehBarAsase())));
            try {
                rptJashnvarehForoshModel.setAzTarikhJashnvareh(dateUtils.gregorianToPersianDateTime(sdf.parse(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_AzTarikhJashnvareh())))));
            }catch (Exception e){
                e.printStackTrace();
                rptJashnvarehForoshModel.setAzTarikhJashnvareh(null);
            }
            try {
                rptJashnvarehForoshModel.setTaTarikhJashnvareh(dateUtils.gregorianToPersianDateTime(sdf.parse(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_TaTarikhJashnvareh())))));
            } catch (Exception exception) {
                exception.printStackTrace();
                rptJashnvarehForoshModel.setTaTarikhJashnvareh(null);
            }
            try {
                rptJashnvarehForoshModel.setAzTarikhJashnvarehSatr(dateUtils.gregorianToPersianDateTime(sdf.parse(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_AzTarikhJashnvarehSatr())))));
            } catch (Exception exception) {
                exception.printStackTrace();
                rptJashnvarehForoshModel.setAzTarikhJashnvarehSatr(null);
            }
            try {
                rptJashnvarehForoshModel.setTaTarikhJashnvarehSatr(dateUtils.gregorianToPersianDateTime(sdf.parse(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_TaTarikhJashnvarehSatr())))));

            } catch (Exception e) {
                rptJashnvarehForoshModel.setTaTarikhJashnvarehSatr(null);
            }


            rptJashnvarehForoshModel.setTxtCodeNoeBastehBandy(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_txtCodeNoeBastehBandy())));
            rptJashnvarehForoshModel.setTxtCodeNoeBastehBandyBeEza(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_txtCodeNoeBastehBandyBeEza())));
            rptJashnvarehForoshModel.setAz(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_Az())));
            rptJashnvarehForoshModel.setTa(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_Ta())));
            rptJashnvarehForoshModel.setBeEza(cursor.getDouble(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_BeEza())));
            rptJashnvarehForoshModel.setEmtiazJashnvareh(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_EmtiazJashnvareh())));
            rptJashnvarehForoshModel.setRialYekEmtiazJashnvareh(cursor.getDouble(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_RialYekEmtiazJashnvareh())));
            rptJashnvarehForoshModel.setEmtiazMoshtary(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_EmtiazMoshtary())));
            rptJashnvarehForoshModel.setRialEmtiazMoshtary(cursor.getDouble(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_RialEmtiazMoshtary())));
            rptJashnvarehForoshModel.setDoreh(cursor.getInt(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_Doreh())));
            try {
                rptJashnvarehForoshModel.setTarikhMohasebehEmtiaz(dateUtils.gregorianToPersianDateTime(sdf.parse(cursor.getString(cursor.getColumnIndex(RptJashnvarehForoshModel.Column_TarikhMohasebehEmtiaz())))));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
//            rptJashnvarehForoshModel.setExpanded(false);


            rptJashnvarehForoshModels.add(rptJashnvarehForoshModel);
            cursor.moveToNext();
        }
        return rptJashnvarehForoshModels;
    }


    public boolean insertGroup
            (ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (RptJashnvarehForoshModel rptJashnvarehForoshModel : rptJashnvarehForoshModels) {
                ContentValues contentValues = modelToContentvalue(rptJashnvarehForoshModel);

                db.insertOrThrow(RptJashnvarehForoshModel.TableName(), null, contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnavareForoshDAO", "", "insertGroup", "");
            return false;
        }
    }


    public ArrayList<RptJashnvarehForoshModel> getAll() {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptJashnvarehForoshModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getAll", "");
        }
        return rptJashnvarehForoshModels;
    }

    public RptJashnvarehForoshModel getEmtiazForoshandeh() {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery =
                    "     select   -1 as Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                            "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                            "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                            "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                            "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                            "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                            "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                            "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                            "     SUM(j.EmtiazMoshtary) as EmtiazMoshtary , Sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary ,\n" +
                            "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j ";


            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getAllMoshtaries", "");
        }
        return rptJashnvarehForoshModels.get(0);
    }


    public ArrayList<RptJashnvarehForoshModel> getAllMoshtaries() {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery = "select j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                    "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                    "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                    "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                    "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                    "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                    "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                    "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                    "     SUM(j.EmtiazMoshtary) as EmtiazMoshtary , Sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary ,\n" +
                    "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j group by ccMoshtary order by radif";


            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getAllMoshtaries", "");
        }
        return rptJashnvarehForoshModels;
    }


    public ArrayList<RptJashnvarehForoshModel> getJashnvarehListByccMoshtary(int ccMoshtary) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery = "select * from " + RptJashnvarehForoshModel.TableName() + " where " + RptJashnvarehForoshModel.Column_ccMoshtary() + " = " + ccMoshtary + " group by " + RptJashnvarehForoshModel.Column_ccJashnvarehForosh() + " order by " + RptJashnvarehForoshModel.Column_Radif();
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }

    public ArrayList<RptJashnvarehForoshModel> getJashnvarehSumByccJashnvareh(
            int ccJashnvarehForosh) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery;
            if (ccJashnvarehForosh == -1){
                strQuery ="   select  j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                        "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                        "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                        "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                        "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                        "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                        "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                        "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                        "     SUM(j.EmtiazMoshtary) as EmtiazMoshtary , Sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary,\n" +
                        "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j group by ccJashnvarehForosh";
            }else {
                strQuery = "select  j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                        "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                        "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                        "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                        "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                        "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                        "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                        "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                        "     SUM(j.EmtiazMoshtary) as EmtiazMoshtary , Sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary ,\n" +
                        "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j \n" +
                        "     where  ccJashnvarehForosh = " + ccJashnvarehForosh + " order by Radif";
            }
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }


    public ArrayList<RptJashnvarehForoshModel> getMoshtaryJashnvaresByccMoshtary(
            int ccMoshtary) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery = "select  j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                    "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                    "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                    "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                    "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                    "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                    "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                    "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                    "     j.EmtiazMoshtary as EmtiazMoshtary , j.RialEmtiazMoshtary  as RialEmtiazMoshtary ,\n" +
                    "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j \n" +
                    "     where  ccMoshtary = " + ccMoshtary;
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }


    public ArrayList<RptJashnvarehForoshModel> getSumJashnvarehForoshByccMoshtary(
            int ccMoshtary) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery = "select  j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                    "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                    "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                    "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                    "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                    "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                    "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                    "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                    "     SUM(j.EmtiazMoshtary) as EmtiazMoshtary , Sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary ,\n" +
                    "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j \n" +
                    "     where  ccMoshtary = " + ccMoshtary + " group by ccJashnvarehForosh ";
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }


    public ArrayList<RptJashnvarehForoshModel> getAllJashnvareh() {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery = " select distinct j.* from Rpt_JashnvarehForosh j group by j.ccJashnvarehForosh \n" +
                    "union all\n" +
                    "select -1,-1,0,'همه',"+
                    "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }


    public ArrayList<RptJashnvarehForoshModel> getJashnvarehSatrByccJashnvarehAndccMoshtary(
            int ccMoshtary, int ccJashnvarehForosh) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery = "select * from " + RptJashnvarehForoshModel.TableName() + " where " + RptJashnvarehForoshModel.Column_ccMoshtary() + " = " + ccMoshtary + " and " + RptJashnvarehForoshModel.Column_ccJashnvarehForosh() + " = " + ccJashnvarehForosh + " order by " + RptJashnvarehForoshModel.Column_Radif();
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehSatrByccJashnvarehAndccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }

    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptJashnvarehForoshModel.TableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "deleteAll", "");
            return false;
        }
    }


    public ArrayList<RptJashnvarehForoshModel> getAllMoshtaryByccJashnvareh(
            int ccJashnvarehForosh) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery = " select  j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                    "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                    "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                    "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                    "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                    "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                    "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                    "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                    "     SUM(j.EmtiazMoshtary) as EmtiazMoshtary , Sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary ,\n" +
                    "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j \n" +
                    "     where  ccJashnvarehForosh = " + ccJashnvarehForosh + " group by ccMoshtary order by Radif";
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }

    public ArrayList<RptJashnvarehForoshModel> getAllJashnvarehByccCustomer(int ccMoshtary) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery =
                    "     select distinct  j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                            "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                            "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                            "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                            "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                            "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                            "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                            "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                            "     sum(j.EmtiazMoshtary) as EmtiazMoshtary ,sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary ,\n" +
                            "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j \n" +
                            "     where  ccMoshtary = " + ccMoshtary + " order by Radif";
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }

    public ArrayList<RptJashnvarehForoshModel> getAllJashnvarehSatrByccCustomer(int ccMoshtary) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery =
                    "     select distinct  j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                            "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                            "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                            "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                            "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                            "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                            "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                            "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                            "     sum(j.EmtiazMoshtary) as EmtiazMoshtary ,sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary ,\n" +
                            "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j \n" +
                            "     where  ccMoshtary = " + ccMoshtary + " group by ccJashnvarehForosh order by Radif";
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }

    public ArrayList<RptJashnvarehForoshModel> getAllMoshtarySatrByJashnvareh(int ccMoshtary,
                                                                              int ccJashnvarehForosh) {
        ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQuery = "select  j.Radif , j.ccJashnvarehForosh , j.ccJashnvarehForoshSatr ,\n" +
                    "     j.SharhJashnvareh , j.ccDarkhastFaktor, j.ccMoshtary , j.CodeMoshtary,\n" +
                    "     j.NameMoshtary , j.ccMarkazSazmanForosh , j.NameMarkaz, j.NameSazmanForosh ,\n" +
                    "     j.NoeMohasebeh , j.MabnaMohasebeh , j.SharhMabnaMohasebeh,\n" +
                    "     j.txtNoeMohasebeh,j.MohasebehBarAsase , j.AzTarikhJashnvareh , j.TaTarikhJashnvareh,\n" +
                    "     j.AzTarikhJashnvarehSatr, j.TaTarikhJashnvarehSatr,\n" +
                    "     j.txtCodeNoeBastehBandy , j.txtCodeNoeBastehBandyBeEza , j.Az, j.Ta, j.BeEza,\n" +
                    "     j.EmtiazJashnvareh, j.RialYekEmtiazJashnvareh,  \n" +
                    "     SUM(j.EmtiazMoshtary) as EmtiazMoshtary , Sum(j.RialEmtiazMoshtary)  as RialEmtiazMoshtary ,\n" +
                    "     j.Doreh , j.TarikhMohasebehEmtiaz from Rpt_JashnvarehForosh j \n" +
                    "     where  ccJashnvarehForosh = " + ccJashnvarehForosh + " and ccMoshtary = " + ccMoshtary + "  order by Radif";
            Cursor cursor = db.rawQuery(strQuery, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptJashnvarehForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptJashnvarehForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptJashnarehDAO", "", "getJashnvarehListByccMoshtary", "");
        }
        return rptJashnvarehForoshModels;
    }
}
