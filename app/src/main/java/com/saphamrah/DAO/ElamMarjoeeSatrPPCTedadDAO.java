package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ElamMarjoeeSatrPPCTedadModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class ElamMarjoeeSatrPPCTedadDAO
{


    private DBHelper dbHelper;
    private Context context;

    public ElamMarjoeeSatrPPCTedadDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ElamMarjoeeSatrPPCTedadDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            ElamMarjoeeSatrPPCTedadModel.COLUMN_ccDarkhastFaktor(),
            ElamMarjoeeSatrPPCTedadModel.COLUMN_TedadSatr(),
            ElamMarjoeeSatrPPCTedadModel.COLUMN_ccElamMarjoeeSatr_Tedad()
        };
    }

    public void insertGroup(ArrayList<ElamMarjoeeSatrPPCTedadModel> elamMarjoeeSatrPPCTedadModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ElamMarjoeeSatrPPCTedadModel elamMarjoeeSatrPPCTedadModel : elamMarjoeeSatrPPCTedadModels)
            {
                ContentValues contentValues = modelToContentvalue(elamMarjoeeSatrPPCTedadModel);
                db.insertOrThrow(ElamMarjoeeSatrPPCTedadModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ElamMarjoeeSatrPPCTedadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCTedadDAO" , "" , "insertGroup" , "");
        }
    }


    public ArrayList<ElamMarjoeeSatrPPCTedadModel> getAll()
    {
        ArrayList<ElamMarjoeeSatrPPCTedadModel> elamMarjoeeSatrPPCTedadModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElamMarjoeeSatrPPCTedadModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeeSatrPPCTedadModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeeSatrPPCTedadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCTedadDAO" , "" , "getAll" , "");
        }
        return elamMarjoeeSatrPPCTedadModels;
    }

    public ArrayList<ElamMarjoeeSatrPPCTedadModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<ElamMarjoeeSatrPPCTedadModel> elamMarjoeeSatrPPCTedadModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ElamMarjoeeSatrPPCTedadModel.TableName(), allColumns(), ElamMarjoeeSatrPPCTedadModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    elamMarjoeeSatrPPCTedadModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ElamMarjoeeSatrPPCTedadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCTedadDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return elamMarjoeeSatrPPCTedadModels;
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElamMarjoeeSatrPPCTedadModel.TableName(), ElamMarjoeeSatrPPCTedadModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeeSatrPPCTedadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCTedadDAO" , "" , "deleteByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ElamMarjoeeSatrPPCTedadModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ElamMarjoeeSatrPPCTedadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCTedadDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public long insert(ElamMarjoeeSatrPPCTedadModel elamMarjoeeSatrPPCTedadModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(elamMarjoeeSatrPPCTedadModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long ccElamMarjoeeSatrPPCTedad = db.insertOrThrow(ElamMarjoeeSatrPPCTedadModel.TableName() , null , contentValues);
            db.close();
            return ccElamMarjoeeSatrPPCTedad;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , ElamMarjoeeSatrPPCTedadModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ElamMarjoeeSatrPPCTedadDAO" , "" , "insert" , "");
            return -1;
        }
    }

    private static ContentValues modelToContentvalue(ElamMarjoeeSatrPPCTedadModel elamMarjoeeSatrPPCTedadModel)
    {
        ContentValues contentValues = new ContentValues();

        if (elamMarjoeeSatrPPCTedadModel.getCcElamMarjoeeSatr_Tedad() != null && elamMarjoeeSatrPPCTedadModel.getCcElamMarjoeeSatr_Tedad() > 0)
        {
            contentValues.put(ElamMarjoeeSatrPPCTedadModel.COLUMN_ccElamMarjoeeSatr_Tedad() , elamMarjoeeSatrPPCTedadModel.getCcElamMarjoeeSatr_Tedad());
        }
        contentValues.put(ElamMarjoeeSatrPPCTedadModel.COLUMN_ccDarkhastFaktor() , elamMarjoeeSatrPPCTedadModel.getCcDarkhastFaktor());
        contentValues.put(ElamMarjoeeSatrPPCTedadModel.COLUMN_TedadSatr() , elamMarjoeeSatrPPCTedadModel.getTedadSatr());

        return contentValues;
    }


    private ArrayList<ElamMarjoeeSatrPPCTedadModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ElamMarjoeeSatrPPCTedadModel> elamMarjoeeSatrPPCTedadModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ElamMarjoeeSatrPPCTedadModel elamMarjoeeSatrPPCTedadModel = new ElamMarjoeeSatrPPCTedadModel();

            elamMarjoeeSatrPPCTedadModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(ElamMarjoeeSatrPPCTedadModel.COLUMN_ccDarkhastFaktor())));
            elamMarjoeeSatrPPCTedadModel.setTedadSatr(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCTedadModel.COLUMN_TedadSatr())));
            elamMarjoeeSatrPPCTedadModel.setCcElamMarjoeeSatr_Tedad(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCTedadModel.COLUMN_ccElamMarjoeeSatr_Tedad())));

            elamMarjoeeSatrPPCTedadModels.add(elamMarjoeeSatrPPCTedadModel);
            cursor.moveToNext();
        }
        return elamMarjoeeSatrPPCTedadModels;
    }


}
