package com.dongl.rabbitmq.service.direct;

import com.dongl.rabbitmq.connection.RabbitMQConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName Consumer.java
 * @description: TODO
 * @createTime 2022年01月13日 18:34:00
 */
public class Consumer {

    private static final String QUEUE_NAME = "dongl-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建连接
        Connection connection = RabbitMQConnection.getConnection();
        //2、创建通道
        Channel channel = connection.createChannel();
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body , "UTF-8");
                System.out.println(msg);
            }
        };
        //3、监听队列  设置为true：标识自动签收（ack）
        //在实际开发时  需要改成false 手动签收
        channel.basicConsume(QUEUE_NAME , true , defaultConsumer);
    }

}
