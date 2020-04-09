package com.honeycomb.reactive.rxjava;

import io.reactivex.Observable;
import org.junit.Test;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ObservableErrorTest {

    void observableErrorTest(int n){
        Observable.create(emitter -> {
            emitter.onNext(n);
            emitter.onComplete();
        }).subscribe(x -> errorTest(n), Throwable::printStackTrace, () -> System.out.println("Emission completed"));
    }

    void errorTest(int n){
        if(n == 5) {
            throw new RuntimeException("我就是我");
        }
        System.out.println("我消费的元素是 -->" + n);
    }

    @Test
    public void test1(){
        observableErrorTest(1);
        observableErrorTest(5);
    }


    int errorTestP(int n){
        if(n == 5) {
            throw new RuntimeException("我就是我");
        }
        System.out.println("我消费的元素是 -->" + n);
        return n + 10;
    }

    /**
     *  参数是callback函数，函数返回值就是发布的数据
     *  io.reactivex.Observable.fromCallable在onComplete函数中调用了onNext函数。
     */
    Observable<Integer> errorTestPro(int n){
        return Observable.fromCallable(() -> errorTestP(n));
    }

    @Test
    public void test2(){
        errorTestPro(1).subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("Emission completed"));
        errorTestPro(5).subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("Emission completed"));
    }
}
