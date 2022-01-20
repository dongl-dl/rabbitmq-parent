package com.dongl.common.rabbitmqenum;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName QueueEnum.java
 * @description: 队列枚举类
 * @createTime 2022年01月19日 19:31:00
 */
@Getter
public enum QueueEnum {

    /**
     * 广播队列
     */
    fanout_dong("dong.fanout" , "fanout.*" , true , false , false , QueueArguments.public_argument , false , null , ExchangeEnum.FANOUT);

    /**
     *队列名称（队列名称必须唯一）
     */
    private String name;

    /**
     *队列路由键（注意匹配  例如："dong" 将会匹配到指定路由下的路由键为[*.dong]的队列）
     */
    private String routingKey;

    /**
     *是否为持久队列
     */
    private boolean durable;

    /**
     *是否为排他队列（该队列仅由声明者的队列使用连接）
     */
    private boolean exclusive;

    /**
     *如果队列为空时是否删除（如果服务器不再使用队列时 是否删除队列）
     */
    private boolean autoDelete;

    /**
     *队列参数
     */
    private Map<String , Object> arguments;

    /**
     *是否需要全部匹配（用于头队列使用）
     */
    private boolean whereAll;

    /**
     *匹配消息头（用于头队列使用）
     */
    private Map<String , Object> headers;

    /**
     *交换机
     */
    private ExchangeEnum exchangeEnum;

    QueueEnum(String name, String routingKey, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments, boolean whereAll, Map<String, Object> headers, ExchangeEnum exchangeEnum) {
        this.name = name;
        this.routingKey = routingKey;
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
        this.arguments = arguments;
        this.whereAll = whereAll;
        this.headers = headers;
        this.exchangeEnum = exchangeEnum;
    }

    /**
     * 将枚举值转化成list集合
     *
     * @return
     */
    public static List<QueueEnum> toList(){
        List<QueueEnum> list = new ArrayList<>();
        for (QueueEnum queueEnum: QueueEnum.values()) {
            list.add(queueEnum);
        }
        return list;
    }

    // 这个方法在QueueEnum
    public static Map<String, String> getQueuesNames() {
        return Arrays.asList(QueueEnum.values()).stream().collect(Collectors.toMap(queueEnum -> queueEnum.toString(), queueEnum -> queueEnum.getName()));
    }
}
