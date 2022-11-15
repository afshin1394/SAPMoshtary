package com.saphamrah.customer.presentation.createRequest.verifyRequest.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.databinding.TakhfifPishfaktorCustomlistBinding;

import java.text.DecimalFormat;
import java.util.List;

public class TakhfifPishFaktorAdatper extends RecyclerView.Adapter<TakhfifPishFaktorAdatper.BaseViewHolder> {

    private Context context;
    private List<DiscountModel> models;
    private int noePrintFaktor;

    public TakhfifPishFaktorAdatper(Context context, List<DiscountModel> models, int noePrintFaktor)
    {
        this.context = context;
        this.models = models;
        this.noePrintFaktor = noePrintFaktor;

    }


    @NonNull
    @Override
    public TakhfifPishFaktorAdatper.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        /*View view = LayoutInflater.from(context).inflate(R.layout.takhfif_pishfaktor_customlist, parent , false);
        return new TakhfifPishFaktorAdatper.BaseViewHolder(view);*/

        TakhfifPishfaktorCustomlistBinding takhfifPishfaktorCustomlistBinding =
                TakhfifPishfaktorCustomlistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new BaseViewHolder(takhfifPishfaktorCustomlistBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull TakhfifPishFaktorAdatper.BaseViewHolder holder, int position)
    {
        holder.bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder
    {
        TakhfifPishfaktorCustomlistBinding binding;

        BaseViewHolder(@NonNull TakhfifPishfaktorCustomlistBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected void bind(DiscountModel discountModel)
        {
            binding.lblSharhTakhfif.setText(discountModel.getDescription());
            binding.lblDarsadTakhfif.setText(String.format("%1$s %2$s", discountModel.getPercentage() , context.getResources().getString(R.string.percentageSign)));
            binding.lblMablaghTakhfif.setText(new DecimalFormat("#,###,###").format((int) discountModel.getAmount()));
        }

    }

}
