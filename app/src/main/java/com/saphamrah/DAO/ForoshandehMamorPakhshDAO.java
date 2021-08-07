package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetForoshandehMamorPakhshResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForoshandehMamorPakhshDAO
{

    private DBHelper dbHelper;
    private Context context;



    public ForoshandehMamorPakhshDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            /*PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMamorPakhshDAO" , "" , "constructor" , "");*/
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            ForoshandehMamorPakhshModel.COLUMN_ccForoshandeh(),
            ForoshandehMamorPakhshModel.COLUMN_ccMamorPakhsh(),
            ForoshandehMamorPakhshModel.COLUMN_NoeMasouliat(),
            ForoshandehMamorPakhshModel.COLUMN_ccAfrad(),
            ForoshandehMamorPakhshModel.COLUMN_ccAmargar(),
            ForoshandehMamorPakhshModel.COLUMN_ccAfradModir(),
            ForoshandehMamorPakhshModel.COLUMN_CodeForoshandeh(),
            ForoshandehMamorPakhshModel.COLUMN_FullName(),
            ForoshandehMamorPakhshModel.COLUMN_DeviceSerialNumber(),
            ForoshandehMamorPakhshModel.COLUMN_CanGetProgram(),
            ForoshandehMamorPakhshModel.COLUMN_CanSetFaktorKharejAzMahal(),
            ForoshandehMamorPakhshModel.COLUMN_CanGetDarkhastTelephoni(),
            ForoshandehMamorPakhshModel.COLUMN_CanGetPhotoChidman(),
            ForoshandehMamorPakhshModel.COLUMN_CanChangeMoshtaryPosition(),
            ForoshandehMamorPakhshModel.COLUMN_NoeForoshandehMamorPakhsh(),
            ForoshandehMamorPakhshModel.COLUMN_ccMarkazForosh(),
            ForoshandehMamorPakhshModel.COLUMN_ccMarkazAnbar(),
            ForoshandehMamorPakhshModel.COLUMN_ccMarkazAnbarVosol(),
            ForoshandehMamorPakhshModel.COLUMN_NameMarkazForosh(),
            ForoshandehMamorPakhshModel.COLUMN_NameMarkazAnbar(),
            ForoshandehMamorPakhshModel.COLUMN_MaxTedadCheckBargashty(),
            ForoshandehMamorPakhshModel.COLUMN_MaxMablaghCheckBargashty(),
            ForoshandehMamorPakhshModel.COLUMN_MaxModatCheckBargashty(),
            ForoshandehMamorPakhshModel.COLUMN_Max_ResidCheck(),
            ForoshandehMamorPakhshModel.COLUMN_Max_ResidNaghd(),
            ForoshandehMamorPakhshModel.COLUMN_ExtraProp_IsSelect(),
            ForoshandehMamorPakhshModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh(),
            ForoshandehMamorPakhshModel.COLUMN_NameSazmanForosh(),
            ForoshandehMamorPakhshModel.COLUMN_TelephoneShobeh(),
            ForoshandehMamorPakhshModel.COLUMN_TelephoneForoshandehMamorPakhsh(),
            ForoshandehMamorPakhshModel.COLUMN_PosNumber(),
            ForoshandehMamorPakhshModel.COLUMN_ccPosShomarehHesab(),
            ForoshandehMamorPakhshModel.COLUMN_ccMarkaz(),
            ForoshandehMamorPakhshModel.COLUMN_IsMojazForSabtDarkhast(),
            ForoshandehMamorPakhshModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            ForoshandehMamorPakhshModel.COLUMN_ccMarkazSazmanForosh(),
            ForoshandehMamorPakhshModel.COLUMN_ccSazmanForosh(),
            ForoshandehMamorPakhshModel.COLUMN_CanGetMarjoee(),
            ForoshandehMamorPakhshModel.COLUMN_NoeSabtMarjoee(),
			ForoshandehMamorPakhshModel.COLUMN_ccDarkhastFaktorNoeForosh(),																		   
            ForoshandehMamorPakhshModel.COLUMN_ccMashin(),
            ForoshandehMamorPakhshModel.COLUMN_flag_SabtNaghd(),
            ForoshandehMamorPakhshModel.COLUMN_ccForoshandehs(),
            ForoshandehMamorPakhshModel.COLUMN_ccAnbar(),
            ForoshandehMamorPakhshModel.COLUMN_ccAnbarak(),
			ForoshandehMamorPakhshModel.COLUMN_CheckOlaviatMoshtary(),
			ForoshandehMamorPakhshModel.COLUMN_FromDateKharejAzMahal(),
			ForoshandehMamorPakhshModel.COLUMN_EndDateKharejAzMahal(),
			ForoshandehMamorPakhshModel.COLUMN_ccAnbarMarjoee(),
			ForoshandehMamorPakhshModel.COLUMN_CodeNoeAnbarMarjoee()

        };
    }


    public void fetchForoshandehMamorPakhshForUpdate(final Context context,final String activityNameForLog, String imei, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandehMamorPakhshForUpdate", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetForoshandehMamorPakhshResult> call = apiServiceGet.getForoshandehMamorPakhsh(imei);
            call.enqueue(new Callback<GetForoshandehMamorPakhshResult>() {
                @Override
                public void onResponse(Call<GetForoshandehMamorPakhshResult> call, Response<GetForoshandehMamorPakhshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ForoshandehMamorPakhshDAO.class.getSimpleName(), "", "fetchForoshandehMamorPakhshForUpdate", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}

                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetForoshandehMamorPakhshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    ArrayList<Object> arrayList = new ArrayList<>();
                                    for (ForoshandehMamorPakhshModel item : result.getData())
                                    {
                                        arrayList.add((Object)item);
                                        Log.d("ForoshandehMamaorPakhsh","arrayList:"+arrayList.toString());
                                    }
                                    retrofitResponse.onSuccess(arrayList);

                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ForoshandehMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandehMamorPakhshForUpdate", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), ForoshandehMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandehMamorPakhshForUpdate", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandehMamorPakhshForUpdate", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ForoshandehMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandehMamorPakhshForUpdate", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetForoshandehMamorPakhshResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ForoshandehMamorPakhshDAO.class.getSimpleName(), activityNameForLog, "fetchForoshandehMamorPakhshForUpdate", "onFailure");
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

    public boolean insert(ForoshandehMamorPakhshModel foroshandehMamorPakhsh)
    {
        ContentValues contentValues = modelToContentValue(foroshandehMamorPakhsh);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(ForoshandehMamorPakhshModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , ForoshandehMamorPakhshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ForoshandehMamorPakhshDAO" , "" , "insert" , "");
            return false;
        }
    }


    public boolean insertGroup(ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ForoshandehMamorPakhshModel foroshandehMamorPakhshModel : foroshandehMamorPakhshModels)
            {
                ContentValues contentValues = modelToContentValue(foroshandehMamorPakhshModel);
                db.insertOrThrow(ForoshandehMamorPakhshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ForoshandehMamorPakhshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehMamorPakhshDAO.class.getSimpleName() , "" , "insertGroup" , "");
            return false;
        }
    }
    public boolean insertGroup(List<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ForoshandehMamorPakhshModel foroshandehMamorPakhshModel : foroshandehMamorPakhshModels)
            {
                ContentValues contentValues = modelToContentValue(foroshandehMamorPakhshModel);
                db.insertOrThrow(ForoshandehMamorPakhshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ForoshandehMamorPakhshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehMamorPakhshDAO.class.getSimpleName() , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ForoshandehMamorPakhshModel> getAll()
    {
        ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehMamorPakhshModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehMamorPakhshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehMamorPakhshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ForoshandehMamorPakhshDAO.class.getSimpleName() , "" , "getAll" , "");
        }
        return foroshandehMamorPakhshModels;
    }

    public int getCCAfrad()
    {
        //String query = "select " + COLUMN_ccAfrad + " from " + ForoshandehMamorPakhshModel.TableName() + " limit 1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehMamorPakhshModel.TableName(),new String[]{ForoshandehMamorPakhshModel.COLUMN_ccAfrad()},null,null,null,null,null,"1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    int ccAfrad = cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccAfrad()));
                    cursor.close();
                    db.close();
                    return ccAfrad;
                }
                else
                {
                    db.close();
                    cursor.close();
                    return 0;
                }
            }
            else
            {
                db.close();
                return 0;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
          //  PubFunc.Logger logger = new PubFunc().new Logger();
            //logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMamorPakhshDAO" , "" , "getCCAfrad" , "");
            return 0;
        }
    }

