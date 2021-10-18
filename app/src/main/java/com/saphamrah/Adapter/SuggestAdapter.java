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
import com.saphamrah.Model.MarjoeeMamorPakhshModel;
import com.saphamrah.Model.SuggestModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class SuggestAdapter extends RecyclerSwipeAdapter<SuggestAdapter.MyViewHolder> {

    private final OnItemClickListener listener;
    private ArrayList<SuggestModel> models;
    private boolean showSwipe;
    private Context context;
    private int lastPosition = -1;

    public SuggestAdapter(Context context, ArrayList<SuggestModel> models, boolean showSwipe, OnItemClickListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
        this.showSwipe = showSwipe;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggest_customlist, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        setAnimation(holder.itemView, position);
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.itemView.findViewById(R.id.laySwipe));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, null);




        holder.lblDescription.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.description),models.get(position).getDescription()));
        holder.lblNameNoePishnahad.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.noePishnahad),models.get(position).getExtraProp_NameNoePishnahad()));


        holder.bind(position, listener);
        setAnimation(holder.itemView, position);


        if (models.get(position).getExtraProp_IsSend() == 0) {
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_red_left);
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_red_right);
        } else if (models.get(position).getExtraProp_IsSend() == 1) {
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_green_left);
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_green_right);
        }


    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipeLayout;
        private LinearLayout laySwipe;
        private TextView lblDescription;
        private TextView lblNameNoePishnahad;
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;
        private RelativeLayout layDeleteSuggest;


        public MyViewHolder(View view) {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            laySwipe = view.findViewById(R.id.laySwipe);
            lblDescription = view.findViewById(R.id.lblDescription);
            lblNameNoePishnahad = view.findViewById(R.id.lblNameNoePishnahad);
            layDeleteSuggest = view.findViewById(R.id.layDeleteSuggest);

            layStatusRight = view.findViewById(R.id.layStatusRight);
            layStatusLeft = view.findViewById(R.id.layStatusLeft);


            lblDescription.setTypeface(font);
            lblNameNoePishnahad.setTypeface(font);

        }

        public void bind(int position, OnItemClickListener listener) {

            layDeleteSuggest.setOnClickListener(v -> {
                swipeLayout.close(true);
                listener.onItemClick(Constants.DELETE(), position);
            });

        }

    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int operation , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
