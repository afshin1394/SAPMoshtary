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

import com.daimajia.swipe.SwipeLayout;
import com.saphamrah.R;
import com.saphamrah.UIModel.RptMoshtaryGharardadUiModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptMoshtaryGharardadAdapter extends RecyclerView.Adapter<RptMoshtaryGharardadAdapter.ViewHolder>
{


    private ArrayList<RptMoshtaryGharardadUiModel> models;
    private Context context;
    private int lastPosition = -1;
    public RptMoshtaryGharardadAdapter(Context context , ArrayList<RptMoshtaryGharardadUiModel> models)
    {
        this.context = context;
        this.models = models;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_moshtary_gharardad_customlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        setAnimation(holder.itemView, position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String mablaghForosh = formatter.format( models.get(position).getMablaghForosh());
        String mablaghMasrafKonandeh = formatter.format( models.get(position).getMablaghMasrafKonandeh());
        holder.lblMablaghForosh.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.mablaghForosh), mablaghForosh));
        holder.lblMablaghMasrafKonandeh.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.mablaghMasrafKonandeh), mablaghMasrafKonandeh));
        holder.lblNameKala.setText(models.get(position).getNameKala());
        holder.lblCodeKala.setText(String.valueOf(models.get(position).getCodeKala()));

        if (models.get(position).getControlMablagh() == 1){
            holder.lblConterolGheymat.setText(context.getResources().getString(R.string.haveConterolGheymat));
        } else {
            holder.lblConterolGheymat.setText(context.getResources().getString(R.string.haveNotConterolGheymat));
        }








    }


    @Override
    public int getItemCount() {
        return models.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private TextView lblNameKala;
        private TextView lblCodeKala;
        private TextView lblMablaghForosh;
        private TextView lblMablaghMasrafKonandeh;
        private TextView lblConterolGheymat;




        private ViewHolder(@NonNull View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            lblNameKala = view.findViewById(R.id.lblNameKala);
            lblCodeKala = view.findViewById(R.id.lblCodeKala);
            lblMablaghMasrafKonandeh = view.findViewById(R.id.lblMablaghMasrafKonandeh);
            lblMablaghForosh = view.findViewById(R.id.lblMablaghForosh);
            lblConterolGheymat = view.findViewById(R.id.lblConterolGheymat);



            lblNameKala.setTypeface(font);
            lblCodeKala.setTypeface(font);
            lblMablaghMasrafKonandeh.setTypeface(font);
            lblMablaghForosh.setTypeface(font);
            lblConterolGheymat.setTypeface(font);

        }



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





}
