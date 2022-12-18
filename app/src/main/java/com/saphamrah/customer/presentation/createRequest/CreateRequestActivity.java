package com.saphamrah.customer.presentation.createRequest;

import static com.saphamrah.customer.utils.Constants.BRAND;
import static com.saphamrah.customer.utils.Constants.CONSUMER_PRICE_TRACK;
import static com.saphamrah.customer.utils.Constants.GOROH_KALA;
import static com.saphamrah.customer.utils.Constants.MAX_CONSUMER_PRICE;
import static com.saphamrah.customer.utils.Constants.MAX_SELL_PRICE;
import static com.saphamrah.customer.utils.Constants.MIN_CONSUMER_PRICE;
import static com.saphamrah.customer.utils.Constants.MIN_SELL_PRICE;
import static com.saphamrah.customer.utils.Constants.SELL_PRICE_TRACK;
import static com.saphamrah.customer.utils.Constants.SORT;

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
import com.saphamrah.customer.data.local.temp.FilterCategoryModel;
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
    private List<FilterSortModel> filterSortModels;
    private String address;
    public ActivityCreateRequestBinding rootBinding;
    public Constants.PaymentStates paymentState;
    public String sazmanName;
    public String codeKala;
    public int ccSazmanForosh;
    public boolean setMarjoee = false;
    public boolean isAdvertiseObserved;
    CartListener cartListener;
    public static  int  sort_order = 0;

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
        ccSazmanForosh = ((int) getIntent().getExtras().get("ccSazmanForosh"));
        codeKala = (((String) getIntent().getExtras().get("advertiseProductCodeKala")));
        Log.i(TAG, "initViews: "+ccSazmanForosh);
        Log.i(TAG, "initViews: "+sazmanName);
        Log.i(TAG, "initViews: "+codeKala);

        paymentState = Constants.PaymentStates.SHOW_PRODUCTS;
        setProducts();
        setFilters();

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


        List<Integer> imageResTavakoli = new ArrayList<>();
        imageResTavakoli.add(R.drawable.tavakoli);
        imageResTavakoli.add(R.drawable.tavakoli_two);

        ProductModel kalaModel = new ProductModel(1,Constants.DELPAZIR,Constants.PASTE, "رب دلپذیر", 14500, 8000, "980702", 500, "1400/5/5","1400/8/5", imageResDelpazir,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel11 = new ProductModel(11,Constants.DELPAZIR,Constants.SAUCE ,"سس کچاپ دلپذیر - 709 گرم", 12100, 9200, "980703", 500, "1400/5/5","1400/8/5", imageResDelpazirKetchap,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel12 = new ProductModel(12,Constants.DELPAZIR,Constants.SAUCE, "سس هزار جزیره دلپذیر 454 گرم", 14100, 9010, "980704", 500, "1400/5/5","1400/8/5", imageResDelpazirHezarjazire,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel13 = new ProductModel(13,Constants.DELPAZIR,Constants.SAUCE, "سس مایونز کم چرب دلپذیر- 460 گرم", 12300, 4000, "980705", 500, "1400/5/5","1400/8/5", imageResDelpazirKamcharb,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel14 = new ProductModel(14,Constants.DELPAZIR,Constants.CANNED, "خیارشور ویژه دلپذیر - 650 گرم", 12400, 5100, "980706", 500, "1400/5/5","1400/8/5", imageResDelpazirKhiarShour,false,0,0,0,3,6,0,290,"دلپذیر",1);
        ProductModel kalaModel2 = new ProductModel(2,Constants.MIHAN,Constants.ICE_CREAM, "بستنی سالار", 12400, 10200, "980707", 600,"1400/5/5", "1400/8/5", imageResDelpazir, true,0,0,0,6,12,0,290,"میهن",2);
        ProductModel kalaModel3 = new ProductModel(3,Constants.MIHAN,Constants.ICE_CREAM, "بستنی زعفرانی", 11000, 8300, "980708", 600,"1400/5/5", "1400/8/5", imageResZaferani, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel15 = new ProductModel(15,Constants.MIHAN,Constants.ICE_CREAM, "بستنی چوبی پریما دبل میکس بری میهن - 60 گرم", 13000, 8560, "980709", 600,"1400/5/5", "1400/8/5", imageResBerry, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel16 = new ProductModel(16,Constants.MIHAN,Constants.ICE_CREAM, "بستنی چوبی وانیلا پریما میهن - 80 گرم", 13000, 8100, "980712", 600,"1400/5/5", "1400/8/5", imageResPrima, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel17 = new ProductModel(17,Constants.MIHAN,Constants.ICE_CREAM, "بستنی موزی میرکس میهن - 85 گرم", 13000, 8040, "980722", 600,"1400/5/5", "1400/8/5", imageResMiracs, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel18 = new ProductModel(18,Constants.MIHAN,Constants.ICE_CREAM, "بستنی قیفی سوپر کارنیلو میهن - 100 گرم", 13000, 5000, "980732", 600,"1400/5/5", "1400/8/5", imageResQifi, false,0,0,0,2,6,0,250,"میهن",2);
        ProductModel kalaModel4 = new ProductModel(4,Constants.MAHRAM,Constants.PASTE, "رب مهرام", 19000, 12000, "980742", 600, "1400/5/5","1400/2/5", imageResMahram, false,0,0,0,3,6,0,250,"مهرام",3);
        ProductModel kalaModel20 = new ProductModel(4,Constants.TAVAKOLLI,Constants.MATCH, "کبریت توکلی", 19000, 12000, "980742", 600, "1400/5/5","1400/2/5", imageResTavakoli, false,0,0,0,3,6,0,250,"توکلی",1);

        ProductModel kalaModel5 = new ProductModel(5,Constants.MIHAN,Constants.ICE_CREAM, "بستنی دبل چاکلت", 14000, 7000, "980752", 600,"1400/5/5", "1400/1/5", imageResDoubleChocolate, false,0,0,0,3,6,0,250,"میهن",2);
        ProductModel kalaModel6 = new ProductModel(6,Constants.DELPAZIR, Constants.SAUCE,"مایونز دلپذیر", 18300, 9000, "980762", 500,"1400/5/5", "1400/8/5", imageResMayonese, true,0,0,0,2,6,0,0,"دلپذیر",1);
//        ProductModel kalaModel7 = new ProductModel(7, "خیارشور دلپذیر", 19000, 7000, "980702", 600,"1400/5/5", "1400/8/5", imageResKhiarShoor,false,0,0,0,3,6,0,250,"دلپذیر",1);
        ProductModel kalaModel8 = new ProductModel(8,Constants.MIHAN,Constants.JUICE, "آب سیب", 11000, 9300, "980772", 300,"1400/5/5", "1400/5/5", imageResAppleJuice, false,0,0,0,3,6,0,0,"فروتلند",5);
        ProductModel kalaModel9 = new ProductModel(9, Constants.MIHAN,Constants.JUICE,"آب هلو", 21000, 9200, "980782", 300, "1400/5/5","1400/7/5", imageResPeachJuice, false,0,0,0,4,6,0,0,"فروتلند",5);
        ProductModel kalaModel10 = new ProductModel(10,Constants.MIHAN, Constants.JUICE,"آب انار", 12000, 9100, "980792", 300, "1400/5/5","1400/8/1", imageResPomegranate, false,0,0,0,2,6,0,0,"فروتلند",5);
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
        productModelGlobal.add(kalaModel20);
    }

    private void setFilters() {
        ArrayList<FilterCategoryModel> filterCategoryModels = new ArrayList<>();
        FilterCategoryModel filterCategory = new FilterCategoryModel(1, "برند", 1);

        FilterCategoryModel filterCategory2 = new FilterCategoryModel(2, "گروه کالا", 2);

        filterCategoryModels.add(filterCategory);
        filterCategoryModels.add(filterCategory2);

        filterSortModels = new ArrayList<>();
        FilterSortModel filterModelBrand = new FilterSortModel(100, 2, 3, "برند", 0, false,-1);
        FilterSortModel filterModelGorohKala = new FilterSortModel(200, 2, 3, "گروه کالا", 0, false,-1);

        FilterSortModel filterModel = new FilterSortModel(101, 2, 3, "دلپذیر", 100, false,BRAND);
        FilterSortModel filterModel2 = new FilterSortModel(106, 2, 3, "توکلی", 100, false,BRAND);
        FilterSortModel filterModel3 = new FilterSortModel(103, 2, 3, "لینا", 100, false,BRAND);
        FilterSortModel filterModel4 = new FilterSortModel(104, 2, 3, "کاله", 100, false,BRAND);

        FilterSortModel filterModel5 = new FilterSortModel(1, 2, 3, "رب", 200, false,GOROH_KALA);
        FilterSortModel filterModel6 = new FilterSortModel(3, 2, 3, "کنسرو", 200, false,GOROH_KALA);
        FilterSortModel filterModel7 = new FilterSortModel(2, 2, 3, "سس", 200, false,GOROH_KALA);
        FilterSortModel filterModel8 = new FilterSortModel(4, 2, 3, "بستنی", 200, false,GOROH_KALA);
        FilterSortModel filterModel9 = new FilterSortModel(5, 2, 3, "آبمیوه", 200, false,GOROH_KALA);
//        FilterSortModel filterModel8 = new FilterSortModel(8, 2, 3, "کبریت", 200, false);

        FilterSortModel filterModel10 = new FilterSortModel(CONSUMER_PRICE_TRACK, 2, 4, "بازه قیمت مصرف کننده", -1, false,CONSUMER_PRICE_TRACK);
//        FilterSortModel filterModel11 = new FilterSortModel(10, 2, 4, "موجودی", -1, false);
        FilterSortModel filterModel12 = new FilterSortModel(SELL_PRICE_TRACK, 2, 4, " بازه قیمت خرید", -1, false,SELL_PRICE_TRACK);

        FilterSortModel filterGheymatForosh = new FilterSortModel(MAX_SELL_PRICE, 1, -1, "بیشترین قیمت خرید", 0, false,SORT);
        FilterSortModel filterGheymatMasrafKonandeh = new FilterSortModel(MIN_SELL_PRICE, 1, -1, "کمترین قیمت خرید", 0, false,SORT);
        FilterSortModel filterTarikh = new FilterSortModel(MAX_CONSUMER_PRICE, 1, -1, "بیشترین قیمت مصرف کننده", 0, false,SORT);
        FilterSortModel filterEngheza = new FilterSortModel(MIN_CONSUMER_PRICE, 1, -1, "کمترین قیمت مصرف کننده", 0, false,SORT);
        filterSortModels.add(filterModelBrand);
        filterSortModels.add(filterModelGorohKala);
        filterSortModels.add(filterGheymatForosh);
        filterSortModels.add(filterGheymatMasrafKonandeh);
        filterSortModels.add(filterTarikh);
        filterSortModels.add(filterEngheza);
        filterSortModels.add(filterModel);
        filterSortModels.add(filterModel2);
//        filterSortModels.add(filterModel3);
//        filterSortModels.add(filterModel4);
        filterSortModels.add(filterModel5);
        filterSortModels.add(filterModel6);
        filterSortModels.add(filterModel7);
        filterSortModels.add(filterModel9);
        filterSortModels.add(filterModel10);
//        filterSortModels.add(filterModel11);
        filterSortModels.add(filterModel12);

    }

    public List<FilterSortModel> getFilterSortModels() {
        return filterSortModels;
    }

    public void disableFilters(){
        Observable.fromIterable(filterSortModels).filter(filterSortModel -> filterSortModel.getFilterSortType() != SORT).map(filterSortModel -> {
            filterSortModel.setEnabled(false);
            return filterSortModel;
        }).blockingSubscribe();
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

    public void updateSortChoice(FilterSortModel model) {
        filterSortModels.get(filterSortModels.indexOf(model)).setEnabled(model.isEnabled());
    }

    public void removeSortFilters() {
        Observable.fromIterable(filterSortModels).filter(filterSortModel -> {
            if (filterSortModel.getFilterSortType() == SORT) {
                filterSortModel.setEnabled(false);
            }
            return true;
        }).blockingSubscribe();
    }

    public interface CartListener {
        void onCartClick(List<ProductModel> productModels);
        void onCartEmpty();
    }
}