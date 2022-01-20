package com.dongl.producerserver01.config;

import com.dongl.common.rabbitmqenum.ExchangeEnum;
import com.dongl.common.rabbitmqenum.QueueEnum;
import com.dongl.producerserver01.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName RabbitMqConfig.java
 * @description: 动态创建 RabbitMq 交换机、队列、绑定关系 bean 实例
 * @createTime 2022年01月20日 09:43:00
 */
@Component
@Slf4j
public class RabbitMqConfig {

    @Bean("queuesNames")
    public Map<String, String> queuesNames() {
        return QueueEnum.getQueuesNames();
    }

    /**
     * 动态创建交换机
     */
    @Bean("createExchange")
    public Object createExchange() {
        // 遍历交换机枚举
        ExchangeEnum.toList().forEach(exchangeEnum -> {
                    // 声明交换机
                    Exchange exchange;
                    // 根据交换机模式 生成不同的交换机
                    switch (exchangeEnum.getType()) {
                        case FANOUT:
                            exchange = ExchangeBuilder.fanoutExchange(exchangeEnum.getExchangeName()).durable(exchangeEnum.isDurable()).build();
                            break;
                        case DIRECT:
                            exchange = ExchangeBuilder.directExchange(exchangeEnum.getExchangeName()).durable(exchangeEnum.isDurable()).build();
                            break;
                        case HEADERS:
                            exchange = ExchangeBuilder.headersExchange(exchangeEnum.getExchangeName()).durable(exchangeEnum.isDurable()).build();
                            break;
                        case TOPIC:
                        default:
                            exchange = ExchangeBuilder.topicExchange(exchangeEnum.getExchangeName()).durable(exchangeEnum.isDurable()).build();
                            break;
                    }
                    // 将交换机注册到spring bean工厂 让spring实现交换机的管理
                    if (exchange != null) {
                        SpringContextUtil.registerBean(exchangeEnum.toString() + "_exchange", exchange);
                    }
                }
        );
        // 不返回任何对象 该方法只用于在spring初始化时 动态的将bean对象注册到spring bean工厂
        return null;
    }

    /**
     * 动态创建队列
     */
    @Bean("createQueue")
    public Object createQueue() {
        // 遍历队列枚举 将队列注册到spring bean工厂 让spring实现队列的管理
        QueueEnum.toList().forEach(queueEnum -> SpringContextUtil.registerBean(queueEnum.toString() + "_queue", new Queue(queueEnum.getName(), queueEnum.isDurable(), queueEnum.isExclusive(), queueEnum.isAutoDelete(), queueEnum.getArguments())));
        // 不返回任何对象 该方法只用于在spring初始化时 动态的将bean对象注册到spring bean工厂
        return null;
    }

    /**
     * 动态将交换机及队列绑定
     */
    @Bean("createBinding")
    public Object createBinding() {
        // 遍历队列枚举 将队列绑定到指定交换机
        QueueEnum.toList().forEach(queueEnum -> {
                    // 从spring bean工厂中获取队列对象（刚才注册的）
                    Queue queue = SpringContextUtil.getBean(queueEnum.toString() + "_queue", Queue.class);
                    // 声明绑定关系
                    Binding binding;
                    // 根据不同的交换机模式 获取不同的交换机对象（注意：刚才注册时使用的是父类Exchange，这里获取的时候将类型获取成相应的子类）生成不同的绑定规则
                    switch (queueEnum.getExchangeEnum().getType()) {
                        case FANOUT:
                            FanoutExchange fanoutExchange = SpringContextUtil.getBean(queueEnum.getExchangeEnum().toString() + "_exchange", FanoutExchange.class);
                            binding = BindingBuilder.bind(queue).to(fanoutExchange);
                            break;
                        case TOPIC:
                            TopicExchange topicExchange = SpringContextUtil.getBean(queueEnum.getExchangeEnum().toString() + "_exchange", TopicExchange.class);
                            binding = BindingBuilder.bind(queue).to(topicExchange).with(queueEnum.getRoutingKey());
                            break;
                        case HEADERS:
                            HeadersExchange headersExchange = SpringContextUtil.getBean(queueEnum.getExchangeEnum().toString() + "_exchange", HeadersExchange.class);
                            if (queueEnum.isWhereAll()) {
                                // whereAll表示全部匹配
                                binding = BindingBuilder.bind(queue).to(headersExchange).whereAll(queueEnum.getHeaders()).match();
                            } else {
                                // whereAny表示部分匹配
                                binding = BindingBuilder.bind(queue).to(headersExchange).whereAny(queueEnum.getHeaders()).match();
                            }
                            break;
                        case DIRECT:
                        default:
                            DirectExchange directExchange = SpringContextUtil.getBean(queueEnum.getExchangeEnum().toString() + "_exchange", DirectExchange.class);
                            binding = BindingBuilder.bind(queue).to(directExchange).with(queueEnum.getRoutingKey());
                            break;
                    }
                    // 将绑定关系注册到spring bean工厂 让spring实现绑定关系的管理
                    if (binding != null) {
                        SpringContextUtil.registerBean(queueEnum.toString() + "_binding", binding);
                    }
                }
        );
        // 不返回任何对象 该方法只用于在spring初始化时 动态的将bean对象注册到spring bean工厂
        return null;
    }
}
