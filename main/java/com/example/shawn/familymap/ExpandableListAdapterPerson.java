package com.example.shawn.familymap;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Handles person expandable list
 */
public class ExpandableListAdapterPerson extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapterPerson(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_person, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        int color = 0;
        if (childText.contains("female")) {
            color = ContextCompat.getColor(convertView.getContext(), R.color.femaleIcon);
        } else if (childText.contains("male")) {
            color = ContextCompat.getColor(convertView.getContext(), R.color.maleIcon);
        } else if (childText.contains("birth")) {
            color = ContextCompat.getColor(convertView.getContext(), R.color.birthEvent);
        } else if (childText.contains("census")) {
            color = ContextCompat.getColor(convertView.getContext(), R.color.censusEvent);
        } else if (childText.contains("marriage")) {
            color = ContextCompat.getColor(convertView.getContext(), R.color.marriageEvent);
        } else if (childText.contains("christening")) {
            color = ContextCompat.getColor(convertView.getContext(), R.color.christeningEvent);
        } else if (childText.contains("baptism")) {
            color = ContextCompat.getColor(convertView.getContext(), R.color.baptismEvent);
        } else if (childText.contains("death")) {
            color = ContextCompat.getColor(convertView.getContext(), R.color.deathEvent);
        }
        txtListChild.setTextColor(color);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this._listDataChild.get(this._listDataHeader.get(groupPosition)) == null) {
            return 0;
        }
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_person, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

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