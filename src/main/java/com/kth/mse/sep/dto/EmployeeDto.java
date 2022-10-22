package com.kth.mse.sep.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private String department;

    private String position;
}
