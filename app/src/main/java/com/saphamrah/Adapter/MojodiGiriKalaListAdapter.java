package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.KalaModel;
import com.saphamrah.R;

import java.util.ArrayList;

public class MojodiGiriKalaListAdapter extends RecyclerView.Adapter<MojodiGiriKalaListAdapter.ViewHolder>
{

    private Context context;
    private final OnItemClickListener listener;
    private ArrayList<KalaModel> kalaModels;
    private int lastSelectedItem;

    public MojodiGiriKalaListAdapter(Context context, ArrayList<KalaModel> kalaModels , OnItemClickListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.kalaModels = kalaModels;
        lastSelectedItem = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.lblKalaName.setText(kalaModels.get(position).getNameKala());
        if (position == lastSelectedItem)
        {
            holder.imgStatus.setImageResource(R.drawable.ic_success);
        }
        else
        {
            holder.imgStatus.setImageResource(R.drawable.circle_tick_gray);
        }

        if (position % 2 == 0)
        {
            holder.layRoot.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }
        else
        {
            holder.layRoot.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
        }
        holder.bind(kalaModels.get(position) , position , listener);
    }


    @Override
    public int getItemCount()
    {
        return kalaModels.size();
    }

    public void clearSelecedItem()
    {
        lastSelectedItem = -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout layRoot;
        private TextView lblKalaName;
        private ImageView imgStatus;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = view.findViewById(R.id.layRoot);
            lblKalaName = view.findViewById(R.id.lblItemName);
            imgStatus = view.findViewById(R.id.imgItemStatus);

            lblKalaName.setTypeface(font);
        }

        void bind(final KalaModel kalaModel , final int position , final OnItemClickListener listener)
        {
            layRoot.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(kalaModel , position);
                }
            });
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick(KalaModel kalaModel , int position);
    }


}
