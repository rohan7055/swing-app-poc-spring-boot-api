package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.model.MutualFund;
import com.bezkoder.spring.jpa.h2.repository.MutualfundRepository;
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
public class MutualFundController {

  @Autowired
  MutualfundRepository mutRepository;

  @GetMapping("/mutualfunds")
  public ResponseEntity<List<MutualFund>> getAllmutualfunds(@RequestParam(required = false) String title) {
    try {
      List<MutualFund> mutualfunds = new ArrayList<MutualFund>();

      if (title == null)
        mutRepository.findAll().forEach(mutualfunds::add);

      if (mutualfunds.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(mutualfunds, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/mutualfunds/{id}")
  public ResponseEntity<MutualFund> getMutualFundById(@PathVariable("id") long id) {
    Optional<MutualFund> MutualFundData = mutRepository.findById(id);

    if (MutualFundData.isPresent()) {
      return new ResponseEntity<>(MutualFundData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/mutualfunds")
  public ResponseEntity<MutualFund> createMutualFund(@RequestBody MutualFund MutualFund) {
    try {
      MutualFund _MutualFund = mutRepository.save(MutualFund);
      return new ResponseEntity<>(_MutualFund, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/mutualfunds/{id}")
  public ResponseEntity<MutualFund> updateMutualFund(@PathVariable("id") long id, @RequestBody MutualFund MutualFund) throws MutualFund.QuantityRangeError, MutualFund.PriceRangeError {
    Optional<MutualFund> MutualFundData = mutRepository.findById(id);

    if (MutualFundData.isPresent()) {
      MutualFund _MutualFund = MutualFundData.get();
      _MutualFund.setName(MutualFund.getName());
      _MutualFund.setSymbol(MutualFund.getSymbol());
      _MutualFund.setBookValue(MutualFund.getBookValue());
      _MutualFund.setQuantity(MutualFund.getQuantity());
      _MutualFund.setSymbol(MutualFund.getSymbol());
      _MutualFund.setPrice(MutualFund.getPrice());
      return new ResponseEntity<>(mutRepository.save(_MutualFund), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/mutualfunds/{id}")
  public ResponseEntity<HttpStatus> deleteMutualFund(@PathVariable("id") long id) {
    try {
      mutRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/mutualfunds")
  public ResponseEntity<HttpStatus> deleteAllmutualfunds() {
    try {
      mutRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
