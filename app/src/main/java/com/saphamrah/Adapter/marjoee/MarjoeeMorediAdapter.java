                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  package com.saphamrah.Adapter.marjoee;

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
import com.saphamrah.Model.MarjoeeMamorPakhshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.Utils.Constants;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MarjoeeMorediAdapter extends RecyclerSwipeAdapter<MarjoeeMorediAdapter.MyViewHolder>
{

    private final OnItemClickListener listener;
    private ArrayList<MarjoeeMamorPakhshModel> models;
    private boolean showSwipe;
    private Context context;
    private int lastPosition = -1;
    public MarjoeeMorediAdapter(Context context , ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels , boolean showSwipe , OnItemClickListener listener)
    {
        this.context = context;
        this.models = marjoeeMamorPakhshModels;
        this.listener = listener;
        this.showSwipe = showSwipe;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.marjoee_moredi_customlist, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        setAnimation(holder.itemView, position);
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.laySwipe));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);

        if (models.get(position).getExtraProp_TedadNahaeeMarjoee() > 0){
            holder.layDeletTedadMarjoee.setVisibility(View.VISIBLE);
        } else {
            holder.layDeletTedadMarjoee.setVisibility(View.GONE);
        }



        holder.lblCodeKalaName.setText(String.format("%1$s - %2$s", models.get(position).getCodeKalaOld(), models.get(position).getNameKala()));
        holder.lblCount.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.adad), models.get(position).getTedad3()));
        holder.lblTedadMarjoee.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.adad), models.get(position).getExtraProp_TedadNahaeeMarjoee()));
        holder.lblCost.setText(String.format("%1$s : %2$s",context.getResources().getString(R.string.tarikhTolid) , models.get(position).getTarikhTolidShamsi() ));
//        holder.lblDesc.setText(models.get(position).getNameElatMarjoeeKala());
        holder.lblShomareBach.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareBach), models.get(position).getShomarehBach()));

        holder.bind(models.get(position) , position , listener);
        setAnimation(holder.itemView, position);


        if (models.get(position).getExtraProp_TedadNahaeeMarjoee() == 0){
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_red_left);
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_red_right);
        } else if (models.get(position).getExtraProp_TedadNahaeeMarjoee() == models.get(position).getTedad3()){
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_green_left);
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_green_right);
        } else if (models.get(position).getExtraProp_TedadNahaeeMarjoee() > 0 && models.get(position).getExtraProp_TedadNahaeeMarjoee() < models.get(position).getTedad3()){
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_yellow_left);
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_yellow_right);
        }


    }


    @Override
    public int getItemCount() {
        return models.size();
    }




public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private LinearLayout laySwipe;
        private TextView lblCodeKalaName;
        private TextView lblTarikhTolid;
        private TextView lblTarikhEngheza;
        private TextView lblCount;
        private TextView lblCost;
        private TextView lblDesc;
        private TextView lblShomareBach;
        private TextView lblTedadMarjoee;
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;
        private RelativeLayout layEditTedadMarjoee;
        private RelativeLayout layDeletTedadMarjoee;



        public MyViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            laySwipe = view.findViewById(R.id.laySwipe);
            lblCodeKalaName = view.findViewById(R.id.lblCodeNameKala);
            lblTarikhTolid = view.findViewById(R.id.lblTarikhTolid);
            lblTarikhEngheza = view.findViewById(R.id.lblTarikhEngheza);
            lblCount = view.findViewById(R.id.lblTedad);
            lblCost = view.findViewById(R.id.lblMablaghForosh);
            lblDesc = view.findViewById(R.id.lblDesc);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
            lblTedadMarjoee = view.findViewById(R.id.lblTedadMarjoee);
            layStatusRight = view.findViewById(R.id.layStatusRight);
            layStatusLeft = view.findViewById(R.id.layStatusLeft);
            layEditTedadMarjoee = view.findViewById(R.id.layEditTedadMarjoee);
            layDeletTedadMarjoee = view.findViewById(R.id.layDeletTedadMarjoee);

            lblCodeKalaName.setTypeface(font);
            lblTarikhTolid.setTypeface(font);
            lblTarikhEngheza.setTypeface(font);
            lblCount.setTypeface(font);
            lblCost.setTypeface(font);
            lblDesc.setTypeface(font);
            lblShomareBach.setTypeface(font);
            lblTedadMarjoee.setTypeface(font);

            lblTarikhTolid.setVisibility(View.GONE);
            lblTarikhEngheza.setVisibility(View.GONE);
        }

        public void bind(MarjoeeMamorPakhshModel model ,  int position ,  OnItemClickListener listener)
        {
            layEditTedadMarjoee.setOnClickListener(v -> {
                swipeLayout.close(true);
                listener.onItemClick(Constants.EDIT(), model , position);
            });
            layDeletTedadMarjoee.setOnClickListener(v -> {
                swipeLayout.close(true);
                listener.onItemClick(Constants.DELETE(), model , position);
            });

        }

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

    public interface OnItemClickListener
    {
        void onItemClick(int operation,MarjoeeMamorPakhshModel model , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
