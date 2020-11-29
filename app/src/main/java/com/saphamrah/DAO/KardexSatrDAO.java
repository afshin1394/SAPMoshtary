package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.KardexSatrModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

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
            KardexSatrModel.COLUMN_GheymatForoshAsli()
        };
    }


    public void insertGroup(ArrayList<KardexSatrModel> kardexSatrModels)
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
        }
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
            kardexSatrModel.setGheymatKharid(cursor.getFloat(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatKharid())));
            kardexSatrModel.setGheymatForosh(cursor.getFloat(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatForosh())));
            kardexSatrModel.setGheymatForoshKhales(cursor.getFloat(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatForoshKhales())));
            kardexSatrModel.setGheymatMasrafKonandeh(cursor.getFloat(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatMasrafKonandeh())));
            kardexSatrModel.setCcAnbarGhesmat(cursor.getInt(cursor.getColumnIndex(KardexSatrModel.COLUMN_ccAnbarGhesmat())));
            kardexSatrModel.setGheymatForoshAsli(cursor.getFloat(cursor.getColumnIndex(KardexSatrModel.COLUMN_GheymatForoshAsli())));

            kardexSatrModels.add(kardexSatrModel);
            cursor.moveToNext();
        }
        return kardexSatrModels;
    }



}
