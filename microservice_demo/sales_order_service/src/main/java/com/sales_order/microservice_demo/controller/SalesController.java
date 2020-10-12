package com.sales_order.microservice_demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sales_order.microservice_demo.config.GetItemNameProxy;
import com.sales_order.microservice_demo.dao.SalesOrderRepository;
import com.sales_order.microservice_demo.dao.SalesRepository;
import com.sales_order.microservice_demo.model.*;
import jdk.internal.org.objectweb.asm.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api3/v1")
public class SalesController {

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GetItemNameProxy getItemNameProxy;

    @PostMapping("/orders")
    @HystrixCommand(fallbackMethod = "errorMethod")
    public ResponseEntity<Object> saveCustomer(@RequestBody OrderDetails orderDetails) {

        Optional<Customer> opCus = salesRepository.findById(orderDetails.getCustomerId());
        Customer customer = null;
        Map<String,Integer> map = new HashMap<>();
        if(opCus.isPresent())
            customer = opCus.get();

        else
            return new ResponseEntity<>("Invalid Customer Id",HttpStatus.BAD_REQUEST);
        Order order = new Order();
        order.setCustId(customer.getId());
        order.setOrderDate(orderDetails.getOrderDate());
        order.setOrderDescription(orderDetails.getOrderDescription());
        double price = 0;
        ItemDetails itemDetails = null;
        List<ItemDetails> itemDetailsList = new ArrayList<>();
        for(String item : orderDetails.getItems()) {
            Item items = getItemNameProxy.getAllItems(item);
            if(map.get(items.getName()) == null){
                map.put(items.getName(),1);
            }else{
                map.put(items.getName(),map.get(items.getName()) +1 );
            }
            if(null!=items){
                price = price + items.getPrice();
            }else{
                return new ResponseEntity<>("Invalid Item Names",HttpStatus.BAD_REQUEST);
            }
        }
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            itemDetails = new ItemDetails();
            itemDetails.setItemName(entry.getKey());
            itemDetails.setItemQuantity(entry.getValue());
            itemDetails.setOrder(order);
            itemDetailsList.add(itemDetails);
        }

        order.setTotalPrice(price);
        order.setItemDetails(itemDetailsList);
        salesOrderRepository.save(order);
        return new ResponseEntity<>(order.getOrderId(),HttpStatus.CREATED);
    }

    private String errorMethod() {
        return "Request fails. Something is wrong";
    }
}
