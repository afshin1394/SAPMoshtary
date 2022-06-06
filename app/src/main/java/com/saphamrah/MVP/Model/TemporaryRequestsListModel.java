package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.MAMOURPAKHSH_SARD;
import static com.saphamrah.Utils.Constants.MAMOURPAKHSH_SMART;
import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.TemporaryRequestsListMVP;
import com.saphamrah.DAO.AdamDarkhastDAO;
import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.DAO.BarkhordForoshandehBaMoshtaryDAO;
import com.saphamrah.DAO.CustomerAdamDarkhastDAO;
import com.saphamrah.DAO.CustomerDarkhastFaktorDAO;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorAfradForoshDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.DAO.DarkhastFaktorJayezehDAO;
import com.saphamrah.DAO.DarkhastFaktorKalaPishnahadiDAO;
import com.saphamrah.DAO.DarkhastFaktorRoozSortDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrTakhfifDAO;
import com.saphamrah.DAO.DarkhastFaktorTakhfifDAO;
import com.saphamrah.DAO.ElamMarjoeePPCDAO;
import com.saphamrah.DAO.ElamMarjoeeSatrPPCDAO;
import com.saphamrah.DAO.ElamMarjoeeSatrPPCTedadDAO;
import com.saphamrah.DAO.ForoshandehDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GPSDataPpcDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.DAO.LogPPCDAO;
import com.saphamrah.DAO.MandehMojodyMashinDAO;
import com.saphamrah.DAO.MarkazDAO;
import com.saphamrah.DAO.ModatVosolGorohDAO;
import com.saphamrah.DAO.MojoodiGiriDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.MoshtaryMorajehShodehRoozDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.SuggestDAO;
import com.saphamrah.MVP.View.TemporaryRequestsListActivity;
import com.saphamrah.MVP.View.TreasuryListMapActivity;
import com.saphamrah.Model.AdamDarkhastModel;
import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Model.ControlDataErsaliTabletModel;
import com.saphamrah.Model.ControlInsertFaktorModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorAfradForoshModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.DarkhastFaktorSatrTakhfifModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.ElamMarjoeePPCModel;
import com.saphamrah.Model.ElamMarjoeeSatrPPCModel;
import com.saphamrah.Model.ElamMarjoeeSatrPPCTedadModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.Model.MojoodiGiriModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryMorajehShodehRoozModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.SuggestModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Repository.DarkhastFaktorEmzaMoshtaryRepository;
import com.saphamrah.Shared.LastOlaviatMoshtaryShared;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.UIModel.CustomerAdamDarkhastModel;
import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.APIServicePost;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetMandehMojodyMashinResponse;
import com.saphamrah.WebService.ServiceResponse.ControlInsertFaktorResult;
import com.saphamrah.WebService.ServiceResponse.CreateAdamDarkhastResult;
import com.saphamrah.WebService.ServiceResponse.CreateBarkhordForoshandehBaMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.CreateDarkhastFaktorAfradForoshResult;
import com.saphamrah.WebService.ServiceResponse.CreateDarkhastFaktorWithVosol;
import com.saphamrah.WebService.ServiceResponse.CreateElamMarjoeeSatrPPCTedadResult;
import com.saphamrah.WebService.ServiceResponse.CreateGpsDataPPCResult;
import com.saphamrah.WebService.ServiceResponse.CreateKalaMojodyWithJSONResult;
import com.saphamrah.WebService.ServiceResponse.CreateLogPPCResult;
import com.saphamrah.WebService.ServiceResponse.CreateMojoodiGiriResult;
import com.saphamrah.WebService.ServiceResponse.SuggestResult;
import com.saphamrah.WebService.ServiceResponse.UpdateDarkhastFaktorWithJSONResult;
import com.saphamrah.protos.InvoiceInsertControlGrpc;
import com.saphamrah.protos.InvoiceInsertControlReply;
import com.saphamrah.protos.InvoiceInsertControlRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemporaryRequestsListModel implements TemporaryRequestsListMVP.ModelOps
{


    private TemporaryRequestsListMVP.RequiredPresenterOps mPresenter;

    private CompositeDisposable compositeDisposable;
    private Handler handler;

    public TemporaryRequestsListModel(TemporaryRequestsListMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getMyIP()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    URL url = new URL("http://checkip.amazonaws.com");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String ip = bufferedReader.readLine();
                    bufferedReader.close();
                    Log.d("ip" , "my ip : " + ip);
                    if (ip != null && !ip.trim().equals(""))
                    {
                        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
                        serverIPShared.remove(serverIPShared.DEVICE_IP());
                        serverIPShared.putString(serverIPShared.DEVICE_IP() , ip);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
	
    @Override
    public void getTemporaryRequests()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeForoshandeh = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);

        CustomerDarkhastFaktorDAO customerDarkhastFaktorDAO = new CustomerDarkhastFaktorDAO(mPresenter.getAppContext());
        ArrayList<CustomerDarkhastFaktorModel> customerDarkhastFaktorModels = customerDarkhastFaktorDAO.getAllDarkhastFaktor();
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        int showReceiptImageIcon = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_Can_Get_Image_Customer_Confirm_Request()));
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if (noeMasouliat != MAMOURPAKHSH_SARD && noeMasouliat!= MAMOURPAKHSH_SMART){
            showReceiptImageIcon = 0;
        }
        mPresenter.onGetTemporaryRequests(customerDarkhastFaktorModels , noeForoshandeh, showReceiptImageIcon == 1);
    }


    @Override
    public void getTemporaryNoRequests()
    {
        CustomerAdamDarkhastDAO customerAdamDarkhastDAO = new CustomerAdamDarkhastDAO(mPresenter.getAppContext());
        ArrayList<CustomerAdamDarkhastModel> models = customerAdamDarkhastDAO.getAllForSendToServer();
        mPresenter.onGetTemporaryNoRequests(models);
    }

private void deleteAllTempRequest(long ccDarkhastFaktor, int ccMoshtary){

    Message message = new Message();
    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
    DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
    DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
    DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
    DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO =new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
    DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
    DarkhastFaktorAfradForoshDAO darkhastFaktorAfradForoshDAO = new DarkhastFaktorAfradForoshDAO(mPresenter.getAppContext());
    DarkhastFaktorRoozSortDAO darkhastFaktorRoozSortDAO = new DarkhastFaktorRoozSortDAO(mPresenter.getAppContext());
    ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
    ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
    ElamMarjoeeSatrPPCTedadDAO elamMarjoeeSatrPPCTedadDAO = new ElamMarjoeeSatrPPCTedadDAO(mPresenter.getAppContext());
    DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
    KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
    GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());


    Thread thread = new Thread() {
        @Override
        public void run() {
          boolean darkhastFaktor =   darkhastFaktorDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean darkhastFaktorSatrTakhfif =   darkhastFaktorSatrTakhfifDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean darkhastFaktorSatr =     darkhastFaktorSatrDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean darkhastFaktorTakhfif =     darkhastFaktorTakhfifDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean darkhastFaktorEmzaMoshtary =     darkhastFaktorEmzaMoshtaryDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean darkhastFaktorAfradForosh =     darkhastFaktorAfradForoshDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean darkhastFaktorRoozSort =     darkhastFaktorRoozSortDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean elamMarjoeePPC =     elamMarjoeePPCDAO.deleteAllByccDarkhastFaktor(ccDarkhastFaktor);
          boolean elamMarjoeeSatrPPC =     elamMarjoeeSatrPPCDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean elamMarjoeeSatrPPCTedad =     elamMarjoeeSatrPPCTedadDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean dariaftPardakhtPPC =     dariaftPardakhtPPCDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean darkhastFaktorJayezeh =     darkhastFaktorJayezehDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean dariaftPardakhtDarkhastFaktorPPC =     dariaftPardakhtDarkhastFaktorPPCDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
          boolean kalaMojodi =     kalaMojodiDAO.deleteAllByccDarkhastFaktor(ccDarkhastFaktor);
          boolean gpsDataPpc =     gpsDataPpcDAO.deleteByccDarkhastFaktorAndccMoshtary(ccDarkhastFaktor, ccMoshtary);

          if(darkhastFaktor && darkhastFaktorSatrTakhfif && darkhastFaktorSatr && darkhastFaktorTakhfif && darkhastFaktorEmzaMoshtary && darkhastFaktorAfradForosh && darkhastFaktorRoozSort &&
                  elamMarjoeePPC && elamMarjoeeSatrPPC && elamMarjoeeSatrPPCTedad && dariaftPardakhtPPC && darkhastFaktorJayezeh && dariaftPardakhtDarkhastFaktorPPC
                  && kalaMojodi && gpsDataPpc) {

              message.what = 1;
          }
          else
          {
              message.what = 0;
          }
            handler.sendMessage(message);
        }
    };
    thread.start();


}



    @Override
    public void deleteTempRequest(final int position , final CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch(serverIpModel.getWebServiceType()){
            case REST:
                controlInsertFaktor(customerDarkhastFaktorModel.getUniqID_Tablet(), String.valueOf(customerDarkhastFaktorModel.getCcMoshtary()), String.valueOf(customerDarkhastFaktorModel.getCcForoshandeh()), new OnControlFaktor() {
                    @Override
                    public void onError(int resErrorId) {
                        mPresenter.onError(resErrorId);
                    }

                    @Override
                    public void onSuccess(int flag) {
                        if (flag == 0)
                        {
                            deleteAllTempRequest(customerDarkhastFaktorModel.getCcDarkhastFaktor(), customerDarkhastFaktorModel.getCcMoshtary());
                            handler = new Handler(new Handler.Callback() {
                                @Override
                                public boolean handleMessage(Message msg) {
                                    if (msg.what == 1) {
                                        mPresenter.onSuccessDeleteTempRequest(position);
                                    } else if (msg.what == 0) {
                                        mPresenter.onError(R.string.errorOperation);                                    }
                                    return false;
                                }
                            });


//                            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
//                            if(darkhastFaktorDAO.deleteByccDarkhastFaktor(customerDarkhastFaktorModel.getCcDarkhastFaktor()))
//                            {
//                                mPresenter.onSuccessDeleteTempRequest(position);
//                            }
//                            else
//                            {
//                                mPresenter.onError(R.string.errorOperation);
//                            }
                        }
                        else
                        {
                            mPresenter.onError(R.string.errorDeleteSenededItem);
                        }
                    }
                });
                break;

            case gRPC:
                controlInsertFaktorGrpc(customerDarkhastFaktorModel.getUniqID_Tablet(), String.valueOf(customerDarkhastFaktorModel.getCcMoshtary()), String.valueOf(customerDarkhastFaktorModel.getCcForoshandeh()), new OnControlFaktor() {
                    @Override
                    public void onError(int resErrorId) {
                        mPresenter.onError(resErrorId);
                    }

                    @Override
                    public void onSuccess(int flag) {
                        if (flag == 0)
                        {
                            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                            if(darkhastFaktorDAO.deleteByccDarkhastFaktor(customerDarkhastFaktorModel.getCcDarkhastFaktor()))
                            {
                                mPresenter.onSuccessDeleteTempRequest(position);
                            }
                            else
                            {
                                mPresenter.onError(R.string.errorOperation);
                            }
                        }
                        else
                        {
                            mPresenter.onError(R.string.errorDeleteSenededItem);
                        }
                    }
                });
                break;
        }

    }


//    @Override
//    public void sendTempRequest(final int position , final CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
//    {
//        if (checkDateTime())
//        {
//            /*ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
//            int noeMasouliat = new PubFunc().new ForoshandehMamorPakhsh().getNoeMasouliat(foroshandehMamorPakhshModel);*/
//            /*if (noeMasouliat != 5)
//            {*/
//                controlInsertFaktor(customerDarkhastFaktorModel.getUniqID_Tablet(), String.valueOf(customerDarkhastFaktorModel.getCcMoshtary()), String.valueOf(customerDarkhastFaktorModel.getCcForoshandeh()), new OnControlFaktor() {
//                    @Override
//                    public void onError(int resErrorId) {
//                        mPresenter.onError(resErrorId);
//                    }
//
//                    @Override
//                    public void onSuccess(int flag) {
//                        if (flag == 1)
//                        {
//                            mPresenter.onErrorSendRequest(R.string.errorResend,"");
//                        }
//                        else
//                        {
//                            AsyncTaskSendRequest asyncTaskSendRequest = new AsyncTaskSendRequest(mPresenter.getAppContext(),customerDarkhastFaktorModel , position);
//                            asyncTaskSendRequest.sendRequestResponse = new SendRequestResponse() {
//                                @Override
//                                public void onError(int resId) {
//                                    mPresenter.onErrorSendRequest(resId,"");
//                                }
//
//                                @Override
//                                public void onSuccess(int position , long ccDarkhastFaktorNew) {
//                                    mPresenter.onSuccessSendRequest(position, ccDarkhastFaktorNew);
//
//
//                                }
//                            };
//                            asyncTaskSendRequest.execute();
//                        }
//                    }
//                });
//            /*}
//            else
//            {
//                AsyncTaskSendRequest asyncTaskSendRequest = new AsyncTaskSendRequest(mPresenter.getAppContext(),customerDarkhastFaktorModel , position);
//                asyncTaskSendRequest.sendRequestResponse = new SendRequestResponse() {
//                    @Override
//                    public void onError(int resId) {
//                        mPresenter.onErrorSendRequest(resId);
//                    }
//
//                    @Override
//                    public void onSuccess(int position , long ccDarkhastFaktorNew) {
//                        mPresenter.onSuccessSendRequest(position, ccDarkhastFaktorNew);
//                    }
//                };
//                asyncTaskSendRequest.execute();
//            }*/
//
//
//        }
//        else
//        {
//            mPresenter.onErrorSendRequest(R.string.errorTimeLimitForRequest,"");
//        }
//    }

    @Override
    public void sendTempRequest(final int position, final CustomerDarkhastFaktorModel customerDarkhastFaktorModel) {
        Log.i("sendTempRequest", "sendTempRequest: ");
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        int noeMasouliate = foroshandehMamorPakhshDAO.getIsSelect().getNoeMasouliat();
        Log.i("noeMasouliat", "sendTempRequest: " + noeMasouliate);
        checkMandehMojodi(customerDarkhastFaktorModel, noeMasouliate, position);


    }
    private boolean checkDateTime()
    {
        String startRestTime = "23:45";
        Date startDate;
        Date endTime;
        Date now = Calendar.getInstance().getTime();
        startRestTime = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CHECK_MOSHTARY_MASIR_ROOZ_CC_START_REST_TIME());
        try
        {
            String[] timeSection = startRestTime.split(":");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY , Integer.parseInt(timeSection[0]));
            calendar.set(Calendar.MINUTE , Integer.parseInt(timeSection[1]));
            startDate = calendar.getTime();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "checkDateTime", "");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY , 23);
            calendar.set(Calendar.MINUTE , 45);
            startDate = calendar.getTime();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY , 23);
        calendar.set(Calendar.MINUTE , 59);
        calendar.set(Calendar.MILLISECOND , 59);
        endTime = calendar.getTime();

        if (now.after(startDate) && now.before(endTime))
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    @Override
    public void saveImageTempRequest(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(customerDarkhastFaktorModel.getCcDarkhastFaktor(),customerDarkhastFaktorModel.getCcMoshtary());
        DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext()).getByccDarkhastFaktor(customerDarkhastFaktorModel.getCcDarkhastFaktor()).get(0);

        DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = darkhastFaktorEmzaMoshtaryDAO.getByccDarkhastFaktor(customerDarkhastFaktorModel.getCcDarkhastFaktor());
        boolean hasReceiptImage = darkhastFaktorEmzaMoshtaryModel.getHave_ReceiptImage() == 1;

        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        boolean receiptImageObligation = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_Require_Image_Customer_Confirm_Request())) == 1;
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshDAO.getIsSelect());
        if (noeMasouliat == MAMOURPAKHSH_SARD || noeMasouliat == MAMOURPAKHSH_SMART)
        {
            if (receiptImageObligation && !hasReceiptImage){
                mPresenter.onErrorSaveImage();
            }else{
                mPresenter.onCheckSaveImage(customerDarkhastFaktorModel.getCcDarkhastFaktor(),darkhastFaktorModel.getCcDarkhastFaktorNoeForosh(),customerDarkhastFaktorModel.getCcMoshtary());
            }

        }
        else
        mPresenter.onCheckSaveImage(customerDarkhastFaktorModel.getCcDarkhastFaktor(),darkhastFaktorModel.getCcDarkhastFaktorNoeForosh(),customerDarkhastFaktorModel.getCcMoshtary());

    }

    @Override
    public void printTempRequest(final int position , final CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST:
                controlInsertFaktor(customerDarkhastFaktorModel.getUniqID_Tablet(), String.valueOf(customerDarkhastFaktorModel.getCcMoshtary()), String.valueOf(customerDarkhastFaktorModel.getCcForoshandeh()), new OnControlFaktor() {
                    @Override
                    public void onError(int resErrorId) {
                        mPresenter.onError(resErrorId);
                    }

                    @Override
                    public void onSuccess(int flag) {
                        mPresenter.onCheckPrint(customerDarkhastFaktorModel);

//                        if (flag == 1)
//                        {
//                            mPresenter.onCheckPrint(customerDarkhastFaktorModel);
//                        }
//                        else
//                        {
//                            mPresenter.onError(R.string.errorNotSendedToSQL);
//                        }
                    }
                });
                break;

            case gRPC:
                controlInsertFaktorGrpc(customerDarkhastFaktorModel.getUniqID_Tablet(), String.valueOf(customerDarkhastFaktorModel.getCcMoshtary()), String.valueOf(customerDarkhastFaktorModel.getCcForoshandeh()), new OnControlFaktor() {
                    @Override
                    public void onError(int resErrorId) {
                        mPresenter.onError(resErrorId);
                    }

                    @Override
                    public void onSuccess(int flag) {
                        if (flag == 1)
                        {
                            mPresenter.onCheckPrint(customerDarkhastFaktorModel);
                        }
                        else
                        {
                            mPresenter.onError(R.string.errorNotSendedToSQL);
                        }
                    }
                });
                break;
        }

    }

    @Override
    public void deleteTempNoRequest(int position , CustomerAdamDarkhastModel customerAdamDarkhastModel)
    {
        AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(mPresenter.getAppContext());
        if (adamDarkhastDAO.deleteByccAdamDarkhast(customerAdamDarkhastModel.getCcAdamDarkhast()))
        {
            mPresenter.onSuccessDeleteNoRequest(position);
        }
    }

    @Override
    public void sendTempNoRequest(final int position , final CustomerAdamDarkhastModel customerAdamDarkhastModel)
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        final AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(mPresenter.getAppContext());
        final AdamDarkhastModel adamDarkhastModel = adamDarkhastDAO.getByccAdamDarkhast(customerAdamDarkhastModel.getCcAdamDarkhast()).get(0);
        String jsonString = adamDarkhastModel.toJsonString(0 , foroshandehMamorPakhshModel.getCcMarkazForosh());
        Log.i("AdamDarkhast", "sendTempNoRequest: "+jsonString);
