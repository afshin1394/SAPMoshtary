package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.VarizBeBankModel;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VarizBeBankAdapter extends RecyclerSwipeAdapter<VarizBeBankAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<VarizBeBankModel> models;
    private int lastPosition = -1;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
    DateUtils dateUtils = new DateUtils();

    public VarizBeBankAdapter(Context context, ArrayList<VarizBeBankModel> models, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variz_be_bank_customlist, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, null);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.itemView.findViewById(R.id.layRigh));

        String tarikhSanad = "";
        try {
            Date dateSanad = sdf.parse(models.get(position).getExtraProp_TarikhSanad());
            tarikhSanad = dateUtils.gregorianToPersianDateWithoutTime(dateSanad);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        holder.txtShomareSanad.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareSanad), models.get(position).getExtraProp_ShomarehSanad()));
        holder.txtDateSanad.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.taakhirSanad), tarikhSanad));
        holder.txtMablagh.setText(String.format("%1$s : %2$s %3$s", context.getResources().getString(R.string.mablagh), formatter.format(models.get(position).getExtraProp_MablaghSabtShode()), context.getResources().getString(R.string.rial)));
        holder.txtBank.setText(String.format("%1$s %2$s - %3$s %4$s", context.getResources().getString(R.string.bank), models.get(position).getExtraProp_NameBankSanad(), context.getResources().getString(R.string.shobe) , models.get(position).getExtraProp_NameShobehSanad()));
        holder.bind(position, listener);


        setAnimation(holder.itemView, position);
    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipeLayout;

        private TextView txtShomareSanad;
        private TextView txtDateSanad;
        private TextView txtMablagh;
        private TextView txtBank;
        private RelativeLayout laySend;

        public ViewHolder(View view) {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            txtShomareSanad = view.findViewById(R.id.txtShomareSanad);
            txtDateSanad = view.findViewById(R.id.txtDateSanad);
            txtMablagh = view.findViewById(R.id.txtMablagh);
            txtBank = view.findViewById(R.id.txtBank);
            laySend = view.findViewById(R.id.laySend);

            txtShomareSanad.setTypeface(font);
            txtDateSanad.setTypeface(font);
            txtMablagh.setTypeface(font);
            txtBank.setTypeface(font);

        }

        void bind( int position,  OnItemClickListener listener) {

            laySend.setOnClickListener(v->{
                listener.onItemClick(Constants.SEND() , position);
                swipeLayout.close();
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick( int operation , int position);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
