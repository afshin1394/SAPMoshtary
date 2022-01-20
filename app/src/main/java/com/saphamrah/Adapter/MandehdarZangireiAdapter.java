package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MandehdarZangireiAdapter extends RecyclerSwipeAdapter<MandehdarZangireiAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<RptMandehdarModel> models;
    private int lastPosition = -1;
    private DateUtils dateUtils = new DateUtils();
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_MILISECONDS());
    private DecimalFormat formatter = new DecimalFormat("#,###,###");
    public MandehdarZangireiAdapter(Context context, ArrayList<RptMandehdarModel> models, OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.madehdar_zangirei_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);

        holder.lblRadif.setText(String.valueOf(position + 1));
        holder.txtCustomerFullNameCode.setText(String.format("%1$s - %2$s" , models.get(position).getCodeMoshtary() ,  models.get(position).getNameMoshtary()));
        holder.txtShomareFaktor.setText(String.valueOf(models.get(position).getShomarehFaktor()));
        try {
            Date date = sdf.parse(models.get(position).getTarikhFaktor());
            String tarikhFaktor = (String) DateFormat.format(Constants.DATE_SHORT_FORMAT_WITH_SLASH() , date);
            holder.txtTarikhFaktor.setText(dateUtils.gregorianWithSlashToPersianSlash(tarikhFaktor));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtMablaghFaktor.setText(String.format(" %1$s %2$s",  formatter.format((int) models.get(position).getMablaghKhalesFaktor()), context.getResources().getString(R.string.rial)));
        holder.txtMablaghMandehFaktor.setText(String.format(" %1$s %2$s",  formatter.format((int) models.get(position).getMablaghMandehMoshtary()), context.getResources().getString(R.string.rial)));


        holder.bind(models.get(position) , listener);
        setAnimation(holder.itemView, position);
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
        private SwipeLayout swipeLayout;
        private TextView lblRadif;
        private TextView txtCustomerFullNameCode;
        private TextView txtShomareFaktor;
        private TextView txtTarikhFaktor;
        private TextView txtMablaghFaktor;
        private TextView txtMablaghMandehFaktor;
        private ImageView imgAddToRequestList;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            lblRadif = view.findViewById(R.id.lblRadif);
            txtCustomerFullNameCode = view.findViewById(R.id.txtCustomerFullNameCode);
            txtShomareFaktor = view.findViewById(R.id.txtShomareFaktor);
            txtTarikhFaktor = view.findViewById(R.id.txtTarikhFaktor);
            txtMablaghFaktor = view.findViewById(R.id.txtMablaghFaktor);
            txtMablaghMandehFaktor = view.findViewById(R.id.txtMablaghMandehFaktor);
            imgAddToRequestList = view.findViewById(R.id.imgAddToRequestList);

            lblRadif.setTypeface(font);
            txtCustomerFullNameCode.setTypeface(font);
            txtShomareFaktor.setTypeface(font);
            txtTarikhFaktor.setTypeface(font);
            txtMablaghFaktor.setTypeface(font);
            txtMablaghMandehFaktor.setTypeface(font);
        }

        void bind(RptMandehdarModel model , final OnItemClickListener listener)
        {
            imgAddToRequestList.setOnClickListener(v -> {
                listener.onItemClick(model);
                swipeLayout.close(true);
            });
        }

    }


    public interface OnItemClickListener
    {
        void onItemClick(RptMandehdarModel model);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }



}
