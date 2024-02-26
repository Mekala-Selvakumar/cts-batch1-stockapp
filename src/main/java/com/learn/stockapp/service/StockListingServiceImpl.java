package com.learn.stockapp.service;

/* create class   implements  all the methods from  StockListing Service
  * throw StockNotFoundException if the stock is not found
 * throw StockAlreadyExistsException if the stock already exists
 * 
 * create a field  api_key which takes the value from application.properties
 * autowire the RestTemplate, StockRepository
 */
 
import com.learn.stockapp.exceptions.StockAlreadyExistException;
import com.learn.stockapp.exceptions.StockNotFoundException;
import com.learn.stockapp.model.Stock;
import com.learn.stockapp.model.StockList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.learn.stockapp.repository.StockRepository;

@Service
public class StockListingServiceImpl implements StockListingService {
    @Value("${api.url}")
    private String api_url;

   @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockRepository stockRepository;
 
 
    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }
    
   

    @Override
    public StockList getStocksByCountryFromAPI(String country) {
        // use UriComponentsBuilder to build the URL
        String url = UriComponentsBuilder.fromHttpUrl(api_url)
                .queryParam("country", country)
                .toUriString();
        StockList stockList = restTemplate.getForObject(url, StockList.class);
        return stockList;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockBySymbol(String symbol) throws StockNotFoundException {
        Stock stock = stockRepository.findById(symbol).orElse(null);
        if (stock == null) {
            throw new StockNotFoundException("Stock with symbol " + symbol + " not found");
        }
        return stock;
    }

    @Override
    public List<Stock> getStocksByExchangeAndCountry(String exchange, String country) throws StockNotFoundException {
        List<Stock> stocks = stockRepository.findByExchangeAndCountry(exchange, country);
        if (stocks.isEmpty()) {
            throw new StockNotFoundException("Stocks with exchange " + exchange + " and country " + country + " not found");
        }
        return stocks;
    }

    @Override
    public Stock saveStock(Stock stock) throws StockAlreadyExistException {
        
            if (stockRepository.existsById(stock.getSymbol())) {
                throw new StockAlreadyExistException("Stock with symbol " + stock.getSymbol() + " already exists");
            }
           return stockRepository.save(stock);
        
    }

    @Override
    public boolean  deleteStock( String  symbol) throws StockNotFoundException {
      
            if (!stockRepository.existsById(symbol)) {
                throw new StockNotFoundException("Stock with symbol " + symbol + " not found");
            }
        stockRepository.deleteById(symbol);
        return true;
    }
}










