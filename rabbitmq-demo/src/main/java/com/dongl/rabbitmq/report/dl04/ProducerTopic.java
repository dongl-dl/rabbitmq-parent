package com.dongl.rabbitmq.report.dl04;

import com.dongl.rabbitmq.report.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ProducerTopic.java
 * @description: Topic主题模式 生产者
 * @createTime 2022年01月18日 19:44:00
 */
public class ProducerTopic {

    /**
     * 定义交换机的名称
     */
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //  创建Connection
        Connection connection = RabbitMQConnection.getConnection();
        // 创建Channel
        Channel channel = connection.createChannel();
        // 通道关联交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
        String msg = "6666";
        channel.basicPublish(EXCHANGE_NAME, "dongliang.sms", null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
