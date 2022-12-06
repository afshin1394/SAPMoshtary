package com.saphamrah.customer.presentation.createRequest.productRequest.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.databinding.FragmentDoubleActionDialogBinding;
import com.saphamrah.customer.databinding.FragmentProductRequestBinding;
import com.saphamrah.customer.presentation.createRequest.productRequest.interactor.ProductRequestMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.productRequest.presenter.ProductRequestMVPPresenter;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter.BonusProductAdapter;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter.DiscountProductAdapter;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter.FilterListAdapter;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter.ProductAdapter;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AnimationUtils;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.RxUtils.Watcher;
import com.saphamrah.customer.utils.customViews.InputFilterMinMax;
import com.saphamrah.customer.utils.customViews.OnSingleClickListener;
import com.saphamrah.customer.utils.dialogs.DoubleActionFragmentDialog;
import com.saphamrah.customer.utils.dialogs.IDoubleActionDialog;
import com.saphamrah.customer.utils.loadingUtils.ShimmerLoading;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProductRequestFragment extends BaseFragment<ProductRequestMVPPresenter, FragmentProductRequestBinding, CreateRequestActivity> implements ProductRequestMVPInteractor.RequiredViewOps, CreateRequestActivity.CartListener {

    public static final String TAG = ProductRequestFragment.class.getSimpleName();
    private FilterListAdapter filterAdapter;
    private ProductAdapter productAdapter;

    private List<FilterSortModel> filterSortModels = new ArrayList<>();
    private List<ProductModel> productModels;
    private List<ProductModel> productModelsTemp;
    private List<FilterSortModel> filterListObserver = new ArrayList<>();
    private BottomSheetBehavior bottomSheetBonusDiscount;
    private BottomSheetBehavior bottomSheetBoxPackNum;
    private ShimmerLoading shimmerLoading = new ShimmerLoading();


    @Override
    protected void onBackPressed() {
        if (bottomSheetBonusDiscount.getState() == BottomSheetBehavior.STATE_EXPANDED) {

            bottomSheetBonusDiscount.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (bottomSheetBoxPackNum.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBoxPackNum.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            FragmentTransaction wft = getChildFragmentManager().beginTransaction();
            new DoubleActionFragmentDialog(context.getString(R.string.exitOnCart), true, new IDoubleActionDialog() {
                @Override
                public void onConfirmClick() {
                    getActivity().finish();
                }

                @Override
                public void onCancelClick() {


                }
            }).show(wft, "exit");

        }

    }


    @Override
    protected void initViews() {
        Log.i(TAG, "initViews: ");
        presenter.getProducts();
    }


    @Override
    protected void setPresenter() {
        presenter = new ProductRequestMVPPresenter(this);
    }


    @Override
    protected FragmentProductRequestBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentProductRequestBinding.inflate(inflater, container, false);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setViews() {
        bottomSheetBonusDiscount = BottomSheetBehavior.from(viewBinding.btmShtJayezehTakhfifKala.linBottomSheet);
        bottomSheetBonusDiscount.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        bottomSheetBonusDiscount.setDraggable(false);

        bottomSheetBoxPackNum = BottomSheetBehavior.from(viewBinding.btmShtCartonBasteAdad.linBottomSheet);
        bottomSheetBoxPackNum.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        bottomSheetBoxPackNum.setDraggable(false);

        viewBinding.btmShtJayezehTakhfifKala.IVCollapse.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                handleBottomSheetBehaiviourDiscountBonus();
            }
        });
        viewBinding.txtProducts.setText(String.format("%1$s %2$s", context.getString(R.string.products), activity.sazmanName));

        viewBinding.linSort.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                viewBinding.mainView.setClickable(false);
                ProductRequestFragmentDirections.ActionProductRequestFragmentToFilterFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToFilterFragment();
                action.setFilterSortType(Constants.SORT);
                navigate(action);
            }
        });
        viewBinding.linFilter.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ProductRequestFragmentDirections.ActionProductRequestFragmentToFilterFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToFilterFragment();
                action.setFilterSortType(Constants.FILTER_LIST);
                navigate(action);
            }
        });
    }

    private void setSearch() {

        viewBinding.searchView.addTextWatcher(new Watcher() {
            @Override
            public void onTextChange(String s) {
                productModelsTemp.clear();
                Log.i("setSearch", "onNext: " + s);
                if (s.equals("")) {
                    productModelsTemp.addAll(productModels);
                    productAdapter.notifyDataSetChanged();
                } else {
                    Observable.fromIterable(productModels)
                            .subscribe(new Observer<ProductModel>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ProductModel productModel) {
                                    if (productModel.getNameProduct().contains(s)) {
                                        productModelsTemp.add(productModel);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    productAdapter.notifyDataSetChanged();
                                }
                            });
                }
            }
        }, 400);
    }


    private void setFilterRecycler() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        filterAdapter = new FilterListAdapter(getActivity(), filterListObserver, null);
        viewBinding.RecyclerFilterList.setLayoutManager(linearLayoutManager);
        viewBinding.RecyclerFilterList.setAdapter(filterAdapter);


        @SuppressLint("NotifyDataSetChanged")
        AdapterItemListener<FilterSortModel> listenerFilterSort = (model, position, Action) -> {
            if (Action == AdapterAction.REMOVE) {
                filterListObserver.remove(model);
                filterAdapter.notifyDataSetChanged();
            }
        };

        getFragmentResultObserver("filters").observe(getViewLifecycleOwner(), o -> {
            filterListObserver = new ArrayList<>((List<FilterSortModel>) o);
            filterAdapter.setDataChanged(filterListObserver, listenerFilterSort);
        });

    }

    private void setProductRecycler() {
        productAdapter = new ProductAdapter(getActivity(), productModelsTemp, (model, position, Action) -> {
            switch (Action) {
                case SELECT:
                    ProductRequestFragmentDirections.ActionProductRequestFragmentToAddItemToCartFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToAddItemToCartFragment(model);
                    navigate(action);
                    break;
                case DETAIL:
                    presenter.getJayezehTakhfifDetails();
                    break;

                case ADD:
                case REMOVE:
                    onClickListener = null;
                    openCartonBastehAdadBottomSheet(position);
                    handleBottomSheetBehaiviourBoxPackNum();
                    break;
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewBinding.RecyclerProduct.setLayoutManager(linearLayoutManager);
        viewBinding.RecyclerProduct.setAdapter(productAdapter);
        ((SimpleItemAnimator) viewBinding.RecyclerProduct.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    View.OnClickListener onClickListener;

    private void openCartonBastehAdadBottomSheet(int position) {

        viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setFilters(new InputFilter[]{new InputFilterMinMax(0, 10000)});
        viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setFilters(new InputFilter[]{new InputFilterMinMax(0, 10000)});
        viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setFilters(new InputFilter[]{new InputFilterMinMax(0, 10000)});


        if (bottomSheetBoxPackNum.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBoxPackNum.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setHint(getString(R.string.carton));
        viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setHint(getString(R.string.basteh));
        viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setHint(getString(R.string.adad));

        viewBinding.btmShtCartonBasteAdad.tvBrand.setText(productModelsTemp.get(position).getNameProduct());
        viewBinding.btmShtCartonBasteAdad.tvKalaCode.setText(productModelsTemp.get(position).getCodeKala());
        viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setText("");
        viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setText("");
        viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setText("");

        if (productModelsTemp.get(position).getBoxCount() > 0)
            viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setText(String.valueOf(productModelsTemp.get(position).getBoxCount()));
        if (productModelsTemp.get(position).getPackCount() > 0)
            viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setText(String.valueOf(productModelsTemp.get(position).getPackCount()));
        if (productModelsTemp.get(position).getNumCount() > 0)
            viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setText(String.valueOf(productModelsTemp.get(position).getNumCount()));
        if (productModelsTemp.get(position).getNumCount() == 0)
            viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setHint(R.string.adad);
        if (productModelsTemp.get(position).getPackCount() == 0)
            viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setHint(R.string.basteh);
        if (productModelsTemp.get(position).getBoxCount() == 0)
            viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setHint(R.string.carton);

        viewBinding.btmShtCartonBasteAdad.IVCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBottomSheetBehaiviourBoxPackNum();
            }
        });


        viewBinding.btmShtCartonBasteAdad.tvAddCarton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productModelsTemp.get(position).setBoxCount(productModelsTemp.get(position).getBoxCount() + 1);
                int orderCount = calculateOrderCount(productModelsTemp.get(position));
                productModelsTemp.get(position).setOrderCount(orderCount);
                viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setText(String.valueOf(productModelsTemp.get(position).getBoxCount()));
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);
            }
        });

        viewBinding.btmShtCartonBasteAdad.tvAddBasteh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clickables", "onClick: ");
                productModelsTemp.get(position).setPackCount(productModelsTemp.get(position).getPackCount() + 1);
                int orderCount = calculateOrderCount(productModelsTemp.get(position));
                productModelsTemp.get(position).setOrderCount(orderCount);
                viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setText(String.valueOf(productModelsTemp.get(position).getPackCount()));
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);
            }
        });


        viewBinding.btmShtCartonBasteAdad.tvAddAdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productModelsTemp.get(position).setNumCount(productModelsTemp.get(position).getNumCount() + 1);
                int orderCount = calculateOrderCount(productModelsTemp.get(position));
                productModelsTemp.get(position).setOrderCount(orderCount);
                viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setText(String.valueOf(productModelsTemp.get(position).getNumCount()));
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);
            }
        });

        viewBinding.btmShtCartonBasteAdad.tvRemoveCarton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productModelsTemp.get(position).getBoxCount() > 1) {

                    productModelsTemp.get(position).setBoxCount(productModelsTemp.get(position).getBoxCount() - 1);
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                    viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setText(String.valueOf(productModelsTemp.get(position).getBoxCount()));

                } else {
                    if (productModelsTemp.get(position).getBoxCount() == 1) {
                        productModelsTemp.get(position).setBoxCount(productModelsTemp.get(position).getBoxCount() - 1);
                        productModelsTemp.get(position).setOrderCount(calculateOrderCount(productModelsTemp.get(position)));

                    }
                    viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setText("");
                    viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.setHint(getString(R.string.carton));
                }
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);
            }
        });
        viewBinding.btmShtCartonBasteAdad.tvRemoveBasteh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productModelsTemp.get(position).getPackCount() > 1) {
                    productModelsTemp.get(position).setPackCount(productModelsTemp.get(position).getPackCount() - 1);
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                    viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setText(String.valueOf(productModelsTemp.get(position).getPackCount()));
                } else {
                    if (productModelsTemp.get(position).getPackCount() == 1) {
                        productModelsTemp.get(position).setPackCount(productModelsTemp.get(position).getPackCount() - 1);
                        productModelsTemp.get(position).setOrderCount(calculateOrderCount(productModelsTemp.get(position)));
                    }

                    viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setText("");
                    viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.setHint(getString(R.string.basteh));
                }
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);


            }
        });

        viewBinding.btmShtCartonBasteAdad.tvRemoveAdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productModelsTemp.get(position).getNumCount() > 1) {
                    productModelsTemp.get(position).setNumCount(productModelsTemp.get(position).getNumCount() - 1);
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                    viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setText(String.valueOf(productModelsTemp.get(position).getNumCount()));
                } else {
                    if (productModelsTemp.get(position).getNumCount() == 1) {
                        productModelsTemp.get(position).setNumCount(productModelsTemp.get(position).getNumCount() - 1);
                        productModelsTemp.get(position).setOrderCount(calculateOrderCount(productModelsTemp.get(position)));
                    }

                    viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setText("");
                    viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.setHint(getString(R.string.adad));
                }
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);
            }
        });


        viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.removeWatcher();
        viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.addTextWatcher(new Watcher() {
            @Override
            public void onTextChange(String s) {
                Log.i(TAG, "onTextChange: tvQuantityCarton" + s);
                try {
                    productModelsTemp.get(position).setBoxCount(Integer.parseInt(s));
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                    productModelsTemp.get(position).setBoxCount(Integer.parseInt(viewBinding.btmShtCartonBasteAdad.tvQuantityCarton.getText().toString()));
                } catch (Exception e) {
                    productModelsTemp.get(position).setBoxCount(0);
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                }
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);
            }
        }, 400);

        viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.removeWatcher();
        viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.addTextWatcher(new Watcher() {
            @Override
            public void onTextChange(String s) {
                Log.i(TAG, "onTextChange: tvQuantityBasteh" + s);
                try {
                    productModelsTemp.get(position).setPackCount(Integer.parseInt(s));
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                    productModelsTemp.get(position).setPackCount(Integer.parseInt(viewBinding.btmShtCartonBasteAdad.tvQuantityBasteh.getText().toString()));

                } catch (Exception e) {
                    productModelsTemp.get(position).setPackCount(0);
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                }
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);
            }
        }, 400);
        viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.removeWatcher();
        viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.addTextWatcher(new Watcher() {
            @Override
            public void onTextChange(String s) {
                Log.i(TAG, "onTextChange: tvQuantityAdad" + s);
                try {
                    productModelsTemp.get(position).setNumCount(Integer.parseInt(s));
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                    productModelsTemp.get(position).setNumCount(Integer.parseInt(viewBinding.btmShtCartonBasteAdad.tvQuantityAdad.getText().toString()));
                } catch (Exception e) {
                    productModelsTemp.get(position).setNumCount(0);
                    int orderCount = calculateOrderCount(productModelsTemp.get(position));
                    productModelsTemp.get(position).setOrderCount(orderCount);
                }
                productAdapter.notifyItemChanged(position);
                activity.checkCart(true);
            }
        }, 400);
    }

    private int calculateOrderCount(ProductModel productModel) {
        return productModel.getBoxCount() * productModel.getNumInBox() + productModel.getPackCount() * productModel.getNumInPack() + productModel.getNumCount();
    }


    @Override
    public void onError(String error) {

    }

    @Override
    public void showLoading(String message) {
        shimmerLoading.startLoading(new ShimmerFrameLayout[]{viewBinding.shimmerViewContainer, viewBinding.shimmerToolbar, viewBinding.shimmerSortOrder}, new View[]{viewBinding.linToolbar, viewBinding.linSortOrder});
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

    @Override
    public void onCartClick(List<ProductModel> productModels) {
        ProductModel[] array = new ProductModel[productModels.size()];
        for (int i = 0; i < productModels.size(); i++) array[i] = productModels.get(i);

        Log.i(TAG, "onCartClick: " + productModels.toString());
        ProductRequestFragmentDirections.ActionProductRequestFragmentToCartFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToCartFragment(array, null);
        navigate(action);
    }

    @Override
    public void onCartEmpty() {
        navigateUp();
    }

    @Override
    public void onGetDiscountAndBonuses(List<DiscountModel> discountModels, List<BonusModel> bonusModels) {
        setBonusRecycler(bonusModels);
        setDiscountRecycler(discountModels);
        handleBottomSheetBehaiviourDiscountBonus();
    }

    @Override
    public void onGetProducts() {
        activity.setCartListener(this);
        activity.checkCart(true);
        Log.i(TAG, "onViewCreated:");
        productModels = activity.getProductModelGlobal();
        productModelsTemp = new ArrayList<>();
        productModelsTemp.addAll(productModels);
        setFilterRecycler();
        setProductRecycler();
        setSearch();
        setViews();
    }


    private void handleBottomSheetBehaiviourBoxPackNum() {
        if (bottomSheetBonusDiscount.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBonusDiscount.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if (bottomSheetBoxPackNum.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            viewBinding.btmShtCartonBasteAdad.IVCollapse.setRotation(0);
            bottomSheetBoxPackNum.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBoxPackNum.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void handleBottomSheetBehaiviourDiscountBonus() {
        if (bottomSheetBoxPackNum.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBoxPackNum.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if (bottomSheetBonusDiscount.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBonusDiscount.setState(BottomSheetBehavior.STATE_EXPANDED);
            viewBinding.btmShtJayezehTakhfifKala.IVCollapse.setRotation(0);
        } else {
            bottomSheetBonusDiscount.setState(BottomSheetBehavior.STATE_COLLAPSED);
            viewBinding.btmShtJayezehTakhfifKala.IVCollapse.setRotation(180);
        }
    }

    private void setBonusRecycler(List<BonusModel> bonusModels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        BonusProductAdapter bonusAdapter = new BonusProductAdapter(context, bonusModels);
        viewBinding.btmShtJayezehTakhfifKala.RVBonus.setLayoutManager(linearLayoutManager);
        viewBinding.btmShtJayezehTakhfifKala.RVBonus.setAdapter(bonusAdapter);
    }

    private void setDiscountRecycler(List<DiscountModel> discountModels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        DiscountProductAdapter discountAdapter = new DiscountProductAdapter(context, discountModels);
        viewBinding.btmShtJayezehTakhfifKala.RVDiscount.setLayoutManager(linearLayoutManager);
        viewBinding.btmShtJayezehTakhfifKala.RVDiscount.setAdapter(discountAdapter);
    }


}