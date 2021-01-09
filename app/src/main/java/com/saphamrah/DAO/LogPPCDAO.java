package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class LogPPCDAO
{

    private DBHelper dbHelper;
    private Context context;



    public LogPPCDAO(Context context)
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
           // logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "LogPPCDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            LogPPCModel.COLUMN_ccLogPPC(),
            LogPPCModel.COLUMN_ccAfrad(),
            LogPPCModel.COLUMN_IMEI(),
            LogPPCModel.COLUMN_Type(),
            LogPPCModel.COLUMN_LogMessage(),
            LogPPCModel.COLUMN_LogClass(),
            LogPPCModel.COLUMN_LogActivity(),
            LogPPCModel.COLUMN_LogFunctionParent(),
            LogPPCModel.COLUMN_LogFunctionChild(),
            LogPPCModel.COLUMN_LogDate(),
            LogPPCModel.COLUMN_ExtraProp_IsOld(),
            LogPPCModel.COLUMN_AndroidAPI()
        };
    }

    public boolean insert(LogPPCModel logppcModel)
    {
        ContentValues contentValues = modelToContentValue(logppcModel);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(LogPPCModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , LogPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "LogPPCDAO" , "" , "insert" , "");
            return false;
        }
    }


    public void updateExtraProp_IsOld(String ccLogPPCs , int isOld) //isOld = 0 => dont sent , isOld = 1 => sended
    {
        String query = "update " + LogPPCModel.TableName() + " set " + LogPPCModel.COLUMN_ExtraProp_IsOld() + " = " + isOld + " where " + LogPPCModel.COLUMN_ccLogPPC() + " in (" + ccLogPPCs + ")";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , LogPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "LogPPCDAO" , "" , "updateExtraProp" , "");
        }
    }


    public ArrayList<LogPPCModel> getUnsendedLogPPC()
    {
        ArrayList<LogPPCModel> logppcs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(LogPPCModel.TableName(), allColumns(), LogPPCModel.COLUMN_ExtraProp_IsOld() + " = " + 0 , null,null,null,LogPPCModel.COLUMN_ccLogPPC() + " desc");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    logppcs = cursorToArraylistModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , LogPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "LogPPCDAO" , "" , "selectUnsendedLogs" , "");
        }
        return logppcs;
    }

    public ArrayList<LogPPCModel> getUnsendExceptionsOrderByIdDesc()
    {
        //String query = "select * from " + LogPPCModel.TableName() + " where " + COLUMN_EXTRA_PROP_IS_OLD + " = 0 and " + COLUMN_TYPE + " = " + Constants.LOG_EXCEPTION() + " order by " + COLUMN_CC_LOG_PPC + " desc";
        ArrayList<LogPPCModel> arrayList = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(LogPPCModel.TableName(),allColumns(),"ExtraProp_IsOld = " + 0 +" and Type = " + Constants.LOG_EXCEPTION(),null,null,null,LogPPCModel.COLUMN_ccLogPPC() + " desc");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    arrayList = cursorToArraylistModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , LogPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "LogPPCDAO" , "" , "selectUnsendExceptionsOrderByIdDesc" , "");
        }
        return arrayList;
    }

    public LogPPCModel getLastUnsendLog()
    {
        LogPPCModel logPPCModel = new LogPPCModel();
        //String query = "select * from " + LogPPCModel.TableName() + " where " + COLUMN_EXTRA_PROP_IS_OLD + " = 0 order by " + COLUMN_CC_LOG_PPC + " desc limit 1" ;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(LogPPCModel.TableName(),allColumns(),LogPPCModel.COLUMN_ExtraProp_IsOld() + " = " + 0,null,null,null,LogPPCModel.COLUMN_ccLogPPC() + " desc " , "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    logPPCModel = cursorToArraylistModel(cursor).get(0);
                    Log.d("log" , logPPCModel.toString());
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , LogPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "LogPPCDAO" , "" , "selectLastUnsendLog" , "");
        }
        return logPPCModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(LogPPCModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , LogPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "LogPPCDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private ContentValues modelToContentValue(LogPPCModel model)
    {
        ContentValues contentValues = new ContentValues();

        //contentValues.put(COLUMN_CC_LOG_PPC , model.getCcLogPPC());
        contentValues.put(LogPPCModel.COLUMN_ccAfrad() , model.getCcAfrad());
        contentValues.put(LogPPCModel.COLUMN_IMEI() , model.getIMEI());
        contentValues.put(LogPPCModel.COLUMN_Type() , model.getLogType());
        contentValues.put(LogPPCModel.COLUMN_LogMessage() , model.getLogMessage());
        contentValues.put(LogPPCModel.COLUMN_LogClass() , model.getLogClass());
        contentValues.put(LogPPCModel.COLUMN_LogActivity() , model.getLogActivity());
        contentValues.put(LogPPCModel.COLUMN_LogFunctionParent() , model.getLogFunctionParent());
        contentValues.put(LogPPCModel.COLUMN_LogFunctionChild() , model.getLogFunctionChild());
        contentValues.put(LogPPCModel.COLUMN_LogDate() , model.getLogDate());
        contentValues.put(LogPPCModel.COLUMN_ExtraProp_IsOld() , model.getExtraProp_IsOld());
        contentValues.put(LogPPCModel.COLUMN_AndroidAPI() , model.getAndroidAPI());

        return contentValues;
    }

    private ArrayList<LogPPCModel> cursorToArraylistModel(Cursor cursor)
    {
        ArrayList<LogPPCModel> arrayListLogModel = new ArrayList<>();
        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                LogPPCModel logModel = new LogPPCModel();
                logModel.setCcAfrad(cursor.getInt(cursor.getColumnIndex(LogPPCModel.COLUMN_ccAfrad())));
                logModel.setCcLogPPC(cursor.getInt(cursor.getColumnIndex(LogPPCModel.COLUMN_ccLogPPC())));
                logModel.setIMEI(cursor.getString(cursor.getColumnIndex(LogPPCModel.COLUMN_IMEI())));
                logModel.setLogType(cursor.getInt(cursor.getColumnIndex(LogPPCModel.COLUMN_Type())));
                logModel.setAndroidAPI(cursor.getInt(cursor.getColumnIndex(LogPPCModel.COLUMN_AndroidAPI())));
                logModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(LogPPCModel.COLUMN_ExtraProp_IsOld())));
                logModel.setLogFunctionParent(cursor.getString(cursor.getColumnIndex(LogPPCModel.COLUMN_LogFunctionParent())));
                logModel.setLogFunctionChild(cursor.getString(cursor.getColumnIndex(LogPPCModel.COLUMN_LogFunctionChild())));
                logModel.setLogActivity(cursor.getString(cursor.getColumnIndex(LogPPCModel.COLUMN_LogActivity())));
                logModel.setLogClass(cursor.getString(cursor.getColumnIndex(LogPPCModel.COLUMN_LogClass())));
                logModel.setLogMessage(cursor.getString(cursor.getColumnIndex(LogPPCModel.COLUMN_LogMessage())));
                logModel.setLogDate(cursor.getString(cursor.getColumnIndex(LogPPCModel.COLUMN_LogDate())));

                arrayListLogModel.add(logModel);
                cursor.moveToNext();
            }
        }
        return arrayListLogModel;
    }


}
