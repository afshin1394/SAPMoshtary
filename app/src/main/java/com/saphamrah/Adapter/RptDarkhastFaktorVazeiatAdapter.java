package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptDarkhastFaktorVazeiatAdapter extends RecyclerView.Adapter<RptDarkhastFaktorVazeiatAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<RptDarkhastFaktorVazeiatPPCModel> models;
    private int lastPosition = -1;

    public RptDarkhastFaktorVazeiatAdapter(Context context, ArrayList<RptDarkhastFaktorVazeiatPPCModel> models)
    {
        this.context = context;
        this.models = models;
        models.add(0 , new RptDarkhastFaktorVazeiatPPCModel());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_darkhast_faktor_vazeiat_table_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (position == 0)
        {
            holder.lblRadif.setText(context.getResources().getString(R.string.radif));
            holder.lblCustomerCode.setText(context.getResources().getString(R.string.customerCode));
            holder.lblCustomerFullname.setText(context.getResources().getString(R.string.customerName));
            holder.lblMablaghFaktor.setText(context.getResources().getString(R.string.mablaghDarkhast));
            holder.lblVazeiatFaktor.setText(context.getResources().getString(R.string.vazeiatFaktor));
            holder.lblElatAdam.setText(context.getResources().getString(R.string.elatAdamFaktor));
            holder.lblRadif.setTextColor(Color.WHITE);
            holder.lblCustomerCode.setTextColor(Color.WHITE);
            holder.lblCustomerFullname.setTextColor(Color.WHITE);
            holder.lblMablaghFaktor.setTextColor(Color.WHITE);
            holder.lblVazeiatFaktor.setTextColor(Color.WHITE);
            holder.lblElatAdam.setTextColor(Color.WHITE);
            holder.lblRadif.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblCustomerCode.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblCustomerFullname.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblVazeiatFaktor.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblElatAdam.setBackgroundResource(R.drawable.table_header_cell_bg);

        }
        else
        {
            holder.lblRadif.setText(String.valueOf(position));
            holder.lblCustomerCode.setText(models.get(position).getCodeMoshtary());
            holder.lblCustomerFullname.setText(models.get(position).getNameMoshtary());
            holder.lblMablaghFaktor.setText(formatter.format((int)models.get(position).getMablaghNahaeeFaktor()));
            holder.lblVazeiatFaktor.setText(String.valueOf(models.get(position).getTxtCodeVazeiat()));
            holder.lblElatAdam.setText(String.valueOf(models.get(position).getTxtCodeNoeVorod()));

            holder.lblRadif.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblCustomerCode.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblCustomerFullname.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblMablaghFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblVazeiatFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblElatAdam.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));

            if (models.get(position).getCodeNoeVorod() == 1)
            {
                holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblCustomerCode.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblCustomerFullname.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblVazeiatFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblElatAdam.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
            }
            else if (models.get(position).getCodeNoeVorod() == 2)
            {
                holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblCustomerCode.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblCustomerFullname.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblVazeiatFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblElatAdam.setBackgroundResource(R.drawable.table_content_cell_bg_green);
            }
            else
            {
                holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblCustomerCode.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblCustomerFullname.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblVazeiatFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblElatAdam.setBackgroundResource(R.drawable.table_content_cell_bg_red);
            }
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
        LinearLayout layRoot;
        TextView lblRadif;
        TextView lblCustomerCode;
        TextView lblCustomerFullname;
        TextView lblMablaghFaktor;
        TextView lblVazeiatFaktor;
        TextView lblElatAdam;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = itemView.findViewById(R.id.layRoot);
            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
            lblCustomerFullname = itemView.findViewById(R.id.lblCustomerName);
            lblMablaghFaktor = itemView.findViewById(R.id.lblMablaghFaktor);
            lblVazeiatFaktor = itemView.findViewById(R.id.lblVazeiat);
            lblElatAdam = itemView.findViewById(R.id.lblElatAdam);

            lblRadif.setTypeface(font);
            lblCustomerCode.setTypeface(font);
            lblCustomerFullname.setTypeface(font);
            lblMablaghFaktor.setTypeface(font);
            lblVazeiatFaktor.setTypeface(font);
            lblElatAdam.setTypeface(font);
        }

    }


}
