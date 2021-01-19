package com.yicj.study.namedcontextfactory2;

import com.yicj.study.namedcontextfactory2.anno.AvoidScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@SpringBootApplication
@ComponentScan(excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes =AvoidScan.class)
})
public class HelloApplication {

    //https://www.jianshu.com/p/6ca1e73816bb
    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args) ;
    }
}
