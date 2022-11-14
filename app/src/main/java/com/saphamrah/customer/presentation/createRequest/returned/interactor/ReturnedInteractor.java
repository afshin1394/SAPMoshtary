package com.saphamrah.customer.presentation.createRequest.returned.interactor;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;

import java.util.List;

public interface ReturnedInteractor {
    interface RequiredViewOps extends BaseView {

        void onGetMarjoee(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels);
    }

    interface PresenterOps extends BasePresenterOps {

        void getMarjoee();
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

        void onGetMarjoee(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels);
    }

    abstract class ModelOps extends BaseModel {

        public abstract void getMarjoee();
    }
}
