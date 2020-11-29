package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MessageBoxStatusModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MessageBoxStatusDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MessageBoxStatusDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MessageBoxStatusDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MessageBoxStatusModel.COLUMN_ccMessage(),
            MessageBoxStatusModel.COLUMN_ccForoshandeh(),
            MessageBoxStatusModel.COLUMN_ccMamorPakhsh(),
            MessageBoxStatusModel.COLUMN_Status(),
            MessageBoxStatusModel.COLUMN_Active()
        };
    }


    public boolean insertGroup(ArrayList<MessageBoxStatusModel> messageBoxStatusModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MessageBoxStatusModel messageBoxStatusModel : messageBoxStatusModels)
            {
                ContentValues contentValues = modelToContentvalue(messageBoxStatusModel);
                db.insertOrThrow(MessageBoxStatusModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            String message = context.getResources().getString(R.string.errorGroupInsert , MessageBoxStatusModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxStatusDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MessageBoxStatusModel> getAll()
    {
        ArrayList<MessageBoxStatusModel> messageBoxStatusModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MessageBoxStatusModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    messageBoxStatusModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MessageBoxStatusModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxStatusDAO" , "" , "getAll" , "");
        }
        return messageBoxStatusModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MessageBoxStatusModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MessageBoxStatusModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxStatusDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MessageBoxStatusModel messageBoxStatusModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MessageBoxStatusModel.COLUMN_ccMessage() , messageBoxStatusModel.getCcMessage());
        contentValues.put(MessageBoxStatusModel.COLUMN_ccForoshandeh() , messageBoxStatusModel.getCcForoshandeh());
        contentValues.put(MessageBoxStatusModel.COLUMN_ccMamorPakhsh() , messageBoxStatusModel.getCcMamorPakhsh());
        contentValues.put(MessageBoxStatusModel.COLUMN_Status() , messageBoxStatusModel.getStatus());
        contentValues.put(MessageBoxStatusModel.COLUMN_Active() , messageBoxStatusModel.getActive());

        return contentValues;
    }


    private ArrayList<MessageBoxStatusModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MessageBoxStatusModel> messageBoxStatusModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MessageBoxStatusModel messageBoxStatusModel = new MessageBoxStatusModel();

            messageBoxStatusModel.setCcMessage(cursor.getInt(cursor.getColumnIndex(MessageBoxStatusModel.COLUMN_ccMessage())));
            messageBoxStatusModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(MessageBoxStatusModel.COLUMN_ccForoshandeh())));
            messageBoxStatusModel.setCcMamorPakhsh(cursor.getInt(cursor.getColumnIndex(MessageBoxStatusModel.COLUMN_ccMamorPakhsh())));
            messageBoxStatusModel.setStatus(cursor.getInt(cursor.getColumnIndex(MessageBoxStatusModel.COLUMN_Status())));
            messageBoxStatusModel.setActive(cursor.getInt(cursor.getColumnIndex(MessageBoxStatusModel.COLUMN_Active())));

            messageBoxStatusModels.add(messageBoxStatusModel);
            cursor.moveToNext();
        }
        return messageBoxStatusModels;
    }

}
