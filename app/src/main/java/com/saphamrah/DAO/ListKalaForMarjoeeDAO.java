package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetListKalaForMarjoeeResult;
import com.saphamrah.protos.GoodsListForReturnedGrpc;
import com.saphamrah.protos.GoodsListForReturnedReply;
import com.saphamrah.protos.GoodsListForReturnedReplyList;
import com.saphamrah.protos.GoodsListForReturnedRequest;

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

public class ListKalaForMarjoeeDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ListKalaForMarjoeeDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ListKalaForMarjoeeDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ListKalaForMarjoeeModel.COLUMN_ccKala(),
            ListKalaForMarjoeeModel.COLUMN_ccKalaCode(),
            ListKalaForMarjoeeModel.COLUMN_CodeKala(),
            ListKalaForMarjoeeModel.COLUMN_NameKala(),
            ListKalaForMarjoeeModel.COLUMN_ccTaminKonandeh(),
            ListKalaForMarjoeeModel.COLUMN_ShomarehBach(),
            ListKalaForMarjoeeModel.COLUMN_TarikhTolid(),
            ListKalaForMarjoeeModel.COLUMN_TarikhEngheza(),
            ListKalaForMarjoeeModel.COLUMN_Tedad(),
            ListKalaForMarjoeeModel.COLUMN_MablaghForosh(),
            ListKalaForMarjoeeModel.COLUMN_MablaghMasrafKonandeh(),
            ListKalaForMarjoeeModel.COLUMN_MablaghKharid(),
            ListKalaForMarjoeeModel.COLUMN_IsKalaZayeatTolid(),
            ListKalaForMarjoeeModel.COLUMN_Tarikh()
        };
    }

    public void fetchListKalaGrpc(final Context context, final String activityNameForLog, String ccForoshandeh, String ccMoshtary, final RetrofitResponse retrofitResponse)
    {
        try {


            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            serverIpModel.setPort("5000");


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ListKalaForMarjoeeDAO.class.getSimpleName(), activityNameForLog, "fetchListKalaGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                GoodsListForReturnedGrpc.GoodsListForReturnedBlockingStub goodsListForReturnedBlockingStub = GoodsListForReturnedGrpc.newBlockingStub(managedChannel);
                GoodsListForReturnedRequest goodsListForReturnedRequest = GoodsListForReturnedRequest.newBuilder().setCustomerID(ccMoshtary).setSellerID(ccForoshandeh).build();
                Callable<GoodsListForReturnedReplyList> goodsListForReturnedReplyListCallable = () -> goodsListForReturnedBlockingStub.getGoodsListForReturned(goodsListForReturnedRequest);
                RxAsync.makeObservable(goodsListForReturnedReplyListCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(goodsListForReturnedReplyList -> {
                            ArrayList<ListKalaForMarjoeeModel> models = new ArrayList<>();
                            for (GoodsListForReturnedReply reply  : goodsListForReturnedReplyList.getGoodsListForReturnedsList()) {
                                ListKalaForMarjoeeModel model = new ListKalaForMarjoeeModel();
                                model.setCcKala(reply.getGoodsID());
                                model.setCcKalaCode(reply.getGoodsCodeID());
                                model.setCodeKala(reply.getGoodsCode());
                                model.setNameKala(reply.getGoodsName());
                                model.setCcTaminKonandeh(reply.getProviderID());
                                model.setShomarehBach(reply.getBatchNumber());
                                model.setTarikhTolid(reply.getProductionDate());
                                model.setTarikhEngheza(reply.getExpirationDate());
                                model.setTedad(reply.getQuantity());
                                model.setMablaghForosh(reply.getSellPrice());
                                model.setMablaghMasrafKonandeh(reply.getConsumerPrice());

                                models.add(model);
                            }

                            return models;

                        }).subscribe(new Observer<ArrayList<ListKalaForMarjoeeModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<ListKalaForMarjoeeModel> models) {
                        retrofitResponse.onSuccess(models);
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
        } catch (
                Exception exception) {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), ListKalaForMarjoeeDAO.class.getSimpleName(), activityNameForLog, "fetchListKalaGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }


    public void fetchListKala(final Context context, final String activityNameForLog, String ccForoshandeh, String ccMoshtary, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListKalaForMarjoeeDAO", activityNameForLog, "fetchListKala", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Log.d("GetProgram","MarjoeeKala-ccForoshandehs:" + ccForoshandeh);
            Call<GetListKalaForMarjoeeResult> call = apiServiceGet.getListKalaForMarjoee(ccForoshandeh, ccMoshtary);
            call.enqueue(new Callback<GetListKalaForMarjoeeResult>() {
                @Override
                public void onResponse(Call<GetListKalaForMarjoeeResult> call, Response<GetListKalaForMarjoeeResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "ListKalaForMarjoeeDAO", "", "fetchListKala", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetListKalaForMarjoeeResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "ListKalaForMarjoeeDAO", activityNameForLog, "fetchListKala", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), "ListKalaForMarjoeeDAO", activityNameForLog, "fetchListKala", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListKalaForMarjoeeDAO", activityNameForLog, "fetchListKala", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ListKalaForMarjoeeDAO", activityNameForLog, "fetchListKala", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetListKalaForMarjoeeResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), "ListKalaForMarjoeeDAO", activityNameForLog, "fetchListKala", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
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

    public boolean insertGroup(ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ListKalaForMarjoeeModel listKalaForMarjoeeModel : listKalaForMarjoeeModels)
            {
                ContentValues contentValues = modelToContentvalue(listKalaForMarjoeeModel);
                db.insertOrThrow(ListKalaForMarjoeeModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ListKalaForMarjoeeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListKalaForMarjoeeDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ListKalaForMarjoeeModel> getAll()
    {
        ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ListKalaForMarjoeeModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    listKalaForMarjoeeModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ListKalaForMarjoeeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListKalaForMarjoeeDAO" , "" , "getAll" , "");
        }
        return listKalaForMarjoeeModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ListKalaForMarjoeeModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ListKalaForMarjoeeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListKalaForMarjoeeDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ListKalaForMarjoeeModel listKalaForMarjoeeModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ListKalaForMarjoeeModel.COLUMN_ccKala() , listKalaForMarjoeeModel.getCcKala());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_ccKalaCode() , listKalaForMarjoeeModel.getCcKalaCode());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_CodeKala() , listKalaForMarjoeeModel.getCodeKala());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_NameKala() , listKalaForMarjoeeModel.getNameKala());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_ccTaminKonandeh() , listKalaForMarjoeeModel.getCcTaminKonandeh());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_ShomarehBach() , listKalaForMarjoeeModel.getShomarehBach());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_TarikhTolid() , listKalaForMarjoeeModel.getTarikhTolid());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_TarikhEngheza() , listKalaForMarjoeeModel.getTarikhEngheza());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_Tedad() , listKalaForMarjoeeModel.getTedad());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_MablaghForosh() , listKalaForMarjoeeModel.getMablaghForosh());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_MablaghMasrafKonandeh() , listKalaForMarjoeeModel.getMablaghMasrafKonandeh());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_MablaghKharid() , listKalaForMarjoeeModel.getMablaghKharid());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_IsKalaZayeatTolid() , listKalaForMarjoeeModel.getIsKalaZayeatTolid());
        contentValues.put(ListKalaForMarjoeeModel.COLUMN_Tarikh() , listKalaForMarjoeeModel.getTarikh());

        return contentValues;
    }

    private ArrayList<ListKalaForMarjoeeModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ListKalaForMarjoeeModel listKalaForMarjoeeModel = new ListKalaForMarjoeeModel();

            listKalaForMarjoeeModel.setCcKala(cursor.getInt(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_ccKala())));
            listKalaForMarjoeeModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_ccKalaCode())));
            listKalaForMarjoeeModel.setCodeKala(cursor.getString(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_CodeKala())));
            listKalaForMarjoeeModel.setNameKala(cursor.getString(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_NameKala())));
            listKalaForMarjoeeModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_ccTaminKonandeh())));
            listKalaForMarjoeeModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_ShomarehBach())));
            listKalaForMarjoeeModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_TarikhTolid())));
            listKalaForMarjoeeModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_TarikhEngheza())));
            listKalaForMarjoeeModel.setTedad(cursor.getInt(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_Tedad())));
            listKalaForMarjoeeModel.setMablaghForosh(cursor.getDouble(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_MablaghForosh())));
            listKalaForMarjoeeModel.setMablaghMasrafKonandeh(cursor.getDouble(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_MablaghMasrafKonandeh())));
            listKalaForMarjoeeModel.setMablaghKharid(cursor.getDouble(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_MablaghKharid())));
            listKalaForMarjoeeModel.setIsKalaZayeatTolid(cursor.getInt(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_IsKalaZayeatTolid())));
            listKalaForMarjoeeModel.setTarikh(cursor.getString(cursor.getColumnIndex(ListKalaForMarjoeeModel.COLUMN_Tarikh())));

            listKalaForMarjoeeModels.add(listKalaForMarjoeeModel);
            cursor.moveToNext();
        }
        return listKalaForMarjoeeModels;
    }


}
