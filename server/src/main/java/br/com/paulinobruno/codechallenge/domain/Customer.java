package br.com.paulinobruno.codechallenge.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @NotNull
    private BigDecimal creditLimit;

    @NotNull
    private RiskCategory risk;

    private BigDecimal interestRate;

    public void inferInterestRate() {
        interestRate = risk.getRate();
    }

}
