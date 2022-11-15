package com.saphamrah.customer.presentation.createRequest.cart.view.fragment;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import com.saphamrah.customer.presentation.createRequest.addItemtoCart.view.fragment.AddItemToCartFragmentDirections;
import com.saphamrah.customer.presentation.createRequest.cart.interactor.CartInteractor;
import com.saphamrah.customer.presentation.createRequest.cart.presenter.CartPresenter;
import com.saphamrah.customer.presentation.createRequest.cart.view.adapter.CartProductAdapter;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.presentation.createRequest.cart.view.adapter.BonusAdapter;
import com.saphamrah.customer.presentation.createRequest.cart.view.adapter.DiscountAdapter;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.data.local.temp.ReceiptModel;
import com.saphamrah.customer.databinding.FragmentCartBinding;
import com.saphamrah.customer.presentation.createRequest.cart.view.adapter.MarjoeeCartAdapter;
import com.saphamrah.customer.presentation.createRequest.cart.view.adapter.SelectableBonusCartAdapter;
import com.saphamrah.customer.presentation.createRequest.filter.view.fragment.FilterFragmentDirections;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.view.adapter.SelectableBonusAdapter;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.view.fragment.SelectableBonusFragmentDirections;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AnimationUtils;
import com.saphamrah.customer.utils.CollectionUtils;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.RxUtils.Watcher;
import com.saphamrah.customer.utils.ScreenUtils;
import com.saphamrah.customer.utils.customViews.InputFilterMinMax;
import com.saphamrah.customer.utils.customViews.OnSingleClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CartFragment extends BaseFragment<CartInteractor.PresenterOps, FragmentCartBinding, CreateRequestActivity> implements CartInteractor.RequiredViewOps {

    private List<ProductModel> productModels;
    private CartProductAdapter cartProductAdapter;
    private MarjoeeCartAdapter marjoeeCartAdapter;
    private BottomSheetBehavior sheetBehavior;
    private List<ReceiptModel> receiptModels;
    private SelectableBonusCartAdapter selectableBonusCartAdapter;
    private BonusAdapter bonusAdapter;
    private DiscountAdapter discountAdapter;
    private int check;


    public CartFragment() {
        super(R.layout.fragment_cart);
    }

    @Override
    protected void onBackPressed() {
        activity.paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        check = 0;

        Log.i(TAG, "onFragmentResult: Cart" + check);
        Object o = getFragmentResult("bonuses");

        if (o != null)
            activity.setJayezehEntekhabiMojodiModel(((List<JayezehEntekhabiMojodiModel>) Arrays.asList(o).get(0)).stream().collect(Collectors.toList()));

        productModels = CollectionUtils.convertArrayToList(CartFragmentArgs.fromBundle(getArguments()).getProducts());
        setProductRecycler();
        checkState();
        setAddressList();
        setReceiptList();
        setMarjoeeList();
        setViews();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMarjoeeList() {
        if (activity.getElamMarjoeeForoshandehModelsGlobal() != null) {
            if (activity.getElamMarjoeeForoshandehModelsGlobal().size() > 0) {
                viewBinding.linReturn.setVisibility(View.VISIBLE);
                marjoeeCartAdapter = new MarjoeeCartAdapter(getActivity(), activity.getElamMarjoeeForoshandehModelsGlobal());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                viewBinding.RVReturn.setLayoutManager(linearLayoutManager);
                viewBinding.RVReturn.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                viewBinding.RVReturn.setAdapter(marjoeeCartAdapter);
            } else {
                viewBinding.linReturn.setVisibility(View.GONE);
            }
        } else {
            viewBinding.linReturn.setVisibility(View.GONE);
        }
    }

    private void setAddressList() {
        String[] address = context.getResources().getStringArray(R.array.addressArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.custom_spinner_itemview, context.getResources().getStringArray(R.array.addressArray));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.spinnerAddress.setAdapter(adapter);
        viewBinding.spinnerAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                activity.setAddress(address[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkState() {
        switch (activity.paymentState) {

            case SHOW_PRODUCTS: {
                Log.i(TAG, "checkState: SHOW_PRODUCTS");
                /*state 1*/
                activity.paymentState = Constants.PaymentStates.CALCULATE_BONUS_DISCOUNT;
                activity.rootBinding.linCart.setVisibility(View.GONE);
                viewBinding.linBonus.setVisibility(View.GONE);
                viewBinding.linDiscount.setVisibility(View.GONE);
                viewBinding.linSelectableBonus.setVisibility(View.GONE);
                activity.clearJayezehTakhfif();
                activity.clearElamMarjoee();
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.calculateBonusDiscount);
                break;
            }


            case CALCULATE_BONUS_DISCOUNT: {
                Log.i(TAG, "checkState: CALCULATE_BONUS_DISCOUNT");
                /*state2*/
                activity.paymentState = Constants.PaymentStates.SELECTABLE_BONUS;
                presenter.getDiscountAndBonuses();

                break;
            }

            case SELECTABLE_BONUS: {


                Log.i(TAG, "checkState: SELECTABLE_BONUS");
                /*state3*/
                if (!activity.setMarjoee) {
                    CartFragmentDirections.ActionCartFragmentToSelectableBonusFragment action = CartFragmentDirections.actionCartFragmentToSelectableBonusFragment(new SelectableBonus[]{});
                    navigate(action);
                }else{
                    activity.paymentState = Constants.PaymentStates.CALCULATE_BONUS_DISCOUNT;
                    activity.setMarjoee = false;
                }

                break;
            }

            case CONFIRM_REQUEST: {
                navigate(CartFragmentDirections.actionCartFragmentToVerifyRequestFragment());
                activity.paymentState = Constants.PaymentStates.SAVE_REQUEST;
//                Log.i(TAG, "checkState: CONFIRM_REQUEST");
//                /*state4*/
//                setSelectableBonusRecycler();
//                activity.paymentState = Constants.PaymentStates.SAVE_REQUEST;
//                presenter.getDiscountAndBonuses();
//                viewBinding.btmShtPurchase.tvPayment.setText(R.string.confirm);


                break;
            }
            /*state5*/
            case SAVE_REQUEST: {
                Log.i(TAG, "checkState: SAVE_REQUEST");
                List<ProductModel> productModels = activity.getProductModelGlobal();
                List<BonusModel> bonusModels = activity.getBonusModelsGlobal();
                List<DiscountModel> discountModels = activity.getDiscountModelsGlobal();
                presenter.saveData(productModels, bonusModels, discountModels);
                Toast.makeText(context, R.string.confirm, Toast.LENGTH_LONG).show();
                activity.finish();
                break;
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViews() {


        viewBinding.imgMarjoee.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                NavDirections action = CartFragmentDirections.actionCartFragmentToReturnFragment();
                navigate(action);
            }
        });

        if (viewBinding.etvReceiptDuration != null) {
            viewBinding.etvReceiptDuration.setFilters(new InputFilter[]{new InputFilterMinMax(0, 30)});
        }


        sheetBehavior = BottomSheetBehavior.from(viewBinding.btmShtPurchase.linBottomSheet);
        viewBinding.btmShtPurchase.arrowDownBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                viewBinding.btmShtPurchase.arrowDownBottomSheet.setRotation(slideOffset * 180);
            }
        });

        viewBinding.btmShtPurchase.linPurchase.setOnClickListener(v -> {
            checkState();
        });

        viewBinding.imgTrash.setOnClickListener(view -> {
            activity.paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
            AnimationUtils.scale(view);
            activity.removeCart();
        });

        viewBinding.linReturn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

            }
        });


    }

    private void setReceiptList() {

        receiptModels = new ArrayList<>();
        ReceiptModel receiptModel = new ReceiptModel(1, "نقد", 0);
        ReceiptModel receiptModel1 = new ReceiptModel(2, "رسید", 0);
        ReceiptModel receiptModel2 = new ReceiptModel(3, "چک", 30);
        receiptModels.add(receiptModel);
        receiptModels.add(receiptModel1);
        receiptModels.add(receiptModel2);
        List<String> receiptTitles = new ArrayList<>();
        for (ReceiptModel model : receiptModels) {
            receiptTitles.add(model.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.custom_spinner_itemview, receiptTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        viewBinding.spinnerReceipt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                check++;
                Log.i(TAG, "onItemSelected:counter " + check);
                Log.i(TAG, "onItemSelected: item log");
                if (check > 1) {
                    Log.i(TAG, "onItemSelected: item log in");
                    activity.paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
                    initViews();
                    if (position == 0) {
                        viewBinding.etvReceiptDuration.setEnabled(false);
                        viewBinding.etvReceiptDuration.setText(" 0روز ");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        viewBinding.spinnerReceipt.setAdapter(adapter);
//        viewBinding.etvReceiptDuration.addTextWatcher(s -> viewBinding.etvReceiptDuration.setText(s.concat(getString(R.string.day))),300);

    }

    @Override
    protected void setPresenter() {
        presenter = new CartPresenter(CartFragment.this);
    }

    @Override
    protected FragmentCartBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentCartBinding.inflate(inflater, container, false);
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
    private void setProductRecycler() {
        cartProductAdapter = new CartProductAdapter(getActivity(), productModels, (model, position, Action) -> {
            switch (Action) {
                case ADD:
                case REMOVE:
                    activity.paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
                    checkState();
                    activity.checkCart(false);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewBinding.RVOrderedProducts.setLayoutManager(linearLayoutManager);
        viewBinding.RVOrderedProducts.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        viewBinding.RVOrderedProducts.setAdapter(cartProductAdapter);
    }

    @Override
    public void onGetDiscountAndBonuses(List<DiscountModel> discountModels, List<BonusModel> bonusModels) {

        viewBinding.btmShtPurchase.tvPayment.setText(R.string.selectableBonus);
        activity.setBonusModelsGlobal(bonusModels);
        activity.setDiscountModelsGlobal(discountModels);
        viewBinding.linBonusDiscount.setVisibility(View.VISIBLE);
        setBonusRecycler();
        setDiscountRecycler();
        viewBinding.svDetails.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void run() {
                viewBinding.svDetails.scrollTo(0, (int) ScreenUtils.getViewLocationOnScreen(viewBinding.tvDiscountTitle)[1]);
            }
        });


    }


    private void setBonusRecycler() {
        Log.i(TAG, "setBonusRecycler: " + activity.getBonusModelsGlobal());
        if (activity.getBonusModelsGlobal() != null) {
            viewBinding.linBonus.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            bonusAdapter = new BonusAdapter(context, activity.getBonusModelsGlobal());
            viewBinding.RVBonus.setLayoutManager(linearLayoutManager);
            viewBinding.RVBonus.setAdapter(bonusAdapter);
            viewBinding.RVBonus.setNestedScrollingEnabled(false);
        }
    }

    private void setDiscountRecycler() {
        Log.i(TAG, "setDiscountRecycler: " + activity.getDiscountModelsGlobal());
        if (activity.getDiscountModelsGlobal() != null) {
            viewBinding.linDiscount.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            discountAdapter = new DiscountAdapter(context, activity.getDiscountModelsGlobal());
            viewBinding.RVDiscount.setLayoutManager(linearLayoutManager);
            viewBinding.RVDiscount.setAdapter(discountAdapter);
            viewBinding.RVDiscount.setNestedScrollingEnabled(false);
        }
    }

    private void setSelectableBonusRecycler() {
        if (activity.getJayezehEntekhabiMojodiModels() != null) {
            viewBinding.linSelectableBonus.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            selectableBonusCartAdapter = new SelectableBonusCartAdapter(context, activity.getJayezehEntekhabiMojodiModels(), new AdapterItemListener<JayezehEntekhabiMojodiModel>() {
                @Override
                public void onItemSelect(JayezehEntekhabiMojodiModel model, int position, AdapterAction Action) {
                    switch (Action) {

                        case ADD:
                        case REMOVE:
                            activity.getJayezehEntekhabiMojodiModels().get(position).setSelectedCount(activity.getJayezehEntekhabiMojodiModels().get(position).getSelectedCount() + 1);
                            break;
                    }
                }
            });
            viewBinding.RVSelectableBonus.setLayoutManager(linearLayoutManager);
            viewBinding.RVSelectableBonus.setAdapter(selectableBonusCartAdapter);
            viewBinding.RVSelectableBonus.setNestedScrollingEnabled(false);
        }
    }


}