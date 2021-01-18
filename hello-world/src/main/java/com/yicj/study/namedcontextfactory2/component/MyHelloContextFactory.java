package com.yicj.study.namedcontextfactory2.component;

import com.yicj.study.namedcontextfactory2.config.HelloContextAutoConfiguration;
import com.yicj.study.namedcontextfactory2.service.impl.MyContextBean;
import com.yicj.study.namedcontextfactory2.service.impl.MyShowContextBean;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyHelloContextFactory extends NamedContextFactory<HelloSpecification> {

    public MyHelloContextFactory() {
//      自定义自动配置类
        super(HelloContextAutoConfiguration.class, "testcontext", "testcontext.name");
    }
    public MyContextBean getMyContextBean(String name) {
        //自定义接口类型
        return getInstance(name, MyContextBean.class);
    }
    public MyShowContextBean getMyShowContextBean(String name) {
        //自定义接口类型
        return getInstance(name, MyShowContextBean.class);
    }
    @Override
    protected AnnotationConfigApplicationContext getContext(String name) {
        return super.getContext(name);
    }

    public void setProperties() {

    }
}