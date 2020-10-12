package com.sales_order.microservice_demo.model;

public class CustomerCreatedEvent {
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;

    @Override
    public String toString() {
        return "CustomerCreatedEvent{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailId() {
        return emailId;
    }
}
