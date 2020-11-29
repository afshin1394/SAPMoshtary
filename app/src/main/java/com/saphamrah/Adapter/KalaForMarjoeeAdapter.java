package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;

public class KalaForMarjoeeAdapter extends RecyclerView.Adapter<KalaForMarjoeeAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModel;
    private int lastSelectedItem;


    public KalaForMarjoeeAdapter(Context context, ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModel , OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.listKalaForMarjoeeModel = listKalaForMarjoeeModel;
        this.lastSelectedItem = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kala_for_marjoee_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.lblNameKala.setText(listKalaForMarjoeeModel.get(position).getNameKala());
        holder.lblShomareBach.setText(String.format("%1$s \n %2$s", context.getResources().getString(R.string.shomareBach), listKalaForMarjoeeModel.get(position).getShomarehBach()));
        holder.lblCost.setText(String.format("%1$s \n %2$s", context.getResources().getString(R.string.mablaghForosh), formatter.format((int)listKalaForMarjoeeModel.get(position).getMablaghForosh())));
        if (position == lastSelectedItem)
        {
            holder.imgSelect.setImageResource(R.drawable.ic_success);
        }
        else
        {
            holder.imgSelect.setImageResource(R.drawable.circle_tick_gray);
        }
        holder.bind(listKalaForMarjoeeModel.get(position) , position , listener);
    }


    @Override
    public int getItemCount()
    {
        return listKalaForMarjoeeModel.size();
    }

    public void clearSelecedItem()
    {
        lastSelectedItem = -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layRoot;
        private TextView lblNameKala;
        private TextView lblShomareBach;
        private TextView lblCost;
        private ImageView imgSelect;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = view.findViewById(R.id.layRoot);
            lblNameKala = view.findViewById(R.id.lblNameKala);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
            lblCost = view.findViewById(R.id.lblGheymat);
            imgSelect = view.findViewById(R.id.imgSelect);

            lblNameKala.setTypeface(font);
            lblShomareBach.setTypeface(font);
            lblCost.setTypeface(font);
        }

        void bind(final ListKalaForMarjoeeModel listKalaForMarjoeeModel , final int position , final OnItemClickListener listener)
        {
            layRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(listKalaForMarjoeeModel , position);
                    Log.d("MarjoeeKala","position:" + position + " , listKalaForMarjoeeModel: "+ listKalaForMarjoeeModel.getNameKala() + " ,Tedad: " + listKalaForMarjoeeModel.getTedad());
                }
            });
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick(ListKalaForMarjoeeModel listKalaForMarjoeeModel , int position);
    }



}
