package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MaxFaktorMandehDarModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MaxFaktorMandehDarDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MaxFaktorMandehDarDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MaxFaktorMandehDarDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MaxFaktorMandehDarModel.COLUMN_ccMaxFaktorMandehDar(),
            MaxFaktorMandehDarModel.COLUMN_ccMarkazForosh(),
            MaxFaktorMandehDarModel.COLUMN_ccGorohMoshtary(),
            MaxFaktorMandehDarModel.COLUMN_MaxTedad(),
            MaxFaktorMandehDarModel.COLUMN_MaxRooz()
        };
    }


    public boolean insertGroup(ArrayList<MaxFaktorMandehDarModel> maxFaktorMandehDarModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MaxFaktorMandehDarModel maxFaktorMandehDarModel : maxFaktorMandehDarModels)
            {
                ContentValues contentValues = modelToContentvalue(maxFaktorMandehDarModel);
                db.insertOrThrow(MaxFaktorMandehDarModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MaxFaktorMandehDarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MaxFaktorMandehDarDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<MaxFaktorMandehDarModel> getAll()
    {
        ArrayList<MaxFaktorMandehDarModel> maxFaktorMandehDarModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MaxFaktorMandehDarModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    maxFaktorMandehDarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MaxFaktorMandehDarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MaxFaktorMandehDarDAO" , "" , "getAll" , "");
        }
        return maxFaktorMandehDarModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MaxFaktorMandehDarModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MaxFaktorMandehDarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MaxFaktorMandehDarDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MaxFaktorMandehDarModel maxFaktorMandehDarModel)
    {
        ContentValues contentValues = new ContentValues();

        if (maxFaktorMandehDarModel.getCcMaxFaktorMandehDar() > 0)
        {
            contentValues.put(MaxFaktorMandehDarModel.COLUMN_ccMaxFaktorMandehDar() , maxFaktorMandehDarModel.getCcMaxFaktorMandehDar());
        }
        contentValues.put(MaxFaktorMandehDarModel.COLUMN_ccMarkazForosh() , maxFaktorMandehDarModel.getCcMarkazForosh());
        contentValues.put(MaxFaktorMandehDarModel.COLUMN_ccGorohMoshtary() , maxFaktorMandehDarModel.getCcGorohMoshtary());
        contentValues.put(MaxFaktorMandehDarModel.COLUMN_MaxTedad() , maxFaktorMandehDarModel.getMaxTedad());
        contentValues.put(MaxFaktorMandehDarModel.COLUMN_MaxRooz() , maxFaktorMandehDarModel.getMaxRooz());

        return contentValues;
    }


    private ArrayList<MaxFaktorMandehDarModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MaxFaktorMandehDarModel> maxFaktorMandehDarModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MaxFaktorMandehDarModel maxFaktorMandehDarModel = new MaxFaktorMandehDarModel();

            maxFaktorMandehDarModel.setCcMaxFaktorMandehDar(cursor.getInt(cursor.getColumnIndex(MaxFaktorMandehDarModel.COLUMN_ccMaxFaktorMandehDar())));
            maxFaktorMandehDarModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(MaxFaktorMandehDarModel.COLUMN_ccMarkazForosh())));
            maxFaktorMandehDarModel.setCcGorohMoshtary(cursor.getInt(cursor.getColumnIndex(MaxFaktorMandehDarModel.COLUMN_ccGorohMoshtary())));
            maxFaktorMandehDarModel.setMaxTedad(cursor.getInt(cursor.getColumnIndex(MaxFaktorMandehDarModel.COLUMN_MaxTedad())));
            maxFaktorMandehDarModel.setMaxRooz(cursor.getInt(cursor.getColumnIndex(MaxFaktorMandehDarModel.COLUMN_MaxRooz())));

            maxFaktorMandehDarModels.add(maxFaktorMandehDarModel);
            cursor.moveToNext();
        }
        return maxFaktorMandehDarModels;
    }


}
