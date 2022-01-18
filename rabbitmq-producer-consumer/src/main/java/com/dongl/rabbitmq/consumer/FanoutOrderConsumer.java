package com.dongl.rabbitmq.consumer;


import com.dongl.rabbitmq.entity.OrderInfo;
import com.dongl.rabbitmq.manager.OrderManager;
import com.dongl.rabbitmq.mapper.OrderInfoDao;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RabbitListener(queues = "fanout_order_queue")
public class FanoutOrderConsumer {

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private OrderInfoDao orderMapper;

    @RabbitHandler
    public void process(OrderInfo orderInfo, Message message, Channel channel) throws IOException {
//        try {
        log.info(">>orderInfo:{}<<", orderInfo.toString());
        String orderId = orderInfo.getOrderid();
        if (StringUtils.isEmpty(orderId)) {
            return;
        }
        OrderInfo dbOrderInfo = orderMapper.getOrder(orderId);
//        OrderInfo dbOrderInfo =  null;
        if (dbOrderInfo != null) {
            log.info("另外消费者已经处理过该业务逻辑");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        int result = orderManager.addOrder(orderInfo);
//        int i = 1 / 0;
        log.info(">>插入数据库中数据成功<<");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (Exception e) {
////            // 记录该消息日志形式  存放数据库db中、后期通过定时任务实现消息补偿、人工实现补偿
//            System.out.println("记录该消息日志形式  存放数据库db中、后期通过定时任务实现消息补偿、人工实现补偿");
////            //将该消息存放到死信队列中，单独写一个死信消费者实现消费。
//        }
    }
}
