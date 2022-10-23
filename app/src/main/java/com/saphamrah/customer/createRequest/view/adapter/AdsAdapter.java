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

import org.w3c.dom.Text;

import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder> {
    private Context context;
    private List<ProductModel> productModelList;
    private AdapterItemListener<ProductModel> listener;

    public AdsAdapter(Context context, List<ProductModel> productModelList, AdapterItemListener<ProductModel> listener) {
        this.context = context;
        this.productModelList = productModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_advertisement_itemview, parent , false);
        return new AdsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsAdapter.ViewHolder holder, int position) {

        holder.bind(productModelList.get(position));
    }



    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtAdTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAdTitle = itemView.findViewById(R.id.TV_AD_title);
        }

        public void bind(ProductModel productModel) {
            txtAdTitle.setText(productModel.getNameProduct());
//            listener.onItemSelect(productModel,getAdapterPosition(), AdapterAction.SELECT);
        }
    }
}
