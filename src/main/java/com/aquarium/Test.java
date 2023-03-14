package com.aquarium;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.RoundingMode;
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
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        double one = 32.99606820864092;
        log.info("one---{}", NumberUtil.round(one, 1, RoundingMode.FLOOR));
        log.info("date---{}", DateUtil.parse(format, "yyyy-MM-dd HH:mm:ss"));
    }
}
