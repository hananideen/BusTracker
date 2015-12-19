package com.tracking.bus.model;

import com.tracking.bus.JSON.JSON2BusList;

/**
 * Created by Hananideen on 19/12/2015.
 */
public class BusList {

    public String BusNumber, BusLat, BusLng, ETA;

    public BusList() {}

    public BusList (String busName, String frequency, String operationStart, String eta){
        BusNumber = busName;
        BusLat = frequency;
        BusLng = operationStart;
        ETA = eta;
    }

    public BusList (JSON2BusList jBus) {
        BusNumber = jBus.busName;
        BusLat = jBus.busLat;
        BusLng = jBus.busLng;
        ETA = jBus.eta;
    }

    public String getBusNumber(){
        return BusNumber;
    }

    public String getBusLat(){
        return BusLat;
    }

    public String getBusLng(){
        return BusLng;
    }

    public String getETA(){
        return ETA;
    }
}
