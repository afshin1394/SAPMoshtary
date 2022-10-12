package com.saphamrah.customer.presentation.interactors;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.network.model.RegisterNetworkModel;

public interface RegisterInteracts {

    interface RequiredViewOps extends BaseView {
        void onGetIdentities();
        void onGetProvinces();
        void onGetCities();
        void onSendRegisterData();
        void onErrorFirstName();
        void onErrorLastName();
        void onErrorMobile();
    }

    interface PresenterOps extends BasePresenterOps {
        void getIdentities();
        void getProvinces();
        void getCities();
        void checkValidityOfRegisterData(RegisterNetworkModel registerModel);
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {
        void onGetIdentities();
        void onGetProvinces();
        void onGetCities();
        void onSendRegisterData();
    }

    abstract class ModelOps extends BaseModel {
        public abstract void getIdentities();
        public abstract void getProvinces();
        public abstract void getCities();
        public abstract void sendRegisterData(RegisterNetworkModel registerModel);
    }

}
