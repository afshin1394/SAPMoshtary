package com.saphamrah.Adapter.marjoee;

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
import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MarjoeeForoshandehAdapter extends RecyclerSwipeAdapter<MarjoeeForoshandehAdapter.ViewHolder>
{

    private Context context;
    private final OnItemClickListener listener;
    private ArrayList<ElamMarjoeeForoshandehModel> models;


    private int lastPosition = -1;

    public MarjoeeForoshandehAdapter(Context context, ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels  , OnItemClickListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.models = elamMarjoeeForoshandehModels;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marjoee_foroshandeh_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.laySwipe));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);

        if (models.get(position).getExtraProp_TedadMarjoee() > 0){
            holder.layDeletTedadMarjoee.setVisibility(View.VISIBLE);
        } else {
            holder.layDeletTedadMarjoee.setVisibility(View.GONE);
        }



        holder.lblCodeKalaName.setText(String.format("%1$s - %2$s", models.get(position).getCodeKala(), models.get(position).getNameKala()));
        holder.lblCount.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.adad), models.get(position).getTedad3()));
        holder.lblTedadMarjoee.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.adad), models.get(position).getExtraProp_TedadMarjoee()));
        holder.lblCost.setText(String.format("%1$s : %2$s",context.getResources().getString(R.string.tarikhTolid) , models.get(position).getTarikhTolidShamsi() ));
        holder.lblDesc.setText(models.get(position).getNameElatMarjoeeKala());
        holder.lblShomareBach.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareBach), models.get(position).getShomarehBach()));

        holder.bind(models.get(position) , position , listener);
        setAnimation(holder.itemView, position);


        if (models.get(position).getExtraProp_TedadMarjoee() == 0){
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_red_left);
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_red_right);
        } else if (models.get(position).getExtraProp_TedadMarjoee() == models.get(position).getTedad3()){
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_green_left);
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_green_right);
        } else if (models.get(position).getExtraProp_TedadMarjoee() > 0 && models.get(position).getExtraProp_TedadMarjoee() < models.get(position).getTedad3()){
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_yellow_left);
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_yellow_right);
        }

    }


    @Override
    public int getItemCount()
    {
        return models.size();
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

        public ViewHolder(View view)
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

        void bind(final ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel , final int position , final OnItemClickListener listener)
        {
            layEditTedadMarjoee.setOnClickListener(v -> {
                swipeLayout.close(true);
                listener.onItemClick(Constants.CLEARING(), elamMarjoeeForoshandehModel , position);
            });
            layDeletTedadMarjoee.setOnClickListener(v -> {
                swipeLayout.close(true);
                listener.onItemClick(Constants.DELETE(), elamMarjoeeForoshandehModel , position);
            });
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick(int operation, ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModel , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}
