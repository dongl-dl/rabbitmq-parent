package com.dongl.rabbitmq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {
    /**
     * 定义交换机
     */
    private String EXCHANGE_SPRINGBOOT_NAME = "/dongl_order";


    /**
     * 订单队列
     */
    private String FANOUT_ORDER_QUEUE = "fanout_order_queue";


    /**
     * 配置orderQueue
     *
     * @return
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(FANOUT_ORDER_QUEUE);
    }


    /**
     * 配置fanoutExchange
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_SPRINGBOOT_NAME);
    }

    // 绑定交换机 orderQueue
    @Bean
    public Binding bindingOrderFanoutExchange(Queue orderQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(orderQueue).to(fanoutExchange);
    }

}
