package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryAmargarImageModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MoshtaryAmargarImageDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryAmargarImageDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryAmargarImageDAO.class.getSimpleName() , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryAmargarImageModel.COLUMN_ccPorseshnameh(),
            MoshtaryAmargarImageModel.COLUMN_Image()
        };
    }


    public void insertGroup(ArrayList<MoshtaryAmargarImageModel> moshtaryAmargarImageModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryAmargarImageModel moshtaryAmargarImageModel : moshtaryAmargarImageModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryAmargarImageModel);
                db.insertOrThrow(MoshtaryAmargarImageModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryAmargarImageModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAmargarImageDAO.class.getSimpleName() , "" , "insertGroup" , "");
        }
    }


    public ArrayList<MoshtaryAmargarImageModel> getAll()
    {
        ArrayList<MoshtaryAmargarImageModel> moshtaryAmargarImageModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryAmargarImageModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAmargarImageModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAmargarImageModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAmargarImageDAO.class.getSimpleName() , "" , "getAll" , "");
        }
        return moshtaryAmargarImageModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAmargarImageModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryAmargarImageModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAmargarImageDAO.class.getSimpleName() , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryAmargarImageModel moshtaryAmargarImageModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoshtaryAmargarImageModel.COLUMN_ccPorseshnameh() , moshtaryAmargarImageModel.getCcPorseshnameh());
        contentValues.put(MoshtaryAmargarImageModel.COLUMN_Image() , moshtaryAmargarImageModel.getImage());

        return contentValues;
    }

    private ArrayList<MoshtaryAmargarImageModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryAmargarImageModel> moshtaryAmargarImageModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryAmargarImageModel moshtaryAmargarImageModel = new MoshtaryAmargarImageModel();

            moshtaryAmargarImageModel.setCcPorseshnameh(cursor.getInt(cursor.getColumnIndex(MoshtaryAmargarImageModel.COLUMN_ccPorseshnameh())));
            moshtaryAmargarImageModel.setImage(cursor.getBlob(cursor.getColumnIndex(MoshtaryAmargarImageModel.COLUMN_Image())));

            moshtaryAmargarImageModels.add(moshtaryAmargarImageModel);
            cursor.moveToNext();
        }
        return moshtaryAmargarImageModels;
    }



}
