package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.saphamrah.Model.JayezehSatrModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllvJayezehSatrResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JayezehSatrDAO
{
    
    private DBHelper dbHelper;
    private Context context;


    public JayezehSatrDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "JayezehSatrDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            JayezehSatrModel.COLUMN_ccJayezehSatr(),
            JayezehSatrModel.COLUMN_ccJayezeh(),
            JayezehSatrModel.COLUMN_NameNoeField(),
            JayezehSatrModel.COLUMN_ccNoeField(),
            JayezehSatrModel.COLUMN_Az(),
            JayezehSatrModel.COLUMN_Ta(),
            JayezehSatrModel.COLUMN_CodeNoeBastehBandy(),
            JayezehSatrModel.COLUMN_BeEza(),
            JayezehSatrModel.COLUMN_CodeNoeBastehBandyBeEza(),
            JayezehSatrModel.COLUMN_TedadJayezeh(),
            JayezehSatrModel.COLUMN_RialJayezeh(),
            JayezehSatrModel.COLUMN_ccKalaCodeJayezeh(),
            JayezehSatrModel.COLUMN_MohasebehAzMazad(),
            JayezehSatrModel.COLUMN_NoeRialJayezeh(),
            JayezehSatrModel.COLUMN_ccKalaJayezeh(),
            JayezehSatrModel.COLUMN_ccKalaJayezehMazad(),
            JayezehSatrModel.COLUMN_ccKalaCodeJayezehMazad(),
            JayezehSatrModel.COLUMN_Naghdy()
            //JayezehSatrModel.COLUMN_JayezehSatrKalas()
        };
    }


    /**
     *  وب سرویس جدید برای دریافت لیست کلیه جایزه ها بر اساس مرکز فروش و جایزه ها برای اعمال جایزه انتخابی
     * @param context
     * @param activityNameForLog
     * @param ccMarkazSazmanForosh
     * @param ccJayezehs
     * @param retrofitResponse
     */
    public void fetchJayezehSatr(final Context context, final String activityNameForLog, String ccMarkazSazmanForosh, String ccJayezehs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvJayezehSatrResult> call = apiService.getJayezehSatr("2", ccMarkazSazmanForosh, ccJayezehs);
            call.enqueue(new Callback<GetAllvJayezehSatrResult>()
            {
                @Override
                public void onResponse(Call<GetAllvJayezehSatrResult> call, Response<GetAllvJayezehSatrResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length = " + contentLength, JayezehSatrDAO.class.getSimpleName(), "", "fetchJayezehSatr", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvJayezehSatrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvJayezehSatrResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    /**
     * @deprecated
     * @param context
     * @param activityNameForLog
     * @param retrofitResponse
     */
    public void fetchJayezehSatr(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllvJayezehSatrResult> call = apiService.getAllvJayezehSatr();
            call.enqueue(new Callback<GetAllvJayezehSatrResult>() {
                @Override
                public void onResponse(Call<GetAllvJayezehSatrResult> call, Response<GetAllvJayezehSatrResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, JayezehSatrDAO.class.getSimpleName(), "", "fetchJayezehSatr", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvJayezehSatrResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvJayezehSatrResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), JayezehSatrDAO.class.getSimpleName(), activityNameForLog, "fetchJayezehSatr", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    private String getEndpoint(Call call)
    {
        String endpoint = "";
        try
        {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
        }
        catch (Exception e)
        {e.printStackTrace();}
        return endpoint;
    }

    public boolean insertGroup(ArrayList<JayezehSatrModel> jayezehSatrModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (JayezehSatrModel jayezehSatrModel : jayezehSatrModels)
            {
                ContentValues contentValues = modelToContentvalue(jayezehSatrModel);
                db.insertOrThrow(JayezehSatrModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , JayezehSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehSatrDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<JayezehSatrModel> getAll()
    {
        ArrayList<JayezehSatrModel> jayezehSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(JayezehSatrModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , JayezehSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehSatrDAO" , "" , "getAll" , "");
        }
        return jayezehSatrModels;
    }

    /**
     * این جایزه بابت کل فاکتور است و نوع آن 1 است
     * @param ccJayezeh
     * @param noeFieldKala
     * @param noeTedadRialArray -> noeTedadRialArray[0] = NoeTedadRial.Tedad.getValue() , noeTedadRialArray[1] = NoeTedadRial.Rial.getValue()
     * @param codeNoeBasteBandiArray -> codeNoeBasteBandiArray[0] = CodeNoeBastehBandy.Karton.getValue() , codeNoeBasteBandiArray[1] = CodeNoeBastehBandy.Basteh.getValue() ,codeNoeBasteBandiArray[2] = CodeNoeBastehBandy.Adad.getValue()
     * @param ccNoeField
     * @param tedad
     * @param tedadBasteh
     * @param tedadKarton
     * @param mablaghKol
     * @param noeTedadRial
     * @return
     */
    public ArrayList<JayezehSatrModel> getForFaktor(int ccJayezeh, int noeFieldKala, int[] noeTedadRialArray, int[] codeNoeBasteBandiArray, int ccNoeField, double tedad, double tedadBasteh, double tedadKarton, double mablaghKol, int noeTedadRial)
    {
        ArrayList<JayezehSatrModel> jayezehSatrs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM JayezehSatr WHERE ccJayezeh= " + ccJayezeh + "   AND NameNoeField= " + noeFieldKala + "   AND ccNoeField = " + ccNoeField;
            if (noeTedadRial == noeTedadRialArray[0])
            {
                query += "   AND (  (CodeNoeBastehBandy= " + codeNoeBasteBandiArray[0] + " AND Az<= " + tedadKarton + " AND " + tedadKarton + "<= Ta)"
                        + " OR(CodeNoeBastehBandy= " + codeNoeBasteBandiArray[1] + " AND Az<= " + tedadBasteh + " AND " + tedadBasteh + "<= Ta)"
                        + " OR(CodeNoeBastehBandy= " + codeNoeBasteBandiArray[2] + " AND Az <= " + tedad + " AND " + tedad + " <= Ta)"
                        + " )";
            }
            if (noeTedadRial == noeTedadRialArray[1])
            {
                query += "   AND Az<= " + mablaghKol + " AND " + mablaghKol + "<= Ta";
            }
            query += " ORDER BY BeEza DESC ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehSatrs = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , JayezehSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehSatrDAO" , "" , "getForFaktor" , "");
        }
        Log.d("jayezeh" , "jayezehSatrs.size in dao : " + jayezehSatrs.size());
        return jayezehSatrs;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(JayezehSatrModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , JayezehSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehSatrDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(JayezehSatrModel jayezehSatrModel)
    {
        ContentValues contentValues = new ContentValues();

        if (jayezehSatrModel.getCcJayezehSatr() > 0)
        {
            contentValues.put(JayezehSatrModel.COLUMN_ccJayezehSatr() , jayezehSatrModel.getCcJayezehSatr());
        }
        contentValues.put(JayezehSatrModel.COLUMN_ccJayezeh() , jayezehSatrModel.getCcJayezeh());
        contentValues.put(JayezehSatrModel.COLUMN_NameNoeField() , jayezehSatrModel.getNameNoeField());
        contentValues.put(JayezehSatrModel.COLUMN_ccNoeField() , jayezehSatrModel.getCcNoeField());
        contentValues.put(JayezehSatrModel.COLUMN_Az() , jayezehSatrModel.getAz());
        contentValues.put(JayezehSatrModel.COLUMN_Ta() , jayezehSatrModel.getTa());
        contentValues.put(JayezehSatrModel.COLUMN_CodeNoeBastehBandy() , jayezehSatrModel.getCodeNoeBastehBandy());
        contentValues.put(JayezehSatrModel.COLUMN_BeEza() , jayezehSatrModel.getBeEza());
        contentValues.put(JayezehSatrModel.COLUMN_CodeNoeBastehBandyBeEza() , jayezehSatrModel.getCodeNoeBastehBandyBeEza());
        contentValues.put(JayezehSatrModel.COLUMN_TedadJayezeh() , jayezehSatrModel.getTedadJayezeh());
        contentValues.put(JayezehSatrModel.COLUMN_RialJayezeh() , jayezehSatrModel.getRialJayezeh());
        contentValues.put(JayezehSatrModel.COLUMN_ccKalaCodeJayezeh() , jayezehSatrModel.getCcKalaCodeJayezeh());
        contentValues.put(JayezehSatrModel.COLUMN_MohasebehAzMazad() , jayezehSatrModel.getMohasebehAzMazad());
        contentValues.put(JayezehSatrModel.COLUMN_NoeRialJayezeh() , jayezehSatrModel.getNoeRialJayezeh());
        contentValues.put(JayezehSatrModel.COLUMN_ccKalaJayezeh() , jayezehSatrModel.getCcKalaJayezeh());
        contentValues.put(JayezehSatrModel.COLUMN_ccKalaJayezehMazad() , jayezehSatrModel.getCcKalaJayezehMazad());
        contentValues.put(JayezehSatrModel.COLUMN_ccKalaCodeJayezehMazad() , jayezehSatrModel.getCcKalaCodeJayezehMazad());
        contentValues.put(JayezehSatrModel.COLUMN_Naghdy() , jayezehSatrModel.getNaghdy());
        //contentValues.put(JayezehSatrModel.COLUMN_JayezehSatrKalas() , jayezehSatrModel.getJayezehSatrKalas());

        return contentValues;
    }


    private ArrayList<JayezehSatrModel> cursorToModel(Cursor cursor)
    {
        ArrayList<JayezehSatrModel> jayezehSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            JayezehSatrModel jayezehSatrModel = new JayezehSatrModel();

            jayezehSatrModel.setCcJayezehSatr(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_ccJayezehSatr())));
            jayezehSatrModel.setCcJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_ccJayezeh())));
            jayezehSatrModel.setNameNoeField(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_NameNoeField())));
            jayezehSatrModel.setCcNoeField(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_ccNoeField())));
            jayezehSatrModel.setAz(cursor.getDouble(cursor.getColumnIndex(JayezehSatrModel.COLUMN_Az())));
            jayezehSatrModel.setTa(cursor.getDouble(cursor.getColumnIndex(JayezehSatrModel.COLUMN_Ta())));
            jayezehSatrModel.setCodeNoeBastehBandy(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_CodeNoeBastehBandy())));
            jayezehSatrModel.setBeEza(cursor.getFloat(cursor.getColumnIndex(JayezehSatrModel.COLUMN_BeEza())));
            jayezehSatrModel.setCodeNoeBastehBandyBeEza(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_CodeNoeBastehBandyBeEza())));
            jayezehSatrModel.setTedadJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_TedadJayezeh())));
            jayezehSatrModel.setRialJayezeh(cursor.getFloat(cursor.getColumnIndex(JayezehSatrModel.COLUMN_RialJayezeh())));
            jayezehSatrModel.setCcKalaCodeJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_ccKalaCodeJayezeh())));
            jayezehSatrModel.setMohasebehAzMazad(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_MohasebehAzMazad())) > 0);
            jayezehSatrModel.setNoeRialJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_NoeRialJayezeh())));
            jayezehSatrModel.setCcKalaJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_ccKalaJayezeh())));
            jayezehSatrModel.setCcKalaJayezehMazad(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_ccKalaJayezehMazad())));
            jayezehSatrModel.setCcKalaCodeJayezehMazad(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_ccKalaCodeJayezehMazad())));
            jayezehSatrModel.setNaghdy(cursor.getInt(cursor.getColumnIndex(JayezehSatrModel.COLUMN_Naghdy())));
            //jayezehSatrModel.setJayezehSatrKalas(cursor.getString(cursor.getColumnIndex(JayezehSatrModel.COLUMN_JayezehSatrKalas())));

            jayezehSatrModels.add(jayezehSatrModel);
            cursor.moveToNext();
        }
        return jayezehSatrModels;
    }



}
