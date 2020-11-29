package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TakhfifPrintAdatper extends RecyclerView.Adapter<TakhfifPrintAdatper.BaseViewHolder>
{

    private Context context;
    private ArrayList<DarkhastFaktorTakhfifModel> models;
    private int noePrintFaktor;

    public TakhfifPrintAdatper(Context context, ArrayList<DarkhastFaktorTakhfifModel> models, int noePrintFaktor)
    {
        this.context = context;
        this.models = models;
        this.noePrintFaktor = noePrintFaktor;
    }


    @NonNull
    @Override
    public TakhfifPrintAdatper.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        if (noePrintFaktor == 1)
        {
            view = LayoutInflater.from(context).inflate(R.layout.takhfif_print_customlist, parent , false);
            return new TakhfifPrintAdatper.ViewHolderOne(view);
        }
        else //if (noePrintFaktor == 2)
        {
            view = LayoutInflater.from(context).inflate(R.layout.takhfif_print_customlist_two, parent , false);
            return new TakhfifPrintAdatper.ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TakhfifPrintAdatper.BaseViewHolder holder, int position)
    {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    abstract class BaseViewHolder extends RecyclerView.ViewHolder
    {
        TextView lblSharh;
        TextView lblDarsad;
        TextView lblMablagh;

        BaseViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblSharh = itemView.findViewById(R.id.lblSharhTakhfif);
            lblDarsad = itemView.findViewById(R.id.lblDarsadTakhfif);
            lblMablagh = itemView.findViewById(R.id.lblMablaghTakhfif);

            lblSharh.setTypeface(font);
            lblDarsad.setTypeface(font);
            lblMablagh.setTypeface(font);
        }


        abstract protected void setData(int position);

    }


    class ViewHolderOne extends BaseViewHolder
    {

        ViewHolderOne(@NonNull View itemView)
        {
            super(itemView);
        }

        @Override
        protected void setData(int position)
        {
            lblSharh.setText(models.get(position).getSharhTakhfif());
            lblDarsad.setText(String.format("%1$s %2$s", models.get(position).getDarsadTakhfif() , context.getResources().getString(R.string.percentageSign)));
            lblMablagh.setText(new DecimalFormat("#,###,###").format((int)models.get(position).getMablaghTakhfif()));
        }
    }


    class ViewHolderTwo extends BaseViewHolder
    {

        ViewHolderTwo(@NonNull View itemView)
        {
            super(itemView);
        }

        @Override
        protected void setData(int position)
        {
            lblSharh.setText(models.get(position).getSharhTakhfif());
            lblDarsad.setText(String.format("%1$s : %2$s %3$s", context.getResources().getString(R.string.darsadTakhfif) , models.get(position).getDarsadTakhfif() , context.getResources().getString(R.string.percentageSign)));
            lblMablagh.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.mablaghTakhfif) ,new DecimalFormat("#,###,###").format((int)models.get(position).getMablaghTakhfif())));
        }
    }


}
