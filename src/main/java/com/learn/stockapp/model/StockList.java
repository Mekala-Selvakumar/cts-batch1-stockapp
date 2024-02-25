package com.learn.stockapp.model;

/*  create a field data  of  list of stocks
 * use lombok to generate the getters and setters, toString, no arg constructor 
 * and constructor with all fields
 */

 //purpose - retrieve the list of stocks from the API (twelvedata.com)
import java.util.List;

 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
 
public class StockList {
    private List<Stock> data;
}
 