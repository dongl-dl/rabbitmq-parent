package com.dongl.producer.controller;

import com.dongl.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ProducerController.java
 * @description: TODO
 * @createTime 2022年01月13日 14:54:00
 */
@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/producer")
    public String producer(){
        System.out.println("ok");
        producerService.sendMsg();
        return "ok";
    }
}
