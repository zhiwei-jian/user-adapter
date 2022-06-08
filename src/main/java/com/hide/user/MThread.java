package com.hide.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MThread {


    public static void main(String args[]) throws ExecutionException, InterruptedException {
        CompletableFuture<?> completableFuture = CompletableFuture.completedFuture("123");
        System.out.println(completableFuture.get());

        test5();
    }


    private static void test5(){

        CompletableFuture<Void> f0 = CompletableFuture.supplyAsync(()->"0").thenAcceptAsync(s->{sleep(3000);System.out.println(s); printThread();});
        CompletableFuture<Void> f1 = CompletableFuture.supplyAsync(()->"1").thenAcceptAsync(s->{System.out.println(s); printThread();});
        CompletableFuture<Void> f2 = CompletableFuture.supplyAsync(()->"2").thenAcceptAsync(s->{System.out.println(s); printThread();});
        CompletableFuture<Void> f3 = CompletableFuture.supplyAsync(()->"3").thenAcceptAsync(s->{System.out.println(s); printThread();});

        f0.join();
        System.out.println(f1.isDone() + "" + f2.isDone() + f3.isDone());
        CompletableFuture.allOf(f1,f2,f3).whenCompleteAsync((r, e)->{System.out.println("ii" + f1.isDone() + "" + f2.isDone() + f3.isDone());f0.join();});
        System.out.println(f1.isDone() + "" + f2.isDone() + f3.isDone());
        System.out.println("Finish1");

        sleep(3000);
        System.out.println("Finish2");
    }
    private static void test4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f = new CompletableFuture<>();
        f = f.supplyAsync(()-> "1");

//       f.thenApply(s -> {System.out.println("first" + s);
//        return s+1;});
//
//        f.thenApply(s -> {System.out.println("second" + s);
//        return s+1;
//        });
//
//        CompletableFuture<Void> f2 =
                f.thenRun(() -> System.out.println("first"));

        f.thenRun(() -> System.out.println("Second"));


//        final CompletableFuture<Integer> nf = f;
//        new Thread(()-> {
//            try {
//                nf.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }).start();

//        System.out.println(f.get());
//        nf.join();
//        f2.join(); //.complete(null);System.out.println(f.get());
        f.complete("");
    }

    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<Integer> future = executorService.submit(()->{
            try {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        Future<Integer> future2 = executorService.submit(()->{
            try {
                Thread.sleep(10000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });

        System.out.println(future.get());
        System.out.println(future2.get());
        System.out.println("Finish");
    }

    public static void test3() throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
//        ForkJoinPool.commonPool().submit();

        CompletableFuture<Integer> future_M = CompletableFuture.supplyAsync(()->{
            printThread();
            sleep(3000);
            return 1;
        });
        CompletableFuture<Integer> future = future_M.thenApply(e->{
            printThread();
            return e + 1;
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()->{
            printThread();
            sleep(3000);
            return 1;
        }).thenApplyAsync(e->{
            printThread();
            return e + "1";
        });
        System.out.println(future.join());
        System.out.println(future.get());
        System.out.println(future2.get());
        System.out.println("Finish");
    }

    private static void printThread(){
        System.out.println(Thread.currentThread().getName());
    }

    private static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test2() throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread().getName());
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(()->{
            System.out.println("------1------------");
            System.out.println(Thread.currentThread().getName());
//            try {
//                Thread.sleep(6000);
//                System.out.println(Thread.currentThread().getName());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return 1;
        });
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()->{
            System.out.println("------2------------");
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });
        CompletableFuture<Void> future2 = CompletableFuture.allOf(future, future1);
        System.out.println(future2.get());
        System.out.println("Finish");
    }
}
