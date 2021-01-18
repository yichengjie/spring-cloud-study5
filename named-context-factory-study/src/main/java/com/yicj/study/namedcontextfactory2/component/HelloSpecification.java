package com.yicj.study.namedcontextfactory2.component;

import org.springframework.cloud.context.named.NamedContextFactory;

public class HelloSpecification implements NamedContextFactory.Specification {
    private String name;
    private Class<?>[] configuration;
    public HelloSpecification(String name, Class<?>[] configuration){
        this.name = name;
        this.configuration = configuration;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public Class<?>[] getConfiguration() {
        return configuration;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setConfiguration(Class<?>[] configuration) {
        this.configuration = configuration;
    }
}