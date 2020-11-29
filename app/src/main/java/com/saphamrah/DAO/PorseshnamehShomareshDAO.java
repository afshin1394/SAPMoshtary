package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.PorseshnamehShomareshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PorseshnamehShomareshDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "PorseshnamehShomareshDAO";

    public PorseshnamehShomareshDAO(Context context)
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
            PorseshnamehShomareshModel.COLUMN_ccPorseshnamehShomaresh(),
            PorseshnamehShomareshModel.COLUMN_ccPorseshnameh(),
            PorseshnamehShomareshModel.COLUMN_ccKala(),
            PorseshnamehShomareshModel.COLUMN_TedadShomaresh()
        };
    }


    public boolean insertGroup(List<PorseshnamehShomareshModel> porseshnamehShomareshModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (PorseshnamehShomareshModel model : porseshnamehShomareshModels)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(PorseshnamehShomareshModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , PorseshnamehShomareshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<PorseshnamehShomareshModel> getAll()
    {
        ArrayList<PorseshnamehShomareshModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PorseshnamehShomareshModel.TableName(), allColumns(), null, null, null, null, null);
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
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehShomareshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return models;
    }

    public ArrayList<PorseshnamehShomareshModel> getAllByPorseshname(int ccPorseshaname)
    {
        ArrayList<PorseshnamehShomareshModel> models = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PorseshnamehShomareshModel.TableName(), allColumns(), PorseshnamehShomareshModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshaname, null, null, null, null);
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
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehShomareshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAllByPorseshname" , "");
        }
        return models;
    }

    public Map<Integer,Integer> getGoodsCountByPorseshnameh(int ccPorseshaname)
    {
        Map<Integer, Integer> map = new HashMap<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select ccKala,TedadShomaresh from porseshnamehshomaresh where ccPorseshnameh = " + ccPorseshaname;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        int ccKala = cursor.getInt(0);
                        int tedad = cursor.getInt(1);
                        map.put(ccKala, tedad);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehShomareshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getGoodsCountByPorseshnameh" , "");
        }
        return map;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PorseshnamehShomareshModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehShomareshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean delete(int ccPorseshname)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PorseshnamehShomareshModel.TableName(), PorseshnamehShomareshModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshname, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehShomareshModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(PorseshnamehShomareshModel model)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PorseshnamehShomareshModel.COLUMN_ccPorseshnameh() , model.getCcPorseshnameh());
        contentValues.put(PorseshnamehShomareshModel.COLUMN_ccKala() , model.getCcKala());
        contentValues.put(PorseshnamehShomareshModel.COLUMN_TedadShomaresh() , model.getTedadShomaresh());

        return contentValues;
    }


    private ArrayList<PorseshnamehShomareshModel> cursorToModel(Cursor cursor)
    {
        ArrayList<PorseshnamehShomareshModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            PorseshnamehShomareshModel model = new PorseshnamehShomareshModel();

            model.setCcPorseshnamehShomaresh(cursor.getInt(cursor.getColumnIndex(PorseshnamehShomareshModel.COLUMN_ccPorseshnamehShomaresh())));
            model.setCcPorseshnameh(cursor.getInt(cursor.getColumnIndex(PorseshnamehShomareshModel.COLUMN_ccPorseshnameh())));
            model.setCcKala(cursor.getInt(cursor.getColumnIndex(PorseshnamehShomareshModel.COLUMN_ccKala())));
            model.setTedadShomaresh(cursor.getInt(cursor.getColumnIndex(PorseshnamehShomareshModel.COLUMN_TedadShomaresh())));

            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }


}
