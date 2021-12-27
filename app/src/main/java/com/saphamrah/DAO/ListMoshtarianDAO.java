package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.RotbehModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetMoshtaryByRadiusResult;
import com.saphamrah.protos.CustomerByRadiusGrpc;
import com.saphamrah.protos.CustomerByRadiusReply;
import com.saphamrah.protos.CustomerByRadiusReplyList;
import com.saphamrah.protos.CustomerByRadiusRequest;

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

public class ListMoshtarianDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "ListMoshtarianDAO";


    public ListMoshtarianDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ListMoshtarianDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ListMoshtarianModel.COLUMN_ccMoshtary(),
            ListMoshtarianModel.COLUMN_CodeMoshtaryOld(),
            ListMoshtarianModel.COLUMN_FNameMoshtary(),
            ListMoshtarianModel.COLUMN_LNameMoshtary(),
            ListMoshtarianModel.COLUMN_NameMoshtary(),
            ListMoshtarianModel.COLUMN_CodeMely(),
            ListMoshtarianModel.COLUMN_MoshtaryCodeVazeiat(),
            ListMoshtarianModel.COLUMN_txtMoshtaryCodeVazeiat(),
            ListMoshtarianModel.COLUMN_NameTablo(),
            ListMoshtarianModel.COLUMN_NameNoeMoshtary(),
            ListMoshtarianModel.COLUMN_Latitude_y(),
            ListMoshtarianModel.COLUMN_Longitude_x(),
            ListMoshtarianModel.COLUMN_Address(),
            ListMoshtarianModel.COLUMN_Telephone(),
            ListMoshtarianModel.COLUMN_Mobile(),
            ListMoshtarianModel.COLUMN_MasahatMaghazeh(),
            ListMoshtarianModel.COLUMN_hasAnbar(),
            ListMoshtarianModel.COLUMN_ccPorseshnameh(),
            ListMoshtarianModel.COLUMN_IsMoshtaryAmargar(),
            ListMoshtarianModel.COLUMN_ccMahaleh(),
            ListMoshtarianModel.COLUMN_CodePosty(),
            ListMoshtarianModel.COLUMN_Pelak(),
            ListMoshtarianModel.COLUMN_CodeVazeiatAmargar(),
            ListMoshtarianModel.COLUMN_KhiabanAsli(),
            ListMoshtarianModel.COLUMN_KhiabanFarei1(),
            ListMoshtarianModel.COLUMN_KhiabanFarei2(),
            ListMoshtarianModel.COLUMN_KoocheAsli(),
            ListMoshtarianModel.COLUMN_KoocheFarei1(),
            ListMoshtarianModel.COLUMN_KoocheFarei2(),
            ListMoshtarianModel.COLUMN_TarikhAkharinFaktor(),
            ListMoshtarianModel.COLUMN_MablaghAkharinFaktor(),
            ListMoshtarianModel.COLUMN_ExtraProp_isSend(),
            ListMoshtarianModel.COLUMN_ExtraProp_Status()
        };
    }
    public void fetchByRadiusGrpc(final Context context, final String activityNameForLog, String radius, String latitude, String longitude, final RetrofitResponse retrofitResponse) {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, RotbehModel.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                CustomerByRadiusGrpc.CustomerByRadiusBlockingStub blockingStub = CustomerByRadiusGrpc.newBlockingStub(managedChannel);
                CustomerByRadiusRequest request = CustomerByRadiusRequest.newBuilder().setRadius(radius).setLatitude(latitude).setLongitude(longitude).build();

                Callable<CustomerByRadiusReplyList> replyListCallable = () -> blockingStub.getCustomerByRadius(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<ListMoshtarianModel> models = new ArrayList<>();
                            for (CustomerByRadiusReply reply : replyList.getCustomerByRadiussList()) {
                                ListMoshtarianModel model = new ListMoshtarianModel();


                                model.setRadif(reply.getIndex());
                                model.setCcMoshtary(reply.getCustomerID());
                                model.setCcPorseshnameh(reply.getQuestionnaireID());
                                model.setCodeMoshtaryOld(reply.getCustomerLastName());
                                model.setNameMoshtary(reply.getCustomerName());
                                model.setFNameMoshtary(reply.getCustomerFullName());
                                model.setLNameMoshtary(reply.getCustomerLastName());
                                model.setMoshtaryCodeVazeiat(reply.getSituationCustomerCode());
                                model.setTxtMoshtaryCodeVazeiat(reply.getSituationCustomerCodeTxt());
                                model.setNameTablo(reply.getSignsName());
                                model.setNameNoeMoshtary(reply.getCustomerTypeName());
                                model.setCodeMely(reply.getNationalCode());
                                model.setLatitudeY(reply.getLatitude());
                                model.setLongitudeX(reply.getLongitude());
                                model.setAddress(reply.getAddress());
                                model.setTelephone(reply.getPhone());
                                model.setMobile(reply.getMobile());
                                model.setIsMoshtaryAmargar(reply.getIsStatisticianCustomer());
                                model.setPelak(reply.getPlague());
                                model.setCodePosty(reply.getPostalCode());
                                model.setCcMahaleh(reply.getDistrictID());
                                model.setCodeVazeiatAmargar(reply.getStatisticianSituationCode());
                                model.setKhiabanAsli(reply.getMainStreet());
                                model.setKhiabanFarei1(reply.getByStreet1());
                                model.setKhiabanFarei2(reply.getByStreet2());
                                model.setKoocheAsli(reply.getMainAlley());
                                model.setKoocheFarei1(reply.getByAlley1());
                                model.setKoocheFarei2(reply.getByAlley2());
                                model.setTarikhAkharinFaktor(reply.getLastInvoiceDate());
                                model.setMablaghAkharinFaktor(reply.getLastInvoicePrice());
                                model.setMasahatMaghazeh(reply.getShopArea());
                                model.setHasAnbar(reply.getHasStore());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ListMoshtarianModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ListMoshtarianModel> models) {
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), RotbehModel.class.getSimpleName(), activityNameForLog, "fetchConfigNoeVosolMojazeFaktorGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }


    }

    public void fetchByRadius(final Context context, final String activityNameForLog, String radius, String latitude, String longitude, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMoshtaryByRadiusResult> call = apiServiceGet.getMoshtaryByRadius(radius , latitude , longitude);
            call.enqueue(new Callback<GetMoshtaryByRadiusResult>() {
                @Override
                public void onResponse(Call<GetMoshtaryByRadiusResult> call, Response<GetMoshtaryByRadiusResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ListMoshtarianDAO.class.getSimpleName(), "", "fetchByRadius", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMoshtaryByRadiusResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMoshtaryByRadiusResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public void fetchByMasir(final Context context, final String activityNameForLog, String noe, String ccMasir, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetMoshtaryByRadiusResult> call = apiServiceGet.getListMoshtarianByMasir(noe, ccMasir);
            call.enqueue(new Callback<GetMoshtaryByRadiusResult>() {
                @Override
                public void onResponse(Call<GetMoshtaryByRadiusResult> call, Response<GetMoshtaryByRadiusResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, ListMoshtarianDAO.class.getSimpleName(), "", "fetchByRadius", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMoshtaryByRadiusResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMoshtaryByRadiusResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), ListMoshtarianDAO.class.getSimpleName(), activityNameForLog, "fetchByRadius", "onFailure");
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

    public boolean insertGroup(ArrayList<ListMoshtarianModel> listMoshtarianModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ListMoshtarianModel listMoshtarianModel : listMoshtarianModels)
            {
                ContentValues contentValues = modelToContentvalue(listMoshtarianModel);
                db.insertOrThrow(ListMoshtarianModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ListMoshtarianModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListMoshtarianDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ListMoshtarianModel> getAll()
    {
        ArrayList<ListMoshtarianModel> listMoshtarianModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ListMoshtarianModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    listMoshtarianModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ListMoshtarianModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListMoshtarianDAO" , "" , "getAll" , "");
        }
        return listMoshtarianModels;
    }

    public ListMoshtarianModel getByccMoshtary(int ccMoshtary)
    {
        ListMoshtarianModel listMoshtarianModel = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + ListMoshtarianModel.TableName() + " where ccMoshtary = " + ccMoshtary + " limit 1";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    listMoshtarianModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ListMoshtarianModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListMoshtarianDAO" , "" , "getByccMoshtaryAndCode" , "");
        }
        return listMoshtarianModel;
    }

    public ListMoshtarianModel getByccMoshtaryAndCode(int ccMoshtary, String codeMoshtary)
    {
        ListMoshtarianModel listMoshtarianModel = null;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + ListMoshtarianModel.TableName() + " where ccMoshtary = " + ccMoshtary + " and CodeMoshtaryOld = '" + codeMoshtary + "' limit 1";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    listMoshtarianModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ListMoshtarianModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListMoshtarianDAO" , "" , "getByccMoshtaryAndCode" , "");
        }
        return listMoshtarianModel;
    }

    public boolean updateStatus(int ccMoshtary, int status)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ListMoshtarianModel.COLUMN_ExtraProp_Status(), status);
            db.update(ListMoshtarianModel.TableName(), values, "ccMoshtary = ?", new String[]{String.valueOf(ccMoshtary)});
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , ListMoshtarianModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME , "" , "updateMoshtaryJadid" , "");
            return false;
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ListMoshtarianModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ListMoshtarianModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ListMoshtarianDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(ListMoshtarianModel listMoshtarianModel)
    {
        ContentValues contentValues = new ContentValues();

        if (listMoshtarianModel.getCcMoshtary() != null && listMoshtarianModel.getCcMoshtary() > 0)
        {
            contentValues.put(ListMoshtarianModel.COLUMN_ccMoshtary() , ListMoshtarianModel.COLUMN_ccMoshtary());
        }
        contentValues.put(ListMoshtarianModel.COLUMN_ccMoshtary() , listMoshtarianModel.getCcMoshtary());
        contentValues.put(ListMoshtarianModel.COLUMN_CodeMoshtaryOld() , listMoshtarianModel.getCodeMoshtaryOld());
        contentValues.put(ListMoshtarianModel.COLUMN_FNameMoshtary() , listMoshtarianModel.getFNameMoshtary());
        contentValues.put(ListMoshtarianModel.COLUMN_LNameMoshtary() , listMoshtarianModel.getLNameMoshtary());
        contentValues.put(ListMoshtarianModel.COLUMN_NameMoshtary() , listMoshtarianModel.getNameMoshtary());
        contentValues.put(ListMoshtarianModel.COLUMN_CodeMely() , listMoshtarianModel.getCodeMely());
        contentValues.put(ListMoshtarianModel.COLUMN_MoshtaryCodeVazeiat() , listMoshtarianModel.getMoshtaryCodeVazeiat());
        contentValues.put(ListMoshtarianModel.COLUMN_txtMoshtaryCodeVazeiat() , listMoshtarianModel.getTxtMoshtaryCodeVazeiat());
        contentValues.put(ListMoshtarianModel.COLUMN_NameTablo() , listMoshtarianModel.getNameTablo());
        contentValues.put(ListMoshtarianModel.COLUMN_NameNoeMoshtary() , listMoshtarianModel.getNameNoeMoshtary());
        contentValues.put(ListMoshtarianModel.COLUMN_Latitude_y() , listMoshtarianModel.getLatitudeY());
        contentValues.put(ListMoshtarianModel.COLUMN_Longitude_x() , listMoshtarianModel.getLongitudeX());
        contentValues.put(ListMoshtarianModel.COLUMN_Address() , listMoshtarianModel.getAddress());
        contentValues.put(ListMoshtarianModel.COLUMN_Telephone() , listMoshtarianModel.getTelephone());
        contentValues.put(ListMoshtarianModel.COLUMN_Mobile() , listMoshtarianModel.getMobile());
        contentValues.put(ListMoshtarianModel.COLUMN_MasahatMaghazeh() , listMoshtarianModel.getMasahatMaghazeh());
        contentValues.put(ListMoshtarianModel.COLUMN_hasAnbar() , listMoshtarianModel.getHasAnbar());
        contentValues.put(ListMoshtarianModel.COLUMN_ccPorseshnameh() , listMoshtarianModel.getCcPorseshnameh());
        contentValues.put(ListMoshtarianModel.COLUMN_IsMoshtaryAmargar() , listMoshtarianModel.getIsMoshtaryAmargar());
        contentValues.put(ListMoshtarianModel.COLUMN_ccMahaleh() , listMoshtarianModel.getCcMahaleh());
        contentValues.put(ListMoshtarianModel.COLUMN_CodePosty() , listMoshtarianModel.getCodePosty());
        contentValues.put(ListMoshtarianModel.COLUMN_Pelak() , listMoshtarianModel.getPelak());
        contentValues.put(ListMoshtarianModel.COLUMN_CodeVazeiatAmargar() , listMoshtarianModel.getCodeVazeiatAmargar());
        contentValues.put(ListMoshtarianModel.COLUMN_KhiabanAsli() , listMoshtarianModel.getKhiabanAsli());
        contentValues.put(ListMoshtarianModel.COLUMN_KhiabanFarei1() , listMoshtarianModel.getKhiabanFarei1());
        contentValues.put(ListMoshtarianModel.COLUMN_KhiabanFarei2() , listMoshtarianModel.getKhiabanFarei2());
        contentValues.put(ListMoshtarianModel.COLUMN_KoocheAsli() , listMoshtarianModel.getKoocheAsli());
        contentValues.put(ListMoshtarianModel.COLUMN_KoocheFarei1() , listMoshtarianModel.getKoocheFarei1());
        contentValues.put(ListMoshtarianModel.COLUMN_KoocheFarei2() , listMoshtarianModel.getKoocheFarei2());
        contentValues.put(ListMoshtarianModel.COLUMN_TarikhAkharinFaktor() , listMoshtarianModel.getTarikhAkharinFaktor());
        contentValues.put(ListMoshtarianModel.COLUMN_MablaghAkharinFaktor() , listMoshtarianModel.getMablaghAkharinFaktor());
        contentValues.put(ListMoshtarianModel.COLUMN_ExtraProp_isSend() , listMoshtarianModel.getExtraProp_isSend());
        contentValues.put(ListMoshtarianModel.COLUMN_ExtraProp_Status() , listMoshtarianModel.getExtraProp_Status());

        return contentValues;
    }

    private ArrayList<ListMoshtarianModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ListMoshtarianModel> listMoshtarianModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ListMoshtarianModel listMoshtarianModel = new ListMoshtarianModel();

            listMoshtarianModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_ccMoshtary())));
            listMoshtarianModel.setCodeMoshtaryOld(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_CodeMoshtaryOld())));
            listMoshtarianModel.setFNameMoshtary(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_FNameMoshtary())));
            listMoshtarianModel.setLNameMoshtary(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_LNameMoshtary())));
            listMoshtarianModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_NameMoshtary())));
            listMoshtarianModel.setCodeMely(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_CodeMely())));
            listMoshtarianModel.setMoshtaryCodeVazeiat(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_MoshtaryCodeVazeiat())));
            listMoshtarianModel.setTxtMoshtaryCodeVazeiat(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_txtMoshtaryCodeVazeiat())));
            listMoshtarianModel.setNameTablo(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_NameTablo())));
            listMoshtarianModel.setNameNoeMoshtary(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_NameNoeMoshtary())));
            listMoshtarianModel.setLatitudeY(cursor.getDouble(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_Latitude_y())));
            listMoshtarianModel.setLongitudeX(cursor.getDouble(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_Longitude_x())));
            listMoshtarianModel.setAddress(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_Address())));
            listMoshtarianModel.setTelephone(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_Telephone())));
            listMoshtarianModel.setMobile(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_Mobile())));
            listMoshtarianModel.setMasahatMaghazeh(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_MasahatMaghazeh())));
            listMoshtarianModel.setHasAnbar(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_hasAnbar())));
            listMoshtarianModel.setCcPorseshnameh(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_ccPorseshnameh())));
            listMoshtarianModel.setIsMoshtaryAmargar(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_IsMoshtaryAmargar())));
            listMoshtarianModel.setCcMahaleh(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_ccMahaleh())));
            listMoshtarianModel.setCodePosty(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_CodePosty())));
            listMoshtarianModel.setPelak(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_Pelak())));
            listMoshtarianModel.setCodeVazeiatAmargar(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_CodeVazeiatAmargar())));
            listMoshtarianModel.setKhiabanAsli(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_KhiabanAsli())));
            listMoshtarianModel.setKhiabanFarei1(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_KhiabanFarei1())));
            listMoshtarianModel.setKhiabanFarei2(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_KhiabanFarei2())));
            listMoshtarianModel.setKoocheAsli(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_KoocheAsli())));
            listMoshtarianModel.setKoocheFarei1(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_KoocheFarei1())));
            listMoshtarianModel.setKoocheFarei2(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_KoocheFarei2())));
            listMoshtarianModel.setTarikhAkharinFaktor(cursor.getString(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_TarikhAkharinFaktor())));
            listMoshtarianModel.setMablaghAkharinFaktor(cursor.getLong(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_MablaghAkharinFaktor())));
            listMoshtarianModel.setExtraProp_isSend(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_ExtraProp_isSend())));
            listMoshtarianModel.setExtraProp_Status(cursor.getInt(cursor.getColumnIndex(ListMoshtarianModel.COLUMN_ExtraProp_Status())));

            listMoshtarianModels.add(listMoshtarianModel);
            cursor.moveToNext();
        }
        return listMoshtarianModels;
    }


}
