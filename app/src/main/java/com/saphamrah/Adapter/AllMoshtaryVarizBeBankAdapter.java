package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.R;
import com.saphamrah.Model.VarizBeBankModel;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class AllMoshtaryVarizBeBankAdapter extends RecyclerView.Adapter<AllMoshtaryVarizBeBankAdapter.MyViewHolder> {

    private final OnItemClickListener listener;
    private ArrayList<VarizBeBankModel> models;
    private boolean showSwipe;
    private Context context;
    private long mablaghKhales;
    private ArrayList<VarizBeBankModel> modelsList = new ArrayList<>();

    public AllMoshtaryVarizBeBankAdapter(Context context, ArrayList<VarizBeBankModel> models, OnItemClickListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
        //
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_moshtary_variz_be_bank_customlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");


        holder.lblMoshtaryDetails.setText(String.format("%1$s - %2$s", models.get(position).getCcMoshtary(), models.get(position).getNamemoshtary()));
        holder.lblMablaghKhales.setText(String.format("%1$s : %2$s", BaseApplication.getContext().getResources().getString(R.string.mablaghKhales), formatter.format(models.get(position).getMablagh())));
        holder.lblNoeVosol.setText(String.format("%1$s : %2$s", BaseApplication.getContext().getResources().getString(R.string.noeVosol), models.get(position).getTxtCodeNoeVosol()));


         // set selected recycler item
        if (models.get(position).getIsSelectedRecycler() == 1) {
            holder.layRoot.setBackground(context.getResources().getDrawable(R.drawable.radius_layout_selected_green));
        } else {
            holder.layRoot.setBackground(context.getResources().getDrawable(R.drawable.radius_layout_unselected_white));
        }



        // set selected item
        if (models.get(position).getExtraProp_IsSelected() == 1) {
            Glide.with(context)
                    .load(R.drawable.ic_success)
                    .placeholder(R.drawable.nopic_whit)
                    .into(holder.img_is_selected);
            holder.lblMablaghSabtShode.setText(String.format("%1$s : %2$s", BaseApplication.getContext().getResources().getString(R.string.mablaghSabtShode), formatter.format(models.get(position).getExtraProp_MablaghSabtShode())));
        } else if (models.get(position).getExtraProp_IsSelected() == 0) {
            Glide.with(context)
                    .load(R.drawable.ic_error)
                    .placeholder(R.drawable.nopic_whit)
                    .into(holder.img_is_selected);
            holder.lblMablaghSabtShode.setText(String.format("%1$s : %2$s", BaseApplication.getContext().getResources().getString(R.string.mablaghSabtShode), 0));
        }
        holder.bind(models.get(position).getMablagh(), position, listener);
    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView lblMoshtaryDetails;
        private TextView lblMablaghKhales;
        private TextView lblNoeVosol;
        private TextView lblMablaghSabtShode;
        private CardView crdviewRoot;
        private ConstraintLayout layRoot;
        private ImageView img_is_selected;


        private MyViewHolder(View view) {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            lblMoshtaryDetails = view.findViewById(R.id.lblMoshtaryDetails);
            lblMablaghKhales = view.findViewById(R.id.lblMablaghKhales);
            lblNoeVosol = view.findViewById(R.id.lblNoeVosol);
            lblMablaghSabtShode = view.findViewById(R.id.lblMablaghSabtShode);
            crdviewRoot = view.findViewById(R.id.crdviewRoot);
            layRoot = view.findViewById(R.id.layRoot);
            img_is_selected = view.findViewById(R.id.img_is_selected);

            lblMoshtaryDetails.setTypeface(font);
            lblMablaghKhales.setTypeface(font);
            lblNoeVosol.setTypeface(font);
            lblMablaghSabtShode.setTypeface(font);
        }

        public void bind(double mablagh, int position, final OnItemClickListener listener) {

            if (models.get(position).getExtraProp_IsSelected() == 0){
                crdviewRoot.setOnClickListener(v -> {
                    if (models.get(position).getIsSelectedRecycler() == 1) {
                        mablaghKhales -= mablagh;
                        models.get(position).setIsSelectedRecycler(0);
                        modelsList.remove(models.get(position));
                    } else {
                        mablaghKhales += mablagh;
                        models.get(position).setIsSelectedRecycler(1);
                        modelsList.add(models.get(position));
                    }
                    notifyItemChanged(position);
                    listener.onItemClick(mablaghKhales, position, modelsList);
                });
            }

        }

    }


    public interface OnItemClickListener {
        void onItemClick(double mablagh, int position, ArrayList<VarizBeBankModel> modelsList);
    }


}
