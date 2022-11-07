package com.saphamrah.customer.presentation.createRequest.cart.view.adapter;

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

public class BonusAdapter extends RecyclerView.Adapter<BonusAdapter.ViewHolder> {
    private Context context;
    private List<BonusModel> bonusModels;

    public BonusAdapter(Context context, List<BonusModel> bonusModels) {
        this.context = context;
        this.bonusModels = bonusModels;
    }

    @NonNull
    @Override
    public BonusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bonus_itemview, parent, false);
        return new BonusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BonusAdapter.ViewHolder holder, int position) {
          holder.bind(bonusModels.get(position));
    }

    @Override
    public int getItemCount() {
        return bonusModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bonusName;
        private TextView bonusQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bonusName = itemView.findViewById(R.id.tv_bonus_name);
            bonusQuantity = itemView.findViewById(R.id.tv_bonus_quantity);
        }

        public void bind(BonusModel bonusModel)
        {
            bonusName.setText(bonusModel.getDescription());
            bonusQuantity.setText(String.format("%1$s%2$s%3$s",context.getString(R.string.tedadJayezeh),bonusModel.getAmount(),context.getString(R.string.adad)));

        }
    }
}
