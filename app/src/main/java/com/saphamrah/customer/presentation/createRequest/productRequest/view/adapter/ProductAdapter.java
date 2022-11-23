package com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter;

import static com.saphamrah.customer.utils.Constants.ADVERTISEMENT;
import static com.saphamrah.customer.utils.Constants.SELL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.customViews.EditTextWatcher;
import com.saphamrah.customer.utils.customViews.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ProductAdapter.class.getSimpleName();
    private Context context;
    private List<ProductModel> models;
    private AdapterItemListener<ProductModel> listener;


    public ProductAdapter(Context context, List<ProductModel> models, AdapterItemListener<ProductModel> listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ADVERTISEMENT:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_advertisement_list_itemview, parent, false);
                return new ViewHolderAdvertisement(view1);

            case SELL:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_sell_itemview, parent, false);
                return new ViewHolderSell(view2);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductModel productModel = models.get(position);

        switch (holder.getItemViewType()) {
            case ADVERTISEMENT:
                ((ViewHolderAdvertisement) holder).bind(position);
                break;

            case SELL:
                ((ViewHolderSell) holder).bind(productModel);
                break;
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (models.get(position).isAd()) {
            return ADVERTISEMENT;
        } else {
            return SELL;
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolderSell extends RecyclerView.ViewHolder {
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
        private ImageView imgDiscountBonus;

        public ViewHolderSell(View view) {
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
            imgDiscountBonus = view.findViewById(R.id.img_discountbonus);

            img_product.setOnClickListener(view1 -> listener.onItemSelect(models.get(getAdapterPosition()), getAdapterPosition(), AdapterAction.SELECT));
            lin_purchase.setOnClickListener(view12 -> {
                models.get(getAdapterPosition()).setOrderCount(models.get(getAdapterPosition()).getOrderCount() + 1);
                lin_purchase_count.setVisibility(View.VISIBLE);
                lin_purchase.setVisibility(View.GONE);
                listener.onItemSelect(models.get(getAdapterPosition()), getAdapterPosition(), AdapterAction.ADD);

            });

            imgDiscountBonus.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    listener.onItemSelect(models.get(getAdapterPosition()),getAdapterPosition(),AdapterAction.DETAIL);
                }
            });

                et_product_count.addTextWatcher(s -> {
                    try {
                        models.get(getAdapterPosition()).setOrderCount(Integer.parseInt(s));
                        listener.onItemSelect(models.get(getAdapterPosition()), getAdapterPosition(), AdapterAction.ADD);
                    } catch (Exception e) {
//                        models.get(getAdapterPosition()).setOrderCount(0);
                    }
                }, 500);


                img_add.setOnClickListener(view13 -> {
                    models.get(getAdapterPosition()).setOrderCount(models.get(getAdapterPosition()).getOrderCount() + 1);
                    et_product_count.setText(String.valueOf(models.get(getAdapterPosition()).getOrderCount()));
                    listener.onItemSelect(models.get(getAdapterPosition()), getAdapterPosition(), AdapterAction.ADD);
                });
                img_remove.setOnClickListener(view14 -> {

                    if (models.get(getAdapterPosition()).getOrderCount() > 1) {

                        models.get(getAdapterPosition()).setOrderCount(models.get(getAdapterPosition()).getOrderCount() - 1);
                        et_product_count.setText(String.valueOf(models.get(getAdapterPosition()).getOrderCount()));
                    } else {
                        lin_purchase.setVisibility(View.VISIBLE);
                        lin_purchase_count.setVisibility(View.GONE);
                    }
                    listener.onItemSelect(models.get(getAdapterPosition()), getAdapterPosition(), AdapterAction.REMOVE);

                });


        }

        public void bind(ProductModel productModel) {

            Glide.with(context)
                    .load(productModel.getImageResource().get(0))
                    .into(img_product);
            nameProduct.setText(String.format("%1$s", productModel.getNameProduct()));
//            sellPrice.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh),kalaModel.getSellPrice(),context.getString(R.string.rial)));
            inventory.setText(String.format("%1$s:%2$s %3$s", context.getString(R.string.mojodi), productModel.getInventory(), context.getString(R.string.adad)));
            consumerPrice.setText(String.format("%1$s:%2$s %3$s", context.getString(R.string.mablaghMasrafKonandeh), productModel.getConsumerPrice(), context.getString(R.string.rial)));
            if (productModel.getOrderCount() > 0)
            {
                lin_purchase_count.setVisibility(View.VISIBLE);
                lin_purchase.setVisibility(View.GONE);
                et_product_count.setText(String.valueOf(productModel.getOrderCount()));
            }
            else
            {
                lin_purchase_count.setVisibility(View.GONE);
                lin_purchase.setVisibility(View.VISIBLE);
            }

//            bachNumber.setText(String.format("%1$s:%2$s",context.getString(R.string.shomareBach),kalaModel.getBachNumber()));

//            linChoice.setOnClickListener(view -> listener.onItemSelect(kalaModel,position, AdapterAction.SELECT));
        }
    }

    public class ViewHolderAdvertisement extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private LinearLayoutManager linearLayoutManager;
        private AdsAdapter adsAdapter;

        public ViewHolderAdvertisement(View view) {
            super(view);
            linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerView = view.findViewById(R.id.RV_ads);
            recyclerView.setLayoutManager(linearLayoutManager);


        }

        public void bind(int position) {
            adsAdapter = new AdsAdapter(context, models, listener);
            recyclerView.setAdapter(adsAdapter);

        }
    }
}
