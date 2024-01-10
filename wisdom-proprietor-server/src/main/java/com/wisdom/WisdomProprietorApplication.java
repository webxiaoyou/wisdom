package com.wisdom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class WisdomProprietorApplication {
    public static void main(String[] args) {
        SpringApplication.run(WisdomProprietorApplication.class, args);
    }
}
