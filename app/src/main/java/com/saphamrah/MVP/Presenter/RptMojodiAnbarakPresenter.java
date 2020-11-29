package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptMojodiAnbrakMVP;
import com.saphamrah.MVP.Model.RptMojodiAnbarakModel;
import com.saphamrah.Model.RptMojodiAnbarModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RptMojodiAnbarakPresenter implements RptMojodiAnbrakMVP.PresenterOps , RptMojodiAnbrakMVP.RequiredPresenterOps
{

    private WeakReference<RptMojodiAnbrakMVP.RequiredViewOps> mView;
    private RptMojodiAnbrakMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptMojodiAnbarakPresenter(RptMojodiAnbrakMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptMojodiAnbarakModel(this);
    }

    @Override
    public void onConfigurationChanged(RptMojodiAnbrakMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void getMojodiAnbar()
    {
        mModel.getMojodiAnbar();
    }

    @Override
    public void updateMojodiAnbar()
    {
        mModel.updateMojodiAnbar();
    }

    @Override
    public void checkSearchNameKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels, String searchWord , boolean viewAsTable)
    {
        if (mojodiAnbarModels != null && mojodiAnbarModels.size() > 0)
        {
            if (searchWord.trim().equals(""))
            {
                mView.get().showToast(R.string.enterValueForSearch , Constants.INFO_MESSAGE() , Constants.DURATION_LONG());
            }
            else
            {
                ArrayList<RptMojodiAnbarModel> arrayList = new ArrayList<>();
                for (RptMojodiAnbarModel mojodiAnbarModel : mojodiAnbarModels)
                {
                    if (mojodiAnbarModel.getNameKala().contains(searchWord))
                    {
                        arrayList.add(mojodiAnbarModel);
                    }
                }
                if (viewAsTable)
                {
                    mView.get().showSearchResult(arrayList , viewAsTable);
                }
                else
                {
                    mView.get().showSearchResult(arrayList , viewAsTable);
                }
            }
        }
        else
        {
            mView.get().showToast(R.string.nothingItemForSearch , Constants.INFO_MESSAGE() , Constants.DURATION_LONG());
        }
    }

    @Override
    public void sortByCodeKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable)
    {
        Collections.sort(mojodiAnbarModels, new Comparator<RptMojodiAnbarModel>() {
            @Override
            public int compare(RptMojodiAnbarModel o1, RptMojodiAnbarModel o2) {
                return o1.getCodeKala().compareTo(o2.getCodeKala());
            }
        });
        mView.get().onSortByCodeKala(mojodiAnbarModels , viewAsTable);
    }

    @Override
    public void sortByNameKala(boolean viewAsTable)
    {
        mModel.getMojodiAnbarOrderByNameKala(viewAsTable);
    }

    @Override
    public void sortByCount(boolean viewAsTable)
    {
        mModel.getMojodiAnbarOrderByCount(viewAsTable);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
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
    public void onGetMojodiAnbar(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels)
    {
        if (mojodiAnbarModels != null)
        {
            mView.get().setAdapter(mojodiAnbarModels);
        }
        else
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetMojodiAnbarOrderByNameKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable)
    {
        if (mojodiAnbarModels != null)
        {
            mView.get().onSortByNameKala(mojodiAnbarModels , viewAsTable);
        }
        else
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetMojodiAnbarOrderByCount(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels, boolean viewAsTable)
    {
        if (mojodiAnbarModels != null)
        {
            mView.get().onSortByCount(mojodiAnbarModels , viewAsTable);
        }
        else
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onFailedGetMojodiAnbar(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


}
