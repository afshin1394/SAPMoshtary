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
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.databinding.ItemBaseSearchBinding;
import com.saphamrah.customer.databinding.JayezehPishfaktorCustomlistBinding;

import java.util.List;

public class JayezehPishFaktorAdapter extends RecyclerView.Adapter<JayezehPishFaktorAdapter.ViewHolder> {

    private Context context;
    private List<BonusModel> models;
    private int noePrintFaktor;

    public JayezehPishFaktorAdapter(Context context, List<BonusModel> models, int noePrintFaktor) {
        this.context = context;
        this.models = models;
        this.noePrintFaktor = noePrintFaktor;

    }


    @NonNull
    @Override
    public JayezehPishFaktorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JayezehPishfaktorCustomlistBinding jayezehPishfaktorCustomlistBinding = JayezehPishfaktorCustomlistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JayezehPishFaktorAdapter.ViewHolder(jayezehPishfaktorCustomlistBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull JayezehPishFaktorAdapter.ViewHolder holder, int position) {
        holder.bind(models.get(position));

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        JayezehPishfaktorCustomlistBinding binding;

        public ViewHolder(@NonNull JayezehPishfaktorCustomlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BonusModel bonusModel) {
            //        holder.lblSharh.setText(models.get(position).getSharh());
            binding.lblTedad.setText(String.valueOf((int) bonusModel.getAmount()));

//        String sood = String.valueOf(models.get(position).getTedad() * models.get(position).getMablaghMasrafKonandeh());

            binding.lblSharh.setText(String.format("%1$s", bonusModel.getDescription()));

//            holder.lblSoodeJayezeh.setVisibility(View.GONE);
        }
          /*  Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));


            lblTedad = itemView.findViewById(R.id.lblTedad);
//            lblSoodeJayezeh = itemView.findViewById(R.id.lblSoodeJayezeh);
            lblSharh = itemView.findViewById(R.id.lblSharh);

            lblSharh.setTypeface(font);
            lblTedad.setTypeface(font);*/
//            lblTedad.setTypeface(font);
//            lblSoodeJayezeh.setTypeface(font);

//            if (noePrintFaktor == 2)
//            {
//                lblSharh.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.FontSize30));
//                lblTedad.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.FontSize30));
//                lblSoodeJayezeh.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.FontSize30));
//            }
        }


}
