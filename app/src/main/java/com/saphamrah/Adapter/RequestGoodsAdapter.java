package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
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
import com.saphamrah.Utils.Constants;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * این آداپتر برای نمایش لیست کالاهای موجود برای انتخاب و سفارش کالا استفاده میشود.
 */
public class RequestGoodsAdapter extends RecyclerSwipeAdapter<RequestGoodsAdapter.ViewHolder>
{

    private Context context;
    private final OnItemClickListener listener;
    private ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels;
    private int lastSelectedItem;
    private int zangireiParam;

    public RequestGoodsAdapter(Context context, ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels,int zangireiParam , OnItemClickListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.kalaMojodiZaribModels = kalaMojodiZaribModels;
        lastSelectedItem = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goodslist_request_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        KalaMojodiZaribModel model = kalaMojodiZaribModels.get(position);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String gheymatForosh = formatter.format(model.getGheymatForosh());
        String gheymatMasrafKonande = formatter.format(model.getMablaghMasrafKonandeh());
        //String profitPercentage = String.valueOf( (int)(((float)(model.getMablaghMasrafKonandeh() - model.getGheymatForosh()) / model.getGheymatForosh()) * 100));
        int haveMaliatAvarezResId = model.getMashmolMaliatAvarez() == 0 ? R.drawable.ic_error :  R.drawable.ic_success;
        String tarikhTolid = model.getTarikhTolid().substring(0 , 10);
        String tarikhEngheza = model.getTarikhEngheza().substring(0 , 10);

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            Date tolidDate = sdf.parse(model.getTarikhTolid());
            Date enghezaDate = sdf.parse(model.getTarikhEngheza());

            tarikhTolid = new PubFunc().new DateUtils().gregorianToPersianDateTime(tolidDate);
            tarikhTolid = tarikhTolid.substring(0 , 9);
            //Log.d("tarikh" , "tolid : " + tarikhTolid);

            tarikhEngheza = new PubFunc().new DateUtils().gregorianToPersianDateTime(enghezaDate);
            tarikhEngheza = tarikhEngheza.substring(0 , 9);
            //Log.d("tarikh" , "engheza : " + tarikhEngheza);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RequestGoodsAdapter" , "" , "onBindViewHolder" , "");
        }

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom , holder.itemView.findViewById(R.id.layBottom));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Top , holder.itemView.findViewById(R.id.layTop));

        holder.lblKalaNameCode.setText(String.format("%1$s - %2$s", model.getCodeKala(), model.getNameKala()));
        holder.lblTedadMojodi.setText(String.format("%1$s \n %2$s %3$s", context.getResources().getString(R.string.mojodi), model.getTedad(), context.getResources().getString(R.string.adad)));
        holder.lblGheymatForosh.setText(String.format("%1$s \n %2$s %3$s", context.getResources().getString(R.string.mablaghVahed), gheymatForosh, context.getResources().getString(R.string.rial)));
        holder.lblZaribForosh.setText(String.format("%1$s \n %2$s", context.getResources().getString(R.string.zaribForosh), model.getZaribForosh()));
        holder.lblCodeNameKala.setText(String.format("%1$s: %2$s - %3$s: %4$s", context.getResources().getString(R.string.codeKala), model.getCodeKala(), context.getResources().getString(R.string.nameKala), model.getNameKala()));
        holder.lblNameBrand.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.nameBrand), model.getNameBrand()));
        holder.lblZaribforoshDetail.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.zaribForosh), model.getZaribForosh()));
        holder.lblBarcode.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.barcode), model.getBarCode()));

        if (kalaMojodiZaribModels.get(position).getShomarehBach() !=null) {
            if (!kalaMojodiZaribModels.get(position).getShomarehBach().equals("")){
                holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.shomareBach), kalaMojodiZaribModels.get(position).getShomarehBach()));
            } else {
                holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.gheymatMasrafKonandeRial),  formatter.format(kalaMojodiZaribModels.get(position).getMablaghMasrafKonandeh())));
            }
        } else {
            holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.gheymatMasrafKonande), kalaMojodiZaribModels.get(position).getMablaghMasrafKonandeh()));
        }

