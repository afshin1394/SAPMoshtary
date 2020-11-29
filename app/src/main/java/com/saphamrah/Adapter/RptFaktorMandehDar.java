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
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RptFaktorMandehDar extends RecyclerView.Adapter<RptFaktorMandehDar.ViewHolder>
{

    private Context context;
    private ArrayList<RptMandehdarModel> models;
    private int lastPosition = -1;

    public RptFaktorMandehDar(Context context, ArrayList<RptMandehdarModel> models)
    {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_faktor_mandehdar_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (models.get(position).getNameMoshtary().equals(""))//if name moshtary == -1 -> this row is sum
        {
            holder.lblRadif.setText("");
            holder.lblRadif.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblCustomerCode.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblCustomerName.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblTarikhFaktor.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblTarikhErsal.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblShomareFaktor.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblMablaghFaktor.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblMandehFaktor.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
        }
        else
        {
            holder.lblRadif.setText(String.valueOf(position+1));
            holder.lblRadif.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            holder.lblCustomerCode.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            holder.lblCustomerName.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            holder.lblTarikhFaktor.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            holder.lblTarikhErsal.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            holder.lblShomareFaktor.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            holder.lblMablaghFaktor.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            holder.lblMandehFaktor.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
        }

        holder.lblRadif.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblCustomerCode.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblCustomerName.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblTarikhFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblTarikhErsal.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblShomareFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblMablaghFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblMandehFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));

        String tarikhFaktor = models.get(position).getTarikhFaktor();
        String tarikhErsal = models.get(position).getTarikhErsal();
        if (tarikhFaktor != null && tarikhFaktor.length() > 0 && tarikhErsal != null && tarikhErsal.length() > 0)
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                Date dateFaktor = sdf.parse(models.get(position).getTarikhFaktor());
                Date dateErsal = sdf.parse(models.get(position).getTarikhErsal());
                tarikhFaktor = new PubFunc().new DateUtils().gregorianToPersianDateTime(dateFaktor);
                tarikhErsal = new PubFunc().new DateUtils().gregorianToPersianDateTime(dateErsal);
                tarikhFaktor = tarikhFaktor.substring(0 , tarikhFaktor.indexOf('-'));
                tarikhErsal = tarikhErsal.substring(0 , tarikhErsal.indexOf('-'));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                tarikhFaktor = models.get(position).getTarikhFaktor().substring(0 , models.get(position).getTarikhFaktor().indexOf('T'));
                tarikhErsal = models.get(position).getTarikhErsal().substring(0 , models.get(position).getTarikhErsal().indexOf('T'));
            }
        }

        holder.lblCustomerCode.setText(String.valueOf(models.get(position).getCodeMoshtary()));
        holder.lblCustomerName.setText(models.get(position).getNameMoshtary());
        holder.lblTarikhFaktor.setText(tarikhFaktor);
        holder.lblTarikhErsal.setText(tarikhErsal);
        holder.lblShomareFaktor.setText(String.valueOf(models.get(position).getShomarehFaktor()));
        holder.lblMablaghFaktor.setText(formatter.format((int)models.get(position).getMablaghKhalesFaktor()));
        holder.lblMandehFaktor.setText(formatter.format((int)models.get(position).getMablaghMandehMoshtary()));

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
        TextView lblCustomerName;
        TextView lblTarikhFaktor;
        TextView lblTarikhErsal;
        TextView lblShomareFaktor;
        TextView lblMablaghFaktor;
        TextView lblMandehFaktor;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = itemView.findViewById(R.id.layRoot);
            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblTarikhFaktor = itemView.findViewById(R.id.lblTarikhFaktor);
            lblTarikhErsal = itemView.findViewById(R.id.lblTarikhErsal);
            lblShomareFaktor = itemView.findViewById(R.id.lblShomareFaktor);
            lblMablaghFaktor = itemView.findViewById(R.id.lblMablaghFaktor);
            lblMandehFaktor = itemView.findViewById(R.id.lblMandehFaktor);

            lblRadif.setTypeface(font);
            lblCustomerCode.setTypeface(font);
            lblCustomerName.setTypeface(font);
            lblMablaghFaktor.setTypeface(font);
            lblTarikhFaktor.setTypeface(font);
            lblTarikhErsal.setTypeface(font);
            lblShomareFaktor.setTypeface(font);
            lblMablaghFaktor.setTypeface(font);
            lblMandehFaktor.setTypeface(font);
        }

    }

}
