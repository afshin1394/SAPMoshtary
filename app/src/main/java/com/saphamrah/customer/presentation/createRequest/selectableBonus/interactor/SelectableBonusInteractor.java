package com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.local.temp.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.SelectableBonus;

import java.util.List;

public interface SelectableBonusInteractor {
    interface RequiredViewOps extends BaseView {
       void onGetJayezeh(List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels);
       void onGetKalaForJayezeh();
    }

    interface PresenterOps extends BasePresenterOps {
       void getJayezeh();
       void getKalaForJayezeh();
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

    }

    abstract class ModelOps extends BaseModel {

    }
}
