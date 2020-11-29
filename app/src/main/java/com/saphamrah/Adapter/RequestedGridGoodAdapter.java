package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.CustomView.CustomScrollView;
import com.saphamrah.MVP.View.DarkhastKalaActivity;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaMojodiZaribModel;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class RequestedGridGoodAdapter extends RecyclerSwipeAdapter<RequestedGridGoodAdapter.ViewHolder> {

    //TODO statistics
    public static final int SHOW_DETAILS = 1;
    public static final int DONT_SHOW_DETAILS = 0;
    public static final String TAG = RequestedGridGoodAdapter.class.getSimpleName();


    private Context context;

    private final OnItemClickListener listener;
    private ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels;
    private int lastSelectedItem;


    public RequestedGridGoodAdapter(Context context, ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.kalaMojodiZaribModels = kalaMojodiZaribModels;
        lastSelectedItem = -1;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_good_list_grid_item, parent, false);
        Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));
        switch (viewType) {
            case SHOW_DETAILS:
                CustomScrollView customScrollView = mainView.findViewById(R.id.layLeftGrid);
                LinearLayout linearLayout=mainView.findViewById(R.id.layRightGrid);
                View viewFullImage=LayoutInflater.from(parent.getContext()).inflate(R.layout.request_good_list_grid_full_image, parent, false);
                View viewDetails = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_good_list_grid_details, parent, false);
                customScrollView.addView(viewDetails);
                linearLayout.addView(viewFullImage);
                PubFunc.FontUtils.getInstance().setFont(parent,font);
                mainView.getLayoutParams().height = ((DarkhastKalaActivity) context).heightOfRecycler / (((DarkhastKalaActivity)context).numberOfDisplayItems/2);
                return new ViewHolder(mainView, SHOW_DETAILS);

            case DONT_SHOW_DETAILS:

                mainView.getLayoutParams().height = ((DarkhastKalaActivity) context).heightOfRecycler /(((DarkhastKalaActivity)context).numberOfDisplayItems/2);
                PubFunc.FontUtils.getInstance().setFont(parent,font);
                return new ViewHolder(mainView, DONT_SHOW_DETAILS);

            default:

                return new ViewHolder(mainView, DONT_SHOW_DETAILS);

        }


    }

    @Override
    public int getItemViewType(int position) {

        if (position == lastSelectedItem) {

            return SHOW_DETAILS;
        } else {

            return DONT_SHOW_DETAILS;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        KalaMojodiZaribModel model = kalaMojodiZaribModels.get(position);


        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String gheymatForosh = formatter.format(model.getGheymatForosh());
        String gheymatMasrafKonande = formatter.format(model.getMablaghMasrafKonandeh());
        int haveMaliatAvarezResId = model.getMashmolMaliatAvarez() == 0 ? R.drawable.ic_error : R.drawable.ic_success;
        String tarikhTolid = model.getTarikhTolid().substring(0, 10);
        String tarikhEngheza = model.getTarikhEngheza().substring(0, 10);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            Date tolidDate = sdf.parse(model.getTarikhTolid());
            Date enghezaDate = sdf.parse(model.getTarikhEngheza());

            tarikhTolid = new PubFunc (). new DateUtils().gregorianToPersianDateTime(tolidDate);
            tarikhTolid = tarikhTolid.substring(0, 9);
            //Log.d("tarikh" , "tolid : " + tarikhTolid);

            tarikhEngheza = new PubFunc (). new DateUtils().gregorianToPersianDateTime(enghezaDate);
            tarikhEngheza = tarikhEngheza.substring(0, 9);
            //Log.d("tarikh" , "engheza : " + tarikhEngheza);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RequestedGridGoodAdapter", "", "onBindViewHolder", "");
        }


//if selected
        if (holder.getItemViewType() == SHOW_DETAILS) {

            holder.swipeLayout.setLeftSwipeEnabled(true);
            holder.lblTedadMojodi.setText(String.format(" %1$s %2$s", model.getTedad(), context.getResources().getString(R.string.adad)));


            //NameCodeKala
            holder.lblCodeKala.setText(String.format(" %1$s", model.getCodeKala()));
            holder.lblNameKala.setText(String.format(" %1$s", model.getNameKala()));

            holder.lblNameBrand.setText(String.format(" %1$s", model.getNameBrand()));
            holder.lblZaribforoshDetail.setText(String.format(" %1$s", model.getZaribForosh()));
            holder.lblBarcode.setText(String.format(" %1$s", model.getBarCode()));
            holder.lblTarikhTolid.setText(String.format("%1$s", tarikhTolid));
            holder.lblTarikhEngheza.setText(String.format("%1$s", tarikhEngheza));
            holder.imgHaveMaliatAvarez.setImageResource(haveMaliatAvarezResId);
            holder.lblGheymatForoshDetail.setText(String.format(" %1$s %2$s", gheymatForosh, context.getResources().getString(R.string.rial)));
            holder.lblGheymatMasrafKonande.setText(String.format("%1$s %2$s", gheymatMasrafKonande, context.getResources().getString(R.string.rial)));
            holder.lblVaznKhales.setText(String.format(" %1$s %2$s", model.getVaznKhales(), model.getNameVahedVazn()));
            holder.lblVaznCarton.setText(String.format("%1$s %2$s", model.getVaznKarton(), model.getNameVahedVazn()));
            holder.lblTedadDarCarton.setText(String.format(" %1$s", model.getTedadDarKarton()));
            holder.lblShomareBach.setText(String.format("%1$s", model.getShomarehBach()));

            if (model.getTedadDarKarton() == model.getTedadDarBasteh()) {
//                holder.lblTedadDarBaste.setVisibility(View.GONE);
                holder.LinTedadDarBaste.setVisibility(View.GONE);
                holder.sixthCapDivider.setVisibility(View.GONE);
            } else {
                holder.lblTedadDarBaste.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.tedadDarBaste), model.getTedadDarBasteh()));
            }
            holder.lblDimen.setText(String.format("%1$s * %2$s * %3$s %4$s", kalaMojodiZaribModels.get(position).getErtefa(), kalaMojodiZaribModels.get(position).getArz(), kalaMojodiZaribModels.get(position).getTol(), kalaMojodiZaribModels.get(position).getNameVahedSize()));
            holder.NameCodeFullImage.setText(String.format("%1$s - %2$s", model.getCodeKala(), model.getNameKala()));

            holder.lblMainNameCodeKala.setText(String.format("%1$s - %2$s", model.getCodeKala(),model.getNameKala()));
            holder.lblMainGheymatForosh.setText(String.format(" %1$s %2$s", gheymatForosh, context.getResources().getString(R.string.rial)));
            holder.lblMainBatchNumber.setText(model.getShomarehBach());

