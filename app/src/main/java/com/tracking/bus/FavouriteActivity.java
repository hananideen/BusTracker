package com.tracking.bus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tracking.bus.adapter.FavoriteAdapter;
import com.tracking.bus.model.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hananideen on 19/12/2015.
 */
public class FavouriteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lvFavList;
    List<Favorite> Favorite;
    FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Favorite = new ArrayList<Favorite>();
        favoriteAdapter = new FavoriteAdapter(this, Favorite);
        lvFavList = (ListView) findViewById(R.id.lvFav);
        lvFavList.setAdapter(favoriteAdapter);
        lvFavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FavouriteActivity.this, "Coming soon :)", Toast.LENGTH_SHORT).show();
            }
        });

        LoadData();
        favoriteAdapter.notifyDataSetChanged();

    }

    public void LoadData (){

        for(int i =0;i<3;i++)
        {
            Favorite restaurants = new Favorite();
            restaurants.setBusNumber("Rapid 50" + i);
            restaurants.setBusRoute("Cyberjaya - Putrajaya");
            Favorite.add(restaurants);
        }
    }
}
