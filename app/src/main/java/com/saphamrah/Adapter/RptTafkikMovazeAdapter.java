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

import com.saphamrah.Model.TafkikKalaMovazeDataModel;
import com.saphamrah.R;

import java.util.ArrayList;

public class RptTafkikMovazeAdapter extends RecyclerView.Adapter<RptTafkikMovazeAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<TafkikKalaMovazeDataModel>  models;
    private int lastPosition = -1;
    private boolean isSat;

    public RptTafkikMovazeAdapter(Context context, ArrayList<TafkikKalaMovazeDataModel>  models)
    {
        this.context = context;
        this.models = models;

        isSat = false;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_tafkik_movaze_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {

        holder.lblNameKala.setText(models.get(position).getNameKala());
        holder.lblkalaCode.setText(models.get(position).getCodeKala());
        holder.lblTedadDarKarton.setText(models.get(position).getTedadDarKarton() + "");
        holder.lblTedadDarBaste.setText(models.get(position).getTedadDarBasteh() + "");
        holder.lblTedadDarAdad.setText(models.get(position).getAdad() + "");
        holder.lblVazn.setText((int)(1000 * models.get(position).getVaznNaKhales()) /1000.0  + "");
        holder.lblHahjm.setText((int)(1000 * models.get(position).getHajm()) /1000.0 + "");

    }

    @Override
    public int getItemCount()
    {
        return models.size()-1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layRoot;
        TextView lblkalaCode;
        TextView lblNameKala;
        TextView lblTedadDarKarton;
        TextView lblTedadDarBaste;
        TextView lblTedadDarAdad;
        TextView lblVazn;
        TextView lblHahjm;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = itemView.findViewById(R.id.layRoot);
            lblNameKala = itemView.findViewById(R.id.lblNameKala);
            lblkalaCode  = itemView.findViewById(R.id.lblKalaCode);
            lblTedadDarKarton  = itemView.findViewById(R.id.lblTedadDarCarton);
            lblTedadDarBaste  = itemView.findViewById(R.id.lblTedadDarBaste);
            lblTedadDarAdad  = itemView.findViewById(R.id.lblTedadDarAdad);
            lblVazn = itemView.findViewById(R.id.lblVazn);
            lblHahjm = itemView.findViewById(R.id.lblHajm);

             lblNameKala.setTypeface(font);
             lblkalaCode.setTypeface(font);
             lblTedadDarKarton.setTypeface(font);
             lblTedadDarBaste.setTypeface(font);
             lblTedadDarAdad.setTypeface(font);
             lblVazn.setTypeface(font);
             lblHahjm.setTypeface(font);
        }

    }


}
