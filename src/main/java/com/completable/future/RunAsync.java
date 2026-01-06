package com.completable.future;

import com.completable.future.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RunAsync {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveEmployees(String fileName) throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                InputStream inputStream = RunAsync.class.getClassLoader().getResourceAsStream(fileName);
                List<Employee> employees = objectMapper.readValue(inputStream, new TypeReference<List<Employee>>() {
                });

                // database save operations in spring boot and jpa
                // repository.saveAll(employee);

                System.out.println("Thread : " + Thread.currentThread().getName());
                System.out.println(employees);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executor);

        future.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunAsync runAsync = new RunAsync();
        runAsync.saveEmployees("employees.json");
    }

}
