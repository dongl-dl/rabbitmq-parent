package com.dongl.consumer.service;

import com.dongl.common.entity.BaseMsg;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RabbitListener(queues = "sms_queue")
public class ConsumerService {

    @RabbitHandler
    public void process(BaseMsg msgEntity) {
        log.info("sms：msgEntity:" + msgEntity.getMsgId());
    }

}
