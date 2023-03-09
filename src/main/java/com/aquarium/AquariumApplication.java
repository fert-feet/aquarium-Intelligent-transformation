package com.aquarium;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @作者: tt—Tang
 * @描述: 主类
 **/
@SpringBootApplication
@MapperScan("com.aquarium.mapper")
@EnableTransactionManagement
public class AquariumApplication {
    public static void main(String[] args) {
        SpringApplication.run(AquariumApplication.class, args);
    }
}
