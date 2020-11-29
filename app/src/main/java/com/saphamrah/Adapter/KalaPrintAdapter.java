package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class KalaPrintAdapter extends RecyclerView.Adapter<KalaPrintAdapter.BaseViewHolder>
{

    private Context context;
    private ArrayList<KalaDarkhastFaktorSatrModel> models;
    private int noePrintFaktor; // این متغییر برای تعیین لی اوت مورد نظر برای نوع نمایش لیست کالا در فرم چاپ مورداستفاده قرار می گیرد.

    public KalaPrintAdapter(Context context, ArrayList<KalaDarkhastFaktorSatrModel> models, int noePrintFaktor)
    {
        this.context = context;
        this.models = models;
        this.noePrintFaktor = noePrintFaktor;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        if (noePrintFaktor == 1)
        {
            view = LayoutInflater.from(context).inflate(R.layout.kala_print_customlist, parent , false);
            return new ViewHolderOne(view);
        }
        else
        {
            view = LayoutInflater.from(context).inflate(R.layout.kala_print_customlist_two, parent , false);
            return new ViewHolderTwo(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        int[] counts = new PubFunc().new ConvertUnit().tedadToCartonBasteAdad(models.get(position).getTedad3(), models.get(position).getTedadDarKarton(), models.get(position).getTedadDarBasteh(), models.get(position).getAdad());
        holder.setData(counts , formatter , position);
    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    abstract class BaseViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView lblRadif;
        protected TextView lblNameKala;
        TextView lblMablagh;
        protected TextView lblCarton;
        protected TextView lblBasteh;
        protected TextView lblAdad;
        TextView lblMablaghKol;

        BaseViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblNameKala = itemView.findViewById(R.id.lblNameKala);
            lblMablagh = itemView.findViewById(R.id.lblMablagh);
            lblCarton = itemView.findViewById(R.id.lblCarton);
            lblBasteh = itemView.findViewById(R.id.lblBasteh);
            lblAdad = itemView.findViewById(R.id.lblAdad);
            lblMablaghKol = itemView.findViewById(R.id.lblMablaghKol);

            lblRadif.setTypeface(font);
            lblNameKala.setTypeface(font);
            lblMablagh.setTypeface(font);
            lblCarton.setTypeface(font);
            lblBasteh.setTypeface(font);
            lblAdad.setTypeface(font);
            lblMablaghKol.setTypeface(font);
        }

        abstract void setData(int[] counts , DecimalFormat formatter , int position);

    }


    class ViewHolderOne extends BaseViewHolder
    {
        ViewHolderOne(@NonNull View itemView)
        {
            super(itemView);
        }

        @Override
        void setData(int[] counts , DecimalFormat formatter , int position)
        {
            lblRadif.setText(String.valueOf(position+1));
            lblNameKala.setText(models.get(position).getNameKala());
            lblMablagh.setText(formatter.format((int)models.get(position).getMablaghForosh()));
            lblCarton.setText(String.valueOf(counts[0]));
            lblBasteh.setText(String.valueOf(counts[1]));
            lblAdad.setText(String.valueOf(counts[2]));
            double sumMablagh = models.get(position).getTedad3() * models.get(position).getMablaghForosh();
            lblMablaghKol.setText(formatter.format(sumMablagh));
        }
    }


    class ViewHolderTwo extends BaseViewHolder
    {
        private TextView lblMablaghTitle;
        private TextView lblCartonTitle;
        private TextView lblBastehTitle;
        private TextView lblAdadTitle;
        private TextView lblMablaghKolTitle;

        ViewHolderTwo(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblMablaghTitle = itemView.findViewById(R.id.lblMablagh);
            lblCartonTitle = itemView.findViewById(R.id.lblCarton);
            lblBastehTitle = itemView.findViewById(R.id.lblBasteh);
            lblAdadTitle = itemView.findViewById(R.id.lblAdad);
            lblMablaghKolTitle = itemView.findViewById(R.id.lblMablaghKol);

            lblMablaghTitle.setTypeface(font);
            lblCartonTitle.setTypeface(font);
            lblBastehTitle.setTypeface(font);
            lblAdadTitle.setTypeface(font);
            lblMablaghKolTitle.setTypeface(font);
        }


        @Override
        void setData(int[] counts , DecimalFormat formatter , int position)
        {
            lblRadif.setText(String.valueOf(position+1));
            lblNameKala.setText(models.get(position).getNameKala());
            lblMablagh.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.mablagh), formatter.format((int)models.get(position).getMablaghForosh())));
            lblCarton.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.carton), String.valueOf(counts[0])));
            lblBasteh.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.basteh), String.valueOf(counts[1])));
            lblAdad.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.adad), String.valueOf(counts[2])));
            double sumMablagh = models.get(position).getTedad3() * models.get(position).getMablaghForosh();
            lblMablaghKol.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.sumCost), formatter.format(sumMablagh)));
        }

    }


}
