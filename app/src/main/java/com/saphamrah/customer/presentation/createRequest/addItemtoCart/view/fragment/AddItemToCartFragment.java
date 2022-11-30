package com.saphamrah.customer.presentation.createRequest.addItemtoCart.view.fragment;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.presentation.createRequest.addItemtoCart.interactor.AddItemToCartInteractor;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.presentation.createRequest.addItemtoCart.view.adapter.SliderAdapterAddToCart;
import com.saphamrah.customer.databinding.FragmentAddItemToCartBinding;
import com.saphamrah.customer.presentation.createRequest.cart.view.adapter.BonusAdapter;
import com.saphamrah.customer.presentation.createRequest.cart.view.adapter.DiscountAdapter;
import com.saphamrah.customer.utils.RxUtils.Watcher;
import com.saphamrah.customer.utils.customViews.OnSingleClickListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class AddItemToCartFragment extends BaseFragment<AddItemToCartInteractor.PresenterOps, FragmentAddItemToCartBinding, CreateRequestActivity> implements CreateRequestActivity.CartListener {
    private Context context;
    private BottomSheetBehavior bottomSheetBehavior;
    private ProductModel productModel;

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
      navigateUp();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        activity.setCartListener(this);
        productModel = AddItemToCartFragmentArgs.fromBundle(getArguments()).getProduct();
        setImageSlider(productModel);
        setViews(productModel);
        Log.i("COUNTERSS", "initViews: order_count: "+productModel.getOrderCount()+"num_count: "+productModel.getNumCount() + "packCount: "+productModel.getPackCount() + "boxCount: " + productModel.getBoxCount());

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
    private void setViews(ProductModel productModel)
    {
        if (productModel.getOrderCount() > 0) {
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.VISIBLE);
            openCartonBastehAdadBottomSheet(productModel);
        } else {
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.cvTxtConsumerPrice.setVisibility(View.INVISIBLE);
        }
        Log.i(TAG, "openCartonBastehAdadBottomSheet:carton "+viewBinding.btmShtPurchase.tvQuantityCarton.getText().toString());
        Log.i(TAG, "openCartonBastehAdadBottomSheet:basteh "+viewBinding.btmShtPurchase.tvQuantityBasteh.getText().toString());
        Log.i(TAG, "openCartonBastehAdadBottomSheet:adad "+viewBinding.btmShtPurchase.tvQuantityAdad.getText().toString());
        viewBinding.btmShtPurchase.txtProductName.setText(productModel.getNameProduct());
        viewBinding.btmShtPurchase.kalaCodeTv.setText(productModel.getCodeKala());
        viewBinding.btmShtPurchase.brandTv.setText(productModel.getSazmanForosh());
        viewBinding.btmShtPurchase.qeymateForoshTv.setText(productModel.getSellPrice() + " تومان");
        viewBinding.btmShtPurchase.qeymateMasrafkonandeTv.setText( productModel.getConsumerPrice() + " تومان");
        viewBinding.btmShtPurchase.numberInBoxTv.setText(" عدد " + productModel.getNumInBox());
        viewBinding.btmShtPurchase.weightTv.setText("گرم" + productModel.getWeight());

        viewBinding.btmShtPurchase.linPurchase.setOnClickListener(view -> {
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.VISIBLE);
            viewBinding.btmShtPurchase.cvTxtConsumerPrice.setVisibility(View.VISIBLE);
//            viewBinding.btmShtPurchase.TVCount.setText("1");
            productModel.setNumCount(1);
            productModel.setOrderCount(1);
            openCartonBastehAdadBottomSheet(productModel);
            activity.checkCart(true);
        });

//        viewBinding.btmShtPurchase.la.setOnClickListener(view -> {
//            productModel.setOrderCount(productModel.getOrderCount()+1);
//            viewBinding.btmShtPurchase.TVCount.setText(String.valueOf(Integer.parseInt(String.valueOf(viewBinding.btmShtPurchase.TVCount.getText())) + 1));
//                     });
//                viewBinding.btmShtPurchase.removeFromCart.setOnClickListener(view2 -> {
//
//
//                    if (productModel.getOrderCount() > 0) {
//                        productModel.setOrderCount(productModel.getOrderCount() - 1);
//                        activity.checkCart(true);
//                        try {
//                            viewBinding.btmShtPurchase.TVCount.setText(String.valueOf(productModel.getOrderCount()));
//                        } catch (Exception e) {
//
//                        }
//                        if (productModel.getOrderCount() == 0){
//                            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
//                            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
//                            viewBinding.btmShtPurchase.cvTxtConsumerPrice.setVisibility(View.INVISIBLE);
//                        }
//                    } else {
//                        viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
//                        viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
//                    }
//
//                });



