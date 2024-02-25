package com.learn.stockapp.model;

/* create the following   fields
 * {
    "symbol": "AAPL",
    "name": "Apple Inc",
    "currency": "USD",
    "exchange": "NASDAQ",
    "mic_code": "XNGS",
    "country": "United States",
    "type": "Common Stock"
  }
  Use Lombok to generate the getters and setters, toString 
  Use Loombok to generate the constructor with all fields , no argus constructor
 Use  @Document to map the class to the collection in MongoDB
  */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "stock")
public class Stock {
    @Id
    private String symbol;
    private String name;
    private String currency;
    private String exchange;
    private String mic_code;
    private String country;
    private String type;
}
