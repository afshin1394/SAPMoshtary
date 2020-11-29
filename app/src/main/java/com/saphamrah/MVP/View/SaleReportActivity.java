package com.saphamrah.MVP.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.saphamrah.BaseMVP.SaleReportMVP;
import com.saphamrah.MVP.Presenter.SaleReportPresenter;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;


public class SaleReportActivity extends AppCompatActivity implements SaleReportMVP.RequiredViewOps
{
    private BarChart barChartCountFaktor;
    private BarChart barChartMablaghFaktor;
    /*private BarChart barChartCountMarjoee;
    private BarChart barChartMablaghMarjoee;*/

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , SaleReportActivity.this);
    SaleReportMVP.PresenterOps mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_report);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        barChartCountFaktor = (BarChart)findViewById(R.id.barChartCountFaktor);
        barChartMablaghFaktor = (BarChart)findViewById(R.id.barChartMablaghFaktor);
        /*barChartCountMarjoee = (BarChart)findViewById(R.id.barChartCountMarjoee);
        barChartMablaghMarjoee = (BarChart)findViewById(R.id.barChartMablaghMarjoee);*/
        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        final FloatingActionMenu fabMenu = (FloatingActionMenu)findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = (FloatingActionButton)findViewById(R.id.fabRefresh);

        setNoTextData();
        startMVPOps();

        mPresenter.getSaleReport();


        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                mPresenter.updateSaleReport();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleReportActivity.this.finish();
            }
        });

    }


    private void setNoTextData()
    {
        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));

        barChartCountFaktor.setNoDataText(getResources().getString(R.string.errorGetData));
        barChartCountFaktor.setNoDataTextColor(Color.RED);
        barChartCountFaktor.setNoDataTextTypeface(font);

        barChartMablaghFaktor.setNoDataText(getResources().getString(R.string.errorGetData));
        barChartMablaghFaktor.setNoDataTextColor(Color.RED);
        barChartMablaghFaktor.setNoDataTextTypeface(font);

        /*barChartCountMarjoee.setNoDataText(getResources().getString(R.string.errorGetData));
        barChartCountMarjoee.setNoDataTextColor(Color.RED);
        barChartCountMarjoee.setNoDataTextTypeface(font);

        barChartMablaghMarjoee.setNoDataText(getResources().getString(R.string.errorGetData));
        barChartMablaghMarjoee.setNoDataTextColor(Color.RED);
        barChartMablaghMarjoee.setNoDataTextTypeface(font);*/
    }


    @Override
    public Context getAppContext()
    {
        return SaleReportActivity.this;
    }

    @Override
    public void drawCountFaktorChart(int countFaktorRooz, int countFaktorMah)
    {
        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        barChartCountFaktor.setDrawBarShadow(false);
        barChartCountFaktor.setDrawValueAboveBar(true);
        barChartCountFaktor.getDescription().setEnabled(false);
        barChartCountFaktor.setNoDataText(getResources().getString(R.string.errorGetData));
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChartCountFaktor.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        barChartCountFaktor.setPinchZoom(false);
        barChartCountFaktor.setDrawGridBackground(false);
        ValueFormatter xAxisFormatter = new SaleReportActivity.TypeAxisValueFormatter(barChartCountFaktor);

        XAxis xAxis = barChartCountFaktor.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(font);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(2);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = barChartCountFaktor.getAxisLeft();
        leftAxis.setTypeface(font);
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChartCountFaktor.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(font);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChartCountFaktor.setFitBars(true);
        barChartCountFaktor.animateY(2500);

        Legend legend = barChartCountFaktor.getLegend();
        legend.setForm(Legend.LegendForm.EMPTY);

        ArrayList<BarEntry> values = new ArrayList<>();
        BarEntry todayEntry = new BarEntry(1f , countFaktorRooz);
        BarEntry untilTodayEntry = new BarEntry(2f , countFaktorMah);
        values.add(todayEntry);
        values.add(untilTodayEntry);

        BarDataSet datasetAmarForosh = new BarDataSet(values , "");
        datasetAmarForosh.setDrawIcons(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(datasetAmarForosh);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        barChartCountFaktor.setData(data);
    }

    @Override
    public void drawMablaghFaktorChart(float mablaghFaktorRooz , float mablaghFaktorMah)
    {
        /*Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        barChartMablaghFaktor.setDrawBarShadow(false);
        barChartMablaghFaktor.setDrawValueAboveBar(true);
        barChartMablaghFaktor.getDescription().setEnabled(false);
        barChartMablaghFaktor.setNoDataText(getResources().getString(R.string.errorGetData));
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChartMablaghFaktor.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        barChartMablaghFaktor.setPinchZoom(false);
        barChartMablaghFaktor.setDrawGridBackground(false);
        ValueFormatter xAxisFormatter = new SaleReportActivity.TypeAxisValueFormatter(barChartMablaghFaktor);

        XAxis xAxis = barChartMablaghFaktor.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(font);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(2);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = barChartMablaghFaktor.getAxisLeft();
        leftAxis.setTypeface(font);
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChartMablaghFaktor.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(font);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChartMablaghFaktor.setFitBars(true);
        barChartMablaghFaktor.animateY(2500);

        Legend legend = barChartMablaghFaktor.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(15f);
        legend.setTypeface(font);
        legend.setXEntrySpace(4f);
        legend.setFormSize(15f);

        ArrayList<BarEntry> values = new ArrayList<>();
        BarEntry todayEntry = new BarEntry(1f , mablaghFaktorRooz);
        BarEntry untilTodayEntry = new BarEntry(2f + BAR_WIDTH , 100);
        values.add(todayEntry);
        values.add(untilTodayEntry);


        ArrayList<BarEntry> values2 = new ArrayList<>();
        BarEntry todayEntry2 = new BarEntry(2f , mablaghFaktorRooz);
        BarEntry untilTodayEntry2 = new BarEntry(1f + BAR_WIDTH , mablaghFaktorMah);
        values2.add(todayEntry2);
        values2.add(untilTodayEntry2);


        BarDataSet datasetAmarForosh = new BarDataSet(values , "تا امروز");
        datasetAmarForosh.setDrawIcons(false);
        datasetAmarForosh.setColor(Color.CYAN);

        BarDataSet datasetAmarForosh2 = new BarDataSet(values2 , "ماه");
        datasetAmarForosh2.setDrawIcons(false);
        datasetAmarForosh2.setColor(Color.RED);


        BarData data = new BarData(datasetAmarForosh , datasetAmarForosh2);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        barChartMablaghFaktor.setData(data);
        barChartMablaghFaktor.getBarData().setBarWidth(BAR_WIDTH);*/

    }

    @Override
    public void drawCountMarjoeeChart(int countMarjoeeRooz, int countMarjoeeMah)
    {
        /*Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        barChartCountMarjoee.setDrawBarShadow(false);
        barChartCountMarjoee.setDrawValueAboveBar(true);
        barChartCountMarjoee.getDescription().setEnabled(false);
        barChartCountMarjoee.setNoDataText(getResources().getString(R.string.errorGetData));
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChartCountMarjoee.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        barChartCountMarjoee.setPinchZoom(false);
        barChartCountMarjoee.setDrawGridBackground(false);
        ValueFormatter xAxisFormatter = new SaleReportActivity.TypeAxisValueFormatter(barChartCountMarjoee);

        XAxis xAxis = barChartCountMarjoee.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(font);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(2);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = barChartCountMarjoee.getAxisLeft();
        leftAxis.setTypeface(font);
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChartCountMarjoee.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(font);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChartCountMarjoee.setFitBars(true);
        barChartCountMarjoee.animateY(2500);

        Legend legend = barChartCountMarjoee.getLegend();
        legend.setForm(Legend.LegendForm.EMPTY);

        ArrayList<BarEntry> values = new ArrayList<>();
        BarEntry todayEntry = new BarEntry(1f , countMarjoeeRooz);
        BarEntry untilTodayEntry = new BarEntry(2f , countMarjoeeMah);
        values.add(todayEntry);
        values.add(untilTodayEntry);

        BarDataSet datasetAmarForosh = new BarDataSet(values , "");
        datasetAmarForosh.setDrawIcons(false);
        datasetAmarForosh.setColor(Color.RED);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(datasetAmarForosh);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        barChartCountMarjoee.setData(data);*/
    }

    @Override
    public void drawMablaghMarjoeeChart(float mablaghMarjoeeRooz , float mablaghMarjoeeMah)
    {
        /*Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        barChartMablaghMarjoee.setDrawBarShadow(false);
        barChartMablaghMarjoee.setDrawValueAboveBar(true);
        barChartMablaghMarjoee.getDescription().setEnabled(false);
        barChartMablaghMarjoee.setNoDataText(getResources().getString(R.string.errorGetData));
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChartMablaghMarjoee.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        barChartMablaghMarjoee.setPinchZoom(false);
        barChartMablaghMarjoee.setDrawGridBackground(false);
        ValueFormatter xAxisFormatter = new SaleReportActivity.TypeAxisValueFormatter(barChartMablaghMarjoee);

        XAxis xAxis = barChartMablaghMarjoee.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(font);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(2);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = barChartMablaghMarjoee.getAxisLeft();
        leftAxis.setTypeface(font);
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChartMablaghMarjoee.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(font);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChartMablaghMarjoee.setFitBars(true);
        barChartMablaghMarjoee.animateY(2500);

        Legend legend = barChartMablaghMarjoee.getLegend();
        legend.setForm(Legend.LegendForm.EMPTY);

        ArrayList<BarEntry> values = new ArrayList<>();
        BarEntry todayEntry = new BarEntry(1f , mablaghMarjoeeRooz);
        BarEntry untilTodayEntry = new BarEntry(2f , mablaghMarjoeeMah);
        values.add(todayEntry);
        values.add(untilTodayEntry);

        BarDataSet datasetAmarForosh = new BarDataSet(values , "");
        datasetAmarForosh.setDrawIcons(false);
        datasetAmarForosh.setColor(Color.YELLOW);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(datasetAmarForosh);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        barChartMablaghMarjoee.setData(data);*/
    }

    @Override
    public void drawMablaghForoshChart(float mablaghFaktorRooz, float mablaghFaktorMah, float mablaghMarjoeeRooz, float mablaghMarjoeeMah)
    {
        float groupSpace = 0.6f;
        float barSpace = 0.0f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet
        // (0.43 + 0.03) * 2 + 0.08 = 1.00 -> interval per "group"

        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        barChartMablaghFaktor.setDrawBarShadow(false);
        barChartMablaghFaktor.setDrawValueAboveBar(true);
        barChartMablaghFaktor.getDescription().setEnabled(false);
        barChartMablaghFaktor.setNoDataText(getResources().getString(R.string.errorGetData));
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChartMablaghFaktor.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        barChartMablaghFaktor.setPinchZoom(false);
        barChartMablaghFaktor.setDrawGridBackground(false);

        Legend legend = barChartMablaghFaktor.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(15f);
        legend.setTypeface(font);
        legend.setXEntrySpace(4f);
        legend.setFormSize(15f);


        XAxis xAxis = barChartMablaghFaktor.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(font);
        //xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(2);
        xAxis.setTextSize(13F);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value)
            {
                if (value == 0)
                {
                    return getResources().getString(R.string.today);
                }
                else if (value == 1)
                {
                    return getResources().getString(R.string.untilToday);
                }
                else
                {
                    return "";
                }
            }
        });


        YAxis leftAxis = barChartMablaghFaktor.getAxisLeft();
        leftAxis.setTypeface(font);
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChartMablaghFaktor.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(font);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChartMablaghFaktor.setFitBars(true);
        barChartMablaghFaktor.animateY(2500);


        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        values1.add(new BarEntry(0, mablaghFaktorRooz));
        values2.add(new BarEntry(0, mablaghMarjoeeRooz));
        values1.add(new BarEntry(1, mablaghFaktorMah));
        values2.add(new BarEntry(1, mablaghMarjoeeMah));

        BarDataSet set1, set2;
        if (barChartMablaghFaktor.getData() != null && barChartMablaghFaktor.getData().getDataSetCount() > 0)
        {
            set1 = (BarDataSet) barChartMablaghFaktor.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) barChartMablaghFaktor.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            set1.setValueTextSize(15f);
            set2.setValueTextSize(15f);
            barChartMablaghFaktor.getData().notifyDataChanged();
            barChartMablaghFaktor.notifyDataSetChanged();
        }
        else
        {
            set1 = new BarDataSet(values1, getResources().getString(R.string.forosh));
            set1.setColor(getResources().getColor(R.color.colorForoshChart));
            set2 = new BarDataSet(values2, getResources().getString(R.string.marjoee));
            set2.setColor(getResources().getColor(R.color.colorMarjoeeChart));
            set1.setValueTextSize(15f);
            set2.setValueTextSize(15f);

            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(font);

            barChartMablaghFaktor.setData(data);
        }


        // specify the width each bar should have
        barChartMablaghFaktor.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        barChartMablaghFaktor.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChartMablaghFaktor.getXAxis().setAxisMaximum(barChartMablaghFaktor.getBarData().getGroupWidth(groupSpace, barSpace) * 2);
        barChartMablaghFaktor.groupBars(0, groupSpace, barSpace);
        barChartMablaghFaktor.invalidate();

    }

    @Override
    public void drawCountForoshChart(int countFaktorRooz, int countFaktorMah, int countMarjoeeRooz, int countMarjoeeMah)
    {
        float groupSpace = 0.6f;
        float barSpace = 0.0f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet
        // (0.43 + 0.03) * 2 + 0.08 = 1.00 -> interval per "group"

        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        barChartCountFaktor.setDrawBarShadow(false);
        barChartCountFaktor.setDrawValueAboveBar(true);
        barChartCountFaktor.getDescription().setEnabled(false);
        barChartCountFaktor.setNoDataText(getResources().getString(R.string.errorGetData));
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChartCountFaktor.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        barChartCountFaktor.setPinchZoom(false);
        barChartCountFaktor.setDrawGridBackground(false);

        XAxis xAxis = barChartCountFaktor.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(font);
        //xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(2);
        xAxis.setTextSize(13f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value)
            {
                if (value == 0)
                {
                    return getResources().getString(R.string.today);
                }
                else if (value == 1)
                {
                    return getResources().getString(R.string.untilToday);
                }
                else
                {
                    return "";
                }
            }
        });

        YAxis leftAxis = barChartCountFaktor.getAxisLeft();
        leftAxis.setTypeface(font);
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChartCountFaktor.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(font);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChartCountFaktor.setFitBars(true);
        barChartCountFaktor.animateY(2500);


        Legend legend = barChartCountFaktor.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(15f);
        legend.setTypeface(font);
        legend.setXEntrySpace(4f);
        legend.setFormSize(15f);

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        values1.add(new BarEntry(0.0f, countFaktorRooz));
        values2.add(new BarEntry(0.0f, countMarjoeeRooz));
        values1.add(new BarEntry(1.0f, countFaktorMah));
        values2.add(new BarEntry(1.0f, countMarjoeeMah));

        BarDataSet set1, set2;
        if (barChartCountFaktor.getData() != null && barChartCountFaktor.getData().getDataSetCount() > 0)
        {
            set1 = (BarDataSet) barChartCountFaktor.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) barChartCountFaktor.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            set1.setValueTextSize(15f);
            set2.setValueTextSize(15f);
            barChartCountFaktor.getData().notifyDataChanged();
            barChartCountFaktor.notifyDataSetChanged();
        }
        else
        {
            set1 = new BarDataSet(values1, getResources().getString(R.string.forosh));
            set1.setColor(getResources().getColor(R.color.colorForoshChart));
            set2 = new BarDataSet(values2, getResources().getString(R.string.marjoee));
            set2.setColor(getResources().getColor(R.color.colorMarjoeeChart));
            set1.setValueTextSize(15f);
            set2.setValueTextSize(15f);

            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(font);

            barChartCountFaktor.setData(data);
        }

        // specify the width each bar should have
        barChartCountFaktor.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        barChartCountFaktor.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChartCountFaktor.getXAxis().setAxisMaximum(barChartMablaghFaktor.getBarData().getGroupWidth(groupSpace, barSpace) * 2);
        barChartCountFaktor.groupBars(0, groupSpace, barSpace);
        barChartCountFaktor.invalidate();
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(SaleReportActivity.this);
        customAlertDialog.showToast(SaleReportActivity.this, getResources().getString(resId), messageType, duration);
    }


    public void startMVPOps()
    {
        try
        {
            if ( stateMaintainer.firstTimeIn() )
            {
                initialize(this);
            }
            else
            {
                reinitialize(this);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleReportActivity", "startMVPOps", "");
        }
    }


    private void initialize(SaleReportMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new SaleReportPresenter(view);
            stateMaintainer.put(SaleReportMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleReportActivity", "initialize", "");
        }
    }


    private void reinitialize(SaleReportMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(SaleReportMVP.PresenterOps.class.getSimpleName());
            if ( mPresenter == null )
            {
                initialize( view );
            }
            else
            {
                mPresenter.onConfigurationChanged(view);
            }
        }
        catch (Exception exception)
        {
            if (mPresenter != null)
            {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleReportActivity", "reinitialize", "");
            }
        }
    }




    private class TypeAxisValueFormatter extends ValueFormatter
    {
        private final BarLineChartBase<?> chart;
        private final String[] values = new String[]{getResources().getString(R.string.today) , getResources().getString(R.string.untilToday) };

        public TypeAxisValueFormatter(BarLineChartBase<?> chart)
        {
            this.chart = chart;
        }

        @Override
        public String getFormattedValue(float value)
        {
            return values[(int)value - 1];
        }

    }


}
