package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RptThreeMonthPurchaseRizFaktorAdapter extends RecyclerView.Adapter<RptThreeMonthPurchaseRizFaktorAdapter.ViewHolder>
{


    private Context context;
    private  ArrayList<Rpt3MonthPurchaseModel> models;
    private int lastPosition = -1;

    public RptThreeMonthPurchaseRizFaktorAdapter(Context context, ArrayList<Rpt3MonthPurchaseModel> models )
    {

        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_three_month_purchaseriz_faktor_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.lblRadif.setText(String.valueOf(position+1));
        holder.lblCustomerCode.setText(models.get(position).getCodeMoshtary());
        holder.lblCustomerName.setText(models.get(position).getNameMoshtary());
        holder.lblMablaghFaktor.setText(formatter.format(models.get(position).getMablaghKhalesFaktor()));
        holder.lblShomarehFaktor.setText(models.get(position).getShomarehFaktor());
        String tarikhFaktor = models.get(position).getTarikhFaktor();
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            Date dateFaktor = sdf.parse(models.get(position).getTarikhFaktor());
            tarikhFaktor = new PubFunc().new DateUtils().gregorianToPersianDateTime(dateFaktor);
            tarikhFaktor = tarikhFaktor.substring(0 , tarikhFaktor.indexOf('-'));

        } catch (Exception e){
            Log.i("", e.toString());
        }
        holder.lblTaakhir.setText(tarikhFaktor);

        if (models.get(position).getMarjoeeKamel() == 1){

            holder.lblTaakhir.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg_red_light));
            holder.lblRadif.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg_red_light));
            holder.lblCustomerCode.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg_red_light));
            holder.lblCustomerName.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg_red_light));
            holder.lblMablaghFaktor.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg_red_light));
            holder.lblShomarehFaktor.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg_red_light));
        } else {
            holder.lblTaakhir.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg));
            holder.lblRadif.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg));
            holder.lblCustomerCode.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg));
            holder.lblCustomerName.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg));;
            holder.lblMablaghFaktor.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg));
            holder.lblShomarehFaktor.setBackground(ContextCompat.getDrawable(BaseApplication.getContext() ,R.drawable.table_content_cell_bg));
        }

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
        TextView lblTaakhir;
        TextView lblRadif;
        TextView lblCustomerCode;
        TextView lblCustomerName;
        TextView lblMablaghFaktor;
        TextView lblShomarehFaktor;
        LinearLayout layRoot;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblTaakhir = itemView.findViewById(R.id.lblTarikhFaktor);
            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblMablaghFaktor = itemView.findViewById(R.id.lblMablaghFaktor);
            lblShomarehFaktor = itemView.findViewById(R.id.lblShomarehFaktor);

            layRoot = itemView.findViewById(R.id.layRoot);

            lblRadif.setTypeface(font);
            lblCustomerCode.setTypeface(font);
            lblCustomerName.setTypeface(font);
            lblMablaghFaktor.setTypeface(font);
            lblShomarehFaktor.setTypeface(font);
            lblTaakhir.setTypeface(font);
        }


    }

    public interface OnItemClickListener
    {
        void onItemClick(int ccMoshtary, int position);
    }

}
