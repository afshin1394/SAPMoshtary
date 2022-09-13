package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.RptTafkikMovazeDataModel;
import com.saphamrah.Model.TafkikKalaMovazeDataModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptTafkikMovazeDAO {

    private DBHelper dbHelper;
    private Context context;


    public RptTafkikMovazeDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptTafkikMovazeDAO" , "" , "constructor" , "");
        }
    }

    public ArrayList<RptTafkikMovazeDataModel> getAllForTafkikMovaze()
    {
        ArrayList<RptTafkikMovazeDataModel> darkhastFaktorModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "Select ccDarkhastFaktor,ShomarehFaktor,DarkhastFaktor.ccMoshtary,NameMoshtary  from DarkhastFaktor " +
                    "LEFT JOIN Moshtary ON Moshtary.ccMoshtary = DarkhastFaktor.ccMoshtary ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorModels = cursorToModelTafkikMovaze(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getAllByCodeVazeiat" , "");
        }
        return darkhastFaktorModels;
    }

    public ArrayList<TafkikKalaMovazeDataModel> getKalaByCcDarkhastFactor(String ccDarkhastFaktors)
    {
        ArrayList<TafkikKalaMovazeDataModel> darkhastFaktorModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//            String query = "Select Kala.TedadDarKarton,Kala.TedadDarBasteh,Kala.Adad ,(Kala.Tol / 100) AS Tol,(Kala.Arz / 100) AS Arz,(Kala.Ertefa / 100) AS Ertefa,Kala.NameKala,Kala.CodeKala,Kala.NameVahedSize," +
//                    " Kala.NameVahedVazn, Sum(DarkhastFaktorSatr.Tedad3) AS Tedad3,SUM(Kala.VaznNaKhales) / 1000.0 AS VaznNaKhales " +
//                    " FROM Kala " +
//                    " LEFT JOIN DarkhastFaktorSatr ON Kala.ccKalaCode = DarkhastFaktorSatr.ccKalaCode " +
//                    " WHERE  DarkhastFaktorSatr.ccDarkhastFaktor IN ( " + ccDarkhastFaktors + " ) " +
//                    " GROUP BY Kala.NameKala " +
//                    " UNION All " +
//                    " SELECT 1, 1, 1, 1, Sum(Kala.Tol) AS Tol, Sum(Kala.Arz) AS Arz, Sum(Kala.Ertefa) As Ertefa , 1, 1, 1," +
//                    " Sum(DarkhastFaktorSatr.Tedad3) AS Tedad3,SUM(Kala.VaznNaKhales) / 1000.0 AS VaznNaKhales " +
//                    " FROM Kala " +
//                    " LEFT JOIN DarkhastFaktorSatr ON Kala.ccKalaCode = DarkhastFaktorSatr.ccKalaCode " +
//                    " WHERE  DarkhastFaktorSatr.ccDarkhastFaktor IN ( " + ccDarkhastFaktors + " ) " ;

            String query = "SELECT k.CodeKala, K.NameKala, Sum(DFS.Tedad3) AS Tedad3 ,\n" +
                    "              IFNULL ((SUM(DFS.Tedad3)/K.TedadDarKarton),0) AS TedadDarKarton, IFNULL(( (SUM(DFS.Tedad3)%K.TedadDarKarton) / K.TedadDarBasteh),0) AS TedadDarBasteh,\n" +
                    "              IFNULL(((SUM(DFS.Tedad3)%K.TedadDarKarton) %K.TedadDarBasteh),0) AS Adad ,\n" +
                    "              IFNULL(((K.Tol ) * (K.Arz ) * (K.Ertefa ))/IFNULL ((SUM(DFS.Tedad3)/K.TedadDarKarton),0)*Sum(DFS.Tedad3) / 1000000.0,0) AS Hajm ,\n" +
                    "              (Sum(DFS.Tedad3)*K.VaznNaKhales) / 1000.0 AS VaznNaKhales, (Sum(DFS.Tedad3)*K.VaznKhales) / 1000.0 AS VaznKhales\n" +
                    "FROM DarkhastFaktorSatr  DFS\n" +
                    "     LEFT JOIN (SELECT CodeKala,ccKalaCode,NameKala,Tol,Arz,Ertefa,TedadDarBasteh,TedadDarKarton,VaznKhales,VaznNaKhales FROM KALA  GROUP BY ccKalaCode,NameKala,Tol,Arz,Ertefa,TedadDarBasteh,TedadDarKarton,VaznKhales,VaznNaKhales) K ON K.ccKalaCode = DFS.ccKalaCode \n" +
                    "WHERE  DFS.ccDarkhastFaktor IN ( " + ccDarkhastFaktors +" ) \n" +
                    "GROUP BY DFS.ccKalaCode\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "\n" +
                    "SELECT '' AS ccKalaCode, 'جمع کل' AS NameKala, SUM(Tedad3),SUM(TedadDarKarton),SUM(TedadDarBasteh),SUM(Adad),SUM(Hajm),SUM(VaznNaKhales),SUM(VaznKhales)\n" +
                    "FROM (\n" +
                    "    SELECT K.ccKalaCode, K.NameKala, Sum(DFS.Tedad3) AS Tedad3 ,\n" +
                    "                  IFNULL ((SUM(DFS.Tedad3)/K.TedadDarKarton),0) AS TedadDarKarton, IFNULL(( (SUM(DFS.Tedad3)%K.TedadDarKarton) / K.TedadDarBasteh),0) AS TedadDarBasteh,\n" +
                    "                  IFNULL(((SUM(DFS.Tedad3)%K.TedadDarKarton) %K.TedadDarBasteh),0) AS Adad ,\n" +
                    "                  IFNULL(((K.Tol ) * (K.Arz ) * (K.Ertefa ))/IFNULL ((SUM(DFS.Tedad3)/K.TedadDarKarton),0)*Sum(DFS.Tedad3) / 1000000.0,0) AS Hajm ,\n" +
                    "                  (Sum(DFS.Tedad3)*K.VaznNaKhales) / 1000.0 AS VaznNaKhales, (Sum(DFS.Tedad3)*K.VaznKhales) / 1000.0 AS VaznKhales\n" +
                    "    FROM DarkhastFaktorSatr  DFS\n" +
                    "         LEFT JOIN (SELECT ccKalaCode,NameKala,Tol,Arz,Ertefa,TedadDarBasteh,TedadDarKarton,VaznKhales,VaznNaKhales FROM KALA  GROUP BY ccKalaCode,NameKala,Tol,Arz,Ertefa,TedadDarBasteh,TedadDarKarton,VaznKhales,VaznNaKhales) K ON K.ccKalaCode = DFS.ccKalaCode \n" +
                    "    WHERE  DFS.ccDarkhastFaktor IN ( " + ccDarkhastFaktors +" ) \n" +
                    "    GROUP BY DFS.ccKalaCode\n" +
                    ")\n" +
                    "\n";

            String query2 = "Select IFNULL ((SUM(DarkhastFaktorSatr.Tedad3)/Kala.TedadDarKarton),0) AS TedadDarKarton, IFNULL(( (SUM(DarkhastFaktorSatr.Tedad3)%Kala.TedadDarKarton) / Kala.TedadDarBasteh),0) AS TedadDarBasteh," +
                    "    IFNULL(((SUM(DarkhastFaktorSatr.Tedad3)%Kala.TedadDarKarton) %Kala.TedadDarBasteh),0) AS Adad ,((Kala.Tol ) * (Kala.Arz ) * (Kala.Ertefa )) / 1000000.0 AS Hajm ," +
                    "    Kala.NameKala,Kala.CodeKala,Kala.NameVahedSize," +
                    "    Kala.NameVahedVazn, Sum(DarkhastFaktorSatr.Tedad3) AS Tedad3,SUM(Kala.VaznNaKhales) / 1000.0 AS VaznNaKhales " +
                    "    FROM Kala " +
                    "    LEFT JOIN DarkhastFaktorSatr ON Kala.ccKalaCode = DarkhastFaktorSatr.ccKalaCode " +
                    "    WHERE  DarkhastFaktorSatr.ccDarkhastFaktor IN ( " + ccDarkhastFaktors + " ) " +
                    "    GROUP BY Kala.NameKala " +
                    "    UNION All " +
                    "    SELECT SUM(IFNULL ((DarkhastFaktorSatr.Tedad3/Kala.TedadDarKarton),0)) AS TedadDarKarton, SUM(IFNULL(( (DarkhastFaktorSatr.Tedad3%Kala.TedadDarKarton) / Kala.TedadDarBasteh),0)) AS TedadDarBasteh," +
                    "    SUM(IFNULL(( (DarkhastFaktorSatr.Tedad3%Kala.TedadDarKarton) %Kala.TedadDarBasteh),0)) AS Adad , SUM((Kala.Tol ) * (Kala.Arz ) * (Kala.Ertefa )) / 1000000.0 AS Hajm ,1, 1, 1, 1," +
                    "    Sum(DarkhastFaktorSatr.Tedad3) AS Tedad3,SUM(Kala.VaznNaKhales) / 1000.0 AS VaznNaKhales " +
                    "    FROM Kala " +
                    "    LEFT JOIN DarkhastFaktorSatr ON Kala.ccKalaCode = DarkhastFaktorSatr.ccKalaCode And" +
                    "     DarkhastFaktorSatr.GheymatForoshAsli = Kala.GheymatForoshAsli  " +
                    "    WHERE  DarkhastFaktorSatr.ccDarkhastFaktor IN ( " + ccDarkhastFaktors + " ) ";

                    Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorModels = cursorToModelKala(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getAllByCodeVazeiat" , "");
        }
        return darkhastFaktorModels;
    }


    private ArrayList<RptTafkikMovazeDataModel> cursorToModelTafkikMovaze(Cursor cursor)
    {
        ArrayList<RptTafkikMovazeDataModel> tafkikMovazeDataModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptTafkikMovazeDataModel rptTafkikMovazeDataModel = new RptTafkikMovazeDataModel();

            rptTafkikMovazeDataModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccDarkhastFaktor())));
            rptTafkikMovazeDataModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ShomarehFaktor())));
            rptTafkikMovazeDataModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccMoshtary())));
            rptTafkikMovazeDataModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameMoshtary())));

            tafkikMovazeDataModels.add(rptTafkikMovazeDataModel);
            cursor.moveToNext();
        }
        return tafkikMovazeDataModels;
    }

    private ArrayList<TafkikKalaMovazeDataModel> cursorToModelKala(Cursor cursor)
    {
        ArrayList<TafkikKalaMovazeDataModel> tafkikMovazeDataModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TafkikKalaMovazeDataModel model = new TafkikKalaMovazeDataModel();

            model.setTedadDarKarton(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadDarKarton())));
            model.setTedadDarBasteh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadDarBasteh())));
            model.setAdad(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_Adad())));
            model.setHajm((cursor.getDouble(cursor.getColumnIndex("Hajm"))));
//            model.setTol(cursor.getDouble(cursor.getColumnIndex(KalaModel.COLUMN_Tol())));
//            model.setArz(cursor.getDouble(cursor.getColumnIndex(KalaModel.COLUMN_Arz())));
//            model.setErtefa(cursor.getDouble(cursor.getColumnIndex(KalaModel.COLUMN_Ertefa())));
            model.setNameKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameKala())));
            model.setCodeKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_CodeKala())));
//            model.setNameVahedVazn(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedVazn())));
//            model.setNameVahedSize(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedSize())));
            model.setVaznKhales(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_VaznKhales())));
            model.setVaznNaKhales(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_VaznNaKhales())));
            model.setTedad(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorSatrModel.COLUMN_Tedad3())));

            tafkikMovazeDataModels.add(model);
            cursor.moveToNext();
        }
        return tafkikMovazeDataModels;
    }
}
