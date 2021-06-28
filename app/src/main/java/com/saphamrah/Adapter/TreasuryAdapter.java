package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TreasuryAdapter extends RecyclerSwipeAdapter<TreasuryAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<DarkhastFaktorMoshtaryForoshandeModel> models;
    private int lastPosition = -1;
    private boolean faktorRooz;
    private int noeMasouliat;
    private PubFunc.DateUtils dateUtils = new PubFunc().new DateUtils();
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_MILISECONDS());
    public TreasuryAdapter(Context context, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels, boolean faktorRooz, int noeMasouliat, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.models = darkhastFaktorMoshtaryForoshandeModels;
        this.faktorRooz = faktorRooz;
        this.noeMasouliat = noeMasouliat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.treasury_customlist, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        holder.lblRadif.setText(String.valueOf(position + 1));
        holder.lblCodeNameCustomer.setText(String.format("%1$s - %2$s", models.get(position).getCodeMoshtary(), models.get(position).getFullNameMoshtary()));
        holder.lblNameForoshandeh.setText(models.get(position).getFullNameForoshande());
        holder.lblMablaghKhalesFaktor.setText(String.format("%1$s : %2$s %3$s", context.getResources().getString(R.string.mablaghKhales), formatter.format((int) models.get(position).getMablaghKhalesFaktor()), context.getResources().getString(R.string.rial)));
        holder.lblMablaghMandehFaktor.setText(String.format("%1$s : %2$s %3$s", context.getResources().getString(R.string.mande), formatter.format(models.get(position).getMablaghMandeh()), context.getResources().getString(R.string.rial)));
        holder.lblNameNoeVosol.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.noeVosol), models.get(position).getNameNoeVosolAzMoshtary()));

        holder.lblShomarehDarkhast.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareDarkhast), models.get(position).getShomarehDarkhast()));
        if (models.get(position).getShomarehFaktor() != 0)
        holder.lblShomarehFaktor.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareFaktor), models.get(position).getShomarehFaktor()));

        try {
            Date date = sdf.parse(models.get(position).getTarikhErsal());
            String tarikhErsal = (String) DateFormat.format(Constants.DATE_SHORT_FORMAT_WITH_SLASH() , date);
            holder.lblTarikhErsal.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.tarikhErsal), dateUtils.gregorianWithSlashToPersianSlash(tarikhErsal)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((models.get(position).getCcDarkhastFaktorNoeForosh() == DarkhastFaktorModel.ccNoeHavale) && ((noeMasouliat == 4 && models.get(position).getCodeVazeiat() == 99) || (noeMasouliat == 5 && models.get(position).getExtraProp_IsSend() == 0 && models.get(position).getCodeVazeiat() < 6))) {
            holder.layEditDarkhast.setVisibility(View.VISIBLE);
            holder.layMarjoee.setVisibility(View.GONE);
        } else {
            holder.layMarjoee.setVisibility(View.VISIBLE);
            holder.layEditDarkhast.setVisibility(View.GONE);
        }

