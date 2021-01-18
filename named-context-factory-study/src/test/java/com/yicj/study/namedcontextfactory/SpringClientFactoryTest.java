package com.yicj.study.namedcontextfactory;

import com.netflix.loadbalancer.ServerList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class},
        value={"stock-service.myRibbon.listOfServers=127.0.0.1:111", "stock-service.ribbon.listOfServers=127.0.0.1:456"})
public class SpringClientFactoryTest {
    @Autowired
    private MyContextFactory myContextFactory;

    @Test
    public void testMyContextFactory() {
        // 1. 自定义ContextFactory
        ServerList serverList1 = myContextFactory.getInstance("stock-service", ServerList.class);
        System.out.println(serverList1.getInitialListOfServers()); // 127.0.0.1:111
    }
}