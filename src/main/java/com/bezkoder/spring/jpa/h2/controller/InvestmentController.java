package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.model.Investment;
import com.bezkoder.spring.jpa.h2.repository.InvestmentRepository;
import com.bezkoder.spring.jpa.h2.repository.InvestmentRepository;
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
public class InvestmentController {

  @Autowired
  InvestmentRepository stockRepository;

  @GetMapping("/investments")
  public ResponseEntity<List<Investment>> getAllinvestments(@RequestParam(required = false) String title) {
    try {
      List<Investment> investments = new ArrayList<Investment>();

      if (title == null)
        stockRepository.findAll().forEach(investments::add);

      if (investments.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(investments, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/investments/{id}")
  public ResponseEntity<Investment> getInvestmentById(@PathVariable("id") long id) {
    Optional<Investment> InvestmentData = stockRepository.findById(id);

    if (InvestmentData.isPresent()) {
      return new ResponseEntity<>(InvestmentData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/investments")
  public ResponseEntity<Investment> createInvestment(@RequestBody Investment Investment) {
    try {
      Investment _Investment = stockRepository.save(Investment);
      return new ResponseEntity<>(_Investment, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/investments/{id}")
  public ResponseEntity<Investment> updateInvestment(@PathVariable("id") long id, @RequestBody Investment Investment) throws Investment.QuantityRangeError, Investment.PriceRangeError {
    Optional<Investment> InvestmentData = stockRepository.findById(id);

    if (InvestmentData.isPresent()) {
      Investment _Investment = InvestmentData.get();
      _Investment.setName(Investment.getName());
      _Investment.setSymbol(Investment.getSymbol());
      _Investment.setBookValue(Investment.getBookValue());
      _Investment.setQuantity(Investment.getQuantity());
      _Investment.setSymbol(Investment.getSymbol());
      _Investment.setPrice(Investment.getPrice());
      return new ResponseEntity<>(stockRepository.save(_Investment), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/investments/{id}")
  public ResponseEntity<HttpStatus> deleteInvestment(@PathVariable("id") long id) {
    try {
      stockRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/investments")
  public ResponseEntity<HttpStatus> deleteAllinvestments() {
    try {
      stockRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
