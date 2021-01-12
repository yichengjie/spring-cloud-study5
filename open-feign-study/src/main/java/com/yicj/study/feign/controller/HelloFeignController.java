package com.yicj.study.feign.controller;

import com.yicj.study.feign.service.HelloFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloFeignController {
    @Autowired
    private HelloFeignService helloFeignService ;

    @GetMapping("/search/github")
    public String searchGithubRepoByStr(@RequestParam("str") String queryStr){
        return helloFeignService.searchRepo(queryStr);
    }
}
