package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MarjoeeAdapter extends RecyclerSwipeAdapter<MarjoeeAdapter.ViewHolder>
{

    private Context context;
    private final OnItemClickListener listener;
    private ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels;
    private boolean showSwipe;
    private int lastPosition = -1;

    public MarjoeeAdapter(Context context, ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels , boolean showSwipe , OnItemClickListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.kalaElamMarjoeeModels = kalaElamMarjoeeModels;
        this.showSwipe = showSwipe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marjoee_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.lblCodeKalaName.setText(String.format("%1$s - %2$s", kalaElamMarjoeeModels.get(position).getCodeKala(), kalaElamMarjoeeModels.get(position).getNameKala()));
        holder.lblCount.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.adad), kalaElamMarjoeeModels.get(position).getTedad3()));
        holder.lblCost.setText(String.format("%1$s : %2$s %3$s",context.getString(R.string.MablaghForosh), formatter.format(kalaElamMarjoeeModels.get(position).getGheymatForoshAsli()), context.getResources().getString(R.string.rial)));
        holder.lblMarjoeeCost.setText(String.format("%1$s : %2$s %3$s",context.getString(R.string.MablaghMarjoee), formatter.format(kalaElamMarjoeeModels.get(position).getFee()), context.getResources().getString(R.string.rial)));
        holder.lblDesc.setText(kalaElamMarjoeeModels.get(position).getSharh());
        holder.lblShomareBach.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareBach), kalaElamMarjoeeModels.get(position).getShomarehBach()));
        if (showSwipe)
        {
            holder.swipe.addDrag(SwipeLayout.DragEdge.Right , holder.laySwipe);
        }
        else
        {
            holder.swipe.addDrag(SwipeLayout.DragEdge.Right , null);
        }
        holder.bind(kalaElamMarjoeeModels.get(position) , position , listener);
        setAnimation(holder.itemView, position);
    }


    @Override
    public int getItemCount()
    {
        return kalaElamMarjoeeModels.size();
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
        private SwipeLayout swipe;
        private LinearLayout laySwipe;
        private TextView lblCodeKalaName;
        private TextView lblTarikhTolid;
        private TextView lblTarikhEngheza;
        private TextView lblCount;
        private TextView lblCost;
        private TextView lblMarjoeeCost;
        private TextView lblDesc;
        private TextView lblShomareBach;
        private RelativeLayout layDelete;
        private RelativeLayout layEditCount;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipe = view.findViewById(R.id.swipe);
            laySwipe = view.findViewById(R.id.laySwipe);
            lblCodeKalaName = view.findViewById(R.id.lblCodeNameKala);
            lblTarikhTolid = view.findViewById(R.id.lblTarikhTolid);
            lblTarikhEngheza = view.findViewById(R.id.lblTarikhEngheza);
            lblCount = view.findViewById(R.id.lblTedad);
            lblCost = view.findViewById(R.id.lblMablaghForosh);
            lblMarjoeeCost = view.findViewById(R.id.lblMablaghMarjoee);
            lblDesc = view.findViewById(R.id.lblDesc);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
            layDelete = view.findViewById(R.id.layDelete);
            layEditCount = view.findViewById(R.id.layEditCount);

            lblCodeKalaName.setTypeface(font);
            lblTarikhTolid.setTypeface(font);
            lblTarikhEngheza.setTypeface(font);
            lblMarjoeeCost.setTypeface(font);
            lblCount.setTypeface(font);
            lblCost.setTypeface(font);
            lblDesc.setTypeface(font);
            lblShomareBach.setTypeface(font);

            lblTarikhTolid.setVisibility(View.GONE);
            lblTarikhEngheza.setVisibility(View.GONE);
        }

        void bind(final KalaElamMarjoeeModel kalaElamMarjoeeModel , final int position , final OnItemClickListener listener)
        {
            layDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    swipe.close(true);
                    listener.onItemClick(Constants.DELETE(), kalaElamMarjoeeModel , position);
                }
            });

            layEditCount.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    swipe.close(true);
                    listener.onItemClick(Constants.CLEARING(), kalaElamMarjoeeModel , position);
                }
            });
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick(int operation, KalaElamMarjoeeModel kalaElamMarjoeeModel , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}
