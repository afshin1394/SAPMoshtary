package com.saphamrah.customer.presentation.main.shopping.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.AddressMoshtaryModel;
import com.saphamrah.customer.data.local.RptStatusModel;

import java.util.ArrayList;

public class RptStatusAdapter extends RecyclerView.Adapter<RptStatusAdapter.ViewHolder> {

    private Context context;
    //    private final OnItemClickListener listener;
    private ArrayList<RptStatusModel> models;

    public RptStatusAdapter(Context context, ArrayList<RptStatusModel> models) {
        this.context = context;
//        this.listener = listener;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_status_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resId = 0;

        holder.codeSefareshTV.setText(models.get(position).getCode());
        holder.tarikhSefareshTV.setText(models.get(position).getTarikh());
        holder.mablaqTV.setText(models.get(position).getMablaq());
        holder.noePardakhtTV.setText(models.get(position).getNoePardakht());

        switch (models.get(position).getStatusCode()) {

            case 1:
                holder.seekBar.setProgress(0);
                resId = R.drawable.first_confirm_rpt;
                break;
            case 2:
                holder.seekBar.setProgress(1);
                resId = R.drawable.factor_rpt;
                break;
            case 3:
                holder.seekBar.setProgress(2);
                resId = R.drawable.delivir_rpt;
                break;
            case 4:
                holder.seekBar.setProgress(3);
                resId = R.drawable.final_confirm_rpt;
                break;

        }

        if (models.get(position).isSuccessful())
            holder.materialCardView.setStrokeColor(context.getResources().getColor(R.color.colorGoodStatus));
        else
            holder.materialCardView.setStrokeColor(context.getResources().getColor(R.color.colorBadStatus));

        Glide.with(context)
                .load(resId)
                .into(holder.statusIV);

        holder.seekBar.setOnTouchListener((v, event) -> true);

    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView codeSefareshTV;
        private TextView tarikhSefareshTV;
        private TextView mablaqTV;
        private TextView noePardakhtTV;
        private ImageView statusIV;
        private SeekBar seekBar;
        private MaterialCardView materialCardView;

        public ViewHolder(View view) {
            super(view);
//            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            codeSefareshTV = view.findViewById(R.id.textview_code_sefaresh);
            tarikhSefareshTV = view.findViewById(R.id.textview_tarikh_sabt);
            mablaqTV = view.findViewById(R.id.textview_cost);
            noePardakhtTV = view.findViewById(R.id.textview_noe_pardakht);
            statusIV = view.findViewById(R.id.imageview_status);
            seekBar = view.findViewById(R.id.seekbar);
            materialCardView = view.findViewById(R.id.material_cardview);
        }

//        void bind(final KalaElamMarjoeeModel kalaElamMarjoeeModel , final int position , final OnItemClickListener listener)
//        {
//            layDelete.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    swipe.close(true);
//                    listener.onItemClick(Constants.DELETE(), kalaElamMarjoeeModel , position);
//                }
//            });
//
//            layEditCount.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    swipe.close(true);
//                    listener.onItemClick(Constants.CLEARING(), kalaElamMarjoeeModel , position);
//                }
//            });
//        }

    }


//    public interface OnItemClickListener
//    {
//        void onItemClick(int operation, KalaElamMarjoeeModel kalaElamMarjoeeModel , int position);
//    }


//    @Override
//    public int getSwipeLayoutResourceId(int position) {
//        return R.id.swipe;
//    }

}
