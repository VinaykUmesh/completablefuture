package com.completable.future;

import com.completable.future.database.EmployeeDatabase;
import com.completable.future.dto.Employee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeRemainderService {

    public CompletableFuture<Void> sendReminderToEmployee() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(10);
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("fetch all Employees : " + Thread.currentThread().getName());
            return EmployeeDatabase.fetchEmployees();
        }, executor).thenApplyAsync(employees -> {
            System.out.println("filter new joiner employee  : " + Thread.currentThread().getName());
            return employees.stream().filter(employee -> employee.getNewJoiner().equals("TRUE"))
                    .collect(Collectors.toList());
        }, executor).thenApplyAsync(employees -> {
            System.out.println("filter training not complete employee  : " + Thread.currentThread().getName());
            return employees.stream().filter(employee -> employee.getLearningPending().equals("TRUE"))
                    .collect(Collectors.toList());
        }, executor).thenApplyAsync(employees -> {
            System.out.println("get emails  : " + Thread.currentThread().getName());
            return employees.stream().map(Employee::getEmail).collect(Collectors.toList());
        }, executor).thenAcceptAsync(emails -> {
            System.out.println("send email  : " + Thread.currentThread().getName());
            emails.forEach(EmployeeRemainderService::sendEmail);
        }, executor);
    }

    public static void sendEmail(String email) {
        System.out.println("sending training reminder email to : " + email);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EmployeeRemainderService remainderService = new EmployeeRemainderService();
        remainderService.sendReminderToEmployee().get();

    }
}
