package com.dongl.rabbitmq.connection;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName RabbitMQConnection.java
 * @description: TODO
 * @createTime 2022年01月13日 18:09:00
 */
public class RabbitMQConnection {

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //1、设置连接virtualHost
        connectionFactory.setVirtualHost("/donglVirtualHost");
        //2、设置账户密码
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //3、mq连接信息地址
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);

        return connectionFactory.newConnection();
    }
}
