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
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.databinding.KalaPishfaktorCustomlistBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class KalaPishFaktorAdapter extends RecyclerView.Adapter<KalaPishFaktorAdapter.BaseViewHolder> {

    private Context context;
    private List<ProductModel> models;
    private int noePrintFaktor; // این متغییر برای تعیین لی اوت مورد نظر برای نوع نمایش لیست کالا در فرم چاپ مورداستفاده قرار می گیرد.

    public KalaPishFaktorAdapter(Context context, List<ProductModel> models, int noePrintFaktor) {
        this.context = context;
        this.models = models;
        this.noePrintFaktor = noePrintFaktor;

    }


    @NonNull
    @Override
    public KalaPishFaktorAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        KalaPishfaktorCustomlistBinding kalaPishfaktorCustomlistBinding = KalaPishfaktorCustomlistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new KalaPishFaktorAdapter.BaseViewHolder(kalaPishfaktorCustomlistBinding);

    }


    @Override
    public void onBindViewHolder(@NonNull KalaPishFaktorAdapter.BaseViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.bind(formatter, models.get(position), position);

    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {
        KalaPishfaktorCustomlistBinding binding;

        BaseViewHolder(@NonNull KalaPishfaktorCustomlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bind(DecimalFormat formatter, ProductModel productModel, int position) {
            binding.lblRadif.setText(String.valueOf(position + 1));
            binding.lblNameKala.setText(productModel.getNameProduct());
            binding.lblMablagh.setText(formatter.format((int) productModel.getConsumerPrice()));
            binding.lblCarton.setText(String.valueOf(productModel.getNumCount()));
            binding.lblBasteh.setText(String.valueOf(productModel.getPackCount()));
            binding.lblAdad.setText(String.valueOf(productModel.getBoxCount()));
            double sumMablagh = productModel.getOrderCount() * productModel.getConsumerPrice();
            binding.lblMablaghKol.setText(formatter.format(sumMablagh));
        }
    }

}
