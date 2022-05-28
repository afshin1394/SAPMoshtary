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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MarjoeeKoliAdapter extends RecyclerSwipeAdapter<MarjoeeKoliAdapter.MyViewHolder>
{

    private final OnItemClickListener listener;
    private ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels;
    private boolean showSwipe;
    private Context context;
    private int lastPosition = -1;
    public MarjoeeKoliAdapter(Context context , ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels , boolean showSwipe , OnItemClickListener listener)
    {
        this.context = context;
        this.kalaDarkhastFaktorSatrModels = kalaDarkhastFaktorSatrModels;
        this.listener = listener;
        this.showSwipe = showSwipe;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.marjoee_koli_customlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        setAnimation(holder.itemView, position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String mablaghForosh = formatter.format((int)kalaDarkhastFaktorSatrModels.get(position).getMablaghForosh());
        holder.lblCodeNameKala.setText(String.format("%1$s - %2$s", kalaDarkhastFaktorSatrModels.get(position).getCodeKalaOld(), kalaDarkhastFaktorSatrModels.get(position).getNameKalaMarjoeeKol()));
        holder.lblMablaghForosh.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.mablaghForoshUnitRial), mablaghForosh));
        holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.shomareBach), kalaDarkhastFaktorSatrModels.get(position).getShomarehBach()));
        holder.lblTedadAghlamMarjoee.setText(String.format("%1$s" , kalaDarkhastFaktorSatrModels.get(position).getTedadMarjoee()));
        holder.lblTedadAghlam.setText(String.format("%1$s" , kalaDarkhastFaktorSatrModels.get(position).getTedad3()));
        holder.lblNameElat.setText(String.format("%1$s" , kalaDarkhastFaktorSatrModels.get(position).getNameElat()));


        //set kala status asasi
        if (kalaDarkhastFaktorSatrModels.get(position).getKalaAsasi())
        {
            holder.imgStatusKala.setImageResource(R.drawable.ic_kalaasasi);
            holder.imgStatusKala.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.imgStatusKala.setVisibility(View.GONE);
        }



        if (showSwipe)
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.layDelete);

        }
        else
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , null);
        }
        holder.bind(kalaDarkhastFaktorSatrModels.get(position), position, listener);

    }


    @Override
    public int getItemCount() {
        return kalaDarkhastFaktorSatrModels.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private TextView lblCodeNameKala;
        private TextView lblShomareBach;
        private TextView lblMablaghForosh;
        private TextView lblNameElat;
        private TextView lblTedadAghlam;
        private TextView lblTedadAghlamMarjoee;
        private ImageView imgStatusKala;
        private ImageView imgDelete;
        private LinearLayout layDelete;


        private MyViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            lblCodeNameKala = view.findViewById(R.id.lblCodeNameKala);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
            lblMablaghForosh = view.findViewById(R.id.lblMablaghForosh);
            lblNameElat = view.findViewById(R.id.lblNameElat);
            lblTedadAghlam = view.findViewById(R.id.lblTedadAghlam);
            lblTedadAghlamMarjoee = view.findViewById(R.id.lblTedadAghlamMarjoee);
            imgStatusKala = view.findViewById(R.id.imgKalaAsasi);
            imgDelete = view.findViewById(R.id.imgDelete);
            layDelete = view.findViewById(R.id.layDelete);


            lblCodeNameKala.setTypeface(font);
            lblShomareBach.setTypeface(font);
            lblMablaghForosh.setTypeface(font);
            lblNameElat.setTypeface(font);
            lblTedadAghlam.setTypeface(font);
            lblTedadAghlamMarjoee.setTypeface(font);
        }

        public void bind(final KalaDarkhastFaktorSatrModel kalaDarkhastFaktorSatrModels , final int position , final OnItemClickListener listener)
        {
            layDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeLayout.close(true);
                    listener.onItemClick(kalaDarkhastFaktorSatrModels , position);
                }
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
        void onItemClick(KalaDarkhastFaktorSatrModel kalaDarkhastFaktorSatrModels , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
