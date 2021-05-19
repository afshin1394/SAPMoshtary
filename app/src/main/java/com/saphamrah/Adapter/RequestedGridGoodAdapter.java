package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.saphamrah.CustomView.MyBounceInterpolator;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaMojodiZaribModel;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RequestedGridGoodAdapter extends RecyclerSwipeAdapter<RequestedGridGoodAdapter.ViewHolder> {

    //TODO statistics
    public static final int SHOW_DETAILS = 1;
    public static final int DONT_SHOW_DETAILS = 0;
    public static final String TAG = RequestedGridGoodAdapter.class.getSimpleName();
    public static final int UNSELECTED=-1;


    private Context context;
    private final OnItemEventListener listener;
    private ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels;
    private ArrayList<KalaMojodiZaribModel> filteredData;
    private int lastSelectedPosition;
    private int itemPerRow;
    private int status;
    private static int heightOfRecycler;
    private static int widthOfRecycler;
    private SparseArray allKalaPhoto = new SparseArray();


    public RequestedGridGoodAdapter(Context context, ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels, OnItemEventListener listener) {
        this.context = context;
        this.listener = listener;
        this.kalaMojodiZaribModels = kalaMojodiZaribModels;
        lastSelectedPosition = UNSELECTED;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RequestedGridGoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_good_list_grid_item, parent, false);
        Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));
        new PubFunc().new FontUtils().setFont(((ViewGroup) mainView), font);
        switch (viewType) {
            case SHOW_DETAILS:
                CustomScrollView customScrollView = mainView.findViewById(R.id.layLeftGrid);
                LinearLayout linearLayout = mainView.findViewById(R.id.layRightGrid);
                View viewFullImage = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_good_list_grid_full_image, parent, false);
                View viewDetails = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_good_list_grid_details, parent, false);
                customScrollView.addView(viewDetails);
                linearLayout.addView(viewFullImage);
                new PubFunc().new FontUtils().setFont(((ViewGroup) viewDetails), font);
                new PubFunc().new FontUtils().setFont(((ViewGroup) viewFullImage), font);
                mainView.getLayoutParams().height = heightOfRecycler / itemPerRow;
                return new RequestedGridGoodAdapter.ViewHolder(mainView, SHOW_DETAILS, listener);

            case DONT_SHOW_DETAILS:
                mainView.getLayoutParams().height = heightOfRecycler / itemPerRow;
                new PubFunc().new FontUtils().setFont(parent, font);
                return new RequestedGridGoodAdapter.ViewHolder(mainView, DONT_SHOW_DETAILS, listener);

            default:

                return new RequestedGridGoodAdapter.ViewHolder(mainView, DONT_SHOW_DETAILS, listener);

        }


    }

    @Override
    public int getItemViewType(int position) {

        if (position == lastSelectedPosition)
        {
            return SHOW_DETAILS;
        } else
        {
            return DONT_SHOW_DETAILS;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation myBounceAnimation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 15);
        myBounceAnimation.setInterpolator(interpolator);


        KalaMojodiZaribModel model = kalaMojodiZaribModels.get(position);


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

        //TODO set Selection
        if (position == lastSelectedPosition) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorLightGreen));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String gheymatForosh = formatter.format(model.getGheymatForosh());
        /**Zarib Arzesh Afzoodeh=0.1**/
        //Todo
        String gheymatForoshBaArzeshAfzoodeh = formatter.format(model.getGheymatForosh() * 1.09 );
        String gheymatMasrafKonande = formatter.format(model.getMablaghMasrafKonandeh());
        int haveMaliatAvarezResId = model.getMashmolMaliatAvarez() == 0 ? R.drawable.ic_error : R.drawable.ic_success;
        String tarikhTolid = model.getTarikhTolid().substring(0, 10);
        String tarikhEngheza = model.getTarikhEngheza().substring(0, 10);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            Date tolidDate = sdf.parse(model.getTarikhTolid());
            Date enghezaDate = sdf.parse(model.getTarikhEngheza());

            tarikhTolid = new PubFunc().new DateUtils().gregorianToPersianDateTime(tolidDate);
            tarikhTolid = tarikhTolid.substring(0, 9);

            tarikhEngheza = new PubFunc().new DateUtils().gregorianToPersianDateTime(enghezaDate);
            tarikhEngheza = tarikhEngheza.substring(0, 9);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RequestedGridGoodAdapter", "", "onBindViewHolder", "");
        }


        /**if item is selected**/

        if (holder.getItemViewType() == SHOW_DETAILS) {


            /**VERSION ANDROID>4.4.2**/
            /**SET ANIMATION AND ARROW_KEYS**/
            /**LOWER VERSION DONT SUPPERT VERCTOR ROTATION**/
            int arrowVisibility = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? View.VISIBLE : View.GONE;

            /**make arrows visible to show scrollable item**/
            holder.rightArrow.setVisibility(arrowVisibility);
            holder.leftArrow.setVisibility(arrowVisibility);

            /**setAnimations for arrows**/
            holder.leftArrow.startAnimation(myBounceAnimation);
            holder.rightArrow.startAnimation(myBounceAnimation);


            //TODO
            int gheymatBaArzeshAfzoodehvisibility = model.getMashmolMaliatAvarez() == 0 ? View.GONE : View.VISIBLE;
            holder.linEightCap.setVisibility(gheymatBaArzeshAfzoodehvisibility);
            holder.dividerEightCap.setVisibility(gheymatBaArzeshAfzoodehvisibility);
            holder.lblGheymatForoshBaArzeshAfzoodeh.setText(String.format(" %1$s %2$s", gheymatForoshBaArzeshAfzoodeh, context.getResources().getString(R.string.rial)));


            holder.swipeLayout.setLeftSwipeEnabled(true);
            holder.lblTedadMojodi.setText(String.format(" %1$s %2$s", model.getTedad(), context.getResources().getString(R.string.adad)));


            /**NameCodeKala**/
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


            //Todo
            holder.lblTedadDarBaste.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.tedadDarBaste), model.getTedadDarBasteh()));


            int linTedadDarBasteVisibility = model.getTedadDarKarton() == model.getTedadDarBasteh() ? View.GONE : View.VISIBLE;
            holder.LinTedadDarBaste.setVisibility(linTedadDarBasteVisibility);
            holder.dividerSixthCap.setVisibility(linTedadDarBasteVisibility);


            holder.lblDimen.setText(String.format("%1$s * %2$s * %3$s %4$s", kalaMojodiZaribModels.get(position).getErtefa(), kalaMojodiZaribModels.get(position).getArz(), kalaMojodiZaribModels.get(position).getTol(), kalaMojodiZaribModels.get(position).getNameVahedSize()));
            holder.NameCodeFullImage.setText(String.format("%1$s - %2$s", model.getCodeKala(), model.getNameKala()));

            holder.lblMainNameCodeKala.setText(String.format("%1$s - %2$s", model.getCodeKala(), model.getNameKala()));
            holder.lblMainGheymatForosh.setText(String.format(" %1$s %2$s", gheymatForosh, context.getResources().getString(R.string.rial)));
            //holder.lblMainBatchNumber.setText(model.getShomarehBach());
            if (model.getShomarehBach() !=null) {
                if (!model.getShomarehBach().equals("")){
                    holder.lblMainBatchNumber.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.shomareBach), model.getShomarehBach()));
                } else {
                    holder.lblMainBatchNumber.setText(String.format("%1$s %2$s",   formatter.format(model.getMablaghMasrafKonandeh() ) , context.getResources().getString(R.string.rial)));
                }
            } else {
                holder.lblMainBatchNumber.setText(String.format("%1$s %2$s", model.getMablaghMasrafKonandeh() , context.getResources().getString(R.string.rial)));
            }
