package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.CodeTypeModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class CodeTypeDAO
{

    private DBHelper dbHelper;
    private Context context;


    public CodeTypeDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CodeTypeDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            CodeTypeModel.COLUMN_ccCodeType(),
            CodeTypeModel.COLUMN_NameCodeType(),
            CodeTypeModel.COLUMN_Pattern()
        };
    }


    public boolean insertGroup(ArrayList<CodeTypeModel> codeTypeModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (CodeTypeModel codeTypeModel : codeTypeModels)
            {
                ContentValues contentValues = modelToContentvalue(codeTypeModel);
                db.insertOrThrow(CodeTypeModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , CodeTypeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CodeTypeDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<CodeTypeModel> getAll()
    {
        ArrayList<CodeTypeModel> codeTypeModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(CodeTypeModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    codeTypeModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , CodeTypeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CodeTypeDAO" , "" , "getAll" , "");
        }
        return codeTypeModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(CodeTypeModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , CodeTypeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CodeTypeDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(CodeTypeModel codeTypeModel)
    {
        ContentValues contentValues = new ContentValues();

        if (codeTypeModel.getCcCodeType() > 0)
        {
            contentValues.put(CodeTypeModel.COLUMN_ccCodeType() , codeTypeModel.getCcCodeType());
        }
        contentValues.put(CodeTypeModel.COLUMN_NameCodeType() , codeTypeModel.getNameCodeType());
        contentValues.put(CodeTypeModel.COLUMN_Pattern() , codeTypeModel.getPattern());

        return contentValues;
    }


    private ArrayList<CodeTypeModel> cursorToModel(Cursor cursor)
    {
        ArrayList<CodeTypeModel> codeTypeModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            CodeTypeModel codeTypeModel = new CodeTypeModel();

            codeTypeModel.setCcCodeType(cursor.getInt(cursor.getColumnIndex(CodeTypeModel.COLUMN_ccCodeType())));
            codeTypeModel.setNameCodeType(cursor.getString(cursor.getColumnIndex(CodeTypeModel.COLUMN_NameCodeType())));
            codeTypeModel.setPattern(cursor.getString(cursor.getColumnIndex(CodeTypeModel.COLUMN_Pattern())));

            codeTypeModels.add(codeTypeModel);
            cursor.moveToNext();
        }
        return codeTypeModels;
    }


}
