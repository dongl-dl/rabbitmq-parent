package com.dongl.rabbitmq.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order_info
 * @author 
 */
@Data
public class OrderInfo implements Serializable {
    /**
     * 业务Id
     */
    private String id;

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 订单名
     */
    private String ordername;

    /**
     * 创建时间
     */
    private Date createTime;

    public OrderInfo(String orderid, String ordername) {
        this.orderid = orderid;
        this.ordername = ordername;
    }

    private static final long serialVersionUID = 1L;
}