//        viewBinding.btmShtPurchase.TVCount.addTextWatcher(s -> {
//
//
//            try {
//                productModel.setOrderCount(Integer.parseInt(s));
//                viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("هزینه پرداختی: "+"%1$s %2$s", productModel.getSellPrice() * productModel.getOrderCount(), getString(R.string.rial)));
//
//            } catch (Exception e) {
//                viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.valueOf("هزینه ای وجود ندارد"));
//            }
//
//        }, 300);
//        viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("هزینه پرداختی: "+"%1$s %2$s", productModel.getSellPrice() * productModel.getOrderCount(), getString(R.string.rial)));


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
        Log.d(TAG, "onCartClick: "+productModel.toString());
        ProductModel[] arr = new ProductModel[productModels.size()];
        for (int i = 0; i < productModels.size(); i++) {
            arr[i] = productModels.get(i);
        }

        AddItemToCartFragmentDirections.ActionAddItemToCartFragmentToCartFragment action = AddItemToCartFragmentDirections.actionAddItemToCartFragmentToCartFragment(arr, null);
        navigate(action);
    }

    @Override
    public void onCartEmpty() {
        Log.i(TAG, "onCartEmpty: ");
        navigateUp();
    }
    private void openCartonBastehAdadBottomSheet(ProductModel  productModel) {


        viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("هزینه پرداختی: "+"%1$s %2$s", productModel.getSellPrice() * productModel.getOrderCount(), getString(R.string.rial)));
        Log.d(TAG, "openCartonBastehAdadBottomSheet: "+productModel.toString());
        viewBinding.btmShtPurchase.tvQuantityCarton.setText("");
        viewBinding.btmShtPurchase.tvQuantityAdad.setText("");
        viewBinding.btmShtPurchase.tvQuantityBasteh.setText("");
        viewBinding.btmShtPurchase.tvQuantityCarton.setHint(getString(R.string.carton));
        viewBinding.btmShtPurchase.tvQuantityBasteh.setHint(getString(R.string.basteh));
        viewBinding.btmShtPurchase.tvQuantityAdad.setHint(getString(R.string.adad));
        if (productModel.getBoxCount()>0)
            viewBinding.btmShtPurchase.tvQuantityCarton.setText(String.valueOf(productModel.getBoxCount()));
        if (productModel.getPackCount()>0)
            viewBinding.btmShtPurchase.tvQuantityBasteh.setText(String.valueOf(productModel.getPackCount()));
        if (productModel.getNumCount()>0)
            viewBinding.btmShtPurchase.tvQuantityAdad.setText(String.valueOf(productModel.getNumCount()));


        viewBinding.btmShtPurchase.tvAddCarton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                productModel.setBoxCount(productModel.getBoxCount() + 1);
                int orderCount  = calculateOrderCount(productModel);
                productModel.setOrderCount( orderCount );
                viewBinding.btmShtPurchase.tvQuantityCarton.setText(String.valueOf(productModel.getBoxCount()));
                activity.checkCart(true);
            }
        });

        viewBinding.btmShtPurchase.tvAddBasteh.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                productModel.setPackCount(productModel.getPackCount() + 1);
                int orderCount  = calculateOrderCount(productModel);
                productModel.setOrderCount( orderCount );
                viewBinding.btmShtPurchase.tvQuantityBasteh.setText(String.valueOf(productModel.getPackCount()));
                activity.checkCart(true);

            }
        });

        viewBinding.btmShtPurchase.tvAddAdad.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                productModel.setNumCount(productModel.getNumCount() + 1);
                int orderCount  = calculateOrderCount(productModel);
                productModel.setOrderCount( orderCount );
                viewBinding.btmShtPurchase.tvQuantityAdad.setText(String.valueOf(productModel.getNumCount()));
                activity.checkCart(true);

            }
        });
        viewBinding.btmShtPurchase.tvRemoveCarton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {


                if (productModel.getBoxCount() > 1)
                {
                    productModel.setBoxCount(productModel.getBoxCount() - 1);
                    int orderCount  = calculateOrderCount(productModel);
                    productModel.setOrderCount( orderCount );
                    checkOrderCount(orderCount);
                    viewBinding.btmShtPurchase.tvQuantityCarton.setText(String.valueOf(productModel.getBoxCount()));
                }
                else
                {
                    if (productModel.getBoxCount() == 1)
                    {
                        productModel.setBoxCount(productModel.getBoxCount() - 1);
                        int orderCount = calculateOrderCount(productModel);
                        productModel.setOrderCount(orderCount);
                        checkOrderCount(orderCount);
                    }
                    viewBinding.btmShtPurchase.tvQuantityCarton.setText("");
                    viewBinding.btmShtPurchase.tvQuantityCarton.setHint(getString(R.string.carton));
                }
                activity.checkCart(true);

            }
        });
        viewBinding.btmShtPurchase.tvRemoveBasteh.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (productModel.getPackCount() > 1){
                    productModel.setPackCount(productModel.getPackCount() - 1);
                    int orderCount  = calculateOrderCount(productModel);
                    productModel.setOrderCount( orderCount );
                    checkOrderCount(orderCount);
                    viewBinding.btmShtPurchase.tvQuantityBasteh.setText(String.valueOf(productModel.getPackCount()));
                }else{
                    if (productModel.getPackCount() == 1)
                    {
                        productModel.setPackCount(productModel.getPackCount() - 1);
                        int orderCount = calculateOrderCount(productModel);
                        productModel.setOrderCount(orderCount);
                        checkOrderCount(orderCount);
                    }
                    viewBinding.btmShtPurchase.tvQuantityBasteh.setText("");
                    viewBinding.btmShtPurchase.tvQuantityBasteh.setHint(getString(R.string.basteh));
                }
                activity.checkCart(true);

            }
        });
        viewBinding.btmShtPurchase.tvRemoveCarton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (productModel.getNumCount() > 1)
                {
                    productModel.setNumCount(productModel.getNumCount() - 1);
                    int orderCount  = calculateOrderCount(productModel);
                    productModel.setOrderCount( orderCount );
                    checkOrderCount(orderCount);
                    viewBinding.btmShtPurchase.tvQuantityCarton.setText(String.valueOf(productModel.getNumCount()));
                }else{
                    if (productModel.getNumCount() == 1) {
                        productModel.setNumCount(productModel.getNumCount() - 1);
                        int orderCount = calculateOrderCount(productModel);
                        productModel.setOrderCount(orderCount);
                        checkOrderCount(orderCount);
                    }
                    viewBinding.btmShtPurchase.tvQuantityCarton.setText("");
                    viewBinding.btmShtPurchase.tvQuantityCarton.setHint(getString(R.string.adad));
                }
                activity.checkCart(true);

            }
        });
        viewBinding.btmShtPurchase.trashIv.setOnClickListener(view -> {
            productModel.setNumCount(0);
            productModel.setPackCount(0);
            productModel.setBoxCount(0);
            productModel.setOrderCount(0);
            activity.checkCart(true);
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
            viewBinding.btmShtPurchase.cvTxtConsumerPrice.setVisibility(View.INVISIBLE);
        });
