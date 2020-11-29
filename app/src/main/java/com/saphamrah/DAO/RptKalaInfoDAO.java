package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.Model.RptMojodiAnbarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllrptKalaInfoResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RptKalaInfoDAO {

    private DBHelper dbHelper;
    private Context context;


    public RptKalaInfoDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptKalaInfoDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        RptKalaInfoModel.COLUMN_ccKalaCode(),
                        RptKalaInfoModel.COLUMN_NameKala(),
                        RptKalaInfoModel.COLUMN_CodeKala(),
                        RptKalaInfoModel.COLUMN_NameBrand(),
                        RptKalaInfoModel.COLUMN_GheymatMasrafKonandeh(),
                        RptKalaInfoModel.COLUMN_GheymatForoshAsli(),
                        RptKalaInfoModel.COLUMN_TedadDarKarton(),
                        RptKalaInfoModel.COLUMN_TedadDarBasteh(),
                        RptKalaInfoModel.COLUMN_Tol(),
                        RptKalaInfoModel.COLUMN_Arz(),
                        RptKalaInfoModel.COLUMN_Ertefa(),
                        RptKalaInfoModel.COLUMN_NameVahedSize(),
                        RptKalaInfoModel.COLUMN_NameVahedShomaresh(),
                        RptKalaInfoModel.COLUMN_VaznKhales(),
                        RptKalaInfoModel.COLUMN_VaznNaKhales(),
                        RptKalaInfoModel.COLUMN_VaznKarton(),
                        RptKalaInfoModel.COLUMN_BarCode(),
                        RptKalaInfoModel.COLUMN_VaznKartonTabdili(),
                        RptKalaInfoModel.COLUMN_HajmKartonTabdili(),
                        RptKalaInfoModel.COLUMN_ShomarehBach(),
                        RptKalaInfoModel.COLUMN_MashmolMaliatAvarez(),
                        RptKalaInfoModel.COLUMN_ccBrand(),
                        RptKalaInfoModel.COLUMN_ccGoroh(),
                        RptKalaInfoModel.COLUMN_NameGoroh(),
                };
    }

    public void fetchRptKalaInfo(final Context context, final String activityNameForLog, String ccMarkazSazmanSakhtarForosh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptKalaInfoDAO.class.getSimpleName(), activityNameForLog, "fetchRptKalaInfo", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp(), serverIpModel.getPort()).create(APIService.class);
            Call<GetAllrptKalaInfoResult> call = apiService.getAllrptKalaInfo(ccMarkazSazmanSakhtarForosh);
            call.enqueue(new Callback<GetAllrptKalaInfoResult>() {
                @Override
                public void onResponse(Call<GetAllrptKalaInfoResult> call, Response<GetAllrptKalaInfoResult> response) {
                    try {
                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptKalaInfoDAO.class.getSimpleName(), "", "fetchRptKalaInfo", "onResponse");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetAllrptKalaInfoResult result = response.body();
                            if (result != null) {
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());
                                } else {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptKalaInfoDAO.class.getSimpleName(), activityNameForLog, "fetchRptKalaInfo", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            } else {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptKalaInfoDAO.class.getSimpleName(), activityNameForLog, "fetchRptKalaInfo", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        } else {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptKalaInfoDAO.class.getSimpleName(), activityNameForLog, "fetchRptKalaInfo", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptKalaInfoDAO.class.getSimpleName(), activityNameForLog, "fetchRptKalaInfo", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptKalaInfoResult> call, Throwable t) {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), RptKalaInfoDAO.class.getSimpleName(), activityNameForLog, "fetchRptKalaInfo", "onFailure");
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

    public boolean insertGroup(ArrayList<RptKalaInfoModel> rptKalaInfoModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (RptKalaInfoModel rptKalaInfoModel : rptKalaInfoModels) {
                ContentValues contentValues = modelToContentvalue(rptKalaInfoModel);
                db.insertOrThrow(RptKalaInfoModel.TableName(), null, contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert, RptKalaInfoModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKalaInfoDAO", "", "insertGroup", "");
            return false;
        }
    }

    public ArrayList<RptKalaInfoModel> getAll() {
        ArrayList<RptKalaInfoModel> rptKalaInfoModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptKalaInfoModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    rptKalaInfoModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptKalaInfoModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKalaInfoDAO", "", "getAll", "");
        }
        return rptKalaInfoModels;
    }

    /*
    get kala ccBrand list for show to filter list
     */
    public ArrayList<RptKalaInfoModel> getCcBrandList() {
        ArrayList<RptKalaInfoModel> rptKalaInfoModels = new ArrayList<>();

        String query = " select Distinct ccBrand , NameBrand from  " + RptKalaInfoModel.TableName() ;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {

                    // set text all for filter
                    RptKalaInfoModel textAllFilter = new RptKalaInfoModel();
                    textAllFilter.setCcBrand(0);
                    textAllFilter.setNameBrand("همه");
                    rptKalaInfoModels.add(textAllFilter);

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        RptKalaInfoModel rptKalaInfoModel = new RptKalaInfoModel();
                        rptKalaInfoModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_ccBrand())));
                        rptKalaInfoModel.setNameBrand(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_NameBrand())));
                        rptKalaInfoModels.add(rptKalaInfoModel);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptKalaInfoModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKalaInfoDAO", "", "getAll", "");
        }


        return rptKalaInfoModels;
    }

    /*
     select filter and search in DB by ccBrand
      */
    public ArrayList<RptKalaInfoModel> getByCcBrandList(String ccBrands) {
        ArrayList<RptKalaInfoModel> rptKalaInfoModels = new ArrayList<>();

        String query = "  select * from Rpt_KalaInfo where ccBrand in (" + ccBrands + " ) ";
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {

                 rptKalaInfoModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptKalaInfoModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKalaInfoDAO", "", "getAll", "");
        }


        return rptKalaInfoModels;
    }

    /*
   get kala ccGoroh list for show to filter list
    */
    public ArrayList<RptKalaInfoModel> getByCcGorohList(String ccBrands) {
        ArrayList<RptKalaInfoModel> rptKalaInfoModels = new ArrayList<>();

        String query = " select  distinct ccGoroh , NameGoroh  from Rpt_KalaInfo where ccBrand in (" + ccBrands + ")" ;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        RptKalaInfoModel rptKalaInfoModel = new RptKalaInfoModel();
                        rptKalaInfoModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_ccGoroh())));
                        rptKalaInfoModel.setNameGoroh(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_NameGoroh())));
                        rptKalaInfoModels.add(rptKalaInfoModel);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptKalaInfoModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKalaInfoDAO", "", "getAll", "");
        }


        return rptKalaInfoModels;
    }

    /*
    select filter and search in DB by ccGoroh
     */
    public ArrayList<RptKalaInfoModel> getByCcGoroh(String ccGoroh) {
        ArrayList<RptKalaInfoModel> rptKalaInfoModels = new ArrayList<>();

        String query = "  select * from Rpt_KalaInfo where ccGoroh in (" + ccGoroh + " ) ";
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {

                    rptKalaInfoModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, RptKalaInfoModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKalaInfoDAO", "", "getAll", "");
        }


        return rptKalaInfoModels;
    }

    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptKalaInfoModel.TableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, RptKalaInfoModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptKalaInfoDAO", "", "deleteAll", "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptKalaInfoModel rptKalaInfoModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(RptKalaInfoModel.COLUMN_ccKalaCode(), rptKalaInfoModel.getCcKalaCode());
        contentValues.put(RptKalaInfoModel.COLUMN_NameKala(), rptKalaInfoModel.getNameKala());
        contentValues.put(RptKalaInfoModel.COLUMN_CodeKala(), rptKalaInfoModel.getCodeKala());
        contentValues.put(RptKalaInfoModel.COLUMN_NameBrand(), rptKalaInfoModel.getNameBrand());
        contentValues.put(RptKalaInfoModel.COLUMN_GheymatMasrafKonandeh(), rptKalaInfoModel.getGheymatMasrafKonandeh());
        contentValues.put(RptKalaInfoModel.COLUMN_GheymatForoshAsli(), rptKalaInfoModel.getGheymatForoshAsli());
        contentValues.put(RptKalaInfoModel.COLUMN_TedadDarKarton(), rptKalaInfoModel.getTedadDarKarton());
        contentValues.put(RptKalaInfoModel.COLUMN_TedadDarBasteh(), rptKalaInfoModel.getTedadDarBasteh());
        contentValues.put(RptKalaInfoModel.COLUMN_Tol(), rptKalaInfoModel.getTol());
        contentValues.put(RptKalaInfoModel.COLUMN_Arz(), rptKalaInfoModel.getArz());
        contentValues.put(RptKalaInfoModel.COLUMN_Ertefa(), rptKalaInfoModel.getErtefa());
        contentValues.put(RptKalaInfoModel.COLUMN_NameVahedSize(), rptKalaInfoModel.getNameVahedSize());
        contentValues.put(RptKalaInfoModel.COLUMN_NameVahedShomaresh(), rptKalaInfoModel.getNameVahedShomaresh());
        contentValues.put(RptKalaInfoModel.COLUMN_VaznKhales(), rptKalaInfoModel.getVaznKhales());
        contentValues.put(RptKalaInfoModel.COLUMN_VaznNaKhales(), rptKalaInfoModel.getVaznNaKhales());
        contentValues.put(RptKalaInfoModel.COLUMN_VaznKarton(), rptKalaInfoModel.getVaznKarton());
        contentValues.put(RptKalaInfoModel.COLUMN_BarCode(), rptKalaInfoModel.getBarCode());
        contentValues.put(RptKalaInfoModel.COLUMN_VaznKartonTabdili(), rptKalaInfoModel.getVaznKartonTabdili());
        contentValues.put(RptKalaInfoModel.COLUMN_HajmKartonTabdili(), rptKalaInfoModel.getHajmKartonTabdili());
        contentValues.put(RptKalaInfoModel.COLUMN_ShomarehBach(), rptKalaInfoModel.getShomarehBach());
        contentValues.put(RptKalaInfoModel.COLUMN_MashmolMaliatAvarez(), rptKalaInfoModel.getMashmolMaliatAvarez());
        contentValues.put(RptKalaInfoModel.COLUMN_ccBrand(), rptKalaInfoModel.getCcBrand());
        contentValues.put(RptKalaInfoModel.COLUMN_ccGoroh(), rptKalaInfoModel.getCcGoroh());
        contentValues.put(RptKalaInfoModel.COLUMN_NameGoroh(), rptKalaInfoModel.getNameGoroh());
        return contentValues;
    }


    private ArrayList<RptKalaInfoModel> cursorToModel(Cursor cursor) {
        ArrayList<RptKalaInfoModel> rptKalaInfoModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RptKalaInfoModel rptKalaInfoModel = new RptKalaInfoModel();

            rptKalaInfoModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_ccKalaCode())));
            rptKalaInfoModel.setNameKala(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_NameKala())));
            rptKalaInfoModel.setCodeKala(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_CodeKala())));
            rptKalaInfoModel.setNameBrand(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_NameBrand())));
            rptKalaInfoModel.setGheymatMasrafKonandeh(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_GheymatMasrafKonandeh())));
            rptKalaInfoModel.setGheymatForoshAsli(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_GheymatForoshAsli())));
            rptKalaInfoModel.setTedadDarKarton(cursor.getInt(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_TedadDarKarton())));
            rptKalaInfoModel.setTedadDarBasteh(cursor.getInt(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_TedadDarBasteh())));
            rptKalaInfoModel.setTol(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_Tol())));
            rptKalaInfoModel.setArz(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_Arz())));
            rptKalaInfoModel.setErtefa(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_Ertefa())));
            rptKalaInfoModel.setNameVahedSize(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_NameVahedSize())));
            rptKalaInfoModel.setNameVahedShomaresh(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_NameVahedShomaresh())));
            rptKalaInfoModel.setVaznKhales(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_VaznKhales())));
            rptKalaInfoModel.setVaznKhales(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_VaznNaKhales())));
            rptKalaInfoModel.setVaznKarton(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_VaznKarton())));
            rptKalaInfoModel.setBarCode(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_BarCode())));
            rptKalaInfoModel.setVaznKartonTabdili(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_VaznKartonTabdili())));
            rptKalaInfoModel.setHajmKartonTabdili(cursor.getDouble(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_HajmKartonTabdili())));
            rptKalaInfoModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_ShomarehBach())));
            rptKalaInfoModel.setMashmolMaliatAvarez(cursor.getInt(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_MashmolMaliatAvarez())));
            rptKalaInfoModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_ccBrand())));
            rptKalaInfoModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_ccGoroh())));
            rptKalaInfoModel.setNameGoroh(cursor.getString(cursor.getColumnIndex(RptKalaInfoModel.COLUMN_NameGoroh())));

            rptKalaInfoModels.add(rptKalaInfoModel);
            cursor.moveToNext();
        }
        return rptKalaInfoModels;
    }


}
