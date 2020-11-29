package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.MyViewHolder>
{

    public interface OnItemClickListener
    {
        void onItemClick(LogPPCModel logPPCModel , int position);
    }

    private List<LogPPCModel> arraylistLogPPCModels;
    private Context context;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView lblMessageTitle;
        public TextView lblMessageContent;
        public TextView lblDateTitle;
        public TextView lblDateContent;

        MyViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblMessageTitle = view.findViewById(R.id.lblLogMessageTitle);
            lblMessageContent = view.findViewById(R.id.lblLogMessageContent);
            lblDateTitle = view.findViewById(R.id.lblLogDateTitle);
            lblDateContent = view.findViewById(R.id.lblLogDateContent);

            lblMessageTitle.setTypeface(font);
            lblMessageContent.setTypeface(font);
            lblDateTitle.setTypeface(font);
            lblDateContent.setTypeface(font);
        }

        public void bind(final LogPPCModel logPPCModel , final int position , final OnItemClickListener listener)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(logPPCModel , position);
                }
            });
        }

    }


    public LogsAdapter(List<LogPPCModel> gpsDataModels , Context context , OnItemClickListener listener)
    {
        this.arraylistLogPPCModels = gpsDataModels;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_customlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        LogPPCModel logPPCModel = arraylistLogPPCModels.get(position);
        holder.lblMessageContent.setText(logPPCModel.getLogMessage());
        try
        {
            SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            Date newDate = format.parse(logPPCModel.getLogDate());

            format = new SimpleDateFormat(Constants.NORMAL_DATE_TIME_FORMAT());
            String date = format.format(newDate);

            holder.lblDateContent.setText(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        holder.bind(arraylistLogPPCModels.get(position) , position , listener);
    }


    @Override
    public int getItemCount() {
        return arraylistLogPPCModels.size();
    }

}
