package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.BanksInfoMVP;
import com.saphamrah.DAO.BankDAO;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetBankByMamorPakhshPositionResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanksInfoModel implements BanksInfoMVP.ModelOps
{

    private BanksInfoMVP.RequiredPresenterOps mPresenter;

    public BanksInfoModel(BanksInfoMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getListOfAllBanks()
    {
        BankDAO bankDAO = new BankDAO(mPresenter.getAppContext());
        ArrayList<BankModel> bankModels = bankDAO.getAll();
        mPresenter.onGetListOfAllBanks(bankModels);
    }

    @Override
    public void getBranchOfBank(String noeBank, String lat, String lng)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());

        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            mPresenter.onError(R.string.errorFindServerIP);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetBankByMamorPakhshPositionResult> call = apiServiceGet.getBankPosition(noeBank, lat, lng);
            call.enqueue(new Callback<GetBankByMamorPakhshPositionResult>()
            {
                @Override
                public void onResponse(Call<GetBankByMamorPakhshPositionResult> call, Response<GetBankByMamorPakhshPositionResult> response)
                {
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetBankByMamorPakhshPositionResult result = response.body();
                            if (result != null)
                            {
                                mPresenter.onGetBranchOfBank(result.getData());
                            }
                            else
                            {
                                mPresenter.onGetBranchOfBank(null);
                            }
                        }
                        else
                        {
                            mPresenter.onError(R.string.errorGetData);
                        }
                    }
                    catch (Exception exception)
                    {
                        setLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "BanksInfoModel", "", "getBranchOfBank", "onResponse");
                        exception.printStackTrace();
                        mPresenter.onError(R.string.errorGetData);
                    }
                }

                @Override
                public void onFailure(Call<GetBankByMamorPakhshPositionResult> call, Throwable t)
                {
                    setLogToDB(LogPPCModel.LOG_EXCEPTION, t.getMessage(), "BanksInfoModel", "", "getBranchOfBank", "onFailure");
                    mPresenter.onError(R.string.errorGetData);
                }
            });
        }
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
}
