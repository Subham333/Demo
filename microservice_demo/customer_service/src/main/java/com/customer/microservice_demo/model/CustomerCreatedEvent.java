package com.customer.microservice_demo.model;

import java.io.Serializable;

public class CustomerCreatedEvent implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;

    public CustomerCreatedEvent(long id, String firstName, String lastName, String emailId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }
}
