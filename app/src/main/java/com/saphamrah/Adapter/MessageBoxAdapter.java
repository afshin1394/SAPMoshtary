package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.icu.util.ValueIterator;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageBoxAdapter extends RecyclerView.Adapter<MessageBoxAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<MessageBoxModel> messageBoxModels;
    private OnItemClickListener listener;

    public MessageBoxAdapter(Context context, ArrayList<MessageBoxModel> messageBoxModels, OnItemClickListener listener)
    {
        this.context = context;
        this.messageBoxModels = messageBoxModels;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.message_box_customlist, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.lblMessageTitle.setText(messageBoxModels.get(position).getTitle());
        holder.lblMessageSummary.setText(Html.fromHtml(messageBoxModels.get(position).getMessage()));
        String date = "";
        try
        {
            date = new PubFunc().new DateUtils().gregorianToPersianDateTime(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(messageBoxModels.get(position).getTarikh()));
            date = date.substring(0 , date.indexOf("-"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        holder.lblDate.setText(date);
        if (messageBoxModels.get(position).getStatusForoshandeh() == 1)
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        }
        else
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        }

        if (messageBoxModels.get(position).getNoeMessage() == 2)
        {
            holder.imgviewMessageType.setImageResource(R.drawable.ic_show_faktor_detail);
        }
        else
        {
            holder.imgviewMessageType.setImageResource(R.drawable.ic_message);
        }
        holder.bind(position , messageBoxModels.get(position));
    }


    @Override
    public int getItemCount()
    {
        return messageBoxModels.size();
    }



    public interface OnItemClickListener
    {
        void onItemClick(int position , MessageBoxModel message);
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout layRoot;
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;
        private TextView lblMessageTitle;
        private TextView lblMessageSummary;
        private TextView lblDate;
        //private TextView lblShowDetail;
        private ImageView imgviewMessageType;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = itemView.findViewById(R.id.layRoot);
            layStatusRight = itemView.findViewById(R.id.layStatusRight);
            layStatusLeft = itemView.findViewById(R.id.layStatusLeft);
            lblMessageTitle = itemView.findViewById(R.id.lblTitle);
            lblMessageSummary = itemView.findViewById(R.id.lblSummary);
            lblDate = itemView.findViewById(R.id.lblDate);
            //lblShowDetail = itemView.findViewById(R.id.lblShowDetail);
            imgviewMessageType = itemView.findViewById(R.id.imgviewMessageType);


            lblMessageTitle.setTypeface(font);
            lblMessageSummary.setTypeface(font);
            lblDate.setTypeface(font);
            //lblShowDetail.setTypeface(font);
        }

        void bind(final int position , final MessageBoxModel message)
        {
            layRoot.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(position , message);
                }
            });
        }


    }

}
