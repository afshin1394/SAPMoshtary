package com.saphamrah.customer.presentation.createRequest.returned.model;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.presentation.createRequest.returned.interactor.ReturnedInteractor;

import java.util.ArrayList;
import java.util.List;

public class ReturnedModel extends ReturnedInteractor.ModelOps
{
    private ReturnedInteractor.RequiredPresenterOps presenter;


    public ReturnedModel(ReturnedInteractor.RequiredPresenterOps presenter) {
        this.presenter = presenter;
    }





    @Override
    public void getMarjoee() {
        List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels = new ArrayList<>();
        ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel1 = new ElamMarjoeeForoshandehModel(1,"1245335","حلوا شکری","216556698","22/7/1401","22/11/1401",0,430000f);
        ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel2 = new ElamMarjoeeForoshandehModel(2,"1245335","گز اصفهان","216556698","22/7/1401","22/11/1401",0,230000f);
        ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel3 = new ElamMarjoeeForoshandehModel(3,"1245335","دستمال مرطوب","216556698","22/7/1401","22/11/1401",0,130000f);
        ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel4 = new ElamMarjoeeForoshandehModel(4,"1245335","ماست سنتی","216556698","22/7/1401","22/11/1401",0,630000f);
        ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel5 = new ElamMarjoeeForoshandehModel(5,"1245335","ماست چیلی","216556698","22/7/1401","22/11/1401",0,130300f);
        ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel6 = new ElamMarjoeeForoshandehModel(6,"1245335","ارده رژیمی","216556698","22/7/1401","22/11/1401",0,223000f);
        elamMarjoeeForoshandehModels.add(elamMarjoeeForoshandehModel1);
        elamMarjoeeForoshandehModels.add(elamMarjoeeForoshandehModel2);
        elamMarjoeeForoshandehModels.add(elamMarjoeeForoshandehModel3);
        elamMarjoeeForoshandehModels.add(elamMarjoeeForoshandehModel4);
        elamMarjoeeForoshandehModels.add(elamMarjoeeForoshandehModel5);
        elamMarjoeeForoshandehModels.add(elamMarjoeeForoshandehModel6);
        presenter.onGetMarjoee(elamMarjoeeForoshandehModels);
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
