package com.saphamrah.Adapter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BarkhordAvalieAdapter extends RecyclerSwipeAdapter<BarkhordAvalieAdapter.MyViewHolder>
{
    private boolean onBind;
    private final BarkhordAvalieAdapter.OnItemClickListener listener;
    private ArrayList<BarkhordForoshandehBaMoshtaryModel> arrayListBarkhords;
    private Context context;

    public BarkhordAvalieAdapter(Context context , ArrayList<BarkhordForoshandehBaMoshtaryModel> arrayListBarkhords , OnItemClickListener listener)
    {
        this.context = context;
        this.arrayListBarkhords = arrayListBarkhords;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.barkhord_avalie_customlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        onBind = true;
        holder.bind(arrayListBarkhords.get(position) , position , listener);
        onBind = false;
    }


    @Override
    public int getItemCount() {
        return arrayListBarkhords.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private TextView lblDate;
        private TextView lblMessageContent;
        private ImageView imgDelete;
        private LinearLayout linFavorite;
        private SwitchCompat switchFavorite;
        private ImageView imgFavorite;

        private MyViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
            swipeLayout = view.findViewById(R.id.swipe);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, itemView.findViewById(R.id.layRight));

            lblDate = view.findViewById(R.id.lblDate);
            lblMessageContent = view.findViewById(R.id.lblMessageContent);
            imgDelete = view.findViewById(R.id.imgDelete);
            linFavorite = view.findViewById(R.id.LinFavorite);
            switchFavorite = view.findViewById(R.id.switchFavorite);
            imgFavorite = view.findViewById(R.id.ImgFavorite);
            lblDate.setTypeface(font);
            lblMessageContent.setTypeface(font);
        }

        public void bind(final BarkhordForoshandehBaMoshtaryModel barkhord , final int position , final OnItemClickListener listener)
        {
            if (barkhord.getExtraProp_IsOld() == 1){
                linFavorite.setVisibility(View.INVISIBLE);
            }else{
                linFavorite.setVisibility(View.VISIBLE);
            }

            if (barkhord.getIsFavorite() == 1)
            {
                imgFavorite.setVisibility(View.VISIBLE);
                switchFavorite.setChecked(true);

            } else {
                imgFavorite.setVisibility(View.INVISIBLE);
                switchFavorite.setChecked(false);
            }
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                Date date = sdf.parse(barkhord.getTarikh());
                lblDate.setText(new PubFunc().new DateUtils().gregorianToPersianDateTime(date));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "BarkhordAvalieAdapter", "", "onBindViewHolder", "");
            }
            if (barkhord.getExtraProp_IsOld() == 0)
            {
                swipeLayout.getSurfaceView().setEnabled(true);
            }
            else if (barkhord.getExtraProp_IsOld() == 1)
            {
                swipeLayout.getSurfaceView().setEnabled(false);
            }
            lblMessageContent.setText(barkhord.getTozihat());
            switchFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                  if (!onBind) {
                      if (isChecked) {
                          imgFavorite.setVisibility(View.VISIBLE);
                      } else {
                          imgFavorite.setVisibility(View.INVISIBLE);
                      }
                      listener.addToFavorite(barkhord, position, isChecked);
                  }


            });


            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    swipeLayout.close(true);
                    listener.deleteItem(barkhord , position);
                }
            });
        }

    }



    public interface OnItemClickListener
    {
        void deleteItem(BarkhordForoshandehBaMoshtaryModel barkhords , int position);
        void addToFavorite(BarkhordForoshandehBaMoshtaryModel barkhords , int position,boolean add);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setBarkhords(ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordForoshandehBaMoshtaryModels){
        this.arrayListBarkhords.clear();
        this.arrayListBarkhords = barkhordForoshandehBaMoshtaryModels;
        this.notifyDataSetChanged();
    }
    public void updateBarkhordFavorite(int position,boolean operator){
        if (operator)
        this.arrayListBarkhords.get(position).setIsFavorite(1);
        else
            this.arrayListBarkhords.get(position).setIsFavorite(0);

       new Handler().post(() -> notifyItemChanged(position));


    }


}
