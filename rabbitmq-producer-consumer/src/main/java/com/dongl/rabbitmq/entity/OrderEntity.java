package com.dongl.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderEntity implements Serializable {
    private int id;
    private String orderName;
    private String orderId;

    public OrderEntity(String orderName, String orderId) {
        this.orderName = orderName;
        this.orderId = orderId;
    }

    public OrderEntity() {

    }

}