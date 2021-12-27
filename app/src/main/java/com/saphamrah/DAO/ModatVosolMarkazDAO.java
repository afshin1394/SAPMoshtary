package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.ModatVosolMarkazModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllModatVosolMarkazForoshByccMarkazForoshResult;
import com.saphamrah.protos.CenterRecieptionDurationGrpc;
import com.saphamrah.protos.CenterRecieptionDurationReply;
import com.saphamrah.protos.CenterRecieptionDurationReplyList;
import com.saphamrah.protos.CenterRecieptionDurationRequest;

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

public class ModatVosolMarkazDAO
{


    private DBHelper dbHelper;
    private Context context;


    public ModatVosolMarkazDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ModatVosolMarkazDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ModatVosolMarkazModel.COLUMN_ccModatVosolMarkaz(),
            ModatVosolMarkazModel.COLUMN_ccModatVosol(),
            ModatVosolMarkazModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh()
        };
    }

    public void fetchAllModatVosolMarkazGrpc(final Context context, final String activityNameForLog, final String ccMarkazForosh, final RetrofitResponse retrofitResponse)
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
                CenterRecieptionDurationGrpc.CenterRecieptionDurationBlockingStub centerRecieptionDurationBlockingStub = CenterRecieptionDurationGrpc.newBlockingStub(managedChannel);
                CenterRecieptionDurationRequest centerRecieptionDurationRequest = CenterRecieptionDurationRequest.newBuilder().setSellCenterID(ccMarkazForosh).build();
                Callable<CenterRecieptionDurationReplyList> getCenterRecieptionDurationCallable  = () -> centerRecieptionDurationBlockingStub.getCenterRecieptionDuration(centerRecieptionDurationRequest);
                RxAsync.makeObservable(getCenterRecieptionDurationCallable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(centerRecieptionDurationReplyList -> {
                            ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels = new ArrayList<>();
                            for (CenterRecieptionDurationReply centerRecieptionDurationReply : centerRecieptionDurationReplyList.getCenterRecieptionDurationsList()) {
                                ModatVosolMarkazModel modatVosolMarkazModel = new ModatVosolMarkazModel();

                                modatVosolMarkazModel.setCcModatVosolMarkaz(centerRecieptionDurationReply.getRecieptionDurationCenterID());
                                modatVosolMarkazModel.setCcModatVosol(centerRecieptionDurationReply.getRecieptionDurationID());
                                modatVosolMarkazModel.setCcMarkazSazmanForoshSakhtarForosh(centerRecieptionDurationReply.getSellStructureSellOrganizationCenterID());


                                modatVosolMarkazModels.add(modatVosolMarkazModel);
                            }

                            return modatVosolMarkazModels;

                        }).subscribe(new Observer<ArrayList<ModatVosolMarkazModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels) {
                        retrofitResponse.onSuccess(modatVosolMarkazModels);
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

    public void fetchAllModatVosolMarkaz(final Context context, final String activityNameForLog, final String ccMarkazForosh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolMarkazDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolMarkaz", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllModatVosolMarkazForoshByccMarkazForoshResult> call = apiServiceGet.getAllModatVosolMarkazForoshByccMarkazForosh(ccMarkazForosh);
            call.enqueue(new Callback<GetAllModatVosolMarkazForoshByccMarkazForoshResult>()
            {
                @Override
                public void onResponse(Call<GetAllModatVosolMarkazForoshByccMarkazForoshResult> call, Response<GetAllModatVosolMarkazForoshByccMarkazForoshResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ModatVosolMarkazDAO.class.getSimpleName(), "", "fetchAllModatVosolMarkaz", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllModatVosolMarkazForoshByccMarkazForoshResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ModatVosolMarkazDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolMarkaz", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), context.getResources().getString(R.string.resultIsNull), ModatVosolMarkazDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolMarkaz", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ModatVosolMarkazDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolMarkaz", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ModatVosolMarkazDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolMarkaz", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllModatVosolMarkazForoshByccMarkazForoshResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), t.getMessage(), ModatVosolMarkazDAO.class.getSimpleName(), activityNameForLog, "fetchAllModatVosolMarkaz", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public boolean insertGroup(ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ModatVosolMarkazModel modatVosolMarkazModel : modatVosolMarkazModels)
            {
                ContentValues contentValues = modelToContentvalue(modatVosolMarkazModel);
                db.insertOrThrow(ModatVosolMarkazModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ModatVosolMarkazModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolMarkazDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ModatVosolMarkazModel> getAll()
    {
        ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ModatVosolMarkazModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    modatVosolMarkazModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ModatVosolMarkazModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ModatVosolMarkazDAO" , "" , "getAll" , "");
        }
        return modatVosolMarkazModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ModatVosolMarkazModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ModatVosolMarkazModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ModatVosolMarkazDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ModatVosolMarkazModel modatVosolMarkazModel)
    {
        ContentValues contentValues = new ContentValues();

        if (modatVosolMarkazModel.getCcModatVosolMarkaz() > 0)
        {
            contentValues.put(ModatVosolMarkazModel.COLUMN_ccModatVosolMarkaz() , modatVosolMarkazModel.getCcModatVosolMarkaz());
        }
        contentValues.put(ModatVosolMarkazModel.COLUMN_ccModatVosol() , modatVosolMarkazModel.getCcModatVosol());
        contentValues.put(ModatVosolMarkazModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh() , modatVosolMarkazModel.getCcMarkazSazmanForoshSakhtarForosh());

        return contentValues;
    }


    private ArrayList<ModatVosolMarkazModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ModatVosolMarkazModel> modatVosolMarkazModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ModatVosolMarkazModel modatVosolMarkazModel = new ModatVosolMarkazModel();

            modatVosolMarkazModel.setCcModatVosolMarkaz(cursor.getInt(cursor.getColumnIndex(ModatVosolMarkazModel.COLUMN_ccModatVosolMarkaz())));
            modatVosolMarkazModel.setCcModatVosol(cursor.getInt(cursor.getColumnIndex(ModatVosolMarkazModel.COLUMN_ccModatVosol())));
            modatVosolMarkazModel.setCcMarkazSazmanForoshSakhtarForosh(cursor.getInt(cursor.getColumnIndex(ModatVosolMarkazModel.COLUMN_ccMarkazSazmanForoshSakhtarForosh())));

            modatVosolMarkazModels.add(modatVosolMarkazModel);
            cursor.moveToNext();
        }
        return modatVosolMarkazModels;
    }


}
