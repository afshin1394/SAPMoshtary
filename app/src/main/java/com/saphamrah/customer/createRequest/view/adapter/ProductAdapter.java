package com.saphamrah.customer.createRequest.view.adapter;

import static com.saphamrah.customer.utils.Constants.ADVERTISEMENT;
import static com.saphamrah.customer.utils.Constants.SELL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = ProductAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<ProductModel> models;
    private AdapterItemListener<ProductModel> listener;



    public ProductAdapter(Context context, ArrayList<ProductModel> models, AdapterItemListener<ProductModel> listener)
    {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        switch (viewType){
            case ADVERTISEMENT:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_advertisement_list_itemview, parent , false);
                return new ViewHolderAdvertisement(view1);

            case SELL:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_sell_itemview, parent , false);
                return new ViewHolderSell(view2);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ProductModel productModel = models.get(position);

        switch (holder.getItemViewType()){
            case ADVERTISEMENT:
                ((ViewHolderAdvertisement) holder).bind(position);
                break;

            case SELL:
                ((ViewHolderSell) holder).bind(position,productModel);

                break;
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (models.get(position).isAd()){
            return ADVERTISEMENT;
        }else{
            return SELL;
        }
    }

    @Override
    public int getItemCount()
    {
        return models.size();
    }



    public class ViewHolderSell extends RecyclerView.ViewHolder {
        private TextView nameProduct;
        private TextView consumerPrice;
        private RecyclerView img_product;
        private TextView inventory;

        public ViewHolderSell(View view) {
            super(view);

            nameProduct = view.findViewById(R.id.tv_title);
            inventory = view.findViewById(R.id.tv_inventory);
            consumerPrice = view.findViewById(R.id.tv_consumerPrice);
            itemView.setOnClickListener(view1 -> listener.onItemSelect(models.get(getAdapterPosition()),getAdapterPosition(),AdapterAction.SELECT));

        }

        public void bind(int position, ProductModel kalaModel)
        {
            nameProduct.setText(String.format("%1$s",kalaModel.getNameProduct()));
//            sellPrice.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh),kalaModel.getSellPrice(),context.getString(R.string.rial)));
            inventory.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mojodi),kalaModel.getInventory(),context.getString(R.string.adad)));
            consumerPrice.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghMasrafKonandeh),kalaModel.getConsumerPrice(),context.getString(R.string.rial)));
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
            linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            recyclerView = view.findViewById(R.id.RV_ads);
            recyclerView.setLayoutManager(linearLayoutManager);



        }

        public void bind(int position)
        {
         adsAdapter = new AdsAdapter(context,models,listener);
         recyclerView.setAdapter(adsAdapter);
        }
    }
}
