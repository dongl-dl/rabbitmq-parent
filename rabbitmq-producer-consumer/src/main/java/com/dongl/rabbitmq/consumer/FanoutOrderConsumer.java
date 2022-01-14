package com.dongl.rabbitmq.consumer;


import com.dongl.rabbitmq.entity.OrderEntity;
import com.dongl.rabbitmq.manager.OrderManager;
import com.dongl.rabbitmq.mapper.OrderMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @ClassName fanout_sms_queue
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@Slf4j
@Component
@RabbitListener(queues = "fanout_order_queue")
public class FanoutOrderConsumer {

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private OrderMapper orderMapper;

    @RabbitHandler
    public void process(OrderEntity orderEntity, Message message, Channel channel) throws IOException {
//        try {
        log.info(">>orderEntity:{}<<", orderEntity.toString());
        String orderId = orderEntity.getOrderId();
        if (StringUtils.isEmpty(orderId)) {
            return;
        }
        OrderEntity dbOrderEntity = orderMapper.getOrder(orderId);
        if (dbOrderEntity != null) {
            log.info("另外消费者已经处理过该业务逻辑");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        int result = orderManager.addOrder(orderEntity);
//        int i = 1 / 0;
        log.info(">>插入数据库中数据成功<<");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (Exception e) {
//            // 记录该消息日志形式  存放数据库db中、后期通过定时任务实现消息补偿、人工实现补偿
//
//            //将该消息存放到死信队列中，单独写一个死信消费者实现消费。
//        }
    }
}
