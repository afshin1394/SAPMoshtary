package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.MainMenuMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.SystemMenuDAO;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainMenuModel implements MainMenuMVP.ModelOps
{

    private MainMenuMVP.RequiredPresenterOps mPresenter;

    public MainMenuModel(MainMenuMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getMenuItems(String parentsId)
    {
        try
        {
            SystemMenuDAO systemMenuDAO = new SystemMenuDAO(mPresenter.getAppContext());
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshDAO.getOne());
            ArrayList<SystemMenuModel> arrayListAllMenuItems = systemMenuDAO.getMenuByParent(noeMasouliat , parentsId);
            Log.d("MainMenuModel","arrayListAllMenuItems: " + arrayListAllMenuItems.toString());
            mPresenter.onGetMenuItems(arrayListAllMenuItems);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "MainMenuModel", "", "getMenuItems", "");
            mPresenter.onGetMenuItems(new ArrayList<SystemMenuModel>());
        }
    }

    @Override
    public void getAlertAboutData()
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());


        final String currentVersion = new PubFunc().new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());

        if (!currentVersion.trim().equals("") && !serverIpModel.getServerIp().equals("") && !serverIpModel.getPort().equals(""))
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);//ApiClient.getClient(serverIP , port).create(APIServiceGet.class);
            Call<GetVersionResult> call = apiServiceGet.getVersionInfo();
            call.enqueue(new Callback<GetVersionResult>() {
                @Override
                public void onResponse(Call<GetVersionResult> call, Response<GetVersionResult> response)
                {
                    if (response.raw().body() != null)
                    {
                        long contentLength = response.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(mPresenter.getAppContext(),Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "MainModel", "", "getAlertAboutData", "onResponse");
                    }
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            GetVersionResult result = response.body();
                            mPresenter.onGetAlertAboutData(currentVersion, result.getNewVersion(), result.getStableVersion() , "");
                        }
                        else
                        {
                            mPresenter.onGetAlertAboutData(currentVersion , "" , "" , "");
                        }
                    }
                    catch (Exception exception)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(),exception.toString(), "MainModel", "", "getAlertAboutData", "onResponse");
                        exception.printStackTrace();
                        mPresenter.onGetAlertAboutData(currentVersion , "" , "" , "");
                    }
                }

                @Override
                public void onFailure(Call<GetVersionResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(),t.toString(), "MainModel", "", "getAlertAboutData", "onFailure");
                    mPresenter.onGetAlertAboutData(currentVersion , "" , "" , "");
                }
            });
        }
        else
        {
            mPresenter.onGetAlertAboutData(currentVersion , "" , "" , "");
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
