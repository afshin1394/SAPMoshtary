package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckBargashtiAdapter extends RecyclerSwipeAdapter<CheckBargashtiAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<BargashtyModel> bargashtyModels;
    private int lastPosition = -1;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());

    public CheckBargashtiAdapter(Context context, ArrayList<BargashtyModel> bargashtyModels, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.bargashtyModels = bargashtyModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_bargashti_customlist, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.itemView.findViewById(R.id.layLeft));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.itemView.findViewById(R.id.layRight));

        String tarikhSanad = bargashtyModels.get(position).getTarikhSanad();
        try {
            Date dateSanad = sdf.parse(bargashtyModels.get(position).getTarikhSanad());
            tarikhSanad = new PubFunc().new DateUtils().gregorianToPersianDateTime(dateSanad);
            tarikhSanad = tarikhSanad.substring(0, tarikhSanad.indexOf('-'));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        holder.txtNameMoshtary.setText(bargashtyModels.get(position).getCodeMoshtary() + " - " + bargashtyModels.get(position).getNameMoshtary());
        holder.txtShomareSanad.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareSanad), bargashtyModels.get(position).getShomarehSanad()));
        holder.txtDateSanad.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.taakhirSanad), tarikhSanad));
        holder.txtMablagh.setText(String.format("%1$s : %2$s %3$s", context.getResources().getString(R.string.mablagh), formatter.format(bargashtyModels.get(position).getMablagh()), context.getResources().getString(R.string.rial)));
        holder.txtMablaghMandeh.setText(String.format("%1$s : %2$s %3$s", context.getResources().getString(R.string.mandeMandeh), formatter.format(bargashtyModels.get(position).getMablaghMandeh()), context.getResources().getString(R.string.rial)));
        holder.bind(position, listener);


        setAnimation(holder.itemView, position);
    }


    @Override
    public int getItemCount() {
        return bargashtyModels.size();
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

        private TextView txtNameMoshtary;
        private TextView txtShomareSanad;
        private TextView txtDateSanad;
        private TextView txtMablagh;
        private TextView txtMablaghMandeh;
        private ImageView imgAddToRequestList;
        private RelativeLayout laySend;

        public ViewHolder(View view) {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));


            swipeLayout = itemView.findViewById(R.id.swipe);

            txtNameMoshtary = view.findViewById(R.id.txtNameMoshtary);
            txtShomareSanad = view.findViewById(R.id.txtShomareSanad);
            txtDateSanad = view.findViewById(R.id.txtDateSanad);
            txtMablagh = view.findViewById(R.id.txtMablagh);
            txtMablaghMandeh = view.findViewById(R.id.txtMablaghMandeh);
            imgAddToRequestList = view.findViewById(R.id.imgAddToRequestList);
            laySend = view.findViewById(R.id.laySend);

            txtNameMoshtary.setTypeface(font);
            txtShomareSanad.setTypeface(font);
            txtDateSanad.setTypeface(font);
            txtMablagh.setTypeface(font);
            txtMablaghMandeh.setTypeface(font);
        }

        void bind(final int position, final OnItemClickListener listener) {
            imgAddToRequestList.setOnClickListener(v -> {
                listener.onItemClick(Constants.CLEARING() ,position);
                swipeLayout.close(true);
            });

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
