package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.AdamDarkhastModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdamDarkhastDAO
{

    private DBHelper dbHelper;
    private Context context;

    public AdamDarkhastDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "AdamDarkhastDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            AdamDarkhastModel.COLUMN_ccAdamDarkhast(),
            AdamDarkhastModel.COLUMN_ccForoshandeh(),
            AdamDarkhastModel.COLUMN_ccMoshtary(),
            AdamDarkhastModel.COLUMN_ccElatAdamDarkhast(),
            AdamDarkhastModel.COLUMN_TarikhAdamDarkhast(),
            AdamDarkhastModel.COLUMN_Latitude(),
            AdamDarkhastModel.COLUMN_Longitude(),
            AdamDarkhastModel.COLUMN_IsSentToServer(),
            AdamDarkhastModel.COLUMN_AdamDarkhastImage(),
            AdamDarkhastModel.COLUMN_CodeMoshtaryTekrari(),
            AdamDarkhastModel.COLUMN_SaatVorod()
        };
    }


    public boolean insertGroup(ArrayList<AdamDarkhastModel> adamDarkhastModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (AdamDarkhastModel adamDarkhastModel : adamDarkhastModels)
            {
                ContentValues contentValues = modelToContentvalue(adamDarkhastModel);
                db.insertOrThrow(AdamDarkhastModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(AdamDarkhastModel adamDarkhastModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(adamDarkhastModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(AdamDarkhastModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<AdamDarkhastModel> getAll()
    {
        ArrayList<AdamDarkhastModel> adamDarkhastModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AdamDarkhastModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    adamDarkhastModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "getAll" , "");
        }
        return adamDarkhastModels;
    }

    public ArrayList<AdamDarkhastModel> getByccMoshtary(int ccMoshtary)
    {
        ArrayList<AdamDarkhastModel> adamDarkhastModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AdamDarkhastModel.TableName(), allColumns(), AdamDarkhastModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    adamDarkhastModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "getByccMoshtary" , "");
        }
        return adamDarkhastModels;
    }

    public int getCountByccMoshtary(int ccMoshtary)
    {
        int count = -1;
        String query = "select count(*) from " + AdamDarkhastModel.TableName() + " where " + AdamDarkhastModel.COLUMN_ccMoshtary() + " = " + ccMoshtary;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
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
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "getCountByccMoshtary" , "");
        }
        return count;
    }

    public ArrayList<AdamDarkhastModel> getByccAdamDarkhast(int ccAdamDarkhast)
    {
        ArrayList<AdamDarkhastModel> adamDarkhastModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AdamDarkhastModel.TableName(), allColumns(), AdamDarkhastModel.COLUMN_ccAdamDarkhast() + " = " + ccAdamDarkhast, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    adamDarkhastModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "getByccMoshtary" , "");
        }
        return adamDarkhastModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AdamDarkhastModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccAdamDarkhast(int ccAdamDarkhast)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AdamDarkhastModel.TableName(), AdamDarkhastModel.COLUMN_ccAdamDarkhast() + " = " + ccAdamDarkhast, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "deleteByccAdamDarkhast" , "");
            return false;
        }
    }

    public boolean updateIsSentToServer(int value , int ccAdamDarkhast)
    {
        try
        {
            String query = "update " + AdamDarkhastModel.TableName() + " set " + AdamDarkhastModel.COLUMN_IsSentToServer() + " = " + value +
                    " where " + AdamDarkhastModel.COLUMN_ccAdamDarkhast() + " = " + ccAdamDarkhast;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "AdamDarkhastDAO" , "" , "updateIsSentToServer" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(AdamDarkhastModel adamDarkhastModel)
    {
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());

        if (adamDarkhastModel.getCcAdamDarkhast() > 0)
        {
            contentValues.put(AdamDarkhastModel.COLUMN_ccAdamDarkhast() , adamDarkhastModel.getCcAdamDarkhast());
        }
        contentValues.put(AdamDarkhastModel.COLUMN_ccForoshandeh() , adamDarkhastModel.getCcForoshandeh());
        contentValues.put(AdamDarkhastModel.COLUMN_ccMoshtary() , adamDarkhastModel.getCcMoshtary());
        contentValues.put(AdamDarkhastModel.COLUMN_ccElatAdamDarkhast() , adamDarkhastModel.getCcElatAdamDarkhast());
        contentValues.put(AdamDarkhastModel.COLUMN_TarikhAdamDarkhast() , simpleDateFormat.format(adamDarkhastModel.getDateAdamDarkhast()));
        contentValues.put(AdamDarkhastModel.COLUMN_Latitude() , adamDarkhastModel.getLatitude());
        contentValues.put(AdamDarkhastModel.COLUMN_Longitude() , adamDarkhastModel.getLongitude());
        contentValues.put(AdamDarkhastModel.COLUMN_IsSentToServer() , adamDarkhastModel.getIsSentToServer());
        contentValues.put(AdamDarkhastModel.COLUMN_AdamDarkhastImage() , adamDarkhastModel.getAdamDarkhastImage());
        contentValues.put(AdamDarkhastModel.COLUMN_CodeMoshtaryTekrari() , adamDarkhastModel.getCodeMoshtaryTekrari());
        contentValues.put(AdamDarkhastModel.COLUMN_SaatVorod() , simpleDateFormat.format(adamDarkhastModel.getSaatVorod()));

        return contentValues;
    }


    private ArrayList<AdamDarkhastModel> cursorToModel(Cursor cursor)
    {
        ArrayList<AdamDarkhastModel> adamDarkhastModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            AdamDarkhastModel adamDarkhastModel = new AdamDarkhastModel();
            Date dateAdamDarkhast = new Date();
            Date dateVorod = new Date();
            try
            {
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                dateVorod = formatter.parse(cursor.getString(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_SaatVorod())));
                dateAdamDarkhast = formatter.parse(cursor.getString(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_TarikhAdamDarkhast())));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "AdamDarkhastDAO" , "" , "cursorToModel" , "");
            }
            adamDarkhastModel.setCcAdamDarkhast(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_ccAdamDarkhast())));
            adamDarkhastModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_ccForoshandeh())));
            adamDarkhastModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_ccMoshtary())));
            adamDarkhastModel.setCcElatAdamDarkhast(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_ccElatAdamDarkhast())));
            adamDarkhastModel.setDateAdamDarkhast(dateAdamDarkhast);
            adamDarkhastModel.setLatitude(cursor.getFloat(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_Latitude())));
            adamDarkhastModel.setLongitude(cursor.getFloat(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_Longitude())));
            adamDarkhastModel.setIsSentToServer(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_IsSentToServer())) > 0);
            adamDarkhastModel.setAdamDarkhastImage(cursor.getBlob(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_AdamDarkhastImage())));
            adamDarkhastModel.setCodeMoshtaryTekrari(cursor.getString(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_CodeMoshtaryTekrari())));
            adamDarkhastModel.setSaatVorod(dateVorod);

            adamDarkhastModels.add(adamDarkhastModel);
            cursor.moveToNext();
        }
        return adamDarkhastModels;
    }


}
