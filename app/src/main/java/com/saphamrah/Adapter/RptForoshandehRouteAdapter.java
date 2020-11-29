package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.RptMasirModel;
import com.saphamrah.R;

import java.util.ArrayList;

public class RptForoshandehRouteAdapter extends RecyclerView.Adapter<RptForoshandehRouteAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<RptMasirModel> models;
    private int lastPosition = -1;

    public RptForoshandehRouteAdapter(Context context, ArrayList<RptMasirModel> models)
    {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_foroshandeh_route_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , int position)
    {
        int sum = models.get(position).getKhordeh() + models.get(position).getOmdeh() + models.get(position).getTaavoni() + models.get(position).getZanjireh() + models.get(position).getNemaiandeh();

        holder.lblRadif.setText(String.valueOf(position+1));
        holder.lblNameMasir.setText(models.get(position).getNameMasir());
        holder.lblRoozVisit.setText(models.get(position).getNameRoozVisit());
        holder.lblToorVisit.setText(models.get(position).getNameToorVisit());
        holder.lblKhorde.setText(String.valueOf(models.get(position).getKhordeh()));
        holder.lblOmdeh.setText(String.valueOf(models.get(position).getOmdeh()));
        holder.lblTaavoni.setText(String.valueOf(models.get(position).getTaavoni()));
        holder.lblZanjiree.setText(String.valueOf(models.get(position).getZanjireh()));
        holder.lblNamayandeh.setText(String.valueOf(models.get(position).getNemaiandeh()));
        holder.lblSum.setText(String.valueOf(sum));
        //holder.lblSumBaZaribKhorde.setText(String.valueOf(Math.round(sum/5)));

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
        TextView lblNameMasir;
        TextView lblRoozVisit;
        TextView lblToorVisit;
        TextView lblKhorde;
        TextView lblOmdeh;
        TextView lblTaavoni;
        TextView lblZanjiree;
        TextView lblNamayandeh;
        TextView lblSum;
        //TextView lblSumBaZaribKhorde;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblNameMasir = itemView.findViewById(R.id.lblNameMasir);
            lblRoozVisit = itemView.findViewById(R.id.lblRoozVisit);
            lblToorVisit = itemView.findViewById(R.id.lblToorVisit);
            lblKhorde = itemView.findViewById(R.id.lblKhorde);
            lblOmdeh = itemView.findViewById(R.id.lblOmdeh);
            lblTaavoni = itemView.findViewById(R.id.lblTaavoni);
            lblZanjiree = itemView.findViewById(R.id.lblZanjiree);
            lblNamayandeh = itemView.findViewById(R.id.lblNamayandeh);
            lblSum = itemView.findViewById(R.id.lblSum);
            //lblSumBaZaribKhorde = itemView.findViewById(R.id.lblSumBaZaribKhorde);

            lblRadif.setTypeface(font);
            lblNameMasir.setTypeface(font);
            lblRoozVisit.setTypeface(font);
            lblToorVisit.setTypeface(font);
            lblKhorde.setTypeface(font);
            lblOmdeh.setTypeface(font);
            lblTaavoni.setTypeface(font);
            lblZanjiree.setTypeface(font);
            lblNamayandeh.setTypeface(font);
            lblSum.setTypeface(font);
            //lblSumBaZaribKhorde.setTypeface(font);
        }

    }

}
