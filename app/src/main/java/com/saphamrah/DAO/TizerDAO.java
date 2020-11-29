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
import android.util.Base64;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetTizeriResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TizerDAO
{

    TizerModel modelGetTABLE_NAME = new TizerModel();
    private DBHelper dbHelper;




    /*
    create constructor
     */
    public TizerDAO(Context context)
    {

        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TizerDAO" , "" , "constructor" , "");
        }
    }

    /*
    fetch = request server and get result
     */
    public void fetchTizer(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TizerDAO.class.getSimpleName(), activityNameForLog, "fetchTizer", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);

        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetTizeriResult> call = apiService.getAllTizer();
            call.enqueue(new Callback<GetTizeriResult>()
            {
                @Override
                public void onResponse(Call<GetTizeriResult> call, Response<GetTizeriResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TizerDAO.class.getSimpleName(), "", "fetchTizer", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetTizeriResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchTizer", "onResponse");
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
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchTizer", "onResponse");
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
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TizerDAO.class.getSimpleName(), activityNameForLog, "fetchTizer", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchTizer", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetTizeriResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TizerDAO.class.getSimpleName(), activityNameForLog, "fetchTizer", "onFailure");
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
                modelGetTABLE_NAME.getCOLUMN_ccTizer(),
                modelGetTABLE_NAME.getCOLUMN_NameTizer(),
                modelGetTABLE_NAME.getCOLUMN_NameTizer_Farsi(),
                modelGetTABLE_NAME.getCOLUMN_NameFolder(),
                modelGetTABLE_NAME.getCOLUMN_Image(),
        };
    }

    /*
    set result model to DB
     */
    public boolean insertGroup(ArrayList<TizerModel> tizerModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TizerModel tizerModel : tizerModels)
            {
                ContentValues contentValues = modelToContentvalue(tizerModel);
                db.insertOrThrow(tizerModel.getTABLE_NAME() , null , contentValues);

                Log.i("TizerDao" , contentValues.toString());
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "TizerDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    // get all data as db
    public ArrayList<TizerModel> getAll()
    {
        ArrayList<TizerModel> tizerModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    tizerModels = cursorToModel(cursor);
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "TizerDAO" , "" , "getAll" , "");
        }
        return tizerModels;
    }

    public ArrayList<String> getFolder()
    {
        ArrayList<String> tizerResultModels = new ArrayList<>();
        try
        {


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " select distinct NameFolder from Tizer ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()){
                        tizerResultModels.add(cursor.getString(0));
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
            String message = BaseApplication.getContext().getResources().getString(R.string.errorSelectAll , modelGetTABLE_NAME.getTABLE_NAME()) + "\n" + exception.toString();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "TizerDAO" , "" , "getAll" , "");
        }
        return tizerResultModels;
    }

    public ArrayList<TizerModel> getFile(String folderName)
    {
        ArrayList<TizerModel> tizerModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(modelGetTABLE_NAME.getTABLE_NAME(), allColumns(), "NameFolder = '" +  folderName + "'", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    tizerModels = cursorToModel(cursor);
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "TizerDAO" , "" , "getAll" , "");
        }
        return tizerModels;
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
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), message, "TizerDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TizerModel tizerModel)
    {
        ContentValues contentValues = new ContentValues();
        TizerModel tizerModel1 = new TizerModel();
        contentValues.put(tizerModel1.getCOLUMN_ccTizer() , tizerModel.getCcTizer());
        contentValues.put(tizerModel1.getCOLUMN_NameTizer() , tizerModel.getNameTizer());
        contentValues.put(tizerModel1.getCOLUMN_NameTizer_Farsi() ,tizerModel.getNameTizer_Farsi());
        contentValues.put(tizerModel1.getCOLUMN_NameFolder() , tizerModel.getNameFolder());
        /*
        Image when is null, convert image in drawable to byte and set in table
         */
        if (tizerModel.getImage()!= null){
            contentValues.put(tizerModel1.getCOLUMN_Image() , tizerModel.getImage());
        } else {
            Resources res = BaseApplication.getContext().getResources();
            Drawable drawable = res.getDrawable(R.drawable.nopicfolder);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitMapData = stream.toByteArray();



            contentValues.put(tizerModel1.getCOLUMN_Image() , bitMapData);
        }



        return contentValues;
    }


    /*
    set  cursor to model in get All
     */
    private ArrayList<TizerModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TizerModel> tizerModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TizerModel tizerModel = new TizerModel();
            tizerModel.setCcTizer(cursor.getInt(cursor.getColumnIndex(tizerModel.getCOLUMN_ccTizer())));
            tizerModel.setNameTizer(cursor.getString(cursor.getColumnIndex(tizerModel.getCOLUMN_NameTizer())));
            tizerModel.setNameTizer_Farsi(cursor.getString(cursor.getColumnIndex(tizerModel.getCOLUMN_NameTizer_Farsi())));
            tizerModel.setNameFolder(cursor.getString(cursor.getColumnIndex(tizerModel.getCOLUMN_NameFolder())));
            tizerModel.setImage(cursor.getBlob(cursor.getColumnIndex(tizerModel.getCOLUMN_Image())));
            tizerModels.add(tizerModel);
            cursor.moveToNext();
        }
        return tizerModels;
    }



}
