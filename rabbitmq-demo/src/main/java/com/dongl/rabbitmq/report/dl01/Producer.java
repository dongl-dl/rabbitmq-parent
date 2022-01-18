package com.dongl.rabbitmq.report.dl01;

import com.dongl.rabbitmq.report.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName Producer.java
 * @description: 生产者
 * @createTime 2022年01月18日 14:45:00
 */
public class Producer {
    private static final String QUEUE_NAME = "dltest";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接
        Connection connection = RabbitMQConnection.getConnection();
        // 2.设置通道
        Channel channel = connection.createChannel();
        // 3.设置消息
        String msg = "董亮消息体";
        System.out.println("msg:" + msg);
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
