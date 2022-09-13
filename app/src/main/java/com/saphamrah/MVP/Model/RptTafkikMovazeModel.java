package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.BaseMVP.RptDarkhastFaktorVazeiatMVP;
import com.saphamrah.BaseMVP.RptTafkikMovazeMVP;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptDarkhastFaktorVazeiatPPCDAO;
import com.saphamrah.DAO.RptTafkikMovazeDAO;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.Model.RptTafkikMovazeDataModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TafkikKalaMovazeDataModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptTafkikMovazeModel implements RptTafkikMovazeMVP.ModelOps
{

    private RptTafkikMovazeMVP.RequiredPresenterOps mPresenter;


    public RptTafkikMovazeModel(RptTafkikMovazeMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getDarkhastFaktorList()
    {
        RptTafkikMovazeDAO rptTafkikMovazeDAO = new RptTafkikMovazeDAO(mPresenter.getAppContext());

        ArrayList<RptTafkikMovazeDataModel> darkhastFaktorVazeiatModels = rptTafkikMovazeDAO.getAllForTafkikMovaze();
        mPresenter.onGetDarkhastFaktortList(darkhastFaktorVazeiatModels);
    }

    @Override
    public void getTafkikKalaList(String ccDarkhastFaktors)
    {
        RptTafkikMovazeDAO rptTafkikMovazeDAO = new RptTafkikMovazeDAO(mPresenter.getAppContext());

        ArrayList<TafkikKalaMovazeDataModel> darkhastFaktorVazeiatModels = rptTafkikMovazeDAO.getKalaByCcDarkhastFactor(ccDarkhastFaktors);
        mPresenter.onGetTafkikKalaList(darkhastFaktorVazeiatModels);
    }


    @Override
    public void updateData()
    {}

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }

    @Override
    public void onDestroy()
    {

    }


}
