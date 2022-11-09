package com.saphamrah.customer.presentation.createRequest.cart.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.view.adapter.SelectableBonusAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.RxUtils.Watcher;
import com.saphamrah.customer.utils.customViews.EditTextWatcher;

import java.util.List;

public class SelectableBonusCartAdapter extends RecyclerView.Adapter<SelectableBonusCartAdapter.ViewHolder> {
    private static final String TAG = SelectableBonusAdapter.class.getSimpleName();
    private Context context;
    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels;
    private AdapterItemListener<JayezehEntekhabiMojodiModel> listener;

    public SelectableBonusCartAdapter(Context context, List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, AdapterItemListener<JayezehEntekhabiMojodiModel> listener) {
        this.context = context;
        this.jayezehEntekhabiMojodiModels = jayezehEntekhabiMojodiModels;
        this.listener = listener;
        Log.i(TAG, "SelectableBonusAdapter: " + jayezehEntekhabiMojodiModels);
    }

    @NonNull
    @Override
    public SelectableBonusCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "SelectableBonusAdapter: onCreateViewHolder"+jayezehEntekhabiMojodiModels);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectable_bonus_cart_itemview, parent, false);
        return new SelectableBonusCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectableBonusCartAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        Log.i(TAG, "SelectableBonusAdapter: onBindViewHolder payload"+jayezehEntekhabiMojodiModels);

        holder.bind(jayezehEntekhabiMojodiModels.get(position));


    }

    @Override
    public void onBindViewHolder(@NonNull SelectableBonusCartAdapter.ViewHolder holder, int position) {
        Log.i(TAG, "SelectableBonusAdapter: onBindViewHolder"+jayezehEntekhabiMojodiModels);

    }

    @Override
    public int getItemCount() {
        return jayezehEntekhabiMojodiModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_description;
        private TextView bonusQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_description =  itemView.findViewById(R.id.tv_bonus_name);
            bonusQuantity = itemView.findViewById(R.id.tv_bonus_quantity);
        }
        public void bind(JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel)
        {
            tv_description.setText(jayezehEntekhabiMojodiModel.getNameKala());
            bonusQuantity.setText(String.format("%1$s%2$s%3$s",context.getString(R.string.tedadJayezeh),jayezehEntekhabiMojodiModel.getTedad(),context.getString(R.string.adad)));


        }

    }
}
