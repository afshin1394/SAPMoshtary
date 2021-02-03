package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.MoshtaryGharardadKalaModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;

import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryGharardadKalaResult;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryGharardadKalaDAO {
    private DBHelper dbHelper;
    private Context context;


    public MoshtaryGharardadKalaDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryGhararDadKalaDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        MoshtaryGharardadKalaModel.COLUMN_radif(),
                        MoshtaryGharardadKalaModel.COLUMN_ccMoshtaryGharardad(),
                        MoshtaryGharardadKalaModel.COLUMN_ccKalaCode(),
                        MoshtaryGharardadKalaModel.COLUMN_MablaghForosh(),
                        MoshtaryGharardadKalaModel.COLUMN_MablaghMasrafKonandeh(),
                        MoshtaryGharardadKalaModel.COLUMN_ControlMablagh(),
                        MoshtaryGharardadKalaModel.COLUMN_ExtraPropCcSazmanForosh()

                };
    }


    @SuppressLint("LongLogTag")
    public void fetchMoshtaryGharadadKala(final Context context, final String activityNameForLog, int ccSazmanForosh, int ccMoshtaryGharardad, final RetrofitResponse retrofitResponse) {

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharadadKala", "");
            retrofitResponse.onFailed(Constants.HTTP_ERROR(), message);
        } else {
            Log.i("fetchMoshtaryGharardadkala", "fetchMoshtaryGharardadkala" + serverIpModel.getServerIp() + " " + serverIpModel.getPort());
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllMoshtaryGharardadKalaResult> call = apiServiceGet.getMoshtaryGharardadKala((String.valueOf(ccSazmanForosh)), (String.valueOf(ccMoshtaryGharardad)));

            try {
                Response<GetAllMoshtaryGharardadKalaResult> response = call.execute();
                try {
                    if (response.raw().body() != null) {
                        long contentLength = response.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: Response is not Null");
                        logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryGharardadKalaDAO.class.getSimpleName(), "", "fetchMoshtaryGharadadKala", "onResponse");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "exception in response.raw().body()");
                }
                try {

                    if (response.isSuccessful()) {
                        GetAllMoshtaryGharardadKalaResult result = response.body();
                        if (result != null) {
                            if (result.getSuccess()) {
                                result.setData(result.getData());
                                if (result.getData() != null) {
                                    if (result.getData().size() > 0) {
                                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: getTheResponse" + result.getData().get(0).getCcKalaCode() + " " + response.body());
                                        retrofitResponse.onSuccess(result.getData());

                                    }
                                }
                            } else {
                                Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: resultNotSuccessfull");
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                            }

                        } else {
                            Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: retrofit result is null");
                            String endpoint = getEndpoint(call);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                        }

                    } else {
                        Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: failed on Api Call");
                        String endpoint = getEndpoint(call);
                        String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                    }
                } catch (Exception exception) {
                    Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onResponse: checking weather response is successfull" + exception.getMessage());
                    exception.printStackTrace();
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onResponse");
                    retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());

                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.i("GET__RESPONSE_MOSHTARY_GHARARDAD_KALA", "onFailure: 8");
                String endpoint = getEndpoint(call);
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", e.getMessage(), endpoint), MoshtaryGharardadKalaDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardadkala", "onFailure");
                retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), e.getMessage());
            }


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



    public boolean insert(MoshtaryGharardadKalaModel moshtaryGharardadKalaModel) {
        try {
            ContentValues contentValues = modelToContentvalue(moshtaryGharardadKalaModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(MoshtaryGharardadKalaModel.TableName(), null, contentValues);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert, MoshtaryGharardadKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryGharardadKalaDao", "", "insert", "");
            return false;
        }


    }
    public static final String GET_TABLE_COUNT="getTableCount";
    public int getTableItemsCount() {
        Cursor cursor=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strQry = "select * from " + MoshtaryGharardadKalaModel.TableName();
        try {
            cursor=db.rawQuery(strQry, null);
            Log.i(GET_TABLE_COUNT, "getTableItemsCount: "+cursor.getCount());
        } catch (Exception exception) {

        }
        return cursor.getCount();
    }

    public boolean insertGroup(ArrayList<ArrayList<MoshtaryGharardadKalaModel> > moshtaryGharardadKalaModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModelArrayList : moshtaryGharardadKalaModels) {
                for (MoshtaryGharardadKalaModel moshtaryGharardadKalaModel:moshtaryGharardadKalaModelArrayList) {
                    ContentValues contentValues = modelToContentvalue(moshtaryGharardadKalaModel);
                    db.insertOrThrow(MoshtaryGharardadKalaModel.TableName(), null, contentValues);
                }
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
            String message = context.getResources().getString(R.string.errorGroupInsert, MoshtaryGharardadKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryGharardadDAO", "", "insertGroup", "");
            return false;
        }
    }




    public boolean deleteAll() {

        try {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryGharardadKalaModel.TableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, MoshtaryGharardadKalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryGharardadDAO", "", "deleteAll", "");
            return false;
        }
    }

    //TODO
    private static ContentValues modelToContentvalue(MoshtaryGharardadKalaModel moshtaryGharardadKalaModel) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_radif(), moshtaryGharardadKalaModel.getRadif());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_ccMoshtaryGharardad(), moshtaryGharardadKalaModel.getCcMoshtaryGharardad());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_ccKalaCode(), moshtaryGharardadKalaModel.getCcKalaCode());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_MablaghForosh(), moshtaryGharardadKalaModel.getMablaghForosh());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_MablaghMasrafKonandeh(), moshtaryGharardadKalaModel.getMablaghMasrafKonandeh());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_ControlMablagh(), moshtaryGharardadKalaModel.getControlMablagh());
        contentValues.put(MoshtaryGharardadKalaModel.COLUMN_ExtraPropCcSazmanForosh(),moshtaryGharardadKalaModel.getExtraprop_ccSazmanForosh());


        return contentValues;
    }


    private ArrayList<MoshtaryGharardadKalaModel> cursorToModel(Cursor cursor) {
        ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MoshtaryGharardadKalaModel moshtaryGharardadKalaModel = new MoshtaryGharardadKalaModel();

            moshtaryGharardadKalaModel.setRadif(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_radif())));
            moshtaryGharardadKalaModel.setCcMoshtaryGharardad(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_ccMoshtaryGharardad())));
            moshtaryGharardadKalaModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_ccKalaCode())));
            moshtaryGharardadKalaModel.setMablaghForosh(cursor.getLong(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_MablaghForosh())));
            moshtaryGharardadKalaModel.setMablaghMasrafKonandeh(cursor.getLong(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_MablaghMasrafKonandeh())));
            moshtaryGharardadKalaModel.setControlMablagh(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_ControlMablagh())));
            moshtaryGharardadKalaModel.setExtraprop_ccSazmanForosh(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadKalaModel.COLUMN_ExtraPropCcSazmanForosh())));
            moshtaryGharardadKalaModels.add(moshtaryGharardadKalaModel);
            cursor.moveToNext();
        }
        return moshtaryGharardadKalaModels;
    }
}
