package com.saphamrah.customer.presentation.createRequest.cart.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
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

import com.facebook.shimmer.ShimmerFrameLayout;
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
import com.saphamrah.customer.utils.loadingUtils.ShimmerLoading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;


public class CartFragment extends BaseFragment<CartInteractor.PresenterOps, FragmentCartBinding, CreateRequestActivity> implements CartInteractor.RequiredViewOps, CreateRequestActivity.CartListener {

    private List<ProductModel> productModels;
    private CartProductAdapter cartProductAdapter;
    private MarjoeeCartAdapter marjoeeCartAdapter;
    private BottomSheetBehavior sheetBehavior;
    private List<ReceiptModel> receiptModels;
    private SelectableBonusCartAdapter selectableBonusCartAdapter;
    private BonusAdapter bonusAdapter;
    private DiscountAdapter discountAdapter;
    private int check;
    private ShimmerLoading shimmerLoading = new ShimmerLoading();




    @Override
    protected void onBackPressed() {
        activity.paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
        navigateUp();
    }


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void initViews() {
        check = 0;
        activity.rootBinding.linCart.setVisibility(View.GONE);
        productModels = Observable.fromIterable(activity.getProductModelGlobal()).filter(productModel -> productModel.getOrderCount() > 0).toList().blockingGet();
        setProductRecycler();
        if (!activity.setMarjoee)
            checkState();


        if (activity.getDiscountModelsGlobal() != null && activity.getBonusModelsGlobal() != null)
            presenter.getDiscountAndBonuses();
        if (activity.getJayezehEntekhabiMojodiModels().size() > 0)
            setSelectableBonusRecycler();


        setBottomSheetOnState();
        setAddressList();
        setReceiptList();
        setMarjoeeList();
        setViews();
        activity.setMarjoee = false;
        Log.i(TAG, "initViews: " + activity.paymentState);


    }

    private void setBottomSheetOnState() {
        switch (activity.paymentState) {
            case SAVE_REQUEST:
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.confirm);
                break;
            case CONFIRM_REQUEST:
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.taeedDarkhast);
                break;

            case SELECTABLE_BONUS:
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.selectableBonus);
                break;
            case CALCULATE_BONUS_DISCOUNT:
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.calculateBonusDiscount);
                break;
            case SHOW_PRODUCTS:
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.calculateBonusDiscount);
                break;
            case PISH_FAKTOR:
                viewBinding.btmShtPurchase.tvPayment.setText(R.string.pishFaktor);
                break;
        }
    }

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
        viewBinding.svDetails.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void run() {
                viewBinding.svDetails.scrollTo(0, (int) ScreenUtils.getViewLocationOnScreen(viewBinding.linReturn)[1]);
            }
        });
    }

    private void setAddressList() {
        String[] address = context.getResources().getStringArray(R.array.addressArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.custom_spinner_title, address);
        adapter.setDropDownViewResource(R.layout.custom_spinner_itemview);
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

    private void checkState() {
        switch (activity.paymentState) {

            case SHOW_PRODUCTS: {

                Log.i(TAG, "checkState: SHOW_PRODUCTS");
                /*state 1*/
                if (!addRemoveProduct) {
                    showLoading("");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activity.paymentState = Constants.PaymentStates.CALCULATE_BONUS_DISCOUNT;
                            activity.clearJayezehTakhfif();
                            activity.clearElamMarjoee();
                            dismissLoading();

                            addRemoveProduct = false;
                        }
                    }, 1000);
                }else{
                    activity.paymentState = Constants.PaymentStates.CALCULATE_BONUS_DISCOUNT;
                    viewBinding.linBonus.setVisibility(View.GONE);
                    viewBinding.linDiscount.setVisibility(View.GONE);
                    viewBinding.linSelectableBonus.setVisibility(View.GONE);
                    activity.clearJayezehTakhfif();
                    activity.clearElamMarjoee();
                }


                break;
            }


            case CALCULATE_BONUS_DISCOUNT: {
                /*state2*/
                Log.i(TAG, "checkState: CALCULATE_BONUS_DISCOUNT");
                showLoading("");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.paymentState = Constants.PaymentStates.SELECTABLE_BONUS;
                        presenter.getDiscountAndBonuses();

                    }
                }, 1000);


                break;
            }

            case SELECTABLE_BONUS: {
                Log.i(TAG, "checkState: SELECTABLE_BONUS");
                /*state3*/

                check = 0;
                CartFragmentDirections.ActionCartFragmentToSelectableBonusFragment action = CartFragmentDirections.actionCartFragmentToSelectableBonusFragment(new SelectableBonus[]{});
                navigate(action);

                break;
            }

            case CONFIRM_REQUEST: {

                Log.i(TAG, "checkState: CONFIRM_REQUEST");
                /*state4*/
                showLoading("");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setSelectableBonusRecycler();
                        activity.paymentState = Constants.PaymentStates.PISH_FAKTOR;
                        dismissLoading();
                    }
                }, 1000);


                break;
            }

            case PISH_FAKTOR: {
                /*state5*/
                Log.i(TAG, "checkState: PISH FAKTOR");
                check = 0;
                navigate(CartFragmentDirections.actionCartFragmentToVerifyRequestFragment());
                activity.paymentState = Constants.PaymentStates.PISH_FAKTOR;


                break;
            }
        }
    }


    private void setViews() {


        viewBinding.imgMarjoee.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                NavDirections action = CartFragmentDirections.actionCartFragmentToReturnFragment();
                navigate(action);
            }
        });
