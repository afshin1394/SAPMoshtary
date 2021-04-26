package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.CodeNoeVosolModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetCodeNoeVosolResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeNoeVosolDAO
{

    private DBHelper dbHelper;
    private Context context;


    public CodeNoeVosolDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CodeNoeVosolDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            CodeNoeVosolModel.COLUMN_CodeNoeVosol(),
            CodeNoeVosolModel.COLUMN_txtNoeVosol(),
            CodeNoeVosolModel.COLUMN_ViewInPPC(),
            CodeNoeVosolModel.COLUMN_CodeNoeSanad_dp(),
            CodeNoeVosolModel.COLUMN_CodeNoeCheck_dp()
        };
    }

    public void fetchCodeNoeVosol(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CodeNoeVosolDAO.class.getSimpleName(), activityNameForLog, "fetchCodeNoeVosol", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetCodeNoeVosolResult> call = apiServiceGet.getCodeNoeVosol();
            call.enqueue(new Callback<GetCodeNoeVosolResult>() {
                @Override
                public void onResponse(Call<GetCodeNoeVosolResult> call, Response<GetCodeNoeVosolResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CodeNoeVosolDAO.class.getSimpleName(), "", "fetchCodeNoeVosol", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetCodeNoeVosolResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CodeNoeVosolDAO.class.getSimpleName(), activityNameForLog, "fetchCodeNoeVosol", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CodeNoeVosolDAO.class.getSimpleName(), activityNameForLog, "fetchCodeNoeVosol", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CodeNoeVosolDAO.class.getSimpleName(), activityNameForLog, "fetchCodeNoeVosol", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CodeNoeVosolDAO.class.getSimpleName(), activityNameForLog, "fetchCodeNoeVosol", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetCodeNoeVosolResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CodeNoeVosolDAO.class.getSimpleName(), activityNameForLog, "fetchCodeNoeVosol", "onFailure");
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

    public boolean insertGroup(ArrayList<CodeNoeVosolModel> codeNoeVosolModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (CodeNoeVosolModel codeNoeVosolModel : codeNoeVosolModels)
            {
                ContentValues contentValues = modelToContentvalue(codeNoeVosolModel);
                db.insertOrThrow(CodeNoeVosolModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , CodeNoeVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CodeNoeVosolDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<CodeNoeVosolModel> getAll()
    {
        ArrayList<CodeNoeVosolModel> codeNoeVosolModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(CodeNoeVosolModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    codeNoeVosolModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , CodeNoeVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CodeNoeVosolDAO" , "" , "getAll" , "");
        }
        return codeNoeVosolModels;
    }

    public int GetCodeNoeVosolByCodeNoeSanad(String CodeNoeSanad, int CodeNoeCheck)
    {
        int CodeNoeVosool = 0;

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(CodeNoeVosolModel.TableName(), allColumns(), " CodeNoeSanad_dp= " + CodeNoeSanad + " AND CodeNoeCheck_dp = " + CodeNoeCheck,null, null, null,  null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast())
            {

                ArrayList<CodeNoeVosolModel> entity = cursorToModel(cursor);
                CodeNoeVosool = entity.get(0).getCodeNoeVosol();
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return CodeNoeVosool;
    }

    public CodeNoeVosolModel getByCodeNoeVosol(int codeNoeVosolMoshtary)
    {
        CodeNoeVosolModel codeNoeVosolModel = new CodeNoeVosolModel();

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(CodeNoeVosolModel.TableName(), allColumns(), CodeNoeVosolModel.COLUMN_CodeNoeVosol() + " = ?", new String[]{String.valueOf(codeNoeVosolMoshtary)}, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    codeNoeVosolModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , CodeNoeVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CodeNoeVosolDAO" , "" , "getByCodeNoeVosol" , "");
        }
        return codeNoeVosolModel;
    }

    public ArrayList<CodeNoeVosolModel> getAllByParentsId(String parentsId)
    {
        ArrayList<CodeNoeVosolModel> arrayList = new ArrayList<>();
        String query = "select * from " +
                CodeNoeVosolModel.TableName() +
                " where " + CodeNoeVosolModel.COLUMN_CodeNoeVosol() + " in (" + parentsId + ")";

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);

//            Cursor cursor = db.query(CodeNoeVosolModel.TableName(), allColumns(), CodeNoeVosolModel.COLUMN_CodeNoeVosol() + " in (?) ", new String[]{(parentsId)}, null, null, null);
            if (cursor != null)
            {
                Log.d("parameter" , "cursor size : " + cursor.getCount());
                arrayList = cursorToModel(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "CodeNoeVosolDAO" , "" , "getAllByParentsId" , "");
        }
        return arrayList;
    }



    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(CodeNoeVosolModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , CodeNoeVosolModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CodeNoeVosolDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(CodeNoeVosolModel codeNoeVosolModel)
    {
        ContentValues contentValues = new ContentValues();

        if (codeNoeVosolModel.getCodeNoeVosol() > 0)
        {
            contentValues.put(CodeNoeVosolModel.COLUMN_CodeNoeVosol() , codeNoeVosolModel.getCodeNoeVosol());
        }
        contentValues.put(CodeNoeVosolModel.COLUMN_txtNoeVosol() , codeNoeVosolModel.getTxtNoeVosol());
        contentValues.put(CodeNoeVosolModel.COLUMN_ViewInPPC() , codeNoeVosolModel.getViewInPPC());
        contentValues.put(CodeNoeVosolModel.COLUMN_CodeNoeSanad_dp() , codeNoeVosolModel.getCodeNoeSanad_dp());
        contentValues.put(CodeNoeVosolModel.COLUMN_CodeNoeCheck_dp() , codeNoeVosolModel.getCodeNoeCheck_dp());

        return contentValues;
    }


    private ArrayList<CodeNoeVosolModel> cursorToModel(Cursor cursor)
    {
        ArrayList<CodeNoeVosolModel> codeNoeVosolModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            CodeNoeVosolModel codeNoeVosolModel = new CodeNoeVosolModel();

            codeNoeVosolModel.setCodeNoeVosol(cursor.getInt(cursor.getColumnIndex(CodeNoeVosolModel.COLUMN_CodeNoeVosol())));
            codeNoeVosolModel.setTxtNoeVosol(cursor.getString(cursor.getColumnIndex(CodeNoeVosolModel.COLUMN_txtNoeVosol())));
            codeNoeVosolModel.setViewInPPC(cursor.getInt(cursor.getColumnIndex(CodeNoeVosolModel.COLUMN_ViewInPPC())));
            codeNoeVosolModel.setCodeNoeSanad_dp(cursor.getInt(cursor.getColumnIndex(CodeNoeVosolModel.COLUMN_CodeNoeSanad_dp())));
            codeNoeVosolModel.setCodeNoeCheck_dp(cursor.getInt(cursor.getColumnIndex(CodeNoeVosolModel.COLUMN_CodeNoeCheck_dp())));

            codeNoeVosolModels.add(codeNoeVosolModel);
            cursor.moveToNext();
        }
        return codeNoeVosolModels;
    }


}
