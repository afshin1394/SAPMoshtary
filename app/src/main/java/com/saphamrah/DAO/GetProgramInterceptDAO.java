package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ForoshandehEtebarModel;
import com.saphamrah.Model.GetImageStringModel;
import com.saphamrah.Model.GetProgramInterceptModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class GetProgramInterceptDAO {
    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "GetProgramInterceptDAO";


    public GetProgramInterceptDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
                {
                        GetProgramInterceptModel.COLUMN_MethodName,
                        GetProgramInterceptModel.COLUMN_ResponseSize,
                        GetProgramInterceptModel.COLUMN_ResponseTime,

                };
    }
    private static ContentValues modelToContentvalue(GetProgramInterceptModel getProgramInterceptModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(GetProgramInterceptModel.COLUMN_MethodName , getProgramInterceptModel.getMethodName());
        contentValues.put(GetProgramInterceptModel.COLUMN_ResponseSize , getProgramInterceptModel.getResponseSize());
        contentValues.put(GetProgramInterceptModel.COLUMN_ResponseTime , getProgramInterceptModel.getResponseTime());


        return contentValues;
    }
    public boolean insertGroup(ArrayList<GetProgramInterceptModel> getProgramInterceptModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            ContentValues contentValues = null;
            db.beginTransaction();

            for (GetProgramInterceptModel getProgramInterceptModel : getProgramInterceptModels) {
                contentValues = modelToContentvalue(getProgramInterceptModel);
                db.insertOrThrow(GetProgramInterceptModel.TABLE_NAME, null, contentValues);

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
            String message = context.getResources().getString(R.string.errorGroupInsert , GetProgramInterceptModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }
    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(GetProgramInterceptModel.TABLE_NAME, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , GetProgramInterceptModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }
}
