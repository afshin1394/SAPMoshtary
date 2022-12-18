package com.saphamrah.customer.presentation.main.shopping.adapter;


import static com.saphamrah.customer.utils.Constants.ADVERTISEMENT;
import static com.saphamrah.customer.utils.Constants.*;
import static com.saphamrah.customer.utils.Constants.SELL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.RptStatusModel;
import com.saphamrah.customer.data.local.temp.FilterSortModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;

import java.util.ArrayList;
import java.util.List;

public class RptStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<RptStatusModel> models;
    private AdapterItemListener<RptStatusModel> listener;

    public RptStatusAdapter(Context context, ArrayList<RptStatusModel> models, AdapterItemListener<RptStatusModel> listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case SUCCESSFUL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_status_item, parent, false);
                return new ViewHolderSuccessful(view);

            case FAILED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_status_failed_item, parent, false);
                return new ViewHolderFailed(view);

        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case SUCCESSFUL:
                ((ViewHolderSuccessful) holder).bind(position);
                break;

            case FAILED:
                ((ViewHolderFailed) holder).bind(position);
                break;

        }
    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    @Override
    public int getItemViewType(int position) {

        if (models.get(position).isSuccessful()) {
            return SUCCESSFUL;
        } else {
            return FAILED;
        }

    }

    public class ViewHolderSuccessful extends RecyclerView.ViewHolder {
        private TextView codeSefareshTV;
        private TextView tarikhSefareshTV;
        private TextView mablaqTV;
        private TextView noePardakhtTV;
        private ImageView statusIV;
        private SeekBar seekBar;
        private Button laqveDarkhastBtn;

        public ViewHolderSuccessful(View view) {
            super(view);
//            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            codeSefareshTV = view.findViewById(R.id.textview_code_sefaresh);
            tarikhSefareshTV = view.findViewById(R.id.textview_tarikh_sabt);
            mablaqTV = view.findViewById(R.id.textview_cost);
            noePardakhtTV = view.findViewById(R.id.textview_noe_pardakht);
            statusIV = view.findViewById(R.id.imageview_status);
            seekBar = view.findViewById(R.id.seekbar);
            laqveDarkhastBtn = view.findViewById(R.id.btn_laqve_darkhast);
        }


        public void bind(int position){
            int resId = 0;

            codeSefareshTV.setText(models.get(position).getCode());
            tarikhSefareshTV.setText(models.get(position).getTarikh());
            mablaqTV.setText(models.get(position).getMablaq());
            noePardakhtTV.setText(models.get(position).getNoePardakht());

            switch (models.get(position).getStatusCode()) {

                case 1:
                    seekBar.setProgress(0);
                    laqveDarkhastBtn.setVisibility(View.VISIBLE);
                    resId = R.drawable.first_confirm_rpt;
                    break;
                case 2:
                    seekBar.setProgress(1);
                    laqveDarkhastBtn.setVisibility(View.GONE);
                    resId = R.drawable.factor_rpt;
                    break;
                case 3:
                    seekBar.setProgress(2);
                    laqveDarkhastBtn.setVisibility(View.GONE);
                    resId = R.drawable.delivir_rpt;
                    break;
                case 4:
                    seekBar.setProgress(3);
                    laqveDarkhastBtn.setVisibility(View.GONE);
                    resId = R.drawable.final_confirm_rpt;
                    break;

            }

            Glide.with(context)
                    .load(resId)
                    .into(statusIV);

            seekBar.setOnTouchListener((v, event) -> true);
            laqveDarkhastBtn.setOnClickListener(view -> {
                listener.onItemSelect(models.get(position),position, AdapterAction.REMOVE);
            });

        }
    }

    public class ViewHolderFailed extends RecyclerView.ViewHolder {
        private TextView codeSefareshTV;
        private TextView tarikhSefareshTV;
        private TextView mablaqTV;
        private TextView noePardakhtTV;
        private TextView elateAdamDarkhastTV;
        private SeekBar seekBar;
        private MaterialCardView materialCardView;

        public ViewHolderFailed(View view) {
            super(view);
//            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            codeSefareshTV = view.findViewById(R.id.textview_code_sefaresh);
            tarikhSefareshTV = view.findViewById(R.id.textview_tarikh_sabt);
            mablaqTV = view.findViewById(R.id.textview_cost);
            noePardakhtTV = view.findViewById(R.id.textview_noe_pardakht);
            elateAdamDarkhastTV = view.findViewById(R.id.textview_elate_adamdarkhast);
            seekBar = view.findViewById(R.id.seekbar);
            materialCardView = view.findViewById(R.id.material_cardview);
        }

        public void bind(int position){

            codeSefareshTV.setText(models.get(position).getCode());
            tarikhSefareshTV.setText(models.get(position).getTarikh());
            mablaqTV.setText(models.get(position).getMablaq());
            noePardakhtTV.setText(models.get(position).getNoePardakht());
            elateAdamDarkhastTV.setText(models.get(position).getElateAdamDarkhast());

            switch (models.get(position).getStatusCode()){
                case FAILED_FROM_SERVER:
                    materialCardView.setStrokeColor(context.getResources().getColor(R.color.colorBadStatus));
                    break;
                case FAILED_BY_USER:
                    materialCardView.setStrokeColor(context.getResources().getColor(R.color.colorOrange400));
                    break;
            }

            seekBar.setOnTouchListener((v, event) -> true);

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataChanged(ArrayList<RptStatusModel> models, AdapterItemListener<RptStatusModel> listener){
        this.models = models;
        this.listener = listener;
        notifyDataSetChanged();
    }
}
