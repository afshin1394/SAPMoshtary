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

import com.saphamrah.Model.RptKharidKalaModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptKharidKalaAdapter extends RecyclerView.Adapter<RptKharidKalaAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<RptKharidKalaModel> models;
    private int lastPosition = -1;

    public RptKharidKalaAdapter(Context context, ArrayList<RptKharidKalaModel> models)
    {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_kharid_kala_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        holder.lblCodeKala.setText(models.get(position).getCodeKala());
        holder.lblNameKala.setText(models.get(position).getNameKala());
        holder.lblCartonCount.setText(String.valueOf(models.get(position).getKarton()));
        holder.lblBastehCount.setText(String.valueOf(models.get(position).getBasteh()));
        holder.lblAdadCount.setText(String.valueOf(models.get(position).getAdad()));

        if (models.get(position).getKarton() > 1)
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
        }
        else if (models.get(position).getBasteh() > 1)
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorMediumStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorMediumStatus));
        }
        else
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
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
        LinearLayout layStatusRight;
        LinearLayout layStatusLeft;
        TextView lblCodeKala;
        TextView lblNameKala;
        TextView lblCartonCount;
        TextView lblBastehCount;
        TextView lblAdadCount;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layStatusRight = itemView.findViewById(R.id.layStatusRight);
            layStatusLeft = itemView.findViewById(R.id.layStatusLeft);
            lblCodeKala = itemView.findViewById(R.id.lblCodeKala);
            lblNameKala = itemView.findViewById(R.id.lblNameKala);
            lblCartonCount = itemView.findViewById(R.id.lblCarton);
            lblBastehCount = itemView.findViewById(R.id.lblBasteh);
            lblAdadCount = itemView.findViewById(R.id.lblAdad);

            lblCodeKala.setTypeface(font);
            lblNameKala.setTypeface(font);
            lblCartonCount.setTypeface(font);
            lblBastehCount.setTypeface(font);
            lblAdadCount.setTypeface(font);
        }

    }

}
