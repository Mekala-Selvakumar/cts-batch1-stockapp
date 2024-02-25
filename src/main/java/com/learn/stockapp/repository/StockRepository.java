package com.learn.stockapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learn.stockapp.model.Stock;

import java.util.List;

@Repository
public interface StockRepository  extends MongoRepository<Stock, String>{

    //get all  stocks  based on country
    public List<Stock> findByCountry(String country);

    //get all stocks based on exchange and country
    public List<Stock> findByExchangeAndCountry(String exchange, String country);   

}

