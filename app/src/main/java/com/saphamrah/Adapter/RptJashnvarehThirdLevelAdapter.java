package com.saphamrah.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.R;
import com.saphamrah.databinding.RptJashnvarehSecondlevelCustomlistBinding;
import com.saphamrah.databinding.RptJashnvarehThirdlevelCustomlistBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptJashnvarehThirdLevelAdapter extends RecyclerView.Adapter<RptJashnvarehThirdLevelAdapter.ViewHolder>{
    private Context context;
    private ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels;
    private RptJashnvarehActivity.State state;

    public RptJashnvarehThirdLevelAdapter(RptJashnvarehActivity.State state, Context context, ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels) {
        this.context = context;
        this.state = state;
        this.jashnavareForoshModels = jashnavareForoshModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RptJashnvarehThirdlevelCustomlistBinding binding = DataBindingUtil.inflate(inflater, R.layout.rpt_jashnvareh_thirdlevel_customlist, parent, false);
        return new RptJashnvarehThirdLevelAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RptJashnvarehForoshModel jashnvarehForoshModel = jashnavareForoshModels.get(position);
        switch (state){
            case Moshtary:
                holder.bindMoshtarySatr(jashnvarehForoshModel);
                break;
            case Jashnvareh:
                holder.bindJashnvarehSatr(jashnvarehForoshModel);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return jashnavareForoshModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RptJashnvarehThirdlevelCustomlistBinding binding;

        public ViewHolder(@NonNull RptJashnvarehThirdlevelCustomlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
            binding.lblName.setTypeface(font);
            binding.lblEmtiaz.setTypeface(font);
            binding.lblRialEmtiaz.setTypeface(font);
            binding.lblAzTarikh.setTypeface(font);
            binding.lblTaTarikh.setTypeface(font);
            binding.lblMarkaz.setTypeface(font);

        }

        public void bindJashnvarehSatr(RptJashnvarehForoshModel jashnvarehForoshModel) {
            binding.lblName.setText(String.format("%1$s" ,  jashnvarehForoshModel.getNameMoshtary()));
            binding.lblEmtiaz.setText(String.format("%1$s %2$s", jashnvarehForoshModel.getEmtiazMoshtary(),context.getResources().getString(R.string.emtiaz)));
            binding.lblAzTarikh.setText(String.format("%1$s" ,   jashnvarehForoshModel.getAzTarikhJashnvareh().substring(0,9)));
            binding.lblTaTarikh.setText(jashnvarehForoshModel.getTaTarikhJashnvareh()!=null ? String.format("%1$s" ,  jashnvarehForoshModel.getTaTarikhJashnvareh().substring(0,9)):String.format("%1$s" ,context.getResources().getString(R.string.undefined)));
            binding.lblMarkaz.setText(String.format("%1$s - %2$s" , jashnvarehForoshModel.getNameMarkaz(),jashnvarehForoshModel.getNameSazmanForosh()));
            binding.lblRialEmtiaz.setText(String.format("%1$s %2$s" ,  (int) jashnvarehForoshModel.getRialEmtiazMoshtary(),context.getResources().getString(R.string.rial)));
            binding.IMGName.setImageResource(R.drawable.ic_customers);
            binding.IMGName.setPadding(6,6,6,6);
        }

        public void bindMoshtarySatr(RptJashnvarehForoshModel jashnvarehForoshModel) {
            binding.lblName.setText(String.format("%1$s" ,    jashnvarehForoshModel.getSharhJashnvareh()));
            binding.lblEmtiaz.setText(String.format("%1$s %2$s", jashnvarehForoshModel.getEmtiazMoshtary(),context.getResources().getString(R.string.emtiaz)));
            binding.lblAzTarikh.setText(String.format("%1$s" ,   jashnvarehForoshModel.getAzTarikhJashnvareh().substring(0,9)));
            binding.lblTaTarikh.setText(jashnvarehForoshModel.getTaTarikhJashnvareh()!=null ? String.format("%1$s" , jashnvarehForoshModel.getTaTarikhJashnvareh().substring(0,9)):String.format("%1$s" ,context.getResources().getString(R.string.undefined)));
            binding.lblMarkaz.setText(String.format("%1$s - %2$s" , jashnvarehForoshModel.getNameMarkaz(),jashnvarehForoshModel.getNameSazmanForosh()));
            binding.lblRialEmtiaz.setText(String.format("%1$s %2$s" ,  (int) jashnvarehForoshModel.getRialEmtiazMoshtary(),context.getResources().getString(R.string.rial)));
            binding.IMGName.setImageResource(R.drawable.ic_jashnvareh_foreground);
        }

    }



    @SuppressLint("NotifyDataSetChanged")
    public void setViewType(RptJashnvarehActivity.State state){
        this.state = state;
        notifyDataSetChanged();
    }
}
