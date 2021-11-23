package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RequestedGoodAdapter extends RecyclerSwipeAdapter<RequestedGoodAdapter.MyViewHolder>
{

    private final RequestedGoodAdapter.OnItemClickListener listener;
    private ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels;
    private boolean showSwipe;
    private Context context;
    private SelectFaktorShared shared = new SelectFaktorShared(BaseApplication.getContext());
    private  int ccNoeMoshtary = 0;
    public RequestedGoodAdapter(Context context , ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels , boolean showSwipe , OnItemClickListener listener)
    {
        this.context = context;
        this.kalaDarkhastFaktorSatrModels = kalaDarkhastFaktorSatrModels;
        this.listener = listener;
        this.showSwipe = showSwipe;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_good_customlist, parent, false);
        ccNoeMoshtary = shared.getInt(shared.getCcGorohNoeMoshtary(), -1);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String mablaghForosh = formatter.format((int)kalaDarkhastFaktorSatrModels.get(position).getMablaghForosh());

        holder.lblMablaghForosh.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.mablaghForoshUnitRial), mablaghForosh));
        holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.shomareBach), kalaDarkhastFaktorSatrModels.get(position).getShomarehBach()));
        int[] counts = new PubFunc().new ConvertUnit().tedadToCartonBasteAdad(kalaDarkhastFaktorSatrModels.get(position).getTedad3(), kalaDarkhastFaktorSatrModels.get(position).getTedadDarKarton(), kalaDarkhastFaktorSatrModels.get(position).getTedadDarBasteh(), kalaDarkhastFaktorSatrModels.get(position).getAdad());
        holder.lblCartonCount.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.carton), String.valueOf(counts[0])));
        holder.lblBasteCount.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.basteh), String.valueOf(counts[1])));
        holder.lblAdadCount.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.adad), String.valueOf(counts[2])));
        if (ccNoeMoshtary == 350){
            holder.lblCodeNameKala.setText(String.format("%1$s - %2$s - %3$s", kalaDarkhastFaktorSatrModels.get(position).getCodeKala(), kalaDarkhastFaktorSatrModels.get(position).getNameKala() , kalaDarkhastFaktorSatrModels.get(position).getBarCode()));
        } else {
            holder.lblCodeNameKala.setText(String.format("%1$s - %2$s", kalaDarkhastFaktorSatrModels.get(position).getCodeKala(), kalaDarkhastFaktorSatrModels.get(position).getNameKala()));
        }


        //set kala status asasi
        if (kalaDarkhastFaktorSatrModels.get(position).getKalaAsasi())
        {
            Log.d("RequestGoodsAdaptor l","KalaAsasi:"+ kalaDarkhastFaktorSatrModels.get(position).getKalaAsasi() );
            holder.imgStatusKala.setImageResource(R.drawable.ic_kalaasasi);
            holder.imgStatusKala.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.imgStatusKala.setVisibility(View.GONE);
        }



        if (showSwipe)
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.layDelete);
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.layJayezeh);
        }
        else
        {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , null);
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , null);
        }
        holder.bind(kalaDarkhastFaktorSatrModels.get(position), position, listener);
    }


    @Override
    public int getItemCount() {
        return kalaDarkhastFaktorSatrModels.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private TextView lblCodeNameKala;
        private TextView lblShomareBach;
        private TextView lblMablaghForosh;
        private TextView lblCartonCount;
        private TextView lblBasteCount;
        private TextView lblAdadCount;
        private ImageView imgStatusKala;
        private ImageView imgDelete;
        private LinearLayout layDelete;
        private LinearLayout layJayezeh;

        private MyViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            lblCodeNameKala = view.findViewById(R.id.lblCodeNameKala);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
            lblMablaghForosh = view.findViewById(R.id.lblMablaghForosh);
            lblCartonCount = view.findViewById(R.id.lblCarton);
            lblBasteCount = view.findViewById(R.id.lblBasteh);
            lblAdadCount = view.findViewById(R.id.lblAdad);
            imgStatusKala = view.findViewById(R.id.imgKalaAsasi);
            imgDelete = view.findViewById(R.id.imgDelete);
            layDelete = view.findViewById(R.id.layDelete);
            layJayezeh = view.findViewById(R.id.layJayezeh);

            lblCodeNameKala.setTypeface(font);
            lblShomareBach.setTypeface(font);
            lblMablaghForosh.setTypeface(font);
            lblCartonCount.setTypeface(font);
            lblBasteCount.setTypeface(font);
            lblAdadCount.setTypeface(font);
        }

        public void bind(final KalaDarkhastFaktorSatrModel kalaDarkhastFaktorSatrModels , final int position , final OnItemClickListener listener)
        {
            layDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    swipeLayout.close(true);
                    listener.onItemClick(kalaDarkhastFaktorSatrModels , position);
                }
            });
            layJayezeh.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    swipeLayout.close(true);
                    Log.d("DarkhastKala" , kalaDarkhastFaktorSatrModels.toString());
                    listener.onItemClickJayezeh(kalaDarkhastFaktorSatrModels.getCcKalaCode() , kalaDarkhastFaktorSatrModels.getTedad3() , kalaDarkhastFaktorSatrModels.getCcDarkhastFaktor() ,kalaDarkhastFaktorSatrModels.getMablaghForosh());
                }
            });
        }

    }



    public interface OnItemClickListener
    {
        void onItemClick(KalaDarkhastFaktorSatrModel kalaDarkhastFaktorSatrModels , int position);
        void onItemClickJayezeh(int CcKalaCode , int tedadKala , Long ccDarkhastFaktor , double  mablaghForosh );

    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
