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
import java.util.Date;

public class KardexSatrDAO
{

    private DBHelper dbHelper;
    private Context context;


    public KardexSatrDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KardexSatrDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
                {
                        KardexSatrModel.COLUMN_ccKardexSatr(),
                        KardexSatrModel.COLUMN_ccKardex(),
                        KardexSatrModel.COLUMN_ccTaminKonandeh(),
                        KardexSatrModel.COLUMN_ccKalaCode(),
                        KardexSatrModel.COLUMN_ShomarehBach(),
                        KardexSatrModel.COLUMN_TarikhTolid(),
                        KardexSatrModel.COLUMN_TarikhEngheza(),
                        KardexSatrModel.COLUMN_Tedad3(),
                        KardexSatrModel.COLUMN_ccElat(),
                        KardexSatrModel.COLUMN_NameElat(),
                        KardexSatrModel.COLUMN_CodeNoeKala(),
                        KardexSatrModel.COLUMN_CodeKalaOld(),
                        KardexSatrModel.COLUMN_NameKala(),
                        KardexSatrModel.COLUMN_ccElamMarjoeeForoshandeh(),
                        KardexSatrModel.COLUMN_ccMarjoeeMamorPakhsh(),
                        KardexSatrModel.COLUMN_ccMoshtary(),
                        KardexSatrModel.COLUMN_TarikhTolidShamsi(),
                        KardexSatrModel.COLUMN_GheymatKharid(),
                        KardexSatrModel.COLUMN_GheymatForosh(),
                        KardexSatrModel.COLUMN_GheymatForoshKhales(),
                        KardexSatrModel.COLUMN_GheymatMasrafKonandeh(),
                        KardexSatrModel.COLUMN_ccAnbarGhesmat(),
                        KardexSatrModel.COLUMN_GheymatForoshAsli(),
                        KardexSatrModel.COLUMN_ccKala()

                };
    }


    public boolean insertGroup(ArrayList<KardexSatrModel> kardexSatrModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KardexSatrModel kardexSatrModel : kardexSatrModels)
            {
                ContentValues contentValues = modelToContentvalue(kardexSatrModel);
                db.insertOrThrow(KardexSatrModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public int insert(KardexSatrModel kardexSatrModel) {
        int ccKardexSatr = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues contentValues = modelToContentvalue(kardexSatrModel);
            db.insertOrThrow(KardexSatrModel.TableName(), null, contentValues);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "kardexSatrDAO", "", "insertGroup", "");
        }

        ccKardexSatr = getCcKardexSatr(kardexSatrModel.getCcKardex());
        return ccKardexSatr;
    }

    public int getCcKardexSatr(long ccKardex) {
        int ccKardexSatr = 0;
        String query = "SELECT ccKardexSatr FROM kardexSatr WHERE ccKardex = " + ccKardex;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        ccKardexSatr = cursor.getInt(0);

                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "kardexSatrDAO", "", "insertGroup", "");
        }

        return ccKardexSatr;
    }



    public ArrayList<KardexSatrModel> getAll()
    {
        ArrayList<KardexSatrModel> kardexSatrModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KardexSatrModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kardexSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "getAll" , "");
        }
        return kardexSatrModels;
    }

    public ArrayList<KardexSatrModel> getByCcKardex(long ccKardex)
    {
        ArrayList<KardexSatrModel> kardexSatrModels = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            String query = "SELECT * FROM kardexSatr WHERE ccKardex = " + ccKardex;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kardexSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "getByCcKardex" , "");
        }
        return kardexSatrModels;
    }


    public KardexSatrModel getByccKardexSatr(int ccKardexSatr)
    {
        ArrayList<KardexSatrModel> kardexSatrModels = new ArrayList<>();
        KardexSatrModel kardexSatrModel = new KardexSatrModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KardexSatrModel.TableName(), allColumns(), KardexSatrModel.COLUMN_ccKardexSatr() + " = " + ccKardexSatr, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kardexSatrModels = cursorToModel(cursor);

                    if (kardexSatrModels.size() > 0){
                        kardexSatrModel = kardexSatrModels.get(0);
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "getByccKardexSatr" , "");
        }
        return kardexSatrModel;
    }

   public int getCcKardexSatrForUpdateTedadMarjoeeForoshandeh(int ccElamMarjoeeSatr){
        int ccDarkhastSatr = 0;

       try
       {
           SQLiteDatabase db = dbHelper.getReadableDatabase();
           String query = "select "+  KardexSatrModel.COLUMN_ccKardexSatr() +" from " + KardexSatrModel.TableName() +" where "+ KardexSatrModel.COLUMN_ccElamMarjoeeForoshandeh() +" = " + ccElamMarjoeeSatr;
           Cursor cursor = db.rawQuery(query , null);
           if (cursor != null)
           {
               if (cursor.getCount() > 0)
               {
                   cursor.moveToFirst();
                   while (!cursor.isAfterLast())
                   {
                       ccDarkhastSatr = cursor.getInt(0);

                       cursor.moveToNext();
                   }
               }
               cursor.close();
           }
           db.close();
       }
       catch (Exception exception)
       {
           exception.printStackTrace();
           PubFunc.Logger logger = new PubFunc().new Logger();
           String message = context.getResources().getString(R.string.errorSelectAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
           logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "getCcKardexSatrForUpdateTedadMarjoeeForoshandeh" , "");
       }

        return ccDarkhastSatr;
   }

    public KardexSatrModel setForInsert_KardexSatr(long ccKardex, int ccTaminKonandeh, int ccKala, int ccKalaCode, String ShomarehBach, String TarikhTolid, String TarikhEngheza,
                                                   int Tedad3, double GheymatKharid, double GheymatForosh, double GheymatForoshKhales, double GheymatMasrafKonandeh, double GheymatForoshAsli, int ccElatMarjoee, String NameElatMarjoee, int CodeNoeKala,
                                                   String CodeKalaOld, String NameKala, int ccElamMarjoeeForoshandeh, int ccMarjoeeMamorPakhsh, int ccMoshtary, String TarikhTolidShamsi, int ccAnbarGhesmat)
    {

        KardexSatrModel kardexSatr = new KardexSatrModel();

        kardexSatr.setCcKardex(ccKardex);
        kardexSatr.setCcTaminKonandeh(ccTaminKonandeh);
        kardexSatr.setCcKalaCode(ccKalaCode);
        kardexSatr.setCcKala(ccKala);
        kardexSatr.setShomarehBach(ShomarehBach);
        kardexSatr.setTarikhTolid(TarikhTolid);
        kardexSatr.setTarikhEngheza(TarikhEngheza);
        kardexSatr.setTedad3(Tedad3);
        kardexSatr.setGheymatKharid(GheymatKharid);
        kardexSatr.setGheymatForosh(GheymatForosh);
        kardexSatr.setGheymatForoshKhales(GheymatForoshKhales);
        kardexSatr.setGheymatMasrafKonandeh(GheymatMasrafKonandeh);
        kardexSatr.setGheymatForoshAsli(GheymatForoshAsli);
        kardexSatr.setCcElat(ccElatMarjoee);
        kardexSatr.setNameElat(NameElatMarjoee);
        kardexSatr.setCodeNoeKala(CodeNoeKala);
        kardexSatr.setCodeKalaOld(CodeKalaOld);
        kardexSatr.setNameKala(NameKala);
        kardexSatr.setCcElamMarjoeeForoshandeh(ccElamMarjoeeForoshandeh);
        kardexSatr.setCcMarjoeeMamorPakhsh(ccMarjoeeMamorPakhsh);
        kardexSatr.setCcMoshtary(ccMoshtary);
        kardexSatr.setTarikhTolidShamsi(TarikhTolidShamsi);
        kardexSatr.setCcAnbarGhesmat(ccAnbarGhesmat);

        return kardexSatr;
    }


    public boolean updateByccKardexSatr(long ccKardexSatr , long tedadMarjoeeForInsert)
    {
        try
        {
            String query = "update " + KardexSatrModel.TableName() + " set " + KardexSatrModel.COLUMN_Tedad3() + " = " + tedadMarjoeeForInsert +
                    " where " + KardexSatrModel.COLUMN_ccKardexSatr() + " = " + ccKardexSatr;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "updateSendedccDarkhastFaktor" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KardexSatrModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KardexSatrModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KardexSatrDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccKardexAndccKalaCode(long ccKardex,int ccKalaCode)
    {
        String query = "delete from " + KardexSatrModel.TableName() + " where " + KardexSatrModel.COLUMN_ccKardex() + " = " + ccKardex + " And ccKalaCode = " + ccKalaCode;
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



    private static ContentValues modelToContentvalue(KardexSatrModel kardexSatrModel)
    {
        ContentValues contentValues = new ContentValues();

        if (kardexSatrModel.getCcKardexSatr() > 0)
        {
            contentValues.put(KardexSatrModel.COLUMN_ccKardexSatr() , kardexSatrModel.getCcKardexSatr());
        }
        contentValues.put(KardexSatrModel.COLUMN_ccKardex() , kardexSatrModel.getCcKardex());
        contentValues.put(KardexSatrModel.COLUMN_ccTaminKonandeh() , kardexSatrModel.getCcTaminKonandeh());
        contentValues.put(KardexSatrModel.COLUMN_ccKalaCode() , kardexSatrModel.getCcKalaCode());
        contentValues.put(KardexSatrModel.COLUMN_ShomarehBach() , kardexSatrModel.getShomarehBach());
        contentValues.put(KardexSatrModel.COLUMN_TarikhTolid() , kardexSatrModel.getTarikhTolid());
        contentValues.put(KardexSatrModel.COLUMN_TarikhEngheza() , kardexSatrModel.getTarikhEngheza());
        contentValues.put(KardexSatrModel.COLUMN_Tedad3() , kardexSatrModel.getTedad3());
        contentValues.put(KardexSatrModel.COLUMN_ccElat() , kardexSatrModel.getCcElat());
        contentValues.put(KardexSatrModel.COLUMN_NameElat() , kardexSatrModel.getNameElat());
        contentValues.put(KardexSatrModel.COLUMN_CodeNoeKala() , kardexSatrModel.getCodeNoeKala());
        contentValues.put(KardexSatrModel.COLUMN_CodeKalaOld() , kardexSatrModel.getCodeKalaOld());
        contentValues.put(KardexSatrModel.COLUMN_NameKala() , kardexSatrModel.getNameKala());
        contentValues.put(KardexSatrModel.COLUMN_ccElamMarjoeeForoshandeh() , kardexSatrModel.getCcElamMarjoeeForoshandeh());
        contentValues.put(KardexSatrModel.COLUMN_ccMarjoeeMamorPakhsh() , kardexSatrModel.getCcMarjoeeMamorPakhsh());
        contentValues.put(KardexSatrModel.COLUMN_ccMoshtary() , kardexSatrModel.getCcMoshtary());
        contentValues.put(KardexSatrModel.COLUMN_TarikhTolidShamsi() , kardexSatrModel.getTarikhTolidShamsi());
        contentValues.put(KardexSatrModel.COLUMN_GheymatKharid() , kardexSatrModel.getGheymatKharid());
        contentValues.put(KardexSatrModel.COLUMN_GheymatForosh() , kardexSatrModel.getGheymatForosh());
        contentValues.put(KardexSatrModel.COLUMN_GheymatForoshKhales() , kardexSatrModel.getGheymatForoshKhales());
        contentValues.put(KardexSatrModel.COLUMN_GheymatMasrafKonandeh() , kardexSatrModel.getGheymatMasrafKonandeh());
        contentValues.put(KardexSatrModel.COLUMN_ccAnbarGhesmat() , kardexSatrModel.getCcAnbarGhesmat());
        contentValues.put(KardexSatrModel.COLUMN_GheymatForoshAsli() , kardexSatrModel.getGheymatForoshAsli());
        contentValues.put(KardexSatrModel.COLUMN_ccKala() , kardexSatrModel.getCcKala());

        return contentValues;
    }


    private ArrayList<KardexSatrModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KardexSatrModel> kardexSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KardexSatrModel kardexSatrModel = new KardexSatrModel();

            kardexSatrModel.setCcKardexSatr(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccKardexSatr())));
            kardexSatrModel.setCcKardex(cursor.getLong(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccKardex())));
            kardexSatrModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccTaminKonandeh())));
            kardexSatrModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccKalaCode())));
            kardexSatrModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(KardexSatrModel.COLUMN_ShomarehBach())));
            kardexSatrModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(KardexSatrModel.COLUMN_TarikhTolid())));
            kardexSatrModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(KardexSatrModel.COLUMN_TarikhEngheza())));
            kardexSatrModel.setTedad3(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_Tedad3())));
            kardexSatrModel.setCcElat(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccElat())));
            kardexSatrModel.setNameElat(cursor.getString(cursor.getColumnIndex(KardexSatrModel.COLUMN_NameElat())));
            kardexSatrModel.setCodeNoeKala(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_CodeNoeKala())));
            kardexSatrModel.setCodeKalaOld(cursor.getString(cursor.getColumnIndex(KardexSatrModel.COLUMN_CodeKalaOld())));
            kardexSatrModel.setNameKala(cursor.getString(cursor.getColumnIndex(KardexSatrModel.COLUMN_NameKala())));
            kardexSatrModel.setCcElamMarjoeeForoshandeh(cursor.getLong(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccElamMarjoeeForoshandeh())));
            kardexSatrModel.setCcMarjoeeMamorPakhsh(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccMarjoeeMamorPakhsh())));
            kardexSatrModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccMoshtary())));
            kardexSatrModel.setTarikhTolidShamsi(cursor.getString(cursor.getColumnIndex(KardexSatrModel.COLUMN_TarikhTolidShamsi())));
            kardexSatrModel.setGheymatKharid(cursor.getDouble(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatKharid())));
            kardexSatrModel.setGheymatForosh(cursor.getDouble(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatForosh())));
            kardexSatrModel.setGheymatForoshKhales(cursor.getDouble(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatForoshKhales())));
            kardexSatrModel.setGheymatMasrafKonandeh(cursor.getDouble(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatMasrafKonandeh())));
            kardexSatrModel.setCcAnbarGhesmat(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccAnbarGhesmat())));
            kardexSatrModel.setGheymatForoshAsli(cursor.getDouble(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatForoshAsli())));
            kardexSatrModel.setCcKala(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccKala())));


            kardexSatrModels.add(kardexSatrModel);
            cursor.moveToNext();
        }
        return kardexSatrModels;
    }






}
