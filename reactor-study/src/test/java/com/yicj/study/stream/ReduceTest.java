package com.yicj.study.stream;

import com.yicj.study.reactor.model.Dish;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

public class ReduceTest {
    private List<Integer> numberList ;
    private List<Dish> menu ;

    @Before
    public void before(){
        numberList = Arrays.asList(1,2,3,4,5) ;
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
    public void testSum(){
        Integer reduce = numberList.stream().reduce(0, (a, b) -> {
            System.out.println("a : " + a + " , " + b);
            return a + b;
        });
        System.out.println(reduce);
    }

    @Test
    public void testMax(){
        Optional<Integer> reduce = numberList.stream().reduce((a, b) -> a > b ? a : b);
        System.out.println(reduce.get());
    }

    @Test
    public void testMin(){
        Optional<Integer> reduce = numberList.stream().reduce((a, b) -> a > b ? b : a);
        System.out.println(reduce.get());
    }

    @Test
    public void countMenu(){
        Optional<Integer> reduce = menu.stream()
                .map(item -> 1)
                .reduce((a, b) -> a + b);
        System.out.println(reduce.get());
    }

    @Test
    public void countMenu2(){
        long count = menu.stream().count();
        System.out.println(count);
    }

    @Test
    public void toMap(){
//        menu.stream()
//            .map(Dish::getCalories)
//            .sum() ;
        menu.stream()
            .mapToInt(Dish::getCalories)
            .sum() ;
    }

    @Test
    public void toMap2(){
        Map<String, Dish> reduce = menu.stream().reduce(new HashMap<>(), (Map<String, Dish> map, Dish d) -> {
            map.put(d.getName(), d);
            return map;
        }, (Map<String, Dish> m1, Map<String, Dish> m2) -> {
            m1.putAll(m2);
            return m1;
        });
        System.out.println(reduce.size());
    }

    @Test
    public void test2(){
        numberList.stream().reduce(new ArrayList<>(), (List<Integer> li, Integer e)->{
            li.add(e) ;
            return li ;
        },(List<Integer> l1, List<Integer> l2) ->{
            l1.addAll(l2) ;
            return l1 ;
        }) ;

    }
}
