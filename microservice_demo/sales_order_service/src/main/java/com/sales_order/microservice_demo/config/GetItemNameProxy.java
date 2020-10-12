package com.sales_order.microservice_demo.config;

import com.sales_order.microservice_demo.model.Item;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="item-service")
@RibbonClient(name="item-service")
public interface GetItemNameProxy {

    @GetMapping("/items/{itemName}")
    public Item getAllItems(@PathVariable String itemName);
}
