package com.saphamrah.customer.presentation.createRequest.addItemtoCart.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.presentation.createRequest.addItemtoCart.interactor.AddItemToCartInteractor;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.presentation.createRequest.addItemtoCart.view.adapter.SliderAdapterAddToCart;
import com.saphamrah.customer.databinding.FragmentAddItemToCartBinding;
import com.saphamrah.customer.presentation.createRequest.productRequest.view.fragment.ProductRequestFragmentDirections;
import com.saphamrah.customer.utils.AnimationUtils;
import com.saphamrah.customer.utils.CollectionUtils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.animation.type.ScaleAnimation;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;
import java.util.Map;


public class AddItemToCartFragment extends BaseFragment<AddItemToCartInteractor.PresenterOps, FragmentAddItemToCartBinding, CreateRequestActivity> implements CreateRequestActivity.CartListener {
    int indexOfProduct;
    private Context context;
    private BottomSheetBehavior bottomSheetBehavior;

    public AddItemToCartFragment() {
        super(R.layout.fragment_add_item_to_cart);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    protected void onBackPressed() {

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        activity.setCartListener(this);
        ProductModel productModel = AddItemToCartFragmentArgs.fromBundle(getArguments()).getProduct();
        indexOfProduct = activity.getProductModelGlobal().indexOf(productModel);
        setImageSlider(productModel);
        setViews(productModel);
    }

    @Override
    protected void setPresenter() {
    }

    @Override
    protected FragmentAddItemToCartBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAddItemToCartBinding.inflate(inflater, container, false);
    }


    private void setImageSlider(ProductModel productModel) {
        SliderAdapterAddToCart adapter = new SliderAdapterAddToCart(getActivity(), productModel);
        viewBinding.imageSlider.setSliderAdapter(adapter);
        viewBinding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        viewBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        viewBinding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        viewBinding.imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        viewBinding.imageSlider.startAutoCycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViews(ProductModel productModel) {

//        bottomSheetBehavior = BottomSheetBehavior.from(viewBinding.btmShtPurchase.linBottomSheet);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        final float[] oldOffset = {0f};
//        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            float oldOffSet = 0f;
//
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                Log.i(TAG, "onSlide: " + slideOffset);
//
//                boolean inRangeExpanding = oldOffset[0] < slideOffset;
//                boolean inRangeCollapsing = oldOffset[0] > slideOffset;
//                oldOffset[0] = slideOffset;
//                if (inRangeExpanding) {
////                    viewBinding.imageSlider.animate().scaleY(0.7f).translationY(-100f).setDuration(400);
//
//                    Log.i(TAG, "onSlide: EXPAND");
//                }
//                if (inRangeCollapsing) {
//
////                    viewBinding.imageSlider.animate().scaleY(1.2f).translationY(0).setDuration(400);
//                    Log.i(TAG, "onSlide: COLLAPSE");
//                }
//
////                if (inRangeCollapsing){
////                    AnimationUtils.expand(viewBinding.imageSlider);
////                    AnimationUtils.collapse(viewBinding.btmShtPurchase.linDetailsView);
////                    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
////                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
////                }else if (inRangeExpanding){
////
////                    AnimationUtils.collapse(viewBinding.imageSlider);
////                    AnimationUtils.expand(viewBinding.btmShtPurchase.linDetailsView);
////                    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
////                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
////                }
//            }
//        });


        if (productModel.getOrderCount() > 0) {
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.VISIBLE);
            viewBinding.btmShtPurchase.TVCount.setText(String.valueOf(productModel.getOrderCount()));
        } else {
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.cvTxtConsumerPrice.setVisibility(View.INVISIBLE);

        }

        viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("%1$s %2$s :هزینه پرداختی", "45500", getString(R.string.rial)));
        viewBinding.btmShtPurchase.txtProductName.setText(productModel.getNameProduct());
        viewBinding.btmShtPurchase.bachNumberTv.setText(productModel.getBachNumber());
        viewBinding.btmShtPurchase.brandTv.setText(productModel.getSazmanForosh());
        viewBinding.btmShtPurchase.expirationDateTv.setText(productModel.getExpirationDate());
        viewBinding.btmShtPurchase.productionDateTv.setText(productModel.getProductionDate());
        viewBinding.btmShtPurchase.numberInBoxTv.setText(" عدد " + productModel.getNumInBox());
        viewBinding.btmShtPurchase.weightTv.setText("گرم" + productModel.getWeight());


        viewBinding.btmShtPurchase.linPurchase.setOnClickListener(view -> {
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.VISIBLE);
            viewBinding.btmShtPurchase.cvTxtConsumerPrice.setVisibility(View.VISIBLE);
            viewBinding.btmShtPurchase.TVCount.setText("1");
            productModel.setOrderCount(1);
            activity.checkCart(true);
        });

        viewBinding.btmShtPurchase.addToCart.setOnClickListener(view -> {
            productModel.setOrderCount(productModel.getOrderCount()+1);
            viewBinding.btmShtPurchase.TVCount.setText(String.valueOf(Integer.parseInt(String.valueOf(viewBinding.btmShtPurchase.TVCount.getText())) + 1));
                     });
                viewBinding.btmShtPurchase.removeFromCart.setOnClickListener(view2 -> {


                    if (productModel.getOrderCount() > 0) {
                        productModel.setOrderCount(productModel.getOrderCount() - 1);
                        activity.checkCart(true);
                        try {
                            viewBinding.btmShtPurchase.TVCount.setText(String.valueOf(productModel.getOrderCount()));
                        } catch (Exception e) {

                        }
                        if (productModel.getOrderCount() == 0){
                            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
                            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
                            viewBinding.btmShtPurchase.cvTxtConsumerPrice.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
                        viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
                    }

                });


        viewBinding.btmShtPurchase.trashIv.setOnClickListener(view -> {
            productModel.setOrderCount(0);
            activity.checkCart(true);
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
            viewBinding.btmShtPurchase.TVCount.setText(String.valueOf(productModel.getOrderCount()));
            viewBinding.btmShtPurchase.cvTxtConsumerPrice.setVisibility(View.INVISIBLE);
        });


        viewBinding.btmShtPurchase.TVCount.addTextWatcher(s -> {


            try {
                productModel.setOrderCount(Long.parseLong(s));
                viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("هزینه پرداختی: "+"%1$s %2$s", productModel.getConsumerPrice() * productModel.getOrderCount(), getString(R.string.rial)));

            } catch (Exception e) {
                viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.valueOf("هزینه ای وجود ندارد"));
            }

        }, 300);
        viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("هزینه پرداختی: "+"%1$s %2$s", productModel.getConsumerPrice() * productModel.getOrderCount(), getString(R.string.rial)));


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
        ProductModel[] arr = new ProductModel[productModels.size()];
        for (int i = 0; i < productModels.size(); i++) {
            arr[i] = productModels.get(i);
        }
        AddItemToCartFragmentDirections.ActionAddItemToCartFragmentToCartFragment action = AddItemToCartFragmentDirections.actionAddItemToCartFragmentToCartFragment(arr, null);
        navigate(action);
    }

    @Override
    public void onCartEmpty() {

    }
}