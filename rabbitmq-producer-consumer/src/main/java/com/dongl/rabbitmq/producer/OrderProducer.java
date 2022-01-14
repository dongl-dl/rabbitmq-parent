package com.dongl.rabbitmq.producer;

import com.dongl.rabbitmq.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class OrderProducer implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData.getId();
        log.info("id:" + id);
    }

    /**
     * 使用mq发送消息
     *
     * @param orderName
     * @param orderId
     */
    public void sendMsg(String orderName, String orderId) {
        OrderEntity orderEntity = new OrderEntity(orderName, orderId);
        rabbitTemplate.convertAndSend("/mayikt_order", "", orderEntity, message -> {
            return message;
        });
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId(JSONObject.toJSONString(orderEntity));
//        rabbitTemplate.convertAndSend("/mayikt_order", "", orderEntity,correlationData);
    }

}