//        holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.shomareBach), model.getShomarehBach()));
        holder.lblTarikhTolid.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.tarikhTolid), tarikhTolid));
        holder.lblTarikhEngheza.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.tarikhEngheza), tarikhEngheza));
        holder.imgHaveMaliatAvarez.setImageResource(haveMaliatAvarezResId);
        holder.lblGheymatForoshDetail.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.mablaghForosh), gheymatForosh, context.getResources().getString(R.string.rial)));
        holder.lblGheymatMasrafKonande.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.gheymatMasrafKonande), gheymatMasrafKonande, context.getResources().getString(R.string.rial)));
        holder.lblVaznKhales.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.vaznKhales), model.getVaznKhales(), model.getNameVahedVazn()));
        holder.lblVaznCarton.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.vaznCarton), model.getVaznKarton(), model.getNameVahedVazn()));
        holder.lblTedadDarCarton.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.tedadDarCarton), model.getTedadDarKarton()));

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

        if (model.getTedadDarKarton() == model.getTedadDarBasteh())
        {
            holder.lblTedadDarBaste.setVisibility(View.GONE);
        }
        else
        {
            holder.lblTedadDarBaste.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.tedadDarBaste), model.getTedadDarBasteh()));
        }
        holder.lblDimen.setText(String.format("%1$s: %2$s * %3$s * %4$s %5$s", context.getResources().getString(R.string.dimens), kalaMojodiZaribModels.get(position).getErtefa(), kalaMojodiZaribModels.get(position).getArz(), kalaMojodiZaribModels.get(position).getTol(), kalaMojodiZaribModels.get(position).getNameVahedSize()));
        holder.lblNameDetailTop.setText(String.format("%1$s - %2$s", model.getCodeKala(), model.getNameKala()));
        if (position == lastSelectedItem)
        {
            holder.layStatus.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        }
        else
        {
            holder.layStatus.setBackgroundColor(Color.TRANSPARENT);
        }

        try
        {
            File file = new File(Environment.getExternalStorageDirectory() + "/SapHamrah/Album/" , model.getCodeKala() + ".jpg");
            if (file.exists())
            {
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/SapHamrah/Album/" + model.getCodeKala() + ".jpg");
                holder.imgKala.setImageBitmap(bitmap);
                holder.imgKalaFull.setImageBitmap(bitmap);
            }
            else
            {
                holder.imgKala.setImageResource(R.drawable.nopic_whit);
                holder.imgKalaFull.setImageResource(R.drawable.nopic_whit);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            holder.imgKala.setImageResource(R.drawable.nopic_whit);
            holder.imgKalaFull.setImageResource(R.drawable.nopic_whit);
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RequestGoodsAdapter" , "" , "onBindViewHolder" , "");
        }


        holder.bind(kalaMojodiZaribModels.get(position) , position , listener);
    }


    @Override
    public int getItemCount()
    {
        return kalaMojodiZaribModels.size();
    }

    public void clearSelecedItem()
    {
        lastSelectedItem = -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private LinearLayout layRoot;
        private CardView crdview;
        private LinearLayout layStatus;
        private TextView lblKalaNameCode;
        private TextView lblTedadMojodi;
        private TextView lblGheymatForosh;
        private TextView lblZaribForosh;
        private ImageView imgStatusKala;
        //detail
        private TextView lblCodeNameKala;
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
        private TextView lblNameDetailTop;
        private ImageView imgHaveMaliatAvarez;
        private ImageView imgKala;
        private ImageView imgKalaFull;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            swipeLayout = view.findViewById(R.id.swipe);
            layRoot = view.findViewById(R.id.layRoot);
            crdview = view.findViewById(R.id.crdviewRoot);
            layStatus = view.findViewById(R.id.layStatus);
            lblKalaNameCode = view.findViewById(R.id.lblNameCode);
            lblTedadMojodi = view.findViewById(R.id.lblCount);
            lblGheymatForosh = view.findViewById(R.id.lblCost);
            lblZaribForosh = view.findViewById(R.id.lblZaribForosh);
            imgStatusKala = view.findViewById(R.id.imgKalaAsasi);

            lblCodeNameKala = view.findViewById(R.id.lblNameCodeKala);
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
            lblNameDetailTop = view.findViewById(R.id.lblNameCodeDetailTop);
            imgHaveMaliatAvarez = view.findViewById(R.id.imgHaveMaliatAvarez);
            imgKala = view.findViewById(R.id.imgKala);
            imgKalaFull = view.findViewById(R.id.imgKalaFull);

            lblKalaNameCode.setTypeface(font);
            lblTedadMojodi.setTypeface(font);
            lblGheymatForosh.setTypeface(font);
            lblZaribForosh.setTypeface(font);
            lblCodeNameKala.setTypeface(font);
            lblNameBrand.setTypeface(font);
            lblZaribforoshDetail.setTypeface(font);
            lblBarcode.setTypeface(font);
            lblShomareBach.setTypeface(font);
            lblTarikhTolid.setTypeface(font);
            lblTarikhEngheza.setTypeface(font);
            lblHaveMaliatAvarez.setTypeface(font);
            lblGheymatForoshDetail.setTypeface(font);
            lblGheymatMasrafKonande.setTypeface(font);
            lblVaznKhales.setTypeface(font);
            lblVaznCarton.setTypeface(font);
            lblTedadDarCarton.setTypeface(font);
            lblTedadDarBaste.setTypeface(font);
            lblDimen.setTypeface(font);
            lblNameDetailTop.setTypeface(font);
        }

        void bind(final KalaMojodiZaribModel kalaMojodiZaribModel , final int position , final OnItemClickListener listener)
        {
            //listener.onItemClick(kalaMojodiZaribModel, position);

            /*layRoot.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(kalaMojodiZaribModel , position);
                }
            });

            swipeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(kalaMojodiZaribModel , position);
                }
            });*/

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
