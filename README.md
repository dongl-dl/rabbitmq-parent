# rabbitmq-parent
spring boot集成rabbitmq



一、RabbitMq如何保证消息不丢失？
MQ如何保证消息不丢失：持久化机制
1、生产者角色：
确保生产者投递消息到mq服务器成功。
  ACK消息确认机制
  同步或者异步（开启一个监听，异步回调）
  
2、消费者角色：
rabbitmq:
  必须将消息消费成功后，再将消息从mq服务器中移除;
kafka:
  在Kafka中有所不同，无论消费者消费成功或失败，服务器中的消息都不会在短时间内被移除; offset:偏移量 定时任务清除掉