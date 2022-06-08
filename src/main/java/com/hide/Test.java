package com.hide;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * @Author Zhiwei Jian
 * @create 1/13/20 6:55 AM
 */
public class Test {


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i < 5; i++) {
            semaphore.acquireUninterruptibly();
            printA.apply(String.valueOf(i)).thenAccept(result->{
                    semaphore.release();
                }
            );
        }

        System.out.println("123");
    }

    static Function<String, CompletableFuture<Boolean>> printA = (a-> {
        System.out.println(Thread.currentThread().getName() + "____" +a);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new CompletableFuture<Boolean>();}
        );
}
