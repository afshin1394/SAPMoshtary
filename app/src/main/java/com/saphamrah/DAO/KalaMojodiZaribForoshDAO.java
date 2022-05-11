package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.saphamrah.MVP.View.DarkhastKalaActivity;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.KalaOlaviatModel;
import com.saphamrah.Model.KalaPhotoModel;
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


    public ArrayList<KalaMojodiZaribModel> getAllByMoshtary(String ccDarajeh, int ccNoeMoshtary, int ccNoeSenf,int ccMarkazForosh, int ccSazmanForosh, int MoshtaryGharardadccSazmanForosh, int ccMoshtaryGharardad,int zangireiParam, DarkhastKalaActivity.AddItemType type)
    {
        ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels = new ArrayList<>();
        String query = null;
        Log.i("getAllByMoshtary", "KalaMojodiZarib ccMarkazForosh:" + ccMarkazForosh + " ,ccSazmanForosh: "+ccSazmanForosh +" ,ccNoeMoshtary:"+ccNoeMoshtary + " ,ccNoeSenf:"+ ccNoeSenf + " ,ccDarajeh"+ccDarajeh + " ,MoshtaryGharardadccSazmanForosh:" + MoshtaryGharardadccSazmanForosh +  " ,ccMoshtaryGharardad:" + ccMoshtaryGharardad + " ,type:" + type);

         /*مشتری زنجیره ای=350
         مشتری خرده=347
         مشتری عمده= 348*/


       if ( ccNoeMoshtary ==  zangireiParam)
       {
           query = " SELECT km.*,mgk.mablaghforosh GheymatForosh,mgk.MablaghMasrafKonandeh, mgk.GheymatKharid,mgk.ControlMablagh, \n" +
                   "        (SELECT  IFNULL(ZaribForosh,1)ZaribForosh  \n" +
                   "         FROM KalaZaribForosh z \n" +
                   "         WHERE z.ccGorohMoshtary IN (0," + ccNoeMoshtary + ")  AND z.ccKalaCode =km.ccKalaCode AND z.Darajeh IN (0," + ccDarajeh + ") \n" +
                   "         ORDER BY z.Darajeh DESC limit 1) ZaribForosh,\n" +
                   "        (SELECT  Darajeh  \n" +
                   "         FROM KalaZaribForosh z \n" +
                   "         WHERE z.ccGorohMoshtary =  347 AND z.ccKalaCode =km.ccKalaCode AND z.Darajeh IN ( 0,3) ORDER BY z.Darajeh DESC limit 1) Darajeh, \n" +
                   " o.Olaviat , kp.Photo FROM \n" +
                   " (SELECT k.ccKalaCode, k.CodeKala, k.NameKala, k.ccTaminKonandeh, k.TedadDarKarton, k.TedadDarBasteh, k.Adad, k.MashmolMaliatAvarez, k.ccGorohKala, k.ccBrand, \n" +
                   "         k.MablaghKharid, k.Tol, k.Arz, k.Ertefa, k.ccVahedSize, k.VaznKhales, k.VaznNakhales, VaznKarton, k.ccVahedVazn, k.BarCode, k.TarikhTolid, k.TarikhEngheza, \n" +
                   "         k.NameVahedVazn, k.NameBrand, k.TedadMojodyGhabelForosh, k.NameVahedSize, k.ccVahedShomaresh,k.NameVahedShomaresh, k.ShomarehBach, k.GheymatForoshAsli,k.GheymatMasrafKonandehAsli, k.MablaghForosh MablaghForoshKala, m.GheymatKharid,  \n" +
                   "         k.MablaghMasrafKonandeh MablaghMasrafKonandehKala, m.sumTedad, m.ccKalaMojodi, m.sumMax_MojodyByShomarehBach Max_MojodyByShomarehBach \n" +
                   "  FROM Kala k \n" +
                   "              LEFT JOIN (SELECT KalaMojodi.* , SUM(Tedad) sumTedad, sum(Max_Mojody) sumMax_Mojody, sum(Max_MojodyByShomarehBach) sumMax_MojodyByShomarehBach FROM KalaMojodi where IsAdamForosh = 0 group by ccKalaCode , ShomarehBach, \n" +
                   "                                GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh,TarikhTolid,TarikhEngheza) m \n" +
                   "              ON k.ccKalaCode = m.ccKalaCode AND k.ccTaminKonandeh = m.ccTaminkonandeh AND \n" +
                   "                 k.ShomarehBach = m.ShomarehBach AND k.GheymatMasrafKonandehAsli = m.GheymatMasrafKonandeh AND k.GheymatForoshAsli = m.GheymatForosh AND \n" +
                   "                 k.TarikhTolid = m.TarikhTolid AND k.TarikhEngheza = m.TarikhEngheza \n" +
                   " WHERE k.TedadMojodyGhabelForosh > 0 \n" +
                   " ORDER BY codekala DESC ) km \n" +
                   " LEFT JOIN KalaOlaviat o on o.ccKalaCode = km.ccKalaCode \n" +
                   " LEFT JOIN (SELECT * FROM KalaPhoto) kp ON kp.ccKalaCode= km.ccKalaCode \n" +
                   " LEFT JOIN (SELECT * FROM MoshtaryGharardadKala WHERE ccMoshtaryGharardad = " + ccMoshtaryGharardad + ") mgk \n" +
                   "    ON mgk.ccKalaCode= km.ccKalaCode AND \n" +
                   "                 (CASE WHEN mgk.ControlMablagh = 1 AND km.mablaghforoshKala = mgk.mablaghforosh AND km.MablaghMasrafKonandehKala = mgk.MablaghMasrafKonandeh \n" +
                   "            THEN 1=1 \n" +
                   "            WHEN    mgk.ControlMablagh = 0 \n" +
                   "            THEN 1=1 \n" +
                   "       END) \n" +
                   " WHERE km.sumTedad > 0 AND mgk.ExtraPropCcSazmanForosh =  "+ MoshtaryGharardadccSazmanForosh +"\n AND IFNULL(zaribforosh,0)<>0 AND mgk.ccMoshtaryGharardad = "+ccMoshtaryGharardad+
                   " ORDER BY o.Olaviat";


           }
       else
           {
           query = " SELECT km.* , \n" +
                   " (SELECT  IFNULL(ZaribForosh,1)ZaribForosh  \n" +
                   "  FROM KalaZaribForosh z \n" +
                   "  WHERE z.ccGorohMoshtary IN (0," + ccNoeMoshtary + ")  AND z.ccKalaCode =km.ccKalaCode AND z.Darajeh IN (0," + ccDarajeh + ") \n" +
                   "  ORDER BY z.Darajeh DESC limit 1) ZaribForosh,\n" +
                   " (SELECT  Darajeh  \n" +
                   "  FROM KalaZaribForosh z \n" +
                   "  WHERE z.ccGorohMoshtary =  347 AND z.ccKalaCode =km.ccKalaCode AND z.Darajeh IN ( 0,3) ORDER BY z.Darajeh DESC limit 1) Darajeh, \n" +
                   " o.Olaviat , Kp.Photo FROM \n" +
                   " (SELECT k.* , m.sumTedad, (m.GheymatForosh * IFNULL(kg.ZaribAfzayeshGheymat,1)) GheymatForosh, m.GheymatKharid, m.ccKalaMojodi, m.sumMax_MojodyByShomarehBach Max_MojodyByShomarehBach \n" +
                   " FROM Kala k \n" +
                   "   LEFT JOIN (SELECT KalaMojodi.* , sum(Tedad) sumTedad, sum(Max_Mojody) sumMax_Mojody, sum(Max_MojodyByShomarehBach) sumMax_MojodyByShomarehBach \n" +
                   "              FROM KalaMojodi \n" +
                   "              WHERE IsAdamForosh = 0 \n" +
                   "              GROUP BY ccKalaCode , ShomarehBach, \n" +
                   "                       GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh,TarikhTolid,TarikhEngheza) m \n" +
                   "   ON k.ccKalaCode = m.ccKalaCode AND k.ccTaminKonandeh = m.ccTaminkonandeh AND \n" +
                   "      k.ShomarehBach = m.ShomarehBach AND k.MablaghMasrafKonandeh = m.GheymatMasrafKonandeh AND k.MablaghForosh = m.GheymatForosh AND \n" +
                   "      k.TarikhTolid = m.TarikhTolid AND k.TarikhEngheza = m.TarikhEngheza \n" +
                   "   LEFT JOIN (SELECT kg.ccKalaCode,(SELECT ZaribAfzayeshGheymat \n" +
                   "                                    FROM KalaGheymatForosh \n" +
                   "                                    WHERE ccKalaCode = kg.ccKalaCode \n" +
                   "                                    ORDER BY ccDarajeh DESC LIMIT 1)  ZaribAfzayeshGheymat \n" +
                   "              FROM KalaGheymatForosh kg \n" +
                   "              WHERE ccMarkazForosh =" + ccMarkazForosh + " AND ccSazmanForosh=" + ccSazmanForosh  + " AND \n" +
                   "                    ccNoeMoshtary IN (0," + ccNoeMoshtary + ") AND ccNoeSenf IN (0," + ccNoeSenf + ") AND ccDarajeh IN (0," + ccDarajeh + ") \n" +
                   "              GROUP BY kg.ccKalaCode \n" +
                   "              ORDER BY kg.ccKalaCode)   kg ON kg.ccKalaCode = k.ccKalaCode \n" +
                   " WHERE k.TedadMojodyGhabelForosh > 0 \n" +
                   " ORDER BY codekala DESC ) km  \n" +
                   " LEFT JOIN KalaOlaviat o ON o.ccKalaCode = km.ccKalaCode \n" +
                   " LEFT JOIN (SELECT * FROM KalaPhoto) kp ON kp.ccKalaCode= km.ccKalaCode \n" +
                   " WHERE km.sumTedad > 0 \n" +
                   " ORDER BY o.Olaviat";
           }




        try
        {
            Log.i("getAllByMoshtary", "KalaMojodiZarib query: "+ query);

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    //TODO
                    kalaMojodiZaribModels = cursorToModel(cursor,type);
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

    public ArrayList<KalaMojodiZaribModel> getByMoshtaryAndccKalaCode(String ccDarajeh , int ccNoeMoshtary , int ccNoeSenf,int ccMarkazForosh, int ccSazmanForosh, String ccKalaCode, int moshtaryGharardadccSazmanForosh, int ccMoshtaryGharardad,int zangireiParam)
    {
        Log.i("getByMoshtaryAndccKala", "KalaMojodiZarib ccMarkazForosh:" + ccMarkazForosh + " ,ccSazmanForosh: "+ccSazmanForosh +" ,ccNoeMoshtary:"+ccNoeMoshtary + " ,ccNoeSenf:"+ ccNoeSenf + " ,ccDarajeh"+ccDarajeh + " ,MoshtaryGharardadccSazmanForosh:" + moshtaryGharardadccSazmanForosh +  " ,ccMoshtaryGharardad:" + ccMoshtaryGharardad );

        String query = null;
        ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels = new ArrayList<>();
        if (ccNoeMoshtary == zangireiParam)
        {

            query = " SELECT km.*,mgk.mablaghforosh GheymatForosh, mgk.GheymatKharid,mgk.MablaghMasrafKonandeh,mgk.ControlMablagh, ZaribForosh , Darajeh, o.Olaviat FROM \n" +
                    "(SELECT k.ccKalaCode, k.CodeKala, k.NameKala, k.ccTaminKonandeh, k.TedadDarKarton, k.TedadDarBasteh, k.Adad, k.MashmolMaliatAvarez, k.ccGorohKala, k.ccBrand, \n" +
                    " k.MablaghKharid, k.Tol, k.Arz, k.Ertefa, k.ccVahedSize, k.VaznKhales, k.VaznNakhales, VaznKarton, k.ccVahedVazn, k.BarCode, k.TarikhTolid, k.TarikhEngheza, \n" +
                    " k.NameVahedVazn, k.NameBrand, k.TedadMojodyGhabelForosh, k.NameVahedSize, k.ccVahedShomaresh,k.NameVahedShomaresh, k.ShomarehBach, k.GheymatForoshAsli,k.GheymatMasrafKonandehAsli , k.MablaghForosh MablaghForoshKala, m.GheymatKharid,  \n" +
                    " k.MablaghMasrafKonandeh MablaghMasrafKonandehKala, m.sumTedad, m.ccKalaMojodi, m.sumMax_MojodyByShomarehBach Max_MojodyByShomarehBach \n" +
                    " FROM Kala k \n" +
                    "              LEFT JOIN (SELECT KalaMojodi.* , SUM(Tedad) sumTedad , sum(Max_Mojody) sumMax_Mojody, sum(Max_MojodyByShomarehBach) sumMax_MojodyByShomarehBach  FROM KalaMojodi where  ccKalaCode = " + ccKalaCode + " AND  IsAdamForosh = 0 group by ccKalaCode , ShomarehBach, \n" +
                    " GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh,TarikhTolid,TarikhEngheza) m \n" +
                    "    ON k.ccKalaCode = m.ccKalaCode AND k.ccTaminKonandeh = m.ccTaminkonandeh AND \n" +
                    "       k.ShomarehBach = m.ShomarehBach AND k.GheymatMasrafKonandehAsli = m.GheymatMasrafKonandeh AND k.GheymatForoshAsli = m.GheymatForosh \n" +
                    " AND k.TarikhTolid = m.TarikhTolid AND k.TarikhEngheza = m.TarikhEngheza \n" +
                    " WHERE k.TedadMojodyGhabelForosh > 0 \n" +
                    " ORDER BY codekala DESC ) km \n" +
                    " LEFT JOIN (SELECT * FROM KalaZaribForosh z  WHERE ccGorohMoshtary =   " + ccNoeMoshtary + " AND z.Darajeh IN ( 0," + ccDarajeh + ")) z  ON km.ccKalaCode = z.ccKalaCode \n" +
                    " LEFT JOIN KalaOlaviat o on o.ccKalaCode = km.ccKalaCode \n" +
                    " LEFT JOIN (SELECT * FROM MoshtaryGharardadKala WHERE ccMoshtaryGharardad = " + ccMoshtaryGharardad + " ) mgk \n" +
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
                    " (select k.* , m.sumTedad, m.GheymatForosh, m.GheymatKharid, m.ccKalaMojodi, m.sumMax_MojodyByShomarehBach Max_MojodyByShomarehBach \n" +
                    " from Kala k left join (select KalaMojodi.* , sum(Tedad) sumTedad , sum(Max_Mojody) sumMax_Mojody, sum(Max_MojodyByShomarehBach) sumMax_MojodyByShomarehBach  from KalaMojodi where ccKalaCode = " + ccKalaCode + " and IsAdamForosh = 0 " +
                    " group by ccKalaCode , ShomarehBach, \n" +
                    " GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh,TarikhTolid,TarikhEngheza) m \n" +
                    " on k.ccKalaCode = m.ccKalaCode and k.ccTaminKonandeh = m.ccTaminkonandeh and \n" +
                    " k.ShomarehBach = m.ShomarehBach and k.MablaghMasrafKonandeh = m.GheymatMasrafKonandeh and k.MablaghForosh = m.GheymatForosh \n" +
                    " AND k.TarikhTolid = m.TarikhTolid AND k.TarikhEngheza = m.TarikhEngheza \n" +
                    " LEFT JOIN (SELECT kg.ccKalaCode,(SELECT ZaribAfzayeshGheymat FROM KalaGheymatForosh WHERE ccKalaCode = kg.ccKalaCode ORDER BY ccDarajeh DESC LIMIT 1)  ZaribAfzayeshGheymat \n" +
                    " FROM KalaGheymatForosh kg \n" +
                    " WHERE ccMarkazForosh =" + ccMarkazForosh + " AND ccSazmanForosh=" + ccSazmanForosh + " \n" +
                    " AND ccNoeMoshtary IN (0," + ccNoeMoshtary + ") AND ccNoeSenf IN (0," + ccNoeSenf + ") AND ccDarajeh IN (0," + ccDarajeh + ") \n" +
                    " GROUP BY kg.ccKalaCode \n" +
                    " ORDER BY kg.ccKalaCode)   kg ON kg.ccKalaCode = k.ccKalaCode \n" +
                    " where k.TedadMojodyGhabelForosh > 0 \n" +
                    " order by codekala desc ) km left join \n" +
                    " (select * from KalaZaribForosh z \n" +
                    " where ccGorohMoshtary = " + ccNoeMoshtary +
                    " and z.Darajeh IN ( 0," + ccDarajeh + " )) z \n" +
                    " on km.ccKalaCode = z.ccKalaCode \n" +
                    " left join KalaOlaviat o on o.ccKalaCode = km.ccKalaCode \n" +
                    " where km.sumTedad > 0 order by km.TarikhTolid";
        }
        try
        {

            Log.d("getByMoshtaryAndccKala","KalaMojodiZarib query: " + query);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    //TODO
                    kalaMojodiZaribModels = cursorToModel(cursor, DarkhastKalaActivity.AddItemType.NONE);
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
//TODO
    private ArrayList<KalaMojodiZaribModel> cursorToModel(Cursor cursor,DarkhastKalaActivity.AddItemType type)
    {
        ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaMojodiZaribModel kalaMojodiZaribModel = new KalaMojodiZaribModel();
            Log.d("check1 curserdao" ,
                    String.valueOf(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccKalaCode())))+","+
                    String.valueOf(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatForosh())))+","+
                    String.valueOf(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatKharid())))+","+
                    String.valueOf(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_MablaghMasrafKonandeh())))+","+
                    String.valueOf(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_GheymatForoshAsli())))+","+
                    String.valueOf(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_GheymatMasrafKonandehAsli()))));
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
            //TODO
            kalaMojodiZaribModel.setGheymatForoshAsli(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_GheymatForoshAsli())));
            kalaMojodiZaribModel.setGheymatMasrafKonandehAsli(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_GheymatMasrafKonandehAsli())));

            // KalaMojodi
            kalaMojodiZaribModel.setCcKalaMojodi(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccKalaMojodi())));
            kalaMojodiZaribModel.setTedad(cursor.getInt(cursor.getColumnIndex("sumTedad")));
            kalaMojodiZaribModel.setMax_MojodyByShomarehBach(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Max_MojodyByShomarehBach())));
            kalaMojodiZaribModel.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatForosh())));
            kalaMojodiZaribModel.setGheymatKharid(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatKharid())));
            kalaMojodiZaribModel.setMablaghMasrafKonandeh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_MablaghMasrafKonandeh())));
            kalaMojodiZaribModel.setZaribForosh(cursor.getInt(cursor.getColumnIndex(KalaZaribForoshModel.COLUMN_ZaribForosh())));
            kalaMojodiZaribModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_ShomarehBach())));
            kalaMojodiZaribModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_TarikhTolid())));
            kalaMojodiZaribModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_TarikhEngheza())));
            kalaMojodiZaribModel.setCcGorohKala(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccGorohKala())));

            // KalaOlaviat
            kalaMojodiZaribModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(KalaOlaviatModel.COLUMN_Olaviat())));
//TODO
            if (type.equals(DarkhastKalaActivity.AddItemType.SHOW_GRID_LIST)) {
                byte[] byteImage = cursor.getBlob(cursor.getColumnIndex(KalaPhotoModel.getColumnImage()));
                if (byteImage != null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                    Bitmap bitmap=getResizedBitmap(bmp,500);
                    kalaMojodiZaribModel.setImageDb(bitmap);
                }
            }



            kalaMojodiZaribModels.add(kalaMojodiZaribModel);
            cursor.moveToNext();
        }
        return kalaMojodiZaribModels;
    }
//TODO
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}
