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
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.R;

import java.util.List;

public class PorseshnamehAdapter extends RecyclerView.Adapter<PorseshnamehAdapter.ViewHolder>
{
    public static int SEND = 1;
    public static int DELETE = 2;
    public static int EDIT = 3;

    private List<PorseshnamehModel> porseshnamehModels;
    private Context context;
    private onClickListener listener;


    public PorseshnamehAdapter(Context context, List<PorseshnamehModel> porseshnamehModels, onClickListener listener)
    {
        this.context = context;
        this.porseshnamehModels = porseshnamehModels;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.porseshnameh_customlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        PorseshnamehModel porseshnamehModel = porseshnamehModels.get(position);
        holder.txtviewRadif.setText(String.valueOf(position+1));
        holder.txtviewFullNameCode.setText(porseshnamehModel.getNameMoshtary());
        holder.txtviewAddress.setText(porseshnamehModel.getAddress());
        holder.txtviewTelephone.setText(porseshnamehModel.getTelephone());

        if (porseshnamehModel.getExtraProp_IsOld() == 1)
        {
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , null);
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);
        }
        else
        {
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));
        }
    }

    @Override
    public int getItemCount()
    {
        return porseshnamehModels.size();
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
        RelativeLayout layEdit;
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
            layEdit = itemView.findViewById(R.id.layEdit);
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


            layEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    swipeLayout.close(true);
                    listener.onItemClickListener(EDIT, getAdapterPosition());
                }
            });

        }
    }

}
