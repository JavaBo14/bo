package com.bo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bo.mapper")
public class BoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoApplication.class, args);
    }

}
