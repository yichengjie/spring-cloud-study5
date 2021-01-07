package com.yicj.study.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class PredicateTest {

    @Test
    public void test1(){
        ZonedDateTime now = ZonedDateTime.now();
        log.info("new : {}", now);
    }

    @Test
    public void test2(){
        String format = LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        System.out.println(format);
    }
}
