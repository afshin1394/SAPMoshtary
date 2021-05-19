package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaMojodiZaribModel;
import java.text.DecimalFormat;
import java.util.ArrayList;



public class RequestGoodsListAdapter extends RecyclerSwipeAdapter<RequestGoodsListAdapter.MyViewHolder>
{

    private final OnItemClickListener listener;
    private ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels;
    private boolean showSwipe;
    private Context context;
    private static int lastSelectedItem; // todo Add Static

    public RequestGoodsListAdapter(Context context , ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels , boolean showSwipe, OnItemClickListener listener)
    {
        this.context = context;
        this.kalaMojodiZaribModels = kalaMojodiZaribModels;
        this.listener = listener;
        this.showSwipe = showSwipe;
        lastSelectedItem = -1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_goods_list_customlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);
        String mablaghForosh = formatter.format((int)kalaMojodiZaribModels.get(position).getGheymatForosh());
        holder.lblCodeNameKala.setText(String.format("%1$s - %2$s", kalaMojodiZaribModels.get(position).getCodeKala(), kalaMojodiZaribModels.get(position).getNameKala()));
        holder.lblMablaghForosh.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.mablaghForoshUnitRial), mablaghForosh));
        if (kalaMojodiZaribModels.get(position).getShomarehBach() !=null) {
            if (!kalaMojodiZaribModels.get(position).getShomarehBach().equals("")){
                holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.shomareBach), kalaMojodiZaribModels.get(position).getShomarehBach()));
            } else {
                holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.gheymatMasrafKonandeRial),  formatter.format(kalaMojodiZaribModels.get(position).getMablaghMasrafKonandeh())));
            }
        } else {
            holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.gheymatMasrafKonande), kalaMojodiZaribModels.get(position).getMablaghMasrafKonandeh()));
        }

            int[] counts = new PubFunc().new ConvertUnit().tedadToCartonBasteAdad(kalaMojodiZaribModels.get(position).getTedad(), kalaMojodiZaribModels.get(position).getTedadDarKarton(), kalaMojodiZaribModels.get(position).getTedadDarBasteh(), kalaMojodiZaribModels.get(position).getAdad());
        holder.lblCartonCount.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.carton), String.valueOf(counts[0])));
        holder.lblBasteCount.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.basteh), String.valueOf(counts[1])));
        holder.lblAdadCount.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.adad), String.valueOf(counts[2])));



        //set kala status asasi
        if (kalaMojodiZaribModels.get(position).getKalaAsasi())
        {
            Log.d("RequestGoodsAdaptor t","KalaAsasi:"+ kalaMojodiZaribModels.get(position).getCodeKala() + "-" + kalaMojodiZaribModels.get(position).getKalaAsasi() );
            holder.imgStatusKala.setImageResource(R.drawable.ic_kalaasasi);
            holder.imgStatusKala.setVisibility(View.VISIBLE);
        }

        //set kala status pishnahadi
        else if (kalaMojodiZaribModels.get(position).getTedadPishnahadi()>0)
        {
            Log.d("RequestGoodsAdaptor t","KalaPishnahadi:"+ kalaMojodiZaribModels.get(position).getCodeKala() + "-" + kalaMojodiZaribModels.get(position).getTedadPishnahadi() );
            holder.imgStatusKala.setImageResource(R.drawable.ic_kalapishnahadi);
            holder.imgStatusKala.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.imgStatusKala.setVisibility(View.GONE);
        }


        // set selected item
        if (position == lastSelectedItem)
        {
            holder.layRoot.setBackgroundColor(context.getResources().getColor(R.color.colorLightGreen));
        }
        else
        {
            holder.layRoot.setBackgroundColor(Color.WHITE);
        }
        holder.bind(kalaMojodiZaribModels.get(position), position, listener);
    }


    @Override
    public int getItemCount() {
        return kalaMojodiZaribModels.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private LinearLayout layRoot;
        private TextView lblCodeNameKala;
        private TextView lblShomareBach;
        private TextView lblMablaghForosh;
        private TextView lblCartonCount;
        private TextView lblBasteCount;
        private TextView lblAdadCount;
        private ImageView imgStatusKala;
        private LinearLayout laySelected;

        private MyViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            layRoot = view.findViewById(R.id.layRoot);
            lblCodeNameKala = view.findViewById(R.id.lblCodeNameKala);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
            lblMablaghForosh = view.findViewById(R.id.lblMablaghForosh);
            lblCartonCount = view.findViewById(R.id.lblCarton);
            lblBasteCount = view.findViewById(R.id.lblBasteh);
            lblAdadCount = view.findViewById(R.id.lblAdad);
            imgStatusKala = view.findViewById(R.id.imgKalaAsasi);
            laySelected = view.findViewById(R.id.laySelected);

            lblCodeNameKala.setTypeface(font);
            lblShomareBach.setTypeface(font);
            lblMablaghForosh.setTypeface(font);
            lblCartonCount.setTypeface(font);
            lblBasteCount.setTypeface(font);
            lblAdadCount.setTypeface(font);
        }

        public void bind(final KalaMojodiZaribModel kalaMojodiZaribModel , final int position , final OnItemClickListener listener)
        {
            swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v)
                {
                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(kalaMojodiZaribModel , position);
                }
            });
        }

    }



    public interface OnItemClickListener
    {
        void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}
