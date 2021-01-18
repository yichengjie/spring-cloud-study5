package com.yicj.study.namedcontextfactory;

import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.stereotype.Component;

@Component
public class MyContextFactory extends NamedContextFactory<MyRibbonClientSpecification> {
   public MyContextFactory() {
       super(DefaultConfiguration.class, "myRibbon", "myRibbon.client.name");
   }
}