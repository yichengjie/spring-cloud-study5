package com.yicj.study.mvc.controller;

import com.yicj.study.mvc.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/feignGet")
public class FeignGetInterceptorController {

    @RequestMapping(
        value = "/addUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    private User addUser(User user) {
        user.setUsername(user.getUsername() + "1");
        user.setPassword("password " + 1);
        return user;
    }


    @RequestMapping(
        value = "/updateUser",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    User updateUser(@RequestBody User user) {
        user.setUsername(user.getUsername() + "1");
        user.setPassword(user.getPassword() + "1");
        return user;
    }

}
