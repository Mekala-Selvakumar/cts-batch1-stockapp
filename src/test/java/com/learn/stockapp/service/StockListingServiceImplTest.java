package com.learn.stockapp.service;

import com.learn.stockapp.exceptions.StockAlreadyExistException;
import com.learn.stockapp.exceptions.StockNotFoundException;

// FILEPATH: /d:/cts/cts-batch1/stockapp/src/test/java/com/learn/stockapp/service/StockListingServiceImplTest.java

import com.learn.stockapp.model.Stock;
import com.learn.stockapp.model.StockList;
import com.learn.stockapp.repository.StockRepository;
import com.learn.stockapp.service.StockListingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import com.learn.stockapp.exceptions.StockAlreadyExistException;
import com.learn.stockapp.exceptions.StockNotFoundException;

@ExtendWith(MockitoExtension.class)
public class StockListingServiceImplTest {

    @InjectMocks
    private StockListingServiceImpl stockListingService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private StockRepository stockRepository;

    Stock stock1 ;
    Stock stock2 ;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        stock1 = new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
        stock2 = new Stock("MSFT", "Microsoft Corporation", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
    }

    @Test
    public void testGetAllStocks() {
        when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1,stock2));
        assertEquals(2, stockListingService.getAllStocks().size());
    }

    @Test
    public void testGetStockBySymbol() throws StockNotFoundException {
        
        when(stockRepository.findById("AAPL")).thenReturn(Optional.of(stock1));
        assertEquals(stock1, stockListingService.getStockBySymbol("AAPL"));
    }

    @Test
    public void testGetStocksByExchangeAndCountry() throws StockNotFoundException {
        
        when(stockRepository.findByExchangeAndCountry("NSE", "India")).thenReturn(Arrays.asList(stock1));
        assertEquals(1, stockListingService.getStocksByExchangeAndCountry("NSE", "India").size());
    }

    // ...

    @Test
    public void testSaveStock() throws StockAlreadyExistException {
        when(stockRepository.existsById(anyString())).thenReturn(false);
        when(stockRepository.save(stock1)).thenReturn(stock1);
        Stock result = stockListingService.saveStock(stock1);
        assertEquals(stock1,result);
    }

    @Test
    public void testDeleteStock() throws StockNotFoundException {
        when(stockRepository.existsById("1")).thenReturn(true);
        assertTrue(stockListingService.deleteStock("1"));
    }

    @Test
    public void testGetStocksByCountryFromAPI() {
        List<Stock> stocks = Arrays.asList(stock1, stock2);
        StockList stockList = new StockList();
        stockList.setData(stocks);
        stockListingService.setApi_url("https://api.twelvedata.com/stocks");
         when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(stockList);

        List<Stock> returnedStocks =  stockListingService.getStocksByCountryFromAPI("USA").getData();
        assertEquals(2, returnedStocks.size());
        assertEquals("AAPL", returnedStocks.get(0).getSymbol());
        assertEquals("MSFT", returnedStocks.get(1).getSymbol());
        
          
     }
}
 



