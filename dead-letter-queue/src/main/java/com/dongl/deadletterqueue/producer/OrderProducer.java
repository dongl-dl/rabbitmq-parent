package com.dongl.deadletterqueue.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 订单交换机
     */
    @Value("${dongl.order.exchange}")
    private String orderExchange;
    /**
     * 订单路由key
     */
    @Value("${dongl.order.routingKey}")
    private String orderRoutingKey;

    @RequestMapping("/sendOrder")
    public String sendOrder() {
        String msg = "董亮测试死信队列消息";
        rabbitTemplate.convertAndSend(orderExchange, orderRoutingKey, msg, message -> {
            // 设置消息过期时间 10秒过期
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
        return "success";
    }
}
