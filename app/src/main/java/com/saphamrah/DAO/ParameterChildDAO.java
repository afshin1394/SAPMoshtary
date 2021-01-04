package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ParameterModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetParameterChildResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParameterChildDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ParameterChildDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
                ParameterChildModel.COLUMN_ccChildParameter(),
                ParameterChildModel.COLUMN_ccParameter(),
                ParameterChildModel.COLUMN_Name(),
                ParameterChildModel.COLUMN_Value(),
                ParameterChildModel.COLUMN_txt(),
                ParameterChildModel.COLUMN_CodeSort()
        };
    }


    /**
     *
     * @param context
     * @param activityNameForLog
     * @param noeTitrSatr -> 1 => get titr in splash , 2 => get satr in splash , 3 => get satr in get program
     * @param ccMarkazSazmanForosh
     * @param dateProgram -> date and time of latest get config
     * @param retrofitResponse
     */
    public void fetchParameterChild(final Context context, final String activityNameForLog, String noeTitrSatr, String ccMarkazSazmanForosh, String ccMarkazAnbar, String dateProgram, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterChildDAO", activityNameForLog, "fetchParameterChild", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetParameterChildResult> call = apiServiceGet.getParameterChild(noeTitrSatr , ccMarkazSazmanForosh, ccMarkazAnbar , dateProgram);
            call.enqueue(new Callback<GetParameterChildResult>() {
                @Override
                public void onResponse(Call<GetParameterChildResult> call, Response<GetParameterChildResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "ParameterChildDAO", "", "fetchParameterChild", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetParameterChildResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "ParameterChildDAO", activityNameForLog, "fetchParameterChild", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), "ParameterChildDAO", activityNameForLog, "fetchParameterChild", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterChildDAO", activityNameForLog, "fetchParameterChild", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO", activityNameForLog, "fetchParameterChild", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetParameterChildResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), "ParameterChildDAO", activityNameForLog, "fetchParameterChild", "onFailure");
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

    public boolean insertGroup(ArrayList<ParameterChildModel> parameterChildModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ParameterChildModel parameterChildModel : parameterChildModels)
            {
                ContentValues contentValues = modelToContentvalue(parameterChildModel);
                db.insertOrThrow(ParameterChildModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ParameterChildModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterChildDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ParameterChildModel> getAll()
    {
        ArrayList<ParameterChildModel> parameterChildModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ParameterChildModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    parameterChildModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ParameterChildModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterChildDAO" , "" , "getAll" , "");
        }
        return parameterChildModels;
    }

    public ArrayList<ParameterChildModel> getAllByParameterName(String parameterName)
    {
        ArrayList<ParameterChildModel> arrayList = new ArrayList<>();
        String query = "select " + ParameterChildModel.TableName() + ".* from " +
                ParameterChildModel.TableName() + " inner join " + ParameterModel.TableName() +
                " on " + ParameterModel.TableName() + "." + ParameterChildModel.COLUMN_ccParameter() + " = " + ParameterChildModel.TableName() + "." + ParameterChildModel.COLUMN_ccParameter() +
                " where " + ParameterModel.TableName() + "." + ParameterModel.COLUMN_Name() + " = '" + parameterName + "' collate NOCASE";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            //Log.d("parameter" , "query : " + query);
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getAllByParameterName" , "");
        }
        return arrayList;
    }

    public String getTextByParameterNameAndValue(String parameterName , String value)
    {
        String txt = "";
        String query = "select " + ParameterChildModel.COLUMN_txt() + " from " +
                ParameterChildModel.TableName() + " inner join " + ParameterModel.TableName() +
                " on " + ParameterModel.TableName() + "." + ParameterChildModel.COLUMN_ccParameter() + " = " + ParameterChildModel.TableName() + "." + ParameterChildModel.COLUMN_ccParameter() +
                " where " + ParameterModel.TableName() + "." + ParameterModel.COLUMN_Name() + " = '" + parameterName +
                "' and " + ParameterChildModel.TableName() + "." + ParameterChildModel.COLUMN_Value() + " = '" + value + "' collate NOCASE";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            Log.d("parameter" , "query get Text : " + query);
            if (cursor != null)
            {
                Log.d("parameter" , "cursor size : " + cursor.getCount());
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    txt = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getTextByParameterNameAndValue" , "");
        }
        return txt;
    }

    public ParameterChildModel getByParameterNameAndValue(String parameterName , String value)
    {
        ParameterChildModel parameterChildModel = new ParameterChildModel();
        String query = "select * from " +
                ParameterChildModel.TableName() + " inner join " + ParameterModel.TableName() +
                " on " + ParameterModel.TableName() + "." + ParameterChildModel.COLUMN_ccParameter() + " = " + ParameterChildModel.TableName() + "." + ParameterChildModel.COLUMN_ccParameter() +
                " where " + ParameterModel.TableName() + "." + ParameterModel.COLUMN_Name() + " = '" + parameterName +
                "' and " + ParameterChildModel.TableName() + "." + ParameterChildModel.COLUMN_Value() + " = '" + value + "' collate NOCASE";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                Log.d("parameter" , "cursor size : " + cursor.getCount());
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    parameterChildModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getByParameterNameAndValue" , "");
        }
        return parameterChildModel;
    }

    public String getValueByccChildParameter(int ccChilcParameter)
    {
        String value = "";
        try
        {
            String query = "select " + ParameterChildModel.COLUMN_Value() + " from " + ParameterChildModel.TableName() + " where " + ParameterChildModel.COLUMN_ccChildParameter() + " = " + ccChilcParameter + " order by " + ParameterChildModel.COLUMN_CodeSort();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    value = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getParentsByParameterName" , "");
        }
		value = value == null ? "" : value;												
        return value;
    }

    public ArrayList<ParameterChildModel> getAllByParentsId(String parentsId)
    {
        ArrayList<ParameterChildModel> arrayList = new ArrayList<>();
        String query = "select * from " +
                ParameterChildModel.TableName() +
                " where " + ParameterChildModel.COLUMN_ccChildParameter() + " in (" + parentsId + ") order by " + ParameterChildModel.COLUMN_CodeSort();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
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
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getAllByParentsId" , "");
        }
        return arrayList;
    }

    public ArrayList<ParameterChildModel> getAllByccParameter(String ccParameters)
    {
        ArrayList<ParameterChildModel> arrayList = new ArrayList<>();
        String query = "select * from " +
                ParameterChildModel.TableName() +
                " where " + ParameterChildModel.COLUMN_ccParameter() + " in (" + ccParameters + ")";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                arrayList = cursorToModel(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getAllByccParameter" , "");
        }
        return arrayList;
    }

    public ArrayList<ParameterChildModel> getAllByccChildParameter(String ccChildsParameters)
    {
        ArrayList<ParameterChildModel> arrayList = new ArrayList<>();
        String query = "select * from " +
                ParameterChildModel.TableName() +
                " where " + ParameterChildModel.COLUMN_ccChildParameter() + " in (" + ccChildsParameters + ") order by " + ParameterChildModel.COLUMN_CodeSort();
        Log.d("ParameterChild","query:"+query);
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                arrayList = cursorToModel(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getAllByccChildParameter" , "");
        }
        return arrayList;
    }

    public String getParentsByParameterName(String parameterName)
    {
        String parent = "";
        String query = "select " + ParameterChildModel.COLUMN_Value() + " from " + ParameterChildModel.TableName() +
                " where " + ParameterChildModel.COLUMN_Name() + " = '" + parameterName + "' collate NOCASE";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            Log.d("parameter" , "query of get parent : " + query);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    parent = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ParameterChildDAO" , "" , "getParentsByParameterName" , "");
        }
        return parent;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ParameterChildModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ParameterChildModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterChildDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByParameterNotGetProgram()
    {
        try
        {
            String query = "delete from ParameterChild where ccParameter in (select ccParameter from Parameter where GetProgram = 0)";
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ParameterChildModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ParameterChildDAO" , "" , "deleteByParameterNotGetProgram" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ParameterChildModel parameterChildModel)
    {
        ContentValues contentValues = new ContentValues();

        if (parameterChildModel.getCcParameter() != null && parameterChildModel.getCcParameter() > 0)
        {
            contentValues.put(ParameterChildModel.COLUMN_ccChildParameter() , parameterChildModel.getCcParameterChild());
        }
        contentValues.put(ParameterChildModel.COLUMN_ccParameter() , parameterChildModel.getCcParameter());
        contentValues.put(ParameterChildModel.COLUMN_Name() , parameterChildModel.getName());
        contentValues.put(ParameterChildModel.COLUMN_Value() , parameterChildModel.getValue());
        contentValues.put(ParameterChildModel.COLUMN_txt() , parameterChildModel.getTxt());
        contentValues.put(ParameterChildModel.COLUMN_CodeSort() , parameterChildModel.getCodeSort());

        return contentValues;
    }


    private ArrayList<ParameterChildModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ParameterChildModel> parameterChildModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ParameterChildModel parameterChildModel = new ParameterChildModel();

            parameterChildModel.setCcParameterChild(cursor.getInt(cursor.getColumnIndex(ParameterChildModel.COLUMN_ccChildParameter())));
            parameterChildModel.setCcParameter(cursor.getInt(cursor.getColumnIndex(ParameterChildModel.COLUMN_ccParameter())));
            parameterChildModel.setName(cursor.getString(cursor.getColumnIndex(ParameterChildModel.COLUMN_Name())));
            parameterChildModel.setValue(cursor.getString(cursor.getColumnIndex(ParameterChildModel.COLUMN_Value())));
            parameterChildModel.setTxt(cursor.getString(cursor.getColumnIndex(ParameterChildModel.COLUMN_txt())));
            parameterChildModel.setCodeSort(cursor.getInt(cursor.getColumnIndex(ParameterChildModel.COLUMN_CodeSort())));

            parameterChildModels.add(parameterChildModel);
            cursor.moveToNext();
        }
        return parameterChildModels;
    }


    public boolean updateShowBarkhordAvalie(int show)
    {
        String query = "update " + ParameterChildModel.COLUMN_ccChildParameter() + " set value = " + show + " where " + ParameterChildModel.COLUMN_ccChildParameter() + " = 30";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateShowMojoodiGiri(int show)
    {
        String query = "update ChildParameter set value = " + show + " where ccChildParameter = 32";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateMultipleMojoodiGiri(int show)
    {
        String query = "update ChildParameter set value = " + show + " where ccChildParameter = 34";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


}
