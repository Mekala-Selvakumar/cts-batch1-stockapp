package com.learn.stockapp.service;

import com.learn.stockapp.exceptions.StockAlreadyExistException;
import com.learn.stockapp.exceptions.StockNotFoundException;

/* create  interface with methods 
   to  retrive data from  third party API called TweleveData.com based on the country
 * retrive   all stocks ,delete ,save stocks from the database
 retrive data based on the symbol
 retrie stocks based on exchange and country
 */

import com.learn.stockapp.model.Stock;
import com.learn.stockapp.model.StockList;

import java.util.List;

public interface StockListingService {
    public StockList getStocksByCountryFromAPI(String country);
    public List<Stock> getAllStocks();
    public Stock getStockBySymbol(String symbol) throws StockNotFoundException;
    public List<Stock> getStocksByExchangeAndCountry(String exchange, String country) throws StockNotFoundException;
    public Stock saveStock(Stock stocks) throws StockAlreadyExistException;
    public boolean deleteStock(String symbol) throws StockNotFoundException;
}


 



 


 
