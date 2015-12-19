package com.tracking.bus.model;

/**
 * Created by Hananideen on 20/12/2015.
 */
public class Favorite {

    public String BusNumber, BusRoute;

    public Favorite() {}

    public Favorite (String busName, String busRoute){
        BusNumber = busName;
        BusRoute = busRoute;
    }

    public String getBusNumber () {
        return BusNumber;
    }

    public String getBusRoute() {
        return BusRoute;
    }

    public void setBusNumber (String busNumber) {
        BusNumber = busNumber;
    }

    public void setBusRoute (String busRoute) {
        BusRoute = busRoute;
    }
}
