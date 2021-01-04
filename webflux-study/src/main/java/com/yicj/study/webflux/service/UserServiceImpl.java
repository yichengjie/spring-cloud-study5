package com.yicj.study.webflux.service;

import com.yicj.study.webflux.entity.User;
import com.yicj.study.webflux.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper ;

    @Override
    public User findById(Integer id) {

        return null;
    }
}
