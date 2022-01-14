package com.dongl.rabbitmq.manager;


import com.dongl.rabbitmq.entity.OrderEntity;
import com.dongl.rabbitmq.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class OrderManager {
    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public int addOrder(OrderEntity orderEntity) {
        return orderMapper.addOrder(orderEntity);
    }
}
