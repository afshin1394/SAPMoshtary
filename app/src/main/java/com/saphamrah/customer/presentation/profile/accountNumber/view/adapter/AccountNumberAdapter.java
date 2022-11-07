package com.saphamrah.customer.presentation.profile.accountNumber.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.AccountNumberModel;

import java.util.ArrayList;

public class AccountNumberAdapter extends RecyclerView.Adapter<AccountNumberAdapter.ViewHolder>
{

    private Context context;
//    private final OnItemClickListener listener;
    private ArrayList<AccountNumberModel> models;

    public AccountNumberAdapter(Context context, ArrayList<AccountNumberModel> models )
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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_number_account , parent , false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        int remain = position % 3;
        switch (remain){
            case 0:
                holder.rootView.setBackgroundResource(R.drawable.sample_gradient);
                break;
            case 1:
                holder.rootView.setBackgroundResource(R.drawable.sample_gradient_two);
                break;
            case 2:
                holder.rootView.setBackgroundResource(R.drawable.sample_gradient_three);
                break;

        }
        holder.bank.setText(models.get(position).getBank());
        holder.type.setText(models.get(position).getType());
        holder.number.setText(models.get(position).getNumber());
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView bank;
        private TextView type;
        private TextView number;
        private RelativeLayout rootView;

        public ViewHolder(View view)
        {
            super(view);

            bank = view.findViewById(R.id.namebank_tv);
            type = view.findViewById(R.id.type_tv);
            number = view.findViewById(R.id.number_tv);
            rootView = view.findViewById(R.id.root_relative);
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