//            if (model.getKalaAsasi()) {
//                holder.imgKalaAsasi.setVisibility(View.VISIBLE);
//            } else {
//                holder.imgKalaAsasi.setVisibility(View.GONE);
//            }
            //set kala status asasi
            if (model.getKalaAsasi())
            {
                Log.d("RequestGoodsAdaptor t","KalaAsasi:"+ model.getKalaAsasi() );
                holder.imgStatusKala.setImageResource(R.drawable.ic_kalaasasi);
                holder.imgStatusKala.setVisibility(View.VISIBLE);
            }

            //set kala status pishnahadi
            else if (model.getTedadPishnahadi()>0)
            {
                Log.d("RequestGoodsAdaptor t","KalaPishnahadi:"+ model.getTedadPishnahadi() );
                holder.imgStatusKala.setImageResource(R.drawable.ic_kalapishnahadi);
                holder.imgStatusKala.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.imgStatusKala.setVisibility(View.GONE);
            }



            try {


                Glide.with(context)
                        .load(DarkhastKalaActivity.imageHash.get(model.getCcKalaCode()))
                        .placeholder(R.drawable.nopic_whit)
                        .into(holder.imgKalaFull);


            } catch (Exception e) {
                e.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "RequestedGridGoodAdapter", "", "onBindViewHolder", "");
            }



            //View has not been selected yet
        } else {


            holder.lblTedadMojodi.setText(String.format(" %1$s %2$s", model.getTedad(), context.getResources().getString(R.string.adad)));
            holder.lblMainBatchNumber.setText(String.format(" %1$s", model.getShomarehBach()));

            //set  Main New lbl Texts
            holder.lblMainNameCodeKala.setText(String.format("%1$s - %2$s", String.valueOf(model.getCodeKala()), model.getNameKala()));
            holder.lblMainGheymatForosh.setText(String.format(" %1$s %2$s", gheymatForosh, context.getResources().getString(R.string.rial)));
//            if (model.getKalaAsasi()) {
//                holder.imgStatusKala.setVisibility(View.VISIBLE);
//            } else {
//                holder.imgStatusKala.setVisibility(View.GONE);
//            }


            if (model.getKalaAsasi())
            {
                Log.d("RequestGoodsAdaptor t","KalaAsasi:"+ model.getKalaAsasi() );
                holder.imgStatusKala.setImageResource(R.drawable.ic_kalaasasi);
                holder.imgStatusKala.setVisibility(View.VISIBLE);
            }

            //set kala status pishnahadi
            else if (model.getTedadPishnahadi()>0)
            {
                Log.d("RequestGoodsAdaptor t","KalaPishnahadi:"+ model.getTedadPishnahadi() );
                holder.imgStatusKala.setImageResource(R.drawable.ic_kalapishnahadi);
                holder.imgStatusKala.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.imgStatusKala.setVisibility(View.GONE);
            }


        }


        if (position == lastSelectedItem) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorLightGreen));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        //TODO Glide library automatically use viewPools to load images

        try {
            Glide.with(context)
                    .load(DarkhastKalaActivity.imageHash.get(model.getCcKalaCode()))
                    .placeholder(R.drawable.nopic_whit)
                    .into(holder.imgKalaGrid);


        } catch (Exception e) {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "RequestedGridGoodAdapter", "", "onBindViewHolder", "");
        }


        holder.bind(model, position, listener);
    }


    @Override
    public int getItemCount() {
        return kalaMojodiZaribModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

        //TODO global views
        private SwipeLayout swipeLayout;
        private CardView cardview;
        private LinearLayout layStatus;
        private TextView lblKalaNameCode;
        private TextView lblTedadMojodi;
        private ImageView imgStatusKala;
        //TODO details of left swipView
        private TextView lblCodeKala;
        private TextView lblNameKala;
        private TextView lblNameBrand;
        private TextView lblZaribforoshDetail;
        private TextView lblBarcode;
        private TextView lblShomareBach;
        private TextView lblTarikhTolid;
        private TextView lblTarikhEngheza;
        private TextView lblHaveMaliatAvarez;
        private TextView lblGheymatForoshDetail;
        private TextView lblGheymatMasrafKonande;
        private TextView lblVaznKhales;
        private TextView lblVaznCarton;
        private TextView lblTedadDarCarton;
        private TextView lblTedadDarBaste;
        private TextView lblDimen;

        private ImageView imgHaveMaliatAvarez;
        private ImageView imgKalaGrid;
        private ImageView imgKalaFull;
        private TextView  NameCodeFullImage;
        private LinearLayout rootView;
        private OnItemClickListener mListener;


        //TODO mainSwipe views
        private TextView lblMainNameCodeKala;
        private TextView lblMainGheymatForosh;
        private TextView lblMainBatchNumber;
        private TextView markCodeKal, markNameKala, markNameBrand, markZaribForosh, markBarcode, markBatchNumber, markTarikhTolid, markTarihkEngheza, markTarikhMashmooleMaliat, markMablaghForosh, markGheymatMasrafKonande, markVaznKhales, markVaznCarton, markTedadDarCarton, markTedadDarBaste, markDimens;
        private LinearLayout LinTedadDarBaste;
        private View sixthCapDivider;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(View view, int viewType) {
            super(view);

            //TODO integrating SWIPEVIEWS AND DRAGS
            swipeLayout = view.findViewById(R.id.swipeGrid);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.layLeftGrid));
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, itemView.findViewById(R.id.layRightGrid));


            //TODO integrating DEFAULT

            switch (viewType) {

                case DONT_SHOW_DETAILS:
                    swipeLayout.setLeftSwipeEnabled(false);
                    swipeLayout.setRightSwipeEnabled(false);
                    cardview = view.findViewById(R.id.crdviewRoot);
                    lblKalaNameCode = view.findViewById(R.id.lblNameCodeKalaMain);
                    lblTedadMojodi = view.findViewById(R.id.lblCountMain);
                    imgStatusKala = view.findViewById(R.id.imgKalaAsasi);
                    lblMainNameCodeKala = view.findViewById(R.id.lblNameCodeKalaMain);
                    lblMainGheymatForosh = view.findViewById(R.id.lblGheymatForoshMain);
                    lblMainBatchNumber = view.findViewById(R.id.lblShomareBachMain);
                    rootView = view.findViewById(R.id.rootView);
                    imgKalaGrid = view.findViewById(R.id.imgKalaGrid);
                    //TODO set All Fonts
                    PubFunc.FontUtils.getInstance().setFont(((ViewGroup) view),font);




                    break;
                //TODO integrating DETAILS

                case SHOW_DETAILS:


                    sixthCapDivider=view.findViewById(R.id.sixthCapDivider);
                    swipeLayout.setLeftSwipeEnabled(true);

                    cardview = view.findViewById(R.id.crdviewRoot);
                    lblKalaNameCode = view.findViewById(R.id.lblNameCodeKalaMain);
                    lblTedadMojodi = view.findViewById(R.id.lblCountMain);
                    imgStatusKala = view.findViewById(R.id.imgKalaAsasi);
                    lblMainNameCodeKala = view.findViewById(R.id.lblNameCodeKalaMain);
                    lblMainGheymatForosh = view.findViewById(R.id.lblGheymatForoshMain);
                    lblMainBatchNumber = view.findViewById(R.id.lblShomareBachMain);
                    rootView = view.findViewById(R.id.rootView);

                    lblCodeKala = view.findViewById(R.id.lblCodeKala);
                    lblNameKala = view.findViewById(R.id.lblNameKala);
                    lblNameBrand = view.findViewById(R.id.lblNameBrand);
                    lblZaribforoshDetail = view.findViewById(R.id.lblZaribForoshDetail);
                    lblBarcode = view.findViewById(R.id.lblBarcode);
                    lblShomareBach = view.findViewById(R.id.lblShomareBach);
                    lblTarikhTolid = view.findViewById(R.id.lblTarikhTolid);
                    lblTarikhEngheza = view.findViewById(R.id.lblTarikhEngheza);
                    lblHaveMaliatAvarez = view.findViewById(R.id.lblHaveMaliatAvarez);
                    lblGheymatForoshDetail = view.findViewById(R.id.lblGheymatForosh);
                    lblGheymatMasrafKonande = view.findViewById(R.id.lblGheymatMasrafKonande);
                    lblVaznKhales = view.findViewById(R.id.lblVaznKhales);
                    lblVaznCarton = view.findViewById(R.id.lblVaznCarton);
                    lblTedadDarCarton = view.findViewById(R.id.lblTedadDarCarton);
                    lblTedadDarBaste = view.findViewById(R.id.lblTedadDarBaste);
                    lblDimen = view.findViewById(R.id.lblDimen);

                    imgKalaFull = view.findViewById(R.id.imgKalaFull);
                    NameCodeFullImage = view.findViewById(R.id.NameCodeFullImage);

                    imgHaveMaliatAvarez = view.findViewById(R.id.imgHaveMaliatAvarez);
                    imgKalaGrid = view.findViewById(R.id.imgKalaGrid);


                    markCodeKal = view.findViewById(R.id.MarkCodeKala);
                    markNameKala = view.findViewById(R.id.MarkNameKala);
                    markNameBrand = view.findViewById(R.id.MarkNameBrand);
                    markZaribForosh = view.findViewById(R.id.MarkZaribForoshDetail);
                    markBarcode = view.findViewById(R.id.markBarcode);
                    markBatchNumber = view.findViewById(R.id.markShomareBach);
                    markTarikhTolid = view.findViewById(R.id.markTarikhTolid);
                    markTarihkEngheza = view.findViewById(R.id.markTarikhEngheza);
                    markMablaghForosh = view.findViewById(R.id.markGheymatForosh);
                    markGheymatMasrafKonande = view.findViewById(R.id.markGheymatMasrafKonande);
                    markVaznKhales = view.findViewById(R.id.markVaznKhales);
                    markVaznCarton = view.findViewById(R.id.markVaznCarton);
                    markTedadDarCarton = view.findViewById(R.id.markTedadDarCarton);
                    markTedadDarBaste = view.findViewById(R.id.markTedadDarBaste);
                    markDimens = view.findViewById(R.id.markDimen);
                    LinTedadDarBaste = view.findViewById(R.id.sixthCap);
                    //TODO setAllFonts
                    PubFunc.FontUtils.getInstance().setFont(((ViewGroup) view),font);




                    swipeLayout.setLeftSwipeEnabled(true);
                    swipeLayout.setRightSwipeEnabled(true);


                    break;

            }




        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        void bind(final KalaMojodiZaribModel model, final int position, final OnItemClickListener listener) {


            swipeLayout.addRevealListener(R.id.layLeftGrid, new SwipeLayout.OnRevealListener() {
                @Override
                public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
                    //TODO onReveal left view
                }
            });


            itemView.findViewById(R.id.layLeftGrid).setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
// TODO   Disallow the touch request for parent scroll on touch of child view
                    listener.onLeftSwipeItemScroll(v.findViewById(R.id.layLeftGrid));

                    return false;
                }
            });


//enable and disable scrolling events in customScrollView
            itemView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    //TODO Disallow the touch request for parent scroll on touch of child view
                    if (v != v.findViewById(R.id.layLeftGrid)) {
                        listener.onOtherItemsScroll(v.findViewById(R.id.layLeftGrid));
                        return true;
                    } else {
                        return true;
                    }

                }
            });

            //TODO on item select
            swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(model, position);
                }
            });

        }



    }

    //TODO adapter interface
    public interface OnItemClickListener {

        void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel, int position);

        void onLeftSwipeItemScroll(CustomScrollView view);

        void onOtherItemsScroll(CustomScrollView view);

    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeGrid;
    }


}
