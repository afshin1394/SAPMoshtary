package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.JayezehEntekhabiModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.JayezehEntekhabiMojodiModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class JayezehEntekhabiMojodiDAO
{

    private DBHelper dbHelper;
    private Context context;
    

    public JayezehEntekhabiMojodiDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "JayezehEntekhabiMojodiDAO" , "" , "constructor" , "");
        }
    }


    public ArrayList<JayezehEntekhabiMojodiModel> getByccTakhfifHajmi(int ccTakhfifHajmi)
    {
        ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            /*String query = "select * , sum(m.Tedad) sumTedad from JayezehEntekhabi j inner join KalaMojodi m \n" +
                    " on j.ccKalaCode = m.ccKalaCode and j.ccTakhfifHajmi = " + ccTakhfifHajmi +
                    " group by  m.ccKalaCode, m.ShomarehBach, m.ccTaminKonandeh, m.GheymatForosh, m.GheymatMasrafKonandeh having sum(m.Tedad) > 0";*/
            String query = "select j.*, km.ccKalaMojodi, km.ccForoshandeh, km.sumTedad AS sumTedad, km.ccDarkhastFaktor, km.TarikhDarkhast, km.ShomarehBach, \n" +
                    " km.TarikhTolid, km.ZamaneSabt, km.TarikhEngheza, km.GheymatMasrafKonandeh, km.GheymatForosh, km.ccTaminKonandeh, km.Max_Mojody, km.Max_MojodyByShomarehBach \n" +
                    " from JayezehEntekhabi j inner join \n" +
                    " (select k.* , m.sumTedad, m.GheymatForosh, m.ZamaneSabt, m.ccKalaMojodi, m.ccForoshandeh, m.ccDarkhastFaktor, m.TarikhDarkhast, m.GheymatMasrafKonandeh, m.Max_Mojody, m.Max_MojodyByShomarehBach \n" +
                    " from Kala k left join (select KalaMojodi.* , sum(Tedad) sumTedad from KalaMojodi where IsAdamForosh = 0 \n" +
                    " group by ccKalaCode , ShomarehBach, GheymatForosh,GheymatMasrafKonandeh,ccTaminKonandeh, TarikhTolid, TarikhEngheza) m \n" +
                    " on k.ccKalaCode = m.ccKalaCode and k.ccTaminKonandeh = m.ccTaminkonandeh and \n" +
                    " k.ShomarehBach = m.ShomarehBach and k.MablaghMasrafKonandeh = m.GheymatMasrafKonandeh and k.MablaghForosh = m.GheymatForosh and\n" +
                    " k.TarikhTolid = m.TarikhTolid and k.TarikhEngheza = m.TarikhEngheza  \n" +
                    " where k.TedadMojodyGhabelForosh > 0 and m.sumTedad > 0  \n" +
                    " order by codekala desc) km \n" +
                    " on j.ccKalaCode = km.ccKalaCode and km.sumTedad > 0 and j.ccTakhfifHajmi = " + ccTakhfifHajmi +
                    " group by km.ccKalaCode, km.ShomarehBach, km.ccTaminKonandeh, km.GheymatForosh, km.GheymatMasrafKonandeh, km.TarikhTolid, km.TarikhEngheza";
            Log.d("jayezeh" , "query : " + query);
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehEntekhabiMojodiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "JayezehEntekhabi , KalaMojodi") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehEntekhabiMojodiDAO" , "" , "getByccTakhfifHajmi" , "");
        }
        return jayezehEntekhabiMojodiModels;
    }


    public ArrayList<JayezehEntekhabiMojodiModel> getByccJayezeh(int ccJayezeh, int ccJayezehSatr)
    {
        ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * , sum(m.Tedad) sumTedad from JayezehEntekhabi j inner join KalaMojodi m \n" +
                    " on j.ccKalaCode = m.ccKalaCode and j.ccJayezeh = " + ccJayezeh + " and j.ccJayezehSatr = " + ccJayezehSatr +
                    " group by  m.ccKalaCode, m.ShomarehBach, m.ccTaminKonandeh, m.GheymatForosh, m.GheymatMasrafKonandeh, m.TarikhTolid, m.TarikhEngheza having sum(m.Tedad) > 0";
            Log.d("jayezeh" , "query : " + query);
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    jayezehEntekhabiMojodiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "JayezehEntekhabi , KalaMojodi") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "JayezehEntekhabiMojodiDAO" , "" , "getByccTakhfifHajmi" , "");
        }
        return jayezehEntekhabiMojodiModels;
    }

    private ArrayList<JayezehEntekhabiMojodiModel> cursorToModel(Cursor cursor)
    {
        ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel = new JayezehEntekhabiMojodiModel();

            //JayezehEntekhabiModel
            jayezehEntekhabiMojodiModel.setCcJayezeh(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccJayezeh())));
            jayezehEntekhabiMojodiModel.setCcJayezehSatr(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccJayezehSatr())));
            jayezehEntekhabiMojodiModel.setCcJayezehSatrKala(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccJayezehSatrKala())));
            jayezehEntekhabiMojodiModel.setCcKala(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccKala())));
            jayezehEntekhabiMojodiModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccKalaCode())));
            jayezehEntekhabiMojodiModel.setCodeKalaOld(cursor.getString(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_CodeKalaOld())));
            jayezehEntekhabiMojodiModel.setNameKala(cursor.getString(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_NameKala())));
            jayezehEntekhabiMojodiModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccMarkazForosh())));
            jayezehEntekhabiMojodiModel.setMablaghForosh(cursor.getFloat(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_MablaghForosh())));
            jayezehEntekhabiMojodiModel.setExtraProp_Tedad(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ExtraProp_Tedad())));
            jayezehEntekhabiMojodiModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccNoeMoshtary())));
            jayezehEntekhabiMojodiModel.setCodeNoe(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_CodeNoe())));
            jayezehEntekhabiMojodiModel.setCcTakhfifHajmi(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccTakhfifHajmi())));
            jayezehEntekhabiMojodiModel.setCcKalaCodeAsli(cursor.getInt(cursor.getColumnIndex(JayezehEntekhabiModel.COLUMN_ccKalaCodeAsli())));

            //KalaMojodiModel
            jayezehEntekhabiMojodiModel.setCcKalaMojodi(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccKalaMojodi())));
            jayezehEntekhabiMojodiModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccForoshandeh())));
            jayezehEntekhabiMojodiModel.setTedad(cursor.getInt(cursor.getColumnIndex("sumTedad")));
            jayezehEntekhabiMojodiModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccDarkhastFaktor())));
            jayezehEntekhabiMojodiModel.setTarikhDarkhast(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhDarkhast())));
            jayezehEntekhabiMojodiModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ShomarehBach())));
            jayezehEntekhabiMojodiModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhTolid())));
            jayezehEntekhabiMojodiModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhEngheza())));
            jayezehEntekhabiMojodiModel.setZamaneSabt(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ZamaneSabt())));
            jayezehEntekhabiMojodiModel.setGheymatMasrafKonandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatMasrafKonandeh())));
            jayezehEntekhabiMojodiModel.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatForosh())));
            jayezehEntekhabiMojodiModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccTaminKonandeh())));
            jayezehEntekhabiMojodiModel.setMax_Mojody(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Max_Mojody())));
            jayezehEntekhabiMojodiModel.setMax_MojodyByShomarehBach(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Max_MojodyByShomarehBach())));

            jayezehEntekhabiMojodiModels.add(jayezehEntekhabiMojodiModel);
            cursor.moveToNext();
        }
        return jayezehEntekhabiMojodiModels;
    }


}
