//package com.saphamrah.Adapter;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.saphamrah.Model.RptJashnvarehForoshModel;
//import com.saphamrah.R;
//import com.saphamrah.databinding.RptJashnvarehCustomlistBinding;
//import com.saphamrah.databinding.RptJashnvarehSatrCustomlistBinding;
//
//import java.util.ArrayList;
//
//public class RptJashnvarehSatrAdapter extends RecyclerView.Adapter<RptJashnvarehSatrAdapter.ViewHolder>{
//    private Context context;
//    private ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels;
//    private RptJashnvarehSatrAdapter.IRptJashnvarehSatrAdapter iRptJashnvarehSatrAdapter;
//
//    @NonNull
//    @Override
//    public RptJashnvarehSatrAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        RptJashnvarehSatrCustomlistBinding binding = RptJashnvarehSatrCustomlistBinding.inflate (inflater);
//        return new RptJashnvarehSatrAdapter.ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RptJashnvarehSatrAdapter.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        RptJashnvarehSatrCustomlistBinding binding;
//        public ViewHolder(@NonNull RptJashnvarehSatrCustomlistBinding binding)
//        {
//            super(binding.getRoot());
//            this.binding = binding;
//            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
//            binding.lblAz.setTypeface(font);
//            binding.lblTa.setTypeface(font);
//            binding.lblBeEza.setTypeface(font);
//            binding.setTypeface(font);
//
//        }
//
//        public void bind(RptJashnvarehForoshModel rptJashnvarehForoshModel) {
//            binding.lblSharhJashnvareh.setText(String.format("%1$s %2$s" , context.getResources().getString(R.string.jashnvareh) ,rptJashnvarehForoshModel.getSharhJashnvareh()));
//            binding.lblEmtiazJashnvareh.setText(String.format("%1$s %2$s" , context.getResources().getString(R.string.emtiaz) ,rptJashnvarehForoshModel.getEmtiazJashnvareh()));
//
//            binding.getRoot().setOnClickListener(v->{
//                iRptJashnvarehListAdapter.onItemClick(rptJashnvarehForoshModel);
//            });
//        }
//
//
//    }
//
//
//    public interface IRptJashnvarehSatrAdapter{
//        void onItemClick(RptJashnvarehForoshModel rptJashnvarehForoshModel);
//    }
//}