//            if (model.getKalaAsasi()) {
//                holder.imgStatusKala.setVisibility(View.VISIBLE);
//            } else {
//                holder.imgStatusKala.setVisibility(View.GONE);
//            }
            try {

                Glide.with(context)
                        .load(allKalaPhoto.get(model.getCcKalaCode()))
                        .placeholder(R.drawable.nopic_whit)
                        .into(holder.imgKalaFull);


            } catch (Exception e) {
                e.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "RequestedGridGoodAdapter", "", "onBindViewHolder", "");
            }


            //View has not been selected yet
        } else {
            holder.rightArrow.setVisibility(View.INVISIBLE);
            holder.leftArrow.setVisibility(View.INVISIBLE);


            holder.lblTedadMojodi.setText(String.format(" %1$s %2$s", model.getTedad(), context.getResources().getString(R.string.adad)));

            if (model.getShomarehBach() !=null) {
                if (!model.getShomarehBach().equals("")){
                    holder.lblMainBatchNumber.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.shomareBach), model.getShomarehBach()));
                } else {
                    holder.lblMainBatchNumber.setText(String.format("%1$s %2$s",   formatter.format(model.getMablaghMasrafKonandeh() ) , context.getResources().getString(R.string.rial)));
                }
            } else {
                holder.lblMainBatchNumber.setText(String.format("%1$s %2$s", model.getMablaghMasrafKonandeh() , context.getResources().getString(R.string.rial)));
            }
