package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetMojodyAnbarResult;
import com.saphamrah.protos.StatisticGoodsGrpc;
import com.saphamrah.protos.StatisticGoodsReply;
import com.saphamrah.protos.StatisticGoodsReplyList;
import com.saphamrah.protos.StatisticGoodsRequest;
import com.saphamrah.protos.StorInventoryGrpc;
import com.saphamrah.protos.StorInventoryReply;
import com.saphamrah.protos.StorInventoryReplyList;
import com.saphamrah.protos.StorInventoryRequest;

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

public class KalaDAO
{

    private DBHelper dbHelper;
    private Context context;

    public KalaDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            KalaModel.COLUMN_Radif(),
            KalaModel.COLUMN_ccKalaCode(),
            KalaModel.COLUMN_CodeKala(),
            KalaModel.COLUMN_NameKala(),
            KalaModel.COLUMN_TedadMojodyGhabelForosh(),
            KalaModel.COLUMN_ccTaminKonandeh(),
            KalaModel.COLUMN_TedadDarKarton(),
            KalaModel.COLUMN_TedadDarBasteh(),
            KalaModel.COLUMN_Adad(),
            KalaModel.COLUMN_CodeSort(),
            KalaModel.COLUMN_MashmolMaliatAvarez(),
            KalaModel.COLUMN_MablaghForosh(),
            KalaModel.COLUMN_MablaghMasrafKonandeh(),
            KalaModel.COLUMN_ccGorohKala(),
            KalaModel.COLUMN_ccBrand(),
            KalaModel.COLUMN_MablaghKharid(),
            KalaModel.COLUMN_Tol(),
            KalaModel.COLUMN_Arz(),
            KalaModel.COLUMN_Ertefa(),
            KalaModel.COLUMN_ccVahedSize(),
            KalaModel.COLUMN_VaznKhales(),
            KalaModel.COLUMN_VaznNaKhales(),
            KalaModel.COLUMN_VaznKarton(),
            KalaModel.COLUMN_ccVahedVazn(),
            KalaModel.COLUMN_BarCode(),
            KalaModel.COLUMN_TarikhTolid(),
            KalaModel.COLUMN_NameVahedVazn(),
            KalaModel.COLUMN_NameBrand(),
            KalaModel.COLUMN_NameVahedSize(),
            KalaModel.COLUMN_ccVahedShomaresh(),
            KalaModel.COLUMN_NameVahedShomaresh(),
            KalaModel.COLUMN_TarikhEngheza(),
            KalaModel.COLUMN_ShomarehBach(),
            KalaModel.COLUMN_GheymatForoshAsli(),
            KalaModel.COLUMN_GheymatMasrafKonandehAsli(),
            KalaModel.COLUMN_ِDarsadMaliat(),
            KalaModel.COLUMN_DarsadAvarez(),
            KalaModel.COLUMN_ZaribTakhfifArzeshAfzodeh()

        };
    }
    public void fetchMojodyAnbarGrpc(final Context context, final String activityNameForLog, final String ccAfrad, final String ccMarkazSazmanForoshSakhtarForosh, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargarGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                StorInventoryGrpc.StorInventoryBlockingStub storInventoryBlockingStub = StorInventoryGrpc.newBlockingStub(managedChannel);
                StorInventoryRequest storInventoryRequest = StorInventoryRequest.newBuilder().setPersonID(ccAfrad).setSellStructureSellOrganizationCenterID(ccMarkazSazmanForoshSakhtarForosh).build();
                Callable<StorInventoryReplyList> storInventoryReplyListCallable = () -> storInventoryBlockingStub.getStorInventory(storInventoryRequest);
                RxAsync.makeObservable(storInventoryReplyListCallable)
                        .map(storInventoryReplyList -> {
                            ArrayList<KalaModel> kalaModels = new ArrayList<>();
                            for (StorInventoryReply reply : storInventoryReplyList.getStorInventorysList()) {
                                KalaModel kalaModel = new KalaModel();
                                kalaModel.setRadif(reply.getRow());
                                kalaModel.setCodeKala(reply.getGoodCode());
                                kalaModel.setCcKalaCode(reply.getGoodCodeID());
                                kalaModel.setCcGorohKala(reply.getGoodGroupID());
                                kalaModel.setArz(reply.getWidth());
                                kalaModel.setTol(reply.getLength());
                                kalaModel.setErtefa(reply.getHeight());
                                kalaModel.setTedadDarBasteh(reply.getQuantityInPackage());
                                kalaModel.setTedadDarKarton(reply.getQuantityInBox());
                                kalaModel.setCcBrand(reply.getBrandID());
                                kalaModel.setNameBrand(reply.getBrandName());
                                kalaModel.setNameKala(reply.getGoodName());
                                kalaModel.setVaznKarton(reply.getBoxWeight());
                                kalaModel.setVaznKhales(reply.getNetWeight());
                                kalaModel.setVaznNaKhales(reply.getGrossWeight());
                                kalaModel.setCcVahedSize(reply.getSizeUnitID());
                                kalaModel.setCcVahedVazn(reply.getUnitWeightID());
                                kalaModel.setTedadMojodyGhabelForosh(reply.getConsumableIncentoryQuantity());
                                kalaModel.setCcTaminKonandeh(reply.getProviderID());
                                kalaModel.setAdad(reply.getNumber());
                                kalaModel.setCcVahedShomaresh(reply.getCountingUnitID());
                                kalaModel.setCodeSort(reply.getSortCode());
                                kalaModel.setMashmolMaliatAvarez(reply.getTaxable());
                                kalaModel.setMablaghForosh(reply.getSellPrice());
                                kalaModel.setLastMablaghForosh(reply.getLastSellPrice());
                                kalaModel.setMablaghMasrafKonandeh(reply.getConsumerPrice());
                                kalaModel.setCcBrand(reply.getBrandID());
                                kalaModel.setMablaghKharid(reply.getBuyPrice());
                                kalaModel.setBarCode(reply.getBarcode());
                                kalaModel.setTarikhTolid(reply.getProductionDate());
                                kalaModel.setTarikhEngheza(reply.getExpirationDate());
                                kalaModel.setNameVahedVazn(reply.getUnitWeightName());
                                kalaModel.setNameVahedSize(reply.getSizeUnitName());
                                kalaModel.setNameVahedShomaresh(reply.getCountingUnitName());
                                kalaModel.setShomarehBach(reply.getBatchNumber());
                                kalaModel.setGheymatForoshAsli((int) reply.getOriginalSellPrice());
                                kalaModel.setGheymatMasrafKonandehAsli(reply.getOrginalConsumerPrice());
                                kalaModel.setZaribTakhfifArzeshAfzodeh(reply.getBonusBudgetCoefficient());

                                kalaModels.add(kalaModel);



                            }
                            return kalaModels;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<KalaModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<KalaModel> kalaModels) {
                                retrofitResponse.onSuccess(kalaModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargarGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }


    public void fetchKalaAmargarGrpc(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargarGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                StatisticGoodsGrpc.StatisticGoodsBlockingStub statisticGoodsBlockingStub = StatisticGoodsGrpc.newBlockingStub(managedChannel);
                StatisticGoodsRequest statisticGoodsRequest = StatisticGoodsRequest.newBuilder().build();
                Callable<StatisticGoodsReplyList> getStatisticGoodsCallable = () -> statisticGoodsBlockingStub.getStatisticGoods(statisticGoodsRequest);
                RxAsync.makeObservable(getStatisticGoodsCallable)
                        .map(statisticGoodsReplyList -> {
                            ArrayList<KalaModel> kalaModels = new ArrayList<>();
                            for (StatisticGoodsReply statisticGoodsReply : statisticGoodsReplyList.getStatisticGoodsList()) {
                                KalaModel kalaModel = new KalaModel();
                                kalaModel.setCcKalaCode(statisticGoodsReply.getGoodsCodeID());
                                kalaModel.setCcGorohKala(statisticGoodsReply.getGoodsGroupID());
                                kalaModel.setArz(statisticGoodsReply.getWidth());
                                kalaModel.setTol(statisticGoodsReply.getLength());
                                kalaModel.setErtefa(statisticGoodsReply.getHeigth());
                                kalaModel.setTedadDarBasteh(statisticGoodsReply.getCount2());
                                kalaModel.setTedadDarKarton(statisticGoodsReply.getCount3());
                                kalaModel.setCcBrand(statisticGoodsReply.getBrandID());
                                kalaModel.setNameBrand(statisticGoodsReply.getBrandName());
                                kalaModel.setNameKala(statisticGoodsReply.getGoodsName());
                                kalaModel.setVaznKarton(statisticGoodsReply.getBoxWeigth());
                                kalaModel.setVaznKhales(statisticGoodsReply.getPureWeight());
                                kalaModel.setVaznNaKhales(statisticGoodsReply.getNonPureWeight());
                                kalaModel.setCcVahedSize(statisticGoodsReply.getSizeUnitID());
                                kalaModel.setCcVahedVazn(statisticGoodsReply.getWeightUnitID());
                                kalaModels.add(kalaModel);
                            }
                            return kalaModels;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<KalaModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<KalaModel> kalaModels) {
                                retrofitResponse.onSuccess(kalaModels);
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargarGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }




    }


    public void fetchMojodyAnbar(final Context context, final String activityNameForLog, final String ccAfrad, final String ccMarkazSazmanForoshSakhtarForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaDAO.class.getSimpleName(), activityNameForLog, "fetchMojodyAnbar", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMojodyAnbarResult> call = apiServiceGet.getMojodyAnbar(ccAfrad,ccMarkazSazmanForoshSakhtarForosh);
            call.enqueue(new Callback<GetMojodyAnbarResult>() {
                @Override
                public void onResponse(Call<GetMojodyAnbarResult> call, Response<GetMojodyAnbarResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaDAO.class.getSimpleName(), "", "fetchMojodyAnbar", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMojodyAnbarResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchMojodyAnbar", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchMojodyAnbar", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaDAO.class.getSimpleName(), activityNameForLog, "fetchMojodyAnbar", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchMojodyAnbar", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMojodyAnbarResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchMojodyAnbar", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }



    public void fetchKalaAmargar(final Context context, final String activityNameForLog, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargar", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMojodyAnbarResult> call = apiServiceGet.getAllKalaAmargar();
            call.enqueue(new Callback<GetMojodyAnbarResult>() {
                @Override
                public void onResponse(Call<GetMojodyAnbarResult> call, Response<GetMojodyAnbarResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaDAO.class.getSimpleName(), "", "fetchKalaAmargar", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMojodyAnbarResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargar", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargar", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargar", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargar", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMojodyAnbarResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), KalaDAO.class.getSimpleName(), activityNameForLog, "fetchKalaAmargar", "onFailure");
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

    public boolean insertGroup(ArrayList<KalaModel> kalaModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (KalaModel kalaModel : kalaModels)
            {
                ContentValues contentValues = modelToContentvalue(kalaModel);
                db.insertOrThrow(KalaModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<KalaModel> getAll()
    {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM Kala";
            Cursor cursor = db.rawQuery(query, null);
//            Cursor cursor = db.query(KalaModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    kalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getAll" , "");
        }
        return kalaModels;
    }

	
	public ArrayList<KalaModel> getAllForMojodiGiri(int ccMoshtary)
    {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();
        try
        {



            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select distinct(k.ccKalaCode),k.nameKala,k.ccGorohKala from Kala k  \n" +
                    " where k.ccKalaCode IN (SELECT  ccKalaCode From DarkhastFaktorKalaPishnahadi  \n" +
                    "WHERE  ccMoshtary =" + ccMoshtary + ")" +
                    " AND k.ccKalaCode IN (SELECT ccKalaCode FROM KalaZaribForosh WHERE ZaribForosh > 0 ) \n" +
                    " AND k.ccKalaCode Not IN(select ccKalaCode FROM KalaGoroh WHERE ccGoroh = 636) \n" +
                    " AND ccKalaCode IN (SELECT ccKalaCode FROM Rpt_HadafForoshTedady WHERE TedadHadafMah > 0 ) \n" +
                    "UNION ALL \n" +
                    "select distinct(k.ccKalaCode),k.nameKala,k.ccGorohKala from Kala k  \n" +
                    " where k.ccKalaCode NOT IN (SELECT  ccKalaCode From DarkhastFaktorKalaPishnahadi  \n" +
                    "WHERE  ccMoshtary =" + ccMoshtary + ")" +
                    " AND k.ccKalaCode IN (SELECT ccKalaCode FROM KalaZaribForosh WHERE ZaribForosh > 0 ) \n" +
                    " AND k.ccKalaCode Not IN(select ccKalaCode FROM KalaGoroh WHERE ccGoroh = 636) " ;
                    //" AND k.ccKalaCode Not IN(select ccKalaCode FROM KalaGoroh WHERE ccGoroh = 636) \n" +
                    //" AND ccKalaCode IN (SELECT ccKalaCode FROM Rpt_HadafForoshTedady WHERE TedadHadafMah > 0 ) \n" ;

            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaModels = cursorToModelForShow(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "KalaDAO" , "" , "getAll" , "");
        }
        return kalaModels;
    }
    public ArrayList<KalaModel> getKalaByMaxMojodyAll(ArrayList<Integer> ccKalaCodes) {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try
        {


            for (int i = 0; i < kalaModels.size(); i++) {
                int ccKalaCode = kalaModels.get(i).getCcKalaCode();

                String query = " SELECT * FROM  " + KalaModel.TableName() +
                        " WHERE " + KalaModel.COLUMN_ccKalaCode() + " = " + ccKalaCode +
                        " ORDER BY " + KalaModel.COLUMN_TedadMojodyGhabelForosh() + " DESC " +
                        " LIMIT 1";

                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        if (cursor.getCount() != 0) {
                            kalaModels.add(cursorToModel(cursor).get(0));
//                        kalaModel = cursorToModel(cursor).get(0);
                        }
                    }

                }
                cursor.close();
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getKalaByMaxMojody" , "");
        }
        return kalaModels;
    }

    public KalaModel getKalaByMaxMojody(int ccKalaCode)
    {
        KalaModel kalaModel = new KalaModel();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try
        {
            String query = " SELECT * FROM  " + KalaModel.TableName() +
                    " WHERE " + KalaModel.COLUMN_ccKalaCode() + " = " + ccKalaCode +
                    " ORDER BY " + KalaModel.COLUMN_TedadMojodyGhabelForosh() + " DESC " +
                    " LIMIT 1" ;
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    if (cursor.getCount()!= 0)
                    {
                        kalaModel = cursorToModel(cursor).get(0);
                    }
                }
                cursor.close();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getKalaByMaxMojody" , "");
        }
        return kalaModel;
    }


    public KalaModel getccKalaByKalaGoroh(String ccGoroh)
    {
        KalaModel kalaModel = new KalaModel();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try
        {
            String query = "select * from Kala where ccKalaCode in (select ccKalaCode from KalaGoroh where ccGoroh in (" + ccGoroh + "))";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    if (cursor.getCount()!= 0)
                    {
                        kalaModel = cursorToModel(cursor).get(0);
                    }
                }
                cursor.close();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getccKalaByKalaGoroh" , "");
        }
        return kalaModel;
    }

    public ArrayList<KalaModel> getKalasByccKalaCode(int ccKalaCode)
    {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaModel.TableName(), allColumns(), KalaModel.COLUMN_ccKalaCode() + " = " + ccKalaCode, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getKalasByccKalaCode" , "");
        }
        return kalaModels;
    }

    public ArrayList<KalaModel> getAllBySortOlaviatKala()
    {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();
        try
        {
            String query = " SELECT Kala.*, KalaOlaviat.Olaviat FROM Kala " +
                    " LEFT OUTER JOIN  KalaOlaviat ON Kala.ccKalaCode = KalaOlaviat.ccKalaCode" +
                    " Order BY KalaOlaviat.Olaviat ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getAllBySortOlaviatKala" , "");
        }

        return kalaModels;
    }

    public ArrayList<KalaModel> getAllByBrandAndGoroh(int ccBrand, int ccGorohKala)
    {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();
        try
        {
            //select * from kala where ccBrand = ? and ccGorohKala = ?
            String query = "select * from " + KalaModel.TableName() + " where " + KalaModel.COLUMN_ccBrand() + " = " + ccBrand + " and " + KalaModel.COLUMN_ccGorohKala() + " = " + ccGorohKala;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getAllByBrandAndGoroh" , "");
        }

        return kalaModels;
    }

    public KalaModel getByccKalaCode(int ccKalaCode)
    {
        KalaModel kalaModel = new KalaModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = " SELECT * FROM Kala "
                    + " WHERE ccKalaCode = "+ ccKalaCode

                    + " Limit 1";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getByccKalaCode" , "");
        }
        return kalaModel;
    }





    public int getBrandByccKalaCode(long ccDarkhastFaktor, int ccGoroh)
    {
        int ccBrand = 0;
        try
        {
            String query = " SELECT * FROM Kala "
                    + " WHERE ccKalaCode IN( SELECT ccKalaCode FROM DarkhastFaktorSatr "
                    + "                      Where ccDarkhastfaktor = " + ccDarkhastFaktor + " AND "
                    +"                             ccKalaCode IN( SELECT ccKalaCode From KalaGoroh "
                    + "                                           WHERE ccGoroh = " + ccGoroh +")  "
                    + " )"
                    + " Limit 1";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    ccBrand = cursorToModel(cursor).get(0).getCcBrand();
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getBrandByccKalaCode" , "");
        }
        return ccBrand;
    }

    public int getSumTedadMojodyKala(int ccKalaCode)
    {
        int tedad = 0;
        try
        {
            String query = "SELECT sum(TedadMojodyGhabelForosh) FROM Kala WHERE ccKalaCode = " + ccKalaCode ;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query ,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    tedad = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "getSumTedadMojodyKala" , "");
        }
        return tedad;
    }

    public boolean updateAllMojody()
    {
        String query = "update " + KalaModel.TableName() + " set " + KalaModel.COLUMN_TedadMojodyGhabelForosh() + " = 99999999 ";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BarkhordForoshandehBaMoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "updateAllMojody" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(KalaModel kalaModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(KalaModel.COLUMN_Radif() , kalaModel.getRadif());
        contentValues.put(KalaModel.COLUMN_ccKalaCode() , kalaModel.getCcKalaCode());
        contentValues.put(KalaModel.COLUMN_CodeKala() , kalaModel.getCodeKala());
        contentValues.put(KalaModel.COLUMN_NameKala() , kalaModel.getNameKala());
        contentValues.put(KalaModel.COLUMN_TedadMojodyGhabelForosh() , kalaModel.getTedadMojodyGhabelForosh());
        contentValues.put(KalaModel.COLUMN_ccTaminKonandeh() , kalaModel.getCcTaminKonandeh());
        contentValues.put(KalaModel.COLUMN_TedadDarKarton() , kalaModel.getTedadDarKarton());
        contentValues.put(KalaModel.COLUMN_TedadDarBasteh() , kalaModel.getTedadDarBasteh());
        contentValues.put(KalaModel.COLUMN_Adad() , kalaModel.getAdad());
        contentValues.put(KalaModel.COLUMN_CodeSort() , kalaModel.getCodeSort());
        contentValues.put(KalaModel.COLUMN_MashmolMaliatAvarez() , kalaModel.getMashmolMaliatAvarez());
        contentValues.put(KalaModel.COLUMN_MablaghForosh() , kalaModel.getMablaghForosh());
        contentValues.put(KalaModel.COLUMN_LastMablaghForosh() , kalaModel.getLastMablaghForosh());
        contentValues.put(KalaModel.COLUMN_MablaghMasrafKonandeh() , kalaModel.getMablaghMasrafKonandeh());
        contentValues.put(KalaModel.COLUMN_ccGorohKala() , kalaModel.getCcGorohKala());
        contentValues.put(KalaModel.COLUMN_ccBrand() , kalaModel.getCcBrand());
        contentValues.put(KalaModel.COLUMN_MablaghKharid() , kalaModel.getMablaghKharid());
        contentValues.put(KalaModel.COLUMN_Tol() , kalaModel.getTol());
        contentValues.put(KalaModel.COLUMN_Arz() , kalaModel.getArz());
        contentValues.put(KalaModel.COLUMN_Ertefa() , kalaModel.getErtefa());
        contentValues.put(KalaModel.COLUMN_ccVahedSize() , kalaModel.getCcVahedSize());
        contentValues.put(KalaModel.COLUMN_VaznKhales() , kalaModel.getVaznKhales());
        contentValues.put(KalaModel.COLUMN_VaznNaKhales() , kalaModel.getVaznNaKhales());
        contentValues.put(KalaModel.COLUMN_VaznKarton() , kalaModel.getVaznKarton());
        contentValues.put(KalaModel.COLUMN_ccVahedVazn() , kalaModel.getCcVahedVazn());
        contentValues.put(KalaModel.COLUMN_BarCode() , kalaModel.getBarCode());
        contentValues.put(KalaModel.COLUMN_TarikhTolid() , kalaModel.getTarikhTolid());
        contentValues.put(KalaModel.COLUMN_NameVahedVazn() , kalaModel.getNameVahedVazn());
        contentValues.put(KalaModel.COLUMN_NameBrand() , kalaModel.getNameBrand());
        contentValues.put(KalaModel.COLUMN_NameVahedSize() , kalaModel.getNameVahedSize());
        contentValues.put(KalaModel.COLUMN_ccVahedShomaresh() , kalaModel.getCcVahedShomaresh());
        contentValues.put(KalaModel.COLUMN_NameVahedShomaresh() , kalaModel.getNameVahedShomaresh());
        contentValues.put(KalaModel.COLUMN_TarikhEngheza() , kalaModel.getTarikhEngheza());
        contentValues.put(KalaModel.COLUMN_ShomarehBach() , kalaModel.getShomarehBach());
        contentValues.put(KalaModel.COLUMN_GheymatForoshAsli() , kalaModel.getGheymatForoshAsli());
        contentValues.put(KalaModel.COLUMN_GheymatMasrafKonandehAsli() , kalaModel.getGheymatMasrafKonandehAsli());
        contentValues.put(KalaModel.COLUMN_ِDarsadMaliat() , kalaModel.getDarsadMaliat());
        contentValues.put(KalaModel.COLUMN_DarsadAvarez() , kalaModel.getDarsadAvarez());
        contentValues.put(KalaModel.COLUMN_ZaribTakhfifArzeshAfzodeh() , kalaModel.getZaribTakhfifArzeshAfzodeh());

        return contentValues;
    }


    /**
     * این متد برای نمایش لیست موجودی گیری استفاده می شود که distinct شده و فقط دو ستون برمیگردد
     * @param cursor لیست کالاها
     * @return لیستی شام نام کالا و کد کالا
     */
    private ArrayList<KalaModel> cursorToModelForShow(Cursor cursor)
    {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaModel kalaModel = new KalaModel();

            kalaModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccKalaCode())));
            kalaModel.setNameKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameKala())));
            kalaModel.setCcGorohKala(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccGorohKala())));

            kalaModels.add(kalaModel);
            cursor.moveToNext();
        }
        return kalaModels;
    }
	
    private ArrayList<KalaModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaModel> kalaModels = new ArrayList<>();
  try {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
          KalaModel kalaModel = new KalaModel();

          kalaModel.setRadif(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_Radif())));
          kalaModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccKalaCode())));
          kalaModel.setCodeKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_CodeKala())));
          kalaModel.setNameKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameKala())));
          kalaModel.setTedadMojodyGhabelForosh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadMojodyGhabelForosh())));
          kalaModel.setCcTaminKonandeh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccTaminKonandeh())));
          kalaModel.setTedadDarKarton(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadDarKarton())));
          kalaModel.setTedadDarBasteh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_TedadDarBasteh())));
          kalaModel.setAdad(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_Adad())));
          kalaModel.setCodeSort(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_CodeSort())));
          kalaModel.setMashmolMaliatAvarez(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_MashmolMaliatAvarez())));
          kalaModel.setMablaghForosh(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_MablaghForosh())));
          kalaModel.setMablaghMasrafKonandeh(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_MablaghMasrafKonandeh())));
          kalaModel.setLastMablaghForosh(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_LastMablaghForosh())));
          kalaModel.setCcGorohKala(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccGorohKala())));
          kalaModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccBrand())));
          kalaModel.setMablaghKharid(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_MablaghKharid())));
          kalaModel.setTol(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_Tol())));
          kalaModel.setArz(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_Arz())));
          kalaModel.setErtefa(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_Ertefa())));
          kalaModel.setCcVahedSize(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccVahedSize())));
          kalaModel.setVaznKhales(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_VaznKhales())));
          kalaModel.setVaznNaKhales(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_VaznNaKhales())));
          kalaModel.setVaznKarton(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_VaznKarton())));
          kalaModel.setCcVahedVazn(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccVahedVazn())));
          kalaModel.setBarCode(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_BarCode())));
          kalaModel.setTarikhTolid(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_TarikhTolid())));
          kalaModel.setNameVahedVazn(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedVazn())));
          kalaModel.setNameBrand(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameBrand())));
          kalaModel.setNameVahedSize(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedSize())));
          kalaModel.setCcVahedShomaresh(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccVahedShomaresh())));
          kalaModel.setNameVahedShomaresh(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameVahedShomaresh())));
          kalaModel.setTarikhEngheza(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_TarikhEngheza())));
          kalaModel.setShomarehBach(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_ShomarehBach())));
          kalaModel.setGheymatForoshAsli(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_GheymatForoshAsli())));
          kalaModel.setGheymatMasrafKonandehAsli(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_GheymatMasrafKonandehAsli())));
          kalaModel.setDarsadMaliat(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_ِDarsadMaliat())));
          kalaModel.setDarsadAvarez(cursor.getFloat(cursor.getColumnIndex(KalaModel.COLUMN_DarsadAvarez())));
          kalaModel.setZaribTakhfifArzeshAfzodeh(cursor.getDouble(cursor.getColumnIndex(KalaModel.COLUMN_ZaribTakhfifArzeshAfzodeh())));

          kalaModels.add(kalaModel);
          cursor.moveToNext();
      }
  }catch (Exception e){
      Log.i("takhfifSenfi", "cursorToModel: "+e.getMessage());
  }
        return kalaModels;

    }



}
