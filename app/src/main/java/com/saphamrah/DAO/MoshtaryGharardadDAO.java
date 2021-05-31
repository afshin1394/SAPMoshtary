package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.MoshtaryGharardadKalaModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllMoshtaryGharardadResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoshtaryGharardadDAO {
    private DBHelper dbHelper;
    private Context context;


    public MoshtaryGharardadDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryGharardadDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad(),
                        MoshtaryGharardadModel.COLUMN_ccMoshtary(),
                        MoshtaryGharardadModel.COLUMN_ccMoshtaryNoeGharardad(),
                        MoshtaryGharardadModel.COLUMN_NameMoshtaryNoeGharardad(),
                        MoshtaryGharardadModel.COLUMN_ShomarehGharardad(),
                        MoshtaryGharardadModel.COLUMN_TarikhGharardad(),
                        MoshtaryGharardadModel.COLUMN_FromDate(),
                        MoshtaryGharardadModel.COLUMN_EndDate(),
                        MoshtaryGharardadModel.COLUMN_TarikhEtebar(),
                        MoshtaryGharardadModel.COLUMN_ccNoeVisit(),
                        MoshtaryGharardadModel.COLUMN_NameNoeVisit(),
                        MoshtaryGharardadModel.COLUMN_CodeNoeHaml(),
                        MoshtaryGharardadModel.COLUMN_ModatVosol(),
                        MoshtaryGharardadModel.COLUMN_ModatTakhirMojaz(),
                        MoshtaryGharardadModel.COLUMN_ccDarkhastFaktorNoeForosh(),
                        MoshtaryGharardadModel.COLUMN_CodeNoeVosolAzMoshtary(),
                        MoshtaryGharardadModel.COLUMN_NameNoeVosolAzMoshtary()

                };
    }

    @SuppressLint("LongLogTag")
    public void fetchMoshtaryGharardad(final Context context, final String activityNameForLog, String ccForoshandeh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);

        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "fetchMoshtaryGhararDad: " + serverIpModel.getServerIp().trim() + " " + serverIpModel.getPort().trim() + " ");
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "");
            retrofitResponse.onFailed(Constants.HTTP_ERROR(), message);
            Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "fetchMoshtaryGhararDad: " + serverIpModel.getServerIp().trim() + " " + serverIpModel.getPort().trim() + " ");

        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Log.i("HDAPDJOPWJ", "fetchMoshtaryGhararDad: " + serverIpModel.getPort() + " " + serverIpModel.getServerIp());
            Call<GetAllMoshtaryGharardadResult> call = apiServiceGet.getMoshtaryGharardad(String.valueOf(ccForoshandeh));

            Log.i("urlOfCall", "fetchMoshtaryGhararDad: " + call.request().url());

            call.enqueue(new Callback<GetAllMoshtaryGharardadResult>() {
                @Override
                public void onResponse(Call<GetAllMoshtaryGharardadResult> call, Response<GetAllMoshtaryGharardadResult> response) {
                    Log.i("messsages", "onResponse: " + response.message());
                    try {
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, MoshtaryGharardadDAO.class.getSimpleName(), "", "fetchMoshtaryGhararDadKala", "onResponse");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                    if (response.isSuccessful()) {
                        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP2: " + response.isSuccessful());
                        GetAllMoshtaryGharardadResult result = response.body();
                        if (result != null) {
                            if (result.getSuccess()) {
                                Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP4: " + result.getSuccess());
                                        retrofitResponse.onSuccess(result.getData());

                            } else {
                                Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6:" + result.getSuccess());
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                            }
                        } else {
                            Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6: result null");
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                        }
                    } else {
                        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP6: result null");
                        String message = String.format("error body : %1$s , code : %2$s", response.message(), response.code());
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                    }
                    }
                    catch (Exception exception) {
                        Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP7: Exception in Request");
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllMoshtaryGharardadResult> call, Throwable t) {
                    Log.i("IN_FETCH_MOSHTARY_GHARARDAD", "STEP8: fail 404 or 403" + t.getMessage());
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), MoshtaryGharardadDAO.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), t.getMessage());
                }
            });
        }
    }


    @SuppressLint("LongLogTag")
    public ArrayList<Integer> getAllCcSazmanForosh() {
        ArrayList<Integer> AllCcSazmanForosh = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select ccSazmanForosh From MoshtaryGhararDad", null);
            if (cursor != null) {
                Log.d("_GetAll_CcSazman_Forosh_", "getAllCcSazmanForosh: " + cursor.getCount());
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        AllCcSazmanForosh.add(cursor.getInt(0));
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryGharardadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), "", "MoshtaryGharardadModel", "");
        }
        return AllCcSazmanForosh;
    }

