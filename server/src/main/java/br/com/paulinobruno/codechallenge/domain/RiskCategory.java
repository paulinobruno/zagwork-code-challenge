package br.com.paulinobruno.codechallenge.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Getter
@RequiredArgsConstructor
public enum RiskCategory {

    A(ZERO), B(BigDecimal.valueOf(0.1)), C(BigDecimal.valueOf(0.2));

    private final BigDecimal rate;

}
