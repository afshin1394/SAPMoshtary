package com.saphamrah.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.R;
import com.saphamrah.databinding.RptJashnvarehSecondlevelCustomlistBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RptJashnvarehSecondLevelAdapter extends RecyclerView.Adapter<RptJashnvarehSecondLevelAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels;
    private IRptJashnvarehSecondLevelAdapter iRptJashnvarehSecondLevelAdapter;
    private RptJashnvarehActivity.State state;


    private RptJashnvarehSecondlevelCustomlistBinding binding;

    public void initChild(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels, int position) {
        this.jashnavareForoshModels.get(position).setRptJashnvarehForoshModels(jashnvarehForoshModels);
        notifyItemChanged(position);
    }


    public RptJashnvarehSecondLevelAdapter(Context context, ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels, IRptJashnvarehSecondLevelAdapter iRptJashnvarehSecondLevelAdapter) {
        this.context = context;
        this.jashnavareForoshModels = jashnavareForoshModels;
        this.iRptJashnvarehSecondLevelAdapter = iRptJashnvarehSecondLevelAdapter;
    }

    @NonNull
    @Override
    public RptJashnvarehSecondLevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.rpt_jashnvareh_secondlevel_customlist, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RptJashnvarehSecondLevelAdapter.ViewHolder holder, int position) {
        RptJashnvarehForoshModel rptJashnvarehForoshModel = jashnavareForoshModels.get(position);
        switch (state) {
            case Jashnvareh:
                holder.bindJashnvareh(rptJashnvarehForoshModel);
                break;
            case Moshtary:
                holder.bindMoshtary(rptJashnvarehForoshModel);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return jashnavareForoshModels.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        RptJashnvarehSecondlevelCustomlistBinding jashnvarehCustomlistBinding;

        public ViewHolder(@NonNull RptJashnvarehSecondlevelCustomlistBinding binding) {
            super(binding.getRoot());
            this.jashnvarehCustomlistBinding = binding;


            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));
            jashnvarehCustomlistBinding.lblName.setTypeface(font);
            jashnvarehCustomlistBinding.lblEmtiaz.setTypeface(font);
            jashnvarehCustomlistBinding.lblRialEmtiaz.setTypeface(font);
            itemView.setOnClickListener(v -> {
                if (jashnavareForoshModels.get(getAdapterPosition()).getRptJashnvarehForoshModels().size() > 0) {
                    if (jashnavareForoshModels.get(getAdapterPosition()).isExpanded()) {
                        jashnavareForoshModels.get(getAdapterPosition()).setExpanded(false);
                        collapse(jashnvarehCustomlistBinding.recyclerView);
                        jashnvarehCustomlistBinding.expandBtn.setRotation(0);
                    } else {
                        jashnavareForoshModels.get(getAdapterPosition()).setExpanded(true);
                        iRptJashnvarehSecondLevelAdapter.onItemClick(jashnavareForoshModels.get(getAdapterPosition()), getAdapterPosition());
                        jashnvarehCustomlistBinding.expandBtn.setRotation(180);
                    }
                } else {
                    jashnavareForoshModels.get(getAdapterPosition()).setExpanded(true);
                    iRptJashnvarehSecondLevelAdapter.onItemClick(jashnavareForoshModels.get(getAdapterPosition()), getAdapterPosition());
                    jashnvarehCustomlistBinding.expandBtn.setRotation(0);
                }
            });

        }

        public void bindMoshtary(RptJashnvarehForoshModel rptJashnvarehForoshModel) {

            jashnvarehCustomlistBinding.lblName.setText(String.format("%1$s-%2$s", rptJashnvarehForoshModel.getCodeMoshtary(),rptJashnvarehForoshModel.getNameMoshtary()));
            jashnvarehCustomlistBinding.lblEmtiaz.setText(String.format("%1$s %2$s", rptJashnvarehForoshModel.getEmtiazMoshtary(),context.getResources().getString(R.string.emtiaz)));
            jashnvarehCustomlistBinding.lblRialEmtiaz.setText(String.format("%1$s %2$s", (int) rptJashnvarehForoshModel.getRialEmtiazMoshtary(), context.getResources().getString(R.string.rial)));
            jashnvarehCustomlistBinding.ImgName.setImageResource(R.drawable.ic_customers);

            if (rptJashnvarehForoshModel.isExpanded()) {

                jashnvarehCustomlistBinding.expandBtn.setRotation(180);
                RptJashnvarehThirdLevelAdapter jashnvarehThirdLevelAdapter = new RptJashnvarehThirdLevelAdapter(state, context, rptJashnvarehForoshModel.getRptJashnvarehForoshModels());
                jashnvarehCustomlistBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                jashnvarehCustomlistBinding.recyclerView.setAdapter(jashnvarehThirdLevelAdapter);
                expand(jashnvarehCustomlistBinding.recyclerView);

            } else {
                jashnvarehCustomlistBinding.expandBtn.setRotation(0);
                collapse(jashnvarehCustomlistBinding.recyclerView);
            }
        }


        public void bindJashnvareh(RptJashnvarehForoshModel rptJashnvarehForoshModel) {
            jashnvarehCustomlistBinding.lblName.setText(String.format("%1$s", rptJashnvarehForoshModel.getSharhJashnvareh()));
            jashnvarehCustomlistBinding.lblEmtiaz.setText(String.format("%1$s %2$s", rptJashnvarehForoshModel.getEmtiazMoshtary(),context.getResources().getString(R.string.emtiaz)));
            jashnvarehCustomlistBinding.lblRialEmtiaz.setText(String.format("%1$s %2$s", (int) rptJashnvarehForoshModel.getRialEmtiazMoshtary(), context.getResources().getString(R.string.rial)));
            jashnvarehCustomlistBinding.ImgName.setImageResource(R.drawable.ic_jashnvareh_foreground);

            if (rptJashnvarehForoshModel.isExpanded()) {
                jashnvarehCustomlistBinding.expandBtn.setRotation(180);
                RptJashnvarehThirdLevelAdapter jashnvarehThirdLevelAdapter = new RptJashnvarehThirdLevelAdapter(state, context, rptJashnvarehForoshModel.getRptJashnvarehForoshModels());
                jashnvarehCustomlistBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                jashnvarehCustomlistBinding.recyclerView.setAdapter(jashnvarehThirdLevelAdapter);
                expand(jashnvarehCustomlistBinding.recyclerView);
            } else {
                jashnvarehCustomlistBinding.expandBtn.setRotation(0);
                collapse(jashnvarehCustomlistBinding.recyclerView);
            }
        }
    }

    public void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);


        int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration(500);
        v.startAnimation(a);



    }

    public void collapse(final View v) {

        int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration(500);
        v.startAnimation(a);
    }


    public interface IRptJashnvarehSecondLevelAdapter {
        void onItemClick(RptJashnvarehForoshModel iRptJashnvarehForoshModel, int position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setViewType(RptJashnvarehActivity.State state, int position) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void clearAll() {
        if (jashnavareForoshModels != null) {
            if (jashnavareForoshModels.size() > 0)
                jashnavareForoshModels.clear();
        }

    }


}
