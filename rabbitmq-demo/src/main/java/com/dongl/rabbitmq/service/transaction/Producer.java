package com.dongl.rabbitmq.service.transaction;

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
        try {
            //开启事务
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, "钱钱钱钱钱".getBytes());
//            int i = 1/0;
            //事务提交
            channel.txCommit();
            //3、关闭连接
            channel.close();
            connection.close();
        }catch (Exception e){
            if(null != channel){
                //事务回滚
                System.out.println("事务回滚~~~~~~~~~~~~~~~~~~");
                channel.txRollback();
            }
            e.printStackTrace();
        }
    }
}
