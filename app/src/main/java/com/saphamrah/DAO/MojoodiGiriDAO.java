package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MojoodiGiriModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MojoodiGiriDAO
{


    private DBHelper dbHelper;
    private Context context;


    public MojoodiGiriDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MojoodiGiriDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MojoodiGiriModel.COLUMN_ccMojoodiGiri(),
            MojoodiGiriModel.COLUMN_ccForoshandeh(),
            MojoodiGiriModel.COLUMN_ccMoshtary(),
            MojoodiGiriModel.COLUMN_ccKalaCode(),
            MojoodiGiriModel.COLUMN_Tarikh(),
            MojoodiGiriModel.COLUMN_TedadMojoodiGiri(),
            MojoodiGiriModel.COLUMN_ToorVisit(),
            MojoodiGiriModel.COLUMN_TedadPishnahady(),
            MojoodiGiriModel.COLUMN_SaatVorod(),
            MojoodiGiriModel.COLUMN_SaatKhoroj(),
            MojoodiGiriModel.COLUMN_Latitude(),
            MojoodiGiriModel.COLUMN_Longitude(),
            MojoodiGiriModel.COLUMN_InsertInPPC(),
            MojoodiGiriModel.COLUMN_IsOld()
        };
    }


    public void insertGroup(ArrayList<MojoodiGiriModel> mojoodiGiriModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MojoodiGiriModel mojoodiGiriModel : mojoodiGiriModels)
            {
                ContentValues contentValues = modelToContentvalue(mojoodiGiriModel);
                db.insertOrThrow(MojoodiGiriModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "insertGroup" , "");
        }
    }


    public boolean insert(MojoodiGiriModel mojoodiGiriModel)
    {
        ContentValues contentValues = modelToContentvalue(mojoodiGiriModel);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(MojoodiGiriModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "insert" , "");
            return false;
        }
    }


    public ArrayList<MojoodiGiriModel> getAll()
    {
        ArrayList<MojoodiGiriModel> mojoodiGiriModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MojoodiGiriModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mojoodiGiriModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "getAll" , "");
        }
        return mojoodiGiriModels;
    }

    public ArrayList<MojoodiGiriModel> getForNewFaktor(int ccForoshandeh, int ccMoshtary)
    {
        ArrayList<MojoodiGiriModel> mojoodiGiriModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String date= new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
            String qyery = "SELECT DISTINCT 0 AS ccMojoodiGiri, " + ccForoshandeh + " AS ccForoshandeh, " + ccMoshtary + " AS ccMoshtary, ccKalaCode, '" + date + "' AS Tarikh, "
                    + "       IFNULL((SELECT SUM(TedadMojoodiGiri) FROM MojoodiGiri WHERE ccMoshtary= " + ccMoshtary + " AND ccKalaCode= Kala.ccKalaCode AND InsertINPPC= 1), -1) AS TedadMojoodiGiri, "
                    + "       0 AS ToorVisit, 0 AS TedadPishnahady, "
                    + "       '" + date + "' AS SaatVorod, '" + date + "' AS SaatKhoroj, 0 AS Latitude, 0 AS Longitude, 1 AS InsertInPPC, 0 AS IsOld "
                    + "  FROM Kala "
                    + "  WHERE ccKalaCode IN(SELECT ccKalaCode FROM KalaZaribForosh WHERE ZaribForosh > 0 ) "
                    + "        AND ccKalaCode Not IN(select ccKalaCode FROM KalaGoroh WHERE ccGoroh = 636)  "
                    + "  ORDER BY CodeSort";
            Cursor cursor = db.rawQuery(qyery ,null);

            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mojoodiGiriModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "getForNewFaktor" , "");
        }

        return mojoodiGiriModels;
    }

    public MojoodiGiriModel getByMoshtaryAndKalaCode(int ccMoshtary, int ccKalaCode, boolean InsertInPPC)
    {
        MojoodiGiriModel mojoodiGiriModel = new MojoodiGiriModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MojoodiGiriModel.TableName(), allColumns(), "ccMoshtary= ? AND ccKalaCode= ? AND InsertInPPC= ?", new String[]{String.valueOf(ccMoshtary), String.valueOf(ccKalaCode), String.valueOf(InsertInPPC? 1: 0)}, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mojoodiGiriModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "getByMoshtaryAndKalaCode" , "");
        }
        return mojoodiGiriModel;
    }

    public int getCountByMoshtary(int ccMoshtary, boolean insertInPPC)
    {
        int count = -1;
        String strInsertInPPC = String.valueOf(insertInPPC ? 1: 0);
        String query = "select count(*) from " + MojoodiGiriModel.getTableName() + " where " +
                MojoodiGiriModel.getCOLUMN_ccMoshtary() +  " = " + ccMoshtary + " and " +
                MojoodiGiriModel.getCOLUMN_InsertInPPC() + " = " + strInsertInPPC + " and (" +
                MojoodiGiriModel.getCOLUMN_TedadMojoodiGiri() + " >=0 or " +  MojoodiGiriModel.getCOLUMN_TedadPishnahady() + " > 0 )";
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
            String message = context.getResources().getString(R.string.errorSelectAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "getCount" , "");
        }
        return count;
    }

    public ArrayList<MojoodiGiriModel> getByccMoshtaryAndNotOld(int ccMoshtary)
    {
        ArrayList<MojoodiGiriModel> mojoodiGiriModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MojoodiGiriModel.TableName(), allColumns(), MojoodiGiriModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and " + MojoodiGiriModel.COLUMN_IsOld() + " = 0", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mojoodiGiriModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "getByccMoshtaryAndNotOld" , "");
        }
        return mojoodiGiriModels;
    }

    public int getCountMojodiGiriByMoshtaryForCheck(int ccMoshtary, boolean InsertInPPC)
    {
        int count = 0;
        String query = "select count(*) from " + MojoodiGiriModel.TableName() + " where ccMoshtary = " + ccMoshtary + " and InsertInPPC = " + String.valueOf(InsertInPPC? 1: 0);
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                count = cursor.getInt(0);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "GetCountMojodiGiriByMoshtaryForCheck" , "");
        }
        return count;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MojoodiGiriModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMojodiGiri(int ccMojodiGiri)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MojoodiGiriModel.TableName(), MojoodiGiriModel.COLUMN_ccMojoodiGiri() + " = " + ccMojodiGiri, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "deleteByccMojodiGiri" , "");
            return false;
        }
    }

    public boolean update(MojoodiGiriModel mojoodiGiriModel)
    {
        try
        {
            String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
            ContentValues contentValues = new ContentValues();
            contentValues.put(MojoodiGiriModel.COLUMN_ccForoshandeh() , mojoodiGiriModel.getCcForoshandeh());
            contentValues.put(MojoodiGiriModel.COLUMN_ccMoshtary() , mojoodiGiriModel.getCcMoshtary());
            contentValues.put(MojoodiGiriModel.COLUMN_ccKalaCode() , mojoodiGiriModel.getCcKalaCode());
            if(mojoodiGiriModel.getTarikh() != null)
            {
                contentValues.put("Tarikh", currentDate);
            }
            contentValues.put(MojoodiGiriModel.COLUMN_TedadMojoodiGiri() , mojoodiGiriModel.getTedadMojoodiGiri());
            contentValues.put(MojoodiGiriModel.COLUMN_ToorVisit() , mojoodiGiriModel.getToorVisit());
            contentValues.put(MojoodiGiriModel.COLUMN_TedadPishnahady() , mojoodiGiriModel.getTedadPishnahady());
            if(mojoodiGiriModel.getSaatVorod() != null)
            {
                contentValues.put("SaatVorod", currentDate);
            }
            if(mojoodiGiriModel.getSaatKhoroj() != null)
            {
                contentValues.put("SaatKhoroj", currentDate);
            }
            contentValues.put(MojoodiGiriModel.COLUMN_Latitude(), mojoodiGiriModel.getLatitude());
            contentValues.put(MojoodiGiriModel.COLUMN_Longitude(), mojoodiGiriModel.getLongitude());
            contentValues.put(MojoodiGiriModel.COLUMN_InsertInPPC(), mojoodiGiriModel.getInsertInPPC());
            contentValues.put(MojoodiGiriModel.COLUMN_IsOld(), mojoodiGiriModel.getOld());

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(MojoodiGiriModel.TableName(), contentValues, "ccMojoodiGiri = ?", new String[]{String.valueOf(mojoodiGiriModel.getCcMojoodiGiri())});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "update" , "");
            return false;
        }
    }

    public boolean updateSentToServer(String ccMojoodiGiris)
    {
        String query = "update " + MojoodiGiriModel.TableName() + " set " + MojoodiGiriModel.getCOLUMN_IsOld() + " = 1 where " + MojoodiGiriModel.COLUMN_ccMojoodiGiri() + " in (" + ccMojoodiGiris + ")";
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
            String message = context.getResources().getString(R.string.errorDeleteAll , MojoodiGiriModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MojoodiGiriDAO" , "" , "updateSentToServer" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MojoodiGiriModel mojoodiGiriModel)
    {
        ContentValues contentValues = new ContentValues();

        if (mojoodiGiriModel.getCcMojoodiGiri() > 0)
        {
            contentValues.put(MojoodiGiriModel.COLUMN_ccMojoodiGiri() , mojoodiGiriModel.getCcMojoodiGiri());
        }
        contentValues.put(MojoodiGiriModel.COLUMN_ccForoshandeh() , mojoodiGiriModel.getCcForoshandeh());
        contentValues.put(MojoodiGiriModel.COLUMN_ccMoshtary() , mojoodiGiriModel.getCcMoshtary());
        contentValues.put(MojoodiGiriModel.COLUMN_ccKalaCode() , mojoodiGiriModel.getCcKalaCode());
        contentValues.put(MojoodiGiriModel.COLUMN_Tarikh() , mojoodiGiriModel.getTarikh());
        contentValues.put(MojoodiGiriModel.COLUMN_TedadMojoodiGiri() , mojoodiGiriModel.getTedadMojoodiGiri());
        contentValues.put(MojoodiGiriModel.COLUMN_ToorVisit() , mojoodiGiriModel.getToorVisit());
        contentValues.put(MojoodiGiriModel.COLUMN_TedadPishnahady() , mojoodiGiriModel.getTedadPishnahady());
        contentValues.put(MojoodiGiriModel.COLUMN_SaatVorod() , mojoodiGiriModel.getSaatVorod());
        contentValues.put(MojoodiGiriModel.COLUMN_SaatKhoroj() , mojoodiGiriModel.getSaatKhoroj());
        contentValues.put(MojoodiGiriModel.COLUMN_Latitude() , mojoodiGiriModel.getLatitude());
        contentValues.put(MojoodiGiriModel.COLUMN_Longitude() , mojoodiGiriModel.getLongitude());
        contentValues.put(MojoodiGiriModel.COLUMN_InsertInPPC() , mojoodiGiriModel.getInsertInPPC());
        contentValues.put(MojoodiGiriModel.COLUMN_IsOld() , mojoodiGiriModel.getOld());

        return contentValues;
    }

    private ArrayList<MojoodiGiriModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MojoodiGiriModel> mojoodiGiriModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MojoodiGiriModel mojoodiGiriModel = new MojoodiGiriModel();

            mojoodiGiriModel.setCcMojoodiGiri(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccMojoodiGiri())));
            mojoodiGiriModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccForoshandeh())));
            mojoodiGiriModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccMoshtary())));
            mojoodiGiriModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccKalaCode())));
            mojoodiGiriModel.setTarikh(cursor.getString(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_Tarikh())));
            mojoodiGiriModel.setTedadMojoodiGiri(cursor.getFloat(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_TedadMojoodiGiri())));
            mojoodiGiriModel.setToorVisit(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ToorVisit())));
            mojoodiGiriModel.setTedadPishnahady(cursor.getFloat(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_TedadPishnahady())));
            mojoodiGiriModel.setSaatVorod(cursor.getString(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_SaatVorod())));
            mojoodiGiriModel.setSaatKhoroj(cursor.getString(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_SaatKhoroj())));
            mojoodiGiriModel.setLatitude(cursor.getFloat(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_Latitude())));
            mojoodiGiriModel.setLongitude(cursor.getFloat(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_Longitude())));
            mojoodiGiriModel.setInsertInPPC(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_InsertInPPC())) > 0); // compare with zero for convert int to boolean
            mojoodiGiriModel.setOld(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_IsOld())) > 0); // compare with zero for convert int to boolean

            mojoodiGiriModels.add(mojoodiGiriModel);
            cursor.moveToNext();
        }
        return mojoodiGiriModels;
    }
    
}
