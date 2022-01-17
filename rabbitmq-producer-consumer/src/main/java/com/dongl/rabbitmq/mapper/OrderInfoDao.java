package com.dongl.rabbitmq.mapper;


import com.dongl.rabbitmq.entity.OrderInfo;

public interface OrderInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    OrderInfo getOrder(String orderId);
}