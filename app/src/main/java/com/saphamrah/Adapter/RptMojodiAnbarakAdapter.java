package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.RptMojodiAnbarModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;

import java.io.File;
import java.util.ArrayList;

public class RptMojodiAnbarakAdapter extends RecyclerView.Adapter<RptMojodiAnbarakAdapter.ViewHolder>
{


    private Context context;
    private ArrayList<RptMojodiAnbarModel> mojodiAnbarakModels;
    private int lastPosition = -1;
    private SparseArray allKalaPhoto=new SparseArray();

    public RptMojodiAnbarakAdapter(Context context, ArrayList<RptMojodiAnbarModel> mojodiAnbarakModels)
    {
        this.context = context;
        this.mojodiAnbarakModels = mojodiAnbarakModels;
        for (int i = 0 ; i < this.mojodiAnbarakModels.size() ; i++)
        {
            if (this.mojodiAnbarakModels.get(i).getRadif() == -1)
            {
                mojodiAnbarakModels.remove(i);
            }
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_mojodi_anbarak_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        holder.lblCodeKala.setText(mojodiAnbarakModels.get(position).getCodeKala());
        holder.lblNameKala.setText(mojodiAnbarakModels.get(position).getNameKala());
        holder.lblNameSazman.setText(mojodiAnbarakModels.get(position).getNameSazmanForosh());
        holder.lblCartonCount.setText(String.format("%1$s: %2$s" , context.getResources().getString(R.string.carton) , String.valueOf(mojodiAnbarakModels.get(position).getMandehMojodi_Karton())));
        holder.lblBastehCount.setText(String.format("%1$s: %2$s" , context.getResources().getString(R.string.basteh) , String.valueOf(mojodiAnbarakModels.get(position).getMandehMojodi_Basteh())));
        holder.lblAdadCount.setText(String.format("%1$s: %2$s" , context.getResources().getString(R.string.adad) , String.valueOf(mojodiAnbarakModels.get(position).getMandehMojodi_Adad())));

        int countCarton = mojodiAnbarakModels.get(position).getMandehMojodi_Karton();
        int countBasteh = mojodiAnbarakModels.get(position).getMandehMojodi_Basteh();
        int countAdad = mojodiAnbarakModels.get(position).getMandehMojodi_Adad();

        if (countCarton == 0 && countBasteh == 0 && countAdad == 0)
        {
            holder.layStatus.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
        }
        else if (countCarton == 0 && countBasteh == 0 && countAdad < 10)
        {
            holder.layStatus.setBackgroundColor(context.getResources().getColor(R.color.colorMediumStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorMediumStatus));
        }
        else
        {
            holder.layStatus.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
        }

        if (mojodiAnbarakModels.get(position).getIsAdamForosh() == 0)
        {
            holder.layRoot.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }
        else
        {
            holder.layRoot.setBackgroundColor(context.getResources().getColor(R.color.colorRedCardBackground));
        }

		
        try
        {
            //Todo
            Glide.with(context)
                    .load(allKalaPhoto.get(mojodiAnbarakModels.get(position).getCcKalaCode()))
                    .placeholder(R.drawable.nopic_whit)
                    .into(holder.imgKalaImage);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            holder.imgKalaImage.setImageResource(R.drawable.nopic_whit);
            holder.imgKalaImage.setImageResource(R.drawable.nopic_whit);
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, exception.toString(), "RptMojodiAnbarakAdapter" , "" , "onBindViewHolder" , "");
        }
		
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount()
    {
        return mojodiAnbarakModels.size();
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
        RelativeLayout layRoot;
        LinearLayout layStatus;
        LinearLayout layStatusLeft;
		ImageView imgKalaImage;
        TextView lblCodeKala;
        TextView lblNameKala;
        TextView lblNameSazman;
        TextView lblCartonCount;
        TextView lblBastehCount;
        TextView lblAdadCount;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = itemView.findViewById(R.id.layRoot);
            layStatus = itemView.findViewById(R.id.layStatus);
            layStatusLeft = itemView.findViewById(R.id.layStatusLeft);
			imgKalaImage = itemView.findViewById(R.id.imgProductPic);																	 
            lblCodeKala = itemView.findViewById(R.id.lblCodeKala);
            lblNameKala = itemView.findViewById(R.id.lblNameKala);
            lblNameSazman = itemView.findViewById(R.id.lblNameSazman);
            lblCartonCount = itemView.findViewById(R.id.lblCarton);
            lblBastehCount = itemView.findViewById(R.id.lblBasteh);
            lblAdadCount = itemView.findViewById(R.id.lblAdad);

            lblCodeKala.setTypeface(font);
            lblNameKala.setTypeface(font);
            lblNameSazman.setTypeface(font);
            lblCartonCount.setTypeface(font);
            lblBastehCount.setTypeface(font);
            lblAdadCount.setTypeface(font);
        }

    }




    public void setKalaImages(ArrayList<KalaPhotoModel> kalaPhotoModels) {
        for (KalaPhotoModel kalaPhotoModel : kalaPhotoModels)
        {
            allKalaPhoto.put(kalaPhotoModel.getCcKalaCodeDb(), kalaPhotoModel.getImageDb());
        }


    }

}
