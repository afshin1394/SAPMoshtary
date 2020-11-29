package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TaghvimTatilModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetAllTaghvimTatilByccMarkazResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaghvimTatilDAO 
{

    private DBHelper dbHelper;
    private Context context;
    private final String CLASS_NAME = "TaghvimTatilDAO";


    public TaghvimTatilDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
                {
                        TaghvimTatilModel.COLUMN_ccTaghvimTatil(),
                        TaghvimTatilModel.COLUMN_ccMarkaz(),
                        TaghvimTatilModel.COLUMN_TarikhTatily()
                };
    }

    public void fetchTaghvimTatil(final Context context, final String activityNameForLog,final String ccMarkaz, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TaghvimTatilDAO.class.getSimpleName(), activityNameForLog, "fetchTaghvimTatil", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIpModel.getServerIp() , serverIpModel.getPort()).create(APIService.class);
            Call<GetAllTaghvimTatilByccMarkazResult> call = apiService.getAllTaghvimTatilByccMarkaz(ccMarkaz);
            call.enqueue(new Callback<GetAllTaghvimTatilByccMarkazResult>() {
                @Override
                public void onResponse(Call<GetAllTaghvimTatilByccMarkazResult> call, Response<GetAllTaghvimTatilByccMarkazResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TaghvimTatilDAO.class.getSimpleName(), "", "fetchTaghvimTatil", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllTaghvimTatilByccMarkazResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TaghvimTatilDAO.class.getSimpleName(), activityNameForLog, "fetchTaghvimTatil", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull), endpoint), TaghvimTatilDAO.class.getSimpleName(), activityNameForLog, "fetchTaghvimTatil", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TaghvimTatilDAO.class.getSimpleName(), activityNameForLog, "fetchTaghvimTatil", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TaghvimTatilDAO.class.getSimpleName(), activityNameForLog, "fetchTaghvimTatil", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllTaghvimTatilByccMarkazResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TaghvimTatilDAO.class.getSimpleName(), activityNameForLog, "fetchTaghvimTatil", "onFailure");
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

    public boolean insertGroup(ArrayList<TaghvimTatilModel> taghvimTatilModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TaghvimTatilModel taghvimTatilModel : taghvimTatilModels)
            {
                ContentValues contentValues = modelToContentvalue(taghvimTatilModel);
                db.insertOrThrow(TaghvimTatilModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TaghvimTatilModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<TaghvimTatilModel> getAll()
    {
        ArrayList<TaghvimTatilModel> taghvimTatilModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TaghvimTatilModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    taghvimTatilModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TaghvimTatilModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return taghvimTatilModels;
    }

    public List<TaghvimTatilModel> getFromNow()
    {
        List<TaghvimTatilModel> taghvimTatilModels = new ArrayList<>();
        try
        {
            //query = select * from TaghvimTatil where TarikhTatily >= date('now') and ccMarkaz =
            String query = "select * from " + TaghvimTatilModel.TableName() + " where " + TaghvimTatilModel.COLUMN_TarikhTatily() + " >= date('now') " ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    taghvimTatilModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TaghvimTatilModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getFromNowByccMarkaz" , "");
        }
        return taghvimTatilModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TaghvimTatilModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , TaghvimTatilModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TaghvimTatilModel taghvimTatilModel)
    {
        ContentValues contentValues = new ContentValues();

        if (taghvimTatilModel.getCcTaghvimTatil() > 0)
        {
            contentValues.put(TaghvimTatilModel.COLUMN_ccTaghvimTatil() , taghvimTatilModel.getCcTaghvimTatil());
        }
        contentValues.put(TaghvimTatilModel.COLUMN_ccMarkaz() , taghvimTatilModel.getCcMarkaz());
        contentValues.put(TaghvimTatilModel.COLUMN_TarikhTatily() , taghvimTatilModel.getTarikhTatily());

        return contentValues;
    }


    private ArrayList<TaghvimTatilModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TaghvimTatilModel> taghvimTatilModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TaghvimTatilModel taghvimTatilModel = new TaghvimTatilModel();

            taghvimTatilModel.setCcTaghvimTatil(cursor.getInt(cursor.getColumnIndex(TaghvimTatilModel.COLUMN_ccTaghvimTatil())));
            taghvimTatilModel.setCcMarkaz(cursor.getInt(cursor.getColumnIndex(TaghvimTatilModel.COLUMN_ccMarkaz())));
            taghvimTatilModel.setTarikhTatily(cursor.getString(cursor.getColumnIndex(TaghvimTatilModel.COLUMN_TarikhTatily())));

            taghvimTatilModels.add(taghvimTatilModel);
            cursor.moveToNext();
        }
        return taghvimTatilModels;
    }



}