//        /**
//         * check show marjoee
//         */
//        if (models.get(position).getFaktorRooz() == 1){
//            holder.layMarjoee.setVisibility(View.GONE);
//        } else {
//            holder.layMarjoee.setVisibility(View.VISIBLE);
//        }

        /**
         * set color for layout
         */

        if (models.get(position).getExtraProp_IsMarjoeeKamel() == 1) {
            holder.lay_for_color.setBackgroundResource(R.color.marjoeeKamel);
        } else if (models.get(position).getExtraProp_Resid() == 1) {
            holder.lay_for_color.setBackgroundResource(R.color.resid);
        } else if (models.get(position).getMablaghMandeh() == 0 && models.get(position).getExtraProp_MablaghDariaftPardakht() == 0) {
            holder.lay_for_color.setBackgroundResource(R.color.tasvieKamel);
        } else if (models.get(position).getMablaghMandeh() == 0 && models.get(position).getExtraProp_MablaghDariaftPardakht() != 0) {
            holder.lay_for_color.setBackgroundResource(R.color.bestankari);
        } else if (models.get(position).getMablaghMandeh() != models.get(position).getMablaghKhalesFaktor() && models.get(position).getMablaghMandeh() != 0) {
            holder.lay_for_color.setBackgroundResource(R.color.mandehDar);
        } else if (models.get(position).getCodeVazeiat() == 9 & models.get(position).getMablaghMandeh() != 0 & models.get(position).getMablaghMandeh() == models.get(position).getMablaghKhalesFaktor()) {
            holder.lay_for_color.setBackgroundResource(R.color.resid);
        } else {
            holder.lay_for_color.setBackgroundResource(R.color.defultTreasuryList);
        }


        if (models.get(position).getExtraProp_IsSend() == 0) {
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_red_right);
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_red_left);
        } else {
            holder.layStatusRight.setBackgroundResource(R.drawable.radius_layout_green_right);
            holder.layStatusLeft.setBackgroundResource(R.drawable.radius_layout_green_right);
        }

        if (models.get(position).getExtraProp_ShowFaktorMamorPakhsh() == 0) {
            holder.layFaktorDetail.setVisibility(View.VISIBLE);
        } else {
            holder.layFaktorDetail.setVisibility(View.GONE);
        }

        if ((noeMasouliat == 4 && models.get(position).getCodeVazeiat() == 99) || (noeMasouliat == 5 && models.get(position).getExtraProp_IsSend() == 0 && models.get(position).getCodeVazeiat() < 6)) {
            holder.layEditVosol.setVisibility(View.GONE);
            holder.laySendVosol.setVisibility(View.GONE);
        } else {
            if (models.get(position).getCcDarkhastFaktorNoeForosh() == DarkhastFaktorModel.ccNoeHavale) {
                holder.layEditVosol.setVisibility(View.VISIBLE);
            }
            holder.laySendVosol.setVisibility(View.VISIBLE);
        }

        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.itemView.findViewById(R.id.layRight));
        if (holder.layEditVosol.getVisibility() == View.GONE && holder.laySendVosol.getVisibility() == View.GONE && holder.layEditDarkhast.getVisibility() == View.GONE) {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, null);
        } else {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.itemView.findViewById(R.id.layLeft));
        }

        if (models.get(position).getCcDarkhastFaktorNoeForosh() == 1 && (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) )
        {
            holder.laySaveAndSend.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.laySaveAndSend.setVisibility(View.GONE);
        }
        holder.bind(position, listener);
        setAnimation(holder.itemView, position);

        if (models.get(position).getExtraProp_SendLocation() == 1){
            holder.laySaveAndSend.setVisibility(View.GONE);
        }
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
        private LinearLayout layStatusRight;
        private LinearLayout layStatusLeft;
        private TextView lblRadif;
        private TextView lblCodeNameCustomer;
        private TextView lblNameForoshandeh;
        private TextView lblMablaghKhalesFaktor;
        private TextView lblMablaghMandehFaktor;
        private TextView lblShomarehDarkhast;
        private TextView lblShomarehFaktor;
        private TextView lblTarikhErsal;
        private TextView lblNameNoeVosol;
        private RelativeLayout layShowLocation;
        private RelativeLayout layFaktor;
        private RelativeLayout layFaktorDetail;
        private RelativeLayout laySendVosol;
        private RelativeLayout layEditVosol;
        private RelativeLayout layEditDarkhast;
        private RelativeLayout layMarjoee;
        private LinearLayout lay_for_color;
        private RelativeLayout laySaveAndSend;


        public ViewHolder(View view) {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            layStatusRight = itemView.findViewById(R.id.layStatusRight);
            layStatusLeft = itemView.findViewById(R.id.layStatusLeft);
            lblRadif = view.findViewById(R.id.lblRadif);
            lblCodeNameCustomer = view.findViewById(R.id.lblCodeNameCustomer);
            lblNameForoshandeh = view.findViewById(R.id.lblNameForoshandeh);
            lblMablaghKhalesFaktor = view.findViewById(R.id.lblMablaghKhalesFaktor);
            lblMablaghMandehFaktor = view.findViewById(R.id.lblMablaghMandehFaktor);
            lblShomarehDarkhast = view.findViewById(R.id.lblShomarehDarkhast);
            lblShomarehFaktor = view.findViewById(R.id.lblShomarehFaktor);
            lblTarikhErsal = view.findViewById(R.id.lblTarikhErsal);
            lblNameNoeVosol = view.findViewById(R.id.lblNameNoeVosol);
            layShowLocation = view.findViewById(R.id.layShowLocation);
            layFaktor = view.findViewById(R.id.layFaktorImage);
            layFaktorDetail = view.findViewById(R.id.layFaktorDetail);
            laySendVosol = view.findViewById(R.id.laySend);
            layEditVosol = view.findViewById(R.id.layEdit);
            layEditDarkhast = view.findViewById(R.id.layEditDarkhast);
            layMarjoee = view.findViewById(R.id.layMarjoee);
            lay_for_color = view.findViewById(R.id.lay_for_color);
            laySaveAndSend = view.findViewById(R.id.laySaveAndSendLocation);


            lblRadif.setTypeface(font);
            lblCodeNameCustomer.setTypeface(font);
            lblNameForoshandeh.setTypeface(font);
            lblMablaghKhalesFaktor.setTypeface(font);
            lblMablaghMandehFaktor.setTypeface(font);
            lblShomarehDarkhast.setTypeface(font);
            lblShomarehFaktor.setTypeface(font);
            lblNameNoeVosol.setTypeface(font);
            lblTarikhErsal.setTypeface(font);
        }

        void bind(final int position, final OnItemClickListener listener) {

            layShowLocation.setOnClickListener(v -> {
                listener.onItemClick(Constants.SHOW_LOCATION(), position);
                swipeLayout.close(true);
            });

            layFaktor.setOnClickListener(v -> {
                listener.onItemClick(Constants.SHOW_IMAGE(), position);
                swipeLayout.close(true);
            });

            layFaktorDetail.setOnClickListener(v -> {
                listener.onItemClick(Constants.SHOW_FAKTOR_DETAIL(), position);
                swipeLayout.close(true);
            });

            laySendVosol.setOnClickListener(v -> {
                listener.onItemClick(Constants.SEND(), position);
                swipeLayout.close(true);
            });

            layEditVosol.setOnClickListener(v -> {
                listener.onItemClick(Constants.CLEARING(), position);
                swipeLayout.close(true);
            });


            layEditDarkhast.setOnClickListener(v -> {
                listener.onItemClick(Constants.EDIT_DARKHAST(), position);
                swipeLayout.close(true);
            });

            layMarjoee.setOnClickListener(v -> {
                listener.onItemClick(Constants.MARJOEE, position);
                Log.d("Treasury", Constants.MARJOEE + "  " + position);
                swipeLayout.close(true);
            });

            laySaveAndSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(Constants.SAVE_SEND_LOCATION, position);
                    swipeLayout.close(true);
                }
            });

        }

    }


    public interface OnItemClickListener {
        void onItemClick(int operation, int position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
