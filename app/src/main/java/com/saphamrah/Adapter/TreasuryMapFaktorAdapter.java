package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TreasuryMapFaktorAdapter extends RecyclerView.Adapter<TreasuryMapFaktorAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels;


    public TreasuryMapFaktorAdapter(Context context, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels)
    {
        this.context = context;
        this.darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.treasury_map_faktor_customlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.lblMablaghKhales.setText(String.format("%1$s : \n %2$s %3$s", context.getString(R.string.mablaghKhales), formatter.format(darkhastFaktorMoshtaryForoshandeModels.get(position).getMablaghKhalesFaktor()), context.getString(R.string.rial)));
        holder.lblMablaghMandeh.setText(String.format("%1$s : \n %2$s %3$s", context.getString(R.string.remain), formatter.format(darkhastFaktorMoshtaryForoshandeModels.get(position).getMablaghMandeh()), context.getString(R.string.rial)));
        holder.lblShomarehDarkhast.setText(String.format("%1$s : %2$s", context.getString(R.string.shomareDarkhast), darkhastFaktorMoshtaryForoshandeModels.get(position).getShomarehDarkhast()));
        holder.lblNameNodeVosol.setText(darkhastFaktorMoshtaryForoshandeModels.get(position).getNameNoeVosolAzMoshtary());
    }

    @Override
    public int getItemCount()
    {
        return darkhastFaktorMoshtaryForoshandeModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView lblMablaghKhales;
        private TextView lblMablaghMandeh;
        private TextView lblShomarehDarkhast;
        private TextView lblNameNodeVosol;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblMablaghKhales = itemView.findViewById(R.id.lblMablaghKhalesFaktor);
            lblMablaghMandeh = itemView.findViewById(R.id.lblMablaghMandehFaktor);
            lblShomarehDarkhast = itemView.findViewById(R.id.lblShomarehDarkhast);
            lblNameNodeVosol = itemView.findViewById(R.id.lblNameNoeVosol);

            lblMablaghKhales.setTypeface(font);
            lblMablaghMandeh.setTypeface(font);
            lblShomarehDarkhast.setTypeface(font);
            lblNameNodeVosol.setTypeface(font);
        }
    }


}
