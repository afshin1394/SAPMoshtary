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

import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RptCheckBargashtyAdapter extends RecyclerView.Adapter<RptCheckBargashtyAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<BargashtyModel> models;
    private int lastPosition = -1;

    public RptCheckBargashtyAdapter(Context context, ArrayList<BargashtyModel> models)
    {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_check_bargashty_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        if (models.get(position).getNameMoshtary().equals(""))// this row is sum
        {
            holder.lblCustomerCode.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblCustomerName.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblShomareCheck.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblMablaghCheck.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblMandehCheck.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblSarResidCheck.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
        }
        else
        {
            holder.lblCustomerCode.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.lblShomareCheck.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.lblMablaghCheck.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.lblMandehCheck.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.lblSarResidCheck.setBackgroundResource(R.drawable.table_content_cell_bg);
        }

        holder.lblCustomerCode.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblCustomerName.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblShomareCheck.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblMablaghCheck.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblMandehCheck.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblSarResidCheck.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));

        String sarResidCheck = models.get(position).getTarikhSanad();
        if (sarResidCheck != null && sarResidCheck.length() > 0)
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                Date dateSanad = sdf.parse(models.get(position).getTarikhSanad());
                sarResidCheck = new PubFunc().new DateUtils().gregorianToPersianDateTime(dateSanad);
                sarResidCheck = sarResidCheck.substring(0 , sarResidCheck.indexOf('-'));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                sarResidCheck = models.get(position).getTarikhSanad().substring(0 , models.get(position).getTarikhSanad().indexOf('T'));
            }
        }
        holder.lblCustomerCode.setText(String.valueOf(models.get(position).getCodeMoshtary()));
        holder.lblCustomerName.setText(models.get(position).getNameMoshtary());
        holder.lblShomareCheck.setText(models.get(position).getShomarehSanad());
        holder.lblMablaghCheck.setText(formatter.format(models.get(position).getMablagh()));
        holder.lblMandehCheck.setText(formatter.format(models.get(position).getMablaghMandeh()));
        holder.lblSarResidCheck.setText(sarResidCheck);
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
        TextView lblCustomerCode;
        TextView lblCustomerName;
        TextView lblShomareCheck;
        TextView lblMablaghCheck;
        TextView lblMandehCheck;
        TextView lblSarResidCheck;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = itemView.findViewById(R.id.layRoot);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblShomareCheck = itemView.findViewById(R.id.lblShomareCheck);
            lblMablaghCheck = itemView.findViewById(R.id.lblMablaghCheck);
            lblMandehCheck = itemView.findViewById(R.id.lblMandehCheck);
            lblSarResidCheck = itemView.findViewById(R.id.lblSarResidCheck);


            lblCustomerCode.setTypeface(font);
            lblCustomerName.setTypeface(font);
            lblShomareCheck.setTypeface(font);
            lblMablaghCheck.setTypeface(font);
            lblMandehCheck.setTypeface(font);
            lblSarResidCheck.setTypeface(font);
        }

    }


}
