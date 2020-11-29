package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryMorajehShodehRoozModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerAddressModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class CustomerAddressDAO
{

    private DBHelper dbHelper;
    private Context context;



    public CustomerAddressDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CustomerAddressDAO" , "" , "constructor" , "");
        }
    }


    public ArrayList<CustomerAddressModel> getByMasirWithoutMoshtaryJadid()
    {
        ArrayList<CustomerAddressModel> customerAddressModels = new ArrayList<>();
        try
        {
            String query = "select m.*,MA.*,IFNULL(NoeMorajeh,0) AS NoeMorajeh from (SELECT * FROM( SELECT * FROM Moshtary \n" +
                    " WHERE (ExtraProp_IsOld = 1 OR ExtraProp_IsMoshtaryAmargar = 1) and ccMasir in (select ccMasir from Masir) \n" +
                    " AND CodeVazeiat > 0 \n" +
                    " UNION SELECT * FROM Moshtary WHERE ExtraProp_MoshtaryMojazKharejAzMasir = 1 ) AS A ORDER BY ExtraProp_Olaviat ) AS M \n" +
                    " LEFT JOIN (SELECT * FROM MoshtaryAddress \n" +
                    " WHERE ccNoeAddress IN (1,2) GROUP BY ccMoshtary ORDER BY ccNoeAddress DESC) AS MA on MA.ccMoshtary = M.ccMoshtary \n" +
                    " LEFT JOIN (select distinct(ccMoshtary),NoeMorajeh from MoshtaryMorajehShodeh_Rooz ) MSR \n" +
                    " on MSR.ccMoshtary = M.ccMoshtary ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    customerAddressModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "Moshtary,MoshtaryAddress") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CustomerAddressDAO" , "" , "getByMasir" , "");
        }
        return customerAddressModels;
    }


    public ArrayList<CustomerAddressModel> getByMasir()
    {
        ArrayList<CustomerAddressModel> customerAddressModels = new ArrayList<>();
        try
        {
            String query = "select m.*,MA.*,IFNULL(NoeMorajeh,0) AS NoeMorajeh from (SELECT * FROM( SELECT * FROM Moshtary \n" +
                    " WHERE (ExtraProp_IsOld = 1 OR ExtraProp_IsMoshtaryAmargar = 1) and ccMasir in (select ccMasir from Masir) \n" +
                    " UNION SELECT * FROM Moshtary WHERE ExtraProp_MoshtaryMojazKharejAzMasir = 1 ) AS A ORDER BY ExtraProp_Olaviat ) AS M \n" +
                    " LEFT JOIN (SELECT * FROM MoshtaryAddress \n" +
                    " WHERE ccNoeAddress IN (1,2) GROUP BY ccMoshtary ORDER BY ccNoeAddress DESC) AS MA on MA.ccMoshtary = M.ccMoshtary \n" +
                    " LEFT JOIN (select distinct(ccMoshtary),NoeMorajeh from MoshtaryMorajehShodeh_Rooz ) MSR \n" +
                    " on MSR.ccMoshtary = M.ccMoshtary";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    customerAddressModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "Moshtary,MoshtaryAddress") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CustomerAddressDAO" , "" , "getByMasir" , "");
        }
        return customerAddressModels;
    }

    public ArrayList<CustomerAddressModel> getByMasirPakhsh()
    {
        ArrayList<CustomerAddressModel> customerAddressModels = new ArrayList<>();
        try
        {
            String query = "select m.*,MA.*,IFNULL(NoeMorajeh,0) AS NoeMorajeh from (SELECT * FROM( SELECT * FROM Moshtary \n" +
                    " WHERE (ExtraProp_IsOld = 1 OR ExtraProp_IsMoshtaryAmargar = 1) and ccMasir in (select ccMasir from Masir) \n" +
                    " UNION SELECT * FROM Moshtary WHERE ExtraProp_MoshtaryMojazKharejAzMasir = 1 ) AS A ORDER BY ExtraProp_Olaviat ) AS M \n" +
                    " LEFT JOIN (SELECT * FROM MoshtaryAddress \n" +
                    " WHERE ccNoeAddress IN (1,2) GROUP BY ccMoshtary ORDER BY ccNoeAddress DESC) AS MA on MA.ccMoshtary = M.ccMoshtary \n" +
                    " LEFT JOIN (select distinct(ccMoshtary),NoeMorajeh from MoshtaryMorajehShodeh_Rooz ) MSR \n" +
                    " on MSR.ccMoshtary = M.ccMoshtary";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    customerAddressModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "Moshtary,MoshtaryAddress") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CustomerAddressDAO" , "" , "getByMasir" , "");
        }
        return customerAddressModels;
    }



    private ArrayList<CustomerAddressModel> cursorToModel(Cursor cursor)
    {
        ArrayList<CustomerAddressModel> customerAddressModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            CustomerAddressModel customerAddressModel = new CustomerAddressModel();
            MoshtaryModel moshtaryModel = new MoshtaryModel();
            ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
            MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();

            moshtaryModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccMoshtary())));
            moshtaryModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameMoshtary())));
            moshtaryModel.setNameTablo(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameTablo())));
            moshtaryModel.setOlaviat(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_Olaviat())));
            moshtaryModel.setModateVosol(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ModateVosol())));
            moshtaryModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeMoshtary())));
            moshtaryModel.setAddress(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_Address())));
            moshtaryModel.setCodeNoeVosolAzMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeNoeVosolAzMoshtary())));
            moshtaryModel.setccForoshandeh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccForoshandeh())));
            moshtaryModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccMasir())));
            moshtaryModel.setToorVisit(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ToorVisit())));
            moshtaryModel.setCodeNoeHaml(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeNoeHaml())));
            moshtaryModel.setFNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_FNameMoshtary())));
            moshtaryModel.setLNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_LNameMoshtary())));
            moshtaryModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_Darajeh())));
            moshtaryModel.setNameDarajeh(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameDarajeh())));
            moshtaryModel.setCodeMely(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeMely())));
            moshtaryModel.setCodeNoeShakhsiat(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeNoeShakhsiat())));
            moshtaryModel.setCcNoeSenf(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ccNoeSenf())));
            moshtaryModel.setShenasehMely(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_ShenasehMely())));
            moshtaryModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeVazeiat())));
            moshtaryModel.setMobile(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_Mobile())));
            moshtaryModel.setMasahatMaghazeh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_MasahatMaghazeh())));
            moshtaryModel.setHasAnbar(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_HasAnbar())));
            moshtaryModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_IsOld())));
            moshtaryModel.setExtraProp_ccMoshtaryParent(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_ccMoshtaryParent())));
            moshtaryModel.setExtraProp_IsMoshtaryAmargar(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_IsMoshtaryAmargar())));
            moshtaryModel.setExtraProp_ccPorseshnameh(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_ccPorseshnameh())));
            moshtaryModel.setExtraProp_NoeForoshandeh_First(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_NoeForoshandeh_First())));
            moshtaryModel.setExtraProp_MoshtaryMojazKharejAzMasir(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_MoshtaryMojazKharejAzMasir())));
            moshtaryModel.setExtraProp_Olaviat(cursor.getInt(cursor.getColumnIndex(MoshtaryModel.COLUMN_ExtraProp_Olaviat())));


            moshtaryAddressModel.setCcMoshtaryAddress(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccMoshtaryAddress())));
            moshtaryAddressModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccMoshtary())));
            moshtaryAddressModel.setCcAddress(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccAddress())));
            moshtaryAddressModel.setCcNoeAddress(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccNoeAddress())));
            moshtaryAddressModel.setCcMahaleh(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccMahaleh())));
            moshtaryAddressModel.setNameNoeAddress(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_NameNoeAddress())));
            moshtaryAddressModel.setAddress(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Address())));
            moshtaryAddressModel.setCcShahr(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ccShahr())));
            moshtaryAddressModel.setKhiabanAsli(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KhiabanAsli())));
            moshtaryAddressModel.setKhiabanFarei1(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KhiabanFarei1())));
            moshtaryAddressModel.setKhiabanFarei2(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KhiabanFarei2())));
            moshtaryAddressModel.setKoocheAsli(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KoocheAsli())));
            moshtaryAddressModel.setKoocheFarei1(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KoocheFarei1())));
            moshtaryAddressModel.setKoocheFarei2(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_KoocheFarei2())));
            moshtaryAddressModel.setPelak(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Pelak())));
            moshtaryAddressModel.setTelephone(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Telephone())));
            moshtaryAddressModel.setCodePosty(cursor.getString(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_CodePosty())));
            moshtaryAddressModel.setExtraProp_InsertInPPC(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ExtraProp_InsertInPPC())));
            moshtaryAddressModel.setExtraProp_IsSendToSql(cursor.getInt(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_ExtraProp_IsSendToSql())));
            moshtaryAddressModel.setLongitude_x(cursor.getDouble(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Longitude_x())));
            moshtaryAddressModel.setLatitude_y(cursor.getDouble(cursor.getColumnIndex(MoshtaryAddressModel.COLUMN_Latitude_y())));

            moshtaryAddressModels.add(moshtaryAddressModel);


            MoshtaryMorajehShodehRoozModel moshtaryMorajehShodehRoozModel = new MoshtaryMorajehShodehRoozModel();
            moshtaryMorajehShodehRoozModel.setNoeMorajeh(cursor.getInt(cursor.getColumnIndex(MoshtaryMorajehShodehRoozModel.COLUMN_NoeMorajeh())));


            customerAddressModel.setMoshtaryModel(moshtaryModel);
            customerAddressModel.setMoshtaryAddressModels(moshtaryAddressModels);
            customerAddressModel.setMoshtaryMorajehShodehRoozModel(moshtaryMorajehShodehRoozModel);

            customerAddressModels.add(customerAddressModel);

            cursor.moveToNext();
        }
        return customerAddressModels;
    }


}
