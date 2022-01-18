package com.dongl.rabbitmq.report.dl03;

import com.dongl.rabbitmq.connection.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ProducerDirect.java
 * @description: Direct路由模式 生产者
 * @createTime 2022年01月18日 19:28:00
 */
public class ProducerDirect {

    /**
     * 定义交换机的名称
     */
    private static final String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //  创建Connection
        Connection connection = RabbitMQConnection.getConnection();
        // 创建Channel
        Channel channel = connection.createChannel();
        // 通道关联交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        String msg = "董亮测试消息~~~~~";
        channel.basicPublish(EXCHANGE_NAME, "email", null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
