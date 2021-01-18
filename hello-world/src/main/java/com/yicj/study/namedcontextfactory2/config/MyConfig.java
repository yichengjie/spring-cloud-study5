package com.yicj.study.namedcontextfactory2.config;

import com.yicj.study.namedcontextfactory2.component.HelloSpecification;
import com.yicj.study.namedcontextfactory2.component.MyHelloContextFactory;
import com.yicj.study.namedcontextfactory2.service.HelloBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConfig {


    @Bean
    public HelloBean helloBean(){
        HelloBean helloBean = new HelloBean();
        helloBean.setName("parent hello bean");
        return helloBean;
    }

    /**
     * 先定义一个ContextFactory对象,通过TestSpecification定义不同命名的
     * 子context需要加载的configuration，当命名是以default开头的，所有的
     *  子context共用该TestSpecification定义的configuration。
     */
    @Bean
    public MyHelloContextFactory getMyTestContextFactory(){
        MyHelloContextFactory context = new MyHelloContextFactory();
        HelloSpecification spcification = new HelloSpecification("test0", new Class[]{HelloContextAutoConfiguration.class/*, CommonContextAutoConfiguration.class*/});
        HelloSpecification spcification1 = new HelloSpecification("test1", new Class[]{Hello1ContextAutoConfiguration.class/*, CommonContextAutoConfiguration.class*/});
        HelloSpecification spcification2 = new HelloSpecification("default.common", new Class[]{CommonContextAutoConfiguration.class});
        List<HelloSpecification> spe = new ArrayList<>();
        spe.add(spcification);
        spe.add(spcification1);
        spe.add(spcification2);
        context.setConfigurations(spe);
        return context;
    }
}
