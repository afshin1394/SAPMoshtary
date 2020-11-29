package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TakhfifForoshAdapter extends RecyclerView.Adapter<TakhfifForoshAdapter.ViewHolder>
{


    private Context context;
    private ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels;

    public TakhfifForoshAdapter(Context context, ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels)
    {
        this.context = context;
        this.darkhastFaktorTakhfifModels = darkhastFaktorTakhfifModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.takhfif_forosh_customlist, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.lblDesc.setText(darkhastFaktorTakhfifModels.get(position).getSharhTakhfif());
        holder.lblCost.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.costRial), formatter.format(darkhastFaktorTakhfifModels.get(position).getMablaghTakhfif())));
        holder.lblPercentage.setText(String.format("%1$s : %2$s %3$s", context.getResources().getString(R.string.darsadTakhfif), darkhastFaktorTakhfifModels.get(position).getDarsadTakhfif(), context.getResources().getString(R.string.percentageSign)));
    }

    @Override
    public int getItemCount() {
        return darkhastFaktorTakhfifModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView lblDesc;
        private TextView lblCost;
        private TextView lblPercentage;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblDesc = itemView.findViewById(R.id.lblDecs);
            lblCost = itemView.findViewById(R.id.lblCost);
            lblPercentage = itemView.findViewById(R.id.lblPercentage);

            lblDesc.setTypeface(font);
            lblCost.setTypeface(font);
            lblPercentage.setTypeface(font);
        }
    }


}
