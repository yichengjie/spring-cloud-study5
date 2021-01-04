package com.yicj.study.webflux.mapper;

import com.yicj.study.webflux.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper ;

    @Test
    public void findById(){
        Integer id = 1 ;
        User user = userMapper.findById(id);
        log.info("user : {}", user);
    }
}
