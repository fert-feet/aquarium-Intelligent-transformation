package com.aquarium;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @作者: tt—Tang
 * @描述: 测试类
 **/

@Slf4j
public class Test {
    public static void main(String[] args) {
        String format = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
        log.info("now---{}", format);
    }
}
