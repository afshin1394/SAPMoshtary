package com.saphamrah.customer.presentation.createRequest.selectableBonus.view.fragment;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import java.util.stream.Collectors;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Predicate;


public class SelectableBonusFragment extends BaseFragment<SelectableBonusInteractor.PresenterOps, FragmentSelectableBonusBinding,CreateRequestActivity> implements SelectableBonusInteractor.RequiredViewOps, AdapterView.OnItemSelectedListener {


    private List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels;
    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels;
    private SelectableBonusAdapter selectableBonusAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayAdapter<String> spinnerAdapter;
    private static final String TAG = SelectableBonusFragment.class.getSimpleName();
    private int counter = 0;





    @Override
    protected void onBackPressed()
    {
        activity.paymentState = Constants.PaymentStates.CALCULATE_BONUS_DISCOUNT;
        navigateUp();
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        jayezehEntekhabiMojodiModels = new ArrayList<>();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViews() {
        viewBinding.btmShtSelectableBonus.spBonusDescription.post(() -> viewBinding.btmShtSelectableBonus.spBonusDescription.setOnItemSelectedListener(SelectableBonusFragment.this));
        setConfirmButton();

        viewBinding.btmShtSelectableBonus.linConfirm.setOnClickListener(v ->
        {
            darkhastFaktorJayezehTakhfifModels.get(counter).setSelected(true);
            jayezehEntekhabiMojodiModels.addAll(Observable.fromIterable(darkhastFaktorJayezehTakhfifModels.get(counter).getJayezehEntekhabiMojodiModelList()).filter(jayezehEntekhabiMojodiModel -> jayezehEntekhabiMojodiModel.getSelectedCount()>0).toList().blockingGet());

            setConfirmButton();

            if (darkhastFaktorJayezehTakhfifModels.size() > 1 )
            {
                Log.i(TAG, "setViews: before remove"+ spinnerAdapter.getItem(counter));
                darkhastFaktorJayezehTakhfifModels.remove(counter);
                setBonusSpinner();

            }
            else
            {
                activity.setJayezehEntekhabiMojodiModels(jayezehEntekhabiMojodiModels);
                viewBinding.btmShtSelectableBonus.tvPayment.setText(R.string.confirm);
                activity.paymentState = Constants.PaymentStates.CONFIRM_REQUEST;
                navigateUp();
            }
        });
    }



    private void setConfirmButton() {
        if (darkhastFaktorJayezehTakhfifModels.size()>2)
        {
            viewBinding.btmShtSelectableBonus.tvPayment.setText(R.string.next);
        }
        else
        {
            viewBinding.btmShtSelectableBonus.tvPayment.setText(R.string.confirm);
        }
    }

    @Override
    protected void setPresenter()
    {
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
        Log.i(TAG, "onGetJayezeh: "+darkhastFaktorJayezehTakhfifModels.size());
        setSelectableBonusRecycler();
        setViews();
    }

    @Override
    public void onGetKalaForJayezeh() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setBonusSpinner() {
        List<String> bonusTitles = new ArrayList<>();
        List<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels = Observable.fromIterable(this.darkhastFaktorJayezehTakhfifModels).filter(darkhastFaktorJayezehTakhfifModel -> !darkhastFaktorJayezehTakhfifModel.isSelected()).toList().blockingGet();


        for (DarkhastFaktorJayezehTakhfifModel model : darkhastFaktorJayezehTakhfifModels)
        {
            bonusTitles.add(model.getSharhJayezehTakhfif());
        }
        spinnerAdapter = new ArrayAdapter<String>(context,
                R.layout.custom_spinner_title_bold, bonusTitles);
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_itemview);
        viewBinding.btmShtSelectableBonus.spBonusDescription.setAdapter(spinnerAdapter);

    }

    private void setSelectableBonusRecycler()
    {
        if (linearLayoutManager == null)
        linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);

        selectableBonusAdapter = new SelectableBonusAdapter(context,darkhastFaktorJayezehTakhfifModels.get(counter).getJayezehEntekhabiMojodiModelList(), (model, position, Action) -> {
          switch (Action){
              case ADD:
              case REMOVE:
              case SELECT:
                  Log.i(TAG, "setSelectableBonusRecycler: "+model.getSelectedCount());
                  updateByccJayezeh(model);
          }
        });
        viewBinding.RVBonusList.setLayoutManager(linearLayoutManager);
        viewBinding.RVBonusList.setAdapter(selectableBonusAdapter);


    }

    private void updateByccJayezeh(JayezehEntekhabiMojodiModel model) {
        for (JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel : darkhastFaktorJayezehTakhfifModels.get(counter).getJayezehEntekhabiMojodiModelList()) {
            if (jayezehEntekhabiMojodiModel.getCcJayezeh() == model.getCcJayezeh()){
                jayezehEntekhabiMojodiModel.setSelectedCount(model.getSelectedCount());
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Log.i(TAG, "onItemSelected: position" + position);
        Log.i(TAG, "onItemSelected: before select"+this.counter);
        this.counter = position;
        Log.i(TAG, "onItemSelected: after select"+this.counter);

        setSelectableBonusRecycler();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}