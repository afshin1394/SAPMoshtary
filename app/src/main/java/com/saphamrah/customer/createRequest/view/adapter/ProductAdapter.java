package com.saphamrah.customer.createRequest.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private static final String TAG = ProductAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<ProductModel> models;
    private AdapterItemListener<ProductModel> listener;
    private int heightOfRecycler;
    private int widthOfRecycler;


    public ProductAdapter(Context context, ArrayList<ProductModel> models, AdapterItemListener<ProductModel> listener)
    {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_itemview , parent , false);
        return new ProductAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position)
    {
        ProductModel kalaModel = models.get(position);
        holder.bind(position,kalaModel);
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }

    public void setMeasurements(int heightOfRecycler, int widthOfRecycler) {
        this.heightOfRecycler = heightOfRecycler;
        this.widthOfRecycler = widthOfRecycler;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameProduct;
        private TextView consumerPrice;
        private RecyclerView img_product;
        private TextView inventory;

        public ViewHolder(View view) {
            super(view);

            nameProduct = view.findViewById(R.id.txt_nameKala);
            inventory = view.findViewById(R.id.txt_inventory);
            consumerPrice = view.findViewById(R.id.txt_consumerPrice);
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
}
