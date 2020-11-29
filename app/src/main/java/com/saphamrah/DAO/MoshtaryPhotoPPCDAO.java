package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.MoshtaryPhotoPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;

import java.util.ArrayList;

public class MoshtaryPhotoPPCDAO
{

    private DBHelper dbHelper;
    private Context context;
    

    public MoshtaryPhotoPPCDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryPhotoPPCDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryPhotoPPCModel.COLUMN_ccMoshtaryPhoto(),
            MoshtaryPhotoPPCModel.COLUMN_ccMoshtary(),
            MoshtaryPhotoPPCModel.COLUMN_ccNoePhoto(),
            MoshtaryPhotoPPCModel.COLUMN_txtNoePhoto(),
            MoshtaryPhotoPPCModel.COLUMN_ImageMadrak()
        };
    }

    public boolean insertGroup(ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryPhotoPPCModel moshtaryPhotoPPCModel : moshtaryPhotoPPCModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryPhotoPPCModel);
                db.insertOrThrow(MoshtaryPhotoPPCModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryPhotoPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPhotoPPCDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(moshtaryPhotoPPCModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = MoshtaryPhotoPPCModel.COLUMN_ccMoshtary() + " = " + moshtaryPhotoPPCModel.getCcMoshtary() + " and " + MoshtaryPhotoPPCModel.COLUMN_ccNoePhoto() + " = " + moshtaryPhotoPPCModel.getCcNoePhoto();
            db.delete(MoshtaryPhotoPPCModel.TableName() , whereClause, null);
            db.insertOrThrow(MoshtaryPhotoPPCModel.TableName(), null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , MoshtaryPhotoPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPhotoPPCDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<MoshtaryPhotoPPCModel> getAll()
    {
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryPhotoPPCModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryPhotoPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryPhotoPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPhotoPPCDAO" , "" , "getAll" , "");
        }
        return moshtaryPhotoPPCModels;
    }

    public ArrayList<MoshtaryPhotoPPCModel> getAllByccMoshtary(int ccMoshtary)
    {
        ArrayList<MoshtaryPhotoPPCModel> arrayList = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryPhotoPPCModel.TableName(), allColumns(), MoshtaryPhotoPPCModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    arrayList = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryPhotoPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPhotoPPCDAO" , "" , "getByccMoshtary" , "");
        }
        return arrayList;
    }

    public ArrayList<MoshtaryPhotoPPCModel> getByccMoshtaryAndType(int ccMoshtary , int noePhoto)
    {
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryPhotoPPCModel.TableName(), allColumns(), MoshtaryPhotoPPCModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and " + MoshtaryPhotoPPCModel.COLUMN_ccNoePhoto() + " = " + noePhoto, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryPhotoPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryPhotoPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPhotoPPCDAO" , "" , "getByccMoshtary" , "");
        }
        return moshtaryPhotoPPCModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryPhotoPPCModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryPhotoPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPhotoPPCDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtaryPhoto(int ccMoshtaryPhoto)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryPhotoPPCModel.TableName(), MoshtaryPhotoPPCModel.COLUMN_ccMoshtaryPhoto() + " = " + ccMoshtaryPhoto, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryPhotoPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPhotoPPCDAO" , "" , "deleteByccMoshtaryPhoto" , "");
            return false;
        }
    }

    public boolean updateMoshtaryJadid(int newccMoshtary , int oldccMoshtary)
    {
        try
        {
            String query = "update " + MoshtaryPhotoPPCModel.TableName() + " set " + MoshtaryPhotoPPCModel.COLUMN_ccMoshtary() + " = " + newccMoshtary + " where " + MoshtaryPhotoPPCModel.COLUMN_ccMoshtary() + " = " + oldccMoshtary;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDelete , MoshtaryPhotoPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MoshtaryPhotoPPCDAO" , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        ContentValues contentValues = new ContentValues();

        if (moshtaryPhotoPPCModel.getCcMoshtaryPhoto() != null && moshtaryPhotoPPCModel.getCcMoshtaryPhoto() > 0)
        {
            contentValues.put(MoshtaryPhotoPPCModel.COLUMN_ccMoshtaryPhoto() , moshtaryPhotoPPCModel.getCcMoshtaryPhoto());
        }
        contentValues.put(MoshtaryPhotoPPCModel.COLUMN_ccMoshtary() , moshtaryPhotoPPCModel.getCcMoshtary());
        contentValues.put(MoshtaryPhotoPPCModel.COLUMN_ccNoePhoto() , moshtaryPhotoPPCModel.getCcNoePhoto());
        contentValues.put(MoshtaryPhotoPPCModel.COLUMN_txtNoePhoto() , moshtaryPhotoPPCModel.getTxtNoePhoto());
        contentValues.put(MoshtaryPhotoPPCModel.COLUMN_ImageMadrak() , moshtaryPhotoPPCModel.getImageMadrak());

        return contentValues;
    }


    private ArrayList<MoshtaryPhotoPPCModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryPhotoPPCModel moshtaryPhotoPPCModel = new MoshtaryPhotoPPCModel();

            moshtaryPhotoPPCModel.setCcMoshtaryPhoto(cursor.getInt(cursor.getColumnIndex(MoshtaryPhotoPPCModel.COLUMN_ccMoshtaryPhoto())));
            moshtaryPhotoPPCModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryPhotoPPCModel.COLUMN_ccMoshtary())));
            moshtaryPhotoPPCModel.setCcNoePhoto(cursor.getInt(cursor.getColumnIndex(MoshtaryPhotoPPCModel.COLUMN_ccNoePhoto())));
            moshtaryPhotoPPCModel.setTxtNoePhoto(cursor.getString(cursor.getColumnIndex(MoshtaryPhotoPPCModel.COLUMN_txtNoePhoto())));
            moshtaryPhotoPPCModel.setImageMadrak(cursor.getBlob(cursor.getColumnIndex(MoshtaryPhotoPPCModel.COLUMN_ImageMadrak())));

            moshtaryPhotoPPCModels.add(moshtaryPhotoPPCModel);
            cursor.moveToNext();
        }
        return moshtaryPhotoPPCModels;
    }


}
