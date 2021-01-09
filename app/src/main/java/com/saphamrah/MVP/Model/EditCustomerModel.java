package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.EditCustomerMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.ForoshandehMoshtaryDAO;
import com.saphamrah.DAO.GorohDAO;
import com.saphamrah.DAO.MessageBoxDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.NoeFaaliatForMoarefiMoshtaryJadidDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.CreateMoshtaryWithJSONResult;
import com.saphamrah.WebService.ServiceResponse.GetUpdateMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.UpdateNotificationMessageBoxResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCustomerModel implements EditCustomerMVP.ModelOps {

    private EditCustomerMVP.RequiredPresenterOps mPresenter;
    private int tagUpdate;
    private MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
    // updateAddress
    private int ccMoshtaryUpdate;
    private int ccAddressUpdate;
    private String telephoneUpdate;
    private String postalCodeUpdate;

    // updateNoeFaaliatSenf
    private int ccNoeFaaliatUpdate;
    private String nameNoeFaaliatUpdate;
    private int ccNoeSenfUpdate;
    private String nameNoeSenfUpdate;

    // updateSaateVisit
    private String timeUpdate;

    // updateCustomerMobile
    private String mobileUpdate;

    // updateHasAnbarAndMasahateMaghaze
    private int hasAnbarUpdate;
    private int masahateMaghazeUpdate;

    public EditCustomerModel(EditCustomerMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getUpdateableItems() {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(String.valueOf(Constants.CC_EDIT_CUSTOMER_ITEMS()));

        mPresenter.onGetUpdateableItems(childParameterModels);
    }

    @Override
    public void getCustomerInfo(int ccMoshtary) {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
        String nationalCode = moshtaryModel.getCodeMely();

        String mobile = moshtaryModel.getMobile();
        String masahat = String.valueOf(moshtaryModel.getMasahatMaghazeh());
        int hasAnbar = moshtaryModel.getHasAnbar();

        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String noeShakhsiat = childParameterDAO.getTextByParameterNameAndValue(Constants.NOE_SHAKHSIAT(), String.valueOf(moshtaryModel.getCodeNoeShakhsiat()));

        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        Log.d("EditCustomer", "moshtaryModel:"+moshtaryModel.toString() );
        String noeSenf = gorohDAO.getByccGoroh(moshtaryModel.getCcNoeSenf()).get(0).getNameGoroh();
        String noeFaaliat = gorohDAO.getByccGoroh(moshtaryModel.getCcNoeMoshtary()).get(0).getNameGoroh();
        mPresenter.onGetBaseCustomerInfo(nationalCode, mobile, masahat, hasAnbar, moshtaryModel.getCodeNoeShakhsiat(), noeShakhsiat, "", noeSenf, noeFaaliat);

        getCustomerAddresses(ccMoshtary);
    }

    private void getCustomerAddresses(int ccMoshtary) {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = moshtaryAddressDAO.getByccMoshtary(ccMoshtary);
        mPresenter.onGetMoshtaryAddress(moshtaryAddressModels);
    }



    @Override
    public void getNoeFaaliat() {
        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());

        String ccGorohsOfNoeFaaliat = noeFaaliatForMoarefiMoshtaryJadidDAO.getDistinctccNoeMoshtary();
        ArrayList<GorohModel> noeFaaliatItems = gorohDAO.getByccGorohs(ccGorohsOfNoeFaaliat);
        ArrayList<Integer> noeFaaliatIds = new ArrayList<>();
        ArrayList<String> noeFaaliatNames = new ArrayList<>();
        for (GorohModel model : noeFaaliatItems) {
            noeFaaliatIds.add(model.getCcGoroh());
            noeFaaliatNames.add(model.getNameGoroh());
        }
        if (noeFaaliatIds.size() > 0) {
            mPresenter.onGetNoeFaaliat(noeFaaliatIds, noeFaaliatNames);
            getNoeSenf(noeFaaliatIds.get(0), true);
        } else {
            mPresenter.onError(R.string.errorGetData);
        }
    }

    @Override
    public void getNoeSenf(int ccGorohLink, boolean showAlertDialog) {
        NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        String ccGorohsOfSenf = noeFaaliatForMoarefiMoshtaryJadidDAO.getDistinctccSenfMoshtary(ccGorohLink);

        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        ArrayList<GorohModel> noeSenfItems = gorohDAO.getccNoeSenfByccGorohLink(ccGorohsOfSenf);
        ArrayList<Integer> noeSenfIds = new ArrayList<>();
        ArrayList<String> noeSenfNames = new ArrayList<>();
        for (GorohModel model : noeSenfItems) {
            noeSenfIds.add(model.getCcGoroh());
            noeSenfNames.add(model.getNameGoroh());
        }
        mPresenter.onGetNoeSenf(showAlertDialog, noeSenfIds, noeSenfNames);
    }

    /*
    tag update = 1
    */
    @Override
    public void updateAddress(int ccMoshtary, int ccAddress, String telephone, String postalCode) {
        ccMoshtaryUpdate = ccMoshtary;
        ccAddressUpdate = ccAddress;
        telephoneUpdate = telephone;
        postalCodeUpdate = postalCode;
        tagUpdate = 1;

        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());

        MoshtaryModel moshtaryModel = new MoshtaryModel();
        moshtaryModel.setCcMoshtary(ccMoshtary);
        moshtaryModel.setCcAfrad(moshtaryDAO.getccAfradByccMoshtary(ccMoshtary));
        moshtaryModel.setMobile("");
        moshtaryModel.setCodeMely("");
        moshtaryModel.setCcNoeMoshtary(-1);
        moshtaryModel.setCcNoeSenf(-1);
        moshtaryModel.setMasahatMaghazeh(-1);
        moshtaryModel.setHasAnbar(-1);

        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
        MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();
        moshtaryAddressModel.setCcAddress(ccAddress);
        moshtaryAddressModel.setCcMoshtaryAddress(moshtaryAddressDAO.getMoshtaryAddressByccMoshtaryAndccAddress(ccMoshtary, ccAddress));
        moshtaryAddressModel.setTelephone(telephone);
        moshtaryAddressModel.setCodePosty(postalCode);

        moshtaryAddressModels.add(moshtaryAddressModel);

        String data = prepareData(moshtaryModel, "", moshtaryAddressModels);
        sendJsonToServer(data, tagUpdate);

    }

    /*
    tag update = 2
     */
    @Override
    public void updateNoeFaaliatSenf(int ccMoshtary, int ccNoeFaaliat, String nameNoeFaaliat, int ccNoeSenf, String nameNoeSenf) {
        ccMoshtaryUpdate = ccMoshtary;
        ccNoeFaaliatUpdate = ccNoeFaaliat;
        nameNoeFaaliatUpdate = nameNoeFaaliat;
        ccNoeSenfUpdate = ccNoeSenf;
        nameNoeSenfUpdate = nameNoeSenf;
        tagUpdate = 2;

        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = new MoshtaryModel();
        moshtaryModel.setCcMoshtary(ccMoshtary);
        moshtaryModel.setCcAfrad(moshtaryDAO.getccAfradByccMoshtary(ccMoshtary));
        moshtaryModel.setMobile("");
        moshtaryModel.setCodeMely("");
        moshtaryModel.setCcNoeMoshtary(ccNoeFaaliat);
        moshtaryModel.setCcNoeSenf(ccNoeSenf);
        moshtaryModel.setMasahatMaghazeh(-1);
        moshtaryModel.setHasAnbar(-1);

        String data = prepareData(moshtaryModel, "", new ArrayList<MoshtaryAddressModel>());
        sendJsonToServer(data , tagUpdate);



    }

    /*
    tag update = 3
     */
    @Override
    public void updateSaateVisit(int ccMoshtary, String time) {
        ccMoshtaryUpdate = ccMoshtary;
        timeUpdate = time;
        tagUpdate = 3;

        Calendar calendar = Calendar.getInstance();
        String[] splittedVisitTime = time.trim().replace(" ", "").split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splittedVisitTime[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(splittedVisitTime[1]));
        String saateVisit = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(calendar.getTime());
  /*      if (new ForoshandehMoshtaryDAO(mPresenter.getAppContext()).updateSaateVisit(ccMoshtary , saateVisit))
        {
            mPresenter.onUpdateSaateVisit(time);
        }
        else
        {
            mPresenter.onFailedUpdate();
        }*/
    }

    /*
    tag update = 4
     */
    @Override
    public void updateCustomerMobile(int ccMoshtary, String mobile) {
        ccMoshtaryUpdate = ccMoshtary;
        mobileUpdate = mobile;
        tagUpdate = 4;

        MoshtaryModel moshtaryModel = new MoshtaryModel();
        moshtaryModel.setCcMoshtary(ccMoshtary);
        moshtaryModel.setCcAfrad(moshtaryDAO.getccAfradByccMoshtary(ccMoshtary));
        moshtaryModel.setMobile(mobile);
        moshtaryModel.setCodeMely("");
        moshtaryModel.setCcNoeMoshtary(-1);
        moshtaryModel.setCcNoeSenf(-1);
        moshtaryModel.setMasahatMaghazeh(-1);
        moshtaryModel.setHasAnbar(-1);

        String data = prepareData(moshtaryModel, "", new ArrayList<MoshtaryAddressModel>());
        Log.i("EditCustomerModel", data);
        sendJsonToServer(data, tagUpdate);


    }

    /*
    tag update = 5
     */
    @Override
    public void updateHasAnbarAndMasahateMaghaze(int ccMoshtary, int hasAnbar, int masahateMaghaze) {
        ccMoshtaryUpdate = ccMoshtary;
        hasAnbarUpdate = hasAnbar;
        masahateMaghazeUpdate = masahateMaghaze;
        tagUpdate = 5;

        MoshtaryModel moshtaryModel = new MoshtaryModel();
        moshtaryModel.setCcMoshtary(ccMoshtary);
        moshtaryModel.setCcAfrad(moshtaryDAO.getccAfradByccMoshtary(ccMoshtary));
        moshtaryModel.setMobile("");
        moshtaryModel.setCodeMely("");
        moshtaryModel.setCcNoeMoshtary(-1);
        moshtaryModel.setCcNoeSenf(-1);
        moshtaryModel.setMasahatMaghazeh(masahateMaghaze);
        moshtaryModel.setHasAnbar(hasAnbar);

        String data = prepareData(moshtaryModel, "", new ArrayList<MoshtaryAddressModel>());
        sendJsonToServer(data, tagUpdate);


    }

    /*
    send Json String to server
    entry function is Json String
    tag update : updateAddress = 1 , updateNoeFaaliatSenf = 2 , updateSaateVisit = 3 , updateCustomerMobile = 4 , updateHasAnbarAndMasahateMaghaze = 5
     */
    private void sendJsonToServer(String jsonString, int tag) {

//        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
//        String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
//, "");
//        String serverPort = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
//, "");
        ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        String serverIP=serverIpModel.getServerIp();
        String serverPort=serverIpModel.getPort();
        if (!serverIP.trim().equals("") && !serverPort.trim().equals("")) {
            final Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.arg1 == 1) {

                        //  updateAddress
                        if (tag == 1) {
                            MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(BaseApplication.getContext());
                            if (moshtaryAddressDAO.updateTelephonePostalCode(ccAddressUpdate, ccMoshtaryUpdate, telephoneUpdate, postalCodeUpdate)) {
                                getCustomerAddresses(ccMoshtaryUpdate);
                            } else {
                                mPresenter.onFailedUpdate();
                            }
                        }
                        // updateNoeFaaliatSenf
                        else if (tag == 2) {
                            if (moshtaryDAO.updateNoeFaaliat(ccMoshtaryUpdate, ccNoeFaaliatUpdate)) {
                                mPresenter.onUpdateNoeFaaliat(nameNoeFaaliatUpdate);
                            } else {
                                mPresenter.onFailedUpdate();
                            }
                        }
                        // updateSaateVisit
                        else if (tag == 3) {
                            // update saat
                        }
                        // updateCustomerMobile
                        else if (tag == 4) {
                            if (moshtaryDAO.updateMobile(ccMoshtaryUpdate, mobileUpdate)) {
                                mPresenter.onUpdateMobile(mobileUpdate);
                            } else {
                                mPresenter.onFailedUpdate();
                            }
                        }
                        // updateHasAnbarAndMasahateMaghaze
                        else if (tag == 5) {
                            if (moshtaryDAO.updateHasAnbarAndMasahateMaghaze(ccMoshtaryUpdate, hasAnbarUpdate, masahateMaghazeUpdate)) {
                                mPresenter.onUpdateHasAnbarMasahateMaghaze(hasAnbarUpdate, masahateMaghazeUpdate);
                            } else {
                                mPresenter.onFailedUpdate();
                            }
                        }
                        // fail result
                    } else if (msg.arg1 == -1)
                        mPresenter.onFailedUpdate();
                    {
//
                    }
                    return false;
                }
            });
            //final APIServicePost apiServicePost = ApiClient.getClient(serverIP, serverPort).create(APIServicePost.class);
            final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);

            Call<GetUpdateMoshtaryResult> call = apiServicePost.getUpdateMoshtaryResult(jsonString);
            call.enqueue(new Callback<GetUpdateMoshtaryResult>() {
                @Override
                public void onResponse(Call<GetUpdateMoshtaryResult> call, Response<GetUpdateMoshtaryResult> response) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            if (response.isSuccessful() && response.body() != null) {
                                GetUpdateMoshtaryResult result = response.body();
                                if (result.getSuccess()) {
                                    message.arg1 = 1;
                                } else {
                                    message.arg1 = -1;
                                }


                                handler.sendMessage(message);
                            }
                        }
                    };


                    thread.start();
                }

                @Override
                public void onFailure(Call<GetUpdateMoshtaryResult> call, Throwable t) {
                    Message message = new Message();
                    message.arg1 = -1;
                    handler.sendMessage(message);


                }
            });
        }

    }




    /*
    convert Model Json to string for send to server
     */
    private String prepareData(MoshtaryModel moshtaryModel, String encodedNationalCardBitmap, ArrayList<MoshtaryAddressModel> moshtaryAddressModels) {
        String jsonData = "";
        JSONObject jsonObject = new JSONObject();
        try {

            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();


            jsonObject.put("ccForoshandeh", foroshandehMamorPakhshModel.getCcForoshandeh());
            jsonObject.put("ccMarkazSazmanForosh", foroshandehMamorPakhshModel.getCcMarkazSazmanForosh());
            jsonObject.put("ccMoshtary", moshtaryModel.getCcMoshtary());

            jsonObject.put("ccAfrad", moshtaryModel.getCcAfrad() == -1 ? JSONObject.NULL : moshtaryModel.getCcAfrad());
            jsonObject.put("CodeMely", moshtaryModel.getCodeMely().equals("") ? JSONObject.NULL : moshtaryModel.getCodeMely());
            jsonObject.put("CodeMelyImage", encodedNationalCardBitmap.equals("") ? JSONObject.NULL : encodedNationalCardBitmap);
            jsonObject.put("ccNoeMoshtary", moshtaryModel.getCcNoeMoshtary() == -1 ? JSONObject.NULL : moshtaryModel.getCcNoeMoshtary());
            jsonObject.put("ccNoeSenf", moshtaryModel.getCcNoeSenf() == -1 ? JSONObject.NULL : moshtaryModel.getCcNoeSenf());
            jsonObject.put("HasAnbar", moshtaryModel.getHasAnbar() == -1 ? JSONObject.NULL : moshtaryModel.getHasAnbar());
            jsonObject.put("MasahateMaghazeh", moshtaryModel.getMasahatMaghazeh() == -1 ? JSONObject.NULL : moshtaryModel.getMasahatMaghazeh());
            jsonObject.put("Mobile", moshtaryModel.getMobile().equals("") ? JSONObject.NULL : moshtaryModel.getMobile());


            JSONArray jsonArrayAddress = new JSONArray();
            for (MoshtaryAddressModel addressModel : moshtaryAddressModels) {
                JSONObject jsonObjectAddress = new JSONObject();
                jsonObjectAddress.put("ccAddress", addressModel.getCcAddress() == -1 ? JSONObject.NULL : addressModel.getCcAddress());
                jsonObjectAddress.put("ccMohstaryAddress", addressModel.getCcMoshtaryAddress() == -1 ? JSONObject.NULL : addressModel.getCcMoshtaryAddress());
                jsonObjectAddress.put("Telephone", addressModel.getTelephone().equals("") ? JSONObject.NULL : addressModel.getTelephone());
                jsonObjectAddress.put("CodePosty", addressModel.getCodePosty().equals("") ? JSONObject.NULL : addressModel.getCodePosty());
                jsonArrayAddress.put(jsonObjectAddress);
            }
            jsonObject.put("MoshtaryAddress", jsonArrayAddress);
            jsonData = jsonObject.toString();
        } catch (Exception e) {
            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "EditCustomerModel", "", "prepareData", "");
        }
        return jsonData;
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }

}
