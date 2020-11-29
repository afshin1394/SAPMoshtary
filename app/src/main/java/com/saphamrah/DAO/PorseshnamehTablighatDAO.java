package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.PorseshnamehTablighatModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PorseshnamehTablighatDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "PorseshnamehTablighatDAO";

    public PorseshnamehTablighatDAO(Context context)
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
            PorseshnamehTablighatModel.COLUMN_ccPorseshnameh_Tablighat(),
            PorseshnamehTablighatModel.COLUMN_ccPorseshnameh(),
            PorseshnamehTablighatModel.COLUMN_ccNoeTablighat()
        };
    }

    public ArrayList<PorseshnamehTablighatModel> getAllByPorseshname(int ccPorseshaname)
    {
        ArrayList<PorseshnamehTablighatModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PorseshnamehTablighatModel.TableName(), allColumns(), PorseshnamehTablighatModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshaname, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehTablighatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAllByPorseshname" , "");
        }
        return models;
    }

    public boolean insertGroup(List<PorseshnamehTablighatModel> porseshnamehTablighatModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (PorseshnamehTablighatModel model : porseshnamehTablighatModels)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(PorseshnamehTablighatModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            String message = context.getResources().getString(R.string.errorGroupInsert , PorseshnamehTablighatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }


    public boolean delete(int ccPorseshnameh)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PorseshnamehTablighatModel.TableName(), PorseshnamehTablighatModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshnameh, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehTablighatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "delete" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PorseshnamehTablighatModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehTablighatModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(PorseshnamehTablighatModel model)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PorseshnamehTablighatModel.COLUMN_ccPorseshnameh() , model.getCcPorseshnameh());
        contentValues.put(PorseshnamehTablighatModel.COLUMN_ccNoeTablighat() , model.getCcNoeTablighat());

        return contentValues;
    }


    private ArrayList<PorseshnamehTablighatModel> cursorToModel(Cursor cursor)
    {
        ArrayList<PorseshnamehTablighatModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            PorseshnamehTablighatModel model = new PorseshnamehTablighatModel();

            model.setCcPorseshnamehTablighat(cursor.getInt(cursor.getColumnIndex(PorseshnamehTablighatModel.COLUMN_ccPorseshnameh_Tablighat())));
            model.setCcPorseshnameh(cursor.getInt(cursor.getColumnIndex(PorseshnamehTablighatModel.COLUMN_ccPorseshnameh())));
            model.setCcNoeTablighat(cursor.getInt(cursor.getColumnIndex(PorseshnamehTablighatModel.COLUMN_ccNoeTablighat())));

            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }


}
