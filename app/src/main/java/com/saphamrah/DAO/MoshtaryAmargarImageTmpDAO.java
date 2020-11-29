package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryAmargarImageTmpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MoshtaryAmargarImageTmpDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryAmargarImageTmpDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), MoshtaryAmargarImageTmpDAO.class.getSimpleName() , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MoshtaryAmargarImageTmpModel.COLUMN_ccPorseshnameh(),
            MoshtaryAmargarImageTmpModel.COLUMN_Image()
        };
    }


    public void insertGroup(ArrayList<MoshtaryAmargarImageTmpModel> moshtaryAmargarImageTmpModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MoshtaryAmargarImageTmpModel moshtaryAmargarImageTmpModel : moshtaryAmargarImageTmpModels)
            {
                ContentValues contentValues = modelToContentvalue(moshtaryAmargarImageTmpModel);
                db.insertOrThrow(MoshtaryAmargarImageTmpModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MoshtaryAmargarImageTmpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAmargarImageTmpDAO.class.getSimpleName() , "" , "insertGroup" , "");
        }
    }


    public ArrayList<MoshtaryAmargarImageTmpModel> getAll()
    {
        ArrayList<MoshtaryAmargarImageTmpModel> moshtaryAmargarImageTmpModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MoshtaryAmargarImageTmpModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryAmargarImageTmpModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MoshtaryAmargarImageTmpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAmargarImageTmpDAO.class.getSimpleName() , "" , "getAll" , "");
        }
        return moshtaryAmargarImageTmpModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MoshtaryAmargarImageTmpModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MoshtaryAmargarImageTmpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MoshtaryAmargarImageTmpDAO.class.getSimpleName() , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MoshtaryAmargarImageTmpModel moshtaryAmargarImageTmpModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoshtaryAmargarImageTmpModel.COLUMN_ccPorseshnameh() , moshtaryAmargarImageTmpModel.getCcPorseshnameh());
        contentValues.put(MoshtaryAmargarImageTmpModel.COLUMN_Image() , moshtaryAmargarImageTmpModel.getImage());

        return contentValues;
    }

    private ArrayList<MoshtaryAmargarImageTmpModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryAmargarImageTmpModel> moshtaryAmargarImageTmpModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryAmargarImageTmpModel moshtaryAmargarImageTmpModel = new MoshtaryAmargarImageTmpModel();

            moshtaryAmargarImageTmpModel.setCcPorseshnameh(cursor.getInt(cursor.getColumnIndex(MoshtaryAmargarImageTmpModel.COLUMN_ccPorseshnameh())));
            moshtaryAmargarImageTmpModel.setImage(cursor.getBlob(cursor.getColumnIndex(MoshtaryAmargarImageTmpModel.COLUMN_Image())));

            moshtaryAmargarImageTmpModels.add(moshtaryAmargarImageTmpModel);
            cursor.moveToNext();
        }
        return moshtaryAmargarImageTmpModels;
    }
    
}
