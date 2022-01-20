package com.dongl.producerserver01.service;


import com.dongl.common.entity.BaseMsg;
import com.dongl.common.rabbitmqenum.ExchangeEnum;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ProducerService.java
 * @description: TODO
 * @createTime 2022年01月12日 19:35:00
 */
@Service
public class ProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsg(){

        //消息对象
        BaseMsg baseMsg = new BaseMsg(UUID.randomUUID().toString(),"1000001", "15566545532", "dl@163.com");

        //发送消息对象
        amqpTemplate.convertAndSend(ExchangeEnum.FANOUT.getExchangeName(), "" , baseMsg);
    }
}
