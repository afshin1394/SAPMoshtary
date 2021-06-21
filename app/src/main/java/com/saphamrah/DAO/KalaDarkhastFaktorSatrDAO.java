package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class KalaDarkhastFaktorSatrDAO
{

    private DBHelper dbHelper;
    private Context context;


    public KalaDarkhastFaktorSatrDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaDarkhastFaktorDAO" , "" , "constructor" , "");
        }
    }


    public ArrayList<KalaDarkhastFaktorSatrModel> getByccDarkhast(long ccDarkhastFaktor)
    {
        ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            String query = "select fs.*, k.CodeKala, k.BarCode, k.Adad, k.TedadDarKarton, k.TedadDarBasteh, k.ccGorohKala, k.NameKala, k.Tol, k.Arz, k.Ertefa, \n" +
                    " k.NameVahedShomaresh, k.NameBrand, k.NameVahedSize, k.NameVahedVazn, k.VaznKarton, k.VaznKhales \n" +
                    " from DarkhastFaktorSatr fs inner join Kala k on k.ccKalaCode = fs.ccKalaCode and k.ShomarehBach = fs.ShomarehBach \n" +
                    " and k.gheymatForoshAsli = fs.gheymatForoshAsli and k.MablaghMasrafKonandeh = fs.GheymatMasrafKonandeh\n" +
                    " and k.ccTaminKonandeh = fs.ccTaminKonandeh where fs.CodeNoeKala != 2 And ccDarkhastFaktor = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaDarkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "DarkhastFaktorSatr , Kala") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDarkhastFaktorDAO" , "" , "getByccDarkhast" , "");
        }
        return kalaDarkhastFaktorSatrModels;
    }

    public ArrayList<KalaDarkhastFaktorSatrModel> getByccDarkhastMarjoeeKoli(long ccDarkhastFaktor)
    {
        ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            String query = "select fs.*, k.CodeKala, k.BarCode, k.Adad, k.TedadDarKarton, k.TedadDarBasteh, k.ccGorohKala, k.NameKala, k.Tol, k.Arz, k.Ertefa, \n" +
                    " k.NameVahedShomaresh, k.NameBrand, k.NameVahedSize, k.NameVahedVazn, k.VaznKarton, k.VaznKhales \n" +
                    " from DarkhastFaktorSatr fs inner join Kala k on k.ccKalaCode = fs.ccKalaCode  where fs.CodeNoeKala != 2 And ccDarkhastFaktor = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaDarkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "DarkhastFaktorSatr , Kala") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDarkhastFaktorDAO" , "" , "getByccDarkhast" , "");
        }
        return kalaDarkhastFaktorSatrModels;
    }


	public ArrayList<KalaDarkhastFaktorSatrModel> getByccDarkhastWithoutGroup(long ccDarkhastFaktor)
    {
        ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            String query = "select fs.*, k.CodeKala, k.BarCode, k.Adad, k.TedadDarKarton, k.TedadDarBasteh, k.ccGorohKala, k.NameKala, k.Tol, k.Arz, k.Ertefa, \n" +
                    " k.NameVahedShomaresh, k.NameBrand, k.NameVahedSize, k.NameVahedVazn, k.VaznKarton, k.VaznKhales \n" +
                    " from DarkhastFaktorSatr fs inner join Kala k on k.ccKalaCode = fs.ccKalaCode where ccDarkhastFaktor = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaDarkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "DarkhastFaktorSatr , Kala") + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "KalaDarkhastFaktorDAO" , "" , "getByccDarkhastWithoutGroup" , "");
        }
        return kalaDarkhastFaktorSatrModels;
    }
	
    public ArrayList<KalaDarkhastFaktorSatrModel> getByccDarkhastForTreasury(long ccDarkhastFaktor)
    {
        ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = new ArrayList<>();
        try
        {
            String query = "select fs.* , k.CodeKala, k.BarCode, k.Adad, k.TedadDarKarton, k.TedadDarBasteh, k.ccGorohKala, k.NameKala, k.Tol, k.Arz, k.Ertefa, \n" +
                    " k.NameVahedShomaresh, k.NameBrand, k.NameVahedSize, k.NameVahedVazn, k.VaznKarton, k.VaznKhales \n" +
                    " from DarkhastFaktorSatr fs inner join (select distinct ccKalaCode,CodeKala, BarCode, Adad, TedadDarKarton, TedadDarBasteh, ccGorohKala, NameKala, \n" +
                    " Tol, Arz, Ertefa, \n" +
                    " NameVahedShomaresh, NameBrand, NameVahedSize, NameVahedVazn, VaznKarton, VaznKhales from kala) k on k.ccKalaCode = fs.ccKalaCode \n" +
                    " where tedad3 > 0 and ccDarkhastFaktor = " + ccDarkhastFaktor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaDarkhastFaktorSatrModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "DarkhastFaktorSatr , Kala") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDarkhastFaktorDAO" , "" , "getByccDarkhastForTreasury" , "");
        }
        return kalaDarkhastFaktorSatrModels;
    }


    private ArrayList<KalaDarkhastFaktorSatrModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaDarkhastFaktorSatrModel kalaDarkhastFaktorSatrModel = new KalaDarkhastFaktorSatrModel();

            //DarkhastFaktorSatr
            kalaDarkhastFaktorSatrModel.setCcDarkhastFaktorSatr(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktorSatr())));
            kalaDarkhastFaktorSatrModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccDarkhastFaktor())));
            kalaDarkhastFaktorSatrModel.setCcKala(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKala())));
            kalaDarkhastFaktorSatrModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccKalaCode())));
            kalaDarkhastFaktorSatrModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ccTaminKonandeh())));
            kalaDarkhastFaktorSatrModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ShomarehBach())));
            kalaDarkhastFaktorSatrModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_TarikhTolid())));
            kalaDarkhastFaktorSatrModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_TarikhEngheza())));
            kalaDarkhastFaktorSatrModel.setTedad3(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Tedad3())));
            kalaDarkhastFaktorSatrModel.setMablaghForosh(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghForosh())));
            kalaDarkhastFaktorSatrModel.setMablaghTakhfifNaghdiVahed(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghTakhfifNaghdiVahed())));
            kalaDarkhastFaktorSatrModel.setMaliat(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Maliat())));
            kalaDarkhastFaktorSatrModel.setAvarez(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Avarez())));
            kalaDarkhastFaktorSatrModel.setMablaghForoshKhalesKala(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghForoshKhalesKala())));
            kalaDarkhastFaktorSatrModel.setMablaghKharid(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_MablaghKharid())));
            kalaDarkhastFaktorSatrModel.setGheymatMasrafKonandeh(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_GheymatMasrafKonandeh())));
            kalaDarkhastFaktorSatrModel.setGheymatForoshAsli(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_GheymatForoshAsli())));
            kalaDarkhastFaktorSatrModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_ExtraProp_IsOld())) > 0);

            // Kala
            kalaDarkhastFaktorSatrModel.setCodeKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_CodeKala())));
            kalaDarkhastFaktorSatrModel.setBarCode(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_BarCode())));
            kalaDarkhastFaktorSatrModel.setNameBrand(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameBrand())));
            kalaDarkhastFaktorSatrModel.setNameKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameKala())));
            kalaDarkhastFaktorSatrModel.setTedadDarKarton(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadDarKarton())));
            kalaDarkhastFaktorSatrModel.setTedadDarBasteh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadDarBasteh())));
            kalaDarkhastFaktorSatrModel.setAdad(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_Adad())));
            kalaDarkhastFaktorSatrModel.setArz(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_Arz())));
            kalaDarkhastFaktorSatrModel.setTol(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_Tol())));
            kalaDarkhastFaktorSatrModel.setErtefa(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_Ertefa())));
            kalaDarkhastFaktorSatrModel.setNameVahedShomaresh(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedShomaresh())));
            kalaDarkhastFaktorSatrModel.setNameVahedSize(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedSize())));
            kalaDarkhastFaktorSatrModel.setNameVahedVazn(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedVazn())));
            kalaDarkhastFaktorSatrModel.setVaznKarton(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_VaznKarton())));
            kalaDarkhastFaktorSatrModel.setVaznKhales(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_VaznKhales())));


            kalaDarkhastFaktorSatrModels.add(kalaDarkhastFaktorSatrModel);
            cursor.moveToNext();
        }
        return kalaDarkhastFaktorSatrModels;
    }


}
