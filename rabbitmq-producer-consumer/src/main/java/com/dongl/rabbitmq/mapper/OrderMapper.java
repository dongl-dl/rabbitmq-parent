package com.dongl.rabbitmq.mapper;


import com.dongl.rabbitmq.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {
    @Insert("insert order_info values (null,#{orderName},#{orderId})")
    int addOrder(OrderEntity orderEntity);

    @Select("SELECT * from order_info where orderId=#{orderId} ")
    OrderEntity getOrder(String orderId);
}