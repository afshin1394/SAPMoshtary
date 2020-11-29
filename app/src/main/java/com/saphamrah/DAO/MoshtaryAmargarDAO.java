package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryAmargarModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MoshtaryAmargarDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryAmargarDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryAmargarDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryAmargarModel.COLUMN_ccPorseshnameh(),
            MoshtaryAmargarModel.COLUMN_ccMahal(),
            MoshtaryAmargarModel.COLUMN_NameMoshtary(),
            MoshtaryAmargarModel.COLUMN_NameMaghazeh(),
            MoshtaryAmargarModel.COLUMN_Telephone(),
            MoshtaryAmargarModel.COLUMN_Address(),
            MoshtaryAmargarModel.COLUMN_Longitude_x(),
            MoshtaryAmargarModel.COLUMN_Latitude_y(),
            MoshtaryAmargarModel.COLUMN_Olaviat(),
            MoshtaryAmargarModel.COLUMN_Image()
        };
    }


    public void insertGroup(ArrayList<MoshtaryAmargarModel> moshtaryAmargarModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryAmargarModel moshtaryAmargarModel : moshtaryAmargarModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryAmargarModel);
                db.insertOrThrow(MoshtaryAmargarModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryAmargarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAmargarDAO" , "" , "insertGroup" , "");
        }
    }


    public ArrayList<MoshtaryAmargarModel> getAll()
    {
        ArrayList<MoshtaryAmargarModel> moshtaryAmargarModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryAmargarModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAmargarModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAmargarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAmargarDAO" , "" , "getAll" , "");
        }
        return moshtaryAmargarModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAmargarModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryAmargarModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryAmargarDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryAmargarModel moshtaryAmargarModel)
    {
        ContentValues contentValues = new ContentValues();

        if (moshtaryAmargarModel.getCcPorseshnameh() > 0)
        {
            contentValues.put(MoshtaryAmargarModel.COLUMN_ccPorseshnameh() , moshtaryAmargarModel.getCcPorseshnameh());
        }
        contentValues.put(MoshtaryAmargarModel.COLUMN_ccMahal() , moshtaryAmargarModel.getCcMahal());
        contentValues.put(MoshtaryAmargarModel.COLUMN_NameMoshtary() , moshtaryAmargarModel.getNameMoshtary());
        contentValues.put(MoshtaryAmargarModel.COLUMN_NameMaghazeh() , moshtaryAmargarModel.getNameMaghazeh());
        contentValues.put(MoshtaryAmargarModel.COLUMN_Telephone() , moshtaryAmargarModel.getTelephone());
        contentValues.put(MoshtaryAmargarModel.COLUMN_Address() , moshtaryAmargarModel.getAddress());
        contentValues.put(MoshtaryAmargarModel.COLUMN_Longitude_x() , moshtaryAmargarModel.getLongitude_x());
        contentValues.put(MoshtaryAmargarModel.COLUMN_Latitude_y() , moshtaryAmargarModel.getLatitude_y());
        contentValues.put(MoshtaryAmargarModel.COLUMN_Olaviat() , moshtaryAmargarModel.getOlaviat());
        contentValues.put(MoshtaryAmargarModel.COLUMN_Image() , moshtaryAmargarModel.getImage());

        return contentValues;
    }


    private ArrayList<MoshtaryAmargarModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryAmargarModel> moshtaryAmargarModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryAmargarModel moshtaryAmargarModel = new MoshtaryAmargarModel();

            moshtaryAmargarModel.setCcPorseshnameh(cursor.getInt(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_ccPorseshnameh())));
            moshtaryAmargarModel.setCcMahal(cursor.getInt(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_ccMahal())));
            moshtaryAmargarModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_NameMoshtary())));
            moshtaryAmargarModel.setNameMaghazeh(cursor.getString(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_NameMaghazeh())));
            moshtaryAmargarModel.setTelephone(cursor.getInt(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_Telephone())));
            moshtaryAmargarModel.setAddress(cursor.getString(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_Address())));
            moshtaryAmargarModel.setLongitude_x(cursor.getString(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_Longitude_x())));
            moshtaryAmargarModel.setLatitude_y(cursor.getString(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_Latitude_y())));
            moshtaryAmargarModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_Olaviat())));
            moshtaryAmargarModel.setImage(cursor.getBlob(cursor.getColumnIndex(MoshtaryAmargarModel.COLUMN_Image())));

            moshtaryAmargarModels.add(moshtaryAmargarModel);
            cursor.moveToNext();
        }
        return moshtaryAmargarModels;
    }
    
    
    
}
