package com.saphamrah.customer.presentation.createRequest.cart.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.customViews.EditTextWatcher;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {

    private Context context;
//    private List<ProductModel> models;
    private AdapterItemListener<ProductModel> listener;
//    private AsyncListDiffer<ProductModel> mDiffer;
    private List<ProductModel> productModels;
    private DiffUtil.ItemCallback<ProductModel> diffCallback = new DiffUtil.ItemCallback<ProductModel>() {
        @Override
        public boolean areItemsTheSame(ProductModel oldItem, ProductModel newItem) {
            return oldItem.getId() == newItem.getId();
        }        @Override
        public boolean areContentsTheSame(ProductModel oldItem, ProductModel newItem) {
            return oldItem.getOrderCount() == newItem.getOrderCount();
        }
    };//define AsyncListDiffer
    public CartProductAdapter(Context context ,List<ProductModel> productModels,AdapterItemListener<ProductModel> listener) {
        this.context = context;
        this.productModels = productModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_adapter_itemview, parent, false);
        return new CartProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapter.ViewHolder holder, int position) {
        ProductModel productModel = productModels.get(position);
        holder.bind(productModel);
    }

//
//    public void submitList(List<ProductModel> data) {
//        mDiffer.submitList(data);
//    }
//    public ProductModel getItem(int position) {
//        return mDiffer.getCurrentList().get(position);
//    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView nameProduct;
        private TextView consumerPrice;
        private CircleImageView img_product;
        private TextView inventory;
        private View img_addToCart;
        private MaterialCardView card_purchase;
        private MaterialCardView card_purchaseCount;
        private ImageView img_add;
        private ImageView img_remove;
        private EditTextWatcher et_product_count;

        public ViewHolder(@NonNull View view) {
            super(view);

            nameProduct = view.findViewById(R.id.tv_title);
            inventory = view.findViewById(R.id.tv_inventory);
            consumerPrice = view.findViewById(R.id.tv_consumerPrice);
            img_addToCart = view.findViewById(R.id.img_add_to_cart);
            card_purchase = view.findViewById(R.id.card_purchase);
            card_purchaseCount = view.findViewById(R.id.card_purchaseCount);
            img_remove = view.findViewById(R.id.img_remove);
            img_add = view.findViewById(R.id.img_add);
            et_product_count = view.findViewById(R.id.etv_product_count);
            img_product = view.findViewById(R.id.img_product);

        }

        public void bind(ProductModel productModel) {
            nameProduct.setText(String.format("%1$s", productModel.getNameProduct()));
//            sellPrice.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh),kalaModel.getSellPrice(),context.getString(R.string.rial)));
            inventory.setText(String.format("%1$s:%2$s %3$s", context.getString(R.string.mablaghForosh), productModel.getSellPrice(), context.getString(R.string.rial)));
            consumerPrice.setText(String.format("%1$s:%2$s %3$s", context.getString(R.string.mablaghMasrafKonandeh), productModel.getConsumerPrice(), context.getString(R.string.rial)));
            if (productModel.getOrderCount() > 0) {
                card_purchaseCount.setVisibility(View.VISIBLE);
                et_product_count.setText(String.valueOf(productModel.getOrderCount()));
            }

            img_product.setOnClickListener(view1 -> listener.onItemSelect(productModel, getAdapterPosition(), AdapterAction.SELECT));
            card_purchase.setOnClickListener(view12 -> {
                productModel.setOrderCount(productModel.getOrderCount() + 1);
                card_purchaseCount.setVisibility(View.VISIBLE);
                card_purchase.setVisibility(View.GONE);
                listener.onItemSelect(productModel, getAdapterPosition(), AdapterAction.ADD);
            });

            if (card_purchaseCount.getVisibility() == View.VISIBLE)
            {
                et_product_count.addTextWatcher(s -> {
                    try {
                        productModel.setOrderCount(Integer.parseInt(s));
                        listener.onItemSelect(productModel, getAdapterPosition(), AdapterAction.ADD);
                    } catch (Exception e) {

                    }
                }, 500);


                Glide.with(context)
                        .load(productModel.getImageResource().get(0))
                        .into(img_product);

                img_add.setOnClickListener(view13 -> {
                    productModel.setOrderCount(productModel.getOrderCount() + 1);
                    et_product_count.setText(String.valueOf(productModel.getOrderCount()));
                    listener.onItemSelect(productModel, getAdapterPosition(), AdapterAction.ADD);
                });
                img_remove.setOnClickListener(view14 -> {
                    if (productModel.getOrderCount() > 0)
                    {
                        productModel.setOrderCount(productModel.getOrderCount() - 1);
                        et_product_count.setText(String.valueOf(productModel.getOrderCount()));
                    }
                    listener.onItemSelect(productModel, getAdapterPosition(), AdapterAction.REMOVE);

                });

            }
        }
    }
}
