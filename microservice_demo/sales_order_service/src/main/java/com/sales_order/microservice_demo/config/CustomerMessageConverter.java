package com.sales_order.microservice_demo.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales_order.microservice_demo.model.CustomerCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class CustomerMessageConverter implements MessageConverter {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CustomerMessageConverter.class);

    ObjectMapper mapper;

    public CustomerMessageConverter() {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    }

    @Override
    public Message toMessage(Object object, Session session)
            throws JMSException {
        CustomerCreatedEvent customerCreatedEvent = (CustomerCreatedEvent) object;
        String payload = null;
        try {
            payload = mapper.writeValueAsString(customerCreatedEvent);
            LOGGER.info("outbound json='{}'", payload);
        } catch (JsonProcessingException e) {
            LOGGER.error("error converting form person", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(payload);

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        LOGGER.info("inbound json='{}'", payload);

        CustomerCreatedEvent customer = null;
        try {
            customer = mapper.readValue(payload, CustomerCreatedEvent.class);
        } catch (Exception e) {
            LOGGER.error("error converting to person", e);
        }

        return customer;
    }
}
