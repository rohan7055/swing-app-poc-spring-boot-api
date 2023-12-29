package com.bezkoder.spring.jpa.h2.repository;

import com.bezkoder.spring.jpa.h2.model.Investment;
import com.bezkoder.spring.jpa.h2.model.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    // Additional custom query methods can be defined here if needed
}