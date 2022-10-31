package com.saphamrah.customer.presentation.login.register.interactor;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.BaseBottomSheetRecyclerModel;
import com.saphamrah.customer.data.network.model.RegisterNetworkModel;

import java.util.ArrayList;

public interface RegisterInteractor {

    interface RequiredViewOps extends BaseView {
        void onGetIdentities(ArrayList<BaseBottomSheetRecyclerModel> itemTitles);
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
        void onGetIdentities(ArrayList<BaseBottomSheetRecyclerModel> itemTitles);
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
