package com.saphamrah.customer.presentation.createRequest.selectableBonus.view.fragment;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.temp.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import com.saphamrah.customer.databinding.FragmentSelectableBonusBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor.SelectableBonusInteractor;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.presenter.SelectableBonusPresenter;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.view.adapter.SelectableBonusAdapter;
import com.saphamrah.customer.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.Flowable;


public class SelectableBonusFragment extends BaseFragment<SelectableBonusInteractor.PresenterOps, FragmentSelectableBonusBinding,CreateRequestActivity> implements SelectableBonusInteractor.RequiredViewOps, AdapterView.OnItemSelectedListener {


    private List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels;
    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels;
    private SelectableBonusAdapter selectableBonusAdapter;

    public SelectableBonusFragment() {
        super(R.layout.fragment_selectable_bonus);
    }


    @Override
    protected void onBackPressed()
    {
        ((CreateRequestActivity) activity).paymentState = Constants.PaymentStates.CALCULATE_BONUS_DISCOUNT;
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        presenter.getJayezeh();
        setViews();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViews() {
        viewBinding.btmShtSelectableBonus.linConfirm.setOnClickListener(v ->
        {
            ((CreateRequestActivity) activity).paymentState = Constants.PaymentStates.CONFIRM_REQUEST;
            Log.i(TAG, "setViews: "+jayezehEntekhabiMojodiModels);
            activity.setJayezehEntekhabiMojodiModel(jayezehEntekhabiMojodiModels.stream().filter(jayezehEntekhabiMojodiModel -> jayezehEntekhabiMojodiModel.getSelectedCount() > 0).collect(Collectors.toList()));
            navigateUp();
        });
    }

    @Override
    protected void setPresenter() {
        presenter = new SelectableBonusPresenter(this);
    }

    @Override
    protected FragmentSelectableBonusBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSelectableBonusBinding.inflate(inflater, container, false);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public Context getAppContext() {
        return context;
    }

    @Override
    public void onGetJayezeh(List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels) {

        this.darkhastFaktorJayezehTakhfifModels = darkhastFaktorJayezehTakhfifModels;
        this.jayezehEntekhabiMojodiModels = new ArrayList<>();
        this.jayezehEntekhabiMojodiModels.clear();
        this.jayezehEntekhabiMojodiModels.addAll(darkhastFaktorJayezehTakhfifModels.get(0).getJayezehEntekhabiMojodiModelList());
        Log.i(TAG, "onGetJayezeh:2 " + jayezehEntekhabiMojodiModels);
        setBonusSpinner(darkhastFaktorJayezehTakhfifModels);
    }

    @Override
    public void onGetKalaForJayezeh() {

    }

    private void setBonusSpinner(List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels) {

        List<String> bonusTitles = new ArrayList<>();
        for (DarkhastFaktorJayezehTakhfifModel model : darkhastFaktorJayezehTakhfifModels) {
            bonusTitles.add(model.getSharhJayezehTakhfif());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.custom_spinner_itemview, bonusTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.btmShtSelectableBonus.tvBonusDescription.setAdapter(adapter);
        viewBinding.btmShtSelectableBonus.tvBonusDescription.setOnItemSelectedListener(this);
        Log.i(TAG, "setBonusSpinner: " + this.jayezehEntekhabiMojodiModels);
        setSelectableBonusRecycler();
    }

    private void setSelectableBonusRecycler()
    {
        Log.i(TAG, "setSelectableBonusRecycler:" + this.jayezehEntekhabiMojodiModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        selectableBonusAdapter = new SelectableBonusAdapter(context, jayezehEntekhabiMojodiModels, (model, position, Action) -> {

        });
        Log.i(TAG, "setSelectableBonusRecycler:" + this.jayezehEntekhabiMojodiModels);
        viewBinding.RVBonusList.setLayoutManager(linearLayoutManager);
        viewBinding.RVBonusList.setAdapter(selectableBonusAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        this.jayezehEntekhabiMojodiModels.clear();
        this.jayezehEntekhabiMojodiModels.addAll(darkhastFaktorJayezehTakhfifModels.get(position).getJayezehEntekhabiMojodiModelList());
        selectableBonusAdapter.notifyDataSetChanged();
        Log.i(TAG, "onItemSelected:2 " + jayezehEntekhabiMojodiModels);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}