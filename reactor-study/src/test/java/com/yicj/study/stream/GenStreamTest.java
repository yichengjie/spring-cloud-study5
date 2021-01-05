package com.yicj.study.stream;

import org.junit.Test;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GenStreamTest {

    @Test
    public void test1(){
        Stream.iterate(0, n -> n+ 2)
                .limit(10)
                .sorted((a,b) -> a > b ? -1 : 1)
                .forEach(System.out::println);
    }

    @Test
    public void test2(){
        Stream.generate(() -> Math.random())
                .limit(5)
                .forEach(System.out::println);
    }

    @Test
    public void test3(){
        IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 2;
            }
        }).limit(5).forEach(System.out::println);
    }

    @Test
    public void test4(){
        IntSupplier fib = new IntSupplier() {
            private int previous = 0 ;
            private int current = 1 ;

            @Override
            public int getAsInt() {
                int oldPrevious = this.previous ;
                int nextValue = this.previous + this.current ;
                this.previous = this.current ;
                this.current = nextValue ;
                return oldPrevious;
            }
        } ;
        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }
}
