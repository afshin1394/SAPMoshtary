package com.saphamrah.customer.presentation.createRequest.returned.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.databinding.FragmentReturnBinding;
import com.saphamrah.customer.databinding.FragmentReturnedBinding;
import com.saphamrah.customer.databinding.FragmentSelectableBonusBinding;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.cart.view.fragment.CartFragmentDirections;
import com.saphamrah.customer.presentation.createRequest.returned.interactor.ReturnedInteractor;
import com.saphamrah.customer.presentation.createRequest.returned.presenter.ReturnedPresenter;
import com.saphamrah.customer.presentation.createRequest.returned.view.adapter.ReturnedAdapter;
import com.saphamrah.customer.presentation.createRequest.returned.view.adapter.SelectedReturnedAdapter;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor.SelectableBonusInteractor;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.customViews.CustomSpinner;
import com.saphamrah.customer.utils.customViews.CustomSpinnerResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;


public class ReturnFragment extends BaseFragment<ReturnedInteractor.PresenterOps, FragmentReturnBinding, CreateRequestActivity> implements ReturnedInteractor.RequiredViewOps {
    private ReturnedAdapter marjoeeAdapter;
    private boolean init;
    private SelectedReturnedAdapter selectedReturnedAdapter;
    BottomSheetBehavior bottomSheetBehavior;
    private CustomSpinner customSpinner = new CustomSpinner();
    List<String> marjoeeTitles;

    @Override
    protected void onBackPressed() {
      activity.paymentState = Constants.PaymentStates.CONFIRM_REQUEST;
    }

    @Override
    protected void initViews() {
        //marjoee
        marjoeeTitles = new ArrayList<>();
        marjoeeTitles.add("ضایعات");
        marjoeeTitles.add("تاریخ گذشته");
        marjoeeTitles.add("خرابی هنگام حمل");
        marjoeeTitles.add("عدم فروش");

        bottomSheetBehavior = BottomSheetBehavior.from(viewBinding.btmShtReturn.linBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        viewBinding.btmShtReturn.linConfirmMarjoee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setMarjoee = true;
                navigateUp();
            }
        });
    }

    private void setMarjoeeSpinner(List<String> marjoeeTitles) {


    }

    private void setSelectedMarjoeeRecycler(List<ElamMarjoeeForoshandehModel> marjoees) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
        selectedReturnedAdapter = new SelectedReturnedAdapter(context);
        viewBinding.btmShtReturn.RVSelectedMarjoee.setLayoutManager(linearLayoutManager);
        selectedReturnedAdapter.submitList(marjoees);
        viewBinding.btmShtReturn.RVSelectedMarjoee.setAdapter(selectedReturnedAdapter);
        ((SimpleItemAnimator) viewBinding.btmShtReturn.RVSelectedMarjoee.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void setMarjoeeRecycler(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);

        marjoeeAdapter = new ReturnedAdapter(context, (model, position, Action) -> {
             switch (Action){
                 case REMOVE:
                 case ADD:
                     List<ElamMarjoeeForoshandehModel> marjoees = Observable.fromIterable(elamMarjoeeForoshandehModels).filter(elamMarjoeeForoshandehModel -> elamMarjoeeForoshandehModel.getTedad3()>0).toList().blockingGet();
                     if (marjoees.size()> 0)
                         expand();
                     else
                         collapse();

                     selectedReturnedAdapter.submitList(marjoees);

                     break;
                 case SELECT:
                     customSpinner.showSpinnerSingleButton(activity, marjoeeTitles,false, new CustomSpinnerResponse() {
                         @Override
                         public void onSingleSelect(int selectedIndex)
                         {
                             model.setNameElatMarjoeeKala(marjoeeTitles.get(selectedIndex));
                             List<ElamMarjoeeForoshandehModel> marjoees = Observable.fromIterable(elamMarjoeeForoshandehModels).filter(elamMarjoeeForoshandehModel -> elamMarjoeeForoshandehModel.getTedad3()>0).toList().blockingGet();
                             selectedReturnedAdapter.submitList(marjoees);
                         }
                         @Override
                         public void onMultiSelect(ArrayList<Integer> selectedIndexes)
                         {

                         }
                     });
             }
        });
        marjoeeAdapter.submitList(elamMarjoeeForoshandehModels);
        viewBinding.RVOrderedProducts.setLayoutManager(linearLayoutManager);
        viewBinding.RVOrderedProducts.setAdapter(marjoeeAdapter);
        ((SimpleItemAnimator) viewBinding.RVOrderedProducts.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    @Override
    protected void setPresenter() {
        presenter = new ReturnedPresenter(this);
        init = true;
        presenter.getMarjoee();

    }

    @Override
    protected FragmentReturnBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentReturnBinding.inflate(inflater, container, false);
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
    public void onGetMarjoee(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {

        if (init) {
            List<ElamMarjoeeForoshandehModel> marjoees = Observable.fromIterable(elamMarjoeeForoshandehModels).filter(elamMarjoeeForoshandehModel -> elamMarjoeeForoshandehModel.getTedad3()>0).toList().blockingGet();
            setSelectedMarjoeeRecycler(marjoees);
            setMarjoeeRecycler(elamMarjoeeForoshandehModels);
            activity.setElamMarjoeeForoshandehModelsGlobal(elamMarjoeeForoshandehModels);
            init = false;
        }
        else
        {
            marjoeeAdapter.submitList(elamMarjoeeForoshandehModels);
        }
    }


    private void expand() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
    private void collapse() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
    }