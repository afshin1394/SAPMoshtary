package com.saphamrah.customer.presentation.createRequest.returned.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;

import org.w3c.dom.Text;

import java.util.List;

public class SelectedReturnedAdapter extends RecyclerView.Adapter<SelectedReturnedAdapter.ViewHolder> {
    private AsyncListDiffer<ElamMarjoeeForoshandehModel> mDiffer;//callBack
    private Context context;
    public SelectedReturnedAdapter(Context context) {
        this.mDiffer = new AsyncListDiffer<>(this,diffCallback);
        this.context = context;
    }

    private DiffUtil.ItemCallback<ElamMarjoeeForoshandehModel> diffCallback = new DiffUtil.ItemCallback<ElamMarjoeeForoshandehModel>() {
        @Override
        public boolean areItemsTheSame(ElamMarjoeeForoshandehModel oldItem, ElamMarjoeeForoshandehModel newItem) {
            return oldItem.getCcElamMarjoeeSatr() == newItem.getCcElamMarjoeeSatr();
        }        @Override
        public boolean areContentsTheSame(ElamMarjoeeForoshandehModel oldItem, ElamMarjoeeForoshandehModel newItem) {
            return false;
        }
    };//define AsyncListDiffer
    @NonNull
    @Override
    public SelectedReturnedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_marjoee_itemview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedReturnedAdapter.ViewHolder holder, int position) {
        holder.setData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }
    public void submitList(List<ElamMarjoeeForoshandehModel> data) {
        mDiffer.submitList(data);
    }
    public ElamMarjoeeForoshandehModel getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView marjoeeKalaName;
        TextView tedad;
        TextView mablaghMarjoee;
        TextView elatMarjoee;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            marjoeeKalaName = itemView.findViewById(R.id.tv_marjoee_kala_name);
            tedad = itemView.findViewById(R.id.tv_marjoee_tedad);
            mablaghMarjoee = itemView.findViewById(R.id.tv_mablagh_marjoee);
            elatMarjoee = itemView.findViewById(R.id.tv_elat_marjoee);
        }

        public void setData(ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel) {
            marjoeeKalaName.setText(elamMarjoeeForoshandehModel.getNameKala());
            tedad.setText(String.format("%1$s:%2$s",context.getString(R.string.tedad),elamMarjoeeForoshandehModel.getTedad3()));
            mablaghMarjoee.setText(String.format("%1$s%2$s", ((int) (elamMarjoeeForoshandehModel.getTedad3() * elamMarjoeeForoshandehModel.getGheymatForosh())),context.getString(R.string.rial)));
            elatMarjoee.setText(elamMarjoeeForoshandehModel.getNameElatMarjoeeKala());
        }
    }
}
