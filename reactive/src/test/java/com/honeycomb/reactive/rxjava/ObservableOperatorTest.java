package com.honeycomb.reactive.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableObserver;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ObservableOperatorTest {

    /**
     * flatMap是用于一个observable映射出多个observable进行合并
     */
    @Test
    public void testFlatMap() {
        Observable.range(1, 10).flatMap(x -> Observable.range(x, 3))
                .subscribe(System.out::println);
    }

    /**
     * merge是用于多个同一个泛型observable进行和并成一个observable
     */
    @Test
    public void testMerge() {
        Observable.merge(Observable.range(1, 3), Observable.create(emitter -> {
            emitter.onNext(4);
            emitter.onComplete();
        })).subscribe(System.out::println);
    }

    /**
     * zip是用于多个不同/相同泛型的observable进行映射成另一个Observable
     */
    @Test
    public void testZip() {
        WeatherStation weatherStation = new BasicWeatherStation();
        Observable.zip(weatherStation.temperature(), weatherStation.wind(), Weather::new)
                .subscribe(System.out::println);
    }

    /**
     * 如果一方没有元素了则直接进入onComplete阶段
     */
    @Test
    public void testZip1() {
        Observable.zip(Observable.just(1), Observable.empty(), (x, y) -> x + " : " + y)
                .subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("Complete"));
    }

    /**
     * zip是一对一匹配，如果有多则忽略，对于无限流，先发布的一方会等待另一方发布元素之后在合并发布。
     */
    @Test
    public void testZip2() {
        Integer[] numbers = {1, 2, 13, 34, 15, 17};
        String[] fruits = {"apple", "pear", "plum", "litchi", "mango"};

        Observable.zip(Observable.fromArray(numbers), Observable.fromArray(fruits), (x, y) -> y + " : " + x)
                .subscribe(System.out::println);

    }

    /**
     * combineLatest是用于多个不同/相同泛型的observable进行映射成另一个Observable,
     * 与zip不同的是如果一方为发布消息，则匹配是会取其最后一次发布的信息。
     * 说明：
     * 2020-04-12T22:12:06.346342 服务启动
     * 2020-04-12T22:12:08.499779 : java0 - 2020-04-12T22:12:08.499753 : spring1 //2秒后第一次聚合（聚合必须是多个Observable都发布一次元素），此时spirng已到第二次发布了。
     * 2020-04-12T22:12:08.499779 : java0 - 2020-04-12T22:12:09.499754 : spring2 //3秒，spring发布，聚合上次java结果
     * 2020-04-12T22:12:10.500187 : java1 - 2020-04-12T22:12:09.499754 : spring2 //4秒，java1发布，聚合上次Spring结果
     * 2020-04-12T22:12:10.500187 : java1 - 2020-04-12T22:12:10.500222 : spring3 //4秒，spring发布，聚合上次java发布结果
     * 2020-04-12T22:12:10.500187 : java1 - 2020-04-12T22:12:11.496107 : spring4 //...
     * 2020-04-12T22:12:10.500187 : java1 - 2020-04-12T22:12:12.495863 : spring5 //...
     * 2020-04-12T22:12:12.495982 : java2 - 2020-04-12T22:12:12.495863 : spring5 //...
     *
     * @throws InterruptedException
     */
    @Test
    public void testCombineLatest() throws InterruptedException {
        System.out.println(LocalDateTime.now());
        Observable.combineLatest(
                Observable.interval(1, TimeUnit.SECONDS).map(x -> LocalDateTime.now() + " : spring" + x),
                Observable.interval(2, TimeUnit.SECONDS).map(x -> LocalDateTime.now() + " : java" + x),
                (x, y) -> y + " - " + x).forEach(System.out::println);

        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 对于非无线流，且没有延迟时间的，会等一方发布完之后，在聚另一方结果。
     * 底层通过锁控制，从一个Observable开始取发布元素（会把发布的元素拼接成链表，遍历链表，但遍历到最后一个为Null时，释放锁，下一个Observable进来），
     * 直到Observable没有再发布元素之后就会换下一个Observable。
     */
    @Test
    public void testCombineLatest1() {
        Integer[] numbers = {1, 2, 13, 34, 15, 17};
        String[] fruits = {"apple", "pear", "plum", "litchi", "mango"};

        Observable.combineLatest(Observable.fromArray(numbers), Observable.fromArray(fruits), (x, y) -> y + " : " + x)
                .subscribe(System.out::println);

        System.err.println("******************************");

        Integer[] month = {1, 2, 3, 4, 5};
        Observable.combineLatest(Observable.fromArray(numbers), Observable.fromArray(fruits), Observable.fromArray(month), (x, y, z) -> "month-" + z + " : " + y + " : " + x)
                .forEach(System.out::println);
    }

    /**
     * 会以调用方为发布驱动器，调用方每发布一次数据就会取和被调用方最后一个发布数据匹配（持续发布的取最后一个值），
     */
    @Test
    public void testWithLatestFrom() {
        Integer[] numbers = {1, 2, 13, 34, 15, 17};
        String[] fruits = {"apple", "pear", "plum", "litchi", "mango"};
        Observable<Integer> integerObservable = Observable.fromArray(numbers);
        Observable<String> stringObservable = Observable.fromArray(fruits);
        integerObservable.withLatestFrom(stringObservable, (x, y) -> y + " : " + x)
                .forEach(System.out::println);

        System.err.println("******************************");

        //没有结果，因为一方已经发布完成，另一方发布时则直接进入onComplete阶段
        Observable<Integer> integerObservable1 = Observable.fromArray(numbers).delay(1, TimeUnit.SECONDS);
        Observable<String> stringObservable1 = Observable.fromArray(fruits);
        integerObservable1.withLatestFrom(stringObservable1, (x, y) -> y + " : " + x)
                .forEach(System.out::println);
    }

    /**
     * 和testCombineLatest不同的是，如果调用方发布的时候，被调用方还没有发布数据，则这次发布的数据无效，
     * 且是以调用发布为驱动，被调用方主动发布数据是不会做聚合操作。
     * @throws InterruptedException
     */
    @Test
    public void testWithLatestFrom1() throws InterruptedException {
        System.out.println(LocalDateTime.now());
        Observable<String> map = Observable.interval(1, TimeUnit.SECONDS).map(x -> LocalDateTime.now() + " : spring" + x);
        Observable<String> map1 = Observable.interval(2, TimeUnit.SECONDS).map(x -> LocalDateTime.now() + " : java" + x);
        map.withLatestFrom(map1, (x, y) -> x + " - " + y).forEach(System.out::println);

        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * amb:最先发布的Obserable为最后的Observable，其他Observable忽略。
     * 其结果泛型为所有Obserable结果泛型的最小公共接口/类
     */
    @Test
    public void testAmb(){
        Integer[] numbers = {1, 2, 13, 34, 15, 17};
        String[] fruits = {"apple", "pear", "plum", "litchi", "mango"};
        Observable<Integer> integerObservable = Observable.fromArray(numbers).delay(1, TimeUnit.SECONDS);
        Observable<String> stringObservable = Observable.fromArray(fruits);

        Observable.ambArray(integerObservable, stringObservable)
                .forEach(System.out::println);
    }

    @Test
    public void collect(){
        Single<ArrayList<Object>> collect = Observable.range(10, 20)
                .collect(ArrayList::new, List::add);
        collect.subscribe(System.out::println);
    }

    @Test
    public void distinct(){
        Integer[] integers = {1, 1, 1, 2, 2, 3, 1, 3, 1};
        Observable.fromArray(integers).distinct().forEach(System.out::println);

        System.err.println("******************************");

        //最近不重复 -> 结果 1， 2， 3， 1， 3， 1
        Observable.fromArray(integers).distinctUntilChanged().forEach(System.out::println);
    }

    /**
     * 1. take(n)与skip(n):take(n)用来获取前n个元素，skip(n)用来忽略前n个元素。
     * 2. takeLast(n)与skipLast(n):takeLast(n)用来获取最后n个元素，skipLast(n)用来忽略最后n个元素。
     * 3. first(defaultValue)与last(defaultValue):first(defaultValue)用来获取首个元素，如果没有则取默认值。last(defaultValue)用来获取最后一个元素，如果没有则取默认值。
     * 4. takeUntil(predicate)与takeWhile(predicate):前者用来获取符合条件之前与包括符合条件的元素，
     *    后者用来获取符合条件之前但不包括符合条件的元素，注意是符合条件之前的元素，一旦符合条件就会发出onComplete事件。
     * 5. all(predicate)与any(predicate)、contains(value):all(predicate)要求所有元素都符合条件，any(predicate)只要一个满足条件即可。contains(value)代表是否包含这个值的元素。
     */
    @Test
    public void testBasicOperator(){
        Observable<Integer> range = Observable.range(1, 5);
        //[1, 2, 3]
        range.take(3).collect(ArrayList::new, List::add).subscribe(System.out::println);
        //[4, 5]
        range.skip(3).collect(ArrayList::new, List::add).subscribe(System.out::println);

        //[3, 4, 5]
        range.takeLast(3).collect(ArrayList::new, List::add).subscribe(System.out::println);
        //[1, 2]
        range.skipLast(3).collect(ArrayList::new, List::add).subscribe(System.out::println);

        //4
        range.skip(3).first(-1).subscribe(System.out::println);
        //5
        range.skip(3).last(-1).subscribe(System.out::println);

        //[1, 2, 3]
        range.takeUntil(x -> x == 3).collect(ArrayList::new, List::add).subscribe(System.out::println);
        //[1, 2]
        range.takeWhile(x -> x != 3).collect(ArrayList::new, List::add).subscribe(System.out::println);

        //false
        range.all(x -> x == 3).subscribe(System.out::println);
        //true
        range.any(x -> x == 3).subscribe(System.out::println);
        //true
        range.contains(3).subscribe(System.out::println);
    }

    /**
     * compose从上游端来抽象操作。
     * 上游：发布数据的Observable。
     * 下游：接受数据的Observer。
     */
    @Test
    public void testCompose(){
        String[] fruits = {"apple", "pear", "plum", "litchi", "mango"};
        Observable.fromArray(fruits)
                .collect(ArrayList::new, List::add)
                .map(Object::toString)
                .subscribe(System.out::println);

        Integer[] numbers = {1, 2, 13, 34, 15, 17};
        Observable.fromArray(numbers)
                .collect(ArrayList::new, List::add)
                .map(Object::toString)
                .subscribe(System.out::println);

        System.out.println("*************************");

        Observable.fromArray(fruits)
                .compose(string())
                .subscribe(System.out::println);

        Observable.fromArray(numbers)
                .compose(string())
                .subscribe(System.out::println);
    }

    private <T> ObservableTransformer<T, String> string(){
        return upstream -> upstream.collect(ArrayList::new, List::add).map(Objects::toString).toObservable();
    }

    /**
     * lift从下游端来抽象数据，通过代理模式。
     * 上游：发布数据的Observable。
     * 下游：接受数据的Observer。
     */
    @Test
    public void testLift(){
        Observable.range(1, 5)
                .lift(doOnEmpty(() -> System.out.println("Operation 1 Empty!")))
                .subscribe(v -> System.out.println("Operation 1:" + v));

        System.out.println("********************");

        Observable.empty()
                .lift(doOnEmpty(() -> System.out.println("Operation 1 Empty!")))
                .subscribe(v -> System.out.println("Operation 1:" + v));
    }

    private <T>ObservableOperator<T, T> doOnEmpty(Action action){
        return observer -> new DisposableObserver<T>() {

            boolean isEmpty = true;

            @Override
            public void onNext(T t) {
                isEmpty = false;
                observer.onNext(t);
            }

            @Override
            public void onError(Throwable e) {
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                if(isEmpty){
                    try {
                        action.run();
                    } catch (Exception e) {
                        onError(e);
                    }
                }else {
                    observer.onComplete();
                }
            }
        };
    }
}


interface WeatherStation {

    Observable<Temperature> temperature();

    Observable<Wind> wind();
}

class BasicWeatherStation implements WeatherStation {

    @Override
    public Observable<Temperature> temperature() {
        return Observable.just(new Temperature());
    }

    @Override
    public Observable<Wind> wind() {
        return Observable.just(new Wind());
    }
}

class Temperature {
}

class Wind {
}

class Weather {

    private final Temperature temperature;

    private final Wind wind;

    Weather(Temperature temperature, Wind wind) {
        this.temperature = temperature;
        this.wind = wind;
    }

    public boolean isSunny() {
        return true;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Wind getWind() {
        return wind;
    }
}