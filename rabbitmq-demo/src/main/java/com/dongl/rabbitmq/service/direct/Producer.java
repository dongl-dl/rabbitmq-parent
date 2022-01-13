package com.dongl.rabbitmq.service.direct;

import com.dongl.rabbitmq.connection.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName Producer.java
 * @description: TODO
 * @createTime 2022年01月13日 18:21:00
 */
public class Producer {

    private static final String QUEUE_NAME = "dongl-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建连接
        Connection connection = RabbitMQConnection.getConnection();
        //2、创建通道
        Channel channel = connection.createChannel();
        channel.basicPublish("" , QUEUE_NAME , null , "钱钱钱钱钱".getBytes());
        //3、关闭连接
        channel.close();
        connection.close();
    }
}
