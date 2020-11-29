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

import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptThreeMonthPurchaseAdapter extends RecyclerView.Adapter<RptThreeMonthPurchaseAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<Rpt3MonthGetSumModel> models;
    private int lastPosition = -1;

    public RptThreeMonthPurchaseAdapter(Context context, ArrayList<Rpt3MonthGetSumModel> models , OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_three_month_purchase_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.lblRadif.setText(String.valueOf(position+1));
        holder.lblCustomerCode.setText(models.get(position).getCodeMoshtary());
        holder.lblCustomerName.setText(models.get(position).getNameMoshtary());
        holder.lblMablaghFaktor.setText(formatter.format(models.get(position).getSumMablagh()));
        holder.lblTedadFaktor.setText(String.valueOf(models.get(position).getTedad()));
        setAnimation(holder.itemView, position);
        holder.bind( models.get(position).getCcMoshtary(), position );
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
        TextView lblMablaghFaktor;
        TextView lblTedadFaktor;
        LinearLayout layRoot;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblMablaghFaktor = itemView.findViewById(R.id.lblMablaghFaktor);
            lblTedadFaktor = itemView.findViewById(R.id.lblTedadFaktor);
            layRoot = itemView.findViewById(R.id.layRoot);

            lblRadif.setTypeface(font);
            lblCustomerCode.setTypeface(font);
            lblCustomerName.setTypeface(font);
            lblMablaghFaktor.setTypeface(font);
            lblTedadFaktor.setTypeface(font);
        }

        void bind(int ccMoshtary , final int position )
        {
            layRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(ccMoshtary , position);
                }
            });
        }

    }

    public interface OnItemClickListener
    {
        void onItemClick(int ccMoshtary , int position);
    }

}
