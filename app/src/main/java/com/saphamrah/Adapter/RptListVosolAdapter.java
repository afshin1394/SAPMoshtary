package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.RptListVosolModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptListVosolAdapter extends RecyclerView.Adapter<RptListVosolAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<RptListVosolModel> models;
    private int lastPosition = -1;

    public RptListVosolAdapter(Context context, ArrayList<RptListVosolModel> models)
    {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_list_vosol_table_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (models.get(position).getNameMoshtary().equals(""))
        {
            /*holder.lblRadif.setText("");
            holder.lblRadif.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));*/
            holder.lblCustomerName.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblShomareFaktor.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblMablaghFaktor.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblMandeAzGhabl.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblTaakhir.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblMarjoee.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblNaghd.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblPOS.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblFish.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblCheck.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblJamDariafti.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblMandehFaktor.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
            holder.lblBestankari.setBackgroundColor(context.getResources().getColor(R.color.colorListHeaderBackground));
        }
        else
        {
            //holder.lblRadif.setText(String.valueOf(position+1));

            if(models.get(position).getMandehNahaee() == 0)
            {
                setRowColorTasvieh(holder);
            }
            else if(models.get(position).getMablaghBestankari() > 0 )
            {
                setRowColorBestankari(holder);
            }
            else if(models.get(position).getMandehNahaee() > 0 && models.get(position).getMandehNahaee() != models.get(position).getMablaghMandehFaktor())
            {
                setRowColorMandehDar(holder);
            }
            else if(models.get(position).getMablaghMarjoee() != 0)
            {
                setRowColorMarjoee(holder);
            }
            else if(models.get(position).getMablaghResid() != 0)
            {
                setRowColorResid(holder);
            }
            else
            {
                setRowColorNormal(holder);
            }
        }

        //holder.lblRadif.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblCustomerName.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblShomareFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblMablaghFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblMandeAzGhabl.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblTaakhir.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblMarjoee.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblNaghd.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblPOS.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblFish.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblCheck.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblJamDariafti.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblMandehFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        holder.lblBestankari.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));


        holder.lblCustomerName.setText(models.get(position).getNameMoshtary());
        holder.lblShomareFaktor.setText(String.valueOf(models.get(position).getShomarehFaktor()));
        holder.lblMablaghFaktor.setText(formatter.format((int)models.get(position).getMablaghKhalesFaktor()));
        holder.lblMandeAzGhabl.setText(formatter.format((int)models.get(position).getMablaghMandehFaktor()));
        holder.lblTaakhir.setText(formatter.format((int)models.get(position).getMablaghDirKard()));
        holder.lblMarjoee.setText(formatter.format((int)models.get(position).getMablaghMarjoee()));
        holder.lblNaghd.setText(formatter.format((int)models.get(position).getMablaghNaghd()));
        holder.lblPOS.setText(formatter.format((int)models.get(position).getMablaghPos()));
        holder.lblFish.setText(formatter.format((int)models.get(position).getMablaghFish()));
        holder.lblCheck.setText(formatter.format((int)models.get(position).getMablaghCheck()));
        holder.lblJamDariafti.setText(formatter.format((int)models.get(position).getJamDariafti()));
        holder.lblMandehFaktor.setText(formatter.format((int)models.get(position).getMandehNahaee()));
        holder.lblBestankari.setText(formatter.format((int)models.get(position).getMablaghBestankari()));

        setAnimation(holder.itemView, position);
    }

    private void setRowColorTasvieh(ViewHolder holder)
    {
        //holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblShomareFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblMandeAzGhabl.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblTaakhir.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblMarjoee.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblNaghd.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblPOS.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblFish.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblCheck.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblJamDariafti.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblMandehFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_green);
        holder.lblBestankari.setBackgroundResource(R.drawable.table_content_cell_bg_green);
    }

    private void setRowColorBestankari(ViewHolder holder)
    {
        //holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblShomareFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblMandeAzGhabl.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblTaakhir.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblMarjoee.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblNaghd.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblPOS.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblFish.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblCheck.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblJamDariafti.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblMandehFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
        holder.lblBestankari.setBackgroundResource(R.drawable.table_content_cell_bg_gray);
    }

    private void setRowColorMandehDar(ViewHolder holder)
    {
        //holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblShomareFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblMandeAzGhabl.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblTaakhir.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblMarjoee.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblNaghd.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblPOS.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblFish.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblCheck.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblJamDariafti.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblMandehFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
        holder.lblBestankari.setBackgroundResource(R.drawable.table_content_cell_bg_orange);
    }

    private void setRowColorMarjoee(ViewHolder holder)
    {
        //holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblShomareFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblMandeAzGhabl.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblTaakhir.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblMarjoee.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblNaghd.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblPOS.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblFish.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblCheck.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblJamDariafti.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblMandehFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_red);
        holder.lblBestankari.setBackgroundResource(R.drawable.table_content_cell_bg_red);
    }

    private void setRowColorResid(ViewHolder holder)
    {
        //holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblShomareFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblMandeAzGhabl.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblTaakhir.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblMarjoee.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblNaghd.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblPOS.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblFish.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblCheck.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblJamDariafti.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblMandehFaktor.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
        holder.lblBestankari.setBackgroundResource(R.drawable.table_content_cell_bg_yellow);
    }

    private void setRowColorNormal(ViewHolder holder)
    {
        //holder.lblRadif.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblCustomerName.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblShomareFaktor.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblMablaghFaktor.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblMandeAzGhabl.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblTaakhir.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblMarjoee.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblNaghd.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblPOS.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblFish.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblCheck.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblJamDariafti.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblMandehFaktor.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.lblBestankari.setBackgroundResource(R.drawable.table_content_cell_bg);
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
        LinearLayout layRoot;
        //TextView lblRadif;
        TextView lblCustomerName;
        TextView lblShomareFaktor;
        TextView lblMablaghFaktor;
        TextView lblMandeAzGhabl;
        TextView lblTaakhir;
        TextView lblMarjoee;
        TextView lblNaghd;
        TextView lblPOS;
        TextView lblFish;
        TextView lblCheck;
        TextView lblJamDariafti;
        TextView lblMandehFaktor;
        TextView lblBestankari;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = itemView.findViewById(R.id.layRoot);
            //lblRadif = itemView.findViewById(R.id.lblRadif);
            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblShomareFaktor = itemView.findViewById(R.id.lblShomareFaktor);
            lblMablaghFaktor = itemView.findViewById(R.id.lblMablaghFaktor);
            lblMandeAzGhabl = itemView.findViewById(R.id.lblMandeAzGhabl);
            lblTaakhir = itemView.findViewById(R.id.lblTaakhir);
            lblMarjoee = itemView.findViewById(R.id.lblMarjoee);
            lblNaghd = itemView.findViewById(R.id.lblNaghd);
            lblPOS = itemView.findViewById(R.id.lblPOS);
            lblFish = itemView.findViewById(R.id.lblFish);
            lblCheck = itemView.findViewById(R.id.lblCheck);
            lblJamDariafti = itemView.findViewById(R.id.lblJamDariafti);
            lblMandehFaktor = itemView.findViewById(R.id.lblMandehFaktor);
            lblBestankari = itemView.findViewById(R.id.lblBestankari);

            //lblRadif.setTypeface(font);
            lblCustomerName.setTypeface(font);
            lblShomareFaktor.setTypeface(font);
            lblMablaghFaktor.setTypeface(font);
            lblMandeAzGhabl.setTypeface(font);
            lblTaakhir.setTypeface(font);
            lblMarjoee.setTypeface(font);
            lblNaghd.setTypeface(font);
            lblPOS.setTypeface(font);
            lblFish.setTypeface(font);
            lblCheck.setTypeface(font);
            lblJamDariafti.setTypeface(font);
            lblMandehFaktor.setTypeface(font);
            lblBestankari.setTypeface(font);
        }

    }

}
