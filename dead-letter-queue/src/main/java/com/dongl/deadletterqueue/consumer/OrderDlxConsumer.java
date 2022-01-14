//package com.dongl.deadletterqueue.consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class OrderDlxConsumer {
//
//    /**
//     * 死信队列监听队列回调的方法
//     *
//     * @param msg
//     */
//    @RabbitListener(queues = "mayikt_order_dlx_queue")
//    public void orderConsumer(String msg) {
//        log.info(">死信队列消费订单消息:msg{}<<", msg);
//    }
//}