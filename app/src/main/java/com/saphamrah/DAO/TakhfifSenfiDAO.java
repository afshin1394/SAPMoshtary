package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TakhfifSenfiModel;
import com.saphamrah.Model.TakhfifSenfiSatrModel;
import com.saphamrah.Model.TakhfifSenfiTitrSatrModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllvTakhfifSenfiByccMarkazForoshResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakhfifSenfiDAO {


    private DBHelper dbHelper;
    private Context context;
    private static final String TAG = "TakhfifSenfiDAO";

    public TakhfifSenfiDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TakhfifSenfiDAO", "", "constructor", "");
        }
    }


    private String[] allColumns() {
        return new String[]
                {


                        TakhfifSenfiModel.COLUMN_Radif(),
                        TakhfifSenfiModel.COLUMN_ccTakhfifSenfi(),
                        TakhfifSenfiModel.COLUMN_CodeNoe(),
                        TakhfifSenfiModel.COLUMN_SharhTakhfif(),
                        TakhfifSenfiModel.COLUMN_NoeTedadRial(),
                        TakhfifSenfiModel.COLUMN_NameNoeFieldMoshtary(),
                        TakhfifSenfiModel.COLUMN_ccNoeFieldMoshtary(),
                        TakhfifSenfiModel.COLUMN_Darajeh(),
                        TakhfifSenfiModel.COLUMN_ccMarkazSazmanForosh(),
                        TakhfifSenfiModel.COLUMN_ccNoeSenf(),
                        TakhfifSenfiModel.COLUMN_NameNoeSenf(),
                        TakhfifSenfiModel.COLUMN_NoeGheymat(),
                        TakhfifSenfiModel.COLUMN_ccMoshtaryGharardad(),
                        TakhfifSenfiModel.COLUMN_ccGorohTakidi(),
                        TakhfifSenfiModel.COLUMN_ccMantagheh(),
                        TakhfifSenfiModel.COLUMN_CodeNoeHaml(),
                        TakhfifSenfiModel.COLUMN_ForJayezeh(),
                        TakhfifSenfiModel.COLUMN_IsPelekani(),
                        TakhfifSenfiModel.COLUMN_NameNoeField(),
                        TakhfifSenfiModel.COLUMN_NoeVosol(),
                        TakhfifSenfiModel.COLUMN_Olaviat(),
                        TakhfifSenfiModel.COLUMN_txtNoeVosol()






                };
    }


    public void fetchTakhfifSenfi(final Context context, final String activityNameForLog, final String ccMarkazSazmanForosh, String ccTakhfifSenfi, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call = apiServiceGet.getTakhfifSenfiTitr("1", ccMarkazSazmanForosh, ccTakhfifSenfi);
            call.enqueue(new Callback<GetAllvTakhfifSenfiByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call, Response<GetAllvTakhfifSenfiByccMarkazForoshResult> response) {
                    try {
                        if (response.isSuccessful()) {
                            GetAllvTakhfifSenfiByccMarkazForoshResult result = response.body();
                            if (result != null) {
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());
                                } else {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            } else {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        } else {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call, Throwable t) {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), t.getMessage());
                }
            });
        }
    }


    /**
     * @param context
     * @param activityNameForLog
     * @param ccMarkazForosh
     * @param retrofitResponse
     * @deprecated
     */
    public void fetchTakhfifSenfi(final Context context, final String activityNameForLog, final String ccMarkazForosh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call = apiServiceGet.getAllvTakhfifSenfiByccMarkazForosh(ccMarkazForosh);
            call.enqueue(new Callback<GetAllvTakhfifSenfiByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call, Response<GetAllvTakhfifSenfiByccMarkazForoshResult> response) {
                    try {
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifSenfiDAO.class.getSimpleName(), "", "fetchTakhfifSenfi", "onResponse");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetAllvTakhfifSenfiByccMarkazForoshResult result = response.body();
                            if (result != null) {
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());
                                } else {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            } else {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        } else {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call, Throwable t) {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfi", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), t.getMessage());
                }
            });
        }
    }


    /**
     * @param context
     * @param activityNameForLog
     * @param ccMarkazForosh
     * @param retrofitResponse
     * @deprecated
     */
    public void fetchTakhfifSenfiForPakhsh(final Context context, final String activityNameForLog, final String ccMarkazForosh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiForPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call = apiServiceGet.getAllvTakhfifSenfiByccMarkazForoshForPakhsh(ccMarkazForosh);
            call.enqueue(new Callback<GetAllvTakhfifSenfiByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call, Response<GetAllvTakhfifSenfiByccMarkazForoshResult> response) {
                    try {
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TakhfifSenfiDAO.class.getSimpleName(), "", "fetchTakhfifSenfiForPakhsh", "onResponse");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetAllvTakhfifSenfiByccMarkazForoshResult result = response.body();
                            if (result != null) {
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());
                                } else {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiForPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            } else {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiForPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        } else {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiForPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiForPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvTakhfifSenfiByccMarkazForoshResult> call, Throwable t) {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TakhfifSenfiDAO.class.getSimpleName(), activityNameForLog, "fetchTakhfifSenfiForPakhsh", "onFailure");
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


    public String insertGroup(ArrayList<TakhfifSenfiModel> takhfifSenfiModels) {
        String ccTakhfifs = "-1";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (TakhfifSenfiModel takhfifSenfiModel : takhfifSenfiModels) {
                if (!ccTakhfifs.contains(String.valueOf(takhfifSenfiModel.getCcTakhfifSenfi())))
                    ccTakhfifs += "," + takhfifSenfiModel.getCcTakhfifSenfi();

                ContentValues contentValues = modelToContentvalue(takhfifSenfiModel);
                db.insertOrThrow(TakhfifSenfiModel.TableName(), null, contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return ccTakhfifs;
        } catch (Exception exception) {
            exception.printStackTrace();
            if (db.inTransaction()) {
                db.endTransaction();
            }
            if (db.isOpen()) {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert, TakhfifSenfiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiDAO", "", "insertGroup", "");
            return ccTakhfifs;
        }
    }
    public boolean insertGroupConditional(ArrayList<TakhfifSenfiModel> takhfifSenfiModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (TakhfifSenfiModel takhfifSenfiModel : takhfifSenfiModels) {

                ContentValues contentValues = modelToContentvalue(takhfifSenfiModel);
                db.insertOrThrow(TakhfifSenfiModel.TableName(), null, contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert, TakhfifSenfiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiDAO", "", "insertGroup", "");
            return false;
        }
    }

    public ArrayList<TakhfifSenfiModel> getAll() {
        ArrayList<TakhfifSenfiModel> takhfifSenfiModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TakhfifSenfiModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    takhfifSenfiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, TakhfifSenfiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiDAO", "", "getAll", "");
        }
        return takhfifSenfiModels;
    }

    public ArrayList<TakhfifSenfiTitrSatrModel> getByMoshtaryWithSatr(MoshtaryModel moshtary, int codeNoeHaml, boolean ShebhOmdeh, int CodeNoeVosol)
    {
        final int NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY = 1;
        final int NAME_NOE_FIELD_MOSHTARY_CC_GOROH = 2;
        final int GOROH_LINK_NOE_MOSHTARY = 304 ;
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
        int ccMarkazSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getCcMarkazSazmanForosh(),0);
        int ccMoshtaryGharardad = selectFaktorShared.getInt(selectFaktorShared.getCcMoshtaryGharardad(),0);
        ArrayList<TakhfifSenfiTitrSatrModel> takhfifSenfiTitrSatrModels = new ArrayList<>();
        try
        {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String ccNoeFieldMoshtarys =  moshtary.getCcNoeMoshtary() + "," + GOROH_LINK_NOE_MOSHTARY;
            int ccNoeSenfMoshtary = moshtary.getCcNoeSenf();
            int ccMoshtaryParent = moshtary.getccMoshtaryParent();
            String noeVosols =  "0 ," + CodeNoeVosol;
            String query = "select t.* , ts.NameNoeField from TakhfifSenfi t inner join (select distinct(NameNoeField) , ccTakhfifSenfi from TakhfifSenfiSatr ) ts on t.ccTakhfifSenfi = ts.ccTakhfifSenfi where ";
            if(!ShebhOmdeh)
            {
                query += " ((NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= " + moshtary.getCcMoshtary() + ")"
                        + " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= "+ccMoshtaryParent+")"
                        + " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_GOROH + " AND ccNoeFieldMoshtary IN(" + ccNoeFieldMoshtarys + ") AND ccNoeSenf in (" + ccNoeSenfMoshtary + " , 0)))"
                        + " AND CodeNoeHaml= " + codeNoeHaml + " AND NoeVosol IN(" + noeVosols + ") AND (Darajeh in ( " + moshtary.getDarajeh() + " , 0 ) AND ccMarkazSazmanForosh = "+ ccMarkazSazmanForosh +")"
                        + " AND (ccMoshtaryGharardad in (" + ccMoshtaryGharardad + " , 0 ))" ;
            }
            else
            {
                query += " ((NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= " + moshtary.getCcMoshtary() + ")"
                        + " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= "+ccMoshtaryParent+")"
                        + " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_GOROH + " AND ccNoeFieldMoshtary IN(" + ccNoeFieldMoshtarys + ") AND ccNoeSenf in (" + ccNoeSenfMoshtary + " , 0)))"
                        + " AND CodeNoeHaml= " + codeNoeHaml + " AND ccTakhfifSenfi<>1620  AND NoeVosol IN(" + noeVosols + ") AND (Darajeh in ( " + moshtary.getDarajeh() + " , 0) AND ccMarkazSazmanForosh = "+ ccMarkazSazmanForosh +")";
            }
            Log.d("TakhfifSatr" , "query : " + query);
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    takhfifSenfiTitrSatrModels = cursorToModelTitrSatr(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifSenfiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiDAO" , "" , "getByMoshtaryWithSatr" , "");
        }
        return takhfifSenfiTitrSatrModels;
    }

    public int getMaxOlaviat()
    {
        int maxOlaviat = -1;
        try
        {
            String query = "select MAX(" + TakhfifSenfiModel.COLUMN_Olaviat() + ") from " + TakhfifSenfiModel.TableName();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    maxOlaviat = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TakhfifSenfiModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiDAO" , "" , "getDistinctOlaviat" , "");
        }
        return maxOlaviat;
    }

    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TakhfifSenfiModel.TableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, TakhfifSenfiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiDAO", "", "deleteAll", "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TakhfifSenfiModel takhfifSenfiModel) {
        ContentValues contentValues = new ContentValues();
        if (takhfifSenfiModel.getCcTakhfifSenfi() > 0)
        {
            contentValues.put(TakhfifSenfiModel.COLUMN_ccTakhfifSenfi() , takhfifSenfiModel.getCcTakhfifSenfi());
        }
        if (takhfifSenfiModel.getRadif() > 0)
        {
            contentValues.put(TakhfifSenfiModel.COLUMN_Radif() , takhfifSenfiModel.getRadif());
        }
        contentValues.put(TakhfifSenfiModel.COLUMN_CodeNoe() , takhfifSenfiModel.getCodeNoe());
        contentValues.put(TakhfifSenfiModel.COLUMN_SharhTakhfif() , takhfifSenfiModel.getSharhTakhfif());
        contentValues.put(TakhfifSenfiModel.COLUMN_NoeTedadRial() , takhfifSenfiModel.getNoeTedadRial());
        contentValues.put(TakhfifSenfiModel.COLUMN_NameNoeFieldMoshtary() , takhfifSenfiModel.getNameNoeFieldMoshtary());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccNoeFieldMoshtary() , takhfifSenfiModel.getCcNoeFieldMoshtary());
        contentValues.put(TakhfifSenfiModel.COLUMN_CodeNoeHaml() , takhfifSenfiModel.getCodeNoeHaml());
        contentValues.put(TakhfifSenfiModel.COLUMN_Darajeh() , takhfifSenfiModel.getDarajeh());
        contentValues.put(TakhfifSenfiModel.COLUMN_ForJayezeh() , takhfifSenfiModel.getForJayezeh());
        contentValues.put(TakhfifSenfiModel.COLUMN_NoeVosol() , takhfifSenfiModel.getNoeVosol());
        contentValues.put(TakhfifSenfiModel.COLUMN_txtNoeVosol() , takhfifSenfiModel.getTxtNoeVosol());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccGorohTakidi() , takhfifSenfiModel.getCcGorohTakidi());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccMarkazSazmanForosh() , takhfifSenfiModel.getCcMarkazSazmanForosh());
        contentValues.put(TakhfifSenfiModel.COLUMN_Olaviat() , takhfifSenfiModel.getOlaviat());
        contentValues.put(TakhfifSenfiModel.COLUMN_IsPelekani() , takhfifSenfiModel.getIsPelekani());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccMantagheh() , takhfifSenfiModel.getCcMantagheh());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccNoeSenf() , takhfifSenfiModel.getCcNoeSenf());
        contentValues.put(TakhfifSenfiModel.COLUMN_NameNoeSenf() , takhfifSenfiModel.getNameNoeSenf());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccMoshtaryGharardad(), takhfifSenfiModel.getCcMoshtrayGharardad());
        contentValues.put(TakhfifSenfiModel.COLUMN_NoeGheymat(), takhfifSenfiModel.getNoeGheymat());



        return contentValues;
    }


    private ArrayList<TakhfifSenfiModel> cursorToModel(Cursor cursor) {
        ArrayList<TakhfifSenfiModel> takhfifSenfiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TakhfifSenfiModel takhfifSenfiModel = new TakhfifSenfiModel();

            takhfifSenfiModel.setRadif(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_Radif())));
            takhfifSenfiModel.setCcTakhfifSenfi(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccTakhfifSenfi())));
            takhfifSenfiModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_CodeNoe())));
            takhfifSenfiModel.setSharhTakhfif(cursor.getString(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_SharhTakhfif())));
            takhfifSenfiModel.setNoeTedadRial(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeTedadRial())));
            takhfifSenfiModel.setNameNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NameNoeFieldMoshtary())));
            takhfifSenfiModel.setCcNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccNoeFieldMoshtary())));
            takhfifSenfiModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_CodeNoeHaml())));
            takhfifSenfiModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_Darajeh())));
            takhfifSenfiModel.setForJayezeh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ForJayezeh())));
            takhfifSenfiModel.setNoeVosol(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeVosol())));
            takhfifSenfiModel.setTxtNoeVosol(cursor.getString(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_txtNoeVosol())));
            takhfifSenfiModel.setCcGorohTakidi(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccGorohTakidi())));
            takhfifSenfiModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccMarkazSazmanForosh())));
            takhfifSenfiModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_Olaviat())));
            takhfifSenfiModel.setIsPelekani(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_IsPelekani())));
            takhfifSenfiModel.setCcMantagheh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccMantagheh())));
            takhfifSenfiModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccNoeSenf())));
            takhfifSenfiModel.setCcTakhfifSenfi(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccTakhfifSenfi())));
            takhfifSenfiModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccMarkazSazmanForosh())));
            takhfifSenfiModel.setNameNoeSenf(cursor.getString(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NameNoeSenf())));
            takhfifSenfiModel.setNoeGheymat(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeGheymat())));
            takhfifSenfiModel.setCcMoshtrayGharardad(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccMoshtaryGharardad())));




            takhfifSenfiModels.add(takhfifSenfiModel);
            cursor.moveToNext();
        }
        return takhfifSenfiModels;
    }

    private ArrayList<TakhfifSenfiTitrSatrModel> cursorToModelTitrSatr(Cursor cursor)
    {
        ArrayList<TakhfifSenfiTitrSatrModel> takhfifSenfiTitrSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel = new TakhfifSenfiTitrSatrModel();

            //////////// TITR ////////////
            takhfifSenfiTitrSatrModel.setRadif(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_Radif())));
            takhfifSenfiTitrSatrModel.setCcTakhfifSenfi(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccTakhfifSenfi())));
            takhfifSenfiTitrSatrModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_CodeNoe())));
            takhfifSenfiTitrSatrModel.setSharhTakhfif(cursor.getString(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_SharhTakhfif())));
            takhfifSenfiTitrSatrModel.setNoeTedadRial(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeTedadRial())));
            takhfifSenfiTitrSatrModel.setNameNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NameNoeFieldMoshtary())));
            takhfifSenfiTitrSatrModel.setCcNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccNoeFieldMoshtary())));
            takhfifSenfiTitrSatrModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_CodeNoeHaml())));
            takhfifSenfiTitrSatrModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_Darajeh())));
            takhfifSenfiTitrSatrModel.setForJayezeh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ForJayezeh())));
            takhfifSenfiTitrSatrModel.setNoeVosol(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeVosol())));
            takhfifSenfiTitrSatrModel.setTxtNoeVosol(cursor.getString(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_txtNoeVosol())));
            takhfifSenfiTitrSatrModel.setCcGorohTakidi(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccGorohTakidi())));
            takhfifSenfiTitrSatrModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_Olaviat())));
            takhfifSenfiTitrSatrModel.setIsPelekani(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_IsPelekani())));
            takhfifSenfiTitrSatrModel.setCcMantagheh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccMantagheh())));
            takhfifSenfiTitrSatrModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccNoeSenf())));
            takhfifSenfiTitrSatrModel.setNameNoeSenf(cursor.getString(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NameNoeSenf())));
            takhfifSenfiTitrSatrModel.setNoeGheymat(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeGheymat())));

            //////////// SATR ////////////
            takhfifSenfiTitrSatrModel.setNameNoeField(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiSatrModel.COLUMN_NameNoeField())));

            takhfifSenfiTitrSatrModels.add(takhfifSenfiTitrSatrModel);
            cursor.moveToNext();
        }
        return takhfifSenfiTitrSatrModels;
    }
}
