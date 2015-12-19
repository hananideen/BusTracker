package com.tracking.bus.JSON;

import org.json.JSONObject;

/**
 * Created by Hananideen on 19/12/2015.
 */
public class JSON2BusList {

    public JSONObject jsonObject;
    public String busName, busLat, busLng, eta;

    public JSON2BusList(){}

    public JSON2BusList(JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            busName= json.optString("bus_id");
            busLat = json.optString("lat");
            busLng = json.optString("lng");
            eta = json.optString("eta");
        }
        else{
            busName = "";
            busLat = "";
            busLng = "";
            eta = "";
        }
    }
}
