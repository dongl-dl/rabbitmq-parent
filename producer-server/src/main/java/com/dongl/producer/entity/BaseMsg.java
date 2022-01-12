package com.dongl.producer.entity;

import lombok.Data;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName BaseMsg.java
 * @description: TODO
 * @createTime 2022年01月12日 19:33:00
 */
@Data
public class BaseMsg {

    private String msgId;

    private String userId;

    private String phone;

    private String email;

    public BaseMsg(String msgId, String userId, String phone, String email) {
        this.msgId = msgId;
        this.userId = userId;
        this.phone = phone;
        this.email = email;
    }
}
