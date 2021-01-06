package com.yicj.study.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.time.ZonedDateTime;

@Slf4j
public class PredicateTest {

    @Test
    public void test1(){
        ZonedDateTime now = ZonedDateTime.now();
        log.info("new : {}", now);
    }
}
