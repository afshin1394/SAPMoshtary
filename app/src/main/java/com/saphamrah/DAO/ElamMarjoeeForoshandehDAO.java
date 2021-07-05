package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetElamMarjoeeForoshandehResult;
import com.saphamrah.WebService.ServiceResponse.GetTizeriResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElamMarjoeeForoshandehDAO {
    ElamMarjoeeForoshandehModel modelGetTABLE_NAME = new ElamMarjoeeForoshandehModel();
    private DBHelper dbHelper;

    /*
    create constructor
     */
    public ElamMarjoeeForoshandehDAO(Context context)
    {

        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ElamMarjoeeForoshandehDAO" , "" , "constructor" , "");
        }
    }

    /*
  fetch = request server and get result
   */
    public void fetchElamMarjoeeForoshandeh(final Context context, final String activityNameForLog , String ccDarkhastFaktors , final RetrofitResponse retrofitResponse)
    {
        Log.d("getProgram", "Marjoee Pakhsh: " + ccDarkhastFaktors);
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TizerDAO.class.getSimpleName(), activityNameForLog, "ElamMarjoeeForoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetElamMarjoeeForoshandehResult> call = apiServiceGet.getElamMarjoeeForoshandeh(ccDarkhastFaktors);
            call.enqueue(new Callback<GetElamMarjoeeForoshandehResult>()
            {
                @Override
                public void onResponse(Call<GetElamMarjoeeForoshandehResult> call, Response<GetElamMarjoeeForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TizerDAO.class.getSimpleName(), "", "fetchElamMarjoeeForoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetElamMarjoeeForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchElamMarjoeeForoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = "";
                                try
                                {
                                    endpoint = call.request().url().toString();
                                    endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                                }catch (Exception e){e.printStackTrace();}
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchElamMarjoeeForoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = "";
                            try
                            {
                                endpoint = call.request().url().toString();
                                endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                            }catch (Exception e){e.printStackTrace();}
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TizerDAO.class.getSimpleName(), activityNameForLog, "fetchElamMarjoeeForoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchElamMarjoeeForoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetElamMarjoeeForoshandehResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchElamMarjoeeForoshandeh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    /*
      get name all columns in model
       */
    private String[] allColumns()
    {
        return new String[]
                {
                        modelGetTABLE_NAME.getCOLUMN_ccElamMarjoeeSatr(),
                        modelGetTABLE_NAME.getCOLUMN_ccElamMarjoee(),
                        modelGetTABLE_NAME.getCOLUMN_ccMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_ccDarkhastFaktor(),
                        modelGetTABLE_NAME.getCOLUMN_ccKala(),
                        modelGetTABLE_NAME.getCOLUMN_ccKalaCode(),
                        modelGetTABLE_NAME.getCOLUMN_CodeKala(),
                        modelGetTABLE_NAME.getCOLUMN_NameKala(),
                        modelGetTABLE_NAME.getCOLUMN_ShomarehBach(),
                        modelGetTABLE_NAME.getCOLUMN_TarikhTolid(),
                        modelGetTABLE_NAME.getCOLUMN_TarikhTolidShamsi(),
                        modelGetTABLE_NAME.getCOLUMN_TarikhEngheza(),
                        modelGetTABLE_NAME.getCOLUMN_ccElatMarjoeeKala(),
                        modelGetTABLE_NAME.getCOLUMN_NameElatMarjoeeKala(),
                        modelGetTABLE_NAME.getCOLUMN_Tedad3(),
                        modelGetTABLE_NAME.getCOLUMN_ccTaminKonandeh(),
                        modelGetTABLE_NAME.getCOLUMN_NameMoshtary(),
                        modelGetTABLE_NAME.getCOLUMN_FullNameForoshandeh(),
                        modelGetTABLE_NAME.getCOLUMN_ShomarehFaktor(),
                        modelGetTABLE_NAME.getCOLUMN_ccAnbar(),
                        modelGetTABLE_NAME.getCOLUMN_ExtraProp_TedadNahaeeMarjoee(),
                        modelGetTABLE_NAME.getCOLUMN_GheymatKharid(),
                        modelGetTABLE_NAME.getCOLUMN_GheymatForosh(),
                        modelGetTABLE_NAME.getCOLUMN_GheymatForoshKhales(),
                        modelGetTABLE_NAME.getCOLUMN_GheymatMasrafKonandeh(),
                        modelGetTABLE_NAME.getCOLUMN_ccAnbarGhesmat(),
                        modelGetTABLE_NAME.getCOLUMN_GheymatForoshAsli(),
                        modelGetTABLE_NAME.getCOLUMN_ExtraProp_TedadMarjoee(),
                        modelGetTABLE_NAME.getCOLUMN_ccForoshandeh(),
                };
    }

    // get all data as db
    public ArrayList<ElamMarjoeeForoshandehModel> getAll()
    {
        ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeeForoshandehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ElamMarjoeeForoshandehDAO" , "" , "getAll" , "");
        }
        return elamMarjoeeForoshandehModels;
    }

    public ArrayList<ElamMarjoeeForoshandehModel> getMarjoeeByCcDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), "ccDarkhastFaktor=" + ccDarkhastFaktor , null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeeForoshandehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ElamMarjoeeForoshandehDAO" , "" , "getAll" , "");
        }
        return elamMarjoeeForoshandehModels;
    }

    public boolean updateTedadMarjoee(String ccDarkhastFaktor , String  ShomarehBach ,int ccTaminKonandeh ,float gheymatForosh  ,float gheymatMasrafKonandeh  , int ExtraProp_TedadMarjoee)
    { String query = "update " + modelGetTABLE_NAME.getTABLE_NAME() + " set " + modelGetTABLE_NAME.getCOLUMN_ExtraProp_TedadMarjoee() + " = " + ExtraProp_TedadMarjoee +
            " where " + modelGetTABLE_NAME.getCOLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " AND " + modelGetTABLE_NAME.getCOLUMN_ShomarehBach() + " = '" + ShomarehBach + "' AND "
            + modelGetTABLE_NAME.getCOLUMN_ccTaminKonandeh() + " = " + ccTaminKonandeh + " AND "
            + modelGetTABLE_NAME.getCOLUMN_GheymatForosh() + " = " + gheymatForosh + " AND " + modelGetTABLE_NAME.getCOLUMN_GheymatMasrafKonandeh() + " = " + gheymatMasrafKonandeh ;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);

            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorUpdate , modelGetTABLE_NAME.getTABLE_NAME() ) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ElamMarjoeeForoshandehDAO" , "" , "updateMablaghMarjoee" , "");
            return false;
        }
    }

    /*
    set result model to DB
     */
    public boolean insertGroup(ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ElamMarjoeeForoshandehModel model : elamMarjoeeForoshandehModels)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(modelGetTABLE_NAME.getTABLE_NAME() , null , contentValues);
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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorGroupInsert , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ElamMarjoeeForoshandehDAO" , "" , "insertGroup" , "");
            return false;
        }
    }




    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(modelGetTABLE_NAME.getTABLE_NAME(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = BaseApplication.getContext().getResources().getString(R.string.errorDeleteAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "ElamMarjoeeForoshandehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    private static ContentValues modelToContentvalue(ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel)
    {
        ContentValues contentValues = new ContentValues();
        ElamMarjoeeForoshandehModel model = new ElamMarjoeeForoshandehModel();
        contentValues.put(model.getCOLUMN_ccElamMarjoeeSatr() , elamMarjoeeForoshandehModel.getCcElamMarjoeeSatr());
        contentValues.put(model.getCOLUMN_ccElamMarjoee() , elamMarjoeeForoshandehModel.getCcElamMarjoee());
        contentValues.put(model.getCOLUMN_ccMoshtary() , elamMarjoeeForoshandehModel.getCcMoshtary());
        contentValues.put(model.getCOLUMN_ccDarkhastFaktor() , elamMarjoeeForoshandehModel.getCcDarkhastFaktor());
        contentValues.put(model.getCOLUMN_ccKala() , elamMarjoeeForoshandehModel.getCcKala());
        contentValues.put(model.getCOLUMN_ccKalaCode() , elamMarjoeeForoshandehModel.getCcKalaCode());
        contentValues.put(model.getCOLUMN_CodeKala() , elamMarjoeeForoshandehModel.getCodeKala());
        contentValues.put(model.getCOLUMN_NameKala() , elamMarjoeeForoshandehModel.getNameKala());
        contentValues.put(model.getCOLUMN_ShomarehBach(),elamMarjoeeForoshandehModel.getShomarehBach());
        contentValues.put(model.getCOLUMN_TarikhTolid() , elamMarjoeeForoshandehModel.getTarikhTolid());
        contentValues.put(model.getCOLUMN_TarikhTolidShamsi() , elamMarjoeeForoshandehModel.getTarikhTolidShamsi());
        contentValues.put(model.getCOLUMN_TarikhEngheza() , elamMarjoeeForoshandehModel.getTarikhEngheza());
        contentValues.put(model.getCOLUMN_ccElatMarjoeeKala() , elamMarjoeeForoshandehModel.getCcElatMarjoeeKala());
        contentValues.put(model.getCOLUMN_NameElatMarjoeeKala() , elamMarjoeeForoshandehModel.getNameElatMarjoeeKala());
        contentValues.put(model.getCOLUMN_Tedad3() , elamMarjoeeForoshandehModel.getTedad3());
        contentValues.put(model.getCOLUMN_ccTaminKonandeh() , elamMarjoeeForoshandehModel.getCcTaminKonandeh());
        contentValues.put(model.getCOLUMN_NameMoshtary() , elamMarjoeeForoshandehModel.getNameMoshtary());
        contentValues.put(model.getCOLUMN_FullNameForoshandeh(),elamMarjoeeForoshandehModel.getFullNameForoshandeh());
        contentValues.put(model.getCOLUMN_ShomarehFaktor(),elamMarjoeeForoshandehModel.getShomarehFaktor());
        contentValues.put(model.getCOLUMN_ccAnbar(),elamMarjoeeForoshandehModel.getCcAnbar());
        contentValues.put(model.getCOLUMN_ExtraProp_TedadNahaeeMarjoee(),elamMarjoeeForoshandehModel.getExtraProp_TedadNahaeeMarjoee());
        contentValues.put(model.getCOLUMN_GheymatKharid(),elamMarjoeeForoshandehModel.getGheymatKharid());
        contentValues.put(model.getCOLUMN_GheymatForosh(),elamMarjoeeForoshandehModel.getGheymatForosh());
        contentValues.put(model.getCOLUMN_GheymatForoshKhales(),elamMarjoeeForoshandehModel.getGheymatForoshKhales());
        contentValues.put(model.getCOLUMN_ccAnbarGhesmat(),elamMarjoeeForoshandehModel.getCcAnbarGhesmat());
        contentValues.put(model.getCOLUMN_GheymatMasrafKonandeh(),elamMarjoeeForoshandehModel.getGheymatMasrafKonandeh());
        contentValues.put(model.getCOLUMN_GheymatForoshAsli(), elamMarjoeeForoshandehModel.getGheymatForoshAsli());
        contentValues.put(model.getCOLUMN_ExtraProp_TedadMarjoee(), elamMarjoeeForoshandehModel.getExtraProp_TedadMarjoee());
        contentValues.put(model.getCOLUMN_ccForoshandeh(), elamMarjoeeForoshandehModel.getCcForoshandeh());



        return contentValues;
    }

    /*
   set  cursor to model in get All
    */
    private ArrayList<ElamMarjoeeForoshandehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ElamMarjoeeForoshandehModel model = new ElamMarjoeeForoshandehModel();
            model.setCcElamMarjoeeSatr(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccElamMarjoeeSatr())));
            model.setCcElamMarjoee(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccElamMarjoee())));
            model.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccMoshtary())));
            model.setCcDarkhastFaktor(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_ccDarkhastFaktor())));
            model.setCodeKala(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_CodeKala())));
            model.setCcKala(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccKala())));
            model.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccKalaCode())));
            model.setNameKala(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_NameKala())));
            model.setShomarehBach(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_ShomarehBach())));
            model.setTarikhTolid(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_TarikhTolid())));
            model.setTarikhTolidShamsi(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_TarikhTolidShamsi())));
            model.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_TarikhEngheza())));
            model.setCcElatMarjoeeKala(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccElatMarjoeeKala())));
            model.setNameElatMarjoeeKala(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_NameElatMarjoeeKala())));
            model.setTedad3(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_Tedad3())));
            model.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccTaminKonandeh())));
            model.setNameMoshtary(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_NameMoshtary())));
            model.setFullNameForoshandeh(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_FullNameForoshandeh())));
            model.setShomarehFaktor(cursor.getString(cursor.getColumnIndex(model.getCOLUMN_ShomarehFaktor())));
            model.setCcAnbar(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccAnbar())));
            model.setExtraProp_TedadNahaeeMarjoee(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ExtraProp_TedadNahaeeMarjoee())));
            model.setGheymatKharid(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_GheymatKharid())));
            model.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_GheymatForosh())));
            model.setGheymatForoshKhales(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_GheymatForoshKhales())));
            model.setCcAnbarGhesmat(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccAnbarGhesmat())));
            model.setGheymatMasrafKonandeh(cursor.getFloat(cursor.getColumnIndex(model.getCOLUMN_GheymatMasrafKonandeh())));
            model.setGheymatForoshAsli(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_GheymatForoshAsli())));
            model.setExtraProp_TedadMarjoee(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ExtraProp_TedadMarjoee())));
            model.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(model.getCOLUMN_ccForoshandeh())));

            elamMarjoeeForoshandehModels.add(model);
            cursor.moveToNext();
        }
        return elamMarjoeeForoshandehModels;
    }
}
