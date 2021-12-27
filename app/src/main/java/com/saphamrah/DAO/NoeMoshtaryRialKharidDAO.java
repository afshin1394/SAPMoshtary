package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.NoeMoshtaryRialKharidModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllNoeMoshtaryRialKharidResualt;
import com.saphamrah.protos.PurchaseRialCustomerTypeGrpc;
import com.saphamrah.protos.PurchaseRialCustomerTypeReply;
import com.saphamrah.protos.PurchaseRialCustomerTypeReplyList;
import com.saphamrah.protos.PurchaseRialCustomerTypeRequest;

import java.util.ArrayList;
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

public class NoeMoshtaryRialKharidDAO
{

    private DBHelper dbHelper;
    private Context context;


    public NoeMoshtaryRialKharidDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "NoeMoshtaryRialKharidDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            NoeMoshtaryRialKharidModel.COLUMN_ccNoeMoshtaryRialKharid(),
            NoeMoshtaryRialKharidModel.COLUMN_ccGoroh(),
            NoeMoshtaryRialKharidModel.COLUMN_HadeAghalMablaghKharid(),
            NoeMoshtaryRialKharidModel.COLUMN_HadeAghalMablaghKharejAzMasir(),
            NoeMoshtaryRialKharidModel.COLUMN_HadeAghalTedadKharid(),
            NoeMoshtaryRialKharidModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh(),
            NoeMoshtaryRialKharidModel.COLUMN_Darajeh()
        };
    }

    public void fetchNoeMoshtaryRialKharidGrpc(final Context context, final String activityNameForLog, String ccMarkazSazmanForoshSakhtarForoshs, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, PurchaseRialCustomerTypeGrpc.class.getSimpleName(), activityNameForLog, "fetchNoeMoshtaryRialKharidGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                PurchaseRialCustomerTypeGrpc.PurchaseRialCustomerTypeBlockingStub purchaseRialCustomerTypeBlockingStub = PurchaseRialCustomerTypeGrpc.newBlockingStub(managedChannel);
                PurchaseRialCustomerTypeRequest purchaseRialCustomerTypeRequest = PurchaseRialCustomerTypeRequest.newBuilder().setSellStructureSellOrganizationCenterID(ccMarkazSazmanForoshSakhtarForoshs).build();
                Callable<PurchaseRialCustomerTypeReplyList> purchaseRialCustomerTypeReplyListCallable = () -> purchaseRialCustomerTypeBlockingStub.getPurchaseRialCustomerType(purchaseRialCustomerTypeRequest);
                RxAsync.makeObservable(purchaseRialCustomerTypeReplyListCallable)
                        .map(purchaseRialCustomerTypeReplyList ->  {
                            ArrayList<NoeMoshtaryRialKharidModel> models = new ArrayList<>();
                            for (PurchaseRialCustomerTypeReply reply : purchaseRialCustomerTypeReplyList.getPurchaseRialCustomerTypesList()) {
                                NoeMoshtaryRialKharidModel model = new NoeMoshtaryRialKharidModel();

                                model.setCcNoeMoshtaryRialKharid(reply.getPurchaseRialCustomerType());
                                model.setCcGoroh(reply.getGroupID());
                                model.setDarajeh(reply.getDegree());
                                model.setCcMarkazSazmanForoshSakhtarForosh(reply.getSellStructureSellOrganizationCenterID());
                                model.setHadeAghalMablaghKharid(reply.getMinPurchasePrice());
                                model.setHadeAghalTedadKharid(reply.getMinPurchaseCount());
                                model.setHadeAghalMablaghKharejAzMasir(reply.getMinOutOfRoutePurchasePrice());


                                models.add(model);

                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<NoeMoshtaryRialKharidModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<NoeMoshtaryRialKharidModel> noeHesabModels) {
                                retrofitResponse.onSuccess(noeHesabModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), e.getMessage());
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), PurchaseRialCustomerTypeGrpc.class.getSimpleName(), activityNameForLog, "fetchNoeMoshtaryRialKharidGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }


    public void fetchNoeMoshtaryRialKharid(final Context context, final String activityNameForLog, String ccMarkazSazmanForoshSakhtarForoshs, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeHesabDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllNoeMoshtaryRialKharidResualt> call = apiServiceGet.getAllNoeMoshtaryrialKharid(ccMarkazSazmanForoshSakhtarForoshs);
            call.enqueue(new Callback<GetAllNoeMoshtaryRialKharidResualt>() {
                @Override
                public void onResponse(Call<GetAllNoeMoshtaryRialKharidResualt> call, Response<GetAllNoeMoshtaryRialKharidResualt> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, NoeMoshtaryRialKharidDAO.class.getSimpleName(), "", "fetchNoeHesab", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllNoeMoshtaryRialKharidResualt result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), NoeMoshtaryRialKharidDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), NoeMoshtaryRialKharidDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, NoeMoshtaryRialKharidDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), NoeMoshtaryRialKharidDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllNoeMoshtaryRialKharidResualt> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), NoeMoshtaryRialKharidDAO.class.getSimpleName(), activityNameForLog, "fetchNoeHesab", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (NoeMoshtaryRialKharidModel noeMoshtaryRialKharidModel : noeMoshtaryRialKharidModels)
            {
                ContentValues contentValues = modelToContentvalue(noeMoshtaryRialKharidModel);
                db.insertOrThrow(NoeMoshtaryRialKharidModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , NoeMoshtaryRialKharidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeMoshtaryRialKharidDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<NoeMoshtaryRialKharidModel> getAll()
    {
        ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(NoeMoshtaryRialKharidModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    noeMoshtaryRialKharidModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeMoshtaryRialKharidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeMoshtaryRialKharidDAO" , "" , "getAll" , "");
        }
        return noeMoshtaryRialKharidModels;
    }

    public long getMablaghByccMoshtary(int ccGoroh , int ccDarajeh)
    {
        long hadeAghalMablaghKharid = 0;
        NoeMoshtaryRialKharidModel noeMoshtaryRialKharidModel = new NoeMoshtaryRialKharidModel();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try
        {
            String query = " SELECT * FROM NoeMoshtaryRialKharid WHERE ccGoroh = " + ccGoroh + " AND "
                    + "       Darajeh = " + ccDarajeh ;
            Cursor cursor= db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    hadeAghalMablaghKharid = (long)cursorToModel(cursor).get(0).getHadeAghalMablaghKharid();
                }
                cursor.close();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeMoshtaryRialKharidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeMoshtaryRialKharidDAO" , "" , "getMablaghByccMoshtary" , "");
        }
        Log.d("NoeMoshtaryRialKhari","hadeAghalMablaghKharid : " + hadeAghalMablaghKharid);
        return hadeAghalMablaghKharid;
    }

    public int getTedadByccMoshtary(int ccGoroh , int ccDarajeh)
    {
        int hadeAghalTedadKharid = 0;
        NoeMoshtaryRialKharidModel noeMoshtaryRialKharidModel = new NoeMoshtaryRialKharidModel();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try
        {
            String query = " SELECT * FROM NoeMoshtaryRialKharid WHERE ccGoroh = " + ccGoroh + " AND "
                    + "       Darajeh = " + ccDarajeh ;
            Cursor cursor= db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    hadeAghalTedadKharid = (int)cursorToModel(cursor).get(0).getHadeAghalTedadKharid();
                }
                cursor.close();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , NoeMoshtaryRialKharidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeMoshtaryRialKharidDAO" , "" , "getTedadByccMoshtary" , "");
        }
        Log.d("NoeMoshtaryRialKhari","hadeAghalTedadKharid : " + hadeAghalTedadKharid);
        return hadeAghalTedadKharid;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(NoeMoshtaryRialKharidModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , NoeMoshtaryRialKharidModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "NoeMoshtaryRialKharidDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(NoeMoshtaryRialKharidModel noeMoshtaryRialKharidModel)
    {
        ContentValues contentValues = new ContentValues();

        if (noeMoshtaryRialKharidModel.getCcNoeMoshtaryRialKharid() > 0)
        {
            contentValues.put(NoeMoshtaryRialKharidModel.COLUMN_ccNoeMoshtaryRialKharid() , noeMoshtaryRialKharidModel.getCcNoeMoshtaryRialKharid());
        }
        contentValues.put(NoeMoshtaryRialKharidModel.COLUMN_ccGoroh() , noeMoshtaryRialKharidModel.getCcGoroh());
        contentValues.put(NoeMoshtaryRialKharidModel.COLUMN_HadeAghalMablaghKharid() , noeMoshtaryRialKharidModel.getHadeAghalMablaghKharid());
        contentValues.put(NoeMoshtaryRialKharidModel.COLUMN_HadeAghalMablaghKharejAzMasir() , noeMoshtaryRialKharidModel.getHadeAghalMablaghKharejAzMasir());
        contentValues.put(NoeMoshtaryRialKharidModel.COLUMN_HadeAghalTedadKharid() , noeMoshtaryRialKharidModel.getHadeAghalTedadKharid());
        contentValues.put(NoeMoshtaryRialKharidModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , noeMoshtaryRialKharidModel.getCcMarkazSazmanForoshSakhtarForosh());
        contentValues.put(NoeMoshtaryRialKharidModel.COLUMN_Darajeh() , noeMoshtaryRialKharidModel.getDarajeh());

        return contentValues;
    }


    private ArrayList<NoeMoshtaryRialKharidModel> cursorToModel(Cursor cursor)
    {
        ArrayList<NoeMoshtaryRialKharidModel> noeMoshtaryRialKharidModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            NoeMoshtaryRialKharidModel noeMoshtaryRialKharidModel = new NoeMoshtaryRialKharidModel();

            noeMoshtaryRialKharidModel.setCcNoeMoshtaryRialKharid(cursor.getInt(cursor.getColumnIndex(NoeMoshtaryRialKharidModel.COLUMN_ccNoeMoshtaryRialKharid())));
            noeMoshtaryRialKharidModel.setCcGoroh(cursor.getInt(cursor.getColumnIndex(NoeMoshtaryRialKharidModel.COLUMN_ccGoroh())));
            noeMoshtaryRialKharidModel.setHadeAghalMablaghKharid(cursor.getFloat(cursor.getColumnIndex(NoeMoshtaryRialKharidModel.COLUMN_HadeAghalMablaghKharid())));
            noeMoshtaryRialKharidModel.setHadeAghalMablaghKharejAzMasir(cursor.getFloat(cursor.getColumnIndex(NoeMoshtaryRialKharidModel.COLUMN_HadeAghalMablaghKharejAzMasir())));
            noeMoshtaryRialKharidModel.setHadeAghalTedadKharid(cursor.getInt(cursor.getColumnIndex(NoeMoshtaryRialKharidModel.COLUMN_HadeAghalTedadKharid())));
            noeMoshtaryRialKharidModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(NoeMoshtaryRialKharidModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));
            noeMoshtaryRialKharidModel.setDarajeh(cursor.getInt(cursor.getColumnIndex(NoeMoshtaryRialKharidModel.COLUMN_Darajeh())));

            noeMoshtaryRialKharidModels.add(noeMoshtaryRialKharidModel);
            cursor.moveToNext();
        }
        return noeMoshtaryRialKharidModels;
    }



}
