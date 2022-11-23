package com.saphamrah.customer.presentation.profile.saleInfo.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.AddressMoshtaryModel;
import com.saphamrah.customer.data.local.SaleInfoModel;

import java.util.ArrayList;

public class SaleInfoAdapter extends RecyclerView.Adapter<SaleInfoAdapter.ViewHolder>
{

    private Context context;
//    private final OnItemClickListener listener;
    private ArrayList<SaleInfoModel> models;

    public SaleInfoAdapter(Context context, ArrayList<SaleInfoModel> models )
    {
        this.context = context;
//        this.listener = listener;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_info_item , parent , false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.noeSenfTv.setText(models.get(position).getNoeSenf());
        holder.noeFaaliatTv.setText(models.get(position).getNoeFaaliat());
        holder.darajeMoshtaryTv.setText(models.get(position).getDarajeMoshtary());
        holder.noeVosolTv.setText(models.get(position).getNoeVosol());
        holder.modateVosolTv.setText(String.valueOf(models.get(position).getModateVosol()));
        holder.sazmanForoshTv.setText(models.get(position).getSazmanForosh());
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView noeSenfTv;
        private TextView noeFaaliatTv;
        private TextView darajeMoshtaryTv;
        private TextView noeVosolTv;
        private TextView modateVosolTv;
        private TextView sazmanForoshTv;

        public ViewHolder(View view)
        {
            super(view);
//            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            noeSenfTv = view.findViewById(R.id.noe_senf_tv);
            noeFaaliatTv = view.findViewById(R.id.noe_faaliat_tv);
            darajeMoshtaryTv  = view.findViewById(R.id.daraje_moshtary_tv);
            noeVosolTv = view.findViewById(R.id.noe_vosol_tv);
            modateVosolTv = view.findViewById(R.id.textview_modate_vosol);
            sazmanForoshTv = view.findViewById(R.id.textview_sazmanforosh);
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
