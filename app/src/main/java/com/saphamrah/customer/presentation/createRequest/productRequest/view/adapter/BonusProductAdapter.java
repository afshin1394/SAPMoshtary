package com.saphamrah.customer.presentation.createRequest.productRequest.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.BonusModel;

import java.util.List;

public class BonusProductAdapter extends RecyclerView.Adapter<BonusProductAdapter.ViewHolder> {
    private Context context;
    private List<BonusModel> bonusModels;

    public BonusProductAdapter(Context context, List<BonusModel> bonusModels) {
        this.context = context;
        this.bonusModels = bonusModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bonus_product_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(bonusModels.get(position));
    }

    @Override
    public int getItemCount() {
        return bonusModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bonusName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bonusName = itemView.findViewById(R.id.tv_bonus_name);
        }

        public void bind(BonusModel bonusModel)
        {
            bonusName.setText(bonusModel.getDescription());
        }
    }
}