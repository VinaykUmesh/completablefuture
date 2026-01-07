package com.completable.future;

import com.completable.future.database.EmployeeDatabase;
import com.completable.future.dto.Employee;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CombineCompletableFuture {

    public CompletableFuture<Employee> getEmployeeDetails(String empId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("getEmployeeDetails() " + Thread.currentThread().getName());
            return EmployeeDatabase.fetchEmployees().stream()
                    .filter(employee -> employee.getEmployeeId().equals(empId))
                    .findAny().orElse(null);
        });
    }

    public CompletableFuture<Integer> getRatings(Employee employee) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("getRatings() " + Thread.currentThread().getName());
            return employee.getRating();
        });
    }

    public CompletableFuture<Map<String, Long>> countEmployeeByGender() {
        return CompletableFuture
                .supplyAsync(() -> {
                    return EmployeeDatabase.fetchEmployees()
                            .stream()
                            .collect(Collectors.groupingBy(
                                    Employee::getGender,
                                    Collectors.counting()
                            ));
                });
    }

    public CompletableFuture<List<String>> getEmails() {
        return CompletableFuture
                .supplyAsync(() -> {
                    return EmployeeDatabase.fetchEmployees()
                            .stream()
                            .map(Employee::getEmail)
                            .collect(Collectors.toList());
                });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // to combine 2 dependent futures : thenCompose
        CombineCompletableFuture completableFuture = new CombineCompletableFuture();
        CompletableFuture<Integer> thenComposeResult = completableFuture.getEmployeeDetails("05-502-0622")
                .thenCompose(completableFuture::getRatings);
        System.out.println("ratings : " + thenComposeResult.get());

        CompletableFuture<Map<String, Long>> employeeMapFuture = completableFuture.countEmployeeByGender();
        CompletableFuture<List<String>> emailsFuture = completableFuture.getEmails();

        // to combine 2 independent futures : thenCombine
        CompletableFuture<String> thenCombineResults = employeeMapFuture
                .thenCombine(emailsFuture, (empMap, emails) -> empMap + " " + emails);
        System.out.println(thenCombineResults.get());
    }
}