//        viewBinding.btmShtPurchase.layCarton.tvQuantity.removeWatcher();
//        viewBinding.btmShtPurchase.layCarton.tvQuantity.addTextWatcher(new Watcher() {
//            @Override
//            public void onTextChange(String s) {
//                try {
//                    productModel.setBoxCount(Integer.parseInt(s));
//                    int orderCount  = calculateOrderCount(productModel);
//                    productModel.setOrderCount(orderCount);
//                    viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("هزینه پرداختی: "+"%1$s %2$s", productModel.getSellPrice() * productModel.getOrderCount(), getString(R.string.rial)));
//                }catch (Exception e){
//                    productModel.setBoxCount(0);
//                    int orderCount  = calculateOrderCount(productModel);
//                    productModel.setOrderCount(orderCount);
//                }
//                activity.checkCart(true);
//            }
//        },400);
//        viewBinding.btmShtPurchase.layBasteh.tvQuantity.removeWatcher();
//        viewBinding.btmShtPurchase.layBasteh.tvQuantity.addTextWatcher(new Watcher() {
//            @Override
//            public void onTextChange(String s) {
//                try {
//                    productModel.setPackCount(Integer.parseInt(s));
//                    int orderCount  = calculateOrderCount(productModel);
//                    productModel.setOrderCount(orderCount);
//                    viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("هزینه پرداختی: "+"%1$s %2$s", productModel.getSellPrice() * productModel.getOrderCount(), getString(R.string.rial)));
//
//                }catch (Exception e){
//                    productModel.setPackCount(0);
//                    int orderCount  = calculateOrderCount(productModel);
//                    productModel.setOrderCount(orderCount);
//                }
//                activity.checkCart(true);
//            }
//        },400);
//        viewBinding.btmShtPurchase.layAdad.tvQuantity.removeWatcher();
//        viewBinding.btmShtPurchase.layAdad.tvQuantity.addTextWatcher(new Watcher() {
//            @Override
//            public void onTextChange(String s) {
//                try {
//                    productModel.setNumCount(Integer.parseInt(s));
//                    int orderCount  = calculateOrderCount(productModel);
//                    productModel.setOrderCount(orderCount);
//                    viewBinding.btmShtPurchase.txtConsumerPrice.setText(String.format("هزینه پرداختی: "+"%1$s %2$s", productModel.getSellPrice() * productModel.getOrderCount(), getString(R.string.rial)));
//
//                }catch (Exception e){
//                    productModel.setNumCount(0);
//                    int orderCount  = calculateOrderCount(productModel);
//                    productModel.setOrderCount(orderCount);
//                }
//                activity.checkCart(true);
//            }
//        },400);
    }

    private void checkOrderCount(int orderCount) {
        if (orderCount == 0){
            viewBinding.btmShtPurchase.linPurchaseCount.setVisibility(View.GONE);
            viewBinding.btmShtPurchase.linPurchase.setVisibility(View.VISIBLE);
        }
    }

    private int calculateOrderCount(ProductModel productModel) {
        return productModel.getBoxCount() * productModel.getNumInBox() + productModel.getPackCount() * productModel.getNumInPack() + productModel.getNumCount();
    }

}