package com.sales_order.microservice_demo.dao;


import com.sales_order.microservice_demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<Order,Long> {
}
