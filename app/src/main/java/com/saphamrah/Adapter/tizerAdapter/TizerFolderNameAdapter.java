package com.saphamrah.Adapter.tizerAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.R;

import java.util.ArrayList;

public class TizerFolderNameAdapter extends RecyclerView.Adapter<TizerFolderNameAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<String> models;
    private int lastPosition = -1;
    private int width;

    public TizerFolderNameAdapter(Context context, ArrayList<String> models, int width , OnItemClickListener listener )
    {
        this.listener = listener;
        this.context = context;
        this.models = models;
        this.width = width;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tizer_folder_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {


        holder.logo_recycler.getLayoutParams().height = (width / 2) + 50;
        if (models.get(position).equals("تبلیغات") ){
            holder.txt_title_recycler.setText(models.get(position));
            holder.logo_recycler.setImageResource(R.drawable.advertising);
        } else if (models.get(position).equals("آموزش") ){
            holder.txt_title_recycler.setText(models.get(position));
            holder.logo_recycler.setImageResource(R.drawable.learning);
        } else {
            holder.txt_title_recycler.setText(models.get(position));
            holder.logo_recycler.setImageResource(R.drawable.nopicfolder);
        }
        setAnimation(holder.itemView, position);
        holder.bind( models.get(position) , position );
        Log.i("", "");
    }

    @Override
    public int getItemCount()
    {
        return models.size();
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

        CardView layRoot;
        ImageView logo_recycler;
        TextView txt_title_recycler;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));


            layRoot = itemView.findViewById(R.id.layRoot);
            logo_recycler = itemView.findViewById(R.id.logo_recycler);
            txt_title_recycler = itemView.findViewById(R.id.txt_title_recycler);
            txt_title_recycler.setTypeface(font);

        }

        void bind(String folderName , final int position )
        {
            layRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(folderName , position);
                }
            });
        }

    }

    public interface OnItemClickListener
    {
        void onItemClick(String folderName, int position);
    }

}
