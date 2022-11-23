package com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.DiscountModel;

import java.util.List;

public class DiscountProductAdapter extends RecyclerView.Adapter<DiscountProductAdapter.ViewHolder> {
    private Context context;
    private List<DiscountModel> discounts;

    public DiscountProductAdapter(Context context, List<DiscountModel> discounts) {
        this.context = context;
        this.discounts = discounts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_product_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(discounts.get(position));
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView percentage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tv_discount_description);
            percentage = itemView.findViewById(R.id.tv_discount_percentage);
        }

        public void bind(DiscountModel discountModel) {
            description.setText(discountModel.getDescription());
            percentage.setText(String.format("%1$s : %2$s%3$s",context.getString(R.string.discount_percentage),discountModel.getPercentage(),context.getString(R.string.percentageSign)));
        }
    }
}