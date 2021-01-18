package com.yicj.study.namedcontextfactory;

import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.stereotype.Component;

public class MyRibbonClientSpecification implements NamedContextFactory.Specification {
   private final String name;
   private final Class<?>[] configuration;

   public MyRibbonClientSpecification(String name, Class<?>[] configuration) {
       this.name = name;
       this.configuration = configuration;
   }
   @Override
   public String getName() {
       return this.name;
   }

   @Override
   public Class<?>[] getConfiguration() {
       return this.configuration;
   }
}