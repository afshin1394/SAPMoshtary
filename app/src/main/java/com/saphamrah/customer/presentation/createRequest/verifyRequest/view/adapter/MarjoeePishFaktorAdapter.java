package com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.databinding.MarjoeePishfaktorCustomlistBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MarjoeePishFaktorAdapter extends RecyclerView.Adapter<MarjoeePishFaktorAdapter.ViewHolder> {

    private Context context;
    private List<ElamMarjoeeForoshandehModel> models;
    private int noePrintFaktor;

    public MarjoeePishFaktorAdapter(Context context, List<ElamMarjoeeForoshandehModel> models, int noePrintFaktor)
    {
        this.context = context;
        this.models = models;
        this.noePrintFaktor = noePrintFaktor;
    }


    @NonNull
    @Override
    public MarjoeePishFaktorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        MarjoeePishfaktorCustomlistBinding marjoeePishfaktorCustomlistBinding =
                MarjoeePishfaktorCustomlistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MarjoeePishFaktorAdapter.ViewHolder(marjoeePishfaktorCustomlistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarjoeePishFaktorAdapter.ViewHolder holder, int position)
    {
       holder.bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        MarjoeePishfaktorCustomlistBinding binding;
        public ViewHolder(@NonNull MarjoeePishfaktorCustomlistBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel) {
            binding.lblNameKala.setText(elamMarjoeeForoshandehModel.getNameKala());
            binding.lblShomarehBach.setText(elamMarjoeeForoshandehModel.getShomarehBach());
            binding.lblTedad.setText(String.valueOf(elamMarjoeeForoshandehModel.getTedad3()));
            binding.lblFee.setText(new DecimalFormat("#,###,###").format(elamMarjoeeForoshandehModel.getGheymatForosh()*elamMarjoeeForoshandehModel.getTedad3()));
        }
    }

}
