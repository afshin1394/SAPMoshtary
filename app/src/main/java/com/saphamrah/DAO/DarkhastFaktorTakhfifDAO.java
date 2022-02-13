package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class DarkhastFaktorTakhfifDAO
{

    private DBHelper dbHelper;
    private Context context;


    public DarkhastFaktorTakhfifDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorTakhfifDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktorTakhfif(),
            DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor(),
            DarkhastFaktorTakhfifModel.COLUMN_ccTakhfif(),
            DarkhastFaktorTakhfifModel.COLUMN_SharhTakhfif(),
            DarkhastFaktorTakhfifModel.COLUMN_CodeNoeTakhfif(),
            DarkhastFaktorTakhfifModel.COLUMN_DarsadTakhfif(),
            DarkhastFaktorTakhfifModel.COLUMN_MablaghTakhfif(),
            DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ForJayezeh(),
            DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_IsTakhfifMazad(),
            DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_MustSendToSql(),
            DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ccJayezehTakhfif()
        };
    }


    public boolean insertGroup(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DarkhastFaktorTakhfifModel darkhastFaktorTakhfifModel : darkhastFaktorTakhfifModels)
            {
                ContentValues contentValues = modelToContentvalue(darkhastFaktorTakhfifModel);
                db.insertOrThrow(DarkhastFaktorTakhfifModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(DarkhastFaktorTakhfifModel darkhastFaktorTakhfifModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(darkhastFaktorTakhfifModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(DarkhastFaktorTakhfifModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorTakhfifDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<DarkhastFaktorTakhfifModel> getAll()
    {
        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorTakhfifModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorTakhfifModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorTakhfifModels;
    }


    public ArrayList<DarkhastFaktorTakhfifModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorTakhfifModel.TableName(), allColumns(), DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ForJayezeh() + " = 0 ", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorTakhfifModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorTakhfifModels;
    }

    public ArrayList<DarkhastFaktorTakhfifModel> getByccDarkhastFaktorAndNoeTakhfif(long ccDarkhastFaktor , int codeNoeTakhfif)
    {
        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorTakhfifModel.TableName(), allColumns(), DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorTakhfifModel.COLUMN_CodeNoeTakhfif() + " = " + codeNoeTakhfif , null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorTakhfifModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorTakhfifModels;
    }

    public double getSumMablaghByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        double sumMablagh = 0;
        try
        {
            String query = "select sum(" + DarkhastFaktorTakhfifModel.COLUMN_MablaghTakhfif() + ") from " + DarkhastFaktorTakhfifModel.TableName() + " where " + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablagh = cursor.getDouble(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getSumMablaghByccDarkhastFaktor" , "");
        }
        return sumMablagh;
    }

	
	public double getSumMablagh(long ccDarkhastFaktor)
    {
        double sumMablaghTakhfif = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select sum(" + DarkhastFaktorTakhfifModel.COLUMN_MablaghTakhfif() + ") from " + DarkhastFaktorTakhfifModel.TableName() +
                    " where " + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablaghTakhfif = cursor.getDouble(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getSumMablagh" , "");
        }
        return sumMablaghTakhfif;
    }

    public double getSumMablaghByccDarkhastFaktorAndNoeTakhfif(long ccDarkhastFaktor, String noeTakhfifs)
    {
        double sumMablagh = 0;
        try
        {
            String query = "select sum(" + DarkhastFaktorTakhfifModel.COLUMN_MablaghTakhfif() + ") from " + DarkhastFaktorTakhfifModel.TableName() + " where " + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor +
                    " and " + DarkhastFaktorTakhfifModel.COLUMN_CodeNoeTakhfif() + " in (" + noeTakhfifs + ")";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablagh = cursor.getDouble(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getSumMablaghByccDarkhastFaktor" , "");
        }
        return sumMablagh;
    }

    public ArrayList<DarkhastFaktorTakhfifModel> getByccDarkhastFaktorsAndccTakhfifs(long ccDarkhastFaktors , String ccTakhfifs)
    {
        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorTakhfifModel.TableName(), allColumns(), DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktors + " and " + DarkhastFaktorTakhfifModel.COLUMN_ccTakhfif() + " in (" + ccTakhfifs + ")", null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorTakhfifModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getByccDarkhastFaktorAndHaveJayezeh" , "");
        }
        return darkhastFaktorTakhfifModels;
    }

    public long getByccDarkhastFaktorsAndccTakhfifs(long ccDarkhastFaktor , int ccTakhfif , long mablaghTakhfif)
    {
        long sumMablaghTakhfif = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select sum(MablaghTakhfif) from DarkhastFaktorTakhfif where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ccTakhfif = " + ccTakhfif + " and MablaghTakhfif = " + mablaghTakhfif;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumMablaghTakhfif = cursor.getLong(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getByccDarkhastFaktorsAndccTakhfifs" , "");
        }
        return sumMablaghTakhfif;
    }

    public int getCountJayezehByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        int countJayezeh = 0;
        try
        {
            String query = "select count(" + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + ") from " + DarkhastFaktorTakhfifModel.TableName() +
                    " where " + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor +
                    " and " + DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ForJayezeh() + " = 1";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    countJayezeh = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getCountJayezehByccDarkhastFaktor" , "");
        }
        return countJayezeh;
    }

    public ArrayList<Integer> getccTakhfifOfJayezehByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<Integer> ccTakhfifs = new ArrayList<>();
        try
        {
            String query = "select " + DarkhastFaktorTakhfifModel.COLUMN_ccTakhfif() + " from " + DarkhastFaktorTakhfifModel.TableName() +
                    " where " + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor +
                    " and " + DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ForJayezeh() + " = 1";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            Log.d("bouns","getccTakhfifOfJayezehByccDarkhastFaktor query:" + query);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast())
                    {
                        ccTakhfifs.add(cursor.getInt(0));
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
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "getCountJayezehByccDarkhastFaktor" , "");
        }
        return ccTakhfifs;
    }

    public boolean updateSendedDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew)
    {
        try
        {
            String query = "update " + DarkhastFaktorTakhfifModel.TableName() + " set " + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew +
                    " where " + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorTakhfifDAO" , "" , "updateSendedDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean updateMablaghTakhfif(long ccDarkhastFaktor , int ccTakhfif , long mablaghTakhfifTitr , long mablaghTakhfifSatr)
    {
        try
        {
            String query = "update " + DarkhastFaktorTakhfifModel.TableName() + " set " + DarkhastFaktorTakhfifModel.COLUMN_MablaghTakhfif() + " = " + mablaghTakhfifSatr +
                    " where " + DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + DarkhastFaktorTakhfifModel.COLUMN_ccTakhfif() + " = " + ccTakhfif +
                    " and " + DarkhastFaktorTakhfifModel.COLUMN_MablaghTakhfif() + " = " + mablaghTakhfifTitr;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorTakhfifDAO" , "" , "updateMablaghTakhfif" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorTakhfifModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorSatrTakhfifDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorTakhfifModel.TableName(), DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorTakhfifDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    public boolean deleteTakhfifNaghdiByccDarkhastFaktor(long ccDarkhastFaktor , String codeNoeTakhfifNaghdi , String sharhTakhfif)
    {
        try
        {
            String query = "delete from " + DarkhastFaktorTakhfifModel.TableName() + " where ccDarkhastFaktor = " + ccDarkhastFaktor +
                    " and CodeNoeTakhfif = " + codeNoeTakhfifNaghdi + " and SharhTakhfif = '"  + sharhTakhfif + "'";
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            /*db.delete(DarkhastFaktorTakhfifModel.TableName(), DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and "
                    + DarkhastFaktorTakhfifModel.COLUMN_CodeNoeTakhfif() + " = " + codeNoeTakhfifNaghdi + " and "
                    + DarkhastFaktorTakhfifModel.COLUMN_SharhTakhfif() + " = '" + sharhTakhfif + "'", null);*/
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorTakhfifDAO" , "" , "deleteAll" , "");
            return false;
        }
    }
    public boolean deleteTakhfifByCodeNoe(long ccDarkhastFaktor,int codeNoe){
        try
        {
            String query = "delete from " + DarkhastFaktorTakhfifModel.TableName() + " where ccDarkhastFaktor = " + ccDarkhastFaktor +
                    " and CodeNoeTakhfif = " + codeNoe +" and ExtraProp_MustSendToSql = 0 ";
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);

            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorTakhfifDAO" , "" , "deleteTakhfifByCodeNoe" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(DarkhastFaktorTakhfifModel darkhastFaktorTakhfifModel)
    {
        ContentValues contentValues = new ContentValues();

        if (darkhastFaktorTakhfifModel.getCcDarkhastFaktorTakhfif() > 0)
        {
            contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktorTakhfif() , darkhastFaktorTakhfifModel.getCcDarkhastFaktorTakhfif());
        }
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor() , darkhastFaktorTakhfifModel.getCcDarkhastFaktor());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_ccTakhfif() , darkhastFaktorTakhfifModel.getCcTakhfif());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_SharhTakhfif() , darkhastFaktorTakhfifModel.getSharhTakhfif());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_CodeNoeTakhfif() , darkhastFaktorTakhfifModel.getCodeNoeTakhfif());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_DarsadTakhfif() , darkhastFaktorTakhfifModel.getDarsadTakhfif());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_MablaghTakhfif() , darkhastFaktorTakhfifModel.getMablaghTakhfif());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ForJayezeh() , darkhastFaktorTakhfifModel.getExtraProp_ForJayezeh());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_IsTakhfifMazad() , darkhastFaktorTakhfifModel.getExtraProp_IsTakhfifMazad());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_MustSendToSql() , darkhastFaktorTakhfifModel.getExtraProp_MustSendToSql());
        contentValues.put(DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ccJayezehTakhfif() , darkhastFaktorTakhfifModel.getExtraProp_ccJayezehTakhfif());

        return contentValues;
    }


    private ArrayList<DarkhastFaktorTakhfifModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorTakhfifModel darkhastFaktorTakhfifModel = new DarkhastFaktorTakhfifModel();

            darkhastFaktorTakhfifModel.setCcDarkhastFaktorTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktorTakhfif())));
            darkhastFaktorTakhfifModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorTakhfifModel.setCcTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_ccTakhfif())));
            darkhastFaktorTakhfifModel.setSharhTakhfif(cursor.getString(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_SharhTakhfif())));
            darkhastFaktorTakhfifModel.setCodeNoeTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_CodeNoeTakhfif())));
            darkhastFaktorTakhfifModel.setDarsadTakhfif(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_DarsadTakhfif())));
            darkhastFaktorTakhfifModel.setMablaghTakhfif(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_MablaghTakhfif())));
            darkhastFaktorTakhfifModel.setExtraProp_ForJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ForJayezeh())));
            darkhastFaktorTakhfifModel.setExtraProp_IsTakhfifMazad(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_IsTakhfifMazad())));
            darkhastFaktorTakhfifModel.setExtraProp_MustSendToSql(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_MustSendToSql())));
            darkhastFaktorTakhfifModel.setExtraProp_ccJayezehTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_ccJayezehTakhfif())));

            darkhastFaktorTakhfifModels.add(darkhastFaktorTakhfifModel);
            cursor.moveToNext();
        }
        return darkhastFaktorTakhfifModels;
    }


    public boolean updateCodeNoeArzeshAfzoodeh() {
        try
        {
            String query = "update " + DarkhastFaktorTakhfifModel.TableName() + " set " + DarkhastFaktorTakhfifModel.COLUMN_ExtraProp_MustSendToSql() + " = " + 1 +
                    " where " + DarkhastFaktorTakhfifModel.COLUMN_CodeNoeTakhfif() + " = " + DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorTakhfifModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorTakhfifDAO" , "" , "updateCodeNoeArzeshAfzoodeh" , "");
            return false;
        }
    }
}
