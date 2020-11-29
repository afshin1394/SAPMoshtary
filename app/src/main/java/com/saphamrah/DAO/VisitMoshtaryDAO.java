package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.VisitMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerVisitModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class VisitMoshtaryDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "VisitMoshtaryDAO";


    public VisitMoshtaryDAO(Context context)
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
            VisitMoshtaryModel.COLUMN_ccVisitMoshtary(),
            VisitMoshtaryModel.COLUMN_ccAmargar(),
            VisitMoshtaryModel.COLUMN_ccMoshtary(),
            VisitMoshtaryModel.COLUMN_ccPorseshnameh(),
            VisitMoshtaryModel.COLUMN_TarikhVisitMoshtary(),
            VisitMoshtaryModel.COLUMN_CodeVazeiatMoshtary(),
            VisitMoshtaryModel.COLUMN_ccElatAdamMoarefiMoshtary(),
            VisitMoshtaryModel.COLUMN_ExtraProp_IsOld(),
            VisitMoshtaryModel.COLUMN_CodeMoshtaryTekrari(),
            VisitMoshtaryModel.COLUMN_SaatVorod(),
            VisitMoshtaryModel.COLUMN_SaatKhoroj()
        };
    }


    public boolean insertGroup(ArrayList<VisitMoshtaryModel> visitMoshtaryModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (VisitMoshtaryModel visitMoshtaryModel : visitMoshtaryModels)
            {
                ContentValues contentValues = modelToContentvalue(visitMoshtaryModel);
                db.insertOrThrow(VisitMoshtaryModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }


    public boolean insert(VisitMoshtaryModel visitMoshtaryModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(visitMoshtaryModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(VisitMoshtaryModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<VisitMoshtaryModel> getAll()
    {
        ArrayList<VisitMoshtaryModel> visitMoshtaryModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(VisitMoshtaryModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    visitMoshtaryModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return visitMoshtaryModels;
    }

    public int getCountNotSended()
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select count(ccVisitMoshtary) from VisitMoshtary where ExtraProp_IsOld = 0";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getCountNotSended" , "");
        }
        return count;
    }

    public VisitMoshtaryModel getByMoshtary(int ccMoshtary)
    {
        VisitMoshtaryModel visitMoshtaryModels = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(VisitMoshtaryModel.TableName(), allColumns(), VisitMoshtaryModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    visitMoshtaryModels = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getByMoshtary" , "");
        }
        return visitMoshtaryModels;
    }

    public VisitMoshtaryModel get(int ccVisitMoshtary)
    {
        VisitMoshtaryModel visitMoshtaryModels = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(VisitMoshtaryModel.TableName(), allColumns(), VisitMoshtaryModel.COLUMN_ccVisitMoshtary() + " = " + ccVisitMoshtary, null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    visitMoshtaryModels = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "get" , "");
        }
        return visitMoshtaryModels;
    }

    public List<CustomerVisitModel> getAllAdam()
    {
        List<CustomerVisitModel> customerVisitModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select v.ccMoshtary, v.ccVisitMoshtary, v.ExtraProp_IsOld, m.NameMoshtary, m.Address, m.Telephone from VisitMoshtary v inner join ListMoshtarian m on v.ccMoshtary = m.ccMoshtary where v.ccPorseshnameh = 0";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    customerVisitModels = cursorToCustomerVisit(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAllCustomerVisit" , "");
        }
        return customerVisitModels;
    }

    public VisitMoshtaryModel getByPorseshname(int ccPorseshname)
    {
        VisitMoshtaryModel visitMoshtaryModels = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(VisitMoshtaryModel.TableName(), allColumns(), VisitMoshtaryModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshname, null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    visitMoshtaryModels = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getByMoshtary" , "");
        }
        return visitMoshtaryModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(VisitMoshtaryModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByPorseshname(int ccPorseshname)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(VisitMoshtaryModel.TableName(), VisitMoshtaryModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshname, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteByPorseshname" , "");
            return false;
        }
    }

    public boolean delete(int ccVisitMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(VisitMoshtaryModel.TableName(), VisitMoshtaryModel.COLUMN_ccVisitMoshtary() + " = " + ccVisitMoshtary, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "delete" , "");
            return false;
        }
    }

    public boolean updateIsOld(int ccVisitMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "update VisitMoshtary set ExtraProp_IsOld = 1 where ccVisitMoshtary = " + ccVisitMoshtary;
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "updateIsOld" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(VisitMoshtaryModel visitMoshtaryModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(VisitMoshtaryModel.COLUMN_ccAmargar() , visitMoshtaryModel.getCcAmargar());
        contentValues.put(VisitMoshtaryModel.COLUMN_ccMoshtary() , visitMoshtaryModel.getCcMoshtary());
        contentValues.put(VisitMoshtaryModel.COLUMN_ccPorseshnameh() , visitMoshtaryModel.getCcPorseshnameh());
        contentValues.put(VisitMoshtaryModel.COLUMN_TarikhVisitMoshtary() , visitMoshtaryModel.getTarikhVisitMoshtary());
        contentValues.put(VisitMoshtaryModel.COLUMN_CodeVazeiatMoshtary() , visitMoshtaryModel.getCodeVazeiatMoshtary());
        contentValues.put(VisitMoshtaryModel.COLUMN_ccElatAdamMoarefiMoshtary() , visitMoshtaryModel.getCcElatAdamMoarefiMoshtary());
        contentValues.put(VisitMoshtaryModel.COLUMN_ExtraProp_IsOld() , visitMoshtaryModel.getExtraProp_IsOld());
        contentValues.put(VisitMoshtaryModel.COLUMN_CodeMoshtaryTekrari() , visitMoshtaryModel.getCodeMoshtaryTekrari());
        contentValues.put(VisitMoshtaryModel.COLUMN_SaatVorod() , visitMoshtaryModel.getSaatVorod());
        contentValues.put(VisitMoshtaryModel.COLUMN_SaatKhoroj() , visitMoshtaryModel.getSaatKhoroj());

        return contentValues;
    }

    private ArrayList<VisitMoshtaryModel> cursorToModel(Cursor cursor)
    {
        ArrayList<VisitMoshtaryModel> visitMoshtaryModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            VisitMoshtaryModel visitMoshtaryModel = new VisitMoshtaryModel();

            visitMoshtaryModel.setCcVisitMoshtary(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ccVisitMoshtary())));
            visitMoshtaryModel.setCcAmargar(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ccAmargar())));
            visitMoshtaryModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ccMoshtary())));
            visitMoshtaryModel.setCcPorseshnameh(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ccPorseshnameh())));
            visitMoshtaryModel.setTarikhVisitMoshtary(cursor.getString(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_TarikhVisitMoshtary())));
            visitMoshtaryModel.setCodeVazeiatMoshtary(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_CodeVazeiatMoshtary())));
            visitMoshtaryModel.setCcElatAdamMoarefiMoshtary(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ccElatAdamMoarefiMoshtary())));
            visitMoshtaryModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ExtraProp_IsOld())));
            visitMoshtaryModel.setCodeMoshtaryTekrari(cursor.getString(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_CodeMoshtaryTekrari())));
            visitMoshtaryModel.setSaatVorod(cursor.getString(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_SaatVorod())));
            visitMoshtaryModel.setSaatKhoroj(cursor.getString(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_SaatKhoroj())));

            visitMoshtaryModels.add(visitMoshtaryModel);
            cursor.moveToNext();
        }
        return visitMoshtaryModels;
    }

    private ArrayList<CustomerVisitModel> cursorToCustomerVisit(Cursor cursor)
    {
        ArrayList<CustomerVisitModel> customerVisitModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            CustomerVisitModel customerVisitModel = new CustomerVisitModel();

            customerVisitModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ccMoshtary())));
            customerVisitModel.setCcVisitMoshtary(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ccVisitMoshtary())));
            customerVisitModel.setExtraprop_isOld(cursor.getInt(cursor.getColumnIndex(VisitMoshtaryModel.COLUMN_ExtraProp_IsOld())));
            customerVisitModel.setCustomerFullName(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_NameMoshtary())));
            customerVisitModel.setCustomerAddress(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_Address())));
            customerVisitModel.setCustomerTelephone(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_Telephone())));

            customerVisitModels.add(customerVisitModel);
            cursor.moveToNext();
        }
        return customerVisitModels;
    }


    public boolean updateSaateKhoroj(int ccPorseshname, String dateTime)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "update VisitMoshtary set SaatKhoroj = '" + dateTime + "' where ccPorseshnameh = " + ccPorseshname;
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , VisitMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "updateSaateKhoroj" , "");
            return false;
        }
    }

}
