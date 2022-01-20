package com.dongl.producerserver01.service;

import com.dongl.common.entity.BaseMsg;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ConsumerService.java
 * @description: 消费者
 * @createTime 2022年01月20日 11:19:00
 */
@Service
@Slf4j
@RabbitListener(queues = "dong.fanout")
public class ConsumerService {

    @RabbitHandler
    public void fanoutListener(BaseMsg msgEntity , Message message, Channel channel) throws IOException {
        log.info("ConsumerService --------------------sms：msgEntity:" + msgEntity);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
