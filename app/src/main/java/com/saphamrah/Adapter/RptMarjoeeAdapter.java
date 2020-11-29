package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.saphamrah.Model.RptMarjoeeKalaModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class RptMarjoeeAdapter extends BaseExpandableListAdapter
{

    private Context context;
    private List<RptMarjoeeKalaModel> listHeaders;
    private HashMap<RptMarjoeeKalaModel, List<RptMarjoeeKalaModel>> listChils;

    public RptMarjoeeAdapter(Context context, List<RptMarjoeeKalaModel> listDataHeader, HashMap<RptMarjoeeKalaModel, List<RptMarjoeeKalaModel>> listChildData)
    {
        this.context = context;
        this.listHeaders = listDataHeader;
        this.listChils = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon)
    {
        return this.listChils.get(this.listHeaders.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        RptMarjoeeKalaModel rptMarjoeeKalaModel = (RptMarjoeeKalaModel)getChild(groupPosition , childPosition);

        if (convertView == null)
        {
            try
            {
                LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.rpt_marjoee_row_content_customlist, null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        TextView lblCustomerCodeName = convertView.findViewById(R.id.lblCustomerCodeName);
        TextView lblShomareBach = convertView.findViewById(R.id.lblShomareBach);
        TextView lblMablagh = convertView.findViewById(R.id.lblMablagh);
        TextView lblCount = convertView.findViewById(R.id.lblCount);

        lblCustomerCodeName.setText(String.format("%1$s - %2$s", rptMarjoeeKalaModel.getCodeKala(), rptMarjoeeKalaModel.getNameKala()));
        lblShomareBach.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareBach), rptMarjoeeKalaModel.getShomarehBach()));
        lblMablagh.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.mablagh), formatter.format(rptMarjoeeKalaModel.getFee())));
        lblCount.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.count), rptMarjoeeKalaModel.getTedad3()));

        lblCustomerCodeName.setTypeface(font);
        lblShomareBach.setTypeface(font);
        lblMablagh.setTypeface(font);
        lblCount.setTypeface(font);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return this.listChils.get(this.listHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this.listHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return this.listHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        RptMarjoeeKalaModel rptMarjoeeKalaModel = (RptMarjoeeKalaModel) getGroup(groupPosition);
        if (convertView == null)
        {
            try
            {
                LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.rpt_marjoee_row_title_customlist, null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }


        TextView lblCustomerCodeName = convertView.findViewById(R.id.lblCustomerCodeName);
        TextView lblShomareFaktor = convertView.findViewById(R.id.lblShomareFaktor);
        TextView lblSumCost = convertView.findViewById(R.id.lblSumCost);
        TextView lblSumCount = convertView.findViewById(R.id.lblSumCount);

        lblCustomerCodeName.setText(String.format("%1$s - %2$s", rptMarjoeeKalaModel.getCodeMoshtary(), rptMarjoeeKalaModel.getNameMoshtary()));
        lblShomareFaktor.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareFaktor), rptMarjoeeKalaModel.getShomarehFaktor()));
        lblSumCost.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.sumCost), formatter.format(rptMarjoeeKalaModel.getFee())));
        lblSumCount.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.sumTedad), rptMarjoeeKalaModel.getTedad3()));

        lblCustomerCodeName.setTypeface(font);
        lblShomareFaktor.setTypeface(font);
        lblSumCost.setTypeface(font);
        lblSumCount.setTypeface(font);

        return convertView;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }


}
