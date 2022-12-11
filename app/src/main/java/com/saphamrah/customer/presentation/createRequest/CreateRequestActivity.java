package com.saphamrah.customer.presentation.createRequest;

import androidx.annotation.RequiresApi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.saphamrah.customer.data.local.temp.DarkhastFaktorJayezehTakhfifModel;
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
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class CreateRequestActivity extends BaseActivity<CreateRequestInteractor.PresenterOps, ActivityCreateRequestBinding>   {
    private String TAG = CreateRequestActivity.class.getSimpleName();
    private List<ProductModel> productModelGlobal;
    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = new ArrayList<>();
    private List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelsGlobal;
    private List<BonusModel> bonusModelsGlobal;
    private List<DiscountModel> discountModelsGlobal;
    private String address;
    public ActivityCreateRequestBinding rootBinding;
    public Constants.PaymentStates paymentState;
    public String sazmanName;
    public int ccSazmanForosh;
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
        //sazmanName
        sazmanName = (((String) getIntent().getExtras().get("sazmanName")));
        ccSazmanForosh = (((int) getIntent().getExtras().get("ccSazmanForosh")));
        Log.i(TAG, "initViews: "+ccSazmanForosh);
        Log.i(TAG, "initViews: "+sazmanName);

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
                List<ProductModel> orderedProducts =  productModelGlobal.stream().filter(productModel -> productModel.getPackCount()>0 || productModel.getNumCount()>0 || productModel.getBoxCount()>0).collect(Collectors.toList());
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

    public void checkCart(boolean showCartIcon) {

        boolean stuffInCart = Observable.fromIterable(productModelGlobal).any(productModel -> productModel.getBoxCount()>0 || productModel.getPackCount()>0 || productModel.getNumCount()>0).blockingGet();

        Log.i(TAG, "checkCart: "+stuffInCart);
        if (stuffInCart) {
            if (showCartIcon) {
                viewBinding.linCart.setVisibility(View.VISIBLE);
            } else {
                viewBinding.linCart.setVisibility(View.GONE);
            }
        }else{
            viewBinding.linCart.setVisibility(View.GONE);
        }
    }

    public void clearJayezehTakhfif(){
        if (jayezehEntekhabiMojodiModels!=null){
            jayezehEntekhabiMojodiModels.clear();

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

    public void removeCart() {
        clearJayezehTakhfif();
        clearElamMarjoee();
        Observable.fromIterable(productModelGlobal).forEach(productModel -> productModel.setBoxCount(0)).dispose();
        Observable.fromIterable(productModelGlobal).forEach(productModel -> productModel.setPackCount(0)).dispose();
        Observable.fromIterable(productModelGlobal).forEach(productModel -> productModel.setNumCount(0)).dispose();
        Observable.fromIterable(productModelGlobal).forEach(productModel -> productModel.setOrderCount(0)).dispose();
        Log.i(TAG, "removeCart: "+productModelGlobal.toString());
        checkCart(false);
        cartListener.onCartEmpty();
    }
    private void setProducts() {
        List<Integer> imageResDelpazir = new ArrayList<>();
        imageResDelpazir.add(R.drawable.rob_delpazir);
        imageResDelpazir.add(R.drawable.rob_delpazir_behind);

        List<Integer> imageResDelpazirKetchap = new ArrayList<>();
        imageResDelpazirKetchap.add(R.drawable.delpazir_ketchap);
        imageResDelpazirKetchap.add(R.drawable.dlpazir_ketchap_front);

        List<Integer> imageResDelpazirKamcharb = new ArrayList<>();
        imageResDelpazirKamcharb.add(R.drawable.delpazir_kamcharb);
        imageResDelpazirKamcharb.add(R.drawable.delpazir_kamcharb_back);

        List<Integer> imageResDelpazirHezarjazire = new ArrayList<>();
        imageResDelpazirHezarjazire.add(R.drawable.delpazir_hezarjazire);
        imageResDelpazirHezarjazire.add(R.drawable.delpazir_hezarjazire_back);

        List<Integer> imageResDelpazirKhiarShour = new ArrayList<>();
        imageResDelpazirKhiarShour.add(R.drawable.delpazir_cumber);
        imageResDelpazirKhiarShour.add(R.drawable.delpazir_cumber_back);

        List<Integer> imageResSalar = new ArrayList<>();
        imageResSalar.add(R.drawable.salar_one);
        imageResSalar.add(R.drawable.salar_two);
        imageResSalar.add(R.drawable.salar_four);

        List<Integer> imageResZaferani = new ArrayList<>();
        imageResZaferani.add(R.drawable.zaferani_one);
        imageResZaferani.add(R.drawable.zaferani_two);

        List<Integer> imageResPrima = new ArrayList<>();
        imageResPrima.add(R.drawable.mihan_prima);
        imageResPrima.add(R.drawable.mihan_prima_back);

        List<Integer> imageResBerry = new ArrayList<>();
        imageResBerry.add(R.drawable.mihan_berry);
        imageResBerry.add(R.drawable.mihan_berry_back);

        List<Integer> imageResQifi = new ArrayList<>();
        imageResQifi.add(R.drawable.mihan_qifi);
        imageResQifi.add(R.drawable.mihan_qifi_back);

        List<Integer> imageResMiracs = new ArrayList<>();
        imageResMiracs.add(R.drawable.mihan_miracs);
        imageResMiracs.add(R.drawable.mihan_miracs_back);


        List<Integer> imageResMahram = new ArrayList<>();
        imageResMahram.add(R.drawable.rob_mahram_one);
        imageResMahram.add(R.drawable.rob_mahram_three);

        List<Integer> imageResDoubleChocolate = new ArrayList<>();
        imageResDoubleChocolate.add(R.drawable.double_chocolate_one);
        imageResDoubleChocolate.add(R.drawable.double_chocolate_two);

        List<Integer> imageResMayonese = new ArrayList<>();
        imageResMayonese.add(R.drawable.mayonese_one);
        imageResMayonese.add(R.drawable.mayonese_two);
        imageResMayonese.add(R.drawable.product_mayonise);


        List<Integer> imageResAppleJuice = new ArrayList<>();
        imageResAppleJuice.add(R.drawable.apple_juice_one);
        imageResAppleJuice.add(R.drawable.apple_juice_two);


        List<Integer> imageResPeachJuice = new ArrayList<>();
        imageResPeachJuice.add(R.drawable.peache_juice_one);
        imageResPeachJuice.add(R.drawable.peach_juice_two);
        imageResPeachJuice.add(R.drawable.peach_juice_three);


        List<Integer> imageResPomegranate = new ArrayList<>();
        imageResPomegranate.add(R.drawable.pomegranate_one);
        imageResPomegranate.add(R.drawable.pomegranate_two);
        imageResPomegranate.add(R.drawable.peach_juice_three);

        ProductModel kalaModel = new ProductModel(1, "رب دلپذیر", 10000, 8000, "980702", 500, "1400/5/5","1400/8/5", imageResDelpazir,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel11 = new ProductModel(11, "سس کچاپ دلپذیر - 709 گرم", 12000, 9000, "980702", 500, "1400/5/5","1400/8/5", imageResDelpazirKetchap,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel12 = new ProductModel(12, "سس هزار جزیره دلپذیر 454 گرم", 12000, 9000, "980702", 500, "1400/5/5","1400/8/5", imageResDelpazirHezarjazire,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel13 = new ProductModel(13, "سس مایونز کم چرب دلپذیر- 460 گرم", 12000, 9000, "980702", 500, "1400/5/5","1400/8/5", imageResDelpazirKamcharb,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel14 = new ProductModel(14, "خیارشور ویژه دلپذیر - 650 گرم", 12000, 9000, "980702", 500, "1400/5/5","1400/8/5", imageResDelpazirKhiarShour,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel2 = new ProductModel(2, "بستنی سالار", 12000, 10000, "980702", 600,"1400/5/5", "1400/8/5", imageResDelpazir, true,0,0,0,6,12,0,290,"میهن",2);
        ProductModel kalaModel3 = new ProductModel(3, "بستنی زعفرانی", 13000, 8000, "980702", 600,"1400/5/5", "1400/8/5", imageResZaferani, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel15 = new ProductModel(15, "بستنی چوبی پریما دبل میکس بری میهن - 60 گرم", 13000, 8000, "980702", 600,"1400/5/5", "1400/8/5", imageResBerry, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel16 = new ProductModel(16, "بستنی چوبی وانیلا پریما میهن - 80 گرم", 13000, 8000, "980702", 600,"1400/5/5", "1400/8/5", imageResPrima, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel17 = new ProductModel(17, "بستنی موزی میرکس میهن - 85 گرم", 13000, 8000, "980702", 600,"1400/5/5", "1400/8/5", imageResMiracs, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel18 = new ProductModel(18, "بستنی قیفی سوپر کارنیلو میهن - 100 گرم", 13000, 8000, "980702", 600,"1400/5/5", "1400/8/5", imageResQifi, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel4 = new ProductModel(4, "رب مهرام", 14000, 10000, "980702", 600, "1400/5/5","1400/2/5", imageResMahram, false,0,0,0,3,6,0,250,"مهرام",3);
        ProductModel kalaModel5 = new ProductModel(5, "بستنی دبل چاکلت", 16000, 7000, "980702", 600,"1400/5/5", "1400/1/5", imageResDoubleChocolate, false,0,0,0,3,6,0,250,"میهن",2);
        ProductModel kalaModel6 = new ProductModel(6, "مایونز دلپذیر", 18000, 9000, "980702", 500,"1400/5/5", "1400/8/5", imageResMayonese, true,0,0,0,2,6,0,0,"دلپذیر",1);
//        ProductModel kalaModel7 = new ProductModel(7, "خیارشور دلپذیر", 19000, 7000, "980702", 600,"1400/5/5", "1400/8/5", imageResKhiarShoor,false,0,0,0,3,6,0,250,"دلپذیر",1);
        ProductModel kalaModel8 = new ProductModel(8, "آب سیب", 17000, 9000, "980702", 300,"1400/5/5", "1400/5/5", imageResAppleJuice, false,0,0,0,3,6,0,0,"فروتلند",5);
        ProductModel kalaModel9 = new ProductModel(9, "آب هلو", 22000, 9000, "980702", 300, "1400/5/5","1400/7/5", imageResPeachJuice, false,0,0,0,4,6,0,0,"فروتلند",5);
        ProductModel kalaModel10 = new ProductModel(10, "آب انار", 10000, 9000, "980702", 300, "1400/5/5","1400/8/1", imageResPomegranate, false,0,0,0,2,6,0,0,"فروتلند",5);
        productModelGlobal = new ArrayList<>();
        productModelGlobal.add(kalaModel);
        productModelGlobal.add(kalaModel2);
        productModelGlobal.add(kalaModel3);
        productModelGlobal.add(kalaModel4);
        productModelGlobal.add(kalaModel5);
        productModelGlobal.add(kalaModel6);
        productModelGlobal.add(kalaModel8);
        productModelGlobal.add(kalaModel9);
        productModelGlobal.add(kalaModel10);
        productModelGlobal.add(kalaModel11);
        productModelGlobal.add(kalaModel12);
        productModelGlobal.add(kalaModel13);
        productModelGlobal.add(kalaModel14);
        productModelGlobal.add(kalaModel15);
        productModelGlobal.add(kalaModel16);
        productModelGlobal.add(kalaModel17);
        productModelGlobal.add(kalaModel18);
    }

    public List<ProductModel> getProductModelGlobal() {
        return Observable.fromIterable(productModelGlobal).filter(new Predicate<ProductModel>() {
            @Override
            public boolean test(ProductModel productModel) throws Exception {
                return productModel.getCcSazmanForosh() == ccSazmanForosh;
            }
        }).toList().blockingGet();
    }

    public List<JayezehEntekhabiMojodiModel> getJayezehEntekhabiMojodiModels() {
        return jayezehEntekhabiMojodiModels;
    }

    public void setJayezehEntekhabiMojodiModels(List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels) {
        this.jayezehEntekhabiMojodiModels.addAll(jayezehEntekhabiMojodiModels);
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

    @SuppressLint("SuspiciousIndentation")
    public List<ElamMarjoeeForoshandehModel> getElamMarjoeeForoshandehModelsGlobal() {
        if (elamMarjoeeForoshandehModelsGlobal!=null)
            return Observable.fromIterable(elamMarjoeeForoshandehModelsGlobal).filter(elamMarjoeeForoshandehModel -> elamMarjoeeForoshandehModel.getTedad3()>0).toList().blockingGet();
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