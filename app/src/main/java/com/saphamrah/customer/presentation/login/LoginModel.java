package com.saphamrah.customer.presentation.login;

import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.domain.repository.BankRepository;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginModel extends LoginInteracts.ModelOps {

    private LoginInteracts.RequiredPresenterOps loginRequiredPresenterOps;

    public LoginModel(LoginInteracts.RequiredPresenterOps loginRequiredPresenterOps) {
        this.loginRequiredPresenterOps = loginRequiredPresenterOps;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }

  /*  @Override
    List<Bank> getAllBanks() {
        return bankRepository.getAllBanks();
    }

    @Override
    void insertBanks(List<Bank> banks) {
        bankRepository.insertBanks(banks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO handle error
                    }
                });

    }*/
}
