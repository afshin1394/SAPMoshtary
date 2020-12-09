package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptKalaInfoAdapter extends RecyclerSwipeAdapter<RptKalaInfoAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<RptKalaInfoModel> rptKalaInfoModels;
    private int lastSelectedItem;
    private SparseArray AllKalaPhoto =new SparseArray();
    private static ArrayList<KalaPhotoModel> kalaPhotoModels=new ArrayList<>();


    public RptKalaInfoAdapter(Context context, ArrayList<RptKalaInfoModel> rptKalaInfoModels)
    {
        this.context = context;
        this.rptKalaInfoModels = rptKalaInfoModels;
        lastSelectedItem = -1;
    }

    @NonNull
    @Override
    public RptKalaInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_kala_info_customlist , parent , false);
        return new RptKalaInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RptKalaInfoAdapter.ViewHolder holder, int position)
    {
        RptKalaInfoModel model = rptKalaInfoModels.get(position);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String gheymatForosh = formatter.format(model.getGheymatForoshAsli());
        String gheymatMasrafKonande = formatter.format(model.getGheymatMasrafKonandeh());
        int haveMaliatAvarezResId = model.getMashmolMaliatAvarez() == 0 ? R.drawable.ic_error :  R.drawable.ic_success;

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom , holder.itemView.findViewById(R.id.layBottom));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Top , holder.itemView.findViewById(R.id.layTop));

        holder.lblKalaNameCode.setText(String.format("%1$s - %2$s", model.getCodeKala(), model.getNameKala()));
        holder.lblGheymatForoshMain.setText(String.format("%1$s \n %2$s %3$s", context.getResources().getString(R.string.mablaghVahed), gheymatForosh, context.getResources().getString(R.string.rial)));
        holder.lblGheymatMasrafKonandeMain.setText(String.format("%1$s \n %2$s %3$s", context.getResources().getString(R.string.mablaghVahed), gheymatForosh, context.getResources().getString(R.string.rial)));
        holder.lblCodeNameKala.setText(String.format("%1$s - %2$s", model.getCodeKala(), model.getNameKala()));
        holder.lblNameBrand.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.nameBrand), model.getNameBrand()));
        holder.lblBarcode.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.barcode), model.getBarCode()));
        holder.lblShomareBach.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.shomareBach), model.getShomarehBach()));
        holder.imgHaveMaliatAvarez.setImageResource(haveMaliatAvarezResId);
        holder.lblGheymatForoshDetail.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.mablaghForosh), gheymatForosh, context.getResources().getString(R.string.rial)));
        holder.lblGheymatMasrafKonande.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.gheymatMasrafKonande), gheymatMasrafKonande, context.getResources().getString(R.string.rial)));
        holder.lblVaznKhales.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.vaznKhales), model.getVaznKhales(), model.getNameVahedSize()));
        holder.lblVaznCarton.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.vaznCarton), model.getVaznKarton(), context.getResources().getString(R.string.gram)));
        holder.lblTedadDarCarton.setText(String.format("%1$s: %2$s %3$s", context.getResources().getString(R.string.tedadDarCarton), model.getTedadDarKarton(), model.getNameVahedShomaresh()));
        if (model.getTedadDarKarton() == model.getTedadDarBasteh())
        {
            holder.lblTedadDarBaste.setVisibility(View.GONE);
        }
        else
        {
            holder.lblTedadDarBaste.setText(String.format("%1$s: %2$s", context.getResources().getString(R.string.tedadDarBaste), model.getTedadDarBasteh()));
        }
        holder.lblDimen.setText(String.format("%1$s: %2$s * %3$s * %4$s %5$s", context.getResources().getString(R.string.dimens), model.getErtefa(), model.getArz(), model.getTol(), model.getNameVahedSize()));
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
            Log.i("imageHashSize", "onBindViewHolder: "+ AllKalaPhoto.size());

            Glide.with(context)
                    .load(AllKalaPhoto.get(model.getCcKalaCode()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.nopic_whit)
                    .into(holder.imgKala);

            Glide.with(context)
                    .load(AllKalaPhoto.get(model.getCcKalaCode()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.nopic_whit)
                    .into(holder.imgKalaFull);

//            File file = new File(Environment.getExternalStorageDirectory() + "/SapHamrah/Album/" , model.getCodeKala() + ".jpg");
//            if (file.exists())
//            {
//                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/SapHamrah/Album/" + model.getCodeKala() + ".jpg");
//                holder.imgKala.setImageBitmap(bitmap);
//                holder.imgKalaFull.setImageBitmap(bitmap);
//            }
//            else
//            {
//                holder.imgKala.setImageResource(R.drawable.nopic_whit);
//                holder.imgKalaFull.setImageResource(R.drawable.nopic_whit);
//            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            holder.imgKala.setImageResource(R.drawable.nopic_whit);
            holder.imgKalaFull.setImageResource(R.drawable.nopic_whit);
            PubFunc.Logger logger = new PubFunc().new Logger ();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptKalaInfoAdapter" , "" , "onBindViewHolder" , "");
        }
    }


    @Override
    public int getItemCount()
    {
        return rptKalaInfoModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SwipeLayout swipeLayout;
        private LinearLayout layStatus;
        private TextView lblKalaNameCode;
        private TextView lblGheymatForoshMain;
        private TextView lblGheymatMasrafKonandeMain;
        //detail
        private TextView lblCodeNameKala;
        private TextView lblNameBrand;
        private TextView lblBarcode;
        private TextView lblShomareBach;
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
            layStatus = view.findViewById(R.id.layStatus);
            lblKalaNameCode = view.findViewById(R.id.lblNameCode);
            lblGheymatForoshMain = view.findViewById(R.id.lblGheymatForoshMain);
            lblGheymatMasrafKonandeMain = view.findViewById(R.id.lblGheymatMasrafKonandeMain);
            lblCodeNameKala = view.findViewById(R.id.lblNameCodeKala);
            lblNameBrand = view.findViewById(R.id.lblNameBrand);
            lblBarcode = view.findViewById(R.id.lblBarcode);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
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
            lblCodeNameKala.setTypeface(font);
            lblGheymatForoshMain.setTypeface(font);
            lblGheymatMasrafKonandeMain.setTypeface(font);
            lblNameBrand.setTypeface(font);
            lblBarcode.setTypeface(font);
            lblShomareBach.setTypeface(font);
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
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }



    public void setKalaPhoto(ArrayList<KalaPhotoModel> kalaPhotoModels){
        this.kalaPhotoModels=kalaPhotoModels;
        this.AllKalaPhoto.clear();
        for(KalaPhotoModel kalaPhotoModel:kalaPhotoModels){
            this.AllKalaPhoto.put(kalaPhotoModel.getCcKalaCodeDb(),kalaPhotoModel.getImageDb());
        }

    }

    public SparseArray getKalaPhoto(){
        return AllKalaPhoto;
    }
    public ArrayList<KalaPhotoModel> getKalaPhotos(){
        return kalaPhotoModels;
    }


    public ArrayList<RptKalaInfoModel> getRptKalaInfoModels() {
        return rptKalaInfoModels;
    }
}
