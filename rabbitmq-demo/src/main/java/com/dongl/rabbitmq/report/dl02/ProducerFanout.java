package com.dongl.rabbitmq.report.dl02;

import com.dongl.rabbitmq.report.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ProducerFanout.java
 * @description: Fanout 发布订阅  生产者
 * @createTime 2022年01月18日 18:24:00
 */
public class ProducerFanout {
    /**
     * 定义交换机的名称
     */
    private static final String EXCHANGE_NAME = "dongl_fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //  创建Connection
        Connection connection = RabbitMQConnection.getConnection();
        // 创建Channel
        Channel channel = connection.createChannel();
        // 通道关联交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
        String msg = "Fanout 消息~~~~~~~~~~~~~~";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        channel.close();
        connection.close();
    }

}
