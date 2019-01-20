package br.com.paulinobruno.codechallenge.controller

import br.com.paulinobruno.codechallenge.AbstractTests
import br.com.paulinobruno.codechallenge.domain.Customer
import br.com.paulinobruno.codechallenge.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.ResultActions

import static br.com.paulinobruno.codechallenge.domain.RiskCategory.A
import static br.com.paulinobruno.codechallenge.domain.RiskCategory.B
import static java.math.BigDecimal.ZERO
import static java.math.BigDecimal.valueOf
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

class CustomerControllerTest extends AbstractTests {

    @Autowired
    CustomerRepository repository
    @Autowired
    ObjectMapper mapper

    private Customer sample

    void setup() {
        mvc = webAppContextSetup(context).build()

        sample = new Customer(
            name: 'BRUNO PAULINO',
            creditLimit: valueOf(50980.0),
            risk: A,
            interestRate: ZERO
        )

        repository.save(sample)
    }

    void cleanup() {
        repository.deleteAll()
    }

    void 'GET /customers should always be OK'() {
        when:
            ResultActions result = mvc.perform(
                get('/customers')
                    .contentType(APPLICATION_JSON)
            )

        then:
            result.andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(1)))
                .andExpect(jsonPath('$[:1].name').value(sample.name))
    }

    void 'DELETE /:xpto should be NOT_FOUND'() {
        when:
            ResultActions result = mvc.perform(
                delete('/customers/0')
                    .contentType(APPLICATION_JSON)
            )

        then:
            result.andExpect(status().isNotFound())
    }

    void 'DELETE /:existing should be NO_CONTENT'() {
        when:
            ResultActions result = mvc.perform(
                delete("/customers/${sample.id}")
                    .contentType(APPLICATION_JSON)
            )

        then:
            result.andExpect(status().isNoContent())

        and:
            Optional<Customer> persisted = repository.findById(sample.id)

        then:
            !persisted.isPresent()
    }

    void 'POST / with empty body should be BAD_REQUEST'() {
        when:
            ResultActions result = mvc.perform(
                post('/customers')
                    .content('{}')
                    .contentType(APPLICATION_JSON)
            )

        then:
            result.andExpect(status().isBadRequest())
    }

    void 'POST / with new object should be CREATED'() {
        given:
            Customer newCustomer = aCustomer()

        when:
            ResultActions result = mvc.perform(
                post('/customers')
                    .content(mapper.writeValueAsString(newCustomer))
                    .contentType(APPLICATION_JSON)
            )

        then:
            result.andExpect(status().isCreated())
                .andExpect(header().exists('Location'))
            String locationHeader = result.andReturn().response.getHeader('Location')
            String generatedId = locationHeader.replaceAll('^/customers/(.+)$', '$1')
            generatedId

        and:
            Optional<Customer> optCustomer = repository.findById(generatedId as Integer)

        then:
            optCustomer.isPresent()

            Customer theCustomer = optCustomer.get()
            theCustomer.name == 'Bruno Almeida'
            theCustomer.interestRate == valueOf(0.1)
    }

    void 'PUT /:xpto should be NOT_FOUND'() {
        when:
            ResultActions result = mvc.perform(
                put('/customers/0')
                    .content(mapper.writeValueAsString(aCustomer()))
                    .contentType(APPLICATION_JSON)
            )

        then:
            result.andExpect(status().isNotFound())
    }

    void 'PUT /:existing with empty body should be BAD_REQUEST'() {
        when:
            ResultActions result = mvc.perform(
                put("/customers/${sample.id}")
                    .content('{}')
                    .contentType(APPLICATION_JSON)
            )

        then:
            result.andExpect(status().isBadRequest())
    }

    void 'PUT /:existing should be ACCEPTED'() {
        when:
            ResultActions result = mvc.perform(
                put("/customers/${sample.id}")
                    .content(mapper.writeValueAsString(aCustomer()))
                    .contentType(APPLICATION_JSON)
            )

        then:
            result.andExpect(status().isAccepted())

        and:
            Optional<Customer> optCustomer = repository.findById(sample.id)

        then:
            optCustomer.isPresent()

            Customer theCustomer = optCustomer.get()
            theCustomer.name == 'Bruno Almeida'
            theCustomer.interestRate == valueOf(0.1)
    }

    private Customer aCustomer() {
        new Customer(
            name: 'Bruno Almeida',
            creditLimit: valueOf(9800),
            risk: B
        )
    }

}
