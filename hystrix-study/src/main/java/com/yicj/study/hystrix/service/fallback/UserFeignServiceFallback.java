package com.yicj.study.hystrix.service.fallback;

import com.yicj.study.hystrix.service.UserFeignService;
import org.springframework.stereotype.Component;

@Component
public class UserFeignServiceFallback implements UserFeignService {
    @Override
    public String getUser(String username) {
        return "The user does not exist in this system, please confirm username";
    }
}
