package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.saphamrah.Model.AllMoshtaryForoshandehModel;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.Model.HadafForosh.RptHadafForoshModel;


import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllrptHadafeForoshResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RptHadafForoshDAO {
    private DBHelper dbHelper;
    private Context context;

    public RptHadafForoshDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptHadafeForoshDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        RptHadafForoshModel.getCOLUMN_ccBrand(),
                        RptHadafForoshModel.getCOLUMN_NameBrand(),
                        RptHadafForoshModel.getCOLUMN_ccGorohKala(),
                        RptHadafForoshModel.getCOLUMN_NameGoroh(),
                        RptHadafForoshModel.getCOLUMN_ccKalaCode(),
                        RptHadafForoshModel.getCOLUMN_CodeKalaOld(),
                        RptHadafForoshModel.getCOLUMN_NameKala(),
                        RptHadafForoshModel.getCOLUMN_ccForoshandeh(),
                        RptHadafForoshModel.getCOLUMN_CodeForoshandehOld(),
                        RptHadafForoshModel.getCOLUMN_SharhForoshandeh(),
                        RptHadafForoshModel.getCOLUMN_TedadForoshRooz(),
                        RptHadafForoshModel.getCOLUMN_TedadHadafRooz(),
                        RptHadafForoshModel.getCOLUMN_TedadForoshMah(),
                        RptHadafForoshModel.getCOLUMN_TedadHadafMah(),
                        RptHadafForoshModel.getCOLUMN_NoeNamayesh()

                };
    }

    public void fetchAllrpHadafeForosh(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptHadafForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptAmarForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllrptHadafeForoshResult> call = apiServiceGet.getAllRptHadafForosh(ccForoshandeh);
            call.enqueue(new Callback<GetAllrptHadafeForoshResult>() {
                @Override
                public void onResponse(Call<GetAllrptHadafeForoshResult> call, Response<GetAllrptHadafeForoshResult> response) {
                    try {
                        Log.i("fetchAllReport", "onResponse");
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptHadafForoshDAO.class.getSimpleName(), "", "fetchAllrptHadfeForosh", "onResponse");
                        }


                    } catch (Exception e) {
                        Log.i("fetchAllReport", "onCatchException " + e.getMessage());
                        e.printStackTrace();

                    }
                    try {
                        if (response.isSuccessful()) {
                            GetAllrptHadafeForoshResult result = response.body();
                            if (result != null) {
                                Log.i("fetchAllReport", "result not null ");
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());

                                    Log.i("fetchAllReport", "result not null and successful ");
                                } else {
                                    Log.i("fetchAllReport", "result not null but not successful " + result.getMessage());

                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptHadafForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptHadafeForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            } else {
                                Log.i("fetchAllReport", "result not null " + result.getMessage());

                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptHadafForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptHadafeForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        } else {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptHadafForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptHadafeForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptHadafForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptHadafeForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptHadafeForoshResult> call, Throwable t) {
                    Log.i("fetchAllSaleReport", "onFailure: ");
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptHadafForoshDAO.class.getSimpleName(), activityNameForLog, "fetchAllrptHadafeForosh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<RptHadafForoshModel> rptHadafForoshModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (RptHadafForoshModel rptHadafForoshModel : rptHadafForoshModels) {
                ContentValues contentValues = modelToContentvalue(rptHadafForoshModel);

                db.insertOrThrow(RptHadafForoshModel.getTableName(), null, contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptHadafeForoshDAO", "", "insertGroup", "");
            return false;
        }
    }


    public ArrayList<BaseHadafForoshModel> getHadafForoshAllBrands() {
        ArrayList<BaseHadafForoshModel> baseHadafForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
            String strSql = " Select 0 AS _id, ccBrand,NameBrand,  "
                    + "              SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                    + "        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah,"
                    + "        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz,"
                    + "        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 0 AS Jam "
                    + " FROM   Rpt_HadafForoshTedady "
                    + " GROUP BY NameBrand "
                    + " UNION ALL  "
                    + " SELECT 0, 0, 'جمع', SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                    + "        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah , "
                    + "        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz, "
                    + "        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 1 AS Jam "
                    + " FROM   Rpt_HadafForoshTedady  ";

            Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    baseHadafForoshModels = sumCursorToSumModel(cursor);
                }
                cursor.close();
            }
            sqLiteDatabase.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptSumHadafForoshDAO", "", "getAll", "");
        }

        return baseHadafForoshModels;
    }
    public BaseHadafForoshModel getJustSumHadafForoshBrands(){
        BaseHadafForoshModel rptBrandHadafForoshModel =new BaseHadafForoshModel();
        try {
            Log.i("RptHadafForoshDaosd", "getJustSumSaleGoalBrands: 1");
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
            String strSql =
                    " SELECT 0, 0, 'جمع', SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                            +"        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah , "
                            +"        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz, "
                            +"        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 1 AS Jam "
                            +" FROM   Rpt_HadafForoshTedady  ";
            Log.i("RptHadafForoshDaosd", "getJustSumSaleGoalBrands: 2");


            Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
            if (cursor != null) {
                Log.i("RptHadafForoshDaosd", "getJustSumSaleGoalBrands: 3"+cursor.getCount());
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    Log.i("RptHadafForoshDaosd", "getJustSumSaleGoalBrands: 3"+cursor.getCount()+" "+cursor.getInt(6)+" "+cursor.getInt(7) );


                    rptBrandHadafForoshModel.setNameBrand(cursor.getString(2));
                    rptBrandHadafForoshModel.setTedadForoshMah(cursor.getInt(3));
                    rptBrandHadafForoshModel.setTedadHadafMah(cursor.getInt(4));
                    rptBrandHadafForoshModel.setTedadForoshRooz(cursor.getInt(6));
                    rptBrandHadafForoshModel.setTedadHadafRooz(cursor.getInt(7));


                }
                cursor.close();
            }
            sqLiteDatabase.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptSumHadafForoshDAO", "", "getAll", "");
        }
        return rptBrandHadafForoshModel;
    }


    public ArrayList<RptHadafForoshModel> getAll() {
        ArrayList<RptHadafForoshModel> rptHadafForoshModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptHadafForoshModel.getTableName(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptHadafForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptHadafForoshDAO", "", "getAll", "");
        }
        return rptHadafForoshModels;
    }

    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptHadafForoshModel.getTableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptHadafForoshDAO", "", "deleteAll", "");
            return false;
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


    private static ContentValues modelToContentvalue(RptHadafForoshModel rptHadafForoshModel) {
        ContentValues contentValues = new ContentValues();


        contentValues.put(RptHadafForoshModel.getCOLUMN_ccBrand(), rptHadafForoshModel.getccBrand());
        contentValues.put(RptHadafForoshModel.getCOLUMN_NameBrand(), rptHadafForoshModel.getNameBrand());
        contentValues.put(RptHadafForoshModel.getCOLUMN_ccGorohKala(), rptHadafForoshModel.getccGorohKala());
        contentValues.put(RptHadafForoshModel.getCOLUMN_NameGoroh(), rptHadafForoshModel.getNameGoroh());
        contentValues.put(RptHadafForoshModel.getCOLUMN_ccKalaCode(), rptHadafForoshModel.getccKalaCode());
        contentValues.put(RptHadafForoshModel.getCOLUMN_CodeKalaOld(), rptHadafForoshModel.getCodeKalaOld());
        contentValues.put(RptHadafForoshModel.getCOLUMN_NameKala(), rptHadafForoshModel.getNameKala());
        contentValues.put(RptHadafForoshModel.getCOLUMN_ccForoshandeh(), rptHadafForoshModel.getccForoshandeh());
        contentValues.put(RptHadafForoshModel.getCOLUMN_CodeForoshandehOld(), rptHadafForoshModel.getCodeForoshandehOld());
        contentValues.put(RptHadafForoshModel.getCOLUMN_SharhForoshandeh(), rptHadafForoshModel.getSharhForoshandeh());
        contentValues.put(RptHadafForoshModel.getCOLUMN_TedadForoshRooz(), rptHadafForoshModel.getTedadForoshRooz());
        contentValues.put(RptHadafForoshModel.getCOLUMN_TedadForoshMah(), rptHadafForoshModel.getTedadForoshMah());
        contentValues.put(RptHadafForoshModel.getCOLUMN_TedadHadafRooz(), rptHadafForoshModel.getTedadHadafRooz());
        contentValues.put(RptHadafForoshModel.getCOLUMN_TedadHadafMah(), rptHadafForoshModel.getTedadHadafMah());
        contentValues.put(RptHadafForoshModel.getCOLUMN_NoeNamayesh(), rptHadafForoshModel.getNoeNamayesh());


        return contentValues;
    }

    private ArrayList<RptHadafForoshModel> cursorToModel(Cursor cursor) {
        ArrayList<RptHadafForoshModel> rptHadafForoshModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RptHadafForoshModel rptHadafForoshModel = new RptHadafForoshModel();
            Log.i("cursorToModel", "cursorToModel: ");
            rptHadafForoshModel.setccBrand(cursor.getInt(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_ccBrand())));
            rptHadafForoshModel.setNameBrand(cursor.getString(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_NameBrand())));
            rptHadafForoshModel.setccGorohKala(cursor.getDouble(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_ccGorohKala())));
            rptHadafForoshModel.setNameGoroh(cursor.getString(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_NameGoroh())));
            rptHadafForoshModel.setccKalaCode(cursor.getInt(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_ccKalaCode())));
            rptHadafForoshModel.setCodeKalaOld(cursor.getString(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_CodeKalaOld())));
            rptHadafForoshModel.setNameKala(cursor.getString(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_NameKala())));
            rptHadafForoshModel.setccForoshandeh(cursor.getInt(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_ccForoshandeh())));
            rptHadafForoshModel.setCodeForoshandehOld(cursor.getString(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_CodeForoshandehOld())));
            rptHadafForoshModel.setSharhForoshandeh(cursor.getString(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_SharhForoshandeh())));
            rptHadafForoshModel.setTedadForoshRooz(cursor.getInt(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_TedadForoshRooz())));
            rptHadafForoshModel.setTedadForoshMah(cursor.getInt(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_TedadForoshMah())));
            rptHadafForoshModel.setTedadHadafRooz(cursor.getInt(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_TedadHadafRooz())));
            rptHadafForoshModel.setTedadHadafMah(cursor.getInt(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_TedadHadafMah())));
            rptHadafForoshModel.setNoeNamayesh(cursor.getInt(cursor.getColumnIndex(RptHadafForoshModel.getCOLUMN_NoeNamayesh())));

            rptHadafForoshModels.add(rptHadafForoshModel);
            cursor.moveToNext();
        }
        return rptHadafForoshModels;
    }


    private ArrayList sumCursorToSumModel(Cursor cursor) {

        ArrayList<BaseHadafForoshModel> baseHadafForoshModels = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BaseHadafForoshModel baseHadafForoshModel=new BaseHadafForoshModel();
            baseHadafForoshModel.setCcBrand(cursor.getInt(1));
            baseHadafForoshModel.setNameBrand(cursor.getString(2));
            baseHadafForoshModel.setTedadForoshMah(cursor.getInt(3));
            baseHadafForoshModel.setTedadHadafMah(cursor.getInt(4));
            baseHadafForoshModel.setTedadForoshRooz(cursor.getInt(6));
            baseHadafForoshModel.setTedadHadafRooz(cursor.getInt(7));
            baseHadafForoshModel.setJam(cursor.getInt(9));



            baseHadafForoshModels.add(baseHadafForoshModel);
            cursor.moveToNext();
        }

        return baseHadafForoshModels;
    }



    public String getBrandNameByccBrand(int ccBrand) {
        String BrandName = null;
        String queryStr = "Select DISTINCT NameBrand From Rpt_HadafForoshTedady where ccBrand=" + ccBrand;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr, null);
        Log.i("getBrandNameBycc", "getBrand");
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    BrandName = cursor.getString(0);


                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        db.close();
        if (BrandName != null) {
            return BrandName;
        } else {
            Log.i("BrandName", "BrandName is empty");
            return null;
        }

    }
    public List<Integer> getAllGorohKala(){
        ArrayList<Integer> allGorooh = new ArrayList<>();
        String strsql = "Select ccGorohKala From Rpt_HadafForoshTedady";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(strsql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    allGorooh.add(cursor.getInt(0));


                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        db.close();
        List<Integer> allBrandsWithoutDuplicates = new ArrayList<>(
                new HashSet<>(allGorooh));

        return allBrandsWithoutDuplicates;

    }


public ArrayList<BaseHadafForoshModel> getKalaByGorohAndBrand(int ccBrand,int ccGorohKala){
    ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels = new ArrayList<>();

    String strSql= " Select 0 AS _id, ccBrand, ccGorohKala, CodeKalaOld, NameKala,  "
            +"              SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
            +"        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah,"
            +"        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz,"
            +"        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 0 AS Jam "
            +" FROM   Rpt_HadafForoshTedady  "
            +" WHERE ccBrand = " + ccBrand + " AND ccGorohKala = " + ccGorohKala
            +" GROUP BY ccBrand, ccGorohKala, CodeKalaOld, NameKala"
            +" UNION ALL  "
            +" SELECT 0, 0, 0, '', 'جمع', SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
            +"        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah, "
            +"        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz, "
            +"        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 1 AS Jam "
            +" FROM   Rpt_HadafForoshTedady  "
            +" WHERE ccBrand = " + ccBrand + " AND ccGorohKala = " + ccGorohKala;
    try {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(strSql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    BaseHadafForoshModel baseHadafForoshModel=new BaseHadafForoshModel();
                    baseHadafForoshModel.setCcBrand(cursor.getInt(1));
                    baseHadafForoshModel.setCcGorohKala(cursor.getInt(2));
                    baseHadafForoshModel.setCcKala(cursor.getInt(3));
                    baseHadafForoshModel.setNameKala(cursor.getString(4));
                    baseHadafForoshModel.setTedadForoshMah(cursor.getInt(5));
                    baseHadafForoshModel.setTedadHadafMah(cursor.getInt(6));
                    baseHadafForoshModel.setTedadForoshRooz(cursor.getInt(8));
                    baseHadafForoshModel.setTedadHadafRooz(cursor.getInt(9));
                    baseHadafForoshModel.setJam(cursor.getInt(10));


                    rptKalaHadafForoshModels.add(baseHadafForoshModel);


                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        db.close();

    } catch (Exception exception) {
        Log.i("catcchgetKala", "getKalaByBrandAndGorohKala: ");
        exception.printStackTrace();
        PubFunc.Logger logger = new PubFunc().new Logger();
        String message = context.getResources().getString(R.string.errorSelectAll, AllMoshtaryForoshandehModel.TableName()) + "\n" + exception.toString();
        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GetKalaByBrandAndGoroh", "", "GetKalaByBrandAndGoroh", "");

    }
return rptKalaHadafForoshModels;

}

    //get GorohKalaByBrand Level2 rptHadafForoshTedady
    public ArrayList<BaseHadafForoshModel> getGorohKalaByBrand(int ccBrand) {
        ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels = new ArrayList<>();
        String strSql = " Select 0 AS _id, ccBrand, ccGorohKala, NameGoroh,  "
                + "              SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                + "        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah,"
                + "        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz,"
                + "        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 0 AS Jam "
                + " FROM   Rpt_HadafForoshTedady  "
                + " WHERE ccBrand = " + ccBrand
                + " GROUP BY ccBrand, ccGorohKala, NameGoroh "
                + " UNION ALL  "
                + " SELECT 0, 0, 0, 'جمع', SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                + "        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah , "
                + "        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz, "
                + "        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 1 AS Jam "
                + " FROM   Rpt_HadafForoshTedady  "
                + " WHERE ccBrand = " + ccBrand;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(strSql, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        BaseHadafForoshModel baseHadafForoshModel=new BaseHadafForoshModel();
                        baseHadafForoshModel.setCcBrand(cursor.getInt(1));
                        baseHadafForoshModel.setCcGorohKala(cursor.getInt(2));
                        baseHadafForoshModel.setNameGorohKala(cursor.getString(3));
                        baseHadafForoshModel.setTedadForoshMah(cursor.getInt(4));
                        baseHadafForoshModel.setTedadHadafMah(cursor.getInt(5));
                        baseHadafForoshModel.setTedadForoshRooz(cursor.getInt(7));
                        baseHadafForoshModel.setTedadHadafRooz(cursor.getInt(8));
                        baseHadafForoshModel.setJam(cursor.getInt(10));

                        rptGorohKalaHadafForoshModels.add(baseHadafForoshModel);


                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();

        } catch (Exception exception) {
            Log.i("catcchgetGorohKala", "getGorohKalaByBrand: ");
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "GetGoorohKalaByBrand", "", "getGoroohKalaByBrand", "");

        }
        Log.i("sizeOfGorohKalaModel", "getGorohKalaByBrand: "+rptGorohKalaHadafForoshModels.size());

        return rptGorohKalaHadafForoshModels;

    }


    //find different GorohKalas from Rpt_HadafForoshTedady

    public ArrayList<Integer> getAllGorohKalas(){
        ArrayList<Integer> allGorohKalas = new ArrayList<>();

        String strSql=" select distinct ccGorohKala From  Rpt_HadafForoshTedady";

        try{
            SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                       allGorohKalas.add(cursor.getInt(0));

                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            sqLiteDatabase.close();

        }catch(Exception exception){
            Log.i("catcchgetGorohKala", "getGorohKalaByBrand: ");
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "findAllGorohKalas", "", "findAllGorohKalas", "");
        }


       return allGorohKalas;

    }

     public ArrayList<Integer>  getGorohKalasByBrand(int ccBrand){
         ArrayList<Integer> allGorohKalasForforccBrand = new ArrayList<>();
         String strSql=" select distinct ccGorohKala From Rpt_HadafForoshTedady where ccBrand="+ccBrand;
         try{
             SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
             Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
             if (cursor != null) {
                 if (cursor.getCount() > 0) {
                     cursor.moveToFirst();
                     while (!cursor.isAfterLast()) {

                         allGorohKalasForforccBrand .add(cursor.getInt(0));

                         cursor.moveToNext();
                     }
                 }
                 cursor.close();
             }
             sqLiteDatabase.close();

         }catch(Exception exception){
             Log.i("catchGorohKalaByBrand", "getGorohKalaByBrand: ");
             exception.printStackTrace();
             PubFunc.Logger logger = new PubFunc().new Logger();
             String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
             logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "getAllGorohKalasByBrand", "", "getAllGorohKalasByBrand", "");
         }
         return allGorohKalasForforccBrand;
     }


    public ArrayList<Integer> getAllBrands(){
        ArrayList<Integer> allBrands=new ArrayList<>();
        String strSql=" select distinct ccBrand From  Rpt_HadafForoshTedady";

        try {
            SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        allBrands.add(cursor.getInt(0));

                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }


        }catch (Exception exception){
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "findAllBrands", "", "findAllBrands", "");
        }
        return allBrands;
    }


    public ArrayList<BaseHadafForoshModel> getAllBrandGorohKala(){
        PubFunc.DAOUtil daoUtils=new PubFunc().new DAOUtil();
        String allBrandsCodes= daoUtils.convertIntegerArrayToString(getAllBrands());
        ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels =new ArrayList<>();

        String strSql = " Select 0 AS _id, ccBrand, ccGorohKala, NameGoroh,  "
                + "              SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                + "        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah,"
                + "        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz,"
                + "        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 0 AS Jam "
                + " FROM   Rpt_HadafForoshTedady  "
                + " WHERE ccBrand IN ("+allBrandsCodes+")"
                + " GROUP BY ccBrand, ccGorohKala, NameGoroh "
                + " UNION ALL  "
                + " SELECT 0, 0, 0, 'جمع', SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                + "        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah , "
                + "        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz, "
                + "        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 1 AS Jam "
                + " FROM   Rpt_HadafForoshTedady  "
                + " WHERE ccBrand IN ("+allBrandsCodes+")";

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(strSql, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        BaseHadafForoshModel baseHadafForoshModel=new BaseHadafForoshModel();
                        baseHadafForoshModel.setCcBrand(cursor.getInt(1));
                        baseHadafForoshModel.setCcGorohKala(cursor.getInt(2));
                        baseHadafForoshModel.setNameGorohKala(cursor.getString(3));
                        baseHadafForoshModel.setTedadForoshMah(cursor.getInt(4));
                        baseHadafForoshModel.setTedadHadafMah(cursor.getInt(5));
                        baseHadafForoshModel.setTedadForoshRooz(cursor.getInt(7));
                        baseHadafForoshModel.setTedadHadafRooz(cursor.getInt(8));
                        baseHadafForoshModel.setJam(cursor.getInt(10));


                        rptGorohKalaHadafForoshModels.add(baseHadafForoshModel);


                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();

        } catch (Exception exception) {
            Log.i("catcchgetGorohKala", "getGorohKalaAllBrands: ");
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "getGorohKalaAllBrands", "", "getGorohKalaAllBrands", "");

        }
        return rptGorohKalaHadafForoshModels;


    }
    public ArrayList<BaseHadafForoshModel> getAllGorohKalaKalasByBrand(){
        ArrayList<BaseHadafForoshModel  > rptKalaHadafForoshModels=new ArrayList<>();
        PubFunc.DAOUtil daoUtils=new PubFunc().new DAOUtil();
        String allBrandsCodes= daoUtils.convertIntegerArrayToString(getAllBrands());
        String allGoroohKalasCodes= daoUtils.convertIntegerArrayToString(getAllGorohKalas());
        String strSql=" Select 0 AS _id, ccBrand, ccGorohKala, CodeKalaOld, NameKala,  "
                +"              SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                +"        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah,"
                +"        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz,"
                +"        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 0 AS Jam "
                +" FROM   Rpt_HadafForoshTedady  "
                +" WHERE ccBrand IN (" + allBrandsCodes + ") AND ccGorohKala IN ( " + allGoroohKalasCodes +")"
                +" GROUP BY ccBrand, ccGorohKala, CodeKalaOld, NameKala"

                + " UNION ALL  "

                + " SELECT 0, 0, 0, 0 , 'جمع', SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                + "        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah , "
                + "        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz, "
                + "        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 1 AS Jam "
                + " FROM   Rpt_HadafForoshTedady  "
                + " WHERE ccBrand IN ("+allBrandsCodes+") AND ccGorohKala IN ( " + allGoroohKalasCodes +")";
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(strSql, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        BaseHadafForoshModel baseHadafForoshModel=new BaseHadafForoshModel();
                        baseHadafForoshModel.setCcBrand(cursor.getInt(1));
                        baseHadafForoshModel.setCcGorohKala(cursor.getInt(2));
                        baseHadafForoshModel.setCcKala(cursor.getInt(3));
                        baseHadafForoshModel.setNameKala(cursor.getString(4));
                        baseHadafForoshModel.setTedadForoshMah(cursor.getInt(5));
                        baseHadafForoshModel.setTedadHadafMah(cursor.getInt(6));
                        baseHadafForoshModel.setTedadForoshRooz(cursor.getInt(8));
                        baseHadafForoshModel.setTedadHadafRooz(cursor.getInt(9));
                        baseHadafForoshModel.setJam(cursor.getInt(11));


                        rptKalaHadafForoshModels.add(baseHadafForoshModel);



                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();

        } catch (Exception exception) {
            Log.i("catcchgetAllKala", "getGorohKalaAllKalas: ");
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "getAllKalas", "", "getAllKalas", "");

        }
        return rptKalaHadafForoshModels;
    }
    public ArrayList<BaseHadafForoshModel> getAllKalasByBrand(int ccBrand){
        ArrayList<BaseHadafForoshModel  > rptKalaHadafForoshModels=new ArrayList<>();
        PubFunc.DAOUtil daoUtils=new PubFunc().new DAOUtil();
        String allGoroohKalasCodes= daoUtils.convertIntegerArrayToString(getGorohKalasByBrand(ccBrand));
        String strSql=" Select 0 AS _id, ccBrand, ccGorohKala, CodeKalaOld, NameKala,  "
                +"              SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                +"        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah,"
                +"        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz,"
                +"        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 0 AS Jam "
                +" FROM   Rpt_HadafForoshTedady  "
                +" WHERE ccBrand IN (" + ccBrand + ") AND ccGorohKala IN ( " + allGoroohKalasCodes +")"
                +" GROUP BY ccBrand, ccGorohKala, CodeKalaOld, NameKala"

                + " UNION ALL  "

                + " SELECT 0, 0, 0, 0 , 'جمع', SUM(TedadForoshMah) AS TedadForoshMah, SUM(TedadHadafMah) AS TedadHadafMah, "
                + "        Round(((SUM(TedadForoshMah) * 1.0 / SUM(TedadHadafMah) * 1.0 ) * 100 ),2) AS DarsadMah , "
                + "        SUM(TedadForoshRooz) AS TedadForoshRooz, SUM(TedadHadafRooz) AS TedadHadafRooz, "
                + "        Round((( SUM(TedadForoshRooz) * 1.0 /SUM(TedadHadafRooz) * 1.0 ) * 100),2) AS DarsadRooz, 1 AS Jam "
                + " FROM   Rpt_HadafForoshTedady  "
                + " WHERE ccBrand IN ("+ccBrand+") AND ccGorohKala IN ( " + allGoroohKalasCodes +")";
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(strSql, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        BaseHadafForoshModel baseHadafForoshModel=new BaseHadafForoshModel();
                        baseHadafForoshModel.setCcBrand(cursor.getInt(1));
                        baseHadafForoshModel.setCcGorohKala(cursor.getInt(2));
                        baseHadafForoshModel.setCcKala(cursor.getInt(3));
                        baseHadafForoshModel.setNameKala(cursor.getString(4));
                        baseHadafForoshModel.setTedadForoshMah(cursor.getInt(5));
                        baseHadafForoshModel.setTedadHadafMah(cursor.getInt(6));
                        baseHadafForoshModel.setTedadForoshRooz(cursor.getInt(8));
                        baseHadafForoshModel.setTedadHadafRooz(cursor.getInt(9));
                        baseHadafForoshModel.setJam(cursor.getInt(11));


                        rptKalaHadafForoshModels.add(baseHadafForoshModel);



                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();

        } catch (Exception exception) {
            Log.i("catcchgetAllKala", "getGorohKalaAllKalas: ");
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptHadafForoshModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "getAllKalas", "", "getAllKalas", "");

        }
        return rptKalaHadafForoshModels;
    }



}
