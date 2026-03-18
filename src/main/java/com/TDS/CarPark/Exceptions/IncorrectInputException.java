package com.TDS.CarPark.Exceptions;

public class IncorrectInputException extends Exception{

    public String returnMessage;

    public IncorrectInputException(String errorMessage, String returnMessage) {
        super(errorMessage);
        this.returnMessage = returnMessage;
    }

}
