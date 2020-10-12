package com.sales_order.microservice_demo.dao;

import com.sales_order.microservice_demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Customer,Long> {
}
