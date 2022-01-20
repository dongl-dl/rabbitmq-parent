package com.dongl.producerserver01.service;

import com.dongl.common.entity.BaseMsg;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ConsumerService.java
 * @description: 消费者
 * @createTime 2022年01月20日 11:19:00
 */
@Component
@Slf4j
public class ConsumerService1 {

    /**
     * #{queuesNames.队列枚举名称}
     * @param msgEntity
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = {"#{queuesNames.fanout_dong}"})
    public void fanoutListener1(BaseMsg msgEntity , Message message, Channel channel) throws IOException {
        log.info("ConsumerService1 ----------------------sms：msgEntity:" + msgEntity);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = {"#{queuesNames.fanout_dong1}"})
    public void fanout_dong1(BaseMsg msgEntity , Message message, Channel channel) throws IOException {
        log.info("ConsumerService1 ----------------------sms：msgEntity:" + msgEntity);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    /**
     * 直连交换机消费者
     * @param msgEntity
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = {"#{queuesNames.direct_dong}"})
    public void direct_dong(BaseMsg msgEntity , Message message, Channel channel) throws IOException {
        log.info("直连交换机对应队列消息：msgEntity:" + msgEntity);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 通配符交换机消费者
     * @param msgEntity
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = {"#{queuesNames.topic_dong}"})
    public void topic_dong(BaseMsg msgEntity , Message message, Channel channel) throws IOException {
        log.info("通配符交换机对应队列消息：msgEntity:" + msgEntity);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
