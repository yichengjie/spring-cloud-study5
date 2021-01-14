package com.yicj.study.mvc.controller;

import com.yicj.study.mvc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@RestController
@RequestMapping("/feignGet")
public class FeignGetInterceptorController {

    @RequestMapping(
        value = "/addUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public User addUser(User user, HttpServletRequest request) {
        this.printHeaders(request);
        user.setUsername(user.getUsername() + "1");
        user.setPassword("password " + 1);
        return user;
    }


    private void printHeaders(HttpServletRequest request){
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            log.info("====> {} : {}",name, request.getHeader(name));
        }
    }


    @RequestMapping(
        value = "/updateUser",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public User updateUser(@RequestBody User user, HttpServletRequest request) {
        this.printHeaders(request);
        user.setUsername(user.getUsername() + "1");
        user.setPassword(user.getPassword() + "1");
        return user;
    }
}
