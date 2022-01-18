package com.dongl.rabbitmq.report;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName RabbitMQConnection.java
 * @description: 快速入门RabbitMQ简单队列 .创建连接
 * @createTime 2022年01月18日 14:43:00
 */
public class RabbitMQConnection {
    public static Connection getConnection() throws IOException, TimeoutException {
        // 1.创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2.设置连接地址
        connectionFactory.setHost("127.0.0.1");
        // 3.设置端口号:
        connectionFactory.setPort(5672);
        // 4.设置账号和密码
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        // 5.设置VirtualHost
        connectionFactory.setVirtualHost("/dongliang07");
        return connectionFactory.newConnection();
    }
}
