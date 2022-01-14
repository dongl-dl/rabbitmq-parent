package com.dongl.rabbitmq.service.demo00;

import com.dongl.rabbitmq.connection.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @ClassName Producer 演示消息确认机制
 * @Version V1.0
 **/
public class Producer {
    private static final String QUEUE_NAME = "dongl-queue";

    public static void main(String[] args) {
        try {
            //1.创建一个新连接
            Connection connection = RabbitMQConnection.getConnection();
            //2.设置channel
            Channel channel = connection.createChannel();
            //3.发送消息
            String msg = "6666";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            boolean result = channel.waitForConfirms();
            if (result) {
                System.out.println("消息投递成功");
            } else {
                System.out.println("消息投递失败");
            }
            channel.close();
            connection.close();
        } catch (Exception e) {

        }
    }
}
