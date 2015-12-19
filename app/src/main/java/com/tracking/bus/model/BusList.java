package com.tracking.bus.model;

import com.tracking.bus.JSON.Json2BusList;

/**
 * Created by Hananideen on 19/12/2015.
 */
public class BusList {

    public String BusNumber, Frequency, OperationStart, OperationEnd;

    public BusList() {}

    public BusList (String busName, String frequency, String operationStart, String operationEnd){
        BusNumber = busName;
        Frequency = frequency;
        OperationStart = operationStart;
        OperationEnd = operationEnd;
    }

    public BusList (Json2BusList jBus) {
        BusNumber = jBus.busName;
        //Frequency = jBus.frequency;
        //OperationStart = jBus.operationStart;
        //OperationEnd = jBus.operationEnd;
    }

    public String getBusNumber(){
        return BusNumber;
    }

    public String getFrequency(){
        return Frequency;
    }

    public String getOperationStart(){
        return OperationStart;
    }

    public String getOperationEnd(){
        return OperationEnd;
    }
}
