package com.completable.future;

import com.completable.future.database.EmployeeDatabase;
import com.completable.future.dto.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupplyAsync {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsync supplyAsync = new SupplyAsync();
        supplyAsync.getEmployees().forEach(System.out::println);
    }

    private List<Employee> getEmployees() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        java.util.concurrent.CompletableFuture<List<Employee>> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executed by : " + Thread.currentThread().getName());
            return EmployeeDatabase.fetchEmployees();
        }, executor);
        return future.get();
    }

}
