package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.VarizNaghdBeBankMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MarkazShomarehHesabDAO;
import com.saphamrah.DAO.VarizBeBankDAO;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.VarizBeBankModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.VarizBeBankResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VarizNaghdBeBankModel implements VarizNaghdBeBankMVP.ModelOps {
    private VarizNaghdBeBankMVP.RequiredPresenterOps mPresenter;
    private String TAG = "VarizBeBAnk";
    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
    private int ccAfrad = 0;
    VarizBeBankDAO varizBeBankDAO = new VarizBeBankDAO(BaseApplication.getContext());
    ArrayList<com.saphamrah.Model.VarizBeBankModel> varizBeBankModels = new ArrayList<>();
    public VarizNaghdBeBankModel(VarizNaghdBeBankMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    /**
    get all bank from MarkazShomarehHesabDAO
     */
    @Override
    public void  getAllBank() {
        // get details in SQLite
        MarkazShomarehHesabDAO markazShomarehHesabDAO = new MarkazShomarehHesabDAO(BaseApplication.getContext());
        ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels = markazShomarehHesabDAO.getAll();
        mPresenter.onGetAllBank(markazShomarehHesabModels);
    }



    /**
  setModelAndUpdate
  dariaftPardakhtDarkhastFaktorPPCModel update DAO
  dariaftPardakhtPPCModel update DAO
     getCcDariaftPardakhtDarkhastFaktor in DariaftPardakhtDarkhastFaktorPPCModel

     */
    @Override
    public void updateDaoAll (int ccBankSanad , String nameShobehSanad , String codeShobehSand , String shomareHesabSanad , int ccShomareHesab , String shomarehSanad ,String nameBankSanad , String tarikhSanad, ArrayList<VarizBeBankModel> modelsArrayList) {
        

        DateUtils dateUtils = new DateUtils();
    for (int i = 0 ; i< modelsArrayList.size() ; i ++){

        VarizBeBankModel model = new VarizBeBankModel();

        model.setCcDariaftPardakht(modelsArrayList.get(i).getCcDariaftPardakht());
        model.setExtraProp_ccBankSanad(ccBankSanad);
        model.setExtraProp_NameShobehSanad(nameShobehSanad);
        model.setExtraProp_CodeShobehSand(codeShobehSand);
        model.setExtraProp_ShomareHesabSanad(shomareHesabSanad);
        model.setExtraProp_ccShomareHesab(ccShomareHesab);
        model.setExtraProp_ShomarehSanad(shomarehSanad);
        model.setExtraProp_TarikhSanad(dateUtils.persianToGregorianWhithTime(tarikhSanad));
        model.setExtraProp_NameBankSanad(nameBankSanad);
        model.setCodeNoeVosol(Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD());
        model.setExtraProp_IsSelected(1);


        if (varizBeBankDAO.UpdateNaghdBeFish(model)){
            mPresenter.onUpdateDao(true);
        } else {
            mPresenter.onUpdateDao(false);
        }

    }

    }


    @Override
    public void getAllMoshtary() {
        varizBeBankModels = varizBeBankDAO.getAll();
        mPresenter.onGetAllMoshtary(varizBeBankModels);
    }


    /**
     * click to Refresh fab
     */

    @Override
    public void getRefresh() {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());

        ccAfrad = foroshandehMamorPakhshDAO.getCCAfrad();

        varizBeBankDAO.fetchBargashty(BaseApplication.getContext(), TAG, ccAfrad, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {

                boolean deleteResult = varizBeBankDAO.deleteAll();
                boolean insertResult = varizBeBankDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult)
                {
                    mPresenter.onGetRefresh(R.string.successUpdate , Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
                    getSumMablagh();
                } else {
                    mPresenter.onGetRefresh(R.string.failUpdate , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }

                    }


            @Override
            public void onFailed(String type, String error) {
                mPresenter.onGetRefresh(R.string.failUpdate , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        });
    }

    @Override
    public void getSumMablagh() {
        varizBeBankModels = varizBeBankDAO.getIsSelected();
        mPresenter.onGetSumMablagh(varizBeBankModels);
    }


    @Override
    public void getAllVarizBeBank() {
        ArrayList<VarizBeBankModel> models  = varizBeBankDAO.getVarizUpdate();
        mPresenter.onGetAllVarizBeBank(models);
    }

    @Override
    public void sendVariz(VarizBeBankModel varizBeBankModel) {
        ArrayList<VarizBeBankModel> varizBeBankModels = varizBeBankDAO.getByCcShomarehSanad(varizBeBankModel.getExtraProp_ShomarehSanad());
        JSONObject jsonObjectFinal = new JSONObject();
        JSONArray jsonArrayVarizBeBank = varizBeBankModel.toJsonArrayVariz(varizBeBankModels);
        try {
            jsonObjectFinal.put("varizBeBank" , jsonArrayVarizBeBank);

            String strVariz = jsonObjectFinal.toString();

            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(BaseApplication.getContext());
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
            {
                mPresenter.onErrorSend(R.string.errorFindServerIP);
            }
            else
            {
                APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);
                Call<VarizBeBankResult> call = apiServicePost.varizVajehNaghdBeBank(strVariz);
                call.enqueue(new Callback<VarizBeBankResult>()
                {
                    @Override
                    public void onResponse(Call<VarizBeBankResult> call, Response<VarizBeBankResult> response) {

                            if (response.isSuccessful() && response.body() != null) {
                                VarizBeBankResult result = response.body();
                                if (result.getSuccess()) {
                                    if (Integer.parseInt(result.getMessage()) > 0) {
                                        mPresenter.onSuccessSend();
                                        int ccDariaftPardakhtNew = Integer.parseInt(result.getMessage());
                                    }
                                } else {
                                    showResultError(Integer.parseInt(result.getMessage()));
                                }
                            }
                    }

                    @Override
                    public void onFailure(Call<VarizBeBankResult> call, Throwable t)
                    {
                        mPresenter.onErrorSend(R.string.errorSendData);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * set text for show Toast and handler response API send Variz Be Bank
     * @param errorCode
     */
    private void showResultError(int errorCode)
    {
        switch (errorCode)
        {
            case -1:
                mPresenter.onErrorSend(R.string.errorDuplicatedVarizBeBank);
                break;
            case -2:
                mPresenter.onErrorSend(R.string.errorTarikhVarizBeBank);
                break;
            default:
                mPresenter.onErrorSend(R.string.errorSendData);
        }
    }


    /**
     * check shomareh sanad for update
     */
    public void checkHaveShomarehSanadForUpdate(String ExtraProp_ShomarehSanad) {
        boolean have = varizBeBankDAO.checkHaveShomarehSanadForUpdate(ExtraProp_ShomarehSanad);
        mPresenter.onCheckHaveShomarehSanadForUpdate(have);
    }


}
