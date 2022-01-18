package com.dongl.rabbitmq.report.dl04;

import com.dongl.rabbitmq.report.RabbitMQConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName MailConsumer.java
 * @description: Topic主题模式 邮件消费者
 * @createTime 2022年01月18日 19:47:00
 */
public class MailConsumer {

    /**
     * 定义邮件队列
     */
    private static final String QUEUE_NAME = "topic_email_queue";
    /**
     * 定义交换机的名称
     */
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("邮件消费者...");
        // 创建我们的连接
        Connection connection = RabbitMQConnection.getConnection();
        // 创建我们通道
        final Channel channel = connection.createChannel();
        // 关联队列消费者关联队列
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "dongliang.*");
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("邮件消费者获取消息:" + msg);
            }
        };
        // 开始监听消息 自动签收
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
