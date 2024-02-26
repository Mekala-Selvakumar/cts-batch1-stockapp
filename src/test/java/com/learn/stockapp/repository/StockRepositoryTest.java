package com.learn.stockapp.repository;
 import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
 /* create  DataJPaTest for testing
  * As part of Setup, delete all records from the table
    create two stocks and save them to the table
  */
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.learn.stockapp.model.Stock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



 

@DataMongoTest
public class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    private Stock  stock1;

    private Stock  stock2;

    @BeforeEach
    public void setUp() {
       // stockRepository.deleteAll();
        stock1 = new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
        stock2 = new Stock("MSFT", "Microsoft Corporation", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
    }
    @AfterEach
    public void tearDown() {
       // stockRepository.deleteAll();
    }

    @Test
    public void testFindByCountry() {
        stockRepository.deleteAll();  
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        List<Stock> stocks = stockRepository.findByCountry("United States");
        assertEquals(2, stocks.size());
    }

    @Test
    public void testFindByExchangeAndCountry() {
        stockRepository.deleteAll();
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        List<Stock> stocks = stockRepository.findByExchangeAndCountry("NASDAQ", "United States");
        assertEquals(2, stocks.size());
    }

    @Test
public void testFindByCountry_NoResult() {
    stockRepository.deleteAll();
    
    stockRepository.save(stock1);
    stockRepository.save(stock2);
    List<Stock> stocks = stockRepository.findByCountry("india");
    assertTrue(stocks.isEmpty());
}

@Test
public void testFindByExchangeAndCountry_NoResult() {
    stockRepository.deleteAll();
     stockRepository.save(stock1);
    stockRepository.save(stock2);
    List<Stock> stocks = stockRepository.findByExchangeAndCountry("NYSE", "USA");
    assertTrue(stocks.isEmpty());
}
//for  save stock
@Test
public void testSaveStock() {
    stockRepository.deleteAll();
    stockRepository.save(stock1);
    Stock stock = stockRepository.findById("AAPL").get();
    assertEquals("AAPL", stock.getSymbol());
    assertEquals("Apple Inc", stock.getName());
}

//for  delete stock
@Test
public void testDeleteStock() {
    stockRepository.deleteAll();
    stockRepository.save(stock1);
    stockRepository.deleteById("AAPL");
    assertTrue(stockRepository.findAll().isEmpty());
}

 
}



 


 


