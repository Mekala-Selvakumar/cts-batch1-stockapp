package com.learn.stockapp.exceptions;

public class StockNotFoundException  extends  Exception {
    public StockNotFoundException(String message) {
        super(message);
    }

}
