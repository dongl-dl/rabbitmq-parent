package com.dongl.rabbitmq.service.demo02;

import com.dongl.rabbitmq.connection.RabbitMQConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    private static final String QUEUE_NAME = "mayikt-queue";

    public static void main(String[] args) throws IOException, TimeoutException, IOException, TimeoutException {
        // 1.创建连接
        Connection connection = RabbitMQConnection.getConnection();
        // 2.设置通道
        Channel channel = connection.createChannel();
        //指定我们消费者每次批量获取消息
        channel.basicQos(2);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取消息:" + msg);
                try {
// 消费者完成 删除该消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }catch (Exception e){

                }
//
            }
        };
        // 3.监听队列
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);

    }
}