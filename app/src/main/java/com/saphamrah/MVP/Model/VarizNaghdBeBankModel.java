package com.saphamrah.MVP.Model;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.VarizNaghdBeBankMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MarkazShomarehHesabDAO;
import com.saphamrah.DAO.VarizBeBankDAO;
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
    private String TAG = "VarizBeBank";
    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
    private int ccAfrad = 0;
    VarizBeBankDAO varizBeBankDAO = new VarizBeBankDAO(BaseApplication.getContext());
    ArrayList<VarizBeBankModel> varizBeBankModels = new ArrayList<>();

    public VarizNaghdBeBankModel(VarizNaghdBeBankMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    /**
     * get all bank from MarkazShomarehHesabDAO
     */
    @Override
    public void getAllBank() {
        // get details in SQLite
        MarkazShomarehHesabDAO markazShomarehHesabDAO = new MarkazShomarehHesabDAO(BaseApplication.getContext());
        ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels = markazShomarehHesabDAO.getAll();
        mPresenter.onGetAllBank(markazShomarehHesabModels);
    }


    /**
     * setModelAndUpdate
     * dariaftPardakhtDarkhastFaktorPPCModel update DAO
     * dariaftPardakhtPPCModel update DAO
     * getCcDariaftPardakhtDarkhastFaktor in DariaftPardakhtDarkhastFaktorPPCModel
     */
    @Override
    public void updateDaoAll(int ccBankSanad, String nameShobehSanad, String codeShobehSand, String shomareHesabSanad, int ccShomareHesab, String shomarehSanad, String nameBankSanad, String tarikhSanad, ArrayList<VarizBeBankModel> modelsArrayList, String mablaghEntekhabi) {

        double mablaghSabtShodeVorodi = Double.valueOf(mablaghEntekhabi.trim().replace(",", ""));

        boolean isUpdate = true;
        for (int i = 0; i < modelsArrayList.size(); i++) {
            DateUtils dateUtils = new DateUtils();
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
            /*
             * check mablagh vorodi
             * when mablaghSabtShodeZero = true we should break for()
             */
            boolean mablaghSabtShodeZero = false;
            if (mablaghSabtShodeVorodi >= modelsArrayList.get(i).getExtraProp_MablaghSabtShode()) {
                model.setExtraProp_MablaghSabtShode(modelsArrayList.get(i).getMablagh());
            } else if (mablaghSabtShodeVorodi < modelsArrayList.get(i).getExtraProp_MablaghSabtShode() && mablaghSabtShodeVorodi > 0){
                model.setExtraProp_MablaghSabtShode(mablaghSabtShodeVorodi);
                mablaghSabtShodeZero = true;
            }
            mablaghSabtShodeVorodi -= modelsArrayList.get(i).getExtraProp_MablaghSabtShode();

            isUpdate = varizBeBankDAO.updateNaghdBeFish(model);

            if (mablaghSabtShodeZero){
                break;
            }
        }

        if (isUpdate) {
            mPresenter.onUpdateDao(true);
        } else {
            mPresenter.onUpdateDao(false);
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

        varizBeBankDAO.fetchVarizBeBanck(BaseApplication.getContext(), TAG, ccAfrad, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {

                boolean deleteResult = varizBeBankDAO.deleteAll();
                boolean insertResult = varizBeBankDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    mPresenter.onGetRefresh(R.string.successUpdate, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
                    getSumMablagh();
                } else {
                    mPresenter.onGetRefresh(R.string.failUpdate, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }

            }


            @Override
            public void onFailed(String type, String error) {
                mPresenter.onGetRefresh(R.string.failUpdate, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
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
        ArrayList<VarizBeBankModel> models = varizBeBankDAO.getVarizUpdate();
        mPresenter.onGetAllVarizBeBank(models);
    }

    /**
     * varizBeBank = vosol vajeh naghd kamel be fish tabdil shode
     * varizBeBankExtra = vosol vajeh naghd be sourat nesfeh be fish tabdil shode
     * @param varizBeBankModel
     */
    @Override
    public void sendVariz(VarizBeBankModel varizBeBankModel) {
        ArrayList<VarizBeBankModel> varizBeBankModelsMablagh = varizBeBankDAO.getByCcShomarehSanadMablagh(varizBeBankModel.getExtraProp_ShomarehSanad());
        ArrayList<VarizBeBankModel> varizBeBankModelsExtraProp_Mablagh = varizBeBankDAO.getByCcShomarehSanadExtraProp_Mablagh(varizBeBankModel.getExtraProp_ShomarehSanad());
        JSONObject jsonObjectFinal = new JSONObject();
        JSONArray jsonArrayVarizBeBank = varizBeBankModel.toJsonArrayVariz(varizBeBankModelsMablagh);
        JSONArray jsonArrayVarizBeBankExtra = varizBeBankModel.toJsonArrayVariz(varizBeBankModelsExtraProp_Mablagh);

        try {
            jsonObjectFinal.put("varizBeBank", jsonArrayVarizBeBank);
            jsonObjectFinal.put("varizBeBankExtra", jsonArrayVarizBeBankExtra);

            String strVariz = jsonObjectFinal.toString();

            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().postServerFromShared(BaseApplication.getContext());
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
                                    boolean isSend = varizBeBankDAO.updateIsSend(Integer.parseInt(varizBeBankModel.getExtraProp_ShomarehSanad()));
                                    boolean ccDaryaftPardakhtUpdate = varizBeBankDAO.updateCcDaryaftPardakht(varizBeBankModel.getCcDariaftPardakht() , ccDariaftPardakhtNew);
                                    if (isSend && ccDaryaftPardakhtUpdate){
                                        mPresenter.onSuccessSend();
                                    } else {
                                        mPresenter.onErrorSend(R.string.errorSabtVariz);
                                    }
                                }
                            } else {
                                showResultError(Integer.parseInt(result.getMessage()));
                            }
                        } else {
                            mPresenter.onErrorSend(R.string.errorSabtVarizServer);
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
            mPresenter.onErrorSend(R.string.errorSendDataResend);
        }

    }

    /**
     * set text for show Toast and handler response API send Variz Be Bank
     *
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
