package com.completable.future.database;

import com.completable.future.RunAsync;
import com.completable.future.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EmployeeDatabase {

    public static List<Employee> fetchEmployees() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = RunAsync.class.getClassLoader().getResourceAsStream("employees.json");
            return objectMapper.readValue(inputStream, new TypeReference<List<Employee>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
