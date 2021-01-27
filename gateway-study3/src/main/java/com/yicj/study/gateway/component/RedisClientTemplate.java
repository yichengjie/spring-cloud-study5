package com.yicj.study.gateway.component;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisClientTemplate {

    private RedisTemplate<String,Object> redisTemplate ;

    public void setObjex(String concat, int i, Object str) {
    }

    public Object getObj(String key){

        return null ;
    }

    public void del(String concat) {
    }
}
