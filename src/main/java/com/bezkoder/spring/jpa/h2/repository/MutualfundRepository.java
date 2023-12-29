package com.bezkoder.spring.jpa.h2.repository;

import com.bezkoder.spring.jpa.h2.model.MutualFund;
import com.bezkoder.spring.jpa.h2.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MutualfundRepository extends JpaRepository<MutualFund, Long> {
    // Additional custom query methods can be defined here if needed
}