package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryTaghiratModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MoshtaryTaghiratDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryTaghiratDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryTaghiratDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryTaghiratModel.COLUMN_ccMoshtaryTaghirat(),
            MoshtaryTaghiratModel.COLUMN_ccMoshtary(),
            MoshtaryTaghiratModel.COLUMN_CodeMely(),
            MoshtaryTaghiratModel.COLUMN_ShenasehMely(),
            MoshtaryTaghiratModel.COLUMN_ccUser(),
            MoshtaryTaghiratModel.COLUMN_CodeMelyImage(),
            MoshtaryTaghiratModel.COLUMN_ExtraProp_IsSendToSql()
        };
    }


    public boolean insertGroup(ArrayList<MoshtaryTaghiratModel> moshtaryTaghiratModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryTaghiratModel moshtaryTaghiratModel : moshtaryTaghiratModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryTaghiratModel);
                db.insertOrThrow(MoshtaryTaghiratModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryTaghiratModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryTaghiratDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public boolean insert(MoshtaryTaghiratModel moshtaryTaghiratModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(moshtaryTaghiratModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(MoshtaryTaghiratModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , MoshtaryTaghiratModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryTaghiratDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<MoshtaryTaghiratModel> getAll()
    {
        ArrayList<MoshtaryTaghiratModel> moshtaryTaghiratModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryTaghiratModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryTaghiratModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryTaghiratModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryTaghiratDAO" , "" , "getAll" , "");
        }
        return moshtaryTaghiratModels;
    }

    public boolean deleteNotSendByccMoshtary(int ccMoshtary)
    {
        String query = "delete from " + MoshtaryTaghiratModel.TableName() + " where " + MoshtaryTaghiratModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryTaghiratModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryTaghiratDAO" , "" , "deleteNotSendByccMoshtary" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryTaghiratModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryTaghiratModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryTaghiratDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryTaghiratModel moshtaryTaghiratModel)
    {
        ContentValues contentValues = new ContentValues();

        if (moshtaryTaghiratModel.getCcMoshtaryTaghirat() > 0)
        {
            contentValues.put(MoshtaryTaghiratModel.COLUMN_ccMoshtaryTaghirat() , moshtaryTaghiratModel.getCcMoshtaryTaghirat());
        }
        contentValues.put(MoshtaryTaghiratModel.COLUMN_ccMoshtary() , moshtaryTaghiratModel.getCcMoshtary());
        contentValues.put(MoshtaryTaghiratModel.COLUMN_CodeMely() , moshtaryTaghiratModel.getCodeMely());
        contentValues.put(MoshtaryTaghiratModel.COLUMN_ShenasehMely() , moshtaryTaghiratModel.getShenasehMely());
        contentValues.put(MoshtaryTaghiratModel.COLUMN_ccUser() , moshtaryTaghiratModel.getCcUser());
        contentValues.put(MoshtaryTaghiratModel.COLUMN_CodeMelyImage() , moshtaryTaghiratModel.getCodeMelyImage());
        contentValues.put(MoshtaryTaghiratModel.COLUMN_ExtraProp_IsSendToSql() , moshtaryTaghiratModel.getExtraProp_IsSendToSql());

        return contentValues;
    }


    private ArrayList<MoshtaryTaghiratModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryTaghiratModel> moshtaryTaghiratModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryTaghiratModel moshtaryTaghiratModel = new MoshtaryTaghiratModel();

            moshtaryTaghiratModel.setCcMoshtaryTaghirat(cursor.getInt(cursor.getColumnIndex(MoshtaryTaghiratModel.COLUMN_ccMoshtaryTaghirat())));
            moshtaryTaghiratModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryTaghiratModel.COLUMN_ccMoshtary())));
            moshtaryTaghiratModel.setCodeMely(cursor.getString(cursor.getColumnIndex(MoshtaryTaghiratModel.COLUMN_CodeMely())));
            moshtaryTaghiratModel.setShenasehMely(cursor.getString(cursor.getColumnIndex(MoshtaryTaghiratModel.COLUMN_ShenasehMely())));
            moshtaryTaghiratModel.setCcUser(cursor.getInt(cursor.getColumnIndex(MoshtaryTaghiratModel.COLUMN_ccUser())));
            moshtaryTaghiratModel.setCodeMelyImage(cursor.getBlob(cursor.getColumnIndex(MoshtaryTaghiratModel.COLUMN_CodeMelyImage())));
            moshtaryTaghiratModel.setExtraProp_IsSendToSql(cursor.getInt(cursor.getColumnIndex(MoshtaryTaghiratModel.COLUMN_ExtraProp_IsSendToSql())));

            moshtaryTaghiratModels.add(moshtaryTaghiratModel);
            cursor.moveToNext();
        }
        return moshtaryTaghiratModels;
    }
    
    
}
