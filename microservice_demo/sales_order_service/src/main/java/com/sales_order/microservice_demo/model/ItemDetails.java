package com.sales_order.microservice_demo.model;

import javax.persistence.*;

@Entity
@Table(name = "Order_Line_Item")
public class ItemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "item_name", nullable = false)
    private String itemName;
    @Column(name = "item_quantity", nullable = false)
    private int itemQuantity;
    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
