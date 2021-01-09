package com.yicj.study.reactor;

import com.sun.prism.shader.Texture_LinearGradient_REFLECT_AlphaTest_Loader;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HelloWorld2Test {

    @Test
    public void test1(){
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        Mono<Integer> just = Mono.just(1);
        Integer [] integers = {1, 2, 3, 4, 5, 6} ;
        Flux<Integer> arrayFlux = Flux.fromArray(integers);
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Flux<Integer> listFlux = Flux.fromIterable(list);
//        arrayFlux.subscribe(System.out::println) ;
//        arrayFlux.subscribe(System.out::println,
//                System.err::println,
//                ()-> System.out.println("complete"),
//                subscription -> subscription.request(3)
//        ) ;
//        listFlux.subscribe(new DemoSubscriber()) ;
//        flux.map(i -> i*3).subscribe(System.out::println) ;
//        flux.flatMap(i -> arrayFlux).subscribe(System.out::println) ;
//        flux.filter(i -> i>3).subscribe(System.out::println) ;
//        Flux.zip(flux, arrayFlux).subscribe(System.out::println) ;

    }


    @Test
    public void testPublishOn(){
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        flux.map(i -> {
            System.out.println(Thread.currentThread().getName() + "-map1");
            return i * 3;
        }).publishOn(Schedulers.elastic()).map(
                i->{
                    System.out.println(Thread.currentThread().getName() +"-map2");
                    return i /3 ;
                }
        ).subscribe(i ->{
            System.out.println(Thread.currentThread().getName() +"- ["+i+"]" );
        });
    }

    @Test
    public void testSubscribeOn() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(6) ;
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        flux.map(i -> {
            System.out.println(Thread.currentThread().getName() + "-map1");
            return i * 3;
        }).publishOn(Schedulers.elastic())
        .map(i->{
            System.out.println(Thread.currentThread().getName() +"-map2");
            return i /3 ;
        }).subscribeOn(Schedulers.parallel())
        .subscribe(i ->{
            System.out.println(Thread.currentThread().getName() +"- ["+i+"]" );
            latch.countDown();
        });
        latch.await();
    }



    class DemoSubscriber extends BaseSubscriber<Integer>{
        // 订阅的时候触发
        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            System.out.println("subscribe");
            // 请求一个元素消费
            subscription.request(1) ;
        }

        @Override
        protected void hookOnNext(Integer value) {
            if (value == 4){
                cancel();
            }
            System.out.println("value: " + value);
            // 请求下一个元素
            request(1);
        }
    }
}
