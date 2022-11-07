package com.saphamrah.customer.presentation.createRequest.selectableBonus.model;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.presentation.createRequest.productRequest.interactor.ProductRequestMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor.SelectableBonusInteractor;

public class SelectableBonusModel extends  SelectableBonusInteractor.ModelOps {
    private SelectableBonusInteractor.RequiredPresenterOps selectableBonusRequiredPresenterOps;
    public SelectableBonusModel(SelectableBonusInteractor.RequiredPresenterOps selectableBonusInteractor) {
        selectableBonusRequiredPresenterOps = selectableBonusInteractor;
    }
    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
