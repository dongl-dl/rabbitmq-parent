package com.dongl.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dongliang7
 * @projectName rabbitmq-parent
 * @ClassName ProducerServerApplication.java
 * @description: 生产者启动类
 * @createTime 2022年01月12日 18:43:00
 */
@SpringBootApplication
public class ProducerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerServerApplication.class, args);
    }

}
