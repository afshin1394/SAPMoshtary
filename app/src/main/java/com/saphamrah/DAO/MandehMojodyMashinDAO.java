package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxCallback;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetMandehMojodyMashinResponse;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;

public class MandehMojodyMashinDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = MandehMojodyMashinDAO.class.getSimpleName();


    public MandehMojodyMashinDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MandehMojodyMashinDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MandehMojodyMashinModel.COLUMN_ccKalaCode(),
            MandehMojodyMashinModel.COLUMN_CodeNoeKala(),
            MandehMojodyMashinModel.COLUMN_Mojody(),
            MandehMojodyMashinModel.COLUMN_ShomarehBach(),
            MandehMojodyMashinModel.COLUMN_TarikhTolid(),
            MandehMojodyMashinModel.COLUMN_GheymatMasrafKonandeh(),
            MandehMojodyMashinModel.COLUMN_GheymatForosh(),
            MandehMojodyMashinModel.COLUMN_ccTaminKonandeh(),
            MandehMojodyMashinModel.COLUMN_Max_Mojody(),
            MandehMojodyMashinModel.COLUMN_Max_MojodyByShomarehBach(),
            MandehMojodyMashinModel.COLUMN_IsAdamForosh()
        };
    }

    public void fetchMandehMojodyMashin(final Context context, final String activityNameForLog, final String ccAnbarak, String ccForoshandeh, String ccMamorPakhsh,String ccKalaCode,String ccSazmanForosh, final RxResponseHandler rxResponseHandler) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);

        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);
        RxHttpRequest.getInstance()
                .execute(apiServiceRxjava.getMandehMojodyMashin(ccAnbarak, ccForoshandeh, ccMamorPakhsh,ccKalaCode,ccSazmanForosh), activityNameForLog, CLASS_NAME, "fetchAllRptHadafForoshRx", new RxCallback<GetMandehMojodyMashinResponse>() {
                    @Override
                    public void onStart(Disposable disposable) {
                        rxResponseHandler.onStart(disposable);
                    }


                    @Override
                    public void onSuccess(GetMandehMojodyMashinResponse response) {
                        rxResponseHandler.onSuccess(response.getMandehMojodyMashinModels());
                    }

                    @Override
                    public void onError(String message, String type) {
                        rxResponseHandler.onFailed(message, type);
                    }


                });

    }

    private String getEndpoint(Call call)
    {
        String endpoint = "";
        try
        {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
        }
        catch (Exception e)
        {e.printStackTrace();}
        return endpoint;
    }



    public boolean insertGroup(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MandehMojodyMashinModel mandehMojodyMashinModel : mandehMojodyMashinModels)
            {
                ContentValues contentValues = modelToContentvalue(mandehMojodyMashinModel);
                db.insertOrThrow(MandehMojodyMashinModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MandehMojodyMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MandehMojodyMashinDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<MandehMojodyMashinModel> getAll()
    {
        ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MandehMojodyMashinModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    mandehMojodyMashinModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MandehMojodyMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MandehMojodyMashinDAO" , "" , "getAll" , "");
        }
        return mandehMojodyMashinModels;
    }


    public void updateIsAdamForosh(String ccKalaCodes)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "update MandehMojodyMashin set IsAdamForosh = 1 where ccKalaCode in (" + ccKalaCodes + ")";
            db.execSQL(query);
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MandehMojodyMashinModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MandehMojodyMashinModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MandehMojodyMashinDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MandehMojodyMashinModel mandehMojodyMashinModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MandehMojodyMashinModel.COLUMN_ccKalaCode() , mandehMojodyMashinModel.getCcKalaCode());
        contentValues.put(MandehMojodyMashinModel.COLUMN_CodeNoeKala() , mandehMojodyMashinModel.getCodeNoeKala());
        contentValues.put(MandehMojodyMashinModel.COLUMN_Mojody() , mandehMojodyMashinModel.getMojody());
        contentValues.put(MandehMojodyMashinModel.COLUMN_ShomarehBach() , mandehMojodyMashinModel.getShomarehBach());
        contentValues.put(MandehMojodyMashinModel.COLUMN_TarikhTolid() , mandehMojodyMashinModel.getTarikhTolid());
        contentValues.put(MandehMojodyMashinModel.COLUMN_GheymatMasrafKonandeh() , mandehMojodyMashinModel.getGheymatMasrafKonandeh());
        contentValues.put(MandehMojodyMashinModel.COLUMN_GheymatForosh() , mandehMojodyMashinModel.getGheymatForosh());
        contentValues.put(MandehMojodyMashinModel.COLUMN_ccTaminKonandeh() , mandehMojodyMashinModel.getCcTaminKonandeh());
        contentValues.put(MandehMojodyMashinModel.COLUMN_Max_Mojody() , mandehMojodyMashinModel.getMaxMojody());
        contentValues.put(MandehMojodyMashinModel.COLUMN_Max_MojodyByShomarehBach() , mandehMojodyMashinModel.getMax_MojodyByShomarehBach());
        contentValues.put(MandehMojodyMashinModel.COLUMN_IsAdamForosh() , mandehMojodyMashinModel.getIsAdamForosh());

        return contentValues;
    }


    private ArrayList<MandehMojodyMashinModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MandehMojodyMashinModel mandehMojodyMashinModel = new MandehMojodyMashinModel();

            mandehMojodyMashinModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_ccKalaCode())));
            mandehMojodyMashinModel.setCodeNoeKala(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_CodeNoeKala())));
            mandehMojodyMashinModel.setMojody(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_Mojody())));
            mandehMojodyMashinModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_ShomarehBach())));
            mandehMojodyMashinModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_TarikhTolid())));
            mandehMojodyMashinModel.setGheymatMasrafKonandeh(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_GheymatMasrafKonandeh())));
            mandehMojodyMashinModel.setGheymatForosh(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_GheymatForosh())));
            mandehMojodyMashinModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_ccTaminKonandeh())));
            mandehMojodyMashinModel.setMaxMojody(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_Max_Mojody())));
            mandehMojodyMashinModel.setMax_MojodyByShomarehBach(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_Max_MojodyByShomarehBach())));
            mandehMojodyMashinModel.setIsAdamForosh(cursor.getInt(cursor.getColumnIndex(MandehMojodyMashinModel.COLUMN_IsAdamForosh())));

            mandehMojodyMashinModels.add(mandehMojodyMashinModel);
            cursor.moveToNext();
        }
        return mandehMojodyMashinModels;
    }





    private Callable<Boolean>  insertGroupRX(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        return new Callable<Boolean>() {
            public Boolean call() {
                return insertGroup(mandehMojodyMashinModels);
            }
        };
    }

    private Callable<Boolean> deleteAllRx() {
        return new Callable<Boolean>() {
            public Boolean call() {
                return deleteAll();
            }
        };
    }

}
