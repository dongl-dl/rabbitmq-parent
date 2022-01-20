package com.dongl.common.rabbitmqenum;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ExchangeTypeEnum.java
 * @description: 交换机类型枚举类
 * @createTime 2022年01月19日 19:19:00
 */
public enum ExchangeTypeEnum {

    /**
     * 直连交换机（全文匹配）
     */
    DIRECT,
    /**
     * 通配符交换机（两种通配符：*只能匹配一个单词，#可以匹配零个或多个）
     */
    TOPIC,
    /**
     * 头交换机（自定义键值对匹配，根据发送消息内容中的headers属性进行匹配）
     */
    HEADERS,
    /**
     * 扇形（广播）交换机 （将消息转发到所有与该交互机绑定的队列上）
     */
    FANOUT;
}
