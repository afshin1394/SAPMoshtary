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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.R;

import java.util.ArrayList;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<SystemMenuModel> systemMenuModels;


    public MainMenuAdapter(Context context, ArrayList<SystemMenuModel> systemMenuModels , OnItemClickListener listener)
    {
        this.context = context;
        this.systemMenuModels = systemMenuModels;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        final String imageURI = "drawable/" + systemMenuModels.get(position).getIconName();

        holder.img.setImageResource(context.getResources().getIdentifier(imageURI, null, context.getPackageName()));
        holder.lblTitle.setText(systemMenuModels.get(position).getMenuName());
        holder.bind(systemMenuModels.get(position) , position , listener);
    }

    @Override
    public int getItemCount()
    {
        return systemMenuModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout layRoot;
        ImageView img;
        TextView lblTitle;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = (RelativeLayout)itemView.findViewById(R.id.layRoot);
            img = (ImageView)itemView.findViewById(R.id.img);
            lblTitle = (TextView)itemView.findViewById(R.id.lbl);

            lblTitle.setTypeface(font);
        }

        public void bind(final SystemMenuModel systemMenuModel , final int position , final OnItemClickListener listener)
        {
            layRoot.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.d("MainMenuAdaptor","Link: " + systemMenuModel.getLink());
                    listener.onItemClick(systemMenuModel , position);
                }
            });
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick(SystemMenuModel systemMenuModel , int position);
    }

}
