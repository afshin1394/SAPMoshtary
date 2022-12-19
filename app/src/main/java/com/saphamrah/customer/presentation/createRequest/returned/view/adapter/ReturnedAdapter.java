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

import java.util.ArrayList;
import java.util.List;

public class ReturnedAdapter extends RecyclerView.Adapter<ReturnedAdapter.ViewHolder> {
    private Context context;
    private AdapterItemListener<ElamMarjoeeForoshandehModel> listener;
    private AsyncListDiffer<ElamMarjoeeForoshandehModel> mDiffer;//callBack
    public ReturnedAdapter(Context context, AdapterItemListener<ElamMarjoeeForoshandehModel> listener) {
        this.context = context;
        this.mDiffer = new AsyncListDiffer<>(this,diffCallback);
        this.listener = listener;
    }
    private DiffUtil.ItemCallback<ElamMarjoeeForoshandehModel> diffCallback = new DiffUtil.ItemCallback<ElamMarjoeeForoshandehModel>() {
        @Override
        public boolean areItemsTheSame(ElamMarjoeeForoshandehModel oldItem, ElamMarjoeeForoshandehModel newItem) {
            return oldItem.getCcElamMarjoeeSatr() == newItem.getCcElamMarjoeeSatr();
        }        @Override
        public boolean areContentsTheSame(ElamMarjoeeForoshandehModel oldItem, ElamMarjoeeForoshandehModel newItem) {
            return oldItem.equals(newItem);
        }
    };//define AsyncListDiffer

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
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marjoee_itemview , parent , false);
        return new ReturnedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnedAdapter.ViewHolder holder, int position) {
        holder.shomarehBach.removeWatcher();
        holder.bind(getItem(position));
        holder.shomarehBach.addTextWatcher(holder.watcher,800);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCodeKala;
        private EditTextWatcher shomarehBach;
//        private TextView mablaghKharid;
//        private TextView mablaghMarjoee;
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
//            mablaghKharid = itemView.findViewById(R.id.tv_MablaghKharid);
//            mablaghMarjoee = itemView.findViewById(R.id.tv_MablaghMarjoee);
            addMarjoee = itemView.findViewById(R.id.lin_addmarjoee);
            addMarjoeeCount = itemView.findViewById(R.id.lin_addmarjoee_count);
            count = itemView.findViewById(R.id.etw_count);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            removeFromCart = itemView.findViewById(R.id.remove_from_cart);
            removeMarjoee = itemView.findViewById(R.id.remove_marjoee);

        }
        private Watcher watcher = new Watcher() {
            @Override
            public void onTextChange(String s) {
                if (s.equals("")){
                    shomarehBach.setHint(context.getString(R.string.shomareBach));
                }else {
                    mDiffer.getCurrentList().get(getBindingAdapterPosition()).setShomarehBach(s);
                }
            }
        };
        @SuppressLint("SuspiciousIndentation")
        public void bind(ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel) {
            nameCodeKala.setText(String.format("%1$s - %2$s",elamMarjoeeForoshandehModel.getCodeKala(),elamMarjoeeForoshandehModel.getNameKala()));
//            shomarehBach.setText(String.format("%1$s:%2$s",context.getString(R.string.shomareBach),elamMarjoeeForoshandehModel.getShomarehBach()));
//            mablaghKharid.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghKharid),elamMarjoeeForoshandehModel.getGheymatForosh(),context.getString(R.string.rial)));
//            mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghMarjoee),elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3(),context.getString(R.string.rial)));
            addMarjoee.setOnClickListener(view -> {
                addMarjoeeCount.setVisibility(View.VISIBLE);
                addMarjoee.setVisibility(View.GONE);
                elamMarjoeeForoshandehModel.setTedad3(1);
//                mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghMarjoee), ((int) (elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3())),context.getString(R.string.rial)));
                count.setText(String.valueOf(elamMarjoeeForoshandehModel.getTedad3()));
                listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.SELECT);

            });

                addToCart.setOnClickListener(view -> {
                    elamMarjoeeForoshandehModel.setTedad3(elamMarjoeeForoshandehModel.getTedad3() + 1);
                    count.setText(String.valueOf(elamMarjoeeForoshandehModel.getTedad3()));
//                    mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghMarjoee), ((int) (elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3())),context.getString(R.string.rial)));
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

//                        mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghMarjoee), ((int) (elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3())),context.getString(R.string.rial)));
                    }

                    listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.REMOVE);

                });


            count.addTextWatcher(new Watcher() {
                @Override
                public void onTextChange(String s) {
                    if (!s.equals(""))
                    elamMarjoeeForoshandehModel.setTedad3(Integer.parseInt(s));
//                    mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghMarjoee), ((int) (elamMarjoeeForoshandehModel.getGheymatForosh() * elamMarjoeeForoshandehModel.getTedad3())),context.getString(R.string.rial)));
                    listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.ADD);
                }
            },300);

            removeMarjoee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    elamMarjoeeForoshandehModel.setTedad3(0);
                    addMarjoeeCount.setVisibility(View.GONE);
                    addMarjoee.setVisibility(View.VISIBLE);
//                    mablaghMarjoee.setText(String.format("%1$s:%2$s %3$s",context.getString(R.string.mablaghMarjoee), 0 ,context.getString(R.string.rial)));
                    listener.onItemSelect(elamMarjoeeForoshandehModel,getAdapterPosition(), AdapterAction.REMOVE);
                }
            });



        }

    }
}
