package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BarkhordAvalieAdapter extends RecyclerSwipeAdapter<BarkhordAvalieAdapter.MyViewHolder>
{

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
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            Date date = sdf.parse(arrayListBarkhords.get(position).getTarikh());
            holder.lblDate.setText(new PubFunc().new DateUtils().gregorianToPersianDateTime(date));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "BarkhordAvalieAdapter", "", "onBindViewHolder", "");
        }
        if (arrayListBarkhords.get(position).getExtraProp_IsOld() == 0)
        {
            holder.swipeLayout.getSurfaceView().setEnabled(true);
        }
        else if (arrayListBarkhords.get(position).getExtraProp_IsOld() == 1)
        {
            holder.swipeLayout.getSurfaceView().setEnabled(false);
        }
        holder.lblMessageContent.setText(arrayListBarkhords.get(position).getTozihat());
        holder.bind(arrayListBarkhords.get(position) , position , listener);
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

        private MyViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            lblDate = view.findViewById(R.id.lblDate);
            lblMessageContent = view.findViewById(R.id.lblMessageContent);
            imgDelete = view.findViewById(R.id.imgDelete);

            lblDate.setTypeface(font);
            lblMessageContent.setTypeface(font);
        }

        public void bind(final BarkhordForoshandehBaMoshtaryModel barkhord , final int position , final OnItemClickListener listener)
        {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    swipeLayout.close(true);
                    listener.onItemClick(barkhord , position);
                }
            });
        }

    }



    public interface OnItemClickListener
    {
        void onItemClick(BarkhordForoshandehBaMoshtaryModel barkhords , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}
