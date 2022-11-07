package com.saphamrah.customer.presentation.profile.address.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.AddressMoshtaryModel;

import java.util.ArrayList;

public class CustomerAddressAdapter extends RecyclerView.Adapter<CustomerAddressAdapter.ViewHolder>
{

    private Context context;
//    private final OnItemClickListener listener;
    private ArrayList<AddressMoshtaryModel> models;

    public CustomerAddressAdapter(Context context, ArrayList<AddressMoshtaryModel> models )
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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_address_moshtary , parent , false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.address.setText(models.get(position).getAddress());
        holder.noeDarkhast.setText(models.get(position).getNoeDarkhast());
        holder.codePosti.setText(models.get(position).getCodePosti());
        holder.phone.setText(models.get(position).getPhone());
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView address;
        private TextView noeDarkhast;
        private TextView codePosti;
        private TextView phone;

        public ViewHolder(View view)
        {
            super(view);
//            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            address = view.findViewById(R.id.address_tv);
            noeDarkhast = view.findViewById(R.id.noe_darkhast_tv);
            codePosti = view.findViewById(R.id.code_posti_tv);
            phone = view.findViewById(R.id.phone_tv);
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
