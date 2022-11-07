package com.saphamrah.customer.presentation.createRequest.productRequest.view.fragment;

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

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.presentation.createRequest.productRequest.presenter.ProductRequestMVPPresenter;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter.FilterListAdapter;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter.ProductAdapter;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.databinding.FragmentProductRequestBinding;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.RxUtils.Watcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProductRequestFragment extends BaseFragment<ProductRequestMVPPresenter, FragmentProductRequestBinding> implements CreateRequestActivity.CartListener {

    public static final String TAG = ProductRequestFragment.class.getSimpleName();
    private FilterListAdapter filterAdapter;
    private ProductAdapter productAdapter;

    private ArrayList<FilterSortModel> filterSortModels = new ArrayList<>();
    private List<ProductModel> productModels = new ArrayList<>();
    private ArrayList<ProductModel> productModelsTemp = new ArrayList<>();



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
        ((CreateRequestActivity) getActivity()).setCartListener(this);
        Log.i(TAG, "onViewCreated: ");
        setFilterRecycler();
        setProductRecycler();
        setSearch();
        setViews();


    }


    @Override
    protected void setPresenter()
    {
      productModels.clear();
      productModels.addAll(((CreateRequestActivity) getActivity()).getProductModelGlobal());
    }

    @Override
    protected FragmentProductRequestBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentProductRequestBinding.inflate(inflater, container, false);
    }


    private void setViews() {
        viewBinding.linSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductRequestFragmentDirections.ActionProductRequestFragmentToFilterFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToFilterFragment();
                action.setFilterSortType(Constants.SORT);
                navigate(action);
            }
        });
        viewBinding.linFilter.setClickable(true);
        viewBinding.linFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ProductRequestFragmentDirections.ActionProductRequestFragmentToFilterFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToFilterFragment();
                action.setFilterSortType(Constants.FILTER_LIST);
                navigate(action);
                viewBinding.linFilter.setClickable(false);
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

        filterSortModels = new ArrayList<>();

        FilterSortModel filterModelBrand = new FilterSortModel(100, 1, "برند", 0, false);
        FilterSortModel filterModelGorohKala = new FilterSortModel(200, 1, "گروه کالا", 0, false);
        FilterSortModel filterModel = new FilterSortModel(1, 1, "دلپذیر", 100, false);
        FilterSortModel filterModel2 = new FilterSortModel(2, 1, "پاکان", 100, false);
        FilterSortModel filterModel3 = new FilterSortModel(3, 1, "توکلی", 100, false);
        FilterSortModel filterModel4 = new FilterSortModel(4, 1, "فروتلند", 100, false);
        FilterSortModel filterModel5 = new FilterSortModel(5, 1, "سس", 200, false);
        FilterSortModel filterModel6 = new FilterSortModel(6, 1, "پنیر", 200, false);
        FilterSortModel filterModel7 = new FilterSortModel(7, 1, "دستمال کاغذی", 200, false);
        FilterSortModel filterModel8 = new FilterSortModel(8, 1, "کبریت", 200, false);
        FilterSortModel filterModel9 = new FilterSortModel(9, 2, "قیمت مصرف کننده", -1, false);
        FilterSortModel filterModel10 = new FilterSortModel(10, 2, "موجودی", -1, false);
        FilterSortModel filterModel11 = new FilterSortModel(11, 2, "قیمت فروش", -1, false);
        filterSortModels.add(filterModelBrand);
        filterSortModels.add(filterModelGorohKala);
        filterSortModels.add(filterModel);
        filterSortModels.add(filterModel2);
        filterSortModels.add(filterModel3);
        filterSortModels.add(filterModel4);
        filterSortModels.add(filterModel5);
        filterSortModels.add(filterModel6);
        filterSortModels.add(filterModel7);
        filterSortModels.add(filterModel8);
        filterSortModels.add(filterModel9);
        filterSortModels.add(filterModel10);
        filterSortModels.add(filterModel11);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        filterAdapter = new FilterListAdapter(getActivity(), filterSortModels, (model, position, Action) -> {
            if (Action == AdapterAction.REMOVE) {
                filterSortModels.remove(model);
                filterAdapter.notifyDataSetChanged();
            }
        });
        viewBinding.RecyclerFilterList.setLayoutManager(linearLayoutManager);
        viewBinding.RecyclerFilterList.setAdapter(filterAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setProductRecycler() {


        productModelsTemp.clear();
        productModelsTemp.addAll(((CreateRequestActivity) getActivity()).getProductModelGlobal());


        productModelsTemp.addAll(((CreateRequestActivity) getActivity()).getProductModelGlobal());
        productAdapter = new ProductAdapter(getActivity(), productModelsTemp, (model, position, Action) -> {
            switch (Action)
            {
                case SELECT:
                    ProductRequestFragmentDirections.ActionProductRequestFragmentToAddItemToCartFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToAddItemToCartFragment(model);
                    navigate(action);
                    break;

                case ADD:
                case REMOVE:
                    ((CreateRequestActivity) getActivity()).checkCart();
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

        Log.i(TAG, "onCartClick: "+productModels.toString());
        ProductRequestFragmentDirections.ActionProductRequestFragmentToCartFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToCartFragment(array,null);
        navigate(action);
    }

    @Override
    public void onCartEmpty() {
        navigateUp();
    }
}