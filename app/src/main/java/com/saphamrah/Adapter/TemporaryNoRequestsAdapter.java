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
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerAdamDarkhastModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class TemporaryNoRequestsAdapter extends RecyclerSwipeAdapter<TemporaryNoRequestsAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<CustomerAdamDarkhastModel> models;

    public TemporaryNoRequestsAdapter(Context context, ArrayList<CustomerAdamDarkhastModel> models, OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temporary_norequest_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
        holder.lblRadif.setText(String.valueOf(position+1));
        holder.lblCustomerNameFamily.setText(String.format("%1$s - %2$s" , models.get(position).getCodeMoshtary() , models.get(position).getFullNameMoshtary()));
        holder.lblElatAdamDarkhast.setText(models.get(position).getNameElatAdamDarkhast());
        if (models.get(position).isSentToServer())
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
            holder.layDelete.setVisibility(View.GONE);
            holder.laySend.setVisibility(View.GONE);
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);
        }
        else
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
            holder.layDelete.setVisibility(View.VISIBLE);
            holder.laySend.setVisibility(View.VISIBLE);
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
        private TextView lblElatAdamDarkhast;
        private RelativeLayout layDelete;
        private RelativeLayout laySend;
        private RelativeLayout layAdamDarkhastImage;
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            lblRadif = view.findViewById(R.id.lblRadif);
            lblCustomerNameFamily = view.findViewById(R.id.lblCustomerFullNameCode);
            lblElatAdamDarkhast = view.findViewById(R.id.lblElatAdamDarkhast);
            layDelete = view.findViewById(R.id.layDelete);
            laySend = view.findViewById(R.id.laySend);
            layAdamDarkhastImage = view.findViewById(R.id.layAdamDarkhastImage);
            layStatusRight = view.findViewById(R.id.layStatusRight);
            layStatusLeft = view.findViewById(R.id.layStatusLeft);

            lblRadif.setTypeface(font);
            lblCustomerNameFamily.setTypeface(font);
            lblElatAdamDarkhast.setTypeface(font);
        }

        public void bind(final int position , final OnItemClickListener listener)
        {
            layDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(Constants.DELETE() , position);
                    swipeLayout.close(true);
                }
            });

            laySend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.SEND() , position);
                    swipeLayout.close(true);
                }
            });

            layAdamDarkhastImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.SHOW_IMAGE(), position);
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
