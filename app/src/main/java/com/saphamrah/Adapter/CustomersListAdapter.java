package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.R;

import java.util.ArrayList;

public class CustomersListAdapter extends RecyclerSwipeAdapter<CustomersListAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels;
    private int lastPosition = -1;

    public CustomersListAdapter(Context context, ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels, OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.allMoshtaryForoshandehModels = allMoshtaryForoshandehModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customers_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);

        holder.lblRadif.setText(String.valueOf(position + 1));
        holder.lblCustomerFullNameCode.setText(String.format("%1$s - %2$s" , allMoshtaryForoshandehModels.get(position).getCodeMoshtary() ,  allMoshtaryForoshandehModels.get(position).getNameMoshtary()));
        holder.lblCustomerAddress.setText(allMoshtaryForoshandehModels.get(position).getAddress());
        String telephone = allMoshtaryForoshandehModels.get(position).getTelephone();
        if (telephone.trim().equals("") || telephone.trim().equals("0"))
        {
            holder.lblCustomerPhone.setText("---");
        }
        else
        {
            holder.lblCustomerPhone.setText(allMoshtaryForoshandehModels.get(position).getTelephone());
        }
        holder.bind(position , listener);

        setAnimation(holder.itemView, position);
    }


    @Override
    public int getItemCount()
    {
        return allMoshtaryForoshandehModels.size();
    }


    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private TextView lblRadif;
        private TextView lblCustomerFullNameCode;
        private TextView lblCustomerAddress;
        private TextView lblCustomerPhone;
        private ImageView imgCustomerPhone;
        private ImageView imgAddToRequestList;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            lblRadif = view.findViewById(R.id.lblRadif);
            lblCustomerFullNameCode = view.findViewById(R.id.lblCustomerFullNameCode);
            lblCustomerAddress = view.findViewById(R.id.lblAddress);
            lblCustomerPhone = view.findViewById(R.id.lblTelephone);
            imgCustomerPhone = view.findViewById(R.id.imgCustomerPhone);
            imgAddToRequestList = view.findViewById(R.id.imgAddToRequestList);

            lblRadif.setTypeface(font);
            lblCustomerFullNameCode.setTypeface(font);
            lblCustomerAddress.setTypeface(font);
            lblCustomerPhone.setTypeface(font);
        }

        void bind(final int position , final OnItemClickListener listener)
        {
            imgAddToRequestList.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(position);
                    swipeLayout.close(true);
                }
            });
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }



}
