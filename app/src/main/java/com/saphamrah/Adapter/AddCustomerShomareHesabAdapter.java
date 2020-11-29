package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.R;

import java.util.ArrayList;

public class AddCustomerShomareHesabAdapter extends RecyclerSwipeAdapter<AddCustomerShomareHesabAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels;
    private OnItemClickListener listener;
    private int listType; //call from addCustomer = 1 , call from showCustomerInfo = 2

    public interface OnItemClickListener
    {
        void onItemClick(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel , int position);
    }


    public AddCustomerShomareHesabAdapter(Context context, ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels , int listType , OnItemClickListener listener)
    {
        this.context = context;
        this.moshtaryShomarehHesabModels = moshtaryShomarehHesabModels;
        this.listener = listener;
        this.listType = listType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_customer_shomare_hesab_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.lblNameBank.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.bank) , moshtaryShomarehHesabModels.get(position).getNameBank()));
        holder.lblNameNoeHesab.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.noeHesab) , moshtaryShomarehHesabModels.get(position).getNameNoeHesab()));
        holder.lblShomareHesab.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.shomareHesab) , moshtaryShomarehHesabModels.get(position).getShomarehHesab()));
        holder.bind(moshtaryShomarehHesabModels.get(position) , position , listener);
        if (listType == 2)
        {
            holder.swipeLayout.setSwipeEnabled(false);
        }
    }


    @Override
    public int getItemCount()
    {
        return moshtaryShomarehHesabModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        SwipeLayout swipeLayout;
        ImageView img;
        TextView lblNameBank;
        TextView lblNameNoeHesab;
        TextView lblShomareHesab;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            img = (ImageView) itemView.findViewById(R.id.imgDelete);
            lblNameBank = (TextView)itemView.findViewById(R.id.lblNameBank);
            lblNameNoeHesab = (TextView)itemView.findViewById(R.id.lblNameNoeHesab);
            lblShomareHesab = (TextView)itemView.findViewById(R.id.lblShomareHesab);

            lblNameBank.setTypeface(font);
            lblNameNoeHesab.setTypeface(font);
            lblShomareHesab.setTypeface(font);
        }

        public void bind(final MoshtaryShomarehHesabModel moshtaryShomarehHesabModel , final int position , final OnItemClickListener listener)
        {
            img.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(moshtaryShomarehHesabModel , position);
                }
            });
        }
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
