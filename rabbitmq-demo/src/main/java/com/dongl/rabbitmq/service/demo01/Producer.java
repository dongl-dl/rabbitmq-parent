package com.dongl.rabbitmq.service.demo01;

import com.dongl.rabbitmq.report.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static final String QUEUE_NAME = "mayikt-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个新连接
        Connection connection = RabbitMQConnection.getConnection();
        //2.设置channel
        Channel channel = connection.createChannel();
        //3.发送消息
        for (int i = 0; i < 100; i++) {
            String msg = "发送测试消息6666:i" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
        System.out.println("消息投递成功");
        channel.close();
        connection.close();
    }
}
