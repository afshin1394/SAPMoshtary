package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptVisitForoshandeAdapter extends RecyclerView.Adapter<RptVisitForoshandeAdapter.ViewHolder>
{

    DecimalFormat formatter;
    private Context context;
    private ArrayList<RptVisitForoshandehMoshtaryModel> models;
    private int lastPosition = -1;
    private int showSingle;
//    private int kharidKardeh = 0;
//    private int adamSefaresh = 0;
//    private int adamDarkhast = 0;
//    private int adamMoraje = 0;

    public RptVisitForoshandeAdapter(Context context, ArrayList<RptVisitForoshandehMoshtaryModel> models, int showSingle)
    {
        this.context = context;
        this.models = models;
        formatter = new DecimalFormat("#,###,###");
        this.showSingle = showSingle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_visit_foroshandeh_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder , final int position)
    {
            RptVisitForoshandehMoshtaryModel model = models.get(position);

            holder.lblOlaviat.setText(" " + (model.getOlaviat()));
            holder.lblCustomerCode.setText(" " + model.getCodeMoshtary() + " - " + model.getNameMoshtary());
//            holder.lblCustomerName.setText(model.getNameMoshtary());
            holder.lblRialKharid.setText(" "  + formatter.format(model.getRialKharid()));
            holder.lblSaateVorod.setText(model.getSaatVorodBeMaghazeh());
            holder.lblSaateKhoroj.setText(model.getSaatKhorojAzMaghazeh());
            holder.lblZamanDarMaghaze.setText(" " + model.getZamanDarMaghazeh());
//        holder.lblVazeiatMorajeh.setText(model.getVazeiatMorajeh());
            holder.lblDalilDarkhastManfi.setText(model.getDalilDarkhastManfi());
            holder.lblTedadAghlam.setText(String.valueOf(model.getTedad_AghlamFaktor()));


            if (model.getIsMorajeh() == 1) {
                setColorForDarkhast(holder);
                holder.adadarkhastDetail.setVisibility(View.GONE);
                holder.kharidDetailLl.setVisibility(View.VISIBLE);
            }
            else if (model.getIsMorajeh() == -1 && model.getCodeNoeAdamDarkhast() == 1) {

                holder.adadarkhastDetail.setVisibility(View.VISIBLE);
                holder.kharidDetailLl.setVisibility(View.GONE);
                setColorForAdamDarkhast(holder);
            } else if (model.getIsMorajeh() == -1 && model.getCodeNoeAdamDarkhast() == 0) {
                setColorForAdamSefarsh(holder);
                holder.adadarkhastDetail.setVisibility(View.VISIBLE);
                holder.kharidDetailLl.setVisibility(View.GONE);
            } else {
                holder.adadarkhastDetail.setVisibility(View.GONE);
                holder.kharidDetailLl.setVisibility(View.GONE);
                setColorForMorajehNashode(holder);
            }


            setAnimation(holder.itemView, position);
    }


    private void setColorForDarkhast(ViewHolder holder)
    {
        holder.rootLay.setBackgroundResource( R.color.green_vf);
    }


    private void setColorForAdamDarkhast(ViewHolder holder)
    {
        holder.rootLay.setBackgroundResource(R.color.yellow_vf);
    }


    private void setColorForMorajehNashode(ViewHolder holder)
    {

        holder.rootLay.setBackgroundResource(R.color.gray_vf);

    }

    private void setColorForAdamSefarsh(ViewHolder holder)
    {

        holder.rootLay.setBackgroundResource(R.color.orange_vf);
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }


    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {

        LinearLayout rootLay;
        TextView lblOlaviat;
        TextView lblCustomerCode;
//        TextView lblCustomerName;
        TextView lblRialKharid;
        TextView lblSaateVorod;
        TextView lblSaateKhoroj;
        TextView lblZamanDarMaghaze;
//        TextView lblVazeiatMorajeh;
        TextView lblDalilDarkhastManfi;
        TextView lblTedadAghlam;
//        ImageView lay_expand_btn;
//        LinearLayout laySecondCustomer;
        LinearLayout adadarkhastDetail;
        LinearLayout kharidDetailLl;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblOlaviat = itemView.findViewById(R.id.lblOlaviat);
            lblCustomerCode = itemView.findViewById(R.id.lblCustomerCode);
//            lblCustomerName = itemView.findViewById(R.id.lblCustomerName);
            lblRialKharid = itemView.findViewById(R.id.lblRialKharid);
            lblSaateVorod = itemView.findViewById(R.id.lblSaateVorod);
            lblSaateKhoroj = itemView.findViewById(R.id.lblSaateKhoroj);
            lblZamanDarMaghaze = itemView.findViewById(R.id.lblZamanDarMaghaze);
//            lblVazeiatMorajeh = itemView.findViewById(R.id.lblVazeiatMorajee);
            lblDalilDarkhastManfi = itemView.findViewById(R.id.lblDalilDarkhastManfi);
            lblTedadAghlam = itemView.findViewById(R.id.lblTedadAghlam);
            adadarkhastDetail = itemView.findViewById(R.id.adadarkhast_detail);
            kharidDetailLl = itemView.findViewById(R.id.kharid_detail_ll);
            rootLay = itemView.findViewById(R.id.root_layview_card);
//            lay_expand_btn = itemView.findViewById(R.id.expand_btn);
//            laySecondCustomer = itemView.findViewById(R.id.visit_forosh_linear_second);

            lblOlaviat.setTypeface(font);
            lblCustomerCode.setTypeface(font);
//            lblCustomerName.setTypeface(font);
            lblRialKharid.setTypeface(font);
            lblSaateVorod.setTypeface(font);
            lblSaateKhoroj.setTypeface(font);
            lblZamanDarMaghaze.setTypeface(font);
//            lblVazeiatMorajeh.setTypeface(font);
            lblDalilDarkhastManfi.setTypeface(font);
            lblTedadAghlam.setTypeface(font);


        }

//        protected void setPosExpand(int position){
//            if (models.get(position).getExtraProp_OpenView() == 1){
//                expand(laySecondCustomer,position);
//                lay_expand_btn.setRotation(180);
//            } else {
//                laySecondCustomer.setVisibility(View.GONE);
//                lay_expand_btn.setRotation(0);
//            }
//
            /**
             * click listener for open and close details
             */
//            lay_expand_btn.setOnClickListener(v -> {
//                if (laySecondCustomer.getVisibility() == View.GONE) {
//                    expand(laySecondCustomer , position);
//                    lay_expand_btn.setRotation(180);
//                } else {
//                    collapse(laySecondCustomer, position);
//                    lay_expand_btn.setRotation(0);
//                }
//            });
//        }

    }

    public void expand(final View v , int position) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration(400);
        v.startAnimation(a);
        models.get(position).setExtraProp_OpenView(1);
    }

    public void collapse(final View v ,int position) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration(400);
        v.startAnimation(a);
        models.get(position).setExtraProp_OpenView(0);
    }
}
