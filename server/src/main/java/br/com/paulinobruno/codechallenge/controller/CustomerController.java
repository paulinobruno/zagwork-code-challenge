package br.com.paulinobruno.codechallenge.controller;

import br.com.paulinobruno.codechallenge.domain.Customer;
import br.com.paulinobruno.codechallenge.exception.NotFoundException;
import br.com.paulinobruno.codechallenge.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.lang.String.format;
import static java.net.URI.create;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:4200")
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
    @ResponseStatus(value = ACCEPTED)
    public void updateCustomer(@PathVariable Integer id, @Valid @RequestBody Customer customer) {
        findCustomer(id);

        customer.setId(id);
        doSave(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer id) {
        Customer customer = findCustomer(id);
        repository.delete(customer);
    }

    private Customer findCustomer(Integer id) {
        return repository.findById(id).orElseThrow(new NotFoundException());
    }

    private Customer doSave(Customer customer) {
        customer.inferInterestRate();
        return repository.save(customer);
    }
}
