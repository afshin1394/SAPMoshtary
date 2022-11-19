package com.saphamrah.customer.presentation.createRequest.productRequest.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.internal.LinkedHashTreeMap;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.presentation.createRequest.filter.view.fragment.FilterFragment;
import com.saphamrah.customer.presentation.createRequest.productRequest.presenter.ProductRequestMVPPresenter;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter.FilterListAdapter;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter.ProductAdapter;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.databinding.FragmentProductRequestBinding;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.RxUtils.Watcher;
import com.saphamrah.customer.utils.customViews.OnSingleClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProductRequestFragment extends BaseFragment<ProductRequestMVPPresenter, FragmentProductRequestBinding, CreateRequestActivity> implements CreateRequestActivity.CartListener {

    public static final String TAG = ProductRequestFragment.class.getSimpleName();
    private FilterListAdapter filterAdapter;
    private ProductAdapter productAdapter;

    private List<FilterSortModel> filterSortModels = new ArrayList<>();
    private List<ProductModel> productModels;
    private List<ProductModel> productModelsTemp;

    private List<FilterSortModel> filterListObserver = new ArrayList<>();

    public ProductRequestFragment() {
        super(R.layout.fragment_product_request);
    }


    @Override
    protected void onBackPressed() {
        getActivity().finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        Log.i(TAG, "initViews: ");


        activity.setCartListener(this);
        Log.i(TAG, "onViewCreated: ");
        productModels = activity.getProductModelGlobal();
        productModelsTemp = new ArrayList<>();
        productModelsTemp.addAll(productModels);
        setFilterRecycler();
        setProductRecycler();
        setSearch();
        setViews();
    }


    @Override
    protected void setPresenter() {

    }


    @Override
    protected FragmentProductRequestBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentProductRequestBinding.inflate(inflater, container, false);
    }


    private void setViews() {
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


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: ");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: " + filterSortModels);
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFilterRecycler() {

//        Object o = getFragmentResult("filters");
//        if (o != null)
//            filterSortModels =  ((List<FilterSortModel>) Arrays.asList(o).get(0)).stream().collect(Collectors.toList());


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
            filterListObserver = new ArrayList<>((Collection<? extends FilterSortModel>) o);
            filterAdapter.setDataChanged(filterListObserver, listenerFilterSort);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setProductRecycler() {


        productAdapter = new ProductAdapter(getActivity(), productModelsTemp, (model, position, Action) -> {
            switch (Action) {
                case SELECT:
                    ProductRequestFragmentDirections.ActionProductRequestFragmentToAddItemToCartFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToAddItemToCartFragment(model);
                    navigate(action);
                    break;

                case ADD:
                case REMOVE:
                    activity.getProductModelGlobal().get(position).setOrderCount(productModelsTemp.get(position).getOrderCount());
                    activity.checkCart(true);
                    break;
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewBinding.RecyclerProduct.setLayoutManager(linearLayoutManager);
        viewBinding.RecyclerProduct.setAdapter(productAdapter);


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


}