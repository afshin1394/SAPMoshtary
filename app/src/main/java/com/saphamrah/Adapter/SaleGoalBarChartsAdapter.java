package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.saphamrah.MVP.View.RptSaleGoalActivity;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;

import java.util.ArrayList;

import static com.saphamrah.MVP.View.RptSaleGoalActivity.ShowType;


public class SaleGoalBarChartsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels = new ArrayList<>();
    Context context;
    OnItemClickListener listener;
    private int lastPosition = -1;
    ArrayList<String> xAxisLabels;
    ArrayList<String> legendLables;
    private float limitLine = 85f;

    public static final int NUMERICAL_CHART = 0;
    public static final int PERCENTAGE_CHART = 1;
    public static final int TABLE = 2;


    public SaleGoalBarChartsAdapter(ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels, Context context, OnItemClickListener listener) {
        this.rptBrandHadafForoshModels = rptBrandHadafForoshModels;
        this.context = context;
        this.listener = listener;
        xAxisLabels = new ArrayList<>();
        legendLables = new ArrayList<>();

    }

    public SaleGoalBarChartsAdapter(ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels, Context context) {
        this.rptBrandHadafForoshModels = rptBrandHadafForoshModels;
        this.context = context;
        xAxisLabels = new ArrayList<>();
        legendLables = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case (TABLE):
                Log.i("onCreateViewHolder", "onCreateViewHolder:getCalledTable ");
                View viewTable = LayoutInflater.from(parent.getContext()).inflate(R.layout.hadaf_forosh_customlist_table, parent, false);
                return new ViewHolderSaleGoalTable(viewTable);
            default:
                Log.i("onCreateViewHolder", "onCreateViewHolder:getCalledCharts ");

                View viewCharts = LayoutInflater.from(parent.getContext()).inflate(R.layout.hadaf_forosh_customlist_chart, parent, false);
                return new ViewHolderSaleGoalCharts(viewCharts);
        }
    }



    @Override
    public int getItemViewType(int position) {

        switch (((RptSaleGoalActivity)context).ShowType) {
            case Numerical:
                return NUMERICAL_CHART;
            case Percentage:
                return PERCENTAGE_CHART;
            case Table:
                return TABLE;
            default:
                return NUMERICAL_CHART;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseHadafForoshModel baseHadafForoshModel = rptBrandHadafForoshModels.get(position);
        switch (holder.getItemViewType()) {
            case (PERCENTAGE_CHART):
                ViewHolderSaleGoalCharts viewHolderSaleGoalCharts1 = (ViewHolderSaleGoalCharts)holder;
                ((ViewHolderSaleGoalCharts) viewHolderSaleGoalCharts1).bindPercentage(baseHadafForoshModel, position);

                break;

            case (NUMERICAL_CHART):
                ViewHolderSaleGoalCharts viewHolderSaleGoalChart2 = (ViewHolderSaleGoalCharts)holder;
                ((ViewHolderSaleGoalCharts) viewHolderSaleGoalChart2).bindNumerical(baseHadafForoshModel, position);

                break;

            case (TABLE):
                ViewHolderSaleGoalTable viewHolderSaleGoalTable = (ViewHolderSaleGoalTable)holder;
                ((ViewHolderSaleGoalTable) viewHolderSaleGoalTable).bindTable(baseHadafForoshModel, position);

                break;
        }



    }


    @Override
    public int getItemCount() {
        return rptBrandHadafForoshModels.size();
    }

    public class  ViewHolderSaleGoalTable extends RecyclerView.ViewHolder{
        private TextView tableTitle, foroshMah, hadafMah, foroshRooz, hadafRooz;

        public ViewHolderSaleGoalTable(@NonNull View itemView) {
            super(itemView);
            Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), itemView.getContext().getString(R.string.fontPath));


            tableTitle = itemView.findViewById(R.id.valNameTitle);
            foroshRooz = itemView.findViewById(R.id.valForoshRooz);
            hadafRooz = itemView.findViewById(R.id.valHadafRooz);
            foroshMah = itemView.findViewById(R.id.valForoshMah);
            hadafMah = itemView.findViewById(R.id.valHadafMah);
            tableTitle.setTypeface(font);
            tableTitle.setTypeface(font);
            foroshRooz.setTypeface(font);
            hadafRooz.setTypeface(font);
            foroshMah.setTypeface(font);
            hadafMah.setTypeface(font);
        }
        public void bindTable(BaseHadafForoshModel baseHadafForoshModel, int position) {
            setAnimation(itemView,position);
            String name = "";
            if (RptSaleGoalActivity.activityState.equals("FragmentLevel1")) {
                name = baseHadafForoshModel.getNameBrand();
                Log.i("getBrandName", "bindNumerical:1 " + name);
                tableTitle.setText(Html.fromHtml("<u>"+name+"</u>"));



            } else if (RptSaleGoalActivity.activityState.equals("FragmentLevel2")) {
                name = baseHadafForoshModel.getNameGorohKala();
                Log.i("getBrandName", "bindNumerical:2" + name);
                tableTitle.setText(Html.fromHtml("<u>"+name+"</u>"));


            } else {
                name = baseHadafForoshModel.getNameKala();
                Log.i("getBrandName", "bindNumerical:3 " + name);
                tableTitle.setTextColor(context.getResources().getColor(R.color.colorBlack));
                tableTitle.setText(name);

            }
//            tableTitle.setText(name);
            foroshRooz.setText(String.valueOf(baseHadafForoshModel.getTedadForoshRooz()));
            hadafRooz.setText(String.valueOf(baseHadafForoshModel.getTedadHadafRooz()));
            foroshMah.setText(String.valueOf(baseHadafForoshModel.getTedadForoshMah())  );
            hadafMah.setText(String.valueOf(baseHadafForoshModel.getTedadHadafMah()));
            if (RptSaleGoalActivity.activityState.equals("FragmentLevel1") || RptSaleGoalActivity.activityState.equals("FragmentLevel2")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("activityState", "onClick: " + RptSaleGoalActivity.activityState);
                        listener.onItemClickListener(baseHadafForoshModel, position);
                    }
                });
            }

        }
    }

    public class ViewHolderSaleGoalCharts extends RecyclerView.ViewHolder {
        //TableView

        private TextView textlblHadaf;
        private BarChart barChartHadaf;




        public ViewHolderSaleGoalCharts(@NonNull View itemView) {
            super(itemView);

            Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), itemView.getContext().getString(R.string.fontPath));


                    textlblHadaf = itemView.findViewById(R.id.lblChartBrandHadaf);
                    barChartHadaf = itemView.findViewById(R.id.barChartBrandHadaf);
                    xAxisLabels.add(itemView.getResources().getString(R.string.untilToday));
                    xAxisLabels.add(itemView.getResources().getString(R.string.today));
                    legendLables.add(itemView.getResources().getString(R.string.saleLegendLable));
                    legendLables.add(itemView.getResources().getString(R.string.goalLegendLabel));
                    textlblHadaf.setTypeface(font);


            }
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void bindNumerical(BaseHadafForoshModel baseHadafForoshModel, int position) {


//            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            setAnimation(itemView,position);
            textlblHadaf.setText(baseHadafForoshModel.getNameBrand());
            Log.i("NameBrand", "bindNumerical: " + baseHadafForoshModel.getNameBrand());
            String name = "";
            if (RptSaleGoalActivity.activityState.equals("FragmentLevel1")) {
                name = baseHadafForoshModel.getNameBrand();
                Log.i("getBrandName", "bindNumerical:1 " + name);

            } else if (RptSaleGoalActivity.activityState.equals("FragmentLevel2")) {
                name = baseHadafForoshModel.getNameGorohKala();
                Log.i("getBrandName", "bindNumerical:2" + name);

            } else {
                name = baseHadafForoshModel.getNameKala();

                Log.i("getBrandName", "bindNumerical:3 " + name);

            }

            textlblHadaf.setText(name);
            new PubFunc().new ChartUtils().drawGroupBarBarChart(context, barChartHadaf, baseHadafForoshModel.getTedadForoshMah(), baseHadafForoshModel.getTedadHadafMah(), baseHadafForoshModel.getTedadForoshRooz(), baseHadafForoshModel.getTedadHadafRooz(), name, xAxisLabels, legendLables, limitLine);

            if (RptSaleGoalActivity.activityState.equals("FragmentLevel1") || RptSaleGoalActivity.activityState.equals("FragmentLevel2")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("activityState", "onClick: " + RptSaleGoalActivity.activityState);
                        listener.onItemClickListener(baseHadafForoshModel, position);
                    }
                });
            }


        }


        public void bindPercentage(BaseHadafForoshModel baseHadafForoshModel, int position) {

            setAnimation(itemView,position);
            textlblHadaf.setText(baseHadafForoshModel.getNameBrand());
            String name = "";
            if (RptSaleGoalActivity.activityState.equals("FragmentLevel1")) {
                name = baseHadafForoshModel.getNameBrand();
                Log.i("getBrandName", "bindNumerical:1 " + name);


            } else if (RptSaleGoalActivity.activityState.equals("FragmentLevel2")) {
                name = baseHadafForoshModel.getNameGorohKala();
                Log.i("getBrandName", "bindNumerical:2" + name);


            } else {
                name = baseHadafForoshModel.getNameKala();
                Log.i("getBrandName", "bindNumerical:3 " + name);

            }
            textlblHadaf.setText(name);

            ArrayList<Float> percentValues = new PubFunc().new ChartUtils().calculatePercentFromNumeric(baseHadafForoshModel.getTedadForoshMah(), baseHadafForoshModel.getTedadHadafMah(), baseHadafForoshModel.getTedadForoshRooz(), baseHadafForoshModel.getTedadHadafRooz());
            new PubFunc().new ChartUtils().drawSingleBarBarChart(context, barChartHadaf, percentValues.get(0), percentValues.get(1), name, xAxisLabels, limitLine, true);
            if (RptSaleGoalActivity.activityState.equals("FragmentLevel1") || RptSaleGoalActivity.activityState.equals("FragmentLevel2")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("activityState", "onClick: " + RptSaleGoalActivity.activityState);
                        listener.onItemClickListener(baseHadafForoshModel, position);
                    }
                });
            }

        }


        }









    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(BaseHadafForoshModel baseHadafForoshModel, int position);


    }
}
