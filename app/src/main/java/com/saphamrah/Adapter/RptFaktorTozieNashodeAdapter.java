package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.RptFaktorTozieNashodehModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptFaktorTozieNashodeAdapter extends RecyclerView.Adapter<RptFaktorTozieNashodeAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<RptFaktorTozieNashodehModel> models;
    private int lastPosition = -1;

    public RptFaktorTozieNashodeAdapter(Context context, ArrayList<RptFaktorTozieNashodehModel> models)
    {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_faktor_tozie_nashode_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.lblRadif.setText(String.valueOf(position+1));
        holder.lblCustomerCode.setText(models.get(position).getCodeMoshtary());
        holder.lblCustomerName.setText(models.get(position).getNameMoshtary());
        holder.lblTarikhFaktor.setText(models.get(position).getTarikhFaktorWithSlash());
        holder.lblShomarehFaktor.setText(String.valueOf(models.get(position).getShomarehFaktor()));
        holder.lblMablaghFaktor.setText(formatter.format((int)models.get(position).getRoundMablaghKhalesFaktor()));
        holder.lblStatus.setText(models.get(position).getTxtCodeVazeiat());
        holder.lblTarikhErsal.setText(models.get(position).getTarikhErsal());
        holder.lblMamorTozie.setText(models.get(position).getNameMamorPakhsh());

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount()
    {
        return models.size();
    }


    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView lblRadif;
        TextView lblCustomerCode;
        TextView lblCustomerName;
        TextView lblTarikhFaktor;
        TextView lblShomarehFaktor;
        TextView lblMablaghFaktor;
        TextView lblStatus;
        TextView lblTarikhErsal;
        TextView lblMamorTozie;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblTarikhFaktor = itemView.findViewById(R.id.lblTarikhFaktor);
            lblShomarehFaktor = itemView.findViewById(R.id.lblShomareFaktor);
            lblMablaghFaktor = itemView.findViewById(R.id.lblMablaghFaktor);
            lblStatus = itemView.findViewById(R.id.lblVazeiat);
            lblTarikhErsal = itemView.findViewById(R.id.lblTarikhErsal);
            lblMamorTozie = itemView.findViewById(R.id.lblMamorTozie);

            lblRadif.setTypeface(font);
            lblCustomerCode.setTypeface(font);
            lblCustomerName.setTypeface(font);
            lblTarikhFaktor.setTypeface(font);
            lblShomarehFaktor.setTypeface(font);
            lblMablaghFaktor.setTypeface(font);
            lblStatus.setTypeface(font);
            lblTarikhErsal.setTypeface(font);
            lblMamorTozie.setTypeface(font);
        }

    }

}
