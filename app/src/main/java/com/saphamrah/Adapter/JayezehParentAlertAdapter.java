package com.saphamrah.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.R;
import com.saphamrah.UIModel.JayezehByccKalaCodeModel;

import java.util.ArrayList;

public class JayezehParentAlertAdapter extends RecyclerView.Adapter<JayezehParentAlertAdapter.MyViewHolder>
{
    OnItemClickListener listener;
    private ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeParentModels;
    private int ccJayezeh = -1;
    private Context context;
    private int tedadKala;
    private double mablaghForosh ;
    private int ccKalaCode ;
    private Long ccDarkhastFaktor;
    JayezehAlertAdapter jayezehAlertAdapter;
    private boolean first = true;

    public JayezehParentAlertAdapter(Context context , ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeParentModels , int tedadKala, double mablaghForosh , int ccKalaCode , Long ccDarkhastFaktor , OnItemClickListener listener)
    {
        this.context = context;
        this.listener=listener;
        this.jayezehByccKalaCodeParentModels = jayezehByccKalaCodeParentModels;
        this.tedadKala = tedadKala;
        this.mablaghForosh = mablaghForosh;
        this.ccKalaCode = ccKalaCode;
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jayezeh_parent_alert_customlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        // show position 0 for first time
        if (jayezehByccKalaCodeParentModels!=null)
        if (position == 0 && jayezehByccKalaCodeParentModels.size()>0){
            holder.bind(0);
            first = false;
        }
        holder.lbl_sharhJayezeh.setText(jayezehByccKalaCodeParentModels.get(position).getSharhJayezeh());

        holder.expand_btn.setOnClickListener(v->{
            holder.bind(position);
        });

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    @Override
    public int getItemCount() {
        return jayezehByccKalaCodeParentModels.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView lbl_sharhJayezeh;
        private RecyclerView recycler_view;
        private ImageView expand_btn;


        private MyViewHolder(View view) {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            lbl_sharhJayezeh = view.findViewById(R.id.lbl_sharhJayezeh);
            recycler_view = view.findViewById(R.id.recycler_view);
            expand_btn = view.findViewById(R.id.expand_btn);
            lbl_sharhJayezeh.setTypeface(font);
            recycler_view.setLayoutManager(new LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false));

        }

        /**
         *  expand and collapse item
         *  click listener  and go to model and get jayezeh details
         * @param position
         */
        void bind( int position )
        {
            ccJayezeh = jayezehByccKalaCodeParentModels.get(position).getCcJayezeh() ;
            if (recycler_view.getVisibility() == View.GONE) {

                /**
                 * boolean first for remove lag show first time
                 */
                if (first){
                    expand(recycler_view );
                    expand_btn.setRotation(0);
                    listener.onItemClick(ccJayezeh , tedadKala,mablaghForosh,ccKalaCode,ccDarkhastFaktor,position);

                    if (jayezehByccKalaCodeParentModels.get(position).getJayezehByccKalaCodeModels() != null){
                        if (jayezehByccKalaCodeParentModels.get(position).getJayezehByccKalaCodeModels().size() > 0){
                            jayezehAlertAdapter = new JayezehAlertAdapter(context , jayezehByccKalaCodeParentModels.get(position).getJayezehByccKalaCodeModels() , jayezehByccKalaCodeParentModels.get(position).getTedadKalaDetails() , jayezehByccKalaCodeParentModels.get(position).getMablaghKol());
                            recycler_view.setAdapter(jayezehAlertAdapter);
                        }
                    }

                } else {
                    listener.onItemClick(ccJayezeh , tedadKala,mablaghForosh,ccKalaCode,ccDarkhastFaktor,position);

                    if (jayezehByccKalaCodeParentModels.get(position).getJayezehByccKalaCodeModels() != null){
                        if (jayezehByccKalaCodeParentModels.get(position).getJayezehByccKalaCodeModels().size() > 0){
                            jayezehAlertAdapter = new JayezehAlertAdapter(context , jayezehByccKalaCodeParentModels.get(position).getJayezehByccKalaCodeModels() , jayezehByccKalaCodeParentModels.get(position).getTedadKalaDetails() , jayezehByccKalaCodeParentModels.get(position).getMablaghKol());
                            recycler_view.setAdapter(jayezehAlertAdapter);
                        }
                    }
                    expand(recycler_view );
                    expand_btn.setRotation(0);
                }



            } else {
                collapse(recycler_view);
                jayezehByccKalaCodeParentModels.get(position).setJayezehByccKalaCodeModels(null);
                jayezehByccKalaCodeParentModels.get(position).setTedadKalaDetails(0);
                jayezehByccKalaCodeParentModels.get(position).setMablaghKol(0);
                expand_btn.setRotation(180);
            }
        }

    }

    // item expand
    public static void expand(final View v) {
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
    }

    // item collapse
    public static void collapse(final View v) {
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
    }

    public interface OnItemClickListener
    {
        void onItemClick(int ccJayezeh , int tedadKala, double mablaghForosh, int ccKalaCode, Long ccDarkhastFaktor , int position);
    }

}
