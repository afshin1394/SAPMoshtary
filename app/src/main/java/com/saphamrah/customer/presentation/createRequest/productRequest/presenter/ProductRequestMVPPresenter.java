package com.saphamrah.customer.presentation.createRequest.productRequest.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.saphamrah.customer.base.BasePresenter;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.presentation.createRequest.productRequest.interactor.ProductRequestMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.productRequest.model.ProductRequestMVPModel;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestMVPPresenter extends BasePresenter<ProductRequestMVPInteractor.RequiredViewOps,ProductRequestMVPInteractor.ModelOps> implements ProductRequestMVPInteractor.PresenterOps, ProductRequestMVPInteractor.RequiredPresenterOps{


    public ProductRequestMVPPresenter(ProductRequestMVPInteractor.RequiredViewOps view) {
        super(view);
        this.model = new ProductRequestMVPModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        model.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public Context getContext() {
        return view.get().getAppContext();
    }

    @Override
    public void getJayezehTakhfifDetails() {
        List<DiscountModel> discountModels = new ArrayList<>();
        DiscountModel discountModel = new DiscountModel(1, "اب انار", 4, 1400);
        DiscountModel discountModel1 = new DiscountModel(2, "اب سیب", 2, 2100);
        DiscountModel discountModel2 = new DiscountModel(3, "آبلیمو", 1, 5000);
        DiscountModel discountModel3 = new DiscountModel(4, "پلاستیک فریزر", 7, 6500);
        DiscountModel discountModel4 = new DiscountModel(5, "اب زرشک", 3, 1300);
        DiscountModel discountModel5 = new DiscountModel(6, "اب زرشک", 4, 8000);
        discountModels.add(discountModel);
        discountModels.add(discountModel1);
        discountModels.add(discountModel2);
        discountModels.add(discountModel3);
        discountModels.add(discountModel4);
        discountModels.add(discountModel5);


        List<BonusModel> bonusModels = new ArrayList<>();
        BonusModel bonusModel = new BonusModel(1, "اب انار", 4);
        BonusModel bonusModel1 = new BonusModel(2, "اب سیب", 2);
        BonusModel bonusModel2 = new BonusModel(3, "آبلیمو", 1);
        BonusModel bonusModel3 = new BonusModel(4, "پلاستیک فریزر", 7);
        BonusModel bonusModel4 = new BonusModel(5, "اب زرشک", 3);
        BonusModel bonusModel5 = new BonusModel(6, "اب زرشک", 4);
        bonusModels.add(bonusModel);
        bonusModels.add(bonusModel1);
        bonusModels.add(bonusModel2);
        bonusModels.add(bonusModel3);
        bonusModels.add(bonusModel4);
        bonusModels.add(bonusModel5);

        view.get().onGetDiscountAndBonuses(discountModels,bonusModels);

    }

    @Override
    public void getProducts() {
        view.get().showLoading("");
        new Handler().postDelayed(() -> {
          view.get().onGetProducts();
          view.get().dismissLoading();
        }, 1000);

    }
}
