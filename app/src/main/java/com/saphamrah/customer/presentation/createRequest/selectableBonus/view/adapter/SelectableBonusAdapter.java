package com.saphamrah.customer.presentation.createRequest.selectableBonus.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.data.local.temp.SelectableBonus;
import com.saphamrah.customer.presentation.createRequest.filter.view.adapter.FilterChoiceAdapter;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.model.SelectableBonusModel;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.view.fragment.SelectableBonusFragment;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.AdapterUtil.SyncDiffUtilCallBack;
import com.saphamrah.customer.utils.RxUtils.Watcher;
import com.saphamrah.customer.utils.customViews.EditTextWatcher;

import java.util.ArrayList;
import java.util.List;


public class SelectableBonusAdapter extends RecyclerView.Adapter<SelectableBonusAdapter.ViewHolder> {
    private static final String TAG = SelectableBonusAdapter.class.getSimpleName();
    private Context context;
    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels;
    private AdapterItemListener<JayezehEntekhabiMojodiModel> listener;
    private int lastPosition = -1;

    public SelectableBonusAdapter(Context context, List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, AdapterItemListener<JayezehEntekhabiMojodiModel> listener) {
        this.context = context;
        this.jayezehEntekhabiMojodiModels = jayezehEntekhabiMojodiModels;
        this.listener = listener;
        Log.i(TAG, "SelectableBonusAdapter: " + jayezehEntekhabiMojodiModels);
    }

    @NonNull
    @Override
    public SelectableBonusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "SelectableBonusAdapter: onCreateViewHolder" + jayezehEntekhabiMojodiModels);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectable_bonus_itemview, parent, false);
        return new SelectableBonusAdapter.ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setJayezehEntekhabiMojodiModels(List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelList) {
            this.jayezehEntekhabiMojodiModels = new ArrayList<>();
            this.jayezehEntekhabiMojodiModels.addAll(jayezehEntekhabiMojodiModelList);
            notifyDataSetChanged();


    }


    @Override
    public void onBindViewHolder(@NonNull SelectableBonusAdapter.ViewHolder holder, int position) {
        Log.i(TAG, "SelectableBonusAdapter: onBindViewHolder" + jayezehEntekhabiMojodiModels);
        holder.bind(jayezehEntekhabiMojodiModels.get(position));

    }

    @Override
    public int getItemCount() {
        return jayezehEntekhabiMojodiModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_inventory;
        private TextView tv_bonus_name;
        private EditTextWatcher etv_bonus_name;
        private LinearLayout lin_minus;
        private LinearLayout lin_plus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_inventory = itemView.findViewById(R.id.tv_inventory);
            tv_bonus_name = itemView.findViewById(R.id.tv_title);
            etv_bonus_name = itemView.findViewById(R.id.etv_bonus_count);
            lin_minus = itemView.findViewById(R.id.lin_minus);
            lin_plus = itemView.findViewById(R.id.lin_plus);
        }

        public void bind(JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel) {
            Log.i(TAG, "SelectableBonusAdapter:bind" + jayezehEntekhabiMojodiModels);
            tv_inventory.setText(String.format("%1$s %2$s %3$s", context.getString(R.string.mojodi), jayezehEntekhabiMojodiModel.getTedad(), context.getString(R.string.adad)));
            tv_bonus_name.setText(jayezehEntekhabiMojodiModel.getNameKala());
            etv_bonus_name.setText(String.format("%1$s", jayezehEntekhabiMojodiModel.getSelectedCount()));
            lin_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "SelectableBonusAdapter: onclick" + jayezehEntekhabiMojodiModels);
                    jayezehEntekhabiMojodiModels.get(getAdapterPosition()).setSelectedCount(jayezehEntekhabiMojodiModel.getSelectedCount() + 1);
                    etv_bonus_name.setText(String.format("%1$s", jayezehEntekhabiMojodiModel.getSelectedCount()));
                    listener.onItemSelect(jayezehEntekhabiMojodiModel, getAdapterPosition(), AdapterAction.ADD);
                }
            });

            lin_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (jayezehEntekhabiMojodiModel.getSelectedCount() > 0) {
                        jayezehEntekhabiMojodiModels.get(getAdapterPosition()).setSelectedCount(jayezehEntekhabiMojodiModel.getSelectedCount() - 1);
                        etv_bonus_name.setText(String.format("%1$s", jayezehEntekhabiMojodiModel.getSelectedCount()));
                        listener.onItemSelect(jayezehEntekhabiMojodiModel, getAdapterPosition(), AdapterAction.REMOVE);
                    }
                }
            });

            etv_bonus_name.addTextWatcher(new Watcher() {
                @Override
                public void onTextChange(String s) {
                    try {
                        jayezehEntekhabiMojodiModel.setSelectedCount(Integer.parseInt(s));
                        listener.onItemSelect(jayezehEntekhabiMojodiModel, getAdapterPosition(), AdapterAction.EDIT);
                    } catch (Exception e) {

                    }
                }
            }, 100);

            setAnimation(itemView,getAdapterPosition());
        }

    }
    private void setAnimation(View view,int position){
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(),  android.R.anim.slide_in_left);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }
}
