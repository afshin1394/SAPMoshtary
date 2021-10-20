package com.saphamrah.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.R;
import com.saphamrah.databinding.RptJashnvarehFirstlevelCustomelistBinding;

import java.util.ArrayList;


public class RptJashnvarehFirstLevelAdapter extends RecyclerView.Adapter<RptJashnvarehFirstLevelAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels;
    private IRptJashnvarehFistLevelAdapter iRptJashnvarehFistLevelAdapter;
    private RptJashnvarehActivity.State state;

    public RptJashnvarehFirstLevelAdapter(Context context, ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels , IRptJashnvarehFistLevelAdapter iRptJashnvarehFistLevelAdapter) {
        this.context = context;
        this.jashnavareForoshModels = jashnavareForoshModels;
        this.iRptJashnvarehFistLevelAdapter = iRptJashnvarehFistLevelAdapter;
    }

    @NonNull
    @Override
    public RptJashnvarehFirstLevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RptJashnvarehFirstlevelCustomelistBinding binding = DataBindingUtil.inflate(inflater, R.layout.rpt_jashnvareh_firstlevel_customelist, parent, false);
        return new RptJashnvarehFirstLevelAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RptJashnvarehFirstLevelAdapter.ViewHolder holder, int position) {
        RptJashnvarehForoshModel rptJashnvarehForoshModel = jashnavareForoshModels.get(position);
        switch (state){
            case Jashnvareh:
                holder.bindJashnvareh(rptJashnvarehForoshModel);
                break;
            case Moshtary:
                holder.bindCustomer(rptJashnvarehForoshModel);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return jashnavareForoshModels.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        RptJashnvarehFirstlevelCustomelistBinding binding;
        public ViewHolder(@NonNull RptJashnvarehFirstlevelCustomelistBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
            binding.lblCustomerFullNameCode.setTypeface(font);
            binding.lblRadif.setTypeface(font);
            binding.getRoot().setOnClickListener(v->
            {
                iRptJashnvarehFistLevelAdapter.onItemClick(state,jashnavareForoshModels.get(getAdapterPosition()) , getAdapterPosition());
            });
        }


        public void bindCustomer(@NonNull RptJashnvarehForoshModel rptJashnvarehForoshModel) {
            binding.lblRadif.setText(String.valueOf(getAdapterPosition()+1));
            binding.lblCustomerFullNameCode.setText(String.format("%1$s" ,  rptJashnvarehForoshModel.getNameMoshtary()));
            binding.IMGName.setImageResource(R.drawable.ic_name);
        }


        public void bindJashnvareh(RptJashnvarehForoshModel rptJashnvarehForoshModel) {
            binding.lblRadif.setText(String.valueOf(getAdapterPosition()+1));
            binding.lblCustomerFullNameCode.setText(String.format("%1$s" ,  rptJashnvarehForoshModel.getSharhJashnvareh()));
            binding.IMGName.setImageResource(R.drawable.ic_jashnvareh);

        }
    }
    public interface IRptJashnvarehFistLevelAdapter {
        void onItemClick(RptJashnvarehActivity.State state, RptJashnvarehForoshModel rptJashnvarehForoshModel , int position);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setViewType(RptJashnvarehActivity.State state) {
        this.state = state;
        notifyDataSetChanged();
    }

}
