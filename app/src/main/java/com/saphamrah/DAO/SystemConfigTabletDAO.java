package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.SystemConfigTabletModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class SystemConfigTabletDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public SystemConfigTabletDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger ();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "SystemConfigTabletDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            SystemConfigTabletModel.COLUMN_NamePrinter(),
            SystemConfigTabletModel.COLUMN_SizePrint(),
            SystemConfigTabletModel.COLUMN_NoeFaktorPrint(),
            SystemConfigTabletModel.COLUMN_NoeNaghshe(),
            SystemConfigTabletModel.COLUMN_GoodsShowNumberEachPage(),
            SystemConfigTabletModel.COLUMN_SortTreasuryList()
            /*SystemConfigTabletModel.COLUMN_UpdateJayezehTakhfif_Tablet(),
            SystemConfigTabletModel.COLUMN_ccMarkaz_GetData(),
            SystemConfigTabletModel.COLUMN_UpdateGallery_Tablet(),
            SystemConfigTabletModel.COLUMN_DarkhastViewType(),
            SystemConfigTabletModel.COLUMN_FooterViewType(),
            SystemConfigTabletModel.COLUMN_NamePrinter(),
            SystemConfigTabletModel.COLUMN_SizePrint(),
            SystemConfigTabletModel.COLUMN_DateServer(),
            SystemConfigTabletModel.COLUMN_CrispID()*/
        };
    }


    public boolean insertGroup(ArrayList<SystemConfigTabletModel> systemConfigTabletModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (SystemConfigTabletModel systemConfigTabletModel : systemConfigTabletModels)
            {
                ContentValues contentValues = modelToContentvalue(systemConfigTabletModel);
                db.insertOrThrow(SystemConfigTabletModel.TableName() , null , contentValues);
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
            PubFunc.Logger logger = new PubFunc().new Logger ();
            String message = context.getResources().getString(R.string.errorGroupInsert , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<SystemConfigTabletModel> getAll()
    {
        ArrayList<SystemConfigTabletModel> systemConfigTabletModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(SystemConfigTabletModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    systemConfigTabletModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "getAll" , "");
        }
        return systemConfigTabletModels;
    }

    public int getSortList()
    {
        int sort = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(SystemConfigTabletModel.TableName(), allColumns(), SystemConfigTabletModel.COLUMN_SortTreasuryList(), null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    sort = cursorToModel(cursor).get(0).getSortTreasuryList();
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "getAll" , "");
        }
        return sort;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(SystemConfigTabletModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean updatePrinterPaperSize(int printerPaperSize)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SystemConfigTabletModel.COLUMN_SizePrint() , printerPaperSize);
            db.update(SystemConfigTabletModel.TableName(), values, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "updatePrinterPaperSize" , "");
            return false;
        }
    }


    public boolean updatePrinterType(int printerType)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SystemConfigTabletModel.COLUMN_NamePrinter() , printerType);
            db.update(SystemConfigTabletModel.TableName(), values, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "updatePrinterPaperSize" , "");
            return false;
        }
    }

    public boolean updatePrintType(int printType)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SystemConfigTabletModel.COLUMN_NoeFaktorPrint() , printType);
            db.update(SystemConfigTabletModel.TableName(), values, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "updatePrintType" , "");
            return false;
        }
    }

    public boolean updateMapType(int mapType){
    try
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConfigTabletModel.COLUMN_NoeNaghshe() , mapType);
        db.update(SystemConfigTabletModel.TableName(), values, null, null);
        db.close();
        return true;
    }
    catch (Exception exception)
    {
        exception.printStackTrace();
        PubFunc.Logger logger = new PubFunc().new Logger();
        String message = context.getResources().getString(R.string.errorUpdate , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "updateMapType" , "");
        return false;
    }
}

    public boolean updateSortTreasuryList(int sortTresuryList){
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SystemConfigTabletModel.COLUMN_SortTreasuryList() , sortTresuryList);
            db.update(SystemConfigTabletModel.TableName(), values, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "updateMapType" , "");
            return false;
        }
    }

    public boolean updateGoodsShowItemNumber(int showItemNumber){
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SystemConfigTabletModel.COLUMN_GoodsShowNumberEachPage() , showItemNumber);
            db.update(SystemConfigTabletModel.TableName(), values, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , SystemConfigTabletModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "updateShowGoodsItemNumber" , "");
            return false;
        }
    }

        /*public boolean updateUpdateJayezehTakhfif(int updateJayezehTakhfif)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SystemConfigTabletModel.COLUMN_UpdateJayezehTakhfif_Tablet() , updateJayezehTakhfif);
            db.update(SystemConfigTabletModel.TableName(), values, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , SystemConfigModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SystemConfigTabletDAO" , "" , "updateUpdateJayezehTakhfif" , "");
            return false;
        }
    }*/

    private static ContentValues modelToContentvalue(SystemConfigTabletModel systemConfigTabletModel)
    {
        ContentValues contentValues = new ContentValues();

        /*contentValues.put(SystemConfigTabletModel.COLUMN_UpdateJayezehTakhfif_Tablet() , systemConfigTabletModel.getUpdateGallery_Tablet());
        contentValues.put(SystemConfigTabletModel.COLUMN_ccMarkaz_GetData() , systemConfigTabletModel.getCcMarkaz_GetData());
        contentValues.put(SystemConfigTabletModel.COLUMN_UpdateGallery_Tablet() , systemConfigTabletModel.getUpdateGallery_Tablet());
        contentValues.put(SystemConfigTabletModel.COLUMN_DarkhastViewType() , systemConfigTabletModel.getDarkhastViewType());
        contentValues.put(SystemConfigTabletModel.COLUMN_FooterViewType() , systemConfigTabletModel.getFooterViewType());*/
        contentValues.put(SystemConfigTabletModel.COLUMN_NamePrinter() , systemConfigTabletModel.getNamePrinter());
        contentValues.put(SystemConfigTabletModel.COLUMN_SizePrint() , systemConfigTabletModel.getSizePrint());
        contentValues.put(SystemConfigTabletModel.COLUMN_NoeFaktorPrint() , systemConfigTabletModel.getNoeFaktorPrint());
        contentValues.put(SystemConfigTabletModel.COLUMN_NoeNaghshe(),systemConfigTabletModel.getNoeNaghshe());
        contentValues.put(SystemConfigTabletModel.COLUMN_GoodsShowNumberEachPage(),systemConfigTabletModel.getGoodsShowNumberEachPage());
        contentValues.put(SystemConfigTabletModel.COLUMN_SortTreasuryList(),systemConfigTabletModel.getSortTreasuryList());
        /*contentValues.put(SystemConfigTabletModel.COLUMN_DateServer() , systemConfigTabletModel.getDateServer());
        contentValues.put(SystemConfigTabletModel.COLUMN_CrispID() , systemConfigTabletModel.getCrispID());*/

        return contentValues;
    }


    private ArrayList<SystemConfigTabletModel> cursorToModel(Cursor cursor)
    {
        ArrayList<SystemConfigTabletModel> systemConfigTabletModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            SystemConfigTabletModel systemConfigTabletModel = new SystemConfigTabletModel();

            /*systemConfigTabletModel.setUpdateJayezehTakhfif_Tablet(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_UpdateJayezehTakhfif_Tablet())));
            systemConfigTabletModel.setCcMarkaz_GetData(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_ccMarkaz_GetData())));
            systemConfigTabletModel.setUpdateGallery_Tablet(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_UpdateGallery_Tablet())));
            systemConfigTabletModel.setDarkhastViewType(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_DarkhastViewType())));
            systemConfigTabletModel.setFooterViewType(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_FooterViewType())));*/
            systemConfigTabletModel.setNamePrinter(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_NamePrinter())));
            systemConfigTabletModel.setSizePrint(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_SizePrint())));
            systemConfigTabletModel.setNoeFaktorPrint(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_NoeFaktorPrint())));
            systemConfigTabletModel.setNoeNaghshe(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_NoeNaghshe())));
            systemConfigTabletModel.setGoodsShowNumberEachPage(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_GoodsShowNumberEachPage())));
            systemConfigTabletModel.setSortTreasuryList(cursor.getInt(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_SortTreasuryList())));
            /*systemConfigTabletModel.setDateServer(cursor.getString(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_DateServer())));
            systemConfigTabletModel.setCrispID(cursor.getString(cursor.getColumnIndex(SystemConfigTabletModel.COLUMN_CrispID())));*/

            systemConfigTabletModels.add(systemConfigTabletModel);
            cursor.moveToNext();
        }
        return systemConfigTabletModels;
    }
    
}
