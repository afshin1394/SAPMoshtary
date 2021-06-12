package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.JayezehModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.JayezehByccKalaCodeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetAllvJayezehByccMarkazForoshResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JayezehDAO 
{


    private DBHelper dbHelper;
    private Context context;


    public JayezehDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "JayezehDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            JayezehModel.COLUMN_ccJayezeh(),
            JayezehModel.COLUMN_CodeNoe(),
            JayezehModel.COLUMN_SharhJayezeh(),
            JayezehModel.COLUMN_NoeTedadRial(),
            JayezehModel.COLUMN_NameNoeFieldMoshtary(),
            JayezehModel.COLUMN_ccNoeFieldMoshtary(),
            JayezehModel.COLUMN_ccMarkazSazmanForosh(),
            JayezehModel.COLUMN_CodeNoeHaml(),
            JayezehModel.COLUMN_CodeNoeMohasebeh(),
            JayezehModel.COLUMN_Darajeh(),
            JayezehModel.COLUMN_IsJayezehEntekhabi(),
            JayezehModel.COLUMN_ccNoeSenf(),
            JayezehModel.COLUMN_NameNoeSenf()
        };
    }


    /**
     * @deprecated
     * @param context
     * @param activityNameForLog
     * @param ccMarkazForosh
     * @param retrofitResponse
     */
    public void fetchAllvJayezehByccMarkazForosh(final Context context, final String activityNameForLog, final String ccMarkazForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForosh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllvJayezehByccMarkazForoshResult> call = apiServiceGet.getAllvJayezehByccMarkazForosh(ccMarkazForosh);
            call.enqueue(new Callback<GetAllvJayezehByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvJayezehByccMarkazForoshResult> call, Response<GetAllvJayezehByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, JayezehDAO.class.getSimpleName(), "", "fetchAllvJayezehByccMarkazForosh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvJayezehByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForosh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForosh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForosh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForosh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvJayezehByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForosh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    /**
     * @deprecated
     * @param context
     * @param activityNameForLog
     * @param ccMarkazForoshPakhsh
     * @param retrofitResponse
     */
    public void fetchAllvJayezehByccMarkazForoshPakhsh(final Context context, final String activityNameForLog, final String ccMarkazForoshPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForoshPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvJayezehByccMarkazForoshResult> call = apiServiceGet.getAllvJayezehByccMarkazForoshForPakhsh(ccMarkazForoshPakhsh);
            call.enqueue(new Callback<GetAllvJayezehByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvJayezehByccMarkazForoshResult> call, Response<GetAllvJayezehByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, JayezehDAO.class.getSimpleName(), "", "fetchAllvJayezehByccMarkazForoshPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvJayezehByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForoshPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForoshPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForoshPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForoshPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvJayezehByccMarkazForoshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllvJayezehByccMarkazForosh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    /**
     * وب سرویس جدید برای دریافت لیست کلیه جایزه ها بر اساس مرکز فروش برای اعمال جایزه انتخابی
     * @param context
     * @param activityNameForLog
     * @param ccMarkazSazmanForosh
     * @param retrofitResponse
     */
    public void fetchAllJayezeh(final Context context, final String activityNameForLog, final String ccMarkazSazmanForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllJayezeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetAllvJayezehByccMarkazForoshResult> call = apiServiceGet.getJayezeh("1", ccMarkazSazmanForosh, "-1");
            call.enqueue(new Callback<GetAllvJayezehByccMarkazForoshResult>() {
                @Override
                public void onResponse(Call<GetAllvJayezehByccMarkazForoshResult> call, Response<GetAllvJayezehByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length = " + contentLength, JayezehDAO.class.getSimpleName(), "", "fetchAllJayezeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllvJayezehByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllJayezeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllJayezeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllJayezeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllJayezeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllvJayezehByccMarkazForoshResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), JayezehDAO.class.getSimpleName(), activityNameForLog, "fetchAllJayezeh", "onFailure");
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


    public boolean insertGroup(ArrayList<JayezehModel> jayezehModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (JayezehModel jayezehModel : jayezehModels)
            {
                ContentValues contentValues = modelToContentvalue(jayezehModel);
                db.insertOrThrow(JayezehModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , JayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<JayezehModel> getAll()
    {
        ArrayList<JayezehModel> jayezehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(JayezehModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , JayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehDAO" , "" , "getAll" , "");
        }
        return jayezehModels;
    }

    public ArrayList<JayezehModel> getByMoshtary(MoshtaryModel moshtary, int codeNoeHaml)
    {

        ArrayList<JayezehModel> jayezehModels = new ArrayList<>();
        final int NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY = 1;
        final int NAME_NOE_FIELD_MOSHTARY_CC_GOROH = 2;
        final int GOROH_LINK_NOE_MOSHTARY = 304 ;
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
        int ccMarkazSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getCcMarkazSazmanForosh(),0);
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String StrSQL="SELECT * FROM Jayezeh "
                    +  " WHERE ccMarkazSazmanForosh = " + ccMarkazSazmanForosh + " AND (CodeNoe = 1 AND "
                    +  " ((NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY + " AND ccNoeFieldMoshtary= " + moshtary.getCcMoshtary() + ") "
                    +  " OR (NameNoeFieldMoshtary= " + NAME_NOE_FIELD_MOSHTARY_CC_GOROH + " AND ccNoeFieldMoshtary IN (" + moshtary.getCcNoeMoshtary() + "," + GOROH_LINK_NOE_MOSHTARY +") AND ccNoeSenf IN (0," + moshtary.getCcNoeSenf() +"))"
                    +  " AND CodeNoeHaml="+ codeNoeHaml + " AND Darajeh IN ( 0,"+ moshtary.getDarajeh() +")))";



            Log.d("JayezehDAO","query: " + StrSQL);
            Cursor cursor = db.rawQuery(StrSQL, null);

            Log.d("JayezehDAO","ccMarkazSazmanForosh:" + ccMarkazSazmanForosh + " NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY: " + NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY +
                    " moshtary.getCcMoshtary(): " + moshtary.getCcMoshtary() + " NAME_NOE_FIELD_MOSHTARY_CC_GOROH: " + NAME_NOE_FIELD_MOSHTARY_CC_GOROH + " moshtary.getCcNoeMoshtary(): " + moshtary.getCcNoeMoshtary() + "-" +GOROH_LINK_NOE_MOSHTARY + " CcNoeSenf:" +  moshtary.getCcNoeSenf() +
                    " codeNoeHaml: " +codeNoeHaml + " Darajeh: " + moshtary.getDarajeh()+",0" );


            /*Cursor cursor = db.query(JayezehModel.TableName(), allColumns(),
                    "  CodeNoe = 3 AND "
                            +  " (    (NameNoeFieldMoshtary= ? AND ccNoeFieldMoshtary= ?) "
                            // + " OR (NameNoeFieldMoshtary= ? AND ccNoeFieldMoshtary= ?)"
                            + " OR (NameNoeFieldMoshtary= ? AND ccNoeFieldMoshtary IN(?, ?))"
                            + " AND CodeNoeHaml= ? AND (Darajeh=9 OR Darajeh= ?))",
                    new String[]{String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_MOSHTARY), String.valueOf(moshtary.getCcMoshtary()),
                            String.valueOf(NAME_NOE_FIELD_MOSHTARY_CC_GOROH), String.valueOf(moshtaryGoroh.getCcGoroh()), String.valueOf(GOROH_LINK_NOE_MOSHTARY),
                            String.valueOf(codeNoeHaml), String.valueOf(moshtary.getDarajeh())},
                    null, null, null);*/
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehModels = cursorToModel(cursor);
                    Log.d("JayezehDAO","jayezehModels:" + jayezehModels.toString());
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , JayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehDAO" , "" , "getByMoshtary" , "");
        }
        return jayezehModels;
    }

    public ArrayList<JayezehByccKalaCodeModel> getByccKalaCodeParent(int ccKalaCode , int tedadKala , int CcnoeMoshtary , int CcNoeSenf , int darajeh )
    {
        ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeModels = new ArrayList<>();

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String StrSQL="SELECT DISTINCT j.ccJayezeh, j.sharhJayezeh, j.NoeTedadRial \n" +
                    "FROM jayezeh j\n" +
                    "     LEFT JOIN jayezehSatr js ON js.ccJayezeh = j.ccJayezeh\n" +
                    "     LEFT JOIN Kala k ON k.ccKalaCode = js.ccNoeField\n" +
                    "WHERE js.NameNoeField=1 AND js.ccNoeField= "+ ccKalaCode +"  --cckalacode\n" +
                    "      AND ccNoeFieldMoshtary = "+ CcnoeMoshtary +" AND ccNoeSenf  IN ( 0 , " + CcNoeSenf +") AND darajeh IN (0, " + darajeh +") \n" +
                    "      AND  js.tedadJayezeh > 0\n" +
                    "UNION ALL\n" +
                    "SELECT DISTINCT j.ccJayezeh, j.sharhJayezeh, j.NoeTedadRial  \n" +
                    "FROM jayezeh j\n" +
                    "     LEFT JOIN jayezehSatr js ON js.ccJayezeh = j.ccJayezeh\n" +
                    "     LEFT JOIN KalaGoroh kg ON kg.ccGoroh = js.ccNoeField \n" +
                    "     LEFT JOIN Kala k ON k.ccKalaCode = js.ccNoeField\n" +
                    "WHERE js.NameNoeField=2 AND kg.ccKalaCode= "+ ccKalaCode +" --cckalacode\n" +
                    "      AND ccNoeFieldMoshtary = "+ CcnoeMoshtary +" AND ccNoeSenf  IN ( 0 , " + CcNoeSenf +") AND darajeh IN (0, " + darajeh +") \n" +
                    "      AND  js.tedadJayezeh > 0\n" +
                    "UNION ALL\n" +
                    "SELECT DISTINCT j.ccJayezeh, j.sharhJayezeh, j.NoeTedadRial \n" +
                    "FROM jayezeh j\n" +
                    "     LEFT JOIN jayezehSatr js ON js.ccJayezeh = j.ccJayezeh\n" +
                    "     LEFT JOIN Kala k ON k.ccBrand = js.ccNoeField \n" +
                    "WHERE js.NameNoeField=3 AND k.ccKalaCode= "+ ccKalaCode +" --cckalacode\n" +
                    "      AND ccNoeFieldMoshtary = "+ CcnoeMoshtary +" AND ccNoeSenf  IN ( 0 , " + CcNoeSenf +") AND darajeh IN (0, " + darajeh + ") \n" +
                    "      AND js.tedadJayezeh > 0\n" +
                    "UNION ALL\n" +
                    "SELECT DISTINCT j.ccJayezeh, j.sharhjayezeh, j.NoeTedadRial \n" +
                    "From jayezeh j \n" +
                    "     LEFT JOIN JayezehSatr js ON js.ccJayezeh = j.ccJayezeh\n" +
                    "     LEFT JOIN Kala k ON k.ccTaminKonandeh = js.ccNoeField \n" +
                    "WHERE js.NameNoeField=4 AND k.ccKalaCode= "+ ccKalaCode +" --cckalacode\n" +
                    "      AND ccNoeFieldMoshtary = "+ CcnoeMoshtary +" AND ccNoeSenf  IN ( 0 , " + CcNoeSenf +" ) AND Darajeh IN (0, " + darajeh +" ) \n" +
                    "      AND js.TedadJayezeh > 0";
            Log.i("query" , StrSQL);


            Cursor cursor = db.rawQuery(StrSQL, null);
            Log.i("query" , db.rawQuery(StrSQL, null).toString());

            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        JayezehByccKalaCodeModel model = new JayezehByccKalaCodeModel();
                        model.setSharhJayezeh(cursor.getString(cursor.getColumnIndex(JayezehModel.COLUMN_SharhJayezeh())));
                        model.setCcJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_ccJayezeh())));
                        jayezehByccKalaCodeModels.add(model);

                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , JayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehDAO" , "" , "getByMoshtary" , "");
        }
        return jayezehByccKalaCodeModels;
    }

    public ArrayList<JayezehByccKalaCodeModel> getByccKalaCode(int ccKalaCode , int tedadKala ,  int CcnoeMoshtary ,  int CcNoeSenf , int darajeh , int ccJayezeh )
    {
        ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeModels = new ArrayList<>();

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String StrSQL= "SELECT DISTINCT j.ccJayezeh, j.sharhJayezeh, j.NoeTedadRial, js.ccJayezehsatr, \n" +
                    "                CASE WHEN js.CodeNoeBastehBandy=1 THEN  (js.Az * k.TedadDarKarton)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=2 THEN  (js.Az * k.TedadDarBasteh)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=3 OR js.CodeNoeBastehBandy=4 THEN  js.Az END AS Az,\n" +
                    "                CASE WHEN js.CodeNoeBastehBandy=1 THEN  (js.Ta * k.TedadDarKarton)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=2 THEN  (js.Ta * k.TedadDarBasteh)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=3 OR js.CodeNoeBastehBandy=4 THEN  js.Ta END AS Ta,\n" +
                    "                CASE WHEN js.CodeNoeBastehBandyBeEza=1 THEN  js.BeEza\n" +
                    "                     WHEN js.CodeNoeBastehBandyBeEza=2 THEN  js.BeEza\n" +
                    "                     WHEN js.CodeNoeBastehBandyBeEza=3 OR js.CodeNoeBastehBandyBeEza=4 THEN  js.BeEza END AS BeEza,\n" +
                    "                js.TedadJayezeh , js.codeNoeBastehbandy\n" +
                    "FROM jayezeh j\n" +
                    "     LEFT JOIN jayezehSatr js ON js.ccJayezeh = j.ccJayezeh\n" +
                    "     LEFT JOIN Kala k ON k.ccKalaCode = js.ccNoeField\n" +
                    "WHERE js.NameNoeField=1 AND js.ccNoeField= "+ ccKalaCode +"  --cckalacode\n" +
                    "      AND ccNoeFieldMoshtary = "+ CcnoeMoshtary +" AND ccNoeSenf  IN ( 0 , " + CcNoeSenf +") AND darajeh IN (0, " + darajeh +") \n" +
                    "      AND  js.tedadJayezeh > 0 AND j.ccJayezeh = "+ ccJayezeh +" \n" +
                    "UNION ALL\n" +
                    "SELECT DISTINCT j.ccJayezeh, j.sharhJayezeh, j.NoeTedadRial, js.ccJayezehSatr,  \n" +
                    "                CASE WHEN js.CodeNoeBastehBandy=1 THEN  (js.Az * k.TedadDarKarton)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=2 THEN  (js.Az * k.TedadDarBasteh)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=3 OR js.CodeNoeBastehBandy=4 THEN  js.Az END AS Az,\n" +
                    "                CASE WHEN js.CodeNoeBastehBandy=1 THEN  (js.Ta * k.TedadDarKarton)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=2 THEN  (js.Ta * k.TedadDarBasteh)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=3 OR js.CodeNoeBastehBandy=4 THEN  js.Ta END AS Ta,\n" +
                    "                CASE WHEN js.CodeNoeBastehBandyBeEza=1 THEN  js.BeEza\n" +
                    "                     WHEN js.CodeNoeBastehBandyBeEza=2 THEN  js.BeEza\n" +
                    "                     WHEN js.CodeNoeBastehBandyBeEza=3 OR js.CodeNoeBastehBandyBeEza=4 THEN  js.BeEza END AS BeEza, \n" +
                    "                js.TedadJayezeh , js.codeNoeBastehbandy\n" +
                    "FROM jayezeh j\n" +
                    "     LEFT JOIN jayezehSatr js ON js.ccJayezeh = j.ccJayezeh\n" +
                    "     LEFT JOIN KalaGoroh kg ON kg.ccGoroh = js.ccNoeField \n" +
                    "     LEFT JOIN Kala k ON k.ccKalaCode = js.ccNoeField\n" +
                    "WHERE js.NameNoeField=2 AND kg.ccKalaCode= "+ ccKalaCode +" --cckalacode\n" +
                    "      AND ccNoeFieldMoshtary = "+ CcnoeMoshtary +" AND ccNoeSenf  IN ( 0 , " + CcNoeSenf +") AND darajeh IN (0, " + darajeh +") \n" +
                    "      AND  js.tedadJayezeh > 0 AND j.ccJayezeh = "+ ccJayezeh +" \n" +
                    "UNION ALL\n" +
                    "SELECT DISTINCT j.ccJayezeh, j.sharhJayezeh, j.NoeTedadRial, js.ccJayezehSatr, \n" +
                    "                CASE WHEN js.CodeNoeBastehBandy=1 THEN  (js.Az * k.TedadDarKarton)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=2 THEN  (js.Az * k.TedadDarBasteh)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=3 OR js.CodeNoeBastehBandy=4 THEN  js.Az END AS Az,\n" +
                    "                CASE WHEN js.CodeNoeBastehBandy=1 THEN  (js.Ta * k.TedadDarKarton)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=2 THEN  (js.Ta * k.TedadDarBasteh)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=3 OR js.CodeNoeBastehBandy=4 THEN  js.Ta END AS Ta,\n" +
                    "                CASE WHEN js.CodeNoeBastehBandyBeEza=1 THEN  js.BeEza\n" +
                    "                     WHEN js.CodeNoeBastehBandyBeEza=2 THEN  js.BeEza\n" +
                    "                     WHEN js.CodeNoeBastehBandyBeEza=3 OR js.CodeNoeBastehBandyBeEza=4 THEN  js.BeEza END AS BeEza, \n" +
                    "                js.TedadJayezeh , js.codeNoeBastehbandy\n" +
                    "FROM jayezeh j\n" +
                    "     LEFT JOIN jayezehSatr js ON js.ccJayezeh = j.ccJayezeh\n" +
                    "     LEFT JOIN Kala k ON k.ccBrand = js.ccNoeField \n" +
                    "WHERE js.NameNoeField=3 AND k.ccKalaCode= "+ ccKalaCode +" --cckalacode\n" +
                    "      AND ccNoeFieldMoshtary = "+ CcnoeMoshtary +" AND ccNoeSenf  IN ( 0 , " + CcNoeSenf +") AND darajeh IN (0, " + darajeh + ") \n" +
                    "      AND js.tedadJayezeh > 0 AND j.ccJayezeh = "+ ccJayezeh +" \n" +
                    "UNION ALL\n" +
                    "SELECT DISTINCT j.ccJayezeh, j.sharhjayezeh, j.NoeTedadRial, js.ccJayezehSatr, \n" +
                    "                CASE WHEN js.CodeNoeBastehBandy=1 THEN  (js.Az * k.TedadDarKarton)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=2 THEN  (js.Az * k.TedadDarBasteh)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=3 OR js.CodeNoeBastehBandy=4 THEN  js.Az END AS Az,\n" +
                    "                CASE WHEN js.CodeNoeBastehBandy=1 THEN  (js.Ta * k.TedadDarKarton)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=2 THEN  (js.Ta * k.TedadDarBasteh)\n" +
                    "                     WHEN js.CodeNoeBastehBandy=3 OR js.CodeNoeBastehBandy=4 THEN  js.Ta END AS Ta,\n" +
                    "                CASE WHEN js.CodeNoeBastehBandyBeEza=1 THEN  js.BeEza\n" +
                    "                     WHEN js.CodeNoeBastehBandyBeEza=2 THEN  js.BeEza\n" +
                    "                     WHEN js.CodeNoeBastehBandyBeEza=3 OR js.CodeNoeBastehBandyBeEza=4 THEN  js.BeEza END AS BeEza, \n" +
                    "                js.TedadJayezeh , js.codeNoeBastehbandy\n" +
                    "From jayezeh j\n" +
                    "     LEFT JOIN JayezehSatr js ON js.ccJayezeh = j.ccJayezeh\n" +
                    "     LEFT JOIN Kala k ON k.ccTaminKonandeh = js.ccNoeField \n" +
                    "WHERE js.NameNoeField=4 AND k.ccKalaCode= "+ ccKalaCode +" --cckalacode\n" +
                    "      AND ccNoeFieldMoshtary = "+ CcnoeMoshtary +" AND ccNoeSenf  IN ( 0 , " + CcNoeSenf +" ) AND Darajeh IN (0, " + darajeh +" ) \n" +
                    "      AND js.TedadJayezeh > 0 AND j.ccJayezeh = "+ ccJayezeh +" ";


            Cursor cursor = db.rawQuery(StrSQL, null);

            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehByccKalaCodeModels = cursorToModelJayezehByccKalaCode(cursor);
                    Log.d("JayezehDAO","jayezehModels:" + jayezehByccKalaCodeModels.toString());
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , JayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehDAO" , "" , "getByMoshtary" , "");
        }
        return jayezehByccKalaCodeModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(JayezehModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , JayezehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    private static ContentValues modelToContentvalue(JayezehModel jayezehModel)
    {
        ContentValues contentValues = new ContentValues();

        if (jayezehModel.getCcJayezeh() > 0)
        {
            contentValues.put(JayezehModel.COLUMN_ccJayezeh() , jayezehModel.getCcJayezeh());
        }
        contentValues.put(JayezehModel.COLUMN_CodeNoe() , jayezehModel.getCodeNoe());
        contentValues.put(JayezehModel.COLUMN_SharhJayezeh() , jayezehModel.getSharhJayezeh());
        contentValues.put(JayezehModel.COLUMN_NoeTedadRial() , jayezehModel.getNoeTedadRial());
        contentValues.put(JayezehModel.COLUMN_NameNoeFieldMoshtary() , jayezehModel.getNameNoeFieldMoshtary());
        contentValues.put(JayezehModel.COLUMN_ccNoeFieldMoshtary() , jayezehModel.getCcNoeFieldMoshtary());
        contentValues.put(JayezehModel.COLUMN_ccMarkazSazmanForosh() , jayezehModel.getCcMarkazSazmanForosh());
        contentValues.put(JayezehModel.COLUMN_CodeNoeHaml() , jayezehModel.getCodeNoeHaml());
        contentValues.put(JayezehModel.COLUMN_CodeNoeMohasebeh() , jayezehModel.getCodeNoeMohasebeh());
        contentValues.put(JayezehModel.COLUMN_Darajeh() , jayezehModel.getDarajeh());
        contentValues.put(JayezehModel.COLUMN_IsJayezehEntekhabi() , jayezehModel.getIsJayezehEntekhabi());
        contentValues.put(JayezehModel.COLUMN_ccNoeSenf() , jayezehModel.getCcNoeSenf());
        contentValues.put(JayezehModel.COLUMN_NameNoeSenf() , jayezehModel.getNameNoeSenf());

        return contentValues;
    }


    private ArrayList<JayezehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<JayezehModel> jayezehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            JayezehModel jayezehModel = new JayezehModel();

            jayezehModel.setCcJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_ccJayezeh())));
            jayezehModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_CodeNoe())));
            jayezehModel.setSharhJayezeh(cursor.getString(cursor.getColumnIndex(JayezehModel.COLUMN_SharhJayezeh())));
            jayezehModel.setNoeTedadRial(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_NoeTedadRial())));
            jayezehModel.setNameNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_NameNoeFieldMoshtary())));
            jayezehModel.setCcNoeFieldMoshtary(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_ccNoeFieldMoshtary())));
            jayezehModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_ccMarkazSazmanForosh())));
            jayezehModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_CodeNoeHaml())));
            jayezehModel.setCodeNoeMohasebeh(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_CodeNoeMohasebeh())));
            jayezehModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_Darajeh())));
            jayezehModel.setIsJayezehEntekhabi(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_IsJayezehEntekhabi())));
            jayezehModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(JayezehModel.COLUMN_ccNoeSenf())));
            jayezehModel.setNameNoeSenf(cursor.getString(cursor.getColumnIndex(JayezehModel.COLUMN_NameNoeSenf())));

            jayezehModels.add(jayezehModel);
            cursor.moveToNext();
        }
        return jayezehModels;
    }


    private ArrayList<JayezehByccKalaCodeModel> cursorToModelJayezehByccKalaCode(Cursor cursor)
    {
        ArrayList<JayezehByccKalaCodeModel> jayezehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            JayezehByccKalaCodeModel jayezehModel = new JayezehByccKalaCodeModel();

            jayezehModel.setCcJayezeh(cursor.getInt(cursor.getColumnIndex(jayezehModel.getCOLUMN_ccJayezeh())));
            jayezehModel.setSharhJayezeh(cursor.getString(cursor.getColumnIndex(jayezehModel.getCOLUMN_SharhJayezeh())));
            jayezehModel.setCcJayezehSatr(cursor.getInt(cursor.getColumnIndex(jayezehModel.getCOLUMN_ccJayezehSatr())));
            jayezehModel.setAz(cursor.getDouble(cursor.getColumnIndex(jayezehModel.getCOLUMN_Az())));
            jayezehModel.setTa(cursor.getDouble(cursor.getColumnIndex(jayezehModel.getCOLUMN_Ta())));
            jayezehModel.setTedadJayezeh(cursor.getInt(cursor.getColumnIndex(jayezehModel.getCOLUMN_TedadJayezeh())));
            jayezehModel.setBeEza(cursor.getInt(cursor.getColumnIndex(jayezehModel.getCOLUMN_BeEza())));
            jayezehModel.setNoeTedadRial(cursor.getInt(cursor.getColumnIndex(jayezehModel.getCOLUMN_NoeTedadRial())));
            jayezehModel.setCodeNoeBastehBandy(cursor.getInt(cursor.getColumnIndex(jayezehModel.getCOLUMN_CodeNoeBastehBandy())));


            jayezehModels.add(jayezehModel);
            cursor.moveToNext();
        }
        return jayezehModels;
    }

}
