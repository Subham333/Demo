package com.item.microservice_demo.controller;

import com.item.microservice_demo.dao.ItemRepository;
import com.item.microservice_demo.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import java.util.List;

@RestController
@RequestMapping("/api2/v1")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/item")
    public ResponseEntity<Item> saveCustomer(@RequestBody Item item) {
        Item savedItem =  itemRepository.save(item);
        return new ResponseEntity(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/items/{itemName}")
    public Item getAllItems(@PathVariable String itemName) {
        return itemRepository.findByName(itemName);
    }
}
