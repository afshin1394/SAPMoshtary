package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.R;

import java.util.HashMap;
import java.util.List;

public class DrawerExpandablelistAdapter extends BaseExpandableListAdapter
{

    private Context context;
    private List<SystemMenuModel> listHeaders;
    private HashMap<SystemMenuModel, List<SystemMenuModel>> listChils;

    public DrawerExpandablelistAdapter(Context context, List<SystemMenuModel> listDataHeader, HashMap<SystemMenuModel, List<SystemMenuModel>> listChildData)
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
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        SystemMenuModel systemMenuModel = (SystemMenuModel)getChild(groupPosition , childPosition);
        final String childText = systemMenuModel.getMenuName();
        final String imageURI = "drawable/" + systemMenuModel.getIconName();
        //final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drawer_child, null);
        }
        TextView txtListChild = (TextView) convertView.findViewById(R.id.lbldrawerChild);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        txtListChild.setText(childText);
        txtListChild.setTypeface(font);
        imgIcon.setImageResource(context.getResources().getIdentifier(imageURI, null, context.getPackageName()));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChils.get(this.listHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        SystemMenuModel systemMenuModel = (SystemMenuModel) getGroup(groupPosition);
        String headerTitle = systemMenuModel.getMenuName();
        final String imageURI = "drawable/" + systemMenuModel.getIconName();
        //String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drawer_header, null);
        }
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblDrawerHeader);
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        lblListHeader.setText(headerTitle);
        lblListHeader.setTypeface(font);
        imgIcon.setImageResource(context.getResources().getIdentifier(imageURI, null, context.getPackageName()));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
