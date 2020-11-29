package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.MainMenuMVP;
import com.saphamrah.MVP.Model.MainMenuModel;
import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainMenuPresenter implements MainMenuMVP.PresenterOps , MainMenuMVP.RequiredPresenterOps
{

    private WeakReference<MainMenuMVP.RequiredViewOps> mView;
    private MainMenuMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public MainMenuPresenter(MainMenuMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MainMenuModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////
    @Override
    public void onConfigurationChanged(MainMenuMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }


    @Override
    public void getMenuItems(String parentsId)
    {
        if (parentsId != null)
        {
            mModel.getMenuItems(parentsId);
        }
        else
        {
            mView.get().showToast(R.string.errorGetMenu , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkSelectedMenuItem(SystemMenuModel selectedMenuItem)
    {
        if (selectedMenuItem.getLinkTypeName().equals(SystemMenuModel.LINK_TYPE_ACTIVITY()))
        {
            Log.d("MainMenu","Link: " + selectedMenuItem.getLink());
            mView.get().openActivity(selectedMenuItem.getLink());
        }
        else if (selectedMenuItem.getLinkTypeName().equals(SystemMenuModel.LINK_TYPE_ALERT_DIALOG()))
        {
            if (selectedMenuItem.getLink().equals(SystemMenuModel.ALERT_ABOUT()))
            {
                mModel.getAlertAboutData();
            }
            else if (selectedMenuItem.getLink().equals(SystemMenuModel.ALERT_EXIT()))
            {
                mView.get().showExitAlert();
            }
        }
        else if (selectedMenuItem.getLinkTypeName().equals(SystemMenuModel.LINK_TYPE_FRAGMENT()))
        {
            //open fragment
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }



    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetMenuItems(ArrayList<SystemMenuModel> systemMenuModels)
    {
        mView.get().setMenuAdapter(systemMenuModels);
    }

    @Override
    public void onGetAlertAboutData(String currentVersion, String newestVersion, String lastStableVersion, String newFeatures)
    {
        mView.get().showAboutAlert(currentVersion, newestVersion, lastStableVersion, newFeatures);
    }


}
