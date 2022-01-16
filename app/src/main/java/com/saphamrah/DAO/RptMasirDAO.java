package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.Model.RptMasirModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllrptMasirForoshandehResult;
import com.saphamrah.protos.RptInvoiceRequestStatusGrpc;
import com.saphamrah.protos.RptInvoiceRequestStatusReply;
import com.saphamrah.protos.RptInvoiceRequestStatusReplyList;
import com.saphamrah.protos.RptInvoiceRequestStatusRequest;
import com.saphamrah.protos.RptSalesManRouteGrpc;
import com.saphamrah.protos.RptSalesManRouteReply;
import com.saphamrah.protos.RptSalesManRouteReplyList;
import com.saphamrah.protos.RptSalesManRouteRequest;

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

public class RptMasirDAO
{

    private DBHelper dbHelper;
    private Context context;


    public RptMasirDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptMasirDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            RptMasirModel.COLUMN_ccRpt_Masir(),
            RptMasirModel.COLUMN_ccForoshandeh(),
            RptMasirModel.COLUMN_CodeForoshandeh(),
            RptMasirModel.COLUMN_FullNameForoshandeh(),
            RptMasirModel.COLUMN_ccMasir(),
            RptMasirModel.COLUMN_NameMasir(),
            RptMasirModel.COLUMN_NameToorVisit(),
            RptMasirModel.COLUMN_NameRoozVisit(),
            RptMasirModel.COLUMN_ccMasirRoozVisit(),
            RptMasirModel.COLUMN_Khordeh(),
            RptMasirModel.COLUMN_Omdeh(),
            RptMasirModel.COLUMN_Taavoni(),
            RptMasirModel.COLUMN_Zanjireh(),
            RptMasirModel.COLUMN_Nemaiandeh(),
            RptMasirModel.COLUMN_ccGorohForosh(),
            RptMasirModel.COLUMN_NameGorohForosh()
        };
    }

    public void fetchRPTMasirForoshandehGrpc(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMasirForoshandehGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                RptSalesManRouteGrpc.RptSalesManRouteBlockingStub blockingStub = RptSalesManRouteGrpc.newBlockingStub(managedChannel);
                RptSalesManRouteRequest request = RptSalesManRouteRequest.newBuilder().setSalesManID(ccForoshandeh).build();

                Callable<RptSalesManRouteReplyList> replyListCallable = () -> blockingStub.getRptSalesManRoute(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<RptMasirModel> models = new ArrayList<>();
                            for (RptSalesManRouteReply reply : replyList.getRptSalesManRoutesList()) {
                                RptMasirModel model = new RptMasirModel();

                                model.setCcRpt_Masir(reply.getRptRoutID());
                                model.setCcForoshandeh(reply.getSalesManID());
                                model.setCodeForoshandeh(reply.getSalesManCode());
                                model.setFullNameForoshandeh(reply.getSalesManFullName());
                                model.setCcMasir(reply.getRouteID());
                                model.setNameMasir(reply.getRouteName());
                                model.setNameToorVisit(reply.getVisitTourName());
                                model.setNameRoozVisit(reply.getVisitDayName());
                                model.setCcMasirRoozVisit(reply.getVisitDayRouteId());
                                model.setKhordeh(reply.getRetail());
                                model.setOmdeh(reply.getMajor());
                                model.setTaavoni(reply.getCooperative());
                                model.setZanjireh(reply.getChained());
                                model.setNemaiandeh(reply.getAgent());
                                model.setCcGorohForosh(reply.getSellGroupID());
                                model.setNameGorohForosh(reply.getSellGroupName());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptMasirModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptMasirModel> models) {
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
        } catch (Exception exception) {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), RptDarkhastFaktorVazeiatPPCDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMasirForoshandehGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }

    public void fetchRPTMasirForoshandeh(final Context context, final String activityNameForLog, final String ccForoshandeh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        //serverIpModel.setPort("8040");

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMasirDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMasirForoshandeh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllrptMasirForoshandehResult> call = apiServiceGet.getAllrptMasirForoshandeh(ccForoshandeh);
            call.enqueue(new Callback<GetAllrptMasirForoshandehResult>() {
                @Override
                public void onResponse(Call<GetAllrptMasirForoshandehResult> call, Response<GetAllrptMasirForoshandehResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, RptMasirDAO.class.getSimpleName(), "", "fetchRPTMasirForoshandeh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllrptMasirForoshandehResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), RptMasirDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMasirForoshandeh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), RptMasirDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMasirForoshandeh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", response.message(), response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RptMasirDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMasirForoshandeh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), RptMasirDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMasirForoshandeh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllrptMasirForoshandehResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage() , endpoint), RptMasirDAO.class.getSimpleName(), activityNameForLog, "fetchRPTMasirForoshandeh", "onFailure");
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

    public boolean insertGroup(ArrayList<RptMasirModel> rptMasirModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptMasirModel rptMasirModel : rptMasirModels)
            {
                ContentValues contentValues = modelToContentvalue(rptMasirModel);
                db.insertOrThrow(RptMasirModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , RptMasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMasirDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<RptMasirModel> getAll()
    {
        ArrayList<RptMasirModel> rptMasirModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptMasirModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMasirModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMasirDAO" , "" , "getAll" , "");
        }
        return rptMasirModels;
    }

    public ArrayList<RptMasirModel> getRptForoshandehMaisrWithSum()
    {
        ArrayList<RptMasirModel> rptMasirModels = new ArrayList<>();
        String query = "SELECT * FROM( \n" +
                "            SELECT 0 AS _id, ccMasir, ccMasirRoozVisit, NameMasir, NameRoozVisit, NameToorVisit, Khordeh AS TedadKhordeh, Omdeh AS TedadOmdeh,\n" +
                "                    Taavoni AS TedadTaavoni, Zanjireh AS TedadZanjireh, Nemaiandeh AS TedadNemaiandeh,    \n" +
                "                    (Khordeh + Omdeh + Taavoni + Zanjireh + Nemaiandeh) AS Jam_Moshtary,  \n" +
                "                    (Khordeh*1 + Omdeh*2 + Taavoni*2 + Zanjireh*5 + Nemaiandeh*10) AS JamBaZaribKhorderh, 0 AS Jam \n" +
                "            FROM Rpt_Masir \n" +
                "            UNION ALL \n" +
                "            SELECT 1 AS _id, -1 AS ccMasir, 0 AS ccMasirRoozVisit, '', '', 'جمع', SUM(Khordeh) AS TedadKhordeh, SUM(Omdeh) AS TedadOmdeh, \n" +
                "                    SUM(Taavoni) AS TedadTaavoni, SUM(Zanjireh) AS TedadZanjireh, SUM(Nemaiandeh) AS TedadNemaiandeh,    \n" +
                "                    SUM(Khordeh + Omdeh + Taavoni + Zanjireh + Nemaiandeh) AS Jam_Moshtary, \n" +
                "                    SUM(Khordeh*1 + Omdeh*2 + Taavoni*2 + Zanjireh*5 + Nemaiandeh*10) AS JamBaZaribKhorderh, 1 AS Jam \n" +
                "            FROM Rpt_Masir \n" +
                "            UNION ALL \n" +
                "            SELECT 2 AS _id, -2 AS ccMasir, 0 AS ccMasirRoozVisit, '', '', 'متوسط روزانه', SUM(Khordeh)/Count(ccMasir) AS TedadKhordeh, \n" +
                "                   SUM(Omdeh)/Count(ccMasir) AS TedadOmdeh, SUM(Taavoni)/Count(ccMasir) AS TedadTaavoni, \n" +
                "                   SUM(Zanjireh)/Count(ccMasir) AS TedadZanjireh, SUM(Nemaiandeh)/Count(ccMasir) AS TedadNemaiandeh, \n" +
                "                   SUM(Khordeh + Omdeh + Taavoni + Zanjireh + Nemaiandeh)/ Count(ccMasir) AS Jam_Moshtary, \n" +
                "                   SUM(Khordeh*1 + Omdeh*2 + Taavoni*2 + Zanjireh*5 + Nemaiandeh*10)/ Count(ccMasir) AS JamBaZaribKhorderh, 2 AS Jam \n" +
                "            FROM Rpt_Masir \n" +
                "                  )A   \n" +
                "ORDER BY _id,Jam, ccMasirRoozVisit";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptMasirModels = cursorToModelWithSum(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptMasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMasirDAO" , "" , "getRptForoshandehMaisrWithSum" , "");
        }
        return rptMasirModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptMasirModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptMasirModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptMasirDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptMasirModel rptMasirModel)
    {
        ContentValues contentValues = new ContentValues();

        if (rptMasirModel.getCcRpt_Masir() > 0)
        {
            contentValues.put(RptMasirModel.COLUMN_ccRpt_Masir() , rptMasirModel.getCcRpt_Masir());
        }
        contentValues.put(RptMasirModel.COLUMN_ccForoshandeh() , rptMasirModel.getCcForoshandeh());
        contentValues.put(RptMasirModel.COLUMN_CodeForoshandeh() , rptMasirModel.getCodeForoshandeh());
        contentValues.put(RptMasirModel.COLUMN_FullNameForoshandeh() , rptMasirModel.getFullNameForoshandeh());
        contentValues.put(RptMasirModel.COLUMN_ccMasir() , rptMasirModel.getCcMasir());
        contentValues.put(RptMasirModel.COLUMN_NameMasir() , rptMasirModel.getNameMasir());
        contentValues.put(RptMasirModel.COLUMN_NameToorVisit() , rptMasirModel.getNameToorVisit());
        contentValues.put(RptMasirModel.COLUMN_NameRoozVisit() , rptMasirModel.getNameRoozVisit());
        contentValues.put(RptMasirModel.COLUMN_ccMasirRoozVisit() , rptMasirModel.getCcMasirRoozVisit());
        contentValues.put(RptMasirModel.COLUMN_Khordeh() , rptMasirModel.getKhordeh());
        contentValues.put(RptMasirModel.COLUMN_Omdeh() , rptMasirModel.getOmdeh());
        contentValues.put(RptMasirModel.COLUMN_Taavoni() , rptMasirModel.getTaavoni());
        contentValues.put(RptMasirModel.COLUMN_Zanjireh() , rptMasirModel.getZanjireh());
        contentValues.put(RptMasirModel.COLUMN_Nemaiandeh() , rptMasirModel.getNemaiandeh());
        contentValues.put(RptMasirModel.COLUMN_ccGorohForosh() , rptMasirModel.getCcGorohForosh());
        contentValues.put(RptMasirModel.COLUMN_NameGorohForosh() , rptMasirModel.getNameGorohForosh());

        return contentValues;
    }


    private ArrayList<RptMasirModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptMasirModel> rptMasirModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptMasirModel rptMasirModel = new RptMasirModel();

            rptMasirModel.setCcRpt_Masir(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_ccRpt_Masir())));
            rptMasirModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_ccForoshandeh())));
            rptMasirModel.setCodeForoshandeh(cursor.getString(cursor.getColumnIndex(RptMasirModel.COLUMN_CodeForoshandeh())));
            rptMasirModel.setFullNameForoshandeh(cursor.getString(cursor.getColumnIndex(RptMasirModel.COLUMN_FullNameForoshandeh())));
            rptMasirModel.setCcMasir(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_ccMasir())));
            rptMasirModel.setNameMasir(cursor.getString(cursor.getColumnIndex(RptMasirModel.COLUMN_NameMasir())));
            rptMasirModel.setNameToorVisit(cursor.getString(cursor.getColumnIndex(RptMasirModel.COLUMN_NameToorVisit())));
            rptMasirModel.setNameRoozVisit(cursor.getString(cursor.getColumnIndex(RptMasirModel.COLUMN_NameRoozVisit())));
            rptMasirModel.setCcMasirRoozVisit(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_ccMasirRoozVisit())));
            rptMasirModel.setKhordeh(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_Khordeh())));
            rptMasirModel.setOmdeh(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_Omdeh())));
            rptMasirModel.setTaavoni(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_Taavoni())));
            rptMasirModel.setZanjireh(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_Zanjireh())));
            rptMasirModel.setNemaiandeh(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_Nemaiandeh())));
            rptMasirModel.setCcGorohForosh(cursor.getInt(cursor.getColumnIndex(RptMasirModel.COLUMN_ccGorohForosh())));
            rptMasirModel.setNameGorohForosh(cursor.getString(cursor.getColumnIndex(RptMasirModel.COLUMN_NameGorohForosh())));

            rptMasirModels.add(rptMasirModel);
            cursor.moveToNext();
        }
        return rptMasirModels;
    }



    private ArrayList<RptMasirModel> cursorToModelWithSum(Cursor cursor)
    {
        ArrayList<RptMasirModel> rptMasirModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptMasirModel rptMasirModel = new RptMasirModel();


            rptMasirModel.setCcMasir(cursor.getInt(cursor.getColumnIndex("ccMasir")));
            rptMasirModel.setNameMasir(cursor.getString(cursor.getColumnIndex("NameMasir")));
            rptMasirModel.setNameToorVisit(cursor.getString(cursor.getColumnIndex("NameToorVisit")));
            rptMasirModel.setNameRoozVisit(cursor.getString(cursor.getColumnIndex("NameRoozVisit")));
            rptMasirModel.setCcMasirRoozVisit(cursor.getInt(cursor.getColumnIndex("ccMasirRoozVisit")));
            rptMasirModel.setKhordeh(cursor.getInt(cursor.getColumnIndex("TedadKhordeh")));
            rptMasirModel.setOmdeh(cursor.getInt(cursor.getColumnIndex("TedadOmdeh")));
            rptMasirModel.setTaavoni(cursor.getInt(cursor.getColumnIndex("TedadTaavoni")));
            rptMasirModel.setZanjireh(cursor.getInt(cursor.getColumnIndex("TedadZanjireh")));
            rptMasirModel.setNemaiandeh(cursor.getInt(cursor.getColumnIndex("TedadNemaiandeh")));
            rptMasirModel.setJam(cursor.getInt(cursor.getColumnIndex("Jam")));

            rptMasirModels.add(rptMasirModel);
            cursor.moveToNext();
        }
        return rptMasirModels;
    }



}