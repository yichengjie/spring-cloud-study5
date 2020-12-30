package com.yicj.study.zuul.config;

import com.yicj.study.zuul.filter.*;
import org.springframework.context.annotation.Bean;
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

    //@Bean
    public RewritePreFilter rewritePreFilter(){
        return new RewritePreFilter() ;
    }

    @Bean
    public ModifyRequestEntityFilter modifyRequestEntityFilter(){
        return new ModifyRequestEntityFilter() ;
    }
}
