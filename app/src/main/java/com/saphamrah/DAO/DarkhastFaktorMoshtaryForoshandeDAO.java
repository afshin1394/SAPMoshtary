package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class DarkhastFaktorMoshtaryForoshandeDAO {

    private DBHelper dbHelper;
    private Context context;
    private final String CLASS_NAME = "DarkhastFaktorMoshtaryForoshandeDAO";

    public DarkhastFaktorMoshtaryForoshandeDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "constructor", "");
        }
    }


    public ArrayList<DarkhastFaktorMoshtaryForoshandeModel> getAll(int faktorRooz) {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
        try {
//            String query = "select df.ccDarkhastFaktor, df.ccDarkhastFaktorNoeForosh, df.ccMarkazForosh, df.ccSazmanForosh, df.ccMarkazSazmanForosh, df.TarikhFaktor, df.ShomarehDarkhast, df.ShomarehFaktor, df.ccForoshandeh, df.FaktorRooz, 1 AS CountDarkhastFaktor, \n" +
//                    " df.ccMoshtary, df.ccUser, df.NameNoeVosolAzMoshtary, \n" +
//                    " df.MablaghKhalesFaktor, df.MablaghMandeh , df.CodeVazeiat, df.Latitude, df.Longitude, \n" +
//                    " df.ExtraProp_IsSend, df.CodeNoeVosolAzMoshtary, df.ExtraProp_ShowFaktorMamorPakhsh, \n" +
//                    " f.FullNameForoshandeh, f.ccAfradForoshandeh , \n" +
//                    " m.NameMoshtary, m.CodeMoshtary \n" +
//                    " from DarkhastFaktor df left join Moshtary m on df.ccMoshtary = m.ccMoshtary \n" +
//                    " left join Foroshandeh f on df.ccForoshandeh = f.ccForoshandeh \n" +
//                    " where ForTasviehVosol = 1 AND FaktorRooz = " + faktorRooz + " order by df.TarikhFaktor DESC";
//
            String query = "select df.ccDarkhastFaktor, df.ccDarkhastFaktorNoeForosh, df.ccMarkazForosh, df.ccSazmanForosh, df.ccMarkazSazmanForosh, df.TarikhFaktor , df.TarikhErsal, df.ShomarehDarkhast, df.ShomarehFaktor, df.ccForoshandeh, df.FaktorRooz, 1 AS CountDarkhastFaktor, \n" +
                    " df.ccMoshtary, df.ccUser, df.NameNoeVosolAzMoshtary, \n" +
                    " df.MablaghKhalesFaktor, df.MablaghMandeh , df.CodeVazeiat, df.Latitude, df.Longitude, \n" +
                    " df.ExtraProp_IsSend, df.CodeNoeVosolAzMoshtary,df.ExtraProp_IsMarjoeeKamel,df.ExtraProp_Resid, df.ExtraProp_ShowFaktorMamorPakhsh , df.ExtraProp_MablaghDariaftPardakht, \n" +
                    " df.ccMoshtaryGharardad, df.MoshtaryGharardadccSazmanForosh, \n" +
                    " f.FullNameForoshandeh, f.ccAfradForoshandeh , df.ExtraProp_MablaghDariaftPardakht , \n" +
                    " m.NameMoshtary, m.CodeMoshtary \n" +
                    " from DarkhastFaktor df left join Moshtary m on df.ccMoshtary = m.ccMoshtary \n" +
                    " left join Foroshandeh f on df.ccForoshandeh = f.ccForoshandeh \n" +
                    " where ForTasviehVosol = 1 AND FaktorRooz = " + faktorRooz + " order by df.TarikhFaktor DESC";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    darkhastFaktorMoshtaryForoshandeModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, "DarkhastFaktor,Moshtary,Foroshandeh") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, "", "getAll", "");
        }
        return darkhastFaktorMoshtaryForoshandeModels;
    }

    public ArrayList<DarkhastFaktorMoshtaryForoshandeModel> getAllOrderByRoutingSort(int faktorRooz) {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
        try {
            String query = "select df.ccDarkhastFaktor, df.ccDarkhastFaktorNoeForosh, df.ccMarkazForosh, df.ccSazmanForosh, df.ccMarkazSazmanForosh, df.TarikhFaktor , df.TarikhErsal, df.ShomarehDarkhast, df.ShomarehFaktor, df.ccForoshandeh, df.FaktorRooz, 1 AS CountDarkhastFaktor, \n" +
                    " df.ccMoshtary, df.ccUser, df.NameNoeVosolAzMoshtary, \n" +
                    " df.MablaghKhalesFaktor, df.MablaghMandeh , df.CodeVazeiat, df.Latitude, df.Longitude, \n" +
                    " df.ExtraProp_IsSend, df.CodeNoeVosolAzMoshtary,df.ExtraProp_IsMarjoeeKamel,df.ExtraProp_Resid, df.ExtraProp_ShowFaktorMamorPakhsh , df.ExtraProp_MablaghDariaftPardakht, \n" +
                    " df.ccMoshtaryGharardad, df.MoshtaryGharardadccSazmanForosh, \n" +
                    " f.FullNameForoshandeh, f.ccAfradForoshandeh , \n" +
                    " m.NameMoshtary, m.CodeMoshtary \n" +
                    " from DarkhastFaktor df left join Moshtary m on df.ccMoshtary = m.ccMoshtary \n" +
                    " left join Foroshandeh f on df.ccForoshandeh = f.ccForoshandeh \n" +
                    " left join DarkhastFaktorRoozSort dfs \n" +
                    " on df.ccDarkhastFaktor = dfs.ccDarkhastFaktor \n" +
                    " where ForTasviehVosol = 1 AND FaktorRooz = " + faktorRooz +
                    " order by dfs.Sort";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    darkhastFaktorMoshtaryForoshandeModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, "DarkhastFaktor,Moshtary,Foroshandeh") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, "", "getAll", "");
        }
        return darkhastFaktorMoshtaryForoshandeModels;
    }


    public ArrayList<DarkhastFaktorMoshtaryForoshandeModel> getDistinctCustomers(int faktorRooz) {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
        try {
            String query = "select df.ccMoshtary, df.ccMarkazForosh, df.ccSazmanForosh, df.ccMarkazSazmanForosh, df.ccDarkhastFaktorNoeForosh, m.CodeMoshtary, m.NameMoshtary, f.FullNameForoshandeh, df.ccUser, df.Latitude, df.Longitude, \n" +
                    " f.ccAfradForoshandeh, count(df.ccDarkhastFaktor) AS CountDarkhastFaktor \n" +
                    " from DarkhastFaktor df left join Moshtary m on df.ccMoshtary = m.ccMoshtary \n" +
                    " left join Foroshandeh f on df.ccForoshandeh = f.ccForoshandeh \n" +
                    " where ForTasviehVosol = 1 AND FaktorRooz = " + faktorRooz +
                    " group by df.ccMoshtary order by df.CodeMoshtary";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    darkhastFaktorMoshtaryForoshandeModels = cursorToModelCustomersInfo(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, "DarkhastFaktor,Moshtary,Foroshandeh") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, "", "getDistinctCustomers", "");
        }
        return darkhastFaktorMoshtaryForoshandeModels;
    }


    public ArrayList<DarkhastFaktorMoshtaryForoshandeModel> getCustomerDarkhastFaktor(int ccMoshtary, int faktorRooz) {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
        try {
            String query = "select df.ccDarkhastFaktor, df.ccMarkazForosh, df.ccSazmanForosh, df.ccMarkazSazmanForosh, df.ccDarkhastFaktorNoeForosh, df.TarikhFaktor, df.ShomarehDarkhast, df.ShomarehFaktor, df.ccForoshandeh, df.FaktorRooz, \n" +
                    " df.ccMoshtary, df.ccUser, df.NameNoeVosolAzMoshtary, \n" +
                    " df.MablaghKhalesFaktor, df.MablaghMandeh , df.CodeVazeiat, df.Latitude, df.Longitude, \n" +
                    " df.ExtraProp_IsSend, df.CodeNoeVosolAzMoshtary, df.ExtraProp_ShowFaktorMamorPakhsh,df.ccMoshtaryGharardad, \n" +
                    " m.NameMoshtary, m.CodeMoshtary,m.ccMoshtaryGharardad \n" +
                    " from DarkhastFaktor df left join Moshtary m on df.ccMoshtary = m.ccMoshtary  \n" +
                    " where df.ccMoshtary = " + ccMoshtary + " And ForTasviehVosol = 1 AND FaktorRooz = " + faktorRooz +
                    " order by df.CodeMoshtary";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorMoshtaryForoshandeModels = cursorToModelFaktorInfo(cursor);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, "DarkhastFaktor,Moshtary,Foroshandeh") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, "", "getCustomerDarkhastFaktor", "");
        }
        return darkhastFaktorMoshtaryForoshandeModels;
    }

    public int getCountCanEditDarkhastForMamorPakhshSard(int ccMoshtary, int faktorRooz, int codeVazeiat) {
        int count = 0;
        try {
            String query = "select count(ccDarkhastFaktor) from DarkhastFaktor " +
                    " where ForTasviehVosol = 1 AND FaktorRooz = " + faktorRooz + " AND ccMoshtary = " + ccMoshtary + " AND CodeVazeiat = " + codeVazeiat;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToNext();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, "DarkhastFaktor,Moshtary,Foroshandeh") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, "", "getCountCanEditDarkhastForMamorPakhshSard", "");
        }
        return count;
    }


    public int getCountCanEditDarkhastForMamorPakhshSmart(int ccMoshtary, int faktorRooz, int extrapropIsSend, int codeVazeiat) {
        int count = 0;
        try {
            String query = "select count(ccDarkhastFaktor) from DarkhastFaktor " +
                    " where ForTasviehVosol = 1 AND FaktorRooz = " + faktorRooz + " AND ccMoshtary = " + ccMoshtary + " AND CodeVazeiat < " + codeVazeiat + " AND ExtraProp_IsSend = " + extrapropIsSend;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToNext();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll, "DarkhastFaktor,Moshtary,Foroshandeh") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, "", "getCountCanEditDarkhastForMamorPakhshSmart", "");
        }
        return count;
    }

    private ArrayList<DarkhastFaktorMoshtaryForoshandeModel> cursorToModel(Cursor cursor) {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();

        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel = new DarkhastFaktorMoshtaryForoshandeModel();

                darkhastFaktorMoshtaryForoshandeModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccDarkhastFaktor())));
                darkhastFaktorMoshtaryForoshandeModel.setCcDarkhastFaktorNoeForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccDarkhastFaktorNoeForosh())));
                darkhastFaktorMoshtaryForoshandeModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_TarikhFaktor())));
                darkhastFaktorMoshtaryForoshandeModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_TarikhErsal())));
                darkhastFaktorMoshtaryForoshandeModel.setShomarehDarkhast(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ShomarehDarkhast())));
                darkhastFaktorMoshtaryForoshandeModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ShomarehFaktor())));
                darkhastFaktorMoshtaryForoshandeModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccForoshandeh())));
                darkhastFaktorMoshtaryForoshandeModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMoshtary())));
                darkhastFaktorMoshtaryForoshandeModel.setCcUser(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccUser())));
                darkhastFaktorMoshtaryForoshandeModel.setFaktorRooz(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_FaktorRooz())));
                darkhastFaktorMoshtaryForoshandeModel.setNameNoeVosolAzMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_NameNoeVosolAzMoshtary())));
                darkhastFaktorMoshtaryForoshandeModel.setMablaghKhalesFaktor(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_MablaghKhalesFaktor())));
                darkhastFaktorMoshtaryForoshandeModel.setMablaghMandeh(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_MablaghMandeh())));
                darkhastFaktorMoshtaryForoshandeModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CodeVazeiat())));
                darkhastFaktorMoshtaryForoshandeModel.setLatitude(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_Latitude())));
                darkhastFaktorMoshtaryForoshandeModel.setLongitude(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_Longitude())));
                darkhastFaktorMoshtaryForoshandeModel.setCountDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CountDarkhastFaktor())));
                darkhastFaktorMoshtaryForoshandeModel.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ExtraProp_IsSend())));
                darkhastFaktorMoshtaryForoshandeModel.setFullNameForoshande(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_FullNameForoshandeh())));
                darkhastFaktorMoshtaryForoshandeModel.setCcAfradForoshandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccAfradForoshandeh())));
                darkhastFaktorMoshtaryForoshandeModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMarkazForosh())));
                darkhastFaktorMoshtaryForoshandeModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccSazmanForosh())));
                darkhastFaktorMoshtaryForoshandeModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMarkazSazmanForosh())));
                darkhastFaktorMoshtaryForoshandeModel.setFullNameMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_NameMoshtary())));
                darkhastFaktorMoshtaryForoshandeModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CodeMoshtary())));
                darkhastFaktorMoshtaryForoshandeModel.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CodeNoeVosolAzMoshtary())));
                darkhastFaktorMoshtaryForoshandeModel.setExtraProp_ShowFaktorMamorPakhsh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ExtraProp_ShowFaktorMamorPakhsh())));
                darkhastFaktorMoshtaryForoshandeModel.setExtraProp_IsMarjoeeKamel(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ExtraProp_IsMarjoeeKamel())));
                darkhastFaktorMoshtaryForoshandeModel.setExtraProp_Resid(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ExtraProp_Resid())));
                darkhastFaktorMoshtaryForoshandeModel.setExtraProp_MablaghDariaftPardakht(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ExtraProp_MablaghDariaftPardakht())));
                darkhastFaktorMoshtaryForoshandeModel.setCcMoshtaryGharardad(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMoshtaryGharardad())));
                darkhastFaktorMoshtaryForoshandeModel.setMoshtaryGharardadccSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_MoshtaryGharardadccSazmanForosh())));
                darkhastFaktorMoshtaryForoshandeModels.add(darkhastFaktorMoshtaryForoshandeModel);
                cursor.moveToNext();
            }
        }catch (Exception exception){
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), CLASS_NAME, "", "cursorToModel", "cursorToModel");
        }
            return darkhastFaktorMoshtaryForoshandeModels;
    }

    private ArrayList<DarkhastFaktorMoshtaryForoshandeModel> cursorToModelFaktorInfo(Cursor cursor) {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel = new DarkhastFaktorMoshtaryForoshandeModel();

            darkhastFaktorMoshtaryForoshandeModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorMoshtaryForoshandeModel.setCcDarkhastFaktorNoeForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccDarkhastFaktorNoeForosh())));
            darkhastFaktorMoshtaryForoshandeModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_TarikhFaktor())));
            darkhastFaktorMoshtaryForoshandeModel.setShomarehDarkhast(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ShomarehDarkhast())));
            darkhastFaktorMoshtaryForoshandeModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ShomarehFaktor())));
            darkhastFaktorMoshtaryForoshandeModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccForoshandeh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMoshtary())));
            darkhastFaktorMoshtaryForoshandeModel.setCcUser(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccUser())));
            darkhastFaktorMoshtaryForoshandeModel.setFaktorRooz(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_FaktorRooz())));
            darkhastFaktorMoshtaryForoshandeModel.setNameNoeVosolAzMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_NameNoeVosolAzMoshtary())));
            darkhastFaktorMoshtaryForoshandeModel.setMablaghKhalesFaktor(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_MablaghKhalesFaktor())));
            darkhastFaktorMoshtaryForoshandeModel.setMablaghMandeh(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_MablaghMandeh())));
            darkhastFaktorMoshtaryForoshandeModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CodeVazeiat())));
            darkhastFaktorMoshtaryForoshandeModel.setLatitude(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_Latitude())));
            darkhastFaktorMoshtaryForoshandeModel.setLongitude(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_Longitude())));
            darkhastFaktorMoshtaryForoshandeModel.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ExtraProp_IsSend())));
            darkhastFaktorMoshtaryForoshandeModel.setFullNameMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_NameMoshtary())));
            darkhastFaktorMoshtaryForoshandeModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CodeMoshtary())));
            darkhastFaktorMoshtaryForoshandeModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMarkazForosh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccSazmanForosh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMarkazSazmanForosh())));
            darkhastFaktorMoshtaryForoshandeModel.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CodeNoeVosolAzMoshtary())));
            darkhastFaktorMoshtaryForoshandeModel.setExtraProp_ShowFaktorMamorPakhsh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ExtraProp_ShowFaktorMamorPakhsh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcMoshtaryGharardad(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMoshtaryGharardad())));
