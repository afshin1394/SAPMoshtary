package com.saphamrah.Adapter.tizerAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import java.util.ArrayList;


public class TizerFileAdapter extends RecyclerView.Adapter<TizerFileAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<TizerModel> models;
    private int lastPosition = -1;
    private int width;

    public TizerFileAdapter(Context context, ArrayList<TizerModel> models, int width , OnItemClickListener listener )
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tizer_file_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(BaseApplication.getContext());
        holder.logo_recycler.getLayoutParams().height = (width / 2) + 50;
            holder.txt_title_recycler.setText(models.get(position).getNameTizer_Farsi());

            /*
            change ByteArray to bitmap from model
             */
        Bitmap bmp = BitmapFactory.decodeByteArray(models.get(position).getImage(), 0, models.get(position).getImage().length);
        holder.logo_recycler.setImageBitmap(bmp);


        setAnimation(holder.itemView, position);
        Log.d("Tizer","http://" + serverIpModel.getServerIp() + ":" + serverIpModel.getPort()   + "/album/video/" + models.get(position).getNameTizer());
        holder.bind( "http://" + serverIpModel.getServerIp() + ":" + serverIpModel.getPort()   + "/album/video/" + models.get(position).getNameTizer() , position );

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

        void bind(String url , final int position )
        {
            layRoot.setOnClickListener(v -> {
                notifyDataSetChanged();
                listener.onItemClick(url , position);
            });
        }

    }

    public interface OnItemClickListener
    {
        void onItemClick(String folderName, int position);
    }

}
