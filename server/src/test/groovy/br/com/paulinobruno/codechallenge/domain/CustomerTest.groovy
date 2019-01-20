package br.com.paulinobruno.codechallenge.domain

import spock.lang.Specification
import spock.lang.Unroll

import static br.com.paulinobruno.codechallenge.domain.RiskCategory.A
import static br.com.paulinobruno.codechallenge.domain.RiskCategory.B
import static br.com.paulinobruno.codechallenge.domain.RiskCategory.C
import static java.math.BigDecimal.ZERO
import static java.math.BigDecimal.valueOf

class CustomerTest extends Specification {

    @Unroll
    void 'given customer with risk #RISK then interestRate should be #INTEREST'() {
        given:
            Customer customer = new Customer(risk: RISK)

        when:
            customer.inferInterestRate()

        then:
            customer.interestRate == INTEREST

        where:
            RISK || INTEREST
            A    || ZERO
            B    || valueOf(0.1)
            C    || valueOf(0.2)
    }

}
