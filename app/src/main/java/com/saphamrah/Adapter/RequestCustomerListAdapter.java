package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;


public class RequestCustomerListAdapter extends RecyclerSwipeAdapter<RequestCustomerListAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<MoshtaryModel> moshtaryModels;
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels;
    private ArrayList<Integer> arrayListNoeMorajeh;
    private boolean canUpdateCustomer;
    private int lastPosition = -1;

    public RequestCustomerListAdapter(Context context, ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels, ArrayList<Integer> arrayListNoeMorajeh, boolean canUpdateCustomer, OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.moshtaryModels = moshtaryModels;
        this.moshtaryAddressModels = moshtaryAddressModels;
        this.arrayListNoeMorajeh = arrayListNoeMorajeh;
        this.canUpdateCustomer = canUpdateCustomer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_customer_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));

        String address = "";
        if (moshtaryAddressModels.get(position).getAddress() != null && !moshtaryAddressModels.get(position).getAddress().trim().equals(""))
        {
            address = moshtaryAddressModels.get(position).getAddress();
        }
        holder.lblCustomerFullNameCode.setText(String.format("%1$s - %2$s" , moshtaryModels.get(position).getCodeMoshtary() ,  moshtaryModels.get(position).getNameMoshtary()));
        holder.lblCustomerAddress.setText(address);
        holder.lblRadif.setText(String.valueOf(position + 1));

        if (arrayListNoeMorajeh.get(position) != 0)
        {
            if (arrayListNoeMorajeh.get(position) == 1)
            {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            }
            else if (arrayListNoeMorajeh.get(position) == 2)
            {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
            }
            else if (arrayListNoeMorajeh.get(position) == 3)
            {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            }
            else if (arrayListNoeMorajeh.get(position) == 4)
            {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorPurple));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorPurple));
            }
            else
            {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
            }
        }
        else
        {
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
        }
        if (canUpdateCustomer)
        {
            holder.layChangeCustomerInfo.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.layChangeCustomerInfo.setVisibility(View.GONE);
        }
        holder.bind(position , listener);
        setAnimation(holder.itemView, position);
    }


    @Override
    public int getItemCount()
    {
        return moshtaryModels.size();
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
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;
        private RelativeLayout layShowLocation;
        private RelativeLayout layChangeLocation;
        private RelativeLayout layChangeCustomerInfo;
        private RelativeLayout layShowCustomerInfo;
        private RelativeLayout layUpdateEtebar;
        private TextView lblRadif;
        private TextView lblCustomerFullNameCode;
        private TextView lblCustomerAddress;
        /*private ImageView imgShowLocation;
        private ImageView imgChangeLocation;
        private ImageView imgShowCustomerInfo;
        private ImageView imgChangeCustomerInfo;*/

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            layStatusRight = view.findViewById(R.id.layStatusRight);
            layStatusLeft = view.findViewById(R.id.layStatusLeft);
            layChangeCustomerInfo = view.findViewById(R.id.layChangeCustomerInfo);
            lblRadif = view.findViewById(R.id.lblRadif);
            lblCustomerFullNameCode = view.findViewById(R.id.lblCustomerFullNameCode);
            lblCustomerAddress = view.findViewById(R.id.lblAddress);

            layShowLocation = view.findViewById(R.id.layShowLocation);
            layChangeLocation = view.findViewById(R.id.layChangeLocation);
            layChangeCustomerInfo = view.findViewById(R.id.layChangeCustomerInfo);
            layShowCustomerInfo = view.findViewById(R.id.layShowCustomerInfo);
            layUpdateEtebar = view.findViewById(R.id.layUpdateEtebar);

            lblRadif.setTypeface(font);
            lblCustomerAddress.setTypeface(font);
            lblCustomerFullNameCode.setTypeface(font);
        }

        void bind(final int position , final OnItemClickListener listener)
        {
            swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SELECT_CUSTOMER() , position);
                }
            });

            layShowLocation.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SHOW_LOCATION() , position);
                    swipeLayout.close(true);
                }
            });

            layChangeLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_CHANGE_LOCATION() , position);
                    swipeLayout.close(true);
                }
            });

            layShowCustomerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_SHOW_CUSTOMER_INFO(), position);
                    swipeLayout.close(true);
                }
            });

            layChangeCustomerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_CHANGE_CUSTOMER_INFO(), position);
                    swipeLayout.close(true);
                }
            });

            layUpdateEtebar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.REQUEST_CUSTOMER_UPDATE_CREDIT(), position);
                    swipeLayout.close(true);
                }
            });

        }


    }


    public interface OnItemClickListener
    {
        void onItemClick(int operation , int position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
