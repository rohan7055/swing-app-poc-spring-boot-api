package com.bezkoder.spring.jpa.h2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mutualfund")
public class MutualFund extends Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public static final double REDEMPTION = 45;
    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;
    public MutualFund(String symbol, String name, double price) throws EmptySymbolError,
            EmptyNameError,
            PriceRangeError {
        super(symbol, name, price);
    }

    public double getGain() {
        return (this.quantity * this.price - MutualFund.REDEMPTION) - this.bookValue;
    }

    public double getPaymentReceived(int amount) {
        return amount * this.price - MutualFund.REDEMPTION;
    }

    // Methods like buyUnits, sellUnits, getGain, getPaymentReceived, print...

    // Override methods like equals and toString...
}
