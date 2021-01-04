package com.yicj.study.stream;

import com.yicj.study.reactor.model.Dish;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloStreamTest {

    private List<Dish> menu ;

    @Before
    public void before(){
        menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef",false, 700, Dish.Type.MEAT),
            new Dish("chicken",false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns",false, 300, Dish.Type.FISH),
            new Dish("salmon",false, 450, Dish.Type.FISH)
        ) ;
    }

    @Test
    public void test1(){

        List<Dish> menu = new ArrayList<>() ;
        menu.parallelStream()
                .filter(f -> f.getCalories() < 400)
                //.sorted((f1,f2) -> f1.getCalories() - f2.getCalories())
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(Collectors.toList()) ;

    }


    @Test
    public void test2(){
        List<String> words = Arrays.asList("Hello", "World");
        List<String[]> collect = words.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());

        List<Stream<String>> collect1 = words.stream()
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        List<String> collect2 = words.stream()
                .map(word -> word.split(""))
                .flatMap(w -> Arrays.stream(w))
                .distinct()
                .collect(Collectors.toList());

        collect2.stream().forEach(System.out::println);
    }

    @Test
    public void test3(){
        List<Integer> numbers1 = Arrays.asList(1, 2, 3) ;
        List<Integer> numbers2 = Arrays.asList(3, 4) ;
        List<int[]> collect = numbers1.stream()
                .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

    }
}
