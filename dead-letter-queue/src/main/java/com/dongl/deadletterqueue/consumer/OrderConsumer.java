package com.dongl.deadletterqueue.consumer;//package com.mayikt.consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * 订单消费者
// */
//@Component
//@Slf4j
//public class OrderConsumer {
//
//    /**
//     * 监听队列回调的方法
//     *
//     * @param msg
//     */
//    @RabbitListener(queues = "mayikt_order_queue")
//    public void orderConsumer(String msg) {
//        log.info(">>正常订单消费者消息MSG:{}<<", msg);
//    }
//}