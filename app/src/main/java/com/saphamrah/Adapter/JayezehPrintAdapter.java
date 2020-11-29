package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.R;
import java.util.ArrayList;

public class JayezehPrintAdapter extends RecyclerView.Adapter<JayezehPrintAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<DarkhastFaktorJayezehModel> models;
    private int noePrintFaktor;

    public JayezehPrintAdapter(Context context, ArrayList<DarkhastFaktorJayezehModel> models, int noePrintFaktor)
    {
        this.context = context;
        this.models = models;
        this.noePrintFaktor = noePrintFaktor;
    }


    @NonNull
    @Override
    public JayezehPrintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.jayezeh_print_customlist, parent , false);
        return new JayezehPrintAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JayezehPrintAdapter.ViewHolder holder, int position)
    {
        holder.lblSharh.setText(models.get(position).getSharh());
        holder.lblTedad.setText(String.valueOf(models.get(position).getTedad()));
        if (noePrintFaktor == -1)
        {
            holder.lblSoodeJayezeh.setVisibility(View.GONE);
        }
        else
        {
            holder.lblSoodeJayezeh.setText(String.valueOf(models.get(position).getTedad() * models.get(position).getMablaghMasrafKonandeh()));
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView lblSharh;
        private TextView lblTedad;
        private TextView lblSoodeJayezeh;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblSharh = itemView.findViewById(R.id.lblSharh);
            lblTedad = itemView.findViewById(R.id.lblTedad);
            lblSoodeJayezeh = itemView.findViewById(R.id.lblSoodeJayezeh);

            lblSharh.setTypeface(font);
            lblTedad.setTypeface(font);
            lblSoodeJayezeh.setTypeface(font);

            if (noePrintFaktor == 2)
            {
                lblSharh.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.FontSize30));
                lblTedad.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.FontSize30));
                lblSoodeJayezeh.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.FontSize30));
            }
        }
    }

}
