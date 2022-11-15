package com.saphamrah.customer.presentation.createRequest.cart.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.C;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;

import java.util.List;

public class MarjoeeCartAdapter extends RecyclerView.Adapter<MarjoeeCartAdapter.ViewHolder> {
    private Context context;
    private List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelList;

    public MarjoeeCartAdapter(Context context, List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelList) {
        this.context = context;
        this.elamMarjoeeForoshandehModelList = elamMarjoeeForoshandehModelList;
    }

    @NonNull
    @Override
    public MarjoeeCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marjoee_cart_itemview, parent, false);
        return new MarjoeeCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarjoeeCartAdapter.ViewHolder holder, int position) {
           holder.bind(elamMarjoeeForoshandehModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return elamMarjoeeForoshandehModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView marjoeeName;
        private TextView marjoeeTedad;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            marjoeeName = itemView.findViewById(R.id.tv_marjoee_kala_cart_name);
            marjoeeTedad = itemView.findViewById(R.id.tv_tedad_marjoee_cart);
        }

        public void bind(ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel) {
            marjoeeName.setText(elamMarjoeeForoshandehModel.getNameKala());
            marjoeeTedad.setText(String.format("%1$s:%2$s",context.getString(R.string.tedad),elamMarjoeeForoshandehModel.getTedad3()));
        }
    }
}
