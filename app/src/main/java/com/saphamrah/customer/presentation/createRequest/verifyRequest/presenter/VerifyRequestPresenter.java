package com.saphamrah.customer.presentation.createRequest.verifyRequest.presenter;

import android.content.Context;

import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.interactor.VerifyRequestInteractor;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.model.VerifyRequestModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

public class VerifyRequestPresenter implements VerifyRequestInteractor.PresenterOps, VerifyRequestInteractor.RequiredPresenterOps {

    private WeakReference<VerifyRequestInteractor.RequiredViewOps> view;
    private VerifyRequestModel model;

    public VerifyRequestPresenter(VerifyRequestInteractor.RequiredViewOps view) {
        this.view = new WeakReference<>(view);
        model = new VerifyRequestModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void saveData(List<ProductModel> productModels, List<BonusModel> bonusModels, List<DiscountModel> discountModels) {

    }

    @Override
    public void saveRequest(List<ProductModel> productModelGlobal, List<DiscountModel> discountModelsGlobal, List<BonusModel> bonusModelsGlobal, List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelsGlobal) {
       List<ProductModel> orderedProducts =  Observable.fromIterable(productModelGlobal!=null?productModelGlobal:new ArrayList<>()).filter(productModel -> productModel.getOrderCount()>0).toList().blockingGet();
       List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels = Observable.fromIterable(elamMarjoeeForoshandehModelsGlobal!=null?elamMarjoeeForoshandehModelsGlobal:new ArrayList<>()).filter(elamMarjoeeForoshandehModel -> elamMarjoeeForoshandehModel.getTedad3() > 0).toList().blockingGet();
       model.saveProducts(orderedProducts);
       model.saveDiscounts(discountModelsGlobal);
       model.saveBonuses(bonusModelsGlobal);
       model.saveElamMarjoeeForoshandeh(elamMarjoeeForoshandehModels);
    }
}
