package com.tracking.bus;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tracking.bus.JSON.JSON2BusList;
import com.tracking.bus.adapter.BusListAdapter;
import com.tracking.bus.model.BusList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView tvName;
    private ImageView ivIcon;
    GPSTracker gps;
    LatLng myLoc, busStop, bus;
    double latitude, longitude;
    String sendLat, sendLong;
    GoogleMap map;
    private ListView lvBusList;
    List<BusList> BusList;
    BusListAdapter busListAdapter;
    Polyline line;
    TextView tvDistanceDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new NearbyFragment()).commit();
//        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        nvDrawer.addHeaderView(header);
        nvDrawer.inflateHeaderView(R.layout.nav_header2);

        tvName = (TextView) header.findViewById(R.id.tvName);
        ivIcon = (ImageView) header.findViewById(R.id.ivIcon);

        tvDistanceDuration = (TextView) findViewById(R.id.tv_distance_time);

        setupDrawerContent(nvDrawer);

        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fm.getMap();

        gps = new GPSTracker(MainActivity.this);
        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            myLoc = new LatLng(latitude, longitude);
            sendLat =  String.valueOf(latitude);
            sendLong = String.valueOf(longitude);
            connect(sendLat, sendLong);

            CameraUpdate zoomLocation = CameraUpdateFactory.newLatLngZoom(myLoc, 15);
            map.addMarker(new MarkerOptions().position(myLoc).title("My Location ")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_men)));
            map.animateCamera(zoomLocation);

            busStop = new LatLng(2.9445361,101.7654237);
            map.addMarker(new MarkerOptions().position(busStop).title("Bus Stop")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_busstand)));

            String url = getDirectionsUrl(busStop, myLoc);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);

        } else {
            gps.showSettingsAlert();
        }

        BusList = new ArrayList<BusList>();
        busListAdapter = new BusListAdapter(this, BusList);
        lvBusList = (ListView) findViewById(R.id.lvBus);
        lvBusList.setAdapter(busListAdapter);

        lvBusList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusList busList = busListAdapter.getBusList(position);
                bus = new LatLng(Double.parseDouble(busList.getBusLat()),Double.parseDouble(busList.getBusLng()));
                map.addMarker(new MarkerOptions().position(bus).title(busList.getBusNumber())
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_bus)));
                //Toast.makeText(MainActivity.this, busList.getBusLat() +busList.getBusLng(), Toast.LENGTH_SHORT).show();
                CameraUpdate zoomLocation = CameraUpdateFactory.newLatLngZoom(bus, 15);
                map.animateCamera(zoomLocation);
            }
        });

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        //Fragment fragment = null;

        //Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                //fragmentClass = NearbyFragment.class;
                break;
            case R.id.nav_second_fragment:
                Intent intent1 = new Intent(MainActivity.this, FindBusActivity.class);
                startActivity(intent1);
                //fragmentClass = PlannerFragment.class;
                break;
            case R.id.nav_third_fragment:
                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
                //fragmentClass = FavouriteFragment.class;
                break;
            case R.id.nav_fourth_fragment:
                //fragmentClass = FavouriteFragment.class;
                break;
            default:
                //fragmentClass = NearbyFragment.class;
        }

//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

//        menuItem.setChecked(true);
//        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }


    public void connect (String lat, String lng){
        JsonArrayRequest busRequest = new JsonArrayRequest("http://188.166.234.67/hackathon/nearby/bus/" +lat +"/" +lng +"/", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        BusList busList = new BusList(new JSON2BusList(obj));
                        BusList.add(busList);
                        busListAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyServer", "Error: " + error.getMessage());
                Toast.makeText(getApplication(), "Cannot connect to server", Toast.LENGTH_SHORT).show();
                busListAdapter.notifyDataSetChanged();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(busRequest);
    }

    private String getDirectionsUrl(LatLng destination ,LatLng origin){

        String str_destination = "destination="+destination.latitude+","+destination.longitude;
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        String sensor = "sensor=false";
        String parameters = str_destination+"&"+str_origin+"&"+sensor;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionJSONParser parser = new DirectionJSONParser();

                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            if(result.size()<1){
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){
                        duration = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            tvDistanceDuration.setText("Distance to bus stop: "+distance);

            line = map.addPolyline(lineOptions);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}