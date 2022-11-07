package com.saphamrah.customer.presentation.createRequest.cart.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.customViews.EditTextWatcher;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> models;
    private AdapterItemListener<ProductModel> listener;

    public CartProductAdapter(Context context ,List<ProductModel> productModels,AdapterItemListener<ProductModel> listener) {
        this.context = context;
        this.models = productModels;
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
        ProductModel productModel = models.get(position);
        holder.bind(position,productModel);
    }



    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView nameProduct;
        private TextView consumerPrice;
        private CircleImageView img_product;
        private TextView inventory;
        private View img_addToCart;
        private LinearLayout lin_purchase;
        private LinearLayout lin_purchase_count;
        private ImageView img_add;
        private ImageView img_remove;
        private EditTextWatcher et_product_count;

        public ViewHolder(@NonNull View view) {
            super(view);

            nameProduct = view.findViewById(R.id.tv_title);
            inventory = view.findViewById(R.id.tv_inventory);
            consumerPrice = view.findViewById(R.id.tv_consumerPrice);
            img_addToCart = view.findViewById(R.id.img_add_to_cart);
            lin_purchase = view.findViewById(R.id.lin_purchase);
            lin_purchase_count = view.findViewById(R.id.lin_purchaseCount);
            img_remove = view.findViewById(R.id.img_remove);
            img_add = view.findViewById(R.id.img_add);
            et_product_count = view.findViewById(R.id.ET_product_count);
            img_product = view.findViewById(R.id.img_product);

        }

        public void bind(int position,ProductModel productModel) {
            nameProduct.setText(String.format("%1$s", productModel.getNameProduct()));
//            sellPrice.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh),kalaModel.getSellPrice(),context.getString(R.string.rial)));
            inventory.setText(String.format("%1$s:%2$s %3$s", context.getString(R.string.mojodi), productModel.getInventory(), context.getString(R.string.adad)));
            consumerPrice.setText(String.format("%1$s:%2$s %3$s", context.getString(R.string.mablaghMasrafKonandeh), productModel.getConsumerPrice(), context.getString(R.string.rial)));
            if (productModel.getOrderCount() > 0) {
                lin_purchase_count.setVisibility(View.VISIBLE);
                et_product_count.setText(String.valueOf(productModel.getOrderCount()));
            }

            img_product.setOnClickListener(view1 -> listener.onItemSelect(models.get(position), position, AdapterAction.SELECT));
            lin_purchase.setOnClickListener(view12 -> {
                models.get(getAdapterPosition()).setOrderCount(models.get(position).getOrderCount() + 1);
                lin_purchase_count.setVisibility(View.VISIBLE);
                lin_purchase.setVisibility(View.GONE);
                listener.onItemSelect(models.get(position), position, AdapterAction.ADD);
            });

            if (lin_purchase_count.getVisibility() == View.VISIBLE) {
                et_product_count.addTextWatcher(s -> {
                    try {

                        models.get(position).setOrderCount(Integer.parseInt(s));
                        listener.onItemSelect(models.get(position), position, AdapterAction.ADD);
                    } catch (Exception e) {

                    }
                }, 500);

                img_add.setOnClickListener(view13 -> {
                    models.get(position).setOrderCount(models.get(position).getOrderCount() + 1);
                    et_product_count.setText(String.valueOf(models.get(position).getOrderCount()));
                    listener.onItemSelect(models.get(position), position, AdapterAction.ADD);
                });
                img_remove.setOnClickListener(view14 -> {
                    models.get(position).setOrderCount(models.get(position).getOrderCount() - 1);
                    if (models.get(position).getOrderCount() > 0)
                    {
                        et_product_count.setText(String.valueOf(models.get(position).getOrderCount()));
                        listener.onItemSelect(models.get(position), position, AdapterAction.REMOVE);

                    }else{
                        listener.onItemSelect(models.get(position), position, AdapterAction.REMOVE);
                        models.remove(position);
                        notifyDataSetChanged();
                    }
                });

            }
        }
    }
}
