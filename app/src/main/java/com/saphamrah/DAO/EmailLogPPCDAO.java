package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.EmailLogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class EmailLogPPCDAO
{

    private DBHelper dbHelper;
    private Context context;



    public EmailLogPPCDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "EmailLogPPCDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            EmailLogPPCModel.COLUMN_ccEmailLogPPC(),
            EmailLogPPCModel.COLUMN_Email(),
            EmailLogPPCModel.COLUMN_Password()
        };
    }


    public EmailLogPPCModel getRandom()
    {
        EmailLogPPCModel emailLogPPCModel = null;
        String query = "select * from " + EmailLogPPCModel.TableName() + " order by Random() limit 1";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    emailLogPPCModel = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "EmailLogPPCDAO", "", "selectRandom", "");
        }
        return emailLogPPCModel;
    }


    private EmailLogPPCModel cursorToModel(Cursor cursor)
    {
        cursor.moveToFirst();
        EmailLogPPCModel emailLogPPCModel = new EmailLogPPCModel();
        emailLogPPCModel.setCcEmailLogPPC(cursor.getInt(cursor.getColumnIndex(EmailLogPPCModel.COLUMN_ccEmailLogPPC())));
        emailLogPPCModel.setEmail(cursor.getString(cursor.getColumnIndex(EmailLogPPCModel.COLUMN_Email())));
        emailLogPPCModel.setPassword(cursor.getString(cursor.getColumnIndex(EmailLogPPCModel.COLUMN_Password())));
        return emailLogPPCModel;
    }


}