//            darkhastFaktorMoshtaryForoshandeModel.setMoshtaryGharardadccSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_MoshtaryGharardadccSazmanForosh())));

            darkhastFaktorMoshtaryForoshandeModels.add(darkhastFaktorMoshtaryForoshandeModel);
            cursor.moveToNext();
        }
        return darkhastFaktorMoshtaryForoshandeModels;
    }

    private ArrayList<DarkhastFaktorMoshtaryForoshandeModel> cursorToModelCustomersInfo(Cursor cursor) {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel = new DarkhastFaktorMoshtaryForoshandeModel();

            darkhastFaktorMoshtaryForoshandeModel.setCcDarkhastFaktorNoeForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccDarkhastFaktorNoeForosh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMoshtary())));
            darkhastFaktorMoshtaryForoshandeModel.setCcUser(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccUser())));
            darkhastFaktorMoshtaryForoshandeModel.setLatitude(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_Latitude())));
            darkhastFaktorMoshtaryForoshandeModel.setLongitude(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_Longitude())));
            darkhastFaktorMoshtaryForoshandeModel.setCountDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CountDarkhastFaktor())));
            darkhastFaktorMoshtaryForoshandeModel.setFullNameForoshande(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_FullNameForoshandeh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcAfradForoshandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccAfradForoshandeh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMarkazForosh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccSazmanForosh())));
            darkhastFaktorMoshtaryForoshandeModel.setCcMarkazSazmanForosh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_ccMarkazSazmanForosh())));
            darkhastFaktorMoshtaryForoshandeModel.setFullNameMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_NameMoshtary())));
            darkhastFaktorMoshtaryForoshandeModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(DarkhastFaktorMoshtaryForoshandeModel.COLUMN_CodeMoshtary())));

            darkhastFaktorMoshtaryForoshandeModels.add(darkhastFaktorMoshtaryForoshandeModel);
            cursor.moveToNext();
        }
        return darkhastFaktorMoshtaryForoshandeModels;
    }

}
