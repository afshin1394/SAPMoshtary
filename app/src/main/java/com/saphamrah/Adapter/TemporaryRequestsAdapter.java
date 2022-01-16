package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TemporaryRequestsAdapter extends RecyclerSwipeAdapter<TemporaryRequestsAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<CustomerDarkhastFaktorModel> models;
    private int noeForoshandeh;

    public TemporaryRequestsAdapter(Context context, ArrayList<CustomerDarkhastFaktorModel> models, int noeForoshandeh , OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.models = models;
        this.noeForoshandeh = noeForoshandeh;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temporary_requests_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));
        holder.lblRadif.setText(String.valueOf(position+1));
        holder.lblCustomerNameFamily.setText(String.format("%1$s - %2$s" , models.get(position).getCodeMoshtary() , models.get(position).getFullNameMoshtary()));
        holder.lblMablaghKhales.setText(formatter.format(models.get(position).getMablaghKhalesFaktor()));

        holder.imgSaveImage.setVisibility(View.VISIBLE);
        holder.laySaveImage.setVisibility(View.VISIBLE);

        holder.imgDelete.setVisibility(View.VISIBLE);
        holder.layDelete.setVisibility(View.VISIBLE);

        holder.imgPrint.setVisibility(View.GONE);
        holder.layPrint.setVisibility(View.GONE);

        holder.imgSend.setVisibility(View.VISIBLE);
        holder.laySend.setVisibility(View.VISIBLE);

        if (models.get(position).getExtraProp_IsOld() == 1)
        {
            if (noeForoshandeh == 1 || noeForoshandeh == 3)
            {
                holder.imgSaveImage.setVisibility(View.VISIBLE);
                holder.laySaveImage.setVisibility(View.VISIBLE);

                holder.imgDelete.setVisibility(View.GONE);
                holder.layDelete.setVisibility(View.GONE);

                holder.imgPrint.setVisibility(View.GONE);
                holder.layPrint.setVisibility(View.GONE);

                holder.imgSend.setVisibility(View.GONE);
                holder.laySend.setVisibility(View.GONE);
            }
            else
            {
                holder.imgSaveImage.setVisibility(View.VISIBLE);
                holder.laySaveImage.setVisibility(View.VISIBLE);

                holder.imgDelete.setVisibility(View.GONE);
                holder.layDelete.setVisibility(View.GONE);

                holder.imgPrint.setVisibility(View.VISIBLE);
                holder.layPrint.setVisibility(View.VISIBLE);

                holder.imgSend.setVisibility(View.GONE);
                holder.laySend.setVisibility(View.GONE);
            }
        }
        else
        {
            if (!models.get(position).getHaveEmzaImage() || !models.get(position).getHaveFaktorImage())
            {
                holder.imgSend.setVisibility(View.GONE);
                holder.laySend.setVisibility(View.GONE);
            }
            else
            {
                holder.imgSend.setVisibility(View.VISIBLE);
                holder.laySend.setVisibility(View.VISIBLE);
            }
        }
        if (holder.layDelete.getVisibility() == View.GONE && holder.laySend.getVisibility() == View.GONE && holder.layPrint.getVisibility() == View.GONE)
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);
        }
        else
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));
        }


        holder.bind(position , listener);
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private TextView lblRadif;
        private TextView lblCustomerNameFamily;
        private TextView lblMablaghKhales;
        private ImageView imgDelete;
        private ImageView imgSend;
        private ImageView imgPrint;
        private ImageView imgSaveImage;
        private RelativeLayout layDelete;
        private RelativeLayout laySend;
        private RelativeLayout layPrint;
        private RelativeLayout laySaveImage;


        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            lblRadif = view.findViewById(R.id.lblRadif);
            lblCustomerNameFamily = view.findViewById(R.id.lblCustomerFullNameCode);
            lblMablaghKhales = view.findViewById(R.id.lblInvoiceCost);
            imgDelete = view.findViewById(R.id.imgDelete);
            imgSend = view.findViewById(R.id.imgSend);
            imgPrint = view.findViewById(R.id.imgPrint);
            imgSaveImage = view.findViewById(R.id.imgSaveImage);
            layDelete = view.findViewById(R.id.layDelete);
            laySend = view.findViewById(R.id.laySend);
            layPrint = view.findViewById(R.id.layPrint);
            laySaveImage = view.findViewById(R.id.laySaveImage);

            lblRadif.setTypeface(font);
            lblCustomerNameFamily.setTypeface(font);
            lblMablaghKhales.setTypeface(font);
        }

        public void bind(final int position , final OnItemClickListener listener)
        {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(Constants.DELETE() , position);
                    swipeLayout.close(true);
                }
            });

            imgSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.SEND() , position);
                    swipeLayout.close(true);
                }
            });

            imgPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.PRINT(), position);
                    swipeLayout.close(true);
                }
            });

            imgSaveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.SAVE_IMAGE(), position);
                    swipeLayout.close(true);
                }
            });

        }
    }


    public interface OnItemClickListener
    {
        void onItemClick(int action , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}
