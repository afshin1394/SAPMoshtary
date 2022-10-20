package com.saphamrah.customer.createRequest.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.createRequest.view.adapter.SliderAdapterAddToCart;
import com.saphamrah.customer.databinding.FragmentAddItemToCartBinding;
import com.saphamrah.customer.utils.RxUtils.RxEditable;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


public class AddItemToCartFragment extends Fragment {

    FragmentAddItemToCartBinding binding;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public AddItemToCartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddItemToCartBinding.inflate(LayoutInflater.from(context), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        btmsht_consumerPrice = view.findViewById(R.id.consumerPrice);

        ProductModel productModel = AddItemToCartFragmentArgs.fromBundle(getArguments()).getProduct();
        setImageSlider(productModel);
        setViews(productModel);
    }



    private void setImageSlider(ProductModel productModel) {
        SliderAdapterAddToCart adapter = new SliderAdapterAddToCart(getActivity(), productModel);

        binding.imageSlider.setSliderAdapter(adapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageSlider.startAutoCycle();
    }
    private void setViews(ProductModel productModel) {
        binding.purchaseBtmsht.consumerPrice.setText(String.format("%1$s %2$s","0",getString(R.string.rial)));
        binding.txtName.setText(productModel.getNameProduct());
        binding.txtInventory.setText(String.valueOf(productModel.getInventory()));
        binding.txtSellPrice.setText(String.valueOf(productModel.getSellPrice()));
        binding.txtConsumerPrice.setText(String.valueOf(productModel.getConsumerPrice()));
        binding.txtExpirationDate.setText(String.valueOf(productModel.getExpirationDate()));
        binding.txtProductionDate.setText(String.valueOf(productModel.getProductionDate()));
        binding.txtBachNumber.setText(String.valueOf(productModel.getBachNumber()));
        binding.purchaseBtmsht.linPurchase.setOnClickListener(view -> {
            binding.purchaseBtmsht.linPurchase.setVisibility(View.GONE);
            binding.purchaseBtmsht.linPurchaseCount.setVisibility(View.VISIBLE);
            binding.purchaseBtmsht.TVCount.setText("1");
        });
        binding.purchaseBtmsht.addToCart.setOnClickListener(view -> binding.purchaseBtmsht.TVCount.setText(String.valueOf(Integer.parseInt(String.valueOf(binding.purchaseBtmsht.TVCount.getText())) + 1)));
        binding.purchaseBtmsht.removeFromCart.setOnClickListener(view2 -> {
            if (!String.valueOf(binding.purchaseBtmsht.TVCount.getText()).equals("1")) {
                try {
                    binding.purchaseBtmsht.TVCount.setText(String.valueOf(Integer.parseInt(String.valueOf(binding.purchaseBtmsht.TVCount.getText())) - 1));
                }catch (Exception e){

                }
            }
            else {
                binding.purchaseBtmsht.linPurchaseCount.setVisibility(View.GONE);
                binding.purchaseBtmsht.linPurchase.setVisibility(View.VISIBLE);
            }
        });
        if (binding.purchaseBtmsht.linPurchaseCount.getVisibility() == View.VISIBLE || binding.purchaseBtmsht.linPurchase.getVisibility() == View.GONE){
               binding.imgNotifyCart.setVisibility(View.VISIBLE);
        }else{
            binding.imgNotifyCart.setVisibility(View.GONE);
        }


        binding.purchaseBtmsht.TVCount.addTextWatcher(s -> {
            if (binding.purchaseBtmsht.linPurchaseCount.getVisibility() == View.VISIBLE || binding.purchaseBtmsht.linPurchase.getVisibility() == View.GONE){
                binding.imgNotifyCart.setVisibility(View.VISIBLE);
            }else{
                binding.imgNotifyCart.setVisibility(View.GONE);
            }
            try {
                int count = Integer.parseInt(binding.purchaseBtmsht.TVCount.getText().toString());
                binding.purchaseBtmsht.consumerPrice.setText(String.format("%1$s %2$s",productModel.getConsumerPrice()*count,getString(R.string.rial)));
            }catch (Exception e) {
                binding.purchaseBtmsht.consumerPrice.setText(String.format("%1$s %2$s","0",getString(R.string.rial)));

            }

        },700);

    }

}