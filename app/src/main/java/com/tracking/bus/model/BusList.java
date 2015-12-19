package com.tracking.bus.model;

import com.tracking.bus.JSON.Json2BusList;

/**
 * Created by Hananideen on 19/12/2015.
 */
public class BusList {

    public String BusNumber, BusLat, BusLng, OperationEnd;

    public BusList() {}

    public BusList (String busName, String frequency, String operationStart, String operationEnd){
        BusNumber = busName;
        BusLat = frequency;
        BusLng = operationStart;
        OperationEnd = operationEnd;
    }

    public BusList (Json2BusList jBus) {
        BusNumber = jBus.busName;
        BusLat = jBus.busLat;
        BusLng = jBus.busLng;
        //OperationEnd = jBus.operationEnd;
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

    public String getOperationEnd(){
        return OperationEnd;
    }
}
