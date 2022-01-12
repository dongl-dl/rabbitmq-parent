package com.dongl.producer.service;

import com.dongl.producer.entity.BaseMsg;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ProducerService.java
 * @description: TODO
 * @createTime 2022年01月12日 19:35:00
 */
@RestController
public class ProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsg(){

        //消息对象
        BaseMsg baseMsg = new BaseMsg(UUID.randomUUID().toString(),"1000001", "15566545532", "dl@163.com");

        //发送消息对象
        amqpTemplate.convertAndSend("my_exchange" , "" , baseMsg);
    }
}
