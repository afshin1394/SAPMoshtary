package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;


public class AddCustomerAdapter extends RecyclerSwipeAdapter<AddCustomerAdapter.ViewHolder>
{
    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<AddCustomerInfoModel> addCustomerInfoModels;

    public AddCustomerAdapter(Context context, ArrayList<AddCustomerInfoModel> addCustomerInfoModels , OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.addCustomerInfoModels = addCustomerInfoModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_customer_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));
        holder.lblCustomerNameFamily.setText(String.format("%1$s %2$s" , addCustomerInfoModels.get(position).getFirstName() , addCustomerInfoModels.get(position).getLastName()));
        holder.lblCustomerNoeSenfFaaliat.setText(String.format("%1$s - %2$s" , addCustomerInfoModels.get(position).getNoeSenfTitle() , addCustomerInfoModels.get(position).getNoeFaaliatTitle()));
        holder.lblCustomerDarajeh.setText(addCustomerInfoModels.get(position).getRotbeTitle());
        if (addCustomerInfoModels.get(position).getMoshtaryAddressModels().size() > 0)
        {
            holder.lblCustomerAddress.setText(addCustomerInfoModels.get(position).getMoshtaryAddressModels().get(0).getAddress());
        }
        if (addCustomerInfoModels.get(position).getIsOld() == 1)
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , null);
            holder.layRightstatus.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
            holder.layLeftstatus.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
        }
        else
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
            holder.layRightstatus.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
            holder.layLeftstatus.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
        }
        holder.bind(position , listener);
    }


    @Override
    public int getItemCount()
    {
        return addCustomerInfoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private TextView lblCustomerNameFamily;
        private TextView lblCustomerNoeSenfFaaliat;
        private TextView lblCustomerDarajeh;
        private TextView lblCustomerAddress;
        private ImageView imgDelete;
        private ImageView imgAddDocs;
        private ImageView imgShowCustomerLocation;
        private ImageView imgSend;
        private LinearLayout layRightstatus;
        private LinearLayout layLeftstatus;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgAddDocs = (ImageView) itemView.findViewById(R.id.imgAddDocs);
            imgShowCustomerLocation = (ImageView) itemView.findViewById(R.id.imgShowCustomerLocation);
            imgSend = (ImageView) itemView.findViewById(R.id.imgSend);
            lblCustomerNameFamily = view.findViewById(R.id.lblNameFamily);
            lblCustomerNoeSenfFaaliat = view.findViewById(R.id.lblNoeSenfFaaliat);
            lblCustomerDarajeh = view.findViewById(R.id.lblDarajeh);
            lblCustomerAddress = view.findViewById(R.id.lblAddress);
            layRightstatus = view.findViewById(R.id.layRightStatus);
            layLeftstatus = view.findViewById(R.id.layLeftStatus);

            lblCustomerNameFamily.setTypeface(font);
            lblCustomerNoeSenfFaaliat.setTypeface(font);
            lblCustomerDarajeh.setTypeface(font);
            lblCustomerAddress.setTypeface(font);
        }

        public void bind(final int position , final OnItemClickListener listener)
        {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(Constants.DELETE_CUSTOMER() , position);
                    swipeLayout.close(true);
                }
            });

            imgAddDocs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.SHOW_ADD_DOCS() , position);
                    swipeLayout.close(true);
                }
            });

            imgShowCustomerLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.SHOW_CUSTOMER_LOCATION(), position);
                    swipeLayout.close(true);
                }
            });

            imgSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.SEND_CUSTOMER(), position);
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
