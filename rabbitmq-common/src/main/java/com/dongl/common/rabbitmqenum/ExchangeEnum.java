package com.dongl.common.rabbitmqenum;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ExchangeEnum.java
 * @description: TODO
 * @createTime 2022年01月19日 19:16:00
 */
@Getter
public enum ExchangeEnum {
    /**
     * 直连交换机（全文匹配）
     */
    DIRECT("dong_exchange_direct" , ExchangeTypeEnum.DIRECT , true),
    /**
     * 通配符交换机（两种通配符：*只能匹配一个单词，#可以匹配零个或多个）
     */
    TOPIC("dong_exchange_topic" , ExchangeTypeEnum.TOPIC , true),
    /**
     * 头交换机（自定义键值对匹配，根据发送消息内容中的headers属性进行匹配）
     */
    HEADERS("dong_exchange_headers" , ExchangeTypeEnum.HEADERS , true),
    /**
     * 扇形（广播）交换机 （将消息转发到所有与该交互机绑定的队列上）
     */
    FANOUT("dong_exchange_fanout" , ExchangeTypeEnum.FANOUT , true);

    /**
     * 交换机名称
     */
    private String exchangeName;

    /**
     * 交换机类型
     */
    private ExchangeTypeEnum type;

    /**
     * 是否持久化
     */
    private boolean durable;

    /**
     * 构造函数
     */
    ExchangeEnum(String exchangeName, ExchangeTypeEnum type, boolean durable) {
        this.exchangeName = exchangeName;
        this.type = type;
        this.durable = durable;
    }

    /**
     * 将枚举值转化成list集合
     *
     * @return
     */
    public static List<ExchangeEnum> toList(){
        List<ExchangeEnum> list = new ArrayList<>();
        for (ExchangeEnum channelEnum: ExchangeEnum.values()) {
            list.add(channelEnum);
        }
        return list;
    }
}
