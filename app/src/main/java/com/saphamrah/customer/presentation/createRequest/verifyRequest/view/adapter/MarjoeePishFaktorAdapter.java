/*
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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MarjoeePishFaktorAdapter extends RecyclerView.Adapter<MarjoeePishFaktorAdapter.ViewHolder> {

    private Context context;
    private ArrayList<KalaElamMarjoeeModel> models;
    private int noePrintFaktor;

    public MarjoeePishFaktorAdapter(Context context, ArrayList<KalaElamMarjoeeModel> models, int noePrintFaktor)
    {
        this.context = context;
        this.models = models;
        this.noePrintFaktor = noePrintFaktor;
    }


    @NonNull
    @Override
    public MarjoeePishFaktorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {


        View view = LayoutInflater.from(context).inflate(R.layout.marjoee_pishfaktor_customlist, parent , false);

        return new MarjoeePishFaktorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarjoeePishFaktorAdapter.ViewHolder holder, int position)
    {
        holder.lblNameKala.setText(models.get(position).getNameKala());
        holder.lblShomarehBach.setText(models.get(position).getShomarehBach());
        holder.lblTedad.setText(String.valueOf(models.get(position).getTedad3()));
        holder.lblFee.setText(new DecimalFormat("#,###,###").format(models.get(position).getFee()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView lblNameKala;
        private TextView lblShomarehBach;
        private TextView lblTedad;
        private TextView lblFee;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblNameKala = itemView.findViewById(R.id.lblNameKala);
            lblShomarehBach = itemView.findViewById(R.id.lblShomarehBach);
            lblTedad = itemView.findViewById(R.id.lblTedad);
            lblFee = itemView.findViewById(R.id.lblFee);

            lblNameKala.setTypeface(font);
            lblShomarehBach.setTypeface(font);
            lblTedad.setTypeface(font);
            lblFee.setTypeface(font);
        }
    }

}
*/
