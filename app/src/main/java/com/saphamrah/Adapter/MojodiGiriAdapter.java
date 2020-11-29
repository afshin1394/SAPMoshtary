package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaMojodiGiriModel;

import java.util.ArrayList;

public class MojodiGiriAdapter extends RecyclerSwipeAdapter<MojodiGiriAdapter.ViewHolder>
{

    private Context context;
    private final OnItemClickListener listener;
    private ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels;
    private int lastPosition = -1;

    public MojodiGiriAdapter(Context context, ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels , OnItemClickListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.kalaMojodiGiriModels = kalaMojodiGiriModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mojodigiri_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.lblKalaName.setText(kalaMojodiGiriModels.get(position).getNameKala());
        holder.lblCount.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.count), (int)kalaMojodiGiriModels.get(position).getTedadMojoodiGiri()));
        holder.bind(kalaMojodiGiriModels.get(position) , position , listener);
        setAnimation(holder.itemView, position);
    }


    @Override
    public int getItemCount()
    {
        return kalaMojodiGiriModels.size();
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
        private ImageView imgDelete;
        private TextView lblKalaName;
        private TextView lblCount;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipe = view.findViewById(R.id.swipe);
            imgDelete = view.findViewById(R.id.imgDelete);
            lblKalaName = view.findViewById(R.id.lblName);
            lblCount = view.findViewById(R.id.lblCount);

            lblKalaName.setTypeface(font);
            lblCount.setTypeface(font);
        }

        void bind(final KalaMojodiGiriModel kalaMojodiGiriModel , final int position , final OnItemClickListener listener)
        {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    swipe.close(true);
                    listener.onItemClick(kalaMojodiGiriModel , position);
                }
            });
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick(KalaMojodiGiriModel kalaMojodiGiriModel , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}
