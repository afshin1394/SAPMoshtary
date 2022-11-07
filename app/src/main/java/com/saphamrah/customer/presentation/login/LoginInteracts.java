package com.saphamrah.customer.presentation.login;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.local.db.entity.Bank;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public interface LoginInteracts {

    interface RequiredViewOps extends BaseView {
//        void onGetAllBanks(List<Bank> banks);
    }

    interface PresenterOps extends BasePresenterOps {
//        void getAllBanks();

//        void insertBanks(List<Bank> banks);
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {
//        void onGetAllBanks(List<Bank> banks);
    }

    abstract class ModelOps extends BaseModel {

    /*    abstract void getAllBanks();

        abstract void insertBanks(List<Bank> banks);*/
    }

}
