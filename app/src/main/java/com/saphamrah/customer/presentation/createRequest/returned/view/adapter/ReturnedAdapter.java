package com.saphamrah.customer.presentation.createRequest.returned.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.List;

public class ReturnedAdapter extends RecyclerView.Adapter<ReturnedAdapter.ViewHolder> {
    private Context context;
    private List<ElamMarjoeeForoshandehModel> models;
    private AdapterItemListener<ElamMarjoeeForoshandehModel> listener;

    public ReturnedAdapter(Context context, List<ElamMarjoeeForoshandehModel> models, AdapterItemListener<ElamMarjoeeForoshandehModel> listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marjoee_itemview , parent , false);
        return new ReturnedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnedAdapter.ViewHolder holder, int position) {
        holder.bind(models.get(position));
    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCodeKala;
        private TextView shomarehBach;
        private TextView mablaghForosh;
        private TextView mablaghMarjoee;
        private LinearLayout addMarjoee;
        private LinearLayout addMarjoeeCount;
        private EditText count;
        private ImageView addToCart;
        private ImageView removeFromCart;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCodeKala = itemView.findViewById(R.id.tv_CodeNameKala);
            shomarehBach = itemView.findViewById(R.id.tv_ShomareBach);
            mablaghForosh = itemView.findViewById(R.id.tv_MablaghForosh);
            mablaghMarjoee = itemView.findViewById(R.id.tv_MablaghMarjoee);
            addMarjoee = itemView.findViewById(R.id.lin_addmarjoee);
            addMarjoeeCount = itemView.findViewById(R.id.lin_addmarjoee_count);
            count = itemView.findViewById(R.id.tv_count);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            removeFromCart = itemView.findViewById(R.id.remove_from_cart);
        }

        public void bind(ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel) {
            nameCodeKala.setText(String.format("%1$s - %2$s",elamMarjoeeForoshandehModel.getCodeKala(),elamMarjoeeForoshandehModel.getNameKala()));
            shomarehBach.setText(String.format("%1$s:%2$s",R.string.shomareBach,elamMarjoeeForoshandehModel.getNameKala()));
            mablaghForosh.setText(String.format("%1$s:%2$s %3$s",R.string.mablaghForosh,elamMarjoeeForoshandehModel.getGheymatForosh(),R.string.rial));
            mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",R.string.mablaghForosh,elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3(),R.string.rial));

        }
    }
}
