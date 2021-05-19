package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.CheckBargashtiMVP;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.CreateDariaftPardakhtPPCJSONResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckBargashtiModel implements CheckBargashtiMVP.ModelOps
{
    private CheckBargashtiMVP.RequiredPresenterOps mPresenter;
    private  String ccdpBargashty = "-1,";
    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
    DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
    BargashtyDAO bargashtyDAO = new BargashtyDAO(BaseApplication.getContext());
    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
    DateUtils dateUtils = new DateUtils();
    private SimpleDateFormat simpleDateFormatShort = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
    private boolean canVosolDiffDayForSend = true;
    public CheckBargashtiModel(CheckBargashtiMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getAllCheckBargashti()
    {
        // get details in SQLite
        BargashtyDAO bargashtyDAO = new BargashtyDAO(BaseApplication.getContext());
        ArrayList<BargashtyModel> bargashtyModels = bargashtyDAO.getAll();
        mPresenter.onGetAllCheckBargashti(bargashtyModels);
    }

    @Override
    public void getDariaftPardakhtForSend(long ccDariaftPardakht , int position)
    {
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = dariaftPardakhtPPCDAO.getForSendToSqlByccDarkhastFaktor(ccDariaftPardakht);
        Log.d("treasury" , "dariaftPardakhtPPCModels.size : " + dariaftPardakhtPPCModels.size());
        if (dariaftPardakhtPPCModels.size() > 0)
        {
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
            if (foroshandehMamorPakhshModel == null)
            {
                mPresenter.onErrorSend(R.string.errorFindForoshandehMamorPakhsh);
            }
            else
            {
                ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(BaseApplication.getContext());
                if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
                {
                    mPresenter.onErrorSend(R.string.errorFindServerIP);
                }
                else
                {
                    int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
                    DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(dariaftPardakhtPPCModels.get(0).getCcDarkhastFaktor());
                    ParameterChildDAO childParameterDAO = new ParameterChildDAO(BaseApplication.getContext());
                    int codeNoeVosolVajhNaghd = Integer.parseInt(childParameterDAO.getAllByccChildParameter(String.valueOf(Constants.CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD())).get(0).getValue());
                    String currentVersionNumber = new PubFunc().new DeviceInfo().getCurrentVersion(BaseApplication.getContext());
                    sendDariaftPardakhtToServer(position, serverIpModel, dariaftPardakhtPPCModels, foroshandehMamorPakhshModel, noeMasouliat, darkhastFaktorModel, codeNoeVosolVajhNaghd, currentVersionNumber);
                }
            }
        }
        else
        {
            mPresenter.onErrorSend(R.string.errorNotExistItemForSend);
        }
    }


    /**
     * send to server
     */
    private void sendDariaftPardakhtToServer(final int position , ServerIpModel serverIpModel ,  ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, int noeMasouliat,  DarkhastFaktorModel darkhastFaktorModel, int codeNoeVosolVajhNaghd, String currentVersionNumber)
    {
        int convertToDay = (24 * 60 * 60 * 1000);
        long diffDays = 0;
        canVosolDiffDayForSend = true;
        final DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
        APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);

        String ccDpdfs = "-1";
        JSONArray jsonArrayDariaftPardakht = new JSONArray();
        JSONArray jsonArrayDariaftPardakhtDarkhastFaktor = new JSONArray();
        // get ccMarkazForosh , ccMarkazAnbar , ccMarkazSazmanForoshSakhtarForosh
        int ccMarkazForosh = 0;
        int ccMarkazAnbar = 0;
        int ccMarkazSazmanForoshSakhtarForosh = 0;
        if(noeMasouliat != 4)
        {
            ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
            ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
            ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
        }
        else
        {
            ccMarkazForosh = darkhastFaktorModel.getCcMarkazForosh();
            ccMarkazAnbar = darkhastFaktorModel.getCcMarkazAnbar();
            ccMarkazSazmanForoshSakhtarForosh = darkhastFaktorModel.getCcMarkazSazmanForoshSakhtarForosh();
        }

        //create JsonArray of DariaftPardakhtPPCModel
        for (DariaftPardakhtPPCModel dpModel : dariaftPardakhtPPCModels)
        {
            int codeNoeSanad = 0;
            int codeNoeCheck = 0;
            jsonArrayDariaftPardakht.put(dpModel.toJsonObjectCheckBargashty(ccMarkazForosh, ccMarkazAnbar, ccMarkazSazmanForoshSakhtarForosh, codeNoeSanad, codeNoeCheck, codeNoeVosolVajhNaghd, currentVersionNumber));
            ccDpdfs += "," + dpModel.getCcDariaftPardakht();

            /*
             * check date  ' check submissions by date today '
             */
            try {
                Date zamaneSabt = simpleDateFormatShort.parse(dpModel.getZamaneSabt());
                diffDays = (zamaneSabt.getTime() - dateUtils.getCurrentDate().getTime()) / convertToDay;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (diffDays > 0){
                canVosolDiffDayForSend = false;
            }
        }
        if (canVosolDiffDayForSend) {
            //create JsonArray of DariaftPardakhtDarkhastFaktorPPCModel
            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktors = dariaftPardakhtDarkhastFaktorPPCDAO.getForSendToSqlByccDariaftPardakhts(ccDpdfs);
            for (DariaftPardakhtDarkhastFaktorPPCModel dpdfModel : dariaftPardakhtDarkhastFaktors) {
                jsonArrayDariaftPardakhtDarkhastFaktor.put(dpdfModel.toJsonObjectCheckBargashty(ccMarkazForosh, ccMarkazAnbar, ccMarkazSazmanForoshSakhtarForosh, foroshandehMamorPakhshModel.getCcAfrad()));
            }

            try {
                JSONObject jsonObjectCheckBargashty = new JSONObject();
                jsonObjectCheckBargashty.put("DariaftPardakht", jsonArrayDariaftPardakht);
                jsonObjectCheckBargashty.put("DariaftPardakhtBargashty", jsonArrayDariaftPardakhtDarkhastFaktor);

                String strJsonObjectCheckBargashTy = jsonObjectCheckBargashty.toString();
                //saveToFile("treasury" + darkhastFaktorModel.getCcDarkhastFaktor() + ".txt" , strJsonObjectTreasury);

                Call<CreateDariaftPardakhtPPCJSONResult> call = apiServicePost.createDariaftPardakhtPPCBargashtyJSON(strJsonObjectCheckBargashTy);
                call.enqueue(new Callback<CreateDariaftPardakhtPPCJSONResult>() {
                    @Override
                    public void onResponse(Call<CreateDariaftPardakhtPPCJSONResult> call, Response<CreateDariaftPardakhtPPCJSONResult> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                CreateDariaftPardakhtPPCJSONResult result = response.body();
                                if (result.getSuccess()) {
                                    if (Integer.parseInt(result.getMessage()) > 0) {
                                        long ccDarkhastFaktorNew = Integer.parseInt(result.getMessage());
                                        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
                                        dariaftPardakhtPPCDAO.updateSendedCheckBargashty(ccDarkhastFaktorNew, dariaftPardakhtPPCModels.get(0).getCcDariaftPardakht(), 1);
                                        dariaftPardakhtDarkhastFaktorPPCDAO.updateSendedCheckBargashty(ccDarkhastFaktorNew, dariaftPardakhtPPCModels.get(0).getCcDariaftPardakht(), 1);
                                        mPresenter.onSuccessSend(position);
                                    } else {
                                        showResultError(Integer.parseInt(result.getMessage()));
                                    }

                                } else {
                                    showResultError(Integer.parseInt(result.getMessage()));
                                    setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "CheckBargashtiModel", "", "sendDariaftPardakhtToServer", "onResponse");
                                }
                            } else {
                                String errorMessage = "response not successful " + response.message();
                                if (response.errorBody() != null) {
                                    errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string();//+ "\n" + "can't send this log : " + logMessage;
                                }
                                setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "CheckBargashtiModel", "", "sendDariaftPardakhtToServer", "onResponse");
                                mPresenter.onErrorSend(R.string.errorOperationCheckBarghashtiResponse);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "CheckBargashtiModel", "", "sendDariaftPardakhtToServer", "onResponse");
                            mPresenter.onErrorSend(R.string.errorOperation);
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateDariaftPardakhtPPCJSONResult> call, Throwable t) {
                        setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "CheckBargashtiModel", "", "sendDariaftPardakhtToServer", "onFailure");
                        mPresenter.onErrorSend(R.string.errorOperationCheckBarghashti);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "CheckBargashtiModel", "", "sendDariaftPardakhtToServer", "");
            }
        } else {
            mPresenter.onErrorSend(R.string.checkDiffDayForSend);
        }

    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(BaseApplication.getContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    /**
     * show error for send check bargashty
     * @param errorCode
     */
    private void showResultError(int errorCode)
    {
        switch (errorCode)
        {
            case -1:
                mPresenter.onErrorSend(R.string.errorDuplicatedFaktor);
                break;
            case -2:
                mPresenter.onErrorSend(R.string.errorMoghayeratMablaghDarkhast);
                break;
            case -3:
                mPresenter.onErrorSend(R.string.errorMoghayeratTedadAghlam);
                break;
            case -4:
                mPresenter.onErrorSend(R.string.errorMoghayeratDarJayeze);
                break;
            case -5:
                mPresenter.onErrorSend(R.string.errorMoghayeratTakhfifTitr);
                break;
            case -6:
                mPresenter.onErrorSend(R.string.errorMoghayeratTakhfifSatr);
                break;
            case -7:
                mPresenter.onErrorSend(R.string.errorMoghayeratMablaghTakhfif);
                break;
            case -8:
                mPresenter.onErrorSend(R.string.errorMoghayeratTedadMarjoee);
                break;
            case -9:
                mPresenter.onErrorSend(R.string.errorMoghayeratVosolTitr);
                break;
            case -10:
                mPresenter.onErrorSend(R.string.errorMoghayeratTakhfifNaghdi);
                break;
            case -11:
                mPresenter.onErrorSend(R.string.errorMoghayeratNameSahebHesab);
                break;
            case -12:
                mPresenter.onErrorSend(R.string.errorMablaghVosolManfi);
                break;
            case -13:
                mPresenter.onErrorSend(R.string.errorNotFoundAddress);
                break;
            case -14:
                mPresenter.onErrorSend(R.string.errorTarikhFaktorVosol);
                break;
            default:
                mPresenter.onErrorSend(R.string.errorSendData);
        }
    }

    /**
     *  update checkBargashty
     */
    @Override
    public void updateListBargashty()
    {

        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(BaseApplication.getContext()).getOne();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getAllCheckBargashti();

                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onErrorUpdateListBargashty();
                }
                return false;
            }
        });

        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat ==8)
        {
            bargashtyDAO.fetchBargashty(BaseApplication.getContext(), "RptCheckBargashtyActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run(){
                            boolean deleteResult = bargashtyDAO.deleteAll();
                            boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
                            Message message = new Message();
                            if (deleteResult && insertResult)
                            {
                                for (int i = 0; i < arrayListData.size(); i++) {
                                    ccdpBargashty  += ((BargashtyModel)arrayListData.get(i)).getCcDariaftPardakht() + ",";

                                }
                                getDariaftPardakhtBargashty(ccdpBargashty);
                                message.arg1 = 1;
                            }
                            else
                            {
                                message.arg1 = -1;
                            }
                            handler.sendMessage(message);
                        }
                    };
                    thread.start();
                }
                @Override
                public void onFailed(String type, String error)
                {
                    mPresenter.onErrorUpdateListBargashty();
                    setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCheckBargashtyModel", "RptCheckBargashtyActivity", "updateListBargashty", "onFailed");
                }
            });
        }
        else if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            boolean deleteResult = bargashtyDAO.deleteAll();
            final ArrayList<Integer> arrayListCounter = new ArrayList<>();
            final String[] foroshandehArray;
            if(noeMasouliat != 4)
            {
                if (foroshandehMamorPakhshModel.getCcForoshandehs().contains(","))
                {
                    foroshandehArray = foroshandehMamorPakhshModel.getCcForoshandehs().split(",");//از فروشنده مامور پخش
                }
                else
                {
                    foroshandehArray = new String[]{foroshandehMamorPakhshModel.getCcForoshandehs()};
                }
            }
            else
            {
                ArrayList<String> ccForoshandehs = darkhastFaktorDAO.getccForoshandehForGetCheckBargashty();
                foroshandehArray = ccForoshandehs.toArray(new String[ccForoshandehs.size()]);
            }

            for (String strccForoshandeh : foroshandehArray)
            {
                bargashtyDAO.fetchBargashty(BaseApplication.getContext(), "RptCheckBargashtyActivity", strccForoshandeh, new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {

                        Thread thread = new Thread()
                        {
                            @Override
                            public void run(){
                                boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
                                if (insertResult)
                                {
                                    arrayListCounter.add(1);
                                    if (arrayListCounter.size() == foroshandehArray.length)
                                    {
                                        Message message = new Message();
                                        message.arg1 = 1;
                                        handler.sendMessage(message);
                                    }
                                }
                                else
                                {
                                    Message message = new Message();
                                    message.arg1 = -1;
                                    handler.sendMessage(message);
                                }
                            }
                        };
                        thread.start();
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onErrorUpdateListBargashty();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCheckBargashtyModel", "RptCheckBargashtyActivity", "updateListBargashty", "onFailed");
                    }
                });
            }
        }
    }

    private void getDariaftPardakhtBargashty(String ccdpBargashty)
    {
        dariaftPardakhtDarkhastFaktorPPCDAO.fetchDariaftPardakhtDarkhastFaktorPPC(BaseApplication.getContext(), "RptCheckBargashtyActivity", "3", ccdpBargashty, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        for (int i = 0; i <arrayListData.size() ; i++) {
                            long ccDariaftPardakhtBargashti = ((DariaftPardakhtDarkhastFaktorPPCModel)arrayListData.get(i)).getCcDarkhastFaktor();
                            dariaftPardakhtDarkhastFaktorPPCDAO.deleteByccDarkhastFaktor(ccDariaftPardakhtBargashti);
                            dariaftPardakhtPPCDAO.deleteByccDarkhastFaktor(ccDariaftPardakhtBargashti);
                        }
                        dariaftPardakhtDarkhastFaktorPPCDAO.insertGroup(arrayListData , true);
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onErrorUpdateListBargashty();
                setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCheckBargashtyModel", "RptCheckBargashtyActivity", "getDariaftPardakhtBargashty", "onFailed");
            }
        });
    }

}
