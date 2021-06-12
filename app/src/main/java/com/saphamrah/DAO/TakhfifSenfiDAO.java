package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TakhfifSenfiModel;
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

    public ArrayList<TakhfifSenfiModel> getByMoshtary(MoshtaryModel moshtaryModel, int codeNoeVosol) {
        final int NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY = 1;
        final int NAME_NOE_FIELD_MOSHTARY_CC_GOROH = 2;
        final int GOROH_LINK_NOE_MOSHTARY = 304;
        ArrayList<TakhfifSenfiModel> TakhfifSenfis = new ArrayList<>();

        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
        int ccMarkazSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getCcMarkazSazmanForosh(), 0);
        int ccMoshtaryGharardadSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getMoshtaryGharardadccSazmanForosh(),0);
        int ccMoshtaryGharardad = selectFaktorShared.getInt(selectFaktorShared.getCcMoshtaryGharardad(),0);
        Log.d("takhfifSenfi","codeNoeVosol :" + codeNoeVosol);
        if(codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD() || codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_Resid_Naghd() || codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh())
        {
            codeNoeVosol = 1;
        }
        else if (codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK() || codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID() || codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh())
        {
            codeNoeVosol = 2;
        }
        String noeVosols = "0 ," + codeNoeVosol;
        Log.d("takhfifSenfi","noeVosols :" + noeVosols);
        Log.i(TAG, "mohasebeTakhfifSenfiFaktor: ccMoshtaryGharardad\t" + ccMoshtaryGharardad + "ccMoshtaryGharardadSazmanForosh\t" + ccMoshtaryGharardadSazmanForosh + "takhfifSenfisSize\t" + "ccMarkazSazmanForosh\t" + ccMarkazSazmanForosh);

        try {
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(context);

            int ccMoshtaryParent = moshtaryDAO.getByccMoshtary(moshtaryModel.getCcMoshtary()).getccMoshtaryParent();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String strQry = null;

            //اگر نوع مشتری زنجیره ای باشد بر اساس سازمان فروش قرارداد تخفیفات را بارگذاری میکنیم
            if (moshtaryModel.getCcNoeMoshtary() == 350) {

                strQry = "select * from takhfifsenfi\n" +
                        "where ccMarkazSazmanForosh = " + ccMoshtaryGharardadSazmanForosh + " AND ( (NameNoeFieldMoshtary=" + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary=" + moshtaryModel.getCcMoshtary() + ")\n" +
                        "                                      OR (NameNoeFieldMoshtary=" + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary=" + ccMoshtaryParent + ")\n" +
                        "                                      OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_GOROH + " AND ccNoeFieldMoshtary IN(" + moshtaryModel.getCcNoeMoshtary() + "," + GOROH_LINK_NOE_MOSHTARY + ") AND ccNoeSenf in (" + moshtaryModel.getCcNoeSenf() + " , 0)))\n" +
                        "                                      AND (Darajeh in (" + moshtaryModel.getDarajeh() + ", 0 ))\n" +
                        "                                      AND  (ccMoshtaryGharardad in (" + ccMoshtaryGharardad + " , 0 )) " +
                        "                                      AND NoeVosol IN(" + noeVosols + ")";

            }
            //اگر نوع مشتری  غیر زنجیره ای باشد بر اساس مرکز سازمان فروش  تخفیفات را بارگذاری میکنیم
            else {
                strQry = "select * from takhfifsenfi \n" +
                        "where ccMarkazSazmanForosh = " + ccMarkazSazmanForosh + " AND ( (NameNoeFieldMoshtary=" + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary=" + moshtaryModel.getCcMoshtary() + ")\n" +
                        "                                      OR (NameNoeFieldMoshtary=" + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary=" + ccMoshtaryParent + ")\n" +
                        "                                      OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_GOROH + " AND ccNoeFieldMoshtary IN(" + moshtaryModel.getCcNoeMoshtary() + "," + GOROH_LINK_NOE_MOSHTARY + ") AND ccNoeSenf in (" + moshtaryModel.getCcNoeSenf() + " , 0)))\n" +
                        "                                     AND (Darajeh in (" + moshtaryModel.getDarajeh() + ", 0 ))\n" +
                        "                                     AND (ccMoshtaryGharardad in (" + ccMoshtaryGharardad + " , 0 ))" +
                        "                                     AND NoeVosol IN(" + noeVosols + ")";
            }

            Log.d("takhfifSenfi","strQry :" + strQry);
            Cursor cursor = db.rawQuery(strQry, null);
            Log.i(TAG, " getByMoshtary cursor.getCount():" + cursor.getCount());
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    TakhfifSenfis = cursorToModel(cursor);
                }

                Log.d(TAG, "getByMoshtary cursor.getCount(): " + cursor.getCount());

                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, TakhfifSenfiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TakhfifSenfiDAO", "", "getByMoshtary", "");
        }
        return TakhfifSenfis;
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

        if (takhfifSenfiModel.getCcTakhfifSenfi() > 0) {
            contentValues.put(TakhfifSenfiModel.COLUMN_ccTakhfifSenfi(), takhfifSenfiModel.getCcTakhfifSenfi());
        }
        contentValues.put(TakhfifSenfiModel.COLUMN_CodeNoe(), takhfifSenfiModel.getCodeNoe());
        contentValues.put(TakhfifSenfiModel.COLUMN_SharhTakhfif(), takhfifSenfiModel.getSharhTakhfif());
        contentValues.put(TakhfifSenfiModel.COLUMN_NoeTedadRial(), takhfifSenfiModel.getNoeTedadRial());
        contentValues.put(TakhfifSenfiModel.COLUMN_NameNoeFieldMoshtary(), takhfifSenfiModel.getNameNoeFieldMoshtary());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccNoeFieldMoshtary(), takhfifSenfiModel.getCcNoeFieldMoshtary());
        contentValues.put(TakhfifSenfiModel.COLUMN_Darajeh(), takhfifSenfiModel.getDarajeh());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccMarkazSazmanForosh(), takhfifSenfiModel.getCcMarkazSazmanForosh());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccNoeSenf(), takhfifSenfiModel.getCcNoeSenf());
        contentValues.put(TakhfifSenfiModel.COLUMN_NameNoeSenf(), takhfifSenfiModel.getNameNoeSenf());
        contentValues.put(TakhfifSenfiModel.COLUMN_ccMoshtaryGharardad(), takhfifSenfiModel.getCcMoshtrayGharardad());
        contentValues.put(TakhfifSenfiModel.COLUMN_NoeGheymat(), takhfifSenfiModel.getNoeGheymat());
        contentValues.put(TakhfifSenfiModel.COLUMN_NoeVosol(), takhfifSenfiModel.getNoeVosol());



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
            takhfifSenfiModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_Darajeh())));
            takhfifSenfiModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccMarkazSazmanForosh())));
            takhfifSenfiModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccNoeSenf())));
            takhfifSenfiModel.setNameNoeSenf(cursor.getString(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NameNoeSenf())));
            takhfifSenfiModel.setNoeGheymat(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeGheymat())));
            takhfifSenfiModel.setCcMoshtrayGharardad(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_ccMoshtaryGharardad())));
            takhfifSenfiModel.setNoeVosol(cursor.getInt(cursor.getColumnIndex(TakhfifSenfiModel.COLUMN_NoeVosol())));



            takhfifSenfiModels.add(takhfifSenfiModel);
            cursor.moveToNext();
        }
        return takhfifSenfiModels;
    }


}
