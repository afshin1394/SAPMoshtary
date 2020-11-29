package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.RptMarjoeeKalaModel;
import com.saphamrah.R;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class RptMarjoeePrintAdapter extends RecyclerView.Adapter<RptMarjoeePrintAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<RptMarjoeeKalaModel> models;

    public RptMarjoeePrintAdapter(Context context, ArrayList<RptMarjoeeKalaModel> models)
    {
        this.context = context;
        this.models = models;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rpt_marjoee_content_customlist , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if (models.get(position).getRadif() == -1)
        {
            holder.lblCodeName.setText(String.format("%1$s - %2$s", models.get(position).getCodeMoshtary(), models.get(position).getNameMoshtary()));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0 , LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0 , 25 , 0 , 0);
            params.weight = 2;
            holder.lblSumCost.setLayoutParams(params);

            params = new LinearLayout.LayoutParams(0 , LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0 , 25 , 0 , 0);
            params.weight = 3;
            holder.lblCodeName.setLayoutParams(params);

            holder.lblShomareBach.setVisibility(View.GONE);
            holder.lblCount.setVisibility(View.GONE);
            holder.lblCost.setVisibility(View.GONE);
            holder.lblSumCost.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareFaktor), models.get(position).getShomarehFaktor()));
        }
        else
        {
            DecimalFormat formatter = new DecimalFormat("#,###,###");

            holder.lblShomareBach.setVisibility(View.VISIBLE);
            holder.lblCount.setVisibility(View.VISIBLE);
            holder.lblCost.setVisibility(View.VISIBLE);
            holder.lblCodeName.setText(String.format("%1$s - %2$s", models.get(position).getCodeKala(), models.get(position).getNameKala()));
            holder.lblShomareBach.setText(models.get(position).getShomarehBach());
            holder.lblCount.setText(String.valueOf(models.get(position).getTedad3()));
            holder.lblCost.setText(formatter.format(models.get(position).getFee()));
            holder.lblSumCost.setText(formatter.format(models.get(position).getFee() * models.get(position).getTedad3()));
        }
    }

    @Override
    public int getItemCount()
    {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView lblCodeName;
        TextView lblShomareBach;
        TextView lblCount;
        TextView lblCost;
        TextView lblSumCost;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblCodeName = itemView.findViewById(R.id.lblNameKala);
            lblShomareBach = itemView.findViewById(R.id.lblShomarehBach);
            lblCount = itemView.findViewById(R.id.lblTedad);
            lblCost = itemView.findViewById(R.id.lblFee);
            lblSumCost = itemView.findViewById(R.id.lblSumFee);

            lblCodeName.setTypeface(font , Typeface.NORMAL);
            lblShomareBach.setTypeface(font , Typeface.NORMAL);
            lblCount.setTypeface(font , Typeface.NORMAL);
            lblCost.setTypeface(font , Typeface.NORMAL);
            lblSumCost.setTypeface(font , Typeface.NORMAL);

        }
    }


}

/*public class RptMarjoeePrintAdapter extends BaseExpandableListAdapter
{

    private Context context;
    private List<RptMarjoeeKalaModel> listHeaders;
    private HashMap<RptMarjoeeKalaModel, List<RptMarjoeeKalaModel>> listChils;

    public RptMarjoeePrintAdapter(Context context, List<RptMarjoeeKalaModel> listDataHeader, HashMap<RptMarjoeeKalaModel, List<RptMarjoeeKalaModel>> listChildData)
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
                convertView = infalInflater.inflate(R.layout.rpt_marjoee_content_customlist, null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        TextView lblNameKala = convertView.findViewById(R.id.lblNameKala);
        TextView lblShomareBach = convertView.findViewById(R.id.lblShomarehBach);
        TextView lblMablagh = convertView.findViewById(R.id.lblFee);
        TextView lblSumMablagh = convertView.findViewById(R.id.lblSumFee);
        TextView lblCount = convertView.findViewById(R.id.lblTedad);

        lblNameKala.setTypeface(font);
        lblShomareBach.setTypeface(font);
        lblMablagh.setTypeface(font);
        lblSumMablagh.setTypeface(font);
        lblCount.setTypeface(font);

        if (rptMarjoeeKalaModel.getRadif() == -1)
        {
            lblNameKala.setText("");
            lblShomareBach.setText(context.getResources().getString(R.string.sum));
            lblMablagh.setText("");
            lblSumMablagh.setText(formatter.format(rptMarjoeeKalaModel.getFee()));
            lblCount.setText(String.valueOf(rptMarjoeeKalaModel.getTedad3()));
        }
        else
        {
            lblNameKala.setText(String.format("%1$s - %2$s", rptMarjoeeKalaModel.getCodeKala(), rptMarjoeeKalaModel.getNameKala()));
            lblShomareBach.setText(rptMarjoeeKalaModel.getShomarehBach());
            lblMablagh.setText(formatter.format(rptMarjoeeKalaModel.getFee()));
            lblSumMablagh.setText(formatter.format(rptMarjoeeKalaModel.getFee() * rptMarjoeeKalaModel.getTedad3()));
            lblCount.setText(String.valueOf(rptMarjoeeKalaModel.getTedad3()));
        }

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
        RptMarjoeeKalaModel rptMarjoeeKalaModel = (RptMarjoeeKalaModel) getGroup(groupPosition);
        if (convertView == null)
        {
            try
            {
                LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.rpt_marjoee_title_customlist, null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }


        TextView lblCustomerCodeName = convertView.findViewById(R.id.lblCustomerCodeName);
        TextView lblShomareFaktor = convertView.findViewById(R.id.lblShomareFaktor);

        lblCustomerCodeName.setText(String.format("%1$s - %2$s", rptMarjoeeKalaModel.getCodeMoshtary(), rptMarjoeeKalaModel.getNameMoshtary()));
        lblShomareFaktor.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.shomareFaktor), rptMarjoeeKalaModel.getShomarehFaktor()));

        lblCustomerCodeName.setTypeface(font);
        lblShomareFaktor.setTypeface(font);

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

}*/
