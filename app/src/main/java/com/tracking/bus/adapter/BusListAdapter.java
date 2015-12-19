package com.tracking.bus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.tracking.bus.R;
import com.tracking.bus.model.BusList;

import java.util.List;

/**
 * Created by Hananideen on 19/12/2015.
 */
public class BusListAdapter extends BaseAdapter {
    private Activity mactivity;
    private List<BusList> BusList;
    private LayoutInflater inflater;
    private BusList busList;

    public BusListAdapter (Activity mactivity, List<BusList> BusList){
        this.mactivity = mactivity;
        this.BusList = BusList;
    }

    @Override
    public int getCount() {
        return BusList.size();
    }

    @Override
    public Object getItem(int position) {
        return BusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {

            inflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.buslist_row, parent, false);

        }

        TextView busNum = (TextView) convertView.findViewById(R.id.tvBusNum);
        TextView busRoute = (TextView) convertView.findViewById(R.id.tvBusRoute);
        TextView busETA = (TextView) convertView.findViewById(R.id.tvETA);

        busList = BusList.get(position);

        busNum.setText(busList.getBusNumber());
        //busRoute.setText(busList.getOperationStart());
        //busETA.setText(busList.getFrequency());

        return convertView;
    }

    public BusList getBusList (int position) {
        return BusList.get(position);
    }
}
