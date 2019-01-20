package br.com.paulinobruno.codechallenge.controller;

import br.com.paulinobruno.codechallenge.domain.Customer;
import br.com.paulinobruno.codechallenge.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.lang.String.format;
import static java.net.URI.create;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity createCustomer(@Valid @RequestBody Customer customer) {
        customer = doSave(customer);

        URI uri = create(format("/customers/%d", customer.getId()));
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable Integer id, @Valid @RequestBody Customer customer) {
        customer.setId(id);
        doSave(customer);
    }

    private Customer doSave(Customer customer) {
        customer.inferInterestRate();
        return repository.save(customer);
    }
}
