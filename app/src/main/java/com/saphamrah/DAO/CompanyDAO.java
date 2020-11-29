package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.CompanyModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class CompanyDAO
{

    private DBHelper dbHelper;
    private Context context;



    public CompanyDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CompanyDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            CompanyModel.COLUMN_ccCompany(),
            CompanyModel.COLUMN_NameCompany(),
            CompanyModel.COLUMN_LogoPhoto(),
            CompanyModel.COLUMN_LogoPhotoPrint()
        };
    }
    

    public ArrayList<CompanyModel> getAll()
    {
        ArrayList<CompanyModel> companyModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(CompanyModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    companyModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , CompanyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CompanyDAO" , "" , "getAll" , "");
        }
        return companyModels;
    }

    public CompanyModel getByccCompany(int ccCompany)
    {
        CompanyModel companyModel = new CompanyModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(CompanyModel.TableName(), allColumns(), CompanyModel.COLUMN_ccCompany() + " = " + ccCompany, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    companyModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , CompanyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CompanyDAO" , "" , "getByccCompany" , "");
        }
        return companyModel;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(CompanyModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , CompanyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CompanyDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(CompanyModel companyModel)
    {
        ContentValues contentValues = new ContentValues();

        if (companyModel.getCcCompany() > 0)
        {
            contentValues.put(CompanyModel.COLUMN_ccCompany() , companyModel.getCcCompany());
        }
        contentValues.put(CompanyModel.COLUMN_NameCompany() , companyModel.getNameCompany());
        contentValues.put(CompanyModel.COLUMN_LogoPhoto() , companyModel.getLogoPhoto());
        contentValues.put(CompanyModel.COLUMN_LogoPhotoPrint() , companyModel.getLogoPhotoPrint());

        return contentValues;
    }


    private ArrayList<CompanyModel> cursorToModel(Cursor cursor)
    {
        ArrayList<CompanyModel> companyModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            CompanyModel companyModel = new CompanyModel();

            companyModel.setCcCompany(cursor.getInt(cursor.getColumnIndex(CompanyModel.COLUMN_ccCompany())));
            companyModel.setNameCompany(cursor.getString(cursor.getColumnIndex(CompanyModel.COLUMN_NameCompany())));
            companyModel.setLogoPhoto(cursor.getBlob(cursor.getColumnIndex(CompanyModel.COLUMN_LogoPhoto())));
            companyModel.setLogoPhotoPrint(cursor.getBlob(cursor.getColumnIndex(CompanyModel.COLUMN_LogoPhotoPrint())));

            companyModels.add(companyModel);
            cursor.moveToNext();
        }
        return companyModels;
    }



}
