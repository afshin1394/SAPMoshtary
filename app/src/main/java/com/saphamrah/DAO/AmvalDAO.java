package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.Model.AmvalModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetMoshtaryAmvalResult;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmvalDAO {


    private DBHelper dbHelper;
    private Context context;


    public AmvalDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            //logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "AmvalDAO", "", "constructor", "");
        }
    }

    private String[] allColumns() {
        return new String[]
                {
                        AmvalModel.COLUMN_ccAmval(),
                        AmvalModel.COLUMN_NoeAmval(),
                        AmvalModel.COLUMN_NameAmval(),
                        AmvalModel.COLUMN_Tol(),
                        AmvalModel.COLUMN_Arz(),
                        AmvalModel.COLUMN_Ertefa(),
                        AmvalModel.COLUMN_Temperature(),
                        AmvalModel.COLUMN_Barcode(),
                        AmvalModel.COLUMN_ExtraProp_Barcode(),
                        AmvalModel.COLUMN_ccMoshtary()
                };
    }

    public void fetchAllMoshtaryAmval(final Context context, final String activityNameForLog, final int ccMoshtary,final int ccSazmanForosh, final RetrofitResponse retrofitResponse) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AmvalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryAmval", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMoshtaryAmvalResult> call = apiServiceGet.GetMoshtaryAmval(ccMoshtary, ccSazmanForosh);
            call.enqueue(new Callback<GetMoshtaryAmvalResult>() {
                @Override
                public void onResponse(Call<GetMoshtaryAmvalResult> call, Response<GetMoshtaryAmvalResult> response) {
                    try {
                        GetProgramModel.responseSize += response.raw().toString().getBytes(StandardCharsets.UTF_8).length;

                        if (response.raw().body() != null) {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, AmvalDAO.class.getSimpleName(), "", "R", "onResponse");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetMoshtaryAmvalResult result = response.body();
                            if (result != null) {
                                if (result.getSuccess()) {
                                    retrofitResponse.onSuccess(result.getData());
                                } else {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), AmvalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryAmval", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            } else {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), AmvalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryAmval", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        } else {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AmvalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryAmval", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), AmvalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryAmval", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMoshtaryAmvalResult> call, Throwable t) {

                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), AmvalDAO.class.getSimpleName(), activityNameForLog, "fetchAllMoshtaryAmval", "onFailure");
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

    public boolean insertGroup(ArrayList<AmvalModel> models) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (AmvalModel moshtaryAmvalModel : models) {
                ContentValues contentValues = modelToContentvalue(moshtaryAmvalModel);
                db.insertOrThrow(AmvalModel.TableName(), null, contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert, AmvalModel.TableName()) + "\n" + exception.toString();
            //logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AmvalDAO", "", "insertGroup", "");
            return false;
        }
    }

    public ArrayList<AmvalModel> getAll() {
        ArrayList<AmvalModel> moshtaryAmvalModel = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AmvalModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    moshtaryAmvalModel = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, AmvalModel.TableName()) + "\n" + exception.toString();
            //logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AmvalDAO", "", "getAll", "");
        }
        return moshtaryAmvalModel;
    }

    public int getByBarcode(String Barcode, int ccMoshtary) {
        AmvalModel model = new AmvalModel();
        //0 for wrong; 1 for success ; 2 for repeated
        byte status = 0;

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + AmvalModel.TableName() + " where " + AmvalModel.COLUMN_Barcode() + " = '" + Barcode + "' and " +
                    AmvalModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
            Log.d("AmvalDAO","getByBarcode query:"+ query);
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    model = cursorToModel(cursor).get(0);
                    cursor.close();
                    status = 1;

                    if (model.getExtraProp_Barcode() != null)
                        status = 2;
                    else
                        updateByBarcode(db, Barcode);
                }
            }
            db.close();


        } catch (Exception exception) {
            exception.printStackTrace();


            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, AmvalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AmvalDAO", "", "getByParent", "");
        }
        return status;
    }

    public ArrayList<AmvalModel> getAllByScanned(int ccMoshtary) {
        ArrayList<AmvalModel> models = null;

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + AmvalModel.TableName() + " where " + AmvalModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " AND " +
                    AmvalModel.COLUMN_ExtraProp_Barcode() +  " = " +  1 ;
            Cursor cursor = db.rawQuery(query, null);
            models = cursorToModel(cursor);


            cursor.close();

        } catch (Exception exception) {
            exception.printStackTrace();


            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, AmvalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AmvalDAO", "", "getByParent", "");
        }
        return models;
    }


    public boolean getCheckAllSabtAmvallByccMoshtary(int ccMoshtary) {
        AmvalModel model = new AmvalModel();
        boolean flag = true;

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + AmvalModel.TableName() + " where " + AmvalModel.COLUMN_ccMoshtary() + " = " + ccMoshtary  ;
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursorToModel(cursor).size(); i++ ){
                        if (cursorToModel(cursor).get(i).getExtraProp_Barcode() == null)
                            flag = false;
                    }
                    cursor.close();
                }
                else
                    flag = false;
            }
            db.close();


        } catch (Exception exception) {
            exception.printStackTrace();


            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, AmvalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AmvalDAO", "", "getCheckAllSabtAmvallByccMoshtary", "");
        }
        return flag;
    }
    private void updateByBarcode(SQLiteDatabase db, String Barcode){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AmvalModel.COLUMN_ExtraProp_Barcode() , "1");
        db.update(AmvalModel.TableName() , contentValues , AmvalModel.COLUMN_Barcode() + " =? " , new String[]{Barcode});
    }

    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AmvalModel.TableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, AmvalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AmvalDAO", "", "deleteAll", "");
            return false;
        }
    }
    public boolean deleteByCcMoshtaryAndCcSazmanForosh(int ccMoshtary, int ccSazmanForosh) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AmvalModel.TableName(), AmvalModel.COLUMN_ccMoshtary() + '=' + ccMoshtary +" AND "+ AmvalModel.COLUMN_ccSazmanForosh() + '=' + ccSazmanForosh, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, AmvalModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AmvalDAO", "", "deleteByccMoshtary", "");
            return false;
        }
    }

    private ArrayList<AmvalModel> cursorToModel(Cursor cursor) {
        ArrayList<AmvalModel> moshtaryAmvalModel = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AmvalModel model = new AmvalModel();

            model.setCcAmval(cursor.getInt(cursor.getColumnIndex(AmvalModel.COLUMN_ccAmval())));
            model.setArz(cursor.getInt(cursor.getColumnIndex(AmvalModel.COLUMN_Arz())));
            model.setBarcode(cursor.getString(cursor.getColumnIndex(AmvalModel.COLUMN_Barcode())));
            model.setErtefa(cursor.getInt(cursor.getColumnIndex(AmvalModel.COLUMN_Ertefa())));
            model.setTol(cursor.getInt(cursor.getColumnIndex(AmvalModel.COLUMN_Tol())));
            model.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(AmvalModel.COLUMN_ccMoshtary())));
            model.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(AmvalModel.COLUMN_ccSazmanForosh())));
            model.setTemperature(cursor.getInt(cursor.getColumnIndex(AmvalModel.COLUMN_Temperature())));
            model.setNoeAmval(cursor.getInt(cursor.getColumnIndex(AmvalModel.COLUMN_NoeAmval())));
            model.setNameAmval(cursor.getString(cursor.getColumnIndex(AmvalModel.COLUMN_NameAmval())));
            model.setExtraProp_Barcode(cursor.getString(cursor.getColumnIndex(AmvalModel.COLUMN_ExtraProp_Barcode())));

            moshtaryAmvalModel.add(model);
            cursor.moveToNext();
        }
        return moshtaryAmvalModel;
    }

    private static ContentValues modelToContentvalue(AmvalModel model) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(AmvalModel.COLUMN_ccAmval(), model.getCcAmval());
        contentValues.put(AmvalModel.COLUMN_NoeAmval(),model.getNoeAmval());
        contentValues.put(AmvalModel.COLUMN_NameAmval(),model.getNameAmval());
        contentValues.put(AmvalModel.COLUMN_Tol(), model.getTol());
        contentValues.put(AmvalModel.COLUMN_Arz(), model.getArz());
        contentValues.put(AmvalModel.COLUMN_Ertefa(),model.getErtefa());
        contentValues.put(AmvalModel.COLUMN_Temperature(),model.getTemperature());
        contentValues.put(AmvalModel.COLUMN_Barcode(), model.getBarcode());
        contentValues.put(AmvalModel.COLUMN_ccMoshtary(), model.getCcMoshtary());
        contentValues.put(AmvalModel.COLUMN_ccSazmanForosh(), model.getCcSazmanForosh());

        return contentValues;
    }




}
