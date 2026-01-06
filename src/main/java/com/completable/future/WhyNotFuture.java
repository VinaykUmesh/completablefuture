package com.completable.future;

import com.completable.future.database.EmployeeDatabase;
import com.completable.future.dto.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class WhyNotFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);


        Future<List<Integer>> future = executor.submit(() -> {
            //your doing api call
            System.out.println("Thread : " + Thread.currentThread().getName());
            System.out.println(10 / 0);
            return Arrays.asList(1, 2, 3, 4);
        });

        List<Integer> integers = future.get();
        System.out.println(integers);



        // cannot be manually completed
        // cannot chain multiple futures
        // cannot combine multiple future
        // cannot implement Exception Handling Mechanism

        CompletableFuture<String> completableFuture=new CompletableFuture<>();
        completableFuture.get();
        completableFuture.complete("return some dummy data.....");


    }

    public static void delay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
