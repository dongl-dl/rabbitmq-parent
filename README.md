# rabbitmq-parent
spring boot集成rabbitmq

一、RabbitMq如何保证消息不丢失？
    MQ如何保证消息不丢失：持久化机制
    
    1、生产者角色：
    确保生产者投递消息到mq服务器成功。
      ACK消息确认机制
      同步或者异步（开启一个监听，异步回调） 
      方式1：Confirms
      方式2：事务消息
      
    2、消费者角色：
    rabbitmq:
      必须将消息消费成功后，再将消息从mq服务器中移除;
    kafka:
      在Kafka中有所不同，无论消费者消费成功或失败，服务器中的消息都不会在短时间内被移除; offset:偏移量 定时任务清除掉
      
    3、mq服务端
      在默认情况下，都会对队列中的消息实现持久化，持久化到硬盘
  
  
二、rabbitmq角色：

    1、Virtual Hosts :区分不同的团队
    
    2、交换机的种类：路由消息存放在那个队列中
    
    3、队列:存放消息
    
    4、路由key：分发规则
    
    
    直连交换机（Direct exchange）：
    
    扇形交换机（Fanout exchange）：
      1、需要创建两个队列，每个队列对应一个消费者；
      2、队列需要绑定交换机；
      3、生产者投递消息到交换机，交换机将消息分配给两个队列中存放起来；
      4、消费者从队列中获取这个消息；
    主题交换机（Topic exchange）
    头交换机（Headers exchange）