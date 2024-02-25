package com.learn.stockapp.controller;

import com.learn.stockapp.exceptions.StockAlreadyExistException;
import com.learn.stockapp.exceptions.StockNotFoundException;
import com.learn.stockapp.model.Stock;
import com.learn.stockapp.model.StockList;
import com.learn.stockapp.service.StockListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockListingController {

    @Autowired
    private StockListingService stockListingService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockListingService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/exchange/{exchange}/country/{country}")
    public ResponseEntity<List<Stock>> getStocksByExchangeAndCountry(@PathVariable String exchange, @PathVariable String country) throws StockNotFoundException {
        List<Stock> stocks = stockListingService.getStocksByExchangeAndCountry(exchange, country);
        return ResponseEntity.ok(stocks);
    }

    @PostMapping
    public ResponseEntity<Stock> saveStock(@RequestBody Stock stock) throws StockAlreadyExistException {
        Stock savedStock = stockListingService.saveStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStock);
    }

    @DeleteMapping("/delete/{symbol}")
    public ResponseEntity<?> deleteStock(@PathVariable String symbol) throws StockNotFoundException {
        boolean isDeleted = stockListingService.deleteStock(symbol);
        // ResponseEntity entity = new ResponseEntity<>("Deleted Succssfully", HttpStatus.OK);
        // return  entity;
        return ResponseEntity.ok("Deleted Successfully");
        
    }

    @GetMapping("/api/{country}")
    public ResponseEntity<StockList> getStocksByCountryFromAPI(@PathVariable String country) {
        StockList stockList = stockListingService.getStocksByCountryFromAPI(country);
        return ResponseEntity.ok(stockList);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStockBySymbol(@PathVariable String symbol) throws StockNotFoundException {
        Stock stock = stockListingService.getStockBySymbol(symbol);
        return ResponseEntity.ok(stock);
    }
}
