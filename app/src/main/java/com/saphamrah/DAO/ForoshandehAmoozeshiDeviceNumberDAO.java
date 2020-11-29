package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ForoshandehAmoozeshiDeviceNumberDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ForoshandehAmoozeshiDeviceNumberDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehAmoozeshiDeviceNumberDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            ForoshandehAmoozeshiModel.COLUMN_ccDeviceNumber(),
            ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAmoozeshi(),
            ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAsli()
        };
    }


    public boolean insertGroup(List<ForoshandehAmoozeshiModel> listForoshandehAmoozeshi)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ForoshandehAmoozeshiModel foroshandehAmoozeshiModel : listForoshandehAmoozeshi)
            {
                ContentValues contentValues = modelToContentValue(foroshandehAmoozeshiModel);
                db.insertOrThrow(ForoshandehAmoozeshiModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ForoshandehAmoozeshiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ForoshandehAmoozeshiDeviceNumberDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ForoshandehAmoozeshiModel> getAll()
    {
        ArrayList<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehAmoozeshiModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehAmoozeshiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehAmoozeshiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehAmoozeshiDeviceNumberDAO" , "" , "getAll" , "");
        }
        return foroshandehAmoozeshiModels;
    }


    public boolean deleteAll()
    {
        String query = "delete from " + ForoshandehAmoozeshiModel.TableName();
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ForoshandehAmoozeshiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ForoshandehAmoozeshiDeviceNumberDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private ContentValues modelToContentValue(ForoshandehAmoozeshiModel foroshandehAmoozeshiModel)
    {
        ContentValues contentValues = new ContentValues();
        if (foroshandehAmoozeshiModel.getCcDeviceNumber() != null && foroshandehAmoozeshiModel.getCcDeviceNumber() > 0)
        {
            contentValues.put(ForoshandehAmoozeshiModel.COLUMN_ccDeviceNumber() , foroshandehAmoozeshiModel.getCcDeviceNumber());
        }
        contentValues.put(ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAmoozeshi() , foroshandehAmoozeshiModel.getDeviceNumberForoshandehAmoozeshi());
        contentValues.put(ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAsli() , foroshandehAmoozeshiModel.getDeviceNumberForoshandehAsli());
        return contentValues;
    }


    private ArrayList<ForoshandehAmoozeshiModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ForoshandehAmoozeshiModel foroshandehAmoozeshiModel = new ForoshandehAmoozeshiModel();

            foroshandehAmoozeshiModel.setCcDeviceNumber(cursor.getInt(cursor.getColumnIndex(ForoshandehAmoozeshiModel.COLUMN_ccDeviceNumber())));
            foroshandehAmoozeshiModel.setDeviceNumberForoshandehAmoozeshi(cursor.getString(cursor.getColumnIndex(ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAmoozeshi())));
            foroshandehAmoozeshiModel.setDeviceNumberForoshandehAsli(cursor.getString(cursor.getColumnIndex(ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAsli())));

            foroshandehAmoozeshiModels.add(foroshandehAmoozeshiModel);
            cursor.moveToNext();
        }
        return foroshandehAmoozeshiModels;
    }

}
