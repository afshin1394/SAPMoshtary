package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.R;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class GetProgramItemsStatusAdapter extends RecyclerView.Adapter<GetProgramItemsStatusAdapter.MyViewHolder>
{

    private List<String> getProgramItemsName;
    private List<Integer> getProgramItemsStatus;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout layRoot;
        private ImageView imgItemStatus;
        private TextView lblItemName;

        private MyViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = view.findViewById(R.id.layRoot);
            imgItemStatus = view.findViewById(R.id.imgItemStatus);
            lblItemName = view.findViewById(R.id.lblItemName);

            lblItemName.setTypeface(font);
        }
    }


    public GetProgramItemsStatusAdapter(Context context , List<Integer> getProgramItemsStatus , List<String> getProgramItemsName)
    {
        this.context = context;
        this.getProgramItemsName = getProgramItemsName;
        this.getProgramItemsStatus = getProgramItemsStatus;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_customlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        String itemName = "";
        try
        {
            JSONObject jsonObject = new JSONObject(getProgramItemsName.get(position));
            itemName = jsonObject.getString("name");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        holder.lblItemName.setText(itemName);
        if (getProgramItemsStatus.get(position) == 1)
        {
            holder.imgItemStatus.setImageResource(R.drawable.ic_success);
        }
        else if (getProgramItemsStatus.get(position) == 0)
        {
            holder.imgItemStatus.setImageResource(R.drawable.circle_tick_gray);
        }
        else if (getProgramItemsStatus.get(position) == -1)
        {
            holder.imgItemStatus.setImageResource(R.drawable.ic_error);
        }

        if (position%2 == 0)
        {
            holder.layRoot.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }
        else
        {
            holder.layRoot.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
        }
    }


    @Override
    public int getItemCount() {
        return getProgramItemsName.size();
    }


}
