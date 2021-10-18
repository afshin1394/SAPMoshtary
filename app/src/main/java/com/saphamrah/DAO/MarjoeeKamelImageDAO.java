package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.KardexModel;
import com.saphamrah.Model.MarjoeeKamelImageModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MarjoeeKamelImageDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public MarjoeeKamelImageDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MarjoeeKamelImageDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MarjoeeKamelImageModel.COLUMN_ccKardex(),
            MarjoeeKamelImageModel.COLUMN_ccMoshtary(),
            MarjoeeKamelImageModel.COLUMN_Image()
        };
    }

    public boolean insertGroup(ArrayList<MarjoeeKamelImageModel> marjoeeKamelImageModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MarjoeeKamelImageModel marjoeeKamelImageModel : marjoeeKamelImageModels)
            {
                ContentValues contentValues = modelToContentvalue(marjoeeKamelImageModel);
                db.insertOrThrow(MarjoeeKamelImageModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorInsert , MarjoeeKamelImageModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeKamelImageDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MarjoeeKamelImageModel> getAll()
    {
        ArrayList<MarjoeeKamelImageModel> marjoeeKamelImageModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MarjoeeKamelImageModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    marjoeeKamelImageModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MarjoeeKamelImageModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeKamelImageDAO" , "" , "getAll" , "");
        }
        return marjoeeKamelImageModels;
    }

    public ArrayList<MarjoeeKamelImageModel> getByCcKardex(long cckardex) {
        ArrayList<MarjoeeKamelImageModel> models = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MarjoeeKamelImageModel.TableName(), allColumns(), MarjoeeKamelImageModel.COLUMN_ccKardex() + " = " + cckardex, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    models = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, KardexModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeKamelImageDAO", "", "getByCcKardex", "");
        }

        return models;
    }


        public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MarjoeeKamelImageModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MarjoeeKamelImageModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeKamelImageDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MarjoeeKamelImageModel marjoeeKamelImageModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MarjoeeKamelImageModel.COLUMN_ccKardex() , marjoeeKamelImageModel.getCcKardex());
        contentValues.put(MarjoeeKamelImageModel.COLUMN_ccMoshtary() , marjoeeKamelImageModel.getCcMoshtary());
        contentValues.put(MarjoeeKamelImageModel.COLUMN_Image() , marjoeeKamelImageModel.getImage());

        return contentValues;
    }


    private ArrayList<MarjoeeKamelImageModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MarjoeeKamelImageModel> marjoeeKamelImageModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MarjoeeKamelImageModel marjoeeKamelImageModel = new MarjoeeKamelImageModel();

            marjoeeKamelImageModel.setCcKardex(cursor.getInt(cursor.getColumnIndex(MarjoeeKamelImageModel.COLUMN_ccKardex())));
            marjoeeKamelImageModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MarjoeeKamelImageModel.COLUMN_ccMoshtary())));
            marjoeeKamelImageModel.setImage(cursor.getBlob(cursor.getColumnIndex(MarjoeeKamelImageModel.COLUMN_Image())));

            marjoeeKamelImageModels.add(marjoeeKamelImageModel);
            cursor.moveToNext();
        }
        return marjoeeKamelImageModels;
    }
    
    
}
