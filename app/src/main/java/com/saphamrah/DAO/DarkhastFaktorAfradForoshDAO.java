package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.DarkhastFaktorAfradForoshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class DarkhastFaktorAfradForoshDAO
{

    private DBHelper dbHelper;
    private Context context;



    public DarkhastFaktorAfradForoshDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorAfradForoshDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            DarkhastFaktorAfradForoshModel.COLUMN_ccDarkhastFaktorAfradForosh(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccDarkhastFaktor(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccAfradForoshandeh(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccAfradForoshandehJaygozin(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccAfradMamorPakhsh(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccAfradMamorPakhshJaygozin(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccAfradRanandeh(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccAfradRanandehJaygozin(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccAfradGorohForosh(),
            DarkhastFaktorAfradForoshModel.COLUMN_ccAfradSarGorohForosh()
        };
    }


    public boolean insertGroup(ArrayList<DarkhastFaktorAfradForoshModel> darkhastFaktorAfradForoshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DarkhastFaktorAfradForoshModel darkhastFaktorAfradForoshModel : darkhastFaktorAfradForoshModels)
            {
                ContentValues contentValues = modelToContentvalue(darkhastFaktorAfradForoshModel);
                db.insertOrThrow(DarkhastFaktorAfradForoshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorAfradForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorAfradForoshDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public long insert(DarkhastFaktorAfradForoshModel darkhastFaktorAfradForoshModel)
    {
        long insertId = 0;
        ContentValues contentValues = modelToContentvalue(darkhastFaktorAfradForoshModel);
        try
        {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            insertId = db.insertOrThrow(DarkhastFaktorAfradForoshModel.TableName() , null , contentValues);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , DarkhastFaktorAfradForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorAfradForoshDAO" , "" , "insert" , "");
        }
        return insertId;
    }

    public ArrayList<DarkhastFaktorAfradForoshModel> getAll()
    {
        ArrayList<DarkhastFaktorAfradForoshModel> darkhastFaktorAfradForoshModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorAfradForoshModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorAfradForoshModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorAfradForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorAfradForoshDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorAfradForoshModels;
    }

    public DarkhastFaktorAfradForoshModel getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        DarkhastFaktorAfradForoshModel darkhastFaktorAfradForoshModel = new DarkhastFaktorAfradForoshModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorAfradForoshModel.TableName(), allColumns(), DarkhastFaktorAfradForoshModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorAfradForoshModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorAfradForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorAfradForoshDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorAfradForoshModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorAfradForoshModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorAfradForoshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorAfradForoshDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(DarkhastFaktorAfradForoshModel darkhastFaktorAfradForoshModel)
    {
        ContentValues contentValues = new ContentValues();

        if (darkhastFaktorAfradForoshModel.getCcDarkhastFaktorAfradForosh() > 0)
        {
            contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccDarkhastFaktorAfradForosh() , darkhastFaktorAfradForoshModel.getCcDarkhastFaktorAfradForosh());
        }
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccDarkhastFaktor() , darkhastFaktorAfradForoshModel.getCcDarkhastFaktor());
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradForoshandeh() , darkhastFaktorAfradForoshModel.getCcAfradForoshandeh());
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradForoshandehJaygozin() , darkhastFaktorAfradForoshModel.getCcAfradForoshandehJaygozin());
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradMamorPakhsh() , darkhastFaktorAfradForoshModel.getCcAfradMamorPakhsh());
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradMamorPakhshJaygozin() , darkhastFaktorAfradForoshModel.getCcAfradMamorPakhshJaygozin());
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradRanandeh() , darkhastFaktorAfradForoshModel.getCcAfradRanandeh());
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradRanandehJaygozin() , darkhastFaktorAfradForoshModel.getCcAfradRanandehJaygozin());
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradGorohForosh() , darkhastFaktorAfradForoshModel.getCcAfradGorohForosh());
        contentValues.put(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradSarGorohForosh() , darkhastFaktorAfradForoshModel.getCcAfradSarGorohForosh());

        return contentValues;
    }


    private ArrayList<DarkhastFaktorAfradForoshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorAfradForoshModel> darkhastFaktorAfradForoshModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorAfradForoshModel darkhastFaktorAfradForoshModel = new DarkhastFaktorAfradForoshModel();

            darkhastFaktorAfradForoshModel.setCcDarkhastFaktorAfradForosh(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccDarkhastFaktorAfradForosh())));
            darkhastFaktorAfradForoshModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorAfradForoshModel.setCcAfradForoshandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradForoshandeh())));
            darkhastFaktorAfradForoshModel.setCcAfradForoshandehJaygozin(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradForoshandehJaygozin())));
            darkhastFaktorAfradForoshModel.setCcAfradMamorPakhsh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradMamorPakhsh())));
            darkhastFaktorAfradForoshModel.setCcAfradMamorPakhshJaygozin(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradMamorPakhshJaygozin())));
            darkhastFaktorAfradForoshModel.setCcAfradRanandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradRanandeh())));
            darkhastFaktorAfradForoshModel.setCcAfradRanandehJaygozin(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradRanandehJaygozin())));
            darkhastFaktorAfradForoshModel.setCcAfradGorohForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradGorohForosh())));
            darkhastFaktorAfradForoshModel.setCcAfradSarGorohForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorAfradForoshModel.COLUMN_ccAfradSarGorohForosh())));

            darkhastFaktorAfradForoshModels.add(darkhastFaktorAfradForoshModel);
            cursor.moveToNext();
        }
        return darkhastFaktorAfradForoshModels;
    }


}