//
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
            Log.i(TAG, "initViews: " + activity.paymentState);
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
                R.layout.custom_spinner_title, receiptTitles);
        adapter.setDropDownViewResource(R.layout.custom_spinner_itemview);
        viewBinding.spinnerReceipt.post(() -> viewBinding.spinnerReceipt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                check++;
                if (check > 1) {
                    Log.i(TAG, "onItemSelected: in check");
                    activity.paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
                    checkState();
                    setBottomSheetOnState();
                    if (position == 0) {
                        viewBinding.etvReceiptDuration.setHint(" 0 ");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }));


        viewBinding.spinnerReceipt.setAdapter(adapter);
        viewBinding.etvReceiptDuration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused)
                    viewBinding.etvReceiptDuration.setHint("");
                else
                    viewBinding.etvReceiptDuration.setHint("تعداد روز مجاز وصول");
            }
        });
        viewBinding.etvReceiptDuration.removeWatcher();
        viewBinding.etvReceiptDuration.addTextWatcher(new Watcher() {
            @Override
            public void onTextChange(String s) {
                Log.i(TAG, "onTextChange: " + s);

                viewBinding.etvReceiptDuration.setHint(s.concat(context.getString(R.string.day)));

            }
        }, 100);

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
        shimmerLoading.startLoading(new ShimmerFrameLayout[]{viewBinding.shimmerAddress, viewBinding.shimmerVosol, viewBinding.shimmerToolbar, viewBinding.shimmerCartProductList, viewBinding.btmShtPurchase.shimmerPurchase}
                , new View[]{viewBinding.linAddress, viewBinding.linVosol, viewBinding.linToolbar, viewBinding.RVOrderedProducts, viewBinding.btmShtPurchase.linPurchase});
    }

    @Override
    public void dismissLoading() {
        shimmerLoading.stopLoading();
    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public Context getAppContext() {
        return context;
    }

    private boolean addRemoveProduct = false;

    private void setProductRecycler() {

        cartProductAdapter = new CartProductAdapter(getActivity(), productModels, (model, position, Action) -> {
            switch (Action) {
                case ADD:
                case REMOVE:
                    addRemoveProduct = true;
                    if (model.getOrderCount() == 0) {
                        productModels.remove(model);
                        cartProductAdapter.notifyDataSetChanged();
                    }
                    calculateNumPackBoxCount(model);
                    Log.i("calcBASTEADAD", "setProductRecycler: boxCount"+model.getBoxCount() + "packCount "+model.getPackCount() + "numCount "+model.getNumCount());
                    activity.paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
                    checkState();
                    setBottomSheetOnState();
                    activity.checkCart(false);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewBinding.RVOrderedProducts.setLayoutManager(linearLayoutManager);
        viewBinding.RVOrderedProducts.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        viewBinding.RVOrderedProducts.setAdapter(cartProductAdapter);

    }

    private void calculateNumPackBoxCount(ProductModel model) {
        int boxCount = model.getOrderCount() / model.getNumInBox();
        int packCount = (model.getOrderCount() - boxCount * model.getNumInBox()) / model.getNumInPack();
        int remainingCount = model.getOrderCount() - (packCount * model.getNumInPack() + boxCount * model.getNumInBox());
        Log.i(TAG, "calculateNumPackBoxCount: boxCount: " + boxCount + "packCount: " + packCount + "count: " + remainingCount);
        Log.i(TAG, "calculateNumPackBoxCount: num in box " + model.getNumInBox() + "num in pack:" + model.getPackCount());
        model.setBoxCount(boxCount);
        model.setPackCount(packCount);
        model.setNumCount(remainingCount);
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
                viewBinding.svDetails.smoothScrollTo(0,(int) viewBinding.tvDiscountTitle.getY());
//                viewBinding.svDetails.scrollTo(0, (int) ScreenUtils.getViewLocationOnScreen(viewBinding.tvDiscountTitle)[1]);
            }
        });
        dismissLoading();

    }

    @Override
    public void onGetCart() {

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
            viewBinding.svDetails.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void run() {
                    viewBinding.svDetails.scrollTo(0, (int) ScreenUtils.getViewLocationOnScreen(viewBinding.linSelectableBonus)[1]);
                }
            });
        }
    }


    @Override
    public void onCartClick(List<ProductModel> productModels) {

    }

    @Override
    public void onCartEmpty() {
        Log.i(TAG, "onCartEmpty: ");
        navigateUp();
    }
}