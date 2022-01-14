package com.dongl.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dongl.rabbitmq.mapper")
public class RabbitmqProducerConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerConsumerApplication.class, args);
    }

}
