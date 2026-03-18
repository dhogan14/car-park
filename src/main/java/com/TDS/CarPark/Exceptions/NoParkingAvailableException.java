package com.TDS.CarPark.Exceptions;

public class NoParkingAvailableException extends Exception{

    public String returnMessage;

    public NoParkingAvailableException(String errorMessage, String returnMessage) {
        super(errorMessage);
        this.returnMessage = returnMessage;
    }
}
