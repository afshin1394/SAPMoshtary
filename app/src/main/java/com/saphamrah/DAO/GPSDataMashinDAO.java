package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.GPSDataMashinModel;
import com.saphamrah.Model.VisitMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class GPSDataMashinDAO
{

    private DBHelper dbHelper;
    private Context context;
    private final static String CLASS_NAME = "GPSDataMashinDAO";

    public GPSDataMashinDAO(Context context)
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
            GPSDataMashinModel.COLUMN_ccGPSData_Mashin(),
            GPSDataMashinModel.COLUMN_ccMashin(),
            GPSDataMashinModel.COLUMN_ccAfradMamorPakhsh(),
            GPSDataMashinModel.COLUMN_ccMamorPakhsh(),
            GPSDataMashinModel.COLUMN_ccDarkhastFaktor(),
            GPSDataMashinModel.COLUMN_ccMoshtary(),
            GPSDataMashinModel.COLUMN_Longitude_x(),
            GPSDataMashinModel.COLUMN_Latitude_y(),
            GPSDataMashinModel.COLUMN_ZamaneSabt()
        };
    }

    public boolean insert(GPSDataMashinModel gpsDataMashinModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(gpsDataMashinModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(GPSDataMashinModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , GPSDataMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<GPSDataMashinModel> getAll()
    {
        ArrayList<GPSDataMashinModel> gpsDataMashinModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(GPSDataMashinModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gpsDataMashinModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GPSDataMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return gpsDataMashinModels;
    }

    public ArrayList<GPSDataMashinModel> getByccMoshtary(int ccMoshtary)
    {
        ArrayList<GPSDataMashinModel> gpsDataMashinModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(GPSDataMashinModel.TableName(), allColumns(), GPSDataMashinModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    gpsDataMashinModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , GPSDataMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return gpsDataMashinModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(GPSDataMashinModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , GPSDataMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(GPSDataMashinModel.TableName(), GPSDataMashinModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , GPSDataMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean deleteByccGPSDataMashins(String ccGPSDataMashins)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(GPSDataMashinModel.TableName(), GPSDataMashinModel.COLUMN_ccGPSData_Mashin() + " in (" + ccGPSDataMashins + ")", null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , GPSDataMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteByccGPSDataMashins" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(GPSDataMashinModel gpsDataMashinModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(GPSDataMashinModel.COLUMN_ccMashin() , gpsDataMashinModel.getCcMashin());
        contentValues.put(GPSDataMashinModel.COLUMN_ccAfradMamorPakhsh() , gpsDataMashinModel.getCcAfradMamorPakhsh());
        contentValues.put(GPSDataMashinModel.COLUMN_ccMamorPakhsh() , gpsDataMashinModel.getCcMamorPakhsh());
        contentValues.put(GPSDataMashinModel.COLUMN_ccDarkhastFaktor() , gpsDataMashinModel.getCcDarkhastFaktor());
        contentValues.put(GPSDataMashinModel.COLUMN_ccMoshtary() , gpsDataMashinModel.getCcMoshtary());
        contentValues.put(GPSDataMashinModel.COLUMN_Longitude_x() , gpsDataMashinModel.getLongitude_x());
        contentValues.put(GPSDataMashinModel.COLUMN_Latitude_y() , gpsDataMashinModel.getLatitude_y());
        contentValues.put(GPSDataMashinModel.COLUMN_ZamaneSabt() , gpsDataMashinModel.getZamaneSabt());

        return contentValues;
    }


    private ArrayList<GPSDataMashinModel> cursorToModel(Cursor cursor)
    {
        ArrayList<GPSDataMashinModel> gpsDataMashinModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            GPSDataMashinModel gpsDataMashinModel = new GPSDataMashinModel();

            gpsDataMashinModel.setCcGPSDataMashin(cursor.getInt(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_ccGPSData_Mashin())));
            gpsDataMashinModel.setCcMashin(cursor.getInt(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_ccMashin())));
            gpsDataMashinModel.setCcAfradMamorPakhsh(cursor.getInt(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_ccAfradMamorPakhsh())));
            gpsDataMashinModel.setCcMamorPakhsh(cursor.getInt(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_ccMamorPakhsh())));
            gpsDataMashinModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_ccDarkhastFaktor())));
            gpsDataMashinModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_ccMoshtary())));
            gpsDataMashinModel.setLongitude_x(cursor.getFloat(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_Longitude_x())));
            gpsDataMashinModel.setLatitude_y(cursor.getFloat(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_Latitude_y())));
            gpsDataMashinModel.setZamaneSabt(cursor.getString(cursor.getColumnIndex(GPSDataMashinModel.COLUMN_ZamaneSabt())));

            gpsDataMashinModels.add(gpsDataMashinModel);
            cursor.moveToNext();
        }
        return gpsDataMashinModels;
    }

}
