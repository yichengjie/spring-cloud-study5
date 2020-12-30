package com.yicj.study.zuul.config;

import com.yicj.study.zuul.filter.FirstPreFilter;
import com.yicj.study.zuul.filter.HelloFilter;
import com.yicj.study.zuul.filter.PostFilter;
import com.yicj.study.zuul.filter.SecondPreFilter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    //@Bean
    public HelloFilter helloFilter(){
        return new HelloFilter() ;
    }

    //@Bean
    public FirstPreFilter firstPreFilter(){
        return new FirstPreFilter() ;
    }

    //@Bean
    public SecondPreFilter secondPreFilter(){
        return new SecondPreFilter() ;
    }

    //@Bean
    public PostFilter postFilter(){
        return new PostFilter() ;
    }
}
