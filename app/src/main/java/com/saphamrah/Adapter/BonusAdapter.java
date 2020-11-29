package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.R;

import java.util.ArrayList;


public class BonusAdapter extends RecyclerView.Adapter<BonusAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels;


    public BonusAdapter(Context context, ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels)
    {
        this.context = context;
        this.darkhastFaktorJayezehModels = darkhastFaktorJayezehModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bonus_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.lblName.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.nameKala), darkhastFaktorJayezehModels.get(position).getSharh()));
        holder.lblCount.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.countBonus), darkhastFaktorJayezehModels.get(position).getTedad()));
    }


    @Override
    public int getItemCount()
    {
        return darkhastFaktorJayezehModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView lblName;
        private TextView lblCount;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblName = view.findViewById(R.id.lblName);
            lblCount = view.findViewById(R.id.lblCount);

            lblName.setTypeface(font);
            lblCount.setTypeface(font);
        }
    }


}
