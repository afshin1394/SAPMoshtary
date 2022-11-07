package com.saphamrah.customer.presentation.createRequest.selectableBonus.presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.customer.data.local.temp.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor.SelectableBonusInteractor;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.model.SelectableBonusModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SelectableBonusPresenter implements SelectableBonusInteractor.PresenterOps, SelectableBonusInteractor.RequiredPresenterOps{
    private WeakReference<SelectableBonusInteractor.RequiredViewOps> view;
    private SelectableBonusModel model;

    public SelectableBonusPresenter(SelectableBonusInteractor.RequiredViewOps view) {
        this.view = new WeakReference<>(view);
        this.model = new SelectableBonusModel(this);
    }


    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void getJayezeh() {

        List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelList = new ArrayList<>();
        JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel = new JayezehEntekhabiMojodiModel(1,"رب دلپذیر",15,19098,20,0);
        JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel1 = new JayezehEntekhabiMojodiModel(2,"کنسرو ماهی",10,19098,20,0);
        JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel2 = new JayezehEntekhabiMojodiModel(3,"دستمال 200 برگ",12,19098,20,0);
        JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel3 = new JayezehEntekhabiMojodiModel(4,"آب نارنگی",11,19098,20,0);
        JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel4 = new JayezehEntekhabiMojodiModel(5,"آب انار",12,19098,20,0);
        JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel5 = new JayezehEntekhabiMojodiModel(6,"آب سیب",18,19098,20,0);
        jayezehEntekhabiMojodiModelList.add(jayezehEntekhabiMojodiModel);
        jayezehEntekhabiMojodiModelList.add(jayezehEntekhabiMojodiModel1);
        jayezehEntekhabiMojodiModelList.add(jayezehEntekhabiMojodiModel2);
        jayezehEntekhabiMojodiModelList.add(jayezehEntekhabiMojodiModel3);
        jayezehEntekhabiMojodiModelList.add(jayezehEntekhabiMojodiModel4);
        jayezehEntekhabiMojodiModelList.add(jayezehEntekhabiMojodiModel5);


        List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels = new ArrayList<>();
        DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel = new DarkhastFaktorJayezehTakhfifModel(1,2910,1,"جایزه برندینگ دلپذیر",5,jayezehEntekhabiMojodiModelList.subList(0,2));
        DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel1 = new DarkhastFaktorJayezehTakhfifModel(1,2910,2,"جایزه برندینگ پگاه",5,jayezehEntekhabiMojodiModelList.subList(0,3));
        DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel2 = new DarkhastFaktorJayezehTakhfifModel(1,2910,3,"جایزه برندینگ میهن",5,jayezehEntekhabiMojodiModelList.subList(1,4));
        DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel3 = new DarkhastFaktorJayezehTakhfifModel(1,2910,4,"جایزه برندینگ پاکان",5,jayezehEntekhabiMojodiModelList.subList(2,4));
        DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel4 = new DarkhastFaktorJayezehTakhfifModel(1,2910,5,"جایزه برندینگ دلپذیر",5,jayezehEntekhabiMojodiModelList.subList(0,5));
        DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel5 = new DarkhastFaktorJayezehTakhfifModel(1,2910,6,"جایزه برندینگ توکلی",5,jayezehEntekhabiMojodiModelList.subList(1,3));
        Log.i("modelss1", "getJayezeh: "+jayezehEntekhabiMojodiModelList.toString());
        darkhastFaktorJayezehTakhfifModels.add(darkhastFaktorJayezehTakhfifModel1);
        darkhastFaktorJayezehTakhfifModels.add(darkhastFaktorJayezehTakhfifModel2);
        darkhastFaktorJayezehTakhfifModels.add(darkhastFaktorJayezehTakhfifModel3);
        darkhastFaktorJayezehTakhfifModels.add(darkhastFaktorJayezehTakhfifModel4);
        darkhastFaktorJayezehTakhfifModels.add(darkhastFaktorJayezehTakhfifModel5);
        darkhastFaktorJayezehTakhfifModels.add(darkhastFaktorJayezehTakhfifModel);
        Log.i("modelss2", "getJayezeh: "+darkhastFaktorJayezehTakhfifModels.get(0).getJayezehEntekhabiMojodiModelList());


        view.get().onGetJayezeh(darkhastFaktorJayezehTakhfifModels);


    }

    @Override
    public void getKalaForJayezeh() {

    }
}
