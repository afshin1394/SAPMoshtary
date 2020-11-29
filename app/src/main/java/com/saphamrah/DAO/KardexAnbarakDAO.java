package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.KardexAnbarakModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class KardexAnbarakDAO
{
    private DBHelper dbHelper;
    private Context context;


    public KardexAnbarakDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KardexAnbarakDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            KardexAnbarakModel.COLUMN_ccKardexAnbarak(),
            KardexAnbarakModel.COLUMN_ccAnbarak(),
            KardexAnbarakModel.COLUMN_CodeNoeForm(),
            KardexAnbarakModel.COLUMN_CodeNoeAmalyat(),
            KardexAnbarakModel.COLUMN_ccRefrence(),
            KardexAnbarakModel.COLUMN_CodeVazeiat(),
            KardexAnbarakModel.COLUMN_ccUser(),
            KardexAnbarakModel.COLUMN_ccMoshtary(),
            KardexAnbarakModel.COLUMN_ExtraProp_IsSend()
        };
    }

    public long insert(KardexAnbarakModel kardexAnbarakModel)
    {
        long insertId = 0;
        try
        {
            ContentValues contentValues = modelToContentvalue(kardexAnbarakModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            insertId = db.insertOrThrow(KardexAnbarakModel.TableName() , null , contentValues);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , KardexAnbarakModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexAnbarakDAO" , "" , "insert" , "");
        }
        return insertId;
    }

    public ArrayList<KardexAnbarakModel> getAll()
    {
        ArrayList<KardexAnbarakModel> kardexAnbarakModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KardexAnbarakModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kardexAnbarakModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KardexAnbarakModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexAnbarakDAO" , "" , "getAll" , "");
        }
        return kardexAnbarakModels;
    }

    public ArrayList<KardexAnbarakModel> getByccMoshtary(int ccMoshtary)
    {
        ArrayList<KardexAnbarakModel> kardexAnbarakModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KardexAnbarakModel.TableName(), allColumns(), KardexAnbarakModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kardexAnbarakModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KardexAnbarakModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexAnbarakDAO" , "" , "getByccMoshtary" , "");
        }
        return kardexAnbarakModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KardexAnbarakModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KardexAnbarakModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexAnbarakDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KardexAnbarakModel kardexAnbarakModel)
    {
        ContentValues contentValues = new ContentValues();

        if (kardexAnbarakModel.getCcKardexAnbarak() > 0)
        {
            contentValues.put(KardexAnbarakModel.COLUMN_ccKardexAnbarak() , kardexAnbarakModel.getCcKardexAnbarak());
        }
        contentValues.put(KardexAnbarakModel.COLUMN_ccAnbarak() , kardexAnbarakModel.getCcAnbarak());
        contentValues.put(KardexAnbarakModel.COLUMN_CodeNoeForm() , kardexAnbarakModel.getCodeNoeForm());
        contentValues.put(KardexAnbarakModel.COLUMN_CodeNoeAmalyat() , kardexAnbarakModel.getCodeNoeAmalyat());
        contentValues.put(KardexAnbarakModel.COLUMN_ccRefrence() , kardexAnbarakModel.getCcRefrence());
        contentValues.put(KardexAnbarakModel.COLUMN_CodeVazeiat() , kardexAnbarakModel.getCodeVazeiat());
        contentValues.put(KardexAnbarakModel.COLUMN_ccUser() , kardexAnbarakModel.getCcUser());
        contentValues.put(KardexAnbarakModel.COLUMN_ccMoshtary() , kardexAnbarakModel.getCcMoshtary());
        contentValues.put(KardexAnbarakModel.COLUMN_ExtraProp_IsSend() , kardexAnbarakModel.getExtraProp_IsSend());

        return contentValues;
    }


    private ArrayList<KardexAnbarakModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KardexAnbarakModel> kardexAnbarakModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KardexAnbarakModel kardexAnbarakModel = new KardexAnbarakModel();

            kardexAnbarakModel.setCcKardexAnbarak(cursor.getLong(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_ccKardexAnbarak())));
            kardexAnbarakModel.setCcAnbarak(cursor.getInt(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_ccAnbarak())));
            kardexAnbarakModel.setCodeNoeForm(cursor.getInt(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_CodeNoeForm())));
            kardexAnbarakModel.setCodeNoeAmalyat(cursor.getInt(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_CodeNoeAmalyat())));
            kardexAnbarakModel.setCcRefrence(cursor.getLong(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_ccRefrence())));
            kardexAnbarakModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_CodeVazeiat())));
            kardexAnbarakModel.setCcUser(cursor.getInt(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_ccUser())));
            kardexAnbarakModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_ccMoshtary())));
            kardexAnbarakModel.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(KardexAnbarakModel.COLUMN_ExtraProp_IsSend())));

            kardexAnbarakModels.add(kardexAnbarakModel);
            cursor.moveToNext();
        }
        return kardexAnbarakModels;
    }

}
