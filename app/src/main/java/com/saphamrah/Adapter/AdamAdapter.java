package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerVisitModel;

import java.util.List;

public class AdamAdapter extends RecyclerView.Adapter<AdamAdapter.ViewHolder>
{
    public static int SEND = 1;
    public static int DELETE = 2;

    private Context context;
    private List<CustomerVisitModel> customerVisitModels;
    private onClickListener listener;

    public AdamAdapter(Context context, List<CustomerVisitModel> customerVisitModels, onClickListener listener)
    {
        this.context = context;
        this.customerVisitModels = customerVisitModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visit_moshtary_customlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , null);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));

        CustomerVisitModel customerVisitModel = customerVisitModels.get(position);
        holder.txtviewRadif.setText(String.valueOf(position+1));
        holder.txtviewFullNameCode.setText(customerVisitModel.getCustomerFullName());
        holder.txtviewAddress.setText(customerVisitModel.getCustomerAddress());
        holder.txtviewTelephone.setText(customerVisitModel.getCustomerTelephone());

        if (customerVisitModel.getExtraprop_isOld() == 1)
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        }
        else
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        }
    }

    @Override
    public int getItemCount()
    {
        return customerVisitModels.size();
    }

    public interface onClickListener
    {
        void onItemClickListener(int operation, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        SwipeLayout swipeLayout;
        ImageView imgviewStatus;
        TextView txtviewRadif;
        TextView txtviewFullNameCode;
        TextView txtviewAddress;
        TextView txtviewTelephone;
        RelativeLayout laySend;
        RelativeLayout layDelete;
        LinearLayout layStatusRight;
        LinearLayout layStatusLeft;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            imgviewStatus = itemView.findViewById(R.id.imgviewStatus);
            txtviewRadif = itemView.findViewById(R.id.lblRadif);
            txtviewFullNameCode = itemView.findViewById(R.id.lblCustomerFullNameCode);
            txtviewAddress = itemView.findViewById(R.id.lblAddress);
            txtviewTelephone = itemView.findViewById(R.id.lblTelephone);
            laySend = itemView.findViewById(R.id.laySend);
            layDelete = itemView.findViewById(R.id.layDelete);
            layStatusRight = itemView.findViewById(R.id.layStatusRight);
            layStatusLeft = itemView.findViewById(R.id.layStatusLeft);


            txtviewRadif.setTypeface(font);
            txtviewFullNameCode.setTypeface(font);
            txtviewAddress.setTypeface(font);
            txtviewTelephone.setTypeface(font);

            laySend.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    swipeLayout.close(true);
                    listener.onItemClickListener(SEND, getAdapterPosition());
                }
            });

            layDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    swipeLayout.close(true);
                    listener.onItemClickListener(DELETE, getAdapterPosition());
                }
            });
        }
    }

}