public static final String GET_ALL_CCGHARARDAD_SAZMANFOROSH="getAllCCGharardadSazmanForosh";
    @SuppressLint("LongLogTag")
    public Map<Integer,List<Integer>> getAllCcGharardadBySazman() {

        List<Integer> allCcSazmanForosh = getAllCcSazmanForosh();

        Log.i(GET_ALL_CCGHARARDAD_SAZMANFOROSH, "getAllCcGharardadBySazman: "+allCcSazmanForosh.size());

       Map<Integer,List<Integer>> gharardadSazman = new HashMap<>();



        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select ccSazmanForosh,ccMoshtaryGharardad From MoshtaryGhararDad", null);

            if (cursor != null) {

                if (cursor.getCount() > 0) {

                    for (Integer integer : allCcSazmanForosh) {
                        List<Integer> allGharardadOfCcSazmanForosh = new ArrayList<>();
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            if (integer == cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_CcSazmanForosh()))) {
                                allGharardadOfCcSazmanForosh.add(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad())));
                            }
                            cursor.moveToNext();
                        }

                        gharardadSazman.put(integer, allGharardadOfCcSazmanForosh);
                        for (Integer integer1:allGharardadOfCcSazmanForosh)
                            Log.i(GET_ALL_CCGHARARDAD_SAZMANFOROSH, "getAllCcGharardadBySazman: "+String.valueOf(integer1));
                    }
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, MoshtaryGharardadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryGharardadDAO.class.getSimpleName(), "", "MoshtaryGharardadModel", "");
        }
        return gharardadSazman;
    }


    public boolean insertGroup(ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (MoshtaryGharardadModel moshtaryGharardadModel : moshtaryGharardadModels) {
                ContentValues contentValues = modelToContentvalue(moshtaryGharardadModel);
                db.insertOrThrow(MoshtaryGharardadModel.TableName(), null, contentValues);
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

    @SuppressLint("LongLogTag")
    public boolean deleteAll() {
        try {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int NumberOfAffectedItems = db.delete(MoshtaryGharardadModel.TableName(), null, null);
            Log.i("_DeleteAll_Moshtary_Zangirii_", "deleteAll: " + NumberOfAffectedItems);
            db.close();
            return true;
        } catch (Exception exception) {
            Log.i("_DeleteAll_Moshtary_Zangirii_", "Exception" + exception.getMessage());
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, MoshtaryGharardadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryGharardadDAO", "", "deleteAll", "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryGharardadModel moshtaryGharardadModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoshtaryGharardadModel.COLUMN_Radif(), moshtaryGharardadModel.getRadif());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad(), moshtaryGharardadModel.getCcMoshtaryGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccMoshtary(), moshtaryGharardadModel.getCcMoshtary());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccMoshtaryNoeGharardad(), moshtaryGharardadModel.getCcMoshtaryNoeGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_NameMoshtaryNoeGharardad(), moshtaryGharardadModel.getNameMoshtaryNoeGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ShomarehGharardad(), moshtaryGharardadModel.getShomarehGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_TarikhGharardad(), moshtaryGharardadModel.getTarikhGharardad());
        contentValues.put(MoshtaryGharardadModel.COLUMN_FromDate(), moshtaryGharardadModel.getFromDate());
        contentValues.put(MoshtaryGharardadModel.COLUMN_EndDate(), moshtaryGharardadModel.getEndDate());
        contentValues.put(MoshtaryGharardadModel.COLUMN_TarikhEtebar(), moshtaryGharardadModel.getTarikhEtebar());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccNoeVisit(), moshtaryGharardadModel.getCcNoeVisit());
        contentValues.put(MoshtaryGharardadModel.COLUMN_NameNoeVisit(), moshtaryGharardadModel.getNameNoeVisit());
        contentValues.put(MoshtaryGharardadModel.COLUMN_CodeNoeHaml(), moshtaryGharardadModel.getCodeNoeHaml());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ModatVosol(), moshtaryGharardadModel.getModatVosol());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ModatTakhirMojaz(), moshtaryGharardadModel.getModatTakhirMojaz());
        contentValues.put(MoshtaryGharardadModel.COLUMN_ccDarkhastFaktorNoeForosh(), moshtaryGharardadModel.getCcDarkhastFaktorNoeForosh());
        contentValues.put(MoshtaryGharardadModel.COLUMN_NameNoeVosolAzMoshtary(), moshtaryGharardadModel.getNameNoeVosolAzMoshtary());
        contentValues.put(MoshtaryGharardadModel.COLUMN_CcSazmanForosh(), moshtaryGharardadModel.getCcSazmanForosh());
        contentValues.put(MoshtaryGharardadModel.COLUMN_nameSazmanForosh(), moshtaryGharardadModel.getNameSazmanForosh());

        return contentValues;
    }

    private ArrayList<MoshtaryGharardadModel> cursorToModel(Cursor cursor) {
        ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MoshtaryGharardadModel moshtaryGharardadModel = new MoshtaryGharardadModel();


            moshtaryGharardadModel.setRadif(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_Radif())));
            moshtaryGharardadModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad())));
            moshtaryGharardadModel.setCcMoshtaryNoeGharardad(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccMoshtaryGharardad())));
            moshtaryGharardadModel.setNameMoshtaryNoeGharardad(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_NameMoshtaryNoeGharardad())));
            moshtaryGharardadModel.setTarikhGharardad(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_TarikhGharardad())));
            moshtaryGharardadModel.setFromDate(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_FromDate())));
            moshtaryGharardadModel.setEndDate(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_EndDate())));
            moshtaryGharardadModel.setTarikhEtebar(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_TarikhEtebar())));
            moshtaryGharardadModel.setCcNoeVisit(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_TarikhEtebar())));
            moshtaryGharardadModel.setNameNoeVisit(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_NameNoeVisit())));
            moshtaryGharardadModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_CodeNoeHaml())));
            moshtaryGharardadModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ModatVosol())));
            moshtaryGharardadModel.setModatVosol(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ModatTakhirMojaz())));
            moshtaryGharardadModel.setCcDarkhastFaktorNoeForosh(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_ccDarkhastFaktorNoeForosh())));
            moshtaryGharardadModel.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_CodeNoeVosolAzMoshtary())));
            moshtaryGharardadModel.setNameNoeVosolAzMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_NameNoeVosolAzMoshtary())));
            moshtaryGharardadModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(MoshtaryGharardadModel.COLUMN_CcSazmanForosh())));
            moshtaryGharardadModel.setNameSazmanForosh(cursor.getString(cursor.getColumnIndex(MoshtaryGharardadModel.getCOLUMN_NameSazmanForosh())));


            moshtaryGharardadModels.add(moshtaryGharardadModel);
            cursor.moveToNext();
        }
        return moshtaryGharardadModels;
    }


}
