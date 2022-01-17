package com.dongl.rabbitmq.manager;


import com.dongl.rabbitmq.entity.OrderInfo;
import com.dongl.rabbitmq.mapper.OrderInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class OrderManager {
    @Autowired
    private OrderInfoDao orderMapper;

    @Transactional
    public int addOrder(OrderInfo orderInfo) {
        return orderMapper.insertSelective(orderInfo);
    }
}
