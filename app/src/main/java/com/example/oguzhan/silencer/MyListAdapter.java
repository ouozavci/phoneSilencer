package com.example.oguzhan.silencer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by oguzhan on 1.4.2016.
 */
public class MyListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<LocRecord> locations;

    public MyListAdapter(Activity activity,ArrayList<LocRecord> locationList){
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        locations = locationList;
    }
    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Object getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return locations.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null){
            vi = inflater.inflate(R.layout.list_item,null);
        }

        TextView textView = (TextView) vi.findViewById(R.id.row_textview);
        TextView textView2 = (TextView) vi.findViewById(R.id.row_textview2);
        LocRecord location = locations.get(position);

        textView.setText(location.getName());
        textView2.setText(location.getLat()+"-"+location.getLon());
        return vi;
    }
}
