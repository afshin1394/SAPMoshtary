package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.KalaOlaviatModel;
import com.saphamrah.Model.KalaZaribForoshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaMojodiZaribModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;


public class KalaMojodiZaribForoshDAO
{
    private DBHelper dbHelper;
    private Context context;



    public KalaMojodiZaribForoshDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaMojodiZaribForoshDAO" , "" , "constructor" , "");
        }
    }

    public ArrayList<KalaMojodiZaribModel> getAllByMoshtary(String darajeh, int noeMoshtary, int ccSazmanForosh)
    {
        ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels = new ArrayList<>();
        String query = null;
        Log.i("getAllByMoshtary", "ccSazmanForosh: "+ccSazmanForosh+"noeMoshtary:"+noeMoshtary+"daraje"+darajeh);

         /*مشتری زنجیره ای=350
         مشتری خرده=347
         مشتری عمده=348*/
       if (noeMoshtary == 350)
       {

           query = " SELECT km.*,mgk.mablaghforosh GheymatForosh,mgk.MablaghMasrafKonandeh,mgk.ControlMablagh, ZaribForosh , Darajeh, o.Olaviat FROM \n" +
                   "(SELECT k.ccKalaCode, k.CodeKala, k.NameKala, k.ccTaminKonandeh, k.TedadDarKarton, k.TedadDarBasteh, k.Adad, k.MashmolMaliatAvarez, k.ccGorohKala, k.ccBrand, \n" +
                   " k.MablaghKharid, k.Tol, k.Arz, k.Ertefa, k.ccVahedSize, k.VaznKhales, k.VaznNakhales, VaznKarton, k.ccVahedVazn, k.BarCode, k.TarikhTolid, k.TarikhEngheza, \n" +
                   " k.NameVahedVazn, k.NameBrand, k.TedadMojodyGhabelForosh, k.NameVahedSize, k.ccVahedShomaresh,k.NameVahedShomaresh, k.ShomarehBach, k.GheymatForoshAsli, k.MablaghForosh MablaghForoshKala, \n" +
                   " k.MablaghMasrafKonandeh MablaghMasrafKonandehKala, m.sumTedad, m.ccKalaMojodi, m.Max_MojodyByShomarehBach \n" +
                   " FROM Kala k \n" +
                   "              LEFT JOIN (SELECT KalaMojodi.* , SUM(Tedad) sumTedad FROM KalaMojodi where IsAdamForosh = 0 group by ccKalaCode , ShomarehBach, \n" +
                   " GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh) m \n" +
                   "    ON k.ccKalaCode = m.ccKalaCode AND k.ccTaminKonandeh = m.ccTaminkonandeh AND \n" +
                   "       k.ShomarehBach = m.ShomarehBach AND k.MablaghMasrafKonandeh = m.GheymatMasrafKonandeh AND k.MablaghForosh = m.GheymatForosh \n" +
                   " WHERE k.TedadMojodyGhabelForosh > 0 \n" +
                   " ORDER BY codekala DESC ) km \n" +
                   " LEFT JOIN (SELECT * FROM KalaZaribForosh z  WHERE ccGorohMoshtary =   " + noeMoshtary + " AND z.Darajeh IN ( 0," + darajeh + ")) z  ON km.ccKalaCode = z.ccKalaCode \n" +
                   " LEFT JOIN KalaOlaviat o on o.ccKalaCode = km.ccKalaCode \n" +
                   " LEFT JOIN (SELECT * FROM MoshtaryGharardadKala) mgk \n" +
                   "    ON mgk.ccKalaCode= km.ccKalaCode AND \n" +
                   "                 (CASE WHEN mgk.ControlMablagh = 1 AND km.mablaghforoshKala = mgk.mablaghforosh AND km.MablaghMasrafKonandehKala = mgk.MablaghMasrafKonandeh \n" +
                   "            THEN 1=1 \n" +
                   "            WHEN    mgk.ControlMablagh = 0 \n" +
                   "            THEN 1=1 \n" +
                   "       END) \n" +
                   " WHERE km.sumTedad > 0 AND mgk.ExtraPropCcSazmanForosh =  "+ ccSazmanForosh +"\n AND IFNULL(zaribforosh,0)<>0" +
                   " ORDER BY o.Olaviat";


           }
       else
           {
           query = "select km.* , IFNULL(ZaribForosh,1)ZaribForosh , Darajeh, o.Olaviat from \n" +
                   " (select k.* , m.sumTedad, m.GheymatForosh, m.ccKalaMojodi, m.Max_MojodyByShomarehBach \n" +
                   " from Kala k left join (select KalaMojodi.* , sum(Tedad) sumTedad from KalaMojodi where IsAdamForosh = 0 group by ccKalaCode , ShomarehBach, \n" +
                   " GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh) m \n" +
                   " on k.ccKalaCode = m.ccKalaCode and k.ccTaminKonandeh = m.ccTaminkonandeh and \n" +
                   " k.ShomarehBach = m.ShomarehBach and k.MablaghMasrafKonandeh = m.GheymatMasrafKonandeh and k.MablaghForosh = m.GheymatForosh \n" +
                   " where k.TedadMojodyGhabelForosh > 0 \n" +
                   " order by codekala desc ) km left join \n" +
                   " (select * from KalaZaribForosh z \n" +
                   " where ccGorohMoshtary = " + noeMoshtary +
                   " and z.Darajeh IN ( 0," + darajeh + " )) z \n" +
                   " on km.ccKalaCode = z.ccKalaCode \n" +
                   " left join KalaOlaviat o on o.ccKalaCode = km.ccKalaCode \n" +
                   " where km.sumTedad > 0 order by o.Olaviat";
           }
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiZaribModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "KalaMojodi,KalaZaribForosh,Kala") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BankDAO" , "" , "getAllByccMoshtary" , "");
        }
        return kalaMojodiZaribModels;
    }

    public ArrayList<KalaMojodiZaribModel> getByMoshtaryAndccKalaCode(String darajeh , int noeMoshtary , String ccKalaCode, int moshtaryGharardadccSazmanForosh)
    {
        String query = null;
        ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels = new ArrayList<>();
        if (noeMoshtary == 350)
        {

            query = " SELECT km.*,mgk.mablaghforosh GheymatForosh,mgk.MablaghMasrafKonandeh,mgk.ControlMablagh, ZaribForosh , Darajeh, o.Olaviat FROM \n" +
                    "(SELECT k.ccKalaCode, k.CodeKala, k.NameKala, k.ccTaminKonandeh, k.TedadDarKarton, k.TedadDarBasteh, k.Adad, k.MashmolMaliatAvarez, k.ccGorohKala, k.ccBrand, \n" +
                    " k.MablaghKharid, k.Tol, k.Arz, k.Ertefa, k.ccVahedSize, k.VaznKhales, k.VaznNakhales, VaznKarton, k.ccVahedVazn, k.BarCode, k.TarikhTolid, k.TarikhEngheza, \n" +
                    " k.NameVahedVazn, k.NameBrand, k.TedadMojodyGhabelForosh, k.NameVahedSize, k.ccVahedShomaresh,k.NameVahedShomaresh, k.ShomarehBach, k.GheymatForoshAsli, k.MablaghForosh MablaghForoshKala, \n" +
                    " k.MablaghMasrafKonandeh MablaghMasrafKonandehKala, m.sumTedad, m.ccKalaMojodi, m.Max_MojodyByShomarehBach \n" +
                    " FROM Kala k \n" +
                    "              LEFT JOIN (SELECT KalaMojodi.* , SUM(Tedad) sumTedad FROM KalaMojodi where IsAdamForosh = 0 group by ccKalaCode , ShomarehBach, \n" +
                    " GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh) m \n" +
                    "    ON k.ccKalaCode = m.ccKalaCode AND k.ccTaminKonandeh = m.ccTaminkonandeh AND \n" +
                    "       k.ShomarehBach = m.ShomarehBach AND k.MablaghMasrafKonandeh = m.GheymatMasrafKonandeh AND k.GheymatForoshAsli = m.GheymatForosh \n" +
                    " WHERE k.TedadMojodyGhabelForosh > 0 \n" +
                    " ORDER BY codekala DESC ) km \n" +
                    " LEFT JOIN (SELECT * FROM KalaZaribForosh z  WHERE ccGorohMoshtary =   " + noeMoshtary + " AND z.Darajeh IN ( 0," + darajeh + ")) z  ON km.ccKalaCode = z.ccKalaCode \n" +
                    " LEFT JOIN KalaOlaviat o on o.ccKalaCode = km.ccKalaCode \n" +
                    " LEFT JOIN (SELECT * FROM MoshtaryGharardadKala) mgk \n" +
                    "    ON mgk.ccKalaCode= km.ccKalaCode AND \n" +
                    "                 (CASE WHEN mgk.ControlMablagh = 1 AND km.mablaghforoshKala = mgk.mablaghforosh AND km.MablaghMasrafKonandehKala = mgk.MablaghMasrafKonandeh \n" +
                    "            THEN 1=1 \n" +
                    "            WHEN    mgk.ControlMablagh = 0 \n" +
                    "            THEN 1=1 \n" +
                    "       END) \n" +
                    " WHERE km.sumTedad > 0 AND mgk.ExtraPropCcSazmanForosh =  "+ moshtaryGharardadccSazmanForosh +"\n AND IFNULL(zaribforosh,0)<>0" +
                    " ORDER BY o.Olaviat";


        }
        else {
            query = "select km.* , IFNULL(ZaribForosh,1)ZaribForosh , Darajeh , o.Olaviat from \n" +
                    " (select k.* , m.sumTedad, m.GheymatForosh, m.ccKalaMojodi, m.Max_MojodyByShomarehBach \n" +
                    " from Kala k left join (select KalaMojodi.* , sum(Tedad) sumTedad from KalaMojodi where ccKalaCode = " + ccKalaCode + " and IsAdamForosh = 0 " +
                    " group by ccKalaCode , ShomarehBach, \n" +
                    " GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh) m \n" +
                    " on k.ccKalaCode = m.ccKalaCode and k.ccTaminKonandeh = m.ccTaminkonandeh and \n" +
                    " k.ShomarehBach = m.ShomarehBach and k.MablaghMasrafKonandeh = m.GheymatMasrafKonandeh and k.MablaghForosh = m.GheymatForosh \n" +
                    " where k.TedadMojodyGhabelForosh > 0 \n" +
                    " order by codekala desc ) km left join \n" +
                    " (select * from KalaZaribForosh z \n" +
                    " where ccGorohMoshtary = " + noeMoshtary +
                    " and z.Darajeh IN ( 0," + darajeh + " )) z \n" +
                    " on km.ccKalaCode = z.ccKalaCode \n" +
                    " left join KalaOlaviat o on o.ccKalaCode = km.ccKalaCode \n" +
                    " where km.sumTedad > 0 order by km.TarikhTolid";
        }
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiZaribModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "KalaMojodi,KalaZaribForosh,Kala") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BankDAO" , "" , "getAllByccMoshtary" , "");
        }
        return kalaMojodiZaribModels;
    }

    private ArrayList<KalaMojodiZaribModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaMojodiZaribModel kalaMojodiZaribModel = new KalaMojodiZaribModel();

            //Kala
            kalaMojodiZaribModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccKalaCode())));
            kalaMojodiZaribModel.setCodeKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_CodeKala())));
            kalaMojodiZaribModel.setBarCode(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_BarCode())));
            kalaMojodiZaribModel.setNameBrand(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameBrand())));
            kalaMojodiZaribModel.setNameKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameKala())));
            kalaMojodiZaribModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccTaminKonandeh())));
            kalaMojodiZaribModel.setTedadDarKarton(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadDarKarton())));
            kalaMojodiZaribModel.setTedadDarBasteh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadDarBasteh())));
            kalaMojodiZaribModel.setTedadMojodyGhabelForosh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadMojodyGhabelForosh())));
            kalaMojodiZaribModel.setAdad(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_Adad())));
            kalaMojodiZaribModel.setVaznKhales(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_VaznKhales())));
            kalaMojodiZaribModel.setVaznKarton(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_VaznKarton())));
            kalaMojodiZaribModel.setNameVahedVazn(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedVazn())));
            kalaMojodiZaribModel.setNameVahedSize(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedSize())));
            kalaMojodiZaribModel.setTol(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_Tol())));
            kalaMojodiZaribModel.setArz(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_Arz())));
            kalaMojodiZaribModel.setErtefa(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_Ertefa())));
            kalaMojodiZaribModel.setMashmolMaliatAvarez(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_MashmolMaliatAvarez())));
            // KalaMojodi
            kalaMojodiZaribModel.setCcKalaMojodi(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccKalaMojodi())));
            kalaMojodiZaribModel.setTedad(cursor.getInt(cursor.getColumnIndex("sumTedad")));
            kalaMojodiZaribModel.setMax_MojodyByShomarehBach(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Max_MojodyByShomarehBach())));
            kalaMojodiZaribModel.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatForosh())));
            kalaMojodiZaribModel.setMablaghMasrafKonandeh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_MablaghMasrafKonandeh())));
            kalaMojodiZaribModel.setZaribForosh(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ZaribForosh())));
            kalaMojodiZaribModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_ShomarehBach())));
            kalaMojodiZaribModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_TarikhTolid())));
            kalaMojodiZaribModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_TarikhEngheza())));

            // KalaOlaviat
            kalaMojodiZaribModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(KalaOlaviatModel.COLUMN_Olaviat())));

            kalaMojodiZaribModels.add(kalaMojodiZaribModel);
            cursor.moveToNext();
        }
        return kalaMojodiZaribModels;
    }
}
