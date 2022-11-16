package com.saphamrah.customer.presentation.createRequest.returned.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.RxUtils.Watcher;
import com.saphamrah.customer.utils.customViews.EditTextWatcher;

import java.util.List;

public class ReturnedAdapter extends RecyclerView.Adapter<ReturnedAdapter.ViewHolder> {
    private Context context;
    private List<ElamMarjoeeForoshandehModel> models;
    private AdapterItemListener<ElamMarjoeeForoshandehModel> listener;

    public ReturnedAdapter(Context context, List<ElamMarjoeeForoshandehModel> models, AdapterItemListener<ElamMarjoeeForoshandehModel> listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marjoee_itemview , parent , false);
        return new ReturnedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnedAdapter.ViewHolder holder, int position) {
        holder.bind(models.get(position));
    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCodeKala;
        private TextView shomarehBach;
        private TextView mablaghForosh;
        private TextView mablaghMarjoee;
        private LinearLayout addMarjoee;
        private LinearLayout addMarjoeeCount;
        private EditTextWatcher count;
        private ImageView addToCart;
        private ImageView removeFromCart;
        private ImageView removeMarjoee;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCodeKala = itemView.findViewById(R.id.tv_CodeNameKala);
            shomarehBach = itemView.findViewById(R.id.tv_ShomareBach);
            mablaghForosh = itemView.findViewById(R.id.tv_MablaghForosh);
            mablaghMarjoee = itemView.findViewById(R.id.tv_MablaghMarjoee);
            addMarjoee = itemView.findViewById(R.id.lin_addmarjoee);
            addMarjoeeCount = itemView.findViewById(R.id.lin_addmarjoee_count);
            count = itemView.findViewById(R.id.etw_count);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            removeFromCart = itemView.findViewById(R.id.remove_from_cart);
            removeMarjoee = itemView.findViewById(R.id.remove_marjoee);
        }

        @SuppressLint("SuspiciousIndentation")
        public void bind(ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel) {
            nameCodeKala.setText(String.format("%1$s - %2$s",elamMarjoeeForoshandehModel.getCodeKala(),elamMarjoeeForoshandehModel.getNameKala()));
            shomarehBach.setText(String.format("%1$s:%2$s",context.getString(R.string.shomareBach),elamMarjoeeForoshandehModel.getShomarehBach()));
            mablaghForosh.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh),elamMarjoeeForoshandehModel.getGheymatForosh(),context.getString(R.string.rial)));
            mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh),elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3(),context.getString(R.string.rial)));
            addMarjoee.setOnClickListener(view -> {
                addMarjoeeCount.setVisibility(View.VISIBLE);
                addMarjoee.setVisibility(View.GONE);
                elamMarjoeeForoshandehModel.setTedad3(1);
                mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh), ((int) (elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3())),context.getString(R.string.rial)));
                count.setText(String.valueOf(elamMarjoeeForoshandehModel.getTedad3()));
                listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.ADD);

            });

                addToCart.setOnClickListener(view -> {
                    elamMarjoeeForoshandehModel.setTedad3(elamMarjoeeForoshandehModel.getTedad3() + 1);
                    count.setText(String.valueOf(elamMarjoeeForoshandehModel.getTedad3()));
                    mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh), ((int) (elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3())),context.getString(R.string.rial)));
                    listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.ADD);
                });
                removeFromCart.setOnClickListener(view -> {
                    if (elamMarjoeeForoshandehModel.getTedad3() > 1) {
                        elamMarjoeeForoshandehModel.setTedad3(elamMarjoeeForoshandehModel.getTedad3() - 1);
                        count.setText(String.valueOf(elamMarjoeeForoshandehModel.getTedad3()));
                    }else{
                        if (elamMarjoeeForoshandehModel.getTedad3() == 1)
                        elamMarjoeeForoshandehModel.setTedad3(elamMarjoeeForoshandehModel.getTedad3() - 1);
                        addMarjoeeCount.setVisibility(View.GONE);
                        addMarjoee.setVisibility(View.VISIBLE);

                        mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh), ((int) (elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3())),context.getString(R.string.rial)));
                    }

                    listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.REMOVE);

                });


            count.addTextWatcher(new Watcher() {
                @Override
                public void onTextChange(String s) {
                    if (!s.equals(""))
                    elamMarjoeeForoshandehModel.setTedad3(Integer.parseInt(s));
                    mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh), ((int) (elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3())),context.getString(R.string.rial)));
                    listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.ADD);
                }
            },300);

            removeMarjoee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    elamMarjoeeForoshandehModel.setTedad3(0);
                    addMarjoeeCount.setVisibility(View.GONE);
                    addMarjoee.setVisibility(View.VISIBLE);
                    mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghForosh), 0 ,context.getString(R.string.rial)));
                    listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.REMOVE);
                }
            });

        }

    }
}
