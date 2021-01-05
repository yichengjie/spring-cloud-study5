package com.yicj.study.webflux.service.impl;

import com.yicj.study.webflux.entity.User;
import com.yicj.study.webflux.mapper.UserMapper;
import com.yicj.study.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper ;

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
