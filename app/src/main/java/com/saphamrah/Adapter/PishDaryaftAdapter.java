package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class PishDaryaftAdapter extends RecyclerSwipeAdapter<PishDaryaftAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<MoshtaryModel> moshtaryModels;
    private int lastPosition = -1;
    ParameterChildDAO childParameterDAO = new ParameterChildDAO(BaseApplication.getContext());

    public PishDaryaftAdapter(Context context, ArrayList<MoshtaryModel> moshtaryModels, OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.moshtaryModels = moshtaryModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pish_daryaft_customlist, parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        String noeVosolName = childParameterDAO.getTextByParameterNameAndValue(Constants.NOE_VOSOL_MOSHTARY() , String.valueOf(moshtaryModels.get(position).getCodeNoeVosolAzMoshtary()));
        Log.i("PishDaryaft" , "PishDaryaftAdapter : "  + noeVosolName);
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));

        holder.lblRadif.setText(String.valueOf(position + 1));
        holder.lblCustomerFullNameCode.setText(String.format("%1$s - %2$s" , moshtaryModels.get(position).getCodeMoshtary() ,  moshtaryModels.get(position).getNameMoshtary()));
        holder.lblCustomerNoeVosol.setText(noeVosolName);
        String telephone = moshtaryModels.get(position).getMobile();
        if (telephone.trim().equals("") || telephone.trim().equals("0"))
        {
            holder.lblCustomerPhone.setText("---");
        }
        else
        {
            holder.lblCustomerPhone.setText(moshtaryModels.get(position).getMobile());
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
        private TextView lblRadif;
        private TextView lblCustomerFullNameCode;
        private TextView lblCustomerNoeVosol;
        private TextView lblCustomerPhone;
        private ImageView imgCustomerPhone;
        private ImageView imgAddToRequestList;
        private RelativeLayout laySend;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            lblRadif = view.findViewById(R.id.lblRadif);
            lblCustomerFullNameCode = view.findViewById(R.id.lblCustomerFullNameCode);
            lblCustomerNoeVosol = view.findViewById(R.id.lblNoeVosol);
            lblCustomerPhone = view.findViewById(R.id.lblTelephone);
            imgCustomerPhone = view.findViewById(R.id.imgCustomerPhone);
            imgAddToRequestList = view.findViewById(R.id.imgAddToRequestList);
            laySend = view.findViewById(R.id.laySend);

            lblRadif.setTypeface(font);
            lblCustomerFullNameCode.setTypeface(font);
            lblCustomerNoeVosol.setTypeface(font);
            lblCustomerPhone.setTypeface(font);
        }

        void bind(final int position , final OnItemClickListener listener)
        {
            imgAddToRequestList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Constants.EDIT() , position);
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
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick( int operation , int position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }



}