//        saveToFile("noRequest.txt" , jsonString);
//        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
//        String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
// , "");
//        String serverPort = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
// , "");

        ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        String serverIP=serverIpModel.getServerIp();
        String serverPort=serverIpModel.getPort();
        if (serverIP.trim().equals("") || serverPort.trim().equals(""))
        {
            mPresenter.onErrorSendRequest(R.string.errorFindServerIP,"");
        }
        else
        {
            //final APIServicePost apiServicePost = ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
            final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);
            Call<CreateAdamDarkhastResult> call = apiServicePost.createAdamDarkhast(jsonString);
            call.enqueue(new Callback<CreateAdamDarkhastResult>()
            {
                @Override
                public void onResponse(Call<CreateAdamDarkhastResult> call, Response<CreateAdamDarkhastResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            CreateAdamDarkhastResult result = response.body();
                            if (result.getSuccess())
                            {
                                if (adamDarkhastDAO.updateIsSentToServer(1 , customerAdamDarkhastModel.getCcAdamDarkhast()))
                                {
									setLastOlaviat(adamDarkhastModel.getCcMoshtary());												  
                                    checkOtherData(apiServicePost , customerAdamDarkhastModel.getCcMoshtary());
                                    mPresenter.onSuccessSendNoRequest(position);
                                }
                            }
                            else
                            {
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendTempNoRequest" , "onResponse");
                                mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendTempNoRequest" , "onResponse");
                            mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendTempNoRequest" , "onResponse");
                        mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                    }
                }

                @Override
                public void onFailure(Call<CreateAdamDarkhastResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendTempNoRequest" , "onFailure");
                    mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                }
            });
        }
    }


    private void saveToFile(String fileName , String jsonStringData)
    {
        try
        {
            File path = mPresenter.getAppContext().getExternalFilesDir(null);
            File file = new File(path, fileName);
            FileOutputStream stream = new FileOutputStream(file);
            try
            {
                stream.write(jsonStringData.getBytes());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void setLastOlaviat(int ccMoshtary)
    {
        int olaviat = new MoshtaryDAO(mPresenter.getAppContext()).getExtraOlaviatByccMoshtary(ccMoshtary);
        LastOlaviatMoshtaryShared shared = new LastOlaviatMoshtaryShared(mPresenter.getAppContext());
        int lastOlaviat = shared.getInt(LastOlaviatMoshtaryShared.OLAVIAT, -1);
        if (olaviat > lastOlaviat)
        {
            shared.putInt(LastOlaviatMoshtaryShared.OLAVIAT, olaviat);
            shared.putInt(LastOlaviatMoshtaryShared.CCMOSHTARY, ccMoshtary);
            shared.putString(LastOlaviatMoshtaryShared.TARIKH, new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
        }
    }
	
    /**
     * ارسال برخورد اولیه بر اساس کد مشتری، ارسال موجودی گیری بر اساس کد مشتری، ارسال اطلاعات GPS که ارسال نشده اند، ارسال لاگ هایی که ارسال نشده اند.
     * این متد بعد از ارسال درخواست و ارسال عدم درخواست فراخوانی میشود.
     * @param apiServicePost
     * @param ccMoshtary
     */
    private void checkOtherData(APIServicePost apiServicePost , int ccMoshtary)
    {
        BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhords = barkhordForoshandehBaMoshtaryDAO.getAllByccMoshtaryAndNotSend(ccMoshtary);
        if (barkhords.size() > 0)
        {
            sendBarkhordsToServer(apiServicePost , barkhords);
        }

        MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
        ArrayList<MojoodiGiriModel> mojoodiGiriModels = mojoodiGiriDAO.getByccMoshtaryAndNotOld(ccMoshtary);
        if (mojoodiGiriModels.size() > 0)
        {
            sendMojoodigiriToServer(apiServicePost , mojoodiGiriModels);
        }

        GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
        ArrayList<GPSDataModel> gpsDataModels = gpsDataPpcDAO.getAllNotSend();
        if (gpsDataModels.size() > 0)
        {
            sendGPSDataToServer(apiServicePost , gpsDataModels);
        }


        LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
        ArrayList<LogPPCModel> logPPCModels = logPPCDAO.getUnsendExceptionsOrderByIdDesc();
        if (logPPCModels.size() > 0)
        {
            sendLogPPCToServer(apiServicePost , logPPCModels);
        }

        SuggestDAO suggestDAO = new SuggestDAO(mPresenter.getAppContext());
        ArrayList<SuggestModel> suggestModels = suggestDAO.getAllSuggestIsNotSend();
        if (suggestModels.size() > 0)
        {
            sendSuggest(apiServicePost ,suggestModels,suggestDAO);
        }

        DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = darkhastFaktorEmzaMoshtaryDAO.getNotSendReceiptImage();
        if (darkhastFaktorEmzaMoshtaryModels.size() > 0)
        {
            sendReceiptImage(darkhastFaktorEmzaMoshtaryModels,darkhastFaktorEmzaMoshtaryDAO);
        }
    }

    private void sendReceiptImage( ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels, DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());

        DarkhastFaktorEmzaMoshtaryRepository darkhastFaktorEmzaMoshtaryRepository = new DarkhastFaktorEmzaMoshtaryRepository(mPresenter.getAppContext());

        for (DarkhastFaktorEmzaMoshtaryModel model : darkhastFaktorEmzaMoshtaryModels) {
            darkhastFaktorEmzaMoshtaryRepository.sendReceiptImageRx(serverIpModel, mPresenter.getAppContext(), "TemporaryRequestListActivity", model.getReceiptImage(), model.getCcDarkhastFaktor())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Observer<String>() {
                  @Override
                  public void onSubscribe(@androidx.annotation.NonNull Disposable d) {
                      compositeDisposable.add(d);
                  }

                  @Override
                  public void onNext(@androidx.annotation.NonNull String ccDarkhastFaktor) {
                      Log.i("sendReceiptImageRx", "onNext: "+ccDarkhastFaktor);
                      if (!ccDarkhastFaktor.equals("")){
                          darkhastFaktorEmzaMoshtaryRepository.updateIsSendReceiptImage(Long.parseLong(ccDarkhastFaktor));
                      }else{
                          onError(new Throwable());
                      }

                  }

                  @Override
                  public void onError(@androidx.annotation.NonNull Throwable e) {

                      mPresenter.onError(R.string.errorUpdateReceiptImage);
                  }

                  @Override
                  public void onComplete() {

                  }
              });
        }
    }

    private void sendBarkhordsToServer(APIServicePost apiServicePost , ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhords)
    {
        final ArrayList<Integer> arrayListUpdatedccBarkhords = new ArrayList<>();
        for (final BarkhordForoshandehBaMoshtaryModel model : barkhords)
        {
            String jsonString = model.toJsonString(model);
            Call<CreateBarkhordForoshandehBaMoshtaryResult> call = apiServicePost.createBarkhordForoshandehBaMoshtary(jsonString);
            call.enqueue(new Callback<CreateBarkhordForoshandehBaMoshtaryResult>()
            {
                @Override
                public void onResponse(Call<CreateBarkhordForoshandehBaMoshtaryResult> call, Response<CreateBarkhordForoshandehBaMoshtaryResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateBarkhordForoshandehBaMoshtaryResult result = response.body();
                            if (result.getSuccess())
                            {
                                arrayListUpdatedccBarkhords.add(model.getCcBarkhordForoshandeh());
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendBarkhordsToServer" , "onResponse");
                                mPresenter.onError(R.string.errorSendBarkhordMoshtary);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendBarkhordsToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onError(R.string.errorSendBarkhordMoshtary);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendBarkhordsToServer" , "onResponse");
                        mPresenter.onError(R.string.errorSendBarkhordMoshtary);
                    }
                }

                @Override
                public void onFailure(Call<CreateBarkhordForoshandehBaMoshtaryResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendBarkhordsToServer" , "onFailure");
                    mPresenter.onError(R.string.errorSendBarkhordMoshtary);                }
            });
        }

        String updatedccBarkhords = "";
        for (Integer ccBarkhord : arrayListUpdatedccBarkhords)
        {
            updatedccBarkhords += "," + ccBarkhord;
        }
        if (updatedccBarkhords.startsWith(","))
        {
            updatedccBarkhords = updatedccBarkhords.substring(1);
        }
        BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        barkhordForoshandehBaMoshtaryDAO.updateGroupSendToServer(updatedccBarkhords);
    }

    private void sendMojoodigiriToServer(APIServicePost apiServicePost , ArrayList<MojoodiGiriModel> mojoodiGiriModels)
    {
        final ArrayList<Integer> arrayListUpdatedccMojoodiGiri = new ArrayList<>();
        for (final MojoodiGiriModel model : mojoodiGiriModels)
        {
            String jsonString = model.toJsonString();
            Call<CreateMojoodiGiriResult> call = apiServicePost.createMojoodiGiri(jsonString);
            call.enqueue(new Callback<CreateMojoodiGiriResult>()
            {
                @Override
                public void onResponse(Call<CreateMojoodiGiriResult> call, Response<CreateMojoodiGiriResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateMojoodiGiriResult result = response.body();
                            if (result.getSuccess())
                            {
                                arrayListUpdatedccMojoodiGiri.add(model.getCcMojoodiGiri());
                                insertMoshtaryMorajehShodehRooz(model.getCcForoshandeh(),model.getCcMoshtary(),2,0,0);
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendMojoodigiriToServer" , "onResponse");
                                mPresenter.onError(R.string.errorSendMojodiGiri);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendMojoodigiriToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onError(R.string.errorSendMojodiGiri);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendMojoodigiriToServer" , "onResponse");
                        mPresenter.onError(R.string.errorSendMojodiGiri);
                    }
                }

                @Override
                public void onFailure(Call<CreateMojoodiGiriResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendMojoodigiriToServer" , "onFailure");
                    mPresenter.onError(R.string.errorSendMojodiGiri);
                }
            });
        }

        String updatedccMojoodi = "";
        for (Integer ccMojoodi : arrayListUpdatedccMojoodiGiri)
        {
            updatedccMojoodi += "," + ccMojoodi;
        }
        if (updatedccMojoodi.startsWith(","))
        {
            updatedccMojoodi = updatedccMojoodi.substring(1);
        }
        MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
        mojoodiGiriDAO.updateSentToServer(updatedccMojoodi);
    }

    private void sendGPSDataToServer(APIServicePost apiServicePost , ArrayList<GPSDataModel> gpsDataModels)
    {
        JSONArray jsonArray = new JSONArray();
        String ccGPSDatas = "-1";
        for (GPSDataModel model : gpsDataModels)
        {
            JSONObject jsonObject = model.toJsonObject();
            if (jsonObject != null)
            {
                jsonArray.put(jsonObject);
                ccGPSDatas += "," + model.getCcGpsData_PPC();
            }
        }
        //به دلیل اینکه برای استفاده از شناسه موقعیت مکانی های ارسال شده به سرور در کالبک باید متغییر final داشته باشیم و اگر ccGPSDatas را final تعریف کنیم سپس امکان افزودن شناسه های جدید را نداریم، از این فیلد استفاده میکنیم.
        final String ccGPSDatasFinal = ccGPSDatas;
        if (jsonArray.length() > 0)
        {
            Call<CreateGpsDataPPCResult> call = apiServicePost.createGpsDataPPCResult(jsonArray.toString());
            call.enqueue(new Callback<CreateGpsDataPPCResult>()
            {
                @Override
                public void onResponse(Call<CreateGpsDataPPCResult> call, Response<CreateGpsDataPPCResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateGpsDataPPCResult result = response.body();
                            if (result.getSuccess())
                            {
                                GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
                                gpsDataPpcDAO.updateIsSend(ccGPSDatasFinal);
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendGPSDataToServer" , "onResponse");
                                mPresenter.onError(R.string.errorSendGpsData);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendGPSDataToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onError(R.string.errorSendGpsData);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendGPSDataToServer" , "onResponse");
                        mPresenter.onError(R.string.errorSendGpsData);
                    }
                }

                @Override
                public void onFailure(Call<CreateGpsDataPPCResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendGPSDataToServer" , "onFailure");
                    mPresenter.onError(R.string.errorSendGpsData);
                }
            });
        }


        /*final ArrayList<Integer> arrayListUpdatedccGPSData = new ArrayList<>();
        for (final GPSDataModel model : gpsDataModels)
        {
            String jsonString = model.toJsonString();
            Call<CreateGpsDataPPCResult> call = apiServicePost.createGpsDataPPCResult(jsonString);
            call.enqueue(new Callback<CreateGpsDataPPCResult>()
            {
                @Override
                public void onResponse(Call<CreateGpsDataPPCResult> call, Response<CreateGpsDataPPCResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateGpsDataPPCResult result = response.body();
                            if (result.getSuccess())
                            {
                                arrayListUpdatedccGPSData.add(model.getCcGpsData_PPC());
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendGPSDataToServer" , "onResponse");
                                mPresenter.onErrorSendRequest(R.string.errorOperation);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendGPSDataToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendRequest(R.string.errorOperation);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendGPSDataToServer" , "onResponse");
                        mPresenter.onErrorSendRequest(R.string.errorOperation);
                    }
                }

                @Override
                public void onFailure(Call<CreateGpsDataPPCResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendGPSDataToServer" , "onFailure");
                    mPresenter.onErrorSendRequest(R.string.errorOperation);
                }
            });
        }
        String updatedccGPSData = "";
        for (Integer ccGPSData : arrayListUpdatedccGPSData)
        {
            updatedccGPSData += "," + ccGPSData;
        }
        if (updatedccGPSData.startsWith(","))
        {
            updatedccGPSData = updatedccGPSData.substring(1);
        }
        if (updatedccGPSData.trim().length() > 0)
        {
            GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
            gpsDataPpcDAO.updateIsSend(updatedccGPSData);
        }*/
    }



    private void sendSuggest(APIServicePost apiServicePost , ArrayList<SuggestModel> suggestModels,SuggestDAO suggestDAO)
    {
        for (SuggestModel model : suggestModels)
        {
            String jsonString = model.toJsonString();
            Call<SuggestResult> call = apiServicePost.createSuggestResult(jsonString);
            call.enqueue(new Callback<SuggestResult>()
            {
                @Override
                public void onResponse(Call<SuggestResult> call, Response<SuggestResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            SuggestResult result = response.body();
                            if (result.getSuccess())
                            {
                                suggestDAO.updateIsSend(model.getCcSuggest());
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendSuggest" , "onResponse");
                                mPresenter.onError(R.string.errorSendSuggest);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendSuggest" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onError(R.string.errorSendSuggest);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendSuggest" , "onResponse");
                        mPresenter.onError(R.string.errorSendSuggest);
                    }
                }

                @Override
                public void onFailure(Call<SuggestResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendSuggest" , "onFailure");
                    mPresenter.onError(R.string.errorSendSuggest);
                }
            });
        }

    }

    private void sendLogPPCToServer(APIServicePost apiServicePost , ArrayList<LogPPCModel> logPPCModels)
    {
        JSONArray jsonArray = new JSONArray();
        String ccLogs = "-1";
		ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String ip = serverIPShared.getString(serverIPShared.DEVICE_IP() , "");																  
        for (LogPPCModel model : logPPCModels)
        {
            JSONObject jsonObject = model.toJsonObject(ip);
            if (jsonObject != null)
            {
                jsonArray.put(jsonObject);
                ccLogs += "," + model.getCcLogPPC();
            }
        }
        final String ccLogsFinal = ccLogs;

        if (jsonArray.length() > 0)
        {
            Call<CreateLogPPCResult> call = apiServicePost.createLogPPC(jsonArray.toString());
            call.enqueue(new Callback<CreateLogPPCResult>()
            {
                @Override
                public void onResponse(Call<CreateLogPPCResult> call, Response<CreateLogPPCResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateLogPPCResult result = response.body();
                            if (result.getSuccess())
                            {
                                LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
                                logPPCDAO.updateExtraProp_IsOld(ccLogsFinal , 1);
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendLogPPCToServer" , "onResponse");
                                mPresenter.onError(R.string.errorSendLogData);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendLogPPCToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onError(R.string.errorSendLogData);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendLogPPCToServer" , "onResponse");
                        mPresenter.onError(R.string.errorSendLogData);
                    }
                }

                @Override
                public void onFailure(Call<CreateLogPPCResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendLogPPCToServer" , "onFailure");
                    mPresenter.onError(R.string.errorSendLogData);
                }
            });
        }

        /*final ArrayList<Integer> arrayListUpdatedccLogPPC = new ArrayList<>();
        for (final LogPPCModel model : logPPCModels)
        {
            String jsonString = model.toJsonString();
            Call<CreateLogPPCResult> call = apiServicePost.createLogPPC(jsonString);
            call.enqueue(new Callback<CreateLogPPCResult>()
            {
                @Override
                public void onResponse(Call<CreateLogPPCResult> call, Response<CreateLogPPCResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateLogPPCResult result = response.body();
                            if (result.getSuccess())
                            {
                                arrayListUpdatedccLogPPC.add(model.getCcLogPPC());
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendLogPPCToServer" , "onResponse");
                                mPresenter.onErrorSendRequest(R.string.errorOperation);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendLogPPCToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendRequest(R.string.errorOperation);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendLogPPCToServer" , "onResponse");
                        mPresenter.onErrorSendRequest(R.string.errorOperation);
                    }
                }

                @Override
                public void onFailure(Call<CreateLogPPCResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendLogPPCToServer" , "onFailure");
                    mPresenter.onErrorSendRequest(R.string.errorOperation);
                }
            });
        }
        String updatedccLogPPC = "";
        for (Integer ccMojoodi : arrayListUpdatedccLogPPC)
        {
            updatedccLogPPC += "," + ccMojoodi;
        }
        if (updatedccLogPPC.startsWith(","))
        {
            updatedccLogPPC = updatedccLogPPC.substring(1);
        }
        LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
        logPPCDAO.updateExtraProp_IsOld(updatedccLogPPC , 1);*/
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public void insertReceiptImage(byte[] imageBytes,int position ,CustomerDarkhastFaktorModel customerDarkhastFaktorModel) {
        DarkhastFaktorEmzaMoshtaryRepository darkhastFaktorEmzaMoshtaryRepository = new DarkhastFaktorEmzaMoshtaryRepository(mPresenter.getAppContext());
        darkhastFaktorEmzaMoshtaryRepository.updateReceiptImageByccDarkhastFaktor(customerDarkhastFaktorModel.getCcDarkhastFaktor(),imageBytes)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@androidx.annotation.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@androidx.annotation.NonNull Boolean updated) {
                        if (updated){
                            mPresenter.onSuccessSaveReceiptImage(R.string.successfullyDoneOps,position);
                        }else{
                            onError(new Throwable());
                        }
                    }

                    @Override
                    public void onError(@androidx.annotation.NonNull Throwable e) {
                        mPresenter.onError(R.string.errorUpdate);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void sendDarkhastFaktorDataToServer(final int ccMoshtary, final long ccDarkhastFaktorOld, String jsonStringData , final int noeMasouliat , final int position , final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
        final ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels = elamMarjoeePPCDAO.getByccDarkhastFaktor(ccDarkhastFaktorOld);

//        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
//        String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
// , "");
//        String serverPort = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
// , "");
        ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        String serverIP=serverIpModel.getServerIp();
        String serverPort=serverIpModel.getPort();
        if (serverIP.trim().equals("") || serverPort.trim().equals(""))
        {
            //sendRequestResponse.onError(R.string.errorFindServerIP);
            mPresenter.onErrorSendRequest(R.string.errorFindServerIP,"");
        }
        else
        {
            final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);//ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
            Call<CreateDarkhastFaktorWithVosol> call = apiServicePost.createDarkhastFaktorWithVosol(jsonStringData);
            call.enqueue(new Callback<CreateDarkhastFaktorWithVosol>()
            {
                @Override
                public void onResponse(Call<CreateDarkhastFaktorWithVosol> call, Response<CreateDarkhastFaktorWithVosol> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("Temp" , "in if success and body not null");
                            CreateDarkhastFaktorWithVosol result = response.body();
                            if (result.getSuccess())
                            {
                                Log.d("sendData" , "send DarkhastFaktor success to server");
                                long ccDarkhastFaktorNew = Integer.parseInt(result.getMessage());
                                updateElamMarjoeeccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
                                for (ElamMarjoeePPCModel model : elamMarjoeePPCModels)
                                {
                                    model.setCcDarkhastFaktor(ccDarkhastFaktorNew);
                                }
                                //insertDarkhastFaktorAfradForosh(noeMasouliat , ccDarkhastFaktorNew , foroshandehMamorPakhshModel.getCcAfrad());
                                checkOtherData(apiServicePost , ccMoshtary);
                                sendAllAdamDarkhastForCustomerToServer(ccMoshtary, apiServicePost, foroshandehMamorPakhshModel.getCcMarkazForosh());
                                //sendElamMarjoeePPCToServer(apiServicePost , elamMarjoeePPCModels, foroshandehMamorPakhshModel.getCcMarkazForosh(), foroshandehMamorPakhshModel.getNoeForoshandehMamorPakhsh(), foroshandehMamorPakhshModel.getCcAfrad() , ccDarkhastFaktorNew);
                                //sendDarkhastFaktorAfradforoshToServer(ccDarkhastFaktorNew,apiServicePost,position);
                                updateccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
                            }
                            else
                            {
                                Log.d("Temp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                                //sendRequestResponse.onError(R.string.errorOperation);
                                mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                            //sendRequestResponse.onError(R.string.errorOperation);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("Temp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                        mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                        //sendRequestResponse.onError(R.string.errorOperation);
                    }
                }

                @Override
                public void onFailure(Call<CreateDarkhastFaktorWithVosol> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onFailure");
                    mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                    //sendRequestResponse.onError(R.string.errorOperation);
                }
            });
        }
    }


    private void updateElamMarjoeeccDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew)
    {
        ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
        elamMarjoeePPCDAO.updateccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);

        ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
        elamMarjoeeSatrPPCDAO.updateccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
    }

    private void updateccDarkhastFaktor(long ccDarkhastFaktorOld , long ccDarkhastFaktorNew)
    {
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        darkhastFaktorDAO.updateSendedDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew , 1);

        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        darkhastFaktorSatrDAO.updateSendedDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew , 1);

        DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
        darkhastFaktorTakhfifDAO.updateSendedDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);

        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
        darkhastFaktorJayezehDAO.updateSendedDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);

        DariaftPardakhtPPCDAO dpPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        dpPPCDAO.updateSendedDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew , 1);

        DariaftPardakhtDarkhastFaktorPPCDAO dpDFDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        dpDFDAO.updateSendedDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew , 1);

        DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
        darkhastFaktorEmzaMoshtaryDAO.updateSendedccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
    }

    private DarkhastFaktorAfradForoshModel insertDarkhastFaktorAfradForosh(int noeMasouliat, int ccDarkhastFaktorNoeForosh, long ccDarkhastFaktor , int ccForoshandehDarkhastFaktorModel , int ccAfradForoshandehMamorPakhsh, int ccAfradModir)
    {
        DarkhastFaktorAfradForoshDAO darkhastFaktorAfradForoshDAO = new DarkhastFaktorAfradForoshDAO(mPresenter.getAppContext());
        DarkhastFaktorAfradForoshModel darkhastFaktorAfradForoshModel = new DarkhastFaktorAfradForoshModel();
        darkhastFaktorAfradForoshDAO.deleteAll();
        //darkhastFaktorAfradForoshModel.setCcDarkhastFaktor(Long.valueOf(response_DarkhastFaktor.Message));
        darkhastFaktorAfradForoshModel.setCcDarkhastFaktor(ccDarkhastFaktor); // must be new ccDarkhastFaktor
        int ccAfradForoshandeh = 0;
        int ccAfradMamorPakhsh = 0;
        int ccAfradRanandeh = 0;
        if(noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART || (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD && ccDarkhastFaktorNoeForosh == Constants.ccNoeFaktor))
        {
            AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
            ccAfradForoshandeh = ccAfradForoshandehMamorPakhsh;
            ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakAfradDAO.getByccAfradForoshandeh(ccAfradForoshandeh);
            if (anbarakAfradModels.size() > 0)
            {
                ccAfradRanandeh = anbarakAfradModels.get(0).getCcAfradRanandeh();
            }

            darkhastFaktorAfradForoshModel.setCcAfradForoshandeh(ccAfradForoshandeh);
            darkhastFaktorAfradForoshModel.setCcAfradForoshandehJaygozin(ccAfradForoshandeh);
            darkhastFaktorAfradForoshModel.setCcAfradRanandeh(ccAfradRanandeh);
            darkhastFaktorAfradForoshModel.setCcAfradGorohForosh(ccAfradModir);
        }
        else if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD)//mamorpakhsh sard
        {
            ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());
            AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
            ccAfradForoshandeh = foroshandehDAO.getByccForoshande(ccForoshandehDarkhastFaktorModel).getCcAfradForoshandeh();
            ccAfradMamorPakhsh = ccAfradForoshandehMamorPakhsh;

            //Log.d("send" , "ccAfradForoshandehMamorPakhsh : " + anbarakAfradDAO.getByccAfradMamorPakhsh(ccAfradMamorPakhsh));
            ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakAfradDAO.getByccAfradMamorPakhsh(ccAfradMamorPakhsh);
            if (anbarakAfradModels.size() > 0)
            {
                ccAfradRanandeh = anbarakAfradModels.get(0).getCcAfradRanandeh();
            }
            darkhastFaktorAfradForoshModel.setCcAfradMamorPakhsh(ccAfradMamorPakhsh);
            darkhastFaktorAfradForoshModel.setCcAfradForoshandeh(ccAfradForoshandeh);
            darkhastFaktorAfradForoshModel.setCcAfradRanandeh(ccAfradRanandeh);
            darkhastFaktorAfradForoshModel.setCcAfradGorohForosh(ccAfradModir);
        }
        Log.d("tempRequest","AfradForosh : " + darkhastFaktorAfradForoshModel.toString());
        darkhastFaktorAfradForoshDAO.insert(darkhastFaktorAfradForoshModel);
        return darkhastFaktorAfradForoshModel;
    }

    private void insertMoshtaryMorajehShodehRooz(int ccForoshandeh, int ccMoshtary, int NoeMorajeh, int EtebarEzafehShodeh , int MablaghMandehFaktor) {

        MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(mPresenter.getAppContext());
        MoshtaryMorajehShodehRoozModel moshtaryMorajehShodehRoozModel = new MoshtaryMorajehShodehRoozModel();
        moshtaryMorajehShodehRoozModel.setCcForoshandeh(ccForoshandeh);
        moshtaryMorajehShodehRoozModel.setCcMoshtary(ccMoshtary);
        moshtaryMorajehShodehRoozModel.setNoeMorajeh(NoeMorajeh);
        moshtaryMorajehShodehRoozModel.setEtebarEzafehShodeh(EtebarEzafehShodeh);
        moshtaryMorajehShodehRoozModel.setMablaghMandehFaktor(MablaghMandehFaktor);

        moshtaryMorajehShodehRoozDAO.deleteByccMoshtary(ccMoshtary);
        moshtaryMorajehShodehRoozDAO.insert(moshtaryMorajehShodehRoozModel);
    }

    private void sendAllAdamDarkhastForCustomerToServer(int ccMoshtary , APIServicePost apiServicePost , int ccMarkazForosh)
    {
        final AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(mPresenter.getAppContext());
        ArrayList<AdamDarkhastModel> adamDarkhastModels = adamDarkhastDAO.getByccMoshtary(ccMoshtary);
        for (final AdamDarkhastModel model : adamDarkhastModels)
        {
            String jsonString = model.toJsonString(0 , ccMarkazForosh);
            Call<CreateAdamDarkhastResult> call = apiServicePost.createAdamDarkhast(jsonString);
            call.enqueue(new Callback<CreateAdamDarkhastResult>()
            {
                @Override
                public void onResponse(Call<CreateAdamDarkhastResult> call, Response<CreateAdamDarkhastResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateAdamDarkhastResult result = response.body();
                            if (result.getSuccess())
                            {
                                Log.d("sendData" , "send AllAdamDarkhast success to server");

                                adamDarkhastDAO.updateIsSentToServer(1 , model.getCcAdamDarkhast());
                                insertMoshtaryMorajehShodehRooz(model.getCcForoshandeh(),model.getCcMoshtary(),1,0,0);
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendAllAdamDarkhastForCustomerToServer" , "onResponse");
                                mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendAllAdamDarkhastForCustomerToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendAllAdamDarkhastForCustomerToServer" , "onResponse");
                        mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                    }
                }

                @Override
                public void onFailure(Call<CreateAdamDarkhastResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendAllAdamDarkhastForCustomerToServer" , "onFailure");
                    mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                }
            });
        }

    }


    private void sendElamMarjoeeSatrPPCTedad(long ccDarkhastFaktor , APIServicePost apiServicePost)
    {
        ElamMarjoeeSatrPPCTedadDAO elamMarjoeeSatrPPCTedadDAO = new ElamMarjoeeSatrPPCTedadDAO(mPresenter.getAppContext());
        ArrayList<ElamMarjoeeSatrPPCTedadModel> elamMarjoeeSatrPPCTedadModels = elamMarjoeeSatrPPCTedadDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        for (ElamMarjoeeSatrPPCTedadModel model : elamMarjoeeSatrPPCTedadModels)
        {
            String jsonString = model.toJsonObject().toString().replace("\\" , "");
            Call<CreateElamMarjoeeSatrPPCTedadResult> call = apiServicePost.createElamMarjoeeSatrPPCTedadResult(jsonString);
            call.enqueue(new Callback<CreateElamMarjoeeSatrPPCTedadResult>()
            {
                @Override
                public void onResponse(Call<CreateElamMarjoeeSatrPPCTedadResult> call, Response<CreateElamMarjoeeSatrPPCTedadResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateElamMarjoeeSatrPPCTedadResult result = response.body();
                            if (result.getSuccess())
                            {
                                Log.d("sendData" , "send ElamMarjoeeSatrPPCTedad success to server");
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendElamMarjoeeSatrPPCTedad" , "onResponse");
                                mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendElamMarjoeeSatrPPCTedad" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendElamMarjoeeSatrPPCTedad" , "onResponse");
                        mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                    }
                }

                @Override
                public void onFailure(Call<CreateElamMarjoeeSatrPPCTedadResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendElamMarjoeeSatrPPCTedad" , "onFailure");
                    mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                }
            });
        }
    }


    private void sendDarkhastFaktorAfradforoshToServer(final long ccDarkhastFaktorNew , APIServicePost apiServicePost , final int position)
    {
        DarkhastFaktorAfradForoshDAO darkhastFaktorAfradForoshDAO = new DarkhastFaktorAfradForoshDAO(mPresenter.getAppContext());
        DarkhastFaktorAfradForoshModel darkhastFaktorAfradForoshModel = darkhastFaktorAfradForoshDAO.getByccDarkhastFaktor(ccDarkhastFaktorNew);
        if (darkhastFaktorAfradForoshModel.getCcDarkhastFaktor() > 0)
        {
            String jsonString = darkhastFaktorAfradForoshModel.toJsonObject().toString().replace("\\" , "");
            Call<CreateDarkhastFaktorAfradForoshResult> call = apiServicePost.createDarkhastFaktorAfradForosh(jsonString);
            call.enqueue(new Callback<CreateDarkhastFaktorAfradForoshResult>()
            {
                @Override
                public void onResponse(Call<CreateDarkhastFaktorAfradForoshResult> call, Response<CreateDarkhastFaktorAfradForoshResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateDarkhastFaktorAfradForoshResult result = response.body();
                            if (result.getSuccess())
                            {
                                Log.d("sendData" , "send DarkhastFaktorAfradforosh success to server");
                                mPresenter.onSuccessSendRequest(position , ccDarkhastFaktorNew);
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorAfradforoshToServer" , "onResponse");
                                mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendDarkhastFaktorAfradforoshToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorAfradforoshToServer" , "onResponse");
                        mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                    }
                }

                @Override
                public void onFailure(Call<CreateDarkhastFaktorAfradForoshResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorAfradforoshToServer" , "onFailure");
                    mPresenter.onErrorSendRequest(R.string.errorOperation,"");
                }
            });
        }
    }



    private interface OnControlFaktor
    {
        void onError(int resErrorId);
        void onSuccess(int flag);
    }

    private  boolean checkMablaghMandehFaktor(CustomerDarkhastFaktorModel customerDarkhastFaktorModel){
        boolean check = true;
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        double mablaghMandehFaktor = -1 ;
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
        int codeNoeVosolFaktor = darkhastFaktorDAO.getByccDarkhastFaktor(customerDarkhastFaktorModel.getCcDarkhastFaktor(),customerDarkhastFaktorModel.getCcMoshtary()).getCodeNoeVosolAzMoshtary();
        mablaghMandehFaktor = darkhastFaktorDAO.getMablaghMandeh(customerDarkhastFaktorModel.getCcDarkhastFaktor());


        if((noeMasouliat == 2 || noeMasouliat == 4 ||noeMasouliat == 5 )
        && (codeNoeVosolFaktor==Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD() || codeNoeVosolFaktor==Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK())
        && mablaghMandehFaktor>0)
        {
            check=false;
            mPresenter.onErrorSendRequest(R.string.errorRemainBiggerThanZeroForNagh, "");

        }
        return check;
    }
    private void controlInsertFaktorGrpc(String uniqID_Tablet , String ccMoshtary , String ccForoshandeh , final OnControlFaktor onControlFaktor)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());


            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, MoshtaryEtebarSazmanForoshDAO.class.getSimpleName(), TemporaryRequestsListActivity.class.getSimpleName(), "controlInsertFaktorGrpc", "");
                onControlFaktor.onError(R.string.cantFindServer);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                InvoiceInsertControlGrpc.InvoiceInsertControlBlockingStub invoiceInsertControlBlockingStub = InvoiceInsertControlGrpc.newBlockingStub(managedChannel);
                InvoiceInsertControlRequest invoiceInsertControlRequest = InvoiceInsertControlRequest.newBuilder().setCustomersID(Integer.parseInt(ccMoshtary)).setSalesManID(Integer.parseInt(ccForoshandeh)).setTabletUniqueID(uniqID_Tablet).build();
                Callable<InvoiceInsertControlReply> invoiceInsertControlReplyListCallable = () -> invoiceInsertControlBlockingStub.getInvoiceInsertControl(invoiceInsertControlRequest);
                RxAsync.makeObservable(invoiceInsertControlReplyListCallable)
                        .map(reply -> {

                                ControlInsertFaktorModel model = new ControlInsertFaktorModel();
                                model.setFlag(reply.getFlag());
                                model.setId(reply.getID());

                            return model;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ControlInsertFaktorModel>() {
                            @Override
                            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@androidx.annotation.NonNull ControlInsertFaktorModel model) {

                                onControlFaktor.onSuccess(model.getFlag());
                            }

                            @Override
                            public void onError(@androidx.annotation.NonNull Throwable e) {
                                e.printStackTrace();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), e.toString(), TemporaryRequestsListModel.class.getSimpleName(), TemporaryRequestsListActivity.class.getSimpleName(), "controlInsertFaktorGrpc", "CatchException");
                                onControlFaktor.onError(R.string.errorConnectServer);
                            }

                            @Override
                            public void onComplete() {
                                if (!managedChannel.isShutdown()) {
                                    managedChannel.shutdown();
                                }
                                if (!compositeDisposable.isDisposed()) {
                                    compositeDisposable.dispose();
                                }
                                compositeDisposable.clear();
                            }
                        });

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), exception.toString(), ModatVosolGorohDAO.class.getSimpleName(), TemporaryRequestsListActivity.class.getSimpleName(), "controlInsertFaktorGrpc", "CatchException");
            onControlFaktor.onError(R.string.errorException);
        }



    }

    private void controlInsertFaktor(String uniqID_Tablet , String ccMoshtary , String ccForoshandeh , final OnControlFaktor onControlFaktor)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, TemporaryRequestsListModel.class.getSimpleName(), "", "deleteTempRequest", "");
            //mPresenter.onErrorFindServer();
            onControlFaktor.onError(R.string.errorFindServerIP);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<ControlInsertFaktorResult> call = apiServiceGet.controlInsertFaktor(uniqID_Tablet, ccMoshtary, ccForoshandeh);
            call.enqueue(new Callback<ControlInsertFaktorResult>() {
                @Override
                public void onResponse(Call<ControlInsertFaktorResult> call, Response<ControlInsertFaktorResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TemporaryRequestsListModel.class.getSimpleName(), "", "deleteTempRequest", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            ControlInsertFaktorResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    onControlFaktor.onSuccess(result.getData().get(0).getFlag());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), result.getMessage(), TemporaryRequestsListModel.class.getSimpleName(), "", "deleteTempRequest", "onResponse");
                                    //mPresenter.onNetworkError(R.string.errorConnectServer);
                                    onControlFaktor.onError(R.string.errorConnectServer);
                                }
                            }
                            else
                            {
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), mPresenter.getAppContext().getResources().getString(R.string.resultIsNull), TemporaryRequestsListModel.class.getSimpleName(), "", "deleteTempRequest", "onResponse");
                                //mPresenter.onNetworkError(R.string.errorConnectServer);
                                onControlFaktor.onError(R.string.errorConnectServer);
                            }
                        }
                        else
                        {
                            String message = String.format("error body : %1$s , code : %2$s" , response.message() , response.code());
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, TemporaryRequestsListModel.class.getSimpleName(), "", "deleteTempRequest", "onResponse");
                            //mPresenter.onNetworkError(R.string.errorConnectServer);
                            onControlFaktor.onError(R.string.errorConnectServer);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), exception.toString(), TemporaryRequestsListModel.class.getSimpleName(), "", "deleteTempRequest", "onResponse");
                        //mPresenter.onNetworkError(R.string.errorConnectServer);
                        onControlFaktor.onError(R.string.errorConnectServer);
                    }
                }

                @Override
                public void onFailure(Call<ControlInsertFaktorResult> call, Throwable t)
                {
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), t.getMessage(), TemporaryRequestsListModel.class.getSimpleName(), "", "deleteTempRequest", "onFailure");
                    //mPresenter.onNetworkError(R.string.errorConnectServer);
                    onControlFaktor.onError(R.string.errorConnectServer);
                }
            });
        }
    }

    private interface SendRequestResponse
    {
        void onError(int resId);
        void onSuccess(int position , long ccDarkhastFaktorNew);
    }

    private class AsyncTaskSendRequest extends AsyncTask<Void , Void , Integer>
    {
        private CustomerDarkhastFaktorModel customerDarkhastFaktorModel;
        private int position;
        private SendRequestResponse sendRequestResponse;
        Context context;

        private AsyncTaskSendRequest(Context context , CustomerDarkhastFaktorModel customerDarkhastFaktorModel , int position)
        {
            super();
            this.customerDarkhastFaktorModel = customerDarkhastFaktorModel;
            this.position = position;
            this.context = context;
        }

        @Override
        protected Integer doInBackground(Void...params)
        {
            Log.d("time" , "start send temp : " + System.currentTimeMillis());
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            final int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);

            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
            long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
            int ccMoshtary = selectFaktorShared.getInt(selectFaktorShared.getCcMoshtary() , -1);

            int ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
            if(ccMarkazForosh == 0)
            {
                MarkazDAO markazDAO = new MarkazDAO(mPresenter.getAppContext());
                ccMarkazForosh = markazDAO.getAll().get(0).getCcMarkaz();
            }

            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            final DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor,ccMoshtary);

			MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());					   
            MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());

            // Takhfif
            DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
            ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifs = darkhastFaktorTakhfifDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            double mablaghTakhfifFaktorTitr = 0;
            double mablaghNaghdiTakhfifFaktorTitr = 0;
            JSONArray jsonArrayTakhfifs = new JSONArray();
            for (DarkhastFaktorTakhfifModel darkhastFaktorTakhfif : darkhastFaktorTakhfifs)
            {
                Log.d("send" , "getMablaghTakhfif titr : " + darkhastFaktorTakhfif.getMablaghTakhfif());
                if (darkhastFaktorTakhfif.getCodeNoeTakhfif() == 2)
                {
                    mablaghNaghdiTakhfifFaktorTitr += darkhastFaktorTakhfif.getMablaghTakhfif();
                }
                else
                {
                    mablaghTakhfifFaktorTitr += darkhastFaktorTakhfif.getMablaghTakhfif();
                }
                jsonArrayTakhfifs.put(darkhastFaktorTakhfif.toJsonObject());
            }
            Log.d("send" , "mablaghTakhfifFaktorTitr : " + mablaghTakhfifFaktorTitr);

            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
            ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrs = darkhastFaktorSatrDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            Log.d("send" , "darkhastFaktorSatrs size : " + darkhastFaktorSatrs.size());
            DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
            JSONArray jsonArrayTakhfifSatr = new JSONArray();
            double mablaghTakhfifFaktorSatr = 0;
            int sumTedadJayeze = 0;
            JSONArray jsonArrayDarkhastFaktorSatr = new JSONArray();
            for (DarkhastFaktorSatrModel model : darkhastFaktorSatrs)
            {
//                if (model.getMablaghForosh() == 1)
                if (model.getCodeNoeKala() == 2)
                {
                    sumTedadJayeze += model.getTedad3();
                }
                Log.d("send" , "darkhastFaktorSatrs size : " + darkhastFaktorSatrs.toString());
                Log.d("send" , "model.getCcDarkhastFaktorSatr() : " + model.getCcDarkhastFaktorSatr());

                jsonArrayDarkhastFaktorSatr.put(model.toJsonObject(mPresenter.getAppContext()));
                ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifModels = darkhastFaktorSatrTakhfifDAO.getByccDarkhastFaktorSatr(model.getCcDarkhastFaktorSatr());
                Log.d("send" , "darkhastFaktorSatrTakhfifModels : " + darkhastFaktorSatrTakhfifModels);

                for (DarkhastFaktorSatrTakhfifModel takhfifSatr : darkhastFaktorSatrTakhfifModels)
                {
                    jsonArrayTakhfifSatr.put(takhfifSatr.toJsonObject());
                    mablaghTakhfifFaktorSatr += takhfifSatr.getMablaghTakhfif();
                }
            }
            Log.d("send" , "mablaghTakhfifFaktorSatr : " + mablaghTakhfifFaktorSatr);

            //EmzaMoshtary..........
            DarkhastFaktorEmzaMoshtaryDAO emzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
            ArrayList<DarkhastFaktorEmzaMoshtaryModel> emzaMoshtarys = emzaMoshtaryDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            JSONArray jsonEmza = new JSONArray();
            for (DarkhastFaktorEmzaMoshtaryModel emza : emzaMoshtarys)
            {
                jsonEmza.put(emza.toJsonObject());
            }
            //MoshtaryPhotoPPC Chidman..........
                    /*MoshtaryPhotoPPCDAO moshtaryphotoPPCDAO= new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
                    ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCs = moshtaryphotoPPCDAO.getByccMoshtaryAndType(darkhastFaktorModel.getCcMoshtary() , 41);
                    JSONArray jsonMoshtaryPhotoPPC = new JSONArray();
                    if (moshtaryPhotoPPCs != null)
                    {
                        for (MoshtaryPhotoPPCModel moshtaryPhotoPPC : moshtaryPhotoPPCs)
                        {
                            jsonMoshtaryPhotoPPC.put(moshtaryPhotoPPC.toJsonObject());
                        }
                    }*/
            //DariaftPardakhtppc ............
            DariaftPardakhtPPCDAO dariaftpardakhtppcDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
            ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = dariaftpardakhtppcDAO.getForSendToSqlByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());

            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtdfPPCDAO  = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            dariaftPardakhtdfPPCDAO.updateExtraPropccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorModel.getCcDarkhastFaktor());
            JSONArray jsonArrayDariaftPardakhtPPC = new JSONArray();
            JSONArray jsonDariaftPardakhtDarkhastFaktorPPCs = new JSONArray();
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
            ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(Constants.REQUEST_CUSTOMER_CCPARAMETER_OF_CHECKS() + "," + Constants.UPDATE_MANDE_MOJODI());
            int codeNoeVosolVajhNaghd = Integer.parseInt(childParameterDAO.getAllByccChildParameter(String.valueOf(Constants.CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD())).get(0).getValue());
            String currentVersionNumber = new PubFunc().new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
            double mablaghKolDariaftPardakht = 0;
            double mablaghKolDariaftPardakhtDarkhastFaktor = 0;
            //int tedadKolDariaftPardakhtDarkhastFaktor = 0;


            if (dariaftPardakhtPPCModels.size() > 0 || noeMasouliat == 1 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat ==8)
            {
                // get ccMarkazForosh , ccMarkazAnbar , ccMarkazSazmanForoshSakhtarForosh
                int ccMarkazForoshFordp = 0;
                int ccMarkazAnbar = 0;
                int ccMarkazSazmanForoshSakhtarForosh = 0;
                if(noeMasouliat != 4)
                {
                    ccMarkazForoshFordp = foroshandehMamorPakhshModel.getCcMarkazForosh();
                    ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
                    ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
                }
                else
                {
                    ccMarkazForoshFordp = darkhastFaktorModel.getCcMarkazForosh();
                    ccMarkazAnbar = darkhastFaktorModel.getCcMarkazAnbar();
                    ccMarkazSazmanForoshSakhtarForosh = darkhastFaktorModel.getCcMarkazSazmanForoshSakhtarForosh();
                }

                for (DariaftPardakhtPPCModel dariaftpardakhtppc : dariaftPardakhtPPCModels)
                {
                    mablaghKolDariaftPardakht += dariaftpardakhtppc.getMablagh();
                    int codeNoeSanad = 0;
                    int codeNoeCheck = 0;

                    ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktors = dariaftPardakhtdfPPCDAO.getForSendToSql(dariaftpardakhtppc.getCcDariaftPardakht());
                    //tedadKolDariaftPardakhtDarkhastFaktor += dariaftPardakhtDarkhastFaktors.size();
                    for (DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktor : dariaftPardakhtDarkhastFaktors)
                    {
                        jsonDariaftPardakhtDarkhastFaktorPPCs.put(dariaftPardakhtDarkhastFaktor.toJsonObject(ccMarkazForoshFordp, ccMarkazAnbar, ccMarkazSazmanForoshSakhtarForosh, foroshandehMamorPakhshModel.getCcAfrad()));
                        mablaghKolDariaftPardakhtDarkhastFaktor += dariaftPardakhtDarkhastFaktor.getMablagh();
                    }
                    jsonArrayDariaftPardakhtPPC.put(dariaftpardakhtppc.toJsonObject(ccMarkazForoshFordp, ccMarkazAnbar, ccMarkazSazmanForoshSakhtarForosh, codeNoeSanad, codeNoeCheck, codeNoeVosolVajhNaghd, currentVersionNumber));
                }
            }
            else //if (noeMasouliat != 1 && dariaftPardakhtPPCModels.size() ==0)
            {
                ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
                long sumMablaghMarjoee = elamMarjoeeSatrPPCDAO.getSumMablaghMarjoeeByccDarkhastFaktor(ccDarkhastFaktor);
                Log.d("dariaftPardakht" , "sumMablaghMarjoee : " + sumMablaghMarjoee);
                Log.d("dariaftPardakht" , "getMablaghKhalesFaktor : " + darkhastFaktorModel.getMablaghKhalesFaktor());
                Log.d("dariaftPardakht" , "getMablaghKolFaktor : " + darkhastFaktorModel.getMablaghKolFaktor());
                if (sumMablaghMarjoee >= darkhastFaktorModel.getMablaghKhalesFaktor())
                {
                    Log.d("dariaftPardakht" , "in if");
                    return -1;
                }
                Log.d("dariaftPardakht" , "not if");
                //sendRequestResponse.onError(R.string.errorNotFoundDariaftPardakht);
                //mPresenter.onError(R.string.errorNotFoundDariaftPardakht);
            }

            // Marjoee
            ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
            ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
            ElamMarjoeeSatrPPCTedadDAO elamMarjoeeSatrPPCTedadDAO = new ElamMarjoeeSatrPPCTedadDAO(mPresenter.getAppContext());
            JSONArray jsonArrayElamMarjoee = new JSONArray();
            JSONArray jsonArrayElamMarjoeeSatrPPC = new JSONArray();
            float mablaghKolMarjoee = 0;

            final ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels = elamMarjoeePPCDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
            ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels = elamMarjoeeSatrPPCDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());

            if ((elamMarjoeePPCModels.size() > 0 && elamMarjoeeSatrPPCModels.size() == 0) || (elamMarjoeePPCModels.size() == 0 && elamMarjoeeSatrPPCModels.size() > 0))
            {
                return -2;
            }

            for (ElamMarjoeePPCModel model : elamMarjoeePPCModels)
            {
                Log.d("marjoee" , "ElamMarjoeePPCModel : " + model.toString());
                if (model.getCcDarkhastFaktor() <= 0)
                {
                    return -3;
                }
                jsonArrayElamMarjoee.put(model.toJsonString(elamMarjoeeSatrPPCModels.size(), ccMarkazForosh, foroshandehMamorPakhshModel.getNoeForoshandehMamorPakhsh() , foroshandehMamorPakhshModel.getCcAfrad()));
            }

            if (elamMarjoeePPCModels.size() > 0)
            {
                for (ElamMarjoeeSatrPPCModel marjoeeSatrPPC : elamMarjoeeSatrPPCModels)
                {
                    Log.d("marjoee" , "ElamMarjoeeSatrPPCModel : " + marjoeeSatrPPC.toString());
                    if (marjoeeSatrPPC.getCcElamMarjoeePPC() != elamMarjoeePPCModels.get(0).getCcElamMarjoeePPC())
                    {
                        return -4;
                    }
                    else if (marjoeeSatrPPC.getCcDarkhastFaktor() <= 0)
                    {
                        return -3;
                    }
                    jsonArrayElamMarjoeeSatrPPC.put(marjoeeSatrPPC.toJsonObject());
                    mablaghKolMarjoee += marjoeeSatrPPC.getFee() * marjoeeSatrPPC.getTedad3();
                }
            }

            //insert to ElamMarjoeeSatrPPCTedad
            ElamMarjoeeSatrPPCTedadModel elamMarjoeeSatrPPCTedadModel = new ElamMarjoeeSatrPPCTedadModel();
            elamMarjoeeSatrPPCTedadModel.setCcDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
            elamMarjoeeSatrPPCTedadModel.setTedadSatr(elamMarjoeeSatrPPCModels.size());
            elamMarjoeeSatrPPCTedadDAO.deleteByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
            elamMarjoeeSatrPPCTedadDAO.insert(elamMarjoeeSatrPPCTedadModel);
            JSONObject jsonObjectelamMarjoeeSatrPPCTedad = elamMarjoeeSatrPPCTedadModel.toJsonObject();

            //ControlDataErsaliTablet
            /*float diff = Math.abs(Math.round(mablaghTakhfifFaktorSatr) - mablaghTakhfifFaktorTitr);
            if (diff > 0 && diff <= 1)
            {
                mablaghTakhfifFaktorSatr = mablaghTakhfifFaktorTitr;
            }*/
            ControlDataErsaliTabletModel controlDataErsaliTabletModel = new ControlDataErsaliTabletModel();
            controlDataErsaliTabletModel.setCcDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
            controlDataErsaliTabletModel.setCcForoshandeh(darkhastFaktorModel.getCcForoshandeh());
            controlDataErsaliTabletModel.setMablaghKolDarkhast(darkhastFaktorModel.getMablaghKolFaktor());
            controlDataErsaliTabletModel.setTedadAghlamFaktor(darkhastFaktorSatrs.size());
            controlDataErsaliTabletModel.setMablaghTakhfifDarkhastTitr(mablaghTakhfifFaktorTitr);
            controlDataErsaliTabletModel.setMablaghTakhfifDarkhastSatr(mablaghTakhfifFaktorSatr);
            controlDataErsaliTabletModel.setMablaghTakhfifNaghdiDarkhastTitr(mablaghNaghdiTakhfifFaktorTitr);
            controlDataErsaliTabletModel.setTedadAghlamTakhfif(darkhastFaktorTakhfifs.size());
            controlDataErsaliTabletModel.setMablaghKolVosolTitr(mablaghKolDariaftPardakht);
            controlDataErsaliTabletModel.setMablaghKolVosolSatr(mablaghKolDariaftPardakhtDarkhastFaktor);
            controlDataErsaliTabletModel.setMablaghKolMarjoee(mablaghKolMarjoee);
            controlDataErsaliTabletModel.setTedadAghlamMarjoee(elamMarjoeeSatrPPCModels.size());
            controlDataErsaliTabletModel.setTedadJayeze(sumTedadJayeze);
            JSONObject jsonObject = controlDataErsaliTabletModel.toJson();

            DarkhastFaktorAfradForoshModel darkhastFaktorAfradForoshModel = insertDarkhastFaktorAfradForosh(noeMasouliat, darkhastFaktorModel.getCcDarkhastFaktorNoeForosh(), darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorModel.getCcForoshandeh(), foroshandehMamorPakhshModel.getCcAfrad(), foroshandehMamorPakhshModel.getCcAfradModir());


                if ( (noeMasouliat == 1 || noeMasouliat == 3  || noeMasouliat == 6 || noeMasouliat ==8) && foroshandehMamorPakhshModel.getCcDarkhastFaktorNoeForosh() == Constants.ccNoeHavale )
                {

                    JSONArray jsonDarkhastHavaleSatrs = new JSONArray();
                    for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
                    {
                        jsonDarkhastHavaleSatrs.put(darkhastFaktorSatr.toJsonObjectHavale(mPresenter.getAppContext()));
                    }

                    JSONObject jsonObjectHavale = darkhastFaktorModel.toJsonStringForVosolSard(foroshandehMamorPakhshModel, ccMarkazForosh, moshtaryModel.getCcNoeMoshtary(), moshtaryModel.getCcNoeSenf(),customerDarkhastFaktorModel);
                    try
                    {

                        JSONObject jsonObjectFinal = new JSONObject();
                        jsonObjectFinal.put("DarkhastHavaleh" , jsonObjectHavale);
                        jsonObjectFinal.put("DarkhastHavaleh_AfradForoshandeh" , new JSONArray().put(darkhastFaktorAfradForoshModel.toJsonObject()));
                        jsonObjectFinal.put("DarkhastHavalehSatr" , jsonDarkhastHavaleSatrs);
                        jsonObjectFinal.put("DarkhastFaktorTakhfifPPC" , jsonArrayTakhfifs);
                        jsonObjectFinal.put("DarkhastFaktorSatrTakhfifPPC" , jsonArrayTakhfifSatr);
                        jsonObjectFinal.put("DarkhastFaktor_EmzaMoshtary" , jsonEmza);
                        jsonObjectFinal.put("ElamMarjoee" , jsonArrayElamMarjoee);
                        jsonObjectFinal.put("ElamMarjoeeSatr" , jsonArrayElamMarjoeeSatrPPC);
                        jsonObjectFinal.put("ElamMarjoeeSatr_Tedad" , jsonObjectelamMarjoeeSatrPPCTedad);
                        jsonObjectFinal.put("ControlData_ErsaliTablet" , new JSONArray().put(jsonObject));

                        String strJsonFinal = jsonObjectFinal.toString().replace("\\" , "");
                        Log.i("JsonFinalll", "doInBackground: "+strJsonFinal);
                        sendDarkhastHavaleDataToServer(customerDarkhastFaktorModel.getCcMoshtary(), customerDarkhastFaktorModel.getCcDarkhastFaktor(), strJsonFinal, position, foroshandehMamorPakhshModel);
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel.AsyncTaskSendRequest", "", "doInBackground", "noeMasouliat != 5 and noeMasouliat == 1");
                        sendRequestResponse.onError(R.string.errorOccurred);
                    }
                }
                else //if (noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 4  || noeMasouliat == 6 || noeMasouliat == 7)
                {
                    try
                    {
                        JSONObject jsonObjectDarkhast = darkhastFaktorModel.toJsonForVosol(foroshandehMamorPakhshModel, noeMasouliat, ccMarkazForosh, moshtaryModel.getCcNoeMoshtary(), moshtaryModel.getCcNoeSenf(),customerDarkhastFaktorModel);

                        JSONObject jsonObjectFinal = new JSONObject();
                        jsonObjectFinal.put("DarkhastFaktor" , jsonObjectDarkhast);
                        jsonObjectFinal.put("DarkhastFaktor_AfradForoshandeh" , new JSONArray().put(darkhastFaktorAfradForoshModel.toJsonObject()));
                        jsonObjectFinal.put("DarkhastFaktorSatrPPC" , jsonArrayDarkhastFaktorSatr);
                        jsonObjectFinal.put("DarkhastFaktorTakhfifPPC" , jsonArrayTakhfifs);
                        jsonObjectFinal.put("DarkhastFaktorSatrTakhfifPPC" , jsonArrayTakhfifSatr);
                        jsonObjectFinal.put("DarkhastFaktor_EmzaMoshtary" , jsonEmza);
                        jsonObjectFinal.put("DariaftPardakhtPPC" , jsonArrayDariaftPardakhtPPC);
                        jsonObjectFinal.put("DariaftPardakhtDarkhastFaktorPPC" , jsonDariaftPardakhtDarkhastFaktorPPCs);
                        jsonObjectFinal.put("ElamMarjoee" , jsonArrayElamMarjoee);
                        jsonObjectFinal.put("ElamMarjoeeSatr" , jsonArrayElamMarjoeeSatrPPC);
                        jsonObjectFinal.put("ElamMarjoeeSatr_Tedad" , jsonObjectelamMarjoeeSatrPPCTedad);
                        jsonObjectFinal.put("ControlData_ErsaliTablet" , new JSONArray().put(jsonObject));


                        String jsonFinal = jsonObjectFinal.toString();
                        jsonFinal = jsonFinal.replace("\\" , "");
                        Log.d("jsonFinallll", "doInBackground: "+jsonFinal);
                        //TODO
                        boolean isValidTimeForSend = checkDateTimeForSend(childParameterModels);
                        if (!isValidTimeForSend)
                            return -5;
                        else
                        sendDarkhastFaktorToServer(customerDarkhastFaktorModel.getCcMoshtary(), customerDarkhastFaktorModel.getCcDarkhastFaktor(), jsonFinal, position, foroshandehMamorPakhshModel);
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel.AsyncTaskSendRequest", "", "doInBackground", "noeMasouliat != 5 and noeMasouliat != 1");
                        sendRequestResponse.onError(R.string.errorOccurred);
                    }
                }
            //}
            return 1;
        }

        private boolean checkDateTimeForSend(ArrayList<ParameterChildModel> childParameterModels)
        {
            String startRestTime = "23:45";
            Date startDate;
            Date endTime;
            Date now = Calendar.getInstance().getTime();
            boolean checkTimeDarkhast = true;
            for (ParameterChildModel model : childParameterModels)
            {
                if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_TIME_DARKHAST())
                {
                    checkTimeDarkhast = model.getValue().trim().equals("1");
                }
                if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_CC_START_REST_TIME())
                {
                    startRestTime = model.getValue();
                }
            }
            try
            {
                String[] timeSection = startRestTime.split(":");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY , Integer.parseInt(timeSection[0]));
                calendar.set(Calendar.MINUTE , Integer.parseInt(timeSection[1]));
                startDate = calendar.getTime();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "checkDateTime", "");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY , 23);
                calendar.set(Calendar.MINUTE , 45);
                startDate = calendar.getTime();
            }

            endTime = addMinutesToTime(startDate,10) ;

            if (checkTimeDarkhast && (now.after(startDate) && now.before(endTime)))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        private Date addMinutesToTime(Date myTime,int min) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Log.i("addMinutesToTime", "myTime: "+df.format(myTime.getTime()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(myTime);
            cal.add(Calendar.MINUTE, min);
            String newTime = df.format(cal.getTime());
            Log.i("addMinutesToTime", "newTime:" + newTime );
            return cal.getTime();
        }

        @SuppressLint("LongLogTag")
        private void sendDarkhastFaktorToServer(final int ccMoshtary, final long ccDarkhastFaktorOld, String jsonStringData , final int position , final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
        {
//            ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
//            String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
// , "");
//            String serverPort = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
// , "");

            ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
            String serverIP=serverIpModel.getServerIp();
            String serverPort=serverIpModel.getPort();

            if (serverIP.trim().equals("") || serverPort.trim().equals(""))
            {
                sendRequestResponse.onError(R.string.errorFindServerIP);
                //mPresenter.onErrorSendRequest(R.string.errorFindServerIP);
            }
            else
            {
                final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);//ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
                Call<CreateDarkhastFaktorWithVosol> call = apiServicePost.createDarkhastFaktorWithJson(jsonStringData);
                call.enqueue(new Callback<CreateDarkhastFaktorWithVosol>()
                {
                    @Override
                    public void onResponse(Call<CreateDarkhastFaktorWithVosol> call, Response<CreateDarkhastFaktorWithVosol> response)
                    {
                        try
                        {
                            if (response.isSuccessful() && response.body() != null)
                            {
                                Log.d("Temp" , "in if success and body not null");
                                CreateDarkhastFaktorWithVosol result = response.body();
                                if (result.getSuccess())
                                {
                                    Log.d("sendData" , "send DarkhastFaktor success to server");
                                    if (Integer.parseInt(result.getMessage()) > 0)
                                    {
                                        setLastOlaviat(ccMoshtary);
                                        long ccDarkhastFaktorNew = Integer.parseInt(result.getMessage());
                                        updateElamMarjoeeccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
                                        /*for (ElamMarjoeePPCModel model : elamMarjoeePPCModels)
                                        {
                                            model.setCcDarkhastFaktor(ccDarkhastFaktorNew);
                                        }*/
                                        //insertDarkhastFaktorAfradForosh(noeMasouliat , ccDarkhastFaktorNew , foroshandehMamorPakhshModel.getCcAfrad());
                                        checkOtherData(apiServicePost , ccMoshtary);
                                        sendAllAdamDarkhastForCustomerToServer(ccMoshtary, apiServicePost, foroshandehMamorPakhshModel.getCcMarkazForosh());
                                        //sendElamMarjoeePPCToServer(apiServicePost , elamMarjoeePPCModels, foroshandehMamorPakhshModel.getCcMarkazForosh(), foroshandehMamorPakhshModel.getNoeForoshandehMamorPakhsh(), foroshandehMamorPakhshModel.getCcAfrad() , ccDarkhastFaktorNew);
                                        //sendDarkhastFaktorAfradforoshToServer(ccDarkhastFaktorNew,apiServicePost,position);
                                        insertMoshtaryMorajehShodehRooz(foroshandehMamorPakhshModel.getCcForoshandeh(),ccMoshtary,3,0,0);
                                        updateccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
                                        checkAndPrepareDataForSendKalaMojodiToServer(apiServicePost);
                                        sendRequestResponse.onSuccess(position , ccDarkhastFaktorNew);
                                        Log.d("time" , "end time temp : " + System.currentTimeMillis());
                                    }
                                    else
                                    {
                                        showResultError(Integer.parseInt(result.getMessage()));
                                    }
                                }
                                else
                                {
                                    setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                                    showResultError(Integer.parseInt(result.getMessage()));
                                }
                            }
                            else
                            {
                                String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                                if (response.errorBody() != null)
                                {
                                    errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                                }
                                setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                                Log.d("tempRequest" , "message : " + errorMessage);
                                //mPresenter.onErrorSendRequest(R.string.errorOperation);
                                sendRequestResponse.onError(R.string.errorSendData);
                            }
                        }
                        catch (Exception exception)
                        {
                            Log.d("Temp" , "in exception");
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                            //mPresenter.onErrorSendRequest(R.string.errorOperation);
                            sendRequestResponse.onError(R.string.errorSendData);
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateDarkhastFaktorWithVosol> call, Throwable t)
                    {
                        Log.d("noTemp" , "in onFailure");
                        setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onFailure");
                        //mPresenter.onErrorSendRequest(R.string.errorOperation);
                        sendRequestResponse.onError(R.string.errorSendData);
                    }
                });
            }
        }


        private void sendTempRequestMamorPakhshSmart(final int ccMoshtary, final long ccDarkhastFaktorOld, String jsonStringData , final int position, final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
        {
//            ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
//            String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
// , "");
//            String serverPort = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
// , "");
            ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
            String serverIP=serverIpModel.getServerIp();
            String serverPort=serverIpModel.getPort();
            if (serverIP.trim().equals("") || serverPort.trim().equals(""))
            {
                sendRequestResponse.onError(R.string.errorFindServerIP);
                //mPresenter.onErrorSendRequest(R.string.errorFindServerIP);
            }
            else
            {
                final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);//ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
                Call<UpdateDarkhastFaktorWithJSONResult> call = apiServicePost.updateDarkhastFaktorWithJSON(jsonStringData);
                call.enqueue(new Callback<UpdateDarkhastFaktorWithJSONResult>()
                {
                    @Override
                    public void onResponse(Call<UpdateDarkhastFaktorWithJSONResult> call, Response<UpdateDarkhastFaktorWithJSONResult> response)
                    {
                        try
                        {
                            if (response.isSuccessful() && response.body() != null)
                            {
                                Log.d("Temp" , "in if success and body not null");
                                UpdateDarkhastFaktorWithJSONResult result = response.body();
                                if (result.getSuccess())
                                {
                                    Log.d("sendData" , "send DarkhastFaktor success to server");
									setLastOlaviat(ccMoshtary);						   
                                    if (Integer.parseInt(result.getMessage()) > 0)
                                    {
                                        long ccDarkhastFaktorNew = Integer.parseInt(result.getMessage());
                                        updateElamMarjoeeccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
                                        checkOtherData(apiServicePost , ccMoshtary);
                                        sendAllAdamDarkhastForCustomerToServer(ccMoshtary, apiServicePost, foroshandehMamorPakhshModel.getCcMarkazForosh());
                                        updateccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
                                        checkAndPrepareDataForSendKalaMojodiToServer(apiServicePost);
                                        sendRequestResponse.onSuccess(position , ccDarkhastFaktorNew);
                                        Log.d("time" , "end time temp : " + System.currentTimeMillis());
                                    }
                                    else
                                    {
                                        showResultError(Integer.parseInt(result.getMessage()));
                                    }
                                }
                                else
                                {
                                    setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                                    showResultError(Integer.parseInt(result.getMessage()));
                                }
                            }
                            else
                            {
                                String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                                if (response.errorBody() != null)
                                {
                                    errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                                }
                                setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                                Log.d("tempRequest" , "message : " + errorMessage);
                                //mPresenter.onErrorSendRequest(R.string.errorOperation);
                                sendRequestResponse.onError(R.string.errorSendData);
                            }
                        }
                        catch (Exception exception)
                        {
                            Log.d("Temp" , "in exception");
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                            //mPresenter.onErrorSendRequest(R.string.errorOperation);
                            sendRequestResponse.onError(R.string.errorSendData);
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateDarkhastFaktorWithJSONResult> call, Throwable t)
                    {
                        Log.d("noTemp" , "in onFailure");
                        setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onFailure");
                        //mPresenter.onErrorSendRequest(R.string.errorOperation);
                        sendRequestResponse.onError(R.string.errorSendData);
                    }
                });
            }
        }


        private void sendDarkhastHavaleDataToServer(final int ccMoshtary, final long ccDarkhastFaktorOld, String jsonStringData, final int position , final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
        {
            ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
            final ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels = elamMarjoeePPCDAO.getByccDarkhastFaktor(ccDarkhastFaktorOld);

//            ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
//            String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
// , "");
//            String serverPort = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
// , "");
            ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
            String serverIP=serverIpModel.getServerIp();
            String serverPort=serverIpModel.getPort();
            if (serverIP.trim().equals("") || serverPort.trim().equals(""))
            {
                sendRequestResponse.onError(R.string.errorFindServerIP);
                //mPresenter.onErrorSendRequest(R.string.errorFindServerIP);
            }
            else
            {
                final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);//ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
                Call<CreateDarkhastFaktorWithVosol> call = apiServicePost.createDarkhastFaktorWithVosolSard(jsonStringData);
                call.enqueue(new Callback<CreateDarkhastFaktorWithVosol>()
                {
                    @Override
                    public void onResponse(Call<CreateDarkhastFaktorWithVosol> call, Response<CreateDarkhastFaktorWithVosol> response)
                    {
                        try
                        {
                            if (response.isSuccessful() && response.body() != null)
                            {
                                Log.d("Temp" , "in if success and body not null");
                                CreateDarkhastFaktorWithVosol result = response.body();
                                if (result.getSuccess())
                                {
                                    Log.d("sendData" , "send DarkhastHavale success to server");
									setLastOlaviat(ccMoshtary);						   
                                    long ccDarkhastFaktorNew = Integer.parseInt(result.getMessage());
                                    updateElamMarjoeeccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
                                    for (ElamMarjoeePPCModel model : elamMarjoeePPCModels)
                                    {
                                        model.setCcDarkhastFaktor(ccDarkhastFaktorNew);
                                    }
                                    checkOtherData(apiServicePost , ccMoshtary);
                                    sendAllAdamDarkhastForCustomerToServer(ccMoshtary, apiServicePost, foroshandehMamorPakhshModel.getCcMarkazForosh());
                                    insertMoshtaryMorajehShodehRooz(foroshandehMamorPakhshModel.getCcForoshandeh(),ccMoshtary,3,0,0);
                                    updateccDarkhastFaktor(ccDarkhastFaktorOld , ccDarkhastFaktorNew);
                                    checkAndPrepareDataForSendKalaMojodiToServer(apiServicePost);
                                    Log.d("time" , "end time os send havale : " + System.currentTimeMillis());
                                    sendRequestResponse.onSuccess(position,ccDarkhastFaktorNew);

                                }
                                else
                                {
                                    Log.d("Temp" , "in else not success");
                                    setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastHavaleDataToServer" , "onResponse");
                                    showResultError(Integer.parseInt(result.getMessage()));
                                    //mPresenter.onErrorSendRequest(R.string.errorOperation);
                                }
                            }
                            else
                            {
                                String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                                if (response.errorBody() != null)
                                {
                                    errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                                }
                                setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendDarkhastHavaleDataToServer" , "onResponse");
                                Log.d("tempRequest" , "message : " + errorMessage);
                                //mPresenter.onErrorSendRequest(R.string.errorOperation);
                                sendRequestResponse.onError(R.string.errorSendData);
                            }
                        }
                        catch (Exception exception)
                        {
                            Log.d("Temp" , "in exception");
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendDarkhastHavaleDataToServer" , "onResponse");
                            //mPresenter.onErrorSendRequest(R.string.errorOperation);
                            sendRequestResponse.onError(R.string.errorSendData);
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateDarkhastFaktorWithVosol> call, Throwable t)
                    {
                        Log.d("noTemp" , "in onFailure");
                        setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastHavaleDataToServer" , "onFailure");
                        //mPresenter.onErrorSendRequest(R.string.errorOperation);
                        sendRequestResponse.onError(R.string.errorSendData);
                    }
                });
            }
        }


        private void checkAndPrepareDataForSendKalaMojodiToServer(APIServicePost apiServicePost)
        {
            ParameterChildDAO parameterChildDAO = new ParameterChildDAO(context);
            String isSendKalaMojodi = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_IS_SEND_KALA_MOJODI());
            if (isSendKalaMojodi.trim().equals("1"))
            {
                KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(context);
                ArrayList<KalaMojodiModel> kalaMojodiModels = kalaMojodiDAO.getAll();
                JSONArray jsonArrayKalaMojodi = new JSONArray();
                for (KalaMojodiModel model : kalaMojodiModels)
                {
                    jsonArrayKalaMojodi.put(model.toJsonObject());
                }
                if (jsonArrayKalaMojodi.length() > 0)
                {
                    String jsonArrayStringKalaMojodi = jsonArrayKalaMojodi.toString();
                    try
                    {
                        //Log.d("TemporaryRequest","jsonArrayStringKalaMojodi:"+jsonArrayStringKalaMojodi.toString());
                        //saveToFile("kalaMojodi.txt" , jsonArrayStringKalaMojodi);
                        sendKalaMojodiToServer(apiServicePost, jsonArrayStringKalaMojodi);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void sendKalaMojodiToServer(APIServicePost apiServicePost , String jsonArrayStringKalaMojodi)
        {
            Call<CreateKalaMojodyWithJSONResult> call = apiServicePost.createKalaMojodyWithJSON(jsonArrayStringKalaMojodi);
            call.enqueue(new Callback<CreateKalaMojodyWithJSONResult>()
            {
                @Override
                public void onResponse(Call<CreateKalaMojodyWithJSONResult> call, Response<CreateKalaMojodyWithJSONResult> response)
                {}

                @Override
                public void onFailure(Call<CreateKalaMojodyWithJSONResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendKalaMojodiToServer" , "onFailure");
                }
            });
        }


        private void saveToFile(String fileName , String jsonStringData)
        {
            try
            {
                File path = context.getExternalFilesDir(null);
                File file = new File(path, fileName);
                FileOutputStream stream = new FileOutputStream(file);
                try
                {
                    stream.write(jsonStringData.getBytes());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }

        private void showResultError(int errorCode)
        {
            switch (errorCode)
            {
                case -1:
                    sendRequestResponse.onError(R.string.errorDuplicatedFaktor);
                    break;
                case -2:
                    sendRequestResponse.onError(R.string.errorMoghayeratMablaghDarkhast);
                    break;
                case -3:
                    sendRequestResponse.onError(R.string.errorMoghayeratTedadAghlam);
                    break;
                case -4:
                    sendRequestResponse.onError(R.string.errorMoghayeratDarJayeze);
                    break;
                case -5:
                    sendRequestResponse.onError(R.string.errorMoghayeratTakhfifTitr);
                    break;
                case -6:
                    sendRequestResponse.onError(R.string.errorMoghayeratTakhfifSatr);
                    break;
                case -7:
                    sendRequestResponse.onError(R.string.errorMoghayeratMablaghTakhfif);
                    break;
                case -8:
                    sendRequestResponse.onError(R.string.errorMoghayeratTedadMarjoee);
                    break;
                case -9:
                    sendRequestResponse.onError(R.string.errorMoghayeratVosolTitr);
                    break;
                case -10:
                    sendRequestResponse.onError(R.string.errorMoghayeratTakhfifNaghdi);
                    break;
                case -11:
                    sendRequestResponse.onError(R.string.errorMoghayeratNameSahebHesab);
                    break;
                case -12:
                    sendRequestResponse.onError(R.string.errorMablaghVosolManfi);
                    break;
                case -13:
                    sendRequestResponse.onError(R.string.errorNotFoundAddress);
                    break;
                case -14:
                    sendRequestResponse.onError(R.string.errorTarikhFaktorVosol);
                    break;
                case -15:
                    sendRequestResponse.onError(R.string.errorTakhfifNaghdi);
                    break;
                case -16:
                    sendRequestResponse.onError(R.string.errorElatKasrMojodi);
                case -17:
                    sendRequestResponse.onError(R.string.errorMoghayeratVersion);
                break;
                default:
                    sendRequestResponse.onError(R.string.errorSendData);
            }
        }


        @Override
        protected void onPostExecute(Integer num)
        {
            if (num == -1)
            {
                mPresenter.onErrorSendRequest(R.string.errorNotFoundDariaftPardakht,"");
            }
            else if (num == -2)
            {
                mPresenter.onErrorSendRequest(R.string.errorMoghayeratMarjoeeTitrSatr,"");
            }
            else if (num == -3)
            {
                mPresenter.onErrorSendRequest(R.string.errorInvalidFaktorDetail,"");
            }
            else if (num == -4)
            {
                mPresenter.onErrorSendRequest(R.string.errorInvalidMarjoeeData,"");
            }
        }


    }











        /*private void sendElamMarjoeePPCToServer(final APIServicePost apiServicePost, final ArrayList<ElamMarjoeePPCModel> elamMarjoeePPCModels, int ccMarkazForosh, int noeForoshandehMamorPakhsh, int ccAfrad, long ccDarkhastFaktorNew)
    {
        ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
        ElamMarjoeeSatrPPCTedadDAO elamMarjoeeSatrPPCTedadDAO = new ElamMarjoeeSatrPPCTedadDAO(mPresenter.getAppContext());
        final ArrayList<Integer> arrayListSendedccElamMarjoee = new ArrayList<>();
        for (final ElamMarjoeePPCModel model : elamMarjoeePPCModels)
        {
            //create JSON Array of ElamMarjoeeSatrPPC for send To Server
            ArrayList<ElamMarjoeeSatrPPCModel> elamMarjoeeSatrPPCModels = elamMarjoeeSatrPPCDAO.getByccElamMarjoeePPC(model.getCcElamMarjoeePPC());
            JSONArray jsonArrayElamMarjoeeSatrPPC = new JSONArray();
            for (ElamMarjoeeSatrPPCModel marjoeeSatrPPC : elamMarjoeeSatrPPCModels)
            {
                jsonArrayElamMarjoeeSatrPPC.put(marjoeeSatrPPC.toJsonObject());
            }

            //insert to ElamMarjoeeSatrPPCTedad
            ElamMarjoeeSatrPPCTedadModel elamMarjoeeSatrPPCTedadModel = new ElamMarjoeeSatrPPCTedadModel();
            elamMarjoeeSatrPPCTedadModel.setCcDarkhastFaktor(ccDarkhastFaktorNew);
            elamMarjoeeSatrPPCTedadModel.setTedadSatr(elamMarjoeeSatrPPCModels.size());
            elamMarjoeeSatrPPCTedadDAO.deleteByccDarkhastFaktor(model.getCcDarkhastFaktor());
            elamMarjoeeSatrPPCTedadDAO.insert(elamMarjoeeSatrPPCTedadModel);

            String jsonString = model.toJsonString(jsonArrayElamMarjoeeSatrPPC, elamMarjoeeSatrPPCModels.size(), ccMarkazForosh, noeForoshandehMamorPakhsh , ccAfrad).toString().replace("\\" , "");

            int maxLogSize = 1000;
            for(int i = 0; i <= jsonString.length() / maxLogSize; i++)
            {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > jsonString.length() ? jsonString.length() : end;
                Log.d("jsonMarjoee", jsonString.substring(start, end));
            }

            Call<CreateElamMarjoeePPCResult> call = apiServicePost.createElamMarjoeePPCResult(jsonString);
            call.enqueue(new Callback<CreateElamMarjoeePPCResult>()
            {
                @Override
                public void onResponse(Call<CreateElamMarjoeePPCResult> call, Response<CreateElamMarjoeePPCResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateElamMarjoeePPCResult result = response.body();
                            if (result.getSuccess())
                            {
                                Log.d("sendData" , "send ElamMarjoee success to server");
                                //adamDarkhastDAO.updateIsSentToServer(1 , model.getCcAdamDarkhast());

                                arrayListSendedccElamMarjoee.add(model.getCcElamMarjoeePPC());
                                if (arrayListSendedccElamMarjoee.size() == elamMarjoeePPCModels.size())
                                {
                                    Log.d("sendData" , "send all ElamMarjoee success to server");
                                    sendElamMarjoeeSatrPPCTedad(model.getCcDarkhastFaktor(), apiServicePost);
                                }
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendElamMarjoeePPCToServer" , "onResponse");
                                mPresenter.onErrorSendRequest(R.string.errorOperation);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendElamMarjoeePPCToServer" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendRequest(R.string.errorOperation);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendElamMarjoeePPCToServer" , "onResponse");
                        mPresenter.onErrorSendRequest(R.string.errorOperation);
                    }
                }

                @Override
                public void onFailure(Call<CreateElamMarjoeePPCResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendElamMarjoeePPCToServer" , "onFailure");
                    mPresenter.onErrorSendRequest(R.string.errorOperation);
                }
            });
        }
    }*/






        /*@Override
    public void sendTempRequest(final int position , final CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        controlInsertFaktor(customerDarkhastFaktorModel.getUniqID_Tablet(), String.valueOf(customerDarkhastFaktorModel.getCcMoshtary()), String.valueOf(customerDarkhastFaktorModel.getCcForoshandeh()), new OnControlFaktor() {
            @Override
            public void onError(int resErrorId) {
                mPresenter.onError(resErrorId);
            }

            @Override
            public void onSuccess(int flag) {
                if (flag == 1)
                {
                    mPresenter.onError(R.string.errorResend);
                }
                else
                {
                    Log.d("time" , "start send temp : " + System.currentTimeMillis());
                    ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
                    int noeMasouliat = new PubFunc().new ForoshandehMamorPakhsh().getNoeMasouliat(foroshandehMamorPakhshModel);

                    SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
                    long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);

                    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                    DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

                    int ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
                    if(ccMarkazForosh == 0)
                    {
                        MarkazDAO markazDAO = new MarkazDAO(mPresenter.getAppContext());
                        ccMarkazForosh = markazDAO.getAll().get(0).getCcMarkaz();
                    }

                    MoshtaryGorohDAO moshtaryGorohDAO = new MoshtaryGorohDAO(mPresenter.getAppContext());
                    int ccGorohNoeSenf = 0;
                    int ccGorohNoeMoshtary = 0;
                    ArrayList<MoshtaryGorohModel> moshtaryGorohModels = moshtaryGorohDAO.getByccMoshtaryAndccGorohLinks(darkhastFaktorModel.getCcMoshtary() , Constants.CC_GOROH_NOE_FAALIAT() + " , " + Constants.CC_GOROH_NOE_SENF());
                    for (MoshtaryGorohModel model : moshtaryGorohModels)
                    {
                        if (model.getCcGorohLink() == Constants.CC_GOROH_NOE_SENF())
                        {
                            ccGorohNoeSenf = model.getCcGoroh();
                        }
                        else if (model.getCcGorohLink() == Constants.CC_GOROH_NOE_FAALIAT())
                        {
                            ccGorohNoeMoshtary = model.getCcGoroh();
                        }
                    }
                    DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                    ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifs = darkhastFaktorTakhfifDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

                    DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                    ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrs = darkhastFaktorSatrDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

                    float mablaghTakhfifFaktorSatr = 0;
                    JSONArray jsonArrayTakhfifs = new JSONArray();
                    for (DarkhastFaktorTakhfifModel darkhastFaktorTakhfif : darkhastFaktorTakhfifs)
                    {
                        mablaghTakhfifFaktorSatr += darkhastFaktorTakhfif.getMablaghTakhfif();
                        jsonArrayTakhfifs.put(darkhastFaktorTakhfif.toJsonObject());
                    }
                    //EmzaMoshtary..........
                    DarkhastFaktorEmzaMoshtaryDAO emzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
                    ArrayList<DarkhastFaktorEmzaMoshtaryModel> emzaMoshtarys = emzaMoshtaryDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

                    JSONArray jsonEmza = new JSONArray();
                    for (DarkhastFaktorEmzaMoshtaryModel emza : emzaMoshtarys)
                    {
                        jsonEmza.put(emza.toJsonObject());
                    }
                    //MoshtaryPhotoPPC Chidman..........
                    MoshtaryPhotoPPCDAO moshtaryphotoPPCDAO= new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
                    ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCs = moshtaryphotoPPCDAO.getByccMoshtaryAndType(darkhastFaktorModel.getCcMoshtary() , 41);
                    JSONArray jsonMoshtaryPhotoPPC = new JSONArray();
                    if (moshtaryPhotoPPCs != null)
                    {
                        for (MoshtaryPhotoPPCModel moshtaryPhotoPPC : moshtaryPhotoPPCs)
                        {
                            jsonMoshtaryPhotoPPC.put(moshtaryPhotoPPC.toJsonObject());
                        }
                    }
                    //DariaftPardakhtppc ............
                    DariaftPardakhtPPCDAO dariaftpardakhtppcDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
                    ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = dariaftpardakhtppcDAO.getForSendToSqlByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());

                    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtdfPPCDAO  = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
                    dariaftPardakhtdfPPCDAO.updateExtraPropccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorModel.getCcDarkhastFaktor());
                    JSONArray jsonArrayDariaftPardakhtPPC = new JSONArray();
                    CodeNoeVosolDAO codeNoeVosolDAO = new CodeNoeVosolDAO(mPresenter.getAppContext());
                    ChildParameterDAO childParameterDAO = new ChildParameterDAO(mPresenter.getAppContext());
                    int codeNoeVosolVajhNaghd = Integer.parseInt(childParameterDAO.getAllByccChildParameter(String.valueOf(Constants.CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD())).get(0).getValue());
                    String currentVersionNumber = new PubFunc().new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
                    if (dariaftPardakhtPPCModels.size() > 0 || noeMasouliat == 1)
                    {
                        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
                        for (DariaftPardakhtPPCModel dariaftpardakhtppc : dariaftPardakhtPPCModels)
                        {
                            CodeNoeVosolModel codeNoeVosolModel = codeNoeVosolDAO.getByCodeNoeVosol(dariaftpardakhtppc.getCodeNoeVosol());
                            int codeNoeSanad = codeNoeVosolModel.getCodeNoeSanad_dp();
                            int codeNoeCheck = codeNoeVosolModel.getCodeNoeCheck_dp();

                            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktors = dariaftPardakhtDarkhastFaktorPPCDAO.getForSendToSql(dariaftpardakhtppc.getCcDariaftPardakht());
                            JSONArray jsonDariaftPardakhtDarkhastFaktorPPCs = new JSONArray();
                            for (DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktor : dariaftPardakhtDarkhastFaktors)
                            {
                                jsonDariaftPardakhtDarkhastFaktorPPCs.put(dariaftPardakhtDarkhastFaktor.toJsonObject(foroshandehMamorPakhshModel , noeMasouliat , darkhastFaktorModel));
                            }
                            jsonArrayDariaftPardakhtPPC.put(dariaftpardakhtppc.toJsonObject(foroshandehMamorPakhshModel, noeMasouliat, darkhastFaktorModel, codeNoeSanad, codeNoeCheck, codeNoeVosolVajhNaghd, currentVersionNumber, jsonDariaftPardakhtDarkhastFaktorPPCs));
                        }
                    }
                    else //if (noeMasouliat != 1 && dariaftPardakhtPPCModels.size() ==0)
                    {
                        mPresenter.onError(R.string.errorNotFoundDariaftPardakht);
                        return;
                    }

                    if (noeMasouliat != 5)
                    {
                        if (noeMasouliat != 1)
                        {
                            JSONArray jsonDarkhastFaktorSatrs = new JSONArray();
                            for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
                            {
                                jsonDarkhastFaktorSatrs.put(darkhastFaktorSatr.toJsonObject(mPresenter.getAppContext()));
                            }
                            String jsonStringForVosol = darkhastFaktorModel.toJsonStringForVosol(foroshandehMamorPakhshModel, noeMasouliat, ccMarkazForosh, ccGorohNoeMoshtary, ccGorohNoeSenf, jsonDarkhastFaktorSatrs, jsonArrayTakhfifs, mablaghTakhfifFaktorSatr, jsonEmza, jsonMoshtaryPhotoPPC, jsonArrayDariaftPardakhtPPC);
                            jsonStringForVosol = jsonStringForVosol.replace("\\" , "");

                            int maxLogSize = 1000;
                            for(int i = 0; i <= jsonStringForVosol.length() / maxLogSize; i++)
                            {
                                int start = i * maxLogSize;
                                int end = (i+1) * maxLogSize;
                                end = end > jsonStringForVosol.length() ? jsonStringForVosol.length() : end;
                                Log.d("jsonFaktor", jsonStringForVosol.substring(start, end));
                            }
                            sendDarkhastFaktorDataToServer(customerDarkhastFaktorModel.getCcMoshtary(), customerDarkhastFaktorModel.getCcDarkhastFaktor(), jsonStringForVosol, noeMasouliat, position, foroshandehMamorPakhshModel);
                        }
                        else //if (noeMasouliat == 1)
                        {
                            JSONArray jsonDarkhastHavaleSatrs = new JSONArray();
                            for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
                            {
                                jsonDarkhastHavaleSatrs.put(darkhastFaktorSatr.toJsonObjectHavale(mPresenter.getAppContext()));
                            }
                            String jsonStringForVosolSard = darkhastFaktorModel.toJsonStringForVosolSard(foroshandehMamorPakhshModel, ccMarkazForosh, ccGorohNoeMoshtary, ccGorohNoeSenf, jsonDarkhastHavaleSatrs, jsonArrayTakhfifs, mablaghTakhfifFaktorSatr, jsonEmza, jsonMoshtaryPhotoPPC, jsonArrayDariaftPardakhtPPC);
                            jsonStringForVosolSard = jsonStringForVosolSard.replace("\\" , "");
                            sendDarkhastHavaleDataToServer(customerDarkhastFaktorModel.getCcMoshtary(), customerDarkhastFaktorModel.getCcDarkhastFaktor(), jsonStringForVosolSard, noeMasouliat, position, foroshandehMamorPakhshModel);
                        }
                    }
                    else //if (noeMasouliat == 5)
                    {

                    }
                }
            }
        });
    }*/


    private void checkMandehMojodi(CustomerDarkhastFaktorModel customerDarkhastFaktorModel, int noeMasouliat, int position) {
        MandehMojodyMashinDAO mandehMojodyMashinDAO = new MandehMojodyMashinDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
        String ccForoshandeh = String.valueOf(foroshandehMamorPakhshDAO.getIsSelect().getCcForoshandeh());
        String ccMamorPakhsh = String.valueOf(foroshandehMamorPakhshDAO.getIsSelect().getCcMamorPakhsh());
        String ccAnbarakAfrad = String.valueOf(anbarakAfradDAO.getAll().get(0).getCcAnbarak());

        long ccDarkhastFaktor = customerDarkhastFaktorModel.getCcDarkhastFaktor();
        int  ccMoshtary = customerDarkhastFaktorModel.getCcMoshtary();
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = darkhastFaktorSatrDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor,ccMoshtary);
        String ccSazmanForoshFaktor = String.valueOf(foroshandehMamorPakhshDAO.getIsSelect().getCcSazmanForosh());
        ArrayList<Integer> ccKalaCodesForUpdate = new ArrayList<>();
        for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrModels) {
            ccKalaCodesForUpdate.add(darkhastFaktorSatrModel.getCcKala());
        }
        String ccKalaCodes=new PubFunc().new DAOUtil().convertIntegerArrayToString(ccKalaCodesForUpdate);

        /** ررسی مغایرت مانده موجودی تنها برای فروشنده سرد و اسمارت بررسی می شود
         * {@param noeMasouliat(1)  فروشنده سرد
         * {@param noeMasouliat(3) فروشنده اسمارت
         * **/
        Log.i("checkMandehMojodi", "noeMasouliat: " + noeMasouliat);
        if (noeMasouliat == 1 || noeMasouliat == 3  || noeMasouliat == 6 || noeMasouliat == 8) {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());

            switch (serverIpModel.getWebServiceType()){
                case REST:
                    APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);
                    apiServiceRxjava.getMandehMojodyMashin( ccAnbarakAfrad, ccForoshandeh, ccMamorPakhsh,ccKalaCodes, ccSazmanForoshFaktor)
                            .compose(RxHttpRequest.parseHttpErrors(TemporaryRequestsListModel.class.getSimpleName(),TemporaryRequestsListActivity.class.getSimpleName(),"checkContradicts"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Response<GetMandehMojodyMashinResponse>>() {
                                @Override
                                public void onSubscribe( Disposable d) {
                                    mPresenter.bindDisposable(d);
                                }

                                @Override
                                public void onNext( Response<GetMandehMojodyMashinResponse> getMandehMojodyMashinResponseResponse) {

                                    final Set<KalaModel> allContradictions = new HashSet<>();
                                    if(getMandehMojodyMashinResponseResponse.body()!=null) {
                                        checkContradicts(getMandehMojodyMashinResponseResponse.body().getMandehMojodyMashinModels(), darkhastFaktorSatrModels, new Observer<HashMap<Integer, KalaModel>>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {
                                                mPresenter.bindDisposable(d);
                                            }

                                            @Override
                                            public void onNext(@NonNull HashMap<Integer, KalaModel> contradicts) {


                                                if (contradicts != null)
                                                    if (contradicts.size() > 0)
                                                        for (Integer key : contradicts.keySet()) {
                                                            if (!allContradictions.contains(key))
                                                            allContradictions.add(contradicts.get(key));
                                                        }
                                                Log.i("contradicts", "onNext: " + contradicts);
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {
                                                mPresenter.onErrorSendRequest(R.string.errorCheckMoghayerat, "");
                                                setLogToDB(Constants.LOG_EXCEPTION(), e.getMessage(), "TemporaryRequestsListModel", "" , "checkContradicts" , "onNext");
                                            }

                                            @Override
                                            public void onComplete() {


                                                switch (allContradictions.size()) {
                                                    ////**هیچ مغایرتی  نداریم**
                                                    case 0:
                                                        sendTempFaktor(customerDarkhastFaktorModel, position);
                                                        break;
                                                    default:
                                                        String name = "";
                                                        int counter =  0;
                                                        for (KalaModel kalaModel : allContradictions) {
                                                            if (counter  != allContradictions.size()-1)
                                                                name +=  kalaModel.getCodeKala() + " - "+ kalaModel.getNameKala() + ",";
                                                            else
                                                                name +=  kalaModel.getCodeKala() + " - "+ kalaModel.getNameKala();
                                                            counter ++;
                                                        }
                                                        mPresenter.onErrorSendRequest(R.string.errormoghayeratdarmojodiKala, name);
                                                        break;
                                                }
                                            }
                                        });
                                    }else{
                                        mPresenter.onErrorSendRequest(R.string.errorCheckMoghayerat, "");
                                        setLogToDB(Constants.LOG_EXCEPTION(), "null body", "TemporaryRequestsListModel", "" , "checkContradicts" , "onNext");

                                    }
                                }

                                @Override
                                public void onError( Throwable e) {
                                    mPresenter.onErrorSendRequest(R.string.errorSendData, "");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                    break;

                case gRPC:
                    mandehMojodyMashinDAO.fetchMandehMojodyMashinGrpc(mPresenter.getAppContext(), TreasuryListMapActivity.class.getSimpleName(), ccAnbarakAfrad, ccForoshandeh, ccMamorPakhsh,ccKalaCodes,ccSazmanForoshFaktor, new RetrofitResponse() {
                        @Override
                        public void onSuccess(ArrayList arrayListData) {
                             ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = new ArrayList<>(arrayListData);
                            final Set<KalaModel> allContradictions = new HashSet<>();

                            checkContradicts(mandehMojodyMashinModels, darkhastFaktorSatrModels, new Observer<HashMap<Integer,KalaModel>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d)
                                {
                                    mPresenter.bindDisposable(d);
                                }

                                @Override
                                public void onNext(@NonNull HashMap<Integer,KalaModel> contradicts)
                                {


                                    if (contradicts != null)
                                        if (contradicts.size() > 0)
                                            for (Integer key : contradicts.keySet()) {
                                                if (!allContradictions.contains(key))
                                                    allContradictions.add(contradicts.get(key));
                                            }


                                    Log.i("contradicts", "onNext: "+contradicts);
                                }

                                @Override
                                public void onError(@NonNull Throwable e)
                                {
                                    mPresenter.onErrorSendRequest(R.string.errorCheckMoghayerat, "");
                                }

                                @Override
                                public void onComplete()
                                {


                                    switch (allContradictions.size()) {
                                        ////**هیچ مغایرتی  نداریم**
                                        case 0:
                                            sendTempFaktor(customerDarkhastFaktorModel, position);
                                            break;
                                        default:
                                            String name = "";
                                            int counter =  0;
                                            for (KalaModel kalaModel : allContradictions) {
                                                if (counter  != allContradictions.size()-1)
                                                name +=  kalaModel.getCodeKala() + " - "+ kalaModel.getNameKala() + ",";
                                                else
                                                    name +=  kalaModel.getCodeKala() + " - "+ kalaModel.getNameKala();
                                                counter ++;
                                            }
                                            mPresenter.onErrorSendRequest(R.string.errormoghayeratdarmojodiKala, name);

                                            break;
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFailed(String type, String error) {
                            mPresenter.onErrorSendRequest(R.string.errorSendData, "");

                        }
                    });

                    break;
            }


        } else {
            sendTempFaktor(customerDarkhastFaktorModel, position);
        }

    }

    private void sendTempFaktor(CustomerDarkhastFaktorModel customerDarkhastFaktorModel, int position) {
        if (checkDateTime()) {
            if(checkMablaghMandehFaktor(customerDarkhastFaktorModel))
            {
                ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
                switch (serverIpModel.getWebServiceType()){
                    case REST:
                        controlInsertFaktor(customerDarkhastFaktorModel.getUniqID_Tablet(), String.valueOf(customerDarkhastFaktorModel.getCcMoshtary()), String.valueOf(customerDarkhastFaktorModel.getCcForoshandeh()), new OnControlFaktor() {
                            @Override
                            public void onError(int resErrorId) {
                                mPresenter.onError(resErrorId);
                            }

                            @Override
                            public void onSuccess(int flag) {
                                if (flag == 1) {
                                    mPresenter.onErrorSendRequest(R.string.errorResend, "");
                                } else {
                                    AsyncTaskSendRequest asyncTaskSendRequest = new AsyncTaskSendRequest(mPresenter.getAppContext(), customerDarkhastFaktorModel, position);
                                    asyncTaskSendRequest.sendRequestResponse = new SendRequestResponse() {
                                        @Override
                                        public void onError(int resId) {
                                            mPresenter.onErrorSendRequest(resId, "");
                                        }

                                        @Override
                                        public void onSuccess(int position, long ccDarkhastFaktorNew) {
                                            mPresenter.onSuccessSendRequest(position, ccDarkhastFaktorNew);


                                        }
                                    };
                                    asyncTaskSendRequest.execute();
                                }
                            }
                        });
                        break;

                    case gRPC:
                        controlInsertFaktorGrpc(customerDarkhastFaktorModel.getUniqID_Tablet(), String.valueOf(customerDarkhastFaktorModel.getCcMoshtary()), String.valueOf(customerDarkhastFaktorModel.getCcForoshandeh()), new OnControlFaktor() {
                            @Override
                            public void onError(int resErrorId) {
                                mPresenter.onError(resErrorId);
                            }

                            @Override
                            public void onSuccess(int flag) {
                                if (flag == 1) {
                                    mPresenter.onErrorSendRequest(R.string.errorResend, "");
                                } else {
                                    AsyncTaskSendRequest asyncTaskSendRequest = new AsyncTaskSendRequest(mPresenter.getAppContext(), customerDarkhastFaktorModel, position);
                                    asyncTaskSendRequest.sendRequestResponse = new SendRequestResponse() {
                                        @Override
                                        public void onError(int resId) {
                                            mPresenter.onErrorSendRequest(resId, "");
                                        }

                                        @Override
                                        public void onSuccess(int position, long ccDarkhastFaktorNew) {
                                            mPresenter.onSuccessSendRequest(position, ccDarkhastFaktorNew);


                                        }
                                    };
                                    asyncTaskSendRequest.execute();
                                }
                            }
                        });
                        break;


                }

            }



        } else {
            mPresenter.onErrorSendRequest(R.string.errorTimeLimitForRequest, "");
        }
    }


    //**fixed**//
    public HashMap<Integer, Integer> populateSumTedadEachCcKalaHash(ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrModels) {
            if (hashMap.containsKey(darkhastFaktorSatrModel.getCcKalaCode())) {
                int currentMojodi = hashMap.get(darkhastFaktorSatrModel.getCcKalaCode());
                hashMap.remove(darkhastFaktorSatrModel.getCcKalaCode());
                hashMap.put(darkhastFaktorSatrModel.getCcKalaCode(), currentMojodi + darkhastFaktorSatrModel.getTedad3());
            }else {
                hashMap.put(darkhastFaktorSatrModel.getCcKalaCode(), darkhastFaktorSatrModel.getTedad3());
            }
        }
        return hashMap;
    }

    public HashMap<Integer, MandehMojodyMashinModel> getDistinctValuesOfMandehMojodi(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        HashMap<Integer, MandehMojodyMashinModel> hashMap = new HashMap<>();

        for (MandehMojodyMashinModel mandehMojodyMashinModel : mandehMojodyMashinModels) {
            if (!hashMap.containsKey(mandehMojodyMashinModel.getCcKalaCode())) {
                hashMap.put(mandehMojodyMashinModel.getCcKalaCode(), mandehMojodyMashinModel);
            }
        }
        return hashMap;
    }

    public Set<Integer> populatMandehMojodiHash(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels) {
        Set<Integer> hashMap = new HashSet<>();
        for (MandehMojodyMashinModel mandehMojodyMashinModel : mandehMojodyMashinModels) {
            hashMap.add(mandehMojodyMashinModel.getCcKalaCode());
        }

        return hashMap;
    }





    private void checkContradicts(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels, ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels, Observer observer) {

        Set<Integer> hashMapMandehMojodi = populatMandehMojodiHash(mandehMojodyMashinModels);
        HashMap<Integer, Integer> hashMapSumEachGood = populateSumTedadEachCcKalaHash(darkhastFaktorSatrModels);
        KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());

        HashMap<Integer, KalaModel> allContradictions = new HashMap<>();
        Observable.fromIterable(mandehMojodyMashinModels)

                .flatMap(mandehMojodyMashinModel -> Observable.fromIterable(darkhastFaktorSatrModels)
                                .map( darkhastFaktorSatrModel ->
                                {

                                    if (!hashMapMandehMojodi.contains(darkhastFaktorSatrModel.getCcKalaCode())) {
                                        KalaModel kalaModel = kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode());
                                        if (!allContradictions.containsKey(darkhastFaktorSatrModel.getCcKalaCode())) {
//                                            Log.i("searchForContradictions", "!hashMapMandehMojodi.containsKey( darkhastFaktorSatrModel.getCcKalaCode())");
                                            allContradictions.put(kalaModel.getCcKalaCode(), kalaModel);
//                                            Log.i("allContradictionssa", "checkContradicts: " + allContradictions.get(0));
//
                                        }
                                    }

                                    if (mandehMojodyMashinModel.getCcKalaCode()== darkhastFaktorSatrModel.getCcKalaCode() &&   mandehMojodyMashinModel.getMaxMojody() < hashMapSumEachGood.get(darkhastFaktorSatrModel.getCcKalaCode())) {
                                        KalaModel kalaModel = kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode());
                                        if (!allContradictions.containsKey(kalaModel.getCcKalaCode())) {
                                            Log.i("checkContradicts", "distinctValuesOfMandehMojodi.get(ccKalaCode).getMaxMojody() < hashMapSumEachGood.get(darkhastFaktorSatrModel.getCcKalaCode())" + mandehMojodyMashinModel.getMaxMojody() + " " + hashMapSumEachGood.get(darkhastFaktorSatrModel.getCcKalaCode()));

                                            allContradictions.put(kalaModel.getCcKalaCode(), kalaModel);
                                            Log.i("checkContradicts", "checkContradicts: " + allContradictions.get(0));

                                        }
                                    }


                                    if (darkhastFaktorSatrModel.getCcKalaCode() == mandehMojodyMashinModel.getCcKalaCode() &&
                                            darkhastFaktorSatrModel.getShomarehBach().equals(mandehMojodyMashinModel.getShomarehBach()) &&
                                            darkhastFaktorSatrModel.getCcTaminKonandeh() == mandehMojodyMashinModel.getCcTaminKonandeh() &&
                                            darkhastFaktorSatrModel.getMablaghForosh() == mandehMojodyMashinModel.getGheymatForosh() &&
                                            darkhastFaktorSatrModel.getGheymatMasrafKonandeh() == mandehMojodyMashinModel.getGheymatMasrafKonandeh()) {
                                        if (darkhastFaktorSatrModel.getTedad3() > mandehMojodyMashinModel.getMax_MojodyByShomarehBach()) {

                                            KalaModel kalaModel = kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode());

                                            if (!allContradictions.containsKey(kalaModel.getCcKalaCode())) {
                                                Log.i("checkContradicts", "darkhastFaktorSatrModel.getTedad3() > mandehMojodyMashinModel.getMax_MojodyByShomarehBach()");
                                                allContradictions.put(kalaModel.getCcKalaCode(), kalaModel);
                                                Log.i("checkContradicts", "checkContradicts: " + allContradictions.get(0));
                                            }
//                                            allContradictions[0] += kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode()).getNameKala() + " ";

                                        }
                                    }


                                    return allContradictions;
                                })
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
