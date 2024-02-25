package com.learn.stockapp.exceptions;

public class StockAlreadyExistException extends Exception{
    public StockAlreadyExistException(String message) {
        super(message);
    }

}
