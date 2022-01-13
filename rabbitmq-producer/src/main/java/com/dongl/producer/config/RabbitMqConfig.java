package com.dongl.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName RabbitMqConfig.java
 * @description: TODO
 * @createTime 2022年01月12日 18:48:00
 */
@Component
public class RabbitMqConfig {

    /**
     * 定义交换机
     */
    private String FANOUT_EXCHANGE_NAME = "my_exchange";

    /**
     * 短信队列
     */
    private String FANOUT_SMS_QUEUE = "sms_queue";

    /**
     * 邮件队列
     */
    private String FANOUT_EMAIL_QUEUE = "email_queue";

    /**
     * key
     */
    private String SMS_KEY = "my_key";



    /**
     * 1、注入队列和交换机到spring容器中
     */
    @Bean
    public Queue smsQueue(){
        return new Queue(FANOUT_SMS_QUEUE);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(FANOUT_EMAIL_QUEUE);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    /**
     * 2、关联交换机
     */
    @Bean
    public Binding bindingSmsFanoutExchange(Queue smsQueue , FanoutExchange fanoutExchange){
        return BindingBuilder.bind(smsQueue).to(fanoutExchange);
    }
    @Bean
    public Binding bindingEmailFanoutExchange(Queue emailQueue , FanoutExchange fanoutExchange){
        return BindingBuilder.bind(emailQueue).to(fanoutExchange);
    }
}