//    public ForoshandehMamorPakhshModel getForoshandehMamorPakhsh()
//    {
//        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = null;
//        String query = "select * from " + ForoshandehMamorPakhshModel.TableName() + " limit 1";
//        try
//        {
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursor = db.rawQuery(query , null);
//            if (cursor != null)
//            {
//                if (cursor.getCount() > 0)
//                {
//                    foroshandehMamorPakhshModel = cursorToModel(cursor).get(0);
//                }
//                cursor.close();
//            }
//            db.close();
//        }
//        catch (Exception exception)
//        {
//            exception.printStackTrace();
//            PubFunc.Logger logger = new PubFunc().new Logger();
//            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMamorPakhshDAO" , "" , "getForoshandehMamorPakhsh" , "");
//        }
//        return foroshandehMamorPakhshModel;
//    }

    public ForoshandehMamorPakhshModel getSplash()
    {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = null;
        String query = "select * from " + ForoshandehMamorPakhshModel.TableName() + " limit 1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehMamorPakhshModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMamorPakhshDAO" , "" , "getOne" , "");
        }
        return foroshandehMamorPakhshModel;
    }

    public ForoshandehMamorPakhshModel getByccForoshandeh(int ccForoshandeh)
    {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = null;
        String query = "select * from " + ForoshandehMamorPakhshModel.TableName() + " where " + ForoshandehMamorPakhshModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                foroshandehMamorPakhshModel = cursorToModel(cursor).get(0);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMamorPakhshDAO" , "" , "getByccForoshandeh" , "");
        }
        return foroshandehMamorPakhshModel;
    }

    public ForoshandehMamorPakhshModel getIsSelect()
    {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = null;
        String query = "select * from " + ForoshandehMamorPakhshModel.TableName() + " where " + ForoshandehMamorPakhshModel.COLUMN_ExtraProp_IsSelect() + " = 1 " ;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                foroshandehMamorPakhshModel = cursorToModel(cursor).get(0);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMamorPakhshDAO" , "" , "getByccForoshandeh" , "");
        }
        return foroshandehMamorPakhshModel;
    }




    public void updateCanSetFaktorKharejAzMahal(int ccForoshandeh)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ForoshandehMamorPakhshModel.COLUMN_CanSetFaktorKharejAzMahal(), 1);
            db.update(ForoshandehMamorPakhshModel.TableName(), values, ForoshandehMamorPakhshModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh, null);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMamorPakhshDAO" , "" , "updateCanSetFaktorKharejAzMahal" , "");
        }
    }

    public void updateIsSelect(int ccForoshandeh)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ForoshandehMamorPakhshModel.COLUMN_ExtraProp_IsSelect(), 1);
            db.update(ForoshandehMamorPakhshModel.TableName(), values, ForoshandehMamorPakhshModel.COLUMN_ccForoshandeh() + " = " + ccForoshandeh, null);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehMamorPakhshDAO" , "" , "updateCanSetFaktorKharejAzMahal" , "");
        }
    }

    public boolean deleteAll()
    {
        String query = "delete from " + ForoshandehMamorPakhshModel.TableName();
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
            String message = context.getResources().getString(R.string.errorDeleteAll , ForoshandehMamorPakhshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ForoshandehMamorPakhshDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    private ArrayList<ForoshandehMamorPakhshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels = new ArrayList<>();

        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshModel();

                foroshandehMamorPakhshModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccForoshandeh())));
                foroshandehMamorPakhshModel.setCcAfrad(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccAfrad())));
                foroshandehMamorPakhshModel.setCcAmargar(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccAmargar())));
                foroshandehMamorPakhshModel.setCcMamorPakhsh(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccMamorPakhsh())));
                foroshandehMamorPakhshModel.setCcAfradModir(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccAfradModir())));
                foroshandehMamorPakhshModel.setNoeMasouliat(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_NoeMasouliat())));
                foroshandehMamorPakhshModel.setCodeForoshandeh(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CodeForoshandeh())));
                foroshandehMamorPakhshModel.setFullName(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_FullName())));
                foroshandehMamorPakhshModel.setDeviceSerialNumber(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_DeviceSerialNumber())));
                foroshandehMamorPakhshModel.setCanGetProgram(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CanGetProgram())));
                foroshandehMamorPakhshModel.setCanSetFaktorKharejAzMahal(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CanSetFaktorKharejAzMahal())));
                foroshandehMamorPakhshModel.setCanGetDarkhastTelephoni(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CanGetDarkhastTelephoni())));
                foroshandehMamorPakhshModel.setCanGetPhotoChidman(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CanGetPhotoChidman())));
                foroshandehMamorPakhshModel.setCanChangeMoshtaryPosition(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CanChangeMoshtaryPosition())));
                foroshandehMamorPakhshModel.setNoeForoshandehMamorPakhsh(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_NoeForoshandehMamorPakhsh())));
                foroshandehMamorPakhshModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccMarkazForosh())));
                foroshandehMamorPakhshModel.setCcMarkazAnbar(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccMarkazAnbar())));
                foroshandehMamorPakhshModel.setCcMarkazAnbarVosol(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccMarkazAnbarVosol())));
                foroshandehMamorPakhshModel.setNameMarkazForosh(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_NameMarkazForosh())));
                foroshandehMamorPakhshModel.setNameMarkazAnbar(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_NameMarkazAnbar())));
                foroshandehMamorPakhshModel.setMaxTedadCheckBargashty(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_MaxTedadCheckBargashty())));
                foroshandehMamorPakhshModel.setMaxMablaghCheckBargashty(cursor.getLong(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_MaxMablaghCheckBargashty())));
                foroshandehMamorPakhshModel.setMaxModatCheckBargashty(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_MaxModatCheckBargashty())));
                foroshandehMamorPakhshModel.setMaxResidCheck(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_Max_ResidCheck())));
                foroshandehMamorPakhshModel.setMaxResidNaghd(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_Max_ResidNaghd())));
                //foroshandehMamorPakhshModel.setExtraPropIsSelect(cursor.getInt(cursor.getColumnIndex(COLUMN_EXTRA_PROP_IS_SELECT)));
                foroshandehMamorPakhshModel.setNameMarkazSazmanForoshSakhtarForosh(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh())));
                foroshandehMamorPakhshModel.setNameSazmanForosh(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_NameSazmanForosh())));
                foroshandehMamorPakhshModel.setTelephoneShobeh(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_TelephoneShobeh())));
                foroshandehMamorPakhshModel.setTelephoneForoshandehMamorPakhsh(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_TelephoneForoshandehMamorPakhsh())));
                foroshandehMamorPakhshModel.setPosNumber(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_PosNumber())));
                foroshandehMamorPakhshModel.setCcPosShomarehHesab(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccPosShomarehHesab())));
                foroshandehMamorPakhshModel.setCcMarkaz(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccMarkaz())));
                foroshandehMamorPakhshModel.setIsMojazForSabtDarkhast(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_IsMojazForSabtDarkhast())));
                foroshandehMamorPakhshModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));
                foroshandehMamorPakhshModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccMarkazSazmanForosh())));
                foroshandehMamorPakhshModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccSazmanForosh())));
                foroshandehMamorPakhshModel.setCanGetMarjoee(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CanGetMarjoee())));
                foroshandehMamorPakhshModel.setNoeSabtMarjoee(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_NoeSabtMarjoee())));
				foroshandehMamorPakhshModel.setCcDarkhastFaktorNoeForosh(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccDarkhastFaktorNoeForosh())));																																											   
                foroshandehMamorPakhshModel.setCcMashin(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccMashin())));
                foroshandehMamorPakhshModel.setFlagSabtNaghd(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_flag_SabtNaghd())));
                foroshandehMamorPakhshModel.setCcForoshandehs(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccForoshandehs())));
                foroshandehMamorPakhshModel.setCcAnbar(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccAnbar())));
                foroshandehMamorPakhshModel.setCcAnbarak(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccAnbarak())));
				foroshandehMamorPakhshModel.setCheckOlaviatMoshtary(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CheckOlaviatMoshtary())));																																									 
				foroshandehMamorPakhshModel.setFromDateKharejAzMahal(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_FromDateKharejAzMahal())));
				foroshandehMamorPakhshModel.setEndDateKharejAzMahal(cursor.getString(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_EndDateKharejAzMahal())));
				foroshandehMamorPakhshModel.setCcAnbarMarjoee(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_ccAnbarMarjoee())));
				foroshandehMamorPakhshModel.setCodeNoeAnbarMarjoee(cursor.getInt(cursor.getColumnIndex(ForoshandehMamorPakhshModel.COLUMN_CodeNoeAnbarMarjoee())));

                foroshandehMamorPakhshModels.add(foroshandehMamorPakhshModel);
                cursor.moveToNext();
            }
        }
        return foroshandehMamorPakhshModels;
    }


    private ContentValues modelToContentValue(ForoshandehMamorPakhshModel model)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccForoshandeh() , model.getCcForoshandeh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccMamorPakhsh() , model.getCcMamorPakhsh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_NoeMasouliat() , model.getNoeMasouliat());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccAfrad() , model.getCcAfrad());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccAmargar() , model.getCcAmargar());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccAfradModir() , model.getCcAfradModir());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CodeForoshandeh() , model.getCodeForoshandeh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_FullName() , model.getFullName());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_DeviceSerialNumber() , model.getDeviceSerialNumber());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CanGetProgram() , model.getCanGetProgram());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CanSetFaktorKharejAzMahal() , model.getCanSetFaktorKharejAzMahal());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CanGetDarkhastTelephoni() , model.getCanGetDarkhastTelephoni());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CanGetPhotoChidman() , model.getCanGetPhotoChidman());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CanChangeMoshtaryPosition() , model.getCanChangeMoshtaryPosition());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_NoeForoshandehMamorPakhsh() , model.getNoeForoshandehMamorPakhsh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccMarkazForosh() , model.getCcMarkazForosh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccMarkazAnbar() , model.getCcMarkazAnbar());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccMarkazAnbarVosol() , model.getCcMarkazAnbarVosol());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_NameMarkazForosh() , model.getNameMarkazForosh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_NameMarkazAnbar() , model.getNameMarkazAnbar());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_MaxTedadCheckBargashty() , model.getMaxTedadCheckBargashty());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_MaxMablaghCheckBargashty() , model.getMaxMablaghCheckBargashty());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_MaxModatCheckBargashty() , model.getMaxModatCheckBargashty());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_Max_ResidCheck() , model.getMaxResidCheck());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_Max_ResidNaghd() , model.getMaxResidNaghd());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ExtraProp_IsSelect() , model.getExtraPropIsSelect());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh() , model.getNameMarkazSazmanForoshSakhtarForosh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_NameSazmanForosh() , model.getNameSazmanForosh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_TelephoneShobeh() , model.getTelephoneShobeh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_TelephoneForoshandehMamorPakhsh() , model.getTelephoneForoshandehMamorPakhsh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccPosShomarehHesab() , model.getCcPosShomarehHesab());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccMarkaz() , model.getCcMarkaz());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_IsMojazForSabtDarkhast() , model.getIsMojazForSabtDarkhast());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , model.getCcMarkazSazmanForoshSakhtarForosh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccMarkazSazmanForosh() , model.getCcMarkazSazmanForosh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccSazmanForosh() , model.getCcSazmanForosh());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CanGetMarjoee() , model.getCanGetMarjoee());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_NoeSabtMarjoee() , model.getNoeSabtMarjoee());
		contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccDarkhastFaktorNoeForosh() , model.getCcDarkhastFaktorNoeForosh());																																 
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccMashin() , model.getCcMashin());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_flag_SabtNaghd() , model.getFlagSabtNaghd());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccForoshandehs() , model.getCcForoshandehs());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccAnbar() , model.getCcAnbar());
        contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccAnbarak() , model.getCcAnbarak());
		contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CheckOlaviatMoshtary() , model.getCheckOlaviatMoshtary());																													   
		contentValues.put(ForoshandehMamorPakhshModel.COLUMN_FromDateKharejAzMahal() , model.getFromDateKharejAzMahal());
		contentValues.put(ForoshandehMamorPakhshModel.COLUMN_EndDateKharejAzMahal() , model.getEndDateKharejAzMahal());
		contentValues.put(ForoshandehMamorPakhshModel.COLUMN_ccAnbarMarjoee() , model.getCcAnbarMarjoee());
		contentValues.put(ForoshandehMamorPakhshModel.COLUMN_CodeNoeAnbarMarjoee() , model.getCodeNoeAnbarMarjoee());

        return contentValues;
    }

}
