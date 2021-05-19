package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.RptMojodiAnbarModel;
import com.saphamrah.R;

import java.util.ArrayList;

public class RptMojodiAnbarakTableAdapter extends RecyclerView.Adapter<RptMojodiAnbarakTableAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<RptMojodiAnbarModel> mojodiAnbarModels;
    private int lastPosition = -1;

    public RptMojodiAnbarakTableAdapter(Context context, ArrayList<RptMojodiAnbarModel> mojodiAnbarModels)
    {
        this.context = context;
        this.mojodiAnbarModels = mojodiAnbarModels;
        for (int i = 0 ; i < this.mojodiAnbarModels.size() ; i++)
        {
            if (this.mojodiAnbarModels.get(i).getRadif() == -1)
            {
                mojodiAnbarModels.remove(i);
            }
        }
        RptMojodiAnbarModel mojodiAnbarModel = new RptMojodiAnbarModel();
        mojodiAnbarModel.setRadif(-1);
        mojodiAnbarModel.setId(-1);
        mojodiAnbarModel.setCodeKala("");
        mojodiAnbarModel.setNameKala("");
        mojodiAnbarModel.setCcKalaCode(-1);
        mojodiAnbarModel.setMandehMojodi_Karton(0);
        mojodiAnbarModel.setMandehMojodi_Basteh(0);
        mojodiAnbarModel.setMandehMojodi_Adad(0);
        this.mojodiAnbarModels.add(0 , mojodiAnbarModel);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.rpt_mojodi_anbarak_table_customlist, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if (position == 0)
        {
            holder.lblCodeKala.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblNameKala.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblNameSazman.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblCarton.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblBasteh.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.lblAdad.setBackgroundResource(R.drawable.table_header_cell_bg);

            holder.lblCodeKala.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.lblNameKala.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.lblCarton.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.lblBasteh.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.lblAdad.setTextColor(context.getResources().getColor(R.color.colorWhite));


            holder.lblCodeKala.setText(context.getResources().getString(R.string.codeKala));
            holder.lblNameKala.setText(context.getResources().getString(R.string.nameKala));
            holder.lblNameKala.setText(context.getResources().getString(R.string.nameSazman));
            holder.lblCarton.setText(context.getResources().getString(R.string.carton));
            holder.lblBasteh.setText(context.getResources().getString(R.string.basteh));
            holder.lblAdad.setText(context.getResources().getString(R.string.adad));
        }
        else
        {
            int countCarton = mojodiAnbarModels.get(position).getMandehMojodi_Karton();
            int countBasteh = mojodiAnbarModels.get(position).getMandehMojodi_Basteh();
            int countAdad = mojodiAnbarModels.get(position).getMandehMojodi_Adad();

            if (countCarton == 0 && countBasteh == 0 && countAdad == 0)
            {
                holder.lblCodeKala.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblNameKala.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblNameSazman.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblCarton.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblBasteh.setBackgroundResource(R.drawable.table_content_cell_bg_red);
                holder.lblAdad.setBackgroundResource(R.drawable.table_content_cell_bg_red);
            }
            else if (countCarton == 0 && countBasteh == 0 && countAdad < 10)
            {
                holder.lblCodeKala.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblNameKala.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblNameSazman.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblCarton.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblBasteh.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
                holder.lblAdad.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
            }
            else
            {
                holder.lblCodeKala.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblNameKala.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblNameSazman.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblCarton.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblBasteh.setBackgroundResource(R.drawable.table_content_cell_bg_green);
                holder.lblAdad.setBackgroundResource(R.drawable.table_content_cell_bg_green);
            }

            holder.lblCodeKala.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblNameKala.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblNameSazman.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblCarton.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblBasteh.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            holder.lblAdad.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));


            holder.lblCodeKala.setText(mojodiAnbarModels.get(position).getCodeKala());
            holder.lblNameKala.setText(mojodiAnbarModels.get(position).getNameKala());
            holder.lblNameSazman.setText(mojodiAnbarModels.get(position).getNameSazmanForosh());
            holder.lblCarton.setText(String.valueOf(mojodiAnbarModels.get(position).getMandehMojodi_Karton()));
            holder.lblBasteh.setText(String.valueOf(mojodiAnbarModels.get(position).getMandehMojodi_Basteh()));
            holder.lblAdad.setText(String.valueOf(mojodiAnbarModels.get(position).getMandehMojodi_Adad()));
        }
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mojodiAnbarModels.size();
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

        private TextView lblCodeKala;
        private TextView lblNameKala;
        private TextView lblNameSazman;
        private TextView lblCarton;
        private TextView lblBasteh;
        private TextView lblAdad;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblCodeKala = (TextView)itemView.findViewById(R.id.lblCodeKala);
            lblNameKala = (TextView)itemView.findViewById(R.id.lblNameKala);
            lblNameSazman = (TextView)itemView.findViewById(R.id.lblNameSazman);
            lblCarton = (TextView)itemView.findViewById(R.id.lblCarton);
            lblBasteh = (TextView)itemView.findViewById(R.id.lblBasteh);
            lblAdad = (TextView)itemView.findViewById(R.id.lblAdad);

            lblCodeKala.setTypeface(font);
            lblNameKala.setTypeface(font);
            lblNameSazman.setTypeface(font);
            lblCarton.setTypeface(font);
            lblBasteh.setTypeface(font);
            lblAdad.setTypeface(font);
        }
    }

}
