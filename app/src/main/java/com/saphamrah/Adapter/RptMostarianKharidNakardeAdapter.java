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

import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.RptMoshtaryKharidNakardeModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RptMostarianKharidNakardeAdapter extends RecyclerView.Adapter<RptMostarianKharidNakardeAdapter.View_Holder_Moshtarian_Kharid_Nakarde> {
    private Context context;
    private ArrayList<RptMoshtaryKharidNakardeModel> models;
    private int lastPosition = -1;

    public RptMostarianKharidNakardeAdapter(Context context, ArrayList<RptMoshtaryKharidNakardeModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public View_Holder_Moshtarian_Kharid_Nakarde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_moshtarian_kharid_nakarde_customlist, parent, false);
        return new View_Holder_Moshtarian_Kharid_Nakarde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder_Moshtarian_Kharid_Nakarde holder, int position) {
        RptMoshtaryKharidNakardeModel rptMoshtaryKharidNakardeModel = models.get(position);
        ((View_Holder_Moshtarian_Kharid_Nakarde) holder).bind(rptMoshtaryKharidNakardeModel, position);
    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public class View_Holder_Moshtarian_Kharid_Nakarde extends RecyclerView.ViewHolder {
        LinearLayout layRoot;
        TextView lblRadif;
        TextView lblCustomerCode;
        TextView lblCustomerName;
        TextView lblTarikhFaktor;

        public View_Holder_Moshtarian_Kharid_Nakarde(@NonNull View itemView) {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            layRoot = itemView.findViewById(R.id.layRoot);
            lblRadif = itemView.findViewById(R.id.lblRadif);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblTarikhFaktor = itemView.findViewById(R.id.lblTarikhFaktor);
            lblRadif.setTypeface(font);
            lblCustomerCode.setTypeface(font);
            lblCustomerName.setTypeface(font);
            lblTarikhFaktor.setTypeface(font);

        }

        public void bind(RptMoshtaryKharidNakardeModel holder, int position) {
            lblRadif.setText(String.valueOf(position + 1));
            lblRadif.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            lblCustomerCode.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            lblCustomerName.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));
            lblTarikhFaktor.setBackground(context.getResources().getDrawable(R.drawable.table_content_cell_bg));

            lblRadif.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            lblCustomerCode.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            lblCustomerName.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            lblTarikhFaktor.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));

            String tarikhFaktor = models.get(position).getTarikhFaktor();
            if (tarikhFaktor != null && tarikhFaktor.length() > 0){
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                    Date dateFaktor = sdf.parse(models.get(position).getTarikhFaktor());
                    tarikhFaktor = new PubFunc().new DateUtils().gregorianToPersianDateTime(dateFaktor);
                    tarikhFaktor = tarikhFaktor.substring(0 , tarikhFaktor.indexOf('-'));

                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    tarikhFaktor = models.get(position).getTarikhFaktor().substring(0 , models.get(position).getTarikhFaktor().indexOf('T'));
                }
            }
            lblCustomerCode.setText(String.valueOf(models.get(position).getCodeMoshtary()));
            lblCustomerName.setText(models.get(position).getNameMoshtary());
            lblTarikhFaktor.setText(tarikhFaktor);
            setAnimation(itemView, position);

        }


    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
