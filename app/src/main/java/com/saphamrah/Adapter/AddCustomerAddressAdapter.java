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
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.R;

import java.util.ArrayList;

public class AddCustomerAddressAdapter extends RecyclerSwipeAdapter<AddCustomerAddressAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels;
    private int listType; //call from addCustomer = 1 , call from showCustomerInfo = 2
    private boolean showSwipe;

    public AddCustomerAddressAdapter(Context context, ArrayList<MoshtaryAddressModel> moshtaryAddressModels , int listType , boolean showSwipe , OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.moshtaryAddressModels = moshtaryAddressModels;
        this.listType = listType;
        this.showSwipe = showSwipe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_customer_address_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.lblNoeAddress.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.noeAddress) , moshtaryAddressModels.get(position).getNameNoeAddress()));
        holder.lblTelephone.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.telephone) , moshtaryAddressModels.get(position).getTelephone()));
        holder.lblCodePosti.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.codePosti) , moshtaryAddressModels.get(position).getCodePosty()));
        holder.lblAddress.setText(String.format("%1$s : %2$s" , context.getResources().getString(R.string.address) , moshtaryAddressModels.get(position).getAddress()));
        if (showSwipe)
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.layRight);
            if (listType == 1)
            {
                holder.img.setImageResource(R.drawable.ic_delete);
            }
            else if (listType == 2)
            {
                holder.img.setImageResource(R.drawable.ic_edit);
            }
        }
        else
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);
        }
        holder.bind(moshtaryAddressModels.get(position) , position , listener);
    }

    @Override
    public int getItemCount()
    {
        return moshtaryAddressModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private ImageView img;
        private TextView lblNoeAddress;
        private TextView lblTelephone;
        private TextView lblCodePosti;
        private TextView lblAddress;
        private LinearLayout layRight;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            img = view.findViewById(R.id.imgDelete);
            lblNoeAddress = view.findViewById(R.id.lblNoeAddress);
            lblTelephone = view.findViewById(R.id.lblTelephone);
            lblCodePosti = view.findViewById(R.id.lblCodePosti);
            lblAddress = view.findViewById(R.id.lblAddress);
            layRight = view.findViewById(R.id.layRight);

            lblNoeAddress.setTypeface(font);
            lblTelephone.setTypeface(font);
            lblCodePosti.setTypeface(font);
            lblAddress.setTypeface(font);
        }

        void bind(final MoshtaryAddressModel moshtaryAddressModel , final int position , final OnItemClickListener listener)
        {
            layRight.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(moshtaryAddressModel , position);
                }
            });
        }
    }


    public interface OnItemClickListener
    {
        void onItemClick(MoshtaryAddressModel moshtaryAddressModel , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
