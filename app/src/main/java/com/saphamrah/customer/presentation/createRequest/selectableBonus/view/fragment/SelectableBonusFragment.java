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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.reactivex.Flowable;


public class SelectableBonusFragment extends BaseFragment<SelectableBonusInteractor.PresenterOps, FragmentSelectableBonusBinding,CreateRequestActivity> implements SelectableBonusInteractor.RequiredViewOps, AdapterView.OnItemSelectedListener {


    private List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels;
    private SelectableBonusAdapter selectableBonusAdapter;
    private int counter = 0;


    public SelectableBonusFragment() {
        super(R.layout.fragment_selectable_bonus);
    }


    @Override
    protected void onBackPressed()
    {
        activity.paymentState = Constants.PaymentStates.CALCULATE_BONUS_DISCOUNT;
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViews() {



        setConfirmButton();

        viewBinding.btmShtSelectableBonus.linConfirm.setOnClickListener(v ->
        {

            darkhastFaktorJayezehTakhfifModels.get(counter).setSelected(true);
            setConfirmButton();

            if (darkhastFaktorJayezehTakhfifModels.size()>0 && counter<darkhastFaktorJayezehTakhfifModels.size()-2)
            {
                counter++;
                activity.setJayezehEntekhabiMojodiModel(darkhastFaktorJayezehTakhfifModels.get(counter).getJayezehEntekhabiMojodiModelList());
                viewBinding.btmShtSelectableBonus.spBonusDescription.setSelection(counter);
            }
            else
            {
                viewBinding.btmShtSelectableBonus.tvPayment.setText(R.string.confirm);
                activity.paymentState = Constants.PaymentStates.CONFIRM_REQUEST;
                navigateUp();
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setConfirmButton() {
        if (darkhastFaktorJayezehTakhfifModels.stream().filter(darkhastFaktorJayezehTakhfifModel -> !darkhastFaktorJayezehTakhfifModel.isSelected()).count() >0)
        {
            viewBinding.btmShtSelectableBonus.tvPayment.setText(R.string.next);
        }
        else
        {
            viewBinding.btmShtSelectableBonus.tvPayment.setText(R.string.confirm);
        }
    }

    @Override
    protected void setPresenter() {
        presenter = new SelectableBonusPresenter(this);
        presenter.getJayezeh();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onGetJayezeh(List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels) {
        this.darkhastFaktorJayezehTakhfifModels = darkhastFaktorJayezehTakhfifModels;
        setBonusSpinner();
        setSelectableBonusRecycler();
        setViews();


    }

    @Override
    public void onGetKalaForJayezeh() {

    }

    private void setBonusSpinner() {
        List<String> bonusTitles = new ArrayList<>();
        for (DarkhastFaktorJayezehTakhfifModel model : darkhastFaktorJayezehTakhfifModels) {
            bonusTitles.add(model.getSharhJayezehTakhfif());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.custom_spinner_title, bonusTitles);
        adapter.setDropDownViewResource(R.layout.custom_spinner_itemview);
        viewBinding.btmShtSelectableBonus.spBonusDescription.setAdapter(adapter);
        viewBinding.btmShtSelectableBonus.spBonusDescription.setOnItemSelectedListener(this);
    }

    private void setSelectableBonusRecycler()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        selectableBonusAdapter = new SelectableBonusAdapter(context, darkhastFaktorJayezehTakhfifModels.get(counter).getJayezehEntekhabiMojodiModelList(), (model, position, Action) -> {

        });
        viewBinding.RVBonusList.setLayoutManager(linearLayoutManager);
        viewBinding.RVBonusList.setAdapter(selectableBonusAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        this.counter = position;
        this.selectableBonusAdapter.setJayezehEntekhabiMojodiModels(darkhastFaktorJayezehTakhfifModels.get(position).getJayezehEntekhabiMojodiModelList());
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}