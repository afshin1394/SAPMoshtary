package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MarjoeeKamelImageTmpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MarjoeeKamelImageTmpDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MarjoeeKamelImageTmpDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MarjoeeKamelImageTmpDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MarjoeeKamelImageTmpModel.COLUMN_ccMoshtary(),
            MarjoeeKamelImageTmpModel.COLUMN_Image()
        };
    }

    public boolean insertGroup(ArrayList<MarjoeeKamelImageTmpModel> marjoeeKamelImageTmpModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MarjoeeKamelImageTmpModel marjoeeKamelImageTmpModel : marjoeeKamelImageTmpModels)
            {
                ContentValues contentValues = modelToContentvalue(marjoeeKamelImageTmpModel);
                db.insertOrThrow(MarjoeeKamelImageTmpModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MarjoeeKamelImageTmpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeKamelImageTmpDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MarjoeeKamelImageTmpModel> getAll()
    {
        ArrayList<MarjoeeKamelImageTmpModel> marjoeeKamelImageTmpModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MarjoeeKamelImageTmpModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    marjoeeKamelImageTmpModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MarjoeeKamelImageTmpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeKamelImageTmpDAO" , "" , "getAll" , "");
        }
        return marjoeeKamelImageTmpModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MarjoeeKamelImageTmpModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MarjoeeKamelImageTmpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeKamelImageTmpDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MarjoeeKamelImageTmpModel marjoeeKamelImageTmpModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MarjoeeKamelImageTmpModel.COLUMN_ccMoshtary() , marjoeeKamelImageTmpModel.getCcMoshtary());
        contentValues.put(MarjoeeKamelImageTmpModel.COLUMN_Image() , marjoeeKamelImageTmpModel.getImage());

        return contentValues;
    }


    private ArrayList<MarjoeeKamelImageTmpModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MarjoeeKamelImageTmpModel> marjoeeKamelImageTmpModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MarjoeeKamelImageTmpModel marjoeeKamelImageTmpModel = new MarjoeeKamelImageTmpModel();

            marjoeeKamelImageTmpModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MarjoeeKamelImageTmpModel.COLUMN_ccMoshtary())));
            marjoeeKamelImageTmpModel.setImage(cursor.getBlob(cursor.getColumnIndex(MarjoeeKamelImageTmpModel.COLUMN_Image())));

            marjoeeKamelImageTmpModels.add(marjoeeKamelImageTmpModel);
            cursor.moveToNext();
        }
        return marjoeeKamelImageTmpModels;
    }



}
