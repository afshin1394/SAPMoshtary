package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.KardexModel;
import com.saphamrah.Model.KardexSatrModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class KardexDAO {

    private DBHelper dbHelper;
    private Context context;



    public KardexDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KardexDAO", "", "constructor", "");
        }
    }


    private String[] allColumns() {
        return new String[]
                {
                        KardexModel.COLUMN_ccKardex(),
                        KardexModel.COLUMN_ccMarkazAnbar(),
                        KardexModel.COLUMN_ccMarkazForosh(),
                        KardexModel.COLUMN_ccAnbar(),
                        KardexModel.COLUMN_CodeNoeForm(),
                        KardexModel.COLUMN_CodeNoeAmalyat(),
                        KardexModel.COLUMN_CodeVazeiat(),
                        KardexModel.COLUMN_CodeNoeAnbar(),
                        KardexModel.COLUMN_ccMoshtary(),
                        KardexModel.COLUMN_MarjoeeKamel(),
                        KardexModel.COLUMN_ccForoshandeh(),
                        KardexModel.COLUMN_ccAfradMamurPakhsh(),
                        KardexModel.COLUMN_ccRefrence(),
                        KardexModel.COLUMN_ExtraProp_IsOld(),
                        KardexModel.COLUMN_TarikhForm(),
                        KardexModel.COLUMN_TarikhFaktor(),
                        KardexModel.COLUMN_SumGheymatMarjoee(),
                        KardexModel.COLUMN_SumTedadMarjoee(),

                };
    }


    public void insertGroup(ArrayList<KardexModel> kardexModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (KardexModel kardexModel : kardexModels) {
                ContentValues contentValues = modelToContentvalue(kardexModel);
                db.insertOrThrow(KardexModel.TableName(), null, contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (db.inTransaction()) {
                db.endTransaction();
            }
            if (db.isOpen()) {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert, KardexModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexDAO", "", "insertGroup", "");
        }
    }

    public int insert(KardexModel kardexModel) {
        int ccKardex = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
                ContentValues contentValues = modelToContentvalue(kardexModel);
                db.insertOrThrow(KardexModel.TableName(), null, contentValues);
            ccKardex = kardexModel.getCcKardex();
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (db.inTransaction()) {
                db.endTransaction();
            }
            if (db.isOpen()) {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert, KardexModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexDAO", "", "insertGroup", "");
        }

        return ccKardex;
    }

    public KardexModel SetForInsert_Kardex(int ccMarkazAnbar, int ccMarkazForosh, int ccAnbarMarjoee, int ccMoshtary, int MarjoeeKamel, int ccForoshandeh, long ccRefrence , int ExtraProp_ccElatMarjoeeKala) {

        KardexModel kardex = new KardexModel();

        kardex.setCcMarkazAnbar(ccMarkazAnbar);
        kardex.setCcMarkazForosh(ccMarkazForosh);
        kardex.setCcAnbar(ccAnbarMarjoee);
        kardex.setCodeNoeForm(10);
        kardex.setCodeNoeAmalyat(1);
        kardex.setCodeVazeiat(1);
        kardex.setCodeNoeAnbar(4);
        kardex.setCcMoshtary(ccMoshtary);
        kardex.setMarjoeeKamel(MarjoeeKamel);
        kardex.setCcForoshandeh(ccForoshandeh);
        kardex.setCcAfradMamurPakhsh(0);
        kardex.setCcRefrence((int) ccRefrence);
        kardex.setExtraProp_IsOld(0);
        kardex.setExtraProp_ccElatMarjoeeKala(ExtraProp_ccElatMarjoeeKala);

        return kardex;
    }


    public ArrayList<KardexModel> getAll() {
        ArrayList<KardexModel> kardexModels = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KardexModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    kardexModels = cursorToModels(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, KardexModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexDAO", "", "getAll", "");
        }
        return kardexModels;
    }

    public boolean deleteByccDarkhastFaktor(String ccDarkhastFaktor)
    {
        String query = "delete from " + KardexModel.TableName() + " where " + KardexModel.COLUMN_ccRefrence() + " = " + ccDarkhastFaktor;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "deleteByccKardex" , "");
            return false;
        }
    }

    public boolean deleteByccMarjoeeMamorPakhsh(int ccMarjoeeMamorPakhsh)
    {
        String query = "delete from " + KardexModel.TableName() + " where " + KardexModel.COLUMN_ccRefrence() + " = " + ccMarjoeeMamorPakhsh;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "deleteByccMarjoeeMamorPakhsh" , "");
            return false;
        }
    }

    public int findccKardexByccRefrence(long ccDarkhastFaktor){

        int ccKardex = 0;
        String query = "SELECT * FROM kardex WHERE ccRefrence = " + ccDarkhastFaktor ;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursor = db.query(KardexModel.TableName(), allColumns(), " ccMoshtary= " + ccMoshtary,null, null, null,  null);
            Cursor cursor = db.rawQuery(query , null);



            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    ccKardex = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return ccKardex;


    }

    public int findccKardexByccMoshtaryAndccMarjoeeMamorPakhsh(int ccMoshtary , int ccMarjoeeMamorPakhsh){

        int ccKardex = 0;
        String query = "SELECT * FROM kardex WHERE ccMoshtary = " + ccMoshtary + " AND ccRefrence = " + ccMarjoeeMamorPakhsh  ;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    ccKardex = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return ccKardex;


    }




    public boolean deleteAll() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KardexModel.TableName(), null, null);
            db.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll, KardexModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexDAO", "", "deleteAll", "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KardexModel kardexModel) {
        ContentValues contentValues = new ContentValues();

        if (kardexModel.getCcKardex() > 0) {
            contentValues.put(KardexModel.COLUMN_ccKardex(), kardexModel.getCcKardex());
        }
        contentValues.put(KardexModel.COLUMN_ccMarkazAnbar(), kardexModel.getCcMarkazAnbar());
        contentValues.put(KardexModel.COLUMN_ccMarkazForosh(), kardexModel.getCcMarkazForosh());
        contentValues.put(KardexModel.COLUMN_ccAnbar(), kardexModel.getCcAnbar());
        contentValues.put(KardexModel.COLUMN_CodeNoeForm(), kardexModel.getCodeNoeForm());
        contentValues.put(KardexModel.COLUMN_CodeNoeAmalyat(), kardexModel.getCodeNoeAmalyat());
        contentValues.put(KardexModel.COLUMN_CodeVazeiat(), kardexModel.getCodeVazeiat());
        contentValues.put(KardexModel.COLUMN_CodeNoeAnbar(), kardexModel.getCodeNoeAnbar());
        contentValues.put(KardexModel.COLUMN_ccMoshtary(), kardexModel.getCcMoshtary());
        contentValues.put(KardexModel.COLUMN_MarjoeeKamel(), kardexModel.getMarjoeeKamel());
        contentValues.put(KardexModel.COLUMN_ccForoshandeh(), kardexModel.getCcForoshandeh());
        contentValues.put(KardexModel.COLUMN_ccAfradMamurPakhsh(), kardexModel.getCcAfradMamurPakhsh());
        contentValues.put(KardexModel.COLUMN_ccRefrence(), kardexModel.getCcRefrence());
        contentValues.put(KardexModel.COLUMN_ExtraProp_IsOld(), kardexModel.getExtraProp_IsOld());
        contentValues.put(KardexModel.COLUMN_TarikhForm() , kardexModel.getTarikhForm());
        contentValues.put(KardexModel.COLUMN_TarikhFaktor(),kardexModel.getTarikhFaktor());
        contentValues.put(KardexModel.COLUMN_SumGheymatMarjoee(),kardexModel.getSumGheymatMarjoee());
        contentValues.put(KardexModel.COLUMN_SumTedadMarjoee(),kardexModel.getSumTedadMarjoee());
        return contentValues;
    }

    private ArrayList<KardexModel> cursorToModels(Cursor cursor) {
        ArrayList<KardexModel> kardexModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            KardexModel kardexModel = new KardexModel();

            kardexModel.setCcKardex(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccKardex())));
            kardexModel.setCcMarkazAnbar(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccMarkazAnbar())));
            kardexModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccMarkazForosh())));
            kardexModel.setCcAnbar(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccAnbar())));
            kardexModel.setCodeNoeForm(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_CodeNoeForm())));
            kardexModel.setCodeNoeAmalyat(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_CodeNoeAmalyat())));
            kardexModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_CodeVazeiat())));
            kardexModel.setCodeNoeAnbar(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_CodeNoeAnbar())));
            kardexModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccMoshtary())));
            kardexModel.setMarjoeeKamel(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_MarjoeeKamel())));
            kardexModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccForoshandeh())));
            kardexModel.setCcAfradMamurPakhsh(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccAfradMamurPakhsh())));
            kardexModel.setCcRefrence(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccRefrence())));
            kardexModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ExtraProp_IsOld())));
            kardexModel.setTarikhForm(cursor.getString(cursor.getColumnIndex(KardexModel.COLUMN_TarikhForm())));
            kardexModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(KardexModel.COLUMN_TarikhFaktor())));
            kardexModel.setSumGheymatMarjoee(cursor.getDouble(cursor.getColumnIndex(KardexModel.COLUMN_SumGheymatMarjoee())));
            kardexModel.setSumTedadMarjoee(cursor.getDouble(cursor.getColumnIndex(KardexModel.COLUMN_SumTedadMarjoee())));

            kardexModels.add(kardexModel);
            cursor.moveToNext();
        }
        return kardexModels;
    }

    private KardexModel cursorToModel(Cursor cursor) {
        KardexModel kardexModels =new KardexModel();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            KardexModel kardexModel = new KardexModel();

            kardexModel.setCcKardex(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccKardex())));
            kardexModel.setCcMarkazAnbar(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccMarkazAnbar())));
            kardexModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccMarkazForosh())));
            kardexModel.setCcAnbar(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccAnbar())));
            kardexModel.setCodeNoeForm(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_CodeNoeForm())));
            kardexModel.setCodeNoeAmalyat(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_CodeNoeAmalyat())));
            kardexModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_CodeVazeiat())));
            kardexModel.setCodeNoeAnbar(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_CodeNoeAnbar())));
            kardexModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccMoshtary())));
            kardexModel.setMarjoeeKamel(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_MarjoeeKamel())));
            kardexModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccForoshandeh())));
            kardexModel.setCcAfradMamurPakhsh(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccAfradMamurPakhsh())));
            kardexModel.setCcRefrence(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ccRefrence())));
            kardexModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(KardexModel.COLUMN_ExtraProp_IsOld())));
            kardexModel.setTarikhForm(cursor.getString(cursor.getColumnIndex(KardexModel.COLUMN_TarikhForm())));
            kardexModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(KardexModel.COLUMN_TarikhFaktor())));
            kardexModel.setSumGheymatMarjoee(cursor.getDouble(cursor.getColumnIndex(KardexModel.COLUMN_SumGheymatMarjoee())));
            kardexModel.setSumTedadMarjoee(cursor.getDouble(cursor.getColumnIndex(KardexModel.COLUMN_SumTedadMarjoee())));

            cursor.moveToNext();
        }
        return kardexModels;
    }

}
