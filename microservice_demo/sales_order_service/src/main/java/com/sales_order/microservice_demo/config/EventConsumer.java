package com.sales_order.microservice_demo.config;

import com.sales_order.microservice_demo.dao.SalesRepository;
import com.sales_order.microservice_demo.model.Customer;
import com.sales_order.microservice_demo.model.CustomerCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Session;

import static com.sales_order.microservice_demo.config.ActiveMQConfig.CUSTOMER_QUEUE;

@Component
public class EventConsumer {
    private static Logger log = LoggerFactory.getLogger(EventConsumer.class);
    @Autowired
    private SalesRepository customerRepository;
    @JmsListener(destination = CUSTOMER_QUEUE)
    public void receiveMessage(@Payload CustomerCreatedEvent customerCreatedEvent,
                               @Headers MessageHeaders headers,
                               Message message, Session session) {
        log.info("received <" + customerCreatedEvent + ">");

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######          Message Details           #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: " + headers);
        log.info("message: " + message);
        log.info("session: " + session);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        Customer customer = new Customer();
        customer.setEmailId(customerCreatedEvent.getEmailId());
        customer.setFirstName(customerCreatedEvent.getFirstName());
        customer.setLastName(customerCreatedEvent.getLastName());
        customer.setId(customerCreatedEvent.getId());
        customerRepository.save(customer);
    }
}
