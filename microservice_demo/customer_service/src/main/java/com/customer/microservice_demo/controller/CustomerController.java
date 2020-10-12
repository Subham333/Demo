package com.customer.microservice_demo.controller;

import com.customer.microservice_demo.dao.CustomerRepository;
import com.customer.microservice_demo.model.Customer;
import com.customer.microservice_demo.model.CustomerCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import java.util.List;

import static com.customer.microservice_demo.config.ActiveMQConfig.CUSTOMER_QUEUE;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @PostMapping("/customer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        Customer customer1 =  customerRepository.save(customer);
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent(customer1.getId(),
                customer1.getFirstName(),customer1.getLastName(),customer1.getEmailId());
        jmsTemplate.convertAndSend(queue, customerCreatedEvent);
        return new ResponseEntity(customer1, HttpStatus.CREATED);
    }

    @GetMapping("/customers")
    public List< Customer > getAllEmployees() {
        return customerRepository.findAll();
    }
}
