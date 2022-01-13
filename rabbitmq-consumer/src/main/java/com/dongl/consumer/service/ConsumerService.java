package com.dongl.consumer.service;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ConsumerService.java
 * @description: TODO
 * @createTime 2022年01月13日 15:45:00
 */
@Service
public class ConsumerService {
    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "email_queue",durable = "true"),
            exchange = @Exchange(name="my_exchange",durable = "true",type = "fanout"),
            key = "*"
    )
    )
    @RabbitHandler
    public void process(String content) {
        System.out.println("处理器one接收处理队列A当中的消息： " + content);
    }

}
