package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PorseshnamehDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "PorseshnamehDAO";


    public PorseshnamehDAO(Context context)
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
            PorseshnamehModel.COLUMN_ccPorseshnameh(),
            PorseshnamehModel.COLUMN_ccAmargar(),
            PorseshnamehModel.COLUMN_TarikhPorseshnameh(),
            PorseshnamehModel.COLUMN_ccMahal(),
            PorseshnamehModel.COLUMN_IsMoshtary(),
            PorseshnamehModel.COLUMN_HasDelpazir(),
            PorseshnamehModel.COLUMN_NameMoshtary(),
            PorseshnamehModel.COLUMN_NameMaghazeh(),
            PorseshnamehModel.COLUMN_ccNoeMoshtary(),
            PorseshnamehModel.COLUMN_ccSenfMoshtary(),
            PorseshnamehModel.COLUMN_NameMahaleh(),
            PorseshnamehModel.COLUMN_Address(),
            PorseshnamehModel.COLUMN_Telephone(),
            PorseshnamehModel.COLUMN_Mobile(),
            PorseshnamehModel.COLUMN_Pelak(),
            PorseshnamehModel.COLUMN_CodePosty(),
            PorseshnamehModel.COLUMN_ccRotbeh(),
            PorseshnamehModel.COLUMN_ccPorseshnamehTozihat(),
            PorseshnamehModel.COLUMN_ccNoeTablighat(),
            PorseshnamehModel.COLUMN_ExtraProp_IsOld(),
            PorseshnamehModel.COLUMN_Longitude_x(),
            PorseshnamehModel.COLUMN_Latitude_y(),
            PorseshnamehModel.COLUMN_ZamanSabt(),
            PorseshnamehModel.COLUMN_MasahatMaghazeh(),
            PorseshnamehModel.COLUMN_HasAnbar(),
            PorseshnamehModel.COLUMN_ccMoshtary(),
            PorseshnamehModel.COLUMN_CodeVazeiat(),
            PorseshnamehModel.COLUMN_KhiabanAsli(),
            PorseshnamehModel.COLUMN_KhiabanFarei1(),
            PorseshnamehModel.COLUMN_KhiabanFarei2(),
            PorseshnamehModel.COLUMN_KoocheAsli(),
            PorseshnamehModel.COLUMN_KoocheFarei1(),
            PorseshnamehModel.COLUMN_KoocheFarei2(),
            PorseshnamehModel.COLUMN_FNameMoshtary(),
            PorseshnamehModel.COLUMN_LNameMoshtary(),
            PorseshnamehModel.COLUMN_CodeMely(),
            PorseshnamehModel.COLUMN_ccMasir()
        };
    }


    public boolean insertGroup(ArrayList<PorseshnamehModel> porseshnamehModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (PorseshnamehModel model : porseshnamehModels)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(PorseshnamehModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<PorseshnamehModel> getAll()
    {
        ArrayList<PorseshnamehModel> porseshnamehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PorseshnamehModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    porseshnamehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAll" , "");
        }
        return porseshnamehModels;
    }

    public List<PorseshnamehModel> getAllNotSend()
    {
        List<PorseshnamehModel> porseshnamehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PorseshnamehModel.TableName(), allColumns(), PorseshnamehModel.COLUMN_ExtraProp_IsOld() + " = 0" , null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    porseshnamehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getAllNotSend" , "");
        }
        return porseshnamehModels;
    }

    public PorseshnamehModel get(int ccPorseshname)
    {
        PorseshnamehModel porseshnamehModels = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PorseshnamehModel.TableName(), allColumns(), PorseshnamehModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshname, null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    porseshnamehModels = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "get" , "");
        }
        return porseshnamehModels;
    }

    public PorseshnamehModel getByMoshtary(int ccMoshtary)
    {
        PorseshnamehModel porseshnamehModels = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(PorseshnamehModel.TableName(), allColumns(), PorseshnamehModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null, null, null, null, "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    porseshnamehModels = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getByMoshtary" , "");
        }
        return porseshnamehModels;
    }

    public int getCountNotSended()
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select count(ccPorseshnameh) from porseshnameh where ExtraProp_IsOld = 0";
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
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getCountNotSended" , "");
        }
        return count;
    }

    public int getLastccPorseshname()
    {
        int ccPorseshanem = -1;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //select ccPorseshnameh from porseshnameh order by ccPorseshnameh desc limit 1
            String query = "select " + PorseshnamehModel.COLUMN_ccPorseshnameh() + " from " + PorseshnamehModel.TableName() + " order by " + PorseshnamehModel.COLUMN_ccPorseshnameh() + " desc limit 1";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    ccPorseshanem = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "getLastccPorseshname" , "");
        }
        return ccPorseshanem;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PorseshnamehModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean delete(int ccPorseshnameh)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(PorseshnamehModel.TableName(), PorseshnamehModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshnameh, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "delete" , "");
            return false;
        }
    }


    public boolean update(int ccPorseshname, PorseshnamehModel porseshnamehModel)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = modelToContentvalueUpdate(porseshnamehModel);
            db.update(PorseshnamehModel.TableName(), contentValues, PorseshnamehModel.COLUMN_ccPorseshnameh() + " = " + ccPorseshname, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "updateIsOld" , "");
            return false;
        }
    }

    public boolean updateIsOld(int ccPorseshname)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "update Porseshnameh set ExtraProp_IsOld = 1 where ccPorseshnameh = " + ccPorseshname;
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , PorseshnamehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "updateIsOld" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(PorseshnamehModel porseshnamehModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PorseshnamehModel.COLUMN_ccAmargar(), porseshnamehModel.getCcAmargar());
        contentValues.put(PorseshnamehModel.COLUMN_TarikhPorseshnameh(), porseshnamehModel.getTarikhPorseshnameh());
        contentValues.put(PorseshnamehModel.COLUMN_ccMahal(), porseshnamehModel.getCcMahal());
        contentValues.put(PorseshnamehModel.COLUMN_IsMoshtary(), porseshnamehModel.getIsMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_HasDelpazir(), porseshnamehModel.getHasDelpazir());
        contentValues.put(PorseshnamehModel.COLUMN_NameMoshtary(), porseshnamehModel.getNameMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_NameMaghazeh(), porseshnamehModel.getNameMaghazeh());
        contentValues.put(PorseshnamehModel.COLUMN_ccNoeMoshtary(), porseshnamehModel.getCcNoeMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_ccSenfMoshtary(), porseshnamehModel.getCcSenfMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_NameMahaleh(), porseshnamehModel.getNameMahaleh());
        contentValues.put(PorseshnamehModel.COLUMN_Address(), porseshnamehModel.getAddress());
        contentValues.put(PorseshnamehModel.COLUMN_Telephone(), porseshnamehModel.getTelephone());
        contentValues.put(PorseshnamehModel.COLUMN_Mobile(), porseshnamehModel.getMobile());
        contentValues.put(PorseshnamehModel.COLUMN_Pelak(), porseshnamehModel.getPelak());
        contentValues.put(PorseshnamehModel.COLUMN_CodePosty(), porseshnamehModel.getCodePosty());
        contentValues.put(PorseshnamehModel.COLUMN_ccRotbeh(), porseshnamehModel.getCcRotbeh());
        contentValues.put(PorseshnamehModel.COLUMN_ccPorseshnamehTozihat(), porseshnamehModel.getCcPorseshnamehTozihat());
        contentValues.put(PorseshnamehModel.COLUMN_ccNoeTablighat(), porseshnamehModel.getCcNoeTablighat());
        contentValues.put(PorseshnamehModel.COLUMN_ExtraProp_IsOld(), porseshnamehModel.getExtraProp_IsOld());
        contentValues.put(PorseshnamehModel.COLUMN_Longitude_x(), porseshnamehModel.getLongitude_x());
        contentValues.put(PorseshnamehModel.COLUMN_Latitude_y(), porseshnamehModel.getLatitude_y());
        contentValues.put(PorseshnamehModel.COLUMN_ZamanSabt(), porseshnamehModel.getZamanSabt());
        contentValues.put(PorseshnamehModel.COLUMN_MasahatMaghazeh(), porseshnamehModel.getMasahatMaghazeh());
        contentValues.put(PorseshnamehModel.COLUMN_HasAnbar(), porseshnamehModel.getHasAnbar());
        contentValues.put(PorseshnamehModel.COLUMN_ccMoshtary(), porseshnamehModel.getCcMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_CodeVazeiat(), porseshnamehModel.getCodeVazeiat());
        contentValues.put(PorseshnamehModel.COLUMN_KhiabanAsli(), porseshnamehModel.getKhiabanAsli());
        contentValues.put(PorseshnamehModel.COLUMN_KhiabanFarei1(), porseshnamehModel.getKhiabanFarei1());
        contentValues.put(PorseshnamehModel.COLUMN_KhiabanFarei2(), porseshnamehModel.getKhiabanFarei2());
        contentValues.put(PorseshnamehModel.COLUMN_KoocheAsli(), porseshnamehModel.getKoocheAsli());
        contentValues.put(PorseshnamehModel.COLUMN_KoocheFarei1(), porseshnamehModel.getKoocheFarei1());
        contentValues.put(PorseshnamehModel.COLUMN_KoocheFarei2(), porseshnamehModel.getKoocheFarei2());
        contentValues.put(PorseshnamehModel.COLUMN_FNameMoshtary(), porseshnamehModel.getFNameMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_LNameMoshtary(), porseshnamehModel.getLNameMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_CodeMely(), porseshnamehModel.getCodeMely());
        contentValues.put(PorseshnamehModel.COLUMN_ccMasir(), porseshnamehModel.getCcMasir());

        return contentValues;
    }

    private static ContentValues modelToContentvalueUpdate(PorseshnamehModel porseshnamehModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PorseshnamehModel.COLUMN_HasDelpazir(), porseshnamehModel.getHasDelpazir());
        contentValues.put(PorseshnamehModel.COLUMN_FNameMoshtary(), porseshnamehModel.getFNameMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_LNameMoshtary(), porseshnamehModel.getLNameMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_NameMoshtary(), porseshnamehModel.getNameMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_NameMaghazeh(), porseshnamehModel.getNameMaghazeh());
        contentValues.put(PorseshnamehModel.COLUMN_HasAnbar(), porseshnamehModel.getHasAnbar());
        contentValues.put(PorseshnamehModel.COLUMN_ccNoeMoshtary(), porseshnamehModel.getCcNoeMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_ccSenfMoshtary(), porseshnamehModel.getCcSenfMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_NameMahaleh(), porseshnamehModel.getNameMahaleh());
        contentValues.put(PorseshnamehModel.COLUMN_Address(), porseshnamehModel.getAddress());
        contentValues.put(PorseshnamehModel.COLUMN_Telephone(), porseshnamehModel.getTelephone());
        contentValues.put(PorseshnamehModel.COLUMN_Mobile(), porseshnamehModel.getMobile());
        contentValues.put(PorseshnamehModel.COLUMN_CodePosty(), porseshnamehModel.getCodePosty());
        contentValues.put(PorseshnamehModel.COLUMN_KhiabanAsli(), porseshnamehModel.getKhiabanAsli());
        contentValues.put(PorseshnamehModel.COLUMN_KhiabanFarei1(), porseshnamehModel.getKhiabanFarei1());
        contentValues.put(PorseshnamehModel.COLUMN_KhiabanFarei2(), porseshnamehModel.getKhiabanFarei2());
        contentValues.put(PorseshnamehModel.COLUMN_KoocheAsli(), porseshnamehModel.getKoocheAsli());
        contentValues.put(PorseshnamehModel.COLUMN_KoocheFarei1(), porseshnamehModel.getKoocheFarei1());
        contentValues.put(PorseshnamehModel.COLUMN_ccMahal(), porseshnamehModel.getCcMahal());
        contentValues.put(PorseshnamehModel.COLUMN_Pelak(), porseshnamehModel.getPelak());
        contentValues.put(PorseshnamehModel.COLUMN_MasahatMaghazeh(), porseshnamehModel.getMasahatMaghazeh());
        contentValues.put(PorseshnamehModel.COLUMN_ccMasir(), porseshnamehModel.getCcMasir());
        contentValues.put(PorseshnamehModel.COLUMN_ccPorseshnamehTozihat(), porseshnamehModel.getCcPorseshnamehTozihat());
        contentValues.put(PorseshnamehModel.COLUMN_ccMoshtary(), porseshnamehModel.getCcMoshtary());
        contentValues.put(PorseshnamehModel.COLUMN_CodeVazeiat(), porseshnamehModel.getCodeVazeiat());
        contentValues.put(PorseshnamehModel.COLUMN_CodeMely(), porseshnamehModel.getCodeMely());
        contentValues.put(PorseshnamehModel.COLUMN_ccRotbeh(), porseshnamehModel.getCcRotbeh());

        return contentValues;
    }

    private ArrayList<PorseshnamehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<PorseshnamehModel> porseshnamehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            PorseshnamehModel porseshnamehModel = new PorseshnamehModel();


            porseshnamehModel.setCcPorseshnameh(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccPorseshnameh())));
            porseshnamehModel.setCcAmargar(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccAmargar())));
            porseshnamehModel.setTarikhPorseshnameh(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_TarikhPorseshnameh())));
            porseshnamehModel.setCcMahal(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccMahal())));
            porseshnamehModel.setIsMoshtary(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_IsMoshtary())));
            porseshnamehModel.setHasDelpazir(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_HasDelpazir())));
            porseshnamehModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_NameMoshtary())));
            porseshnamehModel.setNameMaghazeh(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_NameMaghazeh())));
            porseshnamehModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccNoeMoshtary())));
            porseshnamehModel.setCcSenfMoshtary(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccSenfMoshtary())));
            porseshnamehModel.setNameMahaleh(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_NameMahaleh())));
            porseshnamehModel.setAddress(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_Address())));
            porseshnamehModel.setTelephone(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_Telephone())));
            porseshnamehModel.setMobile(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_Mobile())));
            porseshnamehModel.setPelak(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_Pelak())));
            porseshnamehModel.setCodePosty(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_CodePosty())));
            porseshnamehModel.setCcRotbeh(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccRotbeh())));
            porseshnamehModel.setCcPorseshnamehTozihat(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccPorseshnamehTozihat())));
            porseshnamehModel.setCcNoeTablighat(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccNoeTablighat())));
            porseshnamehModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ExtraProp_IsOld())));
            porseshnamehModel.setLongitude_x(cursor.getDouble(cursor.getColumnIndex(PorseshnamehModel.COLUMN_Longitude_x())));
            porseshnamehModel.setLatitude_y(cursor.getDouble(cursor.getColumnIndex(PorseshnamehModel.COLUMN_Latitude_y())));
            porseshnamehModel.setZamanSabt(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ZamanSabt())));
            porseshnamehModel.setMasahatMaghazeh(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_MasahatMaghazeh())));
            porseshnamehModel.setHasAnbar(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_HasAnbar())));
            porseshnamehModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccMoshtary())));
            porseshnamehModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_CodeVazeiat())));
            porseshnamehModel.setKhiabanAsli(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_KhiabanAsli())));
            porseshnamehModel.setKhiabanFarei1(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_KhiabanFarei1())));
            porseshnamehModel.setKhiabanFarei2(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_KhiabanFarei2())));
            porseshnamehModel.setKoocheAsli(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_KoocheAsli())));
            porseshnamehModel.setKoocheFarei1(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_KoocheFarei1())));
            porseshnamehModel.setKoocheFarei2(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_KoocheFarei2())));
            porseshnamehModel.setFNameMoshtary(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_FNameMoshtary())));
            porseshnamehModel.setLNameMoshtary(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_LNameMoshtary())));
            porseshnamehModel.setCodeMely(cursor.getString(cursor.getColumnIndex(PorseshnamehModel.COLUMN_CodeMely())));
            porseshnamehModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(PorseshnamehModel.COLUMN_ccMasir())));

            porseshnamehModels.add(porseshnamehModel);
            cursor.moveToNext();
        }
        return porseshnamehModels;
    }


}
