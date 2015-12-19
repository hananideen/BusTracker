package com.tracking.bus.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tracking.bus.R;
import com.tracking.bus.model.Favorite;

import java.util.List;

/**
 * Created by Hananideen on 20/12/2015.
 */
public class FavoriteAdapter extends BaseAdapter{
    private Activity mactivity;
    private List<Favorite> Favorite;
    private LayoutInflater inflater;
    private Favorite favorite;

    public FavoriteAdapter (Activity mactivity, List<Favorite> Favorite){
        this.mactivity = mactivity;
        this.Favorite = Favorite;
    }

    @Override
    public int getCount() {
        return Favorite.size();
    }

    @Override
    public Object getItem(int position) {
        return Favorite.get(position);
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
            convertView = inflater.inflate(R.layout.fav_row, parent, false);

        }

        TextView busNum = (TextView) convertView.findViewById(R.id.tvBusNum);
        TextView busRoute = (TextView) convertView.findViewById(R.id.tvBusRoute);

        favorite = Favorite.get(position);

        busNum.setText(favorite.getBusNumber());
        busRoute.setText(favorite.getBusRoute());


        return convertView;
    }

    public Favorite getFavorite (int position) {
        return Favorite.get(position);
    }
}
