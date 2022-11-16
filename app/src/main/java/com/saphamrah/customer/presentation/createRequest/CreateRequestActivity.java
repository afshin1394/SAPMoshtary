package com.saphamrah.customer.presentation.createRequest;

import androidx.annotation.RequiresApi;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseActivity;
import com.saphamrah.customer.base.BasePermissionModel;
import com.saphamrah.customer.data.local.db.SapDatabase;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.databinding.ActivityCreateRequestBinding;
import com.saphamrah.customer.presentation.createRequest.filter.view.fragment.FilterFragment;
import com.saphamrah.customer.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CreateRequestActivity extends BaseActivity<CreateRequestInteractor.PresenterOps, ActivityCreateRequestBinding>   {
    private String TAG = CreateRequestActivity.class.getSimpleName();
    private List<ProductModel> productModelGlobal;
    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelsGlobal;
    private List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelsGlobal;
    private List<BonusModel> bonusModelsGlobal;
    private List<DiscountModel> discountModelsGlobal;
    private String address;
    public ActivityCreateRequestBinding rootBinding;
    public Constants.PaymentStates paymentState;
    public boolean setMarjoee = false;
    CartListener cartListener;

    public void setCartListener(CartListener cartListener){
        this.cartListener = cartListener;
    }

    @Override
    protected void onKeyBoardVisibilityChange(boolean visible) {

    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
        setProducts();
        checkPermissions(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @Override
    protected ActivityCreateRequestBinding inflateBiding(LayoutInflater inflater) {
        rootBinding = ActivityCreateRequestBinding.inflate(inflater);
        return rootBinding;
    }

    @Override
    public void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {
        viewBinding.linCart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                SapDatabase.getDatabase(CreateRequestActivity.this).exportDatabase();
                List<ProductModel> orderedProducts =  productModelGlobal.stream().filter(productModel -> productModel.getOrderCount() > 0).collect(Collectors.toList());
                cartListener.onCartClick(orderedProducts);
            }
        });

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
        return CreateRequestActivity.this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkCart(boolean showCartIcon) {
        boolean stuffInCart = productModelGlobal.stream().anyMatch(new Predicate<ProductModel>() {
            @Override
            public boolean test(ProductModel productModel) {
                return productModel.getOrderCount() > 0;
            }
        });
        Log.i(TAG, "checkCart: "+stuffInCart);
        if (showCartIcon) {
            if (stuffInCart) {
                viewBinding.linCart.setVisibility(View.VISIBLE);
            } else {
                viewBinding.linCart.setVisibility(View.GONE);
                cartListener.onCartEmpty();
            }
        }
    }

    public void clearJayezehTakhfif(){
        if (jayezehEntekhabiMojodiModelsGlobal!=null){
            jayezehEntekhabiMojodiModelsGlobal.clear();
            jayezehEntekhabiMojodiModelsGlobal = null;
        }
        if (bonusModelsGlobal!=null){
            bonusModelsGlobal.clear();
            bonusModelsGlobal = null;
        }
        if (discountModelsGlobal!=null){
            discountModelsGlobal.clear();
            discountModelsGlobal =null;
        }
    }

    public void clearElamMarjoee(){
        if (elamMarjoeeForoshandehModelsGlobal!=null){
            elamMarjoeeForoshandehModelsGlobal.clear();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void removeCart() {
        clearJayezehTakhfif();
        clearElamMarjoee();
        productModelGlobal.forEach(productModel -> productModel.setOrderCount(0));
        viewBinding.linCart.setVisibility(View.GONE);
        cartListener.onCartEmpty();
    }
    private void setProducts() {
        List<Integer> imageRes = new ArrayList<>();
        imageRes.add(R.drawable.delpazir);
        imageRes.add(R.drawable.yogourt);
        imageRes.add(R.drawable.bastani);
        ProductModel kalaModel = new ProductModel(1, "بستنی دوقلو", 10000, 8000, "980702", 500, 0,"1400/8/5", "1400/8/25", imageRes,false,"پاندا",290,15);
        ProductModel kalaModel2 = new ProductModel(2, "بستنی ماستی", 12000, 10000, "980702", 600,0, "1400/8/5", "1400/8/25", imageRes,true,"پاندا",290,15);
        ProductModel kalaModel3 = new ProductModel(3, "رب دلپذیر", 13000, 8000, "980702", 600,0, "1400/8/5", "1400/8/25", imageRes,false,"میهن",290,15);
        ProductModel kalaModel4 = new ProductModel(4, "آب معدنی", 14000, 10000, "980702", 600, 0,"1400/2/5", "1400/9/25", imageRes,false,"پاندا",290,15);
        ProductModel kalaModel5 = new ProductModel(5, "شربت سکنجبین", 16000, 7000, "980702", 600,0, "1400/1/5", "1400/9/25", imageRes,false,"فرودلند",290,15);
        ProductModel kalaModel6 = new ProductModel(6, "آب انبه", 18000, 9000, "980702", 500,0, "1400/8/5", "1400/8/25", imageRes,true,"پاندا",290,15);
        ProductModel kalaModel7 = new ProductModel(7, "آب پرتقال", 19000, 7000, "980702", 600,0, "1400/8/5", "1400/8/25", imageRes,false,"لبنیات پاستوریزه",290,15);
        ProductModel kalaModel8 = new ProductModel(8, "آب سیب", 17000, 9000, "980702", 300,0, "1400/5/5", "1400/8/25", imageRes,false,"فروتلند",290,15);
        ProductModel kalaModel9 = new ProductModel(9, "آب هلو", 22000, 9000, "980702", 300, 0,"1400/7/5", "1400/8/25", imageRes,true,"پاندا",290,15);
        ProductModel kalaModel10 = new ProductModel(10, "آب انار", 10000, 9000, "980702", 300, 0,"1400/8/1", "1400/8/15", imageRes,false,"میهن",290,15);
        productModelGlobal = new ArrayList<>();
        productModelGlobal.add(kalaModel);
        productModelGlobal.add(kalaModel2);
        productModelGlobal.add(kalaModel3);
        productModelGlobal.add(kalaModel4);
        productModelGlobal.add(kalaModel5);
        productModelGlobal.add(kalaModel6);
        productModelGlobal.add(kalaModel7);
        productModelGlobal.add(kalaModel8);
        productModelGlobal.add(kalaModel9);
        productModelGlobal.add(kalaModel10);
    }

    public List<ProductModel> getProductModelGlobal() {
        return productModelGlobal;
    }

    public void  setJayezehEntekhabiMojodiModel(List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelsGlobal){
        this.jayezehEntekhabiMojodiModelsGlobal = jayezehEntekhabiMojodiModelsGlobal;
    }
    public List<JayezehEntekhabiMojodiModel> getJayezehEntekhabiMojodiModels(){
        return this.jayezehEntekhabiMojodiModelsGlobal;
    }

    public List<BonusModel> getBonusModelsGlobal() {
        return bonusModelsGlobal;
    }

    public void setBonusModelsGlobal(List<BonusModel> bonusModelsGlobal) {
        this.bonusModelsGlobal = bonusModelsGlobal;
    }

    public List<DiscountModel> getDiscountModelsGlobal() {
        return discountModelsGlobal;
    }

    public void setDiscountModelsGlobal(List<DiscountModel> discountModelsGlobal) {
        this.discountModelsGlobal = discountModelsGlobal;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<ElamMarjoeeForoshandehModel> getElamMarjoeeForoshandehModelsGlobal() {
        if (elamMarjoeeForoshandehModelsGlobal!=null)
        return elamMarjoeeForoshandehModelsGlobal.stream().filter(elamMarjoeeForoshandehModel -> elamMarjoeeForoshandehModel.getTedad3()>0).collect(Collectors.toList());
        else
            return null;
    }

    public void setElamMarjoeeForoshandehModelsGlobal(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelsGlobal) {
        this.elamMarjoeeForoshandehModelsGlobal = elamMarjoeeForoshandehModelsGlobal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public interface CartListener {
        void onCartClick(List<ProductModel> productModels);
        void onCartEmpty();
    }
}