package com.yicj.study.clienta.controller;

import com.yicj.study.clienta.model.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@RestController
public class FormController {

    @PostMapping("/submitForm")
    public UserInfoVo submitForm(UserInfoVo userInfoVo, HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            log.info("====> {}: {}", headerName, request.getHeader(headerName));
        }
        userInfoVo.setUsername(userInfoVo.getUsername() +" : OK");
        return userInfoVo ;
    }
}
