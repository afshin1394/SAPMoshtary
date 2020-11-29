package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class DarkhastFaktorEmzaMoshtaryDAO
{


    private DBHelper dbHelper;
    private Context context;



    public DarkhastFaktorEmzaMoshtaryDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorEmzaMoshtaryDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor(),
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccMoshtary(),
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_EmzaImage(),
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_DarkhastFaktorImage(),
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage()
        };
    }


    public boolean insert(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(darkhastFaktorEmzaMoshtaryModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(DarkhastFaktorEmzaMoshtaryModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "insert" , "");
            return false;
        }
    }


    public ArrayList<DarkhastFaktorEmzaMoshtaryModel> getAll()
    {
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorEmzaMoshtaryModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorEmzaMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorEmzaMoshtaryModels;
    }

    public ArrayList<DarkhastFaktorEmzaMoshtaryModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorEmzaMoshtaryModel.TableName(), allColumns(), DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorEmzaMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return darkhastFaktorEmzaMoshtaryModels;
    }

    public Bitmap getEmzaImageByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        Bitmap bitmap = null;
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DarkhastFaktorEmzaMoshtaryModel.TableName(), new String[]{DarkhastFaktorEmzaMoshtaryModel.COLUMN_EmzaImage()}, DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    bitmap = new PubFunc().new ImageUtils().convertByteArrayToBitmap(context , cursor.getBlob(0));
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "getAll" , "");
        }
        return bitmap;
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorEmzaMoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DarkhastFaktorEmzaMoshtaryModel.TableName(), DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "deleteByccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean updateDarkhastFaktorImage(byte[] imageBytes , long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_DarkhastFaktorImage() , imageBytes);
            contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage() , "1");
            db.update(DarkhastFaktorEmzaMoshtaryModel.TableName(), contentValues, DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "updateDarkhastFaktorImage" , "");
            return false;
        }
    }

    public boolean updateSendedccDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew)
    {
        try
        {
            String query = "update " + DarkhastFaktorEmzaMoshtaryModel.TableName() + " set " + DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorNew +
                    " where " + DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktorOld;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "updateSendedccDarkhastFaktor" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor() , darkhastFaktorEmzaMoshtaryModel.getCcDarkhastFaktor());
        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccMoshtary() , darkhastFaktorEmzaMoshtaryModel.getCcMoshtary());
        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_EmzaImage() , darkhastFaktorEmzaMoshtaryModel.getEmzaImage());
        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_DarkhastFaktorImage() , darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage());
        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage() , darkhastFaktorEmzaMoshtaryModel.getHave_FaktorImage());

        return contentValues;
    }


    private ArrayList<DarkhastFaktorEmzaMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel = new DarkhastFaktorEmzaMoshtaryModel();

            darkhastFaktorEmzaMoshtaryModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorEmzaMoshtaryModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccMoshtary())));
            darkhastFaktorEmzaMoshtaryModel.setEmzaImage(cursor.getBlob(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_EmzaImage())));
            darkhastFaktorEmzaMoshtaryModel.setDarkhastFaktorImage(cursor.getBlob(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_DarkhastFaktorImage())));
            darkhastFaktorEmzaMoshtaryModel.setHave_FaktorImage(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage())));

            darkhastFaktorEmzaMoshtaryModels.add(darkhastFaktorEmzaMoshtaryModel);
            cursor.moveToNext();
        }
        return darkhastFaktorEmzaMoshtaryModels;
    }


}
