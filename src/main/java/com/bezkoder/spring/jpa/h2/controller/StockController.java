package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.model.Stock;
import com.bezkoder.spring.jpa.h2.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class StockController {

  @Autowired
  StockRepository stockRepository;

  @GetMapping("/stocks")
  public ResponseEntity<List<Stock>> getAllstocks(@RequestParam(required = false) String title) {
    try {
      List<Stock> stocks = new ArrayList<Stock>();

      if (title == null)
        stockRepository.findAll().forEach(stocks::add);

      if (stocks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(stocks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/stocks/{id}")
  public ResponseEntity<Stock> getStockById(@PathVariable("id") long id) {
    Optional<Stock> StockData = stockRepository.findById(id);

    if (StockData.isPresent()) {
      return new ResponseEntity<>(StockData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/stocks")
  public ResponseEntity<Stock> createStock(@RequestBody Stock Stock) {
    try {
      Stock _Stock = stockRepository.save(Stock);
      return new ResponseEntity<>(_Stock, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/stocks/{id}")
  public ResponseEntity<Stock> updateStock(@PathVariable("id") long id, @RequestBody Stock Stock) {
    Optional<Stock> StockData = stockRepository.findById(id);

    if (StockData.isPresent()) {
      Stock _Stock = StockData.get();
      _Stock.setName(Stock.getName());
      _Stock.setSymbol(Stock.getSymbol());
      _Stock.setBookValue(Stock.getBookValue());
      _Stock.setQuantity(Stock.getQuantity());
      _Stock.setSymbol(Stock.getSymbol());
      _Stock.setPrice(Stock.getPrice());
      return new ResponseEntity<>(stockRepository.save(_Stock), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/stocks/{id}")
  public ResponseEntity<HttpStatus> deleteStock(@PathVariable("id") long id) {
    try {
      stockRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/stocks")
  public ResponseEntity<HttpStatus> deleteAllstocks() {
    try {
      stockRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
