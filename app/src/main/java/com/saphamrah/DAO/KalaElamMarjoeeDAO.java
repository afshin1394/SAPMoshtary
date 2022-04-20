package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.ElamMarjoeeSatrPPCModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class KalaElamMarjoeeDAO
{

    private DBHelper dbHelper;
    private Context context;



    public KalaElamMarjoeeDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaElamMarjoeeDAO" , "" , "constructor" , "");
        }
    }


    public ArrayList<KalaElamMarjoeeModel> getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select sk.*, Sharh from (select s.*,k.CodeKala,k.NameKala,s.Fee AS MablaghForosh,s.GheymatForoshAsli,s.GheymatMasrafKonandeh AS MablaghMasrafKonandeh \n" +
                    "from ElamMarjoeeSatrPPC s \n" +
                    //"left join ListKalaForMarjoee k \n" +
                    "left join (SELECT DISTINCT ccKalaCode,CodeKala,NameKala FROM ListKalaForMarjoee) k \n" +
                    "on s.ccKalaCode = k.ccKalaCode " +
                    //"and s.shomarehbach = k.shomarehbach " +
                    //"and s.fee = k.mablaghforosh and s.cctaminkonandeh = k.cctaminkonandeh " +
                    //"and s.gheymatmasrafkonandeh = k.mablaghmasrafkonandeh) sk \n" +
                    ") sk \n" +
                    "inner join ElatMarjoeeKala \n" +
                    "on sk.ccElatMarjoeeKala = ElatMarjoeeKala.ccElatMarjoeeKala \n" +
                    "where sk.ccDarkhastFaktor = " + ccDarkhastFaktor;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaElamMarjoeeModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "ElamMarjoeeSatrPPC,ListKalaForMarjoee,ElatMarjoeeKala") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaElamMarjoeeDAO" , "" , "getAll" , "");
        }
        return kalaElamMarjoeeModels;
    }


    private ArrayList<KalaElamMarjoeeModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaElamMarjoeeModel kalaElamMarjoeeModel = new KalaElamMarjoeeModel();

            //ElamMarjoeeSatrPPC
            kalaElamMarjoeeModel.setCcElamMarjoeeSatrPPC(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeeSatrPPC())));
            kalaElamMarjoeeModel.setCcDarkhastFaktor(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccDarkhastFaktor())));
            kalaElamMarjoeeModel.setCcElamMarjoeePPC(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccElamMarjoeePPC())));
            kalaElamMarjoeeModel.setCcElatMarjoeeKala(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccElatMarjoeeKala())));
            kalaElamMarjoeeModel.setCodeNoeMarjoee(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_CodeNoeMarjoee())));
            kalaElamMarjoeeModel.setCcKala(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccKala())));
            kalaElamMarjoeeModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccKalaCode())));
            kalaElamMarjoeeModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ShomarehBach())));
            kalaElamMarjoeeModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_TarikhTolid())));
            kalaElamMarjoeeModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_TarikhEngheza())));
            kalaElamMarjoeeModel.setTedad3(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_Tedad3())));
            kalaElamMarjoeeModel.setFee(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_Fee())));
            kalaElamMarjoeeModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_ccTaminkonandeh())));
            kalaElamMarjoeeModel.setGheymatForoshAsli(cursor.getFloat(cursor.getColumnIndex(ElamMarjoeeSatrPPCModel.COLUMN_GheymatForoshAsli())));

            //ListKalaForMarjoee
            kalaElamMarjoeeModel.setCodeKala(cursor.getString(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_CodeKala())));
            kalaElamMarjoeeModel.setNameKala(cursor.getString(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_NameKala())));
            kalaElamMarjoeeModel.setMablaghForosh(cursor.getFloat(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_MablaghForosh())));
            kalaElamMarjoeeModel.setMablaghMasrafKonandeh(cursor.getFloat(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_MablaghMasrafKonandeh())));

            //ElatMarjoeeKala
            kalaElamMarjoeeModel.setSharh(cursor.getString(cursor.getColumnIndex(ElatMarjoeeKalaModel.COLUMN_Sharh())));

            kalaElamMarjoeeModels.add(kalaElamMarjoeeModel);
            cursor.moveToNext();
        }
        return kalaElamMarjoeeModels;
    }


}
