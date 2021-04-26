package com.saphamrah.Adapter.invoiceSettelment;

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
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SettlementAdapter extends RecyclerSwipeAdapter<SettlementAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> models;
    private int lastPosition = 1;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    public SettlementAdapter(Context context, ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> models, OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settlement_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        holder.lblNoeVosol.setText(models.get(position).getNameNoeVosol());
        holder.lblMablagh.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.cost), formatter.format(models.get(position).getMablaghDariaftPardakht())));
        holder.lblMablaghTakhsis.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.mablaghTakhsis), formatter.format(models.get(position).getMablagh())));
        if (models.get(position).getTarikhSanadShamsi() == null || models.get(position).getTarikhSanadShamsi().trim().equalsIgnoreCase("null") || models.get(position).getTarikhSanadShamsi().trim().equals(""))
        {
            holder.lblDateSanad.setVisibility(View.GONE);
        }
        else
        {
            holder.lblDateSanad.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.dateSanad), models.get(position).getTarikhSanadShamsi()));
        }
        if (models.get(position).getShomarehSanad() == null || models.get(position).getShomarehSanad().trim().equalsIgnoreCase("null") || models.get(position).getShomarehSanad().trim().equals(""))
        {
            holder.lblNumber.setVisibility(View.GONE);
        }
        else
        {
            holder.lblNumber.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.number), models.get(position).getShomarehSanad()));
        }
        if (models.get(position).getExtraProp_IsSend() == 0)
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorBadStatus));
            holder.swipe.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layDelete));
        }
        else if (models.get(position).getExtraProp_IsSend() == 1)
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGoodStatus));
            holder.swipe.addDrag(SwipeLayout.DragEdge.Right , null);
        }

        /**
         * set color for is taeidVosol
         */
        if (models.get(position).getIsTaeedShodeh() == 1){
            holder.lay_for_color.setBackgroundResource(R.color.tasvieKamel);
        } else {
            holder.lay_for_color.setBackgroundResource(R.color.marjoeeKamel);
        }

        /**
         * ExtraProp_IsDirkard == 1 have dirkard
         * ExtraProp_IsDirkard == 0 have not dirkard
         */
        if (models.get(position).getExtraProp_IsDirkard() == 1 || models.get(position).getExtraProp_IsTajil() == 1){
            holder.swipe.addDrag(SwipeLayout.DragEdge.Right , null);
        } else {
            holder.swipe.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layDelete));
        }

        /**
         * ExtraProp_IsSend == 1 dont show delete
         * ExtraProp_IsSend == 0 show delete
         */
        if (models.get(position).getExtraProp_IsSend() == 1){
            holder.swipe.addDrag(SwipeLayout.DragEdge.Right , null);
        } else {
            holder.swipe.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layDelete));
        }

        if (models.get(position).getExtraProp_IsDirkard() == 1 || models.get(position).getExtraProp_IsTajil() == 1){
            holder.bind(position ,false, listener);
        } else {
            holder.bind(position, true , listener);
        }
        Log.i("asdasdas", "getSweep: " + holder.swipe.getDragEdge().name() + lastPosition);
        lastPosition++;
        holder.lay_for_color.setOnClickListener(v->{
            Log.i("asdasdas", "click : " + models.get(position).getMablagh());
            Log.i("asdasdas", "click getSweep: " + holder.swipe.getDragEdge().toString());
        });
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipe;
        private TextView lblNoeVosol;
        private TextView lblMablagh;
        private TextView lblMablaghTakhsis;
        private TextView lblNumber;
        private TextView lblDateSanad;
        private ImageView imgDelete;
        private LinearLayout layDelete;
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;
        private LinearLayout lay_for_color;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipe = view.findViewById(R.id.swipe);
            lblNoeVosol = view.findViewById(R.id.lblNoeVosol);
            lblMablagh = view.findViewById(R.id.lblMablagh);
            lblMablaghTakhsis = view.findViewById(R.id.lblMablaghTakhsis);
            lblNumber = view.findViewById(R.id.lblNumber);
            lblDateSanad = view.findViewById(R.id.lblDateSanad);
            imgDelete = view.findViewById(R.id.imgDelete);
            layDelete = view.findViewById(R.id.layDelete);
            layStatusRight = view.findViewById(R.id.layStatusRight);
            layStatusLeft = view.findViewById(R.id.layStatusLeft);
            lay_for_color = view.findViewById(R.id.lay_for_color);

            lblNoeVosol.setTypeface(font);
            lblMablagh.setTypeface(font);
            lblMablaghTakhsis.setTypeface(font);
            lblNumber.setTypeface(font);
            lblDateSanad.setTypeface(font);
        }

        void bind(final int position ,boolean canDelete , final OnItemClickListener listener)
        {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                        listener.onItemClick(position,canDelete);
                        swipe.close(true);


                }
            });

        }




    }



    public interface OnItemClickListener
    {
        void onItemClick(int position, boolean canDelete);
    }

    @Override
    public int getSwipeLayoutResourceId(int position ) {
        return R.id.swipe;
    }

}
