package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetListBargashtyForoshandehResult;
import com.saphamrah.protos.ReturnedChequeGrpc;
import com.saphamrah.protos.ReturnedChequeReply;
import com.saphamrah.protos.ReturnedChequeReplyList;
import com.saphamrah.protos.ReturnedChequeRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BargashtyDAO
{

    private DBHelper dbHelper;
    private Context context;


    public BargashtyDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "BargashtyDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            BargashtyModel.COLUMN_ccAfradErsalKonandeh(),
            BargashtyModel.COLUMN_ccDarkhastFaktor(),
            BargashtyModel.COLUMN_ccDariaftPardakht(),
            BargashtyModel.COLUMN_ccMarkazAnbar(),
            BargashtyModel.COLUMN_ccMarkazForosh(),
            BargashtyModel.COLUMN_NameMarkazForosh(),
            BargashtyModel.COLUMN_ccSazmanForosh(),
            BargashtyModel.COLUMN_NameSazmanForosh(),
            BargashtyModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            BargashtyModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh(),
            BargashtyModel.COLUMN_ccForoshandeh(),
            BargashtyModel.COLUMN_ccMoshtary(),
            BargashtyModel.COLUMN_NameMoshtary(),
            BargashtyModel.COLUMN_CodeMoshtary(),
            BargashtyModel.COLUMN_TarikhSanad(),
            BargashtyModel.COLUMN_ShomarehSanad(),
            BargashtyModel.COLUMN_Mablagh(),
            BargashtyModel.COLUMN_MablaghMandeh(),
            BargashtyModel.COLUMN_TarikhSanadWithSlash(),
            BargashtyModel.COLUMN_ZamaneSabt(),
            BargashtyModel.COLUMN_ZamaneSabtWithSlash(),
            BargashtyModel.COLUMN_ccDarajeh(),
            BargashtyModel.COLUMN_ccNoeMoshtary()
        };
    }

    public void fetchBargashtyGrpc(final Context context, final String activityNameForLog,final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        try {


            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            serverIpModel.setPort("5000");


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT() , message);
            }
            else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                ReturnedChequeGrpc.ReturnedChequeBlockingStub returnedChequeBlockingStub = ReturnedChequeGrpc.newBlockingStub(managedChannel);
                ReturnedChequeRequest returnedChequeRequest = ReturnedChequeRequest.newBuilder().setSalesManID(ccForoshandeh).build();
                Callable<ReturnedChequeReplyList> getReturnedChequeCallable  = () -> returnedChequeBlockingStub.getReturnedCheque(returnedChequeRequest);
                RxAsync.makeObservable(getReturnedChequeCallable)
                        .map(returnedChequeReplyList -> {
                            ArrayList<BargashtyModel> bargashtyModels = new ArrayList<>();
                            for (ReturnedChequeReply returnedChequeReply : returnedChequeReplyList.getReturnedChequesList()) {
                                BargashtyModel bargashtyModel = new BargashtyModel();
                                bargashtyModel.setCcAfradErsalKonandeh(returnedChequeReply.getSenderPersonsID());
                                bargashtyModel.setCcDarkhastFaktor(returnedChequeReply.getInvoiceRequestID());
                                bargashtyModel.setCcDariaftPardakht(returnedChequeReply.getReceivePaymentID());
                                bargashtyModel.setCcMarkazAnbar(returnedChequeReply.getWarehouseCenterID());
                                bargashtyModel.setCcMarkazForosh(returnedChequeReply.getSellCenterID());
                                bargashtyModel.setNameMarkazForosh(returnedChequeReply.getSellCenterName());
                                bargashtyModel.setCcSazmanForosh(returnedChequeReply.getSellOrganizationID());
                                bargashtyModel.setNameSazmanForosh(returnedChequeReply.getSellOrganizationName());
                                bargashtyModel.setCcMarkazSazmanForoshSakhtarForosh(returnedChequeReply.getSellStructureSellOrganizationCenterID());
                                bargashtyModel.setNameMarkazSazmanForoshSakhtarForosh(returnedChequeReply.getSellStructureSellOrganizationCenterName());
                                bargashtyModel.setCcForoshandeh(returnedChequeReply.getSellerID());
                                bargashtyModel.setCcMoshtary(returnedChequeReply.getCustomerID());
                                bargashtyModel.setNameMoshtary(returnedChequeReply.getCustomerName());
                                bargashtyModel.setCodeMoshtary(returnedChequeReply.getCustomerCode());
                                bargashtyModel.setTarikhSanad(returnedChequeReply.getDucumentDate());
                                bargashtyModel.setShomarehSanad(returnedChequeReply.getDucumentNumber());
                                bargashtyModel.setMablagh(returnedChequeReply.getPrice());
                                bargashtyModel.setMablaghMandeh(returnedChequeReply.getRemainingPrice());
                                bargashtyModel.setTarikhSanadWithSlash(returnedChequeReply.getDucumentDateWithSlash());
                                bargashtyModel.setZamaneSabt(returnedChequeReply.getRegisterTime());
                                bargashtyModel.setZamaneSabtWithSlash(returnedChequeReply.getRegisterTimeWithSlash());
                                bargashtyModel.setCcNoeMoshtary(returnedChequeReply.getCustomerTypeID());
                                bargashtyModel.setCcDarajeh(returnedChequeReply.getDegreeID());


                                bargashtyModels.add(bargashtyModel);
                            }

                            return bargashtyModels;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<BargashtyModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<BargashtyModel> bargashtyModels) {
                                retrofitResponse.onSuccess(bargashtyModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(),e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                if (!compositeDisposable.isDisposed()) {
                                    compositeDisposable.dispose();
                                }
                                compositeDisposable.clear();
                            }
                        });
            }
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), AmargarGorohDAO.class.getSimpleName(), activityNameForLog, "fetchamrgarGorohGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION() , exception.getMessage());
        }

    }


    public void fetchBargashty(final Context context, final String activityNameForLog,final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        //serverIpModel.setPort("8040");
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetListBargashtyForoshandehResult> call = apiServiceGet.getListBargashtyForoshandeh(ccForoshandeh);
            call.enqueue(new Callback<GetListBargashtyForoshandehResult>()
            {
                @Override
                public void onResponse(Call<GetListBargashtyForoshandehResult> call, Response<GetListBargashtyForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, BargashtyDAO.class.getSimpleName(), "", "fetchBargashty", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetListBargashtyForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = "";
                                try
                                {
                                    endpoint = call.request().url().toString();
                                    endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                                }catch (Exception e){e.printStackTrace();}
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",context.getResources().getString(R.string.resultIsNull) , endpoint), BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = "";
                            try
                            {
                                endpoint = call.request().url().toString();
                                endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                            }catch (Exception e){e.printStackTrace();}
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetListBargashtyForoshandehResult> call, Throwable t)
                {
                    String endpoint = "";
                    try
                    {
                        endpoint = call.request().url().toString();
                        endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
                    }catch (Exception e){e.printStackTrace();}
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), BargashtyDAO.class.getSimpleName(), activityNameForLog, "fetchBargashty", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<BargashtyModel> bargashtyModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (BargashtyModel bargashtyModel : bargashtyModels)
            {
                ContentValues contentValues = modelToContentvalue(bargashtyModel);
                db.insertOrThrow(BargashtyModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean updateMandehBargashti(long ccDarkhastFaktor)
    {
        try
        {
            String query =" UPDATE Bargashty "
                    +" SET MablaghMandeh = Mablagh -  ifnull((SELECT sum(Mablagh) FROM DariaftPardakhtDarkhastFaktorPPC WHERE ccDarkhastFaktor = " + ccDarkhastFaktor + "),0)  "
                    +" Where ccDariaftPardakht = " + ccDarkhastFaktor ;
            Log.d("mablaghMandeh" , "query : " + query);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , DarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtiDAO" , "" , "updateMandehDarkhastFaktor" , "");
            return false;
        }
    }
    public ArrayList<BargashtyModel> getAll()
    {
        ArrayList<BargashtyModel> bargashtyModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(BargashtyModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    bargashtyModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getAll" , "");
        }
        return bargashtyModels;
    }

    public BargashtyModel getByccDarkhastFaktor(long ccDarkhastFaktor)
    {
        BargashtyModel bargashtyModel = new BargashtyModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(BargashtyModel.TableName(), allColumns(), BargashtyModel.COLUMN_ccDariaftPardakht() + " = " + ccDarkhastFaktor, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    bargashtyModel = cursorToModel(cursor).get(0);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorDAO" , "" , "getByccDarkhastFaktor" , "");
        }
        return bargashtyModel;
    }
    public ArrayList<BargashtyModel> getAllWithSum()
    {
        ArrayList<BargashtyModel> bargashtyModels = new ArrayList<>();
        String query = "select * from (SELECT DISTINCT 0 AS _id, ccMoshtary, CodeMoshtary, NameMoshtary, ShomarehSanad AS ShomarehCheck, Mablagh AS MablaghCheck, \n" +
                " MablaghMandeh, TarikhSanad AS TarikhSarResidCheck, 0 As Jam \n" +
                " FROM Bargashty \n" +
                " UNION ALL \n" +
                " SELECT 0 AS _id, 0,  CodeMoshtary, '', 'جمع', Sum(Mablagh) AS MablaghCheck, \n" +
                " Sum(MablaghMandeh) AS MablaghMandeh,'', 1 As Jam \n" +
                " FROM ( SELECT distinct CodeMoshtary, NameMoshtary, Mablagh, MablaghMandeh  FROM Bargashty ) \n" +
                " group by CodeMoshtary, NameMoshtary \n" +
                " order by CodeMoshtary) \n" +
                " UNION ALL \n" +
                " SELECT 0 AS _id, 0,  '', '', 'جمع کل', Sum(Mablagh) AS MablaghCheck, \n" +
                " Sum(MablaghMandeh) AS MablaghMandeh,'', 2 As Jam \n" +
                " FROM ( SELECT distinct CodeMoshtary, NameMoshtary, Mablagh, MablaghMandeh  FROM Bargashty )";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    bargashtyModels = cursorToModelWithSum(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getAllWithSum" , "");
        }
        return bargashtyModels;
    }

    public float getSumBargashtyByccMoshtary(int ccMoshtary)
    {
        float sumBargashty = 0;
        try
        {
            String query = " Select Round(Sum(MablaghMandeh),0) AS MablaghMandehCheck "
                    + " From (SELECT Distinct TarikhSanad, ShomarehSanad, MablaghMandeh "
                    + "	FROM Bargashty Where ccMoshtary=" + ccMoshtary +")" ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumBargashty = cursor.getFloat(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getSumBargashtyByccMoshtary" , "");
        }
        return sumBargashty;
    }

    public float getSumBargashtyByccForoshandeh(int ccForoshandeh)
    {
        float sumBargashty = 0;
        try
        {
            String query = " Select Round(Sum(MablaghMandeh),0) AS MablaghMandehCheck "
                    + " From (SELECT Distinct TarikhSanad, ShomarehSanad, MablaghMandeh "
                    + " FROM Bargashty WHERE ccForoshandeh= " + ccForoshandeh + "  and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1))" ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    sumBargashty = cursor.getFloat(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getSumBargashtyByccForoshandeh" , "");
        }
        return sumBargashty;
    }

    public int getTedadBargashtyByccForoshandeh(int ccForoshandeh)
    {
        int tedadBargashty = 0;
        try
        {
            String query = " Select Count(ShomarehSanad) AS TedadCheck "
                    + " From (SELECT Distinct TarikhSanad, ShomarehSanad, MablaghMandeh "
                    + " FROM Bargashty WHERE ccForoshandeh=" + ccForoshandeh + " and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1))" ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    tedadBargashty = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getTedadBargashtyByccForoshandeh" , "");
        }
        return tedadBargashty;
    }

    public long getModatBargashtyByccForoshandeh(int ccForoshandeh)
    {
        SimpleDateFormat Formatter = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
        Date today = new Date();
        Date thatDay = null;
        long diff = 0;
        long modatBargashti = 0;
        try
        {
            String query = "SELECT Distinct TarikhSanad, ShomarehSanad, MablaghMandeh "
                    + " FROM Bargashty WHERE ccForoshandeh=" + ccForoshandeh + "  and ccMoshtary not in (select ccMoshtary from Moshtary where ControlEtebarForoshandeh = 1) "
                    + " ORDER BY TarikhSanad LIMIT 1";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    thatDay = Formatter.parse(cursor.getString(0));
                    diff = today.getTime() - thatDay.getTime();
                    modatBargashti = diff / (24 * 60 * 60 * 1000);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getModatBargashtyByccForoshandeh" , "");
        }
        return modatBargashti;
    }

    public int getModatBargashtyByccMoshtary(int ccMoshtary)
    {
        int modat = 0;
        try
        {
            String query = "select julianday('now') - julianday(Max(TarikhSanad)) from Bargashty where ccMoshtary = " + ccMoshtary;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    modat = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getModatBargashtyByccMoshtary" , "");
        }
        return modat;
    }

    public int getCountByccMoshtaryAndSazmanForosh(int ccMoshtary , int ccSazmanForosh)
    {
        int count = 0;
        String query = "select count(" + BargashtyModel.COLUMN_ccMoshtary() + ") from " + BargashtyModel.TableName() +
                " where " + BargashtyModel.COLUMN_ccMoshtary() + " = " + ccMoshtary + " and " + BargashtyModel.COLUMN_ccSazmanForosh() + " = " + ccSazmanForosh;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
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
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getCountByccMoshtaryAndSazmanForosh" , "");
        }
        return count;
    }

    public ArrayList<BargashtyModel> getBargashtyForoshandehBishtarAz5Rooz(int ccForoshandeh)
    {
        ArrayList<BargashtyModel> bargashtyModels = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM Bargashty WHERE ccForoshandeh = " + ccForoshandeh +" AND  (julianday('now') - julianday(ZamaneSabt) > 5) " ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    bargashtyModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getBargashtyForoshandehBishtarAz5Rooz" , "");
        }
        return bargashtyModels;
    }

    public ArrayList<BargashtyModel> getBargashtyByShomarehSanad(String shomarehSanad)
    {
        ArrayList<BargashtyModel> bargashtyModels = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM Bargashty WHERE ShomarehSanad = '" + shomarehSanad + "'";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    bargashtyModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getModatBargashtyByccForoshandeh" , "");
        }
        return bargashtyModels;
    }

    public String getNameMoshtaryByccMoshtary(int ccMoshtary)
    {
        String nameMoshtary = "";
        try
        {
            String query = " Select NameMoshtary "
                    + "	FROM Bargashty Where ccMoshtary=" + ccMoshtary +"" ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    nameMoshtary = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "getSumBargashtyByccMoshtary" , "");
        }
        return nameMoshtary;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(BargashtyModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccMoshtary(int ccMoshtary)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(BargashtyModel.TableName(), BargashtyModel.COLUMN_ccMoshtary() + " = " + ccMoshtary, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BargashtyDAO" , "" , "deleteByccMoshtary" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(BargashtyModel bargashtyModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BargashtyModel.COLUMN_ccAfradErsalKonandeh() , bargashtyModel.getCcAfradErsalKonandeh());
        contentValues.put(BargashtyModel.COLUMN_ccDarkhastFaktor() , bargashtyModel.getCcDarkhastFaktor());
        contentValues.put(BargashtyModel.COLUMN_ccDariaftPardakht() , bargashtyModel.getCcDariaftPardakht());
        contentValues.put(BargashtyModel.COLUMN_ccMarkazAnbar() , bargashtyModel.getCcMarkazAnbar());
        contentValues.put(BargashtyModel.COLUMN_ccMarkazForosh() , bargashtyModel.getCcMarkazForosh());
        contentValues.put(BargashtyModel.COLUMN_NameMarkazForosh() , bargashtyModel.getNameMarkazForosh());
        contentValues.put(BargashtyModel.COLUMN_ccSazmanForosh() , bargashtyModel.getCcSazmanForosh());
        contentValues.put(BargashtyModel.COLUMN_NameSazmanForosh() , bargashtyModel.getNameSazmanForosh());
        contentValues.put(BargashtyModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , bargashtyModel.getCcMarkazSazmanForoshSakhtarForosh());
        contentValues.put(BargashtyModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh() , bargashtyModel.getNameMarkazSazmanForoshSakhtarForosh());
        contentValues.put(BargashtyModel.COLUMN_ccForoshandeh() , bargashtyModel.getCcForoshandeh());
        contentValues.put(BargashtyModel.COLUMN_ccMoshtary() , bargashtyModel.getCcMoshtary());
        contentValues.put(BargashtyModel.COLUMN_NameMoshtary() , bargashtyModel.getNameMoshtary());
        contentValues.put(BargashtyModel.COLUMN_CodeMoshtary() , bargashtyModel.getCodeMoshtary());
        contentValues.put(BargashtyModel.COLUMN_TarikhSanad() , bargashtyModel.getTarikhSanad());
        contentValues.put(BargashtyModel.COLUMN_ShomarehSanad() , bargashtyModel.getShomarehSanad());
        contentValues.put(BargashtyModel.COLUMN_Mablagh() , bargashtyModel.getMablagh());
        contentValues.put(BargashtyModel.COLUMN_MablaghMandeh() , bargashtyModel.getMablaghMandeh());
        contentValues.put(BargashtyModel.COLUMN_TarikhSanadWithSlash() , bargashtyModel.getTarikhSanadWithSlash());
        contentValues.put(BargashtyModel.COLUMN_ZamaneSabt() , bargashtyModel.getZamaneSabt());
        contentValues.put(BargashtyModel.COLUMN_ZamaneSabtWithSlash() , bargashtyModel.getZamaneSabtWithSlash());
        contentValues.put(BargashtyModel.COLUMN_ccNoeMoshtary() , bargashtyModel.getCcNoeMoshtary());
        contentValues.put(BargashtyModel.COLUMN_ccDarajeh() , bargashtyModel.getCcDarajeh());

        return contentValues;
    }


    private ArrayList<BargashtyModel> cursorToModel(Cursor cursor)
    {
        ArrayList<BargashtyModel> bargashtyModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            BargashtyModel bargashtyModel = new BargashtyModel();

            bargashtyModel.setCcAfradErsalKonandeh(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccAfradErsalKonandeh())));
            bargashtyModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(BargashtyModel.COLUMN_ccDarkhastFaktor())));
            bargashtyModel.setCcDariaftPardakht(cursor.getLong(cursor.getColumnIndex(BargashtyModel.COLUMN_ccDariaftPardakht())));
            bargashtyModel.setCcMarkazAnbar(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccMarkazAnbar())));
            bargashtyModel.setCcMarkazForosh(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccMarkazForosh())));
            bargashtyModel.setNameMarkazForosh(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_NameMarkazForosh())));
            bargashtyModel.setCcSazmanForosh(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccSazmanForosh())));
            bargashtyModel.setNameSazmanForosh(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_NameSazmanForosh())));
            bargashtyModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));
            bargashtyModel.setNameMarkazSazmanForoshSakhtarForosh(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_NameMarkazSazmanForoshSakhtarForosh())));
            bargashtyModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccForoshandeh())));
            bargashtyModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccMoshtary())));
            bargashtyModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_NameMoshtary())));
            bargashtyModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_CodeMoshtary())));
            bargashtyModel.setTarikhSanad(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_TarikhSanad())));
            bargashtyModel.setShomarehSanad(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_ShomarehSanad())));
            bargashtyModel.setMablagh(cursor.getDouble(cursor.getColumnIndex(BargashtyModel.COLUMN_Mablagh())));
            bargashtyModel.setMablaghMandeh(cursor.getDouble(cursor.getColumnIndex(BargashtyModel.COLUMN_MablaghMandeh())));
            bargashtyModel.setTarikhSanadWithSlash(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_TarikhSanadWithSlash())));
            bargashtyModel.setZamaneSabt(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_ZamaneSabt())));
            bargashtyModel.setZamaneSabtWithSlash(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_ZamaneSabtWithSlash())));
            bargashtyModel.setCcNoeMoshtary(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccNoeMoshtary())));
            bargashtyModel.setCcDarajeh(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccDarajeh())));

            bargashtyModels.add(bargashtyModel);
            cursor.moveToNext();
        }
        return bargashtyModels;
    }


    private ArrayList<BargashtyModel> cursorToModelWithSum(Cursor cursor)
    {
        ArrayList<BargashtyModel> bargashtyModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            BargashtyModel bargashtyModel = new BargashtyModel();


            bargashtyModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(BargashtyModel.COLUMN_ccMoshtary())));
            bargashtyModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_NameMoshtary())));
            bargashtyModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(BargashtyModel.COLUMN_CodeMoshtary())));
            bargashtyModel.setTarikhSanad(cursor.getString(cursor.getColumnIndex("TarikhSarResidCheck")));
            bargashtyModel.setShomarehSanad(cursor.getString(cursor.getColumnIndex("ShomarehCheck")));
            bargashtyModel.setMablagh(cursor.getDouble(cursor.getColumnIndex("MablaghCheck")));
            bargashtyModel.setMablaghMandeh(cursor.getDouble(cursor.getColumnIndex(BargashtyModel.COLUMN_MablaghMandeh())));

            bargashtyModels.add(bargashtyModel);
            cursor.moveToNext();
        }
        return bargashtyModels;
    }


    
}
