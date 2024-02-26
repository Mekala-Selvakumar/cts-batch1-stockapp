package com.learn.stockapp.controller;

import org.junit.jupiter.api.Test;

// FILEPATH: /d:/cts/cts-batch1/stockapp/src/test/java/com/learn/stockapp/controller/StockListingControllerTest.java

import com.learn.stockapp.exceptions.StockAlreadyExistException;
import com.learn.stockapp.exceptions.StockNotFoundException;
import com.learn.stockapp.model.Stock;
import com.learn.stockapp.model.StockList;
import com.learn.stockapp.service.StockListingService;
import org.junit.jupiter.api.BeforeEach;
 import org.mockito.InjectMocks;
 import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
 import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
 
 import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

 


/* use MovkMvc  for   testing ,   autowire  StockListingSerive  */


@WebMvcTest(StockListingController.class)
public class StockListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockListingService stockListService;


    @InjectMocks
    private StockListingController stockListController;

    private Stock stock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stock = new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
    }

   

      @Test
    public void testGetStocksByCountry() throws Exception {
        when(stockListService.getStocksByCountryFromAPI(any(String.class))).thenReturn(new StockList(Arrays.asList(stock)));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stocks/api/USA"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].symbol").value(stock.getSymbol()));

                   
             
    }


    @Test
    public void testSaveStock() throws Exception {
        when(stockListService.saveStock(any(Stock.class))).thenReturn(stock);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"symbol\":\"AAPL\",\"companyName\":\"Apple Inc\",\"currency\":\"USD\",\"exchange\":\"NASDAQ\",\"exchangeShortName\":\"XNGS\",\"country\":\"United States\",\"type\":\"Common Stock\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value("AAPL"));
    // .content(new ObjectMapper().writeValueAsString(stock)))
	// 			.andExpect(status().isCreated()).andDo(print());

   
            }

    @Test
    public void testGetAllStocks() throws Exception {
        when(stockListService.getAllStocks()).thenReturn(Arrays.asList(stock));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stocks"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].symbol").value("AAPL"));
    }

    @Test
    public void testDeleteStock() throws Exception {
        when(stockListService.deleteStock("AAPL")).thenReturn(true);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stocks/delete/AAPL"))
                .andExpect(status().isOk());
    }

     
}

  



   





// public class StockListingControllerTest {

//     @InjectMocks
//     private StockListingController stockListingController;

//     @Mock
//     private StockListingService stockListingService;
//     Stock  stock1;
//     Stock  stock2;


//     @BeforeEach
//     public void setup() {
//         MockitoAnnotations.openMocks(this);
//         stock1 = new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
//         stock2= new Stock("MSFT", "Microsoft Corporation", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");

//     }

//     @Test
//     public void testGetAllStocks() {
//          when(stockListingService.getAllStocks()).thenReturn(Arrays.asList(stock1,stock2));
//         ResponseEntity<List<Stock>> response = stockListingController.getAllStocks();
//         assertEquals(1, response.getBody().size());
//     }

//     @Test
//     public void testGetStocksByExchangeAndCountry() throws StockNotFoundException {
//          when(stockListingService.getStocksByExchangeAndCountry("NSE", "India")).thenReturn(Arrays.asList(stock1));
//         ResponseEntity<List<Stock>> response = stockListingController.getStocksByExchangeAndCountry("NSE", "India");
//         assertEquals(1, response.getBody().size());
//     }

//     @Test
//     public void testSaveStock() throws StockAlreadyExistException {
//          when(stockListingService.saveStock(stock1)).thenReturn(stock1);
//         ResponseEntity<Stock> response = stockListingController.saveStock(stock1);
//         assertEquals(stock1, response.getBody());
//     }

//     @Test
//     public void testDeleteStock() throws StockNotFoundException {
//         when(stockListingService.deleteStock("1")).thenReturn(true);
//         ResponseEntity<?> response = stockListingController.deleteStock("1");
//         assertEquals("Deleted Successfully", response.getBody());
//     }

//     @Test
//     public void testGetStocksByCountryFromAPI() {
//         StockList stockList = new StockList();
//         when(stockListingService.getStocksByCountryFromAPI("India")).thenReturn(stockList);
//         ResponseEntity<StockList> response = stockListingController.getStocksByCountryFromAPI("India");
//         assertEquals(stockList, response.getBody());
//     }

//     @Test
//     public void testGetStockBySymbol() throws StockNotFoundException {
//          when(stockListingService.getStockBySymbol("1")).thenReturn(stock1);
//         ResponseEntity<Stock> response = stockListingController.getStockBySymbol("1");
//         assertEquals(stock1, response.getBody());
//     }
// }