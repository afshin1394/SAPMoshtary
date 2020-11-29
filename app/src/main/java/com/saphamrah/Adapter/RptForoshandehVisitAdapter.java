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

import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptForoshandehVisitAdapter extends RecyclerView.Adapter<RptForoshandehVisitAdapter.ViewHolder>
{

    DecimalFormat formatter;
    private Context context;
    private ArrayList<RptVisitForoshandehMoshtaryModel> models;
    private int lastPosition = -1;

    public RptForoshandehVisitAdapter(Context context, ArrayList<RptVisitForoshandehMoshtaryModel> models)
    {
        this.context = context;
        this.models = models;
        formatter = new DecimalFormat("#,###,###");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_foroshandeh_visit_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        holder.lblOlaviat.setText(String.valueOf(models.get(position).getOlaviat()));
        holder.lblCustomerCode.setText(models.get(position).getCodeMoshtary());
        holder.lblCustomerName.setText(models.get(position).getNameMoshtary());
        holder.lblRialKharid.setText(formatter.format(models.get(position).getRialKharid()));
        holder.lblSaateVorod.setText(models.get(position).getSaatVorodBeMaghazeh());
        holder.lblSaateKhoroj.setText(models.get(position).getSaatKhorojAzMaghazeh());
        holder.lblZamanDarMaghaze.setText(models.get(position).getZamanDarMaghazeh());
        holder.lblVazeiatMorajeh.setText(models.get(position).getVazeiatMorajeh());
        holder.lblDalilDarkhastManfi.setText(models.get(position).getDalilDarkhastManfi());
        holder.lblTedadAghlam.setText(String.valueOf(models.get(position).getTedad_AghlamFaktor()));

        if (models.get(position).getIsMorajeh() == 1)
        {
            setColorForDarkhast(holder);
        }
        else if (models.get(position).getIsMorajeh() == -1)
        {
            setColorForAdamDarkhast(holder);
        }
        else
        {
            setColorForMorajehNashode(holder);
        }

        setAnimation(holder.itemView, position);
    }


    private void setColorForDarkhast(ViewHolder holder)
    {
        holder.lblOlaviat.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblCustomerCode.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblRialKharid.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblSaateVorod.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblSaateKhoroj.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblZamanDarMaghaze.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblVazeiatMorajeh.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblDalilDarkhastManfi.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblTedadAghlam.setBackgroundResource(R.drawable.table_content_cell_bg_green);
    }


    private void setColorForAdamDarkhast(ViewHolder holder)
    {
        holder.lblOlaviat.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblCustomerCode.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblRialKharid.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblSaateVorod.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblSaateKhoroj.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblZamanDarMaghaze.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblVazeiatMorajeh.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblDalilDarkhastManfi.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblTedadAghlam.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
    }


    private void setColorForMorajehNashode(ViewHolder holder)
    {
        holder.lblOlaviat.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblCustomerCode.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblRialKharid.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblSaateVorod.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblSaateKhoroj.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblZamanDarMaghaze.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblVazeiatMorajeh.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblDalilDarkhastManfi.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblTedadAghlam.setBackgroundResource(R.drawable.table_content_cell_bg_red);
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


    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView lblOlaviat;
        TextView lblCustomerCode;
        TextView lblCustomerName;
        TextView lblRialKharid;
        TextView lblSaateVorod;
        TextView lblSaateKhoroj;
        TextView lblZamanDarMaghaze;
        TextView lblVazeiatMorajeh;
        TextView lblDalilDarkhastManfi;
        TextView lblTedadAghlam;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblOlaviat = itemView.findViewById(R.id.lblOlaviat);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblRialKharid = itemView.findViewById(R.id.lblRialKharid);
            lblSaateVorod = itemView.findViewById(R.id.lblSaateVorod);
            lblSaateKhoroj = itemView.findViewById(R.id.lblSaateKhoroj);
            lblZamanDarMaghaze = itemView.findViewById(R.id.lblZamanDarMaghaze);
            lblVazeiatMorajeh = itemView.findViewById(R.id.lblVazeiatMorajee);
            lblDalilDarkhastManfi = itemView.findViewById(R.id.lblDalilDarkhastManfi);
            lblTedadAghlam = itemView.findViewById(R.id.lblTedadAghlam);

            lblOlaviat.setTypeface(font);
            lblCustomerCode.setTypeface(font);
            lblCustomerName.setTypeface(font);
            lblRialKharid.setTypeface(font);
            lblSaateVorod.setTypeface(font);
            lblSaateKhoroj.setTypeface(font);
            lblZamanDarMaghaze.setTypeface(font);
            lblVazeiatMorajeh.setTypeface(font);
            lblDalilDarkhastManfi.setTypeface(font);
            lblTedadAghlam.setTypeface(font);
        }

    }


}
