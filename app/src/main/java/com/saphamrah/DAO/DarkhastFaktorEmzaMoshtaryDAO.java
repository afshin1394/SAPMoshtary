package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.MarjoeeKamelImageModel;
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
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_ReceiptImage(),
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_DarkhastFaktorImage(),
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage(),
            DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_ReceiptImage()
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

    public boolean insertGroup(ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel : darkhastFaktorEmzaMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(darkhastFaktorEmzaMoshtaryModel);
                db.insertOrThrow(DarkhastFaktorEmzaMoshtaryModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorInsert , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "insertGroup" , "");
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
            String query = " select dfe.*,df.UniqID_Tablet  from DarkhastFaktor_EmzaMoshtary dfe " +
                    "left join DarkhastFaktor df on dfe.ccDarkhastFaktor = df.ccDarkhastFaktor " +
                    " where dfe.ccDarkhastFaktor ="+ccDarkhastFaktor;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel = new DarkhastFaktorEmzaMoshtaryModel();

                        darkhastFaktorEmzaMoshtaryModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor())));
                        darkhastFaktorEmzaMoshtaryModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccMoshtary())));
                        darkhastFaktorEmzaMoshtaryModel.setEmzaImage(cursor.getBlob(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_EmzaImage())));
                        darkhastFaktorEmzaMoshtaryModel.setDarkhastFaktorImage(cursor.getBlob(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_DarkhastFaktorImage())));
                        darkhastFaktorEmzaMoshtaryModel.setReceiptImage(cursor.getBlob(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ReceiptImage())));
                        darkhastFaktorEmzaMoshtaryModel.setHave_FaktorImage(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage())));
                        darkhastFaktorEmzaMoshtaryModel.setHave_ReceiptImage(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_ReceiptImage())));
                        darkhastFaktorEmzaMoshtaryModel.setExtraProp_UniqueID(cursor.getString(cursor.getColumnIndex("UniqID_Tablet")));
                        darkhastFaktorEmzaMoshtaryModels.add(darkhastFaktorEmzaMoshtaryModel);
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


    public boolean updateReceiptImageByccDarkhastFaktor(long ccDarkhastFaktor,byte[] image)
    {

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ReceiptImage(), image);
            values.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_ReceiptImage(), 1);
            db.update(DarkhastFaktorEmzaMoshtaryModel.TableName(), values, DarkhastFaktorEmzaMoshtaryModel.COLUMN_ccDarkhastFaktor()+"= ?", new String[]{String.valueOf(ccDarkhastFaktor)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorEmzaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorEmzaMoshtaryDAO" , "" , "updateReceiptImageByccDarkhastFaktor" , "");
            return false;
        }

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

    public boolean haveImage(long ccDarkhastFaktor){
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + DarkhastFaktorEmzaMoshtaryModel.TableName() + " WHERE "+ DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage() + " = 1 " +"AND ccDarkhastFaktor  = " + ccDarkhastFaktor;
            Cursor cursor = db.rawQuery(query , null);
            cursor.moveToFirst();
            if(cursor.getCount() !=0)
                return true;
            cursor.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return false;
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
        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ReceiptImage() , darkhastFaktorEmzaMoshtaryModel.getReceiptImage());
        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_DarkhastFaktorImage() , darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage());
        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage() , darkhastFaktorEmzaMoshtaryModel.getHave_FaktorImage());
        contentValues.put(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_ReceiptImage() , darkhastFaktorEmzaMoshtaryModel.getHave_ReceiptImage());

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
            darkhastFaktorEmzaMoshtaryModel.setReceiptImage(cursor.getBlob(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_ReceiptImage())));
            darkhastFaktorEmzaMoshtaryModel.setHave_FaktorImage(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage())));
            darkhastFaktorEmzaMoshtaryModel.setHave_ReceiptImage(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_ReceiptImage())));

            darkhastFaktorEmzaMoshtaryModels.add(darkhastFaktorEmzaMoshtaryModel);
            cursor.moveToNext();
        }
        return darkhastFaktorEmzaMoshtaryModels;
    }



}
