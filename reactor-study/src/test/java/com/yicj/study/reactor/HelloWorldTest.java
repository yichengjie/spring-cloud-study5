package com.yicj.study.reactor;

import com.yicj.study.reactor.model.Apple;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class HelloWorldTest {

    private Flux<String> fruitFlux ;

    @Before
    public void before(){
        fruitFlux = Flux.just("Apple","Orange","Grape","Banana","Strawberry") ;
    }

    @Test
    public void test1(){
        fruitFlux.subscribe(f -> System.out.println("Here's some fruit: " + f)) ;
        Function<Integer,String> fun = a -> a + "" ;
        Consumer<Integer> consumer = a ->{} ;
    }

    @Test
    public void test2(){
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete()
        ;
    }

    @Test
    public void test3(){
        //Apple apple = Apple::new ;
        Supplier<Apple> c1 = Apple::new ;
        Supplier<Apple> c2 = () -> new Apple() ;
    }
}
