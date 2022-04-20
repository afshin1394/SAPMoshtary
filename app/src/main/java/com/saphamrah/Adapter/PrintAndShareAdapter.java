package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.PrintFaktorModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class PrintAndShareAdapter extends RecyclerSwipeAdapter<PrintAndShareAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<PrintFaktorModel> models;


    public PrintAndShareAdapter(Context context, ArrayList<PrintFaktorModel> models, OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.models = models;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.print_share_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));

        holder.lblRadif.setText(String.valueOf(position+1));
        holder.lblCustomerNameFamily.setText(String.format("%1$s - %2$s" , models.get(position).getCodeMoshtary() , models.get(position).getNameMoshtary()));
        holder.lblInvoiceCostAndShomarehFaktor.setText(String.format("%1$s : %2$s" , BaseApplication.getContext().getResources().getString(R.string.shomareFaktor),models.get(position).getShomarehFaktor()));


        holder.bind(position , listener);
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private TextView lblRadif;
        private TextView lblCustomerNameFamily;
        private TextView lblInvoiceCostAndShomarehFaktor;


        private ImageView imgPrint;

        private ImageView imgShare;

        private ImageView imgImage;

        private RelativeLayout layPrint;
        private RelativeLayout layShare;
        private RelativeLayout layImage;


        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            lblRadif = view.findViewById(R.id.lblRadif);
            lblCustomerNameFamily = view.findViewById(R.id.lblCustomerFullNameCode);
            lblInvoiceCostAndShomarehFaktor = view.findViewById(R.id.lblInvoiceCostAndShomarehFaktor);
            imgPrint = view.findViewById(R.id.imgPrint);
            imgShare = view.findViewById(R.id.imgShare);
            imgImage = view.findViewById(R.id.imgImage);


            layPrint = view.findViewById(R.id.layPrint);
            layShare = view.findViewById(R.id.layShare);
            layImage = view.findViewById(R.id.layImage);


            lblRadif.setTypeface(font);
            lblCustomerNameFamily.setTypeface(font);
            lblInvoiceCostAndShomarehFaktor.setTypeface(font);
        }



        public void bind(final int position , final OnItemClickListener listener)
        {


            imgPrint.setOnClickListener(v -> {
                listener.onItemClick(Constants.PRINT(), position,imgPrint);
                swipeLayout.close(true);
            });

            imgShare.setOnClickListener(v -> {
                listener.onItemClick(Constants.SHARE, position,imgShare);
                swipeLayout.close(true);
            });
            imgImage.setOnClickListener(v->{
                listener.onItemClick(Constants.IMAGE , position,imgImage);
                swipeLayout.close(true);
            });
        }
    }


    public interface OnItemClickListener
    {
        void onItemClick(int action , int position , ImageView btn);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}
