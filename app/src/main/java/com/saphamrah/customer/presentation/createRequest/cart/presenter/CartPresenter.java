package com.saphamrah.customer.presentation.createRequest.cart.presenter;

import android.content.Context;

import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.presentation.createRequest.cart.interactor.CartInteractor;
import com.saphamrah.customer.presentation.createRequest.cart.model.CartModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CartPresenter implements CartInteractor.PresenterOps, CartInteractor.RequiredPresenterOps {
    private WeakReference<CartInteractor.RequiredViewOps> view;
    private final CartModel model;

    public CartPresenter(CartInteractor.RequiredViewOps view) {
        this.view = new WeakReference<>(view);
        this.model = new CartModel(this);
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
    public void getDiscountAndBonuses() {

        List<DiscountModel> discountModels = new ArrayList<>();
        DiscountModel discountModel = new DiscountModel(1, "تخفیف حجمی درجه1", 4, 1400);
        DiscountModel discountModel1 = new DiscountModel(2, "تخفیف حجمی درجه2", 2, 2100);
        DiscountModel discountModel2 = new DiscountModel(3, "تخفیف حجمی درجه3", 1, 5000);
        DiscountModel discountModel3 = new DiscountModel(4, "تخفیف صنفی درجه1", 7, 6500);
        DiscountModel discountModel4 = new DiscountModel(5, "تخفیف صنفی درجه2", 3, 1300);
        DiscountModel discountModel5 = new DiscountModel(6, "تخفیف صنفی درجه2", 4, 8000);
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
    public void saveData(List<ProductModel> productModels, List<BonusModel> bonusModels, List<DiscountModel> discountModels) {
        model.saveData(productModels,bonusModels,discountModels);
    }
}
