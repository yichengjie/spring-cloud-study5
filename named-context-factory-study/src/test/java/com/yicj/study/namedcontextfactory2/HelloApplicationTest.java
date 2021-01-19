package com.yicj.study.namedcontextfactory2;


import com.yicj.study.namedcontextfactory2.component.MyHelloContextFactory;
import com.yicj.study.namedcontextfactory2.service.impl.MyContextBean;
import com.yicj.study.namedcontextfactory2.service.impl.MyShowContextBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloApplication.class)
public class HelloApplicationTest {
    @Autowired
    private MyHelloContextFactory contextFactory;
    @Autowired
    private ApplicationContext context ;

    /**
     *  当传参是定义TestSpecification时的命名，生成子context时就是用对应的configuration
     *和公用的configuration，当没有对应的TestSpecification时，使用默认的configuration
     */
    @Test
    public void test() {
        MyContextBean myContextBean = contextFactory.getMyContextBean("test0");
        System.out.println("===> contextBean:  " + myContextBean);
        MyShowContextBean showContextBean = contextFactory.getMyShowContextBean("test0");
        System.out.println("===> showContextBean : " + showContextBean);
        System.out.println("==============================================");
        MyContextBean myContextBean1 = contextFactory.getMyContextBean("test1");
        System.out.println("===> myContextBean1 : " + myContextBean1);
        MyShowContextBean showContextBean1 = contextFactory.getMyShowContextBean("test1");
        System.out.println("===> showContextBean1 : " + showContextBean1);
        System.out.println("==============================================");
        //该bean为空，因为公共的configuration没有定义这个bean
        MyContextBean myContextBean2 = contextFactory.getMyContextBean("noname");
        System.out.println("===> myContextBean2: " + myContextBean2);
        MyShowContextBean showContextBean2 = contextFactory.getMyShowContextBean("noname");
        System.out.println("===> showContextBean2: " + showContextBean2);
        System.out.println("==============================================");
        //MyContextBean bean = context.getBean(MyContextBean.class);
        //System.out.println("===> " + bean);
    }

}
