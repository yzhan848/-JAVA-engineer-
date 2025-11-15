package com.cub.coindesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CubCoindeskApplication {
    public static void main(String[] args) {
        SpringApplication.run(CubCoindeskApplication.class, args);
    }
}
