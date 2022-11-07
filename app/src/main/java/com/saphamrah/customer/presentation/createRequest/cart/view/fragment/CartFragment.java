package com.saphamrah.customer.presentation.createRequest.cart.view.fragment;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
import com.saphamrah.customer.presentation.createRequest.selectableBonus.view.adapter.SelectableBonusAdapter;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.view.fragment.SelectableBonusFragmentDirections;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AnimationUtils;
import com.saphamrah.customer.utils.CollectionUtils;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CartFragment extends BaseFragment<CartInteractor.PresenterOps, FragmentCartBinding> implements CartInteractor.RequiredViewOps, AdapterView.OnItemSelectedListener {

    private List<ProductModel> productModels;
    private CartProductAdapter cartProductAdapter;
    private BottomSheetBehavior sheetBehavior;
    private List<ReceiptModel> receiptModels;
    private SelectableBonusAdapter selectableBonusAdapter;
    private int check;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Log.i(TAG, "onItemSelected: item log");
        if (++check > 1) {
            Log.i(TAG, "onItemSelected: item log in");
            viewBinding.tvReceiptDuration.setText(String.format("%1$s: %2$s %3$s", context.getString(R.string.modatVosol), receiptModels.get(position).getDuration(), context.getString(R.string.day)));
            ((CreateRequestActivity) activity).paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
            checkState();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public CartFragment() {
        super(R.layout.fragment_cart);
    }

    @Override
    protected void onBackPressed() {
        ((CreateRequestActivity) activity).paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
    }

    private void setSelectableBonusRecycler() {
        List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelList = ((CreateRequestActivity) activity).getJayezehEntekhabiMojodiModels();
        if (jayezehEntekhabiMojodiModelList != null) {
            viewBinding.linSelectableBonus.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            selectableBonusAdapter = new SelectableBonusAdapter(context, jayezehEntekhabiMojodiModelList, new AdapterItemListener<JayezehEntekhabiMojodiModel>() {
                @Override
                public void onItemSelect(JayezehEntekhabiMojodiModel model, int position, AdapterAction Action) {
                    switch (Action) {

                        case ADD:
                        case REMOVE:
                            jayezehEntekhabiMojodiModelList.get(position).setSelectedCount(jayezehEntekhabiMojodiModelList.get(position).getSelectedCount() + 1);
                            selectableBonusAdapter.updateSelectableBonus(jayezehEntekhabiMojodiModelList);
                            break;
                    }
                }
            });
            viewBinding.RVSelectableBonus.setLayoutManager(linearLayoutManager);
            viewBinding.RVSelectableBonus.setAdapter(selectableBonusAdapter);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews()
    {
        check = 0;
        Log.i(TAG, "onFragmentResult: Cart");
        Object o = getFragmentResult("bonuses");

        if (o != null)
            ((CreateRequestActivity) activity).setJayezehEntekhabiMojodiModel(((List<JayezehEntekhabiMojodiModel>) Arrays.asList(o).get(0)).stream().collect(Collectors.toList()));

        productModels = CollectionUtils.convertArrayToList(CartFragmentArgs.fromBundle(getArguments()).getProducts());
        setProductRecycler();
        setReceiptList();
        setViews();
        checkState();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkState() {
        switch (((CreateRequestActivity) activity).paymentState) {

            case SHOW_PRODUCTS: {
                Log.i(TAG, "checkState: SHOW_PRODUCTS");
                /*state 1*/
                ((CreateRequestActivity) activity).paymentState = Constants.PaymentStates.CALCULATE_BONUS_DISCOUNT;
                ((CreateRequestActivity) activity).rootBinding.linCart.setVisibility(View.GONE);
                viewBinding.linBonus.setVisibility(View.GONE);
                viewBinding.linDiscount.setVisibility(View.GONE);
                viewBinding.linSelectableBonus.setVisibility(View.GONE);
                ((CreateRequestActivity) activity).clearJayezehTakhfif();
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.calculateBonusDiscount);
                break;
            }


            case CALCULATE_BONUS_DISCOUNT: {
                Log.i(TAG, "checkState: CALCULATE_BONUS_DISCOUNT");
                /*state2*/
                ((CreateRequestActivity) activity).paymentState = Constants.PaymentStates.SELECTABLE_BONUS;
                presenter.getDiscountAndBonuses();

                break;
            }

            case SELECTABLE_BONUS: {
                Log.i(TAG, "checkState: SELECTABLE_BONUS");
                /*state3*/
                CartFragmentDirections.ActionCartFragmentToSelectableBonusFragment action = CartFragmentDirections.actionCartFragmentToSelectableBonusFragment(new SelectableBonus[]{});
                navigate(action);

                break;
            }

            case CONFIRM_REQUEST: {
                Log.i(TAG, "checkState: CONFIRM_REQUEST");
                /*state4*/
                setSelectableBonusRecycler();
                ((CreateRequestActivity) activity).paymentState = Constants.PaymentStates.SAVE_REQUEST;
                presenter.getDiscountAndBonuses();
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.confirm);

                break;
            }
            /*state5*/
            case SAVE_REQUEST: {
                Log.i(TAG, "checkState: SAVE_REQUEST");
                List<ProductModel> productModels = ((CreateRequestActivity) activity).getProductModelGlobal();
                List<BonusModel> bonusModels = ((CreateRequestActivity) activity).getBonusModelsGlobal();
                List<DiscountModel> discountModels = ((CreateRequestActivity) activity).getDiscountModelsGlobal();
                presenter.saveData(productModels, bonusModels, discountModels);
                Toast.makeText(context, R.string.confirm, Toast.LENGTH_LONG).show();
                activity.finish();
                break;
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViews() {


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
            ((CreateRequestActivity) activity).paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
            AnimationUtils.scale(view);
            ((CreateRequestActivity) activity).removeCart();
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
        viewBinding.spinnerReceipt.setAdapter(adapter);
        viewBinding.spinnerReceipt.setOnItemSelectedListener(this);
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
                    ((CreateRequestActivity) activity).checkCart();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewBinding.RVOrderedProducts.setLayoutManager(linearLayoutManager);
        viewBinding.RVOrderedProducts.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        viewBinding.RVOrderedProducts.setAdapter(cartProductAdapter);
    }


    private void setBonusRecycler() {
        List<BonusModel> bonusModels = ((CreateRequestActivity) activity).getBonusModelsGlobal();
        Log.i(TAG, "setBonusRecycler: " + bonusModels);
        if (bonusModels != null) {
            viewBinding.linBonus.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            BonusAdapter bonusAdapter = new BonusAdapter(context, bonusModels);
            viewBinding.RVBonus.setLayoutManager(linearLayoutManager);
            viewBinding.RVBonus.setAdapter(bonusAdapter);
        }
    }

    private void setDiscountRecycler() {
        List<DiscountModel> discountModels = ((CreateRequestActivity) activity).getDiscountModelsGlobal();
        Log.i(TAG, "setDiscountRecycler: " + discountModels);
        if (discountModels != null) {
            viewBinding.linDiscount.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            DiscountAdapter discountAdapter = new DiscountAdapter(context, discountModels);
            viewBinding.RVDiscount.setLayoutManager(linearLayoutManager);
            viewBinding.RVDiscount.setAdapter(discountAdapter);
        }
    }

    @Override
    public void onGetDiscountAndBonuses(List<DiscountModel> discountModels, List<BonusModel> bonusModels) {

        viewBinding.btmShtPurchase.tvPayment.setText(R.string.selectableBonus);
        ((CreateRequestActivity) activity).setBonusModelsGlobal(bonusModels);
        ((CreateRequestActivity) activity).setDiscountModelsGlobal(discountModels);
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


}