//            holder.lblMainBatchNumber.setText(String.format(" %1$s", model.getShomarehBach()));

            //set  Main New lbl Texts
            holder.lblMainNameCodeKala.setText(String.format("%1$s - %2$s", String.valueOf(model.getCodeKala()), model.getNameKala()));
            holder.lblMainGheymatForosh.setText(String.format(" %1$s %2$s", gheymatForosh, context.getResources().getString(R.string.rial)));

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


        }


        /*****load images for central layout*****/
        Log.i("getImageHash", "onBindViewHolder: " + allKalaPhoto.get(model.getCcKalaCode()) + model.getCcKalaCode());
        try {
            Glide.with(context)
                    .load(allKalaPhoto.get(model.getCcKalaCode()))
                    .placeholder(R.drawable.nopic_whit)
                    .into(holder.imgKalaGrid);


        } catch (Exception e) {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "RequestedGridGoodAdapter", "", "onBindViewHolder", "");
        }


        /***setEventListeners***/
        holder.setEventListeners(model, position, listener);
    }


    @Override
    public int getItemCount() {
        return kalaMojodiZaribModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));
        OnItemEventListener onItemEventListener;
        /** global views**/
        private SwipeLayout swipeLayout;
        private CardView cardview;
        private LinearLayout layStatus;
        private TextView lblKalaNameCode;
        private TextView lblTedadMojodi;
        private ImageView imgStatusKala;

        /***left side layout views***/
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
        private TextView lblGheymatForoshBaArzeshAfzoodeh;

        private ImageView imgHaveMaliatAvarez;
        private ImageView imgKalaGrid;
        private ImageView imgKalaFull;
        private TextView NameCodeFullImage;

        private LinearLayout rootView;
        private LinearLayout linEightCap;
        private OnItemEventListener mListener;


        /****central layout views****/


        private TextView lblMainNameCodeKala;
        private TextView lblMainGheymatForosh;
        private TextView lblMainBatchNumber;
        private TextView markCodeKala, markNameKala, markNameBrand, markZaribForosh, markBarcode, markBatchNumber, markTarikhTolid, markTarihkEngheza, markTarikhMashmooleMaliat, markMablaghForosh, markGheymatMasrafKonande, markVaznKhales, markVaznCarton, markTedadDarCarton, markTedadDarBaste, markDimens;
        private LinearLayout LinTedadDarBaste;
        private View dividerSixthCap;
        private View dividerEightCap;
        private final ImageView leftArrow;
        private final ImageView rightArrow;


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(View view, int viewType, OnItemEventListener onItemEventListener) {
            super(view);
            /***left and right arrow images***/
            leftArrow = view.findViewById(R.id.arrow_left_side);
            rightArrow = view.findViewById(R.id.arrow_right_side);
            /***integrating swipe layout and drags***/
            swipeLayout = view.findViewById(R.id.swipeGrid);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.layLeftGrid));
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, itemView.findViewById(R.id.layRightGrid));
            /*
            event listeners
             */
            this.onItemEventListener = onItemEventListener;


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
                    new PubFunc().new FontUtils().setFont(((ViewGroup) view), font);


                    break;

                /***integrating DETAILS***/
                case SHOW_DETAILS:


                    dividerSixthCap = view.findViewById(R.id.sixthCapDivider);
                    dividerEightCap = view.findViewById(R.id.eightCapDivider);

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

                    lblGheymatForoshBaArzeshAfzoodeh = view.findViewById(R.id.lblMablaghForoshBaArzeshAfzoodeh);
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


                    markCodeKala = view.findViewById(R.id.MarkCodeKala);
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
                    linEightCap = view.findViewById(R.id.eightCap);

                    swipeLayout.setLeftSwipeEnabled(true);
                    swipeLayout.setRightSwipeEnabled(true);


                    break;

            }


        }


        private void setEventListeners(KalaMojodiZaribModel model, int position, OnItemEventListener onItemEventListener) {
            itemView.findViewById(R.id.layLeftGrid).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Log.i("ON_TOUCH", "--ON--ITEM--TOUCH--LEFT--GRID--LAY_LEFT" + getAdapterPosition() + " " + lastSelectedPosition);

                    listener.onLeftSwipeItemScroll(view.findViewById(R.id.layLeftGrid));

                    return false;
                }
            });




            swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                         lastSelectedPosition = position;
                         notifyDataSetChanged();
                         listener.onItemClick(model, position);

                }
            });


        }


    }

    /****adapter events handler****/


    public interface OnItemEventListener {

        void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel, int position);

        void onLeftSwipeItemScroll(CustomScrollView view);

    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeGrid;
    }


    public void setMeasurements(int heightOfRecycler, int widthOfRecycler) {
        this.heightOfRecycler = heightOfRecycler;
        this.widthOfRecycler = widthOfRecycler;
    }


    public void setKalaImages(ArrayList<KalaPhotoModel> kalaPhotoModels) {

        Disposable disposable = Observable.fromIterable(kalaMojodiZaribModels)
                .flatMap(kalaMojodiZaribModel -> Observable.fromIterable(kalaPhotoModels)
                        .map(kalaPhotoModel -> {

                            if (kalaPhotoModel.getCcKalaCodeDb() == kalaMojodiZaribModel.getCcKalaCode()) {
                                allKalaPhoto.put(kalaPhotoModel.getCcKalaCodeDb(),kalaPhotoModel.getImageDb());

                            }
                            return kalaMojodiZaribModel;
                        })
                ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    public void setStatus(int status) {
        this.status = status;
        switch (status) {
            case Constants.SINGLE_ITEM:
                this.itemPerRow = 1;
                break;
            case Constants.DOUBLE_ITEM:
            case Constants.FOUR_ITEM:
                this.itemPerRow = 2;
                break;
        }

    }

    public int getStatus() {
        return status;
    }

    public void updateStatus() {
        switch (status) {
            case Constants.SINGLE_ITEM:
                setStatus(Constants.DOUBLE_ITEM);
                break;
            case Constants.DOUBLE_ITEM:

                setStatus(Constants.FOUR_ITEM);
                break;
            case Constants.FOUR_ITEM:
                setStatus(Constants.SINGLE_ITEM);
                break;
        }

    }

    public void resetLastSelectedPosition(){
        this.lastSelectedPosition=UNSELECTED;
    }
}
