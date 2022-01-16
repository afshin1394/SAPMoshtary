package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class KalaMojodiDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public KalaMojodiDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaMojodiDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            KalaMojodiModel.COLUMN_ccKalaMojodi(),
            KalaMojodiModel.COLUMN_ccKalaCode(),
            KalaMojodiModel.COLUMN_ccForoshandeh(),
            KalaMojodiModel.COLUMN_Tedad(),
            KalaMojodiModel.COLUMN_ccDarkhastFaktor(),
            KalaMojodiModel.COLUMN_TarikhDarkhast(),
            KalaMojodiModel.COLUMN_ShomarehBach(),
            KalaMojodiModel.COLUMN_TarikhTolid(),
            KalaMojodiModel.COLUMN_TarikhEngheza(),
            KalaMojodiModel.COLUMN_GheymatMasrafKonandeh(),
            KalaMojodiModel.COLUMN_GheymatForosh(),
            KalaMojodiModel.COLUMN_ccTaminKonandeh(),
            KalaMojodiModel.COLUMN_ForJayezeh(),
            KalaMojodiModel.COLUMN_ZamaneSabt(),
            KalaMojodiModel.COLUMN_IsAdamForosh(),
            KalaMojodiModel.COLUMN_Max_Mojody(),
            KalaMojodiModel.COLUMN_Max_MojodyByShomarehBach(),
            KalaMojodiModel.COLUMN_ccAfrad()
        };
    }

    public boolean insertGroup(ArrayList<KalaMojodiModel> kalaMojodiModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaMojodiModel kalaMojodiModel : kalaMojodiModels)
            {
                ContentValues contentValues = modelToContentvalue(kalaMojodiModel);
                db.insertOrThrow(KalaMojodiModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(KalaMojodiModel kalaMojodiModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(kalaMojodiModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(KalaMojodiModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "insert" , "");
            return false;
        }
    }


    public ArrayList<KalaMojodiModel> getAll()
    {
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaMojodiModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getAll" , "");
        }
        return kalaMojodiModels;
    }
	
	
	public int getCountByccKalaCode(int ccKalaCode)
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //select sum(Tedad) from KalaMojodi where ccKalaCode =
            String query = "select sum(" + KalaMojodiModel.COLUMN_Tedad() + ") from " + KalaMojodiModel.TableName() + " where " + KalaMojodiModel.COLUMN_ccKalaCode() + " = " + ccKalaCode;
            Cursor cursor = db.rawQuery(query , null);
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
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getAll" , "");
        }
        return count;
    }


    public int getMaxMojodyByccKalaCode(int ccKalaCode)
    {
        int max = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //select sum(Tedad) from KalaMojodi where ccKalaCode =
//            String query = " SELECT SUM(Tedad) " +
//                           " FROM ( " +
//                           "         SELECT SUM(Tedad) AS Tedad FROM KalaMojodi WHERE ccKalaCode = " + ccKalaCode + " AND Tedad<0 " +
//                           "         UNION ALL " +
//                           "         SELECT MAX(Max_Mojody) AS Tedad FROM KalaMojodi WHERE ccKalaCode = " + ccKalaCode +
//                           " ) ";

            String query = " SELECT SUM(DISTINCT Max_Mojody) FROM KalaMojodi WHERE ccKalaCode = " + ccKalaCode ;

            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    max = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getAll" , "");
        }
        return max;
    }
	
	public ArrayList<KalaMojodiModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaMojodiModel.TableName(), allColumns(), KalaMojodiModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "KalaMojodiDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return kalaMojodiModels;
    }

    public ArrayList<KalaMojodiModel> getByccKalaCode(String ccKalaCode)
    {
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaMojodiModel.TableName(), allColumns(), KalaMojodiModel.COLUMN_ccKalaCode() + " = " + ccKalaCode, null, null, null, KalaMojodiModel.COLUMN_TarikhTolid() + " ASC ");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getAll" , "");
        }
        return kalaMojodiModels;
    }


    public KalaMojodiModel getOneByccKalaCode(String ccKalaCode)
    {
        KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaMojodiModel.TableName(), allColumns(), KalaMojodiModel.COLUMN_ccKalaCode() + " = " + ccKalaCode, null, null, null, KalaMojodiModel.COLUMN_TarikhTolid() + " ASC ", "1");
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getOneByccKalaCode" , "");
        }
        return kalaMojodiModel;
    }

    public KalaMojodiModel getOneByccKalaCode(String ccKalaCode, String shomareBach , int gheymatForosh,int gheymatMasrafKonande,int ccTaminKonande)
    {

        KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + KalaMojodiModel.TableName() + " WHERE ccKalaCode = " + ccKalaCode +  " AND GheymatForosh = " + gheymatForosh
                         + " AND GheymatMasrafKonandeh = " + gheymatMasrafKonande + " AND ccTaminKonandeh = " + ccTaminKonande ;
            if(shomareBach.equals(""))
                    query +=  " AND ShomarehBach = '' " ;
            else
                    query += " AND ShomarehBach = " + shomareBach;
            query +=  " ORDER BY TarikhTolid ASC limit 1 "  ;

            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getOneByccKalaCode" , "");
        }
        return kalaMojodiModel;
    }

    public List<KalaModel> getMaxMojodiKalaByDarkhastFaktor(long ccDarkhastFaktor)
    {
        List<KalaModel> kalaModels = new ArrayList<>();
        try
        {
//            String query = "select dfs.ccKalaCode, k.NameKala, k.CodeKala, (select Max_Mojody from KalaMojodi km \n" +
//                    " where km.ccKalaCode = dfs.ccKalaCode) As TedadMojodi \n" +
//                    " from DarkhastFaktorSatr dfs inner join Kala k on dfs.ccKalaCode = k.ccKalaCode\n" +
//                    " where ccDarkhastFaktor = " + ccDarkhastFaktor + " and TedadMojodi - Tedad3 < 0 group by dfs.ccKalaCode";
            String query = " SELECT dfs.ccKalaCode, k.CodeKala, k.NameKala, (select Max_Mojody from KalaMojodi km \n" +
                    " where km.ccKalaCode = dfs.ccKalaCode) As TedadMojodi \n" +
                    " from DarkhastFaktorSatr dfs \n" +
                    " LEFT JOIN Kala k ON k.cckalacode=dfs.cckalacode AND k.ShomarehBach = dfs.ShomarehBach AND k.ccTaminKonandeh = dfs.ccTaminKonandeh AND k.MablaghForosh = dfs.GheymatForoshAsli AND k.MablaghMasrafKonandeh = dfs.GheymatMasrafKonandehAsli \n"+
                    " AND k.TarikhTolid = dfs.TarikhTolid AND k.TarikhEngheza = dfs.TarikhEngheza" +
                    " where ccDarkhastFaktor = " + ccDarkhastFaktor +   " \n " +
                    " group by dfs.ccKalaCode \n" +
                    " having  TedadMojodi < SUM(Tedad3)";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaModels = cursorToKalaModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + e.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getMaxMojodiKalaByDarkhastFaktor" , "");
        }
        return kalaModels;
    }

    /**
     * دریافت لیست کالاهای موجود در انبارک برای ثبت جایزه اتوماتیک در فرم تایید درخواست
     * @param ccKalaCode
     * @return
     */
    public ArrayList<KalaMojodiModel> getForAutoBonus(int ccKalaCode)
    {
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
        try
        {
            String query = "select ccKalaMojodi,ccKalaCode,ccForoshandeh,sum(Tedad) As Tedad,ccDarkhastFaktor,TarikhDarkhast,ShomarehBach,TarikhTolid,TarikhEngheza, " +
                    " GheymatMasrafKonandeh,GheymatForosh,ccTaminKonandeh,ForJayezeh,ZamaneSabt, IsAdamForosh, Max_Mojody, Max_MojodyByShomarehBach \n" +
                    " from KalaMojodi where ccKalaCode = " + ccKalaCode +
                    " group by ccKalaCode,ShomarehBach,GheymatMasrafKonandeh,GheymatForosh,ccTaminKonandeh,TarikhTolid,TarikhEngheza having sum(Tedad) > 0";
            Log.d("bonus" , "kalaMojodiModels Query : " + query);

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getForAutoBonus" , "");
        }
        return kalaMojodiModels;
    }

    public String getKalaAdamForosh()
    {
        String goodsName = "";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select distinct(k.NameKala), k.CodeKala from KalaMojodi km inner join Kala k on km.ccKalaCode = k.ccKalaCode where km.IsAdamForosh = 1";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        goodsName += "\n" + cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_CodeKala())) + " - " + cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameKala()));
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
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getKalaAdamForosh" , "");
        }
        return goodsName;
    }


    public ArrayList<KalaMojodiModel> getMaxShomarehBach(int ccJayezeh, int ccJayezehSatr){
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();

        try
        {

            //String query = "  select  Sum(distinct m.Max_MojodyByShomarehBach)Max_MojodyByShomarehBach , m.ccKalaCode , m.ShomarehBach , m.GheymatForosh ,m.GheymatMasrafKonandeh , m.ccTaminKonandeh  from\n" +
            String query = "  select  Sum(m.Max_MojodyByShomarehBach)Max_MojodyByShomarehBach , m.ccKalaCode , m.ShomarehBach , m.GheymatForosh ,m.GheymatMasrafKonandeh , m.ccTaminKonandeh, m.TarikhTolid, m.TarikhEngheza  from\n" +
                    "                     JayezehEntekhabi j inner join KalaMojodi m \n" +
                    "                     on j.ccKalaCode = m.ccKalaCode \n" +
                    "                     and j.ccJayezeh = " + ccJayezeh + " \n" +
                    "                     and j.ccJayezehSatr = " + ccJayezehSatr + " \n" +
                    "                     group by m.ccKalaCode , m.ShomarehBach, m.ccTaminKonandeh, m.GheymatForosh, m.GheymatMasrafKonandeh, m.TarikhTolid, m.TarikhEngheza \n" +
                    "                     having sum(m.Tedad) > 0 ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            Log.d("bonus","getMaxMojodi query: " + query);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast())
                    {
                        KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();

                        kalaMojodiModel.setMax_MojodyByShomarehBach(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Max_MojodyByShomarehBach())));
                        kalaMojodiModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccKalaCode())));
                        kalaMojodiModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ShomarehBach())));
                        kalaMojodiModel.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatForosh())));
                        kalaMojodiModel.setGheymatMasrafKonandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatMasrafKonandeh())));
                        kalaMojodiModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccTaminKonandeh())));
                        kalaMojodiModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhTolid())));
                        kalaMojodiModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhEngheza())));
                        kalaMojodiModels.add(kalaMojodiModel);
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
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getMaxShomarehBach" , "");
        }

        return kalaMojodiModels;
    }

    public ArrayList<KalaMojodiModel> getMaxMojodi(int ccJayezeh, int ccJayezehSatr){
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();

        try
        {

            //String query = "  select  sum(distinct m.Max_Mojody) Max_Mojody, m.ccKalaCode , m.ShomarehBach , m.GheymatForosh ,m.GheymatMasrafKonandeh , m.ccTaminKonandeh  from\n" +
            String query = "  select  sum(m.Max_Mojody) Max_Mojody, m.ccKalaCode , m.ShomarehBach , m.GheymatForosh ,m.GheymatMasrafKonandeh , m.ccTaminKonandeh, m.TarikhTolid, m.TarikhEngheza  from\n" +
                    "                     JayezehEntekhabi j inner join KalaMojodi m \n" +
                    "                     on j.ccKalaCode = m.ccKalaCode \n" +
                    "                     and j.ccJayezeh = " + ccJayezeh + " \n" +
                    "                     and j.ccJayezehSatr = " + ccJayezehSatr + " \n" +
                    "                     group by m.ccKalaCode , m.ShomarehBach, m.ccTaminKonandeh, m.GheymatForosh, m.GheymatMasrafKonandeh, m.TarikhTolid, m.TarikhEngheza \n" +
                    "                     having sum(m.Tedad) > 0 ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            Log.d("bonus","getMaxMojodi query: " + query);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast())
                    {
                        KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();

                        kalaMojodiModel.setMax_Mojody(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Max_Mojody())));
                        kalaMojodiModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccKalaCode())));
                        kalaMojodiModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ShomarehBach())));
                        kalaMojodiModel.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatForosh())));
                        kalaMojodiModel.setGheymatMasrafKonandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatMasrafKonandeh())));
                        kalaMojodiModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccTaminKonandeh())));
                        kalaMojodiModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhTolid())));
                        kalaMojodiModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhEngheza())));
                        kalaMojodiModels.add(kalaMojodiModel);
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
            String message = context.getResources().getString(R.string.errorSelectAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "getMaxShomarehBach" , "");
        }

        return kalaMojodiModels;
    }
    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaMojodiModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "deleteAll" , "");
            return false;
        }
    }


    public boolean deleteJayzeheByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaMojodiModel.TableName(), KalaMojodiModel.COLUMN_ccDarkhastFaktor() + " = " + ccDarkhastFaktor + " and " + KalaMojodiModel.COLUMN_ForJayezeh() + " = 1", null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean updateMojodiForReturnJayezeh(long ccDarkhastFaktor)
    {
        String query = "insert into kalaMojodi(ccKalaCode,ccForoshandeh,Tedad,ccDarkhastFaktor,TarikhDarkhast,ShomarehBach, \n" +
                " TarikhTolid, TarikhEngheza,GheymatMasrafKonandeh,GheymatForosh,ccTaminKonandeh,ForJayezeh,Max_Mojody,Max_MojodyByShomarehBach) \n" +
                " select ccKalaCode,ccForoshandeh,Tedad*-1,ccDarkhastFaktor,TarikhDarkhast,ShomarehBach, \n" +
                " TarikhTolid, TarikhEngheza,GheymatMasrafKonandeh,GheymatForosh,ccTaminKonandeh,ForJayezeh,Tedad*-1,Tedad*-1 \n" +
                " from kalaMojodi where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ForJayezeh = 1";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            Log.d("bouns", "updateMojodiForReturnJayezeh query:" + query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiDAO" , "" , "updateMojodiForReturnJayezeh" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KalaMojodiModel kalaMojodiModel)
    {
        ContentValues contentValues = new ContentValues();

        if (kalaMojodiModel.getCcKalaMojodi() > 0)
        {
            contentValues.put(KalaMojodiModel.COLUMN_ccKalaMojodi() , kalaMojodiModel.getCcKalaMojodi());
        }
        contentValues.put(KalaMojodiModel.COLUMN_ccKalaCode() , kalaMojodiModel.getCcKalaCode());
        contentValues.put(KalaMojodiModel.COLUMN_ccForoshandeh() , kalaMojodiModel.getCcForoshandeh());
        contentValues.put(KalaMojodiModel.COLUMN_Tedad() , kalaMojodiModel.getTedad());
        contentValues.put(KalaMojodiModel.COLUMN_ccDarkhastFaktor() , kalaMojodiModel.getCcDarkhastFaktor());
        contentValues.put(KalaMojodiModel.COLUMN_TarikhDarkhast() , kalaMojodiModel.getTarikhDarkhast());
        contentValues.put(KalaMojodiModel.COLUMN_ShomarehBach() , kalaMojodiModel.getShomarehBach());
        contentValues.put(KalaMojodiModel.COLUMN_TarikhTolid() , kalaMojodiModel.getTarikhTolid());
        contentValues.put(KalaMojodiModel.COLUMN_TarikhEngheza() , kalaMojodiModel.getTarikhEngheza());
        contentValues.put(KalaMojodiModel.COLUMN_GheymatMasrafKonandeh() , kalaMojodiModel.getGheymatMasrafKonandeh());
        contentValues.put(KalaMojodiModel.COLUMN_GheymatForosh() , kalaMojodiModel.getGheymatForosh());
        contentValues.put(KalaMojodiModel.COLUMN_ccTaminKonandeh() , kalaMojodiModel.getCcTaminKonandeh());
        contentValues.put(KalaMojodiModel.COLUMN_ForJayezeh() , kalaMojodiModel.getForJayezeh());
        contentValues.put(KalaMojodiModel.COLUMN_IsAdamForosh() , kalaMojodiModel.getIsAdamForosh());
        contentValues.put(KalaMojodiModel.COLUMN_Max_Mojody() , kalaMojodiModel.getMax_Mojody());
        contentValues.put(KalaMojodiModel.COLUMN_Max_MojodyByShomarehBach() , kalaMojodiModel.getMax_MojodyByShomarehBach());
        contentValues.put(KalaMojodiModel.COLUMN_ccAfrad() , kalaMojodiModel.getCcAfrad());
        if (kalaMojodiModel.getZamaneSabt() != null && kalaMojodiModel.getZamaneSabt().trim().length() > 0)
        {
            try
            {
                contentValues.put(KalaMojodiModel.COLUMN_ZamaneSabt() , kalaMojodiModel.getZamaneSabt());
            }
            catch (Exception e)
            {e.printStackTrace();}
        }

        return contentValues;
    }


    private ArrayList<KalaMojodiModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();

            kalaMojodiModel.setCcKalaMojodi(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccKalaMojodi())));
            kalaMojodiModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccKalaCode())));
            kalaMojodiModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccForoshandeh())));
            kalaMojodiModel.setTedad(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Tedad())));
            kalaMojodiModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccDarkhastFaktor())));
            kalaMojodiModel.setTarikhDarkhast(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhDarkhast())));
            kalaMojodiModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ShomarehBach())));
            kalaMojodiModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhTolid())));
            kalaMojodiModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_TarikhEngheza())));
            kalaMojodiModel.setGheymatMasrafKonandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatMasrafKonandeh())));
            kalaMojodiModel.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_GheymatForosh())));
            kalaMojodiModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccTaminKonandeh())));
            kalaMojodiModel.setForJayezeh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ForJayezeh())));
            kalaMojodiModel.setZamaneSabt(cursor.getString(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ZamaneSabt())));
            kalaMojodiModel.setIsAdamForosh(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_IsAdamForosh())));
            kalaMojodiModel.setMax_Mojody(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Max_Mojody())));
            kalaMojodiModel.setMax_MojodyByShomarehBach(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_Max_MojodyByShomarehBach())));
            kalaMojodiModel.setCcAfrad(cursor.getInt(cursor.getColumnIndex(KalaMojodiModel.COLUMN_ccAfrad())));

            kalaMojodiModels.add(kalaMojodiModel);
            cursor.moveToNext();
        }
        return kalaMojodiModels;
    }


    private List<KalaModel> cursorToKalaModel(Cursor cursor)
    {
        List<KalaModel> kalaModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaModel kalaModel = new KalaModel();

            kalaModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccKalaCode())));
            kalaModel.setCodeKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_CodeKala())));
            kalaModel.setNameKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameKala())));
            kalaModel.setTedadMojodyGhabelForosh(cursor.getInt(cursor.getColumnIndex("TedadMojodi")));

            kalaModels.add(kalaModel);
            cursor.moveToNext();
        }
        return kalaModels;
    }

}
