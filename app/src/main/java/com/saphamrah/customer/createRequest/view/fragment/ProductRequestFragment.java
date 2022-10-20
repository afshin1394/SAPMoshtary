package com.saphamrah.customer.createRequest.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.createRequest.presenter.ProductRequestMVPPresenter;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.createRequest.view.adapter.FilterListAdapter;
import com.saphamrah.customer.createRequest.view.adapter.ProductAdapter;
import com.saphamrah.customer.databinding.FragmentProductRequestBinding;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.RxUtils.Watcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProductRequestFragment extends BaseFragment<ProductRequestMVPPresenter, FragmentProductRequestBinding> {

    public static final String TAG = ProductRequestFragment.class.getSimpleName();
    private FilterListAdapter filterAdapter;
    private ProductAdapter productAdapter;

    private ArrayList<FilterSortModel> filterSortModels;
    private ArrayList<ProductModel> productModels;
    private ArrayList<ProductModel> productModelsTemp;


    public ProductRequestFragment() {
        super(R.layout.fragment_product_request);
    }


    @Override
    protected void onBackPressed() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {

        Log.i(TAG, "onViewCreated: ");
        setFilterRecycler();
        setProductRecycler();
        setSearch();
        setViews();
        getFragmentResult("filters", o -> {
            Log.i(TAG, "initViews: " + o.toString());
            filterSortModels.clear();
            this.filterSortModels.addAll(((List<FilterSortModel>) Arrays.asList(o).get(0)).stream(). filter(filterSortModel -> filterSortModel.isEnabled() == true).collect(Collectors.toList()));
            Log.i(TAG, "initViews: " + filterSortModels.toString());
            filterAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void setPresenter()
    {

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
        viewBinding.linFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void setProductRecycler() {

        List<Integer> imageRes = new ArrayList<>();
        imageRes.add(R.drawable.advertising);
        imageRes.add(R.drawable.night);
        imageRes.add(R.drawable.midday);
        imageRes.add(R.drawable.sunrise);
        productModels = new ArrayList<>();
        productModelsTemp = new ArrayList<>();
        ProductModel kalaModel = new ProductModel(1, "بستنی دوقلو", 10000, 8000, "980702", 500, "1400/8/5", "1400/8/25", imageRes);
        ProductModel kalaModel2 = new ProductModel(2, "بستنی ماستی", 12000, 10000, "980702", 600, "1400/8/5", "1400/8/25", imageRes);
        ProductModel kalaModel3 = new ProductModel(3, "رب دلپذیر", 13000, 8000, "980702", 600, "1400/8/5", "1400/8/25", imageRes);
        ProductModel kalaModel4 = new ProductModel(4, "آب معدنی", 14000, 10000, "980702", 600, "1400/2/5", "1400/9/25", imageRes);
        ProductModel kalaModel5 = new ProductModel(5, "شربت سکنجبین", 16000, 7000, "980702", 600, "1400/1/5", "1400/9/25", imageRes);
        ProductModel kalaModel6 = new ProductModel(6, "آب انبه", 18000, 9000, "980702", 500, "1400/8/5", "1400/8/25", imageRes);
        ProductModel kalaModel7 = new ProductModel(7, "آب پرتقال", 19000, 7000, "980702", 600, "1400/8/5", "1400/8/25", imageRes);
        ProductModel kalaModel8 = new ProductModel(8, "آب سیب", 17000, 9000, "980702", 300, "1400/5/5", "1400/8/25", imageRes);
        ProductModel kalaModel9 = new ProductModel(9, "آب هلو", 22000, 9000, "980702", 300, "1400/7/5", "1400/8/25", imageRes);
        ProductModel kalaModel10 = new ProductModel(10, "آب انار", 10000, 9000, "980702", 300, "1400/8/1", "1400/8/15", imageRes);

        productModels.add(kalaModel);
        productModels.add(kalaModel2);
        productModels.add(kalaModel3);
        productModels.add(kalaModel4);
        productModels.add(kalaModel5);
        productModels.add(kalaModel6);
        productModels.add(kalaModel7);
        productModels.add(kalaModel8);
        productModels.add(kalaModel9);
        productModels.add(kalaModel10);

        productModelsTemp.addAll(productModels);
        productAdapter = new ProductAdapter(getActivity(), productModelsTemp, (model, position, Action) -> {
            switch (Action) {
                case SELECT:
                    ProductRequestFragmentDirections.ActionProductRequestFragmentToAddItemToCartFragment action = ProductRequestFragmentDirections.actionProductRequestFragmentToAddItemToCartFragment(model);
                    navigate(action);
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
}