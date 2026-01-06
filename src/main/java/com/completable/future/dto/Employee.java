package com.completable.future.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private Integer salary;
    private String dateOfJoining;
}
