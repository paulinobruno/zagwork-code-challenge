package br.com.paulinobruno.codechallenge.repository;

import br.com.paulinobruno.codechallenge.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
