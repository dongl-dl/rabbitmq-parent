package com.dongl.rabbitmq.service;


import com.dongl.rabbitmq.entity.OrderInfo;
import com.dongl.rabbitmq.mapper.OrderInfoDao;
import com.dongl.rabbitmq.producer.OrderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderService {



    @Autowired
    private OrderInfoDao orderMapper;
    @Autowired
    private OrderProducer orderProducer;

    @RequestMapping("/sendOrder")
    public String sendOrder() {
        // 生成全局id
        String orderId = System.currentTimeMillis() + "";
        log.info("orderId:{}", orderId);
        String orderName = "消息体·············";
        orderProducer.sendMsg(orderId , orderName);
        return orderId;
    }


    /**
     * 前端主动根据orderId定时查询
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/getOrder")
    public Object getOrder(String orderId) {
        OrderInfo order = orderMapper.getOrder(orderId);
        if (order == null) {
            return "该订单没有被消费或者订单号错误!";
        }
        return order;
    }


}