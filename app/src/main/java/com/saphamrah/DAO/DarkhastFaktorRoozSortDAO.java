package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.database.CursorWindowCompat;

import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorRoozSortModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class DarkhastFaktorRoozSortDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "DarkhastFaktorRoozSortDAO";


    public DarkhastFaktorRoozSortDAO(Context context)
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
            DarkhastFaktorRoozSortModel.COLUMN_ccDarkhastFaktor(),
            DarkhastFaktorRoozSortModel.COLUMN_Sort()
        };
    }


    public boolean insert(DarkhastFaktorRoozSortModel darkhastFaktorRoozSortModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(darkhastFaktorRoozSortModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(DarkhastFaktorRoozSortModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorRoozSortModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<DarkhastFaktorRoozSortModel> getAll()
    {
        ArrayList<DarkhastFaktorRoozSortModel> darkhastFaktorRoozSortModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorRoozSortModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorRoozSortModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorRoozSortModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return darkhastFaktorRoozSortModels;
    }

    public int getCount()
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select count ( " + DarkhastFaktorRoozSortModel.COLUMN_ccDarkhastFaktor() + " ) from " + DarkhastFaktorRoozSortModel.TableName();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorRoozSortModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getCount" , "");
        }
        return count;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorRoozSortModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorRoozSortModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorRoozSortModel.TableName(), DarkhastFaktorRoozSortModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorRoozSortModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorRoozSortDAO" , "" , "deleteByccDarkhastFaktor" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(DarkhastFaktorRoozSortModel darkhastFaktorRoozSortModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DarkhastFaktorRoozSortModel.COLUMN_ccDarkhastFaktor() , darkhastFaktorRoozSortModel.getCcDarkhastFaktor());
        contentValues.put(DarkhastFaktorRoozSortModel.COLUMN_Sort() , darkhastFaktorRoozSortModel.getSort());

        return contentValues;
    }


    private ArrayList<DarkhastFaktorRoozSortModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorRoozSortModel> darkhastFaktorRoozSortModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorRoozSortModel darkhastFaktorRoozSortModel = new DarkhastFaktorRoozSortModel();

            darkhastFaktorRoozSortModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorRoozSortModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorRoozSortModel.setSort(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorRoozSortModel.COLUMN_Sort())));

            darkhastFaktorRoozSortModels.add(darkhastFaktorRoozSortModel);
            cursor.moveToNext();
        }
        return darkhastFaktorRoozSortModels;
    }

}
