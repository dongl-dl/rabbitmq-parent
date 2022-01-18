package com.dongl.rabbitmq.report.dl01;

import com.dongl.rabbitmq.report.RabbitMQConnection;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName Consumer.java
 * @description: 消费者
 * @createTime 2022年01月18日 15:05:00
 */
public class Consumer {
    private static final String QUEUE_NAME = "dltest";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接
        Connection connection = RabbitMQConnection.getConnection();
        // 2.设置通道
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取消息:" + msg);
            }
        };
        // 3.监听队列
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
