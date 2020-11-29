package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryRotbehModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MoshtaryRotbehDAO
{

    private DBHelper dbHelper;
    private Context context;



    public MoshtaryRotbehDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryRotbehDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryRotbehModel.COLUMN_ccMoshtaryRotbeh(),
            MoshtaryRotbehModel.COLUMN_ccMoshtary(),
            MoshtaryRotbehModel.COLUMN_ccBrand(),
            MoshtaryRotbehModel.COLUMN_Darajeh(),
            MoshtaryRotbehModel.COLUMN_FromDate(),
            MoshtaryRotbehModel.COLUMN_EndDate(),
            MoshtaryRotbehModel.COLUMN_NameBrand(),
            MoshtaryRotbehModel.COLUMN_DarsadAfzayeshEtebar(),
            MoshtaryRotbehModel.COLUMN_NameDarajeh()
        };
    }

    public boolean insertGroup(ArrayList<MoshtaryRotbehModel> moshtaryRotbehModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryRotbehModel moshtaryRotbehModel : moshtaryRotbehModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryRotbehModel);
                db.insertOrThrow(MoshtaryRotbehModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryRotbehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryRotbehDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(MoshtaryRotbehModel moshtaryRotbehModel)
    {
        ContentValues contentValues = modelToContentvalue(moshtaryRotbehModel);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(MoshtaryRotbehModel.TableName() , null , contentValues);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryRotbehDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<MoshtaryRotbehModel> getAll()
    {
        ArrayList<MoshtaryRotbehModel> moshtaryRotbehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryRotbehModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryRotbehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryRotbehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryRotbehDAO" , "" , "getAll" , "");
        }
        return moshtaryRotbehModels;
    }

    public int getRotbehByccMoshtaryForMoshtaryJadid(int ccMoshtary)
    {
        int rotbeh = -1;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryRotbehModel.TableName(), allColumns(), " ccBrand = 30 AND  ccMoshtary=" + ccMoshtary , null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rotbeh = cursorToModel(cursor).get(0).getDarajeh();
                }
                cursor.close();
            }
            if (rotbeh == -1)
            {
                rotbeh = 4;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryRotbehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryRotbehDAO" , "" , "getRotbehByccMoshtaryForMoshtaryJadid" , "");
        }
        return rotbeh;
    }


    public int getRotbehByccMoshtaryAndBrand(int ccMoshtary, int ccBrand)
    {
        int rotbeh = -1;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryRotbehModel.TableName(), allColumns(), " ccBrand = " + ccBrand + " AND  ccMoshtary=" + ccMoshtary , null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rotbeh = cursorToModel(cursor).get(0).getDarajeh();
                }
                cursor.close();
            }
            if (rotbeh == -1)
            {
                rotbeh = 4;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryRotbehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryRotbehDAO" , "" , "getRotbehByccMoshtaryAndBrand" , "");
        }
        return rotbeh;
    }

    public double getDarsadAfzayeshEtebar(int ccMoshtary)
    {
        MoshtaryRotbehModel moshtaryRotbehModel = new MoshtaryRotbehModel();
        double darsadAfzayeshEtebar = 0 ;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM MoshtaryRotbeh WHERE ccMoshtary = " + ccMoshtary + " LIMIT 1";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryRotbehModel = cursorToModel(cursor).get(0);
                    darsadAfzayeshEtebar = moshtaryRotbehModel.getDarsadAfzayeshEtebar();
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryRotbehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryRotbehDAO" , "" , "getDarsadAfzayeshEtebar" , "");
        }
        return darsadAfzayeshEtebar;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryRotbehModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryRotbehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryRotbehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtaryRotbeh(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryRotbehModel.TableName(), MoshtaryRotbehModel.COLUMN_ccMoshtary() + " = " + ccMoshtary , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryRotbehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryRotbehDAO" , "" , "deleteByccMoshtaryRotbeh" , "");
            return false;
        }
    }

    public boolean updateMoshtaryJadid(int newccMoshtary , int oldccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MoshtaryRotbehModel.COLUMN_ccMoshtary(), newccMoshtary);
            db.update(MoshtaryRotbehModel.TableName(), values, "ccMoshtary= ?", new String[]{String.valueOf(oldccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MoshtaryRotbehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryRotbehDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryRotbehModel moshtaryRotbehModel)
    {
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());

        contentValues.put(MoshtaryRotbehModel.COLUMN_ccMoshtaryRotbeh() , moshtaryRotbehModel.getCcMoshtaryRotbeh());
        contentValues.put(MoshtaryRotbehModel.COLUMN_ccMoshtary() , moshtaryRotbehModel.getCcMoshtary());
        contentValues.put(MoshtaryRotbehModel.COLUMN_ccBrand() , moshtaryRotbehModel.getCcBrand());
        contentValues.put(MoshtaryRotbehModel.COLUMN_Darajeh() , moshtaryRotbehModel.getDarajeh());
        contentValues.put(MoshtaryRotbehModel.COLUMN_FromDate() , sdf.format(moshtaryRotbehModel.getFromDate()));
        contentValues.put(MoshtaryRotbehModel.COLUMN_EndDate() , sdf.format(moshtaryRotbehModel.getEndDate()));
        contentValues.put(MoshtaryRotbehModel.COLUMN_NameBrand() , moshtaryRotbehModel.getNameBrand());
        contentValues.put(MoshtaryRotbehModel.COLUMN_DarsadAfzayeshEtebar() , moshtaryRotbehModel.getDarsadAfzayeshEtebar());
        contentValues.put(MoshtaryRotbehModel.COLUMN_NameDarajeh() , moshtaryRotbehModel.getNameDarajeh());

        return contentValues;
    }


    private ArrayList<MoshtaryRotbehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryRotbehModel> moshtaryRotbehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryRotbehModel moshtaryRotbehModel = new MoshtaryRotbehModel();

            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            Date fromDate = new Date();
            Date endDate = new Date();
            try
            {
                fromDate = sdf.parse(cursor.getString(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_FromDate())));
                endDate = sdf.parse(cursor.getString(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_EndDate())));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryRotbehDAO" , "" , "cursorToModel" , "");
            }
            moshtaryRotbehModel.setCcMoshtaryRotbeh(cursor.getInt(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_ccMoshtaryRotbeh())));
            moshtaryRotbehModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_ccMoshtary())));
            moshtaryRotbehModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_ccBrand())));
            moshtaryRotbehModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_Darajeh())));
            moshtaryRotbehModel.setFromDate(fromDate);
            moshtaryRotbehModel.setEndDate(endDate);
            moshtaryRotbehModel.setNameBrand(cursor.getString(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_NameBrand())));
            moshtaryRotbehModel.setDarsadAfzayeshEtebar(cursor.getFloat(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_DarsadAfzayeshEtebar())));
            moshtaryRotbehModel.setNameDarajeh(cursor.getString(cursor.getColumnIndex(MoshtaryRotbehModel.COLUMN_NameDarajeh())));

            moshtaryRotbehModels.add(moshtaryRotbehModel);
            cursor.moveToNext();
        }
        return moshtaryRotbehModels;
    }


